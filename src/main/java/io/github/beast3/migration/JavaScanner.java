package io.github.beast3.migration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import io.github.beast3.migration.Report.Kind;

/**
 * Walks a package's Java source tree and classifies each top-level class by
 * kind (Distribution / Operator / etc.) and migration status (spec / legacy
 * / mixed). Heuristics only — no symbol resolution.
 */
public final class JavaScanner {

    private static final Set<String> DISTRIBUTION_BASES = Set.of(
            "Distribution", "ParametricDistribution", "ScalarDistribution",
            "TensorDistribution", "GenericDistribution", "BEASTDistribution",
            "Prior", "TreeDistribution", "SpeciesTreeDistribution");

    private static final Set<String> OPERATOR_BASES = Set.of(
            "Operator", "ScaleOperator", "TreeOperator", "KernelOperator",
            "RealOperator", "BactrianScaleOperator", "BactrianRandomWalkOperator",
            "AdaptableOperatorSampler");

    private static final Set<String> LOGGER_INTERFACES = Set.of(
            "Loggable", "Logger");

    private static final Set<String> CALCNODE_BASES = Set.of(
            "CalculationNode");

    private static final Set<String> PARAM_BASES = Set.of(
            "RealParameter", "IntegerParameter", "BooleanParameter",
            "Parameter",
            "RealScalarParam", "RealVectorParam",
            "IntScalarParam", "IntVectorParam",
            "BoolScalarParam", "BoolVectorParam",
            "SimplexParam");

    private static final Set<String> STATENODE_BASES = Set.of(
            "StateNode");

    /**
     * Authoritative legacy registry: every class annotated {@code @Deprecated}
     * across all scanned packages. Built in two phases by {@link Main} —
     * Phase 1 scans every package collecting {@link ClassRecord}s with
     * {@link ClassRecord#isDeprecated}; Phase 2 collects their FQNs into a
     * global set; Phase 3 calls {@link #resolveAndTally(Report, Set)} which
     * marks a class LEGACY when its {@code primaryExtendsFqn} is in the set
     * (and the chain resolver propagates).
     *
     * <p>This replaces the previous hand-curated lists. {@code @Deprecated}
     * is the canonical source of truth in beast3 — {@code Prior},
     * {@code ParametricDistribution}, {@code RealParameter}, {@code Function},
     * {@code HKY}, {@code GTR}, every legacy substitution / branch-rate /
     * coalescent / site-model class, etc., are all explicitly annotated.
     * Notable non-deprecated: the canonical interfaces
     * {@code BranchRateModel} and {@code SubstitutionModel} (spec {@code Base}
     * still implements them).</p>
     */

    /**
     * Package prefixes that classify the *kind* of a base class regardless of
     * its simple name. This catches classes that {@code extends Base} where
     * {@code Base} is e.g. {@code SubstitutionModel.Base} resolved through an
     * import. Order matters: more specific kinds first.
     */
    private static final Map<String, Kind> PACKAGE_KINDS = orderedPackageKinds();

    private static Map<String, Kind> orderedPackageKinds() {
        Map<String, Kind> m = new java.util.LinkedHashMap<>();
        // Distributions
        m.put("beast.base.spec.inference.distribution.", Kind.DISTRIBUTION);
        m.put("beast.base.inference.distribution.",      Kind.DISTRIBUTION);
        m.put("beast.base.spec.evolution.speciation.",   Kind.DISTRIBUTION);
        m.put("beast.base.evolution.speciation.",        Kind.DISTRIBUTION);
        // Operators
        m.put("beast.base.spec.inference.operator.",     Kind.OPERATOR);
        m.put("beast.base.inference.operator.",          Kind.OPERATOR);
        m.put("beast.base.spec.evolution.operator.",     Kind.OPERATOR);
        m.put("beast.base.evolution.operator.",          Kind.OPERATOR);
        // Parameters
        m.put("beast.base.spec.inference.parameter.",    Kind.PARAMETER);
        m.put("beast.base.inference.parameter.",         Kind.PARAMETER);
        // Calculation-node-flavoured model components
        m.put("beast.base.spec.evolution.branchratemodel.",  Kind.CALCNODE);
        m.put("beast.base.evolution.branchratemodel.",       Kind.CALCNODE);
        m.put("beast.base.spec.evolution.substitutionmodel.",Kind.CALCNODE);
        m.put("beast.base.evolution.substitutionmodel.",     Kind.CALCNODE);
        m.put("beast.base.spec.evolution.sitemodel.",        Kind.CALCNODE);
        m.put("beast.base.evolution.sitemodel.",             Kind.CALCNODE);
        m.put("beast.base.spec.evolution.likelihood.",       Kind.CALCNODE);
        m.put("beast.base.evolution.likelihood.",            Kind.CALCNODE);
        m.put("beast.base.spec.evolution.tree.coalescent.",  Kind.CALCNODE);
        m.put("beast.base.evolution.tree.coalescent.",       Kind.CALCNODE);
        return m;
    }

    private static final Set<String> CALCNODE_FQNS = Set.of(
            "beast.base.inference.CalculationNode",
            "beast.base.evolution.tree.coalescent.PopulationFunction",
            "beast.base.evolution.substitutionmodel.SubstitutionModel",
            "beast.base.evolution.sitemodel.SiteModel",
            "beast.base.evolution.sitemodel.SiteModelInterface",
            "beast.base.evolution.branchratemodel.BranchRateModel",
            "beast.base.evolution.likelihood.GenericTreeLikelihood");

    private static final Set<String> STATENODE_FQNS = Set.of(
            "beast.base.inference.StateNode",
            "beast.base.evolution.tree.Tree",
            "beast.base.evolution.alignment.Alignment");

    private static final Set<String> LOGGER_FQNS = Set.of(
            "beast.base.core.Loggable");

    /** Spec interface types — what Distributions/CalcNodes should declare in Inputs. */
    private static final Set<String> INPUT_INTERFACE_NAMES = Set.of(
            "RealScalar", "RealVector",
            "IntScalar", "IntVector",
            "BoolScalar", "BoolVector",
            "Tensor", "Scalar", "Vector",
            "Simplex", "IntSimplex");

    /** Concrete spec param types — required only when the holder mutates the param. */
    private static final Set<String> INPUT_CONCRETE_SPEC_NAMES = Set.of(
            "RealScalarParam", "RealVectorParam",
            "IntScalarParam", "IntVectorParam",
            "BoolScalarParam", "BoolVectorParam",
            "SimplexParam");

    private static final Set<String> INPUT_LEGACY_NAMES = Set.of(
            "RealParameter", "IntegerParameter", "BooleanParameter",
            "RealParameterList", "IntegerParameterList", "BooleanParameterList",
            "Function");

    private static final Pattern BLOCK_COMMENT = Pattern.compile("/\\*[\\s\\S]*?\\*/");
    private static final Pattern LINE_COMMENT = Pattern.compile("(?m)//[^\\n]*");
    private static final Pattern PACKAGE_DECL = Pattern.compile("(?m)^\\s*package\\s+([\\w.]+)\\s*;");
    private static final Pattern IMPORT = Pattern.compile("(?m)^\\s*import\\s+(?:static\\s+)?([\\w.$*]+)\\s*;");
    /** Top-level class declaration: capture name, optional extends list, optional implements list. */
    private static final Pattern CLASS_DECL = Pattern.compile(
            "(?m)^(?:public\\s+|abstract\\s+|final\\s+|sealed\\s+|non-sealed\\s+|static\\s+)*" +
                    "(?:class|interface|enum|record)\\s+(\\w+)" +
                    "(?:\\s*<[^{]*?>)?" +
                    "(?:\\s*\\([^)]*\\))?" +                       // record header
                    "(?:\\s+extends\\s+([^{]+?))?" +
                    "(?:\\s+implements\\s+([^{]+?))?" +
                    "\\s*\\{",
            Pattern.MULTILINE);

    private JavaScanner() {}

    public static void scan(Path pkgRoot, Report report) {
        // Multi-module aware: find every src/main/java subtree, but skip
        // build outputs and any "target/" directory.
        try (Stream<Path> stream = Files.walk(pkgRoot, 8)) {
            stream.filter(Files::isDirectory)
                    .filter(p -> p.endsWith(Path.of("src", "main", "java")))
                    .filter(p -> !p.toString().contains("/target/"))
                    .forEach(srcRoot -> scanSrcRoot(srcRoot, report));
        } catch (IOException e) {
            report.error = appendError(report.error, "java scan: " + e.getMessage());
        }
    }

    private static void scanSrcRoot(Path srcRoot, Report report) {
        try (Stream<Path> stream = Files.walk(srcRoot)) {
            stream.filter(p -> p.toString().endsWith(".java"))
                    .filter(p -> !p.getFileName().toString().equals("module-info.java"))
                    .filter(p -> !p.getFileName().toString().equals("package-info.java"))
                    .forEach(p -> scanFile(p, report));
        } catch (IOException e) {
            report.error = appendError(report.error, "java scan " + srcRoot + ": " + e.getMessage());
        }
    }

    private static void scanFile(Path file, Report report) {
        String src;
        try {
            src = Files.readString(file);
        } catch (IOException e) {
            report.error = appendError(report.error, "read " + file + ": " + e.getMessage());
            return;
        }
        // strip comments so they don't pollute regex matches
        String stripped = LINE_COMMENT.matcher(BLOCK_COMMENT.matcher(src).replaceAll("")).replaceAll("");

        Map<String, String> simpleToFqn = collectImports(stripped);
        Set<String> importFqns = new HashSet<>(simpleToFqn.values());
        String pkgName = extractPackage(stripped);

        Matcher cm = CLASS_DECL.matcher(stripped);
        if (cm.find()) {
            String simpleName = cm.group(1);
            String extendsClause = cm.group(2);
            String implementsClause = cm.group(3);

            Set<String> tokens = parseTypeTokens(extendsClause, implementsClause);
            Set<String> resolved = resolveTokens(tokens, simpleToFqn);

            String primaryExtendsFqn = resolvePrimaryExtends(extendsClause, simpleToFqn, pkgName);
            Kind ownKind = classify(tokens, resolved);
            boolean ownHasSpec = computeOwnHasSpec(tokens, resolved);
            boolean isDeprecated = hasDeprecatedAnnotation(stripped, cm.start());

            java.util.List<InputDecl> inputs = extractInputs(stripped);

            // Counts and legacy-status are deferred until resolveAndTally()
            // — both depend on the global @Deprecated registry collected
            // across all scanned packages.
            report.classes.add(new ClassRecord(
                    file,
                    pkgName,
                    simpleName,
                    trim(extendsClause),
                    trim(implementsClause),
                    java.util.List.of(),                  // populated by resolver
                    java.util.List.copyOf(specEvidence(resolved, tokens)),
                    ownKind,
                    ownHasSpec,
                    false,                                // ownHasLegacy filled by resolver
                    primaryExtendsFqn,
                    isDeprecated,
                    java.util.List.copyOf(inputs)));
        }
    }

    /**
     * Walks backward from the class declaration through any preceding
     * annotations (correctly handling nested parens inside annotation args
     * — so {@code @Description("...(cumulative)...")} doesn't break the
     * scan) and returns true if any of them is {@code @Deprecated}.
     */
    private static boolean hasDeprecatedAnnotation(String src, int classDeclStart) {
        int i = classDeclStart - 1;
        int safety = 8000;
        while (i >= 0 && --safety > 0) {
            while (i >= 0 && Character.isWhitespace(src.charAt(i))) i--;
            if (i < 0) return false;
            // Scan back over optional annotation arg parens
            if (src.charAt(i) == ')') {
                int depth = 1;
                i--;
                while (i >= 0 && depth > 0) {
                    char c = src.charAt(i);
                    if (c == ')') depth++;
                    else if (c == '(') depth--;
                    i--;
                    if (--safety <= 0) return false;
                }
                if (i < 0) return false;
                while (i >= 0 && Character.isWhitespace(src.charAt(i))) i--;
                if (i < 0) return false;
            }
            // Walk back over the annotation name to '@'
            int nameEnd = i + 1;
            while (i >= 0 && (Character.isJavaIdentifierPart(src.charAt(i))
                    || src.charAt(i) == '.')) i--;
            if (i < 0 || src.charAt(i) != '@') return false;
            String name = src.substring(i + 1, nameEnd);
            if (name.equals("Deprecated") || name.endsWith(".Deprecated")) return true;
            i--;
        }
        return false;
    }

    /**
     * Extracts every {@code Input<X>} field type argument from the source.
     * Walks balanced angle brackets so nested generics like
     * {@code Input<RealScalar<? extends PositiveReal>>} work.
     */
    static java.util.List<InputDecl> extractInputs(String src) {
        java.util.List<InputDecl> out = new java.util.ArrayList<>();
        int i = 0;
        while ((i = src.indexOf("Input<", i)) != -1) {
            // Avoid matching identifiers that end in `Input` (e.g. `MyInput<`).
            if (i > 0 && (Character.isJavaIdentifierPart(src.charAt(i - 1))
                    || src.charAt(i - 1) == '.')) {
                i += 6;
                continue;
            }
            int start = i + 6;
            int depth = 1;
            int j = start;
            while (j < src.length() && depth > 0) {
                char c = src.charAt(j);
                if (c == '<') depth++;
                else if (c == '>') depth--;
                j++;
            }
            if (depth != 0) break;
            String inner = src.substring(start, j - 1).trim();
            // Confirm this looks like a field declaration: next non-whitespace
            // tokens should be an identifier and either `=` or `;`. Skip if it
            // looks like a method return type (`Input<X> foo()`), local var,
            // generic method type bound, etc.
            int k = j;
            while (k < src.length() && Character.isWhitespace(src.charAt(k))) k++;
            // a field name at minimum
            if (k >= src.length() || !Character.isJavaIdentifierStart(src.charAt(k))) {
                i = j;
                continue;
            }
            int nameStart = k;
            while (k < src.length() && Character.isJavaIdentifierPart(src.charAt(k))) k++;
            int nameEnd = k;
            while (k < src.length() && Character.isWhitespace(src.charAt(k))) k++;
            if (k >= src.length() || (src.charAt(k) != '=' && src.charAt(k) != ';')) {
                i = j;
                continue;
            }
            // Ignore trivial: empty name suggests bad parse.
            if (nameEnd > nameStart) {
                out.add(new InputDecl(inner, classifyInputCarrier(inner)));
            }
            i = j;
        }
        return out;
    }

    private static InputDecl.Carrier classifyInputCarrier(String inner) {
        // Strip leading wildcards / bounds: `? extends X` → X.
        String head = inner;
        int lt = head.indexOf('<');
        if (lt >= 0) head = head.substring(0, lt);
        int comma = head.indexOf(',');
        if (comma >= 0) head = head.substring(0, comma);
        head = head.trim();
        if (head.startsWith("?")) {
            int sp = head.lastIndexOf(' ');
            if (sp > 0) head = head.substring(sp + 1).trim();
        }
        int dot = head.lastIndexOf('.');
        String simple = dot >= 0 ? head.substring(dot + 1) : head;
        if (INPUT_INTERFACE_NAMES.contains(simple)) return InputDecl.Carrier.INTERFACE;
        if (INPUT_CONCRETE_SPEC_NAMES.contains(simple)) return InputDecl.Carrier.CONCRETE_SPEC;
        if (INPUT_LEGACY_NAMES.contains(simple)) return InputDecl.Carrier.LEGACY;
        return InputDecl.Carrier.OTHER;
    }

    /**
     * Walks the in-package primary-extends chain for every class collected
     * during {@link #scan(Path, Report)}, using the global {@code deprecatedFqns}
     * registry to decide LEGACY status. A class is LEGACY iff:
     * <ul>
     *   <li>its primary {@code extends} target is a deprecated class anywhere
     *       in the scanned set, or</li>
     *   <li>(transitively) its in-package parent inherited LEGACY this way.</li>
     * </ul>
     *
     * <p>A class inherits its parent's <em>kind</em> if its own kind was
     * {@link Kind#OTHER} (subclasses of a model component are model
     * components), and accumulates the parent's spec signals. Tallies the
     * effective values into {@link Report#javaCounts}.</p>
     */
    public static void resolveAndTally(Report report, Set<String> deprecatedFqns) {
        Map<String, ClassRecord> byFqn = new java.util.HashMap<>();
        for (ClassRecord c : report.classes) byFqn.put(c.fqn(), c);

        for (ClassRecord c : report.classes) {
            boolean effSpec = c.ownHasSpec;
            // Two ways to be LEGACY:
            //   1. the class itself is annotated @Deprecated (it IS one of
            //      the legacy classes in beast3 / its own package), or
            //   2. its primary extends target is a deprecated class.
            boolean effLegacy = c.isDeprecated
                    || (c.primaryExtendsFqn != null
                            && deprecatedFqns.contains(c.primaryExtendsFqn));
            Kind effKind = c.ownKind;
            String legacySource = null;
            if (c.isDeprecated) {
                legacySource = "@Deprecated " + c.fqn();
            } else if (effLegacy) {
                legacySource = c.primaryExtendsFqn;
            }

            ClassRecord cur = c;
            Set<String> visited = new HashSet<>();
            visited.add(c.fqn());
            int hops = 0;
            while (cur.primaryExtendsFqn != null && hops++ < 16) {
                ClassRecord parent = byFqn.get(cur.primaryExtendsFqn);
                if (parent == null) break;
                if (!visited.add(parent.fqn())) break; // cycle guard
                effSpec |= parent.ownHasSpec;
                // Inherit legacy via the chain: if the parent itself is
                // deprecated or extends a deprecated class, this class is
                // legacy too.
                if (!effLegacy) {
                    if (parent.isDeprecated) {
                        effLegacy = true;
                        legacySource = parent.fqn();
                    } else if (parent.primaryExtendsFqn != null
                            && deprecatedFqns.contains(parent.primaryExtendsFqn)) {
                        effLegacy = true;
                        legacySource = parent.primaryExtendsFqn;
                    }
                }
                if (effKind == Kind.OTHER && parent.ownKind != Kind.OTHER) {
                    effKind = parent.ownKind;
                }
                cur = parent;
            }
            c.setEffective(effKind, ClassRecord.toStatus(effSpec, effLegacy));
            if (effLegacy && legacySource != null) {
                String label = c.isDeprecated ? legacySource : "extends " + legacySource;
                c.setLegacyEvidence(java.util.List.of(label));
            }
            report.javaCounts.get(effKind).add(c.status());

            int v = c.ruleViolatingInputs().size();
            if (v > 0) {
                report.classesWithInputViolations++;
                report.inputViolations += v;
            }
        }
    }

    /** Builds the global registry of {@code @Deprecated} FQNs across reports. */
    public static Set<String> collectDeprecatedFqns(java.util.List<Report> reports) {
        Set<String> out = new HashSet<>();
        for (Report r : reports) {
            for (ClassRecord c : r.classes) {
                if (c.isDeprecated) out.add(c.fqn());
            }
        }
        return out;
    }

    /** True if the class extends or implements anything in {@code beast.base.spec.*}. */
    private static boolean computeOwnHasSpec(Set<String> tokens, Set<String> resolved) {
        for (String fqn : resolved) {
            if (fqn.startsWith("beast.base.spec.")) return true;
        }
        for (String t : tokens) {
            if (SPEC_BASE_SIMPLE_NAMES.contains(t)) return true;
        }
        return false;
    }

    private static String resolvePrimaryExtends(String extendsClause, Map<String, String> imports, String currentPkg) {
        if (extendsClause == null || extendsClause.isBlank()) return null;
        String stripped = extendsClause.replaceAll("<[^>]*>", "").trim();
        if (stripped.isEmpty()) return null;
        // Java has single inheritance for classes; abstract regex handles
        // commas defensively (interface lists in `extends` for interfaces).
        String first = stripped.split(",", 2)[0].trim();
        if (first.isEmpty()) return null;
        int dot = first.indexOf('.');
        if (dot < 0) {
            String fqn = imports.get(first);
            if (fqn != null) return fqn;
            // No import — assume the parent is in the same package.
            return currentPkg.isBlank() ? first : currentPkg + "." + first;
        }
        String head = first.substring(0, dot);
        String tail = first.substring(dot);
        String headFqn = imports.get(head);
        return headFqn != null ? headFqn + tail : first;
    }

    private static String extractPackage(String src) {
        Matcher m = PACKAGE_DECL.matcher(src);
        return m.find() ? m.group(1) : "";
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    private static java.util.LinkedHashSet<String> specEvidence(
            Set<String> resolved, Set<String> tokens) {
        java.util.LinkedHashSet<String> out = new java.util.LinkedHashSet<>();
        for (String fqn : resolved) {
            if (fqn.startsWith("beast.base.spec.")) out.add("extends/implements " + fqn);
        }
        for (String t : tokens) {
            if (SPEC_BASE_SIMPLE_NAMES.contains(t)) out.add("extends/implements " + t);
        }
        return out;
    }

    /** Maps simple class name → FQN for every {@code import} in the file. */
    private static Map<String, String> collectImports(String src) {
        Map<String, String> out = new HashMap<>();
        Matcher m = IMPORT.matcher(src);
        while (m.find()) {
            String fqn = m.group(1);
            if (fqn.endsWith(".*")) continue; // wildcard imports — can't resolve simple names
            int dot = fqn.lastIndexOf('.');
            String simple = dot >= 0 ? fqn.substring(dot + 1) : fqn;
            // Inner classes: import a.b.C$D would expose D as well as C$D.
            if (simple.contains("$")) {
                int dollar = simple.indexOf('$');
                out.put(simple.substring(dollar + 1), fqn);
            }
            out.put(simple, fqn);
        }
        return out;
    }

    /**
     * Expands every extends/implements token to its FQN using the import map.
     * For a token like {@code SubstitutionModel.Base}, resolves the head
     * {@code SubstitutionModel} via imports and reattaches {@code .Base}.
     */
    private static Set<String> resolveTokens(Set<String> tokens, Map<String, String> imports) {
        Set<String> out = new HashSet<>();
        for (String t : tokens) {
            int dot = t.indexOf('.');
            if (dot < 0) {
                out.add(imports.getOrDefault(t, t));
                continue;
            }
            // Already qualified — try first segment via imports, else assume FQN.
            String head = t.substring(0, dot);
            String tail = t.substring(dot);
            String headFqn = imports.get(head);
            if (headFqn != null) out.add(headFqn + tail);
            else out.add(t);
        }
        return out;
    }

    private static Set<String> parseTypeTokens(String extendsClause, String implementsClause) {
        Set<String> out = new HashSet<>();
        addTokens(out, extendsClause);
        addTokens(out, implementsClause);
        return out;
    }

    private static void addTokens(Set<String> out, String clause) {
        if (clause == null) return;
        // strip generic params and split on commas
        String stripped = clause.replaceAll("<[^>]*>", "").trim();
        for (String t : stripped.split(",")) {
            String s = t.trim();
            if (s.isEmpty()) continue;
            // keep both the simple name and the qualified version
            int dot = s.lastIndexOf('.');
            String simple = dot >= 0 ? s.substring(dot + 1) : s;
            out.add(simple);
            out.add(s);
        }
    }

    private static Kind classify(Set<String> simpleTokens, Set<String> resolvedFqns) {
        // 1) Try to classify by FQN (catches transitive bases like
        // SubstitutionModel.Base, BranchRateModel.Base, etc.)
        Kind k = classifyByFqn(resolvedFqns);
        if (k != null) return k;
        // 2) Fall back to simple-name matching against well-known bases.
        if (anyMatch(simpleTokens, DISTRIBUTION_BASES)) return Kind.DISTRIBUTION;
        if (anyMatch(simpleTokens, OPERATOR_BASES)) return Kind.OPERATOR;
        if (anyMatch(simpleTokens, LOGGER_INTERFACES)) return Kind.LOGGER;
        if (anyMatch(simpleTokens, PARAM_BASES)) return Kind.PARAMETER;
        if (anyMatch(simpleTokens, CALCNODE_BASES)) return Kind.CALCNODE;
        if (anyMatch(simpleTokens, STATENODE_BASES)) return Kind.STATENODE;
        return Kind.OTHER;
    }

    private static Kind classifyByFqn(Set<String> fqns) {
        // Logger first (Loggable is an interface; package-prefix would miss it).
        for (String fqn : fqns) if (LOGGER_FQNS.contains(fqn)) return Kind.LOGGER;
        // Then well-known calc-node FQNs (these classes themselves rather than
        // their packages — e.g. CalculationNode lives in beast.base.inference).
        for (String fqn : fqns) if (CALCNODE_FQNS.contains(fqn)) return Kind.CALCNODE;
        // StateNode FQNs.
        for (String fqn : fqns) if (STATENODE_FQNS.contains(fqn)) return Kind.STATENODE;
        // Then by package prefix in declared order (more specific first).
        for (String fqn : fqns) {
            for (Map.Entry<String, Kind> e : PACKAGE_KINDS.entrySet()) {
                if (fqn.startsWith(e.getKey())) return e.getValue();
            }
        }
        return null;
    }

    private static boolean anyMatch(Set<String> tokens, Set<String> bases) {
        for (String t : tokens) if (bases.contains(t)) return true;
        return false;
    }

    private static final Set<String> SPEC_BASE_SIMPLE_NAMES = Set.of(
            "RealScalarParam", "RealVectorParam",
            "IntScalarParam", "IntVectorParam",
            "BoolScalarParam", "BoolVectorParam",
            "SimplexParam",
            "RealScalar", "RealVector",
            "IntScalar", "IntVector",
            "BoolScalar", "BoolVector",
            "Tensor", "Scalar", "Vector",
            "Simplex", "IntSimplex",
            "ScalarDistribution", "TensorDistribution");

    // No simple-name fallback for legacy any more — the deprecated registry
    // covers everything by FQN. If a class extends a deprecated parent that's
    // outside any scanned package (rare; would need beast3 not in
    // packages.yaml), we'd miss it. The CHECKLIST scan always includes
    // beast3, so this is fine in practice.

    private static String appendError(String existing, String add) {
        if (existing == null || existing.isBlank()) return add;
        return existing + "; " + add;
    }
}

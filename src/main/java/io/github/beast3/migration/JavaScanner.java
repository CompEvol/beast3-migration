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

    private static final Pattern PACKAGE_DECL = Pattern.compile("(?m)^\\s*package\\s+([\\w.]+)\\s*;");
    private static final Pattern IMPORT = Pattern.compile("(?m)^\\s*import\\s+(?:static\\s+)?([\\w.$*]+)\\s*;");
    /**
     * Class declaration anywhere in the file. Allows leading whitespace so
     * indented inner classes match too. Captures simple name + extends
     * + implements clauses. Body opening brace is at the very end of the
     * match (used for scope-bound determination).
     */
    private static final Pattern CLASS_DECL = Pattern.compile(
            "(?m)^[ \\t]*(?:public\\s+|protected\\s+|private\\s+|abstract\\s+|final\\s+|sealed\\s+|non-sealed\\s+|static\\s+)*" +
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
        // strip comments so they don't pollute regex matches — string-aware
        // because regex `//[^\n]*` would chomp `(http://...)` inside a string
        String stripped = stripComments(src);

        Map<String, String> simpleToFqn = collectImports(stripped);
        String pkgName = extractPackage(stripped);

        java.util.List<ClassScope> scopes = findAllClassScopes(stripped);
        if (scopes.isEmpty()) return;
        ClassScope primary = scopes.get(0);

        // Pre-extract all Input<...> declarations with their source positions
        // so we can attribute each to the innermost class scope containing it.
        java.util.List<int[]> inputPositions = new java.util.ArrayList<>();
        java.util.List<InputDecl> inputDecls = extractInputsWithPositions(stripped, inputPositions);

        for (ClassScope s : scopes) {
            // Track only the primary class and its direct inner classes —
            // skip method-local / deeply-nested classes.
            if (s != primary && !isDirectChildOf(s, primary, scopes)) continue;

            boolean isInner = (s != primary);
            String simpleName = isInner
                    ? primary.simpleName + "." + s.simpleName
                    : s.simpleName;

            Set<String> tokens = parseTypeTokens(s.extendsClause, s.implementsClause);
            Set<String> resolved = resolveTokens(tokens, simpleToFqn);
            String primaryExtendsFqn = resolvePrimaryExtends(s.extendsClause, simpleToFqn, pkgName);
            Kind ownKind = classify(tokens, resolved);
            boolean ownHasSpec = computeOwnHasSpec(tokens, resolved);
            boolean isDeprecated = hasDeprecatedAnnotation(stripped, s.declStart);
            // The javadoc is only present in the original (un-stripped) source.
            String deprecationMessage = isDeprecated
                    ? extractDeprecationMessage(src, simpleName)
                    : "";

            java.util.List<InputDecl> myInputs = new java.util.ArrayList<>();
            for (int i = 0; i < inputDecls.size(); i++) {
                int pos = inputPositions.get(i)[0];
                if (innermostScopeContaining(scopes, pos) == s) {
                    myInputs.add(inputDecls.get(i));
                }
            }

            // Counts and legacy-status are deferred until resolveAndTally()
            // — both depend on the global @Deprecated registry collected
            // across all scanned packages.
            report.classes.add(new ClassRecord(
                    file,
                    pkgName,
                    simpleName,
                    trim(s.extendsClause),
                    trim(s.implementsClause),
                    java.util.List.of(),                  // populated by resolver
                    java.util.List.copyOf(specEvidence(resolved, tokens)),
                    ownKind,
                    ownHasSpec,
                    false,                                // ownHasLegacy filled by resolver
                    primaryExtendsFqn,
                    isDeprecated,
                    deprecationMessage,
                    java.util.List.copyOf(myInputs)));
        }
    }

    /** A scope owned by a single class declaration in the source. */
    private record ClassScope(
            String simpleName,
            int declStart,
            int bodyStart,
            int bodyEnd,
            String extendsClause,
            String implementsClause) {}

    private static java.util.List<ClassScope> findAllClassScopes(String src) {
        java.util.List<ClassScope> out = new java.util.ArrayList<>();
        Matcher m = CLASS_DECL.matcher(src);
        while (m.find()) {
            int bodyStart = m.end();
            int bodyEnd = matchClosingBrace(src, bodyStart - 1);
            if (bodyEnd < 0) continue;
            out.add(new ClassScope(
                    m.group(1), m.start(), bodyStart, bodyEnd,
                    m.group(2), m.group(3)));
        }
        return out;
    }

    /**
     * Returns the position right after the matching {@code }} of the
     * {@code {} at {@code openBracePos}, or -1 if unbalanced. Skips string
     * and char literals (comments are already stripped).
     */
    private static int matchClosingBrace(String src, int openBracePos) {
        int depth = 1;
        int i = openBracePos + 1;
        int n = src.length();
        while (i < n && depth > 0) {
            char c = src.charAt(i);
            if (c == '"' || c == '\'') {
                char quote = c;
                i++;
                while (i < n && src.charAt(i) != quote) {
                    if (src.charAt(i) == '\\' && i + 1 < n) i++;
                    i++;
                }
                if (i < n) i++;
                continue;
            }
            if (c == '{') depth++;
            else if (c == '}') depth--;
            i++;
        }
        return depth == 0 ? i : -1;
    }

    private static boolean isDirectChildOf(
            ClassScope inner, ClassScope outer, java.util.List<ClassScope> all) {
        if (inner == outer) return false;
        if (inner.bodyStart < outer.bodyStart || inner.bodyEnd > outer.bodyEnd) return false;
        for (ClassScope t : all) {
            if (t == inner || t == outer) continue;
            if (t.bodyStart > outer.bodyStart && t.bodyEnd < outer.bodyEnd
                    && inner.bodyStart > t.bodyStart && inner.bodyEnd <= t.bodyEnd) {
                return false;
            }
        }
        return true;
    }

    private static ClassScope innermostScopeContaining(java.util.List<ClassScope> scopes, int pos) {
        ClassScope best = null;
        int bestSpan = Integer.MAX_VALUE;
        for (ClassScope s : scopes) {
            if (pos >= s.bodyStart && pos < s.bodyEnd) {
                int span = s.bodyEnd - s.bodyStart;
                if (span < bestSpan) {
                    best = s;
                    bestSpan = span;
                }
            }
        }
        return best;
    }

    /**
     * Returns true if a {@code @Deprecated} annotation appears immediately
     * before the class declaration at {@code classDeclStart}. Scans forward
     * from the previous statement boundary ({@code ;} / {@code }} or
     * start-of-file), reading annotations and skipping their argument lists
     * with proper string-literal handling — so
     * {@code @Description("foo(bar)baz")} doesn't fool the paren balancer.
     */
    /**
     * Extracts the {@code @deprecated <text>} body from the javadoc that
     * precedes the {@code class <simpleName>} declaration in {@code src},
     * if any. Returns the raw text between {@code @deprecated} and the next
     * javadoc tag (another {@code @}) or the end of the javadoc block;
     * leading {@code *} characters and whitespace from each line are
     * stripped. Returns empty string if no such javadoc is present.
     *
     * <p>This works against the <b>original</b> source, not the
     * comment-stripped form used for offset-based scope analysis, because
     * the javadoc text only exists in the original.</p>
     */
    private static String extractDeprecationMessage(String src, String simpleName) {
        // Find the primary "class <simpleName>" declaration; tolerate "final",
        // "public", "abstract" modifiers before the keyword.
        java.util.regex.Matcher m = java.util.regex.Pattern
                .compile("\\bclass\\s+" + java.util.regex.Pattern.quote(simpleName) + "\\b")
                .matcher(src);
        if (!m.find()) return "";
        int classDeclStart = m.start();
        // Find a javadoc block (closes with "*/") that ends at or before
        // classDeclStart with only whitespace/annotations between.
        int end = src.lastIndexOf("*/", classDeclStart);
        if (end < 0) return "";
        // Only accept if nothing but whitespace, annotations, or Java
        // modifier keywords (public/final/abstract/static/sealed/...) sit
        // between the comment close and the class declaration. Anything else
        // means another statement intervenes and the javadoc isn't ours.
        java.util.Set<String> modifiers = java.util.Set.of(
                "public", "protected", "private", "abstract", "final",
                "static", "sealed", "non-sealed", "strictfp");
        int j = end + 2;
        while (j < classDeclStart) {
            char c = src.charAt(j);
            if (Character.isWhitespace(c)) { j++; continue; }
            if (c == '@') {
                int idStart = j + 1;
                int k = idStart;
                while (k < classDeclStart && (Character.isJavaIdentifierPart(src.charAt(k))
                        || src.charAt(k) == '.')) k++;
                j = k;
                while (j < classDeclStart && Character.isWhitespace(src.charAt(j))) j++;
                if (j < classDeclStart && src.charAt(j) == '(') {
                    int after = matchClosingParen(src, j);
                    if (after < 0) return "";
                    j = after;
                }
                continue;
            }
            if (Character.isJavaIdentifierStart(c)) {
                int idStart = j;
                while (j < classDeclStart && Character.isJavaIdentifierPart(src.charAt(j))) j++;
                String tok = src.substring(idStart, j);
                if (modifiers.contains(tok)) continue;
                // Some other identifier — likely the next statement, bail.
                return "";
            }
            // Stray character — bail.
            return "";
        }
        int start = src.lastIndexOf("/**", end);
        if (start < 0) return "";
        String block = src.substring(start + 3, end);
        int tag = block.indexOf("@deprecated");
        if (tag < 0) return "";
        int msgStart = tag + "@deprecated".length();
        // Stop at the next javadoc tag boundary: @ at start of line (after
        // optional whitespace and leading *) signals a new section.
        int msgEnd = block.length();
        for (int k = msgStart; k < block.length(); k++) {
            if (block.charAt(k) != '\n') continue;
            int p = k + 1;
            while (p < block.length() && (block.charAt(p) == ' ' || block.charAt(p) == '\t')) p++;
            if (p < block.length() && block.charAt(p) == '*') {
                p++;
                while (p < block.length() && (block.charAt(p) == ' ' || block.charAt(p) == '\t')) p++;
            }
            if (p < block.length() && block.charAt(p) == '@') {
                msgEnd = k;
                break;
            }
        }
        String raw = block.substring(msgStart, msgEnd);
        // Normalise: strip leading "*" per line, collapse whitespace.
        StringBuilder out = new StringBuilder();
        for (String line : raw.split("\n")) {
            String t = line.trim();
            if (t.startsWith("*")) t = t.substring(1).trim();
            if (out.length() > 0) out.append(' ');
            out.append(t);
        }
        return out.toString().trim();
    }

    private static boolean hasDeprecatedAnnotation(String src, int classDeclStart) {
        int i = previousStatementBoundary(src, classDeclStart);
        while (i < classDeclStart) {
            while (i < classDeclStart && Character.isWhitespace(src.charAt(i))) i++;
            if (i >= classDeclStart) return false;
            if (src.charAt(i) != '@') return false;   // hit a modifier — stop
            i++;
            int nameStart = i;
            while (i < classDeclStart && (Character.isJavaIdentifierPart(src.charAt(i))
                    || src.charAt(i) == '.')) i++;
            String name = src.substring(nameStart, i);
            if (name.equals("Deprecated") || name.endsWith(".Deprecated")) return true;
            // Skip optional annotation args (...).
            while (i < classDeclStart && Character.isWhitespace(src.charAt(i))) i++;
            if (i < classDeclStart && src.charAt(i) == '(') {
                int after = matchClosingParen(src, i);
                if (after < 0) return false;
                i = after;
            }
        }
        return false;
    }

    /**
     * Position of the last {@code ;} or {@code }} before {@code pos} that's
     * not inside a string or char literal, or 0. Scans forward to handle
     * strings correctly — a backward scan would mistake a semicolon inside
     * {@code @Description("p(x;y)")} for a real statement boundary.
     */
    private static int previousStatementBoundary(String src, int pos) {
        int boundary = 0;
        int i = 0;
        int n = Math.min(pos, src.length());
        while (i < n) {
            char c = src.charAt(i);
            if (c == '"' || c == '\'') {
                char q = c;
                i++;
                while (i < n && src.charAt(i) != q) {
                    if (src.charAt(i) == '\\' && i + 1 < n) i++;
                    i++;
                }
                i++;
                continue;
            }
            if (c == ';' || c == '}') boundary = i + 1;
            i++;
        }
        return boundary;
    }

    /** Returns position after matching {@code )} of {@code (} at {@code openPos}, or -1 if unbalanced. */
    private static int matchClosingParen(String src, int openPos) {
        int depth = 1;
        int i = openPos + 1;
        int n = src.length();
        while (i < n && depth > 0) {
            char c = src.charAt(i);
            if (c == '"' || c == '\'') {
                char quote = c;
                i++;
                while (i < n && src.charAt(i) != quote) {
                    if (src.charAt(i) == '\\' && i + 1 < n) i++;
                    i++;
                }
                if (i < n) i++;
                continue;
            }
            if (c == '(') depth++;
            else if (c == ')') depth--;
            i++;
        }
        return depth == 0 ? i : -1;
    }

    /**
     * Extracts every {@code Input<X>} field type argument from the source.
     * If {@code positions} is non-null, parallel ints describing the source
     * position where each {@code Input<} starts are appended to it (so the
     * caller can attribute each input to its containing class scope).
     * Walks balanced angle brackets so nested generics like
     * {@code Input<RealScalar<? extends PositiveReal>>} work.
     */
    static java.util.List<InputDecl> extractInputsWithPositions(String src, java.util.List<int[]> positions) {
        java.util.List<InputDecl> out = new java.util.ArrayList<>();
        int i = 0;
        while ((i = src.indexOf("Input<", i)) != -1) {
            // Avoid matching identifiers that end in `Input` (e.g. `MyInput<`).
            if (i > 0 && (Character.isJavaIdentifierPart(src.charAt(i - 1))
                    || src.charAt(i - 1) == '.')) {
                i += 6;
                continue;
            }
            int matchPos = i;
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
            int k = j;
            while (k < src.length() && Character.isWhitespace(src.charAt(k))) k++;
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
            if (nameEnd > nameStart) {
                out.add(new InputDecl(inner, classifyInputCarrier(inner)));
                if (positions != null) positions.add(new int[] { matchPos });
            }
            i = j;
        }
        return out;
    }

    /** Position-less convenience kept for tests / external callers. */
    static java.util.List<InputDecl> extractInputs(String src) {
        return extractInputsWithPositions(src, null);
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
            // @Deprecated classes are known about (and slated for removal),
            // so they're never reported as legacy regardless of what they
            // extend — the migration concern is classes that aren't yet
            // marked but DO extend a deprecated class.
            boolean effSpec = c.ownHasSpec;
            boolean effLegacy = !c.isDeprecated
                    && c.primaryExtendsFqn != null
                    && deprecatedFqns.contains(c.primaryExtendsFqn);
            Kind effKind = c.ownKind;
            String legacySource = effLegacy ? c.primaryExtendsFqn : null;

            ClassRecord cur = c;
            Set<String> visited = new HashSet<>();
            visited.add(c.fqn());
            int hops = 0;
            while (cur.primaryExtendsFqn != null && hops++ < 16) {
                ClassRecord parent = byFqn.get(cur.primaryExtendsFqn);
                if (parent == null) break;
                if (!visited.add(parent.fqn())) break; // cycle guard
                effSpec |= parent.ownHasSpec;
                // Inherit legacy via the chain: if an in-package ancestor
                // extends a deprecated class, this class is legacy too —
                // unless this class itself is @Deprecated (which means it's
                // already on the core team's radar).
                if (!effLegacy && !c.isDeprecated
                        && parent.primaryExtendsFqn != null
                        && deprecatedFqns.contains(parent.primaryExtendsFqn)) {
                    effLegacy = true;
                    legacySource = parent.primaryExtendsFqn;
                }
                if (effKind == Kind.OTHER && parent.ownKind != Kind.OTHER) {
                    effKind = parent.ownKind;
                }
                cur = parent;
            }
            c.setEffective(effKind, ClassRecord.toStatus(effSpec, effLegacy));
            if (effLegacy && legacySource != null) {
                c.setLegacyEvidence(java.util.List.of("extends " + legacySource));
            }
            report.javaCounts.get(effKind).add(c.status());

            // @Deprecated classes are known about — don't tally their Input
            // violations either; they'll be removed wholesale.
            if (!c.isDeprecated) {
                int v = c.ruleViolatingInputs().size();
                if (v > 0) {
                    report.classesWithInputViolations++;
                    report.inputViolations += v;
                }
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

    /** Every class FQN seen across the scanned packages, deprecated or not. */
    public static Set<String> collectAllClassFqns(java.util.List<Report> reports) {
        Set<String> out = new HashSet<>();
        for (Report r : reports) {
            for (ClassRecord c : r.classes) {
                out.add(c.fqn());
            }
        }
        return out;
    }

    /**
     * For each deprecated FQN, find a spec replacement. First tries the
     * same-simple-name heuristic (a class with the same simple name under a
     * non-deprecated package containing {@code .spec.}). When that fails,
     * falls back to parsing the {@code @deprecated <text>} javadoc on the
     * deprecated class for any FQN or capitalised simple name that resolves
     * to a class in the registry — this catches cases like
     * {@code BactrianUpDownOperator -> UpDownOperator} where the spec class
     * has a different simple name. Returns only the entries where a
     * replacement was found.
     */
    public static java.util.Map<String, String> deriveSpecReplacements(
            java.util.List<Report> reports,
            Set<String> deprecatedFqns, Set<String> allFqns) {
        // Index allFqns by simple name → list of FQNs.
        java.util.Map<String, java.util.List<String>> bySimpleName = new java.util.HashMap<>();
        for (String fqn : allFqns) {
            int dot = fqn.lastIndexOf('.');
            String simple = dot < 0 ? fqn : fqn.substring(dot + 1);
            bySimpleName.computeIfAbsent(simple, k -> new java.util.ArrayList<>()).add(fqn);
        }
        // Index deprecation messages by FQN so the javadoc fallback can find them.
        java.util.Map<String, String> deprecationMessages = new java.util.HashMap<>();
        for (Report r : reports) {
            for (ClassRecord c : r.classes) {
                if (c.isDeprecated && !c.deprecationMessage.isBlank()) {
                    deprecationMessages.put(c.fqn(), c.deprecationMessage);
                }
            }
        }
        java.util.Map<String, String> out = new java.util.LinkedHashMap<>();
        for (String dep : deprecatedFqns) {
            int dot = dep.lastIndexOf('.');
            String simple = dot < 0 ? dep : dep.substring(dot + 1);
            // Same-simple-name pass.
            java.util.List<String> candidates = bySimpleName.getOrDefault(simple, java.util.List.of());
            String pick = null;
            for (String cand : candidates) {
                if (cand.equals(dep)) continue;
                if (!cand.contains(".spec.")) continue;
                if (deprecatedFqns.contains(cand)) continue;
                if (pick == null || commonPrefixLen(cand, dep) > commonPrefixLen(pick, dep)) {
                    pick = cand;
                }
            }
            if (pick != null) {
                out.put(dep, pick);
                continue;
            }
            // Javadoc fallback: parse "Use beast.X.Y instead" / "{@link X}" / etc.
            String msg = deprecationMessages.get(dep);
            if (msg == null || msg.isBlank()) continue;
            String hinted = findReplacementInDeprecationMessage(
                    msg, deprecatedFqns, allFqns, bySimpleName);
            if (hinted != null) out.put(dep, hinted);
        }
        return out;
    }

    /**
     * Scan a {@code @deprecated} javadoc body for class-reference tokens and
     * return the first one that resolves to a non-deprecated class in
     * {@code allFqns}. Considers (in order): full FQNs, then capitalised
     * simple names. Preference goes to candidates in {@code .spec.} packages.
     */
    private static String findReplacementInDeprecationMessage(
            String msg,
            Set<String> deprecatedFqns,
            Set<String> allFqns,
            java.util.Map<String, java.util.List<String>> bySimpleName) {
        // Strip Javadoc {@link ...} braces but keep their content.
        String cleaned = msg.replaceAll("\\{@(?:link|linkplain|code)\\s+", " ")
                            .replaceAll("[{}]", " ");
        // Pass 1 — full FQNs (contain at least one dot, start with lowercase package).
        java.util.regex.Matcher fqnMatcher = java.util.regex.Pattern
                .compile("\\b([a-z][a-zA-Z0-9_]*\\.[\\w.]*[A-Z][\\w]*)")
                .matcher(cleaned);
        while (fqnMatcher.find()) {
            String fqn = fqnMatcher.group(1);
            if (allFqns.contains(fqn) && !deprecatedFqns.contains(fqn)) return fqn;
        }
        // Pass 2 — bare capitalised simple names. Pick the first that has at
        // least one non-deprecated FQN in the registry, preferring spec.
        java.util.regex.Matcher simpleMatcher = java.util.regex.Pattern
                .compile("\\b([A-Z][A-Za-z0-9_]+)\\b").matcher(cleaned);
        while (simpleMatcher.find()) {
            String simple = simpleMatcher.group(1);
            java.util.List<String> candidates = bySimpleName.get(simple);
            if (candidates == null) continue;
            String pick = null;
            for (String c : candidates) {
                if (deprecatedFqns.contains(c)) continue;
                if (c.contains(".spec.")) { pick = c; break; }
                if (pick == null) pick = c;
            }
            if (pick != null) return pick;
        }
        return null;
    }

    /**
     * Short names whose every known FQN is deprecated — safe to flag a bare
     * {@code spec="OneOnX"} reference because there's no non-deprecated class
     * with that short name to confuse it with. Maps short name → one of the
     * deprecated FQNs (used as the canonical "hit" in reports).
     */
    public static java.util.Map<String, String> deriveUnambiguouslyDeprecatedShortNames(
            Set<String> deprecatedFqns, Set<String> allFqns) {
        java.util.Map<String, java.util.List<String>> bySimpleName = new java.util.HashMap<>();
        for (String fqn : allFqns) {
            int dot = fqn.lastIndexOf('.');
            String simple = dot < 0 ? fqn : fqn.substring(dot + 1);
            bySimpleName.computeIfAbsent(simple, k -> new java.util.ArrayList<>()).add(fqn);
        }
        java.util.Map<String, String> out = new java.util.LinkedHashMap<>();
        for (var e : bySimpleName.entrySet()) {
            java.util.List<String> fqns = e.getValue();
            if (fqns.isEmpty()) continue;
            boolean allDeprecated = fqns.stream().allMatch(deprecatedFqns::contains);
            if (!allDeprecated) continue;
            // Pick the shortest FQN as the canonical representative; deterministic.
            String canonical = fqns.stream().min(java.util.Comparator.comparingInt(String::length)).get();
            out.put(e.getKey(), canonical);
        }
        return out;
    }

    private static int commonPrefixLen(String a, String b) {
        int n = Math.min(a.length(), b.length());
        int i = 0;
        while (i < n && a.charAt(i) == b.charAt(i)) i++;
        return i;
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

    /**
     * Removes Java {@code //} and {@code /* … *}{@code /} comments from a
     * source string while leaving string and char literals intact. The naive
     * regex approach mistakes {@code //} inside {@code "(http://...)"} for a
     * line comment.
     */
    static String stripComments(String src) {
        StringBuilder out = new StringBuilder(src.length());
        int i = 0;
        int n = src.length();
        while (i < n) {
            char c = src.charAt(i);
            if (c == '"' || c == '\'') {
                char q = c;
                out.append(c);
                i++;
                while (i < n && src.charAt(i) != q) {
                    if (src.charAt(i) == '\\' && i + 1 < n) {
                        out.append(src.charAt(i));
                        out.append(src.charAt(i + 1));
                        i += 2;
                        continue;
                    }
                    out.append(src.charAt(i));
                    i++;
                }
                if (i < n) {
                    out.append(src.charAt(i));
                    i++;
                }
                continue;
            }
            if (c == '/' && i + 1 < n) {
                char next = src.charAt(i + 1);
                if (next == '/') {
                    while (i < n && src.charAt(i) != '\n') i++;
                    continue;
                }
                if (next == '*') {
                    i += 2;
                    while (i + 1 < n && !(src.charAt(i) == '*' && src.charAt(i + 1) == '/')) i++;
                    if (i + 1 < n) i += 2;
                    continue;
                }
            }
            out.append(c);
            i++;
        }
        return out.toString();
    }

    private static String appendError(String existing, String add) {
        if (existing == null || existing.isBlank()) return add;
        return existing + "; " + add;
    }
}

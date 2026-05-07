package io.github.beast3.migration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import io.github.beast3.migration.Report.Kind;
import io.github.beast3.migration.Report.Status;

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

    /** Legacy fully-qualified imports we treat as "uses legacy types". */
    private static final Set<String> LEGACY_FQNS = Set.of(
            "beast.base.inference.parameter.RealParameter",
            "beast.base.inference.parameter.IntegerParameter",
            "beast.base.inference.parameter.BooleanParameter",
            "beast.base.inference.parameter.RealParameterList",
            "beast.base.inference.parameter.IntegerParameterList",
            "beast.base.inference.parameter.BooleanParameterList",
            "beast.base.inference.distribution.Prior",
            "beast.base.inference.distribution.ParametricDistribution",
            "beast.base.core.Function$Constant");

    private static final Pattern BLOCK_COMMENT = Pattern.compile("/\\*[\\s\\S]*?\\*/");
    private static final Pattern LINE_COMMENT = Pattern.compile("(?m)//[^\\n]*");
    private static final Pattern PACKAGE = Pattern.compile("(?m)^\\s*package\\s+([\\w.]+)\\s*;");
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

        Set<String> imports = collectImports(stripped);

        Matcher cm = CLASS_DECL.matcher(stripped);
        // Only the first top-level class drives classification; nested classes
        // are skipped because the regex matches `^` at line start in
        // multiline mode and nested classes are typically indented.
        while (cm.find()) {
            String className = cm.group(1);
            String extendsClause = cm.group(2);
            String implementsClause = cm.group(3);

            // Skip non top-level: indentation indicates nesting in compiled
            // sources. We additionally require start-of-line by virtue of ^.
            // Heuristic: ignore if preceded directly by tab/4 spaces — already
            // handled by the regex, but we also bail after the first match.
            Set<String> tokens = parseTypeTokens(extendsClause, implementsClause);

            Kind kind = classify(tokens);
            Status status = migrationStatus(tokens, imports);
            report.javaCounts.get(kind).add(status);
            // Only count the primary (first) declaration per file.
            return;
        }
    }

    private static Set<String> collectImports(String src) {
        Set<String> out = new HashSet<>();
        Matcher m = IMPORT.matcher(src);
        while (m.find()) out.add(m.group(1));
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

    private static Kind classify(Set<String> tokens) {
        if (anyMatch(tokens, DISTRIBUTION_BASES)) return Kind.DISTRIBUTION;
        if (anyMatch(tokens, OPERATOR_BASES)) return Kind.OPERATOR;
        if (anyMatch(tokens, LOGGER_INTERFACES)) return Kind.LOGGER;
        if (anyMatch(tokens, PARAM_BASES)) return Kind.PARAMETER;
        if (anyMatch(tokens, CALCNODE_BASES)) return Kind.CALCNODE;
        if (anyMatch(tokens, STATENODE_BASES)) return Kind.STATENODE;
        // Operator/Distribution names that end in "Operator" / "Distribution"
        // are caught above by name; everything else is "other".
        return Kind.OTHER;
    }

    private static boolean anyMatch(Set<String> tokens, Set<String> bases) {
        for (String t : tokens) if (bases.contains(t)) return true;
        return false;
    }

    private static Status migrationStatus(Set<String> typeTokens, Set<String> imports) {
        boolean hasSpec = false;
        boolean hasLegacy = false;

        for (String imp : imports) {
            if (imp.startsWith("beast.base.spec.")) hasSpec = true;
            if (LEGACY_FQNS.contains(imp)) hasLegacy = true;
            if (imp.startsWith("beast.base.inference.parameter.")) hasLegacy = true;
            if (imp.equals("beast.base.inference.distribution.Prior")) hasLegacy = true;
        }
        // also: if extends/implements contains a known spec base name (via FQN), count as spec
        for (String t : typeTokens) {
            if (t.startsWith("beast.base.spec.")) hasSpec = true;
            if (t.equals("RealScalarParam") || t.equals("RealVectorParam")
                    || t.equals("IntScalarParam") || t.equals("IntVectorParam")
                    || t.equals("BoolScalarParam") || t.equals("BoolVectorParam")
                    || t.equals("SimplexParam")
                    || t.equals("RealScalar") || t.equals("RealVector")
                    || t.equals("IntScalar") || t.equals("IntVector")
                    || t.equals("BoolScalar") || t.equals("BoolVector")
                    || t.equals("Tensor") || t.equals("ScalarDistribution")
                    || t.equals("TensorDistribution")) {
                hasSpec = true;
            }
            if (t.equals("RealParameter") || t.equals("IntegerParameter")
                    || t.equals("BooleanParameter")) {
                hasLegacy = true;
            }
        }

        if (hasSpec && hasLegacy) return Status.MIXED;
        if (hasSpec) return Status.SPEC;
        if (hasLegacy) return Status.LEGACY;
        return Status.NEUTRAL;
    }

    private static String appendError(String existing, String add) {
        if (existing == null || existing.isBlank()) return add;
        return existing + "; " + add;
    }
}

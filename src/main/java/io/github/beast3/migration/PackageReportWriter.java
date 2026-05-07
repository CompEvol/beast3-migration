package io.github.beast3.migration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import io.github.beast3.migration.Report.Kind;
import io.github.beast3.migration.Report.Status;

/**
 * Renders a per-package "what's left to do" punch list. Sections appear only
 * if they have something to say, so a fully-migrated package gets a short
 * report.
 */
public final class PackageReportWriter {

    private PackageReportWriter() {}

    public static String render(Report r) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ").append(r.entry.name()).append(" — what's left\n\n");

        renderHeader(sb, r);

        if (!r.pathExists) {
            sb.append("> ⚠️ Local checkout not found. Clone the package and re-run the analyzer.\n\n");
            return sb.toString();
        }
        if (!r.error.isBlank()) {
            sb.append("> ⚠️ Scan reported errors: ").append(r.error).append("\n\n");
        }

        renderSummary(sb, r);
        renderBuildGaps(sb, r);
        renderMavenCentral(sb, r);
        renderJavaPunchList(sb, r);
        renderInputRulePunchList(sb, r);
        renderXmlPunchList(sb, r);

        if (sb.charAt(sb.length() - 1) != '\n') sb.append('\n');
        return sb.toString();
    }

    private static void renderHeader(StringBuilder sb, Report r) {
        // The "version" of the report is whatever combination of fields
        // unambiguously identifies what was scanned: git commit, pom version,
        // and (when available) the published Maven Central version. The
        // local filesystem path is intentionally omitted — the GitHub commit
        // link below identifies the scan target without leaking
        // user-specific paths into the public repo.
        sb.append("> **Scanned at:** ")
                .append(r.scannedAt.toLocalDateTime().toString()).append("  \n");
        if (!r.git.shortSha().isBlank()) {
            sb.append("> **Commit:** `").append(r.git.shortSha()).append('`');
            if (!r.git.branch().isBlank() && !r.git.branch().equals("HEAD")) {
                sb.append(" on `").append(r.git.branch()).append('`');
            }
            if (r.git.dirty()) sb.append(" *(dirty working tree)*");
            if (!r.entry.github().isBlank() && !r.git.fullSha().isBlank()) {
                sb.append(" — [view on GitHub](https://github.com/")
                        .append(r.entry.github()).append("/commit/")
                        .append(r.git.fullSha()).append(')');
            }
            sb.append("  \n");
        }
        if (!r.pomVersion.isBlank()) {
            sb.append("> **Pom version:** `").append(r.pomVersion).append("`  \n");
        }
        if (r.entry.hasMavenCoords()) {
            sb.append("> **Maven Central:** ");
            if (!r.mavenCentralLatest.isBlank()) {
                sb.append('`').append(r.entry.mavenGroupId()).append(':')
                        .append(r.entry.mavenArtifactId()).append(':')
                        .append(r.mavenCentralLatest).append("`  \n");
            } else {
                sb.append("not published as `").append(r.entry.mavenGroupId()).append(':')
                        .append(r.entry.mavenArtifactId()).append('`');
                if (!r.mavenCentralError.isBlank()) {
                    sb.append(" (").append(r.mavenCentralError).append(')');
                }
                sb.append("  \n");
            }
        }
        sb.append("> **Stage hint:** ").append(prettyStage(r.entry.stage())).append('\n');
        if (!r.entry.notes().isBlank()) {
            sb.append(">\n> ").append(r.entry.notes()).append('\n');
        }
        sb.append('\n');
    }

    private static String prettyStage(String s) {
        if (s == null || s.isBlank()) return "—";
        return switch (s) {
            case "maven_central" -> "Maven Central";
            case "compile_tested" -> "compile-tested";
            case "in_progress" -> "in progress";
            case "not_started" -> "not started";
            default -> s;
        };
    }

    private static void renderSummary(StringBuilder sb, Report r) {
        int totalClasses = 0, migratedClasses = 0, mixedClasses = 0, legacyClasses = 0;
        for (Kind k : Kind.values()) {
            Report.KindCounts c = r.javaCounts.get(k);
            totalClasses += c.total;
            migratedClasses += c.spec;
            mixedClasses += c.mixed;
            legacyClasses += c.legacy;
        }
        sb.append("## Summary\n\n");
        sb.append("- **Java classes:** ").append(migratedClasses).append(" on spec, ")
                .append(mixedClasses).append(" mixed, ")
                .append(legacyClasses).append(" legacy of ")
                .append(totalClasses).append(" total\n");
        sb.append("- **Example XMLs:** ").append(r.xmlMigrated).append(" on spec / ")
                .append(r.xmlV28).append(" on `version=\"2.8\"` / ")
                .append(r.xmlTotal).append(" total");
        if (r.xmlLegacyDir > 0) sb.append(" (+").append(r.xmlLegacyDir).append(" under legacy/)");
        sb.append('\n');
        if (r.fxTotal > 0) {
            sb.append("- **BEAUti fxtemplates:** ").append(r.fxClean).append(" clean / ")
                    .append(r.fxWithSpec).append(" use spec / ")
                    .append(r.fxTotal).append(" total\n");
        }
        if (r.inputViolations > 0) {
            sb.append("- **Input rule:** ").append(r.classesWithInputViolations)
                    .append(" classes hold ").append(r.inputViolations)
                    .append(" Input(s) declared too concretely\n");
        } else if (totalClasses > 0) {
            sb.append("- **Input rule:** all Inputs use the right carrier ✅\n");
        }
        if (r.entry.hasMavenCoords() && !r.mavenCentralLatest.isBlank()) {
            sb.append("- **Maven Central:** ").append(r.mavenCentralLatest).append('\n');
        } else if (r.entry.hasMavenCoords()) {
            sb.append("- **Maven Central:** ❌ not published");
            if (!r.mavenCentralError.isBlank()) sb.append(" (").append(r.mavenCentralError).append(")");
            sb.append('\n');
        }
        sb.append('\n');
    }

    private static void renderBuildGaps(StringBuilder sb, Report r) {
        List<String> gaps = new ArrayList<>();
        if (!r.hasPom) gaps.add("**No `pom.xml`** — the package isn't Maven-buildable. The migration guide assumes Maven; consider adding a pom (start from `beast-package-skeleton`).");
        if (r.hasPom && !r.hasReleaseProfile) gaps.add("**No `release` profile in `pom.xml`** — required for publishing to Maven Central. Copy from `beast-package-skeleton`.");
        if (!r.hasModuleInfo) gaps.add("**No `module-info.java`** — JPMS module descriptor is the primary service-discovery mechanism in BEAST 3. Add an `open module` declaration with `provides beast.base.core.BEASTInterface with …`.");
        if (!r.hasGithubActions) gaps.add("**No `.github/workflows/*.yml`** — add CI to catch regressions on PRs.");
        if (!r.hasReleaseScript) gaps.add("**No `release.sh` or `release/` directory** — used to assemble the CBAN-style ZIP.");
        if (!r.hasVersionXml) gaps.add("**No `version.xml`** — required for Package Manager service discovery on deployed packages.");
        if (gaps.isEmpty()) return;
        sb.append("## Build & release gaps\n\n");
        for (String g : gaps) sb.append("- ").append(g).append('\n');
        sb.append('\n');
    }

    private static void renderMavenCentral(StringBuilder sb, Report r) {
        if (!r.entry.hasMavenCoords()) {
            sb.append("## Maven Central\n\n");
            sb.append("No `groupId:artifactId` recorded in `packages.yaml`. Add one to track publication status.\n\n");
            return;
        }
        if (!r.mavenCentralLatest.isBlank()) return; // nothing to do
        sb.append("## Maven Central\n\n");
        sb.append("Not yet published as `").append(r.entry.mavenGroupId()).append(':')
                .append(r.entry.mavenArtifactId()).append("`. Verify the namespace on ")
                .append("central.sonatype.com and run the `release` profile to deploy.\n\n");
    }

    private static void renderJavaPunchList(StringBuilder sb, Report r) {
        // Group classes by Kind, then split legacy / mixed / neutral.
        Map<Kind, List<ClassRecord>> legacyByKind = new EnumMap<>(Kind.class);
        Map<Kind, List<ClassRecord>> mixedByKind = new EnumMap<>(Kind.class);
        for (Kind k : Kind.values()) {
            legacyByKind.put(k, new ArrayList<>());
            mixedByKind.put(k, new ArrayList<>());
        }
        for (ClassRecord c : r.classes) {
            if (c.status() == Status.LEGACY) legacyByKind.get(c.kind()).add(c);
            else if (c.status() == Status.MIXED) mixedByKind.get(c.kind()).add(c);
        }

        boolean any = legacyByKind.values().stream().anyMatch(l -> !l.isEmpty())
                || mixedByKind.values().stream().anyMatch(l -> !l.isEmpty());
        if (!any) {
            sb.append("## Java migration\n\nNo Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅\n\n");
            return;
        }

        sb.append("## Java classes pending migration\n\n");
        for (Kind k : Kind.values()) {
            List<ClassRecord> legacy = legacyByKind.get(k);
            List<ClassRecord> mixed = mixedByKind.get(k);
            if (legacy.isEmpty() && mixed.isEmpty()) continue;

            sb.append("### ").append(k.label).append(" — ")
                    .append(legacy.size()).append(" legacy, ")
                    .append(mixed.size()).append(" mixed");
            int total = r.javaCounts.get(k).total;
            sb.append(" (of ").append(total).append(" total)\n\n");

            if (!mixed.isEmpty()) {
                sb.append("**Mixed** (extends a legacy base AND a spec interface — finish swapping the base):\n\n");
                mixed.sort(Comparator.comparing(ClassRecord::fqn));
                for (ClassRecord c : mixed) renderClassLine(sb, c);
                sb.append('\n');
            }
            if (!legacy.isEmpty()) {
                sb.append("**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):\n\n");
                legacy.sort(Comparator.comparing(ClassRecord::fqn));
                for (ClassRecord c : legacy) renderClassLine(sb, c);
                sb.append('\n');
            }
        }
    }

    private static void renderClassLine(StringBuilder sb, ClassRecord c) {
        sb.append("- `").append(c.fqn()).append('`');
        String evidence = formatEvidence(c.legacyEvidence());
        if (!evidence.isBlank()) {
            sb.append(" — extends ").append(evidence);
        } else if (c.primaryExtendsFqn != null && !c.primaryExtendsFqn.isBlank()) {
            // Class inherited LEGACY via the chain resolver; cite the in-package parent.
            int dot = c.primaryExtendsFqn.lastIndexOf('.');
            String parent = dot >= 0 ? c.primaryExtendsFqn.substring(dot + 1) : c.primaryExtendsFqn;
            sb.append(" — via `").append(parent).append('`');
        }
        sb.append('\n');
    }

    private static String formatEvidence(List<String> items) {
        if (items.isEmpty()) return "";
        // shorten FQNs to the simple-name + parent-package suffix for readability
        List<String> shortened = new ArrayList<>();
        for (String item : items) {
            String s = item.startsWith("extends/implements ") ? item.substring("extends/implements ".length()) : item;
            int dot = s.lastIndexOf('.');
            String simple = dot >= 0 ? s.substring(dot + 1) : s;
            shortened.add(simple);
        }
        // dedupe preserving order
        java.util.LinkedHashSet<String> seen = new java.util.LinkedHashSet<>(shortened);
        // Cap at 4 to keep lines short.
        List<String> out = new ArrayList<>(seen);
        if (out.size() > 4) {
            out = out.subList(0, 4);
            return "`" + String.join("`, `", out) + "`, …";
        }
        return "`" + String.join("`, `", out) + "`";
    }

    private static void renderInputRulePunchList(StringBuilder sb, Report r) {
        // Group offending classes by Kind for easier scanning. @Deprecated
        // classes are skipped — they'll be removed wholesale and don't need
        // input-rule fixes.
        Map<Report.Kind, List<ClassRecord>> byKind = new EnumMap<>(Report.Kind.class);
        for (Report.Kind k : Report.Kind.values()) byKind.put(k, new ArrayList<>());
        int total = 0;
        for (ClassRecord c : r.classes) {
            if (c.isDeprecated) continue;
            if (!c.ruleViolatingInputs().isEmpty()) {
                byKind.get(c.kind()).add(c);
                total++;
            }
        }
        if (total == 0) return;

        sb.append("## Inputs declared too concretely\n\n");
        sb.append("> Concrete spec params (`RealScalarParam`, `RealVectorParam`, …) belong only on Operators, which need to write the parameter. Distributions, CalcNodes, Loggers and other read-only holders should declare the interface (`RealScalar`, `RealVector`, …) so adapters and transforms can be substituted. Legacy `RealParameter` / `Function` Inputs are violations everywhere.\n\n");
        for (Report.Kind k : Report.Kind.values()) {
            List<ClassRecord> list = byKind.get(k);
            if (list.isEmpty()) continue;
            list.sort(Comparator.comparing(ClassRecord::fqn));
            sb.append("### ").append(k.label).append(" (").append(list.size()).append(")\n\n");
            for (ClassRecord c : list) {
                sb.append("- `").append(c.fqn()).append("`\n");
                for (InputDecl d : c.ruleViolatingInputs()) {
                    String tag = d.carrier() == InputDecl.Carrier.LEGACY ? "legacy" : "concrete";
                    sb.append("    - ").append(tag).append(": `Input<").append(d.typeStr()).append(">`\n");
                }
            }
            sb.append('\n');
        }
    }

    private static void renderXmlPunchList(StringBuilder sb, Report r) {
        renderExampleXmlPunchList(sb, r);
        renderFxTemplatePunchList(sb, r);
    }

    private static void renderExampleXmlPunchList(StringBuilder sb, Report r) {
        List<XmlRecord> needsV28 = new ArrayList<>();
        List<XmlRecord> v28NoSpec = new ArrayList<>();
        List<XmlRecord> hasLegacyParam = new ArrayList<>();
        for (XmlRecord x : r.xmls) {
            if (x.inLegacyDir() || x.isFxTemplate()) continue;
            if (!x.hasV28()) needsV28.add(x);
            else if (!x.bodyHasSpec()) v28NoSpec.add(x);
            else if (x.hasLegacyParam()) hasLegacyParam.add(x);
        }
        boolean clean = needsV28.isEmpty() && v28NoSpec.isEmpty() && hasLegacyParam.isEmpty();
        if (clean) {
            if (r.xmlTotal > 0) sb.append("## Example XMLs\n\nAll example XMLs target `version=\"2.8\"` and use spec types with no legacy parameter declarations. ✅\n\n");
            return;
        }
        sb.append("## Example XMLs pending migration\n\n");
        if (!needsV28.isEmpty()) {
            sb.append("**Needs `version=\"2.8\"`** (").append(needsV28.size()).append("):\n\n");
            for (XmlRecord x : needsV28) sb.append("- `").append(x.relPath()).append("`\n");
            sb.append('\n');
        }
        if (!v28NoSpec.isEmpty()) {
            sb.append("**Targets BEAST 3 but body has no `beast.base.spec.*` references** (")
                    .append(v28NoSpec.size()).append("):\n\n");
            for (XmlRecord x : v28NoSpec) sb.append("- `").append(x.relPath()).append("`\n");
            sb.append('\n');
        }
        if (!hasLegacyParam.isEmpty()) {
            sb.append("**Uses spec types but still has legacy `parameter.RealParameter`-style declarations** (")
                    .append(hasLegacyParam.size()).append("):\n\n");
            for (XmlRecord x : hasLegacyParam) sb.append("- `").append(x.relPath()).append("`\n");
            sb.append('\n');
        }
    }

    private static void renderFxTemplatePunchList(StringBuilder sb, Report r) {
        if (r.fxTotal == 0) return;
        // Categorise fxtemplates by spec/legacy-param signals.
        List<XmlRecord> noSpec = new ArrayList<>();
        List<XmlRecord> mixed = new ArrayList<>();
        List<XmlRecord> clean = new ArrayList<>();
        for (XmlRecord x : r.xmls) {
            if (!x.isFxTemplate() || x.inLegacyDir()) continue;
            if (!x.bodyHasSpec()) noSpec.add(x);
            else if (x.hasLegacyParam()) mixed.add(x);
            else clean.add(x);
        }
        if (noSpec.isEmpty() && mixed.isEmpty()) {
            sb.append("## FxTemplates\n\nAll BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅\n\n");
            return;
        }
        sb.append("## FxTemplates pending migration\n\n");
        sb.append("> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.\n\n");
        if (!mixed.isEmpty()) {
            sb.append("**Uses spec types but still has legacy `parameter.*` declarations** (")
                    .append(mixed.size()).append("):\n\n");
            for (XmlRecord x : mixed) sb.append("- `").append(x.relPath()).append("`\n");
            sb.append('\n');
        }
        if (!noSpec.isEmpty()) {
            sb.append("**No `beast.base.spec.*` references in body** (")
                    .append(noSpec.size()).append("):\n\n");
            for (XmlRecord x : noSpec) sb.append("- `").append(x.relPath()).append("`\n");
            sb.append('\n');
        }
    }
}

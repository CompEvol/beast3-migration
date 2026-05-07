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
            sb.append("> ⚠️ Local checkout not found at `").append(r.entry.path()).append("`. Clone it first.\n\n");
            return sb.toString();
        }
        if (!r.error.isBlank()) {
            sb.append("> ⚠️ Scan reported errors: ").append(r.error).append("\n\n");
        }

        renderSummary(sb, r);
        renderBuildGaps(sb, r);
        renderMavenCentral(sb, r);
        renderJavaPunchList(sb, r);
        renderXmlPunchList(sb, r);

        if (sb.charAt(sb.length() - 1) != '\n') sb.append('\n');
        return sb.toString();
    }

    private static void renderHeader(StringBuilder sb, Report r) {
        // The "version" of the report is whatever combination of fields
        // unambiguously identifies what was scanned: git commit, pom version,
        // and (when available) the published Maven Central version.
        sb.append("> **Scanned at:** ")
                .append(r.scannedAt.toLocalDateTime().toString()).append("  \n");
        sb.append("> **Local checkout:** `").append(r.entry.path()).append('`');
        if (!r.git.shortSha().isBlank()) {
            sb.append(" — commit `").append(r.git.shortSha()).append('`');
            if (!r.git.branch().isBlank() && !r.git.branch().equals("HEAD")) {
                sb.append(" on `").append(r.git.branch()).append('`');
            }
            if (r.git.dirty()) sb.append(" *(dirty working tree)*");
            if (!r.entry.github().isBlank() && !r.git.fullSha().isBlank()) {
                sb.append(" — [view on GitHub](https://github.com/")
                        .append(r.entry.github()).append("/commit/")
                        .append(r.git.fullSha()).append(')');
            }
        }
        sb.append("  \n");
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
        sb.append("- **XMLs:** ").append(r.xmlMigrated).append(" on spec / ")
                .append(r.xmlV28).append(" on `version=\"2.8\"` / ")
                .append(r.xmlTotal).append(" total");
        if (r.xmlLegacyDir > 0) sb.append(" (+").append(r.xmlLegacyDir).append(" under legacy/)");
        sb.append('\n');
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
                sb.append("**Mixed** (already imports spec; finish removing legacy):\n\n");
                mixed.sort(Comparator.comparing(ClassRecord::fqn));
                for (ClassRecord c : mixed) renderClassLine(sb, c);
                sb.append('\n');
            }
            if (!legacy.isEmpty()) {
                sb.append("**Legacy** (no spec imports yet):\n\n");
                legacy.sort(Comparator.comparing(ClassRecord::fqn));
                for (ClassRecord c : legacy) renderClassLine(sb, c);
                sb.append('\n');
            }
        }
    }

    private static void renderClassLine(StringBuilder sb, ClassRecord c) {
        sb.append("- `").append(c.fqn()).append('`');
        String evidence = formatEvidence(c.legacyEvidence());
        if (!evidence.isBlank()) sb.append(" — uses ").append(evidence);
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

    private static void renderXmlPunchList(StringBuilder sb, Report r) {
        List<XmlRecord> needsV28 = new ArrayList<>();
        List<XmlRecord> needsSpec = new ArrayList<>();
        for (XmlRecord x : r.xmls) {
            if (x.inLegacyDir()) continue;
            if (!x.hasV28()) needsV28.add(x);
            else if (!x.hasSpecNamespace()) needsSpec.add(x);
        }
        if (needsV28.isEmpty() && needsSpec.isEmpty()) {
            if (r.xmlTotal > 0) sb.append("## XMLs\n\nAll example XMLs are on `version=\"2.8\"` with the spec namespace. ✅\n\n");
            return;
        }
        sb.append("## XMLs pending migration\n\n");
        if (!needsV28.isEmpty()) {
            sb.append("**Needs `version=\"2.8\"`** (").append(needsV28.size()).append("):\n\n");
            for (XmlRecord x : needsV28) sb.append("- `").append(x.relPath()).append("`\n");
            sb.append('\n');
        }
        if (!needsSpec.isEmpty()) {
            sb.append("**Targets BEAST 3 but missing `beast.base.spec.*` in namespace** (")
                    .append(needsSpec.size()).append("):\n\n");
            for (XmlRecord x : needsSpec) sb.append("- `").append(x.relPath()).append("`\n");
            sb.append('\n');
        }
    }
}

package io.github.beast3.migration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import io.github.beast3.migration.Report.Kind;

/**
 * Renders the migration table into CHECKLIST.md between fixed markers,
 * preserving any prose authored outside them.
 */
public final class ChecklistWriter {

    public static final String BEGIN = "<!-- BEGIN AUTO -->";
    public static final String END = "<!-- END AUTO -->";

    private ChecklistWriter() {}

    public static void write(Path checklist, List<Report> reports) throws IOException {
        String generated = render(reports);
        if (Files.isRegularFile(checklist)) {
            String existing = Files.readString(checklist);
            int b = existing.indexOf(BEGIN);
            int e = existing.indexOf(END);
            if (b >= 0 && e > b) {
                String head = existing.substring(0, b);
                String tail = existing.substring(e + END.length());
                Files.writeString(checklist, head + BEGIN + "\n" + generated + END + tail);
                return;
            }
        }
        // No file or no markers — write a fresh one with default scaffold.
        String scaffold = """
                # BEAST 3 migration checklist

                The table below is generated from `packages.yaml` by the
                `beast3-migration-status` Java tool. Re-run with
                `mvn -q exec:java` (or `java -jar target/*-all.jar`) after
                cloning or updating any tracked package.

                Counts:
                * **Distributions / Operators / Loggers / CalcNodes / Parameters / StateNodes**
                  — `migrated / total` Java classes by kind. *Migrated* means
                  the class either imports from `beast.base.spec.*` or extends
                  a spec base type and doesn't simultaneously import a legacy
                  parameter type. The *Java class kinds* table below splits
                  `migrated` into spec / mixed / legacy / neutral so you can
                  see where each kind sits mid-migration.
                * **XMLs** — `spec / v2.8 / total`: `spec` = `<beast>` root
                  has both `version="2.8"` and a `beast.base.spec.*` namespace;
                  `v2.8` = root has `version="2.8"` regardless of namespace;
                  `total` = every XML with a `<beast>` root. Files under
                  `examples/legacy*/` are reported separately as `(+N legacy)`
                  and excluded from the totals.

                Build/release columns are simple presence checks. Maven Central
                shows the latest released version (or `—` if not published).

                """ + BEGIN + "\n" + generated + END + "\n";
        Files.writeString(checklist, scaffold);
    }

    private static String render(List<Report> reports) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n_Last regenerated: ")
                .append(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .append("_\n\n");

        sb.append("## Release & build status\n\n");
        sb.append("| Package | Stage | Maven Central | Pom version | JPMS | Release | CI |\n");
        sb.append("|---|---|---|---|:-:|:-:|:-:|\n");
        for (Report r : reports) {
            sb.append("| ").append(linkPackage(r))
                    .append(" | ").append(prettyStage(r.entry.stage()))
                    .append(" | ").append(mavenCell(r))
                    .append(" | ").append(orDash(r.pomVersion))
                    .append(" | ").append(check(r.hasModuleInfo))
                    .append(" | ").append(check(r.hasReleaseProfile && (r.hasReleaseScript || r.hasPom)))
                    .append(" | ").append(check(r.hasGithubActions))
                    .append(" |\n");
        }
        sb.append('\n');

        sb.append("## Migration progress (Java + XML)\n\n");
        sb.append("| Package | Distrs | Ops | Loggers | CalcNodes | Params | StateNodes | XMLs | FxTemplates | Input rule |\n");
        sb.append("|---|---|---|---|---|---|---|---|---|---|\n");
        for (Report r : reports) {
            sb.append("| ").append(r.entry.name())
                    .append(" | ").append(r.javaCounts.get(Kind.DISTRIBUTION).tableCell())
                    .append(" | ").append(r.javaCounts.get(Kind.OPERATOR).tableCell())
                    .append(" | ").append(r.javaCounts.get(Kind.LOGGER).tableCell())
                    .append(" | ").append(r.javaCounts.get(Kind.CALCNODE).tableCell())
                    .append(" | ").append(r.javaCounts.get(Kind.PARAMETER).tableCell())
                    .append(" | ").append(r.javaCounts.get(Kind.STATENODE).tableCell())
                    .append(" | ").append(xmlCell(r))
                    .append(" | ").append(fxCell(r))
                    .append(" | ").append(inputRuleCell(r))
                    .append(" |\n");
        }

        sb.append("\nLegend: ✅ = present, ❌ = missing, `n / m` = `migrated / total`, `—` = no data.\n");
        sb.append("FxTemplates show `clean / spec / total` — `clean` = uses spec types and no legacy `parameter.RealParameter`-style attrs; `spec` = body references `beast.base.spec.*` at all.\n");
        sb.append("Input rule shows `classes / violations`: classes with at least one Input declared too concretely / total violating Inputs. Concrete spec params (RealScalarParam, …) belong only on Operators; Distributions/CalcNodes/etc. should declare the interface (RealScalar, RealVector, …). 0 = ✅.\n\n");

        // Detailed breakdown — useful when a class is mixed (in progress).
        sb.append("## Java class kinds (with mixed/legacy breakdown)\n\n");
        sb.append("Per package, for each kind: `spec / mixed / legacy / neutral · total`.\n");
        sb.append("`mixed` classes import both spec and legacy types — usually mid-migration.\n\n");
        sb.append("| Package | Distributions | Operators | Loggers | CalcNodes | Parameters | StateNodes |\n");
        sb.append("|---|---|---|---|---|---|---|\n");
        for (Report r : reports) {
            sb.append("| ").append(r.entry.name())
                    .append(" | ").append(detailCell(r, Kind.DISTRIBUTION))
                    .append(" | ").append(detailCell(r, Kind.OPERATOR))
                    .append(" | ").append(detailCell(r, Kind.LOGGER))
                    .append(" | ").append(detailCell(r, Kind.CALCNODE))
                    .append(" | ").append(detailCell(r, Kind.PARAMETER))
                    .append(" | ").append(detailCell(r, Kind.STATENODE))
                    .append(" |\n");
        }
        sb.append('\n');

        // Diagnostics
        boolean anyError = reports.stream().anyMatch(r -> !r.error.isBlank() || !r.pathExists
                || (!r.entry.hasMavenCoords() ? false : !r.mavenCentralError.isBlank()));
        if (anyError) {
            sb.append("## Diagnostics\n\n");
            for (Report r : reports) {
                StringBuilder lineSb = new StringBuilder();
                if (!r.pathExists) lineSb.append("local path missing (").append(r.entry.path()).append("); ");
                if (!r.error.isBlank()) lineSb.append(r.error).append("; ");
                if (r.entry.hasMavenCoords() && !r.mavenCentralError.isBlank())
                    lineSb.append("Maven Central: ").append(r.mavenCentralError);
                String line = lineSb.toString();
                if (!line.isBlank()) {
                    sb.append("- **").append(r.entry.name()).append("** — ").append(line).append('\n');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private static String linkPackage(Report r) {
        String name = r.entry.name();
        String gh = r.entry.github();
        if (gh.isBlank()) return name;
        return "[" + name + "](https://github.com/" + gh + ")";
    }

    private static String mavenCell(Report r) {
        if (!r.entry.hasMavenCoords()) return "—";
        if (!r.mavenCentralLatest.isBlank()) {
            String url = "https://central.sonatype.com/artifact/"
                    + r.entry.mavenGroupId() + "/" + r.entry.mavenArtifactId();
            return "[" + r.mavenCentralLatest + "](" + url + ")";
        }
        return "—";
    }

    private static String fxCell(Report r) {
        if (r.fxTotal == 0) return "—";
        return r.fxClean + " / " + r.fxWithSpec + " / " + r.fxTotal;
    }

    private static String inputRuleCell(Report r) {
        if (r.inputViolations == 0 && r.classesWithInputViolations == 0) return "✅";
        return r.classesWithInputViolations + " / " + r.inputViolations;
    }

    private static String xmlCell(Report r) {
        if (r.xmlTotal == 0 && r.xmlLegacyDir == 0) return "—";
        // spec / v2.8 / total — distinguishes "targets BEAST 3 runtime" from
        // "uses spec namespace".
        StringBuilder s = new StringBuilder();
        s.append(r.xmlMigrated).append(" / ")
                .append(r.xmlV28).append(" / ")
                .append(r.xmlTotal);
        if (r.xmlLegacyDir > 0) s.append(" (+").append(r.xmlLegacyDir).append(" legacy)");
        return s.toString();
    }

    private static String detailCell(Report r, Kind k) {
        Report.KindCounts c = r.javaCounts.get(k);
        if (c.total == 0) return "—";
        return c.spec + " / " + c.mixed + " / " + c.legacy + " / " + c.neutral
                + " · " + c.total;
    }

    private static String orDash(String s) {
        return (s == null || s.isBlank()) ? "—" : s;
    }

    private static String check(boolean b) {
        return b ? "✅" : "❌";
    }

    private static String prettyStage(String stage) {
        if (stage == null || stage.isBlank()) return "—";
        return switch (stage) {
            case "maven_central" -> "Maven Central";
            case "compile_tested" -> "compile-tested";
            case "in_progress" -> "in progress";
            case "not_started" -> "not started";
            default -> stage;
        };
    }
}

package io.github.beast3.migration;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Report {

    public enum Kind {
        DISTRIBUTION("Distributions"),
        OPERATOR("Operators"),
        LOGGER("Loggers"),
        CALCNODE("CalcNodes"),
        PARAMETER("Parameters"),
        STATENODE("StateNodes"),
        OTHER("Other");

        public final String label;
        Kind(String label) { this.label = label; }
    }

    public enum Status { SPEC, MIXED, LEGACY, NEUTRAL }

    public static final class KindCounts {
        public int total;
        public int spec;
        public int mixed;
        public int legacy;
        public int neutral;

        public void add(Status s) {
            total++;
            switch (s) {
                case SPEC -> spec++;
                case MIXED -> mixed++;
                case LEGACY -> legacy++;
                case NEUTRAL -> neutral++;
            }
        }

        /** Compact cell: classes still on a legacy base. ✅ when none. */
        public String tableCell() {
            if (total == 0) return "—";
            int needsWork = legacy + mixed;
            if (needsWork == 0) return "✅ " + total;
            return needsWork + " / " + total;
        }
    }

    public final PackageEntry entry;
    public final Map<Kind, KindCounts> javaCounts = new EnumMap<>(Kind.class);
    public final List<ClassRecord> classes = new ArrayList<>();
    public final List<XmlRecord> xmls = new ArrayList<>();
    public final List<DeprecatedXmlRef> xmlDeprecatedRefs = new ArrayList<>();
    public int xmlTotal;            // example XMLs only (excludes fxtemplates)
    public int xmlMigrated;         // version="2.8" AND beast.base.spec.* namespace
    public int xmlV28;              // version="2.8" (regardless of namespace)
    public int xmlLegacyDir;        // count of XMLs explicitly under examples/legacy*

    public int fxTotal;             // BEAUti fxtemplates
    public int fxWithSpec;          // fxtemplates whose body references beast.base.spec.*
    public int fxClean;             // fxtemplates with spec body AND no legacy parameter spec= attrs

    /** Classes with at least one Input violating the carrier rule. */
    public int classesWithInputViolations;
    /** Total number of Input violations across the package. */
    public int inputViolations;

    public boolean hasPom;
    public boolean hasReleaseProfile;
    public boolean hasModuleInfo;
    public boolean hasGithubActions;
    public boolean hasReleaseScript;
    public boolean hasVersionXml;

    public String pomVersion = "";
    public String mavenCentralLatest = "";   // empty if not published or unreachable
    public String mavenCentralError = "";    // diagnostic, e.g. "404", "timeout"

    public boolean pathExists;
    public String error = "";

    public GitInfo git = GitInfo.MISSING;
    public java.time.OffsetDateTime scannedAt = java.time.OffsetDateTime.now();

    public Report(PackageEntry entry) {
        this.entry = entry;
        for (Kind k : Kind.values()) javaCounts.put(k, new KindCounts());
    }

    public Map<String, Object> toJsonLike() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("name", entry.name());
        // Local filesystem path intentionally omitted — would leak
        // user-specific paths into the published status.json.
        m.put("github", entry.github());
        m.put("maven", entry.hasMavenCoords()
                ? entry.mavenGroupId() + ":" + entry.mavenArtifactId()
                : "");
        m.put("stage", entry.stage());
        m.put("notes", entry.notes());
        m.put("pathExists", pathExists);
        m.put("error", error);

        Map<String, Object> java = new LinkedHashMap<>();
        for (Kind k : Kind.values()) {
            KindCounts c = javaCounts.get(k);
            Map<String, Object> kc = new LinkedHashMap<>();
            kc.put("total", c.total);
            kc.put("spec", c.spec);
            kc.put("mixed", c.mixed);
            kc.put("legacy", c.legacy);
            kc.put("neutral", c.neutral);
            java.put(k.name().toLowerCase(), kc);
        }
        m.put("java", java);

        Map<String, Object> xml = new LinkedHashMap<>();
        xml.put("total", xmlTotal);
        xml.put("migrated", xmlMigrated);
        xml.put("v28", xmlV28);
        xml.put("legacyDir", xmlLegacyDir);
        m.put("xml", xml);

        Map<String, Object> fx = new LinkedHashMap<>();
        fx.put("total", fxTotal);
        fx.put("withSpec", fxWithSpec);
        fx.put("clean", fxClean);
        m.put("fxTemplates", fx);

        Map<String, Object> ir = new LinkedHashMap<>();
        ir.put("classesWithViolations", classesWithInputViolations);
        ir.put("totalViolations", inputViolations);
        m.put("inputRule", ir);

        Map<String, Object> dep = new LinkedHashMap<>();
        dep.put("count", xmlDeprecatedRefs.size());
        List<Map<String, Object>> refs = new ArrayList<>();
        for (DeprecatedXmlRef ref : xmlDeprecatedRefs) {
            Map<String, Object> e = new LinkedHashMap<>();
            // Relative path keeps status.json portable across checkouts.
            e.put("file", ref.file().toString());
            e.put("hit", ref.hit());
            e.put("canonicalFqn", ref.canonicalFqn());
            e.put("replacement", ref.replacement());
            e.put("isFxTemplate", ref.isFxTemplate());
            e.put("isFqn", ref.isFqn());
            e.put("source", ref.source());
            refs.add(e);
        }
        dep.put("refs", refs);
        m.put("xmlDeprecated", dep);

        Map<String, Object> build = new LinkedHashMap<>();
        build.put("hasPom", hasPom);
        build.put("hasReleaseProfile", hasReleaseProfile);
        build.put("hasModuleInfo", hasModuleInfo);
        build.put("hasGithubActions", hasGithubActions);
        build.put("hasReleaseScript", hasReleaseScript);
        build.put("hasVersionXml", hasVersionXml);
        build.put("pomVersion", pomVersion);
        m.put("build", build);

        Map<String, Object> mc = new LinkedHashMap<>();
        mc.put("groupId", entry.mavenGroupId());
        mc.put("artifactId", entry.mavenArtifactId());
        mc.put("latest", mavenCentralLatest);
        mc.put("error", mavenCentralError);
        m.put("mavenCentral", mc);

        return m;
    }
}

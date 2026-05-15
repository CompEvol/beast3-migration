package io.github.beast3.migration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Counts BEAST XML files in a package and tags each as "migrated to beast3"
 * if the root element declares {@code version="2.8"} and the namespace
 * attribute references {@code beast.base.spec.*}.
 *
 * <p>XMLs under a directory named {@code legacy*}, {@code 2.7*}, or
 * {@code v2.7*} are counted separately and excluded from the migrated /
 * total totals — they're intentional pre-migration snapshots. Deprecated-
 * class references inside them are also skipped (see
 * {@link #scanForDeprecatedReferences}).</p>
 */
public final class XmlScanner {

    private static final Pattern BEAST_ROOT = Pattern.compile(
            "<beast\\b([^>]*)>", Pattern.DOTALL);
    private static final Pattern VERSION_28 = Pattern.compile(
            "version\\s*=\\s*[\"']2\\.8[\"']");
    private static final Pattern NAMESPACE_SPEC = Pattern.compile(
            "namespace\\s*=\\s*[\"'][^\"']*beast\\.base\\.spec\\.");
    private static final Pattern MERGEWITH = Pattern.compile("<mergewith\\b");
    private static final Pattern SPEC_REF = Pattern.compile("beast\\.base\\.spec\\.");
    /** Legacy parameter spec= attribute, e.g. {@code spec='parameter.RealParameter'}. */
    private static final Pattern LEGACY_PARAM_REF = Pattern.compile(
            "spec\\s*=\\s*[\"']\\s*(?:beast\\.base\\.inference\\.)?parameter\\.(?:Real|Integer|Boolean)Parameter");

    /** Any {@code spec="X"} or {@code spec='X'} attribute. Captures the value. */
    private static final Pattern SPEC_ATTR = Pattern.compile(
            "\\bspec\\s*=\\s*[\"']([^\"']+)[\"']");
    /** {@code <map name="...">FQN</map>} declaration. Captures the FQN body. */
    private static final Pattern MAP_DECL = Pattern.compile(
            "<map\\b[^>]*>\\s*([A-Za-z_][\\w.]*)\\s*</map>");

    private XmlScanner() {}

    public static void scan(Path pkgRoot, Report report) {
        // Per-package opt-out: directory names listed in packages.yaml.
        List<String> excludeDirs = report.entry.xmlExcludeDirs().stream()
                .map(s -> s.toLowerCase(Locale.ROOT)).toList();
        // Optional git-tracked filter: skips stray run output, IDE caches,
        // and anything else not committed. Null when the package isn't a
        // git working tree or `git ls-files` failed — fall back to "no filter".
        Set<Path> tracked = GitInfo.trackedFiles(pkgRoot);
        report.xmlGitFilterApplied = (tracked != null);

        try (Stream<Path> stream = Files.walk(pkgRoot)) {
            stream.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".xml"))
                    .filter(XmlScanner::isInteresting)
                    .forEach(p -> {
                        if (isExcludedDir(pkgRoot, p, excludeDirs)) {
                            report.xmlSkippedExcludeDir++;
                            return;
                        }
                        if (tracked != null && !tracked.contains(p.toAbsolutePath().normalize())) {
                            report.xmlSkippedUntracked++;
                            return;
                        }
                        classify(pkgRoot, pkgRoot, p, report);
                    });
        } catch (IOException e) {
            // ignore
        }
    }

    private static boolean isInteresting(Path p) {
        for (Path part : p) {
            String n = part.toString();
            if (n.equals("target") || n.equals("build") || n.equals(".git")
                    || n.equals(".idea") || n.equals("node_modules")) {
                return false;
            }
        }
        // Exclude the obvious non-BEAST XML by filename. Anything else still
        // gets the <beast> root check downstream.
        String fn = p.getFileName().toString();
        return !fn.equals("pom.xml") && !fn.equals("version.xml");
    }

    private static boolean isExcludedDir(Path root, Path file, List<String> excludeDirs) {
        if (excludeDirs.isEmpty()) return false;
        Path rel = root.relativize(file);
        for (Path part : rel) {
            if (excludeDirs.contains(part.toString().toLowerCase(Locale.ROOT))) return true;
        }
        return false;
    }

    private static boolean isLegacyDir(Path root, Path file) {
        // Legacy snapshots live under directories named `legacy*` or
        // pinned to the previous major (`2.7` / `v2.7`). `beast2*` was
        // historically included but false-positives badly: `beast2vs1/`
        // is a standard BEAST 1-vs-BEAST 2 comparison suite (found even
        // under beast3's `.../examples/spec/beast2vs1/`, i.e. the
        // migration target).
        Path rel = root.relativize(file);
        for (Path part : rel) {
            String name = part.toString().toLowerCase();
            if (name.startsWith("legacy") || name.startsWith("v2.7")
                    || name.startsWith("2.7")) {
                return true;
            }
        }
        return false;
    }

    private static void classify(Path root, Path pkgRoot, Path file, Report report) {
        if (isLegacyDir(root, file)) {
            report.xmlLegacyDir++;
            report.xmls.add(new XmlRecord(
                    file, pkgRoot, false, false, false, false, true, false));
            return;
        }
        String content;
        try {
            content = Files.readString(file);
        } catch (IOException e) {
            return;
        }
        // Skip non-BEAST XML (e.g. plugin descriptors, beauti templates without <beast> root).
        Matcher m = BEAST_ROOT.matcher(content);
        if (!m.find()) return;
        String rootAttrs = m.group(1);

        boolean isFx = isFxTemplate(root, file, content);
        boolean v28 = VERSION_28.matcher(rootAttrs).find();
        boolean nsSpec = NAMESPACE_SPEC.matcher(rootAttrs).find();
        boolean bodyHasSpec = SPEC_REF.matcher(content).find();
        boolean hasLegacyParam = LEGACY_PARAM_REF.matcher(content).find();

        if (isFx) {
            report.fxTotal++;
            if (bodyHasSpec) report.fxWithSpec++;
            if (bodyHasSpec && !hasLegacyParam) report.fxClean++;
            report.xmls.add(new XmlRecord(
                    file, pkgRoot, v28, nsSpec, bodyHasSpec, hasLegacyParam, false, true));
            return;
        }

        report.xmlTotal++;
        if (v28) report.xmlV28++;
        // "Migrated" = targets BEAST 3 runtime AND uses spec types in body
        // AND has no legacy parameter declarations. Namespace listing of
        // beast.base.spec.* is a hint, not a requirement — many migrated
        // XMLs use spec FQNs everywhere and need no namespace help.
        if (v28 && bodyHasSpec && !hasLegacyParam) report.xmlMigrated++;
        report.xmls.add(new XmlRecord(
                file, pkgRoot, v28, nsSpec, bodyHasSpec, hasLegacyParam, false, false));
    }

    private static boolean isFxTemplate(Path root, Path file, String content) {
        // Path signal: any segment named "fxtemplates".
        for (Path part : file) {
            if (part.toString().equalsIgnoreCase("fxtemplates")) return true;
        }
        // Content signal: BEAUti merge directive — definitive marker.
        return MERGEWITH.matcher(content).find();
    }

    /**
     * Re-reads each XML/fxtemplate already classified for this report and
     * records references to deprecated classes — fully-qualified hits matched
     * against {@code deprecatedFqns}, plus unqualified hits matched against
     * the unambiguously-deprecated short-name map. Replacement hints come
     * from {@code specReplacements}.
     *
     * <p>Run this only after the global {@code @Deprecated} registry has been
     * collected (Phase 2 in {@link Main#scanAll}); the maps are global across
     * all tracked packages.</p>
     */
    public static void scanForDeprecatedReferences(
            Report report,
            java.util.Set<String> deprecatedFqns,
            java.util.Map<String, String> deprecatedShortNames,
            java.util.Map<String, String> specReplacements) {
        for (XmlRecord rec : report.xmls) {
            // Files under any `legacy*` / `beast2*` / `2.7` directory are
            // pre-migration snapshots intentionally kept for reference —
            // they're already excluded from the example-XML counters, so
            // don't surface their deprecated `spec=`/`<map>` hits either.
            if (rec.inLegacyDir()) continue;
            String content;
            try {
                content = java.nio.file.Files.readString(rec.file());
            } catch (java.io.IOException e) {
                continue;
            }
            collectFromAttribute(content, SPEC_ATTR, "spec", rec, report,
                    deprecatedFqns, deprecatedShortNames, specReplacements);
            collectFromAttribute(content, MAP_DECL, "map", rec, report,
                    deprecatedFqns, deprecatedShortNames, specReplacements);
        }
    }

    private static void collectFromAttribute(
            String content, Pattern pattern, String source,
            XmlRecord rec, Report report,
            java.util.Set<String> deprecatedFqns,
            java.util.Map<String, String> deprecatedShortNames,
            java.util.Map<String, String> specReplacements) {
        Matcher m = pattern.matcher(content);
        while (m.find()) {
            String value = m.group(1).trim();
            if (value.isEmpty()) continue;
            // FQN hit: literal match against the deprecated registry.
            if (value.contains(".") && deprecatedFqns.contains(value)) {
                report.xmlDeprecatedRefs.add(new DeprecatedXmlRef(
                        rec.file(), value, value,
                        specReplacements.getOrDefault(value, ""),
                        rec.isFxTemplate(), true, source));
                continue;
            }
            // Short-name hit: bare class name whose every FQN is deprecated.
            if (!value.contains(".") && deprecatedShortNames.containsKey(value)) {
                String canonical = deprecatedShortNames.get(value);
                report.xmlDeprecatedRefs.add(new DeprecatedXmlRef(
                        rec.file(), value, canonical,
                        specReplacements.getOrDefault(canonical, ""),
                        rec.isFxTemplate(), false, source));
            }
        }
    }
}

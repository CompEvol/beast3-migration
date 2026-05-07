package io.github.beast3.migration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Counts BEAST XML files in a package and tags each as "migrated to beast3"
 * if the root element declares {@code version="2.8"} and the namespace
 * attribute references {@code beast.base.spec.*}.
 *
 * <p>XMLs under any directory whose name starts with {@code legacy} are
 * counted separately and excluded from the migrated/total totals — they're
 * intentional pre-migration snapshots.</p>
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

    private XmlScanner() {}

    public static void scan(Path pkgRoot, Report report) {
        // Walk the whole package once; filter out build/IDE noise.
        try (Stream<Path> stream = Files.walk(pkgRoot)) {
            stream.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".xml"))
                    .filter(XmlScanner::isInteresting)
                    .forEach(p -> classify(pkgRoot, pkgRoot, p, report));
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

    private static boolean isLegacyDir(Path root, Path file) {
        Path rel = root.relativize(file);
        for (Path part : rel) {
            String name = part.toString().toLowerCase();
            if (name.startsWith("legacy") || name.startsWith("beast2")
                    || name.startsWith("v2.7") || name.startsWith("2.7")) {
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
}

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
            "version\\s*=\\s*\"2\\.8\"");
    private static final Pattern NAMESPACE_SPEC = Pattern.compile(
            "namespace\\s*=\\s*\"[^\"]*beast\\.base\\.spec\\.");

    private XmlScanner() {}

    public static void scan(Path pkgRoot, Report report) {
        // Walk every common XML location across all submodules.
        try (Stream<Path> stream = Files.walk(pkgRoot, 8)) {
            stream.filter(Files::isDirectory)
                    .filter(p -> {
                        String n = p.getFileName() == null ? "" : p.getFileName().toString();
                        return (n.equals("examples")
                                || n.equals("fxtemplates")
                                || p.endsWith(Path.of("src", "main", "resources")))
                                && !p.toString().contains("/target/");
                    })
                    .forEach(d -> walk(d, pkgRoot, report));
        } catch (IOException e) {
            // ignore
        }
    }

    private static void walk(Path root, Path pkgRoot, Report report) {
        try (Stream<Path> stream = Files.walk(root)) {
            stream.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".xml"))
                    .forEach(p -> classify(root, pkgRoot, p, report));
        } catch (IOException e) {
            // Ignore — partial directories should not crash the run.
        }
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
        boolean legacy = isLegacyDir(root, file);
        if (legacy) {
            report.xmlLegacyDir++;
            report.xmls.add(new XmlRecord(file, pkgRoot, false, false, true));
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
        report.xmlTotal++;

        boolean v28 = VERSION_28.matcher(rootAttrs).find();
        boolean spec = NAMESPACE_SPEC.matcher(rootAttrs).find();
        if (v28) report.xmlV28++;
        if (v28 && spec) report.xmlMigrated++;
        report.xmls.add(new XmlRecord(file, pkgRoot, v28, spec, false));
    }
}

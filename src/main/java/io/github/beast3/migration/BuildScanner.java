package io.github.beast3.migration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Detects build & release indicators per package: presence of pom.xml,
 * a `release` profile inside it, GitHub Actions workflows, a release
 * shell script, a JPMS module-info, and a BEAST version.xml.
 */
public final class BuildScanner {

    private static final Pattern POM_VERSION = Pattern.compile(
            "<version>([^<]+)</version>");
    private static final Pattern RELEASE_PROFILE = Pattern.compile(
            "<id>\\s*release\\s*</id>");

    private BuildScanner() {}

    public static void scan(Path pkgRoot, Report report) {
        Path pom = pkgRoot.resolve("pom.xml");
        if (Files.isRegularFile(pom)) {
            report.hasPom = true;
            try {
                String pomContent = Files.readString(pom);
                report.hasReleaseProfile = RELEASE_PROFILE.matcher(pomContent).find();

                // Project version is the first <version> appearing before
                // any <dependency> block — extract it cheaply with a window.
                int depIdx = pomContent.indexOf("<dependencies>");
                String head = depIdx > 0 ? pomContent.substring(0, depIdx) : pomContent;
                Matcher mv = POM_VERSION.matcher(head);
                if (mv.find()) report.pomVersion = mv.group(1).trim();
            } catch (IOException e) {
                // ignore
            }
        }

        report.hasModuleInfo = findFile(pkgRoot, "module-info.java");

        Path workflows = pkgRoot.resolve(".github/workflows");
        if (Files.isDirectory(workflows)) {
            try (Stream<Path> s = Files.list(workflows)) {
                report.hasGithubActions = s.anyMatch(p -> {
                    String n = p.getFileName().toString().toLowerCase();
                    return n.endsWith(".yml") || n.endsWith(".yaml");
                });
            } catch (IOException e) {
                // ignore
            }
        }

        report.hasReleaseScript = Files.isRegularFile(pkgRoot.resolve("release.sh"))
                || Files.isDirectory(pkgRoot.resolve("release"));

        report.hasVersionXml = Files.isRegularFile(pkgRoot.resolve("version.xml"));
    }

    private static boolean findFile(Path root, String name) {
        if (!Files.isDirectory(root)) return false;
        try (Stream<Path> s = Files.walk(root, 8)) {
            return s.anyMatch(p ->
                    p.getFileName() != null
                            && p.getFileName().toString().equals(name)
                            && !p.toString().contains("/target/"));
        } catch (IOException e) {
            return false;
        }
    }
}

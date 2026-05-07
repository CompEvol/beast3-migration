package io.github.beast3.migration;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public final class Main {

    public static void main(String[] args) throws Exception {
        Path repoRoot = (args.length > 0 ? Paths.get(args[0]) : Paths.get("")).toAbsolutePath().normalize();
        Path config = repoRoot.resolve("packages.yaml");
        Path checklist = repoRoot.resolve("CHECKLIST.md");
        Path statusJson = repoRoot.resolve("status.json");

        boolean offline = Boolean.parseBoolean(System.getProperty("offline", "false"));

        System.out.println("Analyzing packages from " + config);
        List<PackageEntry> entries = loadConfig(config, repoRoot);
        System.out.println("Found " + entries.size() + " package(s).");

        MavenCentralProbe probe = new MavenCentralProbe();
        List<Report> reports = new ArrayList<>();
        for (PackageEntry e : entries) {
            System.out.println("  - " + e.name() + " (" + e.path() + ")");
            Report r = new Report(e);
            r.pathExists = Files.isDirectory(e.path());
            if (r.pathExists) {
                JavaScanner.scan(e.path(), r);
                XmlScanner.scan(e.path(), r);
                BuildScanner.scan(e.path(), r);
            } else {
                r.error = "local path missing";
            }
            if (!offline) probe.probe(r);
            reports.add(r);
        }

        ChecklistWriter.write(checklist, reports);
        StatusJsonWriter.write(statusJson, reports);
        System.out.println("Wrote " + checklist);
        System.out.println("Wrote " + statusJson);
    }

    @SuppressWarnings("unchecked")
    private static List<PackageEntry> loadConfig(Path config, Path repoRoot) throws Exception {
        Yaml yaml = new Yaml();
        try (Reader r = Files.newBufferedReader(config)) {
            Map<String, Object> root = yaml.load(r);
            List<Map<String, Object>> raw = (List<Map<String, Object>>) root.get("packages");
            List<PackageEntry> out = new ArrayList<>();
            for (Map<String, Object> m : raw) out.add(PackageEntry.from(m, repoRoot));
            return out;
        }
    }
}

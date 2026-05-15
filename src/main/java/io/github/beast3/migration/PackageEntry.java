package io.github.beast3.migration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record PackageEntry(
        String name,
        Path path,
        String github,
        String mavenGroupId,
        String mavenArtifactId,
        String stage,
        String notes,
        boolean specFocus,
        List<String> xmlExcludeDirs) {

    public static PackageEntry from(Map<String, Object> raw, Path repoRoot) {
        String name = str(raw, "name");
        String pathStr = str(raw, "path");
        Path path = repoRoot.resolve(pathStr).normalize();
        String maven = Optional.ofNullable((String) raw.get("maven")).orElse("");
        String groupId = "", artifactId = "";
        if (!maven.isBlank()) {
            int colon = maven.indexOf(':');
            if (colon > 0) {
                groupId = maven.substring(0, colon).trim();
                artifactId = maven.substring(colon + 1).trim();
            }
        }
        Object focus = raw.get("specFocus");
        boolean specFocus = focus instanceof Boolean b ? b
                : focus != null && Boolean.parseBoolean(focus.toString());
        return new PackageEntry(
                name,
                path,
                Optional.ofNullable((String) raw.get("github")).orElse(""),
                groupId,
                artifactId,
                Optional.ofNullable((String) raw.get("stage")).orElse(""),
                Optional.ofNullable((String) raw.get("notes")).orElse(""),
                specFocus,
                parseStringList(raw.get("xmlExcludeDirs")));
    }

    /** Optional YAML list — accepts a single string, a YAML sequence, or nothing. */
    @SuppressWarnings("unchecked")
    private static List<String> parseStringList(Object raw) {
        if (raw == null) return List.of();
        if (raw instanceof List<?> l) {
            List<String> out = new ArrayList<>(l.size());
            for (Object o : l) if (o != null) out.add(o.toString());
            return List.copyOf(out);
        }
        return List.of(raw.toString());
    }

    private static String str(Map<String, Object> raw, String key) {
        Object v = raw.get(key);
        if (v == null) throw new IllegalArgumentException("Missing key '" + key + "' in package entry: " + raw);
        return v.toString();
    }

    public boolean hasMavenCoords() {
        return !mavenGroupId.isBlank() && !mavenArtifactId.isBlank();
    }
}

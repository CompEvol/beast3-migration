package io.github.beast3.migration;

import java.nio.file.Path;

/**
 * Per-XML record produced by {@link XmlScanner}.
 */
public record XmlRecord(
        Path file,
        Path relativeTo,
        boolean hasV28,
        boolean hasSpecNamespace,
        boolean inLegacyDir) {

    public Path relPath() {
        return relativeTo.relativize(file);
    }
}

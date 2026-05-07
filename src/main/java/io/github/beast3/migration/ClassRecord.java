package io.github.beast3.migration;

import java.nio.file.Path;
import java.util.List;

import io.github.beast3.migration.Report.Kind;
import io.github.beast3.migration.Report.Status;

/**
 * Per-class record produced by {@link JavaScanner}, carrying enough
 * evidence to explain a punch-list entry.
 *
 * <p>{@code legacyEvidence} / {@code specEvidence} hold imports or extends/
 * implements tokens that triggered the classification — useful when the
 * report needs to say <em>why</em> a class is still legacy.</p>
 */
public record ClassRecord(
        Path file,
        String packageName,
        String simpleName,
        Kind kind,
        Status status,
        String extendsClause,
        String implementsClause,
        List<String> legacyEvidence,
        List<String> specEvidence) {

    public String fqn() {
        return packageName.isBlank() ? simpleName : packageName + "." + simpleName;
    }
}

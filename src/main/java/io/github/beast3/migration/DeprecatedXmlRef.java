package io.github.beast3.migration;

import java.nio.file.Path;

/**
 * One reference to a deprecated class found in an XML or BEAUti fxtemplate
 * during the post-Java-scan deprecation pass.
 *
 * @param file        absolute path to the XML / fxtemplate the hit was found in
 * @param hit         the literal string that matched — either a fully-qualified
 *                    name (when {@code isFqn} is true) or a bare short name
 *                    (when {@code isFqn} is false and the short name's full
 *                    FQN set is unambiguously deprecated)
 * @param canonicalFqn the deprecated FQN the hit resolves to (equals
 *                    {@code hit} when {@code isFqn}; otherwise the canonical
 *                    FQN picked from the unambiguous-short-name map)
 * @param replacement spec FQN with the same simple name, or empty if no
 *                    replacement was found in the scanned packages
 * @param isFxTemplate true if {@code file} is a BEAUti fxtemplate
 * @param isFqn       true if {@code hit} is a fully qualified name; false if
 *                    it's an unqualified short name that resolves to a
 *                    deprecated class
 * @param source      where the hit was found — {@code "spec"}, {@code "map"}, or {@code "tag"}
 */
public record DeprecatedXmlRef(
        Path file,
        String hit,
        String canonicalFqn,
        String replacement,
        boolean isFxTemplate,
        boolean isFqn,
        String source) {
}

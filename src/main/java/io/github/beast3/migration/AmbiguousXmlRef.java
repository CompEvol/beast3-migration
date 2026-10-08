package io.github.beast3.migration;

import java.nio.file.Path;

/**
 * One unqualified class name in an XML that resolves to both a deprecated and
 * a live class, so which one it binds to is decided by namespace ordering
 * rather than by anything written in the file.
 *
 * <p>{@code XMLParserUtils.resolveClass} walks the {@code namespace} attribute
 * in order and returns the first entry that resolves. A file that lists the
 * legacy packages first therefore binds {@code spec="TreeLikelihood"} to the
 * legacy class even when every neighbouring object is a spec type — which the
 * parser reports, if at all, as a type mismatch somewhere else entirely.</p>
 *
 * <p>This is a warning, not a deprecated-reference hit: the name may resolve
 * correctly today. Qualifying it makes that a property of the file rather than
 * of the namespace list.</p>
 *
 * @param file          absolute path to the XML / fxtemplate the hit was found in
 * @param hit           the unqualified name as written
 * @param deprecatedFqn the deprecated FQN it could resolve to
 * @param replacement   spec FQN with the same simple name, or empty if none was found
 * @param isFxTemplate  true if {@code file} is a BEAUti fxtemplate
 * @param source        where the hit was found — {@code "spec"} or {@code "map"}
 */
public record AmbiguousXmlRef(
        Path file,
        String hit,
        String deprecatedFqn,
        String replacement,
        boolean isFxTemplate,
        String source) {
}

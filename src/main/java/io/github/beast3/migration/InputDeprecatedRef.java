package io.github.beast3.migration;

/**
 * One {@code Input<T>} field declaration whose type references an
 * {@code @Deprecated} class. Surfaced by the deprecation-aware Java scan
 * after the global {@code @Deprecated} FQN registry has been built.
 *
 * @param classFqn     fully-qualified name of the class declaring the Input
 * @param decl         the Input declaration itself (carries the raw typeStr
 *                     and the existing carrier classification)
 * @param hit          the literal token matched — either an FQN (with dots)
 *                     or a bare short name (no dots) when the simple name's
 *                     entire FQN set in the registry is deprecated
 * @param canonicalFqn the deprecated FQN the hit resolves to (equals
 *                     {@code hit} when {@code hit} is an FQN; otherwise the
 *                     canonical FQN picked from the unambiguous-short-name
 *                     map)
 * @param replacement  spec FQN with the same simple name, or empty if no
 *                     replacement was found via the same-simple-name or
 *                     javadoc-fallback heuristics
 */
public record InputDeprecatedRef(
        String classFqn,
        InputDecl decl,
        String hit,
        String canonicalFqn,
        String replacement) {
}

package io.github.beast3.migration;

import java.nio.file.Path;

/**
 * Per-XML record produced by {@link XmlScanner}.
 *
 * <p>The record covers two flavours of BEAST XML:
 * <ul>
 *   <li><b>Example XMLs</b> — runtime inputs. Migration is judged by
 *       {@link #hasV28} and {@link #hasSpecNamespace} on the
 *       {@code <beast>} root.</li>
 *   <li><b>FxTemplates</b> — BEAUti subtemplates that get merged into
 *       the document. They typically keep {@code version='2.0'} per
 *       beast3's own convention; migration is instead judged by whether
 *       the body uses {@code beast.base.spec.*} types and whether any
 *       parameter declarations still use legacy {@code parameter.RealParameter}-style
 *       attributes.</li>
 * </ul>
 */
public record XmlRecord(
        Path file,
        Path relativeTo,
        boolean hasV28,
        boolean hasSpecNamespace,
        boolean inLegacyDir,
        boolean isFxTemplate,
        boolean fxBodyHasSpec,
        boolean fxHasLegacyParam) {

    public Path relPath() {
        return relativeTo.relativize(file);
    }
}

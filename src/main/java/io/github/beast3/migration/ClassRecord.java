package io.github.beast3.migration;

import java.nio.file.Path;
import java.util.List;

import io.github.beast3.migration.Report.Kind;
import io.github.beast3.migration.Report.Status;

/**
 * Per-class record produced by {@link JavaScanner}.
 *
 * <p>Carries both the <b>raw</b> signals from the file alone
 * ({@link #ownKind}, {@link #ownHasSpec}, {@link #ownHasLegacy},
 * {@link #primaryExtendsFqn}) and <b>effective</b> values
 * ({@link #kind}, {@link #status}) that account for inheritance from
 * an in-package parent. The effective fields are populated by
 * {@code JavaScanner.resolveAndTally} after every package's classes
 * have been collected.</p>
 *
 * <p>{@link #legacyEvidence} / {@link #specEvidence} hold imports and
 * extends/implements tokens that triggered the classification — useful
 * when a punch-list entry needs to explain <em>why</em> a class is
 * still legacy.</p>
 */
public final class ClassRecord {

    public final Path file;
    public final String packageName;
    public final String simpleName;
    public final String extendsClause;
    public final String implementsClause;
    private List<String> legacyEvidence;   // populated by JavaScanner.resolveAndTally
    public final List<String> specEvidence;

    /** Kind classified from this file's own {@code extends}/{@code implements}, ignoring inheritance. */
    public final Kind ownKind;
    /** Whether this file directly imports/extends a {@code beast.base.spec.*} type. */
    public final boolean ownHasSpec;
    /** Whether this file directly imports/extends a legacy parameter or {@code Prior} type. */
    public final boolean ownHasLegacy;
    /**
     * Best-effort FQN of the class's primary {@code extends} target, resolved
     * via the file's import map. Falls back to {@code currentPackage + "." + simpleName}
     * for unqualified names with no import — that's how in-package parents are
     * found during chain resolution.
     */
    public final String primaryExtendsFqn;

    /** True if the source carries a {@code @Deprecated} annotation on the
     *  primary class declaration — beast3's authoritative legacy marker. */
    public final boolean isDeprecated;

    /**
     * True if the class directly extends/implements
     * {@code beast.base.inference.StateNodeInitialiser}. Initialisers write
     * into the state nodes they're handed, so the concrete-spec-param input
     * rule (see {@link #ruleViolatingInputs}) treats them like Operators —
     * they're allowed to declare {@code Input<RealScalarParam>} etc.
     */
    public final boolean isStateNodeInitialiser;

    /**
     * The {@code @deprecated} javadoc text immediately preceding the class
     * declaration, if any. Empty otherwise. Used by
     * {@link JavaScanner#deriveSpecReplacements} to find a replacement when
     * the same-simple-name heuristic misses (e.g. {@code BactrianUpDownOperator}
     * has a javadoc {@code @deprecated Use ... UpDownOperator instead.}).
     */
    public final String deprecationMessage;

    /** All {@code Input<X>} field declarations seen in the file. */
    public final List<InputDecl> inputs;

    private Kind kind;
    private Status status;

    public ClassRecord(
            Path file,
            String packageName,
            String simpleName,
            String extendsClause,
            String implementsClause,
            List<String> legacyEvidence,
            List<String> specEvidence,
            Kind ownKind,
            boolean ownHasSpec,
            boolean ownHasLegacy,
            String primaryExtendsFqn,
            boolean isDeprecated,
            String deprecationMessage,
            boolean isStateNodeInitialiser,
            List<InputDecl> inputs) {
        this.file = file;
        this.packageName = packageName;
        this.simpleName = simpleName;
        this.extendsClause = extendsClause;
        this.implementsClause = implementsClause;
        this.legacyEvidence = legacyEvidence;
        this.specEvidence = specEvidence;
        this.ownKind = ownKind;
        this.ownHasSpec = ownHasSpec;
        this.ownHasLegacy = ownHasLegacy;
        this.primaryExtendsFqn = primaryExtendsFqn;
        this.isDeprecated = isDeprecated;
        this.deprecationMessage = deprecationMessage == null ? "" : deprecationMessage;
        this.isStateNodeInitialiser = isStateNodeInitialiser;
        this.inputs = inputs;
        this.kind = ownKind;
        this.status = toStatus(ownHasSpec, ownHasLegacy);
    }

    public Kind kind() { return kind; }
    public Status status() { return status; }
    public List<String> legacyEvidence() { return legacyEvidence; }
    public List<String> specEvidence() { return specEvidence; }

    void setEffective(Kind kind, Status status) {
        this.kind = kind;
        this.status = status;
    }

    void setLegacyEvidence(List<String> evidence) {
        this.legacyEvidence = evidence;
    }

    public String fqn() {
        return packageName.isBlank() ? simpleName : packageName + "." + simpleName;
    }

    /**
     * Inputs that violate the "concrete spec params only in Operators and
     * Loggers" rule given the class's effective {@link #kind}. Operators
     * need write access; Loggers identify their target by ID, which the
     * pure spec interfaces (RealScalar, RealVector, …) deliberately do not
     * expose. For both, only LEGACY inputs are violations. For Distributions,
     * CalcNodes, etc. CONCRETE_SPEC and LEGACY are both violations.
     */
    public List<InputDecl> ruleViolatingInputs() {
        java.util.List<InputDecl> out = new java.util.ArrayList<>();
        for (InputDecl d : inputs) {
            switch (d.carrier()) {
                case LEGACY -> out.add(d);
                case CONCRETE_SPEC -> {
                    // Concrete spec params are only barred on model-role
                    // kinds (Distribution, CalcNode, StateNode, Parameter)
                    // that are supposed to be read-only holders. Operators
                    // and Loggers are exempt by design (write / need
                    // getID()). StateNodeInitialiser is exempt because it
                    // writes into the state nodes it's handed. Kind.OTHER
                    // covers utility / configuration / file-iterator
                    // classes that may legitimately write to their input
                    // parameters and shouldn't be lumped in with model
                    // components.
                    if (kind != Kind.OPERATOR && kind != Kind.LOGGER
                            && kind != Kind.OTHER
                            && !isStateNodeInitialiser) {
                        out.add(d);
                    }
                }
                default -> { /* INTERFACE and OTHER are fine in any holder. */ }
            }
        }
        return out;
    }

    /**
     * Inputs whose declared type references an {@code @Deprecated} class —
     * either by FQN ({@code Input<beast.base.inference.distribution.ParametricDistribution>})
     * or by an unambiguously deprecated short name
     * ({@code Input<ParametricDistribution>}). A class with any such Input
     * is blocking migration: downstream XMLs cannot supply a non-deprecated
     * value to it without first migrating this class. This is a separate
     * concern from the carrier rule above (which is about
     * RealParameter / Function / Concrete spec params); the two checks may
     * overlap on the same Input.
     */
    public List<InputDeprecatedRef> inputsReferencingDeprecatedTypes(
            java.util.Set<String> deprecatedFqns,
            java.util.Map<String, String> deprecatedShortNames,
            java.util.Map<String, String> specReplacements) {
        java.util.List<InputDeprecatedRef> out = new java.util.ArrayList<>();
        for (InputDecl d : inputs) {
            String hit = findDeprecatedReference(d.typeStr(), deprecatedFqns, deprecatedShortNames);
            if (hit == null) continue;
            String canonical = hit.contains(".") ? hit : deprecatedShortNames.get(hit);
            // Loggers legitimately accept `Function` as the "thing-that-
            // exposes-array-values" abstraction — that's the role Function
            // was designed for, and spec has no single-interface replacement
            // (RealVector / IntVector / BoolVector / RealScalar / … don't
            // share an API-equivalent ancestor). Exempt only on Kind.LOGGER
            // (the substantive role), not on every class that mixes in
            // Loggable — most parameters / calcnodes do that to self-log.
            if (kind == Kind.LOGGER
                    && "beast.base.core.Function".equals(canonical)) {
                continue;
            }
            String replacement = specReplacements.getOrDefault(canonical, "");
            out.add(new InputDeprecatedRef(fqn(), d, hit, canonical, replacement));
        }
        return out;
    }

    /**
     * Scan {@code typeStr} for the first identifier that resolves to a
     * deprecated class. Returns the matching token (an FQN or a short name)
     * or null.
     */
    private static String findDeprecatedReference(
            String typeStr,
            java.util.Set<String> deprecatedFqns,
            java.util.Map<String, String> deprecatedShortNames) {
        if (typeStr == null || typeStr.isBlank()) return null;
        // Pass 1: full FQNs (lowercase package + at least one segment ending
        // in a capitalised simple name).
        java.util.regex.Matcher fqn = java.util.regex.Pattern
                .compile("\\b[a-z][a-zA-Z0-9_]*(?:\\.[a-zA-Z_][\\w]*)+\\b")
                .matcher(typeStr);
        while (fqn.find()) {
            String token = fqn.group();
            if (deprecatedFqns.contains(token)) return token;
        }
        // Pass 2: bare capitalised simple names matching the unambiguous map.
        java.util.regex.Matcher simple = java.util.regex.Pattern
                .compile("\\b[A-Z][A-Za-z0-9_]+\\b").matcher(typeStr);
        while (simple.find()) {
            String token = simple.group();
            if (deprecatedShortNames.containsKey(token)) return token;
        }
        return null;
    }

    static Status toStatus(boolean hasSpec, boolean hasLegacy) {
        if (hasSpec && hasLegacy) return Status.MIXED;
        if (hasSpec) return Status.SPEC;
        if (hasLegacy) return Status.LEGACY;
        return Status.NEUTRAL;
    }
}

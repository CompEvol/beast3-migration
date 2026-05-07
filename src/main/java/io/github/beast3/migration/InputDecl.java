package io.github.beast3.migration;

/**
 * One {@code Input<X>} field declaration extracted from a Java source.
 * {@link #typeStr} is the raw text inside the {@code <...>}; {@link #carrier}
 * tells us which migration bucket it falls into.
 */
public record InputDecl(String typeStr, Carrier carrier) {

    public enum Carrier {
        /** Spec interface — read-only, polymorphic. RealScalar, RealVector, IntScalar, … */
        INTERFACE,
        /** Concrete spec param — required when the holder mutates the parameter. RealScalarParam, … */
        CONCRETE_SPEC,
        /** Legacy beast.base parameter or {@code Function}. */
        LEGACY,
        /** Anything else — Tree, Alignment, primitives, custom types. */
        OTHER
    }

    /** Simple-name of the head type (strips generics, package qualifier). */
    public String headSimpleName() {
        String s = typeStr;
        int lt = s.indexOf('<');
        if (lt >= 0) s = s.substring(0, lt);
        int comma = s.indexOf(',');
        if (comma >= 0) s = s.substring(0, comma);
        s = s.trim();
        // strip wildcards / bounds
        if (s.startsWith("?")) {
            int sp = s.lastIndexOf(' ');
            if (sp > 0) s = s.substring(sp + 1).trim();
        }
        int dot = s.lastIndexOf('.');
        return dot >= 0 ? s.substring(dot + 1) : s;
    }
}

---
name: beast3-java-cleanup
description: Cross-cutting Java modernisation tasks required for BEAST3 compatibility — remove deprecated finalize() overrides and unbox Double[]/Integer[] to primitive arrays
metadata:
  type: skill
---

You are applying cross-cutting Java modernisation changes that are required for BEAST3 / Java 17+
compatibility. These rules apply to any file regardless of which BEAST domain it belongs to.

---

## Rules

### R1 — Comment out deprecated `finalize()` overrides

Java 9+ deprecates `Object.finalize()` and Java 17 issues strong warnings; it is removed in
Java 21. Any `@Override` of `finalize()` must be commented out.

**Concrete implementation:**
```java
// BEAST2 — comment out:
@Override
public void finalize() throws Throwable {
    field1 = null;
    field2 = 0;
    // ...
}

// BEAST3 — leave as commented block:
//    @Override
//    public void finalize() throws Throwable {
//        field1 = null;
//        field2 = 0;
//        // ...
//    }
```

**Abstract declaration:**
```java
// BEAST2 — comment out:
@Override
public abstract void finalize() throws Throwable;

// BEAST3 — leave as commented lines:
//    @Override
//    public abstract void finalize() throws Throwable;
```

Rules:
- Comment out the `@Override` annotation line, the method signature line, and every line of the
  body (if present), including the closing `}`.
- Do not delete — leave the commented block in place for reviewers.
- Preserve indentation when adding `//` prefixes.
- The `throws Throwable` clause must also be commented.

### R2 — Unbox `Double[]` → `double[]`

Where an array is declared as `Double[]` (boxed) and all values come from primitive-returning
sources (numeric literals, `Randomizer.nextDouble()`, arithmetic on `double` values), change the
declaration and its initializer to the primitive `double[]`.

```java
// BEAST2
Double[] f = new Double[]{0.25, 0.25, 0.25, 0.25};
Double[] rates = new Double[]{Randomizer.nextDouble(), Randomizer.nextDouble()};

// BEAST3
double[] f = new double[]{0.25, 0.25, 0.25, 0.25};
double[] rates = new double[]{Randomizer.nextDouble(), Randomizer.nextDouble()};
```

This is a **prerequisite** when the array is subsequently passed to `new SimplexParam(double[])` or
`new RealVectorParam<>(double[], domain)` — those constructors require primitive arrays.

### R3 — Unbox `Integer[]` → `int[]`

Same rule as R2 applied to integer arrays:

```java
// BEAST2
Integer[] counts = new Integer[]{1, 2, 3};

// BEAST3
int[] counts = new int[]{1, 2, 3};
```

### R4 — Collapse string-building for parameter construction

A common BEAST2 pattern builds a space-separated string then passes it to `RealParameter`:

```java
// BEAST2
Double[] f = new Double[]{d1, d2, d3, d4};
String pi = f[0] + " " + f[1] + " " + f[2] + " " + f[3];
RealParameter f2 = new RealParameter(pi);

// BEAST3 (after R2 unboxing + java-migration/parameters.md migration)
double[] f = new double[]{d1, d2, d3, d4};
Simplex f2 = new SimplexParam(f);
```

Remove the intermediate `String` variable entirely. If the array was declared as `Double[]`, also
apply R2.

---

## What NOT to change

- Do not remove `throws Throwable` from methods other than `finalize()`.
- Do not unbox `Double[]` to `double[]` when the array is passed to a method that explicitly
  requires `Double[]` (check the callee's signature first).
- Do not change `Double` (singular boxed scalar) fields or locals — only arrays are in scope here.
- Do not reformat or rename anything beyond what the rules above require.

---

## XML migration

The rules in this file are Java-only. XML files do not contain `Double[]`/`Integer[]` declarations
or `finalize()` overrides — no XML-level changes are needed for `java-cleanup.md` rules.
For XML class-reference migration see **`xml-migration/example-xmls.md`** and **`xml-migration/fxtemplates.md`**.

---

## Log (Mode 2b — Changes field)

- `finalize() removed: N` — count of `finalize()` blocks commented out
- `Double[] → double[]: N` — count of boxed double array unboxings
- `Integer[] → int[]: N` — count of boxed integer array unboxings

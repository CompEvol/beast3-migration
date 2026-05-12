---
name: beast3-parameters
description: Migrate BEAST2 parameter classes (RealParameter, IntegerParameter, BooleanParameter) to BEAST3 typed spec params, domain qualifiers, and enforce the Input concreteness rule
metadata:
  type: skill
---

You are migrating BEAST2 parameter usage to the BEAST3 typed parameter system. This is the most
frequent and most nuanced migration. Apply all rules below; make minimal, surgical changes only.

---

## Background

BEAST3 replaces the monolithic `RealParameter` / `IntegerParameter` / `BooleanParameter` with a
family of strongly-typed classes. The type is split into two orthogonal axes:

- **Shape**: `Scalar` (single value) vs `Vector` (fixed-length array) vs `Simplex` (sums-to-1 array)
- **Domain**: what values are valid — `PositiveReal`, `NonNegativeReal`, `Real`, `UnitInterval`,
  `PositiveInt`, `NonNegativeInt`, `Int`, `Bool`

There is also a strict **Input concreteness rule** — see R5.

---

## Rules

### R1 — Remove legacy parameter imports

Remove all of:
```java
import beast.base.inference.parameter.RealParameter;
import beast.base.inference.parameter.IntegerParameter;
import beast.base.inference.parameter.BooleanParameter;
import beast.base.evolution.tree.TreeDistribution;   // if used only as Function
import beast.base.inference.util.InputUtil;           // check usage first
```

### R2 — Add BEAST3 parameter imports (only those actually used)

```java
// domain qualifiers
import beast.base.spec.domain.PositiveReal;
import beast.base.spec.domain.NonNegativeReal;
import beast.base.spec.domain.Real;
import beast.base.spec.domain.UnitInterval;
import beast.base.spec.domain.PositiveInt;
import beast.base.spec.domain.NonNegativeInt;
import beast.base.spec.domain.Int;
import beast.base.spec.domain.Bool;

// concrete param classes (for Operators and construction in tests)
import beast.base.spec.inference.parameter.RealScalarParam;
import beast.base.spec.inference.parameter.RealVectorParam;
import beast.base.spec.inference.parameter.SimplexParam;
import beast.base.spec.inference.parameter.IntScalarParam;
import beast.base.spec.inference.parameter.IntVectorParam;
import beast.base.spec.inference.parameter.IntSimplexParam;
import beast.base.spec.inference.parameter.BoolScalarParam;
import beast.base.spec.inference.parameter.BoolVectorParam;

// interface types (for Input declarations in non-Operator classes)
import beast.base.spec.type.RealScalar;
import beast.base.spec.type.RealVector;
import beast.base.spec.type.Simplex;
import beast.base.spec.type.IntScalar;
import beast.base.spec.type.IntVector;
import beast.base.spec.type.BoolScalar;
import beast.base.spec.type.BoolVector;
```

### R3 — Map BEAST2 construction to BEAST3

#### Real parameters

| Value shape | Domain | BEAST2 | BEAST3 |
|---|---|---|---|
| Single scalar | positive (rate, kappa, shape…) | `new RealParameter("1.0")` | `new RealScalarParam<>(1.0, PositiveReal.INSTANCE)` |
| Single scalar | non-negative | `new RealParameter("0.0")` | `new RealScalarParam<>(0.0, NonNegativeReal.INSTANCE)` |
| Single scalar | unrestricted | `new RealParameter("0.0")` | `new RealScalarParam<>(0.0, Real.INSTANCE)` |
| Single scalar | [0,1] | `new RealParameter("0.5")` | `new RealScalarParam<>(0.5, UnitInterval.INSTANCE)` |
| Fixed-length vector | positive | `new RealParameter("1.0 2.0 3.0")` | `new RealVectorParam<>(new double[]{1.0,2.0,3.0}, PositiveReal.INSTANCE)` |
| Frequencies / simplex | sums to 1 | `new RealParameter("0.25 0.25 0.25 0.25")` | `new SimplexParam(new double[]{0.25,0.25,0.25,0.25})` |

**Disambiguation**: a `RealParameter` is a simplex/`SimplexParam` when it is passed to a
`"frequencies"` input or otherwise constrained to sum to 1. All other cases are vectors or scalars.

When a space-separated string was built from a `double[]`, collapse the string-building code and
pass the array directly to the BEAST3 constructor (see also java-cleanup.md R4 for `Double[]` unboxing).

#### Int parameters

| Value shape | Domain | BEAST2 | BEAST3 |
|---|---|---|---|
| Single scalar | positive int | `new IntegerParameter("2")` | `new IntScalarParam<>(2, PositiveInt.INSTANCE)` |
| Single scalar | non-negative int | `new IntegerParameter("0")` | `new IntScalarParam<>(0, NonNegativeInt.INSTANCE)` |
| Fixed-length vector | any int | `new IntegerParameter("1 2 3")` | `new IntVectorParam<>(new int[]{1,2,3}, Int.INSTANCE)` |

#### Boolean parameters

| BEAST2 | BEAST3 |
|---|---|
| `new BooleanParameter("true")` | `new BoolScalarParam(true)` |
| `new BooleanParameter("true false true")` | `new BoolVectorParam(new boolean[]{true,false,true})` |

### R4 — Update declared types in fields and locals

Wherever a field or local variable is declared as `RealParameter`, `IntegerParameter`, or
`BooleanParameter`, change it to the appropriate BEAST3 type:

- If the holder is an **Operator** (writes the param): use the concrete param class
  (`RealScalarParam<PositiveReal>`, `RealVectorParam<?>`, etc.)
- If the holder is a **Distribution / CalcNode / Logger / any read-only class**: use the interface
  type (`RealScalar`, `RealVector`, `Simplex`, `IntScalar`, `IntVector`, `BoolScalar`, `BoolVector`)

### R5 — Input concreteness rule (critical)

This is an architectural rule, not just an import change.

**Operators** (classes that extend `Operator`) MUST declare `Input` with the concrete param type,
because they need write access:
```java
// Operator — correct
public Input<RealScalarParam<PositiveReal>> kappaInput = new Input<>(...);
```

**All other classes** (Distributions, CalcNodes, Loggers, Likelihood classes, etc.) MUST declare
`Input` with the interface type, so adapters and transforms can be substituted:
```java
// Distribution / Logger / CalcNode — correct
public Input<RealScalar> kappaInput = new Input<>(...);

// WRONG in a non-Operator — do not write:
// public Input<RealScalarParam<PositiveReal>> kappaInput = new Input<>(...);
// public Input<RealParameter> kappaInput = new Input<>(...);   // legacy, also wrong
// public Input<Function> kappaInput = new Input<>(...);        // legacy, also wrong
```

When fixing a "declared too concretely" violation in a non-Operator, change only the `Input<>`
generic; the concrete param can still be used at construction time in tests.

### R6 — `initByName` call sites

When test code or `initAndValidate` calls `initByName(...)`, update the value arguments to use
BEAST3 construction. Example from the observed diff:

```java
// BEAST2
gtr.initByName("rateAC", new RealParameter(rates[0]+""), ...);

// BEAST3
gtr.initByName("rateAC", new RealScalarParam<>(rates[0], PositiveReal.INSTANCE), ...);
```

---

## Domain selection guide

When the original `RealParameter` has `lower` / `upper` set, map as follows:

| BEAST2 bounds | BEAST3 domain |
|---|---|
| `lower="0"` (no upper) | `PositiveReal` (if strictly > 0) or `NonNegativeReal` |
| `lower="0" upper="1"` | `UnitInterval` |
| No bounds | `Real` |
| `lower="0"` on an integer | `PositiveInt` or `NonNegativeInt` |

When in doubt (no explicit bounds visible), examine how the parameter is used — rates and shape
parameters are typically `PositiveReal`.

---

## Edge Cases

- **`CompoundRealScalarParam`**: replaces `CompoundParameter` for grouping scalars — only needed
  when the BEAST2 code explicitly uses `CompoundParameter`.
- **`VectorElement`**: replaces `parameter.Parameter.Bound` or indexing into a `RealParameter` by
  position — rarely needed, flag with `// TODO: check VectorElement usage`.
- **Partial migration**: if some `Input` declarations in the file are already on the interface type,
  do not regress them to concrete types.
- **`Function` interface**: legacy `beast.base.evolution.tree.Tree` or
  `beast.base.inference.Function` passed as an input — replace with the appropriate interface type
  (`RealScalar`, `RealVector`, etc.) once the domain is clear.

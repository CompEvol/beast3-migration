---
name: beast3-operators
description: Migrate BEAST2 operator imports to BEAST3 spec equivalents and enforce the Operator Input concreteness rule
metadata:
  type: skill
---

You are migrating BEAST2 operator classes to the BEAST3 spec API. Operators are a special case
because they are the ONLY class type that must hold **concrete** parameter types in their `Input`
declarations. Apply all rules below.

---

## Rules

### R1 — Import prefix: add `.spec.`

**Inference operators** — pattern: `beast.base.inference.operator.X` → `beast.base.spec.inference.operator.X`

`ScaleOperator` · `DeltaExchangeOperator` · `BitFlipOperator` · `RealRandomWalkOperator` · `IntRandomWalkOperator` · `SwapOperator` · `SampleOffValues` · `Transform` · `AdaptableOperatorSampler` · `AdaptableVarianceMultivariateNormalOperator`

**Uniform operators** — pattern: `beast.base.inference.operator.uniform.X` → `beast.base.spec.inference.operator.uniform.X`

`IntScalarUniformOperator` · `IntUniformOperator` · `ScalarIntervalOperator` · `VectorIntervalOperator` · `IntervalOperator`

**Evolution operators** — pattern: `beast.base.evolution.operator.X` → `beast.base.spec.evolution.operator.X`

`UpDownOperator` · `IntervalScaleOperator` · `AdaptableOperatorSampler` · `AdaptableVarianceMultivariateNormalOperator`

**`beast.base.evolution.operator.ScaleOperator` split (special case):**

BEAST2's `ScaleOperator` handled both parameters and trees via a single class. In BEAST3 it is
split into two spec classes depending on what it operates on:

| BEAST2 `ScaleOperator` usage | BEAST3 replacement |
|---|---|
| `parameter=` attribute — scales a `Scalable` parameter | `beast.base.spec.inference.operator.ScaleOperator` |
| `tree=` attribute — scales a tree | `beast.base.spec.evolution.operator.ScaleTreeOperator` |

Update the Java import to match the chosen replacement. For XML `spec=` attribute changes, see `xml-migration/example-xmls.md`.

**No spec equivalent — do not convert:**

`Exchange` · `WilsonBalding` · `SubtreeSlide` · `Uniform` (tree operator)

Leave these unchanged in both Java and XML. They have no `beast.base.spec` counterpart.
Note: in XML, `Uniform` is ambiguous with the spec distribution — see `xml-migration/example-xmls.md` for the required full class path.

### R2 — Operator Input concreteness rule (critical)

Operators are the ONLY class type that must use **concrete** parameter types in `Input` declarations,
because they require write access to modify the parameter during MCMC proposals.

```java
// CORRECT in an Operator
public Input<RealScalarParam<PositiveReal>> kappaInput = new Input<>(...);
public Input<RealVectorParam<PositiveReal>> ratesInput = new Input<>(...);
public Input<SimplexParam> freqsInput = new Input<>(...);
public Input<IntScalarParam<NonNegativeInt>> countInput = new Input<>(...);
public Input<BoolVectorParam> indicatorsInput = new Input<>(...);

// WRONG even in an Operator
// public Input<RealParameter> kappaInput = new Input<>(...);   // legacy
// public Input<RealScalar> kappaInput = new Input<>(...);      // interface — Operator cannot write
```

When migrating an Operator's `Input` declarations:
1. Identify the value type (scalar / vector / simplex)
2. Identify the domain from the parameter's intended range
3. Use the concrete param class with the domain type parameter

### R3 — `extends` clause for custom operators

If the file defines a custom operator that extends a BEAST2 operator base class, update the
`extends` clause to the BEAST3 spec equivalent (apply R1 to the extended class name).

### R4 — `getParameter()` / accessor updates

Some BEAST2 operators call `parameter.getValue()`, `parameter.setValue(i, v)`, or
`parameter.getDimension()`. These method signatures are preserved in the BEAST3 param classes —
no change needed unless a compilation error occurs.

---

## Edge Cases

- **`AbstractScale`**: now at `beast.base.spec.inference.operator.AbstractScale` — if the file
  extends this, update the import.
- **`CompoundRealScalarParamHelper`**: available at
  `beast.base.spec.inference.operator.CompoundRealScalarParamHelper` — import if used.
- **Unknown operator class**: if a class under `beast.base.inference.operator` or
  `beast.base.evolution.operator` is not in the tables above, check the BEAST3 source first. If no
  spec counterpart exists, leave unchanged and flag with `// TODO: no beast3 spec class found for <ClassName>`.
- **Wildcard imports**: expand to explicit imports, then apply R1.

---

## XML migration

For `spec=` attribute changes in example XMLs and FxTemplates, see **`xml-migration/example-xmls.md`** (Step 3 operator patterns) and **`xml-migration/fxtemplates.md`** (Step 3). Key XML-specific rules covered there:
- `ScaleOperator` split: `parameter=` → spec inference; `tree=` → `ScaleTreeOperator`
- `Uniform` tree operator: must use full path `beast.base.evolution.operator.Uniform`
- `Exchange`, `WilsonBalding`, `SubtreeSlide`: leave unchanged

---

## Log (Mode 2b — Changes field)

- Classes renamed to `.spec.`: list each by group (inference / uniform / evolution)
- `Input declarations made concrete: N` — count of Input<interface> → Input<ConcreteParam> changes (Operator concreteness rule)
- `Warnings — non-deprecated BEAST2 classes migrated: N` — list each class name (or "none")

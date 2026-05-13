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

#### Inference operators

| BEAST2 import | BEAST3 import |
|---|---|
| `beast.base.inference.operator.ScaleOperator` | `beast.base.spec.inference.operator.ScaleOperator` |
| `beast.base.inference.operator.DeltaExchangeOperator` | `beast.base.spec.inference.operator.DeltaExchangeOperator` |
| `beast.base.inference.operator.BitFlipOperator` | `beast.base.spec.inference.operator.BitFlipOperator` |
| `beast.base.inference.operator.RealRandomWalkOperator` | `beast.base.spec.inference.operator.RealRandomWalkOperator` |
| `beast.base.inference.operator.IntRandomWalkOperator` | `beast.base.spec.inference.operator.IntRandomWalkOperator` |
| `beast.base.inference.operator.SwapOperator` | `beast.base.spec.inference.operator.SwapOperator` |
| `beast.base.inference.operator.SampleOffValues` | `beast.base.spec.inference.operator.SampleOffValues` |
| `beast.base.inference.operator.Transform` | `beast.base.spec.inference.operator.Transform` |
| `beast.base.inference.operator.AdaptableOperatorSampler` | `beast.base.spec.inference.operator.AdaptableOperatorSampler` |
| `beast.base.inference.operator.AdaptableVarianceMultivariateNormalOperator` | `beast.base.spec.inference.operator.AdaptableVarianceMultivariateNormalOperator` |

#### Uniform operators (inference)

| BEAST2 import | BEAST3 import |
|---|---|
| `beast.base.inference.operator.uniform.IntScalarUniformOperator` | `beast.base.spec.inference.operator.uniform.IntScalarUniformOperator` |
| `beast.base.inference.operator.uniform.IntUniformOperator` | `beast.base.spec.inference.operator.uniform.IntUniformOperator` |
| `beast.base.inference.operator.uniform.ScalarIntervalOperator` | `beast.base.spec.inference.operator.uniform.ScalarIntervalOperator` |
| `beast.base.inference.operator.uniform.VectorIntervalOperator` | `beast.base.spec.inference.operator.uniform.VectorIntervalOperator` |
| `beast.base.inference.operator.uniform.IntervalOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

#### Evolution operators

| BEAST2 import | BEAST3 import |
|---|---|
| `beast.base.evolution.operator.ScaleTreeOperator` | `beast.base.spec.evolution.operator.ScaleTreeOperator` |
| `beast.base.evolution.operator.UpDownOperator` | `beast.base.spec.evolution.operator.UpDownOperator` |
| `beast.base.evolution.operator.IntervalScaleOperator` | `beast.base.spec.evolution.operator.IntervalScaleOperator` |
| `beast.base.evolution.operator.AdaptableOperatorSampler` | `beast.base.spec.evolution.operator.AdaptableOperatorSampler` |
| `beast.base.evolution.operator.AdaptableVarianceMultivariateNormalOperator` | `beast.base.spec.evolution.operator.AdaptableVarianceMultivariateNormalOperator` |

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

## Log (Mode 2b — Changes field)

- Classes renamed to `.spec.`: list each by group (inference / uniform / evolution)
- `Input declarations made concrete: N` — count of Input<interface> → Input<ConcreteParam> changes (Operator concreteness rule)
- `Warnings — non-deprecated BEAST2 classes migrated: N` — list each class name (or "none")

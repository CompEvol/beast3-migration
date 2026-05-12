---
name: beast3-distributions
description: Migrate BEAST2 prior distribution imports to BEAST3 spec equivalents
metadata:
  type: skill
---

You are migrating BEAST2 parametric distribution / prior classes to the BEAST3 spec API. Apply all
rules below; make minimal, surgical changes only.

---

## Rules

### R1 — Import prefix: add `.spec.`

| BEAST2 import | BEAST3 import |
|---|---|
| `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.Prior` |
| `beast.base.inference.distribution.LogNormal` | `beast.base.spec.inference.distribution.LogNormal` |
| `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `beast.base.inference.distribution.GammaMean` | `beast.base.spec.inference.distribution.GammaMean` |
| `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `beast.base.inference.distribution.Dirichlet` | `beast.base.spec.inference.distribution.Dirichlet` |
| `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `beast.base.inference.distribution.LogUniform` | `beast.base.spec.inference.distribution.LogUniform` |
| `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `beast.base.inference.distribution.Laplace` | `beast.base.spec.inference.distribution.Laplace` |
| `beast.base.inference.distribution.Cauchy` | `beast.base.spec.inference.distribution.Cauchy` |
| `beast.base.inference.distribution.ChiSquare` | `beast.base.spec.inference.distribution.ChiSquare` |
| `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |
| `beast.base.inference.distribution.Bernoulli` | `beast.base.spec.inference.distribution.Bernoulli` |
| `beast.base.inference.distribution.IntUniform` | `beast.base.spec.inference.distribution.IntUniform` |
| `beast.base.inference.distribution.TruncatedReal` | `beast.base.spec.inference.distribution.TruncatedReal` |
| `beast.base.inference.distribution.TruncatedInt` | `beast.base.spec.inference.distribution.TruncatedInt` |
| `beast.base.inference.distribution.OffsetReal` | `beast.base.spec.inference.distribution.OffsetReal` |
| `beast.base.inference.distribution.OffsetInt` | `beast.base.spec.inference.distribution.OffsetInt` |
| `beast.base.inference.distribution.IID` | `beast.base.spec.inference.distribution.IID` |
| `beast.base.inference.distribution.MarkovChainDistribution` | `beast.base.spec.inference.distribution.MarkovChainDistribution` |
| `beast.base.inference.distribution.ScalarDistribution` | `beast.base.spec.inference.distribution.ScalarDistribution` |
| `beast.base.inference.distribution.TensorDistribution` | `beast.base.spec.inference.distribution.TensorDistribution` |

### R2 — Distribution Input declarations follow the non-Operator rule

Distribution classes are read-only holders of parameters — they must use **interface** types in
`Input` declarations, not concrete param classes:

```java
// CORRECT in a Distribution
public Input<RealScalar> meanInput = new Input<>(...);
public Input<RealScalar> sigmaInput = new Input<>(...);

// WRONG in a Distribution
// public Input<RealScalarParam<PositiveReal>> meanInput = new Input<>(...);  // too concrete
// public Input<RealParameter> meanInput = new Input<>(...);                   // legacy
// public Input<Function> meanInput = new Input<>(...);                        // legacy
```

See `parameters.md` R5 for the full Input concreteness rule.

### R3 — Distribution parameter arguments in `initByName`

When constructing distributions in test code or `initAndValidate`, pass BEAST3 typed params. Common
examples:

```java
// LogNormal — mean and sigma are positive reals
logNormal.initByName(
    "mean", new RealScalarParam<>(1.0, PositiveReal.INSTANCE),
    "sigma", new RealScalarParam<>(0.5, PositiveReal.INSTANCE),
    "meanInRealSpace", true);

// Gamma — alpha and beta are positive reals
gamma.initByName(
    "alpha", new RealScalarParam<>(2.0, PositiveReal.INSTANCE),
    "beta",  new RealScalarParam<>(1.0, PositiveReal.INSTANCE));

// Dirichlet — alpha is a vector of positive reals
dirichlet.initByName(
    "alpha", new RealVectorParam<>(new double[]{1.0,1.0,1.0}, PositiveReal.INSTANCE));
```

See `parameters.md` for the full parameter migration rules.

### R4 — `ParametricDistribution` base class

If a custom class `extends ParametricDistribution`, check whether `ParametricDistribution` has a
BEAST3 spec twin. If not, flag with `// TODO: ParametricDistribution has no beast3 spec twin` and
leave unchanged — this is a known migration blocker (reported in the Mascot audit as "legacy base").

---

## Edge Cases

- **`Prior` wrapping a distribution**: `Prior.initByName("distr", dist, "x", param)` — the wrapped
  distribution must also be migrated. Migrate the distribution first, then the `Prior`.
- **Unknown distribution class**: if a class under `beast.base.inference.distribution` is not in the
  table above, check the BEAST3 source before rewriting. If no spec counterpart exists, leave
  unchanged and flag with `// TODO: no beast3 spec class found`.
- **Wildcard imports**: expand to explicit imports, then apply R1.
- **Partial migration**: if the file already imports any `beast.base.spec.inference.distribution.*`
  classes, do not duplicate them.

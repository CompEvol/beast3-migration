---
name: beast3-distributions
description: Migrate BEAST2 prior distribution imports to BEAST3 spec equivalents
metadata:
  type: skill
---

You are migrating BEAST2 parametric distribution / prior classes to the BEAST3 spec API. Apply all
rules below.

---

## Rules

### R1 — Import prefix: add `.spec.`

Pattern (U2): `beast.base.inference.distribution.X` → `beast.base.spec.inference.distribution.X`

Confirmed spec counterparts:
`LogNormal` · `Normal` · `Gamma` · `GammaMean` · `Exponential` · `Beta` · `Dirichlet` · `Uniform` · `LogUniform` · `InverseGamma` · `Laplace` · `Cauchy` · `ChiSquare` · `Poisson` · `Bernoulli` · `IntUniform` · `TruncatedReal` · `TruncatedInt` · `OffsetReal` · `OffsetInt` · `IID` · `MarkovChainDistribution` · `ScalarDistribution` · `TensorDistribution`

**`Prior` is removed in BEAST3** — do not import or instantiate `Prior`; the class does not exist
in the spec hierarchy. In Java, replace any `Prior.initByName("distr", dist, "x", param)` call
or `extends Prior` with a direct spec distribution that takes a `param` input. For XML
`<distribution spec="Prior">` conversion, see `xml-migration/example-xmls.md`.

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

See `java-migration/parameters.md` R5 for the full Input concreteness rule.

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

See `java-migration/parameters.md` for the full parameter migration rules.

### R4 — `ParametricDistribution` base class

If a custom class `extends ParametricDistribution`, check whether `ParametricDistribution` has a
BEAST3 spec twin. If not, leave unchanged and flag with
`// TODO: no beast3 spec class found for ParametricDistribution`.

---

## Edge Cases

- **`Prior` wrapping a distribution**: `Prior` is removed — do not migrate it. Replace the whole
  pattern with a direct spec distribution call. Migrate the inner distribution first, then remove
  the `Prior` wrapper. If the inner distribution has no spec twin, leave the block and add a
  `// TODO` comment.
- **Unknown distribution class**: if a class under `beast.base.inference.distribution` is not in the
  table above, check the BEAST3 source before rewriting. If no spec counterpart exists, leave
  unchanged and flag with `// TODO: no beast3 spec class found for <ClassName>`.
- **Wildcard imports**: expand to explicit imports, then apply R1.
- **Partial migration**: if the file already imports any `beast.base.spec.inference.distribution.*`
  classes, do not duplicate them.

---

## XML migration

For `spec=` attribute changes in example XMLs and FxTemplates, see **`xml-migration/example-xmls.md`** (Step 3) and **`xml-migration/fxtemplates.md`** (Step 3). Key XML-specific rules covered there:
- `Prior` + inner distribution → direct spec distribution with `param=`
- Inner distribution has no spec twin → TODO XML comment, leave block commented out
- Vector parameter with scalar distribution → `IID` wrapper

---

## Log (Mode 2b — Changes field)

- Distribution classes renamed to `.spec.`: list each (e.g. `LogNormal, Gamma → .spec.`)
- `Prior wrapper restructured: yes/no`
- `Warnings — non-deprecated BEAST2 classes migrated: N` — list each class name (or "none")

# sampled-ancestors — what's left

> **Scanned at:** 2026-05-13T11:11:53.691140  
> **Commit:** `09bc95c` on `master` — [view on GitHub](https://github.com/CompEvol/sampled-ancestors/commit/09bc95cd1771ce4d85452314331a612549948e69)  
> **Pom version:** `2.3.0-beta1`  
> **Maven Central:** `io.github.compevol:sampled-ancestors:2.3.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 0 on spec, 0 mixed, 6 legacy of 63 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 3 total
- **BEAUti fxtemplates:** 1 clean / 1 use spec / 1 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** 2.3.0-beta1

## Java classes pending migration

### Distributions — 2 legacy, 0 mixed (of 6 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `sa.math.distributions.DegenerateBeta` — extends `ParametricDistribution`
- `sa.math.distributions.DegenerateUniform` — extends `ParametricDistribution`

### Operators — 1 legacy, 0 mixed (of 13 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `sa.evolution.operators.SAScaleOperator` — extends `ScaleOperator`

### Other — 3 legacy, 0 mixed (of 35 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `sa.evolution.tree.AncestryConstraint` — extends `MRCAPrior`
- `sa.math.distributions.SAMRCAPrior` — extends `MRCAPrior`
- `sa.math.distributions.SpecialMRCAPrior` — extends `MRCAPrior`

## Example XMLs pending migration

**Needs `version="2.8"`** (3):

- `examples/bears_ranges.xml`
- `examples/brachiopods.xml`
- `examples/bears.xml`

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`examples/bears_ranges.xml`** (15):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.evolution.branchratemodel.UCRelaxedClockModel` | `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Prior` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | _(no spec equivalent found)_ |

**`examples/brachiopods.xml`** (13):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Prior` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | _(no spec equivalent found)_ |

**`examples/bears.xml`** (15):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.evolution.branchratemodel.UCRelaxedClockModel` | `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Prior` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | _(no spec equivalent found)_ |


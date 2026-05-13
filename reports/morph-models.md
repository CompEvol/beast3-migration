# morph-models — what's left

> **Scanned at:** 2026-05-13T13:53:47.072445  
> **Commit:** `db64781` on `master` — [view on GitHub](https://github.com/CompEvol/morph-models/commit/db64781d130654714b8344de74e65dbe6929bd5a)  
> **Pom version:** `1.3.0-beta3`  
> **Maven Central:** `io.github.compevol:morph-models:1.3.0-beta3`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 4 on spec, 0 mixed, 1 legacy of 7 total
- **Example XMLs:** 2 on spec / 2 on `version="2.8"` / 2 total (+4 under legacy/)
- **BEAUti fxtemplates:** 0 clean / 1 use spec / 1 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** 1.3.0-beta3

## Java classes pending migration

### Other — 1 legacy, 0 mixed (of 2 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `morphmodels.evolution.alignment.AscertainedForParsimonyUninformativeFilteredAlignment` — extends `FilteredAlignment`

## Example XMLs

All example XMLs target `version="2.8"` and use spec types with no legacy parameter declarations. ✅

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**Uses spec types but still has legacy `parameter.*` declarations** (1):

- `src/main/resources/morph.models/fxtemplates/morph-models.xml`

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`examples/legacy-2.7/penguins_Mkv.xml`** (16):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.branchratemodel.UCRelaxedClockModel` | `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`examples/legacy-2.7/penguins.xml`** (16):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.branchratemodel.UCRelaxedClockModel` | `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`examples/legacy-2.7/nonEqualFreqs.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |

**`examples/legacy-2.7/M3982.xml`** (13):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`src/main/resources/morph.models/fxtemplates/morph-models.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.alignment.FilteredAlignment` | `beast.base.spec.evolution.alignment.FilteredAlignment` |


# beast-classic — what's left

> **Scanned at:** 2026-05-13T14:50:56.706995  
> **Commit:** `bff64a7` on `wip-avmn-skyride-skygrid` — [view on GitHub](https://github.com/BEAST2-Dev/beast-classic/commit/bff64a7272d89e251245360c75b74c72fc8dc070)  
> **Pom version:** `1.7.0-SNAPSHOT`  
> **Maven Central:** `io.github.beast2-dev:beast-classic:1.7.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 7 on spec, 0 mixed, 9 legacy of 97 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 10 total
- **BEAUti fxtemplates:** 0 clean / 1 use spec / 5 total
- **Input rule:** 5 classes hold 9 Input(s) declared too concretely
- **Maven Central:** 1.7.0-beta1

## Java classes pending migration

### Loggers — 4 legacy, 0 mixed (of 11 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beastclassic.continuous.AbstractMultivariateTraitLikelihood` — extends `GenericTreeLikelihood`
- `beastclassic.continuous.FullyConjugateMultivariateTraitLikelihood` — extends `GenericTreeLikelihood`
- `beastclassic.continuous.IntegratedMultivariateTraitLikelihood` — extends `GenericTreeLikelihood`
- `beastclassic.continuous.SampledMultivariateTraitLikelihood` — extends `GenericTreeLikelihood`

### CalcNodes — 5 legacy, 0 mixed (of 14 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beastclassic.continuous.MultivariateDiffusionModel` — extends `Base`
- `beastclassic.evolution.substitutionmodel.ContinuousSubstitutionModel` — extends `Base`
- `beastclassic.evolution.substitutionmodel.FLU` — extends `EmpiricalSubstitutionModel`
- `beastclassic.evolution.substitutionmodel.GLMBasedSubstModel` — extends `GeneralSubstitutionModel`
- `beastclassic.evolution.substitutionmodel.LG` — extends `EmpiricalSubstitutionModel`

## Inputs declared too concretely

> Concrete spec params (`RealScalarParam`, `RealVectorParam`, …) belong on Operators, which need to write the parameter, and on Loggers, which need `getID()` to write column headers (the pure type interfaces deliberately do not extend `BEASTInterface`). Distributions, CalcNodes, and other read-only holders should declare the interface (`RealScalar`, `RealVector`, …) so adapters and transforms can be substituted. Legacy `RealParameter` / `Function` Inputs are violations everywhere.

### Distributions (2)

- `beastclassic.evolution.tree.coalescent.GMRFMultilocusSkyrideLikelihood`
    - concrete: `Input<RealScalarParam<? extends PositiveReal>>`
    - concrete: `Input<RealVectorParam<? extends PositiveReal>>`
- `beastclassic.evolution.tree.coalescent.GMRFSkyrideLikelihood`
    - concrete: `Input<RealVectorParam<? extends PositiveReal>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<RealScalarParam<? extends PositiveReal>>`
    - concrete: `Input<RealScalarParam<? extends UnitInterval>>`

### CalcNodes (2)

- `beastclassic.evolution.likelihood.LeafTrait`
    - concrete: `Input<IntVectorParam<? extends Int>>`
- `beastclassic.evolution.substitutionmodel.SVSGeneralSubstitutionModel`
    - concrete: `Input<BoolVectorParam>`

### Other (1)

- `beastclassic.phylogeography.RateIndicatorInitializer`
    - concrete: `Input<BoolVectorParam>`

## Example XMLs pending migration

**Needs `version="2.8"`** (10):

- `examples/RacRABV_LogNRRW2.xml`
- `examples/RacRABV_LogNRRW1.xml`
- `examples/beast1/testBinaryDollo2.xml`
- `examples/beast1/testBinaryDollo1.xml`
- `examples/testSkyGrid.xml`
- `examples/H5N1_HA_discrete2.xml`
- `examples/H5N1_HA_discrete1.xml`
- `examples/testSkyRide.xml`
- `examples/testDiscreteSmall.xml`
- `doc/tutorial/phylogeography_discrete/data/H5N1.xml`

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**Uses spec types but still has legacy `parameter.*` declarations** (1):

- `src/main/resources/beast.classic/fxtemplates/ClassicTreePriors.xml`

**No `beast.base.spec.*` references in body** (4):

- `src/main/resources/beast.classic/fxtemplates/RelaxedClockModels.xml`
- `src/main/resources/beast.classic/fxtemplates/discrete-trait.xml`
- `src/main/resources/beast.classic/fxtemplates/ClassicSubtseModels.xml`
- `src/main/resources/beast.classic/fxtemplates/StarBeast.xml`

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`examples/RacRABV_LogNRRW2.xml`** (21):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.branchratemodel.UCRelaxedClockModel` | `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |

**`examples/testSkyGrid.xml`** (13):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |

**`examples/H5N1_HA_discrete2.xml`** (6):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.branchratemodel.UCRelaxedClockModel` | `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`examples/testSkyRide.xml`** (13):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |

**`examples/testDiscreteSmall.xml`** (16):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.branchratemodel.UCRelaxedClockModel` | `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`src/main/resources/beast.classic/fxtemplates/RelaxedClockModels.xml`** (6):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |

**`src/main/resources/beast.classic/fxtemplates/discrete-trait.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.Sum` | `beast.base.spec.evolution.Sum` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |

**`src/main/resources/beast.classic/fxtemplates/ClassicTreePriors.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.operator.AdaptableOperatorSampler` | `beast.base.spec.evolution.operator.AdaptableOperatorSampler` |

**`src/main/resources/beast.classic/fxtemplates/StarBeast.xml`** (24):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.speciation.StarBeastStartState` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |


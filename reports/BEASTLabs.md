# BEASTLabs — what's left

> **Scanned at:** 2026-05-13T14:39:18.813783  
> **Commit:** `8a075b6` on `master` — [view on GitHub](https://github.com/BEAST2-Dev/BEASTlabs/commit/8a075b6c58657065f4dab2a426d9f7d45f29c92b)  
> **Pom version:** `2.1.0-SNAPSHOT`  
> **Maven Central:** `io.github.beast2-dev:beast-labs:2.1.0-beta2`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 1 on spec, 0 mixed, 16 legacy of 197 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 20 total
- **BEAUti fxtemplates:** 0 clean / 0 use spec / 3 total
- **Input rule:** 16 classes hold 30 Input(s) declared too concretely
- **Maven Central:** 2.1.0-beta2

## Java classes pending migration

### Distributions — 5 legacy, 0 mixed (of 22 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beastlabs.math.distributions.ExcludablePrior` — extends `Prior`
- `beastlabs.math.distributions.ExcludablePriorIndex` — extends `Prior`
- `beastlabs.math.distributions.GammaOneP` — extends `ParametricDistribution`
- `beastlabs.math.distributions.SingleParamGamma` — extends `ParametricDistribution`
- `beastlabs.math.distributions.WeibullDistribution` — extends `ParametricDistribution`

### Loggers — 1 legacy, 0 mixed (of 11 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beastlabs.evolution.likelihood.AncestralStateLogger` — extends `TreeLikelihood`

### CalcNodes — 7 legacy, 0 mixed (of 10 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beastlabs.evolution.likelihood.TraitedTreeLikelihood` — extends `TreeLikelihood`
- `beastlabs.evolution.substitutionmodel.CladeSubstitutionModel` — extends `Base`
- `beastlabs.evolution.substitutionmodel.EmpiricalAAModelFromFile` — extends `EmpiricalSubstitutionModel`
- `beastlabs.evolution.substitutionmodel.EpochSubstitutionModel` — extends `Base`
- `beastlabs.evolution.substitutionmodel.GeneralLazySubstitutionModel` — extends `GeneralSubstitutionModel`
- `beastlabs.evolution.substitutionmodel.LazyHKY` — extends `HKY`
- `beastlabs.evolution.tree.ConstrainedRandomTree` — extends `RandomTree`

### Parameters — 2 legacy, 0 mixed (of 2 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beastlabs.core.parameter.CompoundRealParameter` — extends `RealParameter`
- `beastlabs.core.parameter.NormalisedRealParameter` — extends `RealParameter`

### Other — 1 legacy, 0 mixed (of 123 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beastlabs.math.distributions.MRCAPriorWithRogues` — extends `MRCAPrior`

## Inputs declared too concretely

> Concrete spec params (`RealScalarParam`, `RealVectorParam`, …) belong on Operators, which need to write the parameter, and on Loggers, which need `getID()` to write column headers (the pure type interfaces deliberately do not extend `BEASTInterface`). Distributions, CalcNodes, and other read-only holders should declare the interface (`RealScalar`, `RealVector`, …) so adapters and transforms can be substituted. Legacy `RealParameter` / `Function` Inputs are violations everywhere.

### Distributions (8)

- `beastlabs.evolution.speciation.RandomLocalYuleModel`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<BooleanParameter>`
    - legacy: `Input<RealParameter>`
- `beastlabs.math.distributions.BernoulliDistribution`
    - concrete: `Input<RealVectorParam<? extends UnitInterval>>`
    - concrete: `Input<BoolVectorParam>`
    - concrete: `Input<IntScalarParam<? extends NonNegativeInt>>`
- `beastlabs.math.distributions.BetaRange`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beastlabs.math.distributions.ExcludablePrior`
    - legacy: `Input<BooleanParameter>`
- `beastlabs.math.distributions.ExcludablePriorIndex`
    - legacy: `Input<BooleanParameter>`
    - legacy: `Input<IntegerParameter>`
- `beastlabs.math.distributions.MixtureDistribution`
    - concrete: `Input<RealVectorParam<? extends Real>>`
- `beastlabs.math.distributions.RandomCompositionPositive`
    - concrete: `Input<IntVectorParam<? extends PositiveInt>>`
    - concrete: `Input<IntScalarParam<? extends PositiveInt>>`
    - concrete: `Input<IntScalarParam<? extends PositiveInt>>`
- `beastlabs.prevalence.PrevalenceLikelihood`
    - concrete: `Input<IntScalarParam<? extends PositiveInt>>`

### Loggers (4)

- `beastlabs.core.FilteredValuable`
    - legacy: `Input<Function>`
- `beastlabs.core.util.LoggableSum`
    - legacy: `Input<Function>`
- `beastlabs.core.util.ParameterConstrainer`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
- `beastlabs.core.util.Slice`
    - legacy: `Input<Function>`

### CalcNodes (1)

- `beastlabs.evolution.substitutionmodel.EpochSubstitutionModel`
    - concrete: `Input<RealVectorParam<? extends Real>>`

### StateNodes (2)

- `beastlabs.evolution.tree.ConstrainedClusterTree`
    - legacy: `Input<RealParameter>`
- `beastlabs.evolution.tree.coalescent.StructuredCoalescentTree`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<IntVectorParam<? extends NonNegativeInt>>`

### Other (1)

- `beastlabs.evolution.tree.InitParamFromTree`
    - concrete: `Input<RealVectorParam<? extends Real>>`

## Inputs declaring `@Deprecated` types

> Each entry below is an `Input<T>` field where `T` (or one of its generic parameters) is annotated `@Deprecated` somewhere in the scanned packages. Such Inputs block XML migration: downstream XMLs cannot supply a non-deprecated value to them. Replace the declared type with the suggested spec equivalent (and update the field/local variable types accordingly).

**`beastlabs.core.util.LoggableSum`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `Function` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`beastlabs.core.util.ParameterConstrainer`** (3):

| Input type | Hit | Replacement |
|---|---|---|
| `RealParameter` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`beastlabs.core.util.Slice`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `Function` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`beastlabs.core.FilteredValuable`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `Function` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`beastlabs.core.parameter.CompoundRealParameter`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `List<RealParameter>` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`beastlabs.core.parameter.NormalisedRealParameter`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `List<Function>` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`beastlabs.util.Transform.UnivariableTransform`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `List<Function>` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`beastlabs.util.Transform.MultivariableTransform`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `List<Function>` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`beastlabs.util.Script`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `List<beast.base.core.Function>` | `beast.base.core.Function` | _(no spec equivalent found)_ |

**`beastlabs.math.distributions.ExcludablePrior`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `BooleanParameter` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |

**`beastlabs.math.distributions.BetaRange`** (4):

| Input type | Hit | Replacement |
|---|---|---|
| `Function` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`beastlabs.math.distributions.ExcludablePriorIndex`** (2):

| Input type | Hit | Replacement |
|---|---|---|
| `BooleanParameter` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |
| `IntegerParameter` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |

**`beastlabs.evolution.tree.ConstrainedClusterTree`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `RealParameter` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`beastlabs.evolution.operators.UniformOperatorSelective`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `Parameter<?>` | `Parameter` → `beast.base.inference.parameter.Parameter` | _(no spec equivalent found)_ |

**`beastlabs.evolution.operators.AttachAndUniformOperator`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `Parameter<?>` | `Parameter` → `beast.base.inference.parameter.Parameter` | _(no spec equivalent found)_ |

**`beastlabs.evolution.speciation.RandomLocalYuleModel`** (3):

| Input type | Hit | Replacement |
|---|---|---|
| `RealParameter` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `BooleanParameter` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |

## Example XMLs pending migration

**Needs `version="2.8"`** (20):

- `examples/testSA.xml`
- `examples/testCladeSubstitutionModel.xml`
- `examples/testAncestralStateLogger.xml`
- `examples/testBactrianOperatorSchedule.xml`
- `examples/testMCMCMC.xml`
- `examples/testMultiParitionTreeLikelihood.xml`
- `examples/testMultiChainMCMC.xml`
- `examples/testAVMN.xml`
- `examples/testWeightedDirichlet/testNoPrior.xml`
- `examples/testWeightedDirichlet/testWeightedDirichlet.xml`
- `examples/simulator/testSteppingStone.xml`
- `examples/testTraitedTreeLikelihood.xml`
- `examples/independentMCMC.xml`
- `examples/testPrevalence.xml`
- `examples/testWilsonBaldingOperator.xml`
- `examples/benchmarking/testMultiParitionTreeLikelihoodRuntime.xml`
- `examples/benchmarking/testTreeLikelihoodRuntime.xml`
- `examples/testScript.xml`
- `examples/posthocanalysis/testPostHocAnalysis.xml`
- `examples/testBactrianOperators.xml`

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**No `beast.base.spec.*` references in body** (3):

- `src/main/resources/beast.labs/fxtemplates/Weibull.xml`
- `src/main/resources/beast.labs/fxtemplates/extras.xml`
- `src/main/resources/beast.labs/fxtemplates/SelfTuningMCMC.xml`

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`examples/testSA.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`examples/testCladeSubstitutionModel.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |

**`examples/testAncestralStateLogger.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.MarkovChainDistribution` | `beast.base.spec.inference.distribution.MarkovChainDistribution` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `spec=` | `beast.base.evolution.branchratemodel.UCRelaxedClockModel` | `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |

**`examples/testBactrianOperatorSchedule.xml`** (13):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`examples/testMCMCMC.xml`** (15):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beastlabs.inference.MCMCMC` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.MarkovChainDistribution` | `beast.base.spec.inference.distribution.MarkovChainDistribution` |
| `spec=` | `beast.base.evolution.branchratemodel.UCRelaxedClockModel` | `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`examples/testMultiParitionTreeLikelihood.xml`** (17):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.inference.util.RPNcalculator` | `beast.base.spec.inference.util.RPNcalculator` |
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

**`examples/testMultiChainMCMC.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`examples/testAVMN.xml`** (12):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`examples/testTraitedTreeLikelihood.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.speciation.BirthDeathGernhard08Model` | `beast.base.spec.evolution.speciation.BirthDeathGernhard08Model` |

**`examples/independentMCMC.xml`** (14):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `beast.base.evolution.branchratemodel.UCRelaxedClockModel` | `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`examples/testPrevalence.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `beast.base.evolution.operator.ScaleOperator` | `beast.base.spec.inference.operator.ScaleOperator` |

**`examples/testWilsonBaldingOperator.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`examples/benchmarking/testMultiParitionTreeLikelihoodRuntime.xml`** (17):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
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

**`examples/benchmarking/testTreeLikelihoodRuntime.xml`** (17):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
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

**`examples/testScript.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`examples/posthocanalysis/testPostHocAnalysis.xml`** (15):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
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

**`examples/testBactrianOperators.xml`** (17):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.operator.kernel.BactrianScaleOperator` | `beast.base.spec.inference.operator.ScaleOperator` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`src/main/resources/beast.labs/fxtemplates/SelfTuningMCMC.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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


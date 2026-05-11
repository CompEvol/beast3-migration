# BEASTLabs — what's left

> **Scanned at:** 2026-05-11T16:47:20.792689  
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

> Concrete spec params (`RealScalarParam`, `RealVectorParam`, …) belong only on Operators, which need to write the parameter. Distributions, CalcNodes, Loggers and other read-only holders should declare the interface (`RealScalar`, `RealVector`, …) so adapters and transforms can be substituted. Legacy `RealParameter` / `Function` Inputs are violations everywhere.

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


# beast-classic — what's left

> **Scanned at:** 2026-05-07T21:22:11.949925  
> **Local checkout:** `/Users/adru001/Git/beast-classic` — commit `8baef54` on `master` — [view on GitHub](https://github.com/BEAST2-Dev/beast-classic/commit/8baef5485aa4b218655729d03c7f8d7ef3ab9668)  
> **Pom version:** `1.7.0-SNAPSHOT`  
> **Maven Central:** `io.github.beast2-dev:beast-classic:1.7.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 7 on spec, 0 mixed, 7 legacy of 68 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 10 total
- **BEAUti fxtemplates:** 0 clean / 0 use spec / 5 total
- **Input rule:** 8 classes hold 16 Input(s) declared too concretely
- **Maven Central:** 1.7.0-beta1

## Java classes pending migration

### Loggers — 4 legacy, 0 mixed (of 11 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beastclassic.continuous.AbstractMultivariateTraitLikelihood` — extends `GenericTreeLikelihood`
- `beastclassic.continuous.FullyConjugateMultivariateTraitLikelihood` — extends `GenericTreeLikelihood`
- `beastclassic.continuous.IntegratedMultivariateTraitLikelihood` — extends `GenericTreeLikelihood`
- `beastclassic.continuous.SampledMultivariateTraitLikelihood` — extends `GenericTreeLikelihood`

### CalcNodes — 3 legacy, 0 mixed (of 14 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beastclassic.evolution.substitutionmodel.FLU` — extends `EmpiricalSubstitutionModel`
- `beastclassic.evolution.substitutionmodel.GLMBasedSubstModel` — extends `GeneralSubstitutionModel`
- `beastclassic.evolution.substitutionmodel.LG` — extends `EmpiricalSubstitutionModel`

## Inputs declared too concretely

> Concrete spec params (`RealScalarParam`, `RealVectorParam`, …) belong only on Operators, which need to write the parameter. Distributions, CalcNodes, Loggers and other read-only holders should declare the interface (`RealScalar`, `RealVector`, …) so adapters and transforms can be substituted. Legacy `RealParameter` / `Function` Inputs are violations everywhere.

### Distributions (2)

- `beastclassic.evolution.tree.coalescent.GMRFMultilocusSkyrideLikelihood`
    - concrete: `Input<RealScalarParam<? extends PositiveReal>>`
    - concrete: `Input<RealVectorParam<? extends PositiveReal>>`
- `beastclassic.evolution.tree.coalescent.GMRFSkyrideLikelihood`
    - concrete: `Input<RealVectorParam<? extends PositiveReal>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<RealScalarParam<? extends PositiveReal>>`
    - concrete: `Input<RealScalarParam<? extends UnitInterval>>`

### Loggers (3)

- `beastclassic.continuous.AbstractMultivariateTraitLikelihood`
    - concrete: `Input<RealVectorParam<? extends Real>>`
- `beastclassic.continuous.IntegratedMultivariateTraitLikelihood`
    - concrete: `Input<RealVectorParam<? extends Real>>`
- `beastclassic.evolution.substitutionmodel.GlmModel`
    - concrete: `Input<RealVectorParam<? extends PositiveReal>>`
    - concrete: `Input<BoolVectorParam>`
    - concrete: `Input<RealScalarParam<? extends PositiveReal>>`
    - concrete: `Input<RealVectorParam<? extends NonNegativeReal>>`
    - concrete: `Input<RealVectorParam<? extends NonNegativeReal>>`

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

**No `beast.base.spec.*` references in body** (5):

- `src/main/resources/beast.classic/fxtemplates/RelaxedClockModels.xml`
- `src/main/resources/beast.classic/fxtemplates/discrete-trait.xml`
- `src/main/resources/beast.classic/fxtemplates/ClassicTreePriors.xml`
- `src/main/resources/beast.classic/fxtemplates/ClassicSubtseModels.xml`
- `src/main/resources/beast.classic/fxtemplates/StarBeast.xml`


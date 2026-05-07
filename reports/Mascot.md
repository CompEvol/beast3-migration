# Mascot — what's left

> **Scanned at:** 2026-05-07T22:00:50.697658  
> **Commit:** `f9d1aca` on `master` — [view on GitHub](https://github.com/CompEvol/Mascot/commit/f9d1aca862f4c94a3e174e63fff51c8d7588b800)  
> **Pom version:** `3.1.0-beta1`  
> **Maven Central:** `io.github.compevol:mascot:3.1.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 4 on spec, 0 mixed, 1 legacy of 121 total
- **Example XMLs:** 1 on spec / 1 on `version="2.8"` / 4 total
- **BEAUti fxtemplates:** 0 clean / 0 use spec / 5 total
- **Input rule:** 23 classes hold 45 Input(s) declared too concretely
- **Maven Central:** 3.1.0-beta1

## Java classes pending migration

### Loggers — 1 legacy, 0 mixed (of 25 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `mascot.mapped.AncestralStateTreeLikelihood` — extends `TreeLikelihood`

## Inputs declared too concretely

> Concrete spec params (`RealScalarParam`, `RealVectorParam`, …) belong only on Operators, which need to write the parameter. Distributions, CalcNodes, Loggers and other read-only holders should declare the interface (`RealScalar`, `RealVector`, …) so adapters and transforms can be substituted. Legacy `RealParameter` / `Function` Inputs are violations everywhere.

### Distributions (5)

- `mascot.glmmodel.ErrorSmoothing`
    - legacy: `Input<Function>`
- `mascot.skyline.GLMPrior`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<BoolVectorParam>`
- `mascot.skyline.GrowthRateSmoothingPrior`
    - concrete: `Input<RealVectorParam<? extends Real>>`
- `mascot.skyline.LogSmoothingPrior`
    - concrete: `Input<RealVectorParam<? extends Real>>`
- `mascot.util.LargerThan`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`

### Loggers (10)

- `mascot.distribution.MappedMascot`
    - concrete: `Input<BoolVectorParam>`
- `mascot.distribution.MappedMascotWithTipSampling`
    - concrete: `Input<BoolVectorParam>`
- `mascot.dynamics.Constant`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
- `mascot.dynamics.ConstantBSSVS`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<BoolVectorParam>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
- `mascot.dynamics.DynamicEffectivePopulationSizesBSSVS`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<BoolVectorParam>`
- `mascot.dynamics.StructuredMigrationSkyline`
    - concrete: `Input<BoolVectorParam>`
- `mascot.dynamics.StructuredSkyline`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<BoolVectorParam>`
- `mascot.glmmodel.GlmModel`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<BoolVectorParam>`
    - concrete: `Input<RealScalarParam<Real>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
- `mascot.logger.StructuredTreeLogger`
    - concrete: `Input<BoolVectorParam>`
- `mascot.logger.mappedProbLogger`
    - concrete: `Input<BoolVectorParam>`

### CalcNodes (7)

- `mascot.parameterdynamics.ConstantNe`
    - concrete: `Input<RealScalarParam<Real>>`
- `mascot.parameterdynamics.ExponentialNe`
    - concrete: `Input<RealScalarParam<Real>>`
    - concrete: `Input<RealScalarParam<Real>>`
- `mascot.parameterdynamics.LogLinearGLM`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<BoolVectorParam>`
    - concrete: `Input<RealScalarParam<Real>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
- `mascot.parameterdynamics.LogisticNe`
    - concrete: `Input<RealScalarParam<Real>>`
    - concrete: `Input<RealScalarParam<Real>>`
    - concrete: `Input<RealScalarParam<Real>>`
- `mascot.parameterdynamics.NeSplineInterpolation`
    - concrete: `Input<RealVectorParam<? extends Real>>`
- `mascot.parameterdynamics.Skygrowth`
    - concrete: `Input<RealVectorParam<? extends Real>>`
- `mascot.parameterdynamics.StructuredSkygrid`
    - concrete: `Input<RealVectorParam<? extends Real>>`

### Other (1)

- `mascot.glmmodel.CovariateList`
    - concrete: `Input<BoolVectorParam>`

## Example XMLs pending migration

**Needs `version="2.8"`** (3):

- `examples/GLM.xml`
- `examples/ConstantBSSVS.xml`
- `examples/Skyline.xml`

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**No `beast.base.spec.*` references in body** (5):

- `src/main/resources/mascot/fxtemplates/MascotTreePriorTemplate.xml`
- `src/main/resources/mascot/fxtemplates/MascotConstant.xml`
- `src/main/resources/mascot/fxtemplates/MascotSkyline.xml`
- `src/main/resources/mascot/fxtemplates/MascotConstantBSSVS.xml`
- `src/main/resources/mascot/fxtemplates/MascotGLM.xml`


# Mascot — what's left

> **Scanned at:** 2026-05-13T16:18:06.430004  
> **Commit:** `f9d1aca` on `spec-input-types` — [view on GitHub](https://github.com/CompEvol/Mascot/commit/f9d1aca862f4c94a3e174e63fff51c8d7588b800)  
> **Pom version:** `3.1.0-beta1`  
> **Maven Central:** `io.github.compevol:mascot:3.1.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 4 on spec, 0 mixed, 1 legacy of 121 total
- **Example XMLs:** 1 on spec / 1 on `version="2.8"` / 284 total
- **BEAUti fxtemplates:** 0 clean / 0 use spec / 14 total
- **Input rule:** 15 classes hold 29 Input(s) declared too concretely
- **Maven Central:** 3.1.0-beta1

## Java classes pending migration

### CalcNodes — 1 legacy, 0 mixed (of 30 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `mascot.mapped.AncestralStateTreeLikelihood` — extends `TreeLikelihood`

## Inputs declared too concretely

> Concrete spec params (`RealScalarParam`, `RealVectorParam`, …) belong on Operators, which need to write the parameter, and on Loggers, which need `getID()` to write column headers (the pure type interfaces deliberately do not extend `BEASTInterface`). Distributions, CalcNodes, and other read-only holders should declare the interface (`RealScalar`, `RealVector`, …) so adapters and transforms can be substituted. Legacy `RealParameter` / `Function` Inputs are violations everywhere.

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

### CalcNodes (9)

- `mascot.glmmodel.GlmModel`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<BoolVectorParam>`
    - concrete: `Input<RealScalarParam<Real>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
    - concrete: `Input<RealVectorParam<? extends Real>>`
- `mascot.logger.mappedProbLogger`
    - concrete: `Input<BoolVectorParam>`
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

### StateNodes (1)

- `mascot.logger.StructuredTreeLogger`
    - concrete: `Input<BoolVectorParam>`

## Inputs declaring `@Deprecated` types

> Each entry below is an `Input<T>` field where `T` (or one of its generic parameters) is annotated `@Deprecated` somewhere in the scanned packages. Such Inputs block XML migration: downstream XMLs cannot supply a non-deprecated value to them. Replace the declared type with the suggested spec equivalent (and update the field/local variable types accordingly).

**`mascot.logger.mappedProbLogger`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `List<Function>` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`mascot.logger.StructuredTreeLogger`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `List<Function>` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`mascot.util.LargerThan`** (2):

| Input type | Hit | Replacement |
|---|---|---|
| `Function` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`mascot.distribution.MappedMascot`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `List<Function>` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`mascot.distribution.MappedMascotWithTipSampling`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `List<Function>` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`mascot.skyline.LogSmoothingPrior`** (4):

| Input type | Hit | Replacement |
|---|---|---|
| `ParametricDistribution` | `ParametricDistribution` → `beast.base.inference.distribution.ParametricDistribution` | `beast.base.spec.inference.distribution.TensorDistribution` |

**`mascot.skyline.GLMPrior`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `ParametricDistribution` | `ParametricDistribution` → `beast.base.inference.distribution.ParametricDistribution` | `beast.base.spec.inference.distribution.TensorDistribution` |

**`mascot.skyline.GrowthRateSmoothingPrior`** (4):

| Input type | Hit | Replacement |
|---|---|---|
| `ParametricDistribution` | `ParametricDistribution` → `beast.base.inference.distribution.ParametricDistribution` | `beast.base.spec.inference.distribution.TensorDistribution` |

**`mascot.glmmodel.ErrorSmoothing`** (2):

| Input type | Hit | Replacement |
|---|---|---|
| `Function` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |
| `ParametricDistribution` | `ParametricDistribution` → `beast.base.inference.distribution.ParametricDistribution` | `beast.base.spec.inference.distribution.TensorDistribution` |

**`mascot.glmmodel.MaxRate`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `ParametricDistribution` | `ParametricDistribution` → `beast.base.inference.distribution.ParametricDistribution` | `beast.base.spec.inference.distribution.TensorDistribution` |

## Example XMLs pending migration

**Needs `version="2.8"`** (283):

- `MASCOT6_glmcontinuous_asia_1201_a0.xml`
- `beast-source/test/tmp123x666.xml`
- `beast-source/test/template123x666.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testExponentialGrowth.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testCoalescent.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testGTR.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testBSP.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testPlates.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testSRD06.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testHKY.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testRestrictedGTR.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testRNA.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testYuleOneSite.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testSimulatedAlignment.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testStarBeast.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/bitflip.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testClassicRootCalibrationPrior.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testTN93.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testJukesCantor.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testConditionalRootCalibrationPrior.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testCalibration.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testTipDates.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testTipDates2.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/Primates.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testCalYule_5t_2c.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testRelaxedClock.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testTwoCalibrationsPrior.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testSliceHKY.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testEBSP.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testRandomLocalClock.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testYuleUncalibrated.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testSeqGen.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testYuleCalibrated.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/templates/Standard.xml`
- `beast-source/release/Mac/BEAST/BEAST 2.2.0/templates/StarBeast.xml`
- `beast-source/release/Linux/BEAST/examples/testExponentialGrowth.xml`
- `beast-source/release/Linux/BEAST/examples/testCoalescent.xml`
- `beast-source/release/Linux/BEAST/examples/testGTR.xml`
- `beast-source/release/Linux/BEAST/examples/testBSP.xml`
- `beast-source/release/Linux/BEAST/examples/testPlates.xml`
- `beast-source/release/Linux/BEAST/examples/testSRD06.xml`
- `beast-source/release/Linux/BEAST/examples/testHKY.xml`
- `beast-source/release/Linux/BEAST/examples/testRestrictedGTR.xml`
- `beast-source/release/Linux/BEAST/examples/testRNA.xml`
- `beast-source/release/Linux/BEAST/examples/testYuleOneSite.xml`
- `beast-source/release/Linux/BEAST/examples/testSimulatedAlignment.xml`
- `beast-source/release/Linux/BEAST/examples/testStarBeast.xml`
- `beast-source/release/Linux/BEAST/examples/bitflip.xml`
- `beast-source/release/Linux/BEAST/examples/testClassicRootCalibrationPrior.xml`
- `beast-source/release/Linux/BEAST/examples/testTN93.xml`
- `beast-source/release/Linux/BEAST/examples/testJukesCantor.xml`
- `beast-source/release/Linux/BEAST/examples/testConditionalRootCalibrationPrior.xml`
- `beast-source/release/Linux/BEAST/examples/testCalibration.xml`
- `beast-source/release/Linux/BEAST/examples/testTipDates.xml`
- `beast-source/release/Linux/BEAST/examples/testTipDates2.xml`
- `beast-source/release/Linux/BEAST/examples/Primates.xml`
- `beast-source/release/Linux/BEAST/examples/testCalYule_5t_2c.xml`
- `beast-source/release/Linux/BEAST/examples/testRelaxedClock.xml`
- `beast-source/release/Linux/BEAST/examples/testTwoCalibrationsPrior.xml`
- `beast-source/release/Linux/BEAST/examples/testSliceHKY.xml`
- `beast-source/release/Linux/BEAST/examples/testEBSP.xml`
- `beast-source/release/Linux/BEAST/examples/testRandomLocalClock.xml`
- `beast-source/release/Linux/BEAST/examples/testYuleUncalibrated.xml`
- `beast-source/release/Linux/BEAST/examples/testSeqGen.xml`
- `beast-source/release/Linux/BEAST/examples/testYuleCalibrated.xml`
- `beast-source/release/Linux/BEAST/templates/Standard.xml`
- `beast-source/release/Linux/BEAST/templates/StarBeast.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testExponentialGrowth.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testCoalescent.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testGTR.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testBSP.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testPlates.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testSRD06.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testHKY.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testRestrictedGTR.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testRNA.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testYuleOneSite.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testSimulatedAlignment.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testStarBeast.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/bitflip.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testClassicRootCalibrationPrior.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testTN93.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testJukesCantor.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testConditionalRootCalibrationPrior.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testCalibration.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testTipDates.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testTipDates2.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/Primates.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testCalYule_5t_2c.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testRelaxedClock.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testTwoCalibrationsPrior.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testSliceHKY.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testEBSP.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testRandomLocalClock.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testYuleUncalibrated.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testSeqGen.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testYuleCalibrated.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/templates/Standard.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/templates/StarBeast.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testExponentialGrowth.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testCoalescent.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testGTR.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testBSP.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testPlates.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testSRD06.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testHKY.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testRestrictedGTR.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testRNA.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testYuleOneSite.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testSimulatedAlignment.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testStarBeast.xml`
- `beast-source/_site/release/Linux/BEAST/examples/bitflip.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testClassicRootCalibrationPrior.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testTN93.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testJukesCantor.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testConditionalRootCalibrationPrior.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testCalibration.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testTipDates.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testTipDates2.xml`
- `beast-source/_site/release/Linux/BEAST/examples/Primates.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testCalYule_5t_2c.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testRelaxedClock.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testTwoCalibrationsPrior.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testSliceHKY.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testEBSP.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testRandomLocalClock.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testYuleUncalibrated.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testSeqGen.xml`
- `beast-source/_site/release/Linux/BEAST/examples/testYuleCalibrated.xml`
- `beast-source/_site/release/Linux/BEAST/templates/Standard.xml`
- `beast-source/_site/release/Linux/BEAST/templates/StarBeast.xml`
- `beast-source/_site/examples/Primates.xml`
- `beast-source/examples/benchmark/II/testHKY1748.xml`
- `beast-source/examples/benchmark/II/testHKY1749.xml`
- `beast-source/examples/benchmark/II/testHKY1366.xml`
- `beast-source/examples/benchmark/II/testHKY767.xml`
- `beast-source/examples/benchmark/II/testHKY1809.xml`
- `beast-source/examples/benchmark/II/testHKY501.xml`
- `beast-source/examples/benchmark/II/testHKY3475.xml`
- `beast-source/examples/benchmark/II/testHKY1510.xml`
- `beast-source/examples/benchmark/II/testHKY1044.xml`
- `beast-source/examples/benchmark/II/testHKY520.xml`
- `beast-source/examples/benchmark/II/testHKY336.xml`
- `beast-source/examples/benchmark/II/testHKY755.xml`
- `beast-source/examples/benchmark/1/testHKY1748.xml`
- `beast-source/examples/benchmark/1/testHKY1749.xml`
- `beast-source/examples/benchmark/1/testHKY1366.xml`
- `beast-source/examples/benchmark/1/testHKY767.xml`
- `beast-source/examples/benchmark/1/testHKY1809.xml`
- `beast-source/examples/benchmark/1/testHKY501.xml`
- `beast-source/examples/benchmark/1/testHKY3475.xml`
- `beast-source/examples/benchmark/1/testHKY1510.xml`
- `beast-source/examples/benchmark/1/testHKY1044.xml`
- `beast-source/examples/benchmark/1/testHKY520.xml`
- `beast-source/examples/benchmark/1/testHKY336.xml`
- `beast-source/examples/benchmark/1/testHKY755.xml`
- `beast-source/examples/testJukesCantorShortUncertain2.xml`
- `beast-source/examples/testExponentialGrowth.xml`
- `beast-source/examples/testMultipleAlignments_randomTaxaOrder.xml`
- `beast-source/examples/testCoalescent.xml`
- `beast-source/examples/testGTR.xml`
- `beast-source/examples/testOpSubSchedule.xml`
- `beast-source/examples/testBSP.xml`
- `beast-source/examples/testPlates.xml`
- `beast-source/examples/testJukesCantorShortUncertain.xml`
- `beast-source/examples/testSRD06.xml`
- `beast-source/examples/testHKY.xml`
- `beast-source/examples/testJukesCantorShortUncertain2MLE.xml`
- `beast-source/examples/testRestrictedGTR.xml`
- `beast-source/examples/testRNA.xml`
- `beast-source/examples/parameterised/RSV2.xml`
- `beast-source/examples/testYuleOneSite.xml`
- `beast-source/examples/testSimulatedAlignment.xml`
- `beast-source/examples/testSYM.xml`
- `beast-source/examples/testStarBeast.xml`
- `beast-source/examples/testJukesCantorShort.xml`
- `beast-source/examples/bitflip.xml`
- `beast-source/examples/testClassicRootCalibrationPrior.xml`
- `beast-source/examples/testDirectSimulator2.xml`
- `beast-source/examples/testTN93.xml`
- `beast-source/examples/testStarBeastFBD.xml`
- `beast-source/examples/testJukesCantor.xml`
- `beast-source/examples/testTIM.xml`
- `beast-source/examples/testConditionalRootCalibrationPrior.xml`
- `beast-source/examples/testDirectSimulator.xml`
- `beast-source/examples/testCalibration.xml`
- `beast-source/examples/testTipDates.xml`
- `beast-source/examples/testTipDates2.xml`
- `beast-source/examples/testTVM.xml`
- `beast-source/examples/Primates.xml`
- `beast-source/examples/ratesDirichlet.xml`
- `beast-source/examples/testCalYule_5t_2c.xml`
- `beast-source/examples/testDirectSimulatorHierarchical.xml`
- `beast-source/examples/beast2vs1/testStrictClockTipDatesSampling.xml`
- `beast-source/examples/beast2vs1/testStarBEASTLinear.xml`
- `beast-source/examples/beast2vs1/testBSP1.xml`
- `beast-source/examples/beast2vs1/testExponentialGrowth.xml`
- `beast-source/examples/beast2vs1/testYuleModel_10taxa.xml`
- `beast-source/examples/beast2vs1/testBirthDeathModel_10taxa.xml`
- `beast-source/examples/beast2vs1/testSRD06CP12_3.xml`
- `beast-source/examples/beast2vs1/testStarBEASTConstant.xml`
- `beast-source/examples/beast2vs1/testHKY.xml`
- `beast-source/examples/beast2vs1/testStrictClock2.xml`
- `beast-source/examples/beast2vs1/testCoalescentTipDates1.xml`
- `beast-source/examples/beast2vs1/testCoalescentTipDates.xml`
- `beast-source/examples/beast2vs1/testUCRelaxedClockLogNormal.xml`
- `beast-source/examples/beast2vs1/testStarBEAST.xml`
- `beast-source/examples/beast2vs1/testStarBeast2.xml`
- `beast-source/examples/beast2vs1/beast1/testStrictClockTipDatesSampling.xml`
- `beast-source/examples/beast2vs1/beast1/testStarBEASTLinear.xml`
- `beast-source/examples/beast2vs1/beast1/testBSP1.xml`
- `beast-source/examples/beast2vs1/beast1/testExponentialGrowth.xml`
- `beast-source/examples/beast2vs1/beast1/testCoalescent.xml`
- `beast-source/examples/beast2vs1/beast1/testYuleModel_10taxa.xml`
- `beast-source/examples/beast2vs1/beast1/testBirthDeathModel_10taxa.xml`
- `beast-source/examples/beast2vs1/beast1/testStrictClockNoDate2.xml`
- `beast-source/examples/beast2vs1/beast1/testSRD06CP12_3.xml`
- `beast-source/examples/beast2vs1/beast1/testStarBEASTConstant.xml`
- `beast-source/examples/beast2vs1/beast1/testYule.xml`
- `beast-source/examples/beast2vs1/beast1/testUCRelaxedClockLogNormal.xml`
- `beast-source/examples/beast2vs1/beast1/testStrictClockNoDate.xml`
- `beast-source/examples/beast2vs1/beast1/testCoalescentNoClock.xml`
- `beast-source/examples/beast2vs1/beast1/testMultiSubstModel.xml`
- `beast-source/examples/beast2vs1/beast1/testMCMC.xml`
- `beast-source/examples/beast2vs1/beast1/testCalibration.xml`
- `beast-source/examples/beast2vs1/beast1/testStarBEASTLinearConstRoot.xml`
- `beast-source/examples/beast2vs1/beast1/testSiteModelAlpha.xml`
- `beast-source/examples/beast2vs1/beast1/testStrictClockTipTime.xml`
- `beast-source/examples/beast2vs1/beast1/testEBSP.xml`
- `beast-source/examples/beast2vs1/beast1/testBirthDeathAsYule.xml`
- `beast-source/examples/beast2vs1/beast1/testRandomLocalClock.xml`
- `beast-source/examples/beast2vs1/beast1/testCoalescentNoClock1.xml`
- `beast-source/examples/beast2vs1/beast1/testBSPNoClock.xml`
- `beast-source/examples/beast2vs1/testCoalescentNoClock.xml`
- `beast-source/examples/beast2vs1/testMultiSubstModel.xml`
- `beast-source/examples/beast2vs1/testCoalescentTipDatesSampling.xml`
- `beast-source/examples/beast2vs1/testCalibration.xml`
- `beast-source/examples/beast2vs1/testTipDates.xml`
- `beast-source/examples/beast2vs1/testStarBEASTLinearConstRoot.xml`
- `beast-source/examples/beast2vs1/testCalibrationMono.xml`
- `beast-source/examples/beast2vs1/testSiteModelAlpha.xml`
- `beast-source/examples/beast2vs1/testStrictClock.xml`
- `beast-source/examples/beast2vs1/testStrictClockTipTime.xml`
- `beast-source/examples/beast2vs1/testEBSP.xml`
- `beast-source/examples/beast2vs1/testBirthDeathAsYule.xml`
- `beast-source/examples/beast2vs1/testRandomLocalClock.xml`
- `beast-source/examples/beast2vs1/testCoalescentNoClock1.xml`
- `beast-source/examples/beast2vs1/testBSPNoClock.xml`
- `beast-source/examples/testRelaxedClock.xml`
- `beast-source/examples/testTwoCalibrationsPrior.xml`
- `beast-source/examples/testSliceHKY.xml`
- `beast-source/examples/testDirichlet/testDirichlet.xml`
- `beast-source/examples/testDirichlet/testDirichlet2.xml`
- `beast-source/examples/testDirichlet/testDirichletNoPrior.xml`
- `beast-source/examples/testDirichlet/testDirichletBact.xml`
- `beast-source/examples/starbeastinit/sbi-01.xml`
- `beast-source/examples/starbeastinit/sbi-02.xml`
- `beast-source/examples/starbeastinit/sbi-03.xml`
- `beast-source/examples/testEBSP.xml`
- `beast-source/examples/testRandomLocalClock.xml`
- `beast-source/examples/testYuleUncalibrated.xml`
- `beast-source/examples/testSeqGen.xml`
- `beast-source/examples/testYuleCalibrated.xml`
- `beast-source/myxml.xml`
- `golden/asian-4regions-a0/MASCOT6_glmcontinuous_asia_1201_a0.xml`
- `golden/asian-4regions-a0-888/MASCOT6_glmcontinuous_asia_1201_a0.xml`
- `MASCOT6_glm_mig_ind_Ne_a0.xml`
- `MASCOT6_glm_mig_ind_Ne.xml`
- `examples/GLM.xml`
- `examples/h9n2_mascot_869_weight_change_maxR_10.xml`
- `examples/ConstantBSSVS.xml`
- `examples/Skyline.xml`
- `new/MASCOT6_glmContinuous_asia_1204.xml`
- `new/MASCOT6_glmcontinuous_asia_a0.xml`
- `new/MASCOT6_glmContinuous_alphacon_1209.xml`
- `new/MASCOT6_glmContinuous_a0.xml`
- `new/MASCOT6_glmContinuous_1204.xml`
- `new/run1/MASCOT6_glmcontinuous_asia_a0.xml`
- `new/MASCOT6_glmContinuous_a_close0.xml`
- `MASCOT6_glm_pop_unim2.xml`
- `MASCOT6_glm_mig_ind_Ne_anarrow.xml`
- `runs/upper-migration-10/MASCOT6_glm_mig_ind_Ne.xml`
- `runs/upper-migration-0.1/MASCOT6_glm_mig_ind_Ne.xml`

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**No `beast.base.spec.*` references in body** (14):

- `beast-source/release/Mac/BEAST/BEAST 2.2.0/templates/SubstModels.xml`
- `beast-source/release/Linux/BEAST/templates/SubstModels.xml`
- `beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/templates/SubstModels.xml`
- `beast-source/_site/release/Linux/BEAST/templates/SubstModels.xml`
- `beastfx-source/fxtemplates/ClockModels.xml`
- `beastfx-source/fxtemplates/Standard.xml`
- `beastfx-source/fxtemplates/ParametricDistributions.xml`
- `beastfx-source/fxtemplates/TreePriors.xml`
- `beastfx-source/fxtemplates/SubstModels.xml`
- `src/main/resources/mascot/fxtemplates/MascotTreePriorTemplate.xml`
- `src/main/resources/mascot/fxtemplates/MascotConstant.xml`
- `src/main/resources/mascot/fxtemplates/MascotSkyline.xml`
- `src/main/resources/mascot/fxtemplates/MascotConstantBSSVS.xml`
- `src/main/resources/mascot/fxtemplates/MascotGLM.xml`

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testStarBeast.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/bitflip.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |

**`beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testRelaxedClock.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`beast-source/release/Mac/BEAST/BEAST 2.2.0/examples/testRandomLocalClock.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`beast-source/release/Mac/BEAST/BEAST 2.2.0/templates/Standard.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/release/Mac/BEAST/BEAST 2.2.0/templates/StarBeast.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/release/Mac/BEAST/BEAST 2.2.0/templates/SubstModels.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `LaplaceDistribution` → `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |

**`beast-source/release/Linux/BEAST/examples/testStarBeast.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/release/Linux/BEAST/examples/bitflip.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |

**`beast-source/release/Linux/BEAST/examples/testRelaxedClock.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`beast-source/release/Linux/BEAST/examples/testRandomLocalClock.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`beast-source/release/Linux/BEAST/templates/Standard.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/release/Linux/BEAST/templates/StarBeast.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/release/Linux/BEAST/templates/SubstModels.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `LaplaceDistribution` → `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |

**`beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testStarBeast.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/bitflip.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |

**`beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testRelaxedClock.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/examples/testRandomLocalClock.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/templates/Standard.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/templates/StarBeast.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/_site/release/Mac/BEAST/BEAST 2.2.0/templates/SubstModels.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `LaplaceDistribution` → `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |

**`beast-source/_site/release/Linux/BEAST/examples/testStarBeast.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/_site/release/Linux/BEAST/examples/bitflip.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |

**`beast-source/_site/release/Linux/BEAST/examples/testRelaxedClock.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`beast-source/_site/release/Linux/BEAST/examples/testRandomLocalClock.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`beast-source/_site/release/Linux/BEAST/templates/Standard.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/_site/release/Linux/BEAST/templates/StarBeast.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/_site/release/Linux/BEAST/templates/SubstModels.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `LaplaceDistribution` → `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |

**`beast-source/examples/benchmark/II/testHKY1748.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/benchmark/II/testHKY1749.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/benchmark/II/testHKY1366.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/benchmark/II/testHKY767.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/benchmark/II/testHKY1809.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/benchmark/II/testHKY501.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/benchmark/II/testHKY3475.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/benchmark/II/testHKY1510.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/benchmark/II/testHKY1044.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/benchmark/II/testHKY520.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/benchmark/II/testHKY336.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/benchmark/II/testHKY755.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/testJukesCantorShortUncertain2.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-source/examples/testExponentialGrowth.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`beast-source/examples/testMultipleAlignments_randomTaxaOrder.xml`** (17):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
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

**`beast-source/examples/testCoalescent.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`beast-source/examples/testGTR.xml`** (14):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
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

**`beast-source/examples/testOpSubSchedule.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/testBSP.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.MarkovChainDistribution` | `beast.base.spec.inference.distribution.MarkovChainDistribution` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |

**`beast-source/examples/testPlates.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-source/examples/testJukesCantorShortUncertain.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-source/examples/testSRD06.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.alignment.FilteredAlignment` | `beast.base.spec.evolution.alignment.FilteredAlignment` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-source/examples/testHKY.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/testJukesCantorShortUncertain2MLE.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-source/examples/testRestrictedGTR.xml`** (14):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
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

**`beast-source/examples/testRNA.xml`** (14):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
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

**`beast-source/examples/parameterised/RSV2.xml`** (13):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
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

**`beast-source/examples/testSimulatedAlignment.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`beast-source/examples/testSYM.xml`** (14):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
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

**`beast-source/examples/testStarBeast.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/examples/testJukesCantorShort.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-source/examples/bitflip.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |

**`beast-source/examples/testClassicRootCalibrationPrior.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/testDirectSimulator2.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`beast-source/examples/testTN93.xml`** (14):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
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

**`beast-source/examples/testStarBeastFBD.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/examples/testJukesCantor.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-source/examples/testTIM.xml`** (14):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
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

**`beast-source/examples/testConditionalRootCalibrationPrior.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/testDirectSimulator.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |

**`beast-source/examples/testCalibration.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |

**`beast-source/examples/testTipDates.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |

**`beast-source/examples/testTipDates2.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-source/examples/testTVM.xml`** (14):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
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

**`beast-source/examples/testCalYule_5t_2c.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |

**`beast-source/examples/testDirectSimulatorHierarchical.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |

**`beast-source/examples/beast2vs1/testStrictClockTipDatesSampling.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/beast2vs1/testStarBEASTLinear.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/examples/beast2vs1/testBSP1.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.MarkovChainDistribution` | `beast.base.spec.inference.distribution.MarkovChainDistribution` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |

**`beast-source/examples/beast2vs1/testExponentialGrowth.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |

**`beast-source/examples/beast2vs1/testYuleModel_10taxa.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-source/examples/beast2vs1/testBirthDeathModel_10taxa.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-source/examples/beast2vs1/testSRD06CP12_3.xml`** (13):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.alignment.FilteredAlignment` | `beast.base.spec.evolution.alignment.FilteredAlignment` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/beast2vs1/testStarBEASTConstant.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/examples/beast2vs1/testHKY.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-source/examples/beast2vs1/testStrictClock2.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/beast2vs1/testCoalescentTipDates1.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-source/examples/beast2vs1/testCoalescentTipDates.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-source/examples/beast2vs1/testUCRelaxedClockLogNormal.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`beast-source/examples/beast2vs1/testStarBEAST.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/examples/beast2vs1/testStarBeast2.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/examples/beast2vs1/testCoalescentNoClock.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/beast2vs1/testMultiSubstModel.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-source/examples/beast2vs1/testCoalescentTipDatesSampling.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |

**`beast-source/examples/beast2vs1/testCalibration.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |

**`beast-source/examples/beast2vs1/testTipDates.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-source/examples/beast2vs1/testStarBEASTLinearConstRoot.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-source/examples/beast2vs1/testCalibrationMono.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |

**`beast-source/examples/beast2vs1/testSiteModelAlpha.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/beast2vs1/testStrictClock.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/beast2vs1/testStrictClockTipTime.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/beast2vs1/testEBSP.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.Sum` | `beast.base.spec.evolution.Sum` |
| `spec=` | `beast.base.evolution.tree.coalescent.ScaledPopulationFunction` | `beast.base.spec.evolution.tree.coalescent.ScaledPopulationFunction` |
| `spec=` | `beast.base.evolution.tree.coalescent.CompoundPopulationFunction` | `beast.base.spec.evolution.tree.coalescent.CompoundPopulationFunction` |
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.evolution.tree.coalescent.SampleOffValues` | `beast.base.spec.inference.operator.SampleOffValues` |

**`beast-source/examples/beast2vs1/testBirthDeathAsYule.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/beast2vs1/testRandomLocalClock.xml`** (12):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |

**`beast-source/examples/beast2vs1/testBSPNoClock.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.MarkovChainDistribution` | `beast.base.spec.inference.distribution.MarkovChainDistribution` |

**`beast-source/examples/testRelaxedClock.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |

**`beast-source/examples/testTwoCalibrationsPrior.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`beast-source/examples/testSliceHKY.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-source/examples/starbeastinit/sbi-01.xml`** (6):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |
| `spec=` | `StarBeastStartState` → `beast.base.evolution.speciation.StarBeastStartState` | _(no spec equivalent found)_ |

**`beast-source/examples/starbeastinit/sbi-02.xml`** (6):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |
| `spec=` | `StarBeastStartState` → `beast.base.evolution.speciation.StarBeastStartState` | _(no spec equivalent found)_ |

**`beast-source/examples/starbeastinit/sbi-03.xml`** (6):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |
| `spec=` | `StarBeastStartState` → `beast.base.evolution.speciation.StarBeastStartState` | _(no spec equivalent found)_ |

**`beast-source/examples/testEBSP.xml`** (20):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.Sum` | `beast.base.spec.evolution.Sum` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.evolution.tree.coalescent.ScaledPopulationFunction` | `beast.base.spec.evolution.tree.coalescent.ScaledPopulationFunction` |
| `spec=` | `beast.base.evolution.tree.coalescent.CompoundPopulationFunction` | `beast.base.spec.evolution.tree.coalescent.CompoundPopulationFunction` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.SampleOffValues` | `beast.base.spec.inference.operator.SampleOffValues` |

**`beast-source/examples/testRandomLocalClock.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`beast-source/examples/testYuleUncalibrated.xml`** (12):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-source/examples/testSeqGen.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |

**`beast-source/examples/testYuleCalibrated.xml`** (12):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.speciation.CalibratedYuleModel` | `beast.base.spec.evolution.speciation.CalibratedYuleModel` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-source/myxml.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |

**`beastfx-source/fxtemplates/ClockModels.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.operator.AdaptableOperatorSampler` | `beast.base.spec.evolution.operator.AdaptableOperatorSampler` |
| `spec=` | `beast.base.evolution.Sum` | `beast.base.spec.evolution.Sum` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |

**`beastfx-source/fxtemplates/Standard.xml`** (28):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `spec=` | `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.evolution.operator.AdaptableOperatorSampler` | `beast.base.spec.evolution.operator.AdaptableOperatorSampler` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
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

**`beastfx-source/fxtemplates/ParametricDistributions.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `spec=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `spec=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `spec=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |
| `spec=` | `beast.base.inference.distribution.Dirichlet` | `beast.base.spec.inference.distribution.Dirichlet` |

**`beastfx-source/fxtemplates/TreePriors.xml`** (18):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BactrianScaleOperator` → `beast.base.evolution.operator.kernel.BactrianScaleOperator` | `beast.base.spec.inference.operator.ScaleOperator` |
| `spec=` | `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.operator.kernel.BactrianScaleOperator` | `beast.base.spec.inference.operator.ScaleOperator` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `LaplaceDistribution` → `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `spec=` | `beast.base.inference.distribution.MarkovChainDistribution` | `beast.base.spec.inference.distribution.MarkovChainDistribution` |
| `spec=` | `beast.base.evolution.tree.coalescent.ScaledPopulationFunction` | `beast.base.spec.evolution.tree.coalescent.ScaledPopulationFunction` |
| `spec=` | `beast.base.evolution.tree.coalescent.CompoundPopulationFunction` | `beast.base.spec.evolution.tree.coalescent.CompoundPopulationFunction` |
| `spec=` | `beast.base.evolution.Sum` | `beast.base.spec.evolution.Sum` |
| `spec=` | `beast.base.evolution.tree.coalescent.SampleOffValues` | `beast.base.spec.inference.operator.SampleOffValues` |

**`beastfx-source/fxtemplates/SubstModels.xml`** (32):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.evolution.operator.AdaptableOperatorSampler` | `beast.base.spec.evolution.operator.AdaptableOperatorSampler` |
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Dirichlet` | `beast.base.spec.inference.distribution.Dirichlet` |
| `spec=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `spec=` | `beast.base.evolution.substitutionmodel.MutationDeathModel` | `beast.base.spec.evolution.substitutionmodel.MutationDeathModel` |

**`examples/Constant.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |

**`examples/GLM.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |

**`examples/ConstantBSSVS.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |

**`examples/Skyline.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |

**`src/main/resources/mascot/fxtemplates/MascotTreePriorTemplate.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |

**`src/main/resources/mascot/fxtemplates/MascotSkyline.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.evolution.operator.AdaptableOperatorSampler` | `beast.base.spec.evolution.operator.AdaptableOperatorSampler` |
| `spec=` | `beast.base.evolution.Sum` | `beast.base.spec.evolution.Sum` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |

**`src/main/resources/mascot/fxtemplates/MascotConstantBSSVS.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.evolution.Sum` | `beast.base.spec.evolution.Sum` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |

**`src/main/resources/mascot/fxtemplates/MascotGLM.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `spec=` | `beast.base.evolution.Sum` | `beast.base.spec.evolution.Sum` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |


# Mascot — what's left

> **Scanned at:** 2026-05-11T16:47:21.466888  
> **Commit:** `b7b7295` on `update-citations-and-readme` — [view on GitHub](https://github.com/CompEvol/Mascot/commit/b7b72958436854fbe07d91bb82fed3d3de2aa91d)  
> **Pom version:** `3.1.0-beta1`  
> **Maven Central:** `io.github.compevol:mascot:3.1.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 4 on spec, 0 mixed, 1 legacy of 121 total
- **Example XMLs:** 1 on spec / 1 on `version="2.8"` / 229 total (+55 under legacy/)
- **BEAUti fxtemplates:** 0 clean / 0 use spec / 14 total
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

**Needs `version="2.8"`** (228):

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


# beast3 — what's left

> **Scanned at:** 2026-05-07T19:58:07.294750  
> **Local checkout:** `/Users/adru001/Git/beast3` — commit `4e1ee72` on `target-acceptance-1d-operators` — [view on GitHub](https://github.com/CompEvol/beast3/commit/4e1ee72a7cead616230d9dce5cb43e71400615e1)  
> **Pom version:** `2.8.0-SNAPSHOT`  
> **Maven Central:** `io.github.compevol:beast3:2.8.0-beta5`  
> **Stage hint:** Maven Central
>
> Core. Multi-module — beast-base / beast-fx / beast-pkgmgmt / beast-test-utils.

## Summary

- **Java classes:** 147 on spec, 7 mixed, 73 legacy of 604 total
- **XMLs:** 0 on spec / 0 on `version="2.8"` / 93 total (+82 under legacy/)
- **Maven Central:** 2.8.0-beta5

## Java classes pending migration

### Distributions — 11 legacy, 1 mixed (of 59 total)

**Mixed** (already imports spec; finish removing legacy):

- `beast.base.spec.inference.distribution.Prior` — uses `ParametricDistribution`

**Legacy** (no spec imports yet):

- `beast.base.evolution.speciation.CalibratedBirthDeathModel` — uses `RealParameter`
- `beast.base.evolution.speciation.CalibratedYuleModel` — uses `ParametricDistribution`, `RealParameter`
- `beast.base.evolution.speciation.GeneTreeForSpeciesTreeDistribution` — uses `RealParameter`
- `beast.base.evolution.speciation.SpeciesTreePopFunction` — uses `RealParameter`
- `beast.base.evolution.speciation.YuleModel` — uses `RealParameter`
- `beast.base.evolution.tree.MRCAPrior` — uses `ParametricDistribution`
- `beast.base.evolution.tree.coalescent.BayesianSkyline` — uses `IntegerParameter`
- `beast.base.inference.distribution.ChiSquare` — uses `IntegerParameter`
- `beast.base.inference.distribution.LogNormalDistributionModel` — uses `RealParameter`
- `beast.base.inference.distribution.Poisson` — uses `RealParameter`
- `beast.base.inference.distribution.Prior` — uses `RealParameter`, `IntegerParameter`

### Operators — 21 legacy, 1 mixed (of 45 total)

**Mixed** (already imports spec; finish removing legacy):

- `beast.base.inference.operator.UniformOperator` — uses `RealParameter`, `IntegerParameter`, `Parameter`

**Legacy** (no spec imports yet):

- `beast.base.evolution.operator.AdaptableOperatorSampler` — uses `RealParameter`, `CompoundRealParameter`, `IntegerParameter`
- `beast.base.evolution.operator.EpochFlexOperator` — uses `IntegerParameter`
- `beast.base.evolution.operator.ScaleOperator` — uses `BooleanParameter`, `RealParameter`
- `beast.base.evolution.operator.SubtreeSlide` — uses `RealParameter`
- `beast.base.evolution.operator.TipDatesScaler` — uses `RealParameter`
- `beast.base.evolution.operator.Uniform` — uses `RealParameter`
- `beast.base.evolution.operator.WilsonBalding` — uses `RealParameter`
- `beast.base.evolution.operator.kernel.AdaptableVarianceMultivariateNormalOperator` — uses `RealParameter`
- `beast.base.evolution.operator.kernel.BactrianScaleOperator` — uses `BooleanParameter`, `RealParameter`
- `beast.base.evolution.tree.coalescent.SampleOffValues` — uses `BooleanParameter`, `ParametricDistribution`, `RealParameter`
- `beast.base.inference.operator.BitFlipOperator` — uses `BooleanParameter`
- `beast.base.inference.operator.DeltaExchangeOperator` — uses `RealParameter`, `IntegerParameter`
- `beast.base.inference.operator.IntRandomWalkOperator` — uses `IntegerParameter`
- `beast.base.inference.operator.IntUniformOperator` — uses `IntegerParameter`
- `beast.base.inference.operator.RealRandomWalkOperator` — uses `RealParameter`
- `beast.base.inference.operator.SwapOperator` — uses `RealParameter`, `IntegerParameter`, `BooleanParameter`, `Parameter`
- `beast.base.inference.operator.UpDownOperator` — uses `RealParameter`, `Parameter`
- `beast.base.inference.operator.kernel.BactrianDeltaExchangeOperator` — uses `RealParameter`, `IntegerParameter`
- `beast.base.inference.operator.kernel.BactrianIntervalOperator` — uses `RealParameter`
- `beast.base.inference.operator.kernel.BactrianRandomWalkOperator` — uses `RealParameter`
- `beast.base.inference.operator.kernel.BactrianUpDownOperator` — uses `RealParameter`, `Parameter`

### Loggers — 4 legacy, 0 mixed (of 16 total)

**Legacy** (no spec imports yet):

- `beast.base.evolution.Sum` — uses `IntegerParameter`, `BooleanParameter`
- `beast.base.evolution.TreeWithMetaDataLogger` — uses `RealParameter`, `Parameter`
- `beast.base.evolution.speciation.SpeciesTreeLogger` — uses `Parameter`
- `beast.base.evolution.tree.coalescent.CompoundPopulationFunction` — uses `BooleanParameter`, `RealParameter`

### CalcNodes — 1 legacy, 0 mixed (of 17 total)

**Legacy** (no spec imports yet):

- `beast.base.evolution.substitutionmodel.Frequencies` — uses `RealParameter`

### Parameters — 1 legacy, 0 mixed (of 5 total)

**Legacy** (no spec imports yet):

- `beast.base.inference.parameter.CompoundRealParameter` — uses `RealParameter`

### StateNodes — 2 legacy, 1 mixed (of 16 total)

**Mixed** (already imports spec; finish removing legacy):

- `beast.base.spec.evolution.tree.coalescent.RandomTree` — uses `ParametricDistribution`

**Legacy** (no spec imports yet):

- `beast.base.evolution.speciation.StarBeastStartState` — uses `RealParameter`
- `beast.base.evolution.tree.coalescent.RandomTree` — uses `ParametricDistribution`

### Other — 33 legacy, 4 mixed (of 446 total)

**Mixed** (already imports spec; finish removing legacy):

- `beastfx.app.beauti.PriorListInputEditor` — uses `Prior`, `RealParameter`
- `beastfx.app.inputeditor.BeautiDoc` — uses `Parameter`, `Prior`
- `beastfx.app.inputeditor.ParametricDistributionInputEditor` — uses `Prior`, `ParametricDistribution`, `RealParameter`
- `beastfx.app.methodsection.Phrase` — uses `RealParameter`, `Parameter`

**Legacy** (no spec imports yet):

- `beast.base.evolution.alignment.FilteredAlignment` — uses `IntegerParameter`
- `beast.base.evolution.branchratemodel.RandomLocalClockModel` — uses `BooleanParameter`, `RealParameter`
- `beast.base.evolution.branchratemodel.StrictClockModel` — uses `RealParameter`
- `beast.base.evolution.branchratemodel.UCRelaxedClockModel` — uses `ParametricDistribution`, `RealParameter`, `IntegerParameter`
- `beast.base.evolution.operator.TreeStretchOperator` — uses `RealParameter`
- `beast.base.evolution.operator.kernel.BactrianOperatorSchedule` — uses `RealParameter`
- `beast.base.evolution.sitemodel.SiteModel` — uses `RealParameter`
- `beast.base.evolution.speciation.BirthDeathGernhard08Model` — uses `RealParameter`
- `beast.base.evolution.speciation.CalibrationPoint` — uses `ParametricDistribution`
- `beast.base.evolution.speciation.SpeciesTreePrior` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.BinaryCovarion` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.EmpiricalSubstitutionModel` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.GTR` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.HKY` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.SYM` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.TIM` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.TN93` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.TVM` — uses `RealParameter`
- `beast.base.evolution.tree.coalescent.ExponentialGrowth` — uses `RealParameter`
- `beast.base.inference.distribution.DirichletSimulator` — uses `RealParameter`
- `beast.base.inference.operator.CompoundParameterHelper` — uses `Parameter`
- `beast.base.inference.operator.kernel.Transform` — uses `RealParameter`
- `beast.base.parser.JSONParser` — uses `Parameter`
- `beast.base.parser.JSONProducer` — uses `Parameter`
- `beast.base.parser.NexusParser` — uses `ParametricDistribution`, `RealParameter`
- `beast.base.parser.XMLParser` — uses `RealParameter`, `Parameter`
- `beastfx.app.beauti.ClockModelListInputEditor` — uses `RealParameter`, `IntegerParameter`
- `beastfx.app.beauti.PriorInputEditor` — uses `Prior`, `ParametricDistribution`, `RealParameter`, `IntegerParameter`
- `beastfx.app.inputeditor.ParameterInputEditor` — uses `ParametricDistribution`, `Parameter`
- `beastfx.app.inputeditor.SiteModelInputEditor` — uses `RealParameter`, `IntegerParameter`
- `beastfx.app.methodsection.implementation.ParameterMethodsText` — uses `Parameter`
- `beastfx.app.methodsection.implementation.ParametricDistributionMethodsText` — uses `ParametricDistribution`
- `beastfx.app.methodsection.implementation.PriorMethodsText` — uses `Prior`

## XMLs pending migration

**Needs `version="2.8"`** (93):

- `beast-fx/src/main/resources/beast.fx/fxtemplates/ClockModels.xml`
- `beast-fx/src/main/resources/beast.fx/fxtemplates/Standard.xml`
- `beast-fx/src/main/resources/beast.fx/fxtemplates/ParametricDistributions.xml`
- `beast-fx/src/main/resources/beast.fx/fxtemplates/TreePriors.xml`
- `beast-fx/src/main/resources/beast.fx/fxtemplates/SubstModels.xml`
- `beast-fx/src/main/resources/beast.fx/fxtemplates/ClockModels.xml`
- `beast-fx/src/main/resources/beast.fx/fxtemplates/Standard.xml`
- `beast-fx/src/main/resources/beast.fx/fxtemplates/ParametricDistributions.xml`
- `beast-fx/src/main/resources/beast.fx/fxtemplates/TreePriors.xml`
- `beast-fx/src/main/resources/beast.fx/fxtemplates/SubstModels.xml`
- `release/Mac/output/BEAST.app/Contents/fxtemplates/ClockModels.xml`
- `release/Mac/output/BEAST.app/Contents/fxtemplates/Standard.xml`
- `release/Mac/output/BEAST.app/Contents/fxtemplates/ParametricDistributions.xml`
- `release/Mac/output/BEAST.app/Contents/fxtemplates/TreePriors.xml`
- `release/Mac/output/BEAST.app/Contents/fxtemplates/SubstModels.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY1748.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY1749.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY1366.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY767.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY1809.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY501.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY3475.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY1510.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY1044.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY520.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY336.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY755.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/1/testHKY1748.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/1/testHKY1749.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/1/testHKY1366.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/1/testHKY767.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/1/testHKY1809.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/1/testHKY501.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/1/testHKY3475.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/1/testHKY1510.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/1/testHKY1044.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/1/testHKY520.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/1/testHKY336.xml`
- `beast-base/src/test/resources/beast.base/examples/benchmark/1/testHKY755.xml`
- `beast-base/src/test/resources/beast.base/examples/testJukesCantorShortUncertain2.xml`
- `beast-base/src/test/resources/beast.base/examples/testExponentialGrowth.xml`
- `beast-base/src/test/resources/beast.base/examples/testMultipleAlignments_randomTaxaOrder.xml`
- `beast-base/src/test/resources/beast.base/examples/testCoalescent.xml`
- `beast-base/src/test/resources/beast.base/examples/testGTR.xml`
- `beast-base/src/test/resources/beast.base/examples/testOpSubSchedule.xml`
- `beast-base/src/test/resources/beast.base/examples/testBSP.xml`
- `beast-base/src/test/resources/beast.base/examples/testPlates.xml`
- `beast-base/src/test/resources/beast.base/examples/testJukesCantorShortUncertain.xml`
- `beast-base/src/test/resources/beast.base/examples/testSRD06.xml`
- `beast-base/src/test/resources/beast.base/examples/testHKY.xml`
- `beast-base/src/test/resources/beast.base/examples/testJukesCantorShortUncertain2MLE.xml`
- `beast-base/src/test/resources/beast.base/examples/testRestrictedGTR.xml`
- `beast-base/src/test/resources/beast.base/examples/testRNA.xml`
- `beast-base/src/test/resources/beast.base/examples/parameterised/RSV2.xml`
- `beast-base/src/test/resources/beast.base/examples/testYuleOneSite.xml`
- `beast-base/src/test/resources/beast.base/examples/testSimulatedAlignment.xml`
- `beast-base/src/test/resources/beast.base/examples/testSYM.xml`
- `beast-base/src/test/resources/beast.base/examples/testStarBeast.xml`
- `beast-base/src/test/resources/beast.base/examples/testJukesCantorShort.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/testGTR.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/testBSP.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/testHKY.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/testTN93.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/testEBSP.xml`
- `beast-base/src/test/resources/beast.base/examples/bitflip.xml`
- `beast-base/src/test/resources/beast.base/examples/testClassicRootCalibrationPrior.xml`
- `beast-base/src/test/resources/beast.base/examples/testDirectSimulator2.xml`
- `beast-base/src/test/resources/beast.base/examples/testTN93.xml`
- `beast-base/src/test/resources/beast.base/examples/testStarBeastFBD.xml`
- `beast-base/src/test/resources/beast.base/examples/testJukesCantor.xml`
- `beast-base/src/test/resources/beast.base/examples/testTIM.xml`
- `beast-base/src/test/resources/beast.base/examples/testConditionalRootCalibrationPrior.xml`
- `beast-base/src/test/resources/beast.base/examples/testDirectSimulator.xml`
- `beast-base/src/test/resources/beast.base/examples/testCalibration.xml`
- `beast-base/src/test/resources/beast.base/examples/testTipDates.xml`
- `beast-base/src/test/resources/beast.base/examples/testTipDates2.xml`
- `beast-base/src/test/resources/beast.base/examples/testTVM.xml`
- `beast-base/src/test/resources/beast.base/examples/testCalYule_5t_2c.xml`
- `beast-base/src/test/resources/beast.base/examples/testDirectSimulatorHierarchical.xml`
- `beast-base/src/test/resources/beast.base/examples/testRelaxedClock.xml`
- `beast-base/src/test/resources/beast.base/examples/testTwoCalibrationsPrior.xml`
- `beast-base/src/test/resources/beast.base/examples/testDirichlet/testDirichlet.xml`
- `beast-base/src/test/resources/beast.base/examples/testDirichlet/testDirichlet2.xml`
- `beast-base/src/test/resources/beast.base/examples/testDirichlet/testDirichletNoPrior.xml`
- `beast-base/src/test/resources/beast.base/examples/testDirichlet/testDirichletBact.xml`
- `beast-base/src/test/resources/beast.base/examples/starbeastinit/sbi-01.xml`
- `beast-base/src/test/resources/beast.base/examples/starbeastinit/sbi-02.xml`
- `beast-base/src/test/resources/beast.base/examples/starbeastinit/sbi-03.xml`
- `beast-base/src/test/resources/beast.base/examples/testEBSP.xml`
- `beast-base/src/test/resources/beast.base/examples/testRandomLocalClock.xml`
- `beast-base/src/test/resources/beast.base/examples/testYuleUncalibrated.xml`
- `beast-base/src/test/resources/beast.base/examples/testSeqGen.xml`
- `beast-base/src/test/resources/beast.base/examples/testYuleCalibrated.xml`


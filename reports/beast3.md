# beast3 — what's left

> **Scanned at:** 2026-05-07T20:26:04.380827  
> **Local checkout:** `/Users/adru001/Git/beast3` — commit `4e1ee72` on `target-acceptance-1d-operators` — [view on GitHub](https://github.com/CompEvol/beast3/commit/4e1ee72a7cead616230d9dce5cb43e71400615e1)  
> **Pom version:** `2.8.0-SNAPSHOT`  
> **Maven Central:** `io.github.compevol:beast3:2.8.0-beta5`  
> **Stage hint:** Maven Central
>
> Core. Multi-module — beast-base / beast-fx / beast-pkgmgmt / beast-test-utils.

## Summary

- **Java classes:** 177 on spec, 29 mixed, 64 legacy of 604 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 78 total (+81 under legacy/)
- **BEAUti fxtemplates:** 6 clean / 10 use spec / 10 total
- **Input rule:** 56 classes hold 105 Input(s) declared too concretely
- **Maven Central:** 2.8.0-beta5

## Java classes pending migration

### Distributions — 0 legacy, 15 mixed (of 70 total)

**Mixed** (already imports spec; finish removing legacy):

- `beast.base.evolution.speciation.BirthDeathGernhard08Model` — uses `RealParameter`
- `beast.base.evolution.speciation.CalibratedBirthDeathModel` — uses `RealParameter`
- `beast.base.evolution.speciation.CalibratedYuleModel` — uses `ParametricDistribution`, `RealParameter`
- `beast.base.evolution.speciation.GeneTreeForSpeciesTreeDistribution` — uses `RealParameter`
- `beast.base.evolution.speciation.SpeciesTreePopFunction` — uses `RealParameter`
- `beast.base.evolution.speciation.SpeciesTreePrior` — uses `RealParameter`
- `beast.base.evolution.speciation.YuleModel` — uses `RealParameter`
- `beast.base.evolution.tree.MRCAPrior` — uses `ParametricDistribution`
- `beast.base.evolution.tree.coalescent.BayesianSkyline` — uses `IntegerParameter`
- `beast.base.inference.distribution.ChiSquare` — uses `IntegerParameter`
- `beast.base.inference.distribution.LogNormal`
- `beast.base.inference.distribution.LogNormalDistributionModel` — uses `RealParameter`
- `beast.base.inference.distribution.Poisson` — uses `RealParameter`
- `beast.base.inference.distribution.Prior` — uses `RealParameter`, `IntegerParameter`
- `beast.base.spec.inference.distribution.Prior` — uses `ParametricDistribution`

### Operators — 22 legacy, 1 mixed (of 47 total)

**Mixed** (already imports spec; finish removing legacy):

- `beast.base.inference.operator.UniformOperator` — uses `RealParameter`, `IntegerParameter`, `Parameter`

**Legacy** (no spec imports yet):

- `beast.base.evolution.operator.AdaptableOperatorSampler` — uses `RealParameter`, `CompoundRealParameter`, `IntegerParameter`
- `beast.base.evolution.operator.EpochFlexOperator` — uses `IntegerParameter`
- `beast.base.evolution.operator.ScaleOperator` — uses `BooleanParameter`, `RealParameter`
- `beast.base.evolution.operator.SubtreeSlide` — uses `RealParameter`
- `beast.base.evolution.operator.TipDatesScaler` — uses `RealParameter`
- `beast.base.evolution.operator.TreeStretchOperator` — uses `RealParameter`
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

### CalcNodes — 2 legacy, 1 mixed (of 20 total)

**Mixed** (already imports spec; finish removing legacy):

- `beast.base.spec.evolution.speciation.RandomGeneTree`

**Legacy** (no spec imports yet):

- `beast.base.evolution.speciation.RandomGeneTree`
- `beast.base.evolution.substitutionmodel.Frequencies` — uses `RealParameter`

### Parameters — 0 legacy, 1 mixed (of 5 total)

**Mixed** (already imports spec; finish removing legacy):

- `beast.base.inference.parameter.CompoundRealParameter` — uses `RealParameter`

### StateNodes — 3 legacy, 1 mixed (of 22 total)

**Mixed** (already imports spec; finish removing legacy):

- `beast.base.spec.evolution.tree.coalescent.RandomTree` — uses `ParametricDistribution`

**Legacy** (no spec imports yet):

- `beast.base.evolution.alignment.FilteredAlignment` — uses `IntegerParameter`
- `beast.base.evolution.speciation.StarBeastStartState` — uses `RealParameter`
- `beast.base.evolution.tree.coalescent.RandomTree` — uses `ParametricDistribution`

### Other — 33 legacy, 10 mixed (of 424 total)

**Mixed** (already imports spec; finish removing legacy):

- `beast.base.parser.XMLProducer`
- `beastfx.app.beauti.PriorListInputEditor` — uses `Prior`, `RealParameter`
- `beastfx.app.inputeditor.BeautiDoc` — uses `Parameter`, `Prior`
- `beastfx.app.inputeditor.ParameterInputEditor` — uses `ParametricDistribution`, `Parameter`
- `beastfx.app.inputeditor.ParametricDistributionInputEditor` — uses `Prior`, `ParametricDistribution`, `RealParameter`
- `beastfx.app.inputeditor.SiteModelInputEditor` — uses `RealParameter`, `IntegerParameter`
- `beastfx.app.methodsection.CitationPhrase`
- `beastfx.app.methodsection.PartitionPhrase`
- `beastfx.app.methodsection.Phrase` — uses `RealParameter`, `Parameter`
- `beastfx.app.methodsection.SectionPhrase`

**Legacy** (no spec imports yet):

- `beast.base.evolution.branchratemodel.RandomLocalClockModel` — uses `BooleanParameter`, `RealParameter`
- `beast.base.evolution.branchratemodel.StrictClockModel` — uses `RealParameter`
- `beast.base.evolution.branchratemodel.UCRelaxedClockModel` — uses `ParametricDistribution`, `RealParameter`, `IntegerParameter`
- `beast.base.evolution.operator.kernel.BactrianOperatorSchedule` — uses `RealParameter`
- `beast.base.evolution.sitemodel.SiteModel` — uses `RealParameter`
- `beast.base.evolution.speciation.CalibrationPoint` — uses `ParametricDistribution`
- `beast.base.evolution.substitutionmodel.BinaryCovarion` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.Blosum62`
- `beast.base.evolution.substitutionmodel.CPREV`
- `beast.base.evolution.substitutionmodel.Dayhoff`
- `beast.base.evolution.substitutionmodel.EmpiricalSubstitutionModel` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.GTR` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.HKY` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.JTT`
- `beast.base.evolution.substitutionmodel.MTREV`
- `beast.base.evolution.substitutionmodel.SYM` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.TIM` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.TN93` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.TVM` — uses `RealParameter`
- `beast.base.evolution.substitutionmodel.WAG`
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
- `beastfx.app.methodsection.implementation.ParameterMethodsText` — uses `Parameter`
- `beastfx.app.methodsection.implementation.ParametricDistributionMethodsText` — uses `ParametricDistribution`
- `beastfx.app.methodsection.implementation.PriorMethodsText` — uses `Prior`

## Inputs declared too concretely

> Concrete spec params (`RealScalarParam`, `RealVectorParam`, …) belong only on Operators, which need to write the parameter. Distributions, CalcNodes, Loggers and other read-only holders should declare the interface (`RealScalar`, `RealVector`, …) so adapters and transforms can be substituted. Legacy `RealParameter` / `Function` Inputs are violations everywhere.

### Distributions (19)

- `beast.base.evolution.speciation.BirthDeathGernhard08Model`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
- `beast.base.evolution.speciation.CalibratedBirthDeathModel`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
- `beast.base.evolution.speciation.CalibratedYuleModel`
    - legacy: `Input<RealParameter>`
- `beast.base.evolution.speciation.SpeciesTreePopFunction`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
- `beast.base.evolution.speciation.SpeciesTreePrior`
    - legacy: `Input<RealParameter>`
- `beast.base.evolution.speciation.YuleModel`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
- `beast.base.evolution.tree.coalescent.BayesianSkyline`
    - legacy: `Input<Function>`
    - legacy: `Input<IntegerParameter>`
- `beast.base.inference.distribution.Beta`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.inference.distribution.ChiSquare`
    - legacy: `Input<IntegerParameter>`
- `beast.base.inference.distribution.Dirichlet`
    - legacy: `Input<Function>`
- `beast.base.inference.distribution.Exponential`
    - legacy: `Input<Function>`
- `beast.base.inference.distribution.Gamma`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.inference.distribution.InverseGamma`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.inference.distribution.LaplaceDistribution`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.inference.distribution.LogNormalDistributionModel`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.inference.distribution.MarkovChainDistribution`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.inference.distribution.Normal`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.inference.distribution.Poisson`
    - legacy: `Input<Function>`
- `beast.base.inference.distribution.Prior`
    - legacy: `Input<Function>`

### Operators (13)

- `beast.base.evolution.operator.EpochFlexOperator`
    - legacy: `Input<IntegerParameter>`
- `beast.base.evolution.operator.ScaleOperator`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<BooleanParameter>`
- `beast.base.evolution.operator.TreeStretchOperator`
    - legacy: `Input<RealParameter>`
- `beast.base.evolution.tree.coalescent.SampleOffValues`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<BooleanParameter>`
- `beast.base.inference.operator.BitFlipOperator`
    - legacy: `Input<BooleanParameter>`
- `beast.base.inference.operator.DeltaExchangeOperator`
    - legacy: `Input<IntegerParameter>`
- `beast.base.inference.operator.IntRandomWalkOperator`
    - legacy: `Input<IntegerParameter>`
- `beast.base.inference.operator.IntUniformOperator`
    - legacy: `Input<IntegerParameter>`
- `beast.base.inference.operator.RealRandomWalkOperator`
    - legacy: `Input<RealParameter>`
- `beast.base.inference.operator.SwapOperator`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<IntegerParameter>`
    - legacy: `Input<BooleanParameter>`
- `beast.base.inference.operator.kernel.BactrianDeltaExchangeOperator`
    - legacy: `Input<IntegerParameter>`
- `beast.base.inference.operator.kernel.BactrianIntervalOperator`
    - legacy: `Input<RealParameter>`
- `beast.base.inference.operator.kernel.BactrianRandomWalkOperator`
    - legacy: `Input<RealParameter>`

### Loggers (3)

- `beast.base.evolution.speciation.SpeciesTreeLogger`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.evolution.tree.coalescent.CompoundPopulationFunction`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<BooleanParameter>`
- `beast.base.inference.util.ESS`
    - legacy: `Input<Function>`

### CalcNodes (1)

- `beast.base.evolution.substitutionmodel.Frequencies`
    - legacy: `Input<RealParameter>`

### StateNodes (3)

- `beast.base.evolution.alignment.FilteredAlignment`
    - legacy: `Input<IntegerParameter>`
- `beast.base.evolution.speciation.StarBeastStartState`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<Function>`
- `beast.base.evolution.tree.ClusterTree`
    - legacy: `Input<Function>`

### Other (17)

- `beast.base.evolution.branchratemodel.BranchRateModel`
    - legacy: `Input<Function>`
- `beast.base.evolution.branchratemodel.RandomLocalClockModel`
    - legacy: `Input<BooleanParameter>`
    - legacy: `Input<RealParameter>`
- `beast.base.evolution.branchratemodel.UCRelaxedClockModel`
    - legacy: `Input<IntegerParameter>`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
- `beast.base.evolution.sitemodel.SiteModel`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
- `beast.base.evolution.substitutionmodel.BinaryCovarion`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.evolution.substitutionmodel.GTR`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.evolution.substitutionmodel.GeneralSubstitutionModel`
    - legacy: `Input<Function>`
- `beast.base.evolution.substitutionmodel.HKY`
    - legacy: `Input<Function>`
- `beast.base.evolution.substitutionmodel.MutationDeathModel`
    - legacy: `Input<Function>`
- `beast.base.evolution.substitutionmodel.SYM`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
- `beast.base.evolution.substitutionmodel.TIM`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.evolution.substitutionmodel.TN93`
    - legacy: `Input<RealParameter>`
    - legacy: `Input<RealParameter>`
- `beast.base.evolution.substitutionmodel.TVM`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.evolution.tree.coalescent.ConstantPopulation`
    - legacy: `Input<Function>`
- `beast.base.evolution.tree.coalescent.ExponentialGrowth`
    - legacy: `Input<Function>`
    - legacy: `Input<Function>`
- `beast.base.evolution.tree.coalescent.ScaledPopulationFunction`
    - legacy: `Input<Function>`
- `beastfx.app.beauti.PriorInputEditor`
    - legacy: `Input<Function>`

## Example XMLs pending migration

**Needs `version="2.8"`** (78):

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

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**Uses spec types but still has legacy `parameter.*` declarations** (4):

- `beast-fx/src/main/resources/beast.fx/fxtemplates/Standard.xml`
- `beast-fx/src/main/resources/beast.fx/fxtemplates/ParametricDistributions.xml`
- `release/Mac/output/BEAST.app/Contents/fxtemplates/Standard.xml`
- `release/Mac/output/BEAST.app/Contents/fxtemplates/ParametricDistributions.xml`


# beast3 — what's left

> **Scanned at:** 2026-05-07T21:16:03.273610  
> **Local checkout:** `/Users/adru001/Git/beast3` — commit `4e1ee72` on `target-acceptance-1d-operators` — [view on GitHub](https://github.com/CompEvol/beast3/commit/4e1ee72a7cead616230d9dce5cb43e71400615e1)  
> **Pom version:** `2.8.0-SNAPSHOT`  
> **Maven Central:** `io.github.compevol:beast3:2.8.0-beta5`  
> **Stage hint:** Maven Central
>
> Core. Multi-module — beast-base / beast-fx / beast-pkgmgmt / beast-test-utils.

## Summary

- **Java classes:** 78 on spec, 15 mixed, 78 legacy of 604 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 78 total (+81 under legacy/)
- **BEAUti fxtemplates:** 6 clean / 10 use spec / 10 total
- **Input rule:** 56 classes hold 105 Input(s) declared too concretely
- **Maven Central:** 2.8.0-beta5

## Java classes pending migration

### Distributions — 14 legacy, 15 mixed (of 70 total)

**Mixed** (extends a legacy base AND a spec interface — finish swapping the base):

- `beast.base.evolution.likelihood.BeagleTreeLikelihood` — extends `BeagleTreeLikelihood`
- `beast.base.evolution.likelihood.GenericTreeLikelihood` — extends `GenericTreeLikelihood`
- `beast.base.evolution.likelihood.ThreadedTreeLikelihood` — extends `ThreadedTreeLikelihood`
- `beast.base.evolution.likelihood.TreeLikelihood` — extends `TreeLikelihood`
- `beast.base.evolution.speciation.BirthDeathGernhard08Model` — extends `BirthDeathGernhard08Model`
- `beast.base.evolution.speciation.CalibratedBirthDeathModel` — extends `CalibratedBirthDeathModel`
- `beast.base.evolution.speciation.CalibratedYuleModel` — extends `CalibratedYuleModel`
- `beast.base.evolution.speciation.GeneTreeForSpeciesTreeDistribution` — extends `GeneTreeForSpeciesTreeDistribution`
- `beast.base.evolution.speciation.SpeciesTreePopFunction` — extends `SpeciesTreePopFunction`
- `beast.base.evolution.speciation.SpeciesTreePrior` — extends `SpeciesTreePrior`
- `beast.base.evolution.speciation.YuleModel` — extends `YuleModel`
- `beast.base.evolution.tree.MRCAPrior` — extends `MRCAPrior`
- `beast.base.evolution.tree.coalescent.BayesianSkyline` — extends `BayesianSkyline`
- `beast.base.inference.distribution.Prior` — extends `Prior`
- `beast.base.spec.inference.distribution.Prior` — extends `Prior`

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beast.base.inference.distribution.Beta` — extends `Beta`
- `beast.base.inference.distribution.ChiSquare` — extends `ChiSquare`
- `beast.base.inference.distribution.Dirichlet` — extends `ParametricDistribution`
- `beast.base.inference.distribution.Exponential` — extends `Exponential`
- `beast.base.inference.distribution.Gamma` — extends `Gamma`
- `beast.base.inference.distribution.InverseGamma` — extends `InverseGamma`
- `beast.base.inference.distribution.LaplaceDistribution` — extends `LaplaceDistribution`
- `beast.base.inference.distribution.LogNormal` — extends `LogNormal`
- `beast.base.inference.distribution.LogNormalDistributionModel` — extends `LogNormalDistributionModel`
- `beast.base.inference.distribution.MarkovChainDistribution` — extends `MarkovChainDistribution`
- `beast.base.inference.distribution.Normal` — extends `Normal`
- `beast.base.inference.distribution.OneOnX` — extends `OneOnX`
- `beast.base.inference.distribution.Poisson` — extends `Poisson`
- `beast.base.inference.distribution.Uniform` — extends `Uniform`

### Operators — 16 legacy, 0 mixed (of 47 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beast.base.evolution.operator.AdaptableOperatorSampler` — extends `AdaptableOperatorSampler`
- `beast.base.evolution.operator.EpochFlexOperator` — extends `EpochFlexOperator`
- `beast.base.evolution.operator.TreeStretchOperator` — extends `TreeStretchOperator`
- `beast.base.evolution.operator.kernel.BactrianScaleOperator` — extends `BactrianScaleOperator`
- `beast.base.evolution.tree.coalescent.SampleOffValues` — extends `SampleOffValues`
- `beast.base.inference.operator.BitFlipOperator` — extends `BitFlipOperator`
- `beast.base.inference.operator.DeltaExchangeOperator` — extends `DeltaExchangeOperator`
- `beast.base.inference.operator.IntRandomWalkOperator` — extends `IntRandomWalkOperator`
- `beast.base.inference.operator.IntUniformOperator` — extends `IntUniformOperator`
- `beast.base.inference.operator.RealRandomWalkOperator` — extends `RealRandomWalkOperator`
- `beast.base.inference.operator.SwapOperator` — extends `SwapOperator`
- `beast.base.inference.operator.UniformOperator` — extends `UniformOperator`
- `beast.base.inference.operator.UpDownOperator` — extends `UpDownOperator`
- `beast.base.inference.operator.kernel.BactrianDeltaExchangeOperator` — extends `BactrianDeltaExchangeOperator`
- `beast.base.inference.operator.kernel.BactrianRandomWalkOperator` — extends `BactrianRandomWalkOperator`
- `beast.base.inference.operator.kernel.BactrianUpDownOperator` — extends `BactrianUpDownOperator`

### Loggers — 5 legacy, 0 mixed (of 16 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beast.base.evolution.Sum` — extends `Sum`
- `beast.base.evolution.TreeWithMetaDataLogger` — extends `TreeWithMetaDataLogger`
- `beast.base.evolution.speciation.SpeciesTreeLogger` — extends `SpeciesTreeLogger`
- `beast.base.evolution.tree.coalescent.CompoundPopulationFunction` — extends `CompoundPopulationFunction`
- `beast.base.inference.util.ESS` — extends `ESS`

### CalcNodes — 5 legacy, 0 mixed (of 20 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beast.base.evolution.speciation.RandomGeneTree` — extends `RandomGeneTree`
- `beast.base.evolution.substitutionmodel.Frequencies` — extends `Frequencies`
- `beast.base.inference.distribution.ParametricDistribution` — extends `ParametricDistribution`
- `beast.base.inference.util.RPNcalculator` — extends `RPNcalculator`
- `beast.base.spec.FunctionOfTensor` — extends `FunctionOfTensor`

### Parameters — 1 legacy, 0 mixed (of 5 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beast.base.inference.parameter.CompoundRealParameter` — extends `CompoundRealParameter`

### StateNodes — 5 legacy, 0 mixed (of 22 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beast.base.evolution.alignment.FilteredAlignment` — extends `FilteredAlignment`
- `beast.base.evolution.speciation.CalibratedYuleInitialTree` — extends `CalibratedYuleInitialTree`
- `beast.base.evolution.speciation.StarBeastStartState` — extends `StarBeastStartState`
- `beast.base.evolution.tree.ClusterTree` — extends `ClusterTree`
- `beast.base.evolution.tree.coalescent.RandomTree` — extends `RandomTree`

### Other — 32 legacy, 0 mixed (of 424 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beast.base.core.Function` — extends `Function`
- `beast.base.evolution.branchratemodel.RandomLocalClockModel` — extends `RandomLocalClockModel`
- `beast.base.evolution.branchratemodel.StrictClockModel` — extends `StrictClockModel`
- `beast.base.evolution.branchratemodel.UCRelaxedClockModel` — extends `UCRelaxedClockModel`
- `beast.base.evolution.sitemodel.SiteModel` — extends `SiteModel`
- `beast.base.evolution.speciation.CalibrationLineagesIterator` — extends `CalibrationLineagesIterator`
- `beast.base.evolution.speciation.CalibrationPoint` — extends `CalibrationPoint`
- `beast.base.evolution.substitutionmodel.BinaryCovarion` — extends `BinaryCovarion`
- `beast.base.evolution.substitutionmodel.Blosum62` — extends `Blosum62`
- `beast.base.evolution.substitutionmodel.CPREV` — extends `CPREV`
- `beast.base.evolution.substitutionmodel.ComplexSubstitutionModel` — extends `ComplexSubstitutionModel`
- `beast.base.evolution.substitutionmodel.Dayhoff` — extends `Dayhoff`
- `beast.base.evolution.substitutionmodel.EmpiricalSubstitutionModel` — extends `EmpiricalSubstitutionModel`
- `beast.base.evolution.substitutionmodel.GTR` — extends `GTR`
- `beast.base.evolution.substitutionmodel.GeneralSubstitutionModel` — extends `GeneralSubstitutionModel`
- `beast.base.evolution.substitutionmodel.HKY` — extends `HKY`
- `beast.base.evolution.substitutionmodel.JTT` — extends `JTT`
- `beast.base.evolution.substitutionmodel.JukesCantor` — extends `JukesCantor`
- `beast.base.evolution.substitutionmodel.MTREV` — extends `MTREV`
- `beast.base.evolution.substitutionmodel.MutationDeathModel` — extends `MutationDeathModel`
- `beast.base.evolution.substitutionmodel.SYM` — extends `SYM`
- `beast.base.evolution.substitutionmodel.TIM` — extends `TIM`
- `beast.base.evolution.substitutionmodel.TN93` — extends `TN93`
- `beast.base.evolution.substitutionmodel.TVM` — extends `TVM`
- `beast.base.evolution.substitutionmodel.WAG` — extends `WAG`
- `beast.base.evolution.tree.coalescent.ConstantPopulation` — extends `ConstantPopulation`
- `beast.base.evolution.tree.coalescent.ExponentialGrowth` — extends `ExponentialGrowth`
- `beast.base.evolution.tree.coalescent.ScaledPopulationFunction` — extends `ScaledPopulationFunction`
- `beast.base.inference.parameter.BooleanParameter` — extends `BooleanParameter`
- `beast.base.inference.parameter.IntegerParameter` — extends `IntegerParameter`
- `beast.base.inference.parameter.Parameter` — extends `Parameter`
- `beast.base.inference.parameter.RealParameter` — extends `RealParameter`

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


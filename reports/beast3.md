# beast3 — what's left

> **Scanned at:** 2026-05-07T21:03:28.474286  
> **Local checkout:** `/Users/adru001/Git/beast3` — commit `4e1ee72` on `target-acceptance-1d-operators` — [view on GitHub](https://github.com/CompEvol/beast3/commit/4e1ee72a7cead616230d9dce5cb43e71400615e1)  
> **Pom version:** `2.8.0-SNAPSHOT`  
> **Maven Central:** `io.github.compevol:beast3:2.8.0-beta5`  
> **Stage hint:** Maven Central
>
> Core. Multi-module — beast-base / beast-fx / beast-pkgmgmt / beast-test-utils.

## Summary

- **Java classes:** 93 on spec, 0 mixed, 34 legacy of 604 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 78 total (+81 under legacy/)
- **BEAUti fxtemplates:** 6 clean / 10 use spec / 10 total
- **Input rule:** 56 classes hold 105 Input(s) declared too concretely
- **Maven Central:** 2.8.0-beta5

## Java classes pending migration

### Distributions — 13 legacy, 0 mixed (of 70 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beast.base.inference.distribution.Beta` — extends `ParametricDistribution`
- `beast.base.inference.distribution.ChiSquare` — extends `ParametricDistribution`
- `beast.base.inference.distribution.Dirichlet` — extends `ParametricDistribution`
- `beast.base.inference.distribution.Exponential` — extends `ParametricDistribution`
- `beast.base.inference.distribution.Gamma` — extends `ParametricDistribution`
- `beast.base.inference.distribution.InverseGamma` — extends `ParametricDistribution`
- `beast.base.inference.distribution.LaplaceDistribution` — extends `ParametricDistribution`
- `beast.base.inference.distribution.LogNormal` — via `LogNormalDistributionModel`
- `beast.base.inference.distribution.LogNormalDistributionModel` — extends `ParametricDistribution`
- `beast.base.inference.distribution.Normal` — extends `ParametricDistribution`
- `beast.base.inference.distribution.OneOnX` — extends `ParametricDistribution`
- `beast.base.inference.distribution.Poisson` — extends `ParametricDistribution`
- `beast.base.inference.distribution.Uniform` — extends `ParametricDistribution`

### Parameters — 1 legacy, 0 mixed (of 5 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beast.base.inference.parameter.CompoundRealParameter` — extends `RealParameter`

### Other — 20 legacy, 0 mixed (of 424 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `beast.base.evolution.branchratemodel.RandomLocalClockModel` — extends `Base`
- `beast.base.evolution.branchratemodel.StrictClockModel` — extends `Base`
- `beast.base.evolution.branchratemodel.UCRelaxedClockModel` — extends `Base`
- `beast.base.evolution.substitutionmodel.BinaryCovarion` — extends `GeneralSubstitutionModel`
- `beast.base.evolution.substitutionmodel.Blosum62` — extends `EmpiricalSubstitutionModel`
- `beast.base.evolution.substitutionmodel.CPREV` — extends `EmpiricalSubstitutionModel`
- `beast.base.evolution.substitutionmodel.ComplexColtEigenSystem` — extends `ColtEigenSystem`
- `beast.base.evolution.substitutionmodel.ComplexSubstitutionModel` — extends `GeneralSubstitutionModel`
- `beast.base.evolution.substitutionmodel.Dayhoff` — extends `EmpiricalSubstitutionModel`
- `beast.base.evolution.substitutionmodel.EmpiricalSubstitutionModel` — extends `GeneralSubstitutionModel`
- `beast.base.evolution.substitutionmodel.GTR` — extends `GeneralSubstitutionModel`
- `beast.base.evolution.substitutionmodel.GeneralSubstitutionModel` — extends `Base`
- `beast.base.evolution.substitutionmodel.JTT` — extends `EmpiricalSubstitutionModel`
- `beast.base.evolution.substitutionmodel.JukesCantor` — extends `Base`
- `beast.base.evolution.substitutionmodel.MTREV` — extends `EmpiricalSubstitutionModel`
- `beast.base.evolution.substitutionmodel.MutationDeathModel` — extends `Base`
- `beast.base.evolution.substitutionmodel.SYM` — extends `GeneralSubstitutionModel`
- `beast.base.evolution.substitutionmodel.TIM` — extends `GeneralSubstitutionModel`
- `beast.base.evolution.substitutionmodel.TVM` — extends `GeneralSubstitutionModel`
- `beast.base.evolution.substitutionmodel.WAG` — extends `EmpiricalSubstitutionModel`

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


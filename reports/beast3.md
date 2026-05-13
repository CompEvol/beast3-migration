# beast3 — what's left

> **Scanned at:** 2026-05-13T15:46:05.773816  
> **Commit:** `6df8243` on `oneonx-deprecation-points-at-loguniform` — [view on GitHub](https://github.com/CompEvol/beast3/commit/6df82436c8e13b4061a3477c393a74489ac6b314)  
> **Pom version:** `2.8.0-SNAPSHOT`  
> **Maven Central:** `io.github.compevol:beast3:2.8.0-beta5`  
> **Stage hint:** Maven Central
>
> Core. Multi-module — beast-base / beast-fx / beast-pkgmgmt / beast-test-utils.

## Summary

- **Java classes:** 74 on spec, 0 mixed, 0 legacy of 187 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 157 total (+2 under legacy/)
- **BEAUti fxtemplates:** 6 clean / 10 use spec / 10 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** 2.8.0-beta5

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs pending migration

**Needs `version="2.8"`** (157):

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
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testStrictClockTipDatesSampling.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testBSP1.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testExponentialGrowth.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testYuleModel_10taxa.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testBirthDeathModel_10taxa.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testSRD06CP12_3.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testHKY.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testStrictClock2.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testCoalescentTipDates1.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testCoalescentTipDates.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testUCRelaxedClockLogNormal.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testCoalescentNoClock.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testMultiSubstModel.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testCoalescentTipDatesSampling.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testCalibration.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testCalibrationMono.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testSiteModelAlpha.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testStrictClock.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testStrictClockTipTime.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testEBSP.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testBirthDeathAsYule.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testRandomLocalClock.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testCoalescentNoClock1.xml`
- `beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testBSPNoClock.xml`
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
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testStrictClockTipDatesSampling.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testStarBEASTLinear.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testBSP1.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testExponentialGrowth.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testYuleModel_10taxa.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testBirthDeathModel_10taxa.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testSRD06CP12_3.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testStarBEASTConstant.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testHKY.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testStrictClock2.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testCoalescentTipDates1.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testCoalescentTipDates.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testUCRelaxedClockLogNormal.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testStarBEAST.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testStarBeast2.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testStrictClockTipDatesSampling.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testStarBEASTLinear.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testBSP1.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testExponentialGrowth.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testCoalescent.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testYuleModel_10taxa.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testBirthDeathModel_10taxa.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testStrictClockNoDate2.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testSRD06CP12_3.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testStarBEASTConstant.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testYule.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testUCRelaxedClockLogNormal.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testStrictClockNoDate.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testCoalescentNoClock.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testMultiSubstModel.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testMCMC.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testCalibration.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testStarBEASTLinearConstRoot.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testSiteModelAlpha.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testStrictClockTipTime.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testEBSP.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testBirthDeathAsYule.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testRandomLocalClock.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testCoalescentNoClock1.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/beast1/testBSPNoClock.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testCoalescentNoClock.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testMultiSubstModel.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testCoalescentTipDatesSampling.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testCalibration.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testTipDates.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testStarBEASTLinearConstRoot.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testCalibrationMono.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testSiteModelAlpha.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testStrictClock.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testStrictClockTipTime.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testEBSP.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testBirthDeathAsYule.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testRandomLocalClock.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testCoalescentNoClock1.xml`
- `beast-base/src/test/resources/beast.base/examples/beast2vs1/testBSPNoClock.xml`
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

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`beast-fx/src/main/resources/beast.fx/fxtemplates/Standard.xml`** (14):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
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

**`beast-fx/src/main/resources/beast.fx/fxtemplates/ParametricDistributions.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.Dirichlet` | `beast.base.spec.inference.distribution.Dirichlet` |

**`beast-fx/src/main/resources/beast.fx/fxtemplates/TreePriors.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BactrianScaleOperator` → `beast.base.evolution.operator.kernel.BactrianScaleOperator` | `beast.base.spec.inference.operator.ScaleOperator` |
| `spec=` | `beast.base.evolution.operator.kernel.BactrianScaleOperator` | `beast.base.spec.inference.operator.ScaleOperator` |
| `spec=` | `beast.base.spec.inference.distribution.Prior` | `beast.base.spec.inference.distribution.Normal` |

**`release/Mac/output/BEAST.app/Contents/fxtemplates/Standard.xml`** (14):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
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

**`release/Mac/output/BEAST.app/Contents/fxtemplates/ParametricDistributions.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.Dirichlet` | `beast.base.spec.inference.distribution.Dirichlet` |

**`release/Mac/output/BEAST.app/Contents/fxtemplates/TreePriors.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BactrianScaleOperator` → `beast.base.evolution.operator.kernel.BactrianScaleOperator` | `beast.base.spec.inference.operator.ScaleOperator` |
| `spec=` | `beast.base.evolution.operator.kernel.BactrianScaleOperator` | `beast.base.spec.inference.operator.ScaleOperator` |
| `spec=` | `beast.base.spec.inference.distribution.Prior` | `beast.base.spec.inference.distribution.Normal` |

**`beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY1748.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY1749.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY1366.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY767.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY1809.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY501.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY3475.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY1510.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY1044.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY520.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY336.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/benchmark/II/testHKY755.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/testJukesCantorShortUncertain2.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-base/src/test/resources/beast.base/examples/testExponentialGrowth.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`beast-base/src/test/resources/beast.base/examples/testMultipleAlignments_randomTaxaOrder.xml`** (17):

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

**`beast-base/src/test/resources/beast.base/examples/testCoalescent.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`beast-base/src/test/resources/beast.base/examples/testGTR.xml`** (14):

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

**`beast-base/src/test/resources/beast.base/examples/testOpSubSchedule.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/testBSP.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.MarkovChainDistribution` | `beast.base.spec.inference.distribution.MarkovChainDistribution` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |

**`beast-base/src/test/resources/beast.base/examples/testPlates.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-base/src/test/resources/beast.base/examples/testJukesCantorShortUncertain.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-base/src/test/resources/beast.base/examples/testSRD06.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.alignment.FilteredAlignment` | `beast.base.spec.evolution.alignment.FilteredAlignment` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-base/src/test/resources/beast.base/examples/testHKY.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/testJukesCantorShortUncertain2MLE.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-base/src/test/resources/beast.base/examples/testRestrictedGTR.xml`** (14):

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

**`beast-base/src/test/resources/beast.base/examples/testRNA.xml`** (14):

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

**`beast-base/src/test/resources/beast.base/examples/parameterised/RSV2.xml`** (13):

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

**`beast-base/src/test/resources/beast.base/examples/testSimulatedAlignment.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`beast-base/src/test/resources/beast.base/examples/testSYM.xml`** (14):

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

**`beast-base/src/test/resources/beast.base/examples/testStarBeast.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-base/src/test/resources/beast.base/examples/testJukesCantorShort.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-base/src/test/resources/beast.base/examples/spec/testGTR.xml`** (15):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.spec.inference.distribution.Prior` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |

**`beast-base/src/test/resources/beast.base/examples/spec/testHKY.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.spec.inference.distribution.Prior` | `beast.base.spec.inference.distribution.Normal` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/spec/testTN93.xml`** (14):

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

**`beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testCoalescentTipDatesSampling.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |

**`beast-base/src/test/resources/beast.base/examples/spec/beast2vs1/testRandomLocalClock.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |

**`beast-base/src/test/resources/beast.base/examples/spec/testEBSP.xml`** (12):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.spec.inference.distribution.Prior` | `beast.base.spec.inference.distribution.Normal` |
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-base/src/test/resources/beast.base/examples/bitflip.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |

**`beast-base/src/test/resources/beast.base/examples/testClassicRootCalibrationPrior.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/testDirectSimulator2.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`beast-base/src/test/resources/beast.base/examples/testTN93.xml`** (14):

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

**`beast-base/src/test/resources/beast.base/examples/testStarBeastFBD.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-base/src/test/resources/beast.base/examples/testJukesCantor.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-base/src/test/resources/beast.base/examples/testTIM.xml`** (14):

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

**`beast-base/src/test/resources/beast.base/examples/testConditionalRootCalibrationPrior.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/testDirectSimulator.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |

**`beast-base/src/test/resources/beast.base/examples/testCalibration.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |

**`beast-base/src/test/resources/beast.base/examples/testTipDates.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |

**`beast-base/src/test/resources/beast.base/examples/testTipDates2.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-base/src/test/resources/beast.base/examples/testTVM.xml`** (14):

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

**`beast-base/src/test/resources/beast.base/examples/testCalYule_5t_2c.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |

**`beast-base/src/test/resources/beast.base/examples/testDirectSimulatorHierarchical.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testStrictClockTipDatesSampling.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testStarBEASTLinear.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testBSP1.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.MarkovChainDistribution` | `beast.base.spec.inference.distribution.MarkovChainDistribution` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testExponentialGrowth.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testYuleModel_10taxa.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testBirthDeathModel_10taxa.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testSRD06CP12_3.xml`** (13):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.alignment.FilteredAlignment` | `beast.base.spec.evolution.alignment.FilteredAlignment` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testStarBEASTConstant.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testHKY.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testStrictClock2.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testCoalescentTipDates1.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testCoalescentTipDates.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testUCRelaxedClockLogNormal.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testStarBEAST.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testStarBeast2.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testCoalescentNoClock.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testMultiSubstModel.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testCoalescentTipDatesSampling.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testCalibration.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testTipDates.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testStarBEASTLinearConstRoot.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testCalibrationMono.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testSiteModelAlpha.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testStrictClock.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testStrictClockTipTime.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testEBSP.xml`** (11):

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

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testBirthDeathAsYule.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testRandomLocalClock.xml`** (12):

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

**`beast-base/src/test/resources/beast.base/examples/beast2vs1/testBSPNoClock.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.inference.distribution.MarkovChainDistribution` | `beast.base.spec.inference.distribution.MarkovChainDistribution` |

**`beast-base/src/test/resources/beast.base/examples/testRelaxedClock.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |

**`beast-base/src/test/resources/beast.base/examples/testTwoCalibrationsPrior.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`beast-base/src/test/resources/beast.base/examples/starbeastinit/sbi-01.xml`** (6):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |
| `spec=` | `StarBeastStartState` → `beast.base.evolution.speciation.StarBeastStartState` | _(no spec equivalent found)_ |

**`beast-base/src/test/resources/beast.base/examples/starbeastinit/sbi-02.xml`** (6):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |
| `spec=` | `StarBeastStartState` → `beast.base.evolution.speciation.StarBeastStartState` | _(no spec equivalent found)_ |

**`beast-base/src/test/resources/beast.base/examples/starbeastinit/sbi-03.xml`** (6):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | `beast.base.spec.inference.distribution.LogUniform` |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `SpeciesTreeLogger` → `beast.base.evolution.speciation.SpeciesTreeLogger` | _(no spec equivalent found)_ |
| `spec=` | `StarBeastStartState` → `beast.base.evolution.speciation.StarBeastStartState` | _(no spec equivalent found)_ |

**`beast-base/src/test/resources/beast.base/examples/testEBSP.xml`** (20):

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

**`beast-base/src/test/resources/beast.base/examples/testRandomLocalClock.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | `beast.base.spec.inference.operator.uniform.IntervalOperator` |

**`beast-base/src/test/resources/beast.base/examples/testYuleUncalibrated.xml`** (12):

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

**`beast-base/src/test/resources/beast.base/examples/testSeqGen.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |

**`beast-base/src/test/resources/beast.base/examples/testYuleCalibrated.xml`** (12):

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


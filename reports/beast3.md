# beast3 — what's left

> **Scanned at:** 2026-05-13T10:15:00.317304  
> **Commit:** `d3ad887` on `fix/offset-real-calculate-logp` — [view on GitHub](https://github.com/CompEvol/beast3/commit/d3ad887fb81ba516a9769594ce51d3a007ac0941)  
> **Pom version:** `2.8.0-SNAPSHOT`  
> **Maven Central:** `io.github.compevol:beast3:2.8.0-beta5`  
> **Stage hint:** Maven Central
>
> Core. Multi-module — beast-base / beast-fx / beast-pkgmgmt / beast-test-utils.

## Summary

- **Java classes:** 93 on spec, 0 mixed, 0 legacy of 842 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 78 total (+81 under legacy/)
- **BEAUti fxtemplates:** 6 clean / 10 use spec / 10 total
- **Input rule:** 3 classes hold 3 Input(s) declared too concretely
- **Maven Central:** 2.8.0-beta5

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Inputs declared too concretely

> Concrete spec params (`RealScalarParam`, `RealVectorParam`, …) belong only on Operators, which need to write the parameter. Distributions, CalcNodes, Loggers and other read-only holders should declare the interface (`RealScalar`, `RealVector`, …) so adapters and transforms can be substituted. Legacy `RealParameter` / `Function` Inputs are violations everywhere.

### Operators (1)

- `beast.base.inference.operator.kernel.BactrianIntervalOperator`
    - legacy: `Input<RealParameter>`

### CalcNodes (1)

- `beast.base.evolution.branchratemodel.BranchRateModel.Base`
    - legacy: `Input<Function>`

### Other (1)

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


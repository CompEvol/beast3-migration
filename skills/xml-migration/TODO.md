# XML Migration Smoke-Test Results

Tested 41 XMLs from `WorkSpace/beast3/beast-base/src/test/resources/beast.base/examples`

## Summary

### Failed (25)

| # | XML | Result |
|---|-----|--------|
| 1 | `bitflip.xml` | ✗ FAIL-RUN |
| 2 | `testBSP.xml` | ✗ FAIL-RUN |
| 3 | `testCalYule_5t_2c.xml` | ✗ FAIL-RUN |
| 4 | `testCalibration.xml` | ✗ FAIL-RUN |
| 5 | `testClassicRootCalibrationPrior.xml` | ✗ FAIL-RUN |
| 6 | `testCoalescent.xml` | ✗ FAIL-RUN |
| 7 | `testConditionalRootCalibrationPrior.xml` | ✗ FAIL-RUN |
| 8 | `testDirectSimulator.xml` | ✗ FAIL-CHAINLENGTH |
| 9 | `testDirectSimulator2.xml` | ✗ FAIL-CHAINLENGTH |
| 10 | `testDirectSimulatorHierarchical.xml` | ✗ FAIL-CHAINLENGTH |
| 11 | `testEBSP.xml` | ✗ FAIL-RUN |
| 12 | `testExponentialGrowth.xml` | ✗ FAIL-RUN |
| 20 | `testMultipleAlignments_randomTaxaOrder.xml` | ✗ FAIL-RUN |
| 24 | `testRandomLocalClock.xml` | ✗ FAIL-RUN |
| 25 | `testRelaxedClock.xml` | ✗ FAIL-RUN |
| 27 | `testSRD06.xml` | ✗ FAIL-RUN |
| 29 | `testSeqGen.xml` | ✗ FAIL-CHAINLENGTH |
| 30 | `testSimulatedAlignment.xml` | ✗ FAIL-RUN |
| 31 | `testStarBeast.xml` | ✗ FAIL-RUN |
| 32 | `testStarBeastFBD.xml` | ✗ FAIL-RUN |
| 36 | `testTipDates.xml` | ✗ FAIL-RUN |
| 37 | `testTipDates2.xml` | ✗ FAIL-RUN |
| 38 | `testTwoCalibrationsPrior.xml` | ✗ FAIL-RUN |
| 39 | `testYuleCalibrated.xml` | ✗ FAIL-RUN |
| 41 | `testYuleUncalibrated.xml` | ✗ FAIL-RUN |

### Passed (16)

| # | XML | Result |
|---|-----|--------|
| 13 | `testGTR.xml` | ✓ OK |
| 14 | `testHKY.xml` | ✓ OK |
| 15 | `testJukesCantor.xml` | ✓ OK |
| 16 | `testJukesCantorShort.xml` | ✓ OK |
| 17 | `testJukesCantorShortUncertain.xml` | ✓ OK |
| 18 | `testJukesCantorShortUncertain2.xml` | ✓ OK |
| 19 | `testJukesCantorShortUncertain2MLE.xml` | ✓ OK |
| 21 | `testOpSubSchedule.xml` | ✓ OK |
| 22 | `testPlates.xml` | ✓ OK |
| 23 | `testRNA.xml` | ✓ OK |
| 26 | `testRestrictedGTR.xml` | ✓ OK |
| 28 | `testSYM.xml` | ✓ OK |
| 33 | `testTIM.xml` | ✓ OK |
| 34 | `testTN93.xml` | ✓ OK |
| 35 | `testTVM.xml` | ✓ OK |
| 40 | `testYuleOneSite.xml` | ✓ OK |

## Errors

### 1. `bitflip.xml` — FAIL-RUN

```
FAIL — error context:
java.lang.IllegalArgumentException: This BEASTInterface (indicators) has no input with name domain. Choose one of these inputs: value,estimate
Error 124 parsing the xml input file

This BEASTInterface (indicators) has no input with name domain. Choose one of these inputs: value,estimate

Error detected about here:
  <beast>
      <parameter id='indicators' name='indicators' spec='beast.base.spec.inference.parameter.BoolScalarParam'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:863)
    at org.codehaus.mojo.exec.ExecMojo.execute (ExecMojo.java:454)
```

### 2. `testBSP.xml` — FAIL-RUN

```
FAIL — error context:
Error 130 parsing the xml input file

Input 102b: type mismatch for input popSizes

Error detected about here:
  <beast>
      <distribution id='posterior' spec='CompoundDistribution'>
          <distribution id='prior' spec='CompoundDistribution'>
              <distribution id='skyline' spec='beast.base.spec.evolution.tree.coalescent.BayesianSkyline'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
```

### 3. `testCalYule_5t_2c.xml` — FAIL-RUN

```
FAIL — error context:
java.lang.RuntimeException: Input 101: type mismatch for input calibrations. beast.base.evolution.speciation.CalibrationPoint.isAssignableFrom(class beast.base.spec.evolution.speciation.CalibrationPoint)=false
Error 110 parsing the xml input file

validate and intialize error: Input 101: type mismatch for input calibrations. beast.base.evolution.speciation.CalibrationPoint.isAssignableFrom(class beast.base.spec.evolution.speciation.CalibrationPoint)=false

Error detected about here:
  <beast>
      <run id='mcmc' spec='MCMC'>
          <init id='startTree' spec='beast.base.spec.evolution.speciation.CalibratedYuleInitialTree'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:863)
```

### 4. `testCalibration.xml` — FAIL-RUN

```
FAIL — error context:
Error 123 parsing the xml input file

Input 101: type mismatch for input constraint. beast.base.evolution.tree.MRCAPrior.isAssignableFrom(class beast.base.spec.evolution.tree.MRCAPrior)=false expected 'MRCAPrior' but got 'MRCAPrior'

Error detected about here:
  <beast>
      <run id='mcmc' spec='MCMC'>
          <init id='randomTree' spec='beast.base.spec.evolution.tree.coalescent.RandomTree'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:863)
```

### 5. `testClassicRootCalibrationPrior.xml` — FAIL-RUN

```
FAIL — error context:
Error 130 parsing the xml input file

Input 102: type mismatch for input parameter
Provide type class beast.base.spec.inference.parameter.RealScalarParam, but expected class beast.base.inference.parameter.RealParameter

Error detected about here:
  <beast>
      <run id='mcmc' spec='MCMC'>
          <operator spec='ScaleOperator'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
```

### 6. `testCoalescent.xml` — FAIL-RUN

```
FAIL — error context:
Error 130 parsing the xml input file

Input 102: type mismatch for input parameter
Provide type class beast.base.spec.inference.parameter.RealScalarParam, but expected class beast.base.inference.parameter.RealParameter

Error detected about here:
  <beast>
      <run id='mcmc' spec='MCMC'>
          <operator id='kappaScaler' spec='ScaleOperator'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
```

### 7. `testConditionalRootCalibrationPrior.xml` — FAIL-RUN

```
FAIL — error context:
Error 130 parsing the xml input file

Input 102: type mismatch for input parameter
Provide type class beast.base.spec.inference.parameter.RealScalarParam, but expected class beast.base.inference.parameter.RealParameter

Error detected about here:
  <beast>
      <run id='mcmc' spec='MCMC'>
          <operator spec='ScaleOperator'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
```

### 8. `testDirectSimulator.xml` — FAIL-CHAINLENGTH

```
chainLength="" is not parameterised after conversion
```

### 9. `testDirectSimulator2.xml` — FAIL-CHAINLENGTH

```
chainLength="" is not parameterised after conversion
```

### 10. `testDirectSimulatorHierarchical.xml` — FAIL-CHAINLENGTH

```
chainLength="" is not parameterised after conversion
```

### 11. `testEBSP.xml` — FAIL-RUN

```
FAIL — error context:
java.lang.IllegalArgumentException: This BEASTInterface (beast.base.spec.inference.distribution.Poisson) has no input with name offset. Choose one of these inputs: lambda,param
Error 124 parsing the xml input file

This BEASTInterface (beast.base.spec.inference.distribution.Poisson) has no input with name offset. Choose one of these inputs: lambda,param

Error detected about here:
  <beast>
      <distribution id='posterior' spec='CompoundDistribution'>
          <distribution id='prior' spec='CompoundDistribution'>
              <distribution spec='beast.base.spec.inference.distribution.Poisson'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
```

### 12. `testExponentialGrowth.xml` — FAIL-RUN

```
FAIL — error context:
Error 130 parsing the xml input file

Input 102b: type mismatch for input popSize

Error detected about here:
  <beast>
      <run id='mcmc' spec='MCMC'>
          <distribution id='posterior' spec='CompoundDistribution'>
              <distribution id='coalescent' spec='Coalescent'>
                  <populationModel spec='beast.base.spec.evolution.tree.coalescent.ExponentialGrowth'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
```

### 20. `testMultipleAlignments_randomTaxaOrder.xml` — FAIL-RUN

```
FAIL — error context:
Error 130 parsing the xml input file

Input 102b: type mismatch for input popSize

Error detected about here:
  <beast>
      <run id='mcmc' spec='MCMC'>
          <init id='RandomTree.t:gene1' spec='beast.base.spec.evolution.tree.coalescent.RandomTree'>
              <populationModel id='ConstantPopulation0.t:gene1' spec='beast.base.spec.evolution.tree.coalescent.ConstantPopulation'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
```

### 24. `testRandomLocalClock.xml` — FAIL-RUN

```
FAIL — error context:
java.lang.IllegalArgumentException: This BEASTInterface (indicators) has no input with name domain. Choose one of these inputs: value,estimate
Error 124 parsing the xml input file

This BEASTInterface (indicators) has no input with name domain. Choose one of these inputs: value,estimate

Error detected about here:
  <beast>
      <input id='branchRates' spec='beast.base.spec.evolution.branchratemodel.RandomLocalClockModel'>
          <indicators id='indicators' spec='beast.base.spec.inference.parameter.BoolScalarParam'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:863)
```

### 25. `testRelaxedClock.xml` — FAIL-RUN

```
FAIL — error context:
Error 130 parsing the xml input file

Input 102b: type mismatch for input rateCategories

Error detected about here:
  <beast>
      <input id='branchRates' spec='beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:863)
    at org.codehaus.mojo.exec.ExecMojo.execute (ExecMojo.java:454)
```

### 27. `testSRD06.xml` — FAIL-RUN

```
FAIL — error context:
Error 130 parsing the xml input file

Input 102: type mismatch for input kappa
Provide type class beast.base.spec.inference.parameter.RealScalarParam, but expected interface beast.base.core.Function

Error detected about here:
  <beast>
      <distribution id='posterior' spec='CompoundDistribution'>
          <distribution id='likelihood' spec='CompoundDistribution'>
              <distribution id='treeLikelihood' spec='beast.base.spec.evolution.likelihood.TreeLikelihood'>
                  <siteModel id='firstAndSecondSites.siteModel' spec='beast.base.spec.evolution.sitemodel.SiteModel'>
                      <substModel id='firstAndSecondSites.hky' spec='HKY'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
```

### 29. `testSeqGen.xml` — FAIL-CHAINLENGTH

```
chainLength="" is not parameterised after conversion
```

### 30. `testSimulatedAlignment.xml` — FAIL-RUN

```
FAIL — error context:
Error 130 parsing the xml input file

Input 102: type mismatch for input parameter
Provide type class beast.base.spec.inference.parameter.RealScalarParam, but expected class beast.base.inference.parameter.RealParameter

Error detected about here:
  <beast>
      <run id='mcmc' spec='MCMC'>
          <operator id='kappaScaler' spec='ScaleOperator'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
```

### 31. `testStarBeast.xml` — FAIL-RUN

```
FAIL — error context:
Error 130 parsing the xml input file

Input 102b: type mismatch for input bottomPopSize

Error detected about here:
  <beast>
      <distribution id='posterior' spec='CompoundDistribution'>
          <distribution id='prior' spec='CompoundDistribution'>
              <distribution id='speciesCoalescent' spec='CompoundDistribution'>
                  <distribution id='SpeciesTreePopSizePrior' spec='beast.base.spec.evolution.speciation.SpeciesTreePrior'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
```

### 32. `testStarBeastFBD.xml` — FAIL-RUN

```
FAIL — error context:
Error 130 parsing the xml input file

Input 102b: type mismatch for input bottomPopSize

Error detected about here:
  <beast>
      <distribution id='posterior' spec='CompoundDistribution'>
          <distribution id='prior' spec='CompoundDistribution'>
              <distribution id='speciesCoalescent' spec='CompoundDistribution'>
                  <distribution id='SpeciesTreePopSizePrior' spec='beast.base.spec.evolution.speciation.SpeciesTreePrior'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
```

### 36. `testTipDates.xml` — FAIL-RUN

```
FAIL — error context:
java.lang.IllegalArgumentException: This BEASTInterface (Uniform0) has no input with name offset. Choose one of these inputs: lower,upper,param
Error 124 parsing the xml input file

This BEASTInterface (Uniform0) has no input with name offset. Choose one of these inputs: lower,upper,param

Error detected about here:
  <beast>
      <distribution id='posterior' spec='CompoundDistribution'>
          <distribution id='prior' spec='CompoundDistribution'>
              <distribution id='tipDates' spec='beast.base.spec.evolution.tree.MRCAPrior'>
                  <distr id='Uniform0' spec='beast.base.spec.inference.distribution.Uniform'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
```

### 37. `testTipDates2.xml` — FAIL-RUN

```
FAIL — error context:
java.lang.IllegalArgumentException: This BEASTInterface (Uniform0) has no input with name offset. Choose one of these inputs: lower,upper,param
Error 124 parsing the xml input file

This BEASTInterface (Uniform0) has no input with name offset. Choose one of these inputs: lower,upper,param

Error detected about here:
  <beast>
      <distribution id='posterior' spec='CompoundDistribution'>
          <distribution id='prior' spec='CompoundDistribution'>
              <distribution id='tipDates' spec='beast.base.spec.evolution.tree.MRCAPrior'>
                  <distr id='Uniform0' spec='beast.base.spec.inference.distribution.Uniform'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
```

### 38. `testTwoCalibrationsPrior.xml` — FAIL-RUN

```
FAIL — error context:
Error 130 parsing the xml input file

Input 102: type mismatch for input parameter
Provide type class beast.base.spec.inference.parameter.RealScalarParam, but expected class beast.base.inference.parameter.RealParameter

Error detected about here:
  <beast>
      <run id='mcmc' spec='MCMC'>
          <operator spec='ScaleOperator'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
```

### 39. `testYuleCalibrated.xml` — FAIL-RUN

```
FAIL — error context:
java.lang.IllegalArgumentException: This BEASTInterface (kappa.s:alignment) has no input with name minordimension. Choose one of these inputs: value,domain,estimate
Error 124 parsing the xml input file

This BEASTInterface (kappa.s:alignment) has no input with name minordimension. Choose one of these inputs: value,domain,estimate

Error detected about here:
  <beast>
      <run id='mcmc' spec='MCMC'>
          <state id='state'>
              <parameter id='kappa.s:alignment' name='stateNode' spec='beast.base.spec.inference.parameter.RealScalarParam'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
```

### 41. `testYuleUncalibrated.xml` — FAIL-RUN

```
FAIL — error context:
java.lang.IllegalArgumentException: This BEASTInterface (kappa.s:alignment) has no input with name minordimension. Choose one of these inputs: value,domain,estimate
Error 124 parsing the xml input file

This BEASTInterface (kappa.s:alignment) has no input with name minordimension. Choose one of these inputs: value,domain,estimate

Error detected about here:
  <beast>
      <run id='mcmc' spec='MCMC'>
          <state id='state'>
              <parameter id='kappa.s:alignment' name='stateNode' spec='beast.base.spec.inference.parameter.RealScalarParam'>

[ERROR] Command execution failed.
org.apache.commons.exec.ExecuteException: Process exited with an error: 1 (Exit value: 1)
    at org.apache.commons.exec.DefaultExecutor.executeInternal (DefaultExecutor.java:355)
    at org.apache.commons.exec.DefaultExecutor.execute (DefaultExecutor.java:253)
    at org.codehaus.mojo.exec.ExecMojo.executeCommandLine (ExecMojo.java:903)
```


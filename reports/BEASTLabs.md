# BEASTLabs — what's left

> **Scanned at:** 2026-05-07T20:16:32.796741  
> **Local checkout:** `/Users/adru001/Git/BEASTLabs` — commit `840fd01` on `scalable-contract` — [view on GitHub](https://github.com/BEAST2-Dev/BEASTlabs/commit/840fd015587efb223a2b382f954b0e10c58fc173)  
> **Pom version:** `2.1.0-SNAPSHOT`  
> **Maven Central:** `io.github.beast2-dev:beast-labs:2.1.0-beta2`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 19 on spec, 5 mixed, 13 legacy of 140 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 20 total
- **BEAUti fxtemplates:** 0 clean / 0 use spec / 3 total
- **Maven Central:** 2.1.0-beta2

## Java classes pending migration

### Distributions — 4 legacy, 3 mixed (of 22 total)

**Mixed** (already imports spec; finish removing legacy):

- `beastlabs.math.distributions.GammaOneP` — uses `ParametricDistribution`
- `beastlabs.math.distributions.SingleParamGamma` — uses `ParametricDistribution`
- `beastlabs.math.distributions.WeibullDistribution` — uses `ParametricDistribution`

**Legacy** (no spec imports yet):

- `beastlabs.evolution.speciation.RandomLocalYuleModel` — uses `BooleanParameter`, `RealParameter`
- `beastlabs.math.distributions.ExcludablePrior` — uses `Prior`, `BooleanParameter`, `RealParameter`, `IntegerParameter`
- `beastlabs.math.distributions.ExcludablePriorIndex` — uses `Prior`, `BooleanParameter`, `RealParameter`, `IntegerParameter`
- `beastlabs.math.distributions.MultiMRCAPriors` — uses `ParametricDistribution`

### Operators — 2 legacy, 0 mixed (of 21 total)

**Legacy** (no spec imports yet):

- `beastlabs.evolution.operators.AttachAndUniformOperator` — uses `RealParameter`, `IntegerParameter`, `Parameter`
- `beastlabs.evolution.operators.UniformOperatorSelective` — uses `RealParameter`, `IntegerParameter`, `Parameter`

### Loggers — 1 legacy, 0 mixed (of 11 total)

**Legacy** (no spec imports yet):

- `beastlabs.core.util.ParameterConstrainer` — uses `RealParameter`

### CalcNodes — 1 legacy, 0 mixed (of 10 total)

**Legacy** (no spec imports yet):

- `beastlabs.evolution.sitemodel.SiteModelGI` — uses `RealParameter`

### Parameters — 2 legacy, 0 mixed (of 2 total)

**Legacy** (no spec imports yet):

- `beastlabs.core.parameter.CompoundRealParameter` — uses `RealParameter`
- `beastlabs.core.parameter.NormalisedRealParameter` — uses `RealParameter`

### StateNodes — 3 legacy, 0 mixed (of 8 total)

**Legacy** (no spec imports yet):

- `beastlabs.evolution.tree.ConstrainedClusterTree` — uses `ParametricDistribution`, `RealParameter`
- `beastlabs.evolution.tree.SimpleConstrainedRandomTree`
- `beastlabs.evolution.tree.SimpleRandomTree` — uses `ParametricDistribution`

### Other — 0 legacy, 2 mixed (of 66 total)

**Mixed** (already imports spec; finish removing legacy):

- `beastlabs.app.beauti.DistributionViewer` — uses `Prior`
- `beastlabs.tools.TraceStateNodeSource` — uses `RealParameter`, `IntegerParameter`, `BooleanParameter`, `Parameter`

## Example XMLs pending migration

**Needs `version="2.8"`** (20):

- `examples/testSA.xml`
- `examples/testCladeSubstitutionModel.xml`
- `examples/testAncestralStateLogger.xml`
- `examples/testBactrianOperatorSchedule.xml`
- `examples/testMCMCMC.xml`
- `examples/testMultiParitionTreeLikelihood.xml`
- `examples/testMultiChainMCMC.xml`
- `examples/testAVMN.xml`
- `examples/testWeightedDirichlet/testNoPrior.xml`
- `examples/testWeightedDirichlet/testWeightedDirichlet.xml`
- `examples/simulator/testSteppingStone.xml`
- `examples/testTraitedTreeLikelihood.xml`
- `examples/independentMCMC.xml`
- `examples/testPrevalence.xml`
- `examples/testWilsonBaldingOperator.xml`
- `examples/benchmarking/testMultiParitionTreeLikelihoodRuntime.xml`
- `examples/benchmarking/testTreeLikelihoodRuntime.xml`
- `examples/testScript.xml`
- `examples/posthocanalysis/testPostHocAnalysis.xml`
- `examples/testBactrianOperators.xml`

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**No `beast.base.spec.*` references in body** (3):

- `src/main/resources/beast.labs/fxtemplates/Weibull.xml`
- `src/main/resources/beast.labs/fxtemplates/extras.xml`
- `src/main/resources/beast.labs/fxtemplates/SelfTuningMCMC.xml`


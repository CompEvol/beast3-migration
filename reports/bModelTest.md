# bModelTest — what's left

> **Scanned at:** 2026-05-13T11:11:54.058144  
> **Commit:** `394dca8` on `beast3-migration` — [view on GitHub](https://github.com/BEAST2-Dev/bModelTest/commit/394dca8f6bcc6c25eb3587c743e966389c01569b)  
> **Pom version:** `1.4.0-beta1`  
> **Maven Central:** not published as `io.github.beast2-dev:bModelTest` (not published (404))  
> **Stage hint:** compile-tested

## Summary

- **Java classes:** 6 on spec, 0 mixed, 0 legacy of 20 total
- **Example XMLs:** 2 on spec / 2 on `version="2.8"` / 2 total (+1 under legacy/)
- **BEAUti fxtemplates:** 1 clean / 1 use spec / 1 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** ❌ not published (not published (404))

## Build & release gaps

- **No `.github/workflows/*.yml`** — add CI to catch regressions on PRs.
- **No `release.sh` or `release/` directory** — used to assemble the CBAN-style ZIP.

## Maven Central

Not yet published as `io.github.beast2-dev:bModelTest`. Verify the namespace on central.sonatype.com and run the `release` profile to deploy.

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs

All example XMLs target `version="2.8"` and use spec types with no legacy parameter declarations. ✅

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`examples/bModelTest.xml`** (17):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `bmodeltest.evolution.sitemodel.BEASTModelTest` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.inference.util.RPNcalculator` | `beast.base.spec.inference.util.RPNcalculator` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Prior` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | _(no spec equivalent found)_ |

**`examples/beast2-paper.xml`** (23):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `beast.base.inference.distribution.Dirichlet` | `beast.base.spec.inference.distribution.Dirichlet` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.evolution.branchratemodel.UCRelaxedClockModel` | `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` |
| `spec=` | `UniformOperator` → `beast.base.inference.operator.UniformOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Prior` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | _(no spec equivalent found)_ |

**`doc/tutorial/primates.xml`** (16):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `spec=` | `bmodeltest.evolution.sitemodel.BEASTModelTest` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `spec=` | `beast.base.inference.util.ESS` | `beast.base.spec.inference.util.ESS` |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.Prior` | _(no spec equivalent found)_ |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.OneOnX` | _(no spec equivalent found)_ |

**`src/main/resources/bmodeltest/fxtemplates/bModelTest.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `OneOnX` → `beast.base.inference.distribution.OneOnX` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.inference.distribution.Dirichlet` | `beast.base.spec.inference.distribution.Dirichlet` |


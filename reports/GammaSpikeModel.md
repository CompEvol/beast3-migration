# GammaSpikeModel — what's left

> **Scanned at:** 2026-05-13T18:51:18.673215  
> **Commit:** `13bd1b2` on `main` — [view on GitHub](https://github.com/jordandouglas/GammaSpikeModel/commit/13bd1b200ebc431a0bcaea276b2eaca1508e7730)  
> **Pom version:** `1.3.0-SNAPSHOT`  
> **Maven Central:** not published as `io.github.jordandouglas:beast-gammaspike` (not published (404))  
> **Stage hint:** compile-tested

## Summary

- **Java classes:** 12 on spec, 0 mixed, 0 legacy of 31 total
- **Example XMLs:** 0 on spec / 11 on `version="2.8"` / 17 total
- **BEAUti fxtemplates:** 2 clean / 2 use spec / 2 total
- **Input rule:** 7 classes hold 30 Input(s) declared too concretely
- **Maven Central:** ❌ not published (not published (404))

## Build & release gaps

- **No `.github/workflows/*.yml`** — add CI to catch regressions on PRs.

## Maven Central

Not yet published as `io.github.jordandouglas:beast-gammaspike`. Verify the namespace on central.sonatype.com and run the `release` profile to deploy.

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Inputs declared too concretely

> Concrete spec params (`RealScalarParam`, `RealVectorParam`, …) belong on Operators, which need to write the parameter, and on Loggers, which need `getID()` to write column headers (the pure type interfaces deliberately do not extend `BEASTInterface`). Distributions, CalcNodes, and other read-only holders should declare the interface (`RealScalar`, `RealVector`, …) so adapters and transforms can be substituted. Legacy `RealParameter` / `Function` Inputs are violations everywhere.

### Distributions (3)

- `gammaspike.distribution.BranchSpikePrior`
    - concrete: `Input<IntVectorParam<? extends NonNegativeInt>>`
    - concrete: `Input<RealScalarParam<? extends PositiveReal>>`
    - concrete: `Input<RealScalarParam<? extends PositiveReal>>`
    - concrete: `Input<BoolScalarParam>`
- `gammaspike.distribution.StumpedTreePrior`
    - concrete: `Input<RealScalarParam<? extends NonNegativeReal>>`
    - concrete: `Input<RealScalarParam<? extends NonNegativeReal>>`
    - concrete: `Input<RealScalarParam<? extends NonNegativeReal>>`
    - concrete: `Input<RealScalarParam<? extends NonNegativeReal>>`
    - concrete: `Input<RealScalarParam<? extends NonNegativeReal>>`
    - concrete: `Input<RealScalarParam<? extends NonNegativeReal>>`
    - concrete: `Input<RealScalarParam<? extends UnitInterval>>`
    - concrete: `Input<RealScalarParam<? extends UnitInterval>>`
- `gammaspike.tree.BirthDeathStubSimulator`
    - concrete: `Input<RealScalarParam<? extends PositiveReal>>`
    - concrete: `Input<RealScalarParam<UnitInterval>>`
    - concrete: `Input<IntVectorParam<NonNegativeInt>>`

### CalcNodes (4)

- `gammaspike.clockmodel.PunctuatedRelaxedClockModel`
    - concrete: `Input<IntVectorParam<? extends NonNegativeInt>>`
    - concrete: `Input<RealScalarParam<? extends NonNegativeReal>>`
    - concrete: `Input<BoolScalarParam>`
    - concrete: `Input<RealVectorParam<? extends NonNegativeReal>>`
    - concrete: `Input<RealVectorParam<? extends NonNegativeReal>>`
- `gammaspike.clockmodel.SpikeSize`
    - concrete: `Input<RealVectorParam<NonNegativeReal>>`
    - concrete: `Input<RealScalarParam<NonNegativeReal>>`
- `gammaspike.sitemodel.ClockMixtureModel`
    - concrete: `Input<SimplexParam>`
    - concrete: `Input<RealScalarParam<? extends PositiveReal>>`
    - concrete: `Input<RealScalarParam<? extends PositiveReal>>`
- `gammaspike.tree.Stubs`
    - concrete: `Input<IntVectorParam<NonNegativeInt>>`
    - concrete: `Input<RealVectorParam<UnitInterval>>`
    - concrete: `Input<IntVectorParam<Int>>`
    - concrete: `Input<IntVectorParam<NonNegativeInt>>`
    - concrete: `Input<RealScalarParam<NonNegativeReal>>`

## Example XMLs pending migration

**Needs `version="2.8"`** (6):

- `examples/stubs/nrStubs.xml`
- `examples/stubs/rjStubs.xml`
- `examples/stubs/stochasticStubs.xml`
- `examples/cases/aaRS.xml`
- `examples/cases/cephalopods.xml`
- `examples/cases/indoeuropean.xml`

**Uses spec types but still has legacy `parameter.RealParameter`-style declarations** (11):

- `examples/DS/DS11.xml`
- `examples/DS/DS10.xml`
- `examples/DS/DS9.xml`
- `examples/DS/DS8.xml`
- `examples/DS/DS5.xml`
- `examples/DS/DS4.xml`
- `examples/DS/DS6.xml`
- `examples/DS/DS7.xml`
- `examples/DS/DS3.xml`
- `examples/DS/DS2.xml`
- `examples/DS/DS1.xml`

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`examples/stubs/nrStubs.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
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
| `map=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |

**`examples/stubs/rjStubs.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
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
| `map=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |

**`examples/stubs/stochasticStubs.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
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
| `map=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |

**`examples/DS/DS11.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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

**`examples/DS/DS10.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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

**`examples/DS/DS9.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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

**`examples/DS/DS8.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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

**`examples/DS/DS5.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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

**`examples/DS/DS4.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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

**`examples/DS/DS6.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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

**`examples/DS/DS7.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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

**`examples/DS/DS3.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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

**`examples/DS/DS2.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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

**`examples/DS/DS1.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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

**`examples/cases/aaRS.xml`** (28):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.ConstantPopulation` | `beast.base.spec.evolution.tree.coalescent.ConstantPopulation` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.evolution.sitemodel.SiteModel` | `beast.base.spec.evolution.sitemodel.SiteModel` |
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
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
| `map=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |
| `map=` | `beast.base.inference.distribution.Dirichlet` | `beast.base.spec.inference.distribution.Dirichlet` |

**`examples/cases/cephalopods.xml`** (19):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.ConstantPopulation` | `beast.base.spec.evolution.tree.coalescent.ConstantPopulation` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
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
| `map=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |
| `map=` | `beast.base.inference.distribution.Dirichlet` | `beast.base.spec.inference.distribution.Dirichlet` |

**`examples/cases/indoeuropean.xml`** (72):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `spec=` | `beast.base.evolution.tree.coalescent.ConstantPopulation` | `beast.base.spec.evolution.tree.coalescent.ConstantPopulation` |
| `spec=` | `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `spec=` | `beast.base.evolution.sitemodel.SiteModel` | `beast.base.spec.evolution.sitemodel.SiteModel` |
| `spec=` | `beast.base.evolution.substitutionmodel.BinaryCovarion` | `beast.base.spec.evolution.substitutionmodel.BinaryCovarion` |
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
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
| `map=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |
| `map=` | `beast.base.inference.distribution.Dirichlet` | `beast.base.spec.inference.distribution.Dirichlet` |


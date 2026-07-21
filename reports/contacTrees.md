# contacTrees — what's left

> **Scanned at:** 2026-07-21T16:02:07.591752  
> **Commit:** `0c20eda` on `master` — [view on GitHub](https://github.com/NicoNeureiter/contacTrees/commit/0c20eda49ae00f26846a45d390c7fe56b1a92c29)  
> **Pom version:** `2.0.0`  
> **Maven Central:** `io.github.niconeureiter:contactrees:2.0.0`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 3 on spec, 0 mixed, 0 legacy of 89 total
- **Example XMLs:** 31 on spec / 31 on `version="2.8"` / 31 total
    - skipped: 4 untracked by git
- **BEAUti fxtemplates:** 3 clean / 3 use spec / 3 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** 2.0.0

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs

All example XMLs target `version="2.8"` and use spec types with no legacy parameter declarations. ✅

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`fxtemplates/ContacTrees.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `map=` | `beast.base.inference.distribution.Uniform` | `beast.base.spec.inference.distribution.Uniform` |
| `map=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `map=` | `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |
| `map=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `map=` | `beast.base.inference.distribution.Gamma` | `beast.base.spec.inference.distribution.Gamma` |
| `map=` | `beast.base.inference.distribution.Beta` | `beast.base.spec.inference.distribution.Beta` |
| `map=` | `beast.base.inference.distribution.LaplaceDistribution` | `beast.base.spec.inference.distribution.Laplace` |
| `map=` | `beast.base.inference.distribution.InverseGamma` | `beast.base.spec.inference.distribution.InverseGamma` |
| `map=` | `beast.base.inference.distribution.Prior` | `beast.base.spec.inference.distribution.TensorDistribution` |

**`src/test/resources/examples/operatorTests/10taxa_ConvertedEdgeHop.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_NarrowExchange.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_ConvertedEdgeHopGibbs.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_Uniform.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_Scale.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_AddRemoveConversion.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_ConvertedEdgeSlide.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_ScaleRoot.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_ConversionSplit.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/2taxa_AddRemoveConversion.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_AddRemoveConversionGibbs.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_CFConversionSwap.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_GibbsSampleMovesPerConversion.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_WideExchange.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_ConvertedEdgeFlip.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/10taxa_WilsonBalding.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/operatorTests/2taxa_ConversionSplit.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`src/test/resources/examples/ACGsimulations/simulateACGs2taxon.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |


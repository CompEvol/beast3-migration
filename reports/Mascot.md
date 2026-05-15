# Mascot — what's left

> **Scanned at:** 2026-05-15T12:13:11.247973  
> **Commit:** `201f8b3` on `spec-input-types` — [view on GitHub](https://github.com/CompEvol/Mascot/commit/201f8b3df6dbbe6e29e311f319c6656f9ac8713b)  
> **Pom version:** `3.1.0-beta1`  
> **Maven Central:** `io.github.compevol:mascot:3.1.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 5 on spec, 0 mixed, 0 legacy of 121 total
- **Example XMLs:** 1 on spec / 1 on `version="2.8"` / 4 total
    - skipped: 10 untracked by git
- **BEAUti fxtemplates:** 0 clean / 0 use spec / 5 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** 3.1.0-beta1

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Inputs declaring `@Deprecated` types

> Each entry below is an `Input<T>` field where `T` (or one of its generic parameters) is annotated `@Deprecated` somewhere in the scanned packages. Such Inputs block XML migration: downstream XMLs cannot supply a non-deprecated value to them. Replace the declared type with the suggested spec equivalent (and update the field/local variable types accordingly).

**`mascot.logger.mappedProbLogger`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `List<Function>` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

**`mascot.logger.StructuredTreeLogger`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `List<Function>` | `Function` → `beast.base.core.Function` | _(no spec equivalent found)_ |

## Example XMLs pending migration

**Needs `version="2.8"`** (3):

- `examples/GLM.xml`
- `examples/ConstantBSSVS.xml`
- `examples/Skyline.xml`

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**No `beast.base.spec.*` references in body** (5):

- `src/main/resources/mascot/fxtemplates/MascotTreePriorTemplate.xml`
- `src/main/resources/mascot/fxtemplates/MascotConstant.xml`
- `src/main/resources/mascot/fxtemplates/MascotSkyline.xml`
- `src/main/resources/mascot/fxtemplates/MascotConstantBSSVS.xml`
- `src/main/resources/mascot/fxtemplates/MascotGLM.xml`

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`examples/Constant.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |

**`examples/GLM.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |

**`examples/ConstantBSSVS.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |

**`examples/Skyline.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `EpochFlexOperator` → `beast.base.evolution.operator.EpochFlexOperator` | _(no spec equivalent found)_ |
| `spec=` | `TreeStretchOperator` → `beast.base.evolution.operator.TreeStretchOperator` | _(no spec equivalent found)_ |
| `spec=` | `beast.base.evolution.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |

**`src/main/resources/mascot/fxtemplates/MascotTreePriorTemplate.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |

**`src/main/resources/mascot/fxtemplates/MascotSkyline.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.evolution.operator.AdaptableOperatorSampler` | `beast.base.spec.evolution.operator.AdaptableOperatorSampler` |
| `spec=` | `beast.base.evolution.Sum` | `beast.base.spec.evolution.Sum` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |

**`src/main/resources/mascot/fxtemplates/MascotConstantBSSVS.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.evolution.Sum` | `beast.base.spec.evolution.Sum` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |

**`src/main/resources/mascot/fxtemplates/MascotGLM.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.distribution.Exponential` | `beast.base.spec.inference.distribution.Exponential` |
| `spec=` | `beast.base.inference.distribution.Normal` | `beast.base.spec.inference.distribution.Normal` |
| `spec=` | `beast.base.evolution.Sum` | `beast.base.spec.evolution.Sum` |
| `spec=` | `beast.base.inference.distribution.Poisson` | `beast.base.spec.inference.distribution.Poisson` |


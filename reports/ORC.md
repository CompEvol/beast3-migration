# ORC — what's left

> **Scanned at:** 2026-05-13T11:04:03.316006  
> **Commit:** `8bce4e5` on `master` — [view on GitHub](https://github.com/jordandouglas/ORC/commit/8bce4e5a3fa74a0fac285c3586c9a1bbb3ee1a6c)  
> **Pom version:** `1.3.0-SNAPSHOT`  
> **Maven Central:** not published as `io.github.jordandouglas:beast-orc` (not published (404))  
> **Stage hint:** compile-tested

## Summary

- **Java classes:** 1 on spec, 0 mixed, 0 legacy of 59 total
- **Example XMLs:** 1 on spec / 1 on `version="2.8"` / 1 total
- **BEAUti fxtemplates:** 1 clean / 1 use spec / 1 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** ❌ not published (not published (404))

## Build & release gaps

- **No `.github/workflows/*.yml`** — add CI to catch regressions on PRs.

## Maven Central

Not yet published as `io.github.jordandouglas:beast-orc`. Verify the namespace on central.sonatype.com and run the `release` profile to deploy.

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs

All example XMLs target `version="2.8"` and use spec types with no legacy parameter declarations. ✅

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`examples/caterpillars.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
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

**`fxtemplates/OptimisedRelaxedClock.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.inference.operator.kernel.BactrianUpDownOperator` | _(no spec equivalent found)_ |


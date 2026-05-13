# bModelTest — what's left

> **Scanned at:** 2026-05-13T13:44:44.312524  
> **Commit:** `ecf0aaf` on `beast3-migration` — [view on GitHub](https://github.com/BEAST2-Dev/bModelTest/commit/ecf0aaf87e8ab4a680f9d09c50efcadda9d83839)  
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

## Inputs declaring `@Deprecated` types

> Each entry below is an `Input<T>` field where `T` (or one of its generic parameters) is annotated `@Deprecated` somewhere in the scanned packages. Such Inputs block XML migration: downstream XMLs cannot supply a non-deprecated value to them. Replace the declared type with the suggested spec equivalent (and update the field/local variable types accordingly).

**`bmodeltest.math.distributions.ModelSetPrior`** (1):

| Input type | Hit | Replacement |
|---|---|---|
| `ParametricDistribution` | `ParametricDistribution` → `beast.base.inference.distribution.ParametricDistribution` | `beast.base.spec.inference.distribution.TensorDistribution` |

## Example XMLs

All example XMLs target `version="2.8"` and use spec types with no legacy parameter declarations. ✅

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅


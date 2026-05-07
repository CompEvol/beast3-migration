# ORC — what's left

> **Scanned at:** 2026-05-07T21:03:30.231494  
> **Local checkout:** `/Users/adru001/Git/ORC` — commit `bb6bdbb` on `master` — [view on GitHub](https://github.com/jordandouglas/ORC/commit/bb6bdbb3875b735c37aab49a8e992cf368257838)  
> **Pom version:** `1.3.0-SNAPSHOT`  
> **Maven Central:** not published as `io.github.jordandouglas:beast-orc` (not published (404))  
> **Stage hint:** compile-tested

## Summary

- **Java classes:** 1 on spec, 0 mixed, 0 legacy of 55 total
- **Example XMLs:** 0 on spec / 1 on `version="2.8"` / 1 total
- **BEAUti fxtemplates:** 1 clean / 1 use spec / 1 total
- **Input rule:** 1 classes hold 2 Input(s) declared too concretely
- **Maven Central:** ❌ not published (not published (404))

## Build & release gaps

- **No `.github/workflows/*.yml`** — add CI to catch regressions on PRs.

## Maven Central

Not yet published as `io.github.jordandouglas:beast-orc`. Verify the namespace on central.sonatype.com and run the `release` profile to deploy.

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Inputs declared too concretely

> Concrete spec params (`RealScalarParam`, `RealVectorParam`, …) belong only on Operators, which need to write the parameter. Distributions, CalcNodes, Loggers and other read-only holders should declare the interface (`RealScalar`, `RealVector`, …) so adapters and transforms can be substituted. Legacy `RealParameter` / `Function` Inputs are violations everywhere.

### Loggers (1)

- `orc.inference.TipRateLogger`
    - concrete: `Input<IntVectorParam<NonNegativeInt>>`
    - concrete: `Input<RealVectorParam<NonNegativeReal>>`

## Example XMLs pending migration

**Uses spec types but still has legacy `parameter.RealParameter`-style declarations** (1):

- `examples/caterpillars.xml`

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅


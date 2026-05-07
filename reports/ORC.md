# ORC — what's left

> **Scanned at:** 2026-05-07T19:54:22.202167  
> **Local checkout:** `/Users/adru001/Git/ORC` — commit `bb6bdbb` on `master` — [view on GitHub](https://github.com/jordandouglas/ORC/commit/bb6bdbb3875b735c37aab49a8e992cf368257838)  
> **Pom version:** `1.3.0-SNAPSHOT`  
> **Maven Central:** not published as `io.github.jordandouglas:beast-orc` (not published (404))  
> **Stage hint:** compile-tested

## Summary

- **Java classes:** 6 on spec, 1 mixed, 1 legacy of 55 total
- **XMLs:** 0 on spec / 1 on `version="2.8"` / 2 total
- **Maven Central:** ❌ not published (not published (404))

## Build & release gaps

- **No `.github/workflows/*.yml`** — add CI to catch regressions on PRs.

## Maven Central

Not yet published as `io.github.jordandouglas:beast-orc`. Verify the namespace on central.sonatype.com and run the `release` profile to deploy.

## Java classes pending migration

### Operators — 0 legacy, 1 mixed (of 4 total)

**Mixed** (already imports spec; finish removing legacy):

- `orc.consoperators.SmallPulley` — uses `ParametricDistribution`, `RealParameter`, `CompoundRealParameter`

### Other — 1 legacy, 0 mixed (of 49 total)

**Legacy** (no spec imports yet):

- `orc.consoperators.ConsOperatorUtils` — uses `ParametricDistribution`

## XMLs pending migration

**Needs `version="2.8"`** (1):

- `fxtemplates/OptimisedRelaxedClock.xml`

**Targets BEAST 3 but missing `beast.base.spec.*` in namespace** (1):

- `examples/caterpillars.xml`


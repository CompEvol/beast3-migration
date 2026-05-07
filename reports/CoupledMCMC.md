# CoupledMCMC — what's left

> **Scanned at:** 2026-05-07T20:10:32.847033  
> **Local checkout:** `/Users/adru001/Git/CoupledMCMC` — commit `39e9c05` on `master` — [view on GitHub](https://github.com/CompEvol/CoupledMCMC/commit/39e9c057e474cfe64953592e330a0e1652f9f2ed)  
> **Pom version:** `1.3.0-beta1`  
> **Maven Central:** `io.github.compevol:coupled-mcmc:1.3.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 0 on spec, 0 mixed, 0 legacy of 14 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 1 total
- **BEAUti fxtemplates:** 0 clean / 0 use spec / 1 total
- **Maven Central:** 1.3.0-beta1

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs pending migration

**Needs `version="2.8"`** (1):

- `examples/hcv_coal_coupled.xml`

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**No `beast.base.spec.*` references in body** (1):

- `src/main/resources/coupled.mcmc/fxtemplates/CoupledMCMC.xml`


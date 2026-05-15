# BDMM-Prime — what's left

> **Scanned at:** 2026-05-15T12:13:15.551923  
> **Commit:** `ee37e1b` on `beast2.8-migration` — [view on GitHub](https://github.com/tgvaughan/BDMM-Prime/commit/ee37e1b913a94ee10d6bb12bbe79591b8c53864d)  
> **Pom version:** `3.0.0`  
> **Maven Central:** not published as `io.github.tgvaughan:bdmm-prime` (not published (404))  
> **Stage hint:** compile-tested
>
> Migration WIP on `beast2.8-migration` branch (Maven, v3.0.0).

## Summary

- **Java classes:** 4 on spec, 0 mixed, 0 legacy of 88 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 0 total
    - skipped: 27 under `xmlExcludeDirs` (validation)
- **BEAUti fxtemplates:** 2 clean / 2 use spec / 2 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** ❌ not published (not published (404))

## Build & release gaps

- **No `release.sh` or `release/` directory** — used to assemble the CBAN-style ZIP.

## Maven Central

Not yet published as `io.github.tgvaughan:bdmm-prime`. Verify the namespace on central.sonatype.com and run the `release` profile to deploy.

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅


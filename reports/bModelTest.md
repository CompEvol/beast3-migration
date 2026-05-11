# bModelTest — what's left

> **Scanned at:** 2026-05-11T16:08:34.304605  
> **Commit:** `f63ec16` on `beast3-migration` — [view on GitHub](https://github.com/BEAST2-Dev/bModelTest/commit/f63ec1627fae5b740569bb918327c817637c3fac)  
> **Pom version:** `1.4.0-beta1`  
> **Maven Central:** not published as `io.github.beast2-dev:bModelTest` (not published (404))  
> **Stage hint:** compile-tested

## Summary

- **Java classes:** 6 on spec, 0 mixed, 0 legacy of 20 total
- **Example XMLs:** 1 on spec / 1 on `version="2.8"` / 2 total (+1 under legacy/)
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

## Example XMLs pending migration

**Needs `version="2.8"`** (1):

- `doc/tutorial/primates.xml`

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅


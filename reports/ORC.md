# ORC — what's left

> **Scanned at:** 2026-05-13T16:18:07.608157  
> **Commit:** `c1c5910` on `clear-deprecated-refs` — [view on GitHub](https://github.com/jordandouglas/ORC/commit/c1c59105dbb50cabb8a6406c22e889852a03f57f)  
> **Pom version:** `1.3.0-SNAPSHOT`  
> **Maven Central:** `io.github.jordandouglas:beast-orc:1.3.1`  
> **Stage hint:** compile-tested

## Summary

- **Java classes:** 1 on spec, 0 mixed, 0 legacy of 59 total
- **Example XMLs:** 1 on spec / 1 on `version="2.8"` / 1 total
- **BEAUti fxtemplates:** 1 clean / 1 use spec / 1 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** 1.3.1

## Build & release gaps

- **No `.github/workflows/*.yml`** — add CI to catch regressions on PRs.

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs

All example XMLs target `version="2.8"` and use spec types with no legacy parameter declarations. ✅

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅


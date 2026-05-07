# morph-models — what's left

> **Scanned at:** 2026-05-08T08:25:24.397958  
> **Commit:** `6aee702` on `fix/spec-filtered-alignment` — [view on GitHub](https://github.com/CompEvol/morph-models/commit/6aee70263b5913573186954842df29d2010b51fd)  
> **Pom version:** `1.3.0-beta3`  
> **Maven Central:** `io.github.compevol:morph-models:1.3.0-beta3`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 5 on spec, 0 mixed, 0 legacy of 7 total
- **Example XMLs:** 2 on spec / 2 on `version="2.8"` / 2 total (+4 under legacy/)
- **BEAUti fxtemplates:** 0 clean / 1 use spec / 1 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** 1.3.0-beta3

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs

All example XMLs target `version="2.8"` and use spec types with no legacy parameter declarations. ✅

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**Uses spec types but still has legacy `parameter.*` declarations** (1):

- `src/main/resources/morph.models/fxtemplates/morph-models.xml`


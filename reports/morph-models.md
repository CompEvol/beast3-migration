# morph-models — what's left

> **Scanned at:** 2026-05-11T16:47:22.112366  
> **Commit:** `db64781` on `master` — [view on GitHub](https://github.com/CompEvol/morph-models/commit/db64781d130654714b8344de74e65dbe6929bd5a)  
> **Pom version:** `1.3.0-beta3`  
> **Maven Central:** `io.github.compevol:morph-models:1.3.0-beta3`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 4 on spec, 0 mixed, 1 legacy of 7 total
- **Example XMLs:** 2 on spec / 2 on `version="2.8"` / 2 total (+4 under legacy/)
- **BEAUti fxtemplates:** 0 clean / 1 use spec / 1 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** 1.3.0-beta3

## Java classes pending migration

### Other — 1 legacy, 0 mixed (of 2 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `morphmodels.evolution.alignment.AscertainedForParsimonyUninformativeFilteredAlignment` — extends `FilteredAlignment`

## Example XMLs

All example XMLs target `version="2.8"` and use spec types with no legacy parameter declarations. ✅

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**Uses spec types but still has legacy `parameter.*` declarations** (1):

- `src/main/resources/morph.models/fxtemplates/morph-models.xml`


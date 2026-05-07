# beast-classic — what's left

> **Scanned at:** 2026-05-07T20:10:32.697859  
> **Local checkout:** `/Users/adru001/Git/beast-classic` — commit `8baef54` on `master` — [view on GitHub](https://github.com/BEAST2-Dev/beast-classic/commit/8baef5485aa4b218655729d03c7f8d7ef3ab9668)  
> **Pom version:** `1.7.0-SNAPSHOT`  
> **Maven Central:** `io.github.beast2-dev:beast-classic:1.7.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 37 on spec, 1 mixed, 0 legacy of 68 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 10 total
- **BEAUti fxtemplates:** 0 clean / 0 use spec / 5 total
- **Maven Central:** 1.7.0-beta1

## Java classes pending migration

### Other — 0 legacy, 1 mixed (of 28 total)

**Mixed** (already imports spec; finish removing legacy):

- `beastclassic.app.beauti.BeautiDiscreteTraitProvider` — uses `Prior`, `ParametricDistribution`

## Example XMLs pending migration

**Needs `version="2.8"`** (10):

- `examples/RacRABV_LogNRRW2.xml`
- `examples/RacRABV_LogNRRW1.xml`
- `examples/beast1/testBinaryDollo2.xml`
- `examples/beast1/testBinaryDollo1.xml`
- `examples/testSkyGrid.xml`
- `examples/H5N1_HA_discrete2.xml`
- `examples/H5N1_HA_discrete1.xml`
- `examples/testSkyRide.xml`
- `examples/testDiscreteSmall.xml`
- `doc/tutorial/phylogeography_discrete/data/H5N1.xml`

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**No `beast.base.spec.*` references in body** (5):

- `src/main/resources/beast.classic/fxtemplates/RelaxedClockModels.xml`
- `src/main/resources/beast.classic/fxtemplates/discrete-trait.xml`
- `src/main/resources/beast.classic/fxtemplates/ClassicTreePriors.xml`
- `src/main/resources/beast.classic/fxtemplates/ClassicSubtseModels.xml`
- `src/main/resources/beast.classic/fxtemplates/StarBeast.xml`


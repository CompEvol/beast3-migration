# Mascot — what's left

> **Scanned at:** 2026-05-07T20:16:33.343870  
> **Local checkout:** `/Users/adru001/Git/Mascot` — commit `f9d1aca` on `master` — [view on GitHub](https://github.com/CompEvol/Mascot/commit/f9d1aca862f4c94a3e174e63fff51c8d7588b800)  
> **Pom version:** `3.1.0-beta1`  
> **Maven Central:** `io.github.compevol:mascot:3.1.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 33 on spec, 5 mixed, 2 legacy of 112 total
- **Example XMLs:** 1 on spec / 1 on `version="2.8"` / 4 total
- **BEAUti fxtemplates:** 0 clean / 0 use spec / 5 total
- **Maven Central:** 3.1.0-beta1

## Java classes pending migration

### Distributions — 0 legacy, 5 mixed (of 11 total)

**Mixed** (already imports spec; finish removing legacy):

- `mascot.glmmodel.ErrorSmoothing` — uses `ParametricDistribution`
- `mascot.glmmodel.MaxRate` — uses `ParametricDistribution`
- `mascot.skyline.GLMPrior` — uses `ParametricDistribution`
- `mascot.skyline.GrowthRateSmoothingPrior` — uses `ParametricDistribution`
- `mascot.skyline.LogSmoothingPrior` — uses `ParametricDistribution`

### Other — 2 legacy, 0 mixed (of 50 total)

**Legacy** (no spec imports yet):

- `mascot.app.beauti.NeDynamicsListInputEditor` — uses `Prior`, `RealParameter`
- `mascot.util.BEAUtiConnector` — uses `Prior`

## Example XMLs pending migration

**Needs `version="2.8"`** (3):

- `examples/GLM.xml`
- `examples/ConstantBSSVS.xml`
- `examples/Skyline.xml`

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**No `beast.base.spec.*` references in body** (5):

- `src/main/resources/mascot/fxtemplates/MascotTreePriorTemplate.xml`
- `src/main/resources/mascot/fxtemplates/MascotConstant.xml`
- `src/main/resources/mascot/fxtemplates/MascotSkyline.xml`
- `src/main/resources/mascot/fxtemplates/MascotConstantBSSVS.xml`
- `src/main/resources/mascot/fxtemplates/MascotGLM.xml`


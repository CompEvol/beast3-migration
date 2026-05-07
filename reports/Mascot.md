# Mascot — what's left

> **Scanned at:** 2026-05-07T19:54:21.668212  
> **Local checkout:** `/Users/adru001/Git/Mascot` — commit `f9d1aca` on `master` — [view on GitHub](https://github.com/CompEvol/Mascot/commit/f9d1aca862f4c94a3e174e63fff51c8d7588b800)  
> **Pom version:** `3.1.0-beta1`  
> **Maven Central:** `io.github.compevol:mascot:3.1.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 30 on spec, 5 mixed, 2 legacy of 112 total
- **XMLs:** 1 on spec / 1 on `version="2.8"` / 14 total
- **Maven Central:** 3.1.0-beta1

## Java classes pending migration

### Distributions — 0 legacy, 5 mixed (of 9 total)

**Mixed** (already imports spec; finish removing legacy):

- `mascot.glmmodel.ErrorSmoothing` — uses `ParametricDistribution`
- `mascot.glmmodel.MaxRate` — uses `ParametricDistribution`
- `mascot.skyline.GLMPrior` — uses `ParametricDistribution`
- `mascot.skyline.GrowthRateSmoothingPrior` — uses `ParametricDistribution`
- `mascot.skyline.LogSmoothingPrior` — uses `ParametricDistribution`

### Other — 2 legacy, 0 mixed (of 66 total)

**Legacy** (no spec imports yet):

- `mascot.app.beauti.NeDynamicsListInputEditor` — uses `Prior`, `RealParameter`
- `mascot.util.BEAUtiConnector` — uses `Prior`

## XMLs pending migration

**Needs `version="2.8"`** (13):

- `examples/GLM.xml`
- `examples/ConstantBSSVS.xml`
- `examples/Skyline.xml`
- `src/main/resources/mascot/fxtemplates/MascotTreePriorTemplate.xml`
- `src/main/resources/mascot/fxtemplates/MascotConstant.xml`
- `src/main/resources/mascot/fxtemplates/MascotSkyline.xml`
- `src/main/resources/mascot/fxtemplates/MascotConstantBSSVS.xml`
- `src/main/resources/mascot/fxtemplates/MascotGLM.xml`
- `src/main/resources/mascot/fxtemplates/MascotTreePriorTemplate.xml`
- `src/main/resources/mascot/fxtemplates/MascotConstant.xml`
- `src/main/resources/mascot/fxtemplates/MascotSkyline.xml`
- `src/main/resources/mascot/fxtemplates/MascotConstantBSSVS.xml`
- `src/main/resources/mascot/fxtemplates/MascotGLM.xml`


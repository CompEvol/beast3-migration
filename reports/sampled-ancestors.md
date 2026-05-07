# sampled-ancestors — what's left

> **Scanned at:** 2026-05-07T20:10:33.350823  
> **Local checkout:** `/Users/adru001/Git/sampled-ancestors` — commit `09bc95c` on `master` — [view on GitHub](https://github.com/CompEvol/sampled-ancestors/commit/09bc95cd1771ce4d85452314331a612549948e69)  
> **Pom version:** `2.3.0-beta1`  
> **Maven Central:** `io.github.compevol:sampled-ancestors:2.3.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 15 on spec, 1 mixed, 1 legacy of 52 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 3 total
- **BEAUti fxtemplates:** 1 clean / 1 use spec / 1 total
- **Maven Central:** 2.3.0-beta1

## Java classes pending migration

### Distributions — 1 legacy, 1 mixed (of 6 total)

**Mixed** (already imports spec; finish removing legacy):

- `sa.math.distributions.DegenerateBeta` — uses `ParametricDistribution`

**Legacy** (no spec imports yet):

- `sa.math.distributions.DegenerateUniform` — uses `ParametricDistribution`

## Example XMLs pending migration

**Needs `version="2.8"`** (3):

- `examples/bears_ranges.xml`
- `examples/brachiopods.xml`
- `examples/bears.xml`

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅


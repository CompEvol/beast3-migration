# sampled-ancestors — what's left

> **Scanned at:** 2026-05-07T21:16:05.166578  
> **Local checkout:** `/Users/adru001/Git/sampled-ancestors` — commit `09bc95c` on `master` — [view on GitHub](https://github.com/CompEvol/sampled-ancestors/commit/09bc95cd1771ce4d85452314331a612549948e69)  
> **Pom version:** `2.3.0-beta1`  
> **Maven Central:** `io.github.compevol:sampled-ancestors:2.3.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 0 on spec, 0 mixed, 5 legacy of 52 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 3 total
- **BEAUti fxtemplates:** 1 clean / 1 use spec / 1 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** 2.3.0-beta1

## Java classes pending migration

### Distributions — 2 legacy, 0 mixed (of 6 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `sa.math.distributions.DegenerateBeta` — extends `ParametricDistribution`
- `sa.math.distributions.DegenerateUniform` — extends `ParametricDistribution`

### Other — 3 legacy, 0 mixed (of 24 total)

**Legacy** (extends a legacy base — `ParametricDistribution`, `Prior`, or a `*Parameter` class):

- `sa.evolution.tree.AncestryConstraint` — extends `MRCAPrior`
- `sa.math.distributions.SAMRCAPrior` — extends `MRCAPrior`
- `sa.math.distributions.SpecialMRCAPrior` — extends `MRCAPrior`

## Example XMLs pending migration

**Needs `version="2.8"`** (3):

- `examples/bears_ranges.xml`
- `examples/brachiopods.xml`
- `examples/bears.xml`

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅


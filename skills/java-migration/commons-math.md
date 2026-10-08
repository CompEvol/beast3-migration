---
name: beast3-commons-math
description: Migrate BEAST2's org.apache.commons.math.distribution.* imports to BEAST3's org.apache.commons.statistics.distribution.* equivalents. Fires on any `import org.apache.commons.math.` in a Java file — commons.math has no JPMS module and cannot be a module-info.java dependency under BEAST3.
metadata:
  type: skill
---

You are migrating BEAST2's legacy `org.apache.commons.math.distribution.*` usage to BEAST3's
`org.apache.commons.statistics.distribution.*` equivalents. This trigger condition is exact —
apply this skill to any file matching `import org.apache.commons.math.` (any subpackage:
`.distribution.`, `.MathException`, etc.), whether or not the file also imports any `beast.base.*`
class. Apply all rules below.

---

## Background

BEAST2 uses `org.apache.commons.math` (the pre-2010 "Jakarta Commons Math" 1.x/2.x line, package
`org.apache.commons.math.*` with **no** `3`) for probability distributions. That library publishes
no JPMS module and cannot be listed in `module-info.java`, so it cannot compile under BEAST3.
BEAST3 replaces it with `org.apache.commons.statistics.distribution`, a modern, modular successor
with a narrower API: one interface + one static factory per distribution, no checked exceptions.

**Do not confuse this with `org.apache.commons.math3`** (`org.apache.commons.math3.distribution.*`)
— that is a different, intermediate legacy API (still has checked exceptions, still has `Impl`
suffixes in some places, but different exception types and method signatures). If a file imports
`math3`, do not apply these rules mechanically; check the actual `math3` API first.

---

## Rules

### R1 — Import: drop `Impl`, repackage to `commons.statistics.distribution`

`org.apache.commons.math.distribution.X` (interface) and
`org.apache.commons.math.distribution.XImpl` (concrete class) collapse into a single import:
`org.apache.commons.statistics.distribution.X`. There is no `XImpl` in the new library — `X` is
simultaneously the type and, via `X.of(...)`, the factory.

```diff
-import org.apache.commons.math.distribution.BetaDistribution;
-import org.apache.commons.math.distribution.BetaDistributionImpl;
+import org.apache.commons.statistics.distribution.BetaDistribution;
```

Base interfaces (`ContinuousDistribution`, `DiscreteDistribution`) move package with no rename:

```diff
-import org.apache.commons.math.distribution.ContinuousDistribution;
+import org.apache.commons.statistics.distribution.ContinuousDistribution;
```

**Distribution classes confirmed present in `commons-statistics-distribution`** (verified against
the artifact's class list — treat any name not on this list as unconfirmed, see Edge Cases):
`Beta` `Binomial` `Cauchy` `ChiSquared` `Exponential` `F` `FoldedNormal` `Gamma` `Geometric`
`Gumbel` `Hypergeometric` `Laplace` `Levy` `Logistic` `LogNormal` `LogUniform` `Nakagami` `Normal`
`Pareto` `Pascal` `Poisson` `T` `Trapezoidal` `Triangular` `TruncatedNormal` `UniformContinuous`
`UniformDiscrete` `Weibull` `Zipf` — each as `...Distribution`.

Note the two uniform distributions do not use the old commons-math `Uniform...` naming 1:1 — verify
the exact class name against this list rather than assuming a name survives unchanged.

### R2 — Construction: `new XImpl(args)` → `X.of(args)`

The new API replaces the constructor with a static factory method `of(args)` on the interface
itself. Arguments keep the same order and meaning:

```diff
-betaDistribution = new BetaDistributionImpl(alphaInput.get(), 1.0);
+betaDistribution = BetaDistribution.of(alphaInput.get(), 1.0);

-GammaDistribution distr = new GammaDistributionImpl(alpha, beta);
+GammaDistribution distr = GammaDistribution.of(alpha, beta);

-ExponentialDistribution distr = new ExponentialDistributionImpl(meanLength);
+ExponentialDistribution distr = ExponentialDistribution.of(meanLength);
```

Declare the variable/field using the plain interface type (`BetaDistribution`,
`GammaDistribution`, …) — never reference an `*Impl` class; it does not exist in the new library.

### R3 — Exception handling: drop the checked `MathException`

`org.apache.commons.math.MathException` does not exist in the new library and must not be
imported. `cumulativeProbability` / `inverseCumulativeProbability` no longer declare a checked
exception — invalid arguments throw the **unchecked**
`org.apache.commons.statistics.distribution.DistributionException` (an `IllegalArgumentException`
subtype) instead. Do not catch or translate it unless the surrounding code has a specific reason
to handle bad input; let it propagate like any other `IllegalArgumentException`.

Remove the `try/catch (MathException e)` wrapper and any `throws MathException` on the enclosing
method — do not replace it with a catch for `DistributionException`:

```diff
-try {
-    distributionQuantiles[i] = branchLengthDistr.inverseCumulativeProbability((i+0.0)/127.0);
-} catch (MathException e) {
-    e.printStackTrace();
-    throw new RuntimeException(e);
-}
+distributionQuantiles[i] = branchLengthDistr.inverseCumulativeProbability((i+0.0)/127.0);
```

```diff
-private double estimateMarginalLikelihood(..., double[] betas, boolean verbose) throws MathException, InterruptedException {
+private double estimateMarginalLikelihood(..., double[] betas, boolean verbose) throws InterruptedException {
```

Only remove `MathException` from a `throws` clause — leave every other declared exception
(e.g. `InterruptedException`) untouched.

### R4 — Declare the module dependency

Add the new module to `module-info.java`'s `requires` block if it is not already present:

```
requires org.apache.commons.statistics.distribution;
```

---

## Edge Cases

- **Unconfirmed distribution class**: if the old code imports an `org.apache.commons.math.distribution.X` not on the R1 list (or an `IntegerDistribution`/discrete-specific type), do not
  guess the new name. Check the installed artifact (`unzip -l` the
  `commons-statistics-distribution` jar, or its javadoc) for the closest match before renaming. If
  no equivalent exists, leave the import unchanged and flag with
  `// TODO: no commons-statistics equivalent found for <ClassName>`.
- **`org.apache.commons.math3.*`**: a different, unrelated legacy API — do not touch under this
  skill (see Background).
- **Random sampling**: `createSampler(UniformRandomProvider)` requires
  `org.apache.commons.rng` on the module path. Only add that dependency if the migrated code
  actually calls `createSampler` (the mechanical `cumulativeProbability`/`inverseCumulativeProbability`/`.of()` migration in R1–R3 does not need it).
- **Partial migration**: if a file already imports `org.apache.commons.statistics.distribution.*`
  for some distributions, only migrate the remaining `org.apache.commons.math.*` imports — do not
  re-touch what's already migrated.

---

## Verification checklist

After editing a file:

1. `grep -rn "org\.apache\.commons\.math\b" src/` — no matches (note: `commons.math3` and
   `commons.statistics` are different packages, not part of this check).
2. `grep -rn "DistributionImpl\b" src/` — no matches; all construction goes through `.of(...)`.
3. `grep -rn "MathException" src/` — no matches.
4. `org.apache.commons.statistics.distribution` is present in `module-info.java`'s `requires` list.
5. `mvn compile -q` succeeds.

---

## XML migration

This skill is Java-only. `org.apache.commons.math.distribution.*` classes are internal
implementation detail of Java `Distribution`/`Runnable` classes — they are never the value of a
BEAST XML `spec=`/`class=`/`type=` attribute (those attributes always reference the containing
BEASTObject, e.g. `spec="modelselection.gss.distribution.GSSTreeDistribution"`, never the
commons-math class it happens to use internally). Verified empirically: no FxTemplate or example
XML in this codebase's `src/main/resources/` or `src/test/resources/` contains the string
`commons.math` or `commons.statistics`. No XML-level changes are needed for these rules; do not
add an `org.apache.commons.math` grep to the Step 4–6 XML conversion passes. For XML
class-reference migration in general, see `xml-migration/XML-MIGRATION-STRATEGY.md`.

---

## Log (Mode 2b — Changes field)

- Distribution classes migrated: e.g. `BetaDistribution, GammaDistribution → commons.statistics (2×)`
- `MathException removed: y/n`
- `module-info.java requires added: y/n`
- TODOs: list any `// TODO: no commons-statistics equivalent found for <ClassName>` added (or "none")

---
name: beast3-subst-models
description: Migrate BEAST2 substitution model imports and usages to BEAST3 spec equivalents, including Frequencies, GTR, HKY, TN93, and the SubstitutionModel.Base inner class
metadata:
  type: skill
---

You are migrating BEAST2 substitution model classes to the BEAST3 spec API. Apply all rules below.

---

## Rules

### R1 — Import prefix: add `.spec.`

Replace all `beast.base.evolution.substitutionmodel.*` imports with their BEAST3 equivalents.
Every class in the table below has a confirmed BEAST3 counterpart:

| BEAST2 import | BEAST3 import |
|---|---|
| `beast.base.evolution.substitutionmodel.Frequencies` | `beast.base.spec.evolution.substitutionmodel.Frequencies` |
| `beast.base.evolution.substitutionmodel.GTR` | `beast.base.spec.evolution.substitutionmodel.GTR` |
| `beast.base.evolution.substitutionmodel.HKY` | `beast.base.spec.evolution.substitutionmodel.HKY` |
| `beast.base.evolution.substitutionmodel.TN93` | `beast.base.spec.evolution.substitutionmodel.TN93` |
| `beast.base.evolution.substitutionmodel.TIM` | `beast.base.spec.evolution.substitutionmodel.TIM` |
| `beast.base.evolution.substitutionmodel.TVM` | `beast.base.spec.evolution.substitutionmodel.TVM` |
| `beast.base.evolution.substitutionmodel.SYM` | `beast.base.spec.evolution.substitutionmodel.SYM` |
| `beast.base.evolution.substitutionmodel.JukesCantor` | `beast.base.spec.evolution.substitutionmodel.JukesCantor` |
| `beast.base.evolution.substitutionmodel.GeneralSubstitutionModel` | `beast.base.spec.evolution.substitutionmodel.GeneralSubstitutionModel` |
| `beast.base.evolution.substitutionmodel.BasicGeneralSubstitutionModel` | `beast.base.spec.evolution.substitutionmodel.BasicGeneralSubstitutionModel` |
| `beast.base.evolution.substitutionmodel.ComplexSubstitutionModel` | `beast.base.spec.evolution.substitutionmodel.ComplexSubstitutionModel` |
| `beast.base.evolution.substitutionmodel.BasicComplexSubstitutionModel` | `beast.base.spec.evolution.substitutionmodel.BasicComplexSubstitutionModel` |
| `beast.base.evolution.substitutionmodel.EmpiricalSubstitutionModel` | `beast.base.spec.evolution.substitutionmodel.EmpiricalSubstitutionModel` |
| `beast.base.evolution.substitutionmodel.MutationDeathModel` | `beast.base.spec.evolution.substitutionmodel.MutationDeathModel` |
| `beast.base.evolution.substitutionmodel.BinaryCovarion` | `beast.base.spec.evolution.substitutionmodel.BinaryCovarion` |
| `beast.base.evolution.substitutionmodel.Blosum62` | `beast.base.spec.evolution.substitutionmodel.Blosum62` |
| `beast.base.evolution.substitutionmodel.CPREV` | `beast.base.spec.evolution.substitutionmodel.CPREV` |
| `beast.base.evolution.substitutionmodel.Dayhoff` | `beast.base.spec.evolution.substitutionmodel.Dayhoff` |
| `beast.base.evolution.substitutionmodel.JTT` | `beast.base.spec.evolution.substitutionmodel.JTT` |
| `beast.base.evolution.substitutionmodel.MTREV` | `beast.base.spec.evolution.substitutionmodel.MTREV` |
| `beast.base.evolution.substitutionmodel.WAG` | `beast.base.spec.evolution.substitutionmodel.WAG` |

**Do NOT rename** — these have no `.spec.` counterpart yet:
- `beast.base.evolution.substitutionmodel.SubstitutionModel` (the interface — keep as-is)

### R2 — `SubstitutionModel.Base` inner class → top-level `Base`

In BEAST3 the abstract base is a top-level class, not an inner class of `SubstitutionModel`.

Replace:
```java
import beast.base.evolution.substitutionmodel.SubstitutionModel;
// used as SubstitutionModel.Base in the file
```
With:
```java
import beast.base.spec.evolution.substitutionmodel.Base;
// keep SubstitutionModel import only if the SubstitutionModel interface itself is used
```

Replace every type reference `SubstitutionModel.Base` in the file with `Base` — field declarations,
method parameters, return types, and `Input<>` generics.

If the file uses `SubstitutionModel` (the interface) AND `SubstitutionModel.Base`, keep both
imports and change only the `.Base` references.

### R3 — `Frequencies` now takes a `Simplex`

`Frequencies.initByName("frequencies", ...)` in BEAST3 expects a `Simplex`, not a `RealParameter`.
When you see:

```java
// BEAST2
RealParameter freqParam = new RealParameter("0.25 0.25 0.25 0.25");
freqs.initByName("frequencies", freqParam, "estimate", false);
```

Change to:
```java
// BEAST3
Simplex freqParam = new SimplexParam(new double[]{0.25, 0.25, 0.25, 0.25});
freqs.initByName("frequencies", freqParam, "estimate", false);
```

This requires the `SimplexParam` and `Simplex` imports from `parameters.md`.

### R4 — Rate inputs to GTR / GeneralSubstitutionModel

GTR rate inputs (`rateAC`, `rateAG`, `rateAT`, `rateCG`, `rateCT`, `rateGT`) expect
`RealScalarParam<PositiveReal>` in BEAST3:

```java
// BEAST2
gtr.initByName("rateAC", new RealParameter("1.0"), ...);

// BEAST3
gtr.initByName("rateAC", new RealScalarParam<>(1.0, PositiveReal.INSTANCE), ...);
```

See `parameters.md` for the full parameter migration rules.

---

## Edge Cases

- **Unknown substitution model class**: if a class under `beast.base.evolution.substitutionmodel`
  is not in the table above, check the BEAST3 source before rewriting. If no spec counterpart
  exists, leave the import unchanged and add `// TODO: no beast3 spec class found for <ClassName>`.
- **Wildcard imports** (`import beast.base.evolution.substitutionmodel.*`): expand to explicit
  imports for only the classes actually used, then apply R1.
- **Partial migration**: if the file already imports some `beast.base.spec.evolution.substitutionmodel.*`
  classes, do not re-add them.

---

## Log (Mode 2b — Changes field)

- Classes renamed to `.spec.`: list each (e.g. `HKY, GTR → .spec.`)
- `SubstitutionModel.Base → Base: yes/no`
- `Frequencies.frequencies → SimplexParam: yes/no`
- `Warnings — non-deprecated BEAST2 classes migrated: N` — list each class name (or "none")

---
name: beast3-tree-coalescent
description: Migrate BEAST2 tree, coalescent, and speciation model imports to BEAST3 spec equivalents
metadata:
  type: skill
---

You are migrating BEAST2 tree topology, coalescent, and speciation model classes to the BEAST3 spec
API. Apply all rules below.

---

## Rules

### R1 — Tree class imports

Pattern (U2): `beast.base.evolution.tree.X` → `beast.base.spec.evolution.tree.X`

Confirmed spec counterparts:
`ClusterTree` · `SATree` · `StrictBinaryTree` · `MRCAPrior` · `TreeWithMetaDataLogger`

**Do NOT rename** — no `.spec.` counterpart:
`Tree` · `Node` · `TreeInterface` · `TreeParser`

### R2 — Coalescent imports

Pattern (U2): `beast.base.evolution.tree.coalescent.X` → `beast.base.spec.evolution.tree.coalescent.X`

Confirmed spec counterparts:
`RandomTree` · `ConstantPopulation` · `ExponentialGrowth` · `BayesianSkyline` · `CompoundPopulationFunction` · `ScaledPopulationFunction`

### R3 — Speciation model imports

Pattern (U2): `beast.base.evolution.speciation.X` → `beast.base.spec.evolution.speciation.X`

Confirmed spec counterparts:
`YuleModel` · `BirthDeathGernhard08Model` · `CalibratedYuleModel` · `CalibratedYuleInitialTree` · `CalibratedBirthDeathModel` · `CalibrationPoint` · `CalibrationLineagesIterator` · `GeneTreeForSpeciesTreeDistribution` · `RandomGeneTree` · `SpeciesTreePopFunction` · `SpeciesTreePrior` · `StarBeastStartState` · `SpeciesTreeLogger`

### R4 — Population size and rate parameters

Coalescent and speciation models take population size and rate parameters. When migrating
`initByName(...)` call sites in tests or `initAndValidate`:
- Population sizes → `RealScalarParam<PositiveReal>` or `RealVectorParam<PositiveReal>`
- Birth / death rates → `RealScalarParam<PositiveReal>`
- Sampling proportions → `RealScalarParam<UnitInterval>`

See `java-migration/parameters.md` for the full parameter migration rules.

### R5 — `MRCAPrior` calibration inputs

`MRCAPrior` takes a distribution input that may come from `java-migration/distributions.md`. Ensure the
distribution is also migrated to its BEAST3 spec counterpart when constructing test fixtures.

---

## Edge Cases

- **Unknown class under `beast.base.evolution.speciation` or `beast.base.evolution.tree`**: check
  the BEAST3 source before rewriting. If no spec counterpart exists, leave unchanged and flag with
  `// TODO: no beast3 spec class found for <ClassName>`.
- **Wildcard imports**: expand to explicit imports for only the classes actually used, then apply
  R1–R3.
- **Partial migration**: if the file already imports any `beast.base.spec.evolution.tree.*` or
  `beast.base.spec.evolution.speciation.*` classes, do not duplicate them.

---

## XML migration

For `spec=` attribute changes in example XMLs and FxTemplates, see **`xml-migration/example-xmls.md`** (Step 3) and **`xml-migration/fxtemplates.md`** (Step 3).

---

## Log (Mode 2b — Changes field)

- Classes renamed to `.spec.` by category:
  - tree: N (list each, e.g. `ClusterTree, MRCAPrior → .spec.`)
  - coalescent: N (e.g. `RandomTree, ConstantPopulation → .spec.`)
  - speciation: N (e.g. `YuleModel → .spec.`)
- `Warnings — non-deprecated BEAST2 classes migrated: N` — list each class name (or "none")

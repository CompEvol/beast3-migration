---
name: beast3-tree-coalescent
description: Migrate BEAST2 tree, coalescent, and speciation model imports to BEAST3 spec equivalents
metadata:
  type: skill
---

You are migrating BEAST2 tree topology, coalescent, and speciation model classes to the BEAST3 spec
API. Apply all rules below; make minimal, surgical changes only.

---

## Rules

### R1 — Tree class imports

| BEAST2 import | BEAST3 import |
|---|---|
| `beast.base.evolution.tree.ClusterTree` | `beast.base.spec.evolution.tree.ClusterTree` |
| `beast.base.evolution.tree.SATree` | `beast.base.spec.evolution.tree.SATree` |
| `beast.base.evolution.tree.StrictBinaryTree` | `beast.base.spec.evolution.tree.StrictBinaryTree` |
| `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |
| `beast.base.evolution.tree.TreeWithMetaDataLogger` | `beast.base.spec.evolution.TreeWithMetaDataLogger` |

**Do NOT rename** — no `.spec.` counterpart yet:
- `beast.base.evolution.tree.TreeInterface`
- `beast.base.evolution.tree.Tree`
- `beast.base.evolution.tree.Node`
- `beast.base.evolution.tree.TreeParser`

### R2 — Coalescent imports

| BEAST2 import | BEAST3 import |
|---|---|
| `beast.base.evolution.tree.coalescent.RandomTree` | `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `beast.base.evolution.tree.coalescent.ConstantPopulation` | `beast.base.spec.evolution.tree.coalescent.ConstantPopulation` |
| `beast.base.evolution.tree.coalescent.ExponentialGrowth` | `beast.base.spec.evolution.tree.coalescent.ExponentialGrowth` |
| `beast.base.evolution.tree.coalescent.BayesianSkyline` | `beast.base.spec.evolution.tree.coalescent.BayesianSkyline` |
| `beast.base.evolution.tree.coalescent.CompoundPopulationFunction` | `beast.base.spec.evolution.tree.coalescent.CompoundPopulationFunction` |
| `beast.base.evolution.tree.coalescent.ScaledPopulationFunction` | `beast.base.spec.evolution.tree.coalescent.ScaledPopulationFunction` |

### R3 — Speciation model imports

| BEAST2 import | BEAST3 import |
|---|---|
| `beast.base.evolution.speciation.YuleModel` | `beast.base.spec.evolution.speciation.YuleModel` |
| `beast.base.evolution.speciation.BirthDeathGernhard08Model` | `beast.base.spec.evolution.speciation.BirthDeathGernhard08Model` |
| `beast.base.evolution.speciation.CalibratedYuleModel` | `beast.base.spec.evolution.speciation.CalibratedYuleModel` |
| `beast.base.evolution.speciation.CalibratedYuleInitialTree` | `beast.base.spec.evolution.speciation.CalibratedYuleInitialTree` |
| `beast.base.evolution.speciation.CalibratedBirthDeathModel` | `beast.base.spec.evolution.speciation.CalibratedBirthDeathModel` |
| `beast.base.evolution.speciation.CalibrationPoint` | `beast.base.spec.evolution.speciation.CalibrationPoint` |
| `beast.base.evolution.speciation.CalibrationLineagesIterator` | `beast.base.spec.evolution.speciation.CalibrationLineagesIterator` |
| `beast.base.evolution.speciation.GeneTreeForSpeciesTreeDistribution` | `beast.base.spec.evolution.speciation.GeneTreeForSpeciesTreeDistribution` |
| `beast.base.evolution.speciation.RandomGeneTree` | `beast.base.spec.evolution.speciation.RandomGeneTree` |
| `beast.base.evolution.speciation.SpeciesTreePopFunction` | `beast.base.spec.evolution.speciation.SpeciesTreePopFunction` |
| `beast.base.evolution.speciation.SpeciesTreePrior` | `beast.base.spec.evolution.speciation.SpeciesTreePrior` |
| `beast.base.evolution.speciation.StarBeastStartState` | `beast.base.spec.evolution.speciation.StarBeastStartState` |
| `beast.base.evolution.speciation.SpeciesTreeLogger` | `beast.base.spec.evolution.speciation.SpeciesTreeLogger` |

### R4 — Population size and rate parameters

Coalescent and speciation models take population size and rate parameters. When migrating
`initByName(...)` call sites in tests or `initAndValidate`:
- Population sizes → `RealScalarParam<PositiveReal>` or `RealVectorParam<PositiveReal>`
- Birth / death rates → `RealScalarParam<PositiveReal>`
- Sampling proportions → `RealScalarParam<UnitInterval>`

See `parameters.md` for the full parameter migration rules.

### R5 — `MRCAPrior` calibration inputs

`MRCAPrior` takes a distribution input that may come from `distributions.md`. Ensure the
distribution is also migrated to its BEAST3 spec counterpart when constructing test fixtures.

---

## Edge Cases

- **Unknown class under `beast.base.evolution.speciation` or `beast.base.evolution.tree`**: check
  the BEAST3 source before rewriting. If no spec counterpart exists, leave unchanged and flag with
  `// TODO: no beast3 spec class found`.
- **Wildcard imports**: expand to explicit imports for only the classes actually used, then apply
  R1–R3.
- **Partial migration**: if the file already imports any `beast.base.spec.evolution.tree.*` or
  `beast.base.spec.evolution.speciation.*` classes, do not duplicate them.

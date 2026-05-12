---
name: beast3-site-likelihood
description: Migrate BEAST2 site model and tree likelihood imports to BEAST3 spec equivalents
metadata:
  type: skill
---

You are migrating BEAST2 site model and tree likelihood classes to the BEAST3 spec API. Apply all
rules below; make minimal, surgical changes only.

---

## Rules

### R1 — Site model import

| BEAST2 import | BEAST3 import |
|---|---|
| `beast.base.evolution.sitemodel.SiteModel` | `beast.base.spec.evolution.sitemodel.SiteModel` |

**Do NOT rename** — no `.spec.` counterpart yet:
- `beast.base.evolution.sitemodel.SiteModelInterface` — keep as-is

### R2 — Tree likelihood imports

| BEAST2 import | BEAST3 import |
|---|---|
| `beast.base.evolution.likelihood.TreeLikelihood` | `beast.base.spec.evolution.likelihood.TreeLikelihood` |
| `beast.base.evolution.likelihood.GenericTreeLikelihood` | `beast.base.spec.evolution.likelihood.GenericTreeLikelihood` |
| `beast.base.evolution.likelihood.ThreadedTreeLikelihood` | `beast.base.spec.evolution.likelihood.ThreadedTreeLikelihood` |
| `beast.base.evolution.likelihood.BeagleTreeLikelihood` | `beast.base.spec.evolution.likelihood.BeagleTreeLikelihood` |

### R3 — `SiteModel.Base` inner class → verify

Check whether the file uses `SiteModel.Base` or `SiteModelInterface`. If it uses `SiteModel.Base`,
change to `beast.base.spec.evolution.sitemodel.SiteModel` and update the type reference. If it uses
`SiteModelInterface`, leave the import unchanged (no spec twin yet).

### R4 — Site model `Input` concreteness

In classes that extend `Operator`, the site model input may be declared as the concrete
`SiteModel`. In non-Operator classes (Distributions, Loggers, Likelihoods) it should be declared
as `SiteModelInterface` to remain substitutable:

```java
// Non-operator — prefer the interface
final public Input<SiteModelInterface> siteModelInput = new Input<>(...);
```

### R5 — Parameters inside SiteModel / TreeLikelihood `initByName`

When constructing a `SiteModel` in test code:
- The `shape` (gamma shape) is a `RealScalarParam<PositiveReal>`
- The `mutationRate` is a `RealScalarParam<PositiveReal>`
- The `proportionInvariant` is a `RealScalarParam<UnitInterval>`

See `parameters.md` for parameter migration rules.

---

## Edge Cases

- **`FilteredAlignment`**: now at `beast.base.spec.evolution.alignment.FilteredAlignment` — migrate
  the import if present.
- **Wildcard imports** (`import beast.base.evolution.likelihood.*`): expand to explicit imports for
  only the classes actually used, then apply R2.
- **Partial migration**: if the file already imports any `beast.base.spec.evolution.*` classes, do
  not duplicate them.

# remaster — what's left

> **Scanned at:** 2026-05-15T12:13:15.991084  
> **Commit:** `896c7b7` on `beast2.8-migration` — [view on GitHub](https://github.com/tgvaughan/remaster/commit/896c7b7077843f2fae66460e06bbfe14cce0498b)  
> **Pom version:** `3.0.0`  
> **Maven Central:** not published as `io.github.tgvaughan:remaster` (not published (404))  
> **Stage hint:** compile-tested
>
> Migration WIP on `beast2.8-migration` branch (Maven, v3.0.0).

## Summary

- **Java classes:** 0 on spec, 0 mixed, 0 legacy of 66 total
- **Example XMLs:** 4 on spec / 7 on `version="2.8"` / 25 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** ❌ not published (not published (404))

## Build & release gaps

- **No `release.sh` or `release/` directory** — used to assemble the CBAN-style ZIP.

## Maven Central

Not yet published as `io.github.tgvaughan:remaster`. Verify the namespace on central.sonatype.com and run the `release` profile to deploy.

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs pending migration

**Needs `version="2.8"`** (18):

- `examples/CoalescentInferenceTree.xml`
- `examples/SEIR_tree.xml`
- `examples/SimulatedBDAlignment.xml`
- `examples/YuleInferenceInitialisation.xml`
- `examples/CoalescentInferenceAlignment.xml`
- `examples/SIRdet.xml`
- `examples/EpiBD.xml`
- `examples/BDserialAlignment.xml`
- `examples/StructuredCoalescentExp.xml`
- `examples/SIR.xml`
- `examples/HetCoalescent.xml`
- `examples/Coalescent.xml`
- `examples/SEIRdet_tree.xml`
- `examples/BDcontemp.xml`
- `examples/StructuredCoalescent.xml`
- `examples/BDserial.xml`
- `examples/ComplexCoalescent.xml`
- `examples/SEIR_tree_pruning.xml`

**Targets BEAST 3 but body has no `beast.base.spec.*` references** (3):

- `doc/examples/Ctree.xml`
- `doc/examples/Ctraj.xml`
- `doc/examples/ComplexCoalescent.xml`

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`examples/EpiBD.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`examples/BDserialAlignment.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.sitemodel.SiteModel` | `beast.base.spec.evolution.sitemodel.SiteModel` |
| `spec=` | `beast.base.evolution.substitutionmodel.HKY` | `beast.base.spec.evolution.substitutionmodel.HKY` |
| `spec=` | `beast.base.evolution.substitutionmodel.Frequencies` | `beast.base.spec.evolution.substitutionmodel.Frequencies` |

**`examples/BDcontemp.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |


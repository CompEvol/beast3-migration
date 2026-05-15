# feast — what's left

> **Scanned at:** 2026-05-15T12:13:14.728542  
> **Commit:** `224b618` on `beast2.8-migration` — [view on GitHub](https://github.com/tgvaughan/feast/commit/224b618715c41e4ed58f5d4dc2c161dab4000099)  
> **Pom version:** `11.0.0`  
> **Maven Central:** not published as `io.github.tgvaughan:feast` (not published (404))  
> **Stage hint:** compile-tested
>
> Tasty additions to BEAST 2. v11.0.0 is the first beast3-targeted release.

## Summary

- **Java classes:** 22 on spec, 0 mixed, 0 legacy of 88 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 9 total
- **BEAUti fxtemplates:** 0 clean / 0 use spec / 1 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** ❌ not published (not published (404))

## Build & release gaps

- **No `release.sh` or `release/` directory** — used to assemble the CBAN-style ZIP.

## Maven Central

Not yet published as `io.github.tgvaughan:feast`. Verify the namespace on central.sonatype.com and run the `release` profile to deploy.

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs pending migration

**Needs `version="2.8"`** (9):

- `examples/ECdistribArrayTest.xml`
- `examples/piecewiseCoalescent.xml`
- `examples/DiscreteUniformJump.xml`
- `examples/coalescent.xml`
- `examples/LogFileIteratorTest.xml`
- `examples/DensityMapper.xml`
- `examples/TreeLogFileIteratorTest.xml`
- `examples/ECdistribTest.xml`
- `examples/SimulateCoalescentTrees.xml`

## FxTemplates pending migration

> Note: BEAUti templates conventionally keep `version='2.0'` (beast3 core does the same). Migration here means the body uses `beast.base.spec.*` types and parameter declarations use `RealScalarParam` etc. rather than `parameter.RealParameter`.

**No `beast.base.spec.*` references in body** (1):

- `fxtemplates/ExpCalcSubTemplate.xml`

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`examples/DiscreteUniformJump.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`examples/coalescent.xml`** (2):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`examples/LogFileIteratorTest.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`examples/DensityMapper.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `IntegerParameter` → `beast.base.inference.parameter.IntegerParameter` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `spec=` | `BooleanParameter` → `beast.base.inference.parameter.BooleanParameter` | `beast.base.spec.inference.parameter.BoolScalarParam` |

**`examples/SimulateCoalescentTrees.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |


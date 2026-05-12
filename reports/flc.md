# flc — what's left

> **Scanned at:** 2026-05-13T11:04:02.182658  
> **Commit:** `43218a5` on `master` — [view on GitHub](https://github.com/4ment/flc/commit/43218a505931bf1b3541bb34578f5b270a21476f)  
> **Pom version:** `1.3.0-beta1`  
> **Maven Central:** `io.github.4ment:flc:1.3.0-beta1`  
> **Stage hint:** Maven Central

## Summary

- **Java classes:** 8 on spec, 0 mixed, 0 legacy of 13 total
- **Example XMLs:** 2 on spec / 2 on `version="2.8"` / 2 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** 1.3.0-beta1

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs

All example XMLs target `version="2.8"` and use spec types with no legacy parameter declarations. ✅

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`examples/Human.H3.81-98-elc-StrictClock.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |

**`examples/Human.H3.81-98-elc.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.tree.MRCAPrior` | `beast.base.spec.evolution.tree.MRCAPrior` |


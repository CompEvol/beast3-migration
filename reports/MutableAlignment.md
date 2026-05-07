# MutableAlignment — what's left

> **Scanned at:** 2026-05-07T20:26:05.962690  
> **Local checkout:** `/Users/adru001/Git/MutableAlignment` — commit `d8204fa` on `fix-store-restore-for-mutable-tips-beast27` — [view on GitHub](https://github.com/rbouckaert/MutableAlignment/commit/d8204fad14f1fddeff851fabcd5ff3d8f8aa5db8)  
> **Stage hint:** compile-tested
>
> Ant-based (no pom.xml at root).

## Summary

- **Java classes:** 0 on spec, 0 mixed, 0 legacy of 0 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 2 total

## Build & release gaps

- **No `pom.xml`** — the package isn't Maven-buildable. The migration guide assumes Maven; consider adding a pom (start from `beast-package-skeleton`).
- **No `module-info.java`** — JPMS module descriptor is the primary service-discovery mechanism in BEAST 3. Add an `open module` declaration with `provides beast.base.core.BEASTInterface with …`.
- **No `.github/workflows/*.yml`** — add CI to catch regressions on PRs.

## Maven Central

No `groupId:artifactId` recorded in `packages.yaml`. Add one to track publication status.

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs pending migration

**Needs `version="2.8"`** (2):

- `release/add-on/examples/testSimpleMutableAlignment.xml`
- `examples/testSimpleMutableAlignment.xml`


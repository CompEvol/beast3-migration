# MutableAlignment — what's left

> **Scanned at:** 2026-05-13T14:50:58.106578  
> **Commit:** `1101943` on `fix-store-restore-for-mutable-tips-beast27` — [view on GitHub](https://github.com/rbouckaert/MutableAlignment/commit/1101943c8cdd723b7a8457b158a84797f6700d0f)  
> **Stage hint:** compile-tested
>
> Ant-based (no pom.xml at root).

## Summary

- **Java classes:** 0 on spec, 0 mixed, 0 legacy of 0 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 1 total

## Build & release gaps

- **No `pom.xml`** — the package isn't Maven-buildable. The migration guide assumes Maven; consider adding a pom (start from `beast-package-skeleton`).
- **No `module-info.java`** — JPMS module descriptor is the primary service-discovery mechanism in BEAST 3. Add an `open module` declaration with `provides beast.base.core.BEASTInterface with …`.
- **No `.github/workflows/*.yml`** — add CI to catch regressions on PRs.
- **No `release.sh` or `release/` directory** — used to assemble the CBAN-style ZIP.

## Maven Central

No `groupId:artifactId` recorded in `packages.yaml`. Add one to track publication status.

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs pending migration

**Needs `version="2.8"`** (1):

- `examples/testSimpleMutableAlignment.xml`


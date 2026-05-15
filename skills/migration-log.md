---
name: beast3-migration-log
description: Manages tmp/b3migration/ memory folder under the project root — initialises or resumes the migration queue, records per-file completion, and writes summary reports by sub-skill and TODO list
metadata:
  type: skill
---

Maintains `tmp/b3migration/` (at the **project root**, same directory as `pom.xml`) so the
agent can resume migration across sessions. The agent must never delete this folder — the
user deletes it manually after migration is complete.

---

## When to call each mode

| Mode | Called by controller | Action |
|---|---|---|
| **Mode 1 — Session start** | Path B or Step 4 | Check STATUS.md; resume or initialise |
| **Mode 2a — Pre-file** | Step 5, before migrating each file | Mark file `in-progress` in STATUS.md |
| **Mode 2b — Post-file** | Step 5, after `mvn compile -q` resolves | Mark `done`/`error`; append log entry; record TODOs |
| **Mode 3 — Session end** | Step 7, once after all files processed | Rewrite REPORT.md |

---

## Folder structure

All paths are relative to the project root.

```
tmp/b3migration/
├── STATUS.md          ← master queue; read first on every session start
├── TODO.md            ← @Deprecated classes with no spec twin; append-only
├── REPORT.md          ← sub-skill summary; rewritten at session end
└── log/
    └── YYYY-MM-DD.md  ← daily session log; append across sessions on same day
```

---

## Mode 1 — Session start

```
if tmp/b3migration/STATUS.md does NOT exist → Fresh start
    mkdir -p tmp/b3migration/log
    Write STATUS.md (see format below) with the queue built by controller Step 4, all files set to `pending`.
    Write TODO.md with header row only.

if tmp/b3migration/STATUS.md EXISTS → Resume (controller Path B)
    Read STATUS.md.
    if any row is `in-progress` → resume that file first (it was interrupted)
    else if any row is `pending` → resume from the first `pending` row
    else (all rows `done`) → report "Step 3 complete. X files done." and proceed to controller Step 4.
    Report to user: "Resuming. X done, Y pending, Z errors."
    Jump to controller Step 3 (skip Steps 1–2).
```

### STATUS.md format

```markdown
# Migration Status
Updated: YYYY-MM-DD HH:MM

## Summary
- Total:       N
- Done:        N
- In-progress: N
- Pending:     N
- Error:       N

## File queue
| File | Status | Sub-skills applied | Updated |
|---|---|---|---|
| src/main/java/pkg/Foo.java | done        | java-cleanup · parameters  | 2026-05-13 |
| src/main/java/pkg/Bar.java | in-progress | java-cleanup               | 2026-05-13 |
| src/main/java/pkg/Baz.java | pending     |                            |            |
| src/main/java/pkg/Qux.java | error       | parameters · operators     | 2026-05-13 |
```

Valid status values: `pending` · `in-progress` · `done` · `error`

---

## Mode 2a — Pre-file

Before starting work on a file, set its STATUS.md row to `in-progress` and update the
timestamp. This ensures an interrupted session can find its resume point.

---

## Mode 2b — Post-file

After `mvn compile -q` resolves:

**If compile passes:**
1. Set the file's STATUS.md row to `done`; record sub-skills applied and today's date.
2. Append an entry to `tmp/b3migration/log/YYYY-MM-DD.md`:

```markdown
## HH:MM — src/main/java/pkg/Foo.java
- Sub-skills fired: java-cleanup · parameters
- Changes: RealParameter → RealScalarParam<PositiveReal> (3×), finalize() removed
- Warnings: non-deprecated BEAST2 classes migrated: ClassName1, ClassName2 (or "none")
- TODOs: none
- mvn compile: PASS
```

3. For each `// TODO: no beast3 spec class found` comment added to the file, append a row to
   `tmp/b3migration/TODO.md` (never rewrite — only append):

```markdown
| src/main/java/pkg/Foo.java | MyLegacyClass | no beast3 spec class found |
```

**If compile still fails after all fixes are exhausted:**
- Set STATUS.md row to `error`; log the compiler error message.
- Continue to the next file — do not block the queue.

### TODO.md format (header written once in Mode 1)

```markdown
# Migration TODOs
| File | Class | Note |
|---|---|---|
```

---

## Mode 3 — Session end

Rewrite `tmp/b3migration/REPORT.md` in full from STATUS.md and TODO.md:

```markdown
# Migration Report
Last updated: YYYY-MM-DD

## Progress
- Done:   N / N files (N%)
- Errors: N — <file> : <last compiler error>

## By sub-skill
| Sub-skill       | Files touched |
|---|---|
| java-cleanup    | N |
| parameters      | N |
| subst-models    | N |
| clock-models    | N |
| site-likelihood | N |
| tree-coalescent | N |
| distributions   | N |
| operators       | N |

## TODOs — classes with no beast3 spec twin
(full contents of TODO.md)

## mvn test result
Last run: YYYY-MM-DD — PASS / FAIL (N failures: list test names)
```

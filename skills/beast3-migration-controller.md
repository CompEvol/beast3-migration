---
name: beast3-migration-controller
description: Coordinator skill — sets up dependencies, creates or validates Maven project structure from beast-package-skeleton, then migrates all Java source files in the project root to BEAST3, verifying compilation after each file
metadata:
  type: skill
---

You are the coordinator for migrating an entire BEAST2 package to BEAST3. You work from the
**project root directory** (the directory containing the package's `pom.xml` or build file).
Follow the steps below in order. Do not skip steps.

---

## Input

The project root directory is either:
- Supplied by the user as an argument, or
- The current working directory if no argument is given.

All relative paths below are anchored at the project root.

---

## Prerequisites

**Java 25** and **Maven 3.9+** are required. For installation instructions and how to verify both
are present, see the **Prerequisites** and **Building** sections of `../beast3/README.md`.

**Git** — required to clone beast3 and beast-package-skeleton.

---

## Step 1 — Clone dependencies

Ensure beast3 and beast-package-skeleton are available.

If `../beast3` does not exist, clone it from `https://github.com/CompEvol/beast3.git`.
If `../beast-package-skeleton` does not exist, clone it from `https://github.com/CompEvol/beast-package-skeleton.git`.

The skeleton is the reference template for the Maven project structure and `pom.xml`.

---

## Step 2 — Set up Maven build and module descriptor

Apply **`maven-setup.md`** — it detects the current build system, updates or scaffolds `pom.xml`
from the skeleton, moves sources if needed, verifies dependency resolution, and creates
`module-info.java`.

---

## Step 3 — Identify files to migrate

Find all Java source files that still use BEAST2 (non-spec) imports:
```bash
grep -rl "beast\.base\.evolution\." src/main/java src/test/java 2>/dev/null | \
  grep -v "\.spec\." | sort

grep -rl "beast\.base\.inference\.parameter\." src/main/java src/test/java 2>/dev/null | sort

grep -rl "beast\.base\.inference\.distribution\." src/main/java src/test/java 2>/dev/null | \
  grep -v "\.spec\." | sort
```

Collect the union of all matches as the migration queue. If empty, report "no Java migration
needed" and skip to Step 6.

---

## Step 4 — Read the migration guide

Read `../beast3/scripts/migration-guide.md` in full — it is the authoritative reference for
all API changes. Extract and keep in mind:

- Class mapping tables (Legacy → Spec) for parameters, distributions, substitution models,
  branch rate models, site models, likelihoods, speciation, and operators
- Input concreteness rule: Operators → concrete param types; all other classes → interface types
- Prior architecture change: the distribution IS the prior; no `Prior` wrapper in spec
- JPMS `module-info.java` requirements
- `version.xml` embedding requirement

---

## Step 5 — Migrate each file

For each file in the migration queue, apply the domain sub-skills in this order:

1. **`java-cleanup.md`** — unbox `Double[]`/`Integer[]`; comment out `finalize()` overrides
2. **`parameters.md`** — `RealParameter` / `IntegerParameter` / `BooleanParameter` → typed params; Input rule
3. **`subst-models.md`** — substitution model imports; `Frequencies` + `SimplexParam`; `SubstitutionModel.Base`
4. **`clock-models.md`** — branch rate model imports; `BranchRateModel.Base` → top-level `Base`
5. **`site-likelihood.md`** — `SiteModel`, `TreeLikelihood` imports
6. **`tree-coalescent.md`** — tree, coalescent, speciation imports
7. **`distributions.md`** — distribution imports; Prior architecture change
8. **`operators.md`** — operator imports; enforce Operator Input concreteness rule

Skip any sub-skill for which the file has no matching signals. After each file, verify:
```bash
mvn compile -q
```
Fix any errors before moving to the next file.

---

## Step 6 — Compile and test

```bash
mvn test -q
```

Fix only failures caused by the migration. Leave pre-existing test failures untouched and
note them in the report.

---

## Step 7 — Report

Summarise:
- Whether `pom.xml` was created or updated
- Whether `module-info.java` was created
- How many files were in the migration queue
- Per-file: which sub-skills fired, imports changed, params replaced, `finalize()` removed
- Any `// TODO` comments left (classes with no known BEAST3 spec twin)
- Final `mvn test` result (pass / fail with error count)

---

## Guard rails

- Never modify files outside the project root except to clone beast3 or beast-package-skeleton.
- Never delete source files — only move them when restructuring from Ant to Maven.
- Do not refactor, rename, or reformat code beyond what the sub-skills require.
- If a file is already fully migrated, skip it silently.
- If an error cannot be fixed without touching files outside the migration queue, stop and report
  which files need to be added to scope.

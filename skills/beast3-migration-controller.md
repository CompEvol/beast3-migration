---
name: beast3-migration-controller
description: Coordinator skill — sets up dependencies, creates or validates Maven project structure from beast-package-skeleton, migrates all Java source files to BEAST3, then converts XML resources, verifying compilation after each phase
metadata:
  type: skill
---

You are the coordinator for migrating an entire BEAST2 package to BEAST3. Work from the
**project root directory** (containing `pom.xml` or the build file). All relative paths are
anchored there. Follow the steps below in order.

---

## Before you begin

**First:** check whether `tmp/b3migration/STATUS.md` exists — this determines what has
already been completed in a previous session and informs all decisions below.

### Path B — Resume previous session

If `tmp/b3migration/STATUS.md` **exists** and no specific step was requested: apply
**`migration-log.md` Mode 1** — find the `in-progress` or first `pending` file and jump
directly to Step 3. If Mode 1 finds no pending or in-progress files (all rows `done`),
report completion to the user and proceed to Step 4.

### Path A — User requests a specific step

Use the dependency table below to assess whether the requested step can proceed.
If `tmp/b3migration/STATUS.md` exists, treat Steps 1–3 as already satisfied.
For any dependency that is genuinely not yet met, tell the user which steps must run first
and stop — do not proceed until the user confirms or asks you to run the missing steps.

| Requested step | Depends on | How to verify dependency is satisfied |
|---|---|---|
| Step 1 | — | — |
| Step 2 | Step 1 | `../beast3/` and `../beast-package-skeleton/` both exist |
| Step 3 | Step 2 | `mvn compile -q` passes |
| Step 4 | Step 3 | No independent gate — applied when classifying XML file paths ahead of Steps 5–6 |
| Step 5 | Step 4 | STATUS.md all rows `done` (no errors), or `mvn clean compile` passes |
| Step 6 | Step 4 | STATUS.md all rows `done` (no errors), or `mvn clean compile` passes |
| Step 7 | Step 5, Step 6 | Steps 5 and 6 complete, or skipped if no XMLs pending |
| Step 8 | Step 2 | `../beast3/.github/workflows/ci-publish.yml` exists |

### Path C — Fresh start

If `tmp/b3migration/STATUS.md` **does not exist** and no specific step was requested:
proceed to Step 1.

---

## Universal rules (apply to every file and every sub-skill)

All sub-skills inherit these rules. They are not repeated inside each sub-skill file.

### U1 — Move files with `git mv`

When restructuring sources (e.g. Ant → Maven layout), always use `git mv` instead of a plain
filesystem move so that git history is preserved on the destination file.

```bash
git mv src/MyClass.java src/main/java/com/example/MyClass.java
```

### U2 — Core import transformation

The pattern for every class that has a BEAST3 spec twin is:

```
beast.base.<domain>.<Class>
         ↓
beast.base.spec.<domain>.<Class>
```

Only the path changes — the class name stays the same (except for inner-class promotions noted
in individual sub-skills, e.g. `SubstitutionModel.Base` → top-level `Base`).

### U3 — Input concreteness rule

Overrides any legacy `Input<>` declaration in every class type:

| Class type | `Input<>` generic | Example |
|---|---|---|
| **Operator** (extends `Operator`) | concrete param class | `Input<RealScalarParam<PositiveReal>>` |
| **Everything else** (Distribution, Logger, CalcNode, Likelihood…) | interface type | `Input<RealScalar>` |

### U4 — Import hygiene

Applies to every `beast.base.*` class reference — Java imports and XML `spec`/`type`/`class`
attributes. Apply the **first** matching case:

| # | Condition | Action |
|---|---|---|
| 1 | `beast.base.spec.*` reference already present | Skip — already migrated |
| 2 | Spec twin exists + class **is** `@Deprecated` in BEAST2 | Apply U2 — normal migration |
| 3 | Spec twin exists + class is **not** `@Deprecated` in BEAST2 | Apply U2 + record in Mode 2b log: `Warning — non-deprecated BEAST2 class migrated: <ClassName>` |
| 4 | No spec twin + class **is** `@Deprecated` | Leave unchanged; add `// TODO: no beast3 spec class found for <ClassName>` |
| 5 | No spec twin + class is **not** `@Deprecated` (e.g. `Tree`, `Node`, `TreeInterface`) | Leave unchanged; no comment |

**"Spec twin exists"**: class appears in the active sub-skill's import table or in
`../beast3/scripts/migration-guide.md`.

**Checking `@Deprecated`**: look for `@Deprecated` on the class declaration in
`../beast-base/src/main/java/`. If unavailable locally, consult `../beast3/scripts/migration-guide.md`.

**Wildcard imports** (`.*`): expand to only the classes actually used, then apply the table above.

### U5 — Minimal, surgical changes only

Do not refactor, rename, reformat, or restructure code beyond what the active rules require.
One rule, one change. Leave everything else exactly as found.

### U6 — Suggest skill improvements

During migration, if you encounter a pattern, class mapping, edge case, or conversion rule
that is missing or incorrect in a skill file, suggest updating the relevant skill to the
user before continuing. Do not silently apply a workaround — surface the gap so the skill
stays accurate for future migrations.

---

## Domain sub-skills — quick reference

Each sub-skill fires only when its **signal** appears in the file being migrated (Step 3).
Consult `../beast3/scripts/migration-guide.md` on demand for any class not covered here.

| # | Sub-skill | Signal (grep for this in the file) | Key transformation | Mode 2b log (Changes field) |
|---|---|---|---|---|
| 1 | `java-migration/java-cleanup.md` | `void finalize()` · `Double[` · `Integer[` | Comment out `finalize`; unbox to primitive arrays | `finalize() removed: N` · `Double[]→double[]: N` · `Integer[]→int[]: N` |
| 2 | `java-migration/parameters.md` | `import beast.base.inference.parameter.` · `Function` as Input type | `RealParameter`/`IntegerParameter`/`BooleanParameter` → typed params + domain; enforce Input concreteness rule | param replacements with type and count · `Input declarations updated: N` |
| 3 | `java-migration/subst-models.md` | `import beast.base.evolution.substitutionmodel.` | → `.spec.` equivalents; `Frequencies.frequencies` arg → `SimplexParam`; `SubstitutionModel.Base` → top-level `Base` | classes renamed (list) · `SubstitutionModel.Base→Base: y/n` · `Frequencies.frequencies→SimplexParam: y/n` |
| 4 | `java-migration/clock-models.md` | `import beast.base.evolution.branchratemodel.` | → `.spec.` equivalents; `BranchRateModel.Base` → top-level `Base` | classes renamed (list) · `BranchRateModel.Base→Base: y/n` |
| 5 | `java-migration/site-likelihood.md` | `import beast.base.evolution.sitemodel.` · `import beast.base.evolution.likelihood.` | → `.spec.` equivalents | classes renamed (list) · `SiteModel.Base→SiteModel: y/n` |
| 6 | `java-migration/tree-coalescent.md` | `import beast.base.evolution.tree.` · `import beast.base.evolution.speciation.` | → `.spec.` equivalents (Tree/Node/TreeParser/TreeInterface are NOT renamed) | classes renamed by category: `tree(N)` · `coalescent(N)` · `speciation(N)` |
| 7 | `java-migration/distributions.md` | `import beast.base.inference.distribution.` | → `.spec.` equivalents; `Prior` is **removed** in BEAST3 — replace `Prior` wrapper with the inner distribution directly | classes renamed (list) · `Prior wrapper removed: y/n` |
| 8 | `java-migration/operators.md` | `import beast.base.inference.operator.` · `import beast.base.evolution.operator.` | → `.spec.` equivalents; `ScaleOperator` split: `parameter=` → spec inference, `tree=` → `ScaleTreeOperator`; `Exchange`/`WilsonBalding` unchanged; `SubtreeSlide` → `BactrianSubtreeSlide`; Operators use **concrete** Input types | classes renamed by group · `Input declarations made concrete: N` |
| 9 | `java-migration/commons-math.md` | `import org.apache.commons.math.distribution.` · `import org.apache.commons.math.MathException` | `XxxDistribution`/`XxxDistributionImpl` → `XxxDistribution.of(...)`; drop checked `MathException`; add `requires org.apache.commons.statistics.distribution` to module-info.java | classes migrated (list) · `MathException removed: y/n` |

---

## Step 1 — Clone dependencies

```bash
# Only if the directory is missing:
git clone https://github.com/CompEvol/beast3.git ../beast3
git clone https://github.com/CompEvol/beast-package-skeleton.git ../beast-package-skeleton
```

The skeleton is the reference template for `pom.xml` and project structure.
Requires **Java 25**, **Maven 3.9+**, and **Git** — see `../beast3/README.md` for setup.

---

## Step 2 — Set up Maven build

Apply **`maven-setup.md`** to produce a working single-module BEAST3 Maven build. This
includes moving **all** Java sources and XML resources (FxTemplate XMLs, example analysis
XMLs, FXML, data files) into the Maven directory layout before any migration work begins.

### 2b — Verify the build compiles cleanly

```bash
mvn compile -q
```

Fix any errors now. A failing baseline will mask per-file errors in Step 3.

---

## Step 3 — Identify and migrate Java files

### 3a — Build the migration queue

```bash
grep -rl "beast\.base\.evolution\."              src/main/java src/test/java 2>/dev/null | grep -v "\.spec\." | sort
grep -rl "beast\.base\.inference\.parameter\."   src/main/java src/test/java 2>/dev/null | grep -v "\.spec\." | sort
grep -rl "beast\.base\.inference\.distribution\." src/main/java src/test/java 2>/dev/null | grep -v "\.spec\." | sort
grep -rl "beast\.base\.inference\.operator\."    src/main/java src/test/java 2>/dev/null | grep -v "\.spec\." | sort
grep -rl "org\.apache\.commons\.math\."          src/main/java src/test/java 2>/dev/null | sort
```

The union of all matches is the **migration queue**. The last line catches files whose *only*
non-BEAST3-compatible reference is a legacy `org.apache.commons.math.*` import (sub-skill 9) —
these files commonly have no `beast.base.*` reference at all (e.g. a class that only wraps a
`BetaDistribution` for path sampling), so they would otherwise never enter the queue.

**If the queue is empty — skip directly to Step 4.**

Apply **`migration-log.md` Mode 1 (fresh start)** to initialise `tmp/b3migration/STATUS.md`
with every file set to `pending`.

### 3b — Migrate each file

Universal rules **U1–U5 apply to every file** — no signal check needed for them.

If a file contains a class not covered by the sub-skill table, look it up on demand in
`../beast3/scripts/migration-guide.md` before applying changes.

For each file in the migration queue:

1. Apply **`migration-log.md` Mode 2a**: mark the file `in-progress` in STATUS.md.
2. Check which sub-skill signals are present (see the table above).
3. Apply each matching sub-skill in order 1–9. Skip sub-skills whose signal is absent.
4. Verify compilation:

```bash
mvn compile -q
```

5. Apply **`migration-log.md` Mode 2b**: mark `done` or `error`, append to the daily log,
   and record any TODOs in `tmp/b3migration/TODO.md`.

Fix all compile errors before moving to the next file. If a file cannot be fixed, mark it
`error` and continue with the next.

---

## Step 4 — XML classification rule

Apply this rule whenever deciding whether an XML file is a BEAUti template or an example
analysis — in grep output, in a `convert_b2_to_b3.py` per-file report, or anywhere a file path
appears:

| Condition | Classification | Handled by |
|---|---|---|
| Path contains `fxtemplates` **or** module name ends in `-fx` | **BEAUti template** — GUI only, not runnable by BEAST main | Step 5 — script with `--fxtemplate` |
| Neither condition matches | **Example analysis** — runnable by BEAST main | Step 6 — script without `--fxtemplate` |

Steps 5 and 6 already scope their `find` commands to `src/main/resources/` (FxTemplates) and
`src/test/resources/` (example XMLs) respectively, so classification is usually implicit in
which step you're running. Apply the rule explicitly when a path appears out of that context —
e.g. a mixed grep result spanning both directories, or a file whose location doesn't match its
module's convention.

---

## Step 5 — Convert FxTemplates

**Compile prerequisite** — skip the compile check if `tmp/b3migration/STATUS.md` exists and
all rows are `done` with no `error` rows (Step 3's per-file `mvn compile -q` gate already
guarantees a clean build at that point). If any rows are `error`, fix those Java files and
confirm `mvn clean compile` passes before continuing. Otherwise run:

```bash
mvn clean compile
```

Run the converter on all BEAUti template XMLs and FXML files under `src/main/resources/`:

```bash
find src/main/resources -name "*.xml" -o -name "*.fxml" | sort | \
    xargs python3 skills/xml-migration/convert_b2_to_b3.py \
    --fxtemplate --report >> tmp/b3migration/log/$(date +%Y-%m-%d).md
```

Review the appended report for any **TODOs** (classes with no spec twin) — these need manual follow-up. See `xml-migration/XML-MIGRATION-STRATEGY.md` for the full rule reference.

Verify no unmigrated `beast.base.` references remain:
```bash
grep -rn "beast\.base\." src/main/resources/ --include="*.xml" --include="*.fxml" | grep -v "\.spec\."
```

Steps 5 and 6 may be run individually but both require the compile prerequisite above.

---

## Step 6 — Convert example XMLs

**Compile prerequisite** — skip the compile check if `tmp/b3migration/STATUS.md` exists and
all rows are `done` with no `error` rows. If any rows are `error`, fix those Java files and
confirm `mvn clean compile` passes before continuing. Otherwise run:

```bash
mvn clean compile
```

Run the converter on all example analysis XMLs under `src/test/resources/`:

```bash
find src/test/resources -name "*.xml" | sort | \
    xargs python3 skills/xml-migration/convert_b2_to_b3.py \
    --report >> tmp/b3migration/log/$(date +%Y-%m-%d).md
```

Review the appended report for any **TODOs** — these need manual follow-up. See `xml-migration/XML-MIGRATION-STRATEGY.md` for the full rule reference.

Verify no unmigrated `beast.base.` references remain:
```bash
grep -rn "beast\.base\." src/test/resources/ --include="*.xml" | grep -v "\.spec\."
```

Validate each converted XML with BEAST3:
```bash
BEAST_ROOT_DIR=~/WorkSpace/beast3
for xml in $(find src/test/resources -name "*_b3.xml" | sort); do
    $BEAST_ROOT_DIR/bin/beast -validate "$xml" \
        > /tmp/beast3_validate_output.txt 2>&1
    python3 skills/xml-migration/check_beast_run.py /tmp/beast3_validate_output.txt \
        && echo "OK: $xml" || echo "FAIL: $xml"
done
```

Pass criterion: every file prints `PASS: Done!`. Fix any failures before continuing.

Steps 5 and 6 may be run individually but both require the compile prerequisite above.

---

## Step 7 — Report

Run the full test suite as a final end-to-end validation before generating the report:

```bash
mvn test -q
```

Fix only failures introduced by the migration. Note pre-existing failures without touching them.

Apply **`migration-log.md` Mode 3** to rewrite `tmp/b3migration/REPORT.md` with the final
summary. Then print a brief summary to the user:

| Item | Detail |
|---|---|
| `pom.xml` | Created or updated |
| `version.xml` | Updated or no changes |
| `module-info.java` | Created |
| Java migration queue | Total · done · error · pending |
| By sub-skill | How many Java files each sub-skill touched |
| FxTemplates (`src/main/resources/`) | Files migrated · class references updated · complex conversions · TODOs inserted |
| Example XMLs (`src/test/resources/`) | Files migrated · `version="2.8"` applied · class references updated · complex conversions · TODOs inserted |
| TODOs | Contents of `tmp/b3migration/TODO.md` |
| `mvn test` result | Pass / fail with error count |
| GitHub workflow | `copied and adapted (branch: <branch>)` or `copied (branch: master, no changes)` |

---

## Step 8 — Copy GitHub Actions workflow

**Detect the project's default branch:**

```bash
git rev-parse --abbrev-ref HEAD
```

**Create the workflow directory if missing and copy the workflow:**

```bash
mkdir -p .github/workflows
cp ../beast3/.github/workflows/ci-publish.yml .github/workflows/ci-publish.yml
```

**Adapt branch references** — if the detected branch is not `master`, replace every occurrence:

| Original | Replacement |
|---|---|
| `branches: [ master ]` | `branches: [ <branch> ]` |
| `refs/heads/master` | `refs/heads/<branch>` |

If the branch is already `master`, no substitution is needed.

---

## Guard rails

- Never modify files outside the project root (cloning deps is the only exception).
- Never delete `tmp/b3migration/` — the user must delete it manually after migration is complete.
- Never delete source files — only move them when restructuring Ant → Maven (use `git mv`, see U1).
- Skip files that are already fully migrated (no BEAST2 non-spec imports remaining).
- Stop and report if fixing an error requires touching files outside the migration queue.

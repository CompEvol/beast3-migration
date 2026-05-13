---
name: beast3-migration-controller
description: Coordinator skill — sets up dependencies, creates or validates Maven project structure from beast-package-skeleton, then migrates all Java source files in the project root to BEAST3, verifying compilation after each file
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
**`migration-log.md` Mode 1** — find the `in-progress` or first `pending` file, report
progress to the user, and jump directly to Step 5.

### Path A — User requests a specific step

Use the dependency table below to assess whether the requested step can proceed.
If `tmp/b3migration/STATUS.md` exists, treat Steps 1–5 as already satisfied.
For any dependency that is genuinely not yet met, tell the user which steps must run first
and stop — do not proceed until the user confirms or asks you to run the missing steps.

| Requested step | Depends on | How to verify dependency is satisfied |
|---|---|---|
| Step 1 | — | — |
| Step 2 | Step 1 | `../beast3/` and `../beast-package-skeleton/` both exist |
| Step 3 | Step 2 | `tmp/b3migration/STATUS.md` contains `module-layout: single` or `module-layout: multi` |
| Step 4 | Step 2 | same as Step 3 |
| Step 5 | Step 2, Step 4 | Step 2 verified above; `tmp/b3migration/STATUS.md` exists with a file queue |
| Step 6 | Step 5 | all rows in STATUS.md are `done` or `error` (none `pending` or `in-progress`) |
| Step 7 | Step 5 | same as Step 6 |
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

---

## Domain sub-skills — quick reference

Each sub-skill fires only when its **signal** appears in the file being migrated (Step 5).
Consult `../beast3/scripts/migration-guide.md` on demand for any class not covered here.

| # | Sub-skill | Signal (grep for this in the file) | Key transformation | Mode 2b log (Changes field) |
|---|---|---|---|---|
| 1 | `java-cleanup.md` | `void finalize()` · `Double[` · `Integer[` | Comment out `finalize`; unbox to primitive arrays | `finalize() removed: N` · `Double[]→double[]: N` · `Integer[]→int[]: N` |
| 2 | `parameters.md` | `import beast.base.inference.parameter.` · `Function` as Input type | `RealParameter`/`IntegerParameter`/`BooleanParameter` → typed params + domain; enforce Input concreteness rule | param replacements with type and count · `Input declarations updated: N` |
| 3 | `subst-models.md` | `import beast.base.evolution.substitutionmodel.` | → `.spec.` equivalents; `Frequencies.frequencies` arg → `SimplexParam`; `SubstitutionModel.Base` → top-level `Base` | classes renamed (list) · `SubstitutionModel.Base→Base: y/n` · `Frequencies.frequencies→SimplexParam: y/n` |
| 4 | `clock-models.md` | `import beast.base.evolution.branchratemodel.` | → `.spec.` equivalents; `BranchRateModel.Base` → top-level `Base` | classes renamed (list) · `BranchRateModel.Base→Base: y/n` |
| 5 | `site-likelihood.md` | `import beast.base.evolution.sitemodel.` · `import beast.base.evolution.likelihood.` | → `.spec.` equivalents | classes renamed (list) · `SiteModel.Base→SiteModel: y/n` |
| 6 | `tree-coalescent.md` | `import beast.base.evolution.tree.` · `import beast.base.evolution.speciation.` | → `.spec.` equivalents (Tree/Node/TreeParser/TreeInterface are NOT renamed) | classes renamed by category: `tree(N)` · `coalescent(N)` · `speciation(N)` |
| 7 | `distributions.md` | `import beast.base.inference.distribution.` | → `.spec.` equivalents; `Prior` still exists but distribution can be used directly as prior | classes renamed (list) · `Prior wrapper restructured: y/n` |
| 8 | `operators.md` | `import beast.base.inference.operator.` · `import beast.base.evolution.operator.` | → `.spec.` equivalents; Operators use **concrete** Input types | classes renamed by group · `Input declarations made concrete: N` |

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

## Step 2 — Detect module structure and set up Maven build

### 2a — Detect single vs multi-module layout

Scan all Java sources for JavaFX imports:

```bash
grep -rl "import javafx\." src/ 2>/dev/null | grep "\.java$" | sort
```

| Result | Decision | Action |
|---|---|---|
| One or more `.java` files import `javafx.*` | **Multi-module** | Apply **`maven-setup-multimodule.md`** |
| No JavaFX imports found | **Single-module** | Apply **`maven-setup.md`** |

Record the decision in `tmp/b3migration/STATUS.md` (create the file now if Step 4 has not done so yet):

```
module-layout: single   # or: multi
```

This key is read by Steps 3, 4, and 5 to resolve the correct source and resource directories.

**Multi-module directory conventions** (referenced in later steps):

| Placeholder | Single-module path | Multi-module path |
|---|---|---|
| `{BASE_SRC}` | `src/main/java` | `<artifactId>-base/src/main/java` |
| `{BASE_TEST}` | `src/test/java` | `<artifactId>-base/src/test/java` |
| `{FX_SRC}` | _(n/a)_ | `<artifactId>-fx/src/main/java` |
| `{BASE_RES}` | `src/main/resources` | `<artifactId>-base/src/main/resources` |
| `{FX_RES}` | `src/main/resources` | `<artifactId>-fx/src/main/resources` |
| `{BASE_TEST_RES}` | `src/test/resources` | `<artifactId>-base/src/test/resources` |

### 2b — Verify the build compiles cleanly

```bash
mvn compile -q   # builds all modules for multi-module projects
```

Fix any errors now. A failing baseline will mask per-file errors in Step 5.

---

## Step 3 — Migrate XML resources

Applies to both main and test resources. No `mvn compile` gate — each skill has its own
verify grep. Use the directory placeholders established in Step 2a. Run both sub-skills in order:

1. **`fxtemplates.md`** — `*.xml` / `*.fxml` under **`{FX_RES}/`**: rewrites `spec`,
   `type`, `class`, and `fx:controller` attribute values to `.spec.` equivalents.
2. **`example-xmls.md`** — `*.xml` under **`{BASE_TEST_RES}/`**: rewrites `spec` (and
   occasionally `type`, `class`) attribute values in BEAST analysis XMLs used by tests.

---

## Step 4 — Identify Java files to migrate

Use the directory placeholders from Step 2a. For a **single-module** project:

```bash
grep -rl "beast\.base\.evolution\."            src/main/java src/test/java 2>/dev/null | grep -v "\.spec\." | sort
grep -rl "beast\.base\.inference\.parameter\."  src/main/java src/test/java 2>/dev/null | grep -v "\.spec\." | sort
grep -rl "beast\.base\.inference\.distribution\." src/main/java src/test/java 2>/dev/null | grep -v "\.spec\." | sort
grep -rl "beast\.base\.inference\.operator\."   src/main/java src/test/java 2>/dev/null | grep -v "\.spec\." | sort
```

For a **multi-module** project, expand across all child-module source trees:

```bash
grep -rl "beast\.base\.evolution\."            {BASE_SRC} {BASE_TEST} {FX_SRC} 2>/dev/null | grep -v "\.spec\." | sort
grep -rl "beast\.base\.inference\.parameter\."  {BASE_SRC} {BASE_TEST} {FX_SRC} 2>/dev/null | grep -v "\.spec\." | sort
grep -rl "beast\.base\.inference\.distribution\." {BASE_SRC} {BASE_TEST} {FX_SRC} 2>/dev/null | grep -v "\.spec\." | sort
grep -rl "beast\.base\.inference\.operator\."   {BASE_SRC} {BASE_TEST} {FX_SRC} 2>/dev/null | grep -v "\.spec\." | sort
```

The union of all matches is the **migration queue**.

**If the queue is empty — skip directly to Step 6.**

Apply **`migration-log.md` Mode 1 (fresh start)** to initialise `tmp/b3migration/STATUS.md`
with every file set to `pending`.

---

## Step 5 — Migrate each Java file

Universal rules **U1–U5 apply to every file** — no signal check needed for them.

If a file contains a class not covered by the sub-skill table, look it up on demand in
`../beast3/scripts/migration-guide.md` before applying changes.

For each file in the migration queue:

1. Apply **`migration-log.md` Mode 2a**: mark the file `in-progress` in STATUS.md.
2. Check which sub-skill signals are present (see the table above).
3. Apply each matching sub-skill in order 1–8. Skip sub-skills whose signal is absent.
4. Verify compilation:

```bash
mvn compile -q
```

5. Apply **`migration-log.md` Mode 2b**: mark `done` or `error`, append to the daily log,
   and record any TODOs in `tmp/b3migration/TODO.md`.

Fix all compile errors before moving to the next file. If a file cannot be fixed, mark it
`error` and continue with the next.

---

## Step 6 — Compile and test

```bash
mvn test -q
```

Fix only failures introduced by the migration. Note pre-existing failures without touching them.

---

## Step 7 — Report

Apply **`migration-log.md` Mode 3** to rewrite `tmp/b3migration/REPORT.md` with the final
summary. Then print a brief summary to the user:

| Item | Detail |
|---|---|
| `pom.xml` | Created or updated |
| `module-info.java` | Created |
| FxTemplates (`src/main/resources/`) | Files migrated · class references updated · TODOs inserted |
| Example XMLs (`src/test/resources/`) | Files migrated · class references updated · TODOs inserted |
| Java migration queue | Total · done · error · pending |
| By sub-skill | How many Java files each sub-skill touched |
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

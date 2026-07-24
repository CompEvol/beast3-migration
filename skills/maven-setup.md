---
name: beast3-maven-setup
description: Assess the current build system of a BEAST2 package and produce a working BEAST3 Maven build — updates or scaffolds pom.xml from beast-package-skeleton, moves sources if needed, and creates module-info.java
metadata:
  type: skill
---

Given a BEAST2 package project root, produce a working BEAST3 Maven build. Prerequisites: `../beast-package-skeleton` and `../beast3` must exist (controller Step 1).

**Reference examples** — when a real-world model is needed (pom.xml shape, module-info.java, directory layout, versioning conventions), prefer an actual migrated downstream package over the skeleton or beast3 itself: `../BEASTLabs`, `../model-selection`, `../morph-models`, `../sampled-ancestors` are the recommended examples, in no particular order — pick whichever already has the file/pattern you need. `../beast3` (`beast-base`/`beast-fx`) can be consulted too, but it's the core framework, not a downstream package, so its own `pom.xml`/module setup carries extra concerns (multi-module reactor, publishing both core artifacts, JavaFX split) that don't generalise — treat it as a secondary source, not the template to copy first.

---

## Determine project identity

Infer from existing `pom.xml`, `version.xml`, or source files. Ask before writing any files if any field is unclear.

| Field | Example |
|---|---|
| `groupId` | `io.github.compevol` |
| `artifactId` | `mypackage` |
| `version` | see **Choosing the migrated version** below |
| `beast.pkg.name` | display name from `version.xml` |
| GitHub org/repo | used in SCM and distribution URLs |

`artifactId` should keep the project's existing/original name (from its current `pom.xml` or repo name) — do **not** add a `beast-` prefix by default. Most real BEAST3 packages have no such prefix (`codonsubstmodels`, `sampled-ancestors`, `mascot`, `morph-models`, ...); a few chose one themselves (`beast-labs`, `beast-classic`) but that's their own naming choice, not a convention to apply elsewhere. This also feeds the module name in `module-info.md` (artifactId, hyphens → dots), so an invented prefix here would leak into the JPMS module name too.

### Choosing the migrated version

For a package that already has a `version.xml` (i.e. this is a migration of an existing BEAST2 package, not a brand-new package), bump the **minor** version from the current `version.xml` value rather than keeping it unchanged or resetting to the skeleton's placeholder `1.0.0-SNAPSHOT` — the migration is a real change to the package (new build system, possibly new BEAST3 API surface) and deserves its own release number. E.g. current `1.0.2` → migrated `1.1.0`; current `2.3.1` → migrated `2.4.0`. Use this bumped value for both `pom.xml`'s `<version>` and `version.xml`'s `<package version>` (see **Update `version.xml`** below). Only fall back to the skeleton's `1.0.0-SNAPSHOT` when scaffolding a genuinely new package with no prior `version.xml`.

---

## Detect build system

```bash
ls pom.xml build.xml *.xml 2>/dev/null
```

| Condition | Action |
|---|---|
| `pom.xml` with `<maven.compiler.release>25</maven.compiler.release>` | Skip to **Generate module-info.java** |
| `pom.xml` targeting Java 11/17 | **Update existing pom.xml** |
| `build.xml` (Ant) or nothing | **Scaffold full Maven layout** |

---

## Update existing `pom.xml`

1. Set `<maven.compiler.release>25</maven.compiler.release>` and compiler plugin `<release>25</release>`.
2. Replace BEAST2 deps with BEAST3 deps (`<scope>provided</scope>`); see `../beast3/README.md` → **Add BEAST dependencies**.
3. Copy surefire, resources, and assembly plugin configs from `../beast-package-skeleton/pom.xml`.

Then skip to **Verify Maven dependency resolution**.

---

## Scaffold full Maven layout

Infer or ask the user for: `groupId`, `artifactId`, `version`, `beast.pkg.name`, GitHub org/repo.

**Create directories** (only if missing):
```bash
mkdir -p src/main/java src/main/resources src/test/java src/assembly
mkdir -p src/test/resources/<groupId.with.dots>/examples
```

**Move Java sources** from legacy root (`src/` or `java/`) to `src/main/java/` and `src/test/java/`, preserving package subdirectory paths. Use `git mv` (see controller **U1**) so git history is preserved on the destination file — applies to this move and the non-Java move below.

**Move non-Java files:**

| File type | Destination | Path |
|---|---|---|
| BEAUti FxTemplate XMLs | `src/main/resources/` | `<module-name-with-dots>/fxtemplates/` |
| FXML, icons, CSS | `src/main/resources/` | mirrors calling class's package path |
| Grammar files (ANTLR `.g4`) | `src/main/resources/` | mirrors generated parser's package |
| Any `getClass().getResource(...)` file (main code) | `src/main/resources/` | mirrors calling class's package path |
| Example BEAST XMLs | `src/test/resources/` | `<top-level main package>/examples/` |
| Data files (NEXUS, FASTA, JSON, trees, logs) | `src/test/resources/` | `<top-level main package>/examples/<type>/` |
| Test scripts (R, shell) | `src/test/resources/` | `<top-level main package>/examples/` |

`<module-name-with-dots>` is the JPMS module name (dotted `artifactId`, matching `module-info.md` — e.g. `model.selection`, `beast.labs`, `morph.models`, `sampled.ancestors`), **not** the groupId. Verified against all four reference examples' `src/main/resources/` — every one uses its dotted module name as the top-level folder for fxtemplates.

`<top-level main package>` (for `src/test/resources/`) has no single fixed convention across real packages — `model-selection` uses the plain undotted package name (`modelselection`), `sampled-ancestors` uses a short code (`sa`), `morph-models` uses no prefix at all (`examples/` directly). Default to the plain package name (matches `src/main/java/<package>`) unless the project already has its own test-resources convention to preserve.

If unsure, check which class loads the file: test-only → `src/test/resources/`; main code → `src/main/resources/`.

**Copy and customise skeleton files:**

| Source | Destination | Customise |
|---|---|---|
| `../beast-package-skeleton/pom.xml` | `pom.xml` | groupId, artifactId, version (see **Choosing the migrated version** above), pkg name, GitHub URLs — also bump `central-publishing-maven-plugin` to `>= 0.11.0` if the skeleton still has `0.6.0` (see `beast3-release-packaging.md` → CompEvol/beast3#117). Cross-check the customised result against a reference example's `pom.xml` (`../BEASTLabs`, `../model-selection`, `../morph-models`, `../sampled-ancestors`) rather than the skeleton alone — the skeleton is a minimal template, a real package shows what a finished one actually looks like. |
| `../beast-package-skeleton/src/assembly/beast-package.xml` | `src/assembly/beast-package.xml` | none |
| `../beast-package-skeleton/version.xml` | `version.xml` | version number (bumped, see **Choosing the migrated version** above) only; log other suggestions to `tmp/b3migration/TODO.md` |

---

## Verify Maven dependency resolution

```bash
mvn dependency:resolve -q
```

If BEAST3 artifacts are unresolved, install them locally — see `../beast3/README.md` → **Alternative: local install or SNAPSHOT builds**.

---

## Update `version.xml`

Only version numbers may change:
1. `version` attribute on `<package>` — match `pom.xml` version (the bumped minor version — see **Choosing the migrated version** above, not the pre-migration value).
2. `atleast` attributes on `<depends>` — match BEAST3 dep versions in `pom.xml`.

Do not restructure, reorder, add, or remove any elements. Log any structural suggestions to `tmp/b3migration/TODO.md`:

```markdown
| version.xml | <classname or element> | <suggestion> |
```

---

## Generate `src/main/java/module-info.java`

Apply **`module-info.md`**.

---

## Guard rails

- Never delete source files — only move them.
- Only create directories/files that don't already exist.
- Do not modify files outside the project root.
- Ask before writing if `groupId`, `artifactId`, or other identity fields cannot be inferred.

---

## Log (controller Step 7 report)

- `pom.xml`: `created` or `updated (Java N → 25)`
- `Sources moved`: `yes (Ant → Maven)` or `no`
- `mvn dependency:resolve`: `PASS` or `FAIL — <reason>`
- `version.xml`: `updated` or `no changes`
- `module-info.java`: set by `module-info.md`

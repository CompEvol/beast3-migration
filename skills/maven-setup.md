---
name: beast3-maven-setup
description: Assess the current build system of a BEAST2 package and produce a working BEAST3 Maven build — updates or scaffolds pom.xml from beast-package-skeleton, moves sources if needed, and creates module-info.java
metadata:
  type: skill
---

Given a BEAST2 package project root, produce a working BEAST3 Maven build. All relative paths are
anchored at the project root. The skeleton at `../beast-package-skeleton` and beast3 at
`../beast3` must already be available (run the controller's Step 1 first).

---

## 1 — Detect the current build system

```bash
ls pom.xml build.xml *.xml 2>/dev/null
```

Identify which path applies:

| Condition | Action |
|---|---|
| `pom.xml` with `<maven.compiler.release>25</maven.compiler.release>` | Already on BEAST3 Maven — skip to step 5 |
| `pom.xml` targeting Java 11/17 | Update existing `pom.xml` — go to step 2 |
| `build.xml` (Ant) | Full Maven scaffold — go to step 3 |
| Neither | Full Maven scaffold — go to step 3 |

---

## 2 — Update existing `pom.xml` to BEAST3

Read the project's existing `pom.xml`. Apply these changes:

1. **Java version**: set `<maven.compiler.release>25</maven.compiler.release>` and compiler plugin
   `<release>25</release>`.
2. **BEAST3 dependencies**: see **Add BEAST dependencies** in `../beast3/README.md` for the exact
   `<dependency>` blocks. Use `<scope>provided</scope>`.
3. **Surefire, resources, and assembly plugins**: copy the corresponding plugin configurations
   from `../beast-package-skeleton/pom.xml`.

Then go to step 4 (skip step 3).

---

## 3 — Scaffold full Maven directory structure

For projects with no `pom.xml` or an Ant build, create the Maven layout from scratch using the
skeleton as the template.

**Determine the package identity** by asking the user (or inferring from existing source files):
- `groupId`, `artifactId`, `version`, `beast.pkg.name`, GitHub org/repo

**Create directories** (only those that don't already exist):
```bash
mkdir -p src/main/java src/main/resources src/test/java src/assembly
mkdir -p src/test/resources/<groupId.with.dots>/examples
```

**Move Java source files** from the legacy layout to `src/main/java/` and `src/test/java/`
preserving their package subdirectory structure. For Ant projects the source root is typically
`src/` or `java/` — confirm by checking where `.java` files live.

**Classify and move all non-Java files** according to whether they are needed by production code
or only by tests. Use the table below to decide the destination, then use the path conventions
derived from the beast3 source tree (`../beast3`):

| File type | Loaded by | Destination | Path convention |
|---|---|---|---|
| BEAUti FxTemplate XMLs (`*.xml` in a `fxtemplates/` dir) | main GUI classes | `src/main/resources/` | `<groupId.with.dots>/fxtemplates/` |
| FXML layout files | main GUI classes | `src/main/resources/` | mirrors the Java package path with `/` e.g. `<pkg/path>/MyDialog.fxml` |
| Icons, images, CSS used by GUI | main GUI classes | `src/main/resources/` | alongside the FXML that uses them, e.g. `<pkg/path>/icon/` |
| Grammar files (ANTLR `.g4`) | main Java code | `src/main/resources/` | mirrors the package of the generated parser |
| Any file loaded via `getClass().getResource(...)` from main code | main Java code | `src/main/resources/` | mirrors the calling class's package path |
| Example BEAST XML analyses | test classes only | `src/test/resources/` | `<groupId.with.dots>/examples/` |
| Data files (NEXUS, FASTA, JSON, trees, logs) | test classes or example XMLs | `src/test/resources/` | `<groupId.with.dots>/examples/<type>/` e.g. `nexus/`, `fasta/` |
| Test scripts (R, shell) | test classes only | `src/test/resources/` | `<groupId.with.dots>/examples/` |

**Path naming conventions** (from beast3 layout):
- In `src/main/resources/`: use **slash-separated Java package path** as subdirectory (e.g. `beast/base/evolution/tree/treeparser/`), so `getClass().getResource("Foo.fxml")` resolves correctly.
- In `src/test/resources/`: use the **module name with dots** as the top-level folder (e.g. `beast.base/examples/`), not slashes. This matches how BEAST's test framework resolves example paths.

**Disambiguation rule**: if unsure whether a file belongs in main or test resources, check which Java class loads it. If it is only ever referenced from a class under `src/test/java/`, it goes to `src/test/resources/`. If it is referenced from a class under `src/main/java/`, it goes to `src/main/resources/`.

**Copy and customise template files** from the skeleton:

| Source (skeleton) | Destination | Customisation needed |
|---|---|---|
| `pom.xml` | `pom.xml` | Update groupId, artifactId, version, pkg name, GitHub URLs |
| `src/assembly/beast-package.xml` | `src/assembly/beast-package.xml` | None (copy as-is) |
| `version.xml` | `version.xml` | Update package name, version, service class list |

---

## 4 — Verify Maven dependency resolution

```bash
mvn dependency:resolve -q
```

If BEAST3 artifacts are unresolved, ensure beast3 is installed locally — see
**Alternative: local install or SNAPSHOT builds** in `../beast3/README.md`.

---

## 5 — Generate `src/main/java/module-info.java`

Apply **`module-info.md`** — it scans sources, generates `src/main/java/module-info.java`,
and cross-checks `pom.xml` and `version.xml` for consistency.

---

## Guard rails

- Never delete source files — only move them when restructuring from Ant to Maven.
- Only create directories and files that do not already exist.
- Do not modify files outside the project root.
- If `groupId`, `artifactId`, or other identity fields cannot be inferred, ask the user before
  writing any files.

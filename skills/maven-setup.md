---
name: beast3-maven-setup
description: Assess the current build system of a BEAST2 package and produce a working BEAST3 Maven build — updates or scaffolds pom.xml from beast-package-skeleton, moves sources if needed, and creates module-info.java
metadata:
  type: skill
---

Given a BEAST2 package project root, produce a working BEAST3 Maven build. Prerequisites: `../beast-package-skeleton` and `../beast3` must exist (controller Step 1).

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

**Move Java sources** from legacy root (`src/` or `java/`) to `src/main/java/` and `src/test/java/`, preserving package subdirectory paths.

**Move non-Java files:**

| File type | Destination | Path |
|---|---|---|
| BEAUti FxTemplate XMLs | `src/main/resources/` | `<groupId.with.dots>/fxtemplates/` |
| FXML, icons, CSS | `src/main/resources/` | mirrors calling class's package path |
| Grammar files (ANTLR `.g4`) | `src/main/resources/` | mirrors generated parser's package |
| Any `getClass().getResource(...)` file (main code) | `src/main/resources/` | mirrors calling class's package path |
| Example BEAST XMLs | `src/test/resources/` | `<groupId.with.dots>/examples/` |
| Data files (NEXUS, FASTA, JSON, trees, logs) | `src/test/resources/` | `<groupId.with.dots>/examples/<type>/` |
| Test scripts (R, shell) | `src/test/resources/` | `<groupId.with.dots>/examples/` |

If unsure, check which class loads the file: test-only → `src/test/resources/`; main code → `src/main/resources/`.

**Copy and customise skeleton files:**

| Source | Destination | Customise |
|---|---|---|
| `../beast-package-skeleton/pom.xml` | `pom.xml` | groupId, artifactId, version, pkg name, GitHub URLs |
| `../beast-package-skeleton/src/assembly/beast-package.xml` | `src/assembly/beast-package.xml` | none |
| `../beast-package-skeleton/version.xml` | `version.xml` | version numbers only; log other suggestions to `tmp/b3migration/TODO.md` |

---

## Verify Maven dependency resolution

```bash
mvn dependency:resolve -q
```

If BEAST3 artifacts are unresolved, install them locally — see `../beast3/README.md` → **Alternative: local install or SNAPSHOT builds**.

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
- `module-info.java`: set by `module-info.md`

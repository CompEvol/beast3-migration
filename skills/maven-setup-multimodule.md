---
name: beast3-maven-setup-multimodule
description: Set up a BEAST3 multi-module Maven build — root aggregator POM, <artifactId>-base child module (non-JavaFX code + example XMLs), and <artifactId>-fx child module (JavaFX code + fxtemplates XMLs)
metadata:
  type: skill
---

Given a BEAST2 package project root that contains JavaFX GUI code, produce a working BEAST3
multi-module Maven build with two child modules. All relative paths are anchored at the project
root. The skeleton at `../beast-package-skeleton` and beast3 at `../beast3` must already be
available (run the controller's Step 1 first).

The `../beast3/pom.xml` and its child `beast-base/pom.xml` / `beast-fx/pom.xml` are the
authoritative reference for structure and dependency versions — consult them when filling in
version numbers or plugin configuration.

---

## Determine project identity

Infer from existing `pom.xml`, `version.xml`, or source files. If any field cannot be inferred,
ask the user before writing any files.

| Field | Example |
|---|---|
| `groupId` | `io.github.compevol` |
| `artifactId` | `beast-mypackage` (the project's canonical artifact ID) |
| `version` | `1.0.0-SNAPSHOT` |
| `beast.pkg.name` | display name used in `version.xml` |
| GitHub org / repo | used in SCM and distribution URLs |

Child module artifact IDs are derived automatically:
- **base module**: `<artifactId>-base`
- **fx module**: `<artifactId>-fx`

---

## Classify Java source files

Scan for JavaFX imports to split sources between the two child modules:

```bash
grep -rl "import javafx\." src/ 2>/dev/null | grep "\.java$" | sort
```

| Import pattern | Destination module |
|---|---|
| `import javafx.*` present | `<artifactId>-fx` |
| No `import javafx.*` | `<artifactId>-base` |

**Dependency-pull rule**: if a non-JavaFX class is imported by a JavaFX class, it stays in
`-base`; do not move it to `-fx`. Only files that directly import JavaFX go to `-fx`.

Record the two lists for use in the **Move sources** section below.

---

## Create directory structure

Create only directories that do not already exist:

```bash
# Root (aggregator — no src/ needed)
mkdir -p <artifactId>-base/src/main/java
mkdir -p <artifactId>-base/src/main/resources
mkdir -p <artifactId>-base/src/test/java
mkdir -p <artifactId>-base/src/test/resources

mkdir -p <artifactId>-fx/src/main/java
mkdir -p <artifactId>-fx/src/main/resources
mkdir -p <artifactId>-fx/src/test/java
mkdir -p <artifactId>-fx/src/assembly
```

---

## Move sources with `git mv`

Use `git mv` for every file so that git history is preserved (controller rule U1).

**Java sources** — move each file to the child module that owns it (determined above), preserving
the full package subdirectory path under `src/main/java/` or `src/test/java/`.

```bash
# Example — adjust paths to match the actual file:
git mv src/main/java/com/example/MyPanel.java <artifactId>-fx/src/main/java/com/example/MyPanel.java
git mv src/main/java/com/example/MyModel.java <artifactId>-base/src/main/java/com/example/MyModel.java
```

**Non-Java resources** — classify and move according to the table below:

| File type | Destination module | Path under `src/` |
|---|---|---|
| BEAUti FxTemplate XMLs (`fxtemplates/` dir) | `-fx` | `main/resources/<groupId.slash>/fxtemplates/` |
| FXML layout files + icons / CSS used by GUI | `-fx` | `main/resources/<package/path>/` |
| Grammar files (ANTLR `.g4`) | `-base` | `main/resources/<package/path>/` |
| Any file loaded via `getClass().getResource()` from main code | whichever module owns the loading class | mirrors the calling class's package path |
| Example BEAST XML analyses | `-base` | `test/resources/<groupId.dots>/examples/` |
| Data files (NEXUS, FASTA, JSON, trees, logs) | `-base` | `test/resources/<groupId.dots>/examples/<type>/` |
| Test scripts (R, shell) | `-base` | `test/resources/<groupId.dots>/examples/` |

**Disambiguation**: if unsure, check which Java class loads the file. If only referenced from
`src/test/java/` → test resources of whichever module owns that test class.

---

## Write the root aggregator POM

Create `pom.xml` at the project root. Key elements modelled on `../beast3/pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" ...>
    <modelVersion>4.0.0</modelVersion>

    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>${version}</version>
    <packaging>pom</packaging>        <!-- aggregator — no source compilation here -->

    <modules>
        <module>${artifactId}-base</module>
        <module>${artifactId}-fx</module>
    </modules>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.release>25</maven.compiler.release>
        <javafx.version>25.0.2</javafx.version>   <!-- match ../beast3/pom.xml -->
        <!-- copy remaining version properties from ../beast3/pom.xml as needed -->
    </properties>

    <dependencyManagement>
        <dependencies>
            <!-- Internal modules -->
            <dependency>
                <groupId>${groupId}</groupId>
                <artifactId>${artifactId}-base</artifactId>
                <version>${project.version}</version>
            </dependency>
            <!-- BEAST3 provided deps (beast-pkgmgmt, beast-base, beast-fx) -->
            <!-- JavaFX, commons, JUnit — copy from ../beast3/pom.xml dependencyManagement -->
        </dependencies>
    </dependencyManagement>

    <build>
        <pluginManagement>
            <!-- maven-compiler-plugin (release 25), maven-surefire-plugin, etc.
                 Copy from ../beast-package-skeleton/pom.xml or ../beast3/pom.xml -->
        </pluginManagement>
        <plugins>
            <!-- maven-source-plugin, maven-javadoc-plugin — copy from ../beast3/pom.xml -->
        </plugins>
    </build>
</project>
```

Do not add `<dependencies>` directly to the root POM — put them in child module POMs.

---

## Write the `-base` child POM

Create `<artifactId>-base/pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project ...>
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>${groupId}</groupId>
        <artifactId>${artifactId}</artifactId>
        <version>${version}</version>
    </parent>

    <artifactId>${artifactId}-base</artifactId>
    <name>${beast.pkg.name} Base</name>

    <dependencies>
        <!-- BEAST3 core — provided at runtime -->
        <dependency>
            <groupId>io.github.compevol</groupId>
            <artifactId>beast-pkgmgmt</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>io.github.compevol</groupId>
            <artifactId>beast-base</artifactId>
            <scope>provided</scope>
        </dependency>
        <!-- Add only the non-JavaFX dependencies actually used by base sources:
             commons-math4-legacy, antlr4-runtime, commons-numbers-*, commons-rng-*,
             commons-statistics-* — copy version-managed entries from root POM -->

        <!-- Testing -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

---

## Write the `-fx` child POM

Create `<artifactId>-fx/pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project ...>
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>${groupId}</groupId>
        <artifactId>${artifactId}</artifactId>
        <version>${version}</version>
    </parent>

    <artifactId>${artifactId}-fx</artifactId>
    <name>${beast.pkg.name} FX</name>

    <dependencies>
        <!-- Sibling base module -->
        <dependency>
            <groupId>${groupId}</groupId>
            <artifactId>${artifactId}-base</artifactId>
            <version>${project.version}</version>
        </dependency>

        <!-- BEAST3 FX — provided at runtime -->
        <dependency>
            <groupId>io.github.compevol</groupId>
            <artifactId>beast-pkgmgmt</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>io.github.compevol</groupId>
            <artifactId>beast-base</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>io.github.compevol</groupId>
            <artifactId>beast-fx</artifactId>
            <scope>provided</scope>
        </dependency>

        <!-- JavaFX (provided — runtime supplies them via beast-fx) -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-swing</artifactId>
            <scope>provided</scope>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testfx</groupId>
            <artifactId>testfx-core</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testfx</groupId>
            <artifactId>testfx-junit5</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <!-- Copy assembly plugin config from ../beast-package-skeleton/pom.xml -->
    </build>
</project>
```

Copy `src/assembly/beast-package.xml` from `../beast-package-skeleton/` into
`<artifactId>-fx/src/assembly/`.

---

## Update version.xml

If a `version.xml` exists at the project root, update the service class list to reference
classes from both child modules. Keep the file at the project root — it is not moved into a
child module.

---

## Verify Maven dependency resolution

```bash
mvn dependency:resolve -q
```

Run from the project root — Maven resolves all modules. If BEAST3 artifacts are unresolved,
install them locally; see **Alternative: local install or SNAPSHOT builds** in
`../beast3/README.md`.

---

## Generate `module-info.java` for each child module

Apply **`module-info.md`** twice — once for `-base`, once for `-fx`:

1. `<artifactId>-base/src/main/java/module-info.java` — scan `-base` sources only; do not
   declare JavaFX `requires`.
2. `<artifactId>-fx/src/main/java/module-info.java` — scan `-fx` sources; declare
   `requires javafx.controls`, `requires javafx.fxml`, etc. as needed; add
   `requires <artifactId>.base` for the sibling module.

---

## Guard rails

- Never delete source files — only move them with `git mv`.
- Only create directories and files that do not already exist.
- Do not modify files outside the project root.
- Do not add `<dependencies>` to the root aggregator POM — only child POMs get them.
- If `groupId`, `artifactId`, or other identity fields cannot be inferred, ask the user
  before writing any files.

---

## Log (controller Step 7 report)

Record the following for the Step 7 report table after this skill completes:

- `pom.xml`: `created (multi-module: root + -base + -fx)` or `updated to multi-module`
- `Sources moved`: `yes — N files to -base, M files to -fx`
- `mvn dependency:resolve`: `PASS` or `FAIL — <reason>`
- `module-info.java`: set by `module-info.md` (two files generated)

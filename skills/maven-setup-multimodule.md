---
name: beast3-maven-setup-multimodule
description: Set up a BEAST3 multi-module Maven build — root aggregator POM, <artifactId>-base child module (non-JavaFX code + example XMLs), and <artifactId>-fx child module (JavaFX code + fxtemplates XMLs)
metadata:
  type: skill
---

Given a BEAST2 package project root with JavaFX GUI code, produce a BEAST3 multi-module Maven build. Prerequisites: `../beast-package-skeleton` and `../beast3` must exist (controller Step 1).

Use `../beast3/pom.xml`, `beast-base/pom.xml`, and `beast-fx/pom.xml` as authoritative references for version numbers and plugin config.

---

## Determine project identity

Infer from existing `pom.xml`, `version.xml`, or source files. Ask before writing any files if any field is unclear.

| Field | Example |
|---|---|
| `groupId` | `io.github.compevol` |
| `artifactId` | `beast-mypackage` |
| `version` | `1.0.0-SNAPSHOT` |
| `beast.pkg.name` | display name from `version.xml` |
| GitHub org/repo | used in SCM and distribution URLs |

Child modules: `<artifactId>-base` and `<artifactId>-fx`.

---

## Classify Java sources

```bash
grep -rl "import javafx\." src/ 2>/dev/null | grep "\.java$" | sort
```

| Has `import javafx.*` | → `<artifactId>-fx` |
|---|---|
| No JavaFX import | → `<artifactId>-base` |

If a non-JavaFX class is imported by a JavaFX class, keep it in `-base`.

---

## Create directories

```bash
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

**Java sources** — move each file to its classified module, preserving the full package path under `src/main/java/` or `src/test/java/`.

**Non-Java resources:**

| File type | Module | Path |
|---|---|---|
| BEAUti FxTemplate XMLs | `-fx` | `src/main/resources/<groupId.slash>/fxtemplates/` |
| FXML, icons, CSS | `-fx` | `src/main/resources/<package/path>/` |
| Grammar files (ANTLR `.g4`) | `-base` | `src/main/resources/<package/path>/` |
| `getClass().getResource()` files | whichever module owns the loading class | mirrors calling class's package path |
| Example BEAST XMLs | `-base` | `src/test/resources/<groupId.dots>/examples/` |
| Data files (NEXUS, FASTA, JSON, trees, logs) | `-base` | `src/test/resources/<groupId.dots>/examples/<type>/` |
| Test scripts | `-base` | `src/test/resources/<groupId.dots>/examples/` |

If unsure: check which class loads the file. Test-only → test resources of whichever module owns that test class.

---

## Write root aggregator `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" ...>
    <modelVersion>4.0.0</modelVersion>
    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>${version}</version>
    <packaging>pom</packaging>

    <modules>
        <module>${artifactId}-base</module>
        <module>${artifactId}-fx</module>
    </modules>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.release>25</maven.compiler.release>
        <!-- copy remaining version properties from ../beast3/pom.xml -->
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>${groupId}</groupId>
                <artifactId>${artifactId}-base</artifactId>
                <version>${project.version}</version>
            </dependency>
            <!-- BEAST3 provided deps, JavaFX, JUnit — copy from ../beast3/pom.xml -->
        </dependencies>
    </dependencyManagement>

    <build>
        <pluginManagement>
            <!-- compiler (release 25), surefire — copy from ../beast-package-skeleton/pom.xml -->
        </pluginManagement>
        <plugins>
            <!-- source, javadoc — copy from ../beast3/pom.xml -->
        </plugins>
    </build>
</project>
```

Do not add `<dependencies>` to the root POM — only child POMs get them.

---

## Write `<artifactId>-base/pom.xml`

```xml
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
        <dependency><groupId>io.github.compevol</groupId><artifactId>beast-pkgmgmt</artifactId><scope>provided</scope></dependency>
        <dependency><groupId>io.github.compevol</groupId><artifactId>beast-base</artifactId><scope>provided</scope></dependency>
        <!-- non-JavaFX deps actually used (commons-math4, antlr4-runtime, etc.) -->
        <dependency><groupId>org.junit.jupiter</groupId><artifactId>junit-jupiter</artifactId><scope>test</scope></dependency>
        <dependency><groupId>junit</groupId><artifactId>junit</artifactId><scope>test</scope></dependency>
    </dependencies>
</project>
```

---

## Write `<artifactId>-fx/pom.xml`

```xml
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
        <dependency><groupId>${groupId}</groupId><artifactId>${artifactId}-base</artifactId><version>${project.version}</version></dependency>
        <dependency><groupId>io.github.compevol</groupId><artifactId>beast-pkgmgmt</artifactId><scope>provided</scope></dependency>
        <dependency><groupId>io.github.compevol</groupId><artifactId>beast-base</artifactId><scope>provided</scope></dependency>
        <dependency><groupId>io.github.compevol</groupId><artifactId>beast-fx</artifactId><scope>provided</scope></dependency>
        <dependency><groupId>org.openjfx</groupId><artifactId>javafx-controls</artifactId><scope>provided</scope></dependency>
        <dependency><groupId>org.openjfx</groupId><artifactId>javafx-fxml</artifactId><scope>provided</scope></dependency>
        <dependency><groupId>org.openjfx</groupId><artifactId>javafx-swing</artifactId><scope>provided</scope></dependency>
        <dependency><groupId>org.junit.jupiter</groupId><artifactId>junit-jupiter</artifactId><scope>test</scope></dependency>
        <dependency><groupId>junit</groupId><artifactId>junit</artifactId><scope>test</scope></dependency>
        <dependency><groupId>org.testfx</groupId><artifactId>testfx-core</artifactId><scope>test</scope></dependency>
        <dependency><groupId>org.testfx</groupId><artifactId>testfx-junit5</artifactId><scope>test</scope></dependency>
    </dependencies>

    <build>
        <!-- Copy assembly plugin config from ../beast-package-skeleton/pom.xml -->
    </build>
</project>
```

Copy `src/assembly/beast-package.xml` from `../beast-package-skeleton/` into `<artifactId>-fx/src/assembly/`.

---

## Update `version.xml`

Only version numbers may change:
1. `version` attribute on `<package>` — match `pom.xml` version.
2. `atleast` attributes on `<depends>` — match BEAST3 dep versions in child POMs.

Do not restructure, reorder, add, or remove any elements. Log any structural suggestions to `tmp/b3migration/TODO.md`:

```markdown
| version.xml | <classname or element> | <suggestion> |
```

---

## Verify Maven dependency resolution

```bash
mvn dependency:resolve -q
```

If BEAST3 artifacts are unresolved, see `../beast3/README.md` → **Alternative: local install or SNAPSHOT builds**.

---

## Generate `module-info.java` for each child module

Apply **`module-info.md`** twice:

1. `-base`: scan `-base` sources only; no JavaFX `requires`.
2. `-fx`: scan `-fx` sources; add `requires javafx.controls`, `requires javafx.fxml`, etc.; add `requires <artifactId>.base`.

---

## Guard rails

- Never delete source files — only `git mv`.
- Only create directories/files that don't already exist.
- Do not modify files outside the project root.
- Do not add `<dependencies>` to the root aggregator POM.
- Ask before writing if identity fields cannot be inferred.

---

## Log (controller Step 7 report)

- `pom.xml`: `created (multi-module: root + -base + -fx)` or `updated to multi-module`
- `Sources moved`: `yes — N files to -base, M files to -fx`
- `mvn dependency:resolve`: `PASS` or `FAIL — <reason>`
- `module-info.java`: set by `module-info.md` (two files generated)

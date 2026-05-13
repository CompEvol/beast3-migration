---
name: beast3-module-info
description: Generate src/main/java/module-info.java for a BEAST3 package by scanning Java sources for BEASTInterface providers, then cross-check pom.xml, version.xml, and module-info.java for version and provider consistency
metadata:
  type: skill
---

Run the generation script (Python 3.8+, no extra dependencies) from the project root:

```bash
python ../beast3-migration/skills/scripts/gen_module_info.py .
```


---

## What the script does

### A — Scan sources for BEAST providers

Walks every `.java` file under `src/main/java/` (excluding `module-info.java` itself) and
identifies concrete top-level classes that are BEAST providers using source-level heuristics:
- carries `@Description(...)`, OR
- `extends` a known BEAST base class (`BEASTObject`, `CalculationNode`, `Distribution`,
  `Operator`, `Logger`, `DataType*`, `SubstitutionModel*`, etc.), OR
- directly `implements BEASTInterface`

Abstract classes, interfaces, `@interface` annotations, and inner classes are excluded —
JPMS requires concrete providers with a public no-arg constructor.

### B — Generate `module-info.java`

Writes `src/main/java/module-info.java` with:
- `open module <groupId>` — module name taken from `groupId` in `pom.xml`
- `requires beast.pkgmgmt` and `requires beast.base`
- `exports` for every package found under `src/main/java/`
- `provides beast.base.core.BEASTInterface with` listing all detected providers

See `../beast3/beast-base/src/main/java/module-info.java` for a full real-world example
with additional `requires`, `uses`, and multiple `provides` blocks.

### C — Cross-check pom.xml, version.xml, and module-info.java

After writing the file the script prints any consistency issues:

| Check | What triggers it | Fix |
|---|---|---|
| Version mismatch | `pom.xml` version ≠ `version.xml` version | Align both to the same value |
| SNAPSHOT | `pom.xml` version contains `SNAPSHOT` | Keep `version.xml` at last formal release; do not put a SNAPSHOT in it |
| Pre-release warning | Either version contains `alpha`, `beta`, or `rc` | `version.xml` should only carry formal release versions |
| In `version.xml` not `module-info` | Class in `<service>` block not detected by scan | Verify class still exists; add manually to `provides` or remove from `version.xml` |
| In `module-info` not `version.xml` | Newly detected class missing from `version.xml` | Add `<provider classname="…"/>` to the `<service>` block |
| `BEAST.base` version mismatch | `pom.xml` dep version ≠ `version.xml` `atleast` attribute | Align `atleast` to match the pom dependency version |

Review the console output and fix every reported issue before proceeding. If the heuristic
missed a class (e.g. one that implements `BEASTInterface` only transitively through a
non-standard base), add it manually to both `module-info.java` and `version.xml`.

Use **Option A (single module)** unless the package has substantial GUI code with multiple
custom BEAUti editors, in which case use **Option B (core + fx)**. Both options are described
in `../beast3/scripts/migration-guide.md`.

---

## Code summary (`gen_module_info.py`)

**Location:** `../beast3-migration/skills/scripts/gen_module_info.py`
**Language:** Python 3.8+ — stdlib only (`re`, `sys`, `xml.etree.ElementTree`, `pathlib`)
**Entry point:** `main()` — accepts optional `project_root` as `sys.argv[1]`, defaults to `"."`

### Functions

| Function | Inputs | Returns | Purpose |
|---|---|---|---|
| `scan_sources(src_java)` | `Path` to `src/main/java` | `(list[fqcn], list[package])` both sorted | Walks `*.java`, applies `_is_provider()`, collects packages |
| `_is_provider(source)` | raw Java source `str` | `bool` | Returns `True` if concrete + (`@Description` or BEAST base or `implements BEASTInterface`) |
| `read_pom(pom_path)` | `Path` to `pom.xml` | `dict` with keys `groupId`, `artifactId`, `version`, `module_name`, `deps` | Parses Maven POM; resolves `${property}` references; `deps` is `"g:a" → version` |
| `read_version_xml(version_path)` | `Path` to `version.xml` | `dict` with keys `name`, `version`, `providers`, `depends` | Extracts `<service><provider classname>` list and `<depends on atleast>` list |
| `generate_module_info(module_name, packages, providers)` | strings + sorted lists | `str` | Renders the `module-info.java` file content |
| `check_consistency(pom, vxml, providers)` | dicts + provider list | `list[str]` of issue messages | Runs all six consistency checks; returns empty list if clean |

### Detection regex patterns

| Pattern variable | Matches | Purpose |
|---|---|---|
| `_DESCRIPTION` | `@Description(` | BEAST annotation — primary heuristic |
| `_BEAST_BASES` | `extends BEASTObject\|CalculationNode\|Distribution\|Operator\|Logger\|DataType*\|…` | Known abstract BEAST base classes |
| `_IMPLEMENTS_BI` | `implements … BEASTInterface` (up to `{`) | Direct interface implementation |
| `_ABSTRACT` | `abstract class` | Exclusion — abstract classes cannot be JPMS providers |
| `_INTERFACE` | `public interface Foo` | Exclusion |
| `_ANNOTATION` | `public @interface Foo` | Exclusion |
| `_PACKAGE` | `package com.example;` | Extract package name for FQCNs and `exports` |

### Consistency check logic

- **Version mismatch**: `pom["version"] != vxml["version"]`
- **SNAPSHOT guard**: `"SNAPSHOT" in pom["version"].upper()` → always an issue for `version.xml`
- **Pre-release** (`_PRE_RELEASE = re.compile(r"alpha|beta|rc|snapshot", re.IGNORECASE)`): checked on both `pom["version"]` and `vxml["version"]`
- **Provider symmetric diff**: `set(vxml["providers"]) - set(providers)` and vice-versa
- **BEAST.base atleast**: `pom["deps"]["io.github.compevol:beast-base"]` vs `vxml["depends"][*]["atleast"]` where `on` is `BEAST.base`, `beast-base`, or `beast.base`

### Known limitations

- Transitive `BEASTInterface` implementations through a non-standard base class are not detected — add them manually.
- Inner class providers (e.g. `MyModel.Inner`) are excluded by design; JPMS cannot register them.
- The script reads source files, not bytecode — a class that is generated at compile time will not appear.

---

## Log (controller Step 7 report)

Record the script's console output for the Step 7 report:

- `Providers detected: N`
- `Packages exported: N`
- `Consistency issues: N resolved / N remaining` — list any that required manual fix

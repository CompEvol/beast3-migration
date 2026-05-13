---
name: beast3-module-info
description: Generate src/main/java/module-info.java for a BEAST3 package by scanning Java sources for BEASTInterface providers, then cross-check pom.xml, version.xml, and module-info.java for version and provider consistency
metadata:
  type: skill
---

Run from the project root:

```bash
python ../beast3-migration/skills/scripts/gen_module_info.py .
```

---

## What the script does

**A — Scan sources** (`src/main/java/`, excluding `module-info.java`): detects concrete top-level classes that are BEAST providers if they carry `@Description(...)`, extend a known BEAST base class (`BEASTObject`, `CalculationNode`, `Distribution`, `Operator`, `Logger`, `DataType*`, `SubstitutionModel*`, etc.), or directly `implement BEASTInterface`. Abstract classes, interfaces, annotations, and inner classes are excluded.

**B — Write `module-info.java`** at `src/main/java/module-info.java`:
- `open module <groupId>` (from `pom.xml`)
- `requires beast.pkgmgmt` and `requires beast.base`
- `exports` for every package under `src/main/java/`
- `provides beast.base.core.BEASTInterface with` all detected providers

See `../beast3/beast-base/src/main/java/module-info.java` for a real-world example.

**C — Cross-check** and print consistency issues:

| Issue | Fix |
|---|---|
| `pom.xml` version ≠ `version.xml` version | Align both |
| `pom.xml` version contains `SNAPSHOT` | Keep `version.xml` at last formal release |
| Either version contains `alpha`, `beta`, or `rc` | `version.xml` should only carry formal releases |
| Class in `version.xml` `<service>` block not detected by scan | Verify class exists; add manually to `module-info.java`. Do not remove from `version.xml`. |
| Detected class missing from `version.xml` | Do not add to `version.xml`. Log to `tmp/b3migration/TODO.md`. |
| `pom.xml` beast-base dep version ≠ `version.xml` `atleast` attribute | Update `atleast` in `version.xml` to match pom version |

---

## After the script runs

- **Fix `module-info.java`**: add any providers the heuristic missed (e.g. classes that implement `BEASTInterface` only transitively through a non-standard base).
- **Do not modify `version.xml` structure**: only version-number edits (`<package version>` and `<depends atleast>`) are permitted.
- **Log all provider discrepancies** to `tmp/b3migration/TODO.md` (create with header if missing):

```markdown
# Migration TODOs
| File | Class | Note |
|---|---|---|
| module-info.java | com.example.MyClass | missed by gen_module_info.py — extends Alignment (not in _BEAST_BASES); added manually |
| version.xml | com.example.OtherClass | in module-info but not version.xml — verify and add <provider> manually after review |
```

---

## Known limitations

- Transitive `BEASTInterface` implementations through a non-standard base are not detected — add manually.
- Inner class providers are excluded by design (JPMS cannot register them).
- Compile-time-generated classes will not appear.

---

## Log (controller Step 7 report)

- `Providers detected: N`
- `Packages exported: N`
- `Consistency issues: N resolved / N remaining` — list any requiring manual fix

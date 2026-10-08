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
- `open module <name>` — `<name>` is `pom.xml`'s own `artifactId` with hyphens converted to dots (e.g. `beast-labs` → `beast.labs`, `beast-base` → `beast.base`). **Not** the groupId (groupIds here are reverse-DNS like `io.github.compevol` and are never the module name).
  - **Exception — multi-module Maven reactor** (`pom.xml` has a `<parent>`): the shared/public module name is sometimes the *parent's* artifactId instead, with `.fx` appended for the FX/GUI companion submodule (e.g. `codonsubstmodels` / `codonsubstmodels.fx`, from parent artifactId `codonsubstmodels`, even though the submodules' own artifactIds are `csm-base`/`csm-fx`). This can't be derived mechanically — the script prints a warning when `<parent>` is present; verify against a sibling module's `module-info.java` or `version.xml`'s `<package name>` before trusting the artifactId-based default.
- `requires beast.pkgmgmt` and `requires beast.base`
- `exports` for every package under `src/main/java/` — must cover all packages, not a subset
- if any providers were detected: `provides beast.base.core.BEASTInterface with` all detected providers
- **no `uses beast.base.core.BEASTInterface;` clause** — see "Why no `uses` clause" below before adding one.

See `../BEASTLabs/src/main/java/module-info.java` for a real-world example of this exact shape (`provides` only, no `uses`).

**C — Cross-check** and print consistency issues:

| Issue | Fix |
|---|---|
| `pom.xml` version ≠ `version.xml` version | Align both |
| `pom.xml` version contains `SNAPSHOT` | Keep `version.xml` at last formal release |
| Either version contains `alpha`, `beta`, or `rc` | `version.xml` should only carry formal releases |
| Class in `version.xml` `<service>` block not detected by scan | Verify class exists; add manually to `module-info.java`. Do not remove from `version.xml`. |
| Detected class missing from `version.xml` | Do not add to `version.xml`. Log to `tmp/b3migration/TODO.md`. |
| `pom.xml` beast-base dep version ≠ `version.xml` `atleast` attribute | Update `atleast` in `version.xml` to match the pom version's `major.minor.patch` only — strip any pre-release qualifier (`-beta6`, `-alpha1`, `-rc1`, `-SNAPSHOT`), per the alpha/beta/rc rule above. E.g. pom `beast.version=2.8.0-beta6` → `atleast='2.8.0'`. |

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
- The script only detects `beast.base.core.BEASTInterface` providers. If `version.xml` registers other service types (e.g. `beast.base.evolution.datatype.DataType`), add a matching `provides <type> with ...;` block to `module-info.java` by hand. Only add a `uses <type>;` line alongside it if this package's *own* code calls `ServiceLoader.load(<type>.class)` directly — see "Why no `uses` clause" below.

---

## Why no `uses` clause

`provides` and `uses` are independent JPMS declarations, and only one of them is consumer-side:

- `provides S with C;` — this module supplies an implementation `C` of service `S`. Declared by the **provider**.
- `uses S;` — this module itself calls `ServiceLoader.load(S.class)` to look up implementations of `S`. Required only on the module that makes that call — the **consumer**. A provider never needs to declare `uses` for a service it merely supplies.

This was verified directly (not just reasoned about) with a throwaway 3-module JPMS test: a provider module declaring `provides` but no `uses` was still found correctly by `ServiceLoader.load()` called from a separate consumer module. Removing `uses` from the *consumer* instead — leaving the provider unchanged — broke the lookup with `ServiceConfigurationError: module ... does not declare 'uses'`. That confirms `uses` gates the caller, not the supplier.

BEAST3 packages generated by this skill are providers of `BEASTInterface`, not consumers. Discovery is done by `beast.pkgmgmt.BEASTClassLoader.discoverFromModuleDescriptors()`, which reflects directly over each module's `ModuleDescriptor.provides()` list to find `BEASTInterface` implementations — it never calls `java.util.ServiceLoader.load()`, so a package's own `uses` clause is never consulted for this. `beast-base` and `beast-fx` do declare `uses beast.base.core.BEASTInterface;` anyway (harmless — declaring `uses` for a service you don't consume has no negative effect, it's just unused), but `BEASTLabs/src/main/java/module-info.java` has no `uses` clause at all and works correctly. This skill follows the `BEASTLabs` shape: `provides` only, no `uses`.

If asked "why doesn't my `module-info.java` have `uses beast.base.core.BEASTInterface;`?" — explain the above: it's optional, not required, because BEAST's own provider discovery reads `provides` declarations directly instead of going through `ServiceLoader`.

---

## Log (controller Step 7 report)

- `Providers detected: N`
- `Packages exported: N`
- `Consistency issues: N resolved / N remaining` — list any requiring manual fix

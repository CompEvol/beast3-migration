---
name: beast3-fxtemplates
description: Migrate BEAST2 class references inside BEAUti FxTemplate XMLs and FXML files to BEAST3 spec equivalents — driven by the linter report in ./reports/<package.name>.md
metadata:
  type: skill
---

Migrates class name strings inside BEAUti template XML and FXML files. Runs at controller
Step 4, after Java migration is complete. Files are already in `src/main/resources/` (moved
by `maven-setup.md`). Universal rules U2–U5 apply; U1 does not.
**Prerequisite: see controller Step 4 — compile check may be skipped if STATUS.md exists and all rows are `done`.**

> **Note on `version=`**: BEAUti templates conventionally keep `version='2.0'` — do not
> change it. Migration here means the body uses `beast.base.spec.*` types and parameter
> declarations use `RealScalarParam` etc. rather than `RealParameter`.

---

## Step 1 — Re-read the linter report from disk

> ⚠️ **Read the file fresh from disk now.** Do not use any earlier read from this session.
> Java migration may have changed what remains, and the report may have been regenerated.

Determine the package name from `pom.xml` (`<artifactId>`) or `version.xml`, then read:

```
./reports/<package.name>.md
```

Extract two sections:

- **"FxTemplates pending migration"** — file list of templates whose body does not yet use `beast.base.spec.*` references.
- **"Deprecated class references in XMLs"** — per-file tables; keep only entries classified as **BEAUti templates** by the XML classification rule (controller, between Steps 3 and 4): path contains `fxtemplates` or module name ends in `-fx`. These list exactly which attributes (`spec=`, `type=`, `class=`, `fx:controller`) carry deprecated class names, with the suggested replacement.

This is the **FxTemplate migration queue**. If neither section is present or both are empty, skip to the Log section.

---

## Step 2 — Attributes that carry class references

The linter's per-file tables identify the specific hits. In addition to those, check these
attribute types when working through each file — the linter may not catch every occurrence:

| Attribute | Typical example |
|---|---|
| `spec` | `spec="beast.base.evolution.substitutionmodel.HKY"` |
| `type` | `type="beast.base.inference.parameter.RealParameter"` |
| `class` | `class="beast.base.evolution.likelihood.TreeLikelihood"` |
| `fx:controller` | `fx:controller="com.example.gui.MyPanel"` — update only if the referenced class itself moved |

Only update attribute **values** that contain a `beast.base.*` class with a confirmed `.spec.`
counterpart. Leave attribute names and all other content unchanged.

---

## Step 3 — Apply per-file replacements from the linter table

For each file, work through its linter replacement table row by row.

### Simple `.spec.` rename

When the replacement is the same class name but with `.spec.` inserted in the package path,
apply U2 directly — no structural change needed:

```
beast.base.<domain>.<Class>  →  beast.base.spec.<domain>.<Class>
```

### Complex conversion — consult the relevant skill

When the replacement involves a structural or type-level change, route to the skill below
for the exact XML patterns and examples:

| Conversion type | Signal in linter Replacement column | Skill to consult |
|---|---|---|
| Parameter type + domain | `RealScalarParam`, `RealVectorParam`, `SimplexParam`, `IntScalarParam`, `IntVectorParam`, `BoolScalarParam`, `BoolVectorParam` | `java-migration/parameters.md` |
| Distribution rename only | `*.spec.inference.distribution.*` class — same name as deprecated | Apply U2 directly |
| `Prior` → direct distribution | Element is `<distribution spec="Prior">` wrapping an inner `<distr .../>` | `java-migration/distributions.md` |
| Vector prior → `IID` | Vector-valued `x=` on a `Prior` whose inner distr is scalar | `java-migration/distributions.md` |
| Operator rename | `*.spec.*operator.*` class | `java-migration/operators.md` |

For the full XML conversion patterns (scalar/vector/simplex parameter examples, Prior→distribution,
vector prior→IID, unknown class TODO comments) see **`xml-migration/example-xmls.md` Step 3** — the patterns
are identical for FxTemplates.

---

## Step 4 — Verify

```bash
grep -rn "beast\.base\." src/main/resources/ \
  --include="*.xml" --include="*.fxml" | grep -v "\.spec\."
```

Any hit not in the "do not rename" list is an error — fix it before proceeding.

**Do not rename** (not deprecated, no spec twin):
`Tree`, `Node`, `TreeInterface`, `TreeParser`, `SiteModelInterface`, `SubstitutionModel`, `BranchRateModel`

---

## Log (controller Step 7 report)

- Files migrated: N
- Class references updated: N — list each (e.g. `HKY, RealParameter → .spec.`)
- Complex conversions: list each type applied (e.g. `RealParameter → RealScalarParam<PositiveReal>`, `Prior → LogNormal`, `vector Prior → IID`)
- Warnings — non-deprecated BEAST2 classes migrated: N — list each class name (or "none")
- TODOs: N XML comments inserted — list each class name

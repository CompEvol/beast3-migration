---
name: beast3-fxtemplates
description: Migrate BEAST2 class references inside BEAUti FxTemplate XMLs and FXML files to BEAST3 spec equivalents — updates spec/type/class attribute values after maven-setup has moved the files to src/main/resources/
metadata:
  type: skill
---

Migrates class name strings inside XML resource files. Run after `maven-setup.md` has moved
files to `src/main/resources/`. Universal rules U2–U5 apply; U1 does not (files are already
in place). There is no `mvn compile` check — use the verify grep in Step 4 instead.

---

## Step 1 — Find files to migrate

```bash
grep -rl "beast\.base\." src/main/resources/ 2>/dev/null \
  --include="*.xml" --include="*.fxml" | grep -v "\.spec\." | sort
```

This is the **FxTemplate migration queue**. If empty, skip to the Log section.

---

## Step 2 — Attributes that carry class references

Scan every file in the queue for these attributes:

| Attribute | Typical example |
|---|---|
| `spec` | `spec="beast.base.evolution.substitutionmodel.HKY"` |
| `type` | `type="beast.base.inference.parameter.RealParameter"` |
| `class` | `class="beast.base.evolution.likelihood.TreeLikelihood"` |
| `fx:controller` | `fx:controller="com.example.gui.MyPanel"` — update only if the referenced class itself moved |

Only update attribute **values** that contain a `beast.base.*` class with a confirmed `.spec.`
counterpart. Leave attribute names and all other content unchanged.

---

## Step 3 — Apply the transformation (U2)

```
beast.base.<domain>.<Class>  →  beast.base.spec.<domain>.<Class>
```

Use the same mapping tables as the Java sub-skills (parameters, subst-models, clock-models,
site-likelihood, tree-coalescent, distributions, operators).

**Do NOT rename** — these classes are not deprecated and have no spec twin:

- `beast.base.evolution.tree.Tree`
- `beast.base.evolution.tree.Node`
- `beast.base.evolution.tree.TreeInterface`
- `beast.base.evolution.tree.TreeParser`
- `beast.base.evolution.sitemodel.SiteModelInterface`
- `beast.base.evolution.substitutionmodel.SubstitutionModel`
- `beast.base.evolution.branchratemodel.BranchRateModel`

**Unknown class** (U4 — class is `@Deprecated` in BEAST2 but has no `.spec.` counterpart):
leave the attribute value unchanged and insert an XML comment directly before the element:

```xml
<!-- TODO: no beast3 spec class found for ClassName -->
<plate spec="beast.base.some.ClassName" .../>
```

---

## Step 4 — Verify

Confirm no unintended `beast.base.` (non-spec) references remain:

```bash
grep -rn "beast\.base\." src/main/resources/ \
  --include="*.xml" --include="*.fxml" | grep -v "\.spec\."
```

Any hit that is not in the "Do NOT rename" list above is an error — fix it before proceeding.

---

## Log (controller Step 7 report)

- Files migrated: N
- Class references updated: N — list each (e.g. `HKY, RealParameter → .spec.`)
- Warnings — non-deprecated BEAST2 classes migrated: N — list each class name (or "none")
- TODOs: N XML comments inserted — list each class name

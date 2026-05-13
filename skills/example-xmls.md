---
name: beast3-example-xmls
description: Migrate BEAST2 class references inside example BEAST analysis XMLs in src/test/resources/ to BEAST3 spec equivalents — updates spec attribute values so test analyses run against the migrated API
metadata:
  type: skill
---

Migrates class name strings inside BEAST analysis XML files used by tests. Run after
`maven-setup.md` has moved the files to `src/test/resources/`. The transformation rules
(U2, "do not rename" list, XML TODO comments) are identical to `fxtemplates.md` — this skill
differs only in file location and the narrower set of attributes to check.

Failures in these files are caught by `mvn test` in Step 7 — fix them here first.

---

## Step 1 — Find files to migrate

```bash
grep -rl "beast\.base\." src/test/resources/ 2>/dev/null \
  --include="*.xml" | grep -v "\.spec\." | sort
```

This is the **example XML migration queue**. If empty, skip to the Log section.

---

## Step 2 — Attributes that carry class references

BEAST analysis XMLs use `spec` almost exclusively for class references. Also check:

| Attribute | Example |
|---|---|
| `spec` | `spec="beast.base.evolution.speciation.YuleModel"` |
| `type` | `type="beast.base.inference.parameter.RealParameter"` (rare) |
| `class` | `class="beast.base.evolution.likelihood.TreeLikelihood"` (rare) |

Only update attribute **values** that contain a `beast.base.*` class with a confirmed `.spec.`
counterpart. Leave attribute names, parameter values, and all other content unchanged.

---

## Step 3 — Apply the transformation (U2)

```
beast.base.<domain>.<Class>  →  beast.base.spec.<domain>.<Class>
```

Use the same mapping tables as the Java sub-skills. The same **do not rename** exceptions
apply — see `fxtemplates.md` Step 3 for the full list (`Tree`, `Node`, `TreeInterface`,
`TreeParser`, `SiteModelInterface`, `SubstitutionModel`, `BranchRateModel`).

**Unknown class** (U4 — `@Deprecated` in BEAST2, no `.spec.` counterpart): leave the
attribute value unchanged and insert an XML comment before the element:

```xml
<!-- TODO: no beast3 spec class found for ClassName -->
<distribution spec="beast.base.some.ClassName" .../>
```

---

## Step 4 — Verify

```bash
grep -rn "beast\.base\." src/test/resources/ --include="*.xml" | grep -v "\.spec\."
```

Any hit not in the "do not rename" list is an error — fix it. Remaining issues will also
surface as `mvn test` failures in Step 7.

---

## Log (controller Step 7 report)

- Files migrated: N
- Class references updated: N — list each (e.g. `YuleModel, ScaleOperator → .spec.`)
- Warnings — non-deprecated BEAST2 classes migrated: N — list each class name (or "none")
- TODOs: N XML comments inserted — list each class name

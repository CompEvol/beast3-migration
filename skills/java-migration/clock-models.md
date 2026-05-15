---
name: beast3-clock-models
description: Migrate BEAST2 branch rate model (clock model) imports and the BranchRateModel.Base inner class to BEAST3 spec equivalents
metadata:
  type: skill
---

You are migrating BEAST2 clock model / branch rate model classes to the BEAST3 spec API. Apply all
rules below.

---

## Rules

### R1 — Import prefix: add `.spec.`

Replace `beast.base.evolution.branchratemodel.*` imports with their BEAST3 equivalents.
Every class in the table below has a confirmed BEAST3 counterpart:

| BEAST2 import | BEAST3 import |
|---|---|
| `beast.base.evolution.branchratemodel.StrictClockModel` | `beast.base.spec.evolution.branchratemodel.StrictClockModel` |
| `beast.base.evolution.branchratemodel.UCRelaxedClockModel` | `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` |
| `beast.base.evolution.branchratemodel.RandomLocalClockModel` | `beast.base.spec.evolution.branchratemodel.RandomLocalClockModel` |

`BranchRateModel` is a special case — see R2.

### R2 — `BranchRateModel.Base` inner class → top-level `Base` (special case)

**Apply this rule when both conditions hold:**
1. `import beast.base.evolution.branchratemodel.BranchRateModel` is present
2. `BranchRateModel.Base` appears in the code (fields, parameters, return types, or `Input<>`)

**Skip this rule when:**
- `import beast.base.spec.evolution.branchratemodel.Base` already exists → migration already done
- `BranchRateModel` is used only as an interface → see Edge Cases

In BEAST3 the abstract base is a top-level class, not an inner class of `BranchRateModel`.
Replace the import:

```java
// BEAST2
import beast.base.evolution.branchratemodel.BranchRateModel;

// BEAST3
import beast.base.spec.evolution.branchratemodel.Base;
```

Then replace every type reference `BranchRateModel.Base` in the file with `Base`:
- Field declarations
- Method parameters
- Return types
- `Input<>` generics

Example (from observed diff in `GenericDATreeLikelihood`):
```java
// BEAST2
import beast.base.evolution.branchratemodel.BranchRateModel;
final public Input<BranchRateModel.Base> branchRateModelInput = new Input<>(...);
protected BranchRateModel.Base branchRateModel;
public BranchRateModel.Base getBranchRateModel() { return branchRateModel; }

// BEAST3
import beast.base.spec.evolution.branchratemodel.Base;
final public Input<Base> branchRateModelInput = new Input<>(...);
protected Base branchRateModel;
public Base getBranchRateModel() { return branchRateModel; }
```

### R3 — Mixed import: interface + abstract base

If the file uses both `BranchRateModel` (as an interface) and `BranchRateModel.Base` (as a type),
keep the interface import and add the BEAST3 `Base` import separately:

```java
import beast.base.evolution.branchratemodel.BranchRateModel;  // interface — keep if used
import beast.base.spec.evolution.branchratemodel.Base;         // abstract base — new
```

Rename only the `.Base` references, not the interface references.

### R4 — Clock model rate parameters

Clock models typically expose rate inputs. When migrating `initByName(...)` call sites in tests or
`initAndValidate`, update `RealParameter` arguments to `RealScalarParam<PositiveReal>`. See
`java-migration/parameters.md` for full parameter migration rules.

---

## Edge Cases

- **Custom clock model** that extends `BranchRateModel.Base`: change `extends BranchRateModel.Base`
  to `extends Base` after adding the BEAST3 import.
- **`BranchRateModel` used only as an interface** (e.g. `Input<BranchRateModel>`): this has no spec
  twin yet and is not `@Deprecated` — leave unchanged, no comment (U4 rule 5).
- **Partial migration**: if `import beast.base.spec.evolution.branchratemodel.Base` is already present,
  R2 is done — do not re-add the import or re-replace references.

---

## XML migration

For `spec=` attribute changes in example XMLs and FxTemplates, see **`xml-migration/example-xmls.md`** (Step 3) and **`xml-migration/fxtemplates.md`** (Step 3).

---

## Log (Mode 2b — Changes field)

- Classes renamed to `.spec.`: list each (e.g. `StrictClockModel, UCRelaxedClockModel → .spec.`)
- `BranchRateModel.Base → Base: yes/no`
- `Warnings — non-deprecated BEAST2 classes migrated: N` — list each class name (or "none")

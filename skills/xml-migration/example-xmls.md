---
name: beast3-example-xmls
description: Migrate BEAST2 class references inside example BEAST analysis XMLs in src/test/resources/ to BEAST3 spec equivalents — driven by the linter report in ./reports/<package.name>.md
metadata:
  type: skill
---

Migrates class name strings inside example BEAST analysis XML files. Runs at controller
Step 5, after Java migration is complete. Files are already in `src/test/resources/` (moved
by `maven-setup.md`). Uses the linter report as the primary source of which files need work
and which replacements to make.
**Prerequisite: see controller Step 5 — compile check may be skipped if STATUS.md exists and all rows are `done`.**

---

## Step 1 — Re-read the linter report from disk

> ⚠️ **Read the file fresh from disk now.** Do not use any earlier read from this session.
> Java migration may have changed what remains, and the report may have been regenerated.

Determine the package name from `pom.xml` (`<artifactId>`) or `version.xml`, then read:

```
./reports/<package.name>.md
```

Extract two sections:

- **"Example XMLs pending migration"** — the file list; every file here needs `version="2.8"` and spec updates.
- **"Deprecated class references in XMLs"** — per-file tables; keep only entries classified as **example analyses** by the XML classification rule (controller, between Steps 3 and 4): path does not contain `fxtemplates` and module name does not end in `-fx`. These list exactly which attributes (`spec=`, `type=`, `class=`) carry deprecated class names, with the suggested replacement.

This is the **example XML migration queue**. If neither section is present or both are empty, skip to the Log section.

---

## Step 2 — Apply `version="2.8"`

For every file in the queue, set `version="2.8"` on the root `<beast>` element.

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
| `Prior` → direct distribution | `<distribution spec="Prior">` or `<prior>` element wrapping an inner distr | `java-migration/distributions.md` |
| `popSize` prior — `OneOnX` | `OneOnX` inner distr on `popSize` | See `OneOnX` prior patterns below |
| `hky.kappa` prior — `OneOnX` | `OneOnX` inner distr on `hky.kappa` | See `OneOnX` prior patterns below |
| Vector prior → `IID` | Vector-valued `x=` on a `Prior` whose inner distr is scalar | `java-migration/distributions.md` |
| Operator rename | `*.spec.*operator.*` class | `java-migration/operators.md` |
| Operator — no spec twin | `Exchange`, `WilsonBalding` | Leave unchanged — no spec equivalent |
| `SubtreeSlide` | deprecated — replace with `beast.base.evolution.operator.kernel.BactrianSubtreeSlide` | handled automatically by the XML converter |
| `ScaleOperator` split | `parameter=` vs `tree=` attribute present | See patterns below |
| `Uniform` tree operator | `tree=` attribute present | Use full path — see patterns below |

### XML patterns for parameter conversion

The linter gives the target class name; infer the **domain** from the element's `lower`/`upper`
attributes and the **shape** from the number of space-separated values in `value=`.

**Domain selection (matches `java-migration/parameters.md` domain selection guide):**

| `lower` / `upper` on element | Domain type |
|---|---|
| `lower="0"`, no upper | `beast.base.spec.domain.PositiveReal` (rates, shape params) or `NonNegativeReal` |
| `lower="0" upper="1"` | `beast.base.spec.domain.UnitInterval` |
| No bounds | `beast.base.spec.domain.Real` |
| Integer, `lower="0"` | `beast.base.spec.domain.PositiveInt` or `NonNegativeInt` |

**Scalar (single value in `value=`):**

```xml
<!-- BEAST2 -->
<input id="kappa" spec="beast.base.inference.parameter.RealParameter"
    value="2.0" lower="0"/>

<!-- BEAST3 -->
<input id="kappa" spec="beast.base.spec.inference.parameter.RealScalarParam"
    value="2.0" domain="PositiveReal"/>
```

**Vector (multiple space-separated values):**

```xml
<!-- BEAST2 -->
<input id="rates" spec="beast.base.inference.parameter.RealParameter"
    value="1.0 1.0 1.0" lower="0"/>

<!-- BEAST3 -->
<input id="rates" spec="beast.base.spec.inference.parameter.RealVectorParam"
    value="1.0 1.0 1.0" domain="PositiveReal"/>
```

**Simplex (frequencies — sums to 1, typically connected to a `frequencies` input):**

```xml
<!-- BEAST2 -->
<input id="freqs" spec="beast.base.inference.parameter.RealParameter"
    value="0.25 0.25 0.25 0.25"/>

<!-- BEAST3 -->
<input id="freqs" spec="beast.base.spec.inference.parameter.SimplexParam"
    value="0.25 0.25 0.25 0.25"/>
```

### XML patterns for `Prior` → distribution and vector prior → `IID`

BEAST2 XMLs use two authoring styles for Prior distributions. The converter handles both.

**Style A — explicit `spec=` attribute** (hand-written or older BEAUti):

```xml
<distribution id="kappaprior" spec="beast.base.inference.distribution.Prior" x="@kappa">
    <distr spec="beast.base.inference.distribution.LogNormal" M="1.0" S="1.25"/>
</distribution>
```

**Style B — `<prior>` element tag** (BEAUti-generated, resolved via `<map name="prior">`):

```xml
<prior id="kappaprior" name="distribution" x="@kappa">
    <LogNormal name="distr" M="1.0" S="1.25"/>
</prior>
```

In Style B the inner distribution also uses a tag-as-class name (`<LogNormal>`) instead of
`spec=`. The converter resolves both from the dep_map automatically.

Both styles produce the same BEAST3 output. Note `x=` becomes `param=`:

```xml
<!-- BEAST3 output for both styles above -->
<distribution id="kappaprior"
    spec="beast.base.spec.inference.distribution.LogNormal"
    param="@kappa" M="1.0" S="1.25"/>
```

**Vector prior → `IID`** (vector parameter, scalar inner distribution applied independently):

```xml
<!-- BEAST2 (either style) -->
<distribution id="ratesPrior" spec="beast.base.inference.distribution.Prior" x="@clockRates">
    <distr spec="beast.base.inference.distribution.Gamma" alpha="0.5" beta="2.0"/>
</distribution>

<!-- BEAST3 -->
<distribution id="ratesPrior"
    spec="beast.base.spec.inference.distribution.IID" param="@clockRates">
    <distr spec="beast.base.spec.inference.distribution.Gamma" alpha="0.5" beta="2.0"/>
</distribution>
```

**`OneOnX` prior → LogNormal** (`OneOnX` has no spec equivalent):

`OneOnX` has no spec counterpart in BEAST3. Replace a `Prior` wrapping `OneOnX` with a `LogNormal`
prior. Use `param=` (not `x=`), and declare M and S as child elements (not inline attributes).
Default LogNormal parameters depend on what is being estimated:

*`popSize` — LogNormal(M=3, S=2.5):*

```xml
<!-- BEAST2 -->
<distribution id="popSize.prior" spec="beast.base.inference.distribution.Prior" x="@popSize">
    <distr spec="beast.base.inference.distribution.OneOnX"/>
</distribution>

<!-- BEAST3 -->
<distribution id="popSize.prior" spec="LogNormal" param="@popSize">
    <M spec="RealScalarParam" domain="Real" value="3.0"/>
    <S spec="RealScalarParam" domain="PositiveReal" value="2.5"/>
</distribution>
```

*`hky.kappa` — LogNormal(M=1, S=0.5):*

```xml
<!-- BEAST2 -->
<distribution spec="beast.base.inference.distribution.Prior" x="@hky.kappa">
    <distr spec="beast.base.inference.distribution.OneOnX"/>
</distribution>

<!-- BEAST3 -->
<distribution id="hky.kappa.prior" spec="LogNormal" param="@hky.kappa">
    <M spec="RealScalarParam" domain="Real" value="1.0"/>
    <S spec="RealScalarParam" domain="PositiveReal" value="0.5"/>
</distribution>
```

See `java-migration/distributions.md` for the full Prior and IID rules.

### Unknown class

Deprecated in BEAST2, no `.spec.` counterpart — leave the attribute value unchanged and
insert an XML comment directly before the element:

```xml
<!-- TODO: no beast3 spec class found for ClassName -->
<plate spec="beast.base.some.ClassName" .../>
```

### XML patterns for operator conversion

**`ScaleOperator` split** — BEAST2's `ScaleOperator` is split into two spec classes in BEAST3.
Disambiguate by the attribute that names what is being scaled:

```xml
<!-- parameter mode: parameter= attribute → spec inference ScaleOperator -->
<operator id="kappaScaler" spec="beast.base.spec.inference.operator.ScaleOperator"
    scaleFactor="0.5" weight="1" parameter="@hky.kappa"/>

<!-- tree mode: tree= attribute → spec evolution ScaleTreeOperator -->
<operator id="treeScaler" spec="beast.base.spec.evolution.operator.ScaleTreeOperator"
    scaleFactor="0.5" weight="1" tree="@tree"/>
```

**`Uniform` tree operator — always use the full class path:**

The short name `Uniform` resolves to `beast.base.spec.inference.distribution.Uniform` (a
distribution) because spec packages appear first in the recommended namespace. The tree operator
has no spec twin and must be specified with its full legacy path:

```xml
<!-- WRONG: resolves to the Uniform distribution, not the tree operator -->
<operator id="uniform" spec="Uniform" weight="10" tree="@tree"/>

<!-- CORRECT -->
<operator id="uniform" spec="beast.base.evolution.operator.Uniform" weight="10" tree="@tree"/>
```

---

## Step 4 — Verify

```bash
grep -rn "beast\.base\." src/test/resources/ --include="*.xml" | grep -v "\.spec\."
```

Any hit not in the "do not rename" list is an error — fix it. Remaining issues will also
surface as `mvn test` failures in Step 7.

**Do not rename** (not deprecated, or no spec twin):
`Tree`, `Node`, `TreeInterface`, `TreeParser`, `SiteModelInterface`, `SubstitutionModel`, `BranchRateModel`, `Exchange`, `WilsonBalding`

---

## Log (controller Step 7 report)

- Files migrated: N
- `version="2.8"` applied: N
- Class references updated: N — list each (e.g. `YuleModel, ScaleOperator → .spec.`)
- Complex conversions: list each type applied (e.g. `RealParameter → RealScalarParam<PositiveReal>`, `Prior → LogNormal`, `vector Prior → IID`, `popSize OneOnX → LogNormal(M=3,S=2.5)`, `hky.kappa OneOnX → LogNormal(M=1,S=0.5)`)
- Warnings — non-deprecated BEAST2 classes migrated: N — list each class name (or "none")
- TODOs: N XML comments inserted — list each class name

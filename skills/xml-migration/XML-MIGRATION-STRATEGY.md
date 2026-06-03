---
name: xml-migration-strategy
description: Strategy and pipeline reference for deterministic BEAST2→BEAST3 XML conversion using Python + XSLT. Read this before working on any XML migration step.
metadata:
  type: skill
---

# XML Migration Strategy — Python + XSLT Pipeline

Do not hand-edit XML files. Run the script; review its output.

## Files

| File | Layer | Role |
|---|---|---|
| `deprecated_map.py` | Knowledge | Parses `deprecated_classes.md` → rename map; `resolve_spec()`; `DO_NOT_RENAME`; no lxml |
| `xml_annotator.py` | Decision | lxml pre-pass: stamps `_b3*` attrs encoding all Python-side decisions |
| `reporter.py` | Reporting | `ChangeKind`, `Change`; `render_report`, `save_report`, `print_report` |
| `convert_b2_to_b3.py` | CLI | Orchestrates the modules, applies XSLT, saves report, prints to stdout |
| `b2_to_b3.xsl` | Transform | XSLT 1.0: reads `_b3*` annotations, applies structural changes |
| `../b2deprecated/deprecated_classes.md` | Data | Source for the rename map |

## Commands

**Example XMLs (controller Step 5):**
```bash
python skills/xml-migration/convert_b2_to_b3.py \
    src/test/resources/path/to/file.xml \
    --out src/test/resources/path/to/file.xml \
    --report
```

**FxTemplates / BEAUti templates (controller Step 4):**
```bash
python skills/xml-migration/convert_b2_to_b3.py \
    src/main/resources/path/to/template.xml \
    --out src/main/resources/path/to/template.xml \
    --fxtemplate \
    --report
```

`--fxtemplate` skips the `version="2.8"` and namespace rewrite (BEAUti templates keep `version='2.0'`).

`--overwrite` replaces an existing `*_b3.xml` output; without it the file is skipped.

A per-file Markdown report is always saved to `<input-dir>/reports/<stem>.md`.

**Batch:** `find src/test/resources -name "*.xml" | xargs python skills/xml-migration/convert_b2_to_b3.py --report`

## Namespace Strategy

The converter uses **full FQNs for all renamed (deprecated) classes** and does **not** add
`beast.base.spec.*` packages to the output namespace. This matches the BEAUti3 approach and
avoids ambiguity when both old and spec packages would otherwise resolve the same short name.

The output namespace contains only legacy/core packages that resolve non-deprecated short
names (`MCMC`, `CompoundDistribution`, `Exchange`, `WilsonBalding`, `Tree`, etc.).

Short names that are deprecated (e.g. `HKY`, `ESS`, `ConstantPopulation`, `SubtreeSlide`) are
expanded to their B3 FQN by `resolve_spec()`. The replacement FQN need not be in the spec
package — `SubtreeSlide` → `beast.base.evolution.operator.kernel.BactrianSubtreeSlide` is one
such case. Short names not in `deprecated_classes.md` (e.g. `MCMC`, `Exchange`) pass through
unchanged and resolve via the namespace.

## `_b3*` Annotation Protocol

`xml_annotator.prepass()` stamps temporary attributes onto the lxml tree so the XSLT can
match on Python-computed decisions. Every `_b3*` attribute must be stripped before output —
BEAST3 rejects unknown attributes.

### Attribute inventory

| Attribute | Set by | Read by XSLT | Meaning |
|---|---|---|---|
| `_b3version` | `prepass()` on `<beast>` root | T1 | Present → rewrite `version` and `namespace`; absent in `--fxtemplate` mode |
| `_b3spec` | `prepass()` on any element | T2, T5 | Full FQN to write into `spec=` |
| `_b3domain` | `prepass()` on parameter elements | T2 | Domain class: `PositiveReal`, `UnitInterval`, or `Real` |
| `_b3prior_type` | `prepass()` + `annotate_vector_priors()` | T3a–e | Prior variant: `flatten`, `iid`, `oneonx_pop`, `oneonx_kappa`, `oneonx_generic` |
| `_b3vector_x` | `annotate_vector_priors()` | _(internal)_ | Triggers `flatten` → `iid` upgrade when `x=` param is vector-shaped |
| `_b3type` | `prepass()` | T5 | Replacement value for `type=` attribute |
| `_b3class` | `prepass()` | T5 | Replacement value for `class=` attribute |

### How stripping works

Two complementary filters in `b2_to_b3.xsl`:

**Filter 1** — empty template defeats the identity template for any `_b3*` attribute:
```xsl
<xsl:template match="@*[starts-with(name(),'_b3')]"/>
```

**Filter 2** — structural templates that build elements from scratch exclude annotations explicitly:
```xsl
<xsl:apply-templates select="@*[not(starts-with(name(),'_b3'))]"/>
```

Filter 1 covers the identity path; Filter 2 covers explicit `apply-templates` inside T1, T2, T4, T5.

## Transformation Rules

### T1 — `<beast>` root
Sets `version="2.8"`. Replaces `namespace=` with the legacy/core package list (no spec packages).
Skipped in `--fxtemplate` mode.

### T2 — `<parameter>` (including bare tags with no `spec=`)
`xml_annotator._infer_shape` / `_infer_domain` decide; XSLT applies. Output uses full FQN for spec=.

Shape from `value=` token count:
- 1 token → `scalar`; >1 → `vector`; id contains `freq` or parent is `frequencies` → `simplex`

Domain from `lower=` / `upper=`:
- `lower≥0`, no upper → `PositiveReal`; `lower≥0 upper≤1` → `UnitInterval`; else → `Real`

Output drops `lower=`, `upper=`, `dimension=` (absorbed into `domain=`). Reported as `dropped:` in the rename entry.

### T3 — `<distribution spec="*Prior">` (five variants)
`xml_annotator._prior_type` sets `_b3prior_type`; `collect_prior_changes()` records after
`annotate_vector_priors()` runs to capture any `flatten`→`iid` upgrade.

| `_b3prior_type` | Condition | Output spec= |
|---|---|---|
| `flatten` | scalar inner `<distr>` | inner distribution inlined; Prior wrapper dropped |
| `iid` | vector `x=` param | `beast.base.spec.inference.distribution.IID` |
| `oneonx_pop` | `OneOnX` + `x=` references `popSize` | `beast.base.spec.inference.distribution.LogNormal` M=3 S=2.5 |
| `oneonx_kappa` | `OneOnX` + `x=` references `kappa` | `beast.base.spec.inference.distribution.LogNormal` M=1 S=0.5 |
| `oneonx_generic` | `OneOnX` + unknown param | `beast.base.spec.inference.distribution.LogNormal` M=1 S=1 + WARNING |

### T4 — Operators
- `ScaleOperator`/`BactrianScaleOperator` + `parameter=` → `beast.base.spec.inference.operator.ScaleOperator`
- `ScaleOperator`/`BactrianScaleOperator` + `tree=` → `beast.base.spec.evolution.operator.ScaleTreeOperator`
- `Uniform` + `tree=` → `beast.base.evolution.operator.Uniform` (full legacy path; short `Uniform` resolves to the distribution)

### T5 — Rename (`_b3spec` annotation)
Any element with `_b3spec` stamped has its `spec=`/`type=`/`class=` replaced with the full FQN.
Covers both deprecated full-FQN inputs and deprecated short-name inputs.

### T6 — Identity
Everything not matched by T1–T5 is copied unchanged.

### T7 — `<map>` elements
All `<map name="...">` elements are stripped. These are B2 short-name aliases; B3 uses full FQNs instead.

## `resolve_spec` Decision Table

| Input | Action |
|---|---|
| Already contains `.spec.` | No change |
| Starts with `@` | No change |
| In `DO_NOT_RENAME` | No change (see list below) |
| Short name in `dep_map` | → full B3 FQN from `dep_map` (may or may not be a spec path) |
| Short name not in `dep_map` | No change — resolves via namespace |
| Full FQN with simple name in `dep_map` | → full B3 FQN from `dep_map` |
| Full FQN not in `dep_map` | No change; added to `[todo]` report |

**`DO_NOT_RENAME`**: `Tree`, `Node`, `TreeInterface`, `TreeParser`, `SiteModelInterface`,
`SubstitutionModel`, `BranchRateModel`, `Exchange`, `WilsonBalding`, `TreeStatLogger`

`TreeStatLogger` has a `@Deprecated` annotation but no replacement and no spec twin;
both the hand-converted B3 reference and BEAUti3 use the legacy path unchanged.

## Report Format

The converter always saves `<input-dir>/reports/<stem>.md`. Use `--report` to also print to stdout.

| Kind | Label | Meaning |
|---|---|---|
| INFO | `[info]` | version/namespace update — always correct |
| RENAME | `[rename]` | spec= class renamed to spec FQN |
| WARNING | `[warn] ⚠` | semantic replacement — review required |
| TODO | `[todo] ✗` | no spec twin found — manual action required |

Warnings are emitted for: Prior structural changes, ScaleOperator split,
Uniform tree operator legacy path, TreeLikelihood (ThreadedTreeLikelihood suggestion),
OneOnX→LogNormal generic defaults.

## Limits

Not yet handled; requires manual fix or future XSLT extension:
- `CompoundRealParameter` → `CompoundRealScalarParam`
- FxTemplate `fx:controller` attribute (GUI layer class moves)
- Classes absent from `deprecated_classes.md` (appear as `[todo]` in report)
- Structural changes specific to a package's custom classes

## Example Files Layout

```
skills/xml-migration/examples/
├── testHKY.xml          ← B2 input — run the converter on this
├── testHKY_b3.xml       ← expected B3 output — diff against converter output
├── reports/             ← auto-generated per-run reports (converter writes here)
└── reference/
    └── testHKY_beauti3.xml  ← BEAUti3 output — comparison only, NEVER convert
```

Files in `reference/` are B3 files produced by BEAUti3 or other authoritative sources.
They are used only to compare against converter output and guide rule improvements.
Do not run the converter on them — they are already B3 format and the output would be meaningless.

## Controller Integration

- **Step 4** (FxTemplates): `--fxtemplate` on `src/main/resources/` XMLs
- **Step 5** (Example XMLs): no flag on `src/test/resources/` XMLs
- Append `--report` output to `tmp/b3migration/log/YYYY-MM-DD.md`
- Verify: `grep -rn "beast\.base\." src/test/resources/ --include="*.xml" | grep -v "\.spec\."`

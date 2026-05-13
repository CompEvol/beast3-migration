# BDMM-Prime — what's left

> **Scanned at:** 2026-05-13T18:51:19.031181  
> **Commit:** `ee37e1b` on `beast2.8-migration` — [view on GitHub](https://github.com/tgvaughan/BDMM-Prime/commit/ee37e1b913a94ee10d6bb12bbe79591b8c53864d)  
> **Pom version:** `3.0.0`  
> **Maven Central:** not published as `io.github.tgvaughan:bdmm-prime` (not published (404))  
> **Stage hint:** compile-tested
>
> Migration WIP on `beast2.8-migration` branch (Maven, v3.0.0).

## Summary

- **Java classes:** 4 on spec, 0 mixed, 0 legacy of 88 total
- **Example XMLs:** 0 on spec / 0 on `version="2.8"` / 27 total
- **BEAUti fxtemplates:** 2 clean / 2 use spec / 2 total
- **Input rule:** all Inputs use the right carrier ✅
- **Maven Central:** ❌ not published (not published (404))

## Build & release gaps

- **No `release.sh` or `release/` directory** — used to assemble the CBAN-style ZIP.

## Maven Central

Not yet published as `io.github.tgvaughan:bdmm-prime`. Verify the namespace on central.sonatype.com and run the `release` profile to deploy.

## Java migration

No Java classes flagged as legacy or mixed — all relevant types are on spec or have no parameter involvement. ✅

## Example XMLs pending migration

**Needs `version="2.8"`** (27):

- `validation/mapping/typesOnly_mapping.xml`
- `validation/mapping/typesOnly_MCMC.xml`
- `validation/mapping_rateshifts/mapper.xml`
- `validation/mapping_rateshifts/simulator.xml`
- `validation/operators/changeTimeOperatorTest.xml`
- `validation/operators/scaleAllTest.xml`
- `validation/mapping_unknown_types/mapper.xml`
- `validation/mapping_unknown_types/simulator.xml`
- `validation/smoothing_priors/SkyGrid.xml`
- `validation/smoothing_priors/OUSkyGrid.xml`
- `validation/tip_date_sampling/simulate.xml`
- `validation/tip_date_sampling/infer_tip_dates.xml`
- `validation/trajectories/single_type/single_tree_traj_inference.xml`
- `validation/trajectories/single_type/traj_and_tree_simulator_1type.xml`
- `validation/trajectories/single_type/traj_and_tree_simulator_1type_rhoSA.xml`
- `validation/trajectories/single_type/tree_prior_estimates.xml`
- `validation/trajectories/single_type/traj_and_tree_simulator_1type_SA.xml`
- `validation/trajectories/single_type/traj_inference_1type.xml`
- `validation/trajectories/single_type/traj_inference_1type_rho.xml`
- `validation/trajectories/single_type/traj_and_tree_simulator_1type_rho.xml`
- `validation/trajectories/single_type/traj_inference_1type_rhoSA.xml`
- `validation/trajectories/single_type/traj_inference_1type_SA.xml`
- `validation/trajectories/single_type/epiinf_test.xml`
- `validation/trajectories/multi_type/traj_inference_2types_rhoSA.xml`
- `validation/trajectories/multi_type/traj_and_tree_simulator_2types.xml`
- `validation/trajectories/multi_type/traj_and_tree_simulator_2types_rhoSA.xml`
- `validation/trajectories/multi_type/traj_inference_2types.xml`

## FxTemplates

All BEAUti fxtemplates use spec types with no legacy `parameter.*` declarations. ✅

## Deprecated class references in XMLs

> Every entry below points at a class annotated `@Deprecated` in the spec sources. Replace each `spec=`/`<map>` reference with the suggested spec replacement, or drop it entirely if the surrounding `<prior>` wrapper or `<map>` is now unused. Short-name hits (no dots) resolve to deprecated classes whose simple name is unambiguous within the scanned packages.

**`validation/mapping/typesOnly_mapping.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/mapping/typesOnly_MCMC.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/mapping_rateshifts/mapper.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/mapping_rateshifts/simulator.xml`** (11):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/operators/scaleAllTest.xml`** (1):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `beast.base.evolution.operator.ScaleOperator` | `beast.base.spec.inference.operator.ScaleOperator` |

**`validation/mapping_unknown_types/mapper.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/mapping_unknown_types/simulator.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/smoothing_priors/SkyGrid.xml`** (5):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`validation/smoothing_priors/OUSkyGrid.xml`** (3):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `spec=` | `LogNormalDistributionModel` → `beast.base.inference.distribution.LogNormalDistributionModel` | `beast.base.spec.inference.distribution.LogNormal` |

**`validation/tip_date_sampling/simulate.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/tip_date_sampling/infer_tip_dates.xml`** (6):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/single_type/single_tree_traj_inference.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/single_type/traj_and_tree_simulator_1type.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/single_type/traj_and_tree_simulator_1type_rhoSA.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/single_type/tree_prior_estimates.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/single_type/traj_and_tree_simulator_1type_SA.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/single_type/traj_inference_1type.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/single_type/traj_inference_1type_rho.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/single_type/traj_and_tree_simulator_1type_rho.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/single_type/traj_inference_1type_rhoSA.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/single_type/traj_inference_1type_SA.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/single_type/epiinf_test.xml`** (4):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/multi_type/traj_inference_2types_rhoSA.xml`** (9):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/multi_type/traj_and_tree_simulator_2types.xml`** (8):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/multi_type/traj_and_tree_simulator_2types_rhoSA.xml`** (10):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |

**`validation/trajectories/multi_type/traj_inference_2types.xml`** (7):

| Where | Hit | Replacement |
|---|---|---|
| `spec=` | `RealParameter` → `beast.base.inference.parameter.RealParameter` | `beast.base.spec.inference.parameter.RealScalarParam` |


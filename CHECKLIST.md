# BEAST 3 migration checklist

The table below is generated from `packages.yaml` by the
`beast3-migration-status` Java tool. Re-run with
`mvn -q exec:java` (or `java -jar target/*-all.jar`) after
cloning or updating any tracked package.

Counts:
* **Distributions / Operators / Loggers / CalcNodes / Parameters / StateNodes**
  — Java classes by kind. Cell shows `legacy / total` — classes still
  extending a legacy base (`ParametricDistribution`, `Prior`,
  `RealParameter` / `IntegerParameter` / `BooleanParameter`). `✅ N` means
  none of the `N` classes have a legacy base. The *Java class kinds*
  detail table below splits each kind into `spec / mixed / legacy /
  neutral`; NEUTRAL = no migration target either way (e.g., a subclass
  of a base that has no spec equivalent yet).
* **XMLs** — `spec / v2.8 / total`: `spec` = `<beast>` root has both
  `version="2.8"` and a `beast.base.spec.*` namespace; `v2.8` = root has
  `version="2.8"` regardless of namespace; `total` = every XML with a
  `<beast>` root. Files under `examples/legacy*/` are reported separately
  as `(+N legacy)` and excluded from the totals.

Build/release columns are simple presence checks. Maven Central
shows the latest released version (or `—` if not published).

For per-package migration status ("what's left for `flc`?"), browse the
[`reports/`](reports/README.md) directory — each report includes the
exact local checkout commit it was scanned against.

<!-- BEGIN AUTO -->

_Last regenerated: 2026-05-07T22:00:51.354836+12:00_

## Release & build status

| Package | Stage | Maven Central | Pom version | JPMS | Release | CI | Code | XML | FxT |
|---|---|---|---|:-:|:-:|:-:|:-:|:-:|:-:|
| [beast3](https://github.com/CompEvol/beast3) | Maven Central | [2.8.0-beta5](https://central.sonatype.com/artifact/io.github.compevol/beast3) | 2.8.0-SNAPSHOT | ✅ | ✅ | ✅ | 🟢 | 🔴 | 🟡 |
| [BEASTLabs](https://github.com/BEAST2-Dev/BEASTlabs) | Maven Central | [2.1.0-beta2](https://central.sonatype.com/artifact/io.github.beast2-dev/beast-labs) | 2.1.0-SNAPSHOT | ✅ | ✅ | ✅ | 🔴 | 🔴 | 🔴 |
| [beast-classic](https://github.com/BEAST2-Dev/beast-classic) | Maven Central | [1.7.0-beta1](https://central.sonatype.com/artifact/io.github.beast2-dev/beast-classic) | 1.7.0-SNAPSHOT | ✅ | ✅ | ✅ | 🔴 | 🔴 | 🔴 |
| [CoupledMCMC](https://github.com/CompEvol/CoupledMCMC) | Maven Central | [1.3.0-beta1](https://central.sonatype.com/artifact/io.github.compevol/coupled-mcmc) | 1.3.0-beta1 | ✅ | ✅ | ✅ | 🟢 | 🔴 | 🔴 |
| [flc](https://github.com/4ment/flc) | Maven Central | [1.3.0-beta1](https://central.sonatype.com/artifact/io.github.4ment/flc) | 1.3.0-beta1 | ✅ | ✅ | ✅ | 🟢 | 🟢 | — |
| [Mascot](https://github.com/CompEvol/Mascot) | Maven Central | [3.1.0-beta1](https://central.sonatype.com/artifact/io.github.compevol/mascot) | 3.1.0-beta1 | ✅ | ✅ | ✅ | 🔴 | 🔴 | 🔴 |
| [morph-models](https://github.com/CompEvol/morph-models) | Maven Central | [1.3.0-beta3](https://central.sonatype.com/artifact/io.github.compevol/morph-models) | 1.3.0-beta3 | ✅ | ✅ | ✅ | 🟢 | 🟢 | 🟡 |
| [sampled-ancestors](https://github.com/CompEvol/sampled-ancestors) | Maven Central | [2.3.0-beta1](https://central.sonatype.com/artifact/io.github.compevol/sampled-ancestors) | 2.3.0-beta1 | ✅ | ✅ | ✅ | 🔴 | 🔴 | 🟢 |
| [MutableAlignment](https://github.com/rbouckaert/MutableAlignment) | compile-tested | — | — | ❌ | ❌ | ❌ | — | 🔴 | — |
| [ORC](https://github.com/jordandouglas/ORC) | compile-tested | — | 1.3.0-SNAPSHOT | ✅ | ✅ | ❌ | 🟡 | 🟡 | 🟢 |

Traffic-light columns: 🔴 any legacy lineage / non-migrated content, 🟡 no legacy but some unnecessarily-concrete Inputs (Code) or stray legacy `parameter.*` declarations (XML / FxT), 🟢 clean, — = no data.

## Migration progress (Java + XML)

| Package | Distrs | Ops | Loggers | CalcNodes | Params | StateNodes | XMLs | FxTemplates | Input rule |
|---|---|---|---|---|---|---|---|---|---|
| beast3 | ✅ 70 | ✅ 47 | ✅ 16 | ✅ 45 | ✅ 5 | ✅ 23 | 0 / 0 / 78 (+81 legacy) | 6 / 10 / 10 | ✅ |
| BEASTLabs | 5 / 22 | ✅ 21 | 1 / 11 | 7 / 10 | 2 / 2 | ✅ 8 | 0 / 0 / 20 | 0 / 0 / 3 | 16 / 30 |
| beast-classic | ✅ 9 | ✅ 11 | 4 / 11 | 5 / 14 | ✅ 1 | ✅ 2 | 0 / 0 / 10 | 0 / 0 / 5 | 8 / 16 |
| CoupledMCMC | — | — | ✅ 4 | — | — | — | 0 / 0 / 1 | 0 / 0 / 1 | ✅ |
| flc | — | — | — | ✅ 10 | — | — | 2 / 2 / 2 | — | ✅ |
| Mascot | ✅ 11 | ✅ 6 | 1 / 25 | ✅ 18 | — | ✅ 2 | 1 / 1 / 4 | 0 / 0 / 5 | 23 / 45 |
| morph-models | — | — | — | ✅ 4 | — | ✅ 1 | 2 / 2 / 2 (+4 legacy) | 0 / 1 / 1 | ✅ |
| sampled-ancestors | 2 / 6 | 1 / 13 | ✅ 2 | ✅ 5 | — | ✅ 2 | 0 / 0 / 3 | 1 / 1 / 1 | ✅ |
| MutableAlignment | — | — | — | — | — | — | 0 / 0 / 2 | — | ✅ |
| ORC | ✅ 1 | ✅ 52 | ✅ 1 | — | — | — | 0 / 1 / 1 | 1 / 1 / 1 | 1 / 2 |

Legend: ✅ = clean, ❌ = missing, `legacy / total` = classes still on a legacy base, `—` = no data.
FxTemplates show `clean / spec / total` — `clean` = uses spec types and no legacy `parameter.RealParameter`-style attrs; `spec` = body references `beast.base.spec.*` at all.
Input rule shows `classes / violations`: classes with at least one Input declared too concretely / total violating Inputs. Concrete spec params (RealScalarParam, …) belong only on Operators; Distributions/CalcNodes/etc. should declare the interface (RealScalar, RealVector, …). 0 = ✅.

## Java class kinds (with mixed/legacy breakdown)

Per package, for each kind: `spec / mixed / legacy / neutral · total`.
`mixed` classes import both spec and legacy types — usually mid-migration.

| Package | Distributions | Operators | Loggers | CalcNodes | Parameters | StateNodes |
|---|---|---|---|---|---|---|
| beast3 | 56 / 0 / 0 / 14 · 70 | 1 / 0 / 0 / 46 · 47 | 5 / 0 / 0 / 11 · 16 | 3 / 0 / 0 / 42 · 45 | 4 / 0 / 0 / 1 · 5 | 6 / 0 / 0 / 17 · 23 |
| BEASTLabs | 1 / 0 / 5 / 16 · 22 | 0 / 0 / 0 / 21 · 21 | 0 / 0 / 1 / 10 · 11 | 0 / 0 / 7 / 3 · 10 | 0 / 0 / 2 / 0 · 2 | 0 / 0 / 0 / 8 · 8 |
| beast-classic | 0 / 0 / 0 / 9 · 9 | 1 / 0 / 0 / 10 · 11 | 2 / 0 / 4 / 5 · 11 | 3 / 0 / 5 / 6 · 14 | 1 / 0 / 0 / 0 · 1 | 0 / 0 / 0 / 2 · 2 |
| CoupledMCMC | — | — | 0 / 0 / 0 / 4 · 4 | — | — | — |
| flc | — | — | — | 8 / 0 / 0 / 2 · 10 | — | — |
| Mascot | 0 / 0 / 0 / 11 · 11 | 0 / 0 / 0 / 6 · 6 | 0 / 0 / 1 / 24 · 25 | 4 / 0 / 0 / 14 · 18 | — | 0 / 0 / 0 / 2 · 2 |
| morph-models | — | — | — | 4 / 0 / 0 / 0 · 4 | — | 0 / 0 / 0 / 1 · 1 |
| sampled-ancestors | 0 / 0 / 2 / 4 · 6 | 0 / 0 / 1 / 12 · 13 | 0 / 0 / 0 / 2 · 2 | 0 / 0 / 0 / 5 · 5 | — | 0 / 0 / 0 / 2 · 2 |
| MutableAlignment | — | — | — | — | — | — |
| ORC | 1 / 0 / 0 / 0 · 1 | 0 / 0 / 0 / 52 · 52 | 0 / 0 / 0 / 1 · 1 | — | — | — |

## Diagnostics

- **ORC** — Maven Central: not published (404)

<!-- END AUTO -->

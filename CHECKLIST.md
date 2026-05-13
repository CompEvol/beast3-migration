# BEAST 3 migration checklist

Auto-generated from `packages.yaml` by the `beast3-migration-status`
Java tool. Regenerate with `mvn -q exec:java` (or
`java -jar target/*-all.jar`) after cloning or updating a tracked
package. For per-package migration status ("what's left for `flc`?"),
see [`reports/`](reports/README.md). Notes on how to read the columns
are at the bottom of this page.

<!-- BEGIN AUTO -->

_Last regenerated: 2026-05-13T15:28:36.912052+12:00_

## Release & build status

| Package | Stage | Maven Central | Pom version | JPMS | Release | CI | Code | XML | FxT | Dep refs |
|---|---|---|---|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
| [beast3](https://github.com/CompEvol/beast3) | Maven Central | [2.8.0-beta5](https://central.sonatype.com/artifact/io.github.compevol/beast3) | 2.8.0-SNAPSHOT | ✅ | ✅ | ✅ | 🟢 | 🔴 | 🟡 | 637 |
| [BEASTLabs](https://github.com/BEAST2-Dev/BEASTlabs) | Maven Central | [2.1.0-beta2](https://central.sonatype.com/artifact/io.github.beast2-dev/beast-labs) | 2.1.0-SNAPSHOT | ✅ | ✅ | ✅ | 🔴 | 🔴 | 🔴 | 173 |
| [beast-classic](https://github.com/BEAST2-Dev/beast-classic) | Maven Central | [1.7.0-beta1](https://central.sonatype.com/artifact/io.github.beast2-dev/beast-classic) | 1.7.0-SNAPSHOT | ✅ | ✅ | ✅ | 🔴 | 🔴 | 🔴 | 112 |
| [CoupledMCMC](https://github.com/CompEvol/CoupledMCMC) | Maven Central | [1.3.0-beta1](https://central.sonatype.com/artifact/io.github.compevol/coupled-mcmc) | 1.3.0-beta1 | ✅ | ✅ | ✅ | 🟢 | 🟢 | 🟢 | ✅ |
| [flc](https://github.com/4ment/flc) | Maven Central | [1.3.0-beta1](https://central.sonatype.com/artifact/io.github.4ment/flc) | 1.3.0-beta1 | ✅ | ✅ | ✅ | 🟢 | 🟢 | — | ✅ |
| [Mascot](https://github.com/CompEvol/Mascot) | Maven Central | [3.1.0-beta1](https://central.sonatype.com/artifact/io.github.compevol/mascot) | 3.1.0-beta1 | ✅ | ✅ | ✅ | 🔴 | 🔴 | 🔴 | 757 |
| [morph-models](https://github.com/CompEvol/morph-models) | Maven Central | [1.3.0-beta3](https://central.sonatype.com/artifact/io.github.compevol/morph-models) | 1.3.0-beta3 | ✅ | ✅ | ✅ | 🟢 | 🟢 | 🟢 | ✅ |
| [sampled-ancestors](https://github.com/CompEvol/sampled-ancestors) | Maven Central | [2.3.0-beta1](https://central.sonatype.com/artifact/io.github.compevol/sampled-ancestors) | 2.3.0-beta1 | ✅ | ✅ | ✅ | 🟢 | 🟢 | 🟢 | ✅ |
| [MutableAlignment](https://github.com/rbouckaert/MutableAlignment) | compile-tested | — | — | ❌ | ❌ | ❌ | — | 🔴 | — | ✅ |
| [ORC](https://github.com/jordandouglas/ORC) | compile-tested | [1.3.1](https://central.sonatype.com/artifact/io.github.jordandouglas/beast-orc) | 1.3.0-SNAPSHOT | ✅ | ✅ | ❌ | 🟢 | 🟢 | 🟢 | ✅ |
| [bModelTest](https://github.com/BEAST2-Dev/bModelTest) | compile-tested | — | 1.4.0-beta1 | ✅ | ✅ | ❌ | 🟢 | 🟢 | 🟢 | ✅ |
| [obama](https://github.com/rbouckaert/obama) | compile-tested | — | 1.2.0-beta1 | ✅ | ✅ | ❌ | 🔴 | 🔴 | 🟡 | 207 |

Traffic-light columns: 🔴 any legacy lineage / non-migrated content, 🟡 no legacy but some unnecessarily-concrete Inputs (Code), or stray legacy `parameter.*` declarations / references to `@Deprecated` classes (XML / FxT), 🟢 clean, — = no data.
**Dep refs** = number of references to `@Deprecated` classes found in this package's XMLs and fxtemplates (either as `spec=` attributes or `<map>` bodies); also degrades the XML / FxT light to 🟡 when non-zero. 0 = ✅. Per-package reports list each hit and the spec replacement.

## Migration progress (Java + XML)

| Package | Distrs | Ops | Loggers | CalcNodes | Params | StateNodes | XMLs | FxTemplates | Input rule |
|---|---|---|---|---|---|---|---|---|---|
| beast3 | ✅ 38 | ✅ 15 | ✅ 7 | ✅ 33 | ✅ 4 | ✅ 13 | 0 / 0 / 157 (+2 legacy) | 6 / 10 / 10 | ✅ |
| BEASTLabs | 5 / 22 | ✅ 21 | 1 / 11 | 7 / 10 | 2 / 2 | ✅ 8 | 0 / 0 / 20 | 0 / 0 / 3 | 16 / 30 |
| beast-classic | ✅ 9 | ✅ 11 | 4 / 11 | 5 / 14 | ✅ 1 | ✅ 2 | 0 / 0 / 10 | 0 / 1 / 5 | 5 / 9 |
| CoupledMCMC | — | — | ✅ 4 | — | — | — | 1 / 1 / 1 | 1 / 1 / 1 | ✅ |
| flc | — | — | — | ✅ 10 | — | — | 2 / 2 / 2 | — | ✅ |
| Mascot | ✅ 11 | ✅ 6 | 1 / 25 | ✅ 18 | — | ✅ 2 | 1 / 1 / 284 | 0 / 0 / 14 | 13 / 23 |
| morph-models | — | — | — | ✅ 4 | — | ✅ 1 | 2 / 2 / 2 (+4 legacy) | 1 / 1 / 1 | ✅ |
| sampled-ancestors | ✅ 6 | ✅ 13 | ✅ 2 | ✅ 5 | — | ✅ 2 | 3 / 3 / 3 | 1 / 1 / 1 | ✅ |
| MutableAlignment | — | — | — | — | — | — | 0 / 0 / 1 | — | ✅ |
| ORC | ✅ 1 | ✅ 52 | ✅ 1 | — | — | — | 1 / 1 / 1 | 1 / 1 / 1 | ✅ |
| bModelTest | ✅ 3 | ✅ 4 | ✅ 3 | ✅ 3 | — | — | 3 / 3 / 3 | 1 / 1 / 1 | ✅ |
| obama | ✅ 3 | ✅ 2 | ✅ 2 | 11 / 44 | — | — | 0 / 0 / 14 | 0 / 1 / 1 | 8 / 17 |

Legend: ✅ = clean, ❌ = missing, `legacy / total` = classes still on a legacy base, `—` = no data.
FxTemplates show `clean / spec / total` — `clean` = uses spec types and no legacy `parameter.RealParameter`-style attrs; `spec` = body references `beast.base.spec.*` at all.
Input rule shows `classes / violations`: classes with at least one Input declared too concretely / total violating Inputs. Concrete spec params (RealScalarParam, …) belong on Operators (which write the param) and Loggers (which need `getID()`, absent from the pure type interfaces); Distributions/CalcNodes/etc. should declare the interface (RealScalar, RealVector, …). 0 = ✅.

## Java class kinds (with mixed/legacy breakdown)

Per package, for each kind: `spec / mixed / legacy / neutral · total`.
`mixed` classes import both spec and legacy types — usually mid-migration.

| Package | Distributions | Operators | Loggers | CalcNodes | Parameters | StateNodes |
|---|---|---|---|---|---|---|
| beast3 | 38 / 0 / 0 / 0 · 38 | 1 / 0 / 0 / 14 · 15 | 4 / 0 / 0 / 3 · 7 | 3 / 0 / 0 / 30 · 33 | 4 / 0 / 0 / 0 · 4 | 6 / 0 / 0 / 7 · 13 |
| BEASTLabs | 1 / 0 / 5 / 16 · 22 | 0 / 0 / 0 / 21 · 21 | 0 / 0 / 1 / 10 · 11 | 0 / 0 / 7 / 3 · 10 | 0 / 0 / 2 / 0 · 2 | 0 / 0 / 0 / 8 · 8 |
| beast-classic | 0 / 0 / 0 / 9 · 9 | 1 / 0 / 0 / 10 · 11 | 2 / 0 / 4 / 5 · 11 | 3 / 0 / 5 / 6 · 14 | 1 / 0 / 0 / 0 · 1 | 0 / 0 / 0 / 2 · 2 |
| CoupledMCMC | — | — | 0 / 0 / 0 / 4 · 4 | — | — | — |
| flc | — | — | — | 8 / 0 / 0 / 2 · 10 | — | — |
| Mascot | 0 / 0 / 0 / 11 · 11 | 0 / 0 / 0 / 6 · 6 | 0 / 0 / 1 / 24 · 25 | 4 / 0 / 0 / 14 · 18 | — | 0 / 0 / 0 / 2 · 2 |
| morph-models | — | — | — | 4 / 0 / 0 / 0 · 4 | — | 0 / 0 / 0 / 1 · 1 |
| sampled-ancestors | 2 / 0 / 0 / 4 · 6 | 1 / 0 / 0 / 12 · 13 | 0 / 0 / 0 / 2 · 2 | 0 / 0 / 0 / 5 · 5 | — | 0 / 0 / 0 / 2 · 2 |
| MutableAlignment | — | — | — | — | — | — |
| ORC | 1 / 0 / 0 / 0 · 1 | 0 / 0 / 0 / 52 · 52 | 0 / 0 / 0 / 1 · 1 | — | — | — |
| bModelTest | 0 / 0 / 0 / 3 · 3 | 1 / 0 / 0 / 3 · 4 | 2 / 0 / 0 / 1 · 3 | 3 / 0 / 0 / 0 · 3 | — | — |
| obama | 0 / 0 / 0 / 3 · 3 | 0 / 0 / 0 / 2 · 2 | 0 / 0 / 0 / 2 · 2 | 19 / 0 / 11 / 14 · 44 | — | — |

## Diagnostics

- **bModelTest** — Maven Central: not published (404)
- **obama** — Maven Central: not published (404)

<!-- END AUTO -->

## Notes on how to read this

Most cells have an inline legend right below their table. The points
below cover what those legends don't.

- **Java class counts.** Each kind cell shows `legacy / total` —
  classes still extending a legacy base (`ParametricDistribution`,
  `Prior`, `RealParameter` / `IntegerParameter` / `BooleanParameter`).
  `✅ N` means none of the `N` classes have a legacy base. The *Java
  class kinds* detail table splits each kind into
  `spec / mixed / legacy / neutral`; **NEUTRAL** = no migration target
  either way (e.g., a subclass of a base that has no spec equivalent
  yet).
- **XML counts** — `spec / v2.8 / total`: `spec` = `<beast>` root has
  both `version="2.8"` and a `beast.base.spec.*` namespace; `v2.8` =
  root has `version="2.8"` regardless of namespace; `total` = every
  XML with a `<beast>` root. Files under `examples/legacy*/` are
  reported separately as `(+N legacy)` and excluded from the totals.
- **Build / release columns** are simple presence checks. The Maven
  Central column shows the latest released version, or `—` if not
  published.

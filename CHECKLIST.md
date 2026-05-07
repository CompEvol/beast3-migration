# BEAST 3 migration checklist

The table below is generated from `packages.yaml` by the
`beast3-migration-status` Java tool. Re-run with
`mvn -q exec:java` (or `java -jar target/*-all.jar`) after
cloning or updating any tracked package.

Counts:
* **Distributions / Operators / Loggers / CalcNodes / Parameters / StateNodes**
  — `migrated / total` Java classes by kind. *Migrated* means the class
  either imports from `beast.base.spec.*` or extends a spec base type and
  doesn't simultaneously import a legacy parameter type. The
  *Java class kinds* table below splits `migrated` into spec / mixed /
  legacy / neutral so you can see where each kind sits mid-migration.
* **XMLs** — `spec / v2.8 / total`: `spec` = `<beast>` root has both
  `version="2.8"` and a `beast.base.spec.*` namespace; `v2.8` = root has
  `version="2.8"` regardless of namespace; `total` = every XML with a
  `<beast>` root. Files under `examples/legacy*/` are reported separately
  as `(+N legacy)` and excluded from the totals.

Build/release columns are simple presence checks. Maven Central
shows the latest released version (or `—` if not published).

For a per-package punch list ("what's left for `flc`?"), browse the
[`reports/`](reports/README.md) directory — each report includes the
exact local checkout commit it was scanned against.

<!-- BEGIN AUTO -->

_Last regenerated: 2026-05-07T20:16:33.966652+12:00_

## Release & build status

| Package | Stage | Maven Central | Pom version | JPMS | Release | CI |
|---|---|---|---|:-:|:-:|:-:|
| [beast3](https://github.com/CompEvol/beast3) | Maven Central | [2.8.0-beta5](https://central.sonatype.com/artifact/io.github.compevol/beast3) | 2.8.0-SNAPSHOT | ✅ | ✅ | ✅ |
| [BEASTLabs](https://github.com/BEAST2-Dev/BEASTlabs) | Maven Central | [2.1.0-beta2](https://central.sonatype.com/artifact/io.github.beast2-dev/beast-labs) | 2.1.0-SNAPSHOT | ✅ | ✅ | ✅ |
| [beast-classic](https://github.com/BEAST2-Dev/beast-classic) | Maven Central | [1.7.0-beta1](https://central.sonatype.com/artifact/io.github.beast2-dev/beast-classic) | 1.7.0-SNAPSHOT | ✅ | ✅ | ✅ |
| [CoupledMCMC](https://github.com/CompEvol/CoupledMCMC) | Maven Central | [1.3.0-beta1](https://central.sonatype.com/artifact/io.github.compevol/coupled-mcmc) | 1.3.0-beta1 | ✅ | ✅ | ✅ |
| [flc](https://github.com/4ment/flc) | Maven Central | [1.3.0-beta1](https://central.sonatype.com/artifact/io.github.4ment/flc) | 1.3.0-beta1 | ✅ | ✅ | ✅ |
| [Mascot](https://github.com/CompEvol/Mascot) | Maven Central | [3.1.0-beta1](https://central.sonatype.com/artifact/io.github.compevol/mascot) | 3.1.0-beta1 | ✅ | ✅ | ✅ |
| [morph-models](https://github.com/CompEvol/morph-models) | Maven Central | [1.3.0-beta3](https://central.sonatype.com/artifact/io.github.compevol/morph-models) | 1.3.0-beta3 | ✅ | ✅ | ✅ |
| [sampled-ancestors](https://github.com/CompEvol/sampled-ancestors) | Maven Central | [2.3.0-beta1](https://central.sonatype.com/artifact/io.github.compevol/sampled-ancestors) | 2.3.0-beta1 | ✅ | ✅ | ✅ |
| [MutableAlignment](https://github.com/rbouckaert/MutableAlignment) | compile-tested | — | — | ❌ | ❌ | ❌ |
| [ORC](https://github.com/jordandouglas/ORC) | compile-tested | — | 1.3.0-SNAPSHOT | ✅ | ✅ | ❌ |

## Migration progress (Java + XML)

| Package | Distrs | Ops | Loggers | CalcNodes | Params | StateNodes | XMLs | FxTemplates |
|---|---|---|---|---|---|---|---|---|
| beast3 | 54 / 70 | 13 / 47 | 8 / 16 | 13 / 20 | 4 / 5 | 9 / 22 | 0 / 0 / 78 (+81 legacy) | 6 / 10 / 10 |
| BEASTLabs | 6 / 22 | 1 / 21 | 2 / 11 | 5 / 10 | 0 / 2 | 1 / 8 | 0 / 0 / 20 | 0 / 0 / 3 |
| beast-classic | 9 / 9 | 11 / 11 | 9 / 11 | 7 / 14 | 1 / 1 | 0 / 2 | 0 / 0 / 10 | 0 / 0 / 5 |
| CoupledMCMC | — | — | 0 / 3 | — | — | — | 0 / 0 / 1 | 0 / 0 / 1 |
| flc | — | — | — | 6 / 8 | — | — | 2 / 2 / 2 | — |
| Mascot | 1 / 11 | 6 / 6 | 12 / 25 | 11 / 18 | — | 0 / 2 | 1 / 1 / 4 | 0 / 0 / 5 |
| morph-models | — | — | — | 4 / 4 | — | 0 / 1 | 2 / 2 / 2 (+4 legacy) | 0 / 1 / 1 |
| sampled-ancestors | 3 / 6 | 9 / 13 | 0 / 2 | 3 / 5 | — | 0 / 2 | 0 / 0 / 3 | 1 / 1 / 1 |
| MutableAlignment | — | — | — | — | — | — | 0 / 0 / 2 | — |
| ORC | 1 / 1 | 51 / 52 | 1 / 1 | — | — | — | 0 / 1 / 1 | 1 / 1 / 1 |

Legend: ✅ = present, ❌ = missing, `n / m` = `migrated / total`, `—` = no data.
FxTemplates show `clean / spec / total` — `clean` = uses spec types and no legacy `parameter.RealParameter`-style attrs; `spec` = body references `beast.base.spec.*` at all.

## Java class kinds (with mixed/legacy breakdown)

Per package, for each kind: `spec / mixed / legacy / neutral · total`.
`mixed` classes import both spec and legacy types — usually mid-migration.

| Package | Distributions | Operators | Loggers | CalcNodes | Parameters | StateNodes |
|---|---|---|---|---|---|---|
| beast3 | 54 / 15 / 0 / 1 · 70 | 13 / 1 / 22 / 11 · 47 | 8 / 0 / 4 / 4 · 16 | 13 / 1 / 2 / 4 · 20 | 4 / 1 / 0 / 0 · 5 | 9 / 1 / 3 / 9 · 22 |
| BEASTLabs | 6 / 3 / 4 / 9 · 22 | 1 / 0 / 2 / 18 · 21 | 2 / 0 / 1 / 8 · 11 | 5 / 0 / 1 / 4 · 10 | 0 / 0 / 2 / 0 · 2 | 1 / 0 / 3 / 4 · 8 |
| beast-classic | 9 / 0 / 0 / 0 · 9 | 11 / 0 / 0 / 0 · 11 | 9 / 0 / 0 / 2 · 11 | 7 / 0 / 0 / 7 · 14 | 1 / 0 / 0 / 0 · 1 | 0 / 0 / 0 / 2 · 2 |
| CoupledMCMC | — | — | 0 / 0 / 0 / 3 · 3 | — | — | — |
| flc | — | — | — | 6 / 0 / 0 / 2 · 8 | — | — |
| Mascot | 1 / 5 / 0 / 5 · 11 | 6 / 0 / 0 / 0 · 6 | 12 / 0 / 0 / 13 · 25 | 11 / 0 / 0 / 7 · 18 | — | 0 / 0 / 0 / 2 · 2 |
| morph-models | — | — | — | 4 / 0 / 0 / 0 · 4 | — | 0 / 0 / 0 / 1 · 1 |
| sampled-ancestors | 3 / 1 / 1 / 1 · 6 | 9 / 0 / 0 / 4 · 13 | 0 / 0 / 0 / 2 · 2 | 3 / 0 / 0 / 2 · 5 | — | 0 / 0 / 0 / 2 · 2 |
| MutableAlignment | — | — | — | — | — | — |
| ORC | 1 / 0 / 0 / 0 · 1 | 51 / 1 / 0 / 0 · 52 | 1 / 0 / 0 / 0 · 1 | — | — | — |

## Diagnostics

- **ORC** — Maven Central: not published (404)

<!-- END AUTO -->

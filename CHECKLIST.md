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

_Last regenerated: 2026-05-07T19:54:22.371551+12:00_

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

| Package | Distrs | Ops | Loggers | CalcNodes | Params | StateNodes | XMLs |
|---|---|---|---|---|---|---|---|
| beast3 | 32 / 59 | 12 / 45 | 8 / 16 | 11 / 17 | 4 / 5 | 6 / 16 | 0 / 0 / 93 (+82 legacy) |
| BEASTLabs | 6 / 20 | 1 / 16 | 2 / 11 | 5 / 10 | 0 / 2 | 1 / 7 | 0 / 0 / 26 |
| beast-classic | 6 / 6 | 11 / 11 | 4 / 7 | 6 / 13 | 1 / 1 | 0 / 2 | 0 / 0 / 19 |
| CoupledMCMC | — | — | 0 / 2 | — | — | — | 0 / 0 / 3 |
| flc | — | — | — | 3 / 5 | — | — | 2 / 2 / 2 |
| Mascot | 0 / 9 | 6 / 6 | 10 / 21 | 4 / 9 | — | 0 / 1 | 1 / 1 / 14 |
| morph-models | — | — | — | 2 / 2 | — | 0 / 1 | 0 / 2 / 4 (+4 legacy) |
| sampled-ancestors | 3 / 6 | 9 / 13 | 0 / 2 | 0 / 2 | — | 0 / 2 | 0 / 0 / 5 |
| MutableAlignment | — | — | — | — | — | — | 0 / 0 / 2 |
| ORC | 1 / 1 | 3 / 4 | 1 / 1 | — | — | — | 0 / 1 / 2 |

Legend: ✅ = present, ❌ = missing, `n / m` = `migrated / total`, `—` = no data.

## Java class kinds (with mixed/legacy breakdown)

Per package, for each kind: `spec / mixed / legacy / neutral · total`.
`mixed` classes import both spec and legacy types — usually mid-migration.

| Package | Distributions | Operators | Loggers | CalcNodes | Parameters | StateNodes |
|---|---|---|---|---|---|---|
| beast3 | 32 / 1 / 11 / 15 · 59 | 12 / 1 / 21 / 11 · 45 | 8 / 0 / 4 / 4 · 16 | 11 / 0 / 1 / 5 · 17 | 4 / 0 / 1 / 0 · 5 | 6 / 1 / 2 / 7 · 16 |
| BEASTLabs | 6 / 3 / 2 / 9 · 20 | 1 / 0 / 1 / 14 · 16 | 2 / 0 / 1 / 8 · 11 | 5 / 0 / 1 / 4 · 10 | 0 / 0 / 2 / 0 · 2 | 1 / 0 / 2 / 4 · 7 |
| beast-classic | 6 / 0 / 0 / 0 · 6 | 11 / 0 / 0 / 0 · 11 | 4 / 0 / 0 / 3 · 7 | 6 / 0 / 0 / 7 · 13 | 1 / 0 / 0 / 0 · 1 | 0 / 0 / 0 / 2 · 2 |
| CoupledMCMC | — | — | 0 / 0 / 0 / 2 · 2 | — | — | — |
| flc | — | — | — | 3 / 0 / 0 / 2 · 5 | — | — |
| Mascot | 0 / 5 / 0 / 4 · 9 | 6 / 0 / 0 / 0 · 6 | 10 / 0 / 0 / 11 · 21 | 4 / 0 / 0 / 5 · 9 | — | 0 / 0 / 0 / 1 · 1 |
| morph-models | — | — | — | 2 / 0 / 0 / 0 · 2 | — | 0 / 0 / 0 / 1 · 1 |
| sampled-ancestors | 3 / 1 / 1 / 1 · 6 | 9 / 0 / 0 / 4 · 13 | 0 / 0 / 0 / 2 · 2 | 0 / 0 / 0 / 2 · 2 | — | 0 / 0 / 0 / 2 · 2 |
| MutableAlignment | — | — | — | — | — | — |
| ORC | 1 / 0 / 0 / 0 · 1 | 3 / 1 / 0 / 0 · 4 | 1 / 0 / 0 / 0 · 1 | — | — | — |

## Diagnostics

- **ORC** — Maven Central: not published (404)

<!-- END AUTO -->

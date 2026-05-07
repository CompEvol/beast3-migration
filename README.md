# BEAST 3 Migration

Tool and dashboard tracking the BEAST 2 → BEAST 3 migration across the
CompEvol, BEAST2-Dev, and 4ment package ecosystems. A small Java
program scans sibling checkouts of each tracked package and regenerates
a cross-package status table plus per-package punch lists.

## Status

Both files are auto-generated and timestamped — re-run the tool (see
below) to refresh.

- [`CHECKLIST.md`](CHECKLIST.md) — cross-package dashboard: Maven
  Central versions, JPMS / release / CI flags, traffic-light columns
  for code / XML / FxTemplates, and migration counts by Java class
  kind.
- [`reports/`](reports/README.md) — one Markdown file per tracked
  package, with the exact local checkout commit it was scanned against
  and what's left to migrate.

## Running the tool

Requires Java 21 and Maven. The tool reads [`packages.yaml`](packages.yaml)
and expects each tracked package to be checked out as a sibling
directory of this repo (e.g. `../beast3`, `../BEASTLabs`).

```sh
mvn package           # build the shaded jar
mvn -q exec:java      # regenerate CHECKLIST.md and reports/
# or:
java -jar target/*-all.jar
```

## Adding or removing a tracked package

Edit [`packages.yaml`](packages.yaml). Each entry needs at least a
`name`, `path` (local checkout), `github` slug, and `stage`; add
`maven` (`groupId:artifactId`) once the package is published. The file
header documents every field.

## References

- **Migration guide** — porting an existing package:
  https://github.com/CompEvol/beast3/blob/master/scripts/migration-guide.md
- **Package skeleton** — starting a new BEAST 3 package:
  https://github.com/CompEvol/beast-package-skeleton
- **CBAN** — community package index, used to gauge which BEAST 2
  packages still need migration work:
  https://compevol.github.io/CBAN/
- **Maven Central namespaces** — released artifacts:
  - CompEvol: https://central.sonatype.com/namespace/io.github.compevol
  - Beast2-Dev: https://central.sonatype.com/namespace/io.github.beast2-dev
  - 4ment / FLC: https://central.sonatype.com/search?q=io.github.4ment
- **GitHub Packages** — pre-release snapshots from CompEvol CI:
  https://github.com/orgs/CompEvol/packages

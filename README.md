# BEAST 3 Migration Progress

## Maven releases

The jar files for the following packages are available on Maven Central now.
Full migration may still require updates to tests, example XMLs, or BEAUti templates.

- beast3
- BEASTLabs
- beast-classic
- CoupledMCMC (https://github.com/CompEvol)
- flc
- Mascot (https://github.com/CompEvol)
- morph-models
- sampled-ancestors

Maven Central namespaces:

- CompEvol: https://central.sonatype.com/namespace/io.github.compevol
- Beast2-Dev: https://central.sonatype.com/namespace/io.github.beast2-dev
- FLC: https://central.sonatype.com/search?q=io.github.4ment

## Main branch compiles

**Note:** these packages are only compile-tested, using `mvn clean install -DskipTests`.
Full migration may still require updates to tests, example XMLs, or BEAUti templates.

- MutableAlignment
- ORC

## TODO

If the pull request or branch isn't merged into the main branch, the package still counts as TODO.

https://compevol.github.io/CBAN/

## Guide

- Migration guide: https://github.com/CompEvol/beast3/blob/master/scripts/migration-guide.md
- BEAST 3 Package Skeleton: https://github.com/CompEvol/beast-package-skeleton

## Snapshot releases on GitHub

https://github.com/orgs/CompEvol/packages

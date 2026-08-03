---
name: lphybeast
description: Project-specific facts about LPhyBeast that override or don't fit the generic beast3-migration skills — consult before applying beast3-release-packaging.md, maven-setup.md, or module-info.md to the LPhyBeast repo.
metadata:
  type: skill
---

LPhyBeast (`io.github.linguaphylo:lphybeast-root`) is a Maven multi-module reactor: a core
translator (`lphybeast`) plus per-integration extension modules (`lphybeast-ssm`,
`lphybeast-mascot`, etc). It consumes several BEAST3-migrated packages (substmodels, mascot,
ORC, ...) as ordinary Maven dependencies, but it is a distinct kind of project from those
packages — read the facts below before reusing the other skills here against it.

## Facts

1. **As of 1.4.0, `lphybeast` (and its extension modules) is not a CBAN-installable BEAST2/3
   package** — it's not resolved/installed via BEAST2's `PackageManager` as an end-user
   package, and it doesn't need a `src/assembly/beast-package.xml`-style BEAST package ZIP.
   `BEAST3-MIGRATION.md` design principle 1 states LPhyBeast is a "standalone app, not a BEAST
   package" — it embeds `beast-pkgmgmt` as a *library* to resolve/install *other* beast3
   packages from CBAN or Maven at runtime (`lphybeast install/list/remove`), rather than being
   installed itself as one. Do not apply `beast3-release-packaging.md`'s package-ZIP guidance
   to this repo without first confirming the current distribution model — it doesn't match the
   single-package assumption that skill (and `maven-setup.md`) is built around.

2. **`version.xml` is still required in every module — do not delete it.** Despite fact 1,
   each module (`lphybeast`, `lphybeast-ssm`, `-flc`, `-mascot`, `-mc3`, `-sa`,
   `-mm`, `-orc`, `-ext-dist`) ships and actively needs its own `version.xml`. It's not a CBAN
   distribution artifact here — it's the manifest `LPhyBEASTLoader.loadServicesForTest()` /
   `addBEAST2Services()` feeds to `BEASTClassLoader.addServices(vf)` to register SPI services
   (`LPhyBEASTMapping`, `MCMCStrategy`, `ClockOperatorContributor`, `ValueHandler`, ...) for
   *test* and *IDE* runs. `lphybeast/src/test/java/lphybeast/TestUtils.java` and test classes
   in `lphybeast-ssm`, `-flc`, `-mm`, `-sa`, `-mascot` all call
   `loadServicesForTest(coreModuleDir)`, which throws `IllegalArgumentException` if
   `<coreModuleDir>/version.xml` is missing. `BEAST3-MIGRATION.md` Phase 0 lists "Remove all
   `version.xml` files" as a planned step, but that's superseded by what the code actually
   does now — treat the doc as historical intent, not current instruction, on this point.
   (Production runtime — `LPhyBeastMain`/`LPhyBeastCMD` — doesn't need it: their
   `-vf`/`versionFiles` option defaults to `null` and is opt-in only. It's specifically the
   test harness that hard-requires it.)

3. **Extension discovery has two paths that coexist.** Production/dynamic: per
   `BEAST3-MIGRATION.md`, at startup `beast-pkgmgmt` scans *installed* beast3 packages and
   creates a module layer per package; the SPI system (`lphybeast.spi.LPhyBEASTMapping` —
   renamed from the doc's `LPhyBEASTExt`/`LPhyBEASTExtImpl`, see git history) discovers
   whichever mapper implementations are actually available, keyed off what's installed. Test
   time: the `version.xml`-driven path in fact 2 registers each module's own services
   explicitly, independent of anything being "installed." Both are real and both matter —
   don't assume one supersedes the other.

4. **Distribution packaging is an explicitly open question, not a settled decision.**
   `BEAST3-MIGRATION.md` open question 5 asks outright: "How will LPhyBeast be distributed to
   end users? Fat JAR? Platform installer? Maven artifact?" — unresolved as of that doc. Don't
   treat the disabled `lphybeast-ext-dist` module, the orphaned `lphybeast-assembly.xml`, or
   any Maven Central publish/CI setup as *the* answer this project has settled on; they're
   leftover/exploratory, not a finished design. Confirm current intent before building more on
   top of any one of them.

5. **The module set has grown beyond the original spec.** `BEAST3-MIGRATION.md`'s "target
   architecture" only lists `lphybeast-sa`, `-mm`, `-mascot`, `-flc` as extensions, but the
   current root `pom.xml` also actively builds `-ssm`, `-orc`, `-mc3` (with `-bdtree` excluded
   and `-feast`/the former `-ma` gone entirely — see fact 6). Treat the migration doc as a
   historical design record; cross-check `pom.xml`'s `<modules>` for what's actually current.

6. **No SNAPSHOT dependencies in the active build, as of this writing.** All released
   dependencies for the enabled reactor modules are on Maven Central (see root `pom.xml`
   `<dependencyManagement>`). Only `bdtree.version` and `mutable.alignment.version` are still
   `-SNAPSHOT`, and both are dead properties in practice — `lphybeast-bdtree` is commented out
   of `<modules>` and `lphybeast-ma` has been deleted from the repo entirely, specifically
   because their upstream packages (`bdtree`, `mutable-alignment`) haven't cut real releases
   yet. `TestInstruction.md`'s "Prerequisites" section reflects this: a plain `mvn clean install`
   works, no sibling repos need building from source anymore.

7. **`feast` (and `lphybeast-feast`) is gone entirely, not just excluded.** Unlike `bdtree`/`ma`
   (fact 6), which are excluded only because their upstream packages are SNAPSHOT-only,
   `lphybeast-feast` was never actually created in this repo — the module directory doesn't
   exist, and every `feast`/`lphybeast-feast` reference in the root and `lphybeast-launcher`
   `pom.xml`s has been removed (they were previously commented out, now deleted). The
   functionality feast used to provide (`Concatenate`/`Slice` handling, `ExpCalculator`) is
   covered natively by beast3 core's spec API (`beast.base.spec.inference.parameter.VectorElement`).
   `BEAST3-MIGRATION.md` and `MODULARISATION.md` still describe feast's planned role for
   historical context (with an "Update (3 Aug 2026)" note added), but no code or build file in
   this repo depends on it.

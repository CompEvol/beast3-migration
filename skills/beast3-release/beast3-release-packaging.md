---
name: beast3-release-packaging
description: Wire up a BEAST3 package's release plumbing — the GitHub Actions CI & Publish workflow and the maven-assembly-plugin descriptor that builds the installable BEAST package ZIP. Uses BEASTLabs as the canonical example.
metadata:
  type: skill
---

Given a BEAST3 package project root with a working `pom.xml` (see `maven-setup.md`), add or
verify the two files that make a package installable and releasable:

1. `.github/workflows/ci-publish.yml` — builds/tests on every push and PR, publishes to Maven
   Central on a `v*` tag.
2. `src/assembly/beast-package.xml` — the `maven-assembly-plugin` descriptor that produces the
   BEAST package ZIP (`<PKG_NAME>.v<VERSION>.zip`) uploaded to a GitHub Release / CBAN.

The canonical reference for both is `../BEASTLabs`. Copy from there and adapt identifiers
rather than writing from scratch.

---

## Part 1 — `.github/workflows/ci-publish.yml`

Copy `../BEASTLabs/.github/workflows/ci-publish.yml` verbatim — normally no adaptation is
needed, and in particular **never substitute in the branch currently checked out** while
running this skill (e.g. a migration branch like `beast3`). The `branches: [ master ]` /
`pull_request: branches: [ master ]` triggers match the PR's or push's **target** branch,
not the source branch you happen to be working from, so:

- while migrating on a feature/PR branch, the workflow is correctly inert on push (it only
  fires on push to `master`) and already triggers correctly for PRs *from* that branch via
  the `pull_request` block, regardless of the branch's name;
- once merged, it runs on `master` permanently.

There is only one substitution that ever matters: if the repo's actual default branch is
`main` rather than `master` — a fixed, one-time fact about the repo, not something tied to
whatever branch is checked out. Confirm this once (e.g. from the repo's known default branch,
or `git remote show origin | sed -n '/HEAD branch/s/.*: //p'`) and, only if it is `main`,
replace both occurrences:

| Original | Replacement |
|---|---|
| `branches: [ master ]` | `branches: [ main ]` |
| `refs/heads/master` | `refs/heads/main` |

Nothing else in the workflow should change — it has no project-specific identifiers (the
Maven version comes from the tag, not a hardcoded string).

### What the workflow does

| Trigger | Behaviour |
|---|---|
| push to default branch, or PR into it | `mvn verify` only — build and test, no publish |
| push of tag `v*` | Set Maven version from the tag, `mvn verify`, then `mvn deploy -Prelease -DskipTests` to Maven Central |

Two separate `setup-java` steps exist (mutually exclusive via `if:`) because only the
tag-triggered run needs the Central/GPG server credentials wired into `~/.m2/settings.xml`:

```yaml
- name: Set up JDK 25 (Azul Zulu)
  if: "!startsWith(github.ref, 'refs/tags/v')"
  uses: actions/setup-java@v4
  with:
    distribution: zulu
    java-version: '25'
    cache: maven

- name: Set up JDK 25 (Azul Zulu) for Maven Central
  if: startsWith(github.ref, 'refs/tags/v')
  uses: actions/setup-java@v4
  with:
    distribution: zulu
    java-version: '25'
    cache: maven
    server-id: central
    server-username: CENTRAL_USERNAME
    server-password: CENTRAL_TOKEN
    gpg-private-key: ${{ secrets.GPG_PRIVATE_KEY }}
    gpg-passphrase: GPG_PASSPHRASE
```

The release version is derived from the tag name, never hand-edited in `pom.xml`:

```yaml
- name: Set release version from tag
  if: startsWith(github.ref, 'refs/tags/v')
  run: |
    VERSION=${GITHUB_REF_NAME#v}
    mvn versions:set -DnewVersion=$VERSION -DgenerateBackupPoms=false
```

This requires the `release` Maven profile (javadoc + gpg-sign + central-publishing-maven-plugin)
to already exist in `pom.xml` — `maven-setup.md` / the skeleton `pom.xml` provides it.

**Check `org.sonatype.central:central-publishing-maven-plugin`'s version in that profile is
`0.11.0` or higher.** Version `0.6.0` — still present in `../beast-package-skeleton/pom.xml` and
`../BEASTLabs/pom.xml` as of this writing — crashes with `UnrecognizedPropertyException:
Unrecognized field "warnings"` when Sonatype Central's response includes a field the old plugin
doesn't know about; the deploy still uploads successfully before the crash, so the failure only
shows up as a false-negative red CI run (beast3 issue
[CompEvol/beast3#117](https://github.com/CompEvol/beast3/issues/117), fixed upstream in beast3's
own `pom.xml` by bumping to `0.11.0`). Bump the version in the package's `pom.xml` if it's copied
in at `0.6.0` — don't just copy the skeleton/BEASTLabs value verbatim for this one plugin.

### Secrets required (GitHub Actions)

Store these as **org-level** secrets (e.g. on `BEAST2-Dev`) so every repo can share them —
don't duplicate per-repo:

| Secret | Description |
|---|---|
| `GPG_PRIVATE_KEY` | `gpg --armor --export-secret-keys <KEY_ID>` |
| `GPG_PASSPHRASE` | Passphrase for the GPG key |
| `CENTRAL_USERNAME` | Sonatype Central Portal token username |
| `CENTRAL_TOKEN` | Sonatype Central Portal token password |

Generate Sonatype tokens at https://central.sonatype.com/account.

---

## Part 2 — `src/assembly/beast-package.xml`

Copy `../BEASTLabs/src/assembly/beast-package.xml` as the starting point, then adapt the
`groupId:artifactId` include and the resource paths listed below. Structure:

```xml
<assembly xmlns="http://maven.apache.org/ASSEMBLY/2.2.0" ...>
    <id>beast-package</id>
    <formats>
        <format>zip</format>
    </formats>
    <includeBaseDirectory>false</includeBaseDirectory>

    <!-- version.xml at ZIP root -->
    <files>
        <file>
            <source>${project.basedir}/version.xml</source>
            <outputDirectory>/</outputDirectory>
        </file>
    </files>

    <!-- Module JAR only — BEAST core, JavaFX, and their transitive deps are
         provided by the runtime, so exclude everything except this artifact -->
    <dependencySets>
        <dependencySet>
            <outputDirectory>/lib</outputDirectory>
            <useProjectArtifact>true</useProjectArtifact>
            <scope>runtime</scope>
            <includes>
                <include>io.github.<org>:<artifactId></include>
            </includes>
        </dependencySet>
    </dependencySets>

    <fileSets>
        <!-- fxtemplates/ -->
        <fileSet>
            <directory>${project.basedir}/src/main/resources/<pkg.path>/fxtemplates</directory>
            <outputDirectory>/fxtemplates</outputDirectory>
        </fileSet>
        <!-- examples/ -->
        <fileSet>
            <directory>${project.basedir}/src/test/resources/<pkg.path>/examples</directory>
            <outputDirectory>/examples</outputDirectory>
        </fileSet>
    </fileSets>
</assembly>
```

### Key rules

- **`<dependencySets>` includes only the project's own artifact.** BEAST core/FX and
  transitive deps are `<optional>true</optional>` in `pom.xml` and provided by the BEAST
  runtime at install time — bundling them would bloat the ZIP and risk version clashes.
  If the package genuinely needs an extra runtime-only library the BEAST runtime doesn't
  ship (e.g. BEASTLabs bundles `org.openjdk.nashorn:nashorn-core` for its scripting
  engine), add a second `<include>` line for that artifact only — don't widen the scope.
- **Exclude generated or superseded example files.** Only ship XMLs a user should actually
  run. Add `<excludes>` under the `examples/` fileSet for anything that isn't a canonical,
  currently-maintained example:
  ```xml
  <fileSet>
      <directory>${project.basedir}/src/test/resources/<pkg.path>/examples</directory>
      <outputDirectory>/examples</outputDirectory>
      <excludes>
          <exclude>legacy/**</exclude>
          <exclude>reports/**</exclude>
      </excludes>
  </fileSet>
  ```
  `legacy/**` — pre-migration or superseded example XMLs kept for reference/regression
  testing, not meant for end users (see `morph-models` for this convention).
  `reports/**` — check `git status`: if a subdirectory under `examples/` is untracked, it's
  test-run output (e.g. generated `.md`/`.log` reports), not a source example — exclude it
  the same way, whatever it's named.
- **`examples/` source directory varies by project layout** — either
  `${project.basedir}/examples` (project-root examples, e.g. BEASTLabs, morph-models) or
  `${project.basedir}/src/test/resources/<pkg.path>/examples` (Maven-standard test
  resources, e.g. model-selection). Use whichever already holds the example XMLs — don't
  move them just to match BEASTLabs.

### Wiring into `pom.xml`

The skeleton `pom.xml` already includes this (verify it's present, don't duplicate):

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <version>3.7.1</version>
    <configuration>
        <descriptors>
            <descriptor>src/assembly/beast-package.xml</descriptor>
        </descriptors>
        <finalName>${beast.pkg.name}.v${beast.pkg.version}</finalName>
        <appendAssemblyId>false</appendAssemblyId>
    </configuration>
    <executions>
        <execution>
            <id>beast-package</id>
            <phase>package</phase>
            <goals><goal>single</goal></goals>
        </execution>
    </executions>
</plugin>
```

`${beast.pkg.name}` and `${beast.pkg.version}` are `pom.xml` properties matching the
`name`/`version` attributes of `<package>` in `version.xml` — keep all three in sync.

### Verify

```bash
mvn clean package -Dmaven.test.skip=true
unzip -l target/${beast.pkg.name}.v${beast.pkg.version}.zip
```

Confirm the listing contains exactly: `version.xml`, `lib/<artifactId>-<version>.jar`,
`fxtemplates/*.xml`, and `examples/*.xml` — no `legacy/`, no untracked `reports/`, no
transitive BEAST/JavaFX jars under `lib/`.

---

## Guard rails

- Don't bundle BEAST core, BEAST FX, JavaFX, or any dependency the BEAST runtime already
  provides — only the project's own module JAR (plus rare runtime-only extras, explicitly
  justified).
- Don't ship generated or untracked files (`git status` the `examples/` tree before
  finalising `<excludes>`).
- Don't hand-edit the release version in `pom.xml` — the workflow sets it from the tag.
- Keep `beast.pkg.name` / `beast.pkg.version` (`pom.xml`) and `name` / `version`
  (`version.xml`) in sync; the ZIP filename and CBAN entry are both derived from the former.
- `central-publishing-maven-plugin` in the `release` profile must be `>= 0.11.0`, never the
  skeleton/BEASTLabs default of `0.6.0` — see CompEvol/beast3#117 above.

## Log (controller Step 8 report)

- `ci-publish.yml`: `copied verbatim (master)` or `copied and adapted (default branch: main)`
- `beast-package.xml`: `copied` or `updated (excludes added: <list>)`
- `central-publishing-maven-plugin` version: `>= 0.11.0 (OK)` or `bumped from 0.6.0 (CompEvol/beast3#117)`
- `mvn clean package -Dmaven.test.skip=true`: `PASS` or `FAIL — <reason>`
- ZIP contents: confirmed clean, or list of unexpected entries removed

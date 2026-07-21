---
name: test-b3-xml
description: Validate or run a converted BEAST3 XML file. Covers beast -validate for structural checks and $BEAST_ROOT_DIR/bin/beast for full MCMC runs that produce log/tree files.
metadata:
  type: skill
---

Verify that a `*_b3.xml` file produced by the xml-migration converter is accepted by BEAST3.
Use after conversion to catch class-not-found, missing-input, and format errors before committing.

Set this once before running any command:
```bash
BEAST_ROOT_DIR=~/WorkSpace/beast3
```

## Which tool to use

| Goal | Tool |
|---|---|
| Check XML is structurally valid and all classes resolve | `beast -validate` + `check_beast_run.py` — fast, no output files produced |
| Produce log/tree files for downstream statistical analysis or numerical validation | `$BEAST_ROOT_DIR/bin/beast` (full MCMC run) |

**Default to `beast -validate`.** Only run a full MCMC chain when log or tree file output is explicitly needed.

---

## Validation — `beast -validate`

### Step 1 — Run `-validate`

```bash
$BEAST_ROOT_DIR/bin/beast -validate /abs/path/to/file_b3.xml \
    > /tmp/beast3_validate_output.txt 2>&1
```

Use an absolute path (or `$HOME/...`) — `~` is not expanded inside quoted strings.

**Example:**

```bash
$BEAST_ROOT_DIR/bin/beast -validate \
    $HOME/WorkSpace/beast3-migration/skills/xml-migration/examples/testGTR_b3.xml \
    > /tmp/beast3_validate_output.txt 2>&1
```

### Step 2 — Check result

Pass criterion: last non-empty output line contains `"Done!"`.
On failure, prints only the compact BEAST error block (root exception + "Error detected about here" context), capped at 30 lines.

```bash
python3 ~/WorkSpace/beast3-migration/skills/xml-migration/check_beast_run.py \
    /tmp/beast3_validate_output.txt
```

---

## Full MCMC run — produces log/tree files

**Prerequisite:** build BEAST3 once before first use:
```bash
cd $BEAST_ROOT_DIR && mvn clean install -DskipTests
```

### Option 1 — `bin/beast` (recommended)

Works from any directory. Use absolute paths.

```bash
$BEAST_ROOT_DIR/bin/beast /abs/path/to/file_b3.xml
```

Add `-overwrite` to skip the interactive `Y/N` prompt when log files already exist:
```bash
$BEAST_ROOT_DIR/bin/beast -overwrite /abs/path/to/file_b3.xml
```

### Option 2 — `mvn exec:exec` (fallback — only if Option 1 not working)

Must be run from inside `$BEAST_ROOT_DIR`. File paths in `-Dbeast.args` are relative to the project root.

```bash
cd $BEAST_ROOT_DIR
mvn -pl beast-fx exec:exec -Dbeast.args="-overwrite /abs/path/to/file_b3.xml"
```

---

## Interpreting failures

| Symptom | Likely cause | Fix |
|---|---|---|
| `ClassNotFoundException` or `ClassCastException` | Class FQN wrong in converted XML | Re-check converter output; look for `[todo]` items in the report |
| `Input 'param' not found` or similar | `x=` attribute not converted to `param=` | Re-run converter; check T3a/T3b output |
| `No such file or directory` for the XML | `~` used inside a quoted string | Use `$HOME` or an absolute path |
| No `Done!` and no structured error | BEAST crashed before printing error | Check full `/tmp/beast3_validate_output.txt` for raw Java output |
| Run blocks waiting for `Y/N` prompt | Log files already exist | Add `-overwrite` |

## Notes

- `-validate` checks XML structure and class wiring without running an MCMC chain — much faster.
- `/tmp/beast3_validate_output.txt` is overwritten each run; rename it if you need to compare multiple runs.
- `bin/beast` is the headless launcher (no JavaFX). Use `bin/beast-fx` if the GUI is needed.

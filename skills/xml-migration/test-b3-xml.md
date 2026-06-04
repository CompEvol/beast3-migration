---
name: test-b3-xml
description: Smoke-test a converted BEAST3 XML file by running it through `mvn exec` in ~/WorkSpace/beast3. Checks chainLength format, executes with a short chainLength=100 override, and confirms successful completion via the "Total calculation time" line. Always restores the working directory.
metadata:
  type: skill
---

Verify that a `*_b3.xml` file produced by the xml-migration converter actually runs in
BEAST3. Use after conversion to catch class-not-found, missing-input, and format errors
before committing. Always uses `~` for home-dir paths — never expand to an absolute path.

---

## Step 1 — Verify chainLength format

The `<run>` element must use the parameterised form so the chain length can be overridden
on the command line. A plain integer (`chainLength="5000000"`) will not accept the `-D`
override and is a sign the file has not been converted yet.

```python
python3 - PATH_TO_B3_XML <<'EOF'
import re, sys
from lxml import etree
root = etree.parse(sys.argv[1]).getroot()
run = next((e for e in root.iter()
            if e.tag == 'run' or 'MCMC' in (e.get('spec') or '')), None)
if run is None:
    sys.exit("FAIL: no <run> element found")
cl = run.get('chainLength', '')
if not re.match(r'^\$\(chainLength=', cl):
    sys.exit(f'FAIL: chainLength="{cl}" — run convert_b2_to_b3.py on the source XML first')
print(f"OK: {cl}")
EOF
```

Stop here if the check fails — the converter will fix it automatically.

---

## Step 2 — Save the current directory and switch to beast3

```bash
PREV_DIR=$(pwd)
cd ~/WorkSpace/beast3
```

---

## Step 3 — Run via Maven exec with a short chain

Use `chainLength=100` for a quick smoke test. Redirect all output to a file — do **not**
pipe to `tail`, because errors appear in the middle of the output, not the end.

```bash
mvn -pl beast-fx exec:exec \
    -Dbeast.args="-overwrite -D chainLength=100 $HOME/path/to/file_b3.xml" \
    > /tmp/beast3_test_output.txt 2>&1
```

Use `$HOME` (not `~`) inside `-Dbeast.args` — the shell expands `~` only at word boundaries,
not inside quoted strings, so `~` would be passed literally to BEAST and cause a file-not-found
error. `$HOME` expands correctly inside double quotes.

**Example:**

```bash
mvn -pl beast-fx exec:exec \
    -Dbeast.args="-overwrite -D chainLength=100 $HOME/WorkSpace/beast3-migration/skills/xml-migration/examples/testGTR_b3.xml" \
    > /tmp/beast3_test_output.txt 2>&1
```

---

## Step 4 — Check result and report concisely

On success, prints only the single "Total calculation time" line.
On failure, skips stack-trace noise and prints only the compact BEAST error block
(root exception + "Error detected about here" XML context), capped at 30 lines.

```bash
python3 ~/WorkSpace/beast3-migration/skills/xml-migration/check_beast_run.py \
    /tmp/beast3_test_output.txt
```

---

## Step 5 — Return to previous directory

Always run this regardless of pass or fail:

```bash
cd "$PREV_DIR"
```

---

## Interpreting failures

| Symptom in output | Likely cause | Fix |
|---|---|---|
| `ClassNotFoundException` or `ClassCastException` | Class FQN wrong in converted XML | Re-check converter output; look for `[todo]` items in the report |
| `Input 'param' not found` or similar | `x=` attribute not converted to `param=` | Re-run converter; check T3a/T3b output |
| `chainLength` flag ignored / run too long | XML still has plain integer format | Step 1 failed — run converter first |
| Command never finishes — stuck waiting | Log file already exists; BEAST waiting for `Y/N/A` prompt | Add `-overwrite` to beast.args (already in the template above) |
| Run produces many output lines | `logEvery` is smaller than `chainLength` | Expected for short test runs — loggers use their original `logEvery` from the XML |
| Maven `exec:exec` fails immediately | BEAST3 not compiled | Run `mvn compile -q` in `~/WorkSpace/beast3` first |
| `No such file or directory` for the XML | `~` used inside quoted string | Use `$HOME` inside `-Dbeast.args` — `~` is not expanded inside quotes |

## Notes

- `-overwrite` tells BEAST to overwrite existing log files without prompting. Without it the run blocks waiting for keyboard input.
- `-D chainLength=100` only works when the XML uses `$(chainLength=...)` format — ensured by the converter (Step 1).
- `/tmp/beast3_test_output.txt` is overwritten each run; rename it if you need to compare multiple runs.

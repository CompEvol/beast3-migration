#!/usr/bin/env python3
"""
run_xml_pipeline.py — Convert every BEAST2 XML in EXAMPLES and smoke-test
each resulting _b3.xml through BEAST3.

Usage:
    python3 run_xml_pipeline.py [--examples PATH]

Options:
    --examples PATH   Override the default examples directory.
                      Default: $BEAST3_DIR/beast-base/src/test/resources/beast.base/examples

Output:
    <script-dir>/TODO.md   — summary table + error details
"""

import argparse
import re
import subprocess
import sys
from pathlib import Path

BEAST3_DIR = Path.home() / "WorkSpace/beast3"
SCRIPT_DIR = Path(__file__).parent
CONVERTER  = SCRIPT_DIR / "convert_b2_to_b3.py"
CHECK      = SCRIPT_DIR / "check_beast_run.py"
OUT_FILE   = Path("/tmp/beast3_test_output.txt")
TODO_MD    = SCRIPT_DIR / "TODO.md"

DEFAULT_EXAMPLES = BEAST3_DIR / "beast-base/src/test/resources/beast.base/examples"

try:
    from lxml import etree
except ImportError:
    sys.exit("ERROR: lxml required — pip install lxml")


def main():
    ap = argparse.ArgumentParser(description="Convert B2 XMLs and smoke-test B3 outputs.")
    ap.add_argument("--examples", type=Path, default=DEFAULT_EXAMPLES,
                    help="Directory containing BEAST2 XML files to convert and test.")
    args = ap.parse_args()

    examples_dir = args.examples
    if not examples_dir.is_dir():
        sys.exit(f"ERROR: examples directory not found: {examples_dir}")

    xmls = sorted(examples_dir.glob("*.xml"))
    print(f"Found {len(xmls)} XMLs in {examples_dir}", flush=True)

    # (xml_name, status, detail)
    # status: OK | FAIL-CONVERT | FAIL-PARSE | FAIL-CHAINLENGTH | FAIL-RUN | SKIP-NO-MCMC
    results = []

    for i, xml in enumerate(xmls, 1):
        print(f"\n[{i}/{len(xmls)}] {xml.name}", flush=True)
        b3_xml = xml.with_name(xml.stem + "_b3.xml")

        # ── Step 1: Convert ───────────────────────────────────────────────────
        conv = subprocess.run(
            ["python3", str(CONVERTER), str(xml), "--overwrite"],
            capture_output=True, text=True,
        )
        if not b3_xml.exists():
            msg = (conv.stderr or conv.stdout).strip()
            print(f"  FAIL-CONVERT: {msg}", flush=True)
            results.append((xml.name, "FAIL-CONVERT", msg))
            continue
        print(f"  converted → {b3_xml.name}", flush=True)

        # ── Step 2: Parse _b3.xml and verify chainLength ──────────────────────
        try:
            root = etree.parse(str(b3_xml)).getroot()
        except Exception as exc:
            msg = str(exc)
            print(f"  FAIL-PARSE: {msg}", flush=True)
            results.append((xml.name, "FAIL-PARSE", msg))
            continue

        run_elem = next(
            (e for e in root.iter()
             if e.tag == "run" or "MCMC" in (e.get("spec") or "")),
            None,
        )

        if run_elem is None:
            print("  SKIP-NO-MCMC: no <run spec=MCMC> element", flush=True)
            results.append((xml.name, "SKIP-NO-MCMC",
                            "no MCMC run element — sequence generator or non-MCMC analysis"))
            continue

        cl = run_elem.get("chainLength", "")
        if not re.match(r"^\$\(chainLength=", cl):
            msg = f'chainLength="{cl}" is not parameterised after conversion'
            print(f"  FAIL-CHAINLENGTH: {msg}", flush=True)
            results.append((xml.name, "FAIL-CHAINLENGTH", msg))
            continue

        # ── Step 3: Run BEAST3 via Maven exec ─────────────────────────────────
        beast_args = f"-overwrite -D chainLength=100 {b3_xml}"
        mvn_cmd = ["mvn", "-pl", "beast-fx", "exec:exec",
                   f"-Dbeast.args={beast_args}"]
        print(f"  running: mvn -pl beast-fx exec:exec ... {b3_xml.name}", flush=True)
        try:
            with OUT_FILE.open("w") as fout:
                subprocess.run(
                    mvn_cmd, stdout=fout, stderr=subprocess.STDOUT,
                    cwd=str(BEAST3_DIR), timeout=180,
                )
        except subprocess.TimeoutExpired:
            msg = "Maven exec timed out after 180 s"
            print(f"  FAIL-RUN: {msg}", flush=True)
            results.append((xml.name, "FAIL-RUN", msg))
            continue

        # ── Step 4: Check result ───────────────────────────────────────────────
        check = subprocess.run(
            ["python3", str(CHECK), str(OUT_FILE)],
            capture_output=True, text=True,
        )
        detail = (check.stdout + check.stderr).strip()
        if check.returncode == 0:
            print(f"  OK: {detail}", flush=True)
            results.append((xml.name, "OK", detail))
        else:
            print(f"  FAIL-RUN:\n{detail}", flush=True)
            results.append((xml.name, "FAIL-RUN", detail))

    # ── Write TODO.md ─────────────────────────────────────────────────────────
    ok_count   = sum(1 for _, s, _ in results if s == "OK")
    fail_count = sum(1 for _, s, _ in results if s.startswith("FAIL"))
    skip_count = sum(1 for _, s, _ in results if s.startswith("SKIP"))

    lines = ["# XML Migration Smoke-Test Results\n"]
    lines.append(f"Tested {len(results)} XMLs from `{examples_dir.relative_to(Path.home())}`\n")

    lines.append("## Summary\n")
    lines.append("| # | XML | Result |")
    lines.append("|---|-----|--------|")
    for i, (name, status, _) in enumerate(results, 1):
        icon = "✓" if status == "OK" else ("—" if status.startswith("SKIP") else "✗")
        lines.append(f"| {i} | `{name}` | {icon} {status} |")
    lines.append("")

    failures = [(i, name, status, detail)
                for i, (name, status, detail) in enumerate(results, 1)
                if status.startswith("FAIL")]
    if failures:
        lines.append("## Errors\n")
        for idx, name, status, detail in failures:
            lines.append(f"### {idx}. `{name}` — {status}\n")
            lines.append("```")
            lines.append(detail)
            lines.append("```\n")

    skips = [(i, name, detail)
             for i, (name, status, detail) in enumerate(results, 1)
             if status.startswith("SKIP")]
    if skips:
        lines.append("## Skipped (no MCMC run)\n")
        for idx, name, detail in skips:
            lines.append(f"- **{idx}. `{name}`** — {detail}")
        lines.append("")

    TODO_MD.write_text("\n".join(lines) + "\n")
    print(f"\n{'='*60}")
    print(f"Done: {ok_count} OK, {fail_count} FAIL, {skip_count} SKIP")
    print(f"TODO.md → {TODO_MD}")


if __name__ == "__main__":
    main()

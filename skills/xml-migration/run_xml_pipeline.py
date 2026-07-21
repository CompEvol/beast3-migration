#!/usr/bin/env python3
"""
run_xml_pipeline.py — Convert every BEAST2 XML in EXAMPLES and validate
each resulting _b3.xml through BEAST3 using `beast -validate`.

Use this script only when you need log/tree file output for downstream
analysis or statistical validation. For a quick structural check, use
`beast -validate` directly (or via check_beast_run.py).

Usage:
    python3 run_xml_pipeline.py [--examples PATH]

Options:
    --examples PATH   Override the default examples directory.
                      Default: $BEAST3_DIR/beast-base/src/test/resources/beast.base/examples

Output:
    <script-dir>/TODO.md   — summary table + error details
"""

import argparse
import subprocess
import sys
from pathlib import Path

BEAST3_DIR = Path.home() / "WorkSpace/beast3"
SCRIPT_DIR = Path(__file__).parent
CONVERTER  = SCRIPT_DIR / "convert_b2_to_b3.py"
CHECK      = SCRIPT_DIR / "check_beast_run.py"
BEAST_BIN  = Path.home() / "WorkSpace/beast3/bin/beast"
OUT_FILE   = Path("/tmp/beast3_validate_output.txt")
TODO_MD    = SCRIPT_DIR / "TODO.md"

DEFAULT_EXAMPLES = BEAST3_DIR / "beast-base/src/test/resources/beast.base/examples"

try:
    from lxml import etree
except ImportError:
    sys.exit("ERROR: lxml required — pip install lxml")


def main():
    ap = argparse.ArgumentParser(
        description="Convert B2 XMLs and validate B3 outputs with beast -validate.")
    ap.add_argument("--examples", type=Path, default=DEFAULT_EXAMPLES,
                    help="Directory containing BEAST2 XML files to convert and validate.")
    args = ap.parse_args()

    examples_dir = args.examples
    if not examples_dir.is_dir():
        sys.exit(f"ERROR: examples directory not found: {examples_dir}")

    xmls = sorted(examples_dir.glob("*.xml"))
    print(f"Found {len(xmls)} XMLs in {examples_dir}", flush=True)

    # (xml_name, status, detail)
    # status: OK | FAIL-CONVERT | FAIL-PARSE | FAIL-VALIDATE
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

        # ── Step 2: Parse _b3.xml ─────────────────────────────────────────────
        try:
            etree.parse(str(b3_xml))
        except Exception as exc:
            msg = str(exc)
            print(f"  FAIL-PARSE: {msg}", flush=True)
            results.append((xml.name, "FAIL-PARSE", msg))
            continue

        # ── Step 3: Validate with beast -validate ─────────────────────────────
        print(f"  validating: beast -validate {b3_xml.name}", flush=True)
        with OUT_FILE.open("w") as fout:
            subprocess.run(
                [str(BEAST_BIN), "-validate", str(b3_xml)],
                stdout=fout, stderr=subprocess.STDOUT,
            )

        # ── Step 4: Check result via check_beast_run.py ───────────────────────
        check = subprocess.run(
            ["python3", str(CHECK), str(OUT_FILE)],
            capture_output=True, text=True,
        )
        detail = (check.stdout + check.stderr).strip()
        if check.returncode == 0:
            print(f"  OK: {detail}", flush=True)
            results.append((xml.name, "OK", detail))
        else:
            print(f"  FAIL-VALIDATE:\n{detail}", flush=True)
            results.append((xml.name, "FAIL-VALIDATE", detail))

    # ── Write TODO.md ─────────────────────────────────────────────────────────
    ok_count   = sum(1 for _, s, _ in results if s == "OK")
    fail_count = sum(1 for _, s, _ in results if s.startswith("FAIL"))

    lines = ["# XML Migration Validation Results\n"]
    lines.append(f"Validated {len(results)} XMLs from `{examples_dir.relative_to(Path.home())}`\n")

    lines.append("## Summary\n")
    lines.append("| # | XML | Result |")
    lines.append("|---|-----|--------|")
    for i, (name, status, _) in enumerate(results, 1):
        icon = "✓" if status == "OK" else "✗"
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

    TODO_MD.write_text("\n".join(lines) + "\n")
    print(f"\n{'='*60}")
    print(f"Done: {ok_count} OK, {fail_count} FAIL")
    print(f"TODO.md → {TODO_MD}")


if __name__ == "__main__":
    main()

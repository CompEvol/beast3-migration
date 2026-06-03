#!/usr/bin/env python3
"""
convert_b2_to_b3.py — Deterministic BEAST2 XML → BEAST3 XML converter.

Usage:
    python convert_b2_to_b3.py INPUT.xml [INPUT2.xml ...] [OPTIONS]

Options:
    --out PATH          Output file path (single-input mode only)
    --overwrite         Replace an existing *_b3.xml output file
    --fxtemplate        BEAUti/FxTemplate mode: skip version="2.8" and namespace rewrite
    --report            Also print the Markdown report to stdout
    --deprecated PATH   Path to deprecated_classes.md  (default: auto-located)
    --xsl PATH          Path to b2_to_b3.xsl           (default: alongside this script)

Output files (per input):
    <same-dir>/<stem>_b3.xml          — converted BEAST3 XML
    <same-dir>/reports/<stem>.md      — Markdown migration report (always written)

Pipeline:
    1. deprecated_map.parse_deprecated_md()   → rename map
    2. xml_annotator.prepass()                → stamp _b3* attrs on every element
    3. xml_annotator.annotate_vector_priors() → upgrade flatten → iid where needed
    4. lxml.etree.XSLT(b2_to_b3.xsl)         → structural XML transform
    5. Write XML output and save report

Requires: lxml  (pip install lxml)
Python  : 3.9+
"""

import re
import sys
import argparse
from pathlib import Path

try:
    from lxml import etree
except ImportError:
    sys.exit("ERROR: lxml is required.  Run: pip install lxml")

from deprecated_map import parse_deprecated_md
from reporter import Change, ChangeKind, save_report, print_report
from xml_annotator import prepass, annotate_vector_priors, collect_prior_changes


def convert(
    input_path: Path,
    output_path: Path,
    xsl_path: Path,
    dep_map: dict[str, str],
    fxtemplate: bool,
) -> list[Change]:
    """
    Convert a single BEAST2 XML file to BEAST3 and write the result.

    Returns a list of Change objects for the report.
    Raises on any parse or transform error.
    """
    parser = etree.XMLParser(remove_blank_text=False, remove_comments=False)
    tree = etree.parse(str(input_path), parser)

    changes = prepass(tree, dep_map, fxtemplate)

    root = tree.getroot()
    id_map = {e.get('id'): e for e in root.iter() if e.get('id')}
    annotate_vector_priors(root, id_map)
    # Collect Prior changes after vector-prior upgrade so the report reflects
    # the final _b3prior_type (flatten may have been upgraded to iid).
    changes.extend(collect_prior_changes(root))

    xslt_doc = etree.parse(str(xsl_path))
    result = etree.XSLT(xslt_doc)(tree)

    raw = etree.tostring(result, pretty_print=True,
                         xml_declaration=True, encoding='UTF-8')
    with output_path.open('wb') as f:
        f.write(_postprocess(raw))

    return changes


def _postprocess(xml_bytes: bytes) -> bytes:
    """
    Clean up two XSLT serialisation artefacts:

    1. Blank-line clutter after <map> removal — XSLT strips the element but
       leaves its surrounding whitespace text nodes, producing alternating
       blank and whitespace-only lines. Collapse runs of 3+ such lines to one.

    2. <beast> header readability — lxml serialises all attributes on a single
       line, making the namespace string unreadable at ~400 chars.  Reformat
       so each attribute starts on its own line, aligned under '<beast '.
    """
    text = xml_bytes.decode('utf-8')

    # ── 1. Collapse blank-line runs ──────────────────────────────────────────
    # Match two or more consecutive lines that are blank or whitespace-only,
    # and reduce them to a single blank line.
    text = re.sub(r'\n[ \t]*\n([ \t]*\n)+', '\n\n', text)

    # ── 2. Reformat <beast ...> opening tag ──────────────────────────────────
    def _fmt(m: re.Match) -> str:
        raw = m.group(0)
        # Extract name="value" pairs in source order.
        attrs = re.findall(r'([\w:.-]+)="([^"]*)"', raw)
        if not attrs:
            return raw
        # First attribute sits on the same line as '<beast '.
        indent = ' ' * len('<beast ')   # 7 spaces — aligns subsequent attrs
        lines = [f'<beast {attrs[0][0]}="{attrs[0][1]}"']
        for name, value in attrs[1:]:
            lines.append(f'{indent}{name}="{value}"')
        return '\n'.join(lines) + '>'

    text = re.sub(r'<beast\b[^>]*>', _fmt, text, count=1, flags=re.DOTALL)

    return text.encode('utf-8')


# ---------------------------------------------------------------------------
# CLI
# ---------------------------------------------------------------------------

def main():
    here = Path(__file__).parent

    ap = argparse.ArgumentParser(
        description='Convert BEAST2 XML to BEAST3 XML.',
        formatter_class=argparse.RawDescriptionHelpFormatter,
    )
    ap.add_argument('inputs', nargs='+', type=Path,
                    help='BEAST2 XML file(s) to convert')
    ap.add_argument('--out', type=Path, default=None,
                    help='Output path (single input only; default: <stem>_b3.xml alongside input)')
    ap.add_argument('--overwrite', action='store_true',
                    help='Replace an existing output file instead of skipping it')
    ap.add_argument('--fxtemplate', action='store_true',
                    help='BEAUti/FxTemplate mode: skip version="2.8" and namespace rewrite')
    ap.add_argument('--report', action='store_true',
                    help='Also print the Markdown report to stdout')
    ap.add_argument('--deprecated', type=Path, default=None,
                    help='Path to deprecated_classes.md (default: auto-located)')
    ap.add_argument('--xsl', type=Path, default=here / 'b2_to_b3.xsl',
                    help='Path to b2_to_b3.xsl (default: alongside this script)')
    args = ap.parse_args()

    # --- Validate shared resources ---
    dep_path = args.deprecated or (here.parent / 'b2deprecated' / 'deprecated_classes.md')
    if not dep_path.exists():
        sys.exit(f'ERROR: deprecated_classes.md not found at {dep_path}\n'
                 f'       Use --deprecated PATH to specify its location.')

    if not args.xsl.exists():
        sys.exit(f'ERROR: b2_to_b3.xsl not found at {args.xsl}')

    if args.out and len(args.inputs) > 1:
        sys.exit('ERROR: --out can only be used with a single input file.')

    dep_map = parse_deprecated_md(dep_path)
    all_changes: dict[str, list[Change]] = {}

    for inp in args.inputs:
        if not inp.exists():
            print(f'SKIP: {inp} not found', file=sys.stderr)
            continue

        out = args.out or inp.with_name(inp.stem + '_b3' + inp.suffix)

        # --- Overwrite guard ---
        if out.exists() and not args.overwrite:
            print(
                f'SKIP: {out} already exists. '
                f'Use --overwrite to replace it.',
                file=sys.stderr,
            )
            continue

        # --- Convert ---
        try:
            changes = convert(inp, out, args.xsl, dep_map, args.fxtemplate)
            print(f'OK:   {inp} → {out}', file=sys.stderr)
        except Exception as exc:
            print(f'ERROR: {inp}: {exc}', file=sys.stderr)
            changes = [Change(ChangeKind.TODO, f'conversion failed: {exc}')]

        # --- Save per-file report to <input-dir>/reports/<stem>.md ---
        report_path = save_report(inp, changes)
        print(f'      report → {report_path}', file=sys.stderr)

        all_changes[str(inp)] = changes

    # --- Optionally print full report to stdout ---
    if args.report and all_changes:
        print_report(all_changes)


if __name__ == '__main__':
    main()

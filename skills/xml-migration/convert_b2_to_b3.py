#!/usr/bin/env python3
"""
convert_b2_to_b3.py — Deterministic BEAST2 XML → BEAST3 XML converter.

Usage:
    python convert_b2_to_b3.py INPUT.xml [INPUT2.xml ...] [OPTIONS]

Options:
    --out PATH          Output file path (single-input mode only)
    --overwrite         Replace an existing *_b3.xml output file
    --fxtemplate        BEAUti/FxTemplate mode: bump version="2.8" but skip the namespace rewrite
    --report            Also print the Markdown report to stdout
    --deprecated PATH   Path to deprecated_classes.md  (default: auto-located)
    --xsl PATH          Path to b2_to_b3.xsl           (default: alongside this script)

Output files (per input):
    <same-dir>/<stem>_b3.xml          — converted BEAST3 XML
    <same-dir>/reports/<stem>.md      — Markdown migration report (always written)

Pipeline:
    1. deprecated_map.parse_deprecated_md()   → rename map (first FQN per row)
       deprecated_map.parse_deprecated_alternatives() → rows with >1 replacement
    2. Parse every FxTemplate CDATA fragment (<subtemplate>/<partitiontemplate>)
       and build ONE id map over the document + all fragments, so cross-fragment
       references (e.g. an operator's parameter=) resolve to a typed target
    3. For each CDATA fragment: annotate → XSLT → write back as CDATA
       (its changes are reported with a "[CDATA <tag id>]" prefix)
    4. xml_annotator.prepass()                → stamp _b3* attrs on every element
    5. xml_annotator.annotate_vector_priors() → upgrade flatten → iid where needed
    6. lxml.etree.XSLT(b2_to_b3.xsl)         → structural XML transform
    7. Write XML output and save report

Requires: lxml  (pip install lxml)
Python  : 3.9+
"""

import re
import sys
import argparse
from pathlib import Path
from typing import Optional

try:
    from lxml import etree
except ImportError:
    sys.exit("ERROR: lxml is required.  Run: pip install lxml")

from deprecated_map import parse_deprecated_md, parse_deprecated_alternatives
from reporter import Change, ChangeKind, save_report, print_report
from xml_annotator import (
    prepass, annotate_elements, annotate_vector_priors, collect_prior_changes,
    annotate_int_simplex_params, annotate_simplex_refs,
)

# FxTemplate elements whose text is an embedded BEAST XML fragment held as CDATA.
CDATA_FRAGMENT_TAGS = ('subtemplate', 'partitiontemplate')
_FRAGMENT_ROOT = '_b3fragment'


def convert(
    input_path: Path,
    output_path: Path,
    xsl_path: Path,
    dep_map: dict[str, str],
    fxtemplate: bool,
    alternatives: Optional[dict[str, list[str]]] = None,
) -> list[Change]:
    """
    Convert a single BEAST2 XML file to BEAST3 and write the result.

    Returns a list of Change objects for the report.
    Raises on any parse or transform error.
    """
    # strip_cdata=False: lxml's default (True) silently converts CDATA sections
    # to plain text nodes at parse time, before the XSLT ever runs — this is why
    # a FxTemplate's <subtemplate><![CDATA[...]]></subtemplate> (the embedded
    # runnable-analysis fragment) came out as escaped text (&lt;...&gt;) instead
    # of round-tripping as CDATA. The XSLT itself drops CDATA-ness, so it is
    # restored on the fragment text after the transform (see below).
    parser = etree.XMLParser(remove_blank_text=False, remove_comments=False, strip_cdata=False)
    tree = etree.parse(str(input_path), parser)
    root = tree.getroot()

    xslt = etree.XSLT(etree.parse(str(xsl_path)))
    fragments, frag_changes = _parse_fragments(root, parser)

    # Build id_map before prepass so annotate_int_simplex_params can stamp
    # _b3int_simplex on IntegerParameter elements referenced by DeltaExchangeOperator.
    # It spans the CDATA fragments too: in an FxTemplate a parameter and the
    # operator acting on it often live in different fragments.
    trees = [root] + [frag for _, frag in fragments]
    id_map = {e.get('id'): e for t in trees for e in t.iter() if e.get('id')}
    for t in trees:
        annotate_int_simplex_params(t, id_map)
        annotate_simplex_refs(t, id_map)

    # Convert each CDATA fragment with the same annotate → XSLT steps as the
    # document, then write it back as CDATA for the main pass to copy through.
    for host, frag in fragments:
        label = f'[CDATA <{host.tag}{" id=" + host.get("id") if host.get("id") else ""}>] '
        changes_f = annotate_elements(frag, dep_map, id_map, alternatives)
        annotate_vector_priors(frag, id_map)
        changes_f.extend(collect_prior_changes(frag))
        frag_changes.extend(Change(c.kind, label + c.description) for c in changes_f)
        host.text = etree.CDATA(_serialize_fragment(xslt(etree.ElementTree(frag)).getroot()))

    changes = prepass(tree, dep_map, fxtemplate, id_map, alternatives)
    changes.extend(frag_changes)

    annotate_vector_priors(root, id_map)
    # Collect Prior changes after vector-prior upgrade so the report reflects
    # the final _b3prior_type (flatten may have been upgraded to iid).
    changes.extend(collect_prior_changes(root))

    result = xslt(tree)
    # XSLT drops CDATA-ness; restore it on each fragment's own text only (not
    # on the whitespace tails between a template's <connect> children).
    for host in result.getroot().iter(*CDATA_FRAGMENT_TAGS):
        if host.text and '<' in host.text:
            host.text = etree.CDATA(host.text)

    raw = etree.tostring(result, pretty_print=True,
                         xml_declaration=True, encoding='UTF-8')
    with output_path.open('wb') as f:
        f.write(_postprocess(raw))

    return changes


def _parse_fragments(root: etree._Element, parser: etree.XMLParser):
    """
    Parse the CDATA text of every <subtemplate>/<partitiontemplate> into an
    element tree wrapped in a synthetic <_b3fragment> root (fragments have many
    top-level elements).  Returns ([(host_element, fragment_root)], changes);
    an unparseable fragment is left untouched and reported as a TODO.
    """
    fragments, changes = [], []
    for host in root.iter(*CDATA_FRAGMENT_TAGS):
        text = host.text or ''
        if '<' not in text:
            continue
        try:
            frag = etree.fromstring(f'<{_FRAGMENT_ROOT}>{text}</{_FRAGMENT_ROOT}>', parser)
        except etree.XMLSyntaxError as exc:
            changes.append(Change(
                ChangeKind.TODO,
                f'[CDATA <{host.tag} id={host.get("id")}>] fragment is not well-formed XML '
                f'({exc}) — NOT converted; migrate it by hand',
            ))
            continue
        fragments.append((host, frag))
    return fragments, changes


def _serialize_fragment(frag_root: etree._Element) -> str:
    """Inverse of _parse_fragments: the wrapper's content, whitespace preserved."""
    return (frag_root.text or '') + ''.join(
        etree.tostring(child, encoding='unicode', with_tail=True) for child in frag_root)


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
    alternatives = parse_deprecated_alternatives(dep_path)
    all_changes: dict[str, list[Change]] = {}

    for inp in args.inputs:
        if not inp.exists():
            print(f'SKIP: {inp} not found', file=sys.stderr)
            continue

        # Reject files that already carry the _b3 suffix — they are BEAST3
        # outputs and must not be fed back into the converter.
        if inp.stem.endswith('_b3'):
            print(
                f'SKIP: {inp} already has a _b3 suffix and looks like a '
                f'BEAST3 output file. Pass the original BEAST2 source instead.',
                file=sys.stderr,
            )
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
            changes = convert(inp, out, args.xsl, dep_map, args.fxtemplate, alternatives)
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

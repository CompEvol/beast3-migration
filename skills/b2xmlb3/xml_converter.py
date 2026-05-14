#!/usr/bin/env python3
"""
xml_converter.py — Convert BEAST 2.7 (or earlier) XML to BEAST 2.8/3 format.

Transforms applied to each input file:

  spec= attribute conversion (class mapping)
  - Simple-name spec= where the spec replacement has the same simple name
    (e.g. HKY, SiteModel, TreeLikelihood): simple name is kept unchanged;
    the spec package is added to the namespace and the old deprecated package
    is replaced.
  - Already-FQ spec= (e.g. beast.base.evolution.tree.ClusterTree): replaced
    with the equivalent spec FQ name.
  - Context-dependent (ScaleOperator): resolved to ScaleOperator (parameter=)
    or ScaleTreeOperator (tree=) as appropriate — always written as FQ to
    avoid namespace ambiguity.
  - Bare <parameter> elements and explicit RealParameter / IntegerParameter /
    BooleanParameter: converted to the typed BEAST3 equivalent with domain=
    and lower/upper removed.
  - Prior(dist): restructured automatically — the Prior wrapper is removed and
    the inner distribution gets a param= attribute pointing to the original x=
    parameter.  Prior(OneOnX) specifically maps to LogNormal(M=1.0, S=1.25)
    because OneOnX is deprecated even in beast.base.spec.
  - Operator elements whose simple name is shared with a deprecated
    distribution (e.g. Uniform tree operator): left unchanged.

  Namespace
  - Deprecated packages are replaced by their spec equivalents.
  - Duplicates in the original namespace are removed.
  - Any spec packages for converted classes not already present are appended.

  version attribute: updated to 2.8.

Usage:
    python3 xml_converter.py INPUT [INPUT ...] [-o OUTPUT_DIR] [--deprecated-md PATH]

Defaults:
    output:        same directory as input, with '_b3' suffix before .xml
    deprecated-md: deprecated_classes.md (next to this script)
"""

import argparse
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

SCRIPT_DIR = Path(__file__).parent
sys.path.insert(0, str(SCRIPT_DIR))

from mapping_reader import load_mapping, DEFAULT_DEPRECATED_MD
from param_converter import convert_parameter

BEAST3_VERSION = "2.8"

# Bare BEAST element tags that imply RealParameter when spec= is absent
_BARE_REAL_TAGS = {"parameter"}

# Packages that are fully migrated to spec — replace them in the namespace.
# Packages NOT listed here (e.g. beast.base.evolution.operator,
# beast.base.evolution.tree.coalescent) still contain non-deprecated classes
# and must be kept alongside the spec equivalents.
_PACKAGE_REPLACEMENTS: dict[str, str] = {
    "beast.base.evolution.sitemodel":        "beast.base.spec.evolution.sitemodel",
    "beast.base.evolution.substitutionmodel": "beast.base.spec.evolution.substitutionmodel",
    "beast.base.evolution.likelihood":        "beast.base.spec.evolution.likelihood",
    "beast.base.inference.operator":          "beast.base.spec.inference.operator",
    "beast.base.inference.util":              "beast.base.spec.inference.util",
    "beast.base.inference.distribution":      "beast.base.spec.inference.distribution",
    "beast.base.inference.parameter":         "beast.base.spec.inference.parameter",
    "beast.base.evolution.branchratemodel":   "beast.base.spec.evolution.branchratemodel",
}

# Packages to drop entirely from the namespace (not replaced, just removed).
_PACKAGES_TO_DROP = {
    "beast.base.evolution.alignment",  # data loaded via <data> tag, no simple-name resolution needed
}

# Operator simple names that refer to tree operators, not deprecated distributions.
# These must not be converted even though a deprecated class shares the same name.
_TREE_ONLY_OPERATOR_NAMES = {"Uniform"}

# Classes that require structural XML changes; skipped with a warning.
_SKIP_CLASSES: set[str] = set()  # currently all structural classes are handled automatically

# Context-dependent mappings: {simple_name: [(required_attr, fq_replacement), ...]}
# Entries are checked in order; the first where required_attr is present wins.
# The last entry (required_attr=None) is the fallback.
_CONTEXT_MAPPINGS: dict[str, list[tuple[str | None, str]]] = {
    "ScaleOperator": [
        ("tree",  "beast.base.spec.evolution.operator.ScaleTreeOperator"),
        (None,    "beast.base.spec.inference.operator.ScaleOperator"),
    ],
    "BactrianScaleOperator": [
        ("tree",  "beast.base.spec.evolution.operator.ScaleTreeOperator"),
        (None,    "beast.base.spec.inference.operator.ScaleOperator"),
    ],
}


# ---------------------------------------------------------------------------
# Helpers
# ---------------------------------------------------------------------------

def _simple_name(spec: str) -> str:
    """Return the rightmost component of a simple, partial, or FQ class name."""
    return spec.rsplit(".", 1)[-1]


def _package_of(fq: str) -> str:
    return fq.rsplit(".", 1)[0] if "." in fq else ""


def _is_fq(spec: str) -> bool:
    return "." in spec


def _make_parser() -> ET.XMLParser:
    try:
        return ET.XMLParser(target=ET.TreeBuilder(insert_comments=True))
    except TypeError:
        return ET.XMLParser()


def _update_namespace(old_ns: str, add_packages: set[str]) -> str:
    """
    Build the new namespace string:
      1. Deduplicate the original list.
      2. Drop packages in _PACKAGES_TO_DROP.
      3. Replace fully-migrated packages with their spec equivalents (_PACKAGE_REPLACEMENTS).
      4. Append any spec packages for converted classes not already present.
    """
    parts = [p.strip() for p in old_ns.split(":") if p.strip()]
    seen: set[str] = set()
    result: list[str] = []

    for p in parts:
        if p in seen:
            continue  # drop duplicate
        seen.add(p)
        if p in _PACKAGES_TO_DROP:
            continue
        if p in _PACKAGE_REPLACEMENTS:
            new_p = _PACKAGE_REPLACEMENTS[p]
            if new_p not in seen:
                result.append(new_p)
                seen.add(new_p)
            # old package is dropped (fully replaced by spec equivalent)
        else:
            result.append(p)

    for p in sorted(add_packages):
        if p not in seen:
            result.append(p)
            seen.add(p)

    return ":".join(result)


# ---------------------------------------------------------------------------
# Prior conversion (structural change)
# ---------------------------------------------------------------------------

def _convert_prior(
    elem: ET.Element,
    notes: list[str],
    spec_packages: set[str],
    removed_ids: set[int],
) -> None:
    """
    Restructure a Prior element in-place, removing the Prior wrapper:

      Beast2: <distribution spec="Prior" x="@param"><distr spec="Dist" .../></distribution>
      Beast3: <distribution spec="Dist" param="@param">...</distribution>

    Special case: Prior(OneOnX) → LogNormal(M=1.0, S=1.25) because OneOnX
    is deprecated even in beast.base.spec (it lives in beast2vs1 test shims).
    LogNormal with these defaults is the conventional weakly-informative
    replacement for a scale-invariant prior on positive quantities.
    """
    orig_spec = elem.get("spec", "Prior")
    x_attr = elem.get("x")
    distr_child = next((c for c in elem if c.tag == "distr"), None)

    if x_attr is None or distr_child is None:
        notes.append(
            f"  WARNING: {orig_spec} skipped — missing x= or <distr> child; "
            f"requires manual restructuring"
        )
        return

    inner_spec = distr_child.get("spec", "")
    inner_simple = _simple_name(inner_spec) if inner_spec else ""

    if inner_simple == "OneOnX":
        # OneOnX is deprecated (beast.base.spec.beast2vs1 test shim only).
        # Replace Prior(OneOnX) with LogNormal as a weakly-informative default.
        elem.set("spec", "LogNormal")
        del elem.attrib["x"]
        elem.set("param", x_attr)
        for sub in distr_child.iter():
            removed_ids.add(id(sub))
        removed_ids.add(id(distr_child))
        elem.remove(distr_child)
        m_elem = ET.SubElement(elem, "M")
        m_elem.set("spec", "RealScalarParam")
        m_elem.set("domain", "Real")
        m_elem.set("value", "1.0")
        s_elem = ET.SubElement(elem, "S")
        s_elem.set("spec", "RealScalarParam")
        s_elem.set("domain", "PositiveReal")
        s_elem.set("value", "1.25")
        notes.append(
            f"  Prior(OneOnX) → LogNormal(M=1.0, S=1.25) param={x_attr}"
            f"  [OneOnX deprecated; review prior choice]"
        )
        spec_packages.add("beast.base.spec.inference.distribution")
        spec_packages.add("beast.base.spec.inference.parameter")
    else:
        # General case: lift inner distribution up, x= becomes param=.
        inner_new_simple = _simple_name(inner_spec) if inner_spec else ""
        elem.set("spec", inner_new_simple)
        del elem.attrib["x"]
        elem.set("param", x_attr)
        removed_ids.add(id(distr_child))
        elem.remove(distr_child)
        for child in list(distr_child):
            elem.append(child)
        notes.append(f"  Prior({inner_spec}) → {inner_new_simple}(param={x_attr})")


# ---------------------------------------------------------------------------
# Tree conversion
# ---------------------------------------------------------------------------

def _convert_tree(
    root: ET.Element,
    mapping: dict[str, dict],
) -> tuple[list[str], set[str]]:
    """
    Walk every element in the tree, updating spec= attributes in-place.
    Returns (notes, extra_spec_packages) where extra_spec_packages are
    spec packages that should be added to the namespace beyond what
    _PACKAGE_REPLACEMENTS already handles.
    """
    notes: list[str] = []
    spec_packages: set[str] = set()
    removed_ids: set[int] = set()  # ids of elements detached by Prior restructuring

    # Materialise elements up-front so Prior restructuring (which removes/adds
    # children) does not disturb the iterator.
    for elem in list(root.iter()):
        if callable(elem.tag):  # comment / PI node
            continue
        if id(elem) in removed_ids:  # detached by Prior conversion — skip
            continue

        spec = elem.get("spec")
        is_bare = elem.tag in _BARE_REAL_TAGS and spec is None

        if is_bare:
            simple = "RealParameter"
        elif spec is not None:
            simple = _simple_name(spec)
        else:
            continue

        # --- Tree-only operator names that share a simple name with a deprecated class ---
        if elem.tag == "operator" and simple in _TREE_ONLY_OPERATOR_NAMES:
            continue

        # --- Parameter classes (RealParameter / IntegerParameter / BooleanParameter) ---
        pc = convert_parameter(simple, dict(elem.attrib))
        if pc is not None:
            new_simple = _simple_name(pc.spec_fq)
            elem.set("spec", new_simple)          # use simple name (resolved via namespace)
            if pc.domain_simple:
                elem.set("domain", pc.domain_simple)
                # domain= is resolved internally by the beast runtime, NOT via namespace
            for attr in ("lower", "upper"):
                if attr in elem.attrib:
                    del elem.attrib[attr]
            new_pkg = _package_of(pc.spec_fq)
            # Parameter uses simple name → needs package in namespace;
            # skip if already covered by _PACKAGE_REPLACEMENTS
            if new_pkg:
                spec_packages.add(new_pkg)
            label = f"<{elem.tag}>" if is_bare else spec
            notes.append(f"  {label} → {new_simple}  [{pc.note}]")
            continue

        # --- Prior: structural restructuring (remove wrapper, lift inner distribution) ---
        if simple == "Prior":
            _convert_prior(elem, notes, spec_packages, removed_ids)
            continue

        # --- Classes that need manual structural changes ---
        if simple in _SKIP_CLASSES:
            notes.append(
                f"  WARNING: {spec} skipped — requires manual restructuring "
                f"(see README §3 for {simple})"
            )
            continue

        # --- Context-dependent mappings ---
        if simple in _CONTEXT_MAPPINGS:
            new_fq = _CONTEXT_MAPPINGS[simple][-1][1]  # fallback
            for req_attr, candidate_fq in _CONTEXT_MAPPINGS[simple]:
                if req_attr is None or elem.get(req_attr) is not None:
                    new_fq = candidate_fq
                    break
            elem.set("spec", new_fq)              # always FQ to avoid ambiguity
            # FQ references don't need a namespace entry
            notes.append(f"  {spec} → {new_fq}")
            continue

        # --- General deprecated class mapping ---
        entry = mapping.get(simple)
        if not entry:
            continue  # not deprecated, leave unchanged

        new_fq = entry["replacements"][0]
        new_simple = _simple_name(new_fq)
        new_pkg = _package_of(new_fq)

        if _is_fq(spec):
            # Original was FQ → replace with spec FQ; no namespace entry needed
            elem.set("spec", new_fq)
            notes.append(f"  {spec} → {new_fq}")
        elif new_simple == simple:
            old_pkg = entry["old_package"]
            if old_pkg not in _PACKAGE_REPLACEMENTS:
                # Old package stays in namespace alongside spec package → ambiguity risk.
                # Use FQ to force the spec version.
                elem.set("spec", new_fq)
                notes.append(f"  {spec} → {new_fq}  [FQ: old package kept in ns]")
            else:
                # Old package is fully replaced by spec package → simple name is safe.
                notes.append(f"  namespace: add {new_pkg}  (for '{simple}')")
            if new_pkg:
                spec_packages.add(new_pkg)
        else:
            # Different simple name → use new simple name (resolved via namespace)
            elem.set("spec", new_simple)
            notes.append(f"  {spec} → {new_simple}")
            if new_pkg:
                spec_packages.add(new_pkg)

    return notes, spec_packages


# ---------------------------------------------------------------------------
# File-level conversion
# ---------------------------------------------------------------------------

def convert_file(
    input_path: Path,
    output_path: Path,
    mapping: dict[str, dict],
) -> list[str]:
    """Convert a single XML file and write the result. Returns report lines."""
    try:
        tree = ET.parse(str(input_path), _make_parser())
    except ET.ParseError as exc:
        return [f"ERROR: Cannot parse {input_path}: {exc}"]

    root = tree.getroot()

    old_version = root.get("version", "")
    root.set("version", BEAST3_VERSION)

    notes, spec_packages = _convert_tree(root, mapping)

    old_ns = root.get("namespace", "")
    if old_ns:
        root.set("namespace", _update_namespace(old_ns, spec_packages))

    try:
        ET.indent(tree, space="    ")
    except AttributeError:
        pass  # Python < 3.9

    output_path.parent.mkdir(parents=True, exist_ok=True)
    tree.write(str(output_path), encoding="unicode", xml_declaration=False)

    report = [
        f"Input:       {input_path}",
        f"Output:      {output_path}",
        f"Version:     {old_version!r} → {BEAST3_VERSION!r}",
        f"Conversions: {len(notes)}",
    ]
    report.extend(notes)
    return report


# ---------------------------------------------------------------------------
# Entry point
# ---------------------------------------------------------------------------

def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "inputs", nargs="+", type=Path,
        help="Input BEAST XML file(s)",
    )
    parser.add_argument(
        "-o", "--output-dir", type=Path, default=None,
        help="Output directory (default: same directory as each input file)",
    )
    parser.add_argument(
        "--deprecated-md", type=Path, default=DEFAULT_DEPRECATED_MD,
        help=f"Path to deprecated_classes.md (default: {DEFAULT_DEPRECATED_MD})",
    )
    parser.add_argument(
        "--suffix", default="_b3",
        help="Suffix appended to each output filename stem (default: _b3)",
    )
    args = parser.parse_args()

    if not args.deprecated_md.exists():
        sys.exit(f"ERROR: {args.deprecated_md} not found — run scan_deprecated.py first")

    print(f"Loading mapping from {args.deprecated_md} ...", file=sys.stderr)
    mapping = load_mapping(args.deprecated_md)
    print(f"  → {len(mapping)} class mappings loaded", file=sys.stderr)

    for raw in args.inputs:
        inp = raw.resolve()
        if not inp.exists():
            print(f"WARNING: {inp} not found, skipping", file=sys.stderr)
            continue

        out_dir = args.output_dir.resolve() if args.output_dir else inp.parent
        outp = out_dir / (inp.stem + args.suffix + ".xml")

        for line in convert_file(inp, outp, mapping):
            print(line)
        print()


if __name__ == "__main__":
    main()

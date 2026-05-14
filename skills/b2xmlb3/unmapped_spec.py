#!/usr/bin/env python3
"""
Reads deprecated_classes.md (output of scan_deprecated.py), then scans beast3
for all classes in *.spec.* packages and produces two reports:

  1. Warnings — FQ names that appear in the Replacement column but do not exist
     as spec classes in the scanned source (typos, wrong packages, etc.).
  2. Unmapped spec classes — spec classes with no entry in any Replacement cell.

Excluded packages (type/domain infrastructure, not migration targets):
    beast.base.spec.type
    beast.base.spec.domain

Usage:
    python3 unmapped_spec.py [--deprecated-md PATH] [--beast3-root PATH] [--output PATH]

Defaults:
    deprecated-md: deprecated_classes.md  (same dir as this script)
    beast3-root:   ~/WorkSpace/beast3
    output:        unmapped_spec_classes.md  (same dir as this script)
"""

import argparse
import re
import sys
from collections import defaultdict
from pathlib import Path

SCRIPT_DIR = Path(__file__).parent


def _short(path: Path) -> str:
    try:
        return "~/" + str(path.relative_to(Path.home()))
    except ValueError:
        return str(path)
DEFAULT_DEPRECATED_MD = SCRIPT_DIR / "deprecated_classes.md"
DEFAULT_BEAST3_ROOT = Path("~/WorkSpace/beast3").expanduser()
DEFAULT_OUTPUT = SCRIPT_DIR / "unmapped_spec_classes.md"

MODULES = [
    ("beast-base",    "beast-base/src/main/java"),
    ("beast-fx",      "beast-fx/src/main/java"),
    ("beast-pkgmgmt", "beast-pkgmgmt/src/main/java"),
]

EXCLUDED_PACKAGES = {
    "beast.base.spec.type",
    "beast.base.spec.domain",
}

# Matches a fully-qualified Java class name: lower.lower.Upper...
_FQ_RE = re.compile(r'\b([a-z][a-z0-9]*(?:\.[a-z][a-z0-9]*)+\.[A-Z]\w*)')


# ---------------------------------------------------------------------------
# Parse deprecated_classes.md
# ---------------------------------------------------------------------------

def _fq_from_replacement(text: str) -> set[str]:
    """Extract all fully-qualified class names from a replacement cell."""
    # backtick-wrapped tokens (may or may not be FQ)
    bt = set(re.findall(r'`([^`]+)`', text))
    # bare FQ names in prose
    plain = set(_FQ_RE.findall(text))
    candidates = bt | plain
    # keep only things that look like FQ names (contain at least one dot)
    return {c for c in candidates if '.' in c}


def parse_deprecated_md(md_path: Path) -> tuple[set[str], dict[str, list[str]]]:
    """
    Parse deprecated_classes.md.
    Returns:
        mapped_fq:     set of all FQ names referenced in any Replacement cell
        fq_to_sources: mapping of each FQ name → list of deprecated simple class names
                       that reference it (for warning attribution)
    """
    content = md_path.read_text(encoding="utf-8")
    fq_to_sources: dict[str, list[str]] = defaultdict(list)
    # Table rows look like:  | `SimpleClass` | replacement text |
    for row in re.finditer(r'^\| `(\w+)` \| (.+?) \|$', content, re.MULTILINE):
        dep_class = row.group(1)
        cell = row.group(2).strip()
        if cell == "_no replacement specified_":
            continue
        for fq in _fq_from_replacement(cell):
            fq_to_sources[fq].append(dep_class)
    return set(fq_to_sources.keys()), dict(fq_to_sources)


# ---------------------------------------------------------------------------
# Scan spec classes from beast3 source
# ---------------------------------------------------------------------------

def _extract_package(content: str) -> str:
    m = re.search(r'^\s*package\s+([\w.]+)\s*;', content, re.MULTILINE)
    return m.group(1) if m else ""


def scan_spec_classes(module_name: str, src_root: Path) -> list[dict]:
    """Return all classes/interfaces/enums whose package contains '.spec.'."""
    results = []
    if not src_root.exists():
        print(f"  WARNING: {src_root} does not exist, skipping.", file=sys.stderr)
        return results

    for java_file in sorted(src_root.rglob("*.java")):
        if java_file.name == "module-info.java":
            continue

        # Quick path-based filter before reading the file
        rel_parts = java_file.relative_to(src_root).parts[:-1]  # package dirs
        if "spec" not in rel_parts:
            continue

        try:
            content = java_file.read_text(encoding="utf-8", errors="replace")
        except Exception as exc:
            print(f"  WARN: cannot read {java_file}: {exc}", file=sys.stderr)
            continue

        package = _extract_package(content)
        if ".spec." not in package and not package.endswith(".spec"):
            continue
        if package in EXCLUDED_PACKAGES:
            continue

        class_name = java_file.stem
        results.append(
            {
                "module": module_name,
                "package": package,
                "class": class_name,
                "fq": f"{package}.{class_name}",
            }
        )

    return results


# ---------------------------------------------------------------------------
# Markdown generation
# ---------------------------------------------------------------------------

def generate_markdown(
    unmapped: list[dict],
    dangling: dict[str, list[str]],
    beast3_root: Path,
    deprecated_md: Path,
    total_spec: int,
) -> str:
    total_unmapped = len(unmapped)
    total_mapped = total_spec - total_unmapped

    lines = [
        "# Unmapped Beast3 Spec Classes",
        "",
        f"Scanned from: `{_short(beast3_root)}`  ",
        f"Reference:    `{_short(deprecated_md)}`  ",
        f"Spec classes scanned: **{total_spec}** — mapped: **{total_mapped}**,"
        f" unmapped: **{total_unmapped}**",
        "",
        "Classes in `*.spec.*` packages with no entry in the Replacement column"
        " of `deprecated_classes.md`.",
        "Ordered by Maven module then Java package.",
        "",
    ]

    if dangling:
        lines += [
            "## Warnings: Dangling Replacement References",
            "",
            "The following FQ names appear in the Replacement column of"
            " `deprecated_classes.md` but do not exist as spec classes in"
            " the scanned source. This usually indicates a typo or stale"
            " package name in the `@deprecated` Javadoc.",
            "",
            "| Referenced (non-existent) FQ Name | Cited by Deprecated Class |",
            "|:---|:---|",
        ]
        for fq in sorted(dangling.keys()):
            sources = ", ".join(f"`{s}`" for s in sorted(set(dangling[fq])))
            lines.append(f"| `{fq}` | {sources} |")
        lines.append("")

    by_module: dict[str, dict[str, list]] = defaultdict(lambda: defaultdict(list))
    for entry in unmapped:
        by_module[entry["module"]][entry["package"]].append(entry)

    for module_name, _ in MODULES:
        if module_name not in by_module:
            continue
        packages = by_module[module_name]
        pkg_count = sum(len(v) for v in packages.values())
        lines.append(f"## `{module_name}` ({pkg_count} classes)")
        lines.append("")
        for pkg in sorted(packages.keys()):
            lines.append(f"### `{pkg}`")
            lines.append("")
            lines.append("| Class | Full Qualified Name |")
            lines.append("|:---|:---|")
            for entry in sorted(packages[pkg], key=lambda x: x["class"]):
                lines.append(f"| `{entry['class']}` | `{entry['fq']}` |")
            lines.append("")

    return "\n".join(lines)


# ---------------------------------------------------------------------------
# Entry point
# ---------------------------------------------------------------------------

def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--deprecated-md",
        type=Path,
        default=DEFAULT_DEPRECATED_MD,
        help=f"Input deprecated_classes.md (default: {DEFAULT_DEPRECATED_MD})",
    )
    parser.add_argument(
        "--beast3-root",
        type=Path,
        default=DEFAULT_BEAST3_ROOT,
        help=f"Path to beast3 repo root (default: {DEFAULT_BEAST3_ROOT})",
    )
    parser.add_argument(
        "--output",
        type=Path,
        default=DEFAULT_OUTPUT,
        help=f"Output Markdown file (default: {DEFAULT_OUTPUT})",
    )
    args = parser.parse_args()

    deprecated_md = args.deprecated_md.resolve()
    beast3_root = args.beast3_root.resolve()

    if not deprecated_md.exists():
        sys.exit(f"ERROR: {deprecated_md} not found — run scan_deprecated.py first")
    if not beast3_root.exists():
        sys.exit(f"ERROR: beast3 root not found: {beast3_root}")

    print(f"Parsing {_short(deprecated_md)} ...", file=sys.stderr)
    mapped_fq, fq_to_sources = parse_deprecated_md(deprecated_md)
    print(f"  → {len(mapped_fq)} referenced spec class names", file=sys.stderr)

    all_spec: list[dict] = []
    for module_name, rel_src in MODULES:
        src_root = beast3_root / rel_src
        print(f"Scanning {module_name} for spec classes ...", file=sys.stderr)
        entries = scan_spec_classes(module_name, src_root)
        print(f"  → {len(entries)} spec classes", file=sys.stderr)
        all_spec.extend(entries)

    unmapped = [e for e in all_spec if e["fq"] not in mapped_fq]

    print(f"\nTotal spec classes: {len(all_spec)}", file=sys.stderr)
    print(f"Mapped:             {len(all_spec) - len(unmapped)}", file=sys.stderr)
    print(f"Unmapped:           {len(unmapped)}", file=sys.stderr)

    spec_fq_set = {e["fq"] for e in all_spec}
    dangling = {
        fq: sources
        for fq, sources in fq_to_sources.items()
        if (".spec." in fq or fq.endswith(".spec")) and fq not in spec_fq_set
    }
    if dangling:
        print(f"Dangling references:  {len(dangling)}", file=sys.stderr)

    md = generate_markdown(unmapped, dangling, beast3_root, deprecated_md, len(all_spec))
    args.output.write_text(md, encoding="utf-8")
    print(f"\nOutput: {_short(args.output.resolve())}", file=sys.stderr)


if __name__ == "__main__":
    main()

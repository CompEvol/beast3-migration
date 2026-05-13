#!/usr/bin/env python3
"""
Scans beast3 Java source for class-level @Deprecated annotations and generates
a Markdown report mapping each deprecated class to its beast3 replacement.

Usage:
    python scan_deprecated.py [--beast3-root PATH] [--output PATH]

Defaults:
    beast3-root: ~/WorkSpace/beast3
    output:      deprecated_classes.md  (same dir as this script)
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
DEFAULT_BEAST3_ROOT = Path("~/WorkSpace/beast3").expanduser()
DEFAULT_OUTPUT = SCRIPT_DIR / "deprecated_classes.md"

# Ordered list of (display_name, relative_src_path) for each Maven module
MODULES = [
    ("beast-base",    "beast-base/src/main/java"),
    ("beast-fx",      "beast-fx/src/main/java"),
    ("beast-pkgmgmt", "beast-pkgmgmt/src/main/java"),
]


# ---------------------------------------------------------------------------
# Parsing helpers
# ---------------------------------------------------------------------------

def extract_package(content: str) -> str:
    m = re.search(r'^\s*package\s+([\w.]+)\s*;', content, re.MULTILINE)
    return m.group(1) if m else ""


def _strip_strings(text: str) -> str:
    """Replace string literals with empty strings to avoid false matches."""
    return re.sub(r'"(?:[^"\\]|\\.)*"', '""', text)


def _strip_line_comments(text: str) -> str:
    return re.sub(r'//[^\n]*', '', text)


def _strip_block_comments(text: str) -> str:
    """Remove /* ... */ preserving line count."""
    return re.sub(
        r'/\*.*?\*/',
        lambda m: '\n' * m.group(0).count('\n'),
        text,
        flags=re.DOTALL,
    )


def extract_replacement(pre_class: str) -> str:
    """
    Extract the @deprecated replacement hint from the nearest javadoc or
    block comment preceding the class declaration.
    """
    # Search all block/javadoc comments for @deprecated tag
    replacement = ""
    for cm in re.finditer(r'/\*+\s*(.*?)\s*\*+/', pre_class, re.DOTALL):
        body = cm.group(1)
        dm = re.search(
            r'@deprecated\s+(.*?)(?=\n\s*(?:\*\s*)?@|\n\s*\*/|\Z)',
            body,
            re.IGNORECASE | re.DOTALL,
        )
        if dm:
            raw = dm.group(1)
            # Strip leading " * " from each comment line, then join non-empty lines
            lines = [re.sub(r'^\s*\*+\s*', '', ln) for ln in raw.splitlines()]
            raw = ' '.join(ln for ln in lines if ln.strip())
            raw = re.sub(r'\s+', ' ', raw).strip()
            # Replace {@link X.Y} with `X.Y`
            raw = re.sub(r'\{@link\s+([\w.#$<>\[\]]+)\}', r'`\1`', raw)
            replacement = raw  # keep the last (closest) match
    return replacement


def is_class_deprecated(content: str, class_name: str) -> tuple[bool, str]:
    """
    Returns (is_deprecated, replacement_hint).
    Only considers class-level @Deprecated (not methods or fields).
    """
    # Find the class/interface/enum/annotation declaration for this class name.
    # We look for it outside of braces by requiring minimal leading whitespace.
    class_decl_re = re.compile(
        r'^[ \t]*(?:public\s+|protected\s+)?'
        r'(?:(?:abstract|final|sealed|non-sealed|strictfp)\s+)*'
        r'(?:class|interface|enum|@\s*interface)\s+' + re.escape(class_name) + r'\b',
        re.MULTILINE,
    )

    m = class_decl_re.search(content)
    if not m:
        return False, ""

    pre_class = content[:m.start()]

    # Strip string literals and comments to find bare @Deprecated annotations
    pre_stripped = _strip_strings(pre_class)
    pre_stripped = _strip_line_comments(pre_stripped)
    pre_stripped = _strip_block_comments(pre_stripped)

    dep_matches = list(re.finditer(r'@Deprecated\b', pre_stripped))
    if not dep_matches:
        return False, ""

    last_dep_pos = dep_matches[-1].start()

    # Validate: between the last @Deprecated and the class declaration there
    # must be no statement terminators or closing braces (which would indicate
    # we are inside a method/field body rather than at the class level).
    between_raw = pre_stripped[last_dep_pos + len('@Deprecated'):]
    # Remove annotations (e.g. @Description("..."), @SuppressWarnings)
    between_clean = re.sub(r'@[\w.]+(?:\s*\([^)]*\))?', '', between_raw)
    between_clean = between_clean.strip()

    if re.search(r'[};]', between_clean):
        return False, ""

    replacement = extract_replacement(pre_class[max(0, last_dep_pos - 2000):])
    return True, replacement


# ---------------------------------------------------------------------------
# Scanner
# ---------------------------------------------------------------------------

def scan_module(module_name: str, src_root: Path) -> list[dict]:
    results = []
    if not src_root.exists():
        print(f"  WARNING: {src_root} does not exist, skipping.", file=sys.stderr)
        return results

    for java_file in sorted(src_root.rglob("*.java")):
        if java_file.name == "module-info.java":
            continue

        try:
            content = java_file.read_text(encoding="utf-8", errors="replace")
        except Exception as exc:
            print(f"  WARN: cannot read {java_file}: {exc}", file=sys.stderr)
            continue

        package = extract_package(content)
        class_name = java_file.stem

        deprecated, replacement = is_class_deprecated(content, class_name)
        if deprecated:
            results.append(
                {
                    "module": module_name,
                    "package": package,
                    "class": class_name,
                    "full_class": f"{package}.{class_name}" if package else class_name,
                    "replacement": replacement,
                }
            )

    return results


# ---------------------------------------------------------------------------
# Markdown generation
# ---------------------------------------------------------------------------

def generate_markdown(all_entries: list[dict], beast3_root: Path) -> str:
    total = len(all_entries)
    lines = [
        "# Deprecated Classes in Beast3",
        "",
        f"Scanned from: `{_short(beast3_root)}`  ",
        f"Total deprecated classes found: **{total}**",
        "",
        "Entries are ordered by Maven module then Java package.",
        "The **Replacement** column is extracted from the `@deprecated` Javadoc tag;",
        "_no replacement specified_ means the tag was absent.",
        "",
    ]

    by_module: dict[str, dict[str, list]] = defaultdict(lambda: defaultdict(list))
    for entry in all_entries:
        by_module[entry["module"]][entry["package"]].append(entry)

    # Preserve module declaration order
    module_names = [m[0] for m in MODULES]
    for module in module_names:
        if module not in by_module:
            continue
        packages = by_module[module]
        pkg_count = sum(len(v) for v in packages.values())
        lines.append(f"## `{module}` ({pkg_count} classes)")
        lines.append("")

        for pkg in sorted(packages.keys()):
            lines.append(f"### `{pkg}`")
            lines.append("")
            lines.append("| Deprecated Class | Replacement |")
            lines.append("|:---|:---|")
            for entry in sorted(packages[pkg], key=lambda x: x["class"]):
                cls = f"`{entry['class']}`"
                repl = entry["replacement"] or "_no replacement specified_"
                repl = repl.replace("|", "\\|")
                lines.append(f"| {cls} | {repl} |")
            lines.append("")

    return "\n".join(lines)


# ---------------------------------------------------------------------------
# Entry point
# ---------------------------------------------------------------------------

def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
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

    beast3_root: Path = args.beast3_root.resolve()
    if not beast3_root.exists():
        sys.exit(f"ERROR: beast3 root not found: {beast3_root}")

    all_entries: list[dict] = []
    for module_name, rel_src in MODULES:
        src_root = beast3_root / rel_src
        print(f"Scanning {module_name} ...", file=sys.stderr)
        entries = scan_module(module_name, src_root)
        print(f"  → {len(entries)} deprecated classes", file=sys.stderr)
        all_entries.extend(entries)

    md = generate_markdown(all_entries, beast3_root)
    args.output.write_text(md, encoding="utf-8")
    print(f"\nOutput: {_short(args.output.resolve())}", file=sys.stderr)
    print(f"Total:  {len(all_entries)} deprecated classes", file=sys.stderr)


if __name__ == "__main__":
    main()

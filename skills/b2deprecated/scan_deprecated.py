#!/usr/bin/env python3
"""
Scans beast3 Java source for class-level @Deprecated annotations and generates
a Markdown report mapping each deprecated class to its beast3 replacement.
Both top-level and inner/nested deprecated classes are detected.

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


_CLASS_KW = re.compile(
    r'(?:public\s+|protected\s+|private\s+)?'
    r'(?:(?:abstract|final|sealed|non-sealed|strictfp)\s+)*'
    r'(?:class|interface|enum|@\s*interface)\s+(\w+)',
)


def find_all_deprecated_classes(content: str) -> list[tuple[str, str]]:
    """
    Find every class-level @Deprecated declaration in the file — both top-level
    and inner/nested classes.  Returns list of (simple_class_name, replacement).

    Strategy: scan forward from each @Deprecated in the comment-stripped source.
    Skip whitespace and sibling annotations; if a class/interface/enum keyword
    follows with no intervening } or ; (which would indicate a method/field body),
    the @Deprecated belongs to that class declaration.

    Position mapping: both strippers preserve line counts, so the line number of
    a match in `stripped` equals the line number in `content`. We use that to
    recover the exact character position in `content` for extract_replacement.
    """
    stripped = _strip_strings(content)       # removes ) inside string args
    stripped = _strip_line_comments(stripped)
    stripped = _strip_block_comments(stripped)

    # Pre-compute line start positions in content for stripped→content mapping.
    content_line_starts: list[int] = [0]
    for line in content.splitlines(keepends=True):
        content_line_starts.append(content_line_starts[-1] + len(line))

    seen: set[str] = set()
    results: list[tuple[str, str]] = []

    for dep_m in re.finditer(r'@Deprecated\b', stripped):
        # Look ahead up to 400 chars for the class declaration
        lookahead = stripped[dep_m.end():dep_m.end() + 400]

        # Strip leading whitespace and sibling annotations to reach the
        # class/interface/enum keyword
        remaining = lookahead.lstrip()
        while True:
            ann = re.match(r'^@[\w.]+(?:\s*\([^)]*\))?\s*', remaining)
            if ann:
                remaining = remaining[ann.end():].lstrip()
            else:
                break

        cls_m = _CLASS_KW.match(remaining)
        if not cls_m:
            continue

        # The text consumed between @Deprecated and the class keyword must not
        # contain } or ; — those indicate we're inside a method/field body.
        between = lookahead[: len(lookahead) - len(remaining)]
        if re.search(r'[};]', between):
            continue

        class_name = cls_m.group(1)
        if class_name in seen:
            continue
        seen.add(class_name)

        # Map stripped position → content position via line number.
        dep_line = stripped[: dep_m.start()].count('\n')
        line_start = content_line_starts[dep_line] if dep_line < len(content_line_starts) else 0
        line_end = content_line_starts[dep_line + 1] if dep_line + 1 < len(content_line_starts) else len(content)
        col = content[line_start:line_end].find('@Deprecated')
        dep_content_pos = line_start + col if col >= 0 else line_start

        pre_dep = content[max(0, dep_content_pos - 2000): dep_content_pos]
        replacement = extract_replacement(pre_dep)
        results.append((class_name, replacement))

    return results


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

        for class_name, replacement in find_all_deprecated_classes(content):
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

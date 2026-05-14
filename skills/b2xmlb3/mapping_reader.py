#!/usr/bin/env python3
"""Parse deprecated_classes.md to build a simple-name → FQ spec class mapping."""

import re
from pathlib import Path

SCRIPT_DIR = Path(__file__).parent
DEFAULT_DEPRECATED_MD = SCRIPT_DIR / "deprecated_classes.md"

# Matches a FQ Java class name: lower.lower.Upper...
_FQ_RE = re.compile(r'\b([a-z][a-z0-9]*(?:\.[a-z][a-z0-9]*)+\.[A-Z]\w*)')


def _all_spec_fq(text: str) -> list[str]:
    """Extract all *.spec.* FQ class names from a replacement cell (in order)."""
    seen: set[str] = set()
    result: list[str] = []

    for m in re.finditer(r'`([^`]+)`', text):
        tok = m.group(1)
        if "." in tok and ".spec." in tok and tok[0].islower():
            if tok not in seen:
                seen.add(tok)
                result.append(tok)

    for m in _FQ_RE.finditer(text):
        fq = m.group(1)
        if ".spec." in fq and fq not in seen:
            seen.add(fq)
            result.append(fq)

    return result


def load_mapping(md_path: Path = DEFAULT_DEPRECATED_MD) -> dict[str, dict]:
    """
    Parse deprecated_classes.md.

    Returns {simple_class_name: {"replacements": [fq_spec...], "old_package": "..."}}
    where replacements[0] is the primary replacement and old_package is the Java
    package the deprecated class lived in (from the ### package header).
    Classes with no *.spec.* replacement are omitted.
    """
    content = md_path.read_text(encoding="utf-8")
    result: dict[str, dict] = {}
    current_package = ""

    for line in content.split("\n"):
        pkg_m = re.match(r'^### `([\w.]+)`', line)
        if pkg_m:
            current_package = pkg_m.group(1)
            continue

        row_m = re.match(r'^\| `(\w+)` \| (.+?) \|$', line)
        if row_m:
            class_name = row_m.group(1)
            cell = row_m.group(2).strip()
            if cell == "_no replacement specified_":
                continue
            fqs = _all_spec_fq(cell)
            if fqs:
                result[class_name] = {
                    "replacements": fqs,
                    "old_package": current_package,
                }

    return result

"""
reporter.py — Change data types and Markdown report rendering/saving.

Exports:
    ChangeKind  — enum: INFO | RENAME | WARNING | TODO
    Change      — frozen dataclass: (kind, description)
    render_report(all_changes) -> str
    save_report(input_path, changes) -> Path
    print_report(all_changes)
"""

from __future__ import annotations

from dataclasses import dataclass
from enum import Enum
from pathlib import Path


class ChangeKind(str, Enum):
    INFO    = 'info'     # version/namespace — always correct, no review needed
    RENAME  = 'rename'   # spec=/type=/class= simple class rename
    WARNING = 'warning'  # semantic replacement — review recommended
    TODO    = 'todo'     # no spec twin found — manual action required


@dataclass(frozen=True)
class Change:
    kind: ChangeKind
    description: str


# ---------------------------------------------------------------------------
# Rendering
# ---------------------------------------------------------------------------

_LABEL: dict[ChangeKind, str] = {
    ChangeKind.INFO:    '[info]   ',
    ChangeKind.RENAME:  '[rename] ',
    ChangeKind.WARNING: '[warn] ⚠ ',
    ChangeKind.TODO:    '[todo] ✗ ',
}


def render_report(all_changes: dict[str, list[Change]]) -> str:
    """
    Render a Markdown report for one or more converted files.

    all_changes: {str(input_path): [Change, ...]}
    """
    lines: list[str] = []

    total_changes = sum(len(v) for v in all_changes.values())
    total_warn    = sum(sum(1 for c in v if c.kind == ChangeKind.WARNING)
                        for v in all_changes.values())
    total_todo    = sum(sum(1 for c in v if c.kind == ChangeKind.TODO)
                        for v in all_changes.values())

    lines.append('## XML Migration Report\n')
    lines.append(f'- Files processed : {len(all_changes)}')
    lines.append(f'- Total changes   : {total_changes}')
    if total_warn:
        lines.append(f'- ⚠ Warnings      : {total_warn}'
                     '  (semantic replacements — review required)')
    if total_todo:
        lines.append(f'- ✗ TODOs         : {total_todo}'
                     '  (no spec twin — manual action required)')

    for path, changes in all_changes.items():
        n_rename = sum(1 for c in changes if c.kind == ChangeKind.RENAME)
        n_warn   = sum(1 for c in changes if c.kind == ChangeKind.WARNING)
        n_todo   = sum(1 for c in changes if c.kind == ChangeKind.TODO)

        parts: list[str] = []
        if n_rename: parts.append(f'{n_rename} rename{"s" if n_rename != 1 else ""}')
        if n_warn:   parts.append(f'⚠ {n_warn} warning{"s" if n_warn != 1 else ""}')
        if n_todo:   parts.append(f'✗ {n_todo} todo{"s" if n_todo != 1 else ""}')
        summary = ', '.join(parts) if parts else 'no changes'

        lines.append(f'\n### {path}  ({summary})\n')

        if not changes:
            lines.append('  (no changes)')
            continue

        for n, change in enumerate(changes, 1):
            lines.append(f'  {n:>3}. {_LABEL[change.kind]} {change.description}')

    return '\n'.join(lines) + '\n'


# ---------------------------------------------------------------------------
# Delivery
# ---------------------------------------------------------------------------

def save_report(input_path: Path, changes: list[Change]) -> Path:
    """
    Save a per-file Markdown report to:
        <input_path.parent>/reports/<input_path.stem>.md

    Creates the reports/ directory if it does not exist.
    Returns the path of the written report file.
    """
    report_dir = input_path.parent / 'reports'
    report_dir.mkdir(exist_ok=True)
    report_path = report_dir / (input_path.stem + '.md')
    report_path.write_text(render_report({str(input_path): changes}), encoding='utf-8')
    return report_path


def print_report(all_changes: dict[str, list[Change]]) -> None:
    """Print the full multi-file report to stdout."""
    print(render_report(all_changes))

"""
deprecated_map.py — BEAST2 → BEAST3 class rename knowledge.

Parses deprecated_classes.md and exposes a rename map plus the logic for
resolving a spec=/type=/class= attribute value to its B3 replacement.

No lxml dependency — importable by any tool that needs the rename rules.
"""

import re
from pathlib import Path
from typing import Optional


# Classes that must NOT be renamed even if they appear in the deprecated map.
# These have no spec twin or the legacy path is intentionally kept.
DO_NOT_RENAME: frozenset[str] = frozenset({
    "Tree", "Node", "TreeInterface", "TreeParser",
    "SiteModelInterface", "SubstitutionModel", "BranchRateModel",
    "Exchange", "WilsonBalding",
    # Not class-level deprecated; no spec twin; used unchanged in B3.
    "TreeStatLogger",       # both B2/B3 reference files use legacy path unchanged
    "TreeIntervals",        # not deprecated; no spec twin
    "TraitSet",             # only method-level @Deprecated, not class-level
    "TaxonSet",             # only method-level @Deprecated, not class-level
    "Taxon",                # @Deprecated is commented out
    "Nucleotide",           # not deprecated; no spec twin; used as dataType value
    # B3 replacement classes — must not be re-processed if encountered in output.
    "BactrianSubtreeSlide", # B3 replacement for SubtreeSlide; lives in kernel package
})

# Operators whose split is handled structurally by the XSLT (no annotation needed).
SCALE_OPERATOR_CLASSES: frozenset[str] = frozenset({
    "ScaleOperator", "BactrianScaleOperator",
})


def parse_deprecated_md(md_path: Path) -> dict[str, str]:
    """
    Parse deprecated_classes.md → rename map.

    Keys are both simple class names AND full deprecated FQNs (reconstructed
    from the ### package section headings).  Storing both allows resolve_spec()
    to use exact-FQN lookup for full references, avoiding simple-name collisions
    such as branchratemodel.Base vs substitutionmodel.Base.

    Map entries:
      SimpleName       → B3_FQN   (fallback for short-name references in XML)
      DeprecatedFQN    → B3_FQN   (precise lookup for full-FQN references)

    Only entries whose replacement column contains a backtick-quoted beast.* FQN
    are included.  Classes with prose-only replacements are omitted.
    """
    mapping: dict[str, str] = {}
    fqn_re = re.compile(r'`(beast\.[^`]+)`')
    pkg_re = re.compile(r'(beast\.[^\s`]+)')
    current_package = ''

    with md_path.open() as f:
        for line in f:
            # Track ### package headings to reconstruct the deprecated FQN.
            if line.startswith('### '):
                pkg_match = pkg_re.search(line)
                if pkg_match:
                    current_package = pkg_match.group(1)
                continue

            if not line.startswith('|'):
                continue
            cols = [c.strip() for c in line.split('|')]
            if len(cols) < 4:
                continue
            name_col, replacement_col = cols[1], cols[2]
            name_match = re.search(r'`([^`]+)`', name_col)
            if not name_match:
                continue
            simple_name = name_match.group(1).strip()
            if simple_name in ('Deprecated Class', '---', ''):
                continue
            fqn_match = fqn_re.search(replacement_col)
            if fqn_match:
                b3_fqn = fqn_match.group(1)
                mapping[simple_name] = b3_fqn
                if current_package:
                    mapping[f'{current_package}.{simple_name}'] = b3_fqn

    return mapping


def resolve_spec(value: str, dep_map: dict[str, str]) -> Optional[str]:
    """
    Return the B3 full-FQN replacement for a spec=/type=/class= value, or None.

    Rules (first match wins):
      1. Empty / @ reference / already has .spec. → no change (None)
      2. Simple name in DO_NOT_RENAME → no change (None)
      3. Short name (no dots, or relative path like 'parameter.RealParameter')
         → simple-name lookup in dep_map
      4. Full FQN (starts with 'beast.') → exact-FQN lookup first, then
         simple-name fallback.  Exact lookup disambiguates classes whose simple
         name collides (e.g. branchratemodel.Base vs substitutionmodel.Base).
    """
    if not value:
        return None
    val = value.strip()
    if val.startswith('@') or '.spec.' in val:
        return None

    simple = val.split('.')[-1]
    if simple in DO_NOT_RENAME:
        return None

    # Short name or relative path: simple-name lookup only.
    if not val.startswith('beast.'):
        return dep_map.get(simple)

    # Full FQN: try exact match first to avoid simple-name collisions,
    # then fall back to simple name for classes not stored with package context.
    exact = dep_map.get(val)
    return exact if exact is not None else dep_map.get(simple)

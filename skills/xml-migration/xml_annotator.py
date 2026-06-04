"""
xml_annotator.py — Pre-pass: stamp _b3* annotation attributes on a BEAST2 XML
tree so the XSLT stylesheet (b2_to_b3.xsl) can apply deterministic transforms.

All decisions that require Python logic (shape/domain inference, Prior
classification, vector-x detection) are made here. The XSLT only reads the
annotations — it contains no conditional logic of its own.
"""

from typing import Optional
from lxml import etree

from reporter import Change, ChangeKind
from deprecated_map import (
    DO_NOT_RENAME, SCALE_OPERATOR_CLASSES, resolve_spec,
)


# ---------------------------------------------------------------------------
# Parameter type inference
# ---------------------------------------------------------------------------

_PARAM_BASE_TYPES = frozenset({'RealParameter', 'IntegerParameter', 'BooleanParameter'})

_PARAM_PKG = 'beast.base.spec.inference.parameter.'
_PARAM_SPEC_MAP: dict[str, dict[str, str]] = {
    'RealParameter':    {'scalar': _PARAM_PKG + 'RealScalarParam',
                         'vector': _PARAM_PKG + 'RealVectorParam',
                         'simplex': _PARAM_PKG + 'SimplexParam'},
    'IntegerParameter': {'scalar': _PARAM_PKG + 'IntScalarParam',
                         'vector': _PARAM_PKG + 'IntVectorParam',
                         'simplex': _PARAM_PKG + 'IntScalarParam'},
    'BooleanParameter': {'scalar': _PARAM_PKG + 'BoolScalarParam',
                         'vector': _PARAM_PKG + 'BoolVectorParam',
                         'simplex': _PARAM_PKG + 'BoolScalarParam'},
}


def _infer_domain(elem: etree._Element) -> str:
    """Map lower=/upper= attributes to a BEAST3 domain class name."""
    lower = elem.get('lower', '').strip()
    upper = elem.get('upper', '').strip()
    try:
        lo = float(lower) if lower else None
        hi = float(upper) if upper else None
    except ValueError:
        return 'Real'
    if lo == 0.0 and hi is None:
        return 'PositiveReal'
    if lo == 0.0 and hi == 1.0:
        return 'UnitInterval'
    return 'Real'


def _infer_shape(elem: etree._Element) -> str:
    """Return 'scalar', 'vector', or 'simplex' from value= token count and context."""
    value = elem.get('value', '').strip()
    tokens = value.split() if value else []

    # Heuristic: id contains 'freq' or the parent tag is 'frequencies' → simplex
    eid = elem.get('id', '').lower()
    parent = elem.getparent()
    parent_tag = parent.tag if (parent is not None and isinstance(parent.tag, str)) else ''
    if 'freq' in eid or parent_tag.lower() == 'frequencies':
        return 'simplex'

    return 'scalar' if len(tokens) <= 1 else 'vector'


def _param_spec(base_type: str, shape: str) -> str:
    """Return the short B3 class name for a given base type and shape."""
    return _PARAM_SPEC_MAP.get(base_type, {}).get(shape, 'RealScalarParam')


# ---------------------------------------------------------------------------
# Prior classification
# ---------------------------------------------------------------------------

_ONEONX_CLASSES: frozenset[str] = frozenset({
    'OneOnX', 'beast.base.inference.distribution.OneOnX',
})
_PRIOR_CLASSES: frozenset[str] = frozenset({
    'Prior',
    'beast.base.inference.distribution.Prior',
    'beast.base.spec.inference.distribution.Prior',
})


def _prior_type(elem: etree._Element) -> Optional[str]:
    """
    If elem is a BEAST2 Prior distribution, return the _b3prior_type annotation:
      'flatten'        — scalar inner distr; inline directly
      'iid'            — vector x= param; wrap with IID (set by annotate_vector_priors)
      'oneonx_pop'     — OneOnX prior on popSize → LogNormal(M=3, S=2.5)
      'oneonx_kappa'   — OneOnX prior on kappa  → LogNormal(M=1, S=0.5)
      'oneonx_generic' — OneOnX on unknown param → LogNormal(M=1, S=1.0) + TODO
    Returns None if the element is not a Prior.

    Recognises two BEAST2 authoring styles:
      spec="Prior"  — explicit spec attribute
      <prior .../>  — element tag used as class alias via <map name="prior">
    """
    spec = elem.get('spec', '') or ''
    # BEAUti writes <prior x="..."> with no spec= — the tag itself is the alias.
    if not spec and isinstance(elem.tag, str) and elem.tag == 'prior':
        spec = 'Prior'
    if spec.split('.')[-1] != 'Prior' and spec not in _PRIOR_CLASSES:
        return None

    inner = _find_inner_distr(elem)
    if inner is None:
        return None

    inner_spec = inner.get('spec', '') or ''
    if inner_spec.split('.')[-1] == 'OneOnX' or inner_spec in _ONEONX_CLASSES:
        x = elem.get('x', '')
        if 'popSize' in x:
            return 'oneonx_pop'
        if 'kappa' in x.lower():
            return 'oneonx_kappa'
        return 'oneonx_generic'

    return 'flatten'


def _find_inner_distr(elem: etree._Element) -> Optional[etree._Element]:
    """Return the first child that acts as the inner distribution of a Prior."""
    for child in elem:
        if not isinstance(child.tag, str):
            continue
        if child.tag in ('distr', 'distribution') or child.get('name') == 'distr':
            return child
    return None


# ---------------------------------------------------------------------------
# Pre-pass: annotate the XML tree
# ---------------------------------------------------------------------------

def prepass(tree: etree._ElementTree, dep_map: dict[str, str],
            fxtemplate: bool) -> list[Change]:
    """
    Walk every element and stamp _b3* attributes that the XSLT reads.

    Returns a list of Change objects for the report.
    Skips comment and PI nodes (their get() ignores the default argument).
    """
    root = tree.getroot()
    changes: list[Change] = []
    # Elements already consumed as children of a oneonx_* Prior (replaced wholesale
    # by the XSLT — processing them again would produce spurious rename changes).
    skip_elements: set[int] = set()
    # TODO class names already reported this file — suppress duplicates.
    seen_todos: set[str] = set()

    if not fxtemplate:
        root.set('_b3version', '2.8')
        changes.append(Change(ChangeKind.INFO, 'version: 2.0 → 2.8'))
        changes.append(Change(ChangeKind.INFO, 'namespace: updated (deprecated classes use full FQNs; no spec packages in namespace)'))

    for elem in root.iter():
        if not isinstance(elem.tag, str):
            continue
        if id(elem) in skip_elements:
            continue

        spec_val = elem.get('spec', '') or ''
        spec_simple = spec_val.split('.')[-1]

        # --- chainLength on <run spec="MCMC"> ---
        # Convert plain integers to the parameterised form $(chainLength=N) so the
        # value can be overridden on the command line without editing the XML.
        # Idempotent: already-converted values (starting with "$(") are left alone.
        chain_len = elem.get('chainLength')
        if chain_len is not None and not chain_len.startswith('$('):
            if elem.tag == 'run' or 'MCMC' in spec_val:
                new_cl = f'$(chainLength={chain_len})'
                elem.set('chainLength', new_cl)
                changes.append(Change(
                    ChangeKind.INFO,
                    f'chainLength: "{chain_len}" → "{new_cl}"',
                ))

        # --- logEvery on <logger> elements ---
        # Parameterise so the logging frequency can be overridden at the command
        # line (e.g. -D logEvery=100) independently of the default in the XML.
        # BEAST3 only allows one $(logEvery=N) default declaration per variable:
        # the first logger defines the default; subsequent ones reference $(logEvery).
        # Idempotent: already-converted values are left alone.
        # Bare <parameter> with no spec= is a legacy RealParameter.
        bare = elem.tag == 'parameter' and not spec_val and elem.get('value') is not None
        if bare:
            spec_simple = 'RealParameter'

        # --- Parameter migration ---
        if spec_simple in _PARAM_BASE_TYPES:
            shape = _infer_shape(elem)
            b3spec = _param_spec(spec_simple, shape)
            elem.set('_b3spec', b3spec)
            qualifier = ' (bare tag)' if bare else ''

            if shape == 'simplex':
                # SimplexParam has no domain= input; dimension= must be kept so
                # BEAST3 knows how many elements the simplex has (e.g. dimension="4"
                # with value="0.25" expands to [0.25, 0.25, 0.25, 0.25]).
                # Use sentinel '_b3domain=simplex' so XSLT T2s handles this path.
                elem.set('_b3domain', 'simplex')
                dropped = [f'{a}="{elem.get(a)}"'
                           for a in ('lower', 'upper') if elem.get(a) is not None]
                drop_note = f'  dropped: {", ".join(dropped)}' if dropped else ''
                changes.append(Change(
                    ChangeKind.RENAME,
                    f'spec= "{spec_simple}"{qualifier} → "{b3spec}"{drop_note}',
                ))
            else:
                domain = _infer_domain(elem)
                elem.set('_b3domain', domain)
                # Report attributes dropped by XSLT T2 so nothing is silently lost.
                dropped = [f'{a}="{elem.get(a)}"'
                           for a in ('lower', 'upper', 'dimension')
                           if elem.get(a) is not None]
                drop_note = f'  dropped: {", ".join(dropped)}' if dropped else ''
                changes.append(Change(
                    ChangeKind.RENAME,
                    f'spec= "{spec_simple}"{qualifier} → "{b3spec}"  domain="{domain}"{drop_note}',
                ))
            continue

        # --- Prior classification ---
        # Change is NOT recorded here — _b3prior_type may be upgraded from
        # 'flatten' to 'iid' by annotate_vector_priors() which runs after
        # prepass(). Call collect_prior_changes(root) afterwards to record
        # the final state.
        ptype = _prior_type(elem)
        if ptype is not None:
            elem.set('_b3prior_type', ptype)
            _annotate_inner_distr(elem, dep_map)
            changes.extend(_fix_uniform_infinite_bounds(elem))
            # oneonx_* priors replace all children wholesale via the XSLT template;
            # mark children so the main loop skips them and avoids spurious renames.
            if ptype.startswith('oneonx'):
                for child in elem:
                    skip_elements.add(id(child))
            continue

        # --- ScaleOperator split: XSLT T4 handles it structurally ---
        if spec_simple in SCALE_OPERATOR_CLASSES:
            if elem.get('parameter') is not None:
                changes.append(Change(
                    ChangeKind.WARNING,
                    f'spec= "{spec_simple}" [parameter=] → "ScaleOperator"'
                    '  (class split — inference mode)',
                ))
            elif elem.get('tree') is not None:
                changes.append(Change(
                    ChangeKind.WARNING,
                    f'spec= "{spec_simple}" [tree=] → "ScaleTreeOperator"'
                    '  (class split — evolution mode)',
                ))
            continue

        # --- Standalone OneOnX: reuse the oneonx_generic → LogNormal path ---
        # OneOnX inside a Prior is already handled above (_prior_type detects it
        # and marks the child in skip_elements).  Any OneOnX that reaches here
        # is standalone — stamp it as oneonx_generic so T3e in the XSLT converts
        # it to LogNormal(M=1, S=1) instead of dep_map's LogUniform mapping.
        if spec_simple == 'OneOnX':
            elem.set('_b3prior_type', 'oneonx_generic')
            continue

        # --- UniformOperator: ambiguous split — warn, then fall through to rename ---
        # dep_map picks IntUniformOperator (first FQN); IntervalOperator is the
        # alternative for real-valued parameters.
        if spec_simple == 'UniformOperator':
            changes.append(Change(
                ChangeKind.WARNING,
                'spec= "UniformOperator" → "IntUniformOperator" assumed; '
                'use "beast.base.spec.inference.operator.uniform.IntervalOperator" '
                'instead if the target is a real-valued parameter',
            ))

        # --- Parameter: abstract type — warn, then fall through to rename ---
        # dep_map picks beast.base.spec.type.Tensor (read-only use).
        elif spec_simple == 'Parameter':
            changes.append(Change(
                ChangeKind.WARNING,
                'spec= "Parameter" → "beast.base.spec.type.Tensor" assumed (read-only); '
                'use RealScalarParam/RealVectorParam if the parameter is mutable',
            ))

        # --- Uniform tree operator: XSLT T4c uses full legacy path ---
        if spec_simple == 'Uniform' and elem.get('tree') is not None:
            changes.append(Change(
                ChangeKind.WARNING,
                'spec= "Uniform" [tree=] → full legacy path required'
                '  (short name resolves to distribution, not tree operator)',
            ))
            continue

        # --- Simple spec=/type=/class= rename ---
        for change in _annotate_simple_rename(elem, dep_map):
            if change.kind == ChangeKind.TODO:
                if change.description in seen_todos:
                    continue
                seen_todos.add(change.description)
            changes.append(change)

    return changes


def annotate_vector_priors(root: etree._Element, id_map: dict[str, etree._Element]):
    """
    Upgrade 'flatten' Prior annotations to 'iid' when the referenced x= param
    is vector-shaped.  Must run after prepass() because it relies on id_map.
    """
    for elem in root.iter():
        if elem.get('_b3prior_type') != 'flatten':
            continue
        x_ref = elem.get('x', '').lstrip('@')
        if not x_ref:
            continue
        target = id_map.get(x_ref)
        if target is None:
            continue
        if len((target.get('value', '') or '').split()) > 1:
            elem.set('_b3prior_type', 'iid')
            elem.set('_b3vector_x', '1')


def collect_prior_changes(root: etree._Element) -> list[Change]:
    """
    Record Prior transformation changes based on the FINAL _b3prior_type values.

    Must be called after annotate_vector_priors() so that any flatten→iid
    upgrades are reflected. Iterates in document order.
    """
    changes: list[Change] = []
    for elem in root.iter():
        if not isinstance(elem.tag, str):
            continue
        ptype = elem.get('_b3prior_type')
        if ptype is not None:
            changes.append(_prior_change(ptype, elem))
    return changes


# ---------------------------------------------------------------------------
# Private helpers
# ---------------------------------------------------------------------------

_ONEONX_LOGNORMAL: dict[str, tuple[str, str]] = {
    'oneonx_pop':     ('3.0', '2.5'),
    'oneonx_kappa':   ('1.0', '0.5'),
    'oneonx_generic': ('1.0', '1.0'),
}

_PRIOR_FLATTEN_MSG = (
    'spec= "Prior" → inner distribution inlined  (Prior wrapper removed)'
)
_PRIOR_IID_MSG = (
    'spec= "Prior" on vector param → "IID" wrapper added'
)


def _prior_change(ptype: str, elem: etree._Element) -> Change:
    """Return the Change describing a Prior element transformation."""
    if ptype == 'flatten':
        return Change(ChangeKind.WARNING, _PRIOR_FLATTEN_MSG)
    if ptype == 'iid':
        return Change(ChangeKind.WARNING, _PRIOR_IID_MSG)
    # oneonx_* variants
    m, s = _ONEONX_LOGNORMAL.get(ptype, ('1.0', '1.0'))
    # Standalone OneOnX (element itself is OneOnX, not a Prior wrapper).
    spec = (elem.get('spec', '') or '').split('.')[-1]
    if spec == 'OneOnX':
        return Change(
            ChangeKind.WARNING,
            f'spec= "OneOnX" standalone → "LogNormal"  M={m}  S={s}'
            '  — set param= to the target parameter; review M/S defaults',
        )
    x_ref = elem.get('x', '') or ''
    suffix = '  — review M/S defaults' if ptype == 'oneonx_generic' else ''
    return Change(
        ChangeKind.WARNING,
        f'spec= "Prior+OneOnX" ({x_ref}) → "LogNormal"  M={m}  S={s}{suffix}',
    )


_INFINITE_BOUNDS: frozenset[str] = frozenset({'Infinity', '+Infinity', 'Inf', '+Inf'})
_NEG_INFINITE_BOUNDS: frozenset[str] = frozenset({'-Infinity', '-Inf'})


def _fix_uniform_infinite_bounds(prior_elem: etree._Element) -> list[Change]:
    """
    Replace infinite lower/upper bounds on a Uniform inner distribution.

    BEAST3's Uniform distribution (backed by Apache Commons Statistics) requires
    finite bounds — Infinity is rejected at initAndValidate time.  Replace with a
    large finite sentinel and emit a WARNING so the user can review the value.
    """
    inner = _find_inner_distr(prior_elem)
    if inner is None:
        return []
    inner_spec = (inner.get('spec', '') or inner.tag or '').split('.')[-1]
    if inner_spec != 'Uniform':
        return []
    changes: list[Change] = []
    for attr, infinities, replacement in (
        ('upper', _INFINITE_BOUNDS,     '1.0E6'),
        ('lower', _NEG_INFINITE_BOUNDS, '-1.0E6'),
    ):
        val = inner.get(attr, '')
        if val in infinities:
            inner.set(attr, replacement)
            changes.append(Change(
                ChangeKind.WARNING,
                f'Uniform prior {attr}="{val}" → "{replacement}" — '
                'BEAST3 requires finite bounds; review and adjust this value',
            ))
    return changes


def _annotate_inner_distr(prior_elem: etree._Element, dep_map: dict[str, str]):
    """Stamp _b3spec on non-OneOnX inner distribution children of a Prior."""
    for child in prior_elem:
        if not isinstance(child.tag, str):
            continue
        child_spec = child.get('spec', '') or ''
        # BEAUti writes <LogNormal name="distr" .../> — no spec= attribute;
        # the element tag is the class short name (resolved via <map> in BEAST2).
        if not child_spec:
            child_spec = child.tag
        if child_spec.split('.')[-1] in _ONEONX_CLASSES:
            continue
        new_spec = resolve_spec(child_spec, dep_map)
        if new_spec:
            child.set('_b3spec', new_spec)


def _annotate_simple_rename(
    elem: etree._Element, dep_map: dict[str, str]
) -> list[Change]:
    """
    Check spec=, type=, class= for a full-FQN deprecated class and stamp the
    corresponding _b3spec/_b3type/_b3class annotation.

    Returns a list of Change objects (RENAME or TODO).
    """
    results: list[Change] = []

    for attr_name in ('spec', 'type', 'class'):
        attr_val = elem.get(attr_name, '') or ''
        if not attr_val:
            continue
        replacement = resolve_spec(attr_val, dep_map)
        if replacement is not None:
            elem.set(f'_b3{attr_name}', replacement)
            simple = attr_val.split('.')[-1]
            results.append(Change(
                ChangeKind.RENAME,
                f'{attr_name}= "{simple}" → "{replacement}"',
            ))
            if simple == 'TreeLikelihood':
                results.append(Change(
                    ChangeKind.WARNING,
                    'TreeLikelihood mapped to spec twin — '
                    'consider beast.base.spec.evolution.likelihood.ThreadedTreeLikelihood'
                    ' for multi-core performance',
                ))
            if simple == 'SubtreeSlide' and elem.get('gaussian') is not None:
                results.append(Change(
                    ChangeKind.WARNING,
                    'SubtreeSlide attr gaussian="'
                    + (elem.get('gaussian') or '')
                    + '" dropped — BactrianSubtreeSlide has no gaussian Input',
                ))
            break
        # Full FQN not in dep_map and not in DO_NOT_RENAME → no spec twin
        simple = attr_val.split('.')[-1]
        if (attr_val.startswith('beast.base.')
                and '.spec.' not in attr_val
                and simple not in DO_NOT_RENAME):
            results.append(Change(
                ChangeKind.TODO,
                f'{attr_name}= "{simple}"  — no spec twin in deprecated_classes.md',
            ))

    return results

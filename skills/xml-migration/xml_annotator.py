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
                         'simplex': _PARAM_PKG + 'IntSimplexParam'},
    'BooleanParameter': {'scalar': _PARAM_PKG + 'BoolScalarParam',
                         'vector': _PARAM_PKG + 'BoolVectorParam',
                         'simplex': _PARAM_PKG + 'BoolScalarParam'},
}


# Parameter "roles" (the token before the first '.' or ':' in id=) that BEAST2
# XML conventionally leaves with NO explicit lower=/upper= — old BEAST2 had no
# domain system, so these were only ever implicitly positive by modelling
# convention (a substitution-rate multiplier, a gamma shape parameter). With no
# bound to read, bounds-only inference falls through to unrestricted 'Real',
# which lets BEAST3 operators (e.g. DeltaExchangeOperator) push the value
# negative — confirmed to silently corrupt TreeLikelihood into a bogus positive
# log-density and eventually crash the chain with "positive infinite posterior"
# (starbeast2 tutorial/CanisFBD/Canis-FBD.xml, mutationRate.s:FGFR3 going
# negative around a DeltaExchangeOperator proposal). Keep this list narrow and
# unambiguous: unlike e.g. "growthRate" (can be negative — shrinking
# population), every role here is positive by definition in every BEAST2 model
# that uses it.
_KNOWN_POSITIVE_PARAM_ROLES = frozenset({'mutationRate', 'gammaShape'})


def _infer_domain(elem: etree._Element) -> tuple[str, bool]:
    """Map lower=/upper= attributes to a BEAST3 real domain class name.

    Returns (domain, name_inferred) where name_inferred is True only when the
    domain came from _KNOWN_POSITIVE_PARAM_ROLES rather than from an explicit
    lower=/upper= bound — callers should flag that case for manual review.
    """
    lower = elem.get('lower', '').strip()
    upper = elem.get('upper', '').strip()
    try:
        lo = float(lower) if lower else None
        hi = float(upper) if upper else None
    except ValueError:
        return 'Real', False
    if lo == 0.0 and hi == 1.0:
        return 'UnitInterval', False
    if lo is not None and lo >= 0.0 and (hi is None or hi > 1.0):
        # A finite but generous upper= (e.g. 10.0, 10000.0) is almost always an MCMC
        # operator safety cap, not a genuine domain restriction. Preserve the lower-bound
        # positivity constraint rather than silently widening to unrestricted Real, which
        # would let e.g. population sizes or rates go negative.
        return 'PositiveReal', False
    role = elem.get('id', '').split('.')[0].split(':')[0]
    if role in _KNOWN_POSITIVE_PARAM_ROLES:
        return 'PositiveReal', True
    return 'Real', False


def _infer_int_domain(elem: etree._Element) -> str:
    """Map lower= to a BEAST3 integer domain class name.

    A negative lower= must map to the unrestricted 'Int' domain — falling back
    to NonNegativeInt would reject the very values the source parameter allows.
    """
    lower = elem.get('lower', '').strip()
    try:
        lo = float(lower) if lower else None
    except ValueError:
        return 'NonNegativeInt'
    if lo is not None and lo >= 1:
        return 'PositiveInt'
    if lo is not None and lo < 0:
        return 'Int'
    return 'NonNegativeInt'


def _infer_shape(elem: etree._Element) -> str:
    """Return 'scalar', 'vector', or 'simplex' from dimension= / value= token count and context."""
    value = elem.get('value')
    if value is None:
        value = elem.text or ''
    value = value.strip()
    tokens = value.split() if value else []

    # Structural evidence of a simplex: the parameter sits inside, or is
    # referenced by, a <frequencies> element (annotate_simplex_refs stamps the
    # latter before prepass runs).
    parent = elem.getparent()
    parent_tag = parent.tag if (parent is not None and isinstance(parent.tag, str)) else ''
    if parent_tag.lower() == 'frequencies' or elem.get('_b3simplex') == '1':
        return 'simplex'

    # A fill value like value="0.0" with dimension="5" means five elements.
    # Check dimension first so single-token fill values are not misclassified as scalar.
    dimension = elem.get('dimension', '').strip()
    is_vector = len(tokens) > 1
    try:
        if dimension and int(dimension) > 1:
            is_vector = True
    except ValueError:
        pass

    # Name-only fallback: an id containing 'freq' is a simplex only if it is
    # also vector-shaped — a scalar (e.g. a sampling frequency) never is.
    if is_vector and 'freq' in elem.get('id', '').lower():
        return 'simplex'

    return 'vector' if is_vector else 'scalar'


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
      'oneonx'         — OneOnX inner distr → LogUniform(lower, upper) + WARNING
                         (wrapped in IID when x= is vector — annotate_vector_priors)
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
    # BEAUti tag-as-class style: <OneOnX name="distr"/> has no spec=.
    if not inner_spec and isinstance(inner.tag, str):
        inner_spec = inner.tag
    if inner_spec.split('.')[-1] == 'OneOnX' or inner_spec in _ONEONX_CLASSES:
        # OneOnX is improper; deprecated_classes.md names LogUniform as its proper
        # replacement (density ∝ 1/x on [lower, upper]).  No bounds can be derived
        # from the source, so placeholders are written and a WARNING asks the user
        # to set them per parameter — never guess them from the parameter's name.
        return 'oneonx'

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
            fxtemplate: bool,
            id_map: Optional[dict[str, etree._Element]] = None,
            alternatives: Optional[dict[str, list[str]]] = None) -> list[Change]:
    """
    Stamp the <beast> root annotations, then annotate every element
    (annotate_elements).

    id_map       — id → element across the whole file, INCLUDING elements inside
                   FxTemplate CDATA fragments; used to resolve the type of the
                   parameter an operator/compound acts on.
    alternatives — deprecated_map.parse_deprecated_alternatives() output; any
                   multi-replacement class not resolved by a type-aware rule
                   below gets a WARNING instead of a silent first-FQN pick.

    Returns a list of Change objects for the report.
    """
    root = tree.getroot()
    changes: list[Change] = []

    # version="2.8" is always required by BEAST3 — including FxTemplates, whose
    # own <run>/<subtemplate> fragments are parsed by the same version-gated
    # XMLParser once BEAUti merges them into a document. Only the namespace
    # rewrite (full FQNs replacing spec packages) is fxtemplate-specific: it
    # only makes sense for runnable example XMLs, so it's skipped here.
    orig_version = root.get('version', '?')
    root.set('_b3version', '2.8')
    if fxtemplate:
        root.set('_b3fxtemplate', '1')
        changes.append(Change(ChangeKind.INFO, f'version: {orig_version} → 2.8 (namespace left unchanged — FxTemplate)'))
    else:
        changes.append(Change(ChangeKind.INFO, f'version: {orig_version} → 2.8'))
        changes.append(Change(ChangeKind.INFO, 'namespace: updated (deprecated classes use full FQNs; no spec packages in namespace)'))

    changes.extend(annotate_elements(root, dep_map, id_map, alternatives))
    return changes


def annotate_elements(root: etree._Element, dep_map: dict[str, str],
                      id_map: Optional[dict[str, etree._Element]] = None,
                      alternatives: Optional[dict[str, list[str]]] = None) -> list[Change]:
    """
    Walk every element under root and stamp _b3* attributes that the XSLT reads.
    Used for the main document and for each parsed FxTemplate CDATA fragment.

    Returns a list of Change objects for the report.
    Skips comment and PI nodes (their get() ignores the default argument).
    """
    id_map = id_map if id_map is not None else {e.get('id'): e for e in root.iter() if e.get('id')}
    alternatives = alternatives or {}
    changes: list[Change] = []
    # Elements already consumed as children of a oneonx Prior (replaced wholesale
    # by the XSLT — processing them again would produce spurious rename changes).
    skip_elements: set[int] = set()
    # TODO class names already reported this file — suppress duplicates.
    seen_todos: set[str] = set()

    for elem in root.iter():
        if not isinstance(elem.tag, str):
            continue
        if id(elem) in skip_elements:
            continue

        spec_val = elem.get('spec', '') or ''
        spec_simple = spec_val.split('.')[-1]

        # --- logEvery on <logger> elements ---
        # Parameterise so the logging frequency can be overridden at the command
        # line (e.g. -D logEvery=100) independently of the default in the XML.
        # BEAST3 only allows one $(logEvery=N) default declaration per variable:
        # the first logger defines the default; subsequent ones reference $(logEvery).
        # Idempotent: already-converted values are left alone.
        # Bare <parameter> with no spec= is a legacy RealParameter. BEAST2 allows
        # the value to be given either as a value= attribute or as element text
        # content (e.g. <parameter name="popSize">1.0</parameter>) — both forms
        # must be recognised, or the parameter silently passes through unmigrated.
        bare = (elem.tag == 'parameter' and not spec_val
                and (elem.get('value') is not None
                     or (elem.text is not None and elem.text.strip() != '')))
        if bare:
            spec_simple = 'RealParameter'

        # --- Parameter migration ---
        if spec_simple in _PARAM_BASE_TYPES:
            shape = _infer_shape(elem)

            # IntegerParameter referenced by DeltaExchangeOperator via intparameter=
            # must become IntSimplexParam.  annotate_int_simplex_params() stamps
            # _b3int_simplex='1' before prepass runs.
            if spec_simple == 'IntegerParameter' and elem.get('_b3int_simplex') == '1':
                shape = 'simplex'

            b3spec = _param_spec(spec_simple, shape)
            elem.set('_b3spec', b3spec)
            qualifier = ' (bare tag)' if bare else ''

            if shape == 'simplex' and spec_simple != 'IntegerParameter':
                # Real-valued SimplexParam: no domain= input; T2s handles it.
                # dimension= is kept so BEAST3 can expand a single fill value.
                elem.set('_b3domain', 'simplex')
                dropped = [f'{a}="{elem.get(a)}"'
                           for a in ('lower', 'upper') if elem.get(a) is not None]
                drop_note = f'  dropped: {", ".join(dropped)}' if dropped else ''
                changes.append(Change(
                    ChangeKind.RENAME,
                    f'spec= "{spec_simple}"{qualifier} → "{b3spec}"{drop_note}',
                ))
            elif spec_simple == 'BooleanParameter':
                # BoolScalarParam/BoolVectorParam have no domain= input.
                # Sentinel 'boolean' routes to XSLT T2b which omits domain=.
                elem.set('_b3domain', 'boolean')
                dropped = [f'{a}="{elem.get(a)}"'
                           for a in ('lower', 'upper') if elem.get(a) is not None]
                drop_note = f'  dropped: {", ".join(dropped)}' if dropped else ''
                changes.append(Change(
                    ChangeKind.RENAME,
                    f'spec= "{spec_simple}"{qualifier} → "{b3spec}"{drop_note}',
                ))
            else:
                # RealParameter → PositiveReal/UnitInterval/Real domain.
                # IntegerParameter (scalar/vector/simplex) → PositiveInt/NonNegativeInt domain.
                name_inferred = False
                if spec_simple == 'IntegerParameter':
                    domain = _infer_int_domain(elem)
                else:
                    domain, name_inferred = _infer_domain(elem)
                elem.set('_b3domain', domain)
                dropped = [f'{a}="{elem.get(a)}"'
                           for a in ('lower', 'upper') if elem.get(a) is not None]
                drop_note = f'  dropped: {", ".join(dropped)}' if dropped else ''
                if name_inferred:
                    changes.append(Change(
                        ChangeKind.WARNING,
                        f'spec= "{spec_simple}"{qualifier} → "{b3spec}"  domain="{domain}"'
                        f' — no lower=/upper= in source; inferred PositiveReal from'
                        f' parameter role "{elem.get("id", "").split(".")[0].split(":")[0]}".'
                        f' Verify this parameter is genuinely always positive.',
                    ))
                else:
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
        if ptype == 'oneonx':
            # oneonx priors replace all children wholesale via the XSLT template;
            # mark children so the main loop skips them and avoids spurious renames.
            _stamp_oneonx(elem)
            for child in elem:
                skip_elements.add(id(child))
            continue
        if ptype is not None:
            elem.set('_b3prior_type', ptype)
            changes.extend(_annotate_inner_distr(elem, dep_map, alternatives))
            changes.extend(_fix_uniform_infinite_bounds(elem))
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

        # --- Standalone OneOnX: reuse the oneonx → LogUniform path ---
        # OneOnX inside a Prior is already handled above (_prior_type detects it
        # and marks the child in skip_elements).  Any OneOnX that reaches here
        # is standalone — stamp it so T3c in the XSLT writes LogUniform with
        # explicit placeholder bounds.
        if spec_simple == 'OneOnX':
            _stamp_oneonx(elem)
            continue

        # --- UniformOperator: split by the TYPE of the parameter it acts on ---
        # dep_map's first FQN (IntUniformOperator) is only right for integer
        # targets; never take it without looking at the target.
        if spec_simple == 'UniformOperator':
            changes.extend(_resolve_uniform_operator(elem, id_map))
            continue

        # --- Gamma: split by mode= (Gamma vs GammaMean) and rename beta= ---
        # A Prior's inner Gamma was already converted by _annotate_inner_distr —
        # converting again would misread the renamed inputs.
        if spec_simple == 'Gamma' and '.spec.' not in spec_val:
            if elem.get('_b3spec') is None:
                changes.extend(_convert_gamma(elem))
            continue

        # --- CompoundRealParameter: only scalar real children are supported ---
        if spec_simple == 'CompoundRealParameter':
            changes.extend(_check_compound_real(elem, id_map))
            # fall through to the simple rename (→ CompoundRealScalarParam)

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
        for change in _annotate_simple_rename(elem, dep_map, alternatives):
            if change.kind == ChangeKind.TODO:
                if change.description in seen_todos:
                    continue
                seen_todos.add(change.description)
            changes.append(change)

    return changes


def annotate_int_simplex_params(root: etree._Element, id_map: dict[str, etree._Element]) -> None:
    """
    Stamp _b3int_simplex='1' on IntegerParameter elements that supply groupSizes to
    a DeltaExchangeOperator via intparameter=.  Must be called BEFORE prepass() so the
    annotation is visible when prepass decides the shape (→ IntSimplexParam).
    """
    for elem in root.iter():
        if not isinstance(elem.tag, str):
            continue
        spec = elem.get('spec', '') or ''
        if 'DeltaExchangeOperator' not in spec:
            continue
        ref = elem.get('intparameter', '').lstrip('@')
        if ref and ref in id_map:
            id_map[ref].set('_b3int_simplex', '1')


def annotate_simplex_refs(root: etree._Element, id_map: dict[str, etree._Element]) -> None:
    """
    Stamp _b3simplex='1' on parameters referenced by a <frequencies> element's
    frequencies= attribute, so _infer_shape() can classify them as a simplex
    from structure rather than from the parameter's name.  Call before prepass().
    """
    for elem in root.iter():
        if not isinstance(elem.tag, str):
            continue
        if elem.tag != 'frequencies' and (elem.get('spec', '') or '').split('.')[-1] != 'Frequencies':
            continue
        ref = (elem.get('frequencies', '') or '').lstrip('@')
        if ref and ref in id_map:
            id_map[ref].set('_b3simplex', '1')


def annotate_vector_priors(root: etree._Element, id_map: dict[str, etree._Element]):
    """
    Upgrade 'flatten' Prior annotations to 'iid' when the referenced x= param
    is vector-shaped.  Must run after prepass() because it relies on id_map.
    """
    for elem in root.iter():
        ptype = elem.get('_b3prior_type')
        if ptype not in ('flatten', 'oneonx'):
            continue
        x_ref = elem.get('x', '').lstrip('@')
        if not x_ref:
            continue
        target = id_map.get(x_ref)
        if target is None:
            continue
        if _infer_shape(target) != 'scalar':
            # OneOnX keeps its own type; T3c wraps LogUniform in IID itself.
            if ptype == 'flatten':
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

# Placeholder LogUniform bounds written for every OneOnX (see b2_to_b3.xsl T3c).
# Deliberately wide so the converted prior stays close to the improper 1/x it
# replaces; the WARNING asks the user to narrow them to the plausible range of
# each parameter (e.g. StarBEAST2 popMean uses [1.0E-6, 0.5]).
ONEONX_LOGUNIFORM_BOUNDS: tuple[str, str] = ('1.0E-6', '1.0E6')


def _stamp_oneonx(elem: etree._Element) -> None:
    """Mark elem for XSLT T3c and hand it the LogUniform bounds to write."""
    elem.set('_b3prior_type', 'oneonx')
    elem.set('_b3lower', ONEONX_LOGUNIFORM_BOUNDS[0])
    elem.set('_b3upper', ONEONX_LOGUNIFORM_BOUNDS[1])

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
    # oneonx
    lo, hi = ONEONX_LOGUNIFORM_BOUNDS
    # Standalone OneOnX (element itself is OneOnX, not a Prior wrapper).
    spec = (elem.get('spec', '') or '').split('.')[-1]
    if spec == 'OneOnX':
        return Change(
            ChangeKind.WARNING,
            f'spec= "OneOnX" standalone → "LogUniform"  lower={lo}  upper={hi}'
            '  — set param= to the target parameter; set lower/upper to its plausible range',
        )
    x_ref = elem.get('x', '') or ''
    iid = '"IID" of ' if elem.get('_b3vector_x') == '1' else ''
    return Change(
        ChangeKind.WARNING,
        f'spec= "Prior+OneOnX" ({x_ref}) → {iid}"LogUniform"  lower={lo}  upper={hi}'
        '  — placeholder bounds; set lower/upper to the plausible range of this parameter',
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


def _annotate_inner_distr(prior_elem: etree._Element, dep_map: dict[str, str],
                          alternatives: Optional[dict[str, list[str]]] = None) -> list[Change]:
    """Stamp _b3spec on non-OneOnX inner distribution children of a Prior."""
    changes: list[Change] = []
    for child in prior_elem:
        if not isinstance(child.tag, str):
            continue
        child_spec = child.get('spec', '') or ''
        # BEAUti writes <LogNormal name="distr" .../> — no spec= attribute;
        # the element tag is the class short name (resolved via <map> in BEAST2).
        if not child_spec:
            child_spec = child.tag
        simple = child_spec.split('.')[-1]
        if simple in _ONEONX_CLASSES:
            continue
        if simple == 'Gamma' and '.spec.' not in child_spec:
            changes.extend(_convert_gamma(child))
            continue
        new_spec = resolve_spec(child_spec, dep_map)
        if new_spec:
            child.set('_b3spec', new_spec)
            changes.extend(_unresolved_alternatives(child_spec, new_spec, alternatives))
    return changes


# ---------------------------------------------------------------------------
# Type-aware resolution of multi-replacement classes
# ---------------------------------------------------------------------------
#
# deprecated_classes.md lists several replacements for some classes and
# deprecated_map stores only the first.  Every such class must either be
# resolved here from the actual types involved, or reported by
# _unresolved_alternatives() — never silently take the first FQN.

_UNIFORM_PKG = 'beast.base.spec.inference.operator.uniform.'
_DISTR_PKG = 'beast.base.spec.inference.distribution.'

# Classes whose multiple replacements are chosen by a dedicated rule (shape,
# parameter=/tree=, target type, mode=) — no generic warning for these.
_TYPE_RESOLVED: frozenset[str] = frozenset({
    'RealParameter', 'IntegerParameter', 'BooleanParameter',
    'ScaleOperator', 'BactrianScaleOperator',
    'UniformOperator', 'Gamma', 'CompoundRealParameter',
})


def _unresolved_alternatives(spec_val: str, chosen: str,
                             alternatives: Optional[dict[str, list[str]]]) -> list[Change]:
    """WARNING for a multi-replacement class that no type-aware rule resolved."""
    if not alternatives:
        return []
    simple = spec_val.split('.')[-1]
    if simple in _TYPE_RESOLVED:
        return []
    options = alternatives.get(spec_val) or alternatives.get(simple)
    if not options:
        return []
    others = ', '.join(f'"{o}"' for o in options if o != chosen)
    return [Change(
        ChangeKind.WARNING,
        f'spec= "{simple}" → "{chosen}" is only the FIRST of {len(options)} replacements '
        f'in deprecated_classes.md; check the target type — alternatives: {others}',
    )]


def _ref_target(elem: etree._Element, input_name: str,
                id_map: dict[str, etree._Element]) -> tuple[Optional[str], Optional[etree._Element]]:
    """
    Find the element supplied to input `input_name` of elem: an attribute
    `input_name="@id"`, a child <input_name idref="id"/>, a child with
    name="input_name" (idref= or inline), or an inline child <input_name .../>.
    Returns (reference label, resolved element or None).
    """
    ref = (elem.get(input_name, '') or '').strip()
    if ref.startswith('@'):
        return ref, id_map.get(ref[1:])
    for child in elem:
        if not isinstance(child.tag, str):
            continue
        if child.tag != input_name and child.get('name') != input_name:
            continue
        idref = child.get('idref')
        if idref:
            return '@' + idref, id_map.get(idref)
        return child.get('id', f'<{child.tag}>'), child
    return (ref or None), None


def _param_kind(target: Optional[etree._Element]) -> tuple[Optional[str], Optional[str]]:
    """
    Return (kind, domain) of a parameter element: kind is 'real', 'int', 'bool'
    or None (unknown); domain is the BEAST3 domain it will have after
    conversion (None when unknown or boolean).  Reads the ORIGINAL spec=, so it
    works before or after the pre-pass has annotated the target.
    """
    if target is None:
        return None, None
    spec = (target.get('spec', '') or '').split('.')[-1]
    if not spec and target.tag == 'parameter':
        spec = 'RealParameter'          # bare <parameter> is a legacy RealParameter
    if spec == 'RealParameter':
        if _infer_shape(target) == 'simplex':
            return 'real', 'simplex'
        return 'real', _infer_domain(target)[0]
    if spec == 'IntegerParameter':
        return 'int', _infer_int_domain(target)
    if spec == 'BooleanParameter' or spec.startswith('Bool'):
        return 'bool', None
    if spec.startswith('Int') and spec.endswith('Param'):
        return 'int', target.get('domain')
    if spec.startswith(('Real', 'Simplex', 'CompoundReal')) and spec.endswith('Param'):
        return 'real', target.get('domain', 'simplex' if spec == 'SimplexParam' else None)
    return None, None


def _resolve_uniform_operator(elem: etree._Element,
                              id_map: dict[str, etree._Element]) -> list[Change]:
    """
    BEAST2 UniformOperator accepted real OR integer parameters; BEAST3 splits it:
      integer target → IntUniformOperator (keeps howMany=)
      real target    → IntervalOperator   (no howMany=; needs finite domain bounds)
    The choice is made from the target's type.  An unresolvable target defaults
    to IntervalOperator (real targets are by far the common case) with a WARNING.
    """
    ref, target = _ref_target(elem, 'parameter', id_map)
    kind, domain = _param_kind(target)
    changes: list[Change] = []

    if kind == 'int':
        elem.set('_b3spec', _UNIFORM_PKG + 'IntUniformOperator')
        changes.append(Change(
            ChangeKind.RENAME,
            f'spec= "UniformOperator" ({ref}: integer) → "{_UNIFORM_PKG}IntUniformOperator"',
        ))
        return changes

    if kind == 'bool':
        changes.append(Change(
            ChangeKind.TODO,
            f'spec= "UniformOperator" ({ref}) acts on a boolean parameter — '
            'no BEAST3 uniform operator; use beast.base.spec.inference.operator.BitFlipOperator',
        ))
        return changes

    elem.set('_b3spec', _UNIFORM_PKG + 'IntervalOperator')
    how_many = elem.get('howMany')
    if how_many is not None:
        del elem.attrib['howMany']
    if kind is None:
        changes.append(Change(
            ChangeKind.WARNING,
            f'spec= "UniformOperator" → "{_UNIFORM_PKG}IntervalOperator" assumed — '
            f'target {ref or "(none)"} not found in this file, so its type is unknown; '
            f'use "{_UNIFORM_PKG}IntUniformOperator" instead if it is an integer parameter',
        ))
    else:
        changes.append(Change(
            ChangeKind.RENAME,
            f'spec= "UniformOperator" ({ref}: real) → "{_UNIFORM_PKG}IntervalOperator"',
        ))
        if domain not in ('UnitInterval', None):
            changes.append(Change(
                ChangeKind.WARNING,
                f'IntervalOperator on {ref} needs finite domain bounds, but its domain is '
                f'"{domain}" — BEAST3 will reject it at initAndValidate; give the parameter '
                'a bounded domain or use ScaleOperator/RealRandomWalkOperator instead',
            ))
    if how_many not in (None, '1'):
        changes.append(Change(
            ChangeKind.WARNING,
            f'UniformOperator howMany="{how_many}" dropped — IntervalOperator moves one '
            'dimension per proposal; raise the operator weight if needed',
        ))
    return changes


def _check_compound_real(elem: etree._Element,
                         id_map: dict[str, etree._Element]) -> list[Change]:
    """
    BEAST2 CompoundRealParameter only ever held RealParameters, so its first
    replacement (CompoundRealScalarParam) is the right class — but that class
    only accepts SCALAR real parameters.  Flag any vector or non-real child.
    """
    changes: list[Change] = []
    for child in elem:
        if not isinstance(child.tag, str):
            continue
        if child.tag != 'parameter' and child.get('name') != 'parameter':
            continue
        idref = child.get('idref')
        target = id_map.get(idref) if idref else child
        label = '@' + idref if idref else child.get('id', '<parameter>')
        kind, _ = _param_kind(target)
        if kind == 'real' and _infer_shape(target) == 'scalar':
            continue
        what = 'not found' if target is None else (
            f'a {kind} parameter' if kind not in ('real', None) else 'not a scalar real parameter')
        changes.append(Change(
            ChangeKind.WARNING,
            f'CompoundRealParameter child {label} is {what} — CompoundRealScalarParam '
            'only accepts RealScalarParam children; restructure by hand',
        ))
    return changes


# BEAST2 Gamma: beta= meaning depends on mode= (default ShapeScale).
# BEAST3 Gamma has theta= (scale) XOR lambda= (rate); GammaMean has mean=.
_GAMMA_MODE: dict[str, tuple[str, Optional[str]]] = {
    'ShapeScale': ('Gamma', 'theta'),
    'ShapeRate':  ('Gamma', 'lambda'),
    'ShapeMean':  ('GammaMean', 'mean'),
}


def _rename_input(elem: etree._Element, old: str, new: str) -> bool:
    """Rename input `old` → `new` whether given as attribute, name= child or tag child."""
    if elem.get(old) is not None:
        elem.set(new, elem.get(old))
        del elem.attrib[old]
        return True
    for child in elem:
        if not isinstance(child.tag, str):
            continue
        if child.get('name') == old:
            child.set('name', new)
            return True
        if child.tag == old:
            child.tag = new
            return True
    return False


def _has_input(elem: etree._Element, name: str) -> bool:
    return elem.get(name) is not None or any(
        isinstance(c.tag, str) and (c.tag == name or c.get('name') == name) for c in elem)


def _convert_gamma(elem: etree._Element) -> list[Change]:
    """
    Convert a BEAST2 Gamma (alpha, beta, mode) to BEAST3 Gamma/GammaMean, in place.
    BEAST2 defaults (alpha=2, beta=2 read as a scale in every mode) are written
    out explicitly, because BEAST3 defaults differ and Gamma needs theta XOR lambda.
    """
    changes: list[Change] = []
    mode = elem.get('mode', 'ShapeScale')
    if 'mode' in elem.attrib:
        del elem.attrib['mode']

    if not _has_input(elem, 'alpha'):
        elem.set('alpha', '2.0')
        changes.append(Change(ChangeKind.WARNING,
                              'Gamma: no alpha= in source — wrote BEAST2 default alpha="2.0"'))

    if mode == 'OneParameter':
        # BEAST2 ignores beta and uses scale = 1/alpha.
        for c in [c for c in elem if isinstance(c.tag, str)
                  and (c.tag == 'beta' or c.get('name') == 'beta')]:
            elem.remove(c)
        elem.attrib.pop('beta', None)
        elem.set('_b3spec', _DISTR_PKG + 'Gamma')
        try:
            elem.set('theta', repr(1.0 / float(elem.get('alpha', ''))))
            changes.append(Change(ChangeKind.WARNING,
                                  'Gamma mode="OneParameter" → "Gamma" with theta=1/alpha (beta dropped)'))
        except ValueError:
            changes.append(Change(ChangeKind.TODO,
                                  'Gamma mode="OneParameter" with non-literal alpha — BEAST3 has no '
                                  'one-parameter Gamma; set theta = 1/alpha by hand'))
        return changes

    if mode not in _GAMMA_MODE:
        changes.append(Change(ChangeKind.TODO, f'Gamma: unknown mode="{mode}" — convert by hand'))
        return changes

    cls, beta_name = _GAMMA_MODE[mode]
    if _rename_input(elem, 'beta', beta_name):
        elem.set('_b3spec', _DISTR_PKG + cls)
        changes.append(Change(
            ChangeKind.RENAME,
            f'Gamma (mode={mode}) → "{_DISTR_PKG}{cls}"  beta → {beta_name}',
        ))
    else:
        # Missing beta: BEAST2 uses 2.0 as the SCALE regardless of mode.
        elem.set('_b3spec', _DISTR_PKG + 'Gamma')
        elem.set('theta', '2.0')
        changes.append(Change(
            ChangeKind.WARNING,
            f'Gamma (mode={mode}) with no beta= → "{_DISTR_PKG}Gamma" theta="2.0" (BEAST2 default scale)',
        ))
    return changes


def _annotate_simple_rename(
    elem: etree._Element, dep_map: dict[str, str],
    alternatives: Optional[dict[str, list[str]]] = None,
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
            results.extend(_unresolved_alternatives(attr_val, replacement, alternatives))
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

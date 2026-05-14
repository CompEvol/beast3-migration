#!/usr/bin/env python3
"""
Parameter conversion rules for BEAST2 → BEAST3 XML migration.

Maps legacy RealParameter / IntegerParameter / BooleanParameter to
the BEAST3 typed parameter classes based on value shape and bounds,
following the rules in parameters.md.
"""

from __future__ import annotations
from typing import NamedTuple

# Fully-qualified names for the BEAST3 typed parameter classes
_PKG = "beast.base.spec.inference.parameter"
_DOM = "beast.base.spec.domain"


class ParamConversion(NamedTuple):
    spec_fq: str              # e.g. beast.base.spec.inference.parameter.RealScalarParam
    domain_fq: str | None     # e.g. beast.base.spec.domain.PositiveReal (None for Int/Bool)
    domain_simple: str | None # simple name for XML domain= attribute, e.g. "PositiveReal"
    note: str                 # human-readable note for the conversion report


# Legacy simple names that this module handles
PARAMETER_CLASSES = {"RealParameter", "IntegerParameter", "BooleanParameter"}


# ---------------------------------------------------------------------------
# Internal helpers
# ---------------------------------------------------------------------------

def _count_values(value: str | None) -> int:
    if not value:
        return 1
    return len(value.strip().split())


def _real_domain(lower: str | None, upper: str | None) -> tuple[str, str]:
    """Return (domain_fq, domain_simple) from lower/upper attribute strings."""
    if lower is not None and upper is not None:
        try:
            if float(lower) == 0.0 and float(upper) == 1.0:
                return f"{_DOM}.UnitInterval", "UnitInterval"
        except ValueError:
            pass

    if lower is not None and upper is None:
        try:
            if float(lower) >= 0.0:
                # lower="0" maps to PositiveReal — matches beast3 spec examples
                # (parameters like kappa, rates, sizes are strictly positive in practice)
                return f"{_DOM}.PositiveReal", "PositiveReal"
        except ValueError:
            pass

    return f"{_DOM}.Real", "Real"


# ---------------------------------------------------------------------------
# Public converters
# ---------------------------------------------------------------------------

def convert_real(
    value: str | None,
    lower: str | None,
    upper: str | None,
) -> ParamConversion:
    n = _count_values(value)
    domain_fq, domain_name = _real_domain(lower, upper)

    if n == 1:
        spec = f"{_PKG}.RealScalarParam"
        note = f"RealScalarParam<{domain_name}>"
    else:
        spec = f"{_PKG}.RealVectorParam"
        note = f"RealVectorParam<{domain_name}> (dim={n})"

    return ParamConversion(spec, domain_fq, domain_name, note)


def convert_integer(value: str | None) -> ParamConversion:
    n = _count_values(value)
    if n == 1:
        return ParamConversion(f"{_PKG}.IntScalarParam", None, None, "IntScalarParam")
    return ParamConversion(f"{_PKG}.IntVectorParam", None, None, f"IntVectorParam (dim={n})")


def convert_boolean(value: str | None) -> ParamConversion:
    n = _count_values(value)
    if n == 1:
        return ParamConversion(f"{_PKG}.BoolScalarParam", None, None, "BoolScalarParam")
    return ParamConversion(f"{_PKG}.BoolVectorParam", None, None, f"BoolVectorParam (dim={n})")


def convert_parameter(class_name: str, attrs: dict[str, str]) -> ParamConversion | None:
    """
    Convert a legacy parameter class given the element's XML attributes.
    Returns None if class_name is not a known parameter class.
    """
    if class_name == "RealParameter":
        return convert_real(attrs.get("value"), attrs.get("lower"), attrs.get("upper"))
    if class_name == "IntegerParameter":
        return convert_integer(attrs.get("value"))
    if class_name == "BooleanParameter":
        return convert_boolean(attrs.get("value"))
    return None

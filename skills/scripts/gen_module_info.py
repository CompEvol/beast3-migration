#!/usr/bin/env python3
"""
gen_module_info.py — Generate src/main/java/module-info.java for a BEAST3 package,
then cross-check it against pom.xml and version.xml for version and provider consistency.

Usage:
    python scripts/gen_module_info.py [project_root]

    project_root defaults to the current working directory.

Output:
    src/main/java/module-info.java  (written / overwritten)
    Console report of consistency issues between pom.xml, version.xml, module-info.java

Detection heuristics (source-level, no compilation needed):
    A .java file is treated as a BEASTInterface provider when it is:
      - a concrete (non-abstract) top-level class, AND
      - carries @Description(...), OR
      - extends a known BEAST base class, OR
      - directly implements BEASTInterface
    Inner classes, interfaces, and @interface annotations are excluded.
    Abstract classes are excluded (JPMS requires concrete providers with no-arg constructors).

See also: ../beast3/beast-base/src/main/java/module-info.java for a full example.
"""

import re
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

# ---------------------------------------------------------------------------
# Source-level heuristics
# ---------------------------------------------------------------------------

_DESCRIPTION  = re.compile(r'@Description\s*\(')
_BEAST_BASES  = re.compile(
    r'\bextends\s+('
    r'BEASTObject|BEASTVersion\w*|CalculationNode|Distribution|'
    r'TreeDistribution|Operator|Logger|DataType\w*|'
    r'SubstitutionModel\w*|SiteModelInterface\w*|BranchRateModel\w*|'
    r'RealParameter|IntegerParameter|BooleanParameter'
    r')\b'
)
_IMPLEMENTS_BI = re.compile(r'\bimplements\b[^{]*\bBEASTInterface\b')
_ABSTRACT      = re.compile(r'\babstract\s+class\b')
_INTERFACE     = re.compile(r'(?:^|\s)(?:public\s+)?interface\s+\w', re.MULTILINE)
_ANNOTATION    = re.compile(r'(?:^|\s)(?:public\s+)?@interface\s+\w', re.MULTILINE)
_PACKAGE       = re.compile(r'^package\s+([\w.]+)\s*;', re.MULTILINE)


def _is_provider(source: str) -> bool:
    if _ABSTRACT.search(source):
        return False
    if _INTERFACE.search(source) or _ANNOTATION.search(source):
        return False
    return bool(
        _DESCRIPTION.search(source) or
        _BEAST_BASES.search(source) or
        _IMPLEMENTS_BI.search(source)
    )


def scan_sources(src_java: Path) -> tuple[list[str], list[str]]:
    """Return (sorted providers FQCNs, sorted package names)."""
    providers: list[str] = []
    packages:  set[str]  = set()

    for java_file in sorted(src_java.rglob("*.java")):
        if java_file.name == "module-info.java":
            continue
        source = java_file.read_text(encoding="utf-8", errors="ignore")
        m = _PACKAGE.search(source)
        if not m:
            continue
        package = m.group(1)
        packages.add(package)
        if _is_provider(source):
            providers.append(f"{package}.{java_file.stem}")

    return sorted(providers), sorted(packages)


# ---------------------------------------------------------------------------
# pom.xml reader
# ---------------------------------------------------------------------------

_POM_NS = {"m": "http://maven.apache.org/POM/4.0.0"}


def _pom_text(root: ET.Element, tag: str) -> str:
    el = root.find(f"m:{tag}", _POM_NS)
    return (el.text or "").strip() if el is not None else ""


def read_pom(pom_path: Path) -> dict:
    root = ET.parse(pom_path).getroot()

    props: dict[str, str] = {}
    for p in root.findall("m:properties/m:*", _POM_NS):
        props[p.tag.split("}")[1]] = (p.text or "").strip()

    def resolve(val: str) -> str:
        for k, v in props.items():
            val = val.replace(f"${{{k}}}", v)
        return val

    deps: dict[str, str] = {}
    for dep in root.findall(".//m:dependency", _POM_NS):
        g = dep.findtext("m:groupId",    "", _POM_NS)
        a = dep.findtext("m:artifactId", "", _POM_NS)
        v = resolve(dep.findtext("m:version", "", _POM_NS))
        deps[f"{g}:{a}"] = v

    parent = root.find("m:parent", _POM_NS)
    parent_artifact_id = (
        parent.findtext("m:artifactId", "", _POM_NS) if parent is not None else ""
    )
    artifact_id = _pom_text(root, "artifactId")
    # Own groupId falls back to the parent's when inherited (common for
    # reactor submodules, which often omit <groupId> entirely).
    group_id = _pom_text(root, "groupId") or (
        parent.findtext("m:groupId", "", _POM_NS) if parent is not None else ""
    )

    return {
        "groupId":            group_id,
        "artifactId":         artifact_id,
        "parent_artifactId":  parent_artifact_id,
        "version":            resolve(_pom_text(root, "version")),
        # JPMS module name = artifactId with hyphens as dots. This matches
        # every standalone package checked (beast-labs -> beast.labs,
        # beast-base -> beast.base, beast-fx -> beast.fx, beast-pkgmgmt ->
        # beast.pkgmgmt) even though those all have a <parent> too — so
        # "has a parent" alone does not mean "derive from parent artifactId".
        # It is NOT reliable for a multi-module reactor that intentionally
        # shares one public module name across submodules (e.g. csm-base and
        # csm-fx are both under the reactor artifactId "codonsubstmodels",
        # not their own "csm-base"/"csm-fx") -- that is a package-specific
        # choice, not something derivable from pom.xml alone. main() prints
        # a warning when a <parent> is present so this gets a manual check.
        "module_name":        artifact_id.replace("-", "."),
        "deps":               deps,
    }


# ---------------------------------------------------------------------------
# version.xml reader
# ---------------------------------------------------------------------------

def read_version_xml(version_path: Path) -> dict:
    root = ET.parse(version_path).getroot()
    providers: list[str] = []
    for svc in root.findall(".//service"):
        for p in svc.findall("provider"):
            cn = p.get("classname", "")
            if cn:
                providers.append(cn)
    depends: list[dict] = [
        {"on": d.get("on", ""), "atleast": d.get("atleast", "")}
        for d in root.findall("depends")
    ]
    return {
        "name":      root.get("name",    ""),
        "version":   root.get("version", ""),
        "providers": providers,
        "depends":   depends,
    }


# ---------------------------------------------------------------------------
# module-info.java generator
# ---------------------------------------------------------------------------

def generate_module_info(module_name: str, packages: list[str], providers: list[str]) -> str:
    exports = "\n".join(f"    exports {p};" for p in packages)

    # No "uses beast.base.core.BEASTInterface;" here: BEASTClassLoader
    # discovers providers by reading module-info "provides" declarations
    # directly (reflection over ModuleDescriptor), not via
    # java.util.ServiceLoader.load(). "uses" is only required on a module
    # that itself calls ServiceLoader.load() — see "Why no uses clause"
    # in module-info.md.
    service_block = ""
    if providers:
        provides = ",\n        ".join(providers)
        service_block = (
            f"\n    provides beast.base.core.BEASTInterface with\n"
            f"        {provides};\n"
        )

    return (
        f"open module {module_name} {{\n"
        f"    requires beast.pkgmgmt;\n"
        f"    requires beast.base;\n"
        f"\n"
        f"{exports}\n"
        f"{service_block}"
        f"}}\n"
    )


# ---------------------------------------------------------------------------
# Consistency checker
# ---------------------------------------------------------------------------

_PRE_RELEASE = re.compile(r"alpha|beta|rc|snapshot", re.IGNORECASE)


def check_consistency(pom: dict, vxml: dict, providers: list[str]) -> list[str]:
    issues: list[str] = []
    pom_ver  = pom["version"]
    vxml_ver = vxml["version"]

    # --- Version alignment ---
    if pom_ver != vxml_ver:
        issues.append(
            f"VERSION MISMATCH: pom.xml='{pom_ver}' vs version.xml='{vxml_ver}'"
        )

    # --- SNAPSHOT warning (version.xml must never be a snapshot) ---
    if "SNAPSHOT" in pom_ver.upper():
        issues.append(
            f"SNAPSHOT: pom.xml version '{pom_ver}' is a SNAPSHOT — "
            "keep version.xml at the last formal release until ready to publish"
        )

    # --- Beta / alpha / RC warning ---
    for label, source in ((pom_ver, "pom.xml"), (vxml_ver, "version.xml")):
        if _PRE_RELEASE.search(label):
            issues.append(
                f"PRE-RELEASE WARNING [{source}]: '{label}' — "
                "version.xml should only contain formal release versions"
            )

    # --- Provider alignment: version.xml ↔ module-info.java ---
    vxml_set = set(vxml["providers"])
    mod_set  = set(providers)

    for cls in sorted(vxml_set - mod_set):
        issues.append(
            f"IN version.xml BUT NOT module-info: {cls} — "
            "verify the class still exists; add to provides or remove from version.xml"
        )
    for cls in sorted(mod_set - vxml_set):
        issues.append(
            f"IN module-info BUT NOT version.xml: {cls} — "
            f'add  <provider classname="{cls}"/>  inside the <service> block in version.xml'
        )

    # --- BEAST.base version alignment: pom.xml dep ↔ version.xml atleast ---
    beast_dep = pom["deps"].get("io.github.compevol:beast-base", "")
    for dep in vxml["depends"]:
        if dep["on"] in ("BEAST.base", "beast-base", "beast.base"):
            vxml_beast = dep["atleast"]
            if beast_dep and vxml_beast and beast_dep != vxml_beast:
                issues.append(
                    f"BEAST.base VERSION MISMATCH: "
                    f"pom.xml depends on '{beast_dep}', "
                    f"version.xml requires atleast '{vxml_beast}'"
                )

    return issues


# ---------------------------------------------------------------------------
# Entry point
# ---------------------------------------------------------------------------

def main() -> None:
    project_root = Path(sys.argv[1]) if len(sys.argv) > 1 else Path(".")
    src_java     = project_root / "src" / "main" / "java"
    pom_path     = project_root / "pom.xml"
    version_path = project_root / "version.xml"
    out_path     = src_java / "module-info.java"

    # 1. Scan sources
    print(f"Scanning {src_java} ...")
    providers, packages = scan_sources(src_java)
    print(f"  {len(providers)} provider(s) found across {len(packages)} package(s)")
    for p in providers:
        print(f"    + {p}")

    # 2. Read pom.xml
    if not pom_path.exists():
        sys.exit(f"ERROR: {pom_path} not found — run maven-setup first")
    pom = read_pom(pom_path)
    module_name = pom["module_name"]
    print(f"  Module name (from artifactId '{pom['artifactId']}'): {module_name}")
    if pom["parent_artifactId"]:
        print(
            f"  NOTE: pom.xml has <parent> artifactId='{pom['parent_artifactId']}'. "
            "In a multi-module reactor, the JPMS module name is sometimes shared "
            "across submodules (e.g. codonsubstmodels / codonsubstmodels.fx) "
            "rather than derived from this module's own artifactId — verify "
            "against a sibling module-info.java or version.xml <package name> "
            "before trusting the default above."
        )

    # 3. Write module-info.java
    content = generate_module_info(module_name, packages, providers)
    out_path.write_text(content, encoding="utf-8")
    print(f"  Written: {out_path}")

    # 4. Cross-check
    print()
    if not version_path.exists():
        print(f"WARN: {version_path} not found — skipping consistency check")
        return

    vxml   = read_version_xml(version_path)
    issues = check_consistency(pom, vxml, providers)

    if issues:
        print(f"{len(issues)} consistency issue(s):")
        for issue in issues:
            print(f"  ⚠  {issue}")
    else:
        print("✓ pom.xml, version.xml, and module-info.java are consistent")


if __name__ == "__main__":
    main()

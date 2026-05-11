#!/usr/bin/env python3
"""Regenerate BACKLOG.md from CBAN's v2.7 and v2.8 package manifests.

Fetches packages2.7.xml, packages-extra-2.7.xml, and packages2.8.xml from
CBAN, cross-references them with the local packages.yaml, and writes a
Markdown backlog of v2.7 packages that have not yet been published to
packages2.8.xml.

Usage:
    python3 scripts/gen-backlog.py            # fetch from CBAN and rewrite BACKLOG.md
    python3 scripts/gen-backlog.py --offline  # use cached files under .cache/
"""
from __future__ import annotations

import argparse
import os
import re
import sys
import urllib.request
from datetime import datetime, timezone
from pathlib import Path

try:
    import yaml
except ImportError:
    sys.stderr.write("pyyaml is required: pip install pyyaml\n")
    sys.exit(1)

REPO_ROOT = Path(__file__).resolve().parent.parent
CACHE_DIR = REPO_ROOT / ".cache"

CBAN_BASE = "https://raw.githubusercontent.com/CompEvol/CBAN/master"
SOURCES = {
    "packages2.7.xml": f"{CBAN_BASE}/packages2.7.xml",
    "packages-extra-2.7.xml": f"{CBAN_BASE}/packages-extra-2.7.xml",
    "packages2.8.xml": f"{CBAN_BASE}/packages2.8.xml",
}

# Packages that existed as standalone entries in v2.7 but are built into
# beast3 core in v2.8 — these are not separate "packages to migrate".
CORE_IN_V28 = {"BEAST.base", "BEAST.app"}

# Aliases between CBAN package names and packages.yaml entries.
# (CBAN name -> packages.yaml name)
YAML_ALIASES = {
    "BEAST_CLASSIC": "beast-classic",
    "MM": "morph-models",
    "SA": "sampled-ancestors",
    "OBAMA": "obama",
    "FLC": "flc",
}


def fetch(name: str, url: str, offline: bool) -> str:
    CACHE_DIR.mkdir(exist_ok=True)
    cached = CACHE_DIR / name
    if offline:
        if not cached.is_file():
            raise SystemExit(f"--offline but no cache at {cached}")
        return cached.read_text()
    with urllib.request.urlopen(url) as r:
        text = r.read().decode("utf-8")
    cached.write_text(text)
    return text


def parse_packages(xml_text: str) -> dict[str, dict]:
    """Return name -> {versions: set, projectURL, description, url} keyed by package name.

    CBAN files have one <package> block per version; we keep the highest
    version (by parseVersion-style comparison) along with the metadata from
    that entry.
    """
    out: dict[str, dict] = {}
    for m in re.finditer(r"<package\s+([^>]*?)\s*/?>", xml_text, re.DOTALL):
        attrs = dict(re.findall(r"(\w+)\s*=\s*['\"]([^'\"]*)['\"]", m.group(1)))
        name = attrs.get("name")
        if not name:
            continue
        ver = attrs.get("version", "")
        entry = out.setdefault(
            name,
            {"versions": set(), "projectURL": "", "description": "", "url": ""},
        )
        entry["versions"].add(ver)
        # Keep the metadata from the highest-version entry we've seen.
        if not entry["projectURL"] or _ver_key(ver) > _ver_key(entry.get("_latest_seen", "")):
            entry["projectURL"] = attrs.get("projectURL", entry["projectURL"])
            entry["description"] = attrs.get("description", entry["description"])
            entry["url"] = attrs.get("url", entry["url"])
            entry["_latest_seen"] = ver
    for v in out.values():
        v.pop("_latest_seen", None)
    return out


def _ver_key(v: str) -> tuple:
    """Numeric version-string key. Non-numeric chunks compare as -1."""
    parts = []
    for chunk in re.split(r"[.\-]", v or ""):
        try:
            parts.append((1, int(chunk)))
        except ValueError:
            parts.append((0, chunk))
    return tuple(parts)


def latest(versions: set[str]) -> str:
    return max(versions, key=_ver_key) if versions else ""


def load_yaml_names(yaml_path: Path) -> dict[str, dict]:
    """Return CBAN-name -> packages.yaml entry, using YAML_ALIASES."""
    data = yaml.safe_load(yaml_path.read_text())
    by_yaml_name: dict[str, dict] = {}
    for e in data.get("packages", []):
        by_yaml_name[e["name"]] = e

    by_cban_name: dict[str, dict] = {}
    # Reverse alias: cban name -> yaml entry
    for cban_name, yaml_name in YAML_ALIASES.items():
        if yaml_name in by_yaml_name:
            by_cban_name[cban_name] = by_yaml_name[yaml_name]
    # Direct matches by name
    for yaml_name, entry in by_yaml_name.items():
        by_cban_name.setdefault(yaml_name, entry)
    # Case-insensitive fallback: if a CBAN name matches a yaml name case-insensitively, link it
    return by_cban_name


def main() -> None:
    ap = argparse.ArgumentParser()
    ap.add_argument("--offline", action="store_true", help="use cached files under .cache/")
    args = ap.parse_args()

    raws = {
        name: parse_packages(fetch(name, url, args.offline))
        for name, url in SOURCES.items()
    }
    p27_main = raws["packages2.7.xml"]
    p27_extra = raws["packages-extra-2.7.xml"]
    p28 = raws["packages2.8.xml"]

    # Union of v2.7, tagging source.
    v27: dict[str, dict] = {}
    for n, e in p27_main.items():
        v27[n] = dict(e, source="main")
    for n, e in p27_extra.items():
        if n in v27:
            v27[n]["source"] = "main+extra"
            v27[n]["versions"] = v27[n]["versions"] | e["versions"]
        else:
            v27[n] = dict(e, source="extra")

    yaml_entries = load_yaml_names(REPO_ROOT / "packages.yaml")
    # Case-insensitive fallback so packages.yaml entries match CBAN regardless of casing.
    yaml_ci = {k.lower(): v for k, v in yaml_entries.items()}

    # Categorise:
    #   - migrated_in_28: in v2.7 and in v2.8 manifest
    #   - core_in_28: BEAST.base / BEAST.app (built into beast3 core)
    #   - tracked_but_not_published: in v2.7, NOT in v2.8 manifest, listed in packages.yaml
    #   - untracked: in v2.7, NOT in v2.8 manifest, NOT in packages.yaml
    migrated, core, tracked, untracked = [], [], [], []
    for name in sorted(v27.keys(), key=str.lower):
        if name in CORE_IN_V28:
            core.append(name)
            continue
        if name in p28:
            migrated.append(name)
            continue
        yaml_match = yaml_entries.get(name) or yaml_ci.get(name.lower())
        if yaml_match:
            tracked.append((name, yaml_match))
        else:
            untracked.append(name)

    now = datetime.now(timezone.utc).astimezone().isoformat(timespec="seconds")
    out = []
    out.append("# BEAST 2 → BEAST 3 migration backlog\n")
    out.append(
        "Auto-generated from CBAN's `packages2.7.xml`, `packages-extra-2.7.xml`,\n"
        "and `packages2.8.xml`. Regenerate with `python3 scripts/gen-backlog.py`.\n"
    )
    out.append(f"_Last regenerated: {now}_\n")
    out.append("## Summary\n")
    out.append(f"- v2.7 packages (union of main + extra): **{len(v27)}**")
    out.append(f"- Migrated to v2.8 (in `packages2.8.xml`): **{len(migrated)}**")
    out.append(f"- Built into beast3 core in v2.8: **{len(core)}** ({', '.join(core)})")
    out.append(f"- Tracked in `packages.yaml` but not yet in `packages2.8.xml`: **{len(tracked)}**")
    out.append(f"- Untracked, unmigrated: **{len(untracked)}**\n")

    out.append("## Tracked in `packages.yaml` but not yet in CBAN packages2.8.xml\n")
    out.append("These packages are being worked on locally but have not yet appeared in `packages2.8.xml`.\n")
    out.append("| Package (CBAN name) | v2.7 source | Latest 2.7 | packages.yaml stage | GitHub |")
    out.append("|---|---|---|---|---|")
    for cban_name, ye in tracked:
        e = v27[cban_name]
        gh = ye.get("github", "")
        gh_link = f"[`{gh}`](https://github.com/{gh})" if gh else "—"
        out.append(
            f"| {cban_name} | {e['source']} | {latest(e['versions'])} | "
            f"{ye.get('stage', '—')} | {gh_link} |"
        )
    out.append("")

    out.append("## Untracked — needs migration work and/or addition to packages.yaml\n")
    out.append("These v2.7 packages are not in `packages2.8.xml` and are not currently tracked in this repo.\n")
    out.append("| Package | v2.7 source | Latest 2.7 | projectURL | Description |")
    out.append("|---|---|---|---|---|")
    for name in untracked:
        e = v27[name]
        purl = e.get("projectURL", "")
        purl_md = f"[link]({purl})" if purl else "—"
        desc = (e.get("description") or "").replace("|", "\\|").replace("\n", " ").strip()
        if len(desc) > 120:
            desc = desc[:117] + "..."
        out.append(f"| {name} | {e['source']} | {latest(e['versions'])} | {purl_md} | {desc} |")
    out.append("")

    out.append("## Already migrated to v2.8 (for reference)\n")
    out.append("| Package | Latest 2.7 | Latest 2.8 |")
    out.append("|---|---|---|")
    for name in migrated:
        e27 = v27[name]
        e28 = p28[name]
        out.append(f"| {name} | {latest(e27['versions'])} | {latest(e28['versions'])} |")
    out.append("")

    (REPO_ROOT / "BACKLOG.md").write_text("\n".join(out))
    print(f"Wrote {REPO_ROOT / 'BACKLOG.md'}")
    print(f"  migrated:  {len(migrated)}")
    print(f"  core:      {len(core)}")
    print(f"  tracked:   {len(tracked)}")
    print(f"  untracked: {len(untracked)}")


if __name__ == "__main__":
    main()

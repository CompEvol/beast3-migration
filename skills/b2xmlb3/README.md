# b2xmlb3 — Beast2 XML to Beast3 Tools

## Tools

| Script | Description |
|:---|:---|
| `scan_deprecated.py` | Scans beast3 Java source for class-level `@Deprecated` annotations and produces a mapping to their beast3 replacements |
| `unmapped_spec.py` | Reads `deprecated_classes.md` and finds spec classes in `*.spec.*` packages not yet referenced as a replacement |

---

## 1. `scan_deprecated.py`

Scans the beast3 Java source tree for class-level `@Deprecated` annotations and
produces a Markdown report that maps each deprecated class to its beast3
replacement (extracted from the `@deprecated` Javadoc tag).

### Requirements

Python 3.9+ (stdlib only, no dependencies).

### Usage

```bash
python3 scan_deprecated.py [--beast3-root PATH] [--output PATH]
```

Run from any directory — the script resolves all paths itself.

| Option | Default | Description |
|:---|:---|:---|
| `--beast3-root` | `~/WorkSpace/beast3` | Path to the beast3 repository root |
| `--output` | `deprecated_classes.md` (next to the script) | Output Markdown file |

### Input

The beast3 repository at `--beast3-root`, specifically the `src/main/java` trees
of these three Maven modules (in order):

```
beast-base/src/main/java
beast-fx/src/main/java
beast-pkgmgmt/src/main/java
```

Only production sources are scanned (`src/main/java`). Test sources and
`target/` build artefacts are ignored.

### Output

`deprecated_classes.md` — a Markdown file with one section per Maven module,
subdivided by Java package. Each package section contains a table:

| Column | Content |
|:---|:---|
| **Deprecated Class** | Simple class name (backtick-formatted) |
| **Replacement** | Text from the `@deprecated` Javadoc tag, with `{@link X}` rendered as `` `X` ``; _no replacement specified_ if the tag is absent |

Classes are detected only when `@Deprecated` annotates the top-level
class/interface/enum declaration itself — method- and field-level deprecations
are excluded.

### Example

```bash
# Default paths
python3 skills/b2xmlb3/scan_deprecated.py

# Custom beast3 location
python3 skills/b2xmlb3/scan_deprecated.py --beast3-root /path/to/beast3

# Custom output
python3 skills/b2xmlb3/scan_deprecated.py --output /tmp/deprecated.md
```

Progress is printed to stderr; the output file path and total count are
reported when the scan completes.

---

## 2. `unmapped_spec.py`

Reads `deprecated_classes.md` (produced by `scan_deprecated.py`) and scans
beast3 for all classes in `*.spec.*` packages, then reports those **not**
referenced as a replacement — i.e. spec classes that still lack a deprecated
bridge pointing to them.

The following packages are excluded as they are type/domain infrastructure
rather than migration targets:

- `beast.base.spec.type`
- `beast.base.spec.domain`

### Requirements

Python 3.9+ (stdlib only, no dependencies).  
`deprecated_classes.md` must exist — run `scan_deprecated.py` first.

### Usage

```bash
python3 unmapped_spec.py [--deprecated-md PATH] [--beast3-root PATH] [--output PATH]
```

Run from any directory — the script resolves all paths itself.

| Option | Default | Description |
|:---|:---|:---|
| `--deprecated-md` | `deprecated_classes.md` (next to the script) | Output of `scan_deprecated.py` |
| `--beast3-root` | `~/WorkSpace/beast3` | Path to the beast3 repository root |
| `--output` | `unmapped_spec_classes.md` (next to the script) | Output Markdown file |

### Input

1. `deprecated_classes.md` — the Replacement column is parsed for
   fully-qualified class names (both backtick-wrapped and bare prose).
2. The beast3 repository — same three Maven modules as `scan_deprecated.py`,
   filtered to packages whose path contains a `spec` component, excluding
   `beast.base.spec.type` and `beast.base.spec.domain`.

### Output

`unmapped_spec_classes.md` — same module/package structure as
`deprecated_classes.md`. Each package section lists spec classes with no
matching entry in any Replacement cell:

| Column | Content |
|:---|:---|
| **Class** | Simple class name |
| **Full Qualified Name** | `package.ClassName` |

The header reports total spec classes found, how many are mapped, and how many
are unmapped.

> **Note:** mismatches can arise from typos in `@deprecated` Javadoc comments
> in beast3 (e.g. `subsitutionmodel` instead of `substitutionmodel`). The
> script reports these faithfully — fix the source Javadoc and re-run both
> scripts to resolve them.

### Example

```bash
# Default paths (run scan_deprecated.py first)
python3 skills/b2xmlb3/scan_deprecated.py
python3 skills/b2xmlb3/unmapped_spec.py

# Custom paths
python3 skills/b2xmlb3/unmapped_spec.py \
    --deprecated-md /tmp/deprecated_classes.md \
    --beast3-root /path/to/beast3 \
    --output /tmp/unmapped.md
```

Progress and summary counts are printed to stderr.

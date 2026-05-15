# b2xmlb3 — Beast2 XML to Beast3 Tools

## Generated Reports

| Report | Description |
|:---|:---|
| [deprecated_classes.md](deprecated_classes.md) | All class-level deprecated classes in beast3, mapped to their beast3 replacements |

## Tools

| Script | Description |
|:---|:---|
| `scan_deprecated.py` | Scans beast3 Java source for class-level `@Deprecated` annotations and produces a mapping to their beast3 replacements |

---

## `scan_deprecated.py`

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

Both top-level and inner/nested class declarations are detected — method- and
field-level deprecations are excluded.

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

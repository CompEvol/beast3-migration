# b2deprecated — Deprecated Class Scanner

## Generated Reports

| Report | Description |
|:---|:---|
| [deprecated_classes.md](deprecated_classes.md) | All class-level deprecated classes in beast3, mapped to their B3 replacements |

## Tools

| Script | Description |
|:---|:---|
| `scan_deprecated.py` | Scans beast3 Java source for class-level `@Deprecated` annotations and produces a mapping to their B3 replacements |

---

## `scan_deprecated.py`

Scans the beast3 Java source tree for class-level `@Deprecated` annotations and
produces a Markdown report mapping each deprecated class to its B3 replacement
(extracted from the `@deprecated` Javadoc tag).

### Requirements

Python 3.9+ (stdlib only, no dependencies).

### Usage

```bash
python3 skills/b2deprecated/scan_deprecated.py [--beast3-root PATH] [--output PATH]
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

### Detection notes

**`@Deprecated` with arguments**: The scanner handles `@Deprecated(forRemoval = true)` and
similar forms. After matching `@Deprecated`, it skips the annotation's own `(...)` argument
list before scanning for the class keyword. Without this, classes whose annotation has
arguments (e.g. `Frequencies`, `SiteModel`) were silently missed.

**Replacement FQN scope**: The scanner captures any `{@link X}` FQN from the `@deprecated`
Javadoc tag, regardless of package. The replacement need not be in the `beast.base.spec.*`
namespace — for example, `SubtreeSlide` is replaced by
`beast.base.evolution.operator.kernel.BactrianSubtreeSlide`.

### Example

```bash
# Default paths (run from project root)
python3 skills/b2deprecated/scan_deprecated.py

# Custom beast3 location
python3 skills/b2deprecated/scan_deprecated.py --beast3-root /path/to/beast3

# Custom output
python3 skills/b2deprecated/scan_deprecated.py --output /tmp/deprecated.md
```

Progress is printed to stderr; the output file path and total count are
reported when the scan completes.

### How `deprecated_map.py` consumes this file

`parse_deprecated_md()` in `xml-migration/deprecated_map.py` reads the table and
extracts entries where the Replacement column contains a backtick-quoted `beast.*` FQN.
Classes with prose-only replacements (e.g. `OneOnX`) are omitted — they are handled
by dedicated XSLT templates (T3c–e) rather than a generic rename.

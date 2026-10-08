# xml-migration — quick reference

**Always use `python3`** (not `python`) for all scripts here.

## Key rules

- **`_b3.xml` inputs are always rejected** — files whose stem ends in `_b3` are already
  converted BEAST3 outputs. Pass the original BEAST2 source instead. `--overwrite` does
  not bypass this guard (it only controls whether an existing `*_b3.xml` *output* may be replaced).
- **FxTemplate `<subtemplate>`/`<partitiontemplate>` CDATA is converted** like the rest of the
  document and written back as CDATA (see T1b in `XML-MIGRATION-STRATEGY.md`); its report lines are
  prefixed `[CDATA <tag id=…>]`. Only a fragment that is not well-formed XML is skipped (`[todo]`).
- **A class with several replacements in `deprecated_classes.md` is never resolved by taking the
  first one.** `UniformOperator` (by target type), `Gamma` (by `mode=`), parameter shapes and the
  `ScaleOperator` split have type-aware rules; any other multi-replacement class gets a `[warn]`.
  Review every such `[warn]` against the actual parameter type.

## Post-conversion validation
After converting a file, run `/test-b3-xml` (or follow `test-b3-xml.md`) to validate the
converted XML with `beast -validate`. This catches class-not-found, missing-input,
and format errors before committing. Pass criterion: last output line contains `"Done!"`.

## Full reference
See `XML-MIGRATION-STRATEGY.md` for files, commands, transformation rules, and examples layout.
See `test-b3-xml.md` for the full validation procedure.

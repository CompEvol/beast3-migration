# xml-migration — quick reference

**Always use `python3`** (not `python`) for all scripts here.

## Key rules

- **`_b3.xml` inputs are always rejected** — files whose stem ends in `_b3` are already
  converted BEAST3 outputs. Pass the original BEAST2 source instead. `--overwrite` does
  not bypass this guard (it only controls whether an existing `*_b3.xml` *output* may be replaced).
- **FxTemplate `<subtemplate>` CDATA round-trips as CDATA**, not escaped text (see T1b in
  `XML-MIGRATION-STRATEGY.md`) — but its content is never scanned or renamed. After converting a
  FxTemplate, manually check the CDATA block for deprecated class references and fix by hand.

## Post-conversion validation
After converting a file, run `/test-b3-xml` (or follow `test-b3-xml.md`) to validate the
converted XML with `beast -validate`. This catches class-not-found, missing-input,
and format errors before committing. Pass criterion: last output line contains `"Done!"`.

## Full reference
See `XML-MIGRATION-STRATEGY.md` for files, commands, transformation rules, and examples layout.
See `test-b3-xml.md` for the full validation procedure.

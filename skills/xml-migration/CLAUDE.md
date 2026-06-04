# xml-migration — quick reference

**Always use `python3`** (not `python`) for all scripts here.

## Key rules

- **`_b3.xml` inputs are always rejected** — files whose stem ends in `_b3` are already
  converted BEAST3 outputs. Pass the original BEAST2 source instead. `--overwrite` does
  not bypass this guard (it only controls whether an existing `*_b3.xml` *output* may be replaced).

## Post-conversion smoke test
After converting a file, run `/test-b3-xml` (or follow `test-b3-xml.md`) to verify the
converted XML actually executes in BEAST3. This catches class-not-found, missing-input,
and format errors before committing.

## Full reference
See `XML-MIGRATION-STRATEGY.md` for files, commands, transformation rules, and examples layout.
See `test-b3-xml.md` for the full smoke-test procedure.

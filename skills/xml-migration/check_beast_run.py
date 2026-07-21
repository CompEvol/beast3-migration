#!/usr/bin/env python3
"""
check_beast_run.py — Analyse captured output from `beast -validate`.

Usage:
    BEAST_ROOT_DIR=~/WorkSpace/beast3
    $BEAST_ROOT_DIR/bin/beast -validate /path/to/file_b3.xml \
        > /tmp/beast3_validate_output.txt 2>&1
    python3 check_beast_run.py /tmp/beast3_validate_output.txt

Exit 0 (PASS): last non-empty output line contains "Done!".
Exit 1 (FAIL): prints root exception + compact BEAST error block (≤30 lines);
               skips Java stack-trace noise.
"""

import re
import sys


def main():
    if len(sys.argv) != 2:
        sys.exit(f'Usage: {sys.argv[0]} OUTPUT_FILE')

    lines = open(sys.argv[1]).read().splitlines()

    # 1. Success: last non-empty line contains "Done!"
    non_empty = [l for l in lines if l.strip()]
    if non_empty and 'Done!' in non_empty[-1]:
        print(f'PASS: {non_empty[-1].strip()}')
        sys.exit(0)

    # 2. Failure: extract compact error context, skip stack-trace noise
    print('FAIL — error context:')
    out = []
    i = 0
    while i < len(lines):
        line = lines[i]
        # Root-cause exception: first exception line before any BEAST summary
        if re.match(r'\t*\w[\w.]*Exception\b', line) and not out:
            out.append(line.strip())
        # BEAST human-readable error block + the XML context that follows it
        elif re.search(
            r'(Error\s+\d+\s+parsing|validate and|Error detected about here)',
            line, re.IGNORECASE,
        ):
            out.extend(l.rstrip() for l in lines[i:i + 15])
            break
        i += 1

    print(
        '\n'.join(out[:30]) if out
        else f'(no structured error found — check {sys.argv[1]})'
    )
    sys.exit(1)


if __name__ == '__main__':
    main()

#!/usr/bin/env python3
"""
check_beast_run.py — Analyse a captured BEAST3 run output file.

Usage:
    python3 check_beast_run.py OUTPUT_FILE

Exit 0 (PASS): prints the "Total calculation time" line.
Exit 1 (FAIL): prints root exception + compact BEAST error block (≤30 lines);
               skips Java stack-trace noise.
"""

import re
import sys


def main():
    if len(sys.argv) != 2:
        sys.exit(f'Usage: {sys.argv[0]} OUTPUT_FILE')

    lines = open(sys.argv[1]).read().splitlines()

    # 1. Success: print only the completion line
    for line in lines:
        if re.search(r'total\s*calculation\s*time', line, re.IGNORECASE):
            print(f'PASS: {line.strip()}')
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

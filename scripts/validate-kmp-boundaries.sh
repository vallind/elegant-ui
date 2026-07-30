#!/usr/bin/env bash
set -euo pipefail

common_root="elegant-ui/src/commonMain"

if [[ ! -d "$common_root" ]]; then
  echo "KMP boundary validation failed: $common_root does not exist." >&2
  exit 1
fi

if grep -RInE '^[[:space:]]*import[[:space:]]+android\.' "$common_root"; then
  echo "KMP boundary validation failed: commonMain imports Android platform APIs." >&2
  exit 1
fi

if grep -RInE '^[[:space:]]*import[[:space:]]+androidx\.(activity|appcompat|core|fragment)\.' "$common_root"; then
  echo "KMP boundary validation failed: commonMain imports Android-only AndroidX APIs." >&2
  exit 1
fi

if find elegant-ui/src -type f -path '*/src/main/*' -print -quit | grep -q .; then
  echo "KMP boundary validation failed: library sources must use KMP source sets, not src/main." >&2
  exit 1
fi

echo "KMP boundary validation passed. Android is the only configured target; public UI source is common-first."

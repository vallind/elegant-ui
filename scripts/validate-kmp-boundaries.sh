#!/usr/bin/env bash
set -euo pipefail

common_root="elegant-ui/src/commonMain"
showcase_common_root="showcase/src/commonMain"

for path in "$common_root" "$showcase_common_root"; do
  if [[ ! -d "$path" ]]; then
    echo "KMP boundary validation failed: $path does not exist." >&2
    exit 1
  fi
done

for path in "$common_root" "$showcase_common_root"; do
  if grep -RInE '^[[:space:]]*import[[:space:]]+android\.' "$path"; then
    echo "KMP boundary validation failed: common code imports Android platform APIs." >&2
    exit 1
  fi

  if grep -RInE '^[[:space:]]*import[[:space:]]+androidx\.(activity|appcompat|core|fragment)\.' "$path"; then
    echo "KMP boundary validation failed: common code imports Android-only AndroidX APIs." >&2
    exit 1
  fi

  if grep -RInE '^[[:space:]]*import[[:space:]]+(java\.awt|javax\.swing|kotlinx\.browser|org\.w3c\.dom)\.' "$path"; then
    echo "KMP boundary validation failed: common code imports Desktop- or Web-only APIs." >&2
    exit 1
  fi
done

if find elegant-ui/src showcase/src -type f -path '*/src/main/*' -print -quit | grep -q .; then
  echo "KMP boundary validation failed: KMP modules must use platform source sets, not src/main." >&2
  exit 1
fi

for target in 'android {' 'jvm("desktop")' 'wasmJs {'; do
  if ! grep -Fq "$target" elegant-ui/build.gradle.kts; then
    echo "KMP boundary validation failed: elegant-ui is missing target declaration: $target" >&2
    exit 1
  fi
  if ! grep -Fq "$target" showcase/build.gradle.kts; then
    echo "KMP boundary validation failed: showcase is missing target declaration: $target" >&2
    exit 1
  fi
done

for module in sample desktop-sample web-sample showcase; do
  if [[ ! -f "$module/build.gradle.kts" ]]; then
    echo "KMP boundary validation failed: required sample module is missing: $module" >&2
    exit 1
  fi
done

echo "KMP boundary validation passed for Android, Desktop JVM, and Web/Wasm."

#!/usr/bin/env bash
set -euo pipefail

binaryen_version="125"
binaryen_archive="binaryen-version_${binaryen_version}-x86_64-linux.tar.gz"
binaryen_sha256="7c3bc16599c8274a04d34a504fe4be2047884f900e0e2da2f6fb9cd667183be4"
binaryen_url="https://github.com/WebAssembly/binaryen/releases/download/version_${binaryen_version}/${binaryen_archive}"
binaryen_archive_path="${RUNNER_TEMP:?RUNNER_TEMP must be set}/${binaryen_archive}"
binaryen_install_dir="${RUNNER_TEMP}/binaryen-version_${binaryen_version}"

curl \
  --fail \
  --location \
  --retry 3 \
  --show-error \
  --silent \
  "$binaryen_url" \
  --output "$binaryen_archive_path"

echo "${binaryen_sha256}  ${binaryen_archive_path}" | sha256sum --check -
mkdir -p "$binaryen_install_dir"
tar -xzf "$binaryen_archive_path" -C "$binaryen_install_dir" --strip-components=1

echo "${binaryen_install_dir}/bin" >> "${GITHUB_PATH:?GITHUB_PATH must be set}"
"${binaryen_install_dir}/bin/wasm-opt" --version

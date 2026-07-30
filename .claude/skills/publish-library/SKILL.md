---
name: publish-library
description: Build and publish Elegant UI for Android, Desktop JVM, and Web/Wasm consumers. Use when asked to publish locally, prepare KMP Maven artifacts, package platform samples, verify coordinates, or prepare a release handoff.
---

# Publish Elegant UI

Publish the complete Kotlin Multiplatform repository. Android, Desktop JVM, and Web/Wasm are supported; direct AAR, desktop app, and web distribution files are validation artifacts rather than replacements for KMP metadata.

## Snapshot handoff

1. Run `./scripts/validate-kmp-boundaries.sh`.
2. Run `gradle :elegant-ui:build :showcase:build --stacktrace --no-daemon`.
3. Run `gradle :elegant-ui:publishAllPublicationsToBuildRepository --stacktrace --no-daemon`.
4. Verify `elegant-ui/build/repo/` contains root metadata and Android, Desktop JVM, and Web/Wasm publications.
5. Build `:sample:assembleDebug`, `:desktop-sample:createDistributable`, and `:web-sample:wasmJsBrowserDistribution`.
6. Build the documentation site after copying the real Web/Wasm demo.
7. Test consumers with `io.github.vallind:elegant-ui:0.1.0-SNAPSHOT`.

## Maven Local

```bash
gradle :elegant-ui:publishToMavenLocal --stacktrace --no-daemon
```

KMP consumers declare the root coordinate from `commonMain`; standalone Android apps use the same coordinate in `dependencies`. Do not guess target-suffixed artifacts.

## Release guardrails

- Do not publish stable versions before signing, POM metadata, license, source archives, API compatibility checks, changelog, and release CI exist.
- Do not publish when any supported target fails compilation or sample validation.
- Keep `group = "io.github.vallind"` and artifact `elegant-ui` stable unless a migration plan is approved.
- iOS remains out of scope and must not appear in release claims.

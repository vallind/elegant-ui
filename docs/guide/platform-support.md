# Platform support

Elegant UI is structured for Kotlin Multiplatform without overstating runtime support.

| Platform | Status | Source set | Acceptance requirement |
| --- | --- | --- | --- |
| Android | Supported | `commonMain` + `androidMain` | Clean CI, sample APK, physical-device validation |
| Desktop JVM | Planned | Future `desktopMain` | Compile, desktop sample, tests, published variant |
| iOS | Planned | Future `iosMain` | Simulator/device build, accessibility review, published variant |
| Web/Wasm | Planned | Future `wasmJsMain` | Browser build, keyboard/accessibility review, published variant |

## Common-first does not mean multi-platform support

Components are implemented in `commonMain` whenever their API and behavior can be shared safely. A platform is only marked supported after its target, CI, sample, tests, documentation, and release artifact all exist.

## Public API boundary

Public component signatures must not expose Android platform types such as `Context`, `Activity`, `Drawable`, or `android.graphics` classes. Android-only integrations belong in `androidMain` behind narrow common interfaces when needed.

## Current compatibility promise

During `0.x`, Android is the only compatibility target. Future targets should reuse the same component names and core parameters, but platform-specific behavior may be introduced through additive APIs or scoped adapters.

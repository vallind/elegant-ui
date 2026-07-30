# Platform support

Elegant UI is a Compose Multiplatform component library with three supported targets.

| Platform | Status | Library target | Acceptance requirement |
| --- | --- | --- | --- |
| Android | Supported | `android` | Clean CI, sample APK, touch/accessibility review, physical-device validation |
| Desktop JVM | Supported | `jvm("desktop")` | Clean CI, distributable sample, keyboard/mouse/focus validation |
| Web/Wasm | Supported | `wasmJs` | Clean CI, browser distribution, keyboard/focus/responsive validation |
| iOS | Out of scope | None | No source set, publication, sample, or compatibility promise |

## Shared API contract

Public components, tokens, state models, defaults, and most interaction behavior live in `commonMain`. Platform-specific source sets are limited to adapters that cannot be expressed through common Compose APIs.

Public component signatures must not expose Android classes, Swing/AWT classes, browser DOM objects, or other platform types. Platform-specific integrations belong behind narrow common contracts.

## Platform behavior

The public API and semantic state model remain consistent across all supported targets. Input modality and system integration may adapt:

- Android: touch, hardware keyboard, TalkBack, density and font scale.
- Desktop JVM: mouse, keyboard, focus traversal, window resize and high-DPI rendering.
- Web/Wasm: keyboard, pointer, browser focus, viewport resize and WasmGC-capable browsers.

A component is incomplete when it compiles on only one or two supported targets.

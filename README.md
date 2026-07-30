# Elegant Compose

A refined Android component library built with Kotlin and Jetpack Compose.

**Visual direction:** exquisite, elegant, premium, restrained, modern, and precise.

Design reference: Apple HIG spatial restraint × Linear clarity × Ant Design precision × Material 3 system thinking.

## Current milestone

Button component closed-loop implementation:

- Primary / Secondary / Tertiary
- Small / Medium / Large
- Default / Pressed / Focused / Disabled / Loading
- Leading and trailing icon slots
- Light and dark themes
- 48dp minimum touch target
- Installable sample APK built by GitHub Actions

## Build

```bash
gradle :sample:assembleDebug :elegant-ui:assembleRelease
```

Artifacts:

- `sample/build/outputs/apk/debug/sample-debug.apk`
- `elegant-ui/build/outputs/aar/elegant-ui-release.aar`

## CI / physical-device verification

1. Open the latest successful **Android Build** workflow run.
2. Download `elegant-compose-button-demo-apk` from **Artifacts**.
3. Extract and install `sample-debug.apk` on an Android 7.0+ device.
4. Verify press feedback, touch target, light/dark modes, loading, disabled state, typography, and spacing.

Chinese documentation: [docs/zh/button.md](docs/zh/button.md)  
English documentation: [docs/button.md](docs/button.md)

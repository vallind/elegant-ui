# Elegant Compose

A refined Android component library built with Kotlin and Jetpack Compose.

**Visual direction:** exquisite, elegant, premium, restrained, modern, and precise.

Design reference: Apple HIG spatial restraint × Linear clarity × Ant Design precision × Material 3 system thinking.

## Documentation

The bilingual VitePress website follows the same content architecture as mature Compose component libraries: guide pages, component overview, one page per component, synchronized English/Chinese navigation, interactive visual previews, and GitHub Pages deployment.

- Website: <https://vallind.github.io/elegant-ui/>
- English components: [docs/components/](docs/components/)
- 简体中文组件: [docs/zh_CN/components/](docs/zh_CN/components/)

Run locally:

```bash
cd docs
npm install
npm run docs:dev
```

Build the website:

```bash
cd docs
npm run docs:build
```

After the workflow is committed, enable **Settings → Pages → Build and deployment → Source: GitHub Actions** once for the repository.

## Current milestone

Button component closed-loop implementation:

- Primary / Secondary / Tertiary
- Small / Medium / Large
- Default / Pressed / Focused / Disabled / Loading
- Leading and trailing icon slots
- Light and dark themes
- 48dp minimum touch target
- Installable sample APK built by GitHub Actions
- Bilingual documentation website page and interactive visual preview

## Android build

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

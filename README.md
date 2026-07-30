# Elegant UI

A refined Compose Multiplatform component library for **Android, Desktop JVM, and Web/Wasm**.

The visual direction is exquisite, elegant, premium, restrained, modern, and precise. The design language combines spatial restraint, clear hierarchy, detailed interaction states, and systematic semantic tokens without copying another product's brand or source.

## Supported targets

| Platform | Status | Validation surface |
| --- | --- | --- |
| Android API 24+ | Supported | `:sample` APK and physical device |
| Desktop JVM | Supported | `:desktop-sample` distributable |
| Web/Wasm | Supported | `:web-sample` browser distribution |
| iOS | Out of scope | None |

Compose Multiplatform 1.11.1 supports Android, desktop operating systems, and browsers with WasmGC support. The library keeps public APIs in `commonMain` and isolates unavoidable platform integrations in platform source sets.

## Architecture

```text
elegant-ui/
├── elegant-ui/       # Published KMP component library: Android + Desktop + Web/Wasm
├── showcase/         # Shared component gallery used by all platform launchers
├── sample/           # Android application
├── desktop-sample/   # Desktop JVM application
├── web-sample/       # Compose Web/Wasm application and documentation iframe source
├── docs/             # English + Simplified Chinese VitePress website
└── scripts/          # Multiplatform boundary validation
```

## Use from another application

### KMP application

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
        }
    }
}
```

### Standalone Android application

```kotlin
dependencies {
    implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
}
```

Until Maven Central publishing is configured, use `publishToMavenLocal`, a same-build project dependency, or the `elegant-ui-maven-repository` Actions artifact.

See:

- [Installation](docs/guide/installation.md)
- [Local development](docs/guide/local-development.md)
- [Platform support](docs/guide/platform-support.md)
- [安装与依赖](docs/zh_CN/guide/installation.md)

## Current milestone

Button closed-loop implementation:

- Primary / Secondary / Tertiary
- Small / Medium / Large
- Default / Pressed / Focused / Disabled / Loading
- Leading and trailing icon slots
- Theme-aware `ElegantButtonDefaults` and immutable `ElegantButtonColors`
- Light and dark themes
- 48dp minimum interactive target
- Shared Android/Desktop/Web showcase
- Bilingual Miuix-format documentation page
- Real Compose Web/Wasm iframe demo generated from `:web-sample`

## Build

```bash
./scripts/validate-kmp-boundaries.sh

gradle \
  :elegant-ui:build \
  :showcase:build \
  :elegant-ui:publishAllPublicationsToBuildRepository \
  :sample:assembleDebug \
  :desktop-sample:createDistributable \
  :web-sample:wasmJsBrowserDistribution \
  --stacktrace \
  --no-daemon
```

Expected outputs:

- `elegant-ui/build/repo/` — complete KMP Maven repository
- `sample/build/outputs/apk/debug/sample-debug.apk` — Android sample
- `desktop-sample/build/compose/binaries/main/app/` — Desktop distributable
- `web-sample/build/dist/wasmJs/productionExecutable/` — Web/Wasm distribution

## Documentation

Build the Compose Web demo before the VitePress site:

```bash
gradle :web-sample:wasmJsBrowserDistribution
cd docs
npm install
npm run docs:check
npm run docs:build
```

Website: <https://vallind.github.io/elegant-ui/>

## CI artifacts

The **Multiplatform Build** workflow uploads:

- `elegant-ui-maven-repository`
- `elegant-ui-android-aar`
- `elegant-ui-android-sample`
- `elegant-ui-desktop-sample-linux`
- `elegant-ui-web-sample`

A component is complete only after all three targets compile and their applicable interaction checks pass.

# Elegant UI

A refined Compose component library with a common-first Kotlin Multiplatform foundation.

**Current support:** Android API 24+

**Planned, not yet supported:** Desktop JVM, iOS, Web/Wasm

The visual direction is exquisite, elegant, premium, restrained, modern, and precise. The design language combines spatial restraint, clear hierarchy, detailed interaction states, and systematic semantic tokens without copying another product's brand or source.

## Architecture

```text
elegant-ui/
├── elegant-ui/
│   └── src/
│       ├── commonMain/     # Public components, theme, tokens, shared behavior
│       ├── commonTest/     # Platform-independent contract tests
│       └── androidMain/    # Android-only adapters and manifest
├── sample/                 # Android physical-device validation app
├── docs/                   # English + Simplified Chinese VitePress website
└── scripts/                # KMP boundary validation
```

Code in `commonMain` is designed for future platform targets, but Android remains the only supported runtime until each additional target has CI, a sample, tests, documentation, and release artifacts.

## Use from another Android application

### Same Gradle build

```kotlin
dependencies {
    implementation(project(":elegant-ui"))
}
```

### Maven Local

Publish from this repository:

```bash
gradle :elegant-ui:publishToMavenLocal
```

Add `mavenLocal()` in the consuming project's repositories and depend on:

```kotlin
dependencies {
    implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
}
```

### GitHub Actions artifact

Download `elegant-ui-maven-repository` from the latest successful **Android Build** run, extract it, register the extracted directory as a Maven repository, and use the same coordinate.

The complete Maven repository is the supported handoff because it contains KMP module metadata, POMs, sources, and the Android AAR. The standalone AAR is supplied only for inspection or temporary emergency use.

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
- 48dp minimum touch target
- Common-first source layout
- Installable Android sample APK
- Bilingual Miuix-format documentation page and iframe visual demo

## Build

```bash
./scripts/validate-kmp-boundaries.sh

gradle \
  :elegant-ui:build \
  :elegant-ui:publishAllPublicationsToBuildRepository \
  :sample:assembleDebug \
  --stacktrace \
  --no-daemon
```

Expected outputs:

- `elegant-ui/build/repo/` — complete local Maven repository
- `sample/build/outputs/apk/debug/sample-debug.apk` — installable Android sample
- an Android `.aar` inside the Maven repository

## Documentation

Website: <https://vallind.github.io/elegant-ui/>

```bash
cd docs
npm install
npm run docs:check
npm run docs:dev
```

Build:

```bash
npm run docs:build
```

## CI and physical-device verification

The **Android Build** workflow uploads:

- `elegant-ui-sample-apk`
- `elegant-ui-maven-repository`
- `elegant-ui-android-aar`

Install the latest sample APK on an Android 7.0+ device and complete `VALIDATION.md`. Browser demos are visual documentation aids and do not replace Compose runtime or device validation.

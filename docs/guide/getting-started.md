# Getting started

Elegant UI is a Compose Multiplatform component library for Android, Desktop JVM, and Web/Wasm. Public components live in `commonMain`; thin platform launchers reuse one shared `:showcase`.

::: warning Current status
Elegant UI is under active `0.x` development. Public APIs may evolve before the first stable release. iOS is not part of the current support contract.
:::

## Requirements

- Android 7.0 (API 24) or later for Android consumers
- A 64-bit desktop environment supported by Compose Multiplatform
- A modern browser with WasmGC for Web/Wasm
- JDK 17 for KMP and Desktop packaging
- Node.js 22 or later for the documentation website

## Add the library

KMP application:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":elegant-ui"))
        }
    }
}
```

Standalone Android application:

```kotlin
dependencies {
    implementation(project(":elegant-ui"))
}
```

For another repository, use Maven Local or the `elegant-ui-maven-repository` Actions artifact. See [Installation](./installation).

## Apply the theme

```kotlin
ElegantTheme {
    AppContent()
}
```

## Use a component

```kotlin
ElegantButton(
    onClick = { /* action */ },
    style = ElegantButtonStyle.Primary,
    size = ElegantButtonSize.Medium,
) {
    Text("Continue")
}
```

## Run the samples

```bash
gradle :sample:installDebug
gradle :desktop-sample:run
gradle :web-sample:wasmJsBrowserDevelopmentRun
```

All launchers use the shared `:showcase` component matrix.

## Verify boundaries

```bash
./scripts/validate-kmp-boundaries.sh
```

The script blocks Android, Desktop-only, and browser-only APIs from leaking into common code and verifies all three targets and sample modules remain configured.

## Run this website

Build the real Compose Web demo first:

```bash
gradle :web-sample:wasmJsBrowserDistribution
cd docs
npm install
npm run docs:check
npm run docs:dev
```

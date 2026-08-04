# Getting Started

Supported platforms: **Android 7.0 (API 24) and later** / **Desktop (JVM)** / **Web (Wasm + JS)**

::: warning Current status
Elegant UI is under active `0.x` development. Public APIs may evolve before the first stable release. iOS is not part of the current support contract.
:::

## Requirements

- Android 7.0 (API 24) or later for Android consumers
- A 64-bit desktop environment supported by Compose Multiplatform
- A modern browser with WasmGC for Web/Wasm
- JDK 17 for KMP and Desktop packaging
- Node.js 22 or later for the documentation website

## Adding Dependencies

Elegant UI publishes one Kotlin Multiplatform coordinate, `io.github.vallind:elegant-ui`, that resolves the Android, Desktop JVM, and Web variants from KMP metadata.

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

### Android application

```kotlin
dependencies {
    implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
}
```

Inside this repository, use the project dependency instead:

```kotlin
implementation(project(":elegant-ui"))
```

The snapshot coordinate works from `mavenLocal()` after `gradle :elegant-ui:publishToMavenLocal`, or from the `elegant-ui-maven-repository` artifact uploaded by **Multiplatform Build**. See [Installation](./installation) for details.

## Basic Usage

### Applying the Elegant Theme

```kotlin
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val controller = remember { ElegantThemeController(keyColor = Color(0xFF147D64)) }
    ElegantTheme(
        darkTheme = isSystemInDarkTheme(),
        colors = if (isSystemInDarkTheme()) controller.darkColors() else controller.lightColors(),
    ) {
        content()
    }
}
```

The default theme follows the system appearance with a seed-derived palette. `ElegantThemeController` derives the full `ElegantColors` palette from one seed color with pure, deterministic Kotlin.

### Using a component

```kotlin
ElegantButton(
    onClick = { /* action */ },
    style = ElegantButtonStyle.Primary,
    size = ElegantButtonSize.Medium,
) {
    Text("Continue")
}
```

## Popup Hosts

Overlay components such as `ElegantModal`, `ElegantBottomSheet`, and `ElegantMenu` render in their own dialog windows and capture and restore focus themselves. No host scaffold is required.

## Run the Samples

```bash
gradle :sample:installDebug
gradle :desktop-sample:run
gradle :web-sample:wasmJsBrowserDevelopmentRun
```

All launchers use the shared `:showcase` component matrix.

## Verify Boundaries

```bash
./scripts/validate-kmp-boundaries.sh
```

The script blocks Android, Desktop-only, and browser-only APIs from leaking into common code and verifies all targets and sample modules remain configured.

## Run This Website

Build the real Compose Web demo first:

```bash
gradle :web-sample:wasmJsBrowserDistribution
cd docs
npm install
npm run docs:check
npm run docs:dev
```

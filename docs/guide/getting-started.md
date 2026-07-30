# Getting started

Elegant UI is an Android library for Kotlin and Jetpack Compose. The current repository includes the reusable `:elegant-ui` module and an installable `:sample` application.

::: warning Current status
Elegant UI is under active development. Public APIs may evolve until the first stable release.
:::

## Requirements

- Android 7.0 (API 24) or later
- Kotlin and Jetpack Compose
- JDK 17 for the current Android build
- Node.js 22 or later for the documentation website

## Add the library

During local development, depend on the module directly:

```kotlin
implementation(project(":elegant-ui"))
```

A published Maven coordinate will be documented when distribution is available.

## Apply the theme

```kotlin
ElegantTheme {
    AppContent()
}
```

Use semantic colors and dimensions from `ElegantTheme`, `ElegantSpacing`, and `ElegantRadius` rather than hardcoded visual values.

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

## Run the sample

```bash
gradle :sample:installDebug
```

The sample APK is also uploaded by the **Android Build** GitHub Actions workflow.

## Run this website

```bash
cd docs
npm install
npm run docs:dev
```

Build the static site with:

```bash
npm run docs:build
```

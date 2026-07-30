# Getting started

Elegant UI is a common-first Kotlin Multiplatform Compose library with Android as its first and currently only supported runtime target. The repository includes the reusable `:elegant-ui` module and an installable Android `:sample` application.

::: warning Current status
Elegant UI is under active `0.x` development. Public APIs may evolve before the first stable release. Code in `commonMain` is KMP-ready, but Desktop, iOS, and Web are not supported yet.
:::

## Requirements

- Android 7.0 (API 24) or later
- Kotlin and Compose
- JDK 17 for the Android/KMP build
- Node.js 22 or later for the documentation website

## Add the library

For a module in the same Gradle build:

```kotlin
dependencies {
    implementation(project(":elegant-ui"))
}
```

For another repository, use Maven Local or the Maven repository artifact produced by GitHub Actions. See [Installation](./installation) for complete setup.

## Apply the theme

```kotlin
ElegantTheme {
    AppContent()
}
```

Use semantic values from `ElegantTheme`, `ElegantSpacing`, and `ElegantRadius` rather than hardcoded visual values.

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

## Run the Android sample

```bash
gradle :sample:installDebug
```

The **Android Build** workflow also uploads an installable sample APK, a complete Maven repository, and a direct Android AAR.

## Verify KMP boundaries

```bash
./scripts/validate-kmp-boundaries.sh
```

This prevents Android-only imports from leaking into `commonMain` and prevents the legacy library `src/main` layout from returning.

## Run this website

```bash
cd docs
npm install
npm run docs:check
npm run docs:dev
```

Build the static site with:

```bash
npm run docs:build
```

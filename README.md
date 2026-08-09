## Elyon

A UI library for Compose Multiplatform.

> This library is experimental. APIs may change without notice.

[![Kotlin](https://img.shields.io/badge/kotlin-2.4.10-7F52FF)](https://kotlinlang.org/)
[![Compose Multiplatform](https://img.shields.io/badge/compose-1.11.1-4285F4)](https://kotlinlang.org/compose-multiplatform/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue)](LICENSE)

### Online Demo

[![WasmJs](https://img.shields.io/badge/Demo-Web-654FF0?logo=webassembly&logoColor=white)](https://vallind.github.io/elegant-ui/demo/)

### Supported Platforms

![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-Native-white?logo=apple)
![macOS](https://img.shields.io/badge/macOS-Native-white?logo=apple)
![Desktop](https://img.shields.io/badge/Desktop-JVM-007396?logo=openjdk)
![JsCanvas](https://img.shields.io/badge/Web-JsCanvas-F7DF1E?logo=javascript&logoColor=white)
![WasmJs](https://img.shields.io/badge/Web-WasmJs-654FF0?logo=webassembly&logoColor=white)

### Modules

| Module             | Description                                          |
| ------------------ | ---------------------------------------------------- |
| `elyon-ui`         | Core UI component library                            |
| `elyon-preference` | Preference components library, depends on `elyon-ui` |
| `elyon-icons`      | Extended icon library, can be used independently     |
| `elyon-blur`       | Blur effect library, can be used independently       |
| `elyon-squircle`   | Squircle shapes library, can be used independently   |
| `elyon-nav`        | Navigation library, can be used independently        |
| `elyon-shader`     | Low-level runtime shader / render effect abstraction |

### Getting Started

Elyon is not published to Maven Central yet. Add this repository as a composite build:

```kotlin
// settings.gradle.kts
includeBuild("../elyon")
```

```kotlin
// build.gradle.kts
dependencies {
    implementation(project(":elyon-ui"))
    // Optional: Add elyon-preference for preference components
    implementation(project(":elyon-preference"))
    // Optional: Add elyon-icons for more icons
    implementation(project(":elyon-icons"))
    // Optional: Add elyon-blur for blur effects
    implementation(project(":elyon-blur"))
    // Optional: Add elyon-squircle for squircle (smooth rounded corner) shapes
    implementation(project(":elyon-squircle"))
    // Optional: Add elyon-nav for navigation
    implementation(project(":elyon-nav"))
}
```

### Usage

- Provide a color scheme via `ElyonTheme(colors = ...)`, e.g., `lightColorScheme()` or `darkColorScheme()`.

```kotlin
@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val colors = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    return ElyonTheme(
        colors = colors,
        content = content
    )
}
```

- Use `ThemeController` to manage modes and enable Monet dynamic colors. Pass `keyColor` to set a custom seed color.

```kotlin
@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val controller = remember {
        ThemeController(
            ColorSchemeMode.MonetSystem,
            keyColor = Color(0xFF3482FF)
        )
    }
    return ElyonTheme(
        controller = controller,
        content = content
    )
}
```

### Screenshots

<table>
  <tr>
    <td><a href="assets/001.webp"><img src="assets/001.webp" width="300" alt="Screenshot 001"/></a></td>
    <td><a href="assets/002.webp"><img src="assets/002.webp" width="300" alt="Screenshot 002"/></a></td>
    <td><a href="assets/003.webp"><img src="assets/003.webp" width="300" alt="Screenshot 003"/></a></td>
  </tr>
  <tr>
    <td><a href="assets/004.webp"><img src="assets/004.webp" width="300" alt="Screenshot 004"/></a></td>
    <td><a href="assets/005.webp"><img src="assets/005.webp" width="300" alt="Screenshot 005"/></a></td>
    <td><a href="assets/006.webp"><img src="assets/006.webp" width="300" alt="Screenshot 006"/></a></td>
  </tr>
</table>

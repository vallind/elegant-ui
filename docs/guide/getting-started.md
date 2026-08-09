# Getting Started

Supported platforms: **Android** / **Desktop (JVM)** / **iOS** / **WasmJs** / **Js** / **macOS (Native)**

::: warning
This library is experimental, and APIs may change in future versions without notice.
:::

## Adding Dependencies

Elyon is not published to Maven Central yet. Add this repository as a composite build:

```kotlin
// settings.gradle.kts
includeBuild("../elyon")
```

```kotlin
// build.gradle.kts
dependencies {
    implementation(project(":elyon-ui"))
    // Optional: add elyon-blur for blur effects
    implementation(project(":elyon-blur"))
    // Optional: add elyon-nav for navigation
    implementation(project(":elyon-nav"))
}
```

Elyon is composed of several modules that can be used independently:

| Module             | Description                                          |
| ------------------ | ---------------------------------------------------- |
| `elyon-core`       | Utilities + basic and extended icons                  |
| `elyon-effects`    | Runtime shader + squircle shapes                      |
| `elyon-blur`       | Blur effect library, can be used independently        |
| `elyon-ui`         | Core UI + preference components                       |
| `elyon-nav`        | Navigation library, can be used independently         |

## Basic Usage

### Applying the Elyon Theme

```kotlin
@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    // Available modes: System, Light, Dark, MonetSystem, MonetLight, MonetDark
    val controller = remember { ThemeController(ColorSchemeMode.System) }
    return ElyonTheme(
        controller = controller,
        content = content
    )
}
```

### Using the Elyon Scaffold

```kotlin
Scaffold(
    topBar = {
        // TopBar
    },
    bottomBar = {
        // BottomBar
    },
    floatingActionButton = {
        // FloatingActionButton
    },
    floatingToolbar = {
        // FloatingToolbar
    }
) {
    // Content...
}
```

::: warning
The Scaffold component provides a suitable container for cross-platform popup windows.
Components such as `OverlayDialog`, `OverlayDropdownPreference`, `OverlaySpinnerPreference`, and `OverlayListPopup` are
all implemented based on this and therefore need to be wrapped by this component.
:::

## API Documentation

- View the [API Documentation](/elyon/dokka/index.html){target="\_blank"},
  generated using Dokka, which contains detailed information about all APIs.

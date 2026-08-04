# Theme

Elegant UI theming is one seed color away. `ElegantTheme` composes `ElegantColors`, typography, and design tokens, and `ElegantThemeController` derives a complete light and dark palette from a single key color with pure, deterministic Kotlin.

## The Theme Composition

```kotlin
ElegantTheme(
    darkTheme = true,
) {
    // App content
}
```

Without arguments the theme follows the current system appearance and uses the built-in default palette.

## Seed-Derived Palettes

`ElegantThemeController` derives both palettes from one seed color; derivation never touches platform color APIs, so results are identical on Android, Desktop JVM, and Web.

```kotlin
val controller = remember { ElegantThemeController(keyColor = Color(0xFF147D64)) }

ElegantTheme(
    darkTheme = true,
    colors = controller.darkColors(),
) {
    // Green-derived dark palette
}
```

## Following System Appearance

```kotlin
val darkTheme = isSystemInDarkTheme()
val controller = remember { ElegantThemeController(keyColor = Color(0xFFB45309)) }

ElegantTheme(
    darkTheme = darkTheme,
    colors = if (darkTheme) controller.darkColors() else controller.lightColors(),
) {
    // Palette switches with the system
}
```

## Custom Colors

Any component can override its colors through its defaults object:

```kotlin
val buttonColors = ElegantButtonDefaults.colors(
    style = ElegantButtonStyle.Primary,
).copy(
    containerColor = Color(0xFF147D64),
)

ElegantButton(
    onClick = { },
    colors = buttonColors,
) {
    Text("Custom")
}
```

## Design Tokens

Components resolve spacing, radii, motion, and elevation from `ElegantSpacing`, `ElegantRadius`, `ElegantMotion`, and `ElegantElevation`. Consume the tokens instead of raw values:

```kotlin
Modifier.padding(ElegantSpacing.lg)
RoundedCornerShape(ElegantRadius.md)
tween(durationMillis = ElegantMotion.standardDurationMillis)
```

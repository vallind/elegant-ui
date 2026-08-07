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

`ElegantThemeController` derives both palettes from one seed color; the `Monet*` modes use the Material 3 dynamic color algorithm (HCT), while the seed-only constructor keeps the original HSL derivation. Pass the controller to `ElegantTheme` and mutate it to recompose the theme:

```kotlin
val controller = remember { ElegantThemeController(keyColor = Color(0xFF147D64)) }

ElegantTheme(
    controller = controller,
) {
    // Green-derived palette follows the system appearance
}
```

## Following System Appearance

```kotlin
val controller = remember {
    ElegantThemeController(
        colorSchemeMode = ElegantColorSchemeMode.MonetSystem,
        keyColor = Color(0xFFB45309),
    )
}

ElegantTheme(controller = controller) {
    // Palette derives from the seed and switches with the system
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

## Focus Rings

Keyboard focus rings are opt-in through the theme. Pass `focusRingEnabled = true` to `ElegantTheme` to draw the focus border on interactive components while they hold keyboard focus; by default (`false`) the focused state keeps its semantics and overlay indication, and focused visuals fall back to the hovered or resting colors.

```kotlin
ElegantTheme(
    focusRingEnabled = true,
) {
    // Interactive components show focus borders while keyboard-focused
}
```

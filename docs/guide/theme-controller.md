# Theme Controller

`ElegantThemeController` brings Monet-style dynamic color theming to Elegant UI. A single seed color derives the complete `ElegantColors` palette — interactive tones, surfaces, text, borders, and status colors — through a pure, deterministic derivation that behaves identically on Android, Desktop JVM, and Web/Wasm. The derivation runs in pure Kotlin with no platform color APIs, and the existing `ElegantColors` model stays unchanged so every component keeps working.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=theme-controller" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.foundation.theme.ElegantThemeController
import com.elegant.compose.ui.foundation.theme.deriveElegantColors
import androidx.compose.ui.graphics.Color
```

## Basic Usage

### Derive from a Key Color

Pass a `keyColor` to `ElegantTheme` and the full palette derives from the seed automatically:

```kotlin
ElegantTheme(
    keyColor = Color(0xFF6C4EFF),
) {
    // All components now resolve colors derived from the violet seed
}
```

### Theme Controller

`ElegantThemeController` is a small state holder that exposes the light and dark palettes derived from one seed color:

```kotlin
val controller = ElegantThemeController(keyColor = Color(0xFF147D64))

ElegantTheme(
    darkTheme = true,
    colors = controller.darkColors(),
) {
    // Green-derived dark palette
}
```

## Component States

### Light and Dark Palettes

Dark palettes use dark surfaces with light text and brightened status tones; light palettes use light surfaces with dark text and deepened status tones:

```kotlin
ElegantTheme(
    keyColor = Color(0xFFB45309),
    darkTheme = true,
) {
    // Orange-derived dark palette
}
```

### Interaction Tones

Hover, press, and focus tones are derived from the key color: hover lightens the seed by `0.06`, press darkens it by `0.10`, and the focus ring lightens it by `0.30`. Disabled and loading states resolve from the derived roles exactly as with the built-in palettes.

## Properties

### ElegantTheme Properties

The `keyColor` overload delegates to the existing `ElegantTheme` function, whose original signature stays untouched.

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `keyColor` | `Color` | Seed color the full palette derives from | - | Yes |
| `darkTheme` | `Boolean` | Whether to derive the light or dark palette | `isSystemInDarkTheme()` | No |
| `typography` | `ElegantTypography` | Typography roles provided to content | `DefaultElegantTypography` | No |
| `content` | `@Composable () -> Unit` | Content rendered with the derived palette | - | Yes |

### ElegantThemeController Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `keyColor` | `Color` | Seed color stored by the controller | - | Yes |
| `lightColors()` | `ElegantColors` | Palette derived for the light theme | - | No |
| `darkColors()` | `ElegantColors` | Palette derived for the dark theme | - | No |

### deriveElegantColors Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `keyColor` | `Color` | Seed color the palette derives from | - | Yes |
| `darkTheme` | `Boolean` | Whether to derive the dark palette | - | Yes |

## Advanced Usage

### Custom Palette with Derived Colors

`deriveElegantColors` is a pure function: call it outside composition, cache the result, or feed it into the existing `colors` parameter for full control:

```kotlin
val derived = deriveElegantColors(
    keyColor = Color(0xFF147D64),
    darkTheme = false,
)

ElegantTheme(
    colors = derived,
) {
    // Explicit palette, identical on every target
}
```

### Derivation Rules

- `interactivePrimary` is the key color; hover lightens by `0.06`, press darkens by `0.10`, and the focus ring lightens by `0.30`.
- `textInverse` is white when key-color luminance is below `0.45`, otherwise near-black (`#111216`).
- Surfaces keep the seed hue with a low saturation and fixed neutral lightness per theme.
- Text and borders are pure neutral gray with fixed lightness per theme.
- Status colors fix their hue at green `150`, amber `45`, and red `350`; saturation keeps the seed saturation floored at `0.45`; lightness is `0.33` in light and `0.60` in dark themes; `onStatus*` colors resolve by contrast.

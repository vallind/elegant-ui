# Theme Controller

`ElegantThemeController` brings Monet-style dynamic color theming to Elegant UI. A single seed color derives the complete `ElegantColors` palette with the Material 3 dynamic color algorithm (HCT + tonal schemes), mapped onto the interactive, surface, text, border, and status roles. The controller also exposes the fixed palettes, so one state holder switches between built-in and derived color schemes; mutating any of its properties recomposes the theme. On Android the `Monet*` modes can read the system wallpaper palette, while Desktop JVM and Web/Wasm derive from a seed or a fixed fallback.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=theme-controller" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.foundation.theme.ElegantColorSchemeMode
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.foundation.theme.ElegantThemeColorSpec
import com.elegant.compose.ui.foundation.theme.ElegantThemeController
import com.elegant.compose.ui.foundation.theme.ElegantThemePaletteStyle
import androidx.compose.ui.graphics.Color
```

## Basic Usage

### Monet Mode with a Key Color

Pass a `keyColor` and a `Monet*` mode to `ElegantThemeController` and hand it to `ElegantTheme`; the full palette derives from the seed with the Material 3 algorithm:

```kotlin
val controller = remember {
    ElegantThemeController(
        colorSchemeMode = ElegantColorSchemeMode.MonetSystem,
        keyColor = Color(0xFF6C4EFF),
    )
}

ElegantTheme(controller = controller) {
    // All components now resolve colors derived from the violet seed
}
```

### Compat Constructor

The `ElegantThemeController(keyColor)` constructor keeps the original HSL derivation contract: it derives the light and dark palettes eagerly and starts in `System` mode:

```kotlin
val controller = remember { ElegantThemeController(keyColor = Color(0xFF147D64)) }

ElegantTheme(controller = controller) {
    // Green-derived light or dark palette follows the system appearance
}
```

## Component States

### Light and Dark Palettes

The `Monet*` modes follow the system appearance (`MonetSystem`) or pin a palette (`MonetLight`, `MonetDark`); an explicit `isDark` overrides the system appearance in `System` and `MonetSystem`:

```kotlin
ElegantTheme(
    controller = remember {
        ElegantThemeController(
            colorSchemeMode = ElegantColorSchemeMode.MonetDark,
            keyColor = Color(0xFFB45309),
        )
    },
) {
    // Orange-derived dark palette
}
```

### Interaction Tones

Interactive roles map from the scheme's primary and its containers; hover, press, and focus tones derive from their base role with the same HSL shifts the seed derivation uses — hover lightens the base by `0.06`, press darkens it by `0.10`, and the focus ring lightens it by `0.30`. Surface hover lightens `surfaceDefault` by `0.04` and the sunken surface darkens it by `0.05`. Disabled and loading states resolve from the derived roles exactly as with the built-in palettes.

## Properties

### ElegantTheme Properties

The `controller` overload resolves the palette through `ElegantThemeController.currentColors()`; the original `ElegantTheme` function keeps its signature untouched.

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `controller` | `ElegantThemeController` | State holder that resolves the active palette | - | Yes |
| `typography` | `ElegantTypography` | Typography roles provided to content | `DefaultElegantTypography` | No |
| `content` | `@Composable () -> Unit` | Content rendered with the resolved palette | - | Yes |

### ElegantThemeController Properties

All properties are backed by compose state; assigning any of them recomposes the theme.

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `colorSchemeMode` | `ElegantColorSchemeMode` | How the active palette is resolved | `System` | No |
| `lightColors` | `ElegantColors` | Fixed palette used for light appearance | `ElegantLightColors` | No |
| `darkColors` | `ElegantColors` | Fixed palette used for dark appearance | `ElegantDarkColors` | No |
| `keyColor` | `Color?` | Seed color for the `Monet*` modes; `null` defers to the platform palette | `null` | No |
| `colorSpec` | `ElegantThemeColorSpec` | Material color specification for derivation | `Spec2021` | No |
| `paletteStyle` | `ElegantThemePaletteStyle` | Palette style used for derivation | `TonalSpot` | No |
| `isDark` | `Boolean?` | Explicit dark-mode override; `null` follows the system appearance | `null` | No |
| `currentColors()` | `ElegantColors` | `@Composable` resolution of the active palette for the current mode | - | No |

### ElegantColorSchemeMode Values

| Value | Description |
| --- | --- |
| `System` | Follow the system appearance with the fixed palettes |
| `Light` | Always use the light fixed palette |
| `Dark` | Always use the dark fixed palette |
| `MonetSystem` | Follow the system appearance, deriving from the seed or platform palette |
| `MonetLight` | Derive a light palette from the seed or platform palette |
| `MonetDark` | Derive a dark palette from the seed or platform palette |

### ElegantThemePaletteStyle Values

| Value | Description |
| --- | --- |
| `TonalSpot` | Baseline tonal-spot scheme |
| `Neutral` | Low-chroma neutral scheme |
| `Vibrant` | High-chroma vibrant scheme |
| `Expressive` | Expressive hue-shifted scheme |
| `Rainbow` | Rainbow multi-hue scheme |
| `FruitSalad` | Fruit-salad complementary-hue scheme |
| `Monochrome` | Fully monochrome scheme |
| `Fidelity` | Fidelity scheme that preserves the seed hue |
| `Content` | Content scheme tuned for content-driven palettes |

### ElegantThemeColorSpec Values

| Value | Description |
| --- | --- |
| `Spec2021` | Material color specification revision 2021 |
| `Spec2025` | Revision 2025; honored by `TonalSpot`, `Neutral`, `Vibrant`, and `Expressive`, other styles downgrade to `Spec2021` |

## Advanced Usage

### Platform Dynamic Colors

With `keyColor = null` the `Monet*` modes ask the platform for its palette. Android reads the wallpaper palette — the palette overlay JSON from API 33+, or the system accent color resources on API 31–32; below that a fixed seed (`#6750A4`) is used. Desktop JVM and Web/Wasm have no wallpaper access and always fall back to the fixed seed:

```kotlin
ElegantTheme(
    controller = remember {
        ElegantThemeController(colorSchemeMode = ElegantColorSchemeMode.MonetSystem)
    },
) {
    // Android: system wallpaper palette; Desktop/Web: fixed fallback seed
}
```

### Palette Styles and Color Specs

`paletteStyle` selects one of the nine Material schemes; the color spec revision is honored only where the scheme supports it and downgrades gracefully otherwise. Palette derivation is pure and deterministic, so the same seed, spec, style, and appearance produce identical `ElegantColors` on every target.

### Pure Derivation with deriveElegantColors

The original HSL derivation remains available as a pure function: call it outside composition, cache the result, or feed it into the existing `colors` parameter for full control:

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

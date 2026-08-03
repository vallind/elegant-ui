# ColorPicker

`ElegantColorPicker` presents a wrapping grid of round color swatches and reports the chosen color through a controlled callback. Use it for theming, accent selection, and any place where the user picks one value out of a curated color set.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=color-picker" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.colorpicker.ElegantColorPicker
import com.elegant.compose.ui.colorpicker.ElegantColorPickerColors
import com.elegant.compose.ui.colorpicker.ElegantColorPickerDefaults
```

## Basic Usage

`ElegantColorPicker` is a controlled component: `selectedColor` is owned by the caller, and every swatch click invokes `onColorSelected` with the chosen color, which the caller writes back.

```kotlin
var selected by remember { mutableStateOf(ElegantColorPickerDefaults.palette().first()) }

ElegantColorPicker(
    selectedColor = selected,
    onColorSelected = { selected = it },
)
```

## Palette

The default palette is a curated, fixed set of 16 colors: 8 saturated chromatic colors (red, orange, amber, green, teal, blue, violet, pink) followed by 8 light tints of the same hues. Swatches render at 32dp in a `FlowRow` that wraps on the 8dp rhythm. Pass any `List<Color>` as `colors` for a product-specific palette; the list is owned by the caller and should stay stable across recompositions.

```kotlin
val brandPalette = listOf(
    Color(0xFF6C4EFF),
    Color(0xFF5840D6),
    Color(0xFFA99CFF),
    Color(0xFF17181A),
)

ElegantColorPicker(
    selectedColor = brand,
    onColorSelected = { brand = it },
    colors = brandPalette,
)
```

## Component States

State precedence per swatch is disabled, selected, hovered, resting. The selected swatch draws a 2dp `interactivePrimary` ring; a hovered or keyboard-focused swatch draws a 1dp ring; a disabled swatch renders at 40% opacity and never invokes the callback. Every swatch announces `Role.Button`, its `selected` state, and its `#RRGGBB` hex value as the content description.

```kotlin
ElegantColorPicker(
    selectedColor = selected,
    onColorSelected = { selected = it },
)

ElegantColorPicker(
    selectedColor = selected,
    onColorSelected = {},
    enabled = false,
)
```

## Properties

### ElegantColorPicker Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `selectedColor` | `Color` | Currently selected color, owned by the caller; equality is component-based | - | Yes |
| `onColorSelected` | `(Color) -> Unit` | Callback invoked with the color chosen by the user | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the picker root | `Modifier` | No |
| `enabled` | `Boolean` | Whether swatches accept clicks | `true` | No |
| `colors` | `List<Color>` | Palette swatches rendered in the grid | `ElegantColorPickerDefaults.palette()` | No |
| `paletteColors` | `ElegantColorPickerColors` | Theme-aware ring colors for the resting, selected, and hovered states | `ElegantColorPickerDefaults.colors()` | No |

### ElegantColorPickerDefaults

| Member | Type | Description |
| --- | --- | --- |
| `SwatchSize` | `Dp` | 32dp visual diameter of every swatch |
| `SwatchGap` | `Dp` | 8dp gap between swatch interactive roots on both flow axes |
| `colors()` | `ElegantColorPickerColors` | Theme-aware Light/Dark ring colors |
| `palette()` | `List<Color>` | Curated default palette of 16 colors |

### ElegantColorPickerColors

`ElegantColorPickerColors` contains the resting border color, the selection ring color, and the hovered ring color (defaulting to `containerColor`). Start with `ElegantColorPickerDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Theme Accent Picker

Build a small palette from theme roles so the accent follows the active Light or Dark theme.

```kotlin
var accent by remember { mutableStateOf(ElegantTheme.colors.interactivePrimary) }

val accentPalette = listOf(
    ElegantTheme.colors.interactivePrimary,
    ElegantTheme.colors.interactivePrimaryHover,
    ElegantTheme.colors.interactivePrimaryPressed,
    ElegantTheme.colors.focusRing,
)

ElegantColorPicker(
    selectedColor = accent,
    onColorSelected = { accent = it },
    colors = accentPalette,
)
```

### Hex Readout

Swatches announce their hex value through semantics; show the same value on screen with a small formatter.

```kotlin
var selected by remember { mutableStateOf(ElegantColorPickerDefaults.palette().first()) }

fun hex(color: Color): String {
    fun channel(value: Float): Int = (value * 255f + 0.5f).toInt().coerceIn(0, 255)
    return listOf(color.red, color.green, color.blue).joinToString(prefix = "#", separator = "") {
        channel(it).toString(16).uppercase().padStart(2, '0')
    }
}

Column {
    ElegantColorPicker(
        selectedColor = selected,
        onColorSelected = { selected = it },
    )
    Text(
        text = hex(selected),
        style = ElegantTheme.typography.bodyMedium,
        color = ElegantTheme.colors.textSecondary,
    )
}
```

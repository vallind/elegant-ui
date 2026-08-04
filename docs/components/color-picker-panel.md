# ColorPicker Panel

`ElegantColorPickerPanel` combines the saturation × value `ElegantColorArea` with the rainbow `ElegantHueSlider` into one free-form HSV color picker. Use it for theme accents, drawing tools, and any place where the user needs the full continuous color space instead of a curated palette.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=color-picker-panel" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.colorpicker.ElegantColorPickerPanel
import com.elegant.compose.ui.colorpicker.ElegantColorArea
import com.elegant.compose.ui.colorpicker.ElegantHueSlider
import com.elegant.compose.ui.colorpicker.ElegantColorPicker
```

## Basic Usage

`ElegantColorPickerPanel` is a controlled component: `color` is owned by the caller, and every interaction invokes `onColorChange` with the resolved color, which the caller writes back. Dragging or tapping the area keeps the hue and changes saturation and value; dragging or tapping the slider keeps saturation and value and changes the hue.

```kotlin
var selected by remember { mutableStateOf(Color(0xFF6C4EFF)) }

ElegantColorPickerPanel(
    color = selected,
    onColorChange = { selected = it },
)
```

## Component States

The panel forwards its state to both controls: while `enabled` is false neither control invokes its callback. The area renders at 40% opacity and the slider at 40% opacity, and both swap their outline and thumb ring to the focus color while keyboard focused. The area announces `Role.Slider` with the `#RRGGBB` hex value as its content description; the slider announces `Role.Slider` with a `0..360` progress range.

```kotlin
ElegantColorPickerPanel(
    color = selected,
    onColorChange = { selected = it },
)

ElegantColorPickerPanel(
    color = selected,
    onColorChange = {},
    enabled = false,
)
```

## Properties

### ElegantColorPickerPanel Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `color` | `Color` | Currently selected color, owned by the caller | - | Yes |
| `onColorChange` | `(Color) -> Unit` | Callback invoked with the color resolved by either control | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the panel column | `Modifier` | No |
| `enabled` | `Boolean` | Whether either control accepts user interaction | `true` | No |
| `areaColors` | `ElegantColorAreaColors` | Theme-aware colors forwarded to the color area | `ElegantColorAreaDefaults.colors()` | No |
| `hueColors` | `ElegantHueSliderColors` | Theme-aware colors forwarded to the hue slider | `ElegantHueSliderDefaults.colors()` | No |

### ElegantColorArea Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `color` | `Color` | Currently selected color, owned by the caller | - | Yes |
| `onColorChange` | `(Color) -> Unit` | Callback invoked with the color resolved from the pointer or key position | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the panel root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the panel accepts pointer and keyboard interaction | `true` | No |
| `colors` | `ElegantColorAreaColors` | Theme-aware colors for the fill, outline, thumb, and focus ring | `ElegantColorAreaDefaults.colors()` | No |

### ElegantColorAreaDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Width` | `Dp` | 220dp default panel width |
| `Height` | `Dp` | 160dp default panel height |
| `ThumbSize` | `Dp` | 16dp diameter of the circular thumb |
| `colors()` | `ElegantColorAreaColors` | Theme-aware Light/Dark colors |

### ElegantColorAreaColors

`ElegantColorAreaColors` contains the base fill, the outline and thumb ring color, the thumb fill, and the focused outline color. Start with `ElegantColorAreaDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

### ElegantHueSlider Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `hue` | `Float` | Current hue in degrees, owned by the caller; values outside `0..360` are clamped | - | Yes |
| `onHueChange` | `(Float) -> Unit` | Callback invoked with the resolved hue after user interaction | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the interactive root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the slider accepts pointer and keyboard interaction | `true` | No |
| `colors` | `ElegantHueSliderColors` | Theme-aware colors for the track, thumb, outline, and focus ring | `ElegantHueSliderDefaults.colors()` | No |

### ElegantHueSliderDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Height` | `Dp` | 12dp height of the rainbow track |
| `ThumbSize` | `Dp` | 16dp diameter of the circular thumb |
| `colors()` | `ElegantHueSliderColors` | Theme-aware Light/Dark colors |

### ElegantHueSliderColors

`ElegantHueSliderColors` contains the track base color (transparent by default because the rainbow gradient is the track), the thumb fill, the outline and thumb ring color, and the focused outline color. Start with `ElegantHueSliderDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Theme Accent Editor

Combine the free-form panel with the curated swatch picker so the user can either drag through the continuous color space or jump to a palette color.

```kotlin
var accent by remember { mutableStateOf(Color(0xFF6C4EFF)) }

Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
    ElegantColorPickerPanel(
        color = accent,
        onColorChange = { accent = it },
    )
    ElegantColorPicker(
        selectedColor = accent,
        onColorSelected = { accent = it },
    )
}
```

# Slider Preference

`ElegantSliderPreference` is a settings-style preference for bounded values. It renders a title row with the formatted current value, an optional supporting text, and a full-width `ElegantSlider` beneath the row, with an optional inset hairline divider below. The slider owns all interaction; the title row is not clickable.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=slider-preference" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.preference.ElegantSliderPreference
import com.elegant.compose.ui.preference.ElegantSliderPreferenceColors
import com.elegant.compose.ui.preference.ElegantSliderPreferenceDefaults
```

## Basic Usage

The preference is controlled: the parent owns `value`, and every interaction is reported through `onValueChange`. `valueFormatter` renders the current value at the end of the title row. By default the value is continuous over the `0f..1f` range.

```kotlin
var brightness by remember { mutableStateOf(0.6f) }

ElegantSliderPreference(
    title = "Brightness",
    value = brightness,
    onValueChange = { brightness = it },
    valueFormatter = { "${(it * 100).roundToInt()}%" },
)
```

## Component States

A preference with `enabled = false` renders the title and value in the quiet disabled tone, blocks drag, tap, and keyboard interaction on the slider, and announces the disabled state to assistive technology. The supporting text and the inset divider keep their resting colors.

State precedence is owned by the slider: disabled, pressed or dragged, keyboard focused, pointer hovered, resting.

```kotlin
ElegantSliderPreference(
    title = "Brightness",
    value = 0.6f,
    onValueChange = {},
    enabled = false,
)
```

## Properties

### ElegantSliderPreference Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `title` | `String` | Label shown at the start of the title row | - | Yes |
| `value` | `Float` | Current slider value; clamped to `valueRange`, NaN renders at the range start | - | Yes |
| `onValueChange` | `(Float) -> Unit` | Callback invoked with the resolved value after user interaction | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the preference root | `Modifier` | No |
| `supportingText` | `String?` | Optional guidance shown below the title row; blank text is hidden | `null` | No |
| `valueRange` | `ClosedFloatingPointRange<Float>` | Range the value is constrained to | `0f..1f` | No |
| `steps` | `Int` | Number of discrete snap positions between the range endpoints; zero keeps the slider continuous | `0` | No |
| `valueFormatter` | `(Float) -> String` | Formats the current value for display at the end of the title row | `{ it.toString() }` | No |
| `enabled` | `Boolean` | Whether the slider accepts user interaction | `true` | No |
| `colors` | `ElegantSliderPreferenceColors` | Theme-aware text and divider colors | `ElegantSliderPreferenceDefaults.colors()` | No |
| `showDivider` | `Boolean` | Whether a hairline divider is drawn below the preference | `true` | No |

### ElegantSliderPreferenceDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 56dp minimum content-row height |
| `colors()` | `ElegantSliderPreferenceColors` | Theme-aware Light/Dark colors |

### ElegantSliderPreferenceColors

`ElegantSliderPreferenceColors` contains the title, supporting text, value, disabled title, and divider colors. Start with `ElegantSliderPreferenceDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Stepped Preference

Set `steps` to snap the value to discrete positions and pass `supportingText` to guide the user. The resolved value is rounded, so the parent should map it back to an integer state.

```kotlin
var level by remember { mutableStateOf(2) }

ElegantSliderPreference(
    title = "Text scale",
    supportingText = "Applies to all in-app text",
    value = level.toFloat(),
    onValueChange = { level = it.roundToInt() },
    valueRange = 0f..4f,
    steps = 4,
    valueFormatter = { "${it.roundToInt()}x" },
)
```

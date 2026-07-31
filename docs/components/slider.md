# Slider

`ElegantSlider` is a refined controlled value-selection slider. It renders a full-width rounded track with a filled active segment and a circular thumb inside a 48dp minimum interactive target, and supports drag, tap-to-jump, discrete steps, keyboard arrow adjustment, and complete disabled and accessibility semantics.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=slider" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.slider.ElegantSlider
import com.elegant.compose.ui.slider.ElegantSliderColors
import com.elegant.compose.ui.slider.ElegantSliderDefaults
```

## Basic Usage

The slider is controlled: the parent owns `value`, and every interaction reports the resolved value through `onValueChange`. By default the value is continuous over the `0f..1f` range.

```kotlin
var volume by remember { mutableStateOf(0.5f) }

ElegantSlider(
    value = volume,
    onValueChange = { volume = it },
)
```

## Component States

A slider with `enabled = false` renders quiet disabled colors, blocks drag, tap, and keyboard interaction, and announces the disabled state to assistive technology. Hovering the pointer highlights the track and thumb, pressing or dragging scales the thumb to 1.1 while keeping the 48dp hit target, and keyboard focus tints the thumb and enables arrow-key adjustment.

State precedence: disabled, pressed or dragged, keyboard focused, pointer hovered, resting.

```kotlin
ElegantSlider(
    value = 0.5f,
    onValueChange = {},
    enabled = false,
)
```

## Properties

### ElegantSlider Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `value` | `Float` | Current slider value; clamped to `valueRange`, NaN renders at the range start | - | Yes |
| `onValueChange` | `(Float) -> Unit` | Callback invoked with the resolved value after user interaction | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the interactive root | `Modifier` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted | `true` | No |
| `valueRange` | `ClosedFloatingPointRange<Float>` | Range the value is constrained to | `0f..1f` | No |
| `steps` | `Int` | Number of discrete snap positions between the range endpoints; zero keeps the slider continuous | `0` | No |
| `colors` | `ElegantSliderColors` | Theme-aware state colors | `ElegantSliderDefaults.colors()` | No |
| `interactionSource` | `MutableInteractionSource?` | Optional hoisted interaction source for observing or controlling state | `null` | No |

### ElegantSliderDefaults

| Member | Type | Description |
| --- | --- | --- |
| `TrackHeight` | `Dp` | 4dp height of the full-width track |
| `ThumbSize` | `Dp` | 20dp diameter of the circular thumb |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive root height |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantSliderColors` | Theme-aware Light/Dark colors |

### ElegantSliderColors

`ElegantSliderColors` contains track, active track, and thumb colors for the resting, hovered, pressed, focused, and disabled states. Start with `ElegantSliderDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Stepped Slider

Set `steps` to snap the value to discrete positions: `steps = 4` resolves to five evenly spaced positions between the endpoints. The resolved value is rounded, so the parent should map it back to an integer state.

```kotlin
var level by remember { mutableStateOf(2) }

ElegantSlider(
    value = level.toFloat(),
    onValueChange = { level = it.roundToInt() },
    valueRange = 0f..4f,
    steps = 4,
)
```

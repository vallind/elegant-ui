# Radio

`ElegantRadio` is a refined single-choice indicator for mutually exclusive options. It renders a 20dp circular indicator that fills with an animated dot when selected, keeps a 48dp minimum interactive row, and announces a merged `Role.RadioButton` state. Use it for settings, filters, and forms where exactly one option must be chosen from a group.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=radio" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.radio.ElegantRadio
import com.elegant.compose.ui.radio.ElegantRadioColors
import com.elegant.compose.ui.radio.ElegantRadioDefaults
```

## Basic Usage

A radio needs `selected` and `onSelect`. The optional `label` is drawn after the indicator and read as part of the row semantics; without it the row stays interactive and quiet.

```kotlin
ElegantRadio(
    selected = true,
    onSelect = { /* announce the chosen option */ },
    label = "Standard delivery",
)

ElegantRadio(
    selected = false,
    onSelect = { /* switch back to express delivery */ },
    label = "Express delivery",
)
```

## Component States

A radio row announces `Role.RadioButton` and its `selected` state, shows a focus ring on the indicator when the theme enables focus rings, applies hover and press feedback, and keeps a 48dp minimum interactive target.

State precedence: disabled, pressed, focused ring, hovered, resting. Selected and unselected are semantic states that combine with the interaction colors.

```kotlin
var accent by remember { mutableStateOf("Violet") }

for (candidate in listOf("Violet", "Indigo", "Teal")) {
    ElegantRadio(
        selected = accent == candidate,
        onSelect = { accent = candidate },
        label = candidate,
    )
}

ElegantRadio(
    selected = true,
    onSelect = {},
    enabled = false,
    label = "Unavailable",
)
```

## Properties

### ElegantRadio Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `selected` | `Boolean` | Whether this radio communicates the chosen option | - | Yes |
| `onSelect` | `() -> Unit` | Callback invoked when the radio accepts a selection | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the interactive row | `Modifier` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted | `true` | No |
| `label` | `String?` | Optional text label shown after the indicator | `null` | No |
| `colors` | `ElegantRadioColors` | Theme-aware state colors | `ElegantRadioDefaults.colors()` | No |
| `interactionSource` | `MutableInteractionSource?` | Optional hoisted interaction source for observing or controlling state | `null` | No |

### ElegantRadioDefaults

| Member | Type | Description |
| --- | --- | --- |
| `BoxSize` | `Dp` | 20dp circular indicator size |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive row height |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantRadioColors` | Theme-aware Light/Dark colors |

### ElegantRadioColors

`ElegantRadioColors` contains the indicator color for the selected and unselected states plus hovered, pressed, disabled, and focused variants. Start with `ElegantRadioDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Exclusive Radio Group

Share one mutable selection state across the group so exactly one radio is selected.

```kotlin
var delivery by remember { mutableStateOf("Standard") }

Column {
    for (option in listOf("Standard", "Express", "Overnight")) {
        ElegantRadio(
            selected = delivery == option,
            onSelect = { delivery = option },
            label = option,
        )
    }
}
```

### Custom Colors

```kotlin
val baseColors = ElegantRadioDefaults.colors()

ElegantRadio(
    selected = selected,
    onSelect = onSelect,
    colors = baseColors.copy(
        selectedColor = Color(0xFF147D64),
        unselectedColor = Color(0xFF92969E),
    ),
    label = "Custom",
)
```

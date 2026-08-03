# NumberPicker

`ElegantNumberPicker` is a refined vertical stepper for integers: a large centered value with circular increase and decrease buttons above and below it. Use it for counts, seats, pages, or any value where a precise number is chosen without a keyboard. A quick press steps once; holding a button repeats the step after a short delay for fast adjustments.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=number-picker" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.numberpicker.ElegantNumberPicker
import com.elegant.compose.ui.numberpicker.ElegantNumberPickerColors
import com.elegant.compose.ui.numberpicker.ElegantNumberPickerDefaults
```

## Basic Usage

`ElegantNumberPicker` is a controlled component: keep the integer `value` in a `remember`-backed state and write every accepted step back from `onValueChange`. The buttons step by `step` and stop at `minValue`..`maxValue`.

```kotlin
var quantity by remember { mutableStateOf(1) }

ElegantNumberPicker(
    value = quantity,
    onValueChange = { quantity = it },
    minValue = 1,
    maxValue = 99,
)
```

## Component States

Holding a step button repeats the step after an initial 350ms delay and then every 80ms until the pointer is released; a quick press applies `step` once. At a range boundary the corresponding button is disabled and rendered with the disabled color but stays visible. When `enabled` is false the picker rejects pointer interaction, both buttons and the value drop to the disabled color, and `onValueChange` is never invoked. An inverted range (`minValue` greater than `maxValue`) is treated as unbounded: stepping never clamps and only stops at the `Int` limits.

```kotlin
ElegantNumberPicker(
    value = 4,
    onValueChange = {},
    enabled = false,
)

ElegantNumberPicker(
    value = 12,
    onValueChange = { count = it },
    minValue = 1,
    maxValue = 50,
    step = 2,
)
```

## Properties

### ElegantNumberPicker Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `value` | `Int` | Current integer, owned by the caller | - | Yes |
| `onValueChange` | `(Int) -> Unit` | Callback invoked with the newest accepted integer value | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the picker root | `Modifier` | No |
| `enabled` | `Boolean` | Whether stepping and pointer interaction are accepted | `true` | No |
| `minValue` | `Int` | Smallest value reachable by stepping; inverted ranges behave as unbounded | `0` | No |
| `maxValue` | `Int` | Largest value reachable by stepping; inverted ranges behave as unbounded | `Int.MAX_VALUE` | No |
| `step` | `Int` | Increment applied per step; non-positive values fall back to 1 | `1` | No |
| `colors` | `ElegantNumberPickerColors` | Theme-aware state colors | `ElegantNumberPickerDefaults.colors()` | No |

### ElegantNumberPickerDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum height reserved for the centered value |
| `ButtonSize` | `Dp` | 40dp edge length of each circular step button |
| `colors()` | `ElegantNumberPickerColors` | Theme-aware Light/Dark colors matching the stepper roles |

### ElegantNumberPickerColors

`ElegantNumberPickerColors` holds the container, content, secondary content, disabled content, hovered container, pressed container, and divider roles resolved from the theme hierarchy. Start with `ElegantNumberPickerDefaults.colors()` and use `copy(...)` only for a deliberate product-specific change.

## Advanced Usage

### Quantity Card

```kotlin
var seats by remember { mutableStateOf(2) }

ElegantCard {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ElegantSpacing.xl),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Seats",
            color = ElegantTheme.colors.textSecondary,
            style = ElegantTheme.typography.labelMedium,
        )
        ElegantNumberPicker(
            value = seats,
            onValueChange = { seats = it },
            minValue = 1,
            maxValue = 8,
        )
    }
}
```

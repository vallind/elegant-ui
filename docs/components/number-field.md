# NumberField

`ElegantNumberField` is a refined integer-entry field with an optional label, placeholder, supporting or error text, a leading icon, compact increase and decrease step buttons, and arrow-key stepping. Use it for quantities, counts, ages, or any value where the keyboard and the pointer must both reach the same constrained number.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=number-field" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.numberfield.ElegantNumberField
import com.elegant.compose.ui.numberfield.ElegantNumberFieldColors
import com.elegant.compose.ui.numberfield.ElegantNumberFieldDefaults
```

## Basic Usage

`ElegantNumberField` is a controlled component: keep the integer `value` in a `remember`-backed state and write every accepted change back from `onValueChange`. While the field has focus, the edited text is a local draft; incomplete input such as an empty draft or a dangling minus sign stays uncommitted, and the draft resets to `value` when focus is lost.

```kotlin
var quantity by remember { mutableStateOf(1) }

ElegantNumberField(
    value = quantity,
    onValueChange = { quantity = it },
    label = "Quantity",
    minValue = 1,
    maxValue = 99,
    supportingText = "Orders of at least 1 ship free.",
)
```

## Component States

`ElegantNumberField` follows the interaction precedence: disabled, error border, focused border, hovered border, resting. `isError` turns the border `statusCritical`, replaces the supporting text below the field with `errorText`, and announces the message through semantics.

When `enabled` is false the field rejects focus, input, arrow-key stepping, and both step buttons; the buttons stay visible with disabled colors at their range boundaries. An inverted range (`minValue` greater than `maxValue`) is treated as unbounded: no value is rejected and stepping only stops at the `Int` limits.

```kotlin
ElegantNumberField(
    value = 4,
    onValueChange = {},
    label = "Disabled field",
    enabled = false,
)

ElegantNumberField(
    value = 12,
    onValueChange = { count = it },
    label = "People",
    minValue = 1,
    maxValue = 50,
    isError = true,
    errorText = "The hall fits at most 50 people.",
)
```

## Properties

### ElegantNumberField Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `value` | `Int` | Current committed integer, owned by the caller | - | Yes |
| `onValueChange` | `(Int) -> Unit` | Callback invoked with the newest accepted integer value | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the field root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the field accepts focus, input, and stepping | `true` | No |
| `label` | `String?` | Label shown above the field | `null` | No |
| `placeholder` | `String?` | Hint shown inside the field while it is enabled and empty | `null` | No |
| `minValue` | `Int` | Smallest accepted value; inverted ranges behave as unbounded | `Int.MIN_VALUE` | No |
| `maxValue` | `Int` | Largest accepted value; inverted ranges behave as unbounded | `Int.MAX_VALUE` | No |
| `step` | `Int` | Increment used by the step buttons and arrow keys; non-positive values fall back to 1 | `ElegantNumberFieldDefaults.Step` | No |
| `supportingText` | `String?` | Guidance shown below the field unless error text is shown | `null` | No |
| `isError` | `Boolean` | Whether the field communicates an error state | `false` | No |
| `errorText` | `String?` | Error message shown below the field and announced when `isError` | `null` | No |
| `colors` | `ElegantNumberFieldColors` | Theme-aware state colors | `ElegantNumberFieldDefaults.colors()` | No |
| `leadingIcon` | `@Composable (() -> Unit)?` | Content before the input area, tinted with the field content color | `null` | No |

### ElegantNumberFieldDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Step` | `Int` | Default increment of `1` used by stepping |
| `MinimumTouchHeight` | `Dp` | 48dp minimum field-container height |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantNumberFieldColors` | Theme-aware Light/Dark colors matching the filled-input roles |

### ElegantNumberFieldColors

`ElegantNumberFieldColors` holds the same container, border, content, placeholder, label, supporting-text, and error-text roles as `ElegantInputColors`, resolved from the filled-input theme hierarchy. Start with `ElegantNumberFieldDefaults.colors()` and use `copy(...)` only for a deliberate product-specific change.

## Advanced Usage

### Bounded Stepping

```kotlin
var guests by remember { mutableStateOf(2) }

ElegantNumberField(
    value = guests,
    onValueChange = { guests = it },
    label = "Guests",
    minValue = 1,
    maxValue = 8,
    step = 2,
    supportingText = "The step buttons stop at the range boundaries.",
)
```

### Custom Error Styling

```kotlin
val baseColors = ElegantNumberFieldDefaults.colors()

ElegantNumberField(
    value = age,
    onValueChange = { age = it },
    label = "Age",
    minValue = 18,
    colors = baseColors.copy(
        errorBorderColor = Color(0xFFC63D52),
        errorTextColor = Color(0xFFC63D52),
    ),
    isError = true,
    errorText = "You must be at least 18 years old.",
)
```

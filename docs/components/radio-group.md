# RadioGroup

`ElegantRadioGroup` presents a column of `ElegantRadio` rows that share one exclusive selection. Each row keeps the 48dp interactive target from the item radio, while the group adds the vertical rhythm between rows and an optional supporting text below the items. Use it for settings, filters, and forms where exactly one option must be chosen from a bounded set.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=radio-group" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.radiogroup.ElegantRadioGroup
import com.elegant.compose.ui.radiogroup.ElegantRadioGroupColors
import com.elegant.compose.ui.radiogroup.ElegantRadioGroupDefaults
import com.elegant.compose.ui.radiogroup.ElegantRadioGroupItem
```

## Basic Usage

Build a stable model list and share one `selectedValue` across the group. The callback reports the value of the item the user picked.

```kotlin
val deliveryItems = listOf(
    ElegantRadioGroupItem(text = "Standard", value = "standard"),
    ElegantRadioGroupItem(text = "Express", value = "express"),
)

ElegantRadioGroup(
    selectedValue = selectedValue,
    onSelect = { selectedValue = it },
    items = deliveryItems,
)
```

## Component States

Each row announces `Role.RadioButton` and its selection state, shows a focus ring when the theme enables focus rings, and keeps the 48dp minimum interactive target. A disabled group rejects interaction on every row; a disabled item keeps its selection visible but rejects interaction on that row only. When `selectedValue` is null or blank, no item is selected.

```kotlin
var plan by remember { mutableStateOf("pro") }

ElegantRadioGroup(
    selectedValue = plan,
    onSelect = { plan = it },
    items = listOf(
        ElegantRadioGroupItem(text = "Free", value = "free"),
        ElegantRadioGroupItem(text = "Pro", value = "pro"),
        ElegantRadioGroupItem(text = "Team", value = "team", enabled = false),
    ),
)

ElegantRadioGroup(
    selectedValue = "pro",
    onSelect = {},
    enabled = false,
    items = listOf(
        ElegantRadioGroupItem(text = "Free", value = "free"),
        ElegantRadioGroupItem(text = "Pro", value = "pro"),
    ),
)
```

## Properties

### ElegantRadioGroup Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `selectedValue` | `String?` | Value of the selected item; null or blank selects nothing | - | Yes |
| `onSelect` | `(String) -> Unit` | Callback invoked with the value of the item the user picked | - | Yes |
| `items` | `List<ElegantRadioGroupItem>` | Ordered options rendered as radio rows | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the column of rows | `Modifier` | No |
| `enabled` | `Boolean` | Whether the group accepts user interaction | `true` | No |
| `colors` | `ElegantRadioGroupColors` | Theme-aware text colors | `ElegantRadioGroupDefaults.colors()` | No |
| `supportingText` | `String?` | Optional guidance shown below the items | `null` | No |

### ElegantRadioGroupItem

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Label rendered by the row radio | - | Yes |
| `value` | `String` | Value reported by `onSelect` when the option is picked | - | Yes |
| `enabled` | `Boolean` | Whether this option accepts interaction | `true` | No |

### ElegantRadioGroupDefaults

| Member | Type | Description |
| --- | --- | --- |
| `ItemGap` | `Dp` | 4dp vertical spacing between rows |
| `colors()` | `ElegantRadioGroupColors` | Theme-aware Light/Dark colors |

### ElegantRadioGroupColors

`ElegantRadioGroupColors` contains the text colors for the item labels and the supporting text. The item radios draw their labels with the theme text colors, which `labelColor` and `disabledLabelColor` mirror; `supportingTextColor` and `disabledLabelColor` style the group's own supporting text. Start with `ElegantRadioGroupDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Supporting Text

The optional `supportingText` sits below the items and dims when the group is disabled.

```kotlin
ElegantRadioGroup(
    selectedValue = region,
    onSelect = { region = it },
    items = regions,
    supportingText = "The delivery estimate updates for the chosen region.",
)
```

### Form Row with an Input

Combine a group with an input field for a compact form section.

```kotlin
var zone by remember { mutableStateOf("north") }
var street by remember { mutableStateOf("") }

ElegantRadioGroup(
    selectedValue = zone,
    onSelect = { zone = it },
    items = listOf(
        ElegantRadioGroupItem(text = "North zone", value = "north"),
        ElegantRadioGroupItem(text = "South zone", value = "south"),
    ),
)

ElegantInput(
    value = street,
    onValueChange = { street = it },
    label = "Street",
    placeholder = "Street name and number",
)
```

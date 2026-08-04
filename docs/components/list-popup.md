# ListPopup

`ElegantListPopup` shows a data-driven single-choice option list on a temporary surface anchored below the Box that contains both the trigger and the popup: the caller owns the trigger, places `ElegantListPopup` next to it in the same Box, and the surface drops below that Box, start-aligned and clamped into the window. The option matching the selected value is highlighted with the interactive color, a subtle background, and a trailing check glyph. Clicking outside, pressing Escape, or using the platform back gesture dismisses it. The focusable popup moves keyboard focus into the list when it opens; selecting an option reports it through `onOptionSelected`, and the caller owns `expanded` — the popup does not close itself after a selection.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=list-popup" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.listpopup.ElegantListPopup
import com.elegant.compose.ui.listpopup.ElegantListPopupDefaults
import com.elegant.compose.ui.listpopup.ElegantListPopupOption
```

## Basic Usage

Place `ElegantListPopup` inside the same Box as the trigger. The popup anchors to that Box, so wrapping only the trigger keeps the dropdown exactly on the trigger; the caller toggles `expanded`, resets it in `onDismissRequest`, and stores the chosen option's value back into `selectedValue` from `onOptionSelected`.

```kotlin
var expanded by remember { mutableStateOf(false) }
var selectedValue by remember { mutableStateOf("paris") }

Box {
    ElegantButton(onClick = { expanded = true }) {
        Text("Choose city")
    }
    ElegantListPopup(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        options = listOf(
            ElegantListPopupOption(text = "Paris", value = "paris"),
            ElegantListPopupOption(text = "London", value = "london"),
            ElegantListPopupOption(text = "Tokyo", value = "tokyo"),
        ),
        selectedValue = selectedValue,
        onOptionSelected = { option ->
            selectedValue = option.value
            expanded = false
        },
    )
}
```

## Options

`ElegantListPopupOption` pairs the rendered `text` with a stable `value` identity compared against `selectedValue` and delivered with the selection. Disabled options render with the tertiary text color, ignore clicks, and announce the disabled state; a disabled option can still be the selected value.

```kotlin
ElegantListPopupOption(
    text = "Berlin",
    value = "berlin",
    enabled = false,
)
```

## Component States

Selecting an enabled option invokes `onOptionSelected` with that option; the popup does not dismiss itself, so the caller decides in the callback. Outside click, Escape, and back dismiss through `onDismissRequest`. The focusable popup moves keyboard focus into the list when it opens and back to the trigger on dismissal; focused options activate with Enter or Space. The selected option shows the interactive text color, the selected background, and the check glyph; hovered options show the hovered background; disabled options never invoke callbacks.

```kotlin
ElegantListPopup(
    expanded = expanded,
    onDismissRequest = { expanded = false },
    options = options,
    selectedValue = selectedValue,
    onOptionSelected = { option -> selectedValue = option.value },
)
```

## Properties

### ElegantListPopup Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `expanded` | `Boolean` | Whether the popup surface is shown | - | Yes |
| `onDismissRequest` | `() -> Unit` | Called when the user requests dismissal | - | Yes |
| `options` | `List<ElegantListPopupOption>` | Option list rendered in the popup, in the given order | - | Yes |
| `selectedValue` | `String?` | Value of the currently selected option, matched against `ElegantListPopupOption.value` | `null` | No |
| `onOptionSelected` | `(ElegantListPopupOption) -> Unit` | Called with the option chosen by the user | - | Yes |
| `modifier` | `Modifier` | Modifier applied to the scrollable option column | `Modifier` | No |
| `colors` | `ElegantListPopupColors` | Popup surface and option colors | `ElegantListPopupDefaults.colors()` | No |

### ElegantListPopupOption

| Property Name | Type | Description |
| --- | --- | --- |
| `text` | `String` | Text rendered in the option row |
| `value` | `String` | Stable identity matched against the selection |
| `enabled` | `Boolean` | Whether the option can be chosen |

### ElegantListPopupColors

| Property Name | Type | Description |
| --- | --- | --- |
| `containerColor` | `Color` | Popup surface background |
| `contentColor` | `Color` | Text color of enabled, unselected options |
| `disabledContentColor` | `Color` | Text color of disabled options |
| `selectedContentColor` | `Color` | Text and check color of the selected option |
| `selectedContainerColor` | `Color` | Background of the selected option |
| `hoveredContainerColor` | `Color` | Background of hovered options |
| `borderColor` | `Color` | Popup surface border color |

### ElegantListPopupDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinWidth` | `Dp` | 160dp minimum popup width; the surface grows to fit the widest option |
| `MaxHeight` | `Dp` | 320dp maximum popup height before the option list scrolls |
| `ItemHeight` | `Dp` | 40dp height of one option row |
| `HorizontalPadding` | `Dp` | 16dp horizontal padding inside every option row |
| `AnimationDurationMillis` | `Int` | 90ms popup entrance transition duration |

## Advanced Usage

### Popup in a Form Row

Pair a list popup with an `ElegantInput` so a form reads the city from the popup selection and the free-text note from the input; both fields keep the caller-owned values.

```kotlin
var selectedValue by remember { mutableStateOf("paris") }
var note by remember { mutableStateOf("") }

Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
    ElegantInput(
        value = note,
        onValueChange = { note = it },
        label = "Delivery note",
        placeholder = "Optional note",
    )
    Box {
        ElegantButton(onClick = { expanded = true }) {
            Text("Choose city")
        }
        ElegantListPopup(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            options = options,
            selectedValue = selectedValue,
            onOptionSelected = { option ->
                selectedValue = option.value
                expanded = false
            },
        )
    }
}
```

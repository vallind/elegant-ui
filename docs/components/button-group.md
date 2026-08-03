# Button Group

`ElegantButtonGroup` is a segmented control that presents mutually exclusive options in a single compact row. It renders one equal-width cell per item, outlines the control with a 1dp border, highlights the selected cell, and announces radio-button semantics on every cell.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=button-group" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.buttongroup.ElegantButtonGroup
import com.elegant.compose.ui.buttongroup.ElegantButtonGroupColors
import com.elegant.compose.ui.buttongroup.ElegantButtonGroupDefaults
import com.elegant.compose.ui.buttongroup.ElegantButtonGroupItem
```

## Basic Usage

`ElegantButtonGroup` is controlled: keep `selectedIndex` in state and update it from `onSelect`. Every cell takes an equal share of the row width.

```kotlin
var selected by remember { mutableStateOf(0) }
val items = listOf(
    ElegantButtonGroupItem("Day"),
    ElegantButtonGroupItem("Week"),
    ElegantButtonGroupItem("Month"),
)

ElegantButtonGroup(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = items,
)
```

## Item Model

`ElegantButtonGroupItem` is the stable data model that drives the group. Set `enabled` to `false` on a model to keep that cell visible but non-interactive.

```kotlin
val items = listOf(
    ElegantButtonGroupItem("General"),
    ElegantButtonGroupItem("Security", enabled = false),
    ElegantButtonGroupItem("Billing"),
)
```

## Component States

The cell background follows the precedence disabled, selected, pressed, hovered, resting (transparent); the label color follows disabled, selected, resting. Every cell announces `Role.RadioButton` with its `selected` and `disabled` state and never invokes `onSelect` while the group or the cell is disabled. Pass `null` to `selectedIndex` to show no selection, and an empty `items` list renders nothing.

```kotlin
var selected by remember { mutableStateOf(0) }

ElegantButtonGroup(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = listOf(
        ElegantButtonGroupItem("Draft"),
        ElegantButtonGroupItem("Published"),
        ElegantButtonGroupItem("Archived", enabled = false),
    ),
)

ElegantButtonGroup(
    selectedIndex = 1,
    onSelect = {},
    items = listOf(ElegantButtonGroupItem("Offline"), ElegantButtonGroupItem("Online")),
    enabled = false,
)
```

## Properties

### ElegantButtonGroup Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `selectedIndex` | `Int?` | Index of the selected cell, or `null` for no selection; out-of-range values clamp to the closest cell | `null` | Yes |
| `onSelect` | `(Int) -> Unit` | Callback invoked with the newly selected index | - | Yes |
| `items` | `List<ElegantButtonGroupItem>` | Cell models rendered by the group | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the group root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the group accepts user interaction | `true` | No |
| `colors` | `ElegantButtonGroupColors` | Theme-aware state colors | `ElegantButtonGroupDefaults.colors()` | No |

### ElegantButtonGroupItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Label rendered with the labelMedium style | - | Yes |
| `enabled` | `Boolean` | Whether this cell accepts user interaction | `true` | No |

### ElegantButtonGroupDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | Minimum interactive root height used by every cell |
| `Height` | `Dp` | Visual height of the segmented control |
| `HorizontalPadding` | `Dp` | Horizontal padding reserved inside each cell |
| `AnimationDurationMillis` | `Int` | Standard state-transition duration |
| `colors()` | `ElegantButtonGroupColors` | Theme-aware Light/Dark colors |

### ElegantButtonGroupColors

`ElegantButtonGroupColors` holds the control container, border, and divider color plus the cell container and label colors for the resting, selected, hovered, pressed, and disabled states. Start with `ElegantButtonGroupDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Switching Content

Use the same controlled state to drive both the group and the content below it.

```kotlin
var selected by remember { mutableStateOf(0) }
val items = listOf(
    ElegantButtonGroupItem("List"),
    ElegantButtonGroupItem("Grid"),
)

Column {
    ElegantButtonGroup(
        selectedIndex = selected,
        onSelect = { selected = it },
        items = items,
    )
    Text(
        text = "Viewing ${items[selected].text}",
        style = ElegantTheme.typography.bodyMedium,
        color = ElegantTheme.colors.textSecondary,
        modifier = Modifier.padding(top = ElegantSpacing.lg),
    )
}
```

### No Selection

Pass `null` to render the group without a selected cell.

```kotlin
ElegantButtonGroup(
    selectedIndex = null,
    onSelect = { selected = it },
    items = listOf(ElegantButtonGroupItem("Compact"), ElegantButtonGroupItem("Comfortable")),
)
```

### Custom Colors

```kotlin
val baseColors = ElegantButtonGroupDefaults.colors()

ElegantButtonGroup(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = listOf(ElegantButtonGroupItem("Overview"), ElegantButtonGroupItem("Details")),
    colors = baseColors.copy(
        selectedContainerColor = Color(0xFF147D64),
        selectedContentColor = Color(0xFFFFFFFF),
    ),
)
```

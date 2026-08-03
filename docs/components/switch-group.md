# Switch Group

`ElegantSwitchGroup` is a vertical list of related switch rows with one shared, caller-owned selection set and an optional supporting caption. Use it for settings sections and toggled preferences where several related options share a single controlled state.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=switch-group" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.switchgroup.ElegantSwitchGroup
import com.elegant.compose.ui.switchgroup.ElegantSwitchGroupColors
import com.elegant.compose.ui.switchgroup.ElegantSwitchGroupDefaults
import com.elegant.compose.ui.switchgroup.ElegantSwitchGroupItem
```

## Basic Usage

The group is fully controlled: `selectedValues` holds the current selection and `onToggle` reports the item value with the requested state. The caller owns the `Set` and should copy it when toggling — an immutable `Set` is recommended.

```kotlin
var channels by remember { mutableStateOf(setOf("push")) }

ElegantSwitchGroup(
    selectedValues = channels,
    onToggle = { value, checked ->
        channels = if (checked) channels + value else channels - value
    },
    items = listOf(
        ElegantSwitchGroupItem(text = "Push notifications", value = "push"),
        ElegantSwitchGroupItem(text = "Email digest", value = "email"),
        ElegantSwitchGroupItem(text = "In-app mentions", value = "mentions"),
    ),
)
```

## Component Types

### ElegantSwitchGroupItem

Each item pairs the visible `text` with a stable `value` matched against `selectedValues`. `enabled = false` keeps the row visible while disabling only that row.

## Component States

A row is interactive only when the group `enabled` and the item's own `enabled` both allow it; disabled rows never invoke `onToggle`. Every row keeps the 48dp minimum interactive height and `Role.Switch` semantics of `ElegantSwitch`, with the label at the start and the switch at the end, while the group adds a 4dp vertical rhythm and an optional supporting caption.

```kotlin
ElegantSwitchGroup(
    selectedValues = setOf("camera"),
    onToggle = { _, _ -> },
    items = listOf(
        ElegantSwitchGroupItem(text = "Camera", value = "camera"),
        ElegantSwitchGroupItem(text = "Microphone", value = "microphone", enabled = false),
    ),
)

ElegantSwitchGroup(
    selectedValues = setOf("notifications"),
    onToggle = { _, _ -> },
    enabled = false,
    items = listOf(
        ElegantSwitchGroupItem(text = "Notifications", value = "notifications"),
        ElegantSwitchGroupItem(text = "Announcements", value = "announcements"),
    ),
)
```

## Properties

### ElegantSwitchGroup Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `selectedValues` | `Set<String>` | Values currently selected, owned by the caller | - | Yes |
| `onToggle` | `(String, Boolean) -> Unit` | Callback invoked with the item value and the requested selection state | - | Yes |
| `items` | `List<ElegantSwitchGroupItem>` | Item models rendered as switch rows | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the group root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the whole group accepts user interaction | `true` | No |
| `colors` | `ElegantSwitchGroupColors` | Theme-aware text colors | `ElegantSwitchGroupDefaults.colors()` | No |
| `supportingText` | `String?` | Optional supporting text rendered below the rows | `null` | No |

### ElegantSwitchGroupItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Label rendered on the switch row | - | Yes |
| `value` | `String` | Stable identifier matched against `selectedValues` | - | Yes |
| `enabled` | `Boolean` | Whether this item accepts user interaction | `true` | No |

### ElegantSwitchGroupDefaults

| Member | Type | Description |
| --- | --- | --- |
| `ItemGap` | `Dp` | 4dp vertical gap between rows |
| `colors()` | `ElegantSwitchGroupColors` | Theme-aware Light/Dark text colors |

### ElegantSwitchGroupColors

`ElegantSwitchGroupColors` carries the row label palette (`labelColor`, `disabledLabelColor`) and the supporting caption color (`supportingTextColor`). Start with `ElegantSwitchGroupDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Settings Section with Supporting Text

```kotlin
var channels by remember { mutableStateOf(setOf("push")) }

ElegantSwitchGroup(
    selectedValues = channels,
    onToggle = { value, checked ->
        channels = if (checked) channels + value else channels - value
    },
    items = listOf(
        ElegantSwitchGroupItem(text = "Push notifications", value = "push"),
        ElegantSwitchGroupItem(text = "Email digest", value = "email"),
        ElegantSwitchGroupItem(text = "In-app mentions", value = "mentions"),
    ),
    supportingText = "Choose how you want to be notified.",
)
```

### Custom Colors

```kotlin
val baseColors = ElegantSwitchGroupDefaults.colors()

ElegantSwitchGroup(
    selectedValues = selectedValues,
    onToggle = onToggle,
    items = items,
    colors = baseColors.copy(
        supportingTextColor = Color(0xFF6E727A),
    ),
    supportingText = "Custom caption",
)
```

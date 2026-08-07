# Checkbox Group

`ElegantCheckboxGroup` is a vertical list of related checkbox rows with one shared, caller-owned selection set and an optional supporting caption. Use it for permission pickers, preference sections, and multi-select filters where several related options share a single controlled state.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=checkbox-group" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.checkboxgroup.ElegantCheckboxGroup
import com.elegant.compose.ui.checkboxgroup.ElegantCheckboxGroupColors
import com.elegant.compose.ui.checkboxgroup.ElegantCheckboxGroupDefaults
import com.elegant.compose.ui.checkboxgroup.ElegantCheckboxGroupItem
```

## Basic Usage

The group is fully controlled: `selectedValues` holds the current selection and `onToggle` reports the item value with the requested state. The caller owns the `Set` and should copy it when toggling — an immutable `Set` is recommended.

```kotlin
var channels by remember { mutableStateOf(setOf("stable")) }

ElegantCheckboxGroup(
    selectedValues = channels,
    onToggle = { value, checked ->
        channels = if (checked) channels + value else channels - value
    },
    items = listOf(
        ElegantCheckboxGroupItem(text = "Stable channel", value = "stable"),
        ElegantCheckboxGroupItem(text = "Beta channel", value = "beta"),
        ElegantCheckboxGroupItem(text = "Nightly", value = "nightly"),
    ),
)
```

## Component Types

### ElegantCheckboxGroupItem

Each item pairs the visible `text` with a stable `value` matched against `selectedValues`. `enabled = false` keeps the row visible while disabling only that row.

## Component States

A row is interactive only when the group `enabled` and the item's own `enabled` both allow it; disabled rows never invoke `onToggle`. Every row keeps its own `Role.Checkbox` semantics, 48dp minimum interactive height, and animated checkmark from `ElegantCheckbox`, while the group adds a 4dp vertical rhythm and an optional supporting caption.

```kotlin
ElegantCheckboxGroup(
    selectedValues = setOf("camera"),
    onToggle = { _, _ -> },
    items = listOf(
        ElegantCheckboxGroupItem(text = "Camera", value = "camera"),
        ElegantCheckboxGroupItem(text = "Microphone", value = "microphone", enabled = false),
    ),
)

ElegantCheckboxGroup(
    selectedValues = setOf("notifications"),
    onToggle = { _, _ -> },
    enabled = false,
    items = listOf(
        ElegantCheckboxGroupItem(text = "Notifications", value = "notifications"),
        ElegantCheckboxGroupItem(text = "Announcements", value = "announcements"),
    ),
)
```

## Properties

### ElegantCheckboxGroup Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `selectedValues` | `Set<String>` | Values currently selected, owned by the caller | - | Yes |
| `onToggle` | `(String, Boolean) -> Unit` | Callback invoked with the item value and the requested selection state | - | Yes |
| `items` | `List<ElegantCheckboxGroupItem>` | Item models rendered as checkbox rows | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the group root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the whole group accepts user interaction | `true` | No |
| `colors` | `ElegantCheckboxGroupColors` | Theme-aware text colors | `ElegantCheckboxGroupDefaults.colors()` | No |
| `supportingText` | `String?` | Optional supporting text rendered below the rows | `null` | No |

### ElegantCheckboxGroupItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Label rendered on the checkbox row | - | Yes |
| `value` | `String` | Stable identifier matched against `selectedValues` | - | Yes |
| `enabled` | `Boolean` | Whether this item accepts user interaction | `true` | No |

### ElegantCheckboxGroupDefaults

| Member | Type | Description |
| --- | --- | --- |
| `ItemGap` | `Dp` | 4dp vertical gap between rows |
| `colors()` | `ElegantCheckboxGroupColors` | Theme-aware Light/Dark text colors |

### ElegantCheckboxGroupColors

`ElegantCheckboxGroupColors` carries the item label palette (`labelColor`, `disabledLabelColor`) and the supporting caption color (`supportingTextColor`). Start with `ElegantCheckboxGroupDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy. Row labels render through `ElegantCheckbox` from the active theme, so the defaults stay aligned by construction.

## Advanced Usage

### Permission Picker with Supporting Text

```kotlin
var permissions by remember { mutableStateOf(setOf("camera")) }

ElegantCheckboxGroup(
    selectedValues = permissions,
    onToggle = { value, checked ->
        permissions = if (checked) permissions + value else permissions - value
    },
    items = listOf(
        ElegantCheckboxGroupItem(text = "Camera", value = "camera"),
        ElegantCheckboxGroupItem(text = "Photos", value = "photos"),
        ElegantCheckboxGroupItem(text = "Microphone", value = "microphone"),
    ),
    supportingText = "Choose what this app may access.",
)
```

### Custom Colors

```kotlin
val baseColors = ElegantCheckboxGroupDefaults.colors()

ElegantCheckboxGroup(
    selectedValues = selectedValues,
    onToggle = onToggle,
    items = items,
    colors = baseColors.copy(
        supportingTextColor = Color(0xFF6E727A),
    ),
    supportingText = "Custom caption",
)
```

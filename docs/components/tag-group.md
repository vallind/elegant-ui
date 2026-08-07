# Tag Group

`ElegantTagGroup` is a wrapping row of related selectable tag chips with one shared, caller-owned selection set. Use it for filter bars, category pickers, and multi-select keyword lists where several related choices share a single controlled state.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=tag-group" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.taggroup.ElegantTagGroup
import com.elegant.compose.ui.taggroup.ElegantTagGroupColors
import com.elegant.compose.ui.taggroup.ElegantTagGroupDefaults
import com.elegant.compose.ui.taggroup.ElegantTagGroupItem
```

## Basic Usage

The group is fully controlled: `selectedValues` holds the current selection and `onToggle` reports the item value with the requested state. The caller owns the `Set` and should copy it when toggling — an immutable `Set` is recommended.

```kotlin
var filters by remember { mutableStateOf(setOf("design")) }

ElegantTagGroup(
    selectedValues = filters,
    onToggle = { value, checked ->
        filters = if (checked) filters + value else filters - value
    },
    items = listOf(
        ElegantTagGroupItem(text = "Design", value = "design"),
        ElegantTagGroupItem(text = "Engineering", value = "engineering"),
        ElegantTagGroupItem(text = "Release", value = "release"),
    ),
)
```

## Component Types

### ElegantTagGroupItem

Each item pairs the visible `text` with a stable `value` matched against `selectedValues`. `enabled = false` keeps the chip visible while disabling only that chip.

## Component States

A chip is interactive only when the group `enabled` and the item's own `enabled` both allow it; disabled chips never invoke `onToggle`. Every chip announces `Role.Button` and its `selected` state, keeps a 48dp minimum interactive target with a compact 28dp pill inside, and switches between a filled pill when selected and an outlined pill otherwise. The group flows on an 8dp rhythm and wraps across multiple lines automatically.

```kotlin
ElegantTagGroup(
    selectedValues = setOf("design"),
    onToggle = { _, _ -> },
    items = listOf(
        ElegantTagGroupItem(text = "Design", value = "design"),
        ElegantTagGroupItem(text = "Release", value = "release", enabled = false),
    ),
)

ElegantTagGroup(
    selectedValues = setOf("notifications"),
    onToggle = { _, _ -> },
    enabled = false,
    items = listOf(
        ElegantTagGroupItem(text = "Notifications", value = "notifications"),
        ElegantTagGroupItem(text = "Announcements", value = "announcements"),
    ),
)
```

## Properties

### ElegantTagGroup Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `selectedValues` | `Set<String>` | Values currently selected, owned by the caller | - | Yes |
| `onToggle` | `(String, Boolean) -> Unit` | Callback invoked with the item value and the requested selection state | - | Yes |
| `items` | `List<ElegantTagGroupItem>` | Item models rendered as selectable tag chips | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the group root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the whole group accepts user interaction | `true` | No |
| `colors` | `ElegantTagGroupColors` | Theme-aware chip colors | `ElegantTagGroupDefaults.colors()` | No |

### ElegantTagGroupItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Label rendered on the tag chip | - | Yes |
| `value` | `String` | Stable identifier matched against `selectedValues` | - | Yes |
| `enabled` | `Boolean` | Whether this item accepts user interaction | `true` | No |

### ElegantTagGroupDefaults

| Member | Type | Description |
| --- | --- | --- |
| `ItemGap` | `Dp` | 8dp gap between chips in both flow axes |
| `colors()` | `ElegantTagGroupColors` | Theme-aware Light/Dark chip colors |

### ElegantTagGroupColors

`ElegantTagGroupColors` carries the selected pill palette (`selectedContainerColor`, `selectedContentColor`), the unselected pill palette (`unselectedContainerColor`, `unselectedContentColor`, `unselectedBorderColor`), and the disabled label color (`disabledContentColor`). Start with `ElegantTagGroupDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Filter Bar

```kotlin
var platforms by remember { mutableStateOf(setOf("android")) }

ElegantTagGroup(
    selectedValues = platforms,
    onToggle = { value, checked ->
        platforms = if (checked) platforms + value else platforms - value
    },
    items = listOf(
        ElegantTagGroupItem(text = "Android", value = "android"),
        ElegantTagGroupItem(text = "Desktop", value = "desktop"),
        ElegantTagGroupItem(text = "Web", value = "web"),
        ElegantTagGroupItem(text = "Wasm", value = "wasm"),
    ),
)
```

### Custom Colors

```kotlin
val baseColors = ElegantTagGroupDefaults.colors()

ElegantTagGroup(
    selectedValues = selectedValues,
    onToggle = onToggle,
    items = items,
    colors = baseColors.copy(
        selectedContainerColor = Color(0xFF5840D6),
    ),
)
```

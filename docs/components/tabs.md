# Tabs

`ElegantTabRow` is a controlled tab strip for switching between mutually exclusive views. It renders `ElegantTab` models at equal width in fixed mode, or at natural width with horizontal scrolling in scrollable mode, and keeps the selection reachable with wrap-around arrow-key navigation.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=tabs" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.tabs.ElegantTab
import com.elegant.compose.ui.tabs.ElegantTabColors
import com.elegant.compose.ui.tabs.ElegantTabDefaults
import com.elegant.compose.ui.tabs.ElegantTabRow
```

## Basic Usage

`ElegantTabRow` is controlled: keep `selectedIndex` in state and update it from `onSelect`. By default every tab takes an equal share of the row width.

```kotlin
var selected by remember { mutableStateOf(0) }
val tabs = listOf(
    ElegantTab("Overview"),
    ElegantTab("Projects"),
    ElegantTab("Settings"),
)

ElegantTabRow(
    tabs = tabs,
    selectedIndex = selected,
    onSelect = { selected = it },
)
```

### Scrollable Mode

Set `scrollable = true` to let many tabs keep their natural width and scroll horizontally.

```kotlin
ElegantTabRow(
    tabs = tabs,
    selectedIndex = selected,
    onSelect = { selected = it },
    scrollable = true,
)
```

## Tab Model

`ElegantTab` is the stable data model that drives the row. Set `enabled` to `false` on a model to keep that tab visible but non-interactive.

```kotlin
val tabs = listOf(
    ElegantTab("General"),
    ElegantTab("Security", enabled = false),
    ElegantTab("Billing"),
)
```

## Component States

The row and every tab share the disabled and selected semantics: each tab announces `Role.Tab` with its `selected` and `disabled` state, and the whole row is a single keyboard-focusable node whose arrow keys move the selection to the next enabled tab, wrapping around both ends. `selectedIndex` outside the tab range is clamped to the last tab, and an empty tab list renders nothing.

State precedence for the label color: disabled, selected, hovered, resting. `hoveredContentColor` applies only when the tab is not selected.

```kotlin
var selected by remember { mutableStateOf(1) }

ElegantTabRow(
    tabs = listOf(
        ElegantTab("Overview"),
        ElegantTab("Archived"),
        ElegantTab("Shared", enabled = false),
    ),
    selectedIndex = selected,
    onSelect = { selected = it },
)

ElegantTabRow(
    tabs = listOf(ElegantTab("Offline")),
    selectedIndex = 0,
    onSelect = {},
    enabled = false,
)
```

## Properties

### ElegantTabRow Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `tabs` | `List<ElegantTab>` | Tab models rendered by the row | - | Yes |
| `selectedIndex` | `Int` | Index of the selected tab; out-of-range values clamp to the last tab | - | Yes |
| `onSelect` | `(Int) -> Unit` | Callback invoked with the newly selected index | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the row root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the row accepts user interaction | `true` | No |
| `scrollable` | `Boolean` | Whether tabs keep natural width and scroll horizontally | `false` | No |
| `colors` | `ElegantTabColors` | Theme-aware state colors | `ElegantTabDefaults.colors()` | No |
| `interactionSource` | `MutableInteractionSource?` | Hoisted source for observing the row's focus state | `null` | No |

### ElegantTab Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Label rendered with the labelMedium style | - | Yes |
| `enabled` | `Boolean` | Whether this tab accepts user interaction | `true` | No |

### ElegantTabDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive root height |
| `IndicatorHeight` | `Dp` | 2dp selection indicator height |
| `AnimationDurationMillis` | `Int` | Standard 160ms label-color transition duration |
| `colors()` | `ElegantTabColors` | Theme-aware Light/Dark colors |

### ElegantTabColors

`ElegantTabColors` holds the container, indicator, and label colors for the resting, selected, hovered, and disabled states. Start with `ElegantTabDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Tabs Above a Content Area

Keep the row controlled and switch the content below it from the same state.

```kotlin
var selected by remember { mutableStateOf(0) }
val tabs = listOf(ElegantTab("Design"), ElegantTab("Engineering"), ElegantTab("Release"))

Column {
    ElegantTabRow(
        tabs = tabs,
        selectedIndex = selected,
        onSelect = { selected = it },
    )
    Text(
        text = "Showing ${tabs[selected].text}",
        style = ElegantTheme.typography.bodyMedium,
        color = ElegantTheme.colors.textSecondary,
        modifier = Modifier.padding(ElegantSpacing.lg),
    )
}
```

### Custom Emphasis

```kotlin
val baseColors = ElegantTabDefaults.colors()

ElegantTabRow(
    tabs = listOf(ElegantTab("Overview"), ElegantTab("Details")),
    selectedIndex = selected,
    onSelect = { selected = it },
    colors = baseColors.copy(
        selectedContentColor = Color(0xFF147D64),
        indicatorColor = Color(0xFF147D64),
    ),
)
```

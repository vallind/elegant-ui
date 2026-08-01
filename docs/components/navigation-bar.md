# NavigationBar

`ElegantNavigationBar` is a controlled bottom navigation bar for switching between primary destinations. It renders `ElegantNavigationBarItem` models at equal width, marks the current destination with a pill indicator behind the label and a small active dot, and keeps the selection reachable on Android, Desktop, and Web.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=navigation-bar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.navigationbar.ElegantNavigationBar
import com.elegant.compose.ui.navigationbar.ElegantNavigationBarColors
import com.elegant.compose.ui.navigationbar.ElegantNavigationBarDefaults
import com.elegant.compose.ui.navigationbar.ElegantNavigationBarItem
```

## Basic Usage

`ElegantNavigationBar` is controlled: keep `selectedIndex` in state and update it from `onSelect`. By default every item takes an equal share of the bar width.

```kotlin
var selected by remember { mutableStateOf(0) }
val items = listOf(
    ElegantNavigationBarItem("Home"),
    ElegantNavigationBarItem("Library"),
    ElegantNavigationBarItem("Settings"),
)

ElegantNavigationBar(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = items,
)
```

## Item Model

`ElegantNavigationBarItem` is the stable data model that drives the bar. Set `enabled` to `false` on a model to keep that item visible but non-interactive.

```kotlin
val items = listOf(
    ElegantNavigationBarItem("Home"),
    ElegantNavigationBarItem("Archive", enabled = false),
    ElegantNavigationBarItem("Settings"),
)
```

## Component States

Each item announces `Role.Tab` with its `selected` and `disabled` state. `selectedIndex` outside the item range is clamped to the last item, and an empty item list renders nothing. The selected item shows a 32dp pill behind its label together with a small dot above it; hovering or pressing an unselected item shows a faint pill behind its label.

State precedence for the label color: disabled, selected, pressed, hovered, resting.

```kotlin
var selected by remember { mutableStateOf(1) }

ElegantNavigationBar(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = listOf(
        ElegantNavigationBarItem("Home"),
        ElegantNavigationBarItem("Archive"),
        ElegantNavigationBarItem("Shared", enabled = false),
    ),
)

ElegantNavigationBar(
    selectedIndex = 0,
    onSelect = {},
    items = listOf(ElegantNavigationBarItem("Offline")),
    enabled = false,
)
```

## Properties

### ElegantNavigationBar Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `items` | `List<ElegantNavigationBarItem>` | Item models rendered by the bar | - | Yes |
| `selectedIndex` | `Int` | Index of the selected item; out-of-range values clamp to the last item | - | Yes |
| `onSelect` | `(Int) -> Unit` | Callback invoked with the newly selected index | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the bar root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the bar accepts user interaction | `true` | No |
| `colors` | `ElegantNavigationBarColors` | Theme-aware state colors | `ElegantNavigationBarDefaults.colors()` | No |

### ElegantNavigationBarItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Label rendered with the labelMedium style | - | Yes |
| `enabled` | `Boolean` | Whether this item accepts user interaction | `true` | No |

### ElegantNavigationBarDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 56dp minimum interactive root height |
| `IndicatorSize` | `Dp` | 32dp selection pill height |
| `AnimationDurationMillis` | `Int` | Standard 160ms label-color transition duration |
| `colors()` | `ElegantNavigationBarColors` | Theme-aware Light/Dark colors |

### ElegantNavigationBarColors

`ElegantNavigationBarColors` holds the container, item, label, and indicator colors for the resting, selected, hovered, pressed, and disabled states. Start with `ElegantNavigationBarDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Bottom Bar in a Page Layout

Keep the bar controlled and pin it below the content with a spacer.

```kotlin
var selected by remember { mutableStateOf(0) }
val items = listOf(
    ElegantNavigationBarItem("Home"),
    ElegantNavigationBarItem("Search"),
    ElegantNavigationBarItem("Profile"),
)

Column(modifier = Modifier.fillMaxSize()) {
    Text(
        text = "Showing ${items[selected].text}",
        style = ElegantTheme.typography.bodyMedium,
        color = ElegantTheme.colors.textSecondary,
        modifier = Modifier.padding(ElegantSpacing.xl),
    )
    Spacer(modifier = Modifier.weight(1f))
    ElegantNavigationBar(
        selectedIndex = selected,
        onSelect = { selected = it },
        items = items,
    )
}
```

### Custom Emphasis

```kotlin
val baseColors = ElegantNavigationBarDefaults.colors()

ElegantNavigationBar(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = listOf(ElegantNavigationBarItem("Home"), ElegantNavigationBarItem("Profile")),
    colors = baseColors.copy(
        selectedContentColor = Color(0xFF147D64),
        indicatorColor = Color(0xFF147D64).copy(alpha = 0.12f),
    ),
)
```

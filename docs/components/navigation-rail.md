# Navigation Rail

`ElegantNavigationRail` is a refined compact navigation surface: a controlled vertical rail with a selected index, per-item enabled flags, and optional header and footer slots. Use it to anchor destination switching on desktop and tablet layouts, beside a sidebar or directly next to main content.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=navigation-rail" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.navigationrail.ElegantNavigationRail
import com.elegant.compose.ui.navigationrail.ElegantNavigationRailColors
import com.elegant.compose.ui.navigationrail.ElegantNavigationRailDefaults
import com.elegant.compose.ui.navigationrail.ElegantNavigationRailItem
```

## Basic Usage

`ElegantNavigationRail` is a controlled component: keep `selectedIndex` in a `remember`-backed state and write every choice back from `onSelect`. Pass the item list in display order; out-of-range indexes are coerced into the item range. The rail paints its container color across the full height and scrolls vertically once the items overflow.

```kotlin
var selected by remember { mutableStateOf(0) }
val items = listOf(
    ElegantNavigationRailItem("Home"),
    ElegantNavigationRailItem("Search"),
    ElegantNavigationRailItem("Profile"),
)

ElegantNavigationRail(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = items,
)
```

## Item Model

`ElegantNavigationRailItem` is the navigation model rendered by the rail. `text` is what is displayed; setting `enabled = false` renders the item disabled so it keeps its resting look and can never be invoked.

```kotlin
ElegantNavigationRailItem(
    text = "Legacy settings",
    enabled = false,
)
```

## Component States

Items follow the visual precedence: disabled, selected, hovered or pressed, resting. The selected item paints `indicatorColor` behind a `selectedContentColor` label; hovering or pressing an unselected item paints `hoveredContainerColor` with `hoveredItemColor`, and hovering or pressing the selected item paints `selectedItemColor`. Disabled items keep the resting indicator and show `disabledItemColor`.

Each interactive item exposes `Role.Tab` with `selected` and `disabled` semantics, and every hit target is at least 48dp tall. The whole rail disables through `enabled`, and `ElegantNavigationRailDefaults.colors()` maps the state colors to the active Light or Dark theme.

```kotlin
ElegantNavigationRail(
    selectedIndex = 1,
    onSelect = { index -> selected = index },
    items = listOf(
        ElegantNavigationRailItem("Inbox"),
        ElegantNavigationRailItem("Archived"),
        ElegantNavigationRailItem("Trash", enabled = false),
    ),
)

ElegantNavigationRail(
    selectedIndex = selected,
    onSelect = { selected = it },
    enabled = false,
    items = listOf(
        ElegantNavigationRailItem("Read-only A"),
        ElegantNavigationRailItem("Read-only B"),
    ),
)
```

## Properties

### ElegantNavigationRail Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `selectedIndex` | `Int` | Currently selected item index, owned by the caller and coerced into the item range | - | Yes |
| `onSelect` | `(Int) -> Unit` | Callback invoked with the chosen index | - | Yes |
| `items` | `List<ElegantNavigationRailItem>` | Navigation entries rendered in order | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the rail root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the rail accepts selection and items can be invoked | `true` | No |
| `colors` | `ElegantNavigationRailColors` | Theme-aware state colors | `ElegantNavigationRailDefaults.colors()` | No |
| `header` | `(@Composable () -> Unit)?` | Optional header content shown above the items | `null` | No |
| `footer` | `(@Composable () -> Unit)?` | Optional footer content shown below the items | `null` | No |

### ElegantNavigationRailItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Text rendered as the item label | - | Yes |
| `enabled` | `Boolean` | Whether the item can be invoked | `true` | No |

### ElegantNavigationRailDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Width` | `Dp` | 80dp default rail width |
| `MinimumTouchHeight` | `Dp` | 48dp minimum hit-target height per item |
| `IndicatorSize` | `Dp` | 48dp diameter of the rounded indicator |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantNavigationRailColors` | Theme-aware Light/Dark state colors |

### ElegantNavigationRailColors

`ElegantNavigationRailColors` contains the container color, the selected indicator and content colors, and the resting, hovered, and disabled item overrides. Start with `ElegantNavigationRailDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Rail with Header and Footer

Compose an `ElegantAvatar` into the header slot and keep a short label in the footer slot. Both slots are unstyled areas padded with 8dp vertically and centered within the rail width.

```kotlin
ElegantNavigationRail(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = listOf(
        ElegantNavigationRailItem("Home"),
        ElegantNavigationRailItem("Search"),
        ElegantNavigationRailItem("Notifications"),
        ElegantNavigationRailItem("Profile"),
    ),
    header = {
        ElegantAvatar(name = "Maya Chen", initials = "MC")
    },
    footer = {
        Text("Settings")
    },
)
```

### Rail Next to Content

The rail fills the height of its parent, so it anchors a layout row while content flows beside it.

```kotlin
Row {
    ElegantNavigationRail(
        selectedIndex = selected,
        onSelect = { selected = it },
        items = listOf(
            ElegantNavigationRailItem("Dashboard"),
            ElegantNavigationRailItem("Projects"),
            ElegantNavigationRailItem("Team"),
        ),
        header = {
            ElegantAvatar(name = "Acme Corp", initials = "AC")
        },
    )
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(ElegantSpacing.xl),
    ) {
        Text("Dashboard", style = ElegantTheme.typography.titleMedium)
        Text("Welcome back, Maya.", style = ElegantTheme.typography.bodyMedium)
    }
}
```

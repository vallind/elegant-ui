# Sidebar

`ElegantSidebar` is a refined vertical navigation surface: a controlled item list with a selected index, per-item enabled flags, and optional header and footer slots. Use it for app navigation rails, settings panels, and any compact list of destinations that anchors a layout next to main content.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=sidebar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.sidebar.ElegantSidebar
import com.elegant.compose.ui.sidebar.ElegantSidebarColors
import com.elegant.compose.ui.sidebar.ElegantSidebarDefaults
import com.elegant.compose.ui.sidebar.ElegantSidebarItem
```

## Basic Usage

`ElegantSidebar` is a controlled component: keep `selectedIndex` in a `remember`-backed state and write every choice back from `onSelect`. Pass the item list in display order; a null `selectedIndex` renders every item unselected. The sidebar paints its container color across the full height and scrolls vertically once the items overflow.

```kotlin
var selected by remember { mutableStateOf(0) }
val items = listOf(
    ElegantSidebarItem("Overview"),
    ElegantSidebarItem("Analytics"),
    ElegantSidebarItem("Reports"),
)

ElegantSidebar(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = items,
    header = {
        Text("Workspace")
    },
)
```

## Item Model

`ElegantSidebarItem` is the navigation model rendered by the sidebar. `text` is what is displayed; setting `enabled = false` renders the item disabled so it keeps its resting look and can never be selected or invoked.

```kotlin
ElegantSidebarItem(
    text = "Legacy settings",
    enabled = false,
)
```

## Component States

Items follow the visual precedence: disabled, selected, hovered, resting. The selected item paints `selectedItemContainerColor` behind a `selectedItemContentColor` label; hovering or pressing an item paints `hoveredItemContainerColor` with `hoveredItemContentColor` unless the item is selected. Disabled items keep the resting container and show `disabledItemContentColor`.

Each interactive item exposes `Role.Tab` with `selected` and `disabled` semantics. When `onSelect` is null the items become plain text: no Tab role, no hover, press, or focus feedback. The whole sidebar disables through `enabled`, and `ElegantSidebarDefaults.colors()` maps the state colors to the active Light or Dark theme.

```kotlin
ElegantSidebar(
    selectedIndex = 1,
    onSelect = { index -> selected = index },
    items = listOf(
        ElegantSidebarItem("Inbox"),
        ElegantSidebarItem("Archived"),
        ElegantSidebarItem("Trash", enabled = false),
    ),
)

ElegantSidebar(
    selectedIndex = null,
    onSelect = null,
    items = listOf(
        ElegantSidebarItem("Read-only A"),
        ElegantSidebarItem("Read-only B"),
    ),
)
```

## Properties

### ElegantSidebar Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `selectedIndex` | `Int?` | Currently selected item index, owned by the caller; null selects nothing | - | Yes |
| `onSelect` | `((Int) -> Unit)?` | Callback invoked with the chosen index; null renders items as plain text | `null` | No |
| `items` | `List<ElegantSidebarItem>` | Navigation entries rendered in order | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the sidebar root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the sidebar accepts selection and items can be invoked | `true` | No |
| `width` | `Dp` | Sidebar width | `ElegantSidebarDefaults.Width` | No |
| `colors` | `ElegantSidebarColors` | Theme-aware state colors | `ElegantSidebarDefaults.colors()` | No |
| `header` | `(@Composable () -> Unit)?` | Optional header content shown above the items | `null` | No |
| `footer` | `(@Composable () -> Unit)?` | Optional footer content shown below the items | `null` | No |

### ElegantSidebarItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Text rendered in the sidebar item | - | Yes |
| `enabled` | `Boolean` | Whether the item can be selected or invoked | `true` | No |

### ElegantSidebarDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Width` | `Dp` | 240dp default sidebar width |
| `ItemHeight` | `Dp` | 40dp minimum height of every item |
| `ItemHorizontalPadding` | `Dp` | 12dp horizontal padding inside every item |
| `ItemGap` | `Dp` | 4dp vertical spacing between items |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantSidebarColors` | Theme-aware Light/Dark state colors |

### ElegantSidebarColors

`ElegantSidebarColors` contains the container and resting item colors plus selected, hovered, and disabled overrides. Start with `ElegantSidebarDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy. Header and footer content receives the item content color through `LocalContentColor`.

## Advanced Usage

### Sidebar with Header and Footer

Compose an `ElegantAvatar` and account text into the header slot, and keep a short label in the footer slot. Both slots are unstyled areas padded with 12dp horizontally and 8dp vertically.

```kotlin
ElegantSidebar(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = listOf(
        ElegantSidebarItem("Overview"),
        ElegantSidebarItem("Analytics"),
        ElegantSidebarItem("Reports"),
    ),
    header = {
        Row(
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ElegantAvatar(name = "Maya Chen", initials = "MC")
            Column {
                Text("Maya Chen")
                Text("Design systems")
            }
        }
    },
    footer = {
        Text("Settings")
    },
)
```

### Sidebar Next to Content

The sidebar fills the height of its parent, so it anchors a layout row while content flows beside it.

```kotlin
Row {
    ElegantSidebar(
        selectedIndex = selected,
        onSelect = { selected = it },
        items = listOf(
            ElegantSidebarItem("Dashboard"),
            ElegantSidebarItem("Projects"),
            ElegantSidebarItem("Team"),
        ),
        header = {
            Text("Acme Corp")
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

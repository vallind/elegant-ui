# Menu

`ElegantMenu` shows a list of actions on a temporary surface anchored below the Box that contains both the trigger and the menu: the caller owns the trigger, places `ElegantMenu` next to it in the same Box, and the surface drops below that Box, start-aligned and clamped into the window. Clicking outside, pressing Escape, or using the platform back gesture dismisses it. The focusable popup moves keyboard focus into the menu when it opens; items are 40dp rows with optional leading and trailing slots.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=menu" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.menu.ElegantMenu
import com.elegant.compose.ui.menu.ElegantMenuDefaults
import com.elegant.compose.ui.menu.ElegantMenuItem
```

## Basic Usage

Place `ElegantMenu` inside the same Box as the trigger. The menu anchors to that Box, so wrapping only the trigger keeps the dropdown exactly on the trigger; the caller toggles `expanded` and resets it in `onDismissRequest` and in every item's `onClick`.

```kotlin
var expanded by remember { mutableStateOf(false) }

Box {
    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "More options")
    }
    ElegantMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        ElegantMenuItem(
            text = "Edit",
            onClick = { expanded = false },
        )
        ElegantMenuItem(
            text = "Share",
            onClick = { expanded = false },
        )
    }
}
```

## Item Slots

`ElegantMenuItem` accepts a leading and a trailing slot rendered inside 20dp boxes with a 12dp gap, useful for icons, status dots, and shortcut hints. Slot content inherits the resolved item content color and disables along with the item.

```kotlin
ElegantMenuItem(
    text = "Move to trash",
    onClick = { /* handle */ },
    leadingContent = { Icon(Icons.Default.Delete, contentDescription = null) },
    trailingContent = { Text("Del") },
)
```

## Component States

The menu surface has no visual states of its own; interaction lives on the items. Hovered and pressed items show the hovered background, and disabled items never invoke callbacks. The focusable popup dismisses on outside click, Escape, or back, and keyboard focus moves into the menu when it opens and back to the trigger on dismissal; focused items activate with Enter or Space.

```kotlin
ElegantMenuItem(
    text = "Restore",
    onClick = { expanded = false },
    enabled = false,
)
```

## Properties

### ElegantMenu Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `expanded` | `Boolean` | Whether the menu surface is shown | - | Yes |
| `onDismissRequest` | `() -> Unit` | Called when the user requests dismissal | - | Yes |
| `modifier` | `Modifier` | Modifier applied to the scrollable item column | `Modifier` | No |
| `colors` | `ElegantMenuColors` | Menu surface colors | `ElegantMenuDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Menu items and custom rows | - | Yes |

### ElegantMenuItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Item label, truncated with an ellipsis | - | Yes |
| `onClick` | `() -> Unit` | Called when the enabled item is activated | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the item row | `Modifier` | No |
| `enabled` | `Boolean` | Whether the item accepts activation | `true` | No |
| `leadingContent` | `@Composable (() -> Unit)?` | Content before the label, such as an icon | `null` | No |
| `trailingContent` | `@Composable (() -> Unit)?` | Content after the label, such as a shortcut hint | `null` | No |
| `colors` | `ElegantMenuColors` | Menu colors resolving the item appearance | `ElegantMenuDefaults.colors()` | No |

### ElegantMenuColors

| Property Name | Type | Description |
| --- | --- | --- |
| `containerColor` | `Color` | Menu surface background |
| `contentColor` | `Color` | Text and icon color inside the menu |
| `disabledContentColor` | `Color` | Text and icon color of disabled items |
| `dividerColor` | `Color` | Recommended divider color for separators between items |
| `selectedItemColor` | `Color` | Background of a semantically selected item |
| `hoveredItemColor` | `Color` | Background of hovered or pressed items |

### ElegantMenuDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinWidth` | `Dp` | 160dp minimum menu width; the surface grows to fit the widest item |
| `MaxHeight` | `Dp` | 320dp maximum menu height before the item column scrolls |
| `ItemHeight` | `Dp` | 40dp height of one menu item row |
| `HorizontalPadding` | `Dp` | 16dp horizontal padding inside every item |
| `AnimationDurationMillis` | `Int` | 90ms menu entrance transition duration |

## Advanced Usage

### Menu Sections

Group related items and separate them with `ElegantDivider`; the divider color role in `ElegantMenuColors` matches the default divider theme so grouped menus stay coherent.

```kotlin
ElegantMenuItem(
    text = "Profile",
    onClick = { expanded = false },
    leadingContent = { Icon(Icons.Default.Person, contentDescription = null) },
)
ElegantMenuItem(
    text = "Settings",
    onClick = { expanded = false },
    leadingContent = { Icon(Icons.Default.Settings, contentDescription = null) },
)
ElegantDivider(modifier = Modifier.padding(vertical = ElegantSpacing.xs))
ElegantMenuItem(
    text = "Sign out",
    onClick = { expanded = false },
)
```

### Scrolling Menus

The item column scrolls once it exceeds `ElegantMenuDefaults.MaxHeight`; add a trailing slot to hint at more actions beyond the fold.

```kotlin
ElegantMenu(
    expanded = expanded,
    onDismissRequest = { expanded = false },
    modifier = Modifier.heightIn(max = 240.dp),
) {
    repeat(10) { index ->
        ElegantMenuItem(
            text = "Item ${index + 1}",
            onClick = { expanded = false },
        )
    }
}
```

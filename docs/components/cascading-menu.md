# Cascading Menu

`ElegantCascadingMenu` shows multi-level actions on nested surfaces anchored below the Box that contains both the trigger and the menu: the caller owns the trigger, places `ElegantCascadingMenu` next to it in the same Box, and the root surface drops below that Box, start-aligned and clamped into the window. Items with children render a trailing chevron and open a child surface beside them; pointing at or clicking such an item replaces the open submenu chain, and clicking a leaf item reports its ancestor chain through `onItemClick` and dismisses the menu. Clicking outside, pressing Escape, or using the platform back gesture closes the whole chain through the focusable root popup.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=cascading-menu" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.cascadingmenu.ElegantCascadingMenu
import com.elegant.compose.ui.cascadingmenu.ElegantCascadingMenuDefaults
import com.elegant.compose.ui.cascadingmenu.ElegantCascadingMenuItem
```

## Basic Usage

Place `ElegantCascadingMenu` inside the same Box as the trigger, exactly like `ElegantMenu`. The caller toggles `expanded` and resets it in `onDismissRequest`; `onItemClick` receives the ancestor chain of the clicked leaf item, ending at the leaf, for example `[Edit, Copy]`.

```kotlin
var expanded by remember { mutableStateOf(false) }

val items = listOf(
    ElegantCascadingMenuItem(
        text = "Edit",
        children = listOf(
            ElegantCascadingMenuItem(text = "Copy"),
            ElegantCascadingMenuItem(text = "Paste"),
        ),
    ),
    ElegantCascadingMenuItem(
        text = "Insert",
        children = listOf(
            ElegantCascadingMenuItem(text = "Image"),
            ElegantCascadingMenuItem(text = "Table"),
        ),
    ),
)

Box {
    ElegantButton(onClick = { expanded = true }) {
        Text("Edit document")
    }
    ElegantCascadingMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        items = items,
        onItemClick = { path ->
            expanded = false
            // path is [Edit, Copy], [Edit, Paste], [Insert, Image], ...
        },
    )
}
```

## Nested Items

`ElegantCascadingMenuItem` is a recursive model: `children` holds the submenu of a parent item, and leaf items keep it empty. A disabled parent still shows its submenu on hover, but disabled items never invoke callbacks.

```kotlin
ElegantCascadingMenuItem(
    text = "Style",
    children = listOf(
        ElegantCascadingMenuItem(text = "Bold"),
        ElegantCascadingMenuItem(
            text = "Align",
            children = listOf(
                ElegantCascadingMenuItem(text = "Left"),
                ElegantCascadingMenuItem(text = "Center"),
                ElegantCascadingMenuItem(text = "Right"),
            ),
        ),
        ElegantCascadingMenuItem(text = "Strikethrough", enabled = false),
    ),
)
```

## Component States

Interaction lives on the items at every level: hovered items show the hovered background, disabled items never invoke callbacks, and hovering or clicking a different parent replaces the open submenu chain. The root popup is focusable and dismisses on outside click, Escape, or back, closing the whole chain; submenu surfaces are not focusable, share the root's dismissal, and keyboard focus moves into the root menu when it opens and returns to the trigger on dismissal.

```kotlin
ElegantCascadingMenuItem(
    text = "Restore",
    enabled = false,
)
```

## Properties

### ElegantCascadingMenu Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `expanded` | `Boolean` | Whether the menu chain is shown | - | Yes |
| `onDismissRequest` | `() -> Unit` | Called when the user requests dismissal | - | Yes |
| `items` | `List<ElegantCascadingMenuItem>` | Recursive menu tree shown in the menu | - | Yes |
| `onItemClick` | `(List<ElegantCascadingMenuItem>) -> Unit` | Called with the ancestor chain ending at the clicked leaf item | - | Yes |
| `modifier` | `Modifier` | Modifier applied to the scrollable item column of the root surface | `Modifier` | No |
| `colors` | `ElegantCascadingMenuColors` | Menu surface colors for every level | `ElegantCascadingMenuDefaults.colors()` | No |

### ElegantCascadingMenuItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Item label, truncated with an ellipsis | - | Yes |
| `enabled` | `Boolean` | Whether the item accepts activation | `true` | No |
| `children` | `List<ElegantCascadingMenuItem>` | Child items shown in the submenu; empty for leaf items | `emptyList()` | No |

### ElegantCascadingMenuColors

| Property Name | Type | Description |
| --- | --- | --- |
| `containerColor` | `Color` | Menu surface background at every level |
| `contentColor` | `Color` | Text and chevron color inside the menu |
| `disabledContentColor` | `Color` | Text and chevron color of disabled items |
| `hoveredContainerColor` | `Color` | Background of hovered items |
| `borderColor` | `Color` | Recommended border or separator color for surfaces placed next to the menu |

### ElegantCascadingMenuDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinWidth` | `Dp` | 180dp minimum menu width; the surface grows to fit the widest item |
| `MaxHeight` | `Dp` | 320dp maximum menu height before the item column scrolls |
| `ItemHeight` | `Dp` | 40dp height of one item row at every menu level |
| `HorizontalPadding` | `Dp` | 16dp horizontal padding inside every item row |
| `SubmenuOffset` | `Dp` | 4dp gap between a parent item's end edge and its submenu surface |
| `AnimationDurationMillis` | `Int` | 90ms root menu entrance transition duration |

## Advanced Usage

### Menus from Data

Build the item tree from a data model when the menu must stay in sync with changing content; the item chain reported by `onItemClick` maps directly back to the source objects.

```kotlin
data class Action(val title: String, val subActions: List<Action> = emptyList())

val actions: List<Action> = /* ... */

val items = actions.map { action ->
    ElegantCascadingMenuItem(
        text = action.title,
        children = action.subActions.map { subAction ->
            ElegantCascadingMenuItem(text = subAction.title)
        },
    )
}

ElegantCascadingMenu(
    expanded = expanded,
    onDismissRequest = { expanded = false },
    items = items,
    onItemClick = { path ->
        expanded = false
        val clicked = path.last().text
    },
)
```

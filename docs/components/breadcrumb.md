# Breadcrumb

`ElegantBreadcrumb` is a refined navigation aid that shows the current page's position within a hierarchy. Entries before the current page act as links with a 48dp touch target, hover feedback, and disabled support; the trailing entry is the current page and stays non-interactive plain text. Logical chevron separators mirror automatically in RTL and remain decorative for assistive technology.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=breadcrumb" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.breadcrumb.ElegantBreadcrumb
import com.elegant.compose.ui.breadcrumb.ElegantBreadcrumbColors
import com.elegant.compose.ui.breadcrumb.ElegantBreadcrumbDefaults
import com.elegant.compose.ui.breadcrumb.ElegantBreadcrumbItem
```

## Basic Usage

Pass the hierarchy as items in logical order; the last item is always treated as the current page. `onItemClick` receives the clicked entry's index so the caller can map it back to its navigation destination.

```kotlin
ElegantBreadcrumb(
    items = listOf(
        ElegantBreadcrumbItem("Home"),
        ElegantBreadcrumbItem("Library"),
        ElegantBreadcrumbItem("Compose"),
    ),
    onItemClick = { index -> openSection(index) },
)
```

## Item Model

`ElegantBreadcrumbItem` describes one entry of the hierarchy. `text` is the visible label; `enabled = false` renders the entry disabled so it can never be activated.

```kotlin
ElegantBreadcrumbItem(
    text = "Archived",
    enabled = false,
)
```

## Component States

The trailing entry is always the current page: it is not clickable, exposes no button role, and renders in `currentColor`. Every earlier entry is interactive when both its `enabled` and `onItemClick` are provided: it announces a `Button` role through merged semantics, honors the 48dp minimum touch height, and paints `hoveredItemColor` on pointer hover. Disabled entries paint `disabledItemColor` and never invoke the callback.

When `onItemClick` is null every entry renders as plain non-interactive text in `itemColor`, so the breadcrumb can act as a pure display of the current location.

```kotlin
ElegantBreadcrumb(
    items = listOf(
        ElegantBreadcrumbItem("Home"),
        ElegantBreadcrumbItem("Drafts", enabled = false),
        ElegantBreadcrumbItem("Current draft"),
    ),
    onItemClick = { index -> openSection(index) },
)

ElegantBreadcrumb(
    items = listOf(
        ElegantBreadcrumbItem("Home"),
        ElegantBreadcrumbItem("Settings"),
    ),
)
```

Separators are decorative and omitted from the semantics tree. The row measures its natural width, so wrap the breadcrumb in a horizontally scrolling container when the hierarchy is long.

## Properties

### ElegantBreadcrumb Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `items` | `List<ElegantBreadcrumbItem>` | Hierarchy entries in logical order; the trailing entry is the current page | - | Yes |
| `onItemClick` | `((Int) -> Unit)?` | Callback invoked with the clicked entry's index; null makes every entry non-interactive | `null` | No |
| `modifier` | `Modifier` | Modifier applied once to the breadcrumb root | `Modifier` | No |
| `colors` | `ElegantBreadcrumbColors` | Theme-aware item, current, separator, hovered, and disabled colors | `ElegantBreadcrumbDefaults.colors()` | No |

### ElegantBreadcrumbItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Visible breadcrumb label | - | Yes |
| `enabled` | `Boolean` | Whether the entry can be activated | `true` | No |

### ElegantBreadcrumbDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum touch height for interactive entries |
| `ItemGap` | `Dp` | Default 4dp spacing between entries and separators |
| `colors()` | `ElegantBreadcrumbColors` | Returns Light/Dark theme-aware breadcrumb colors |

### ElegantBreadcrumbColors

`ElegantBreadcrumbColors` contains `itemColor`, `currentColor`, `separatorColor`, `hoveredItemColor` (defaults to `itemColor`), and `disabledItemColor` (defaults to `itemColor`). Start from `ElegantBreadcrumbDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Breadcrumb Above a Document Title

Place the breadcrumb above page content so the current entry reads as the document's title.

```kotlin
Column {
    ElegantBreadcrumb(
        items = listOf(
            ElegantBreadcrumbItem("Home"),
            ElegantBreadcrumbItem("Guides"),
            ElegantBreadcrumbItem("Breadcrumb"),
        ),
        onItemClick = { index -> openGuide(index) },
    )

    Text(
        text = "Breadcrumb",
        style = ElegantTheme.typography.titleMedium,
    )
}
```

### Custom Colors

```kotlin
val baseColors = ElegantBreadcrumbDefaults.colors()

ElegantBreadcrumb(
    items = listOf(
        ElegantBreadcrumbItem("Home"),
        ElegantBreadcrumbItem("Settings"),
    ),
    onItemClick = { index -> openSection(index) },
    colors = baseColors.copy(
        itemColor = Color(0xFF6C4EFF),
        hoveredItemColor = Color(0xFF5840D6),
    ),
)
```

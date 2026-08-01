# Pagination

`ElegantPagination` is a compact cross-platform pagination control in Elegant UI. It renders a bounded row of page items with previous and next chevron buttons, always keeps the first and last page visible, collapses distant pages into ellipses, and exposes the current page as a controlled `page` state through `onPageChange`.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=pagination" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.pagination.ElegantPagination
import com.elegant.compose.ui.pagination.ElegantPaginationColors
import com.elegant.compose.ui.pagination.ElegantPaginationDefaults
```

## Basic Usage

`ElegantPagination` is a controlled component: pass the current `page` and update it from `onPageChange`.

```kotlin
var page by remember { mutableStateOf(1) }

ElegantPagination(
    page = page,
    onPageChange = { page = it },
    pageCount = 10,
)
```

## Page Items

The row always shows the first and last page plus the pages around the current page, collapsing each collapsed gap into a single ellipsis. The current page is highlighted and does not invoke `onPageChange`; the previous and next chevron buttons are disabled at the first and last page. Use `siblingCount` to widen the window of visible pages.

```kotlin
var page by remember { mutableStateOf(1) }

ElegantPagination(
    page = page,
    onPageChange = { page = it },
    pageCount = 20,
    siblingCount = 1,
)
```

## Component States

Hover, press, and keyboard focus feedback are resolved automatically per item. Disabled items use the tertiary text color, keep their layout, and never invoke `onPageChange`.

### Disabled State

```kotlin
ElegantPagination(
    page = 1,
    onPageChange = { /* Handle page change event */ },
    pageCount = 10,
    enabled = false,
)
```

## Properties

### ElegantPagination Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `page` | `Int` | Current page number shown as selected | - | Yes |
| `onPageChange` | `(Int) -> Unit` | Callback invoked with the target page when an item is activated | - | Yes |
| `pageCount` | `Int` | Total number of pages; non-positive values render nothing | - | Yes |
| `modifier` | `Modifier` | Modifier applied to the pagination row | `Modifier` | No |
| `enabled` | `Boolean` | Whether the pagination accepts user interaction | `true` | No |
| `siblingCount` | `Int` | Number of pages shown on each side of the current page | `ElegantPaginationDefaults.SiblingCount` | No |
| `colors` | `ElegantPaginationColors` | Theme-aware colors for the row, items, and the selected page | `ElegantPaginationDefaults.colors()` | No |

### ElegantPaginationDefaults

| Member | Type | Description |
| --- | --- | --- |
| `ItemSize` | `Dp` | Edge length of every square page item and circular chevron button |
| `ItemGap` | `Dp` | Spacing between adjacent items |
| `AnimationDurationMillis` | `Int` | Standard state-transition duration |
| `SiblingCount` | `Int` | Default number of pages shown on each side of the current page |
| `colors()` | `ElegantPaginationColors` | Returns theme-aware pagination colors |

### ElegantPaginationColors

`ElegantPaginationColors` contains the row container color, the selected page container and content colors, the resting item content color, and the hovered, pressed, and disabled item colors. Start with `ElegantPaginationDefaults.colors()` and use `copy(...)` to override only product-supported values.

## Advanced Usage

### More Sibling Pages

```kotlin
var page by remember { mutableStateOf(5) }

ElegantPagination(
    page = page,
    onPageChange = { page = it },
    pageCount = 10,
    siblingCount = 2,
)
```

### Custom Colors

```kotlin
val baseColors = ElegantPaginationDefaults.colors()

ElegantPagination(
    page = 1,
    onPageChange = { /* Handle page change event */ },
    pageCount = 10,
    colors = baseColors.copy(
        selectedItemColor = Color(0xFF0F766E),
        hoveredItemColor = Color(0xFFCCFBF1),
    ),
)
```

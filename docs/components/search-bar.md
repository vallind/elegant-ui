# Search Bar

`ElegantSearchBar` is a pill-shaped search field for filtering and look-up: it owns a drawn magnifier glyph, an optional clear affordance, and an IME search action, and can sit above any content list. Use it when a full `ElegantInput` with label and supporting text would be more machinery than the task needs.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=search-bar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.searchbar.ElegantSearchBar
import com.elegant.compose.ui.searchbar.ElegantSearchBarColors
import com.elegant.compose.ui.searchbar.ElegantSearchBarDefaults
```

## Basic Usage

`ElegantSearchBar` is a controlled component: keep `query` in a `remember`-backed state and write every accepted change back from `onQueryChange`. The leading magnifier glyph is drawn and owned by the component, so there is no icon parameter to configure.

```kotlin
var query by remember { mutableStateOf("") }

ElegantSearchBar(
    query = query,
    onQueryChange = { query = it },
    placeholder = "Search components",
)
```

## Component States

`ElegantSearchBar` follows the interaction precedence: disabled, focused, hovered, resting. Focus outlines the pill with the `focusRing` color when the theme enables focus rings; hovering tints the container without changing the geometry. When `enabled` is false the field rejects focus and input and renders the sunken container with a transparent border.

While the query is not empty, a clear button with a drawn X appears at the trailing edge; activating it calls `onClear` or, by default, clears the query. The placeholder is shown only while the field is enabled and empty.

```kotlin
ElegantSearchBar(
    query = "Components",
    onQueryChange = {},
    placeholder = "Search components",
)

ElegantSearchBar(
    query = "Components",
    onQueryChange = {},
    enabled = false,
)

ElegantSearchBar(
    query = query,
    onQueryChange = { query = it },
    placeholder = "Search components",
    onClear = { query = "" },
)
```

## Properties

### ElegantSearchBar Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `query` | `String` | Current search query, owned by the caller | - | Yes |
| `onQueryChange` | `(String) -> Unit` | Callback invoked with the newest accepted query | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the search field root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the field accepts focus, input, and the clear action | `true` | No |
| `placeholder` | `String?` | Hint shown inside the field while it is enabled and empty | `null` | No |
| `onSearch` | `(() -> Unit)?` | Callback invoked by the IME search action and the Enter key | `null` | No |
| `onClear` | `(() -> Unit)?` | Callback invoked by the clear button; null clears the query | `null` | No |
| `colors` | `ElegantSearchBarColors` | Theme-aware state colors | `ElegantSearchBarDefaults.colors()` | No |
| `trailingContent` | `@Composable (() -> Unit)?` | Content after the input area | `null` | No |

### ElegantSearchBarDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum field height |
| `colors()` | `ElegantSearchBarColors` | Theme-aware Light/Dark state colors |
| `shape()` | `Shape` | Fully rounded pill shape |

### ElegantSearchBarColors

`ElegantSearchBarColors` contains the container, border, and content colors for the resting, hovered, focused, and disabled states, plus the placeholder color. Start with `ElegantSearchBarDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Search Action and Enter Key

Pass `onSearch` to announce and trigger a search: the IME shows a search action button, and pressing Enter on a desktop keyboard fires the same callback.

```kotlin
var query by remember { mutableStateOf("") }
var submitted by remember { mutableStateOf("") }

ElegantSearchBar(
    query = query,
    onQueryChange = { query = it },
    placeholder = "Search the catalog",
    onSearch = { submitted = query },
)
```

### Filtering a List

Combine the search bar with `ElegantList` to filter items by query.

```kotlin
val allItems = listOf("Badge", "Divider", "Input", "Tag")
var query by remember { mutableStateOf("") }
val visibleItems = allItems.filter { it.contains(query, ignoreCase = true) }

ElegantSearchBar(
    query = query,
    onQueryChange = { query = it },
    placeholder = "Filter components",
)

ElegantList {
    visibleItems.forEach { item ->
        ElegantListItem(
            title = { Text(item) },
        )
    }
}
```

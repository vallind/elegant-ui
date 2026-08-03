# Autocomplete

`ElegantAutocomplete` is a controlled text field with an inline suggestion list: while the field is focused, options matching the query appear on a floating surface anchored below the field, and choosing one invokes the selection callback. Use it for free-text entry where the caller owns the query and needs structured choices, such as country or product pickers.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=autocomplete" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.autocomplete.ElegantAutocomplete
import com.elegant.compose.ui.autocomplete.ElegantAutocompleteColors
import com.elegant.compose.ui.autocomplete.ElegantAutocompleteDefaults
import com.elegant.compose.ui.autocomplete.ElegantAutocompleteOption
```

## Basic Usage

`ElegantAutocomplete` is a controlled component: keep `query` in a `remember`-backed state and write every accepted change back from `onQueryChange`. The full `options` list is filtered against the query — a blank query shows every option, otherwise options whose text contains the query are shown, case-insensitively, in their original order. Selecting a suggestion calls `onOptionSelected` and closes the list; the caller then decides how to update the query.

```kotlin
var query by remember { mutableStateOf("") }

ElegantAutocomplete(
    query = query,
    onQueryChange = { query = it },
    options = listOf(
        ElegantAutocompleteOption(text = "France", value = "FR"),
        ElegantAutocompleteOption(text = "Germany", value = "DE"),
    ),
    onOptionSelected = { option ->
        query = option.text
    },
    label = "Country",
    placeholder = "Search a country",
)
```

## Options

`ElegantAutocompleteOption` models one suggestion: `text` is rendered in the suggestion row, `value` is the stable identity submitted with the selection, and `enabled` marks options that cannot be chosen. Disabled options render with the tertiary text color, ignore clicks, and announce the disabled state through semantics.

```kotlin
val countries = listOf(
    ElegantAutocompleteOption(text = "France", value = "FR"),
    ElegantAutocompleteOption(text = "Germany", value = "DE"),
    ElegantAutocompleteOption(text = "Fiji", value = "FJ", enabled = false),
)
```

## Component States

The suggestion list appears while the field is focused and at least one option matches the query, and closes when a suggestion is selected, the user clicks outside or presses Escape, or the field loses focus. The list is anchored below the field, clamped to the window, and scrolls once it exceeds `ElegantAutocompleteDefaults.MenuMaxHeight`.

`isError` paints the field border with `statusCritical` and replaces the supporting text with the error text below the field; the error text is announced through semantics. When `enabled` is false the field rejects focus and input and the list never opens.

```kotlin
ElegantAutocomplete(
    query = "F",
    onQueryChange = {},
    options = countries,
    onOptionSelected = {},
    label = "Country",
    isError = true,
    errorText = "Choose a country from the list.",
)

ElegantAutocomplete(
    query = "France",
    onQueryChange = {},
    options = countries,
    onOptionSelected = {},
    label = "Country",
    enabled = false,
)
```

## Properties

### ElegantAutocomplete Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `query` | `String` | Current query text, owned by the caller | - | Yes |
| `onQueryChange` | `(String) -> Unit` | Callback invoked with the newest accepted query | - | Yes |
| `options` | `List<ElegantAutocompleteOption>` | Full option list the suggestions are filtered from | - | Yes |
| `onOptionSelected` | `(ElegantAutocompleteOption) -> Unit` | Callback invoked when a suggestion is chosen | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the component root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the field accepts focus and input | `true` | No |
| `label` | `String?` | Label shown above the field | `null` | No |
| `placeholder` | `String?` | Hint shown inside the field while it is enabled and empty | `null` | No |
| `isError` | `Boolean` | Whether the field communicates an error state | `false` | No |
| `errorText` | `String?` | Error message shown below the field and announced when `isError` | `null` | No |
| `supportingText` | `String?` | Guidance shown below the field unless error text is shown | `null` | No |
| `colors` | `ElegantAutocompleteColors` | Theme-aware state colors | `ElegantAutocompleteDefaults.colors()` | No |

### ElegantAutocompleteOption

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Text rendered in the suggestion row | - | Yes |
| `value` | `String` | Stable identity submitted with the selection | - | Yes |
| `enabled` | `Boolean` | Whether the option can be chosen | `true` | No |

### ElegantAutocompleteDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum field-container height |
| `MenuMaxHeight` | `Dp` | 280dp maximum suggestion-list height before scrolling |
| `colors()` | `ElegantAutocompleteColors` | Theme-aware Light/Dark field and suggestion colors |

### ElegantAutocompleteColors

`ElegantAutocompleteColors` contains container, border, and content colors for the resting, hovered, focused, disabled, and error states, plus the placeholder, label, supporting-text, and error-text colors. Start with `ElegantAutocompleteDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Custom Error Styling

```kotlin
val baseColors = ElegantAutocompleteDefaults.colors()

ElegantAutocomplete(
    query = city,
    onQueryChange = { city = it },
    options = cities,
    onOptionSelected = { option -> city = option.text },
    label = "City",
    colors = baseColors.copy(
        errorBorderColor = Color(0xFFC63D52),
        errorTextColor = Color(0xFFC63D52),
    ),
    isError = true,
    errorText = "Choose a city from the list.",
)
```

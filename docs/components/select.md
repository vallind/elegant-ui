# Select

`ElegantSelect` is a refined single-choice field for forms and filters: a labeled, Filled-style trigger opens a themed menu of options with a check mark on the selected item, and the menu handles focus, keyboard traversal, escape, and outside-click dismissal. Use it whenever exactly one value must be picked from a predefined list where Radio rows would take too much space.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=select" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.select.ElegantSelect
import com.elegant.compose.ui.select.ElegantSelectDefaults
import com.elegant.compose.ui.select.ElegantSelectOption
```

## Basic Usage

`ElegantSelect` is a controlled component: keep `selectedOption` in a `remember`-backed state and write every choice back from `onOptionSelected`. Pass the full option list — entries with a blank text or value are ignored — and the trigger shows the selected option text, or the `placeholder` while nothing is selected.

```kotlin
var plan by remember { mutableStateOf<ElegantSelectOption?>(null) }
val plans = listOf(
    ElegantSelectOption("Starter", "starter"),
    ElegantSelectOption("Pro", "pro"),
    ElegantSelectOption("Enterprise", "enterprise"),
)

ElegantSelect(
    selectedOption = plan,
    onOptionSelected = { plan = it },
    options = plans,
    label = "Workspace plan",
    placeholder = "Choose a plan",
    supportingText = "Billed monthly, cancel anytime.",
)
```

## Option Model

`ElegantSelectOption` is the single model for both the selection and the menu items. `value` is the stable identity for storage, queries, or submission; `text` is what is displayed. Setting `enabled = false` renders the option disabled in the menu so it can never be chosen.

```kotlin
ElegantSelectOption(
    text = "Legacy account",
    value = "legacy",
    enabled = false,
)
```

## Component States

The trigger follows the interaction precedence: disabled, error border, focused border, resting. `isError` paints the border `statusCritical` at 2dp and replaces the supporting text with `errorText`, which is also announced through semantics. The trigger exposes a `DropdownList` role and announces its expanded state; disabled options stay visible in the menu but cannot be selected.

When `enabled` is false the trigger rejects clicks and focus, the menu never opens, and the placeholder is hidden like the Filled input.

```kotlin
ElegantSelect(
    selectedOption = region,
    onOptionSelected = { region = it },
    options = regions,
    label = "Region",
    isError = true,
    errorText = "This region is no longer available.",
)

ElegantSelect(
    selectedOption = ElegantSelectOption("Legacy", "legacy"),
    onOptionSelected = {},
    options = legacyOptions,
    label = "Account",
    enabled = false,
)
```

## Properties

### ElegantSelect Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `selectedOption` | `ElegantSelectOption?` | Currently chosen option, owned by the caller; matched by data equality | - | Yes |
| `onOptionSelected` | `(ElegantSelectOption) -> Unit` | Callback invoked with the option chosen from the menu | - | Yes |
| `options` | `List<ElegantSelectOption>` | Full option list; entries with a blank text or value are ignored | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the select root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the trigger accepts clicks and focus and the menu can open | `true` | No |
| `label` | `String?` | Label shown above the field | `null` | No |
| `placeholder` | `String?` | Hint shown inside the field while it is enabled and nothing is selected | `null` | No |
| `isError` | `Boolean` | Whether the field communicates an error state | `false` | No |
| `errorText` | `String?` | Error message shown below the field and announced when `isError` | `null` | No |
| `supportingText` | `String?` | Guidance shown below the field unless error text is shown | `null` | No |
| `interactionSource` | `MutableInteractionSource?` | Optional interaction source shared with the trigger | `null` | No |

### ElegantSelectOption Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Text rendered in the trigger and the menu item | - | Yes |
| `value` | `String` | Stable identity used to compare options and to store the choice | - | Yes |
| `enabled` | `Boolean` | Whether the option can be chosen from the menu | `true` | No |

### ElegantSelectDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum trigger height |
| `MenuMaxHeight` | `Dp` | 320dp menu height cap after which the option list scrolls |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |

## Advanced Usage

### Long List with Disabled Options

The menu scrolls once its options exceed `ElegantSelectDefaults.MenuMaxHeight`; disabled options stay visible but can never be selected.

```kotlin
val countries = remember {
    listOf(
        ElegantSelectOption("China", "cn"),
        ElegantSelectOption("Germany", "de"),
        ElegantSelectOption("Japan", "jp"),
        ElegantSelectOption("Norway", "no", enabled = false),
        ElegantSelectOption("United Kingdom", "gb"),
        ElegantSelectOption("United States", "us"),
    )
}

ElegantSelect(
    selectedOption = country,
    onOptionSelected = { country = it },
    options = countries,
    label = "Country",
    placeholder = "Pick a country",
)
```

### Select in a Form Row

The trigger fills the width of its parent, so it composes naturally next to an `ElegantInput` inside a form row.

```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
    ElegantInput(
        value = teamName,
        onValueChange = { teamName = it },
        modifier = Modifier.weight(1f),
        label = "Team name",
        placeholder = "e.g. Nova",
    )
    ElegantSelect(
        selectedOption = teamSize,
        onOptionSelected = { teamSize = it },
        options = sizes,
        modifier = Modifier.weight(1f),
        label = "Team size",
        placeholder = "Pick a size",
    )
}
```

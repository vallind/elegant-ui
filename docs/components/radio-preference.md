# RadioPreference

`ElegantRadioPreference` is a settings-style row that presents one selectable option. Unlike a bare `ElegantRadio`, the whole row is the interactive target: clicking the title, the supporting text, or the trailing indicator activates `onSelect`. It renders a `labelMedium` title with an optional `bodyMedium` supporting line, a trailing `ElegantRadio` indicator, hovered and pressed container feedback, and an optional bottom divider inset 16dp from the start edge.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=radio-preference" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.preference.ElegantRadioPreference
import com.elegant.compose.ui.preference.ElegantRadioPreferenceColors
import com.elegant.compose.ui.preference.ElegantRadioPreferenceDefaults
```

## Basic Usage

A radio preference needs `title`, `selected`, and `onSelect`. Share one selection state across a group so exactly one row is selected at a time; each row keeps a 48dp minimum interactive height.

```kotlin
ElegantRadioPreference(
    title = "Violet",
    selected = accent == "Violet",
    onSelect = { accent = "Violet" },
)

ElegantRadioPreference(
    title = "Indigo",
    selected = accent == "Indigo",
    onSelect = { accent = "Indigo" },
    supportingText = "A quieter blue",
    showDivider = false,
)
```

## Component States

A row announces `Role.RadioButton`, its `selected` state, and the disabled state. The whole row activates on click; the trailing indicator mirrors the same state and accepts its own pointer input.

State precedence: disabled, pressed, hovered, resting. The disabled row keeps the resting container and switches the title to the disabled color.

```kotlin
var delivery by remember { mutableStateOf("Standard") }

ElegantRadioPreference(
    title = "Standard",
    selected = delivery == "Standard",
    onSelect = { delivery = "Standard" },
    supportingText = "3 to 5 business days",
)

ElegantRadioPreference(
    title = "Express",
    selected = delivery == "Express",
    onSelect = { delivery = "Express" },
    supportingText = "1 to 2 business days",
)

ElegantRadioPreference(
    title = "Overnight",
    selected = false,
    onSelect = {},
    enabled = false,
    supportingText = "Currently unavailable",
)
```

## Properties

### ElegantRadioPreference Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `title` | `String` | Row title shown in `labelMedium` | - | Yes |
| `selected` | `Boolean` | Whether this row communicates the chosen option | - | Yes |
| `onSelect` | `() -> Unit` | Callback invoked when the row accepts a selection | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the interactive row | `Modifier` | No |
| `supportingText` | `String?` | Optional supporting text below the title | `null` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted | `true` | No |
| `colors` | `ElegantRadioPreferenceColors` | Theme-aware state colors | `ElegantRadioPreferenceDefaults.colors()` | No |
| `showDivider` | `Boolean` | Whether a bottom divider is drawn, inset 16dp | `true` | No |

### ElegantRadioPreferenceDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive row height |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantRadioPreferenceColors` | Theme-aware Light/Dark colors |

### ElegantRadioPreferenceColors

`ElegantRadioPreferenceColors` contains the resting container and title colors, the supporting-text and divider colors, the disabled title color, and the hovered and pressed container tints. Start with `ElegantRadioPreferenceDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Settings Group on a Card

Stack rows on a card surface and let the row divider draw the separators.

```kotlin
ElegantCard(
    modifier = Modifier.fillMaxWidth(),
) {
    var accent by remember { mutableStateOf("Violet") }

    Column {
        for (candidate in listOf("Violet", "Indigo", "Teal")) {
            ElegantRadioPreference(
                title = candidate,
                selected = accent == candidate,
                onSelect = { accent = candidate },
                showDivider = candidate != "Teal",
            )
        }
    }
}
```

### Custom Colors

```kotlin
val baseColors = ElegantRadioPreferenceDefaults.colors()

ElegantRadioPreference(
    title = "Compact",
    selected = selected,
    onSelect = onSelect,
    colors = baseColors.copy(
        titleColor = Color(0xFF147D64),
        supportingTextColor = Color(0xFF6E727A),
    ),
    supportingText = "Keeps the row height small",
)
```

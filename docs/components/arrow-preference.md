# ArrowPreference

`ElegantArrowPreference` is a settings-style row that navigates or drills into another screen. The whole row is the interactive target: clicking the title, the supporting text, or the trailing chevron activates `onClick`. It renders a `labelMedium` title with an optional `bodyMedium` supporting line, a trailing chevron that points in the logical layout direction, hovered and pressed container feedback, and an optional bottom divider inset 16dp from the start edge.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=arrow-preference" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.preference.ElegantArrowPreference
import com.elegant.compose.ui.preference.ElegantArrowPreferenceColors
import com.elegant.compose.ui.preference.ElegantArrowPreferenceDefaults
```

## Basic Usage

An arrow preference needs `title` and `onClick`. The row keeps a 48dp minimum interactive height; the trailing chevron mirrors horizontally when the layout is right-to-left.

```kotlin
ElegantArrowPreference(
    title = "Account",
    onClick = { openSettings("account") },
)

ElegantArrowPreference(
    title = "Notifications",
    onClick = { openSettings("notifications") },
    supportingText = "Manage alert preferences",
    showDivider = false,
)
```

## Component States

A row announces `Role.Button` and the disabled state. The whole row activates on click, including the title, the supporting text, and the trailing chevron.

State precedence: disabled, pressed, hovered, resting. The disabled row keeps the resting container and switches the title to the disabled color.

```kotlin
ElegantArrowPreference(
    title = "General",
    onClick = { openSettings("general") },
    supportingText = "Language, region, and appearance",
)

ElegantArrowPreference(
    title = "Appearance",
    onClick = { openSettings("appearance") },
    supportingText = "Theme, density, and font size",
)

ElegantArrowPreference(
    title = "Privacy",
    onClick = {},
    enabled = false,
    supportingText = "Locked by administrator",
)
```

## Properties

### ElegantArrowPreference Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `title` | `String` | Row title shown in `labelMedium` | - | Yes |
| `onClick` | `() -> Unit` | Callback invoked when the row accepts a click | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the interactive row | `Modifier` | No |
| `supportingText` | `String?` | Optional supporting text below the title | `null` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted | `true` | No |
| `colors` | `ElegantArrowPreferenceColors` | Theme-aware state colors | `ElegantArrowPreferenceDefaults.colors()` | No |
| `showDivider` | `Boolean` | Whether a bottom divider is drawn, inset 16dp | `true` | No |

### ElegantArrowPreferenceDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive row height |
| `colors()` | `ElegantArrowPreferenceColors` | Theme-aware Light/Dark colors |

### ElegantArrowPreferenceColors

`ElegantArrowPreferenceColors` contains the resting container and title colors, the supporting-text, divider, and arrow colors, the disabled title color, and the hovered and pressed container tints. Start with `ElegantArrowPreferenceDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Settings Group on a Card

Stack rows on a card surface and let the row divider draw the separators.

```kotlin
ElegantCard(
    modifier = Modifier.fillMaxWidth(),
) {
    Column {
        ElegantArrowPreference(
            title = "Profile",
            onClick = { openSettings("profile") },
            supportingText = "Name, avatar, and contact details",
        )
        ElegantArrowPreference(
            title = "Security",
            onClick = { openSettings("security") },
            supportingText = "Password and two-factor authentication",
        )
        ElegantArrowPreference(
            title = "About",
            onClick = { openSettings("about") },
            showDivider = false,
        )
    }
}
```

### Custom Colors

```kotlin
val baseColors = ElegantArrowPreferenceDefaults.colors()

ElegantArrowPreference(
    title = "Workspace",
    onClick = { openSettings("workspace") },
    colors = baseColors.copy(
        titleColor = Color(0xFF147D64),
        arrowColor = Color(0xFF6E727A),
    ),
    supportingText = "Members, plans, and billing",
)
```

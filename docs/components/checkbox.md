# Checkbox

`ElegantCheckbox` is a refined selection control with an animated checkmark, theme-aware state colors, and an optional inline label. Use it for multi-select lists and preference screens where a compact, always-visible selection state is required.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=checkbox" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.checkbox.ElegantCheckbox
import com.elegant.compose.ui.checkbox.ElegantCheckboxColors
import com.elegant.compose.ui.checkbox.ElegantCheckboxDefaults
```

## Basic Usage

A checkbox is fully controlled: pass the current `checked` value and an `onCheckedChange` callback that writes the requested state back. Add `label` to render an inline text label on the same 48dp interactive row.

```kotlin
var subscribed by remember { mutableStateOf(true) }

ElegantCheckbox(
    checked = subscribed,
    onCheckedChange = { subscribed = it },
    label = "Subscribe to release notes",
)
```

## Component States

A 20dp rounded box carries the checked and unchecked containers, while a 2dp border communicates hover and keyboard focus. `checked` is a semantic state that combines with interaction visuals: a pressed checked checkbox shows the pressed checked colors, and the focus ring, when enabled, wins over the hover border. Disabled checkboxes never invoke `onCheckedChange` and are announced through the `Role.Checkbox` semantics of the toggleable row.

State precedence: disabled, pressed, hovered, resting; the checked or unchecked container is then applied on top of the winning interaction state.

```kotlin
var options by remember { mutableStateOf(setOf("Stable")) }

ElegantCheckbox(
    checked = "Stable" in options,
    onCheckedChange = { checked ->
        options = if (checked) options + "Stable" else options - "Stable"
    },
    label = "Stable channel",
)

ElegantCheckbox(
    checked = false,
    onCheckedChange = {},
    enabled = false,
    label = "Disabled",
)
```

## Properties

### ElegantCheckbox Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `checked` | `Boolean` | Whether the checkbox is selected | - | Yes |
| `onCheckedChange` | `(Boolean) -> Unit` | Callback invoked with the requested selection state | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the checkbox row root | `Modifier` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted | `true` | No |
| `label` | `String?` | Optional inline text label rendered after the box | `null` | No |
| `colors` | `ElegantCheckboxColors` | Theme-aware state colors | `ElegantCheckboxDefaults.colors()` | No |
| `interactionSource` | `MutableInteractionSource?` | Optional hoisted interaction source for observing or controlling state | `null` | No |

### ElegantCheckboxDefaults

| Member | Type | Description |
| --- | --- | --- |
| `BoxSize` | `Dp` | 20dp visual box size |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive row height |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantCheckboxColors` | Theme-aware Light/Dark colors |

### ElegantCheckboxColors

`ElegantCheckboxColors` contains container, content, and border colors for the checked, unchecked, hovered, pressed, disabled, and focused states. Start with `ElegantCheckboxDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Controlled Preference Group

Hoist every checkbox to the screen state so dependent preferences can be gated behind their parent.

```kotlin
var emailUpdates by remember { mutableStateOf(true) }
var announcements by remember { mutableStateOf(false) }

ElegantCheckbox(
    checked = emailUpdates,
    onCheckedChange = { emailUpdates = it },
    label = "Email updates",
)

ElegantCheckbox(
    checked = announcements,
    onCheckedChange = { announcements = it },
    enabled = emailUpdates,
    label = "Announcements",
)
```

### Custom Colors

```kotlin
val baseColors = ElegantCheckboxDefaults.colors()

ElegantCheckbox(
    checked = checked,
    onCheckedChange = onCheckedChange,
    label = "Custom",
    colors = baseColors.copy(
        checkedContainerColor = Color(0xFF147D64),
        checkedContentColor = Color.White,
    ),
)
```

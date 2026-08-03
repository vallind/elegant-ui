# SwitchPreference

`ElegantSwitchPreference` is a settings row that pairs a start title block with an end-anchored `ElegantSwitch` control. Use it for settings screens where a single persistent on/off state is described by a title and optional supporting text.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=switch-preference" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.preference.ElegantPreferenceColors
import com.elegant.compose.ui.preference.ElegantPreferenceDefaults
import com.elegant.compose.ui.preference.ElegantSwitchPreference
```

## Basic Usage

The preference is fully controlled: pass the current `checked` value and an `onCheckedChange` callback that writes the requested state back. The 48dp row shows the title in `labelMedium` typography on the start side and anchors the switch at the end. Only the switch toggles; tapping the row itself never invokes the callback.

```kotlin
var notifications by remember { mutableStateOf(true) }

ElegantSwitchPreference(
    title = "Notifications",
    checked = notifications,
    onCheckedChange = { notifications = it },
    supportingText = "Receive push notifications",
)
```

## Component States

The row keeps a 48dp minimum height and fills the width of its container, with 16dp horizontal padding and a 2dp gap between the title and the supporting text. A non-blank `supportingText` renders below the title in `bodyMedium` typography; blank or null values collapse to a single title line. While `enabled` is false the title falls back to the disabled title color and the switch announces its disabled state through its own `Role.Switch` semantics without invoking `onCheckedChange`. When `showDivider` is set, a 1dp divider line inset by 16dp from the start edge closes the row.

```kotlin
var wifi by remember { mutableStateOf(true) }

ElegantSwitchPreference(
    title = "Wi-Fi",
    checked = wifi,
    onCheckedChange = { wifi = it },
    supportingText = "Join known networks automatically",
)

ElegantSwitchPreference(
    title = "Airplane mode",
    checked = false,
    onCheckedChange = {},
    enabled = false,
)
```

## Properties

### ElegantSwitchPreference Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `title` | `String` | Primary title text rendered in `labelMedium` typography | - | Yes |
| `checked` | `Boolean` | Whether the end switch is on | - | Yes |
| `onCheckedChange` | `(Boolean) -> Unit` | Callback invoked with the requested state | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the row root | `Modifier` | No |
| `supportingText` | `String?` | Optional secondary text rendered only when non-blank | `null` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted | `true` | No |
| `colors` | `ElegantPreferenceColors` | Theme-aware state colors | `ElegantPreferenceDefaults.colors()` | No |
| `showDivider` | `Boolean` | Whether a 1dp divider line closes the row bottom | `true` | No |

### ElegantPreferenceDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum row height |
| `colors()` | `ElegantPreferenceColors` | Theme-aware Light/Dark colors |

### ElegantPreferenceColors

`ElegantPreferenceColors` contains the row container, title, supporting text, disabled title, and divider colors. Start with `ElegantPreferenceDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Divider Control

`showDivider` draws the row-closing divider automatically; set it to `false` on the last row of a settings group so the group bottom stays clean.

```kotlin
ElegantSwitchPreference(
    title = "Dark mode",
    checked = darkMode,
    onCheckedChange = { darkMode = it },
    showDivider = false,
)
```

### Custom Colors

```kotlin
val baseColors = ElegantPreferenceDefaults.colors()

ElegantSwitchPreference(
    title = "Custom",
    checked = checked,
    onCheckedChange = onCheckedChange,
    colors = baseColors.copy(
        titleColor = Color(0xFF147D64),
        dividerColor = Color(0xFFC5C8CF),
    ),
)
```

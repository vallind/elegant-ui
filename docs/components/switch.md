# Switch

`ElegantSwitch` is a refined on/off control with an animated capsule track and thumb, theme-aware state colors, and an optional inline label. Use it for settings screens and real-time preference toggles where a single persistent on/off state is required.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=switch" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.switch.ElegantSwitch
import com.elegant.compose.ui.switch.ElegantSwitchColors
import com.elegant.compose.ui.switch.ElegantSwitchDefaults
```

## Basic Usage

A switch is fully controlled: pass the current `checked` value and an `onCheckedChange` callback that writes the requested state back. Add `label` to render an inline text label on the same 48dp interactive row.

```kotlin
var notifications by remember { mutableStateOf(true) }

ElegantSwitch(
    checked = notifications,
    onCheckedChange = { notifications = it },
    label = "Push notifications",
)
```

## Component States

A 44x24dp capsule track carries the checked and unchecked containers, while a 2dp focus ring appears only while the row has keyboard focus. The 16dp thumb travels across the track with a standard 160ms motion. `checked` is a semantic state that combines with interaction visuals: a pressed on switch shows the pressed on colors, and disabled switches never invoke `onCheckedChange` and are announced through the `Role.Switch` semantics of the toggleable row.

State precedence: disabled, pressed, hovered, resting; the on or off visuals are then applied on top of the winning interaction state.

```kotlin
var wifi by remember { mutableStateOf(true) }

ElegantSwitch(
    checked = wifi,
    onCheckedChange = { wifi = it },
    label = "Wi-Fi",
)

ElegantSwitch(
    checked = false,
    onCheckedChange = {},
    enabled = false,
    label = "Disabled",
)
```

## Properties

### ElegantSwitch Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `checked` | `Boolean` | Whether the switch is on | - | Yes |
| `onCheckedChange` | `(Boolean) -> Unit` | Callback invoked with the requested state | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the switch row root | `Modifier` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted | `true` | No |
| `label` | `String?` | Optional inline text label rendered after the track | `null` | No |
| `colors` | `ElegantSwitchColors` | Theme-aware state colors | `ElegantSwitchDefaults.colors()` | No |
| `interactionSource` | `MutableInteractionSource?` | Optional hoisted interaction source for observing or controlling state | `null` | No |

### ElegantSwitchDefaults

| Member | Type | Description |
| --- | --- | --- |
| `TrackWidth` | `Dp` | 44dp visual track width |
| `TrackHeight` | `Dp` | 24dp visual track height |
| `ThumbSize` | `Dp` | 16dp visual thumb diameter |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive row height |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantSwitchColors` | Theme-aware Light/Dark colors |

### ElegantSwitchColors

`ElegantSwitchColors` contains track and thumb colors for the checked, unchecked, hovered, pressed, disabled, and focused states. Start with `ElegantSwitchDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Controlled Preference Group

Hoist every switch to the screen state so dependent preferences can be gated behind their parent.

```kotlin
var autoUpdates by remember { mutableStateOf(true) }
var overnightInstall by remember { mutableStateOf(false) }

ElegantSwitch(
    checked = autoUpdates,
    onCheckedChange = { autoUpdates = it },
    label = "Automatic updates",
)

ElegantSwitch(
    checked = overnightInstall,
    onCheckedChange = { overnightInstall = it },
    enabled = autoUpdates,
    label = "Install overnight",
)
```

### Custom Colors

```kotlin
val baseColors = ElegantSwitchDefaults.colors()

ElegantSwitch(
    checked = checked,
    onCheckedChange = onCheckedChange,
    label = "Custom",
    colors = baseColors.copy(
        trackCheckedColor = Color(0xFF147D64),
        thumbCheckedColor = Color.White,
    ),
)
```

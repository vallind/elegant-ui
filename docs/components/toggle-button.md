# ToggleButton

`ElegantToggleButton` is a polished cross-platform toggle component in Elegant UI. It provides a single selectable state with a checkbox-style semantic contract, pointer hover, touch press, keyboard focus, and animated container, content, and border feedback. `ElegantToggleButtonGroup` joins toggles into a single rounded cluster with zero spacing while each child keeps its own border and interaction behavior.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=toggle-button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.togglebutton.ElegantToggleButton
import com.elegant.compose.ui.togglebutton.ElegantToggleButtonColors
import com.elegant.compose.ui.togglebutton.ElegantToggleButtonDefaults
import com.elegant.compose.ui.togglebutton.ElegantToggleButtonGroup
```

## Basic Usage

A toggle is a controlled component: pass the current state and the callback that applies the next state. The toggle keeps a 48dp minimum touch target around a 36dp visual box.

```kotlin
var bold by remember { mutableStateOf(false) }

ElegantToggleButton(
    selected = bold,
    onToggle = { bold = it },
) {
    Text("Bold")
}
```

## Toggle Button Group

`ElegantToggleButtonGroup` renders a row of joined toggles with zero spacing and clips the cluster to a single small-radius outline. The group's `enabled` and `colors` become the defaults for children that do not pass their own values.

```kotlin
var viewPeriod by remember { mutableIntStateOf(0) }

ElegantToggleButtonGroup {
    ElegantToggleButton(
        selected = viewPeriod == 0,
        onToggle = { if (it) viewPeriod = 0 },
    ) {
        Text("Day")
    }
    ElegantToggleButton(
        selected = viewPeriod == 1,
        onToggle = { if (it) viewPeriod = 1 },
    ) {
        Text("Week")
    }
    ElegantToggleButton(
        selected = viewPeriod == 2,
        onToggle = { if (it) viewPeriod = 2 },
    ) {
        Text("Month")
    }
}
```

## Component States

Hover, press, and keyboard focus feedback are resolved automatically from the shared interaction source. The selected state wins over pressed and hovered visuals: a selected toggle keeps its accent container and content while pressed. When the theme enables focus rings, keyboard focus replaces the border with the focus ring, and the border keeps a constant width across every state. Disabled toggles drop to muted container and text tones and never invoke `onToggle`.

### Selected State

The selected state switches the container to the subtle accent surface and the content and border to the primary interactive color. Selection is announced to accessibility services through a merged `Role.Checkbox` node with an `On` or `Off` toggle state.

### Disabled State

```kotlin
ElegantToggleButton(
    selected = false,
    onToggle = { },
    enabled = false,
) {
    Text("Disabled Toggle")
}
```

## Properties

### ElegantToggleButton Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `selected` | `Boolean` | Whether the toggle communicates the on state | - | Yes |
| `onToggle` | `(Boolean) -> Unit` | Callback invoked with the state to apply when the toggle accepts activation | - | Yes |
| `modifier` | `Modifier` | Modifier applied to the 48dp minimum touch-target container | `Modifier` | No |
| `enabled` | `Boolean` | Whether the toggle can accept user interaction | `true` | No |
| `colors` | `ElegantToggleButtonColors` | Theme-aware default, selected, hovered, pressed, focused, and disabled colors | `ElegantToggleButtonDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Composable content displayed as the toggle label | - | Yes |

### ElegantToggleButtonGroup Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied to the joined cluster | `Modifier` | No |
| `enabled` | `Boolean` | Whether children can accept user interaction | `true` | No |
| `colors` | `ElegantToggleButtonColors` | Colors inherited by children that do not pass their own | `ElegantToggleButtonDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Composable content rendered as joined toggles | - | Yes |

### ElegantToggleButtonDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | Minimum interactive root height |
| `Height` | `Dp` | Visual height of the toggle box |
| `HorizontalPadding` | `Dp` | Horizontal padding inside the visual box |
| `AnimationDurationMillis` | `Int` | Standard state-transition duration |
| `colors()` | `ElegantToggleButtonColors` | Returns theme-aware colors, or the enclosing group's colors |
| `shape()` | `Shape` | Returns the small-radius default shape |

### ElegantToggleButtonColors

`ElegantToggleButtonColors` contains default, selected, hovered, pressed, disabled, and focused container, content, and border values plus the constant border width. Hovered, pressed, and disabled values fall back to their resting equivalents when not set. Start with `ElegantToggleButtonDefaults.colors()` and use `copy(...)` to override only product-supported values.

## Advanced Usage

### Toggle with Icon

```kotlin
var starred by remember { mutableStateOf(false) }

ElegantToggleButton(
    selected = starred,
    onToggle = { starred = it },
) {
    Icon(
        imageVector = Icons.AutoMirrored.Default.Star,
        contentDescription = null,
    )
}
```

### Filter Bar with Group

A disabled group keeps every child toggle non-interactive while the underlying data is loading.

```kotlin
var filter by remember { mutableIntStateOf(0) }
var loading by remember { mutableStateOf(false) }

ElegantToggleButtonGroup(enabled = !loading) {
    ElegantToggleButton(selected = filter == 0, onToggle = { if (it) filter = 0 }) { Text("All") }
    ElegantToggleButton(selected = filter == 1, onToggle = { if (it) filter = 1 }) { Text("Favorites") }
    ElegantToggleButton(selected = filter == 2, onToggle = { if (it) filter = 2 }) { Text("Archived") }
}
```

### Custom Colors

```kotlin
val baseColors = ElegantToggleButtonDefaults.colors()

ElegantToggleButton(
    selected = false,
    onToggle = { },
    colors = baseColors.copy(
        selectedContainerColor = Color(0xFF0F766E),
        selectedContentColor = Color(0xFFFFFFFF),
    ),
) {
    Text("Custom Selected")
}
```

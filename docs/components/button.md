# Button

`ElegantButton` is a polished cross-platform action component in Elegant UI. It provides primary, secondary, and tertiary emphasis, three optically tuned sizes, pointer hover, touch press, keyboard focus, optional icon slots, and a width-stable loading state.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.button.ElegantButtonColors
import com.elegant.compose.ui.button.ElegantButtonDefaults
import com.elegant.compose.ui.button.ElegantButtonElevation
import com.elegant.compose.ui.button.ElegantButtonSize
import com.elegant.compose.ui.button.ElegantButtonStyle
```

## Basic Usage

The Button component can be used to trigger an action:

```kotlin
ElegantButton(
    onClick = { /* Handle click event */ },
) {
    Text("Button")
}
```

## Button Types

Elegant UI provides button types for different levels of emphasis.

### Primary Button

Use the primary button for the dominant action in a task or surface.

```kotlin
ElegantButton(
    onClick = { /* Handle click event */ },
    style = ElegantButtonStyle.Primary,
) {
    Text("Primary Button")
}
```

### Secondary Button

Use the secondary button for supporting actions that still require a visible container.

```kotlin
ElegantButton(
    onClick = { /* Handle click event */ },
    style = ElegantButtonStyle.Secondary,
) {
    Text("Secondary Button")
}
```

### Tertiary Button

Use the tertiary button for low-emphasis or contextual actions.

```kotlin
ElegantButton(
    onClick = { /* Handle click event */ },
    style = ElegantButtonStyle.Tertiary,
) {
    Text("Tertiary Button")
}
```

## Component States

Hover, press, and keyboard focus feedback are resolved automatically from the shared interaction source. Primary buttons subtly raise on hover and settle on press; all styles keep a visible focus ring.

### Disabled State

```kotlin
ElegantButton(
    onClick = { /* Handle click event */ },
    enabled = false,
) {
    Text("Disabled Button")
}
```

### Loading State

Loading keeps the measured label and icon content in place, overlays a centered progress indicator, prevents duplicate activation, and exposes a customizable accessibility description.

## Properties

### ElegantButton Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `onClick` | `() -> Unit` | Callback triggered when the button accepts a click | - | Yes |
| `modifier` | `Modifier` | Modifier applied to the 48dp minimum touch-target container | `Modifier` | No |
| `enabled` | `Boolean` | Whether the button can accept user interaction | `true` | No |
| `loading` | `Boolean` | Shows progress and prevents duplicate activation | `false` | No |
| `loadingStateDescription` | `String` | Localized accessibility state announced while loading | `"Loading"` | No |
| `interactionSource` | `MutableInteractionSource?` | Optional hoisted source for observing hover, press, and focus | `null` | No |
| `style` | `ElegantButtonStyle` | Visual emphasis type of the button | `ElegantButtonStyle.Primary` | No |
| `size` | `ElegantButtonSize` | Visual size and internal metrics | `ElegantButtonSize.Medium` | No |
| `shape` | `Shape` | Optically tuned container shape | `ElegantButtonDefaults.shape(size)` | No |
| `colors` | `ElegantButtonColors` | Theme-aware default, hovered, pressed, focused, and disabled colors | `ElegantButtonDefaults.colors(style)` | No |
| `elevation` | `ElegantButtonElevation` | State-aware tonal elevation model | `ElegantButtonDefaults.elevation(style)` | No |
| `leadingIcon` | `(@Composable () -> Unit)?` | Optional icon or content before the label | `null` | No |
| `trailingIcon` | `(@Composable () -> Unit)?` | Optional icon or content after the label | `null` | No |
| `content` | `@Composable () -> Unit` | Composable content displayed as the button label | - | Yes |

### ElegantButtonStyle Values

| Value | Description |
| --- | --- |
| `Primary` | Highest-emphasis action with the primary interactive container |
| `Secondary` | Supporting action with a raised container and visible border |
| `Tertiary` | Low-emphasis action with a transparent default container |

### ElegantButtonSize Values

| Value | Visual Height | Minimum Touch Height | Horizontal Padding | Icon Size |
| --- | --- | --- | --- | --- |
| `Small` | `36.dp` | `48.dp` | `12.dp` | `16.dp` |
| `Medium` | `40.dp` | `48.dp` | `16.dp` | `18.dp` |
| `Large` | `48.dp` | `48.dp` | `20.dp` | `20.dp` |

### ElegantButtonDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | Minimum interactive root height used by every size |
| `AnimationDurationMillis` | `Int` | Standard state-transition duration |
| `PressAnimationDurationMillis` | `Int` | Immediate press-response duration |
| `HoveredScale` | `Float` | Restrained pointer-hover scale |
| `PressedScale` | `Float` | Restrained press scale |
| `colors(style)` | `ElegantButtonColors` | Returns theme-aware colors for a button style |
| `shape(size)` | `Shape` | Returns the optically tuned shape for a size |
| `elevation(style)` | `ElegantButtonElevation` | Returns the interaction elevation model for a style |

### ElegantButtonColors

`ElegantButtonColors` contains default, hovered, pressed, focused, and disabled container, content, border, and border-width values. Start with `ElegantButtonDefaults.colors(style)` and use `copy(...)` to override only product-supported values.

### ElegantButtonElevation

`ElegantButtonElevation` centralizes default, hovered, pressed, focused, and disabled elevation. Primary actions use a subtle resting shadow, rise on hover or focus, and settle on press; secondary and tertiary actions remain quieter.

## Advanced Usage

### Button with Icon

```kotlin
ElegantButton(
    onClick = { /* Handle click event */ },
    leadingIcon = {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
        )
    },
) {
    Text("Create")
}
```

### Button with Leading and Trailing Icons

```kotlin
ElegantButton(
    onClick = { /* Handle click event */ },
    leadingIcon = {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
        )
    },
    trailingIcon = {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowForward,
            contentDescription = null,
        )
    },
) {
    Text("Continue")
}
```

### Custom Colors

```kotlin
val baseColors = ElegantButtonDefaults.colors(ElegantButtonStyle.Primary)

ElegantButton(
    onClick = { /* Handle click event */ },
    colors = baseColors.copy(
        containerColor = Color(0xFF0F766E),
        pressedContainerColor = Color(0xFF115E59),
    ),
) {
    Text("Custom Action")
}
```

### Loading State Button

```kotlin
var isLoading by remember { mutableStateOf(false) }
val scope = rememberCoroutineScope()

ElegantButton(
    onClick = {
        isLoading = true
        scope.launch {
            delay(2000)
            isLoading = false
        }
    },
    loading = isLoading,
    loadingStateDescription = "Submitting",
) {
    Text("Submit")
}
```

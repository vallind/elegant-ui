# Button

`ElegantButton` is a basic interactive component in Elegant UI, used to trigger actions or events. It provides primary, secondary, and tertiary button types, three sizes, optional icon slots, disabled behavior, and a built-in loading state.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.button.ElegantButton
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

### Disabled State

```kotlin
ElegantButton(
    onClick = { /* Handle click event */ },
    enabled = false,
) {
    Text("Disabled Button")
}
```

## Properties

### ElegantButton Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `onClick` | `() -> Unit` | Callback triggered when the button accepts a click | - | Yes |
| `modifier` | `Modifier` | Modifier applied to the 48dp minimum touch-target container | `Modifier` | No |
| `style` | `ElegantButtonStyle` | Visual emphasis type of the button | `ElegantButtonStyle.Primary` | No |
| `size` | `ElegantButtonSize` | Visual size and internal metrics | `ElegantButtonSize.Medium` | No |
| `enabled` | `Boolean` | Whether the button can accept user interaction | `true` | No |
| `loading` | `Boolean` | Shows progress and prevents duplicate activation | `false` | No |
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
) {
    Text(if (isLoading) "Submitting" else "Submit")
}
```

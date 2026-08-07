# Floating Action Button

`ElegantFloatingActionButton` is a circular, elevated action component in Elegant UI for the primary action of a screen. It provides standard and compact sizes, pointer hover, touch press with a restrained scale, a keyboard focus ring when the theme enables it, and theme-aware disabled colors.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=floating-action-button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.floatingactionbutton.ElegantFloatingActionButton
import com.elegant.compose.ui.floatingactionbutton.ElegantFloatingActionButtonColors
import com.elegant.compose.ui.floatingactionbutton.ElegantFloatingActionButtonDefaults
```

## Basic Usage

Use a floating action button for the primary action of a screen, anchored above the content:

```kotlin
ElegantFloatingActionButton(
    onClick = { /* Handle click event */ },
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "Add",
    )
}
```

## Component States

Hover, press, and keyboard focus feedback are resolved automatically from the shared interaction source. Hovering shifts the container color, pressing settles the container color and applies a restrained scale inside the fixed touch target, and keyboard focus draws a visible ring around the circle.

### Disabled State

```kotlin
ElegantFloatingActionButton(
    onClick = { /* Handle click event */ },
    enabled = false,
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "Add",
    )
}
```

## Properties

### ElegantFloatingActionButton Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `onClick` | `() -> Unit` | Callback triggered when the FAB accepts a click | - | Yes |
| `modifier` | `Modifier` | Modifier applied to the fixed-size touch-target container | `Modifier` | No |
| `enabled` | `Boolean` | Whether the FAB can accept user interaction | `true` | No |
| `compact` | `Boolean` | Whether the compact 40dp circular size is used | `false` | No |
| `colors` | `ElegantFloatingActionButtonColors` | Theme-aware default, hovered, pressed, focused, and disabled colors | `ElegantFloatingActionButtonDefaults.colors()` | No |
| `interactionSource` | `MutableInteractionSource?` | Optional hoisted source for observing hover, press, and focus | `null` | No |
| `content` | `@Composable () -> Unit` | Composable content centered inside the FAB | - | Yes |

### ElegantFloatingActionButtonDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Size` | `Dp` | Standard 56dp circular size |
| `CompactSize` | `Dp` | Compact 40dp circular size |
| `AnimationDurationMillis` | `Int` | Standard state-transition duration |
| `PressedScale` | `Float` | Restrained press scale that preserves the fixed touch target |
| `colors()` | `ElegantFloatingActionButtonColors` | Returns theme-aware FAB colors |

### ElegantFloatingActionButtonColors

`ElegantFloatingActionButtonColors` contains default, hovered, pressed, focused, and disabled container and content colors plus the focus-ring border color. Start with `ElegantFloatingActionButtonDefaults.colors()` and use `copy(...)` to override only product-supported values.

## Advanced Usage

### Compact Floating Action Button

Use the compact size in dense surfaces or toolbars:

```kotlin
ElegantFloatingActionButton(
    onClick = { /* Handle click event */ },
    compact = true,
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "Add",
    )
}
```

### Custom Colors

```kotlin
val baseColors = ElegantFloatingActionButtonDefaults.colors()

ElegantFloatingActionButton(
    onClick = { /* Handle click event */ },
    colors = baseColors.copy(
        containerColor = Color(0xFF0F766E),
        pressedContainerColor = Color(0xFF115E59),
    ),
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "Add",
    )
}
```

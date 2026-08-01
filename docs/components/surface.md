# Surface

`ElegantSurface` is the low-level container primitive of the library. Unlike `ElegantCard`, which is a content surface with `Filled`/`Outlined`/`Elevated` style presets, a surface has no style enum, no built-in padding, and no text styles: it renders a background, an optional border, and an optional click interaction only, and hands spacing and styling to the caller. Use it as the foundation for custom containers, or nest it to build layered layouts.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=surface" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.surface.ElegantSurface
import com.elegant.compose.ui.surface.ElegantSurfaceColors
import com.elegant.compose.ui.surface.ElegantSurfaceDefaults
```

## Basic Usage

A surface without `onClick` is a plain container: it clips the `shape`, fills it with the container color, draws an optional border, and keeps the semantics of its content. There is no padding and no text style — the caller owns spacing and typography inside the content, which receives the content color through `LocalContentColor`.

```kotlin
ElegantSurface(borderWidth = 1.dp) {
    Column(Modifier.padding(16.dp)) {
        Text("Foundation layer")
        Text("The surface itself carries no spacing or text style.")
    }
}
```

## Component States

Passing `onClick` turns the surface into a button-like container: it keeps a 48dp minimum interactive root, announces `Role.Button`, applies hover and press container colors with a ripple, draws a 2dp focus ring while focused and enabled, and rejects interaction when `enabled` is false.

State precedence: disabled, pressed, hovered, resting. Focus overrides only the border, never the container color.

```kotlin
var taps by remember { mutableIntStateOf(0) }

ElegantSurface(
    onClick = { taps += 1 },
    borderWidth = 1.dp,
) {
    Column(Modifier.padding(16.dp)) {
        Text("Tap to activate")
        Text("$taps activations")
    }
}

ElegantSurface(
    onClick = {},
    enabled = false,
) {
    Text("Disabled")
}
```

## Properties

### ElegantSurface Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `onClick` | `(() -> Unit)?` | Optional activation callback; null keeps the surface non-interactive | `null` | No |
| `modifier` | `Modifier` | Modifier applied once to the surface root | `Modifier` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted | `true` | No |
| `shape` | `Shape` | Clipping and border shape | `ElegantSurfaceDefaults.shape()` | No |
| `colors` | `ElegantSurfaceColors` | Theme-aware state colors | `ElegantSurfaceDefaults.colors()` | No |
| `borderWidth` | `Dp` | Resting border width; 0 renders no border | `0.dp` | No |
| `content` | `@Composable () -> Unit` | Surface content; padding and text styles are the caller's responsibility | - | Yes |

### ElegantSurfaceDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive root used by clickable surfaces |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantSurfaceColors` | Theme-aware Light/Dark colors |
| `shape()` | `Shape` | Shared 8dp rounded corner shape |

### ElegantSurfaceColors

`ElegantSurfaceColors` contains the container, content, and border colors plus hovered, pressed, disabled, and focus-ring overrides. Start with `ElegantSurfaceDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Nested Surface Composition

Surfaces nest freely: an outer bordered surface can hold inner surfaces that group content at a deeper visual level. Each surface provides its own `LocalContentColor`, so nested text keeps the color of its immediate container.

```kotlin
ElegantSurface(borderWidth = 1.dp) {
    Column(Modifier.padding(16.dp)) {
        Text("Outer surface")
        ElegantSurface {
            Column(Modifier.padding(12.dp)) {
                Text("Inner surface")
            }
        }
    }
}
```

### Custom Colors and Border

```kotlin
val baseColors = ElegantSurfaceDefaults.colors()

ElegantSurface(
    borderWidth = 1.dp,
    colors = baseColors.copy(
        containerColor = Color(0xFF6C4EFF),
        contentColor = Color.White,
        borderColor = Color(0xFF6C4EFF),
    ),
) {
    Text("Custom surface")
}
```

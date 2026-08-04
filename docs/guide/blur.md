# Blur

`Modifier.elegantBlur` applies a Gaussian blur render effect to a node's own drawn content on Android, Desktop JVM, and Web/Wasm. It maps onto the Compose Multiplatform blur render effect (`Modifier.blur` with its `BlurredEdgeTreatment`), which every supported target implements through the platform render-effect pipeline: Skia `ImageFilter` on Desktop JVM, the blur `RenderEffect` on Android, and the equivalent canvas filter on Web/Wasm.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=blur" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.foundation.effect.BlurEdgeTreatment
import com.elegant.compose.ui.foundation.effect.elegantBlur
```

## Basic Usage

Attach `elegantBlur` with a positive radius to blur the node's own drawn content. A non-positive or non-finite radius leaves the modifier unchanged.

```kotlin
Text(
    text = "Blurred text",
    modifier = Modifier.elegantBlur(radius = 8.dp),
)
```

## Component States

The `radius` controls the blur spread and `edgeTreatment` controls how the blurred layer behaves at its edges: `Rectangle` clips the blur to the node's bounds, `Unbounded` lets it extend beyond them.

```kotlin
Text(
    text = "Rectangle edge",
    modifier = Modifier.elegantBlur(
        radius = 12.dp,
        edgeTreatment = BlurEdgeTreatment.Rectangle,
    ),
)

Text(
    text = "Unbounded edge",
    modifier = Modifier.elegantBlur(
        radius = 12.dp,
        edgeTreatment = BlurEdgeTreatment.Unbounded,
    ),
)
```

## Properties

### Modifier.elegantBlur Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `radius` | `Dp` | Blur radius; non-positive or non-finite values leave the modifier unchanged | - | Yes |
| `edgeTreatment` | `BlurEdgeTreatment` | How the blurred layer is treated at its edges | `BlurEdgeTreatment.Rectangle` | No |

### BlurEdgeTreatment

| Value | Description |
| --- | --- |
| `Rectangle` | The blurred layer is clipped to the node's rectangular bounds |
| `Unbounded` | The blur may extend beyond the node's bounds |

## Advanced Usage

### Blurred Copy Behind Crisp Content

The effect blurs only the node's own drawn content — siblings behind the node are not blurred on any supported target. To blur a background, compose a blurred copy of it behind the crisp foreground content.

```kotlin
Box {
    Text(
        text = "Background blur",
        modifier = Modifier.elegantBlur(radius = 12.dp),
    )
    Text(
        text = "Crisp foreground",
        modifier = Modifier.padding(4.dp),
    )
}
```

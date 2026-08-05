# Blur

Elegant UI provides two blur systems. `Modifier.elegantBlur` applies a Gaussian blur to a node's own drawn content on Android, Desktop JVM, and Web/Wasm. The `elegant-blur` module adds the full backdrop glass-effect system: it captures the content behind a surface into a graphics layer, then applies a downscaled, noise-dithered, multi-level texture blur — with optional progressive (gradient) ramps, color blending, and edge highlights — matching the Miuix glass pipeline pixel for pixel.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=blur" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

The simple node blur lives in `foundation.effect`; the backdrop system lives in the `elegant-blur` module:

```kotlin
import com.elegant.compose.ui.foundation.effect.BlurEdgeTreatment
import com.elegant.compose.ui.foundation.effect.elegantBlur
import com.elegant.compose.ui.blur.rememberElegantLayerBackdrop
import com.elegant.compose.ui.blur.elegantLayerBackdrop
import com.elegant.compose.ui.blur.elegantTextureBlur
import com.elegant.compose.ui.blur.elegantProgressiveTextureBlur
```

## Basic Usage

Attach `elegantBlur` with a positive radius to blur the node's own drawn content. A non-positive or non-finite radius leaves the modifier unchanged.

```kotlin
Text(
    text = "Blurred text",
    modifier = Modifier.elegantBlur(radius = 8.dp),
)
```

## Backdrop Blur

The backdrop system blurs what is *behind* a surface instead of the surface's own content. Capture the background into a layer, then apply the blur to the foreground surface:

```kotlin
val backdrop = rememberElegantLayerBackdrop()

Box(Modifier.elegantLayerBackdrop(backdrop)) { /* scrolling background content */ }

Box(
    Modifier
        .size(200.dp)
        .elegantTextureBlur(
            backdrop = backdrop,
            shape = RoundedCornerShape(ElegantRadius.lg),
            blurRadius = 20f,
        ),
) { /* crisp foreground content */ }
```

`elegantTextureBlur` downscales the captured layer, dithers it against banding, and blurs it with a separable Gaussian kernel, then composites the surface's own content on top. `colors` from `ElegantBlurDefaults.blurColors` applies brightness/contrast/saturation adjustments and layered color blends (all standard and extended blend modes); `highlight` paints an `ElegantHighlight` edge bloom on top; `contentBlendMode = ComposeBlendMode.DstIn` masks the blur by the content alpha for foreground blur.

## Progressive Blur

`elegantProgressiveTextureBlur` ramps the blur from full strength to pixel-sharp along a gradient — ideal for navigation bars and edge fades. `ElegantProgressiveBlur` presets cover the four edges (`Top`, `Bottom`, `Left`, `Right`); `angle`, `startFraction`, `endFraction`, and `curve` customize the ramp.

```kotlin
val backdrop = rememberElegantLayerBackdrop()

Box(
    Modifier
        .fillMaxWidth()
        .height(56.dp)
        .elegantProgressiveTextureBlur(
            backdrop = backdrop,
            shape = RectangleShape,
            blurRadius = 40f,
            gradient = ElegantProgressiveBlur.Bottom,
        ),
) { /* navigation bar content */ }
```

## Properties

### Modifier.elegantBlur Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `radius` | `Dp` | Blur radius; non-positive or non-finite values leave the modifier unchanged | - | Yes |
| `edgeTreatment` | `BlurEdgeTreatment` | How the blurred layer is treated at its edges | `BlurEdgeTreatment.Rectangle` | No |

### Modifier.elegantTextureBlur Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `backdrop` | `ElegantBackdrop` | The captured background to blur | - | Yes |
| `shape` | `Shape` | Clips the blur region | - | Yes |
| `blurRadius` | `Float` | Blur radius in dp, clamped to `ElegantBlurDefaults.MaxBlurRadius` | `20f` | No |
| `noiseCoefficient` | `Float` | Anti-banding dithering strength; 0 disables | `0.0045f` | No |
| `colors` | `ElegantBlurColors` | Brightness/contrast/saturation and blend layers | `ElegantBlurColors()` | No |
| `highlight` | `ElegantHighlight?` | Edge highlight painted on top; null skips | `null` | No |
| `contentBlendMode` | `BlendMode` | How the content composites over the blur | `SrcOver` | No |
| `enabled` | `Boolean` | When false the effect is skipped | `true` | No |

### Modifier.elegantProgressiveTextureBlur Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `backdrop` | `ElegantBackdrop` | The captured background to blur | - | Yes |
| `shape` | `Shape` | Clips the blur region | - | Yes |
| `blurRadius` | `Float` | Blur radius in dp at full strength | `20f` | No |
| `gradient` | `ElegantProgressiveBlur` | Where the blur is full vs zero | `ElegantProgressiveBlur.Top` | No |
| `noiseCoefficient` | `Float` | Anti-banding dithering strength; 0 disables | `0f` | No |
| `colors` | `ElegantBlurColors` | Brightness/contrast/saturation and blend layers | `ElegantBlurColors()` | No |
| `highlight` | `ElegantHighlight?` | Edge highlight painted on top; null skips | `null` | No |
| `contentBlendMode` | `BlendMode` | How the content composites over the blur | `SrcOver` | No |
| `enabled` | `Boolean` | When false the effect is skipped | `true` | No |

## Advanced Usage

### Glass Panel with Tint and Highlight

Combine the captured backdrop with a tinted blur and a light halo for the classic glass card. `ElegantBlurDefaults.blurColors` remembers the color configuration so `@Immutable` stability is preserved.

```kotlin
val backdrop = rememberElegantLayerBackdrop()
val colors = ElegantBlurDefaults.blurColors(
    blendColors = listOf(
        ElegantBlendColorEntry(Color.White.copy(alpha = 0.4f), ElegantBlurBlendMode.SrcOver),
    ),
    brightness = 0.05f,
)

Box(Modifier.elegantLayerBackdrop(backdrop)) { /* background */ }

ElegantCard(
    modifier = Modifier.elegantTextureBlur(
        backdrop = backdrop,
        shape = RoundedCornerShape(ElegantRadius.lg),
        blurRadius = 24f,
        colors = colors,
        highlight = ElegantHighlight.GlassStrokeMiddleLight,
    ),
) {
    Text("Glass surface")
}
```

Runtime shaders are required by the backdrop pipeline; on platforms or API levels without them (`isRuntimeShaderSupported()` false, e.g. Android below API 33) the effect draws normally without blur — same behavior as the reference implementation. `Modifier.elegantBlur` has no such gate.

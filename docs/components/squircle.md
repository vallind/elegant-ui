# Squircle

`ElegantSquircleShape` is a continuous-curvature rounded rectangle whose corners approximate the superellipse `|x/a|^n + |y/b|^n = 1` with `n ≈ 4`. Unlike `RoundedCornerShape`, whose corners join a circle arc to a straight edge at a tangent discontinuity, every squircle corner is a cubic Bezier arc that flows into the straight edges without a visible transition — the silhouette reads as one soft, continuous curve. It implements the `Shape` contract, so it works anywhere a shape is accepted: `ElegantSurface`, `ElegantAvatar`, `Modifier.clip`, borders, and shadows.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=squircle" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.shape.ElegantSquircleShape
```

## Basic Usage

Pass `ElegantSquircleShape` as the `shape` of any component that accepts a `Shape`. The default `cornerRadius` is `16.dp` and the default `smoothing` is `0.6f`.

```kotlin
ElegantSurface(shape = ElegantSquircleShape()) {
    Text("Squircle surface")
}
```

## Component States

Smoothing drives the curvature of every corner: `0f` collapses the corner to a plain right angle, `1f` reproduces the roundest stable superellipse look. The effective radius clamps to half of the smaller side, so the outline never self-intersects on small surfaces.

```kotlin
ElegantSurface(shape = ElegantSquircleShape(smoothing = 0f)) {
    Text("Plain corner")
}

ElegantSurface(shape = ElegantSquircleShape(smoothing = 0.6f)) {
    Text("Default smooth")
}

ElegantSurface(shape = ElegantSquircleShape(smoothing = 1f)) {
    Text("Roundest")
}
```

## Properties

### ElegantSquircleShape Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `cornerRadius` | `Dp` | Corner radius of the superellipse arcs, clamped to half of the smaller side | `16.dp` | No |
| `smoothing` | `Float` | Bezier factor in `0..1`; `0` yields plain corners, `1` the roundest arc | `0.6f` | No |

## Advanced Usage

### Squircle Avatar and Card

`smoothing` also makes the squircle ideal for identity surfaces: an avatar in a squircle shape keeps the familiar rounded-square identity without the discontinuity of plain rounded corners.

```kotlin
ElegantAvatar(
    name = "Maya Chen",
    initials = "MC",
    shape = ElegantSquircleShape(cornerRadius = 12.dp, smoothing = 0.8f),
)

ElegantSurface(shape = ElegantSquircleShape(cornerRadius = 20.dp)) {
    Column(Modifier.padding(16.dp)) {
        Text("Squircle card")
    }
}
```

### Compact Surfaces

For small surfaces, reduce both the radius and the smoothing to keep the squircle reading as a rounded rectangle rather than a pill.

```kotlin
ElegantSurface(shape = ElegantSquircleShape(cornerRadius = 8.dp, smoothing = 0.4f)) {
    Text("Compact squircle")
}
```
## Modifier Helpers

When you only need a squircle silhouette, use the composable modifier helpers instead of a raw `ElegantSquircleShape`:

```kotlin
Modifier
    .elegantSquircleSurface(
        color = ElegantTheme.colors.surfaceRaised,
        cornerRadius = 20.dp,
    )
    .elegantSquircleBorder(
        width = 1.dp,
        color = ElegantTheme.colors.borderStrong,
        cornerRadius = 20.dp,
    )
```

The helpers read `LocalSquircleEnabled`; when set to `false` they fall back to plain `RoundedCornerShape` with the same corner radius, which lets apps opt out of squircle rendering for the whole subtree.

```kotlin
CompositionLocalProvider(LocalSquircleEnabled provides false) {
    // rounded corners here
}
```

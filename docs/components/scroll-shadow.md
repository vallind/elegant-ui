# Scroll Shadow

`ElegantScrollShadow` is a decorative edge-fade overlay for scrollable content. Place it above a `verticalScroll` column or `horizontalScroll` row, and it fades the leading edge while the content has scrolled away from it and the trailing edge while more content remains ahead, so long lists never end abruptly.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=scroll-shadow" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.scrollshadow.ElegantScrollShadow
import com.elegant.compose.ui.scrollshadow.ElegantScrollShadowOrientation
import com.elegant.compose.ui.scrollshadow.ElegantScrollShadowColors
import com.elegant.compose.ui.scrollshadow.ElegantScrollShadowDefaults
```

## Basic Usage

Give the scrollable content a `rememberScrollState()` and place `ElegantScrollShadow` as the last child of a `Box` that wraps the content. The overlay fills the box, reads the scroll state, and draws nothing while the content cannot scroll in a direction. It never intercepts scroll gestures.

```kotlin
val scrollState = rememberScrollState()

Box {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        repeat(20) { index ->
            ElegantListItem(title = { Text("Item $index") })
        }
    }
    ElegantScrollShadow(state = scrollState)
}
```

## Orientation

`ElegantScrollShadowOrientation` picks the scroll direction the shadows track. `Vertical` (the default) fades the top edge while the content has scrolled down and the bottom edge while more content remains below. `Horizontal` fades the logical start edge while the content has scrolled inward and the end edge while more content remains ahead, mirroring automatically in RTL.

```kotlin
val scrollState = rememberScrollState()

Box {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(scrollState),
    ) {
        repeat(20) { index ->
            ElegantListItem(title = { Text("Item $index") })
        }
    }
    ElegantScrollShadow(
        state = scrollState,
        orientation = ElegantScrollShadowOrientation.Horizontal,
    )
}
```

## Component States

The scroll shadow has no hover, press, focus, or disabled state. It is decorative by default: it clears its semantics so screen readers skip the overlay, and it never blocks pointer input. Each fade starts transparent and grows to `ElegantScrollShadowDefaults.MaxAlpha` as the remaining scrollable distance grows from zero to `ElegantScrollShadowDefaults.ShadowHeight`. The shadow color resolves from `ElegantTheme` and adapts between Light and Dark automatically.

```kotlin
val scrollState = rememberScrollState()

Box {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        repeat(20) { index ->
            ElegantListItem(title = { Text("Item $index") })
        }
    }
    ElegantScrollShadow(
        state = scrollState,
        colors = ElegantScrollShadowDefaults.colors(),
    )
}
```

## Properties

### ElegantScrollShadow Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `state` | `ScrollState` | Scroll state driving the leading and trailing edge fades | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the overlay root | `Modifier` | No |
| `colors` | `ElegantScrollShadowColors` | Theme-aware shadow color | `ElegantScrollShadowDefaults.colors()` | No |
| `orientation` | `ElegantScrollShadowOrientation` | Scroll direction the shadows track | `ElegantScrollShadowOrientation.Vertical` | No |

### ElegantScrollShadowOrientation

| Option | Description |
| --- | --- |
| `Vertical` | Fades the top and bottom edges of vertically scrolling content |
| `Horizontal` | Fades the start and end edges of horizontally scrolling content |

### ElegantScrollShadowDefaults

| Member | Type | Description |
| --- | --- |
| `ShadowHeight` | `Dp` | 24dp height of the fade band on each scrollable edge |
| `MaxAlpha` | `Float` | 0.35f maximum alpha of a fully revealed fade |
| `colors()` | `ElegantScrollShadowColors` | Theme-aware Light/Dark shadow color |

### ElegantScrollShadowColors

`ElegantScrollShadowColors` holds the single `shadowColor` drawn at the fade alpha. Start with `ElegantScrollShadowDefaults.colors()` and use `copy(...)` only for a deliberate product-specific tint.

## Advanced Usage

### Horizontal Scroll Shadow

Fade the edges of a horizontally scrolling row of cards so the row suggests continuation in both directions.

```kotlin
val scrollState = rememberScrollState()

Box {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
    ) {
        repeat(12) { index ->
            ElegantListItem(
                title = { Text("Card $index") },
                modifier = Modifier.width(160.dp),
            )
        }
    }
    ElegantScrollShadow(
        state = scrollState,
        orientation = ElegantScrollShadowOrientation.Horizontal,
    )
}
```

### Custom Shadow Color

Tint the fades to match the surrounding surface instead of the primary text color.

```kotlin
val shadowColors = ElegantScrollShadowDefaults.colors().copy(
    shadowColor = ElegantTheme.colors.textSecondary,
)

ElegantScrollShadow(
    state = rememberScrollState(),
    colors = shadowColors,
)
```

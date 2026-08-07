# Scroll Bar

`ElegantScrollBar` is a slim, non-interactive scroll-position indicator. Place it over a `verticalScroll` column or `horizontalScroll` row, and a rounded thumb travels along a track while the content scrolls, so long content always shows where it stands.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=scroll-bar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.scrollbar.ElegantScrollBar
import com.elegant.compose.ui.scrollbar.ElegantScrollBarOrientation
import com.elegant.compose.ui.scrollbar.ElegantScrollBarColors
import com.elegant.compose.ui.scrollbar.ElegantScrollBarDefaults
```

## Basic Usage

Give the scrollable content a `rememberScrollState()` and place `ElegantScrollBar` as the last child of a `Box` that wraps the content, aligned to the end edge. The indicator sizes itself to the track, reads the scroll state, and never intercepts scroll gestures.

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
    ElegantScrollBar(
        state = scrollState,
        modifier = Modifier.align(Alignment.CenterEnd),
    )
}
```

## Orientation

`ElegantScrollBarOrientation` picks the scroll direction the indicator tracks. `Vertical` (the default) runs a vertical track along the full height. `Horizontal` mirrors the geometry: a horizontal track along the full width with the thumb travelling from the start edge.

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
    ElegantScrollBar(
        state = scrollState,
        orientation = ElegantScrollBarOrientation.Horizontal,
        modifier = Modifier.align(Alignment.BottomCenter),
    )
}
```

## Component States

The scroll bar has no hover, press, focus, or disabled state. It is decorative by default: it clears its semantics so screen readers skip the indicator, and it never blocks pointer input. The thumb length is the track length scaled by the ratio of the track to the scrollable distance, clamped to at least `ElegantScrollBarDefaults.MinThumbFraction` of the track, and the thumb offset is the scrolled fraction of the remaining track length. When the content fits and cannot scroll, the thumb fills the track at the leading edge. Colors resolve from `ElegantTheme` and adapt between Light and Dark automatically.

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
    ElegantScrollBar(
        state = scrollState,
        colors = ElegantScrollBarDefaults.colors(),
        modifier = Modifier.align(Alignment.CenterEnd),
    )
}
```

## Properties

### ElegantScrollBar Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `state` | `ScrollState` | Scroll state driving the thumb position and length | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the indicator root | `Modifier` | No |
| `orientation` | `ElegantScrollBarOrientation` | Scroll direction the indicator tracks | `ElegantScrollBarOrientation.Vertical` | No |
| `colors` | `ElegantScrollBarColors` | Theme-aware thumb and track colors | `ElegantScrollBarDefaults.colors()` | No |

### ElegantScrollBarOrientation

| Option | Description |
| --- | --- |
| `Vertical` | Runs a vertical track with the thumb travelling top to bottom |
| `Horizontal` | Runs a horizontal track with the thumb travelling from the start edge |

### ElegantScrollBarDefaults

| Member | Type | Description |
| --- | --- |
| `ThumbWidth` | `Dp` | 4dp width of the scroll-position thumb |
| `TrackWidth` | `Dp` | 8dp width of the track behind the thumb |
| `MinThumbFraction` | `Float` | 0.1f smallest fraction of the track the thumb can occupy |
| `colors()` | `ElegantScrollBarColors` | Theme-aware Light/Dark thumb and track colors |

### ElegantScrollBarColors

`ElegantScrollBarColors` holds the `thumbColor` and `trackColor` drawn by the indicator. Start with `ElegantScrollBarDefaults.colors()` and use `copy(...)` only for a deliberate product-specific tint.

## Advanced Usage

### Horizontal Scroll Bar

Add a scroll-position indicator to the bottom edge of a horizontally scrolling row of cards.

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
    ElegantScrollBar(
        state = scrollState,
        orientation = ElegantScrollBarOrientation.Horizontal,
        modifier = Modifier.align(Alignment.BottomCenter),
    )
}
```

### Custom Scroll Bar Colors

Tint the indicator to match the surrounding surface instead of the theme defaults.

```kotlin
val scrollBarColors = ElegantScrollBarDefaults.colors().copy(
    thumbColor = ElegantTheme.colors.textSecondary,
    trackColor = ElegantTheme.colors.borderStrong,
)

ElegantScrollBar(
    state = rememberScrollState(),
    colors = scrollBarColors,
)
```

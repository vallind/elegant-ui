# Skeleton

`ElegantSkeleton` is a shimmering placeholder surface that previews content while it loads. Use it for loading lists, profile headers, media blocks, and any region where a themed placeholder will be replaced by real content.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=skeleton" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.skeleton.ElegantSkeleton
import com.elegant.compose.ui.skeleton.ElegantSkeletonBlock
import com.elegant.compose.ui.skeleton.ElegantSkeletonColors
import com.elegant.compose.ui.skeleton.ElegantSkeletonDefaults
```

## Basic Usage

A single `ElegantSkeleton` renders a rounded, theme-aware placeholder with a continuously moving highlight band. The caller owns the size: the skeleton fills whatever width and height the modifier provides.

```kotlin
ElegantSkeleton(
    modifier = Modifier
        .fillMaxWidth()
        .height(96.dp),
)
```

`ElegantSkeletonBlock` is a convenience column of shimmering text-like lines. The last line is shortened with `lastLineWidthFraction` so paragraphs read like real copy.

```kotlin
ElegantSkeletonBlock(
    columns = 3,
    modifier = Modifier.fillMaxWidth(),
)
```

## Component States

Skeletons have no hover, press, focus, or disabled state. They are decorative by default: `ElegantSkeleton` clears its semantics so screen readers skip the placeholder while content is loading. Colors resolve from `ElegantTheme` and adapt between Light and Dark automatically; the highlight always stays lighter than the base surface.

```kotlin
var loading by remember { mutableStateOf(true) }

if (loading) {
    ElegantSkeletonBlock(columns = 3, modifier = Modifier.fillMaxWidth())
} else {
    Column {
        Text("Content is ready")
    }
}
```

## Properties

### ElegantSkeleton Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the skeleton root | `Modifier` | No |
| `shape` | `Shape` | Clipping shape of the placeholder | `RoundedCornerShape(ElegantRadius.sm)` | No |
| `colors` | `ElegantSkeletonColors` | Theme-aware base and highlight colors | `ElegantSkeletonDefaults.colors()` | No |

### ElegantSkeletonBlock Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `columns` | `Int` | Number of shimmering lines; coerced to at least 1 | `3` | No |
| `modifier` | `Modifier` | Modifier applied once to the column root | `Modifier` | No |
| `shape` | `Shape` | Clipping shape shared by every line | `RoundedCornerShape(ElegantRadius.xs)` | No |
| `spacing` | `Dp` | Vertical gap between lines | `ElegantSpacing.md` | No |
| `colors` | `ElegantSkeletonColors` | Theme-aware base and highlight colors | `ElegantSkeletonDefaults.colors()` | No |
| `lastLineWidthFraction` | `Float` | Width fraction of the last line; NaN falls back to 0.6f and clamps to 0.2f..1f | `0.6f` | No |

### ElegantSkeletonDefaults

| Member | Type | Description |
| --- | --- | --- |
| `AnimationDurationMillis` | `Int` | 1400ms duration of one shimmer sweep |
| `colors()` | `ElegantSkeletonColors` | Theme-aware Light/Dark base and highlight colors |

### ElegantSkeletonColors

`ElegantSkeletonColors` holds the resting `baseColor` and the moving `highlightColor` band. Start with `ElegantSkeletonDefaults.colors()` and use `copy(...)` only for a deliberate product-specific placeholder palette.

## Advanced Usage

### Profile Card Skeleton

Compose a circular avatar placeholder and shimmering lines to preview a profile card before real data arrives.

```kotlin
ElegantCard {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ElegantSpacing.xl),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantSkeleton(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
        )
        ElegantSkeletonBlock(
            columns = 2,
            modifier = Modifier.weight(1f),
        )
    }
}
```

### Custom Colors

```kotlin
val placeholderColors = ElegantSkeletonDefaults.colors().copy(
    baseColor = Color(0xFFEDEEF1),
    highlightColor = Color(0xFFF1F1F3),
)

ElegantSkeleton(
    modifier = Modifier
        .fillMaxWidth()
        .height(96.dp),
    colors = placeholderColors,
)
```

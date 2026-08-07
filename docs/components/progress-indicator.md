# Progress Indicator

`ElegantLinearProgressIndicator` and `ElegantCircularProgressIndicator` are refined non-interactive progress indicators. They render a full-width rounded linear track and a circular ring with a filled segment or arc, supporting both a determinate fraction in `0f..1f` and a continuous indeterminate sweep, with `progressBarRangeInfo` accessibility semantics and Light/Dark theme awareness.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=progress-indicator" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.progress.ElegantCircularProgressIndicator
import com.elegant.compose.ui.progress.ElegantLinearProgressIndicator
import com.elegant.compose.ui.progress.ElegantProgressColors
import com.elegant.compose.ui.progress.ElegantProgressDefaults
```

## Basic Usage

The indicators are driven by a `progress` fraction in `0f..1f`. The linear indicator fills the track from the logical start, while the circular indicator sweeps an arc clockwise from the top. Values outside the range are coerced, and NaN is treated as indeterminate.

```kotlin
var uploadProgress by remember { mutableStateOf(0.65f) }

ElegantLinearProgressIndicator(progress = uploadProgress)
ElegantCircularProgressIndicator(progress = uploadProgress)
```

## Component States

Passing `progress = null` switches the indicator to its indeterminate state: the linear indicator shows a segment sweeping left to right on an endless loop, and the circular indicator shows a rotating arc. Both states are non-interactive and announce their current fraction through `progressBarRangeInfo` semantics.

```kotlin
ElegantLinearProgressIndicator(progress = null)
ElegantCircularProgressIndicator(progress = null)
```

## Properties

### ElegantLinearProgressIndicator Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `progress` | `Float?` | Current progress fraction; null or NaN renders an indeterminate sweep | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the indicator root | `Modifier` | No |
| `colors` | `ElegantProgressColors` | Theme-aware indicator and track colors | `ElegantProgressDefaults.colors()` | No |

### ElegantCircularProgressIndicator Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `progress` | `Float?` | Current progress fraction; null or NaN renders an indeterminate rotation | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the indicator root | `Modifier` | No |
| `size` | `Dp` | Diameter of the circular ring | `40.dp` | No |
| `strokeWidth` | `Dp` | Stroke thickness of the circular ring | `4.dp` | No |
| `colors` | `ElegantProgressColors` | Theme-aware indicator and track colors | `ElegantProgressDefaults.colors()` | No |

### ElegantProgressDefaults

| Member | Type | Description |
| --- | --- | --- |
| `LinearTrackHeight` | `Dp` | 4dp height of the linear track |
| `CircularSize` | `Dp` | 40dp diameter of the circular ring |
| `CircularStrokeWidth` | `Dp` | 4dp ring stroke thickness |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `IndeterminateDurationMillis` | `Int` | 1200ms duration of one indeterminate sweep or rotation |
| `colors()` | `ElegantProgressColors` | Theme-aware Light/Dark colors |

### ElegantProgressColors

`ElegantProgressColors` contains the indicator and track colors used by both indicators. Start with `ElegantProgressDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Upload Row

Combine the linear and circular indicators with text to report an in-context upload: the ring mirrors the fraction shown by the track, and a label reads the current percentage.

```kotlin
var uploadProgress by remember { mutableStateOf(0.35f) }

Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
    verticalAlignment = Alignment.CenterVertically,
) {
    ElegantCircularProgressIndicator(progress = uploadProgress)
    Column(modifier = Modifier.weight(1f)) {
        Text(
            text = "report.pdf",
            style = ElegantTheme.typography.labelMedium,
        )
        ElegantLinearProgressIndicator(progress = uploadProgress)
    }
}
```

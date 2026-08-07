# Spinner

`ElegantSpinner` is a refined non-interactive loading indicator. It renders a centered rotating ring with an optional label below, always in the indeterminate state, with `progressBarRangeInfo` accessibility semantics and Light/Dark theme awareness.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=spinner" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.spinner.ElegantSpinner
import com.elegant.compose.ui.spinner.ElegantSpinnerColors
import com.elegant.compose.ui.spinner.ElegantSpinnerDefaults
```

## Basic Usage

The spinner always renders its indeterminate state: a 270-degree arc rotating clockwise on an endless loop. Pass a `label` to show loading text centered below the ring; pass `null` or omit it to show the ring alone.

```kotlin
ElegantSpinner()

ElegantSpinner(label = "Loading...")
```

## Component States

The spinner has no determinate state; it always signals continuous activity. The `size` and `strokeWidth` control the ring geometry, and a label communicates what is loading. The component is non-interactive and announces an indeterminate progress through `progressBarRangeInfo` semantics; the track behind the rotating arc is decorative.

```kotlin
ElegantSpinner(size = 24.dp, strokeWidth = 3.dp)
ElegantSpinner(size = 40.dp, strokeWidth = 4.dp)
ElegantSpinner(size = 56.dp, strokeWidth = 5.dp)
```

## Properties

### ElegantSpinner Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the spinner root | `Modifier` | No |
| `size` | `Dp` | Diameter of the spinner ring; non-positive or non-finite values fall back to the default | `40.dp` | No |
| `strokeWidth` | `Dp` | Stroke thickness of the spinner ring; non-positive or non-finite values fall back to the default | `4.dp` | No |
| `label` | `String?` | Optional loading label shown below the ring; null hides it | `null` | No |
| `colors` | `ElegantSpinnerColors` | Theme-aware indicator, track, and label colors | `ElegantSpinnerDefaults.colors()` | No |

### ElegantSpinnerDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Size` | `Dp` | 40dp diameter of the spinner ring |
| `StrokeWidth` | `Dp` | 4dp ring stroke thickness |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantSpinnerColors` | Theme-aware Light/Dark colors |

### ElegantSpinnerColors

`ElegantSpinnerColors` contains the indicator, track, and label colors used by the spinner. Start with `ElegantSpinnerDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Loading Card

Combine the spinner with skeleton placeholders to preview a loading card: the spinner reports the pending state, while shimmering lines preview the content that will fill the card.

```kotlin
ElegantCard(modifier = Modifier.fillMaxWidth()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ElegantSpacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ElegantSpinner(label = "Loading...")
        Spacer(modifier = Modifier.height(ElegantSpacing.lg))
        ElegantSkeletonBlock(columns = 3)
    }
}
```

# Meter

`ElegantMeter` is a refined non-interactive determinate meter. It renders a full-width rounded track with a filled portion proportional to the coerced value, a semantic fill tone that auto-resolves from usage zones (healthy, elevated, critical) or can be forced by the caller, an optional label below the bar, and `progressBarRangeInfo` accessibility semantics with Light/Dark theme awareness.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=meter" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.meter.ElegantMeter
import com.elegant.compose.ui.meter.ElegantMeterColors
import com.elegant.compose.ui.meter.ElegantMeterDefaults
import com.elegant.compose.ui.meter.ElegantMeterTone
```

## Basic Usage

The meter is driven by a `value` measured against `valueRange`, which defaults to `0f..1f`. The bar fills from the logical start, and the fill tone auto-resolves from the fraction: healthy at or below `highThreshold`, elevated above it up to 90%, and critical beyond 90%. Values outside the range are coerced, and NaN renders an empty track.

```kotlin
var storageUsed by remember { mutableStateOf(0.42f) }

ElegantMeter(
    value = storageUsed,
    label = "Storage used",
)
```

## Component States

The meter is always determinate and non-interactive, and announces its current fraction through `progressBarRangeInfo` semantics. The fill tone can be forced with the `tone` parameter; the `ElegantMeterTone` enum offers `Neutral`, `Positive`, `Warning`, and `Critical`.

```kotlin
ElegantMeter(value = 0.35f, tone = ElegantMeterTone.Positive)
ElegantMeter(value = 0.72f, tone = ElegantMeterTone.Warning)
ElegantMeter(value = 0.96f, tone = ElegantMeterTone.Critical)
```

## Properties

### ElegantMeter Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `value` | `Float` | Current value measured against `valueRange`; values outside the range are coerced | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the meter root | `Modifier` | No |
| `label` | `String?` | Optional label rendered below the bar; blank labels are omitted | `null` | No |
| `tone` | `ElegantMeterTone?` | Explicit semantic fill tone; null auto-resolves from the fraction | `null` | No |
| `valueRange` | `ClosedFloatingPointRange<Float>` | Range the value is measured against | `0f..1f` | No |
| `lowThreshold` | `Float` | Boundary between the low and medium usage zones | `0.33f` | No |
| `highThreshold` | `Float` | Boundary between the healthy and elevated usage zones; fractions at or below it resolve to `Positive` | `0.66f` | No |
| `colors` | `ElegantMeterColors` | Theme-aware track, fill, and text colors | `ElegantMeterDefaults.colors()` | No |

### ElegantMeterDefaults

| Member | Type | Description |
| --- | --- | --- |
| `TrackHeight` | `Dp` | 6dp height of the meter track |
| `LowThreshold` | `Float` | 0.33f boundary between the low and medium usage zones |
| `HighThreshold` | `Float` | 0.66f boundary between the healthy and elevated usage zones |
| `colors()` | `ElegantMeterColors` | Theme-aware Light/Dark colors |

### ElegantMeterColors

`ElegantMeterColors` contains the track color, the fill color for every `ElegantMeterTone`, and the content and label text colors. Start with `ElegantMeterDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

### ElegantMeterTone

`ElegantMeterTone` selects the semantic tone of the fill. Pass it to the `tone` parameter to force a tone, or leave the parameter null to resolve the tone from the fraction.

| Tone | Description |
| --- | --- |
| `Neutral` | Brand fill used when the meter carries no semantic meaning or the value is unknown |
| `Positive` | Healthy fill used while the value stays within normal operating ranges |
| `Warning` | Elevated fill used when the value approaches the configured limit |
| `Critical` | Critical fill used when the value reaches or exceeds the limit |

## Advanced Usage

### Storage Row

Measure a value against a custom range and report it next to the meter: the bar reflects `valueRange` directly, and the value text is composed by the caller.

```kotlin
var usedBytes by remember { mutableStateOf(63.4f) }
val capacity = 128f

Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
    verticalAlignment = Alignment.CenterVertically,
) {
    Column(modifier = Modifier.weight(1f)) {
        ElegantMeter(
            value = usedBytes,
            valueRange = 0f..capacity,
            label = "Storage",
        )
        Text(
            text = "$usedBytes GB of $capacity GB",
            style = ElegantTheme.typography.labelMedium,
        )
    }
}
```

package com.elegant.compose.ui.meter

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Semantic tone of the [ElegantMeter] fill.
 *
 * The tone is either forced by the caller through the `tone` parameter or auto-resolved from the
 * value fraction when it is null.
 */
public enum class ElegantMeterTone {
    /** Brand fill used when the meter carries no semantic meaning or the value is unknown. */
    Neutral,

    /** Healthy fill used while the value stays within normal operating ranges. */
    Positive,

    /** Elevated fill used when the value approaches the configured limit. */
    Warning,

    /** Critical fill used when the value reaches or exceeds the limit. */
    Critical,
}

/**
 * Theme-aware colors used by Elegant UI meters.
 *
 * @property trackColor unfilled track color.
 * @property fillColor fill color used by the [ElegantMeterTone.Neutral] tone.
 * @property positiveFillColor fill color used by the [ElegantMeterTone.Positive] tone.
 * @property warningFillColor fill color used by the [ElegantMeterTone.Warning] tone.
 * @property criticalFillColor fill color used by the [ElegantMeterTone.Critical] tone.
 * @property contentColor default text and icon color locally provided for content composed inside
 *   the meter; the label uses [labelColor] instead.
 * @property labelColor color of the meter label text.
 */
@Immutable
public data class ElegantMeterColors(
    val trackColor: Color,
    val fillColor: Color,
    val positiveFillColor: Color,
    val warningFillColor: Color,
    val criticalFillColor: Color,
    val contentColor: Color,
    val labelColor: Color,
)

/** Defaults and theme-aware factories shared by Elegant UI meter APIs. */
public object ElegantMeterDefaults {
    /** 6dp height of the meter track. */
    public val TrackHeight: Dp = 6.dp

    /** Default boundary between the low and medium usage zones. */
    public const val LowThreshold: Float = 0.33f

    /** Default boundary between the healthy and elevated usage zones. */
    public const val HighThreshold: Float = 0.66f

    /** Returns theme-aware meter colors for the active Light or Dark theme. */
    @Composable
    public fun colors(): ElegantMeterColors = resolveMeterColors(ElegantTheme.colors)
}

/**
 * Displays a determinate value along a full-width rounded track with a semantic fill tone.
 *
 * The bar renders [value] against [valueRange] with a filled portion proportional to the coerced
 * fraction; NaN renders an empty track. The fill tone auto-resolves from the fraction — healthy at
 * or below [highThreshold], elevated between it and 90%, critical beyond — unless [tone] forces an
 * explicit tone. An optional [label] renders below the bar, and the meter is non-interactive and
 * exposes `progressBarRangeInfo` semantics.
 *
 * @param value current value within [valueRange]; values outside the range are coerced.
 * @param modifier modifier applied once to the meter root.
 * @param label optional label rendered below the bar; blank labels are omitted.
 * @param tone explicit semantic fill tone; null resolves the tone from the fraction automatically.
 * @param valueRange range the value is measured against.
 * @param lowThreshold boundary between the low and medium usage zones; the auto tone treats both
 *   zones as healthy, so this boundary exists for callers that tier auxiliary content, such as
 *   value readouts, by usage zone.
 * @param highThreshold boundary between the healthy and elevated usage zones; fractions at or
 *   below it resolve to [ElegantMeterTone.Positive].
 * @param colors theme-aware track, fill, and text colors.
 */
@Composable
public fun ElegantMeter(
    value: Float,
    modifier: Modifier = Modifier,
    label: String? = null,
    tone: ElegantMeterTone? = null,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    lowThreshold: Float = ElegantMeterDefaults.LowThreshold,
    highThreshold: Float = ElegantMeterDefaults.HighThreshold,
    colors: ElegantMeterColors = ElegantMeterDefaults.colors(),
) {
    val fraction = meterFraction(value, valueRange)
    val resolvedTone = resolveMeterTone(
        fraction = fraction,
        explicit = tone,
        highThreshold = highThreshold,
    )
    val resolvedLabel = resolveLabel(label)
    val fillColor = when (resolvedTone) {
        ElegantMeterTone.Neutral -> colors.fillColor
        ElegantMeterTone.Positive -> colors.positiveFillColor
        ElegantMeterTone.Warning -> colors.warningFillColor
        ElegantMeterTone.Critical -> colors.criticalFillColor
    }

    Column(
        modifier = modifier.semantics {
            progressBarRangeInfo = ProgressBarRangeInfo(fraction, valueRange, 0)
        },
        horizontalAlignment = Alignment.Start,
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ElegantMeterDefaults.TrackHeight),
            ) {
                val strokeWidth = size.height
                val centerY = size.height / 2f
                drawLine(
                    color = colors.trackColor,
                    start = Offset(0f, centerY),
                    end = Offset(size.width, centerY),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round,
                )

                val fillEnd = fraction * size.width
                if (fillEnd > 0f) {
                    drawLine(
                        color = fillColor,
                        start = Offset(0f, centerY),
                        end = Offset(fillEnd, centerY),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round,
                    )
                }
            }

            if (resolvedLabel != null) {
                Spacer(Modifier.height(ElegantSpacing.xs))
                Text(
                    text = resolvedLabel,
                    style = ElegantTheme.typography.bodyMedium,
                    color = colors.labelColor,
                )
            }
        }
    }
}

/** Fraction of the track at which the auto-resolved tone turns critical. */
internal const val AutoCriticalFraction: Float = 0.9f

internal fun resolveMeterColors(themeColors: ElegantColors): ElegantMeterColors =
    ElegantMeterColors(
        trackColor = themeColors.borderDefault,
        fillColor = themeColors.interactivePrimary,
        positiveFillColor = themeColors.statusPositive,
        warningFillColor = themeColors.statusWarning,
        criticalFillColor = themeColors.statusCritical,
        contentColor = themeColors.textPrimary,
        labelColor = themeColors.textSecondary,
    )

internal fun resolveMeterTone(
    fraction: Float,
    explicit: ElegantMeterTone?,
    highThreshold: Float,
): ElegantMeterTone {
    if (explicit != null) return explicit
    if (fraction.isNaN()) return ElegantMeterTone.Neutral
    return when {
        fraction <= highThreshold -> ElegantMeterTone.Positive
        fraction <= AutoCriticalFraction -> ElegantMeterTone.Warning
        else -> ElegantMeterTone.Critical
    }
}

internal fun meterFraction(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
): Float {
    val span = valueRange.endInclusive - valueRange.start
    if (value.isNaN() || span <= 0f) return 0f
    val fraction = (value - valueRange.start) / span
    return if (fraction.isNaN()) 0f else fraction.coerceIn(0f, 1f)
}

internal fun resolveLabel(label: String?): String? = label?.takeIf { it.isNotBlank() }

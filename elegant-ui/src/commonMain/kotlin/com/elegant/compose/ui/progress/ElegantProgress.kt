package com.elegant.compose.ui.progress

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware colors used by Elegant UI progress indicators.
 *
 * @property indicatorColor filled segment or arc color.
 * @property trackColor empty track or ring color.
 */
@Immutable
public data class ElegantProgressColors(
    val indicatorColor: Color,
    val trackColor: Color,
)

/** Defaults and theme-aware factories shared by Elegant UI progress APIs. */
public object ElegantProgressDefaults {
    /** 4dp height of the linear track. */
    public val LinearTrackHeight: Dp = 4.dp

    /** 40dp diameter of the circular ring. */
    public val CircularSize: Dp = 40.dp

    /** 4dp stroke thickness of the circular ring. */
    public val CircularStrokeWidth: Dp = 4.dp

    /** Standard 160ms state-transition duration. */
    public val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** 1200ms duration of one full indeterminate sweep or rotation. */
    public val IndeterminateDurationMillis: Int = 1200

    /** Returns theme-aware progress colors for the active Light or Dark theme. */
    @Composable
    public fun colors(): ElegantProgressColors = resolveProgressColors(ElegantTheme.colors)
}

/**
 * Displays progress along a full-width rounded track.
 *
 * A [progress] fraction in `0f..1f` renders a determinate indicator that fills the track from the
 * logical start; values outside the range are coerced, and NaN is treated as indeterminate. A null
 * [progress] renders a 30%-wide segment sweeping left to right on an endless loop. The indicator
 * is non-interactive and exposes `progressBarRangeInfo` semantics with the resolved fraction.
 *
 * @param progress current progress fraction, or null for indeterminate.
 * @param modifier modifier applied once to the indicator root.
 * @param colors theme-aware indicator and track colors.
 */
@Composable
public fun ElegantLinearProgressIndicator(
    progress: Float?,
    modifier: Modifier = Modifier,
    colors: ElegantProgressColors = ElegantProgressDefaults.colors(),
) {
    val resolvedProgress = clampProgress(progress)
    val translationFraction = if (resolvedProgress == null) {
        val transition = rememberInfiniteTransition(label = "ElegantLinearProgressTransition")
        transition
            .animateFloat(
                initialValue = -1f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = ElegantProgressDefaults.IndeterminateDurationMillis,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Restart,
                ),
                label = "ElegantLinearProgressTranslation",
            )
            .value
    } else {
        0f
    }

    Canvas(
        modifier = modifier
            .then(progressSemanticModifier(resolvedProgress))
            .fillMaxWidth()
            .height(ElegantProgressDefaults.LinearTrackHeight),
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

        val indicatorStart: Float
        val indicatorEnd: Float
        if (resolvedProgress == null) {
            val segmentWidth = size.width * LinearIndicatorSegmentFraction
            indicatorStart = translationFraction * size.width
            indicatorEnd = indicatorStart + segmentWidth
        } else {
            indicatorStart = 0f
            indicatorEnd = resolvedProgress * size.width
        }
        if (indicatorEnd > indicatorStart) {
            drawLine(
                color = colors.indicatorColor,
                start = Offset(indicatorStart, centerY),
                end = Offset(indicatorEnd, centerY),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round,
            )
        }
    }
}

/**
 * Displays progress along a circular ring.
 *
 * A [progress] fraction in `0f..1f` renders a determinate arc that sweeps clockwise from the top;
 * values outside the range are coerced, and NaN is treated as indeterminate. A null [progress]
 * renders a 270-degree arc rotating clockwise on an endless loop. The indicator is non-interactive
 * and exposes `progressBarRangeInfo` semantics with the resolved fraction.
 *
 * @param progress current progress fraction, or null for indeterminate.
 * @param modifier modifier applied once to the indicator root.
 * @param size diameter of the circular ring.
 * @param strokeWidth stroke thickness of the circular ring.
 * @param colors theme-aware indicator and track colors.
 */
@Composable
public fun ElegantCircularProgressIndicator(
    progress: Float?,
    modifier: Modifier = Modifier,
    size: Dp = ElegantProgressDefaults.CircularSize,
    strokeWidth: Dp = ElegantProgressDefaults.CircularStrokeWidth,
    colors: ElegantProgressColors = ElegantProgressDefaults.colors(),
) {
    val resolvedProgress = clampProgress(progress)
    val ringDiameter = size
    val rotation = if (resolvedProgress == null) {
        val transition = rememberInfiniteTransition(label = "ElegantCircularProgressTransition")
        transition
            .animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = ElegantProgressDefaults.IndeterminateDurationMillis,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Restart,
                ),
                label = "ElegantCircularProgressRotation",
            )
            .value
    } else {
        0f
    }

    Canvas(
        modifier = modifier
            .then(progressSemanticModifier(resolvedProgress))
            .size(ringDiameter)
            .graphicsLayer { rotationZ = rotation },
    ) {
        val canvasSize = this.size
        val strokePx = strokeWidth.toPx()
        val ringRadius = (minOf(canvasSize.width, canvasSize.height) - strokePx) / 2f
        val ringTopLeft = Offset(
            x = canvasSize.width / 2f - ringRadius,
            y = canvasSize.height / 2f - ringRadius,
        )
        val ringSize = Size(ringRadius * 2f, ringRadius * 2f)
        val strokeStyle = Stroke(
            width = strokePx,
            cap = StrokeCap.Round,
        )

        drawArc(
            color = colors.trackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = ringTopLeft,
            size = ringSize,
            style = strokeStyle,
        )
        drawArc(
            color = colors.indicatorColor,
            startAngle = -90f,
            sweepAngle = if (resolvedProgress == null) {
                IndeterminateArcSweepDegrees
            } else {
                circularSweep(resolvedProgress)
            },
            useCenter = false,
            topLeft = ringTopLeft,
            size = ringSize,
            style = strokeStyle,
        )
    }
}

/** Width of the sweeping indeterminate linear segment relative to the track. */
internal const val LinearIndicatorSegmentFraction: Float = 0.3f

/** Sweep of the rotating indeterminate circular arc in degrees. */
internal const val IndeterminateArcSweepDegrees: Float = 270f

internal fun resolveProgressColors(themeColors: ElegantColors): ElegantProgressColors =
    ElegantProgressColors(
        indicatorColor = themeColors.interactivePrimary,
        trackColor = themeColors.borderDefault,
    )

internal fun clampProgress(progress: Float?): Float? = when {
    progress == null || progress.isNaN() -> null
    else -> progress.coerceIn(0f, 1f)
}

internal fun circularSweep(progress: Float): Float = 360f * (clampProgress(progress) ?: 0f)

private fun progressSemanticModifier(progress: Float?): Modifier = Modifier.semantics {
    progressBarRangeInfo = ProgressBarRangeInfo(progress ?: 0f, 0f..1f, 0)
}

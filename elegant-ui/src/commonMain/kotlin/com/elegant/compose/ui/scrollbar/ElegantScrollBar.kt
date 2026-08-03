package com.elegant.compose.ui.scrollbar

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantTheme

/** Scroll directions tracked by [ElegantScrollBar]. */
public enum class ElegantScrollBarOrientation {
    /** Runs a vertical track with the thumb travelling top to bottom. */
    Vertical,

    /** Runs a horizontal track with the thumb travelling from the start edge. */
    Horizontal,
}

/**
 * Theme-aware colors used by [ElegantScrollBar].
 *
 * @property thumbColor color of the scroll-position thumb.
 * @property trackColor color of the track behind the thumb.
 */
@Immutable
public data class ElegantScrollBarColors(
    val thumbColor: Color,
    val trackColor: Color,
)

/** Defaults and the theme-aware factory shared by the Elegant UI scroll-bar API. */
public object ElegantScrollBarDefaults {
    /** Width of the scroll-position thumb. */
    public val ThumbWidth: Dp = 4.dp

    /** Width of the track behind the thumb. */
    public val TrackWidth: Dp = 8.dp

    /** Smallest fraction of the track the thumb can occupy. */
    public const val MinThumbFraction: Float = 0.1f

    /** Returns theme-aware scroll-bar colors for the active light or dark theme. */
    @Composable
    public fun colors(): ElegantScrollBarColors = resolveScrollBarColors(ElegantTheme.colors)
}

/**
 * Overlays a slim, non-interactive scroll-position indicator on scrollable content.
 *
 * Place this overlay above the scrollable content, for example as the last child of a `Box`
 * wrapping a `verticalScroll` column, aligned to the end edge:
 * `Box { scrollableContent; ElegantScrollBar(state, Modifier.align(Alignment.CenterEnd)) }`.
 *
 * A rounded track spans the full length the overlay is given and a thinner rounded thumb travels
 * along it. The thumb length is the track length scaled by the viewport fraction — the ratio of
 * the track length to [ScrollState.maxValue] — clamped to at least
 * [ElegantScrollBarDefaults.MinThumbFraction] of the track. The thumb offset is the scrolled
 * fraction of the remaining track length, so the thumb sits at the leading edge at the start and
 * at the trailing edge at the end. When the content fits and cannot scroll, the thumb fills the
 * track at the leading edge. Both [ScrollState.value] and [ScrollState.maxValue] are observable,
 * so the indicator updates while [state] scrolls.
 *
 * The overlay sizes itself to the track, is purely decorative, and never intercepts scroll
 * gestures.
 *
 * @param state scroll state driving the thumb position and length.
 * @param modifier modifier applied once to the overlay root; the track is centered on its cross
 *   axis.
 * @param orientation scroll direction the indicator tracks.
 * @param colors theme-aware thumb and track colors.
 */
@Composable
public fun ElegantScrollBar(
    state: ScrollState,
    modifier: Modifier = Modifier,
    orientation: ElegantScrollBarOrientation = ElegantScrollBarOrientation.Vertical,
    colors: ElegantScrollBarColors = ElegantScrollBarDefaults.colors(),
) {
    val vertical = orientation == ElegantScrollBarOrientation.Vertical

    BoxWithConstraints(
        modifier = modifier
            .clearAndSetSemantics { },
    ) {
        val density = LocalDensity.current
        val trackLengthPx = with(density) {
            if (vertical) maxHeight.toPx() else maxWidth.toPx()
        }
        val maxValue = state.maxValue.toFloat()
        val currentScrollFraction = scrollFraction(
            offset = state.value.toFloat(),
            maxValue = maxValue,
        )
        val currentViewportFraction = viewportFraction(
            viewportLength = trackLengthPx,
            contentLength = maxValue,
        )
        val currentThumbLength = thumbLength(
            trackLength = trackLengthPx,
            viewportFraction = currentViewportFraction,
            minFraction = ElegantScrollBarDefaults.MinThumbFraction,
        )
        val currentThumbOffset = thumbOffset(
            trackLength = trackLengthPx,
            thumbLength = currentThumbLength,
            scrollFraction = currentScrollFraction,
        )
        val thumbLengthDp = with(density) { currentThumbLength.toDp() }
        val thumbOffsetDp = with(density) { currentThumbOffset.toDp() }

        if (vertical) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxHeight()
                    .width(ElegantScrollBarDefaults.TrackWidth)
                    .clip(RoundedCornerShape(ElegantRadius.full))
                    .background(colors.trackColor),
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = thumbOffsetDp)
                        .width(ElegantScrollBarDefaults.ThumbWidth)
                        .height(thumbLengthDp)
                        .clip(RoundedCornerShape(ElegantRadius.full))
                        .background(colors.thumbColor),
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(ElegantScrollBarDefaults.TrackWidth)
                    .clip(RoundedCornerShape(ElegantRadius.full))
                    .background(colors.trackColor),
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(x = thumbOffsetDp)
                        .width(thumbLengthDp)
                        .height(ElegantScrollBarDefaults.ThumbWidth)
                        .clip(RoundedCornerShape(ElegantRadius.full))
                        .background(colors.thumbColor),
                )
            }
        }
    }
}

/** Resolves the scroll-bar colors from the active theme's semantic roles. */
internal fun resolveScrollBarColors(themeColors: ElegantColors): ElegantScrollBarColors =
    ElegantScrollBarColors(
        thumbColor = themeColors.textTertiary,
        trackColor = themeColors.borderDefault,
    )

/**
 * Maps the scrolled [offset] onto the 0..1 position of the scrollable [maxValue] span.
 *
 * Returns zero when the content cannot scroll or any input is NaN; otherwise returns
 * [offset] / [maxValue] clamped to the 0..1 range.
 */
internal fun scrollFraction(offset: Float, maxValue: Float): Float = when {
    offset.isNaN() || maxValue.isNaN() || maxValue <= 0f -> 0f
    else -> (offset / maxValue).coerceIn(minimumValue = 0f, maximumValue = 1f)
}

/**
 * Ratio of the visible [viewportLength] to the scrollable [contentLength], used to size the
 * thumb.
 *
 * Returns 1f when the content cannot scroll or any input is NaN; otherwise returns
 * [viewportLength] / [contentLength] clamped to the 0..1 range.
 */
internal fun viewportFraction(viewportLength: Float, contentLength: Float): Float = when {
    viewportLength.isNaN() || contentLength.isNaN() || contentLength <= 0f -> 1f
    else -> (viewportLength / contentLength).coerceIn(minimumValue = 0f, maximumValue = 1f)
}

/**
 * Length of the thumb for a [trackLength] track, at least [minFraction] of the track.
 *
 * Returns zero when the track has no positive length or any input is NaN. Both the
 * [viewportFraction] and [minFraction] inputs are clamped to the 0..1 range, so the result never
 * exceeds the track length.
 */
internal fun thumbLength(trackLength: Float, viewportFraction: Float, minFraction: Float): Float {
    if (trackLength.isNaN() || viewportFraction.isNaN() || minFraction.isNaN() || trackLength <= 0f) {
        return 0f
    }
    return trackLength * viewportFraction
        .coerceIn(minimumValue = 0f, maximumValue = 1f)
        .coerceAtLeast(minFraction.coerceIn(minimumValue = 0f, maximumValue = 1f))
}

/**
 * Leading-edge position of a [thumbLength] thumb inside a [trackLength] track.
 *
 * Returns zero when the thumb fills the track, the track has no positive length, or any input is
 * NaN; otherwise returns [scrollFraction] of the remaining track length clamped to the range the
 * thumb can travel.
 */
internal fun thumbOffset(trackLength: Float, thumbLength: Float, scrollFraction: Float): Float {
    if (
        trackLength.isNaN() ||
        thumbLength.isNaN() ||
        scrollFraction.isNaN() ||
        trackLength <= 0f ||
        thumbLength >= trackLength
    ) {
        return 0f
    }
    val travel = trackLength - thumbLength
    return (scrollFraction * travel).coerceIn(minimumValue = 0f, maximumValue = travel)
}

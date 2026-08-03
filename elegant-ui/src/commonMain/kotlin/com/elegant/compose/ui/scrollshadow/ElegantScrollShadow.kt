package com.elegant.compose.ui.scrollshadow

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantTheme

/** Scroll directions tracked by [ElegantScrollShadow]. */
public enum class ElegantScrollShadowOrientation {
    /** Fades the top and bottom edges of vertically scrolling content. */
    Vertical,

    /** Fades the start and end edges of horizontally scrolling content. */
    Horizontal,
}

/**
 * Theme-aware colors used by [ElegantScrollShadow].
 *
 * @property shadowColor color of the edge fade; drawn with an alpha that grows with the distance
 *   the content can still scroll.
 */
@Immutable
public data class ElegantScrollShadowColors(
    val shadowColor: Color,
)

/** Defaults and the theme-aware factory shared by the Elegant UI scroll-shadow API. */
public object ElegantScrollShadowDefaults {
    /** Height of the fade band drawn on each scrollable edge. */
    public val ShadowHeight: Dp = 24.dp

    /** Maximum alpha the fade reaches once an edge is at least one fade band away. */
    public const val MaxAlpha: Float = 0.35f

    /** Returns theme-aware scroll-shadow colors for the active light or dark theme. */
    @Composable
    public fun colors(): ElegantScrollShadowColors = resolveScrollShadowColors(ElegantTheme.colors)
}

/**
 * Fades the leading and trailing edges of scrollable content while more content is available.
 *
 * Place this overlay above the scrollable content, for example as the last child of a `Box`
 * wrapping a `verticalScroll` column: `Box { scrollableContent; ElegantScrollShadow(state) }`.
 * The overlay fills the space it is given, is purely decorative, and never intercepts scroll
 * gestures.
 *
 * The leading edge fades while the content has scrolled away from it and the trailing edge fades
 * while the content can still scroll forward. Each fade starts transparent and grows to
 * [ElegantScrollShadowDefaults.MaxAlpha] as the remaining scrollable distance grows from zero to
 * [ElegantScrollShadowDefaults.ShadowHeight], then stays there for larger distances. Both
 * [ScrollState.value] and [ScrollState.maxValue] are observable, so the overlay updates while
 * [state] scrolls.
 *
 * @param state scroll state driving the edge fades.
 * @param modifier modifier applied once to the overlay root.
 * @param colors theme-aware shadow color.
 * @param orientation scroll direction the shadows track.
 */
@Composable
public fun ElegantScrollShadow(
    state: ScrollState,
    modifier: Modifier = Modifier,
    colors: ElegantScrollShadowColors = ElegantScrollShadowDefaults.colors(),
    orientation: ElegantScrollShadowOrientation = ElegantScrollShadowOrientation.Vertical,
) {
    val shadowHeightPx = with(LocalDensity.current) {
        ElegantScrollShadowDefaults.ShadowHeight.toPx()
    }
    val offset = state.value.toFloat()
    val maxValue = state.maxValue.toFloat()
    val leading = leadingAlpha(
        offset = offset,
        maxValue = maxValue,
        heightPx = shadowHeightPx,
        maxAlpha = ElegantScrollShadowDefaults.MaxAlpha,
    )
    val trailing = trailingAlpha(
        offset = offset,
        maxValue = maxValue,
        heightPx = shadowHeightPx,
        maxAlpha = ElegantScrollShadowDefaults.MaxAlpha,
    )
    val shadowColor = colors.shadowColor

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .clearAndSetSemantics { },
    ) {
        when (orientation) {
            ElegantScrollShadowOrientation.Vertical -> {
                if (leading > 0f) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0f to shadowColor.copy(alpha = leading),
                                1f to Color.Transparent,
                            ),
                        ),
                        topLeft = Offset.Zero,
                        size = Size(size.width, shadowHeightPx),
                    )
                }
                if (trailing > 0f) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0f to Color.Transparent,
                                1f to shadowColor.copy(alpha = trailing),
                            ),
                        ),
                        topLeft = Offset(0f, size.height - shadowHeightPx),
                        size = Size(size.width, shadowHeightPx),
                    )
                }
            }

            ElegantScrollShadowOrientation.Horizontal -> {
                if (leading > 0f) {
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colorStops = arrayOf(
                                0f to shadowColor.copy(alpha = leading),
                                1f to Color.Transparent,
                            ),
                        ),
                        topLeft = Offset.Zero,
                        size = Size(shadowHeightPx, size.height),
                    )
                }
                if (trailing > 0f) {
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colorStops = arrayOf(
                                0f to Color.Transparent,
                                1f to shadowColor.copy(alpha = trailing),
                            ),
                        ),
                        topLeft = Offset(size.width - shadowHeightPx, 0f),
                        size = Size(shadowHeightPx, size.height),
                    )
                }
            }
        }
    }
}

/** Resolves the scroll-shadow color from the active theme's semantic roles. */
internal fun resolveScrollShadowColors(themeColors: ElegantColors): ElegantScrollShadowColors =
    ElegantScrollShadowColors(
        shadowColor = themeColors.textPrimary,
    )

/**
 * Whether the leading-edge fade is visible: the content has scrolled away from the leading edge.
 *
 * The [maxValue] parameter mirrors [shouldDrawTrailing] for a symmetric contract.
 */
internal fun shouldDrawLeading(offset: Float, maxValue: Float): Boolean = offset > 0f

/**
 * Whether the trailing-edge fade is visible: the content can still scroll forward.
 *
 * The [offset] parameter mirrors [shouldDrawLeading] for a symmetric contract.
 */
internal fun shouldDrawTrailing(offset: Float, maxValue: Float): Boolean = maxValue > offset

/**
 * Alpha of the leading-edge fade, proportional to the distance scrolled away from the leading
 * edge.
 *
 * Returns zero when the content cannot scroll, any input is NaN, the fade band has no positive
 * height, or [maxAlpha] is not positive. Otherwise returns [maxAlpha] scaled by the scrolled
 * distance clamped to one fade band, so the result never leaves the 0..maxAlpha range.
 */
internal fun leadingAlpha(
    offset: Float,
    maxValue: Float,
    heightPx: Float,
    maxAlpha: Float,
): Float = if (
    offset.isNaN() ||
    maxValue.isNaN() ||
    heightPx.isNaN() ||
    heightPx <= 0f ||
    maxAlpha.isNaN() ||
    maxAlpha <= 0f ||
    !shouldDrawLeading(offset, maxValue)
) {
    0f
} else {
    maxAlpha * (offset / heightPx).coerceIn(minimumValue = 0f, maximumValue = 1f)
}

/**
 * Alpha of the trailing-edge fade, proportional to the distance the content can still scroll
 * forward.
 *
 * Returns zero when the content cannot scroll, any input is NaN, the fade band has no positive
 * height, or [maxAlpha] is not positive. Otherwise returns [maxAlpha] scaled by the remaining
 * distance clamped to one fade band, so the result never leaves the 0..maxAlpha range.
 */
internal fun trailingAlpha(
    offset: Float,
    maxValue: Float,
    heightPx: Float,
    maxAlpha: Float,
): Float = if (
    offset.isNaN() ||
    maxValue.isNaN() ||
    heightPx.isNaN() ||
    heightPx <= 0f ||
    maxAlpha.isNaN() ||
    maxAlpha <= 0f ||
    !shouldDrawTrailing(offset, maxValue)
) {
    0f
} else {
    maxAlpha * ((maxValue - offset) / heightPx).coerceIn(minimumValue = 0f, maximumValue = 1f)
}

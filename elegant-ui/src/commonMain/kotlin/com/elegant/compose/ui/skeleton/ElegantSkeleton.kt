package com.elegant.compose.ui.skeleton

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware colors used by Elegant UI skeleton placeholders.
 *
 * @property baseColor resting fill of the placeholder surface.
 * @property highlightColor moving shimmer band swept across the placeholder.
 */
@Immutable
public data class ElegantSkeletonColors(
    val baseColor: Color,
    val highlightColor: Color,
)

/** Defaults and the theme-aware factory shared by Elegant UI skeleton APIs. */
public object ElegantSkeletonDefaults {
    /** Duration in milliseconds of one full shimmer sweep. */
    public const val AnimationDurationMillis: Int = 1400

    /** Returns theme-aware skeleton colors for the active light or dark theme. */
    @Composable
    public fun colors(): ElegantSkeletonColors = resolveSkeletonColors(ElegantTheme.colors)
}

/**
 * A shimmering placeholder surface that previews content before it loads.
 *
 * The placeholder is decorative by default: it clears its semantics so screen readers skip it
 * while real content is loading. The caller owns the size; the skeleton fills whatever width and
 * height [modifier] provides. A highlight band continuously sweeps across the [colors.baseColor]
 * surface, clipped to [shape].
 *
 * @param modifier modifier applied once to the skeleton root.
 * @param shape clipping shape of the placeholder.
 * @param colors theme-aware base and highlight colors.
 */
@Composable
public fun ElegantSkeleton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(ElegantRadius.sm),
    colors: ElegantSkeletonColors = ElegantSkeletonDefaults.colors(),
) {
    val shimmerTransition = rememberInfiniteTransition(label = "ElegantSkeletonShimmer")
    val shimmerProgress by shimmerTransition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = ElegantSkeletonDefaults.AnimationDurationMillis,
                easing = LinearEasing,
            ),
        ),
        label = "ElegantSkeletonShimmerProgress",
    )

    Box(
        modifier = modifier
            .clip(shape)
            .clearAndSetSemantics { }
            .drawWithCache {
                val baseColor = colors.baseColor
                val highlightColor = colors.highlightColor

                onDrawBehind {
                    drawRect(color = baseColor)
                    val bandStart = (shimmerProgress - 1f) * size.width
                    val bandEnd = (shimmerProgress + 1f) * size.width
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.Transparent, highlightColor, Color.Transparent),
                            start = Offset(bandStart, 0f),
                            end = Offset(bandEnd, 0f),
                        ),
                    )
                }
            },
    )
}

/**
 * A convenience column of shimmering text-like lines.
 *
 * Renders [columns] lines, each a rounded [ElegantSkeleton] of fixed height; the last line is
 * shortened to [lastLineWidthFraction] so paragraphs read like real copy. The value is not
 * interactive and inherits the decorative semantics of its lines.
 *
 * @param columns number of shimmering lines; values below one coerce to one.
 * @param modifier modifier applied once to the column root.
 * @param shape clipping shape shared by every line.
 * @param spacing vertical gap between lines.
 * @param colors theme-aware base and highlight colors shared by every line.
 * @param lastLineWidthFraction width fraction of the last line; NaN falls back to 0.6f and the
 *   result is clamped to the 0.2f..1f range.
 */
@Composable
public fun ElegantSkeletonBlock(
    columns: Int = DefaultSkeletonColumns,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(ElegantRadius.xs),
    spacing: Dp = ElegantSpacing.md,
    colors: ElegantSkeletonColors = ElegantSkeletonDefaults.colors(),
    lastLineWidthFraction: Float = DefaultSkeletonLastLineWidthFraction,
) {
    val lineCount = resolveBlockColumns(columns)
    val resolvedWidthFraction = resolveLastLineWidth(lastLineWidthFraction)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing),
    ) {
        for (index in 0 until lineCount) {
            val widthFraction = if (index == lineCount - 1) resolvedWidthFraction else 1f
            ElegantSkeleton(
                modifier = Modifier
                    .fillMaxWidth(widthFraction)
                    .height(SkeletonBlockLineHeight),
                shape = shape,
                colors = colors,
            )
        }
    }
}

/** Default number of lines rendered by [ElegantSkeletonBlock]. */
internal const val DefaultSkeletonColumns: Int = 3

/** Lower bound applied to the [ElegantSkeletonBlock.columns] parameter. */
internal const val MinimumSkeletonColumns: Int = 1

/** Default width fraction of the shortened last line in [ElegantSkeletonBlock]. */
internal const val DefaultSkeletonLastLineWidthFraction: Float = 0.6f

/** Lower bound applied to the [ElegantSkeletonBlock.lastLineWidthFraction] parameter. */
internal const val MinimumSkeletonLastLineWidthFraction: Float = 0.2f

/** Height of every line rendered by [ElegantSkeletonBlock]. */
internal val SkeletonBlockLineHeight: Dp = 12.dp

internal fun resolveSkeletonColors(themeColors: ElegantColors): ElegantSkeletonColors =
    ElegantSkeletonColors(
        baseColor = themeColors.surfaceSunken,
        highlightColor = themeColors.backgroundSubtle,
    )

internal fun resolveBlockColumns(columns: Int): Int = columns.coerceAtLeast(MinimumSkeletonColumns)

internal fun resolveLastLineWidth(widthFraction: Float): Float =
    if (widthFraction.isNaN()) {
        DefaultSkeletonLastLineWidthFraction
    } else {
        widthFraction.coerceIn(
            minimumValue = MinimumSkeletonLastLineWidthFraction,
            maximumValue = 1f,
        )
    }

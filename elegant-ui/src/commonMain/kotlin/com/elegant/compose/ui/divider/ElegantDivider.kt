package com.elegant.compose.ui.divider

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

/** Layout orientations supported by [ElegantDivider]. */
public enum class ElegantDividerOrientation {
    /** Separates vertically stacked content and occupies the available width. */
    Horizontal,

    /** Separates horizontally arranged content and occupies the available height. */
    Vertical,
}

/** Stroke treatments supported by Elegant UI dividers. */
public enum class ElegantDividerStyle {
    /** A continuous separator for standard content boundaries. */
    Solid,

    /** A restrained segmented separator for provisional or secondary boundaries. */
    Dashed,
}

/** Semantic emphasis levels supported by Elegant UI dividers. */
public enum class ElegantDividerEmphasis {
    /** Quiet separation within a related content group. */
    Subtle,

    /** Clearer separation between distinct regions. */
    Strong,
}

/** Logical label positions supported by [ElegantLabeledDivider]. */
public enum class ElegantDividerLabelPosition {
    /** Places the label at the logical start edge and the line after it. */
    Start,

    /** Centers the label between two equal line segments. */
    Center,

    /** Places the label at the logical end edge and the line before it. */
    End,
}

/**
 * Theme-aware colors used by Elegant UI dividers.
 *
 * @property lineColor separator stroke color.
 * @property contentColor locally provided color for labeled-divider content.
 */
@Immutable
public data class ElegantDividerColors(
    val lineColor: Color,
    val contentColor: Color,
)

/** Defaults and theme-aware factories shared by Elegant UI divider APIs. */
public object ElegantDividerDefaults {
    /** Default hairline thickness. */
    public val Thickness: Dp = 1.dp

    /** Horizontal breathing room around labeled-divider content. */
    public val LabelGap: Dp = ElegantSpacing.lg

    /** Length of each segment in the fixed dashed treatment. */
    public val DashLength: Dp = ElegantSpacing.md

    /** Space between segments in the fixed dashed treatment. */
    public val DashGap: Dp = ElegantSpacing.xs

    /** Returns theme-aware divider colors for [emphasis]. */
    @Composable
    public fun colors(
        emphasis: ElegantDividerEmphasis = ElegantDividerEmphasis.Subtle,
    ): ElegantDividerColors = resolveDividerColors(
        emphasis = emphasis,
        themeColors = ElegantTheme.colors,
    )
}

/**
 * Separates adjacent content with a horizontal or vertical visual boundary.
 *
 * The divider is decorative by default and does not add a semantics node. Supply a localized,
 * non-blank [contentDescription] only when the boundary communicates information that is absent
 * from nearby content. A vertical divider needs a bounded height from its parent or [modifier].
 *
 * Non-positive or non-finite [thickness] values safely fall back to
 * [ElegantDividerDefaults.Thickness].
 *
 * @param modifier modifier applied once to the divider root.
 * @param contentDescription optional localized semantic description.
 * @param orientation horizontal or vertical layout direction.
 * @param style continuous or segmented stroke treatment.
 * @param emphasis semantic visual emphasis.
 * @param colors theme-aware line and content colors.
 * @param thickness visible stroke thickness.
 */
@Composable
public fun ElegantDivider(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    orientation: ElegantDividerOrientation = ElegantDividerOrientation.Horizontal,
    style: ElegantDividerStyle = ElegantDividerStyle.Solid,
    emphasis: ElegantDividerEmphasis = ElegantDividerEmphasis.Subtle,
    colors: ElegantDividerColors = ElegantDividerDefaults.colors(emphasis),
    thickness: Dp = ElegantDividerDefaults.Thickness,
) {
    DividerLine(
        modifier = modifier.then(dividerSemanticModifier(contentDescription)),
        orientation = orientation,
        style = style,
        color = colors.lineColor,
        thickness = resolveDividerThickness(thickness),
    )
}

/**
 * Separates vertically stacked content with a logical start, center, or end label.
 *
 * Labels are intentionally horizontal-only so vertical text behavior cannot become ambiguous.
 * With a null [contentDescription], [content] keeps its own semantics. A non-blank description
 * replaces descendant semantics with one localized label, while an empty description makes the
 * complete divider decorative.
 *
 * Long custom content may wrap when space is constrained; line segments collapse before content
 * is clipped. Start and end positions mirror automatically in RTL.
 *
 * @param modifier modifier applied once to the labeled-divider root.
 * @param contentDescription optional localized semantic override; null preserves content semantics.
 * @param labelPosition logical placement of the label between divider segments.
 * @param style continuous or segmented stroke treatment.
 * @param emphasis semantic visual emphasis.
 * @param colors theme-aware line and label colors.
 * @param thickness visible stroke thickness.
 * @param labelGap breathing room on both sides of the label.
 * @param content label text, icon, or custom content.
 */
@Composable
public fun ElegantLabeledDivider(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    labelPosition: ElegantDividerLabelPosition = ElegantDividerLabelPosition.Center,
    style: ElegantDividerStyle = ElegantDividerStyle.Solid,
    emphasis: ElegantDividerEmphasis = ElegantDividerEmphasis.Subtle,
    colors: ElegantDividerColors = ElegantDividerDefaults.colors(emphasis),
    thickness: Dp = ElegantDividerDefaults.Thickness,
    labelGap: Dp = ElegantDividerDefaults.LabelGap,
    content: @Composable () -> Unit,
) {
    val weights = dividerLineWeightsFor(labelPosition)
    val resolvedThickness = resolveDividerThickness(thickness)
    val resolvedLabelGap = resolveDividerLabelGap(labelGap)

    Row(
        modifier = modifier
            .then(dividerSemanticModifier(contentDescription))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (weights.before > 0f) {
            DividerLine(
                modifier = Modifier.weight(weights.before),
                orientation = ElegantDividerOrientation.Horizontal,
                style = style,
                color = colors.lineColor,
                thickness = resolvedThickness,
            )
        }

        Box(
            modifier = Modifier.padding(horizontal = resolvedLabelGap),
            contentAlignment = Alignment.Center,
        ) {
            CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
                ProvideTextStyle(ElegantTheme.typography.labelMedium) {
                    content()
                }
            }
        }

        if (weights.after > 0f) {
            DividerLine(
                modifier = Modifier.weight(weights.after),
                orientation = ElegantDividerOrientation.Horizontal,
                style = style,
                color = colors.lineColor,
                thickness = resolvedThickness,
            )
        }
    }
}

@Composable
private fun DividerLine(
    modifier: Modifier,
    orientation: ElegantDividerOrientation,
    style: ElegantDividerStyle,
    color: Color,
    thickness: Dp,
) {
    val sizeModifier = when (orientation) {
        ElegantDividerOrientation.Horizontal -> Modifier
            .fillMaxWidth()
            .height(thickness)

        ElegantDividerOrientation.Vertical -> Modifier
            .width(thickness)
            .fillMaxHeight()
    }

    Box(
        modifier = modifier
            .then(sizeModifier)
            .drawWithCache {
                val dashPathEffect = if (style == ElegantDividerStyle.Dashed) {
                    PathEffect.dashPathEffect(
                        intervals = floatArrayOf(
                            ElegantDividerDefaults.DashLength.toPx(),
                            ElegantDividerDefaults.DashGap.toPx(),
                        ),
                    )
                } else {
                    null
                }

                onDrawBehind {
                    when (style) {
                        ElegantDividerStyle.Solid -> drawRect(color = color)
                        ElegantDividerStyle.Dashed -> {
                            val strokeWidth = when (orientation) {
                                ElegantDividerOrientation.Horizontal -> size.height
                                ElegantDividerOrientation.Vertical -> size.width
                            }
                            val start = when (orientation) {
                                ElegantDividerOrientation.Horizontal ->
                                    Offset(0f, size.height / 2f)

                                ElegantDividerOrientation.Vertical ->
                                    Offset(size.width / 2f, 0f)
                            }
                            val end = when (orientation) {
                                ElegantDividerOrientation.Horizontal ->
                                    Offset(size.width, size.height / 2f)

                                ElegantDividerOrientation.Vertical ->
                                    Offset(size.width / 2f, size.height)
                            }
                            drawLine(
                                color = color,
                                start = start,
                                end = end,
                                strokeWidth = strokeWidth,
                                cap = StrokeCap.Round,
                                pathEffect = dashPathEffect,
                            )
                        }
                    }
                }
            },
    )
}

@Immutable
internal data class DividerLineWeights(
    val before: Float,
    val after: Float,
)

internal fun dividerLineWeightsFor(
    labelPosition: ElegantDividerLabelPosition,
): DividerLineWeights = when (labelPosition) {
    ElegantDividerLabelPosition.Start -> DividerLineWeights(before = 0f, after = 1f)
    ElegantDividerLabelPosition.Center -> DividerLineWeights(before = 1f, after = 1f)
    ElegantDividerLabelPosition.End -> DividerLineWeights(before = 1f, after = 0f)
}

internal fun resolveDividerColors(
    emphasis: ElegantDividerEmphasis,
    themeColors: ElegantColors,
): ElegantDividerColors = when (emphasis) {
    ElegantDividerEmphasis.Subtle -> ElegantDividerColors(
        lineColor = themeColors.borderDefault,
        contentColor = themeColors.textSecondary,
    )

    ElegantDividerEmphasis.Strong -> ElegantDividerColors(
        lineColor = themeColors.borderStrong,
        contentColor = themeColors.textPrimary,
    )
}

internal fun resolveDividerThickness(thickness: Dp): Dp =
    if (thickness.value.isFinite() && thickness > 0.dp) {
        thickness
    } else {
        ElegantDividerDefaults.Thickness
    }

internal fun resolveDividerLabelGap(labelGap: Dp): Dp =
    if (labelGap.value.isFinite() && labelGap >= 0.dp) {
        labelGap
    } else {
        ElegantDividerDefaults.LabelGap
    }

private fun dividerSemanticModifier(contentDescription: String?): Modifier = when {
    contentDescription == null -> Modifier
    contentDescription.isBlank() -> Modifier.clearAndSetSemantics {}
    else -> Modifier.clearAndSetSemantics {
        this.contentDescription = contentDescription
    }
}

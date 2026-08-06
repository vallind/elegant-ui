package com.elegant.compose.ui.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/** Semantic visual styles supported by Elegant UI badges. */
public enum class ElegantBadgeStyle {
    /** Quiet status that does not imply action or severity. */
    Neutral,

    /** Brand-accent status or count requiring stronger emphasis. */
    Accent,

    /** Successful, available, or healthy status. */
    Positive,

    /** Status that requires awareness but is not yet critical. */
    Warning,

    /** Urgent, failed, destructive, or otherwise critical status. */
    Critical,
}

/** Optical size presets shared by label, count, and dot badges. */
public enum class ElegantBadgeSize {
    /** Densest badge for compact overlays and metadata. */
    Small,

    /** Default badge for standard controls and content surfaces. */
    Medium,

    /** Prominent badge for spacious layouts or stronger emphasis. */
    Large,
}

/** Logical corner placements supported by [ElegantBadgeBox]. */
public enum class ElegantBadgePlacement {
    /** Top corner at the logical start edge. */
    TopStart,

    /** Top corner at the logical end edge. */
    TopEnd,

    /** Bottom corner at the logical start edge. */
    BottomStart,

    /** Bottom corner at the logical end edge. */
    BottomEnd,
}

/**
 * Theme-aware colors used by Elegant UI badges.
 *
 * @property containerColor label, count, and dot badge background.
 * @property contentColor locally provided text and icon color.
 * @property borderColor optical outline color.
 */
@Immutable
public data class ElegantBadgeColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color,
)

/** Defaults and theme-aware factories shared by Elegant UI badge APIs. */
public object ElegantBadgeDefaults {
    /** Default optical outline width. */
    public val BorderWidth: Dp = 1.dp

    /** Default largest count rendered without an overflow suffix. */
    public const val DefaultMaxCount: Int = 99

    /**
     * Formats [count] for a compact count badge.
     *
     * Negative counts resolve to `0`. Values above [maxCount] use a `+` suffix, and a non-positive
     * [maxCount] is treated as `1` so invalid-but-representable input cannot crash composition.
     */
    public fun countLabel(
        count: Int,
        maxCount: Int = DefaultMaxCount,
    ): String = resolveBadgeCountLabel(count = count, maxCount = maxCount)

    /** Returns the fully rounded default shape used by label, count, and dot badges. */
    public fun shape(): Shape = RoundedCornerShape(ElegantRadius.full)

    /** Returns theme-aware colors for [style]. */
    @Composable
    public fun colors(
        style: ElegantBadgeStyle = ElegantBadgeStyle.Accent,
    ): ElegantBadgeColors = resolveBadgeColors(
        style = style,
        themeColors = ElegantTheme.colors,
    )
}

@Immutable
internal data class BadgeMetrics(
    val dotSize: Dp,
    val minContainerSize: Dp,
    val horizontalPadding: Dp,
)

/**
 * Displays a compact non-interactive label or custom status.
 *
 * Badge dimensions intentionally use 2dp optical increments because a strict 4dp scale would make
 * compact overlays disproportionate. A null [contentDescription] preserves semantics supplied by
 * [content]. Pass a localized non-blank description to make the badge own one merged label, or an
 * empty string to make all badge content decorative.
 *
 * @param modifier modifier applied once to the badge container.
 * @param contentDescription optional localized semantic label; null preserves content semantics.
 * @param style semantic visual style.
 * @param size optical size preset.
 * @param shape clipping and outline shape.
 * @param colors theme-aware container, content, and outline colors.
 * @param borderWidth optical outline width.
 * @param content short label, number, icon, or custom badge content.
 */
@Composable
public fun ElegantBadge(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    style: ElegantBadgeStyle = ElegantBadgeStyle.Accent,
    size: ElegantBadgeSize = ElegantBadgeSize.Medium,
    shape: Shape = ElegantBadgeDefaults.shape(),
    colors: ElegantBadgeColors = ElegantBadgeDefaults.colors(style),
    borderWidth: Dp = ElegantBadgeDefaults.BorderWidth,
    content: @Composable () -> Unit,
) {
    val metrics = badgeMetricsFor(size)
    val semanticModifier = when {
        contentDescription == null -> Modifier
        contentDescription.isBlank() -> Modifier.clearAndSetSemantics {}
        else -> Modifier.clearAndSetSemantics {
            this.contentDescription = contentDescription
        }
    }

    Box(
        modifier = modifier
            .then(semanticModifier)
            .defaultMinSize(
                minWidth = metrics.minContainerSize,
                minHeight = metrics.minContainerSize,
            )
            .clip(shape)
            .background(colors.containerColor)
            .border(
                width = borderWidth,
                color = colors.borderColor,
                shape = shape,
            )
            .padding(horizontal = metrics.horizontalPadding),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
            ProvideTextStyle(value = badgeTextStyleFor(size)) {
                content()
            }
        }
    }
}

/**
 * Displays a compact status dot.
 *
 * A null or blank [contentDescription] makes the dot decorative. Supply a localized description
 * such as `"Online"` when the dot communicates status that is not available in adjacent content.
 *
 * @param modifier modifier applied once to the dot container.
 * @param contentDescription optional localized status description.
 * @param style semantic visual style.
 * @param size optical dot size preset.
 * @param shape clipping and outline shape.
 * @param colors theme-aware indicator and outline colors.
 * @param borderWidth optical outline width.
 */
@Composable
public fun ElegantBadgeDot(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    style: ElegantBadgeStyle = ElegantBadgeStyle.Accent,
    size: ElegantBadgeSize = ElegantBadgeSize.Medium,
    shape: Shape = ElegantBadgeDefaults.shape(),
    colors: ElegantBadgeColors = ElegantBadgeDefaults.colors(style),
    borderWidth: Dp = ElegantBadgeDefaults.BorderWidth,
) {
    val semanticModifier = if (contentDescription.isNullOrBlank()) {
        Modifier.clearAndSetSemantics {}
    } else {
        Modifier.clearAndSetSemantics {
            this.contentDescription = contentDescription
        }
    }

    Box(
        modifier = modifier
            .then(semanticModifier)
            .size(badgeMetricsFor(size).dotSize)
            .clip(shape)
            .background(colors.containerColor)
            .border(
                width = borderWidth,
                color = colors.borderColor,
                shape = shape,
            ),
    )
}

/**
 * Displays a formatted numeric badge.
 *
 * Counts at or below zero occupy no layout when [showZero] is false. The visible label is clamped
 * by [maxCount], while [contentDescription] remains caller-configurable for localized context such
 * as `"12 unread messages"`. Passing an empty description makes the count decorative.
 *
 * @param count current count; negative values are displayed as zero when visible.
 * @param modifier modifier applied once to the visible badge container.
 * @param maxCount largest count rendered without a `+` suffix.
 * @param showZero whether zero and negative counts remain visible as `0`.
 * @param contentDescription optional localized semantic description.
 * @param style semantic visual style.
 * @param size optical size preset.
 * @param shape clipping and outline shape.
 * @param colors theme-aware container, content, and outline colors.
 * @param borderWidth optical outline width.
 */
@Composable
public fun ElegantCountBadge(
    count: Int,
    modifier: Modifier = Modifier,
    maxCount: Int = ElegantBadgeDefaults.DefaultMaxCount,
    showZero: Boolean = false,
    contentDescription: String? = ElegantBadgeDefaults.countLabel(count, maxCount),
    style: ElegantBadgeStyle = ElegantBadgeStyle.Accent,
    size: ElegantBadgeSize = ElegantBadgeSize.Medium,
    shape: Shape = ElegantBadgeDefaults.shape(),
    colors: ElegantBadgeColors = ElegantBadgeDefaults.colors(style),
    borderWidth: Dp = ElegantBadgeDefaults.BorderWidth,
) {
    if (count <= 0 && !showZero) return

    ElegantBadge(
        modifier = modifier,
        contentDescription = contentDescription,
        style = style,
        size = size,
        shape = shape,
        colors = colors,
        borderWidth = borderWidth,
    ) {
        Text(
            text = ElegantBadgeDefaults.countLabel(count, maxCount),
            maxLines = 1,
            overflow = TextOverflow.Clip,
        )
    }
}

/**
 * Places [badge] over a logical corner of [content] without changing the content's measured size.
 *
 * Start and end placements mirror automatically in RTL. The badge is centered on the selected
 * content corner and may draw outside this layout's bounds, so callers should avoid clipping the
 * parent when the overflow must remain visible.
 *
 * @param badge status, count, or custom badge displayed above the content.
 * @param modifier modifier applied once to the content-sized layout root.
 * @param placement logical corner used to anchor the badge.
 * @param content content receiving the badge overlay.
 */
@Composable
public fun ElegantBadgeBox(
    badge: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    placement: ElegantBadgePlacement = ElegantBadgePlacement.TopEnd,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = {
            Box(contentAlignment = Alignment.Center) {
                content()
            }
            Box(contentAlignment = Alignment.Center) {
                badge()
            }
        },
    ) { measurables, constraints ->
        val contentPlaceable = measurables[0].measure(constraints)
        val badgePlaceable = measurables[1].measure(
            constraints.copy(minWidth = 0, minHeight = 0),
        )
        val position = badgePositionFor(
            placement = placement,
            contentWidth = contentPlaceable.width,
            contentHeight = contentPlaceable.height,
            badgeWidth = badgePlaceable.width,
            badgeHeight = badgePlaceable.height,
        )

        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.placeRelative(0, 0)
            badgePlaceable.placeRelative(position.x, position.y)
        }
    }
}

internal fun resolveBadgeCountLabel(
    count: Int,
    maxCount: Int,
): String {
    val safeCount = count.coerceAtLeast(0)
    val safeMaxCount = maxCount.coerceAtLeast(1)
    return if (safeCount > safeMaxCount) "$safeMaxCount+" else safeCount.toString()
}

internal fun resolveBadgeColors(
    style: ElegantBadgeStyle,
    themeColors: ElegantColors,
): ElegantBadgeColors = when (style) {
    ElegantBadgeStyle.Neutral -> ElegantBadgeColors(
        containerColor = themeColors.surfaceSunken,
        contentColor = themeColors.textPrimary,
        borderColor = themeColors.borderDefault,
    )

    ElegantBadgeStyle.Accent -> ElegantBadgeColors(
        // The pressed primary variant keeps white text at WCAG AA on both palettes,
        // while the resting primary stays reserved for interactive containers.
        containerColor = themeColors.interactivePrimaryPressed,
        contentColor = themeColors.textInverse,
        borderColor = themeColors.textInverse.copy(alpha = 0.18f),
    )

    ElegantBadgeStyle.Positive -> ElegantBadgeColors(
        containerColor = themeColors.statusPositive,
        contentColor = themeColors.onStatusPositive,
        borderColor = themeColors.onStatusPositive.copy(alpha = 0.18f),
    )

    ElegantBadgeStyle.Warning -> ElegantBadgeColors(
        containerColor = themeColors.statusWarning,
        contentColor = themeColors.onStatusWarning,
        borderColor = themeColors.onStatusWarning.copy(alpha = 0.18f),
    )

    ElegantBadgeStyle.Critical -> ElegantBadgeColors(
        containerColor = themeColors.statusCritical,
        contentColor = themeColors.onStatusCritical,
        borderColor = themeColors.onStatusCritical.copy(alpha = 0.18f),
    )
}

internal fun badgeMetricsFor(size: ElegantBadgeSize): BadgeMetrics = when (size) {
    ElegantBadgeSize.Small -> BadgeMetrics(
        dotSize = 6.dp,
        minContainerSize = 18.dp,
        horizontalPadding = 5.dp,
    )

    ElegantBadgeSize.Medium -> BadgeMetrics(
        dotSize = 8.dp,
        minContainerSize = 22.dp,
        horizontalPadding = 7.dp,
    )

    ElegantBadgeSize.Large -> BadgeMetrics(
        dotSize = 10.dp,
        minContainerSize = 26.dp,
        horizontalPadding = 9.dp,
    )
}

@Composable
internal fun badgeTextStyleFor(size: ElegantBadgeSize): TextStyle = when (size) {
    ElegantBadgeSize.Small,
    ElegantBadgeSize.Medium,
    -> ElegantTheme.typography.labelSmall

    ElegantBadgeSize.Large -> ElegantTheme.typography.labelMedium
}

internal fun badgePositionFor(
    placement: ElegantBadgePlacement,
    contentWidth: Int,
    contentHeight: Int,
    badgeWidth: Int,
    badgeHeight: Int,
): IntOffset {
    val x = when (placement) {
        ElegantBadgePlacement.TopStart,
        ElegantBadgePlacement.BottomStart,
        -> -(badgeWidth / 2)

        ElegantBadgePlacement.TopEnd,
        ElegantBadgePlacement.BottomEnd,
        -> contentWidth - (badgeWidth / 2)
    }
    val y = when (placement) {
        ElegantBadgePlacement.TopStart,
        ElegantBadgePlacement.TopEnd,
        -> -(badgeHeight / 2)

        ElegantBadgePlacement.BottomStart,
        ElegantBadgePlacement.BottomEnd,
        -> contentHeight - (badgeHeight / 2)
    }
    return IntOffset(x = x, y = y)
}

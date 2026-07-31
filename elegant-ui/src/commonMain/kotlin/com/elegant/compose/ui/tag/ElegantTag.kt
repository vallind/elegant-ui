package com.elegant.compose.ui.tag

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantTheme

/** Visual variants supported by [ElegantTag]. */
public enum class ElegantTagStyle {
    /** Dominant solid container for the primary category of the surface. */
    Filled,

    /** Soft accent container for balanced emphasis on default surfaces. */
    Tinted,

    /** Transparent container with a visible border for secondary categories. */
    Outlined,

    /** Quiet container without a border for tertiary metadata. */
    Plain,
}

/** Optical size presets supported by [ElegantTag]. */
public enum class ElegantTagSize {
    /** Densest tag for metadata and inline filters. */
    Small,

    /** Default tag for forms, filters, and content surfaces. */
    Medium,

    /** Prominent tag for hero content or strong classification. */
    Large,
}

/**
 * Theme-aware state colors used by [ElegantTag].
 *
 * Use [ElegantTagDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor default container color.
 * @property contentColor default content color.
 * @property borderColor default border color.
 * @property borderWidth default border width.
 * @property selectedContainerColor selected container color.
 * @property selectedContentColor selected content color.
 * @property selectedBorderColor selected border color.
 * @property selectedBorderWidth selected border width.
 * @property hoveredContainerColor hovered container color.
 * @property hoveredContentColor hovered content color.
 * @property hoveredBorderColor hovered border color.
 * @property pressedContainerColor pressed container color.
 * @property pressedContentColor pressed content color.
 * @property pressedBorderColor pressed border color.
 * @property disabledContainerColor disabled container color.
 * @property disabledContentColor disabled content color.
 * @property disabledBorderColor disabled border color.
 * @property disabledBorderWidth disabled border width.
 * @property focusedBorderColor keyboard focus-ring color.
 * @property focusedBorderWidth keyboard focus-ring width.
 */
@Immutable
public data class ElegantTagColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color,
    val borderWidth: Dp,
    val selectedContainerColor: Color = containerColor,
    val selectedContentColor: Color = contentColor,
    val selectedBorderColor: Color = borderColor,
    val selectedBorderWidth: Dp = borderWidth,
    val hoveredContainerColor: Color = containerColor,
    val hoveredContentColor: Color = contentColor,
    val hoveredBorderColor: Color = borderColor,
    val pressedContainerColor: Color = containerColor,
    val pressedContentColor: Color = contentColor,
    val pressedBorderColor: Color = borderColor,
    val disabledContainerColor: Color = containerColor,
    val disabledContentColor: Color = contentColor,
    val disabledBorderColor: Color = borderColor,
    val disabledBorderWidth: Dp = borderWidth,
    val focusedBorderColor: Color = borderColor,
    val focusedBorderWidth: Dp = 2.dp,
)

/** Theme-aware defaults for [ElegantTag]. */
public object ElegantTagDefaults {
    /** Minimum interactive root height used by selectable tags. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Immediate press-response duration. */
    public const val PressAnimationDurationMillis: Int = ElegantMotion.fastDurationMillis

    /** Restrained pressed scale that preserves the interactive target. */
    public const val PressedScale: Float = 0.97f

    /** Returns the fully rounded default tag shape. */
    public fun shape(): Shape = RoundedCornerShape(ElegantRadius.full)

    /** Returns theme-aware state colors for [style]. */
    @Composable
    public fun colors(
        style: ElegantTagStyle = ElegantTagStyle.Tinted,
    ): ElegantTagColors = resolveTagColors(
        style = style,
        themeColors = ElegantTheme.colors,
    )
}

@Immutable
internal data class TagMetrics(
    val visualHeight: Dp,
    val horizontalPadding: Dp,
    val leadingContentSize: Dp,
    val gap: Dp,
)

@Immutable
internal data class TagVisuals(
    val container: Color,
    val content: Color,
    val border: Color,
    val borderWidth: Dp,
    val scale: Float,
)

/**
 * Labels or classifies content with an optional selection interaction.
 *
 * A tag without [onClick] is non-interactive: it keeps the semantics of [content], supports no
 * focus, and renders at its optical height. Passing [onClick] turns the tag into a selectable
 * control with a 48dp minimum interactive root, visible focus ring, hover and press feedback, and
 * a merged [Role.Button] label that announces [selected].
 *
 * @param onClick optional activation callback; null keeps the tag non-interactive.
 * @param modifier modifier applied once to the tag root.
 * @param selected whether the tag communicates a chosen filter or category.
 * @param enabled whether user interaction is accepted.
 * @param style visual variant.
 * @param size optical size preset.
 * @param shape clipping and outline shape.
 * @param colors theme-aware state colors.
 * @param leadingContent optional content before the label, such as a status dot or icon.
 * @param content tag label or custom content.
 */
@Composable
public fun ElegantTag(
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    enabled: Boolean = true,
    style: ElegantTagStyle = ElegantTagStyle.Tinted,
    size: ElegantTagSize = ElegantTagSize.Medium,
    shape: Shape = ElegantTagDefaults.shape(),
    colors: ElegantTagColors = ElegantTagDefaults.colors(style),
    leadingContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val interactive = onClick != null
    val metrics = tagMetricsFor(size)
    val resolvedInteractionSource = remember { MutableInteractionSource() }
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    val visuals = resolveTagVisuals(
        colors = colors,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
        focused = focused,
        selected = selected,
        interactive = interactive,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = tween(
            durationMillis = ElegantTagDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantTagContainer",
    )
    val animatedContent by animateColorAsState(
        targetValue = visuals.content,
        animationSpec = tween(
            durationMillis = ElegantTagDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantTagContent",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = tween(
            durationMillis = ElegantTagDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantTagBorder",
    )
    val animatedBorderWidth by androidx.compose.animation.core.animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = tween(
            durationMillis = ElegantTagDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantTagBorderWidth",
    )
    val animatedScale by animateFloatAsState(
        targetValue = visuals.scale,
        animationSpec = tween(
            durationMillis = if (pressed) {
                ElegantTagDefaults.PressAnimationDurationMillis
            } else {
                ElegantTagDefaults.AnimationDurationMillis
            },
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantTagScale",
    )

    val semanticModifier = if (interactive) {
        Modifier.semantics(mergeDescendants = true) {
            role = Role.Button
            if (!enabled) disabled()
            this.selected = selected
        }
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .then(semanticModifier)
            .defaultMinSize(minHeight = if (interactive) {
                ElegantTagDefaults.MinimumTouchHeight
            } else {
                0.dp
            })
            .clickable(
                enabled = interactive && enabled,
                role = if (interactive) Role.Button else null,
                interactionSource = resolvedInteractionSource,
                indication = null,
                onClick = { onClick?.invoke() },
            ),
        contentAlignment = Alignment.Center,
    ) {
        val borderModifier = if (animatedBorderWidth > 0.dp) {
            Modifier.border(
                border = BorderStroke(animatedBorderWidth, animatedBorder),
                shape = shape,
            )
        } else {
            Modifier
        }

        Row(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                }
                .defaultMinSize(minHeight = metrics.visualHeight)
                .clip(shape)
                .background(animatedContainer)
                .indication(
                    interactionSource = resolvedInteractionSource,
                    indication = if (interactive) {
                        ripple(color = animatedContent)
                    } else {
                        null
                    },
                )
                .then(borderModifier)
                .padding(horizontal = metrics.horizontalPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CompositionLocalProvider(LocalContentColor provides animatedContent) {
                if (leadingContent != null) {
                    Box(
                        modifier = Modifier.size(metrics.leadingContentSize),
                        contentAlignment = Alignment.Center,
                    ) {
                        leadingContent()
                    }
                    Spacer(Modifier.width(metrics.gap))
                }

                ProvideTextStyle(tagTextStyleFor(size)) {
                    content()
                }
            }
        }
    }
}

internal fun tagMetricsFor(size: ElegantTagSize): TagMetrics = when (size) {
    ElegantTagSize.Small -> TagMetrics(
        visualHeight = 24.dp,
        horizontalPadding = 8.dp,
        leadingContentSize = 6.dp,
        gap = 6.dp,
    )

    ElegantTagSize.Medium -> TagMetrics(
        visualHeight = 28.dp,
        horizontalPadding = 10.dp,
        leadingContentSize = 8.dp,
        gap = 6.dp,
    )

    ElegantTagSize.Large -> TagMetrics(
        visualHeight = 32.dp,
        horizontalPadding = 12.dp,
        leadingContentSize = 10.dp,
        gap = 8.dp,
    )
}

@Composable
internal fun tagTextStyleFor(size: ElegantTagSize): TextStyle = when (size) {
    ElegantTagSize.Small,
    ElegantTagSize.Medium,
    -> ElegantTheme.typography.labelSmall

    ElegantTagSize.Large -> ElegantTheme.typography.labelMedium
}

internal fun resolveTagColors(
    style: ElegantTagStyle,
    themeColors: ElegantColors,
): ElegantTagColors {
    val accentContainer = themeColors.interactivePrimary.copy(alpha = 0.12f)
    val accentContainerSelected = themeColors.interactivePrimary.copy(alpha = 0.22f)
    val accentContainerPressed = themeColors.interactivePrimary.copy(alpha = 0.26f)
    val accentBorder = themeColors.interactivePrimary.copy(alpha = 0.55f)
    val accentOutline = themeColors.interactivePrimary.copy(alpha = 0.45f)
    return when (style) {
        ElegantTagStyle.Filled -> ElegantTagColors(
            containerColor = themeColors.interactivePrimary,
            contentColor = themeColors.textInverse,
            borderColor = Color.Transparent,
            borderWidth = 0.dp,
            selectedContainerColor = themeColors.interactivePrimaryPressed,
            selectedContentColor = themeColors.textInverse,
            selectedBorderColor = themeColors.textInverse.copy(alpha = 0.35f),
            selectedBorderWidth = 1.dp,
            hoveredContainerColor = themeColors.interactivePrimaryHover,
            hoveredContentColor = themeColors.textInverse,
            hoveredBorderColor = Color.Transparent,
            pressedContainerColor = themeColors.interactivePrimaryPressed,
            pressedContentColor = themeColors.textInverse,
            pressedBorderColor = Color.Transparent,
            disabledContainerColor = themeColors.surfaceSunken,
            disabledContentColor = themeColors.textTertiary,
            disabledBorderColor = Color.Transparent,
            disabledBorderWidth = 0.dp,
            focusedBorderColor = themeColors.focusRing,
        )

        ElegantTagStyle.Tinted -> ElegantTagColors(
            containerColor = accentContainer,
            contentColor = themeColors.interactivePrimary,
            borderColor = Color.Transparent,
            borderWidth = 0.dp,
            selectedContainerColor = accentContainerSelected,
            selectedContentColor = themeColors.interactivePrimary,
            selectedBorderColor = accentBorder,
            selectedBorderWidth = 1.dp,
            hoveredContainerColor = themeColors.interactivePrimary.copy(alpha = 0.18f),
            hoveredContentColor = themeColors.interactivePrimaryHover,
            hoveredBorderColor = Color.Transparent,
            pressedContainerColor = accentContainerPressed,
            pressedContentColor = themeColors.interactivePrimaryPressed,
            pressedBorderColor = Color.Transparent,
            disabledContainerColor = themeColors.surfaceSunken,
            disabledContentColor = themeColors.textTertiary,
            disabledBorderColor = Color.Transparent,
            disabledBorderWidth = 0.dp,
            focusedBorderColor = themeColors.focusRing,
        )

        ElegantTagStyle.Outlined -> ElegantTagColors(
            containerColor = Color.Transparent,
            contentColor = themeColors.textPrimary,
            borderColor = themeColors.borderStrong,
            borderWidth = 1.dp,
            selectedContainerColor = themeColors.interactivePrimary.copy(alpha = 0.10f),
            selectedContentColor = themeColors.interactivePrimary,
            selectedBorderColor = themeColors.interactivePrimary,
            selectedBorderWidth = 1.dp,
            hoveredContainerColor = themeColors.backgroundSubtle,
            hoveredContentColor = themeColors.textPrimary,
            hoveredBorderColor = accentOutline,
            pressedContainerColor = themeColors.surfaceSunken,
            pressedContentColor = themeColors.textPrimary,
            pressedBorderColor = themeColors.borderStrong,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = themeColors.textTertiary,
            disabledBorderColor = themeColors.borderDefault,
            disabledBorderWidth = 1.dp,
            focusedBorderColor = themeColors.focusRing,
        )

        ElegantTagStyle.Plain -> ElegantTagColors(
            containerColor = Color.Transparent,
            contentColor = themeColors.textSecondary,
            borderColor = Color.Transparent,
            borderWidth = 0.dp,
            selectedContainerColor = themeColors.interactivePrimary.copy(alpha = 0.10f),
            selectedContentColor = themeColors.interactivePrimary,
            selectedBorderColor = Color.Transparent,
            selectedBorderWidth = 0.dp,
            hoveredContainerColor = themeColors.backgroundSubtle,
            hoveredContentColor = themeColors.textPrimary,
            hoveredBorderColor = Color.Transparent,
            pressedContainerColor = themeColors.surfaceSunken,
            pressedContentColor = themeColors.textPrimary,
            pressedBorderColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = themeColors.textTertiary,
            disabledBorderColor = Color.Transparent,
            disabledBorderWidth = 0.dp,
            focusedBorderColor = themeColors.focusRing,
        )
    }
}

internal fun resolveTagVisuals(
    colors: ElegantTagColors,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
    focused: Boolean,
    selected: Boolean,
    interactive: Boolean,
): TagVisuals {
    val container = when {
        !enabled -> colors.disabledContainerColor
        pressed && interactive -> colors.pressedContainerColor
        selected -> colors.selectedContainerColor
        hovered -> colors.hoveredContainerColor
        else -> colors.containerColor
    }
    val content = when {
        !enabled -> colors.disabledContentColor
        pressed && interactive -> colors.pressedContentColor
        selected -> colors.selectedContentColor
        hovered -> colors.hoveredContentColor
        else -> colors.contentColor
    }
    val border = when {
        !enabled -> colors.disabledBorderColor
        pressed && interactive -> colors.pressedBorderColor
        focused && interactive -> colors.focusedBorderColor
        selected -> colors.selectedBorderColor
        hovered -> colors.hoveredBorderColor
        else -> colors.borderColor
    }
    val borderWidth = when {
        !enabled -> colors.disabledBorderWidth
        focused && interactive -> colors.focusedBorderWidth
        selected -> colors.selectedBorderWidth
        else -> colors.borderWidth
    }
    val scale = when {
        !interactive || !enabled -> 1f
        pressed -> ElegantTagDefaults.PressedScale
        else -> 1f
    }

    return TagVisuals(
        container = container,
        content = content,
        border = border,
        borderWidth = borderWidth,
        scale = scale,
    )
}

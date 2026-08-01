package com.elegant.compose.ui.link

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantLink].
 *
 * Use [ElegantLinkDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property contentColor resting link text color.
 * @property hoveredContentColor hovered link text color; defaults to [contentColor].
 * @property pressedContentColor pressed link text color; defaults to [contentColor].
 * @property disabledContentColor disabled link text color; defaults to [contentColor].
 * @property underlineColor resting underline color; defaults to [contentColor].
 */
@Immutable
public data class ElegantLinkColors(
    val contentColor: Color,
    val hoveredContentColor: Color = contentColor,
    val pressedContentColor: Color = contentColor,
    val disabledContentColor: Color = contentColor,
    val underlineColor: Color = contentColor,
)

/** Theme-aware defaults for [ElegantLink]. */
public object ElegantLinkDefaults {
    /** Minimum interactive target height enforced on the link root. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware link colors. */
    @Composable
    public fun colors(): ElegantLinkColors = resolveLinkColors(ElegantTheme.colors)
}

/** Stroke width of the underline drawn beneath the link text. */
internal val LinkUnderlineWidth: Dp = 1.dp

/** Alpha applied to the resting underline color over the interactive primary role. */
internal const val LinkUnderlineAlpha: Float = 0.5f

/**
 * Renders an inline text link with an animated underline.
 *
 * The link is a compact text-only interactive root: it paints [text] in the labelMedium style
 * with a 1dp underline beneath the glyphs, while [ElegantLinkDefaults.MinimumTouchHeight] is
 * enforced through a minimum interactive component size that expands the clickable area without
 * enlarging the visible text. The underline mirrors the resolved state color: it rests at 50%
 * alpha, brightens to the hovered content color, returns to the resting color while pressed, and
 * fades to the disabled content color.
 *
 * Color precedence follows disabled, pressed, hovered, resting. Disabled links announce their
 * state through semantics and never invoke [onClick]. The link merges its text label into one
 * semantics node and deliberately exposes no [androidx.compose.ui.semantics.Role] — links are
 * not buttons in Compose semantics.
 *
 * @param text label rendered as link text.
 * @param onClick activation callback; never invoked while [enabled] is false.
 * @param modifier modifier applied once to the link root.
 * @param enabled whether user interaction is accepted.
 * @param colors theme-aware state colors.
 * @param interactionSource optional hoisted interaction source for observing or controlling
 * state; one is created and remembered when null.
 */
@Composable
public fun ElegantLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantLinkColors = ElegantLinkDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
) {
    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val visuals = resolveLinkVisuals(
        colors = colors,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
    )
    val animatedContent by animateColorAsState(
        targetValue = visuals.content,
        animationSpec = tween(
            durationMillis = ElegantLinkDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantLinkContent",
    )
    val animatedUnderline by animateColorAsState(
        targetValue = visuals.underline,
        animationSpec = tween(
            durationMillis = ElegantLinkDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantLinkUnderline",
    )

    Box(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                if (!enabled) disabled()
            }
            .defaultMinSize(minHeight = ElegantLinkDefaults.MinimumTouchHeight)
            .clickable(
                enabled = enabled,
                interactionSource = resolvedInteractionSource,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = animatedContent,
            style = ElegantTheme.typography.labelMedium,
            modifier = Modifier.drawBehind {
                val y = underlineY(size.height)
                drawLine(
                    color = animatedUnderline,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = LinkUnderlineWidth.toPx(),
                )
            },
        )
    }
}

@Immutable
internal data class LinkVisuals(
    val content: Color,
    val underline: Color,
)

/**
 * Resolves the content and underline colors for the current link state.
 *
 * Precedence: disabled, pressed, hovered, resting. The underline mirrors the content color in
 * every state except resting, where the dedicated [ElegantLinkColors.underlineColor] applies.
 *
 * @param colors link colors to pick from.
 * @param enabled whether the link accepts interaction.
 * @param pressed whether a pointer currently presses the link.
 * @param hovered whether a pointer currently hovers the link.
 * @return the visuals matching the leading link state.
 */
internal fun resolveLinkVisuals(
    colors: ElegantLinkColors,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
): LinkVisuals {
    val content = when {
        !enabled -> colors.disabledContentColor
        pressed -> colors.pressedContentColor
        hovered -> colors.hoveredContentColor
        else -> colors.contentColor
    }
    val underline = when {
        !enabled -> colors.disabledContentColor
        pressed -> colors.pressedContentColor
        hovered -> colors.hoveredContentColor
        else -> colors.underlineColor
    }
    return LinkVisuals(
        content = content,
        underline = underline,
    )
}

/**
 * Resolves theme-aware link colors for [themeColors].
 *
 * @param themeColors semantic roles of the active light or dark theme.
 * @return link colors derived from the semantic roles.
 */
internal fun resolveLinkColors(themeColors: ElegantColors): ElegantLinkColors =
    ElegantLinkColors(
        contentColor = themeColors.interactivePrimary,
        hoveredContentColor = themeColors.interactivePrimaryHover,
        pressedContentColor = themeColors.interactivePrimary,
        disabledContentColor = themeColors.textTertiary,
        underlineColor = themeColors.interactivePrimary.copy(alpha = LinkUnderlineAlpha),
    )

/**
 * Returns the vertical offset in pixels at which the underline is drawn.
 *
 * The underline sits one pixel above the bottom edge of the text box, clamping to zero for
 * degenerate or negative heights so the line never leaves the drawable area.
 *
 * @param heightPx the height of the text box in pixels.
 * @return [heightPx] minus one, never below zero.
 */
internal fun underlineY(heightPx: Float): Float = (heightPx - 1f).coerceAtLeast(0f)

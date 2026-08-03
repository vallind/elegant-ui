package com.elegant.compose.ui.closebutton

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantTheme

/** Default fraction of the glyph size reserved as the X inset from the pill edge. */
internal const val DefaultCloseGlyphInsetFraction: Float = 0.3f

/** Smallest allowed fraction of the glyph size used as the X inset. */
internal const val MinCloseGlyphInsetFraction: Float = 0.2f

/** Largest allowed fraction of the glyph size used as the X inset. */
internal const val MaxCloseGlyphInsetFraction: Float = 0.45f

/** Stroke width of the two X glyph lines. */
internal val CloseGlyphStrokeWidth: Dp = 2.dp

/** Border width of the focus ring drawn on the pill. */
internal val CloseButtonFocusedBorderWidth: Dp = 2.dp

/**
 * State colors and border metric used by [ElegantCloseButton].
 *
 * Use [ElegantCloseButtonDefaults.colors] for theme-aware defaults and [copy] for intentional
 * product-level customization.
 *
 * @property containerColor resting pill color.
 * @property contentColor resting glyph color.
 * @property hoveredContainerColor hovered pill color.
 * @property pressedContainerColor pressed pill color.
 * @property disabledContentColor disabled glyph color.
 * @property focusedBorderColor focused pill border color.
 */
@Immutable
public data class ElegantCloseButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val hoveredContainerColor: Color = containerColor,
    val pressedContainerColor: Color = containerColor,
    val disabledContentColor: Color = contentColor,
    val focusedBorderColor: Color = containerColor,
)

/** Theme-aware defaults for [ElegantCloseButton]. */
public object ElegantCloseButtonDefaults {
    /** Minimum width and height of every interactive root. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Visual pill diameter hosting the X glyph. */
    public val VisualSize: Dp = 28.dp

    /** Standard hover, focus, and state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware colors. */
    @Composable
    public fun colors(): ElegantCloseButtonColors = resolveCloseButtonColors(ElegantTheme.colors)
}

/**
 * Resolves the theme-aware default colors for [ElegantCloseButton].
 *
 * The resting pill is transparent, the pill fills on hover, darkens while pressed, the glyph dims
 * when disabled, and the focus ring reuses the theme focus color.
 */
internal fun resolveCloseButtonColors(themeColors: ElegantColors): ElegantCloseButtonColors =
    ElegantCloseButtonColors(
        containerColor = Color.Transparent,
        contentColor = themeColors.textSecondary,
        hoveredContainerColor = themeColors.surfaceHover,
        pressedContainerColor = themeColors.backgroundSubtle,
        disabledContentColor = themeColors.textTertiary,
        focusedBorderColor = themeColors.focusRing,
    )

@Immutable
internal data class CloseButtonVisuals(
    val container: Color,
    val content: Color,
)

/**
 * Resolves pill and glyph colors for the disabled, pressed, hovered, or resting state in that
 * precedence order.
 */
internal fun resolveCloseButtonVisuals(
    colors: ElegantCloseButtonColors,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
): CloseButtonVisuals = when {
    !enabled -> CloseButtonVisuals(
        container = colors.containerColor,
        content = colors.disabledContentColor,
    )

    pressed -> CloseButtonVisuals(
        container = colors.pressedContainerColor,
        content = colors.contentColor,
    )

    hovered -> CloseButtonVisuals(
        container = colors.hoveredContainerColor,
        content = colors.contentColor,
    )

    else -> CloseButtonVisuals(
        container = colors.containerColor,
        content = colors.contentColor,
    )
}

/**
 * Returns the four glyph endpoints of the two diagonal X lines within a square of [sizePx].
 *
 * The returned order is the start and end of the falling diagonal followed by the start and end of
 * the rising diagonal. [insetFraction] is the fraction of [sizePx] reserved as the inset from each
 * edge and is clamped to `MinCloseGlyphInsetFraction`..`MaxCloseGlyphInsetFraction`.
 */
internal fun closeGlyphEndpoints(
    sizePx: Float,
    insetFraction: Float = DefaultCloseGlyphInsetFraction,
): List<Offset> {
    val inset = sizePx * insetFraction.coerceIn(
        MinCloseGlyphInsetFraction,
        MaxCloseGlyphInsetFraction,
    )
    return listOf(
        Offset(inset, inset),
        Offset(sizePx - inset, sizePx - inset),
        Offset(sizePx - inset, inset),
        Offset(inset, sizePx - inset),
    )
}

/**
 * Displays an Elegant UI dismiss action.
 *
 * The component draws a fixed X glyph on a quiet transparent pill inside a 48dp interaction target.
 * The pill fills on hover, darkens while pressed, shows a focus ring when keyboard-focused, and
 * dims the glyph when disabled. [contentDescription] names the action for accessibility services
 * and defaults to `"Close"`; localize it for production use.
 *
 * @param onClick callback invoked when the close action accepts an activation.
 * @param modifier modifier applied to the 48dp minimum interactive root.
 * @param enabled whether user interaction is accepted.
 * @param contentDescription localized accessibility name for the close action.
 * @param colors state colors for the pill and the X glyph.
 * @param interactionSource optional hoisted interaction source for observing interaction state.
 */
@Composable
public fun ElegantCloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String = "Close",
    colors: ElegantCloseButtonColors = ElegantCloseButtonDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
) {
    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    val visuals = resolveCloseButtonVisuals(
        colors = colors,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = tween(
            durationMillis = ElegantCloseButtonDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantCloseButtonContainer",
    )
    val animatedContent by animateColorAsState(
        targetValue = visuals.content,
        animationSpec = tween(
            durationMillis = ElegantCloseButtonDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantCloseButtonContent",
    )

    Box(
        modifier = modifier
            .semantics {
                role = Role.Button
                this.contentDescription = contentDescription
                if (!enabled) disabled()
            }
            .defaultMinSize(
                minWidth = ElegantCloseButtonDefaults.MinimumTouchHeight,
                minHeight = ElegantCloseButtonDefaults.MinimumTouchHeight,
            )
            .clickable(
                enabled = enabled,
                role = Role.Button,
                interactionSource = resolvedInteractionSource,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(ElegantCloseButtonDefaults.VisualSize)
                .clip(CircleShape)
                .background(animatedContainer)
                .then(
                    if (focused && enabled) {
                        Modifier.border(
                            border = BorderStroke(
                                width = CloseButtonFocusedBorderWidth,
                                color = colors.focusedBorderColor,
                            ),
                            shape = CircleShape,
                        )
                    } else {
                        Modifier
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(modifier = Modifier.size(ElegantCloseButtonDefaults.VisualSize)) {
                val endpoints = closeGlyphEndpoints(size.width.toFloat())
                drawLine(
                    color = animatedContent,
                    start = endpoints[0],
                    end = endpoints[1],
                    strokeWidth = CloseGlyphStrokeWidth.toPx(),
                    cap = StrokeCap.Round,
                )
                drawLine(
                    color = animatedContent,
                    start = endpoints[2],
                    end = endpoints[3],
                    strokeWidth = CloseGlyphStrokeWidth.toPx(),
                    cap = StrokeCap.Round,
                )
            }
        }
    }
}

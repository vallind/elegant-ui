package com.elegant.compose.ui.surface

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.foundation.shape.resolveSquircleAwareShape
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantSurface].
 *
 * Use [ElegantSurfaceDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor default container color.
 * @property contentColor default content color, provided to [ElegantSurface] content through
 * [LocalContentColor].
 * @property borderColor default border color, drawn only when a border width is supplied.
 * @property hoveredContainerColor hovered container color.
 * @property pressedContainerColor pressed container color.
 * @property disabledContainerColor disabled container color.
 * @property disabledContentColor disabled content color.
 * @property focusedBorderColor keyboard focus-ring color, drawn at 2dp while focused and enabled.
 */
@Immutable
public data class ElegantSurfaceColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color,
    val hoveredContainerColor: Color = containerColor,
    val pressedContainerColor: Color = containerColor,
    val disabledContainerColor: Color = containerColor,
    val disabledContentColor: Color = contentColor,
    val focusedBorderColor: Color = borderColor,
)

/** Theme-aware defaults for [ElegantSurface]. */
public object ElegantSurfaceDefaults {
    /** Minimum interactive root height used by clickable surfaces. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors. */
    @Composable
    public fun colors(): ElegantSurfaceColors = resolveSurfaceColors(ElegantTheme.colors)

    /** Returns the squircle-aware default container shape, a 16dp rounded square. */
    public fun shape(): Shape = RoundedCornerShape(ElegantRadius.lg)
}

@Immutable
internal data class SurfaceVisuals(
    val container: Color,
    val content: Color,
    val border: Color,
    val borderWidth: Dp,
)

/**
 * Low-level container primitive combining a background, an optional border, and an optional click
 * interaction.
 *
 * [ElegantSurface] is the foundation other components build on. Unlike
 * [com.elegant.compose.ui.card.ElegantCard], which is a content surface with
 * `Filled`/`Outlined`/`Elevated` style presets, a surface has no style enum, no built-in padding,
 * and no text styles: it renders only the [shape]-clipped container with [colors]'s container
 * color, an optional [borderWidth] border, and, when [onClick] is supplied, button-like
 * interaction. The caller owns all spacing and text styling inside [content], which receives
 * [colors]'s content color through [LocalContentColor].
 *
 * Without [onClick] the surface is non-interactive: it keeps the semantics of [content] and
 * supports no focus, hover, or press states. Passing [onClick] turns it into a button-like
 * surface with a 48dp minimum interactive root, a merged [Role.Button] label, hover and press
 * container colors with a ripple, a visible 2dp focus ring while focused and enabled, and a
 * disabled state. State precedence for the container color is disabled, pressed, hovered,
 * resting; focus overrides only the border.
 *
 * @param onClick optional activation callback; null keeps the surface non-interactive.
 * @param modifier modifier applied once to the surface root.
 * @param enabled whether user interaction is accepted.
 * @param shape clipping and border shape.
 * @param colors theme-aware state colors.
 * @param borderWidth resting border width; 0 renders no border. Focused interactive surfaces draw
 * a 2dp border in the focused border color.
 * @param content surface content; padding and text styles are the caller's responsibility.
 */
@Composable
public fun ElegantSurface(
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ElegantSurfaceDefaults.shape(),
    colors: ElegantSurfaceColors = ElegantSurfaceDefaults.colors(),
    borderWidth: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    val effectiveShape = resolveSquircleAwareShape(
        userShape = shape,
        defaultShape = ElegantSurfaceDefaults.shape(),
        cornerRadius = ElegantRadius.lg,
    )
    if (onClick == null) {
        val borderModifier = if (borderWidth > 0.dp) {
            Modifier.border(
                border = BorderStroke(borderWidth, colors.borderColor),
                shape = effectiveShape,
            )
        } else {
            Modifier
        }

        Box(
            modifier = modifier
                .clip(effectiveShape)
                .background(colors.containerColor)
                .then(borderModifier),
        ) {
            CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
                content()
            }
        }
        return
    }

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val visuals = resolveSurfaceVisuals(
        colors = colors,
        borderWidth = borderWidth,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
        focused = focused,
        interactive = true,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSurfaceContainer",
    )

    Box(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                role = Role.Button
                if (!enabled) disabled()
            }
            .defaultMinSize(minHeight = ElegantSurfaceDefaults.MinimumTouchHeight)
            .clickable(
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
    ) {
        val borderModifier = if (visuals.borderWidth > 0.dp) {
            Modifier.border(
                border = BorderStroke(visuals.borderWidth, visuals.border),
                shape = effectiveShape,
            )
        } else {
            Modifier
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(effectiveShape)
                .background(animatedContainer)
                .indication(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                )
                .then(borderModifier),
        ) {
            CompositionLocalProvider(LocalContentColor provides visuals.content) {
                content()
            }
        }
    }
}

internal fun resolveSurfaceColors(themeColors: ElegantColors): ElegantSurfaceColors =
    ElegantSurfaceColors(
        containerColor = themeColors.surfaceDefault,
        contentColor = themeColors.textPrimary,
        borderColor = themeColors.borderDefault,
        hoveredContainerColor = themeColors.surfaceHover,
        pressedContainerColor = themeColors.backgroundSubtle,
        disabledContainerColor = themeColors.surfaceSunken,
        disabledContentColor = themeColors.textTertiary,
        focusedBorderColor = themeColors.focusRing,
    )

internal fun resolveSurfaceVisuals(
    colors: ElegantSurfaceColors,
    borderWidth: Dp,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
    focused: Boolean,
    interactive: Boolean,
): SurfaceVisuals {
    val container = when {
        !enabled -> colors.disabledContainerColor
        pressed && interactive -> colors.pressedContainerColor
        hovered && interactive -> colors.hoveredContainerColor
        else -> colors.containerColor
    }
    val content = if (!enabled) colors.disabledContentColor else colors.contentColor
    val border = when {
        focused && interactive && enabled -> colors.focusedBorderColor
        else -> colors.borderColor
    }
    val resolvedBorderWidth = when {
        focused && interactive && enabled -> 2.dp
        else -> borderWidth
    }

    return SurfaceVisuals(
        container = container,
        content = content,
        border = border,
        borderWidth = resolvedBorderWidth,
    )
}

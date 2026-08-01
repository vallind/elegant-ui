package com.elegant.compose.ui.floatingactionbutton

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantElevation
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * State colors used by [ElegantFloatingActionButton].
 *
 * Use [ElegantFloatingActionButtonDefaults.colors] for theme-aware defaults, then use [copy] for
 * supported product-level customization.
 *
 * @property containerColor default container color.
 * @property contentColor default content color.
 * @property hoveredContainerColor hovered container color.
 * @property hoveredContentColor hovered content color.
 * @property pressedContainerColor pressed container color.
 * @property pressedContentColor pressed content color.
 * @property disabledContainerColor disabled container color.
 * @property disabledContentColor disabled content color.
 * @property focusedBorderColor keyboard-focus ring border color.
 */
@Immutable
public data class ElegantFloatingActionButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val hoveredContainerColor: Color = containerColor,
    val hoveredContentColor: Color = contentColor,
    val pressedContainerColor: Color = containerColor,
    val pressedContentColor: Color = contentColor,
    val disabledContainerColor: Color = containerColor,
    val disabledContentColor: Color = contentColor,
    val focusedBorderColor: Color = containerColor,
)

/** Theme-aware defaults for [ElegantFloatingActionButton]. */
public object ElegantFloatingActionButtonDefaults {
    /** Standard 56dp circular floating action button size. */
    public val Size: Dp = 56.dp

    /** Compact 40dp circular floating action button size. */
    public val CompactSize: Dp = 40.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Restrained pressed scale that preserves the fixed touch target. */
    public const val PressedScale: Float = 0.96f

    /** Returns theme-aware colors for [ElegantFloatingActionButton]. */
    @Composable
    public fun colors(): ElegantFloatingActionButtonColors =
        resolveFloatingActionButtonColors(ElegantTheme.colors)
}

/** Width of the keyboard-focus ring drawn around the circle. */
internal val FocusedBorderWidth: Dp = 2.dp

@Immutable
internal data class FloatingActionButtonVisuals(
    val container: Color,
    val content: Color,
    val border: Color,
    val borderWidth: Dp,
    val scale: Float,
)

/**
 * Displays an Elegant UI floating action button.
 *
 * A circular 56dp (or 40dp compact) container with a medium tonal elevation that hosts the primary
 * action of a screen. Hover shifts the container color, press settles the container color and
 * applies a restrained scale inside the fixed-size touch target, and keyboard focus draws a ring.
 *
 * @param onClick callback invoked when the button accepts an activation.
 * @param modifier modifier applied to the fixed-size touch-target container.
 * @param enabled whether user interaction is accepted.
 * @param compact whether the compact 40dp circular size is used.
 * @param colors state colors and the focus-ring border color.
 * @param interactionSource optional hoisted interaction source for observing or controlling state.
 * @param content composable content centered inside the button.
 */
@Composable
public fun ElegantFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    compact: Boolean = false,
    colors: ElegantFloatingActionButtonColors = ElegantFloatingActionButtonDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    val visuals = resolveFloatingActionButtonVisuals(
        colors = colors,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
        focused = focused,
    )

    val stateAnimationSpec = tween(
        durationMillis = if (pressed) {
            ElegantMotion.fastDurationMillis
        } else {
            ElegantFloatingActionButtonDefaults.AnimationDurationMillis
        },
        easing = FastOutSlowInEasing,
    )
    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = stateAnimationSpec,
        label = "ElegantFloatingActionButtonContainer",
    )
    val animatedContent by animateColorAsState(
        targetValue = visuals.content,
        animationSpec = stateAnimationSpec,
        label = "ElegantFloatingActionButtonContent",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = stateAnimationSpec,
        label = "ElegantFloatingActionButtonBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = stateAnimationSpec,
        label = "ElegantFloatingActionButtonBorderWidth",
    )
    val animatedScale by animateFloatAsState(
        targetValue = visuals.scale,
        animationSpec = stateAnimationSpec,
        label = "ElegantFloatingActionButtonScale",
    )

    val semanticModifier = Modifier.semantics(mergeDescendants = true) {
        role = Role.Button
        if (!enabled) disabled()
    }

    Box(
        modifier = modifier
            .then(semanticModifier)
            .size(fabSize(compact))
            .clickable(
                enabled = enabled,
                role = Role.Button,
                interactionSource = resolvedInteractionSource,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        val borderModifier = if (animatedBorderWidth > 0.dp) {
            Modifier.border(
                border = BorderStroke(animatedBorderWidth, animatedBorder),
                shape = CircleShape,
            )
        } else {
            Modifier
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                }
                .shadow(
                    elevation = ElegantElevation.medium,
                    shape = CircleShape,
                    clip = false,
                )
                .clip(CircleShape)
                .background(animatedContainer)
                .indication(
                    interactionSource = resolvedInteractionSource,
                    indication = ripple(color = animatedContent),
                )
                .then(borderModifier),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier.size(fabContentSize(compact)),
                contentAlignment = Alignment.Center,
            ) {
                CompositionLocalProvider(LocalContentColor provides animatedContent) {
                    content()
                }
            }
        }
    }
}

/** Returns the fixed touch-target size for [compact]. */
internal fun fabSize(compact: Boolean): Dp = if (compact) {
    ElegantFloatingActionButtonDefaults.CompactSize
} else {
    ElegantFloatingActionButtonDefaults.Size
}

/** Returns the content bounding-box size for [compact]. */
internal fun fabContentSize(compact: Boolean): Dp = if (compact) 20.dp else 24.dp

/** Resolves theme roles into [ElegantFloatingActionButtonColors]. */
internal fun resolveFloatingActionButtonColors(
    themeColors: ElegantColors,
): ElegantFloatingActionButtonColors = ElegantFloatingActionButtonColors(
    containerColor = themeColors.interactivePrimary,
    contentColor = themeColors.textInverse,
    hoveredContainerColor = themeColors.interactivePrimaryHover,
    pressedContainerColor = themeColors.interactivePrimaryPressed,
    disabledContainerColor = themeColors.surfaceSunken,
    disabledContentColor = themeColors.textTertiary,
    focusedBorderColor = themeColors.focusRing,
)

/**
 * Resolves interaction state precedence: disabled, pressed, focused border, hovered, resting.
 */
internal fun resolveFloatingActionButtonVisuals(
    colors: ElegantFloatingActionButtonColors,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
    focused: Boolean,
): FloatingActionButtonVisuals {
    val container = when {
        !enabled -> colors.disabledContainerColor
        pressed -> colors.pressedContainerColor
        hovered -> colors.hoveredContainerColor
        else -> colors.containerColor
    }
    val content = when {
        !enabled -> colors.disabledContentColor
        pressed -> colors.pressedContentColor
        hovered -> colors.hoveredContentColor
        else -> colors.contentColor
    }
    val focusedBorderVisible = enabled && focused && !pressed
    val border = if (focusedBorderVisible) colors.focusedBorderColor else Color.Transparent
    val borderWidth = if (focusedBorderVisible) FocusedBorderWidth else 0.dp
    val scale = when {
        !enabled -> 1f
        pressed -> ElegantFloatingActionButtonDefaults.PressedScale
        else -> 1f
    }

    return FloatingActionButtonVisuals(
        container = container,
        content = content,
        border = border,
        borderWidth = borderWidth,
        scale = scale,
    )
}

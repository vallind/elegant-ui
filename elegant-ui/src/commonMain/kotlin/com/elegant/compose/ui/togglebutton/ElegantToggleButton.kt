package com.elegant.compose.ui.togglebutton

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * State colors and border metrics used by [ElegantToggleButton].
 *
 * Use [ElegantToggleButtonDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization. Hovered, pressed, and disabled values fall back to their resting
 * equivalents unless set.
 *
 * @property containerColor default container color.
 * @property contentColor default content color.
 * @property borderColor default border color.
 * @property borderWidth constant border width across every state.
 * @property selectedContainerColor selected container color.
 * @property selectedContentColor selected content color.
 * @property selectedBorderColor selected border color.
 * @property hoveredContainerColor hovered container color.
 * @property hoveredContentColor hovered content color.
 * @property pressedContainerColor pressed container color.
 * @property pressedContentColor pressed content color.
 * @property disabledContainerColor disabled container color.
 * @property disabledContentColor disabled content color.
 * @property disabledBorderColor disabled border color.
 * @property focusedBorderColor keyboard focus-ring color.
 */
@Immutable
public data class ElegantToggleButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color,
    val borderWidth: Dp,
    val selectedContainerColor: Color,
    val selectedContentColor: Color,
    val selectedBorderColor: Color,
    val hoveredContainerColor: Color = containerColor,
    val hoveredContentColor: Color = contentColor,
    val pressedContainerColor: Color = containerColor,
    val pressedContentColor: Color = contentColor,
    val disabledContainerColor: Color = containerColor,
    val disabledContentColor: Color = contentColor,
    val disabledBorderColor: Color = borderColor,
    val focusedBorderColor: Color = borderColor,
)

/** Theme-aware defaults for [ElegantToggleButton]. */
public object ElegantToggleButtonDefaults {
    /** Minimum interactive root height. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Visual height of the toggle box. */
    public val Height: Dp = 36.dp

    /** Horizontal padding inside the visual toggle box. */
    public val HorizontalPadding: Dp = 12.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /**
     * Returns theme-aware state colors, or the enclosing [ElegantToggleButtonGroup] colors when
     * composed inside one.
     */
    @Composable
    public fun colors(): ElegantToggleButtonColors =
        LocalToggleButtonColors.current ?: resolveToggleButtonColors(ElegantTheme.colors)

    /** Returns the small-radius default toggle shape. */
    public fun shape(): Shape = RoundedCornerShape(ElegantRadius.sm)
}

@Immutable
internal data class ToggleButtonVisuals(
    val container: Color,
    val content: Color,
    val border: Color,
    val borderWidth: Dp,
)

private val LocalToggleButtonColors = staticCompositionLocalOf<ElegantToggleButtonColors?> { null }

private val LocalToggleButtonEnabled = staticCompositionLocalOf { true }

/**
 * Displays a single selectable toggle with a checkbox-style semantic state.
 *
 * The toggle is a controlled component: [selected] drives the visuals and the announced state, and
 * [onToggle] receives the state to apply when the toggle accepts an activation. The outer root
 * keeps a 48dp minimum touch target around a 36dp visual box with 12dp horizontal padding. The
 * semantic node merges descendants and announces [Role.Checkbox] with an `On` or `Off`
 * [ToggleableState] and the disabled state.
 *
 * Visual precedence: disabled, selected, pressed, hovered, resting for the container and content;
 * the border keeps a constant width and shows the focus ring while keyboard focus is visible.
 *
 * When composed inside an [ElegantToggleButtonGroup], the toggle stays disabled while the group is
 * disabled and resolves its default colors from the group's colors.
 *
 * @param selected whether the toggle communicates the on state.
 * @param onToggle callback invoked with the state to apply when the toggle accepts an activation.
 * @param modifier modifier applied to the 48dp minimum interactive root.
 * @param enabled whether user interaction is accepted.
 * @param colors state colors and border metrics.
 * @param content toggle label or custom content, rendered with `labelMedium` typography.
 */
@Composable
public fun ElegantToggleButton(
    selected: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantToggleButtonColors = ElegantToggleButtonDefaults.colors(),
    content: @Composable () -> Unit,
) {
    val effectiveEnabled = enabled && LocalToggleButtonEnabled.current
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val visuals = resolveToggleButtonVisuals(
        colors = colors,
        enabled = effectiveEnabled,
        selected = selected,
        pressed = pressed,
        hovered = hovered,
        focused = focused,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = tween(
            durationMillis = ElegantToggleButtonDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantToggleButtonContainer",
    )
    val animatedContent by animateColorAsState(
        targetValue = visuals.content,
        animationSpec = tween(
            durationMillis = ElegantToggleButtonDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantToggleButtonContent",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = tween(
            durationMillis = ElegantToggleButtonDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantToggleButtonBorder",
    )

    Box(
        modifier = modifier
            .defaultMinSize(minHeight = ElegantToggleButtonDefaults.MinimumTouchHeight)
            .clickable(
                enabled = effectiveEnabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null,
                onClick = { onToggle(!selected) },
            )
            .semantics(mergeDescendants = true) {
                role = Role.Checkbox
                toggleableState = if (selected) ToggleableState.On else ToggleableState.Off
                if (!effectiveEnabled) disabled()
            },
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .height(ElegantToggleButtonDefaults.Height)
                .padding(horizontal = ElegantToggleButtonDefaults.HorizontalPadding)
                .clip(shape = ElegantToggleButtonDefaults.shape())
                .background(animatedContainer)
                .indication(
                    interactionSource = interactionSource,
                    indication = ripple(color = animatedContent),
                )
                .border(
                    border = BorderStroke(visuals.borderWidth, animatedBorder),
                    shape = ElegantToggleButtonDefaults.shape(),
                ),
            contentAlignment = Alignment.Center,
        ) {
            CompositionLocalProvider(LocalContentColor provides animatedContent) {
                ProvideTextStyle(ElegantTheme.typography.labelMedium) {
                    content()
                }
            }
        }
    }
}

/**
 * Arranges a row of joined [ElegantToggleButton]s with zero spacing between them.
 *
 * The cluster is clipped to a single small-radius outline; each child keeps its own border and
 * interaction behavior, and the clip rounds only the outer corners of the cluster. No border
 * collapsing is attempted between adjacent children.
 *
 * The group's [colors] and [enabled] become the defaults for the buttons composed inside [content]:
 * a child without explicit colors resolves [ElegantToggleButtonDefaults.colors] against the group's
 * colors, and a child stays disabled while the group is disabled. Explicit values passed to a child
 * take precedence.
 *
 * @param modifier modifier applied once to the joined row.
 * @param enabled whether children accept user interaction.
 * @param colors colors inherited by children that do not pass their own.
 * @param content joined toggles rendered in a row.
 */
@Composable
public fun ElegantToggleButtonGroup(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantToggleButtonColors = ElegantToggleButtonDefaults.colors(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalToggleButtonColors provides colors,
        LocalToggleButtonEnabled provides enabled,
    ) {
        Row(
            modifier = modifier.clip(shape = RoundedCornerShape(ElegantRadius.sm)),
        ) {
            content()
        }
    }
}

internal fun resolveToggleButtonColors(themeColors: ElegantColors): ElegantToggleButtonColors =
    ElegantToggleButtonColors(
        containerColor = themeColors.surfaceRaised,
        contentColor = themeColors.textPrimary,
        borderColor = themeColors.borderDefault,
        borderWidth = 1.dp,
        selectedContainerColor = themeColors.backgroundSubtle,
        selectedContentColor = themeColors.interactivePrimary,
        selectedBorderColor = themeColors.interactivePrimary,
        hoveredContainerColor = themeColors.surfaceHover,
        pressedContainerColor = themeColors.surfaceSunken,
        disabledContainerColor = themeColors.surfaceSunken,
        disabledContentColor = themeColors.textTertiary,
        disabledBorderColor = themeColors.borderDefault,
        focusedBorderColor = themeColors.focusRing,
    )

internal fun resolveToggleButtonVisuals(
    colors: ElegantToggleButtonColors,
    enabled: Boolean,
    selected: Boolean,
    pressed: Boolean,
    hovered: Boolean,
    focused: Boolean,
): ToggleButtonVisuals {
    val container = when {
        !enabled -> colors.disabledContainerColor
        selected -> colors.selectedContainerColor
        pressed -> colors.pressedContainerColor
        hovered -> colors.hoveredContainerColor
        else -> colors.containerColor
    }
    val content = when {
        !enabled -> colors.disabledContentColor
        selected -> colors.selectedContentColor
        pressed -> colors.pressedContentColor
        hovered -> colors.hoveredContentColor
        else -> colors.contentColor
    }
    val border = when {
        !enabled -> colors.disabledBorderColor
        focused -> colors.focusedBorderColor
        selected -> colors.selectedBorderColor
        else -> colors.borderColor
    }
    return ToggleButtonVisuals(
        container = container,
        content = content,
        border = border,
        borderWidth = colors.borderWidth,
    )
}

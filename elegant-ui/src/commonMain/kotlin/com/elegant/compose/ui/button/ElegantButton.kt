package com.elegant.compose.ui.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantTheme

/** Visual-emphasis variants supported by [ElegantButton]. */
public enum class ElegantButtonStyle {
    /** Dominant action for the current task or surface. */
    Primary,

    /** Supporting action with a visible container and border. */
    Secondary,

    /** Low-emphasis action with a transparent default container. */
    Tertiary,
}

/** Visual size presets supported by [ElegantButton]. */
public enum class ElegantButtonSize {
    /** 36dp visual height inside a 48dp minimum touch target. */
    Small,

    /** 40dp visual height inside a 48dp minimum touch target. */
    Medium,

    /** 48dp visual and touch height. */
    Large,
}

/**
 * State colors and border metrics used by [ElegantButton].
 *
 * Use [ElegantButtonDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor default container color.
 * @property pressedContainerColor pressed container color.
 * @property disabledContainerColor disabled container color.
 * @property contentColor default content color.
 * @property pressedContentColor pressed content color.
 * @property disabledContentColor disabled content color.
 * @property borderColor default border color.
 * @property pressedBorderColor pressed border color.
 * @property focusedBorderColor focused border color.
 * @property disabledBorderColor disabled border color.
 * @property borderWidth default border width.
 * @property pressedBorderWidth pressed border width.
 * @property focusedBorderWidth focused border width.
 * @property disabledBorderWidth disabled border width.
 */
@Immutable
public data class ElegantButtonColors(
    val containerColor: Color,
    val pressedContainerColor: Color,
    val disabledContainerColor: Color,
    val contentColor: Color,
    val pressedContentColor: Color,
    val disabledContentColor: Color,
    val borderColor: Color,
    val pressedBorderColor: Color,
    val focusedBorderColor: Color,
    val disabledBorderColor: Color,
    val borderWidth: Dp,
    val pressedBorderWidth: Dp,
    val focusedBorderWidth: Dp,
    val disabledBorderWidth: Dp,
)

/** Theme-aware defaults for [ElegantButton]. */
public object ElegantButtonDefaults {
    /** Minimum interactive root height used by every button size. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = 120

    /** Returns theme-aware colors for [style]. */
    @Composable
    public fun colors(
        style: ElegantButtonStyle = ElegantButtonStyle.Primary,
    ): ElegantButtonColors {
        val colors = ElegantTheme.colors
        return when (style) {
            ElegantButtonStyle.Primary -> ElegantButtonColors(
                containerColor = colors.interactivePrimary,
                pressedContainerColor = colors.interactivePrimaryPressed,
                disabledContainerColor = colors.surfaceSunken,
                contentColor = colors.textInverse,
                pressedContentColor = colors.textInverse,
                disabledContentColor = colors.textTertiary,
                borderColor = Color.Transparent,
                pressedBorderColor = Color.Transparent,
                focusedBorderColor = colors.focusRing,
                disabledBorderColor = Color.Transparent,
                borderWidth = 0.dp,
                pressedBorderWidth = 0.dp,
                focusedBorderWidth = 2.dp,
                disabledBorderWidth = 0.dp,
            )

            ElegantButtonStyle.Secondary -> ElegantButtonColors(
                containerColor = colors.surfaceRaised,
                pressedContainerColor = colors.backgroundSubtle,
                disabledContainerColor = colors.surfaceSunken,
                contentColor = colors.textPrimary,
                pressedContentColor = colors.textPrimary,
                disabledContentColor = colors.textTertiary,
                borderColor = colors.borderDefault,
                pressedBorderColor = colors.borderStrong,
                focusedBorderColor = colors.focusRing,
                disabledBorderColor = colors.borderDefault,
                borderWidth = 1.dp,
                pressedBorderWidth = 1.dp,
                focusedBorderWidth = 2.dp,
                disabledBorderWidth = 1.dp,
            )

            ElegantButtonStyle.Tertiary -> ElegantButtonColors(
                containerColor = Color.Transparent,
                pressedContainerColor = colors.backgroundSubtle,
                disabledContainerColor = Color.Transparent,
                contentColor = colors.interactivePrimary,
                pressedContentColor = colors.interactivePrimary,
                disabledContentColor = colors.textTertiary,
                borderColor = Color.Transparent,
                pressedBorderColor = Color.Transparent,
                focusedBorderColor = colors.focusRing,
                disabledBorderColor = Color.Transparent,
                borderWidth = 0.dp,
                pressedBorderWidth = 0.dp,
                focusedBorderWidth = 2.dp,
                disabledBorderWidth = 0.dp,
            )
        }
    }
}

@Immutable
private data class ButtonMetrics(
    val visualHeight: Dp,
    val minWidth: Dp,
    val horizontalPadding: Dp,
    val iconSize: Dp,
    val gap: Dp,
    val shape: Shape,
)

@Immutable
private data class ButtonVisuals(
    val container: Color,
    val content: Color,
    val border: Color,
    val borderWidth: Dp,
)

/**
 * Displays an Elegant UI action button.
 *
 * @param onClick callback invoked when the button accepts an activation.
 * @param modifier modifier applied to the 48dp minimum interactive root.
 * @param enabled whether user interaction is accepted.
 * @param loading whether progress is shown and interaction is temporarily disabled.
 * @param style visual-emphasis variant.
 * @param size visual size preset.
 * @param colors state colors and border metrics.
 * @param leadingIcon optional content before the label.
 * @param trailingIcon optional content after the label.
 * @param content button label or custom content.
 */
@Composable
public fun ElegantButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    style: ElegantButtonStyle = ElegantButtonStyle.Primary,
    size: ElegantButtonSize = ElegantButtonSize.Medium,
    colors: ElegantButtonColors = ElegantButtonDefaults.colors(style),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val metrics = metricsFor(size)
    val visuals = visualsFor(
        colors = colors,
        enabled = enabled,
        pressed = pressed,
        focused = focused,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = tween(durationMillis = ElegantButtonDefaults.AnimationDurationMillis),
        label = "ElegantButtonContainer",
    )
    val animatedContent by animateColorAsState(
        targetValue = visuals.content,
        animationSpec = tween(durationMillis = ElegantButtonDefaults.AnimationDurationMillis),
        label = "ElegantButtonContent",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = tween(durationMillis = ElegantButtonDefaults.AnimationDurationMillis),
        label = "ElegantButtonBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = tween(durationMillis = ElegantButtonDefaults.AnimationDurationMillis),
        label = "ElegantButtonBorderWidth",
    )

    val interactive = enabled && !loading
    val semanticModifier = Modifier.semantics(mergeDescendants = true) {
        role = Role.Button
        if (!interactive) disabled()
        if (loading) stateDescription = "Loading"
    }

    Box(
        modifier = modifier
            .then(semanticModifier)
            .defaultMinSize(minHeight = ElegantButtonDefaults.MinimumTouchHeight)
            .clickable(
                enabled = interactive,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .focusable(
                enabled = interactive,
                interactionSource = interactionSource,
            ),
        contentAlignment = Alignment.Center,
    ) {
        val borderModifier = if (animatedBorderWidth > 0.dp) {
            Modifier.border(
                border = BorderStroke(animatedBorderWidth, animatedBorder),
                shape = metrics.shape,
            )
        } else {
            Modifier
        }

        Row(
            modifier = Modifier
                .defaultMinSize(minWidth = metrics.minWidth)
                .height(metrics.visualHeight)
                .clip(metrics.shape)
                .background(animatedContainer)
                .then(borderModifier)
                .padding(horizontal = metrics.horizontalPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CompositionLocalProvider(LocalContentColor provides animatedContent) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(metrics.iconSize),
                        color = animatedContent,
                        strokeWidth = 2.dp,
                    )
                    Spacer(Modifier.width(metrics.gap))
                } else if (leadingIcon != null) {
                    Box(Modifier.size(metrics.iconSize), contentAlignment = Alignment.Center) {
                        leadingIcon()
                    }
                    Spacer(Modifier.width(metrics.gap))
                }

                ProvideTextStyle(textStyleFor(size)) {
                    content()
                }

                if (!loading && trailingIcon != null) {
                    Spacer(Modifier.width(metrics.gap))
                    Box(Modifier.size(metrics.iconSize), contentAlignment = Alignment.Center) {
                        trailingIcon()
                    }
                }
            }
        }
    }
}

private fun metricsFor(size: ElegantButtonSize): ButtonMetrics = when (size) {
    ElegantButtonSize.Small -> ButtonMetrics(
        visualHeight = 36.dp,
        minWidth = 64.dp,
        horizontalPadding = 12.dp,
        iconSize = 16.dp,
        gap = 6.dp,
        shape = RoundedCornerShape(10.dp),
    )

    ElegantButtonSize.Medium -> ButtonMetrics(
        visualHeight = 40.dp,
        minWidth = 72.dp,
        horizontalPadding = 16.dp,
        iconSize = 18.dp,
        gap = 8.dp,
        shape = RoundedCornerShape(12.dp),
    )

    ElegantButtonSize.Large -> ButtonMetrics(
        visualHeight = 48.dp,
        minWidth = 80.dp,
        horizontalPadding = 20.dp,
        iconSize = 20.dp,
        gap = 8.dp,
        shape = RoundedCornerShape(14.dp),
    )
}

@Composable
private fun textStyleFor(size: ElegantButtonSize): TextStyle = when (size) {
    ElegantButtonSize.Small -> ElegantTheme.typography.labelSmall
    ElegantButtonSize.Medium -> ElegantTheme.typography.labelMedium
    ElegantButtonSize.Large -> ElegantTheme.typography.labelLarge
}

private fun visualsFor(
    colors: ElegantButtonColors,
    enabled: Boolean,
    pressed: Boolean,
    focused: Boolean,
): ButtonVisuals = when {
    !enabled -> ButtonVisuals(
        container = colors.disabledContainerColor,
        content = colors.disabledContentColor,
        border = colors.disabledBorderColor,
        borderWidth = colors.disabledBorderWidth,
    )

    focused -> ButtonVisuals(
        container = if (pressed) colors.pressedContainerColor else colors.containerColor,
        content = if (pressed) colors.pressedContentColor else colors.contentColor,
        border = colors.focusedBorderColor,
        borderWidth = colors.focusedBorderWidth,
    )

    pressed -> ButtonVisuals(
        container = colors.pressedContainerColor,
        content = colors.pressedContentColor,
        border = colors.pressedBorderColor,
        borderWidth = colors.pressedBorderWidth,
    )

    else -> ButtonVisuals(
        container = colors.containerColor,
        content = colors.contentColor,
        border = colors.borderColor,
        borderWidth = colors.borderWidth,
    )
}

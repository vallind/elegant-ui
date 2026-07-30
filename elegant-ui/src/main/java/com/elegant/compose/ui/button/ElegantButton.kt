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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantTheme

public enum class ElegantButtonStyle {
    Primary,
    Secondary,
    Tertiary,
}

public enum class ElegantButtonSize {
    Small,
    Medium,
    Large,
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

@Composable
public fun ElegantButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: ElegantButtonStyle = ElegantButtonStyle.Primary,
    size: ElegantButtonSize = ElegantButtonSize.Medium,
    enabled: Boolean = true,
    loading: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val metrics = metricsFor(size)
    val visuals = visualsFor(
        style = style,
        enabled = enabled,
        pressed = pressed,
        focused = focused,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = tween(durationMillis = 120),
        label = "ElegantButtonContainer",
    )
    val animatedContent by animateColorAsState(
        targetValue = visuals.content,
        animationSpec = tween(durationMillis = 120),
        label = "ElegantButtonContent",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = tween(durationMillis = 120),
        label = "ElegantButtonBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = tween(durationMillis = 120),
        label = "ElegantButtonBorderWidth",
    )

    val interactive = enabled && !loading
    val semanticModifier = Modifier.semantics(mergeDescendants = true) {
        role = Role.Button
        if (!enabled || loading) disabled()
        if (loading) stateDescription = "Loading"
    }

    Box(
        modifier = modifier
            .then(semanticModifier)
            .defaultMinSize(minHeight = 48.dp)
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

                ProvideTextStyle(MaterialTheme.typography.labelLarge) {
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
private fun visualsFor(
    style: ElegantButtonStyle,
    enabled: Boolean,
    pressed: Boolean,
    focused: Boolean,
): ButtonVisuals {
    val colors = ElegantTheme.colors

    if (!enabled) {
        return ButtonVisuals(
            container = colors.surfaceSunken,
            content = colors.textTertiary,
            border = if (style == ElegantButtonStyle.Secondary) colors.borderDefault else Color.Transparent,
            borderWidth = if (style == ElegantButtonStyle.Secondary) 1.dp else 0.dp,
        )
    }

    val focusBorder = if (focused) colors.focusRing else Color.Transparent
    val focusWidth = if (focused) 2.dp else 0.dp

    return when (style) {
        ElegantButtonStyle.Primary -> ButtonVisuals(
            container = if (pressed) colors.interactivePrimaryPressed else colors.interactivePrimary,
            content = colors.textInverse,
            border = focusBorder,
            borderWidth = focusWidth,
        )

        ElegantButtonStyle.Secondary -> ButtonVisuals(
            container = if (pressed) colors.backgroundSubtle else colors.surfaceRaised,
            content = colors.textPrimary,
            border = if (focused) colors.focusRing else if (pressed) colors.borderStrong else colors.borderDefault,
            borderWidth = if (focused) 2.dp else 1.dp,
        )

        ElegantButtonStyle.Tertiary -> ButtonVisuals(
            container = if (pressed) colors.backgroundSubtle else Color.Transparent,
            content = colors.interactivePrimary,
            border = focusBorder,
            borderWidth = focusWidth,
        )
    }
}

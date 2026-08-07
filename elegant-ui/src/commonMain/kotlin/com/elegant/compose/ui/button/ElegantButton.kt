package com.elegant.compose.ui.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.internal.action.ActionStateColors
import com.elegant.compose.ui.internal.action.ActionStateElevation
import com.elegant.compose.ui.internal.action.ActionVisuals
import com.elegant.compose.ui.internal.action.resolveActionVisuals
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.foundation.shape.resolveSquircleAwareShape
import com.elegant.compose.ui.foundation.theme.ElegantElevation
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantTheme

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
    /** 40dp visual height inside a 48dp minimum touch target. */
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
 * @property hoveredContainerColor hovered container color.
 * @property pressedContainerColor pressed container color.
 * @property disabledContainerColor disabled container color.
 * @property contentColor default content color.
 * @property hoveredContentColor hovered content color.
 * @property pressedContentColor pressed content color.
 * @property disabledContentColor disabled content color.
 * @property borderColor default border color.
 * @property hoveredBorderColor hovered border color.
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
    val hoveredContainerColor: Color = containerColor,
    val hoveredContentColor: Color = contentColor,
    val hoveredBorderColor: Color = borderColor,
)

/**
 * Elevation values used by [ElegantButton] interaction states.
 *
 * @property defaultElevation resting elevation.
 * @property hoveredElevation elevation while a pointer hovers the button.
 * @property pressedElevation elevation while the button is pressed.
 * @property focusedElevation elevation while keyboard focus is visible.
 * @property disabledElevation elevation while interaction is disabled.
 */
@Immutable
public data class ElegantButtonElevation(
    val defaultElevation: Dp,
    val hoveredElevation: Dp,
    val pressedElevation: Dp,
    val focusedElevation: Dp,
    val disabledElevation: Dp,
)

/** Theme-aware defaults for [ElegantButton]. */
public object ElegantButtonDefaults {
    /** Minimum interactive root height used by every button size. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Immediate press-response duration. */
    public const val PressAnimationDurationMillis: Int = ElegantMotion.fastDurationMillis

    /** Subtle pointer-hover scale that preserves layout dimensions. */
    public const val HoveredScale: Float = 1.008f

    /** Restrained pressed scale that preserves the 48dp interactive target. */
    public const val PressedScale: Float = 0.985f

    /** Returns theme-aware colors for [style]. */
    @Composable
    public fun colors(
        style: ElegantButtonStyle = ElegantButtonStyle.Primary,
    ): ElegantButtonColors {
        val colors = ElegantTheme.colors
        return when (style) {
            ElegantButtonStyle.Primary -> ElegantButtonColors(
                containerColor = colors.interactivePrimary,
                hoveredContainerColor = colors.interactivePrimaryHover,
                pressedContainerColor = colors.interactivePrimaryPressed,
                disabledContainerColor = colors.surfaceSunken,
                contentColor = colors.textInverse,
                hoveredContentColor = colors.textInverse,
                pressedContentColor = colors.textInverse,
                disabledContentColor = colors.textTertiary,
                borderColor = Color.Transparent,
                hoveredBorderColor = Color.Transparent,
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
                hoveredContainerColor = colors.surfaceHover,
                pressedContainerColor = colors.backgroundSubtle,
                disabledContainerColor = colors.surfaceSunken,
                contentColor = colors.textPrimary,
                hoveredContentColor = colors.textPrimary,
                pressedContentColor = colors.textPrimary,
                disabledContentColor = colors.textTertiary,
                borderColor = colors.borderDefault,
                hoveredBorderColor = colors.borderStrong,
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
                hoveredContainerColor = colors.surfaceHover,
                pressedContainerColor = colors.backgroundSubtle,
                disabledContainerColor = Color.Transparent,
                contentColor = colors.interactivePrimary,
                hoveredContentColor = colors.interactivePrimaryHover,
                pressedContentColor = colors.interactivePrimary,
                disabledContentColor = colors.textTertiary,
                borderColor = Color.Transparent,
                hoveredBorderColor = Color.Transparent,
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

    /** Returns the squircle-aware default container shape, a 16dp rounded square across sizes. */
    public fun shape(size: ElegantButtonSize = ElegantButtonSize.Medium): Shape = RoundedCornerShape(16.dp)

    /** Returns the interaction elevation model for [style]. */
    public fun elevation(
        style: ElegantButtonStyle = ElegantButtonStyle.Primary,
    ): ElegantButtonElevation = when (style) {
        ElegantButtonStyle.Primary -> ElegantButtonElevation(
            defaultElevation = ElegantElevation.low,
            hoveredElevation = ElegantElevation.medium,
            pressedElevation = ElegantElevation.none,
            focusedElevation = ElegantElevation.medium,
            disabledElevation = ElegantElevation.none,
        )

        ElegantButtonStyle.Secondary -> ElegantButtonElevation(
            defaultElevation = ElegantElevation.none,
            hoveredElevation = ElegantElevation.low,
            pressedElevation = ElegantElevation.none,
            focusedElevation = ElegantElevation.low,
            disabledElevation = ElegantElevation.none,
        )

        ElegantButtonStyle.Tertiary -> ElegantButtonElevation(
            defaultElevation = ElegantElevation.none,
            hoveredElevation = ElegantElevation.none,
            pressedElevation = ElegantElevation.none,
            focusedElevation = ElegantElevation.none,
            disabledElevation = ElegantElevation.none,
        )
    }
}

@Immutable
internal data class ButtonMetrics(
    val visualHeight: Dp,
    val minWidth: Dp,
    val horizontalPadding: Dp,
    val iconSize: Dp,
    val gap: Dp,
)

/**
 * Displays an Elegant UI action button.
 *
 * @param onClick callback invoked when the button accepts an activation.
 * @param modifier modifier applied to the 48dp minimum interactive root.
 * @param enabled whether user interaction is accepted.
 * @param loading whether progress is shown and interaction is temporarily disabled.
 * @param loadingStateDescription localized accessibility description announced while loading.
 * @param interactionSource optional hoisted interaction source for observing or controlling state.
 * @param style visual-emphasis variant.
 * @param size visual size preset.
 * @param shape optically tuned container shape.
 * @param colors state colors and border metrics.
 * @param elevation state-aware tonal elevation model.
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
    loadingStateDescription: String = "Loading",
    interactionSource: MutableInteractionSource? = null,
    style: ElegantButtonStyle = ElegantButtonStyle.Primary,
    size: ElegantButtonSize = ElegantButtonSize.Medium,
    shape: Shape = ElegantButtonDefaults.shape(size),
    colors: ElegantButtonColors = ElegantButtonDefaults.colors(style),
    elevation: ElegantButtonElevation = ElegantButtonDefaults.elevation(style),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val effectiveShape = resolveSquircleAwareShape(
        userShape = shape,
        defaultShape = ElegantButtonDefaults.shape(size),
        cornerRadius = buttonDefaultCornerRadius(size),
    )
    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    val focusRingEnabled = ElegantTheme.focusRingEnabled
    val metrics = metricsFor(size)
    val visuals = resolveButtonVisuals(
        colors = colors,
        elevation = elevation,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
        focused = focused && focusRingEnabled,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantButtonContainer",
    )
    val animatedContent by animateColorAsState(
        targetValue = visuals.content,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantButtonContent",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantButtonBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantButtonBorderWidth",
    )
    val animatedElevation by animateDpAsState(
        targetValue = visuals.elevation,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantButtonElevation",
    )
    val animatedScale by animateFloatAsState(
        targetValue = visuals.scale,
        animationSpec = elegantFolmeSpring(dampingRatio = 0.8f, responseSeconds = 0.25f),
        label = "ElegantButtonScale",
    )

    val interactive = enabled && !loading
    val semanticModifier = Modifier.semantics(mergeDescendants = true) {
        role = Role.Button
        if (!interactive) disabled()
        if (loading) stateDescription = loadingStateDescription
    }

    Box(
        modifier = modifier
            .then(semanticModifier)
            .defaultMinSize(minHeight = ElegantButtonDefaults.MinimumTouchHeight)
            .clickable(
                enabled = interactive,
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
                shape = effectiveShape,
            )
        } else {
            Modifier
        }

        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                }
                .shadow(
                    elevation = animatedElevation,
                    shape = effectiveShape,
                    clip = false,
                )
                .defaultMinSize(
                    minWidth = metrics.minWidth,
                    minHeight = metrics.visualHeight,
                )
                .clip(effectiveShape)
                .background(animatedContainer)
                .indication(
                    interactionSource = resolvedInteractionSource,
                    indication = LocalIndication.current,
                )
                .then(borderModifier)
                .padding(horizontal = metrics.horizontalPadding),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier.alpha(if (loading) 0f else 1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CompositionLocalProvider(LocalContentColor provides animatedContent) {
                    if (leadingIcon != null) {
                        Box(Modifier.size(metrics.iconSize), contentAlignment = Alignment.Center) {
                            leadingIcon()
                        }
                        Spacer(Modifier.width(metrics.gap))
                    }

                    ProvideTextStyle(textStyleFor(size)) {
                        content()
                    }

                    if (trailingIcon != null) {
                        Spacer(Modifier.width(metrics.gap))
                        Box(Modifier.size(metrics.iconSize), contentAlignment = Alignment.Center) {
                            trailingIcon()
                        }
                    }
                }
            }

            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(metrics.iconSize),
                    color = animatedContent,
                    strokeWidth = 2.dp,
                )
            }
        }
    }
}

internal fun metricsFor(size: ElegantButtonSize): ButtonMetrics = when (size) {
    ElegantButtonSize.Small -> ButtonMetrics(
        visualHeight = 40.dp,
        minWidth = 64.dp,
        horizontalPadding = 12.dp,
        iconSize = 16.dp,
        gap = 6.dp,
    )

    ElegantButtonSize.Medium -> ButtonMetrics(
        visualHeight = 40.dp,
        minWidth = 72.dp,
        horizontalPadding = 16.dp,
        iconSize = 18.dp,
        gap = 8.dp,
    )

    ElegantButtonSize.Large -> ButtonMetrics(
        visualHeight = 48.dp,
        minWidth = 80.dp,
        horizontalPadding = 20.dp,
        iconSize = 20.dp,
        gap = 8.dp,
    )
}

@Composable
private fun textStyleFor(size: ElegantButtonSize): TextStyle = when (size) {
    ElegantButtonSize.Small -> ElegantTheme.typography.labelSmall
    ElegantButtonSize.Medium -> ElegantTheme.typography.labelMedium
    ElegantButtonSize.Large -> ElegantTheme.typography.labelLarge
}

internal fun buttonDefaultCornerRadius(size: ElegantButtonSize): Dp = 16.dp

internal fun resolveButtonVisuals(
    colors: ElegantButtonColors,
    elevation: ElegantButtonElevation,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
    focused: Boolean,
): ActionVisuals = resolveActionVisuals(
    colors = ActionStateColors(
        container = colors.containerColor,
        hoveredContainer = colors.hoveredContainerColor,
        pressedContainer = colors.pressedContainerColor,
        disabledContainer = colors.disabledContainerColor,
        content = colors.contentColor,
        hoveredContent = colors.hoveredContentColor,
        pressedContent = colors.pressedContentColor,
        disabledContent = colors.disabledContentColor,
        border = colors.borderColor,
        hoveredBorder = colors.hoveredBorderColor,
        pressedBorder = colors.pressedBorderColor,
        focusedBorder = colors.focusedBorderColor,
        disabledBorder = colors.disabledBorderColor,
        borderWidth = colors.borderWidth,
        pressedBorderWidth = colors.pressedBorderWidth,
        focusedBorderWidth = colors.focusedBorderWidth,
        disabledBorderWidth = colors.disabledBorderWidth,
    ),
    elevation = ActionStateElevation(
        default = elevation.defaultElevation,
        hovered = elevation.hoveredElevation,
        pressed = elevation.pressedElevation,
        focused = elevation.focusedElevation,
        disabled = elevation.disabledElevation,
    ),
    enabled = enabled,
    pressed = pressed,
    hovered = hovered,
    focused = focused,
    hoveredScale = ElegantButtonDefaults.HoveredScale,
    pressedScale = ElegantButtonDefaults.PressedScale,
)

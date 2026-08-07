package com.elegant.compose.ui.iconbutton

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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
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

/** Visual-emphasis variants supported by [ElegantIconButton]. */
public enum class ElegantIconButtonStyle {
    /** Dominant compact action with a primary-color container. */
    Primary,

    /** Supporting compact action with a raised container and border. */
    Secondary,

    /** Low-emphasis compact action with a transparent resting container. */
    Tertiary,
}

/** Visual size presets supported by [ElegantIconButton]. */
public enum class ElegantIconButtonSize {
    /** 32dp visual container and 16dp icon inside a 48dp interaction target. */
    Small,

    /** 40dp visual container and 20dp icon inside a 48dp interaction target. */
    Medium,

    /** 48dp visual container and 24dp icon matching the interaction target. */
    Large,
}

/**
 * State colors and border metrics used by [ElegantIconButton].
 *
 * Use [ElegantIconButtonDefaults.colors] for theme-aware defaults and [copy] for intentional
 * product-level customization.
 *
 * @property containerColor resting container color.
 * @property hoveredContainerColor hovered container color.
 * @property pressedContainerColor pressed container color.
 * @property disabledContainerColor disabled container color.
 * @property contentColor resting icon color.
 * @property hoveredContentColor hovered icon color.
 * @property pressedContentColor pressed icon color.
 * @property disabledContentColor disabled icon color.
 * @property borderColor resting border color.
 * @property hoveredBorderColor hovered border color.
 * @property pressedBorderColor pressed border color.
 * @property focusedBorderColor focused border color.
 * @property disabledBorderColor disabled border color.
 * @property borderWidth resting border width.
 * @property pressedBorderWidth pressed border width.
 * @property focusedBorderWidth focused border width.
 * @property disabledBorderWidth disabled border width.
 */
@Immutable
public data class ElegantIconButtonColors(
    val containerColor: Color,
    val hoveredContainerColor: Color,
    val pressedContainerColor: Color,
    val disabledContainerColor: Color,
    val contentColor: Color,
    val hoveredContentColor: Color,
    val pressedContentColor: Color,
    val disabledContentColor: Color,
    val borderColor: Color,
    val hoveredBorderColor: Color,
    val pressedBorderColor: Color,
    val focusedBorderColor: Color,
    val disabledBorderColor: Color,
    val borderWidth: Dp,
    val pressedBorderWidth: Dp,
    val focusedBorderWidth: Dp,
    val disabledBorderWidth: Dp,
)

/**
 * Elevation values used by [ElegantIconButton] interaction states.
 *
 * @property defaultElevation resting elevation.
 * @property hoveredElevation pointer-hover elevation.
 * @property pressedElevation pressed elevation.
 * @property focusedElevation keyboard-focus elevation.
 * @property disabledElevation disabled elevation.
 */
@Immutable
public data class ElegantIconButtonElevation(
    val defaultElevation: Dp,
    val hoveredElevation: Dp,
    val pressedElevation: Dp,
    val focusedElevation: Dp,
    val disabledElevation: Dp,
)

/** Theme-aware defaults for [ElegantIconButton]. */
public object ElegantIconButtonDefaults {
    /** Minimum width and height of every interactive root. */
    public val MinimumTouchSize: Dp = 48.dp

    /** Standard hover, focus, and state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Immediate press-response duration. */
    public const val PressAnimationDurationMillis: Int = ElegantMotion.fastDurationMillis

    /** Subtle hover scale that preserves layout geometry. */
    public const val HoveredScale: Float = 1.025f

    /** Restrained pressed scale that preserves the interaction target. */
    public const val PressedScale: Float = 0.94f

    /** Returns theme-aware colors for [style]. */
    @Composable
    public fun colors(
        style: ElegantIconButtonStyle = ElegantIconButtonStyle.Tertiary,
    ): ElegantIconButtonColors {
        val colors = ElegantTheme.colors
        return when (style) {
            ElegantIconButtonStyle.Primary -> ElegantIconButtonColors(
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

            ElegantIconButtonStyle.Secondary -> ElegantIconButtonColors(
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

            ElegantIconButtonStyle.Tertiary -> ElegantIconButtonColors(
                containerColor = Color.Transparent,
                hoveredContainerColor = colors.surfaceHover,
                pressedContainerColor = colors.backgroundSubtle,
                disabledContainerColor = Color.Transparent,
                contentColor = colors.textSecondary,
                hoveredContentColor = colors.textPrimary,
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
    public fun shape(size: ElegantIconButtonSize = ElegantIconButtonSize.Medium): Shape =
        RoundedCornerShape(16.dp)

    /** Returns the interaction elevation model for [style]. */
    public fun elevation(
        style: ElegantIconButtonStyle = ElegantIconButtonStyle.Tertiary,
    ): ElegantIconButtonElevation = when (style) {
        ElegantIconButtonStyle.Primary -> ElegantIconButtonElevation(
            defaultElevation = ElegantElevation.low,
            hoveredElevation = ElegantElevation.medium,
            pressedElevation = ElegantElevation.none,
            focusedElevation = ElegantElevation.medium,
            disabledElevation = ElegantElevation.none,
        )

        ElegantIconButtonStyle.Secondary -> ElegantIconButtonElevation(
            defaultElevation = ElegantElevation.none,
            hoveredElevation = ElegantElevation.low,
            pressedElevation = ElegantElevation.none,
            focusedElevation = ElegantElevation.low,
            disabledElevation = ElegantElevation.none,
        )

        ElegantIconButtonStyle.Tertiary -> ElegantIconButtonElevation(
            defaultElevation = ElegantElevation.none,
            hoveredElevation = ElegantElevation.none,
            pressedElevation = ElegantElevation.none,
            focusedElevation = ElegantElevation.none,
            disabledElevation = ElegantElevation.none,
        )
    }
}

@Immutable
internal data class IconButtonMetrics(
    val visualSize: Dp,
    val iconSize: Dp,
)

/**
 * Displays an Elegant UI icon-only action.
 *
 * The required [contentDescription] names the action for accessibility services; decorative icons
 * inside [content] should therefore use a null description.
 *
 * @param onClick callback invoked when the icon button accepts an activation.
 * @param contentDescription localized accessibility name for the action.
 * @param modifier modifier applied to the 48dp minimum interactive root.
 * @param enabled whether user interaction is accepted.
 * @param loading whether progress is shown and interaction is temporarily disabled.
 * @param loadingStateDescription localized accessibility description announced while loading.
 * @param interactionSource optional hoisted interaction source for observing interaction state.
 * @param style visual-emphasis variant.
 * @param size visual container and icon size preset.
 * @param shape optically tuned container shape.
 * @param colors state colors and border metrics.
 * @param elevation state-aware elevation model.
 * @param content icon content rendered at the size owned by [size].
 */
@Composable
public fun ElegantIconButton(
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingStateDescription: String = "Loading",
    interactionSource: MutableInteractionSource? = null,
    style: ElegantIconButtonStyle = ElegantIconButtonStyle.Tertiary,
    size: ElegantIconButtonSize = ElegantIconButtonSize.Medium,
    shape: Shape = ElegantIconButtonDefaults.shape(size),
    colors: ElegantIconButtonColors = ElegantIconButtonDefaults.colors(style),
    elevation: ElegantIconButtonElevation = ElegantIconButtonDefaults.elevation(style),
    content: @Composable () -> Unit,
) {
    val effectiveShape = resolveSquircleAwareShape(
        userShape = shape,
        defaultShape = ElegantIconButtonDefaults.shape(size),
        cornerRadius = iconButtonDefaultCornerRadius(size),
    )
    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    val focusRingEnabled = ElegantTheme.focusRingEnabled
    val metrics = iconButtonMetricsFor(size)
    val visuals = resolveIconButtonVisuals(
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
        label = "ElegantIconButtonContainer",
    )
    val animatedContent by animateColorAsState(
        targetValue = visuals.content,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantIconButtonContent",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantIconButtonBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantIconButtonBorderWidth",
    )
    val animatedElevation by animateDpAsState(
        targetValue = visuals.elevation,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantIconButtonElevation",
    )
    val animatedScale by animateFloatAsState(
        targetValue = visuals.scale,
        animationSpec = elegantFolmeSpring(dampingRatio = 0.8f, responseSeconds = 0.25f),
        label = "ElegantIconButtonScale",
    )

    val interactive = enabled && !loading
    val semanticModifier = Modifier.semantics(mergeDescendants = true) {
        this.contentDescription = contentDescription
        role = Role.Button
        if (!interactive) disabled()
        if (loading) stateDescription = loadingStateDescription
    }

    Box(
        modifier = modifier
            .then(semanticModifier)
            .defaultMinSize(
                minWidth = ElegantIconButtonDefaults.MinimumTouchSize,
                minHeight = ElegantIconButtonDefaults.MinimumTouchSize,
            )
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
                .size(metrics.visualSize)
                .clip(effectiveShape)
                .background(animatedContainer)
                .indication(
                    interactionSource = resolvedInteractionSource,
                    indication = LocalIndication.current,
                )
                .then(borderModifier),
            contentAlignment = Alignment.Center,
        ) {
            CompositionLocalProvider(LocalContentColor provides animatedContent) {
                Box(
                    modifier = Modifier
                        .size(metrics.iconSize)
                        .alpha(if (loading) 0f else 1f),
                    contentAlignment = Alignment.Center,
                ) {
                    content()
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

internal fun iconButtonMetricsFor(size: ElegantIconButtonSize): IconButtonMetrics = when (size) {
    ElegantIconButtonSize.Small -> IconButtonMetrics(
        visualSize = 32.dp,
        iconSize = 16.dp,
    )

    ElegantIconButtonSize.Medium -> IconButtonMetrics(
        visualSize = 40.dp,
        iconSize = 20.dp,
    )

    ElegantIconButtonSize.Large -> IconButtonMetrics(
        visualSize = 48.dp,
        iconSize = 24.dp,
    )
}

internal fun iconButtonDefaultCornerRadius(size: ElegantIconButtonSize): Dp = 16.dp

internal fun resolveIconButtonVisuals(
    colors: ElegantIconButtonColors,
    elevation: ElegantIconButtonElevation,
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
    hoveredScale = ElegantIconButtonDefaults.HoveredScale,
    pressedScale = ElegantIconButtonDefaults.PressedScale,
)

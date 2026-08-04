package com.elegant.compose.ui.card

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.shape.resolveSquircleAwareShape
import com.elegant.compose.ui.theme.ElegantElevation
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantTheme

/** Visual variants supported by [ElegantCard]. */
public enum class ElegantCardStyle {
    /** Default surface container without a border or shadow. */
    Filled,

    /** Raised container with a visible border for secondary grouping. */
    Outlined,

    /** Raised container with a resting tonal shadow for prominent content. */
    Elevated,
}

/**
 * Theme-aware state colors used by [ElegantCard].
 *
 * Use [ElegantCardDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor default container color.
 * @property contentColor default content color, provided to [ElegantCard] content through
 * [LocalContentColor].
 * @property borderColor default border color; transparent for styles without a resting border.
 * @property hoveredContainerColor hovered container color.
 * @property pressedContainerColor pressed container color.
 * @property disabledContainerColor disabled container color.
 * @property focusedBorderColor keyboard focus-ring color.
 */
@Immutable
public data class ElegantCardColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color,
    val hoveredContainerColor: Color = containerColor,
    val pressedContainerColor: Color = containerColor,
    val disabledContainerColor: Color = containerColor,
    val focusedBorderColor: Color = borderColor,
)

/** Theme-aware defaults for [ElegantCard]. */
public object ElegantCardDefaults {
    /** Minimum interactive root height used by clickable cards. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors for [style]. */
    @Composable
    public fun colors(
        style: ElegantCardStyle = ElegantCardStyle.Filled,
    ): ElegantCardColors = resolveCardColors(
        style = style,
        themeColors = ElegantTheme.colors,
    )

    /** Returns the shared 16dp rounded shape used by every [style]. */
    public fun shape(style: ElegantCardStyle = ElegantCardStyle.Filled): Shape =
        RoundedCornerShape(ElegantRadius.lg)

    /** Returns the resting shadow elevation for [style]. */
    public fun elevation(style: ElegantCardStyle = ElegantCardStyle.Filled): Dp =
        resolveCardElevation(style)
}

@Immutable
internal data class CardVisuals(
    val container: Color,
    val content: Color,
    val border: Color,
    val borderWidth: Dp,
    val elevation: Dp,
)

/**
 * Groups content on an optional interactive surface.
 *
 * A card without [onClick] is non-interactive: it renders only the container, border, and shadow,
 * keeps the semantics of [content], and supports no focus. Passing [onClick] turns the card into a
 * button-like surface with a 48dp minimum interactive root, a merged [Role.Button] label, a visible
 * focus ring, hover and press color feedback, and a disabled state. Elevation drops to none while
 * pressed or disabled and stays at the resting value for hover and focus.
 *
 * The card adds no internal padding; the caller owns spacing inside [content]. Card content receives
 * [colors]'s content color through [LocalContentColor].
 *
 * @param onClick optional activation callback; null keeps the card non-interactive.
 * @param onLongPress optional long-press callback; enables combined click handling.
 * @param modifier modifier applied once to the card root.
 * @param enabled whether user interaction is accepted.
 * @param style visual variant.
 * @param shape clipping, border, and shadow shape.
 * @param colors theme-aware state colors.
 * @param elevation resting shadow elevation.
 * @param holdDownState forces the pressed visual state while true.
 * @param content card content; padding is the caller's responsibility.
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
public fun ElegantCard(
    onClick: (() -> Unit)? = null,
    onLongPress: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: ElegantCardStyle = ElegantCardStyle.Filled,
    shape: Shape = ElegantCardDefaults.shape(style),
    colors: ElegantCardColors = ElegantCardDefaults.colors(style),
    elevation: Dp = ElegantCardDefaults.elevation(style),
    holdDownState: Boolean = false,
    content: @Composable () -> Unit,
) {
    val effectiveShape = resolveSquircleAwareShape(
        userShape = shape,
        defaultShape = ElegantCardDefaults.shape(style),
        cornerRadius = ElegantRadius.lg,
    )
    val interactive = onClick != null
    val resolvedInteractionSource = remember { MutableInteractionSource() }
    if (holdDownState) {
        DisposableEffect(Unit) {
            val press = PressInteraction.Press(Offset.Zero)
            resolvedInteractionSource.tryEmit(press)
            onDispose { resolvedInteractionSource.tryEmit(PressInteraction.Release(press)) }
        }
    }
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    val visuals = resolveCardVisuals(
        colors = colors,
        style = style,
        elevation = elevation,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
        focused = focused,
        interactive = interactive,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = tween(
            durationMillis = ElegantCardDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantCardContainer",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = tween(
            durationMillis = ElegantCardDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantCardBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = tween(
            durationMillis = ElegantCardDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantCardBorderWidth",
    )
    val animatedElevation by animateDpAsState(
        targetValue = visuals.elevation,
        animationSpec = tween(
            durationMillis = ElegantCardDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantCardElevation",
    )

    val semanticModifier = if (interactive) {
        Modifier.semantics(mergeDescendants = true) {
            role = Role.Button
            if (!enabled) disabled()
        }
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .then(semanticModifier)
            .defaultMinSize(
                minHeight = if (interactive) {
                    ElegantCardDefaults.MinimumTouchHeight
                } else {
                    0.dp
                },
            )
            .combinedClickable(
                enabled = interactive && enabled,
                role = if (interactive) Role.Button else null,
                interactionSource = resolvedInteractionSource,
                indication = null,
                onClick = { onClick?.invoke() },
                onLongClick = {
                    onLongPress?.invoke()
                },
            ),
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
                .fillMaxSize()
                .shadow(
                    elevation = animatedElevation,
                    shape = effectiveShape,
                    clip = false,
                )
                .clip(effectiveShape)
                .background(animatedContainer)
                .indication(
                    interactionSource = resolvedInteractionSource,
                    indication = if (interactive) {
                        ripple(color = visuals.content)
                    } else {
                        null
                    },
                )
                .then(borderModifier),
        ) {
            CompositionLocalProvider(LocalContentColor provides visuals.content) {
                content()
            }
        }
    }
}

internal fun resolveCardColors(
    style: ElegantCardStyle,
    themeColors: ElegantColors,
): ElegantCardColors = when (style) {
    ElegantCardStyle.Filled -> ElegantCardColors(
        containerColor = themeColors.surfaceDefault,
        contentColor = themeColors.textPrimary,
        borderColor = Color.Transparent,
        hoveredContainerColor = themeColors.surfaceHover,
        pressedContainerColor = themeColors.backgroundSubtle,
        disabledContainerColor = themeColors.surfaceSunken,
        focusedBorderColor = themeColors.focusRing,
    )

    ElegantCardStyle.Outlined -> ElegantCardColors(
        containerColor = themeColors.surfaceRaised,
        contentColor = themeColors.textPrimary,
        borderColor = themeColors.borderDefault,
        hoveredContainerColor = themeColors.surfaceHover,
        pressedContainerColor = themeColors.backgroundSubtle,
        disabledContainerColor = themeColors.surfaceSunken,
        focusedBorderColor = themeColors.focusRing,
    )

    ElegantCardStyle.Elevated -> ElegantCardColors(
        containerColor = themeColors.surfaceRaised,
        contentColor = themeColors.textPrimary,
        borderColor = Color.Transparent,
        hoveredContainerColor = themeColors.surfaceHover,
        pressedContainerColor = themeColors.backgroundSubtle,
        disabledContainerColor = themeColors.surfaceSunken,
        focusedBorderColor = themeColors.focusRing,
    )
}

internal fun resolveCardElevation(style: ElegantCardStyle): Dp = when (style) {
    ElegantCardStyle.Filled,
    ElegantCardStyle.Outlined,
    -> ElegantElevation.none

    ElegantCardStyle.Elevated -> ElegantElevation.medium
}

internal fun resolveCardVisuals(
    colors: ElegantCardColors,
    style: ElegantCardStyle,
    elevation: Dp,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
    focused: Boolean,
    interactive: Boolean,
): CardVisuals {
    val baseBorderWidth = if (style == ElegantCardStyle.Outlined) 1.dp else 0.dp
    val container = when {
        !enabled -> colors.disabledContainerColor
        pressed && interactive -> colors.pressedContainerColor
        hovered && interactive -> colors.hoveredContainerColor
        else -> colors.containerColor
    }
    val border = when {
        focused && interactive -> colors.focusedBorderColor
        else -> colors.borderColor
    }
    val borderWidth = when {
        focused && interactive -> 2.dp
        else -> baseBorderWidth
    }
    val resolvedElevation = when {
        !enabled -> 0.dp
        pressed && interactive -> 0.dp
        else -> elevation
    }

    return CardVisuals(
        container = container,
        content = colors.contentColor,
        border = border,
        borderWidth = borderWidth,
        elevation = resolvedElevation,
    )
}

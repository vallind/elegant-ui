package com.elegant.compose.ui.internal.action

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Immutable
internal data class ActionStateColors(
    val container: Color,
    val hoveredContainer: Color,
    val pressedContainer: Color,
    val disabledContainer: Color,
    val content: Color,
    val hoveredContent: Color,
    val pressedContent: Color,
    val disabledContent: Color,
    val border: Color,
    val hoveredBorder: Color,
    val pressedBorder: Color,
    val focusedBorder: Color,
    val disabledBorder: Color,
    val borderWidth: Dp,
    val pressedBorderWidth: Dp,
    val focusedBorderWidth: Dp,
    val disabledBorderWidth: Dp,
)

@Immutable
internal data class ActionStateElevation(
    val default: Dp,
    val hovered: Dp,
    val pressed: Dp,
    val focused: Dp,
    val disabled: Dp,
)

@Immutable
internal data class ActionVisuals(
    val container: Color,
    val content: Color,
    val border: Color,
    val borderWidth: Dp,
    val elevation: Dp,
    val scale: Float,
)

internal fun resolveActionVisuals(
    colors: ActionStateColors,
    elevation: ActionStateElevation,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
    focused: Boolean,
    hoveredScale: Float,
    pressedScale: Float,
): ActionVisuals {
    val container = when {
        !enabled -> colors.disabledContainer
        pressed -> colors.pressedContainer
        hovered -> colors.hoveredContainer
        else -> colors.container
    }
    val content = when {
        !enabled -> colors.disabledContent
        pressed -> colors.pressedContent
        hovered -> colors.hoveredContent
        else -> colors.content
    }
    val border = when {
        !enabled -> colors.disabledBorder
        focused -> colors.focusedBorder
        pressed -> colors.pressedBorder
        hovered -> colors.hoveredBorder
        else -> colors.border
    }
    val borderWidth = when {
        !enabled -> colors.disabledBorderWidth
        focused -> colors.focusedBorderWidth
        pressed -> colors.pressedBorderWidth
        else -> colors.borderWidth
    }
    val resolvedElevation = when {
        !enabled -> elevation.disabled
        pressed -> elevation.pressed
        focused -> elevation.focused
        hovered -> elevation.hovered
        else -> elevation.default
    }
    val scale = when {
        !enabled -> 1f
        pressed -> pressedScale
        hovered -> hoveredScale
        else -> 1f
    }

    return ActionVisuals(
        container = container,
        content = content,
        border = border,
        borderWidth = borderWidth,
        elevation = resolvedElevation,
        scale = scale,
    )
}

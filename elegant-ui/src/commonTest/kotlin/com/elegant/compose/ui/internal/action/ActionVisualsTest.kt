package com.elegant.compose.ui.internal.action

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ActionVisualsTest {
    @Test
    fun statePriorityIsSharedAcrossActionComponents() {
        val pressedAndFocused = resolveActionVisuals(
            colors = colors,
            elevation = elevation,
            enabled = true,
            pressed = true,
            hovered = true,
            focused = true,
            hoveredScale = 1.02f,
            pressedScale = 0.95f,
        )

        assertEquals(colors.pressedContainer, pressedAndFocused.container)
        assertEquals(colors.pressedContent, pressedAndFocused.content)
        assertEquals(colors.focusedBorder, pressedAndFocused.border)
        assertEquals(colors.focusedBorderWidth, pressedAndFocused.borderWidth)
        assertEquals(elevation.pressed, pressedAndFocused.elevation)
        assertEquals(0.95f, pressedAndFocused.scale)
    }

    @Test
    fun disabledStateSuppressesInteractionFeedback() {
        val disabled = resolveActionVisuals(
            colors = colors,
            elevation = elevation,
            enabled = false,
            pressed = true,
            hovered = true,
            focused = true,
            hoveredScale = 1.02f,
            pressedScale = 0.95f,
        )

        assertEquals(colors.disabledContainer, disabled.container)
        assertEquals(colors.disabledContent, disabled.content)
        assertEquals(colors.disabledBorder, disabled.border)
        assertEquals(colors.disabledBorderWidth, disabled.borderWidth)
        assertEquals(elevation.disabled, disabled.elevation)
        assertEquals(1f, disabled.scale)
    }

    private companion object {
        val colors = ActionStateColors(
            container = Color(0xFF000001),
            hoveredContainer = Color(0xFF000002),
            pressedContainer = Color(0xFF000003),
            disabledContainer = Color(0xFF000004),
            content = Color(0xFF000005),
            hoveredContent = Color(0xFF000006),
            pressedContent = Color(0xFF000007),
            disabledContent = Color(0xFF000008),
            border = Color(0xFF000009),
            hoveredBorder = Color(0xFF00000A),
            pressedBorder = Color(0xFF00000B),
            focusedBorder = Color(0xFF00000C),
            disabledBorder = Color(0xFF00000D),
            borderWidth = 1.dp,
            pressedBorderWidth = 2.dp,
            focusedBorderWidth = 3.dp,
            disabledBorderWidth = 4.dp,
        )

        val elevation = ActionStateElevation(
            default = 1.dp,
            hovered = 2.dp,
            pressed = 3.dp,
            focused = 4.dp,
            disabled = 5.dp,
        )
    }
}

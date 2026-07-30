package com.elegant.compose.ui.iconbutton

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ElegantIconButtonContractTest {
    @Test
    fun iconButtonStylesRemainStable() {
        assertEquals(
            listOf("Primary", "Secondary", "Tertiary"),
            ElegantIconButtonStyle.entries.map(ElegantIconButtonStyle::name),
        )
    }

    @Test
    fun iconButtonSizesRemainStable() {
        assertEquals(
            listOf("Small", "Medium", "Large"),
            ElegantIconButtonSize.entries.map(ElegantIconButtonSize::name),
        )
    }

    @Test
    fun sizeMetricsPreserveCompactVisualsInsideTouchTarget() {
        assertEquals(
            IconButtonMetrics(visualSize = 32.dp, iconSize = 16.dp),
            iconButtonMetricsFor(ElegantIconButtonSize.Small),
        )
        assertEquals(
            IconButtonMetrics(visualSize = 40.dp, iconSize = 20.dp),
            iconButtonMetricsFor(ElegantIconButtonSize.Medium),
        )
        assertEquals(
            IconButtonMetrics(visualSize = 48.dp, iconSize = 24.dp),
            iconButtonMetricsFor(ElegantIconButtonSize.Large),
        )
        assertEquals(48.dp, ElegantIconButtonDefaults.MinimumTouchSize)
    }

    @Test
    fun hoveredStateUsesIconButtonFeedback() {
        val visuals = resolveIconButtonVisuals(
            colors = testColors,
            elevation = testElevation,
            enabled = true,
            pressed = false,
            hovered = true,
            focused = false,
        )

        assertEquals(testColors.hoveredContainerColor, visuals.container)
        assertEquals(testColors.hoveredContentColor, visuals.content)
        assertEquals(testColors.hoveredBorderColor, visuals.border)
        assertEquals(testElevation.hoveredElevation, visuals.elevation)
        assertEquals(ElegantIconButtonDefaults.HoveredScale, visuals.scale)
    }

    @Test
    fun focusRingRemainsVisibleDuringPress() {
        val visuals = resolveIconButtonVisuals(
            colors = testColors,
            elevation = testElevation,
            enabled = true,
            pressed = true,
            hovered = true,
            focused = true,
        )

        assertEquals(testColors.pressedContainerColor, visuals.container)
        assertEquals(testColors.pressedContentColor, visuals.content)
        assertEquals(testColors.focusedBorderColor, visuals.border)
        assertEquals(testColors.focusedBorderWidth, visuals.borderWidth)
        assertEquals(testElevation.pressedElevation, visuals.elevation)
        assertEquals(ElegantIconButtonDefaults.PressedScale, visuals.scale)
    }

    @Test
    fun disabledStateOverridesEveryInteraction() {
        val visuals = resolveIconButtonVisuals(
            colors = testColors,
            elevation = testElevation,
            enabled = false,
            pressed = true,
            hovered = true,
            focused = true,
        )

        assertEquals(testColors.disabledContainerColor, visuals.container)
        assertEquals(testColors.disabledContentColor, visuals.content)
        assertEquals(testColors.disabledBorderColor, visuals.border)
        assertEquals(testColors.disabledBorderWidth, visuals.borderWidth)
        assertEquals(testElevation.disabledElevation, visuals.elevation)
        assertEquals(1f, visuals.scale)
    }

    private companion object {
        val testColors = ElegantIconButtonColors(
            containerColor = Color(0xFF000001),
            hoveredContainerColor = Color(0xFF000002),
            pressedContainerColor = Color(0xFF000003),
            disabledContainerColor = Color(0xFF000004),
            contentColor = Color(0xFF000005),
            hoveredContentColor = Color(0xFF000006),
            pressedContentColor = Color(0xFF000007),
            disabledContentColor = Color(0xFF000008),
            borderColor = Color(0xFF000009),
            hoveredBorderColor = Color(0xFF00000A),
            pressedBorderColor = Color(0xFF00000B),
            focusedBorderColor = Color(0xFF00000C),
            disabledBorderColor = Color(0xFF00000D),
            borderWidth = 1.dp,
            pressedBorderWidth = 2.dp,
            focusedBorderWidth = 3.dp,
            disabledBorderWidth = 4.dp,
        )

        val testElevation = ElegantIconButtonElevation(
            defaultElevation = 1.dp,
            hoveredElevation = 2.dp,
            pressedElevation = 3.dp,
            focusedElevation = 4.dp,
            disabledElevation = 5.dp,
        )
    }
}

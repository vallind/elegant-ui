package com.elegant.compose.ui.button

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ElegantButtonContractTest {
    @Test
    fun buttonStylesRemainStable() {
        assertEquals(
            listOf("Primary", "Secondary", "Tertiary"),
            ElegantButtonStyle.entries.map(ElegantButtonStyle::name),
        )
    }

    @Test
    fun buttonSizesRemainStable() {
        assertEquals(
            listOf("Small", "Medium", "Large"),
            ElegantButtonSize.entries.map(ElegantButtonSize::name),
        )
    }

    @Test
    fun sizeMetricsRemainOpticallyTuned() {
        assertEquals(
            ButtonMetrics(
                visualHeight = 36.dp,
                minWidth = 64.dp,
                horizontalPadding = 12.dp,
                iconSize = 16.dp,
                gap = 6.dp,
            ),
            metricsFor(ElegantButtonSize.Small),
        )
        assertEquals(
            ButtonMetrics(
                visualHeight = 40.dp,
                minWidth = 72.dp,
                horizontalPadding = 16.dp,
                iconSize = 18.dp,
                gap = 8.dp,
            ),
            metricsFor(ElegantButtonSize.Medium),
        )
        assertEquals(
            ButtonMetrics(
                visualHeight = 48.dp,
                minWidth = 80.dp,
                horizontalPadding = 20.dp,
                iconSize = 20.dp,
                gap = 8.dp,
            ),
            metricsFor(ElegantButtonSize.Large),
        )
    }

    @Test
    fun hoveredStateResolvesTonalFeedback() {
        val visuals = resolveButtonVisuals(
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
        assertEquals(ElegantButtonDefaults.HoveredScale, visuals.scale)
    }

    @Test
    fun pressedStateWinsWhileFocusedRingRemainsVisible() {
        val visuals = resolveButtonVisuals(
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
        assertEquals(ElegantButtonDefaults.PressedScale, visuals.scale)
    }

    @Test
    fun disabledStateOverridesAllInteractions() {
        val visuals = resolveButtonVisuals(
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
        val testColors = ElegantButtonColors(
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

        val testElevation = ElegantButtonElevation(
            defaultElevation = 1.dp,
            hoveredElevation = 2.dp,
            pressedElevation = 3.dp,
            focusedElevation = 4.dp,
            disabledElevation = 5.dp,
        )
    }
}

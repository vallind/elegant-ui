package com.elegant.compose.ui.numberpicker

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantNumberPickerContractTest {

    @Test
    fun colorsResolveStepperRolesFromTheTheme() {
        val light = resolveNumberPickerColors(ElegantLightColors)
        val dark = resolveNumberPickerColors(ElegantDarkColors)

        assertEquals(Color.Transparent, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.textSecondary, light.secondaryContentColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledContentColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredContainerColor)
        assertEquals(ElegantLightColors.backgroundSubtle, light.pressedContainerColor)
        assertEquals(ElegantLightColors.borderDefault, light.dividerColor)
        assertNotEquals(light, dark, "colors must follow the active theme")
    }

    @Test
    fun colorsDataClassDefaultsHoveredAndPressedToTheContainerColor() {
        val colors = ElegantNumberPickerColors(
            containerColor = Color.Red,
            contentColor = Color.Green,
            secondaryContentColor = Color.Blue,
            disabledContentColor = Color.Gray,
            dividerColor = Color.White,
        )

        assertEquals(Color.Red, colors.hoveredContainerColor)
        assertEquals(Color.Red, colors.pressedContainerColor)
    }

    @Test
    fun stepResolutionCoercesNonPositiveStepsToTheDefault() {
        assertEquals(1, resolveStep(0))
        assertEquals(1, resolveStep(-3))
        assertEquals(1, resolveStep(Int.MIN_VALUE))
        assertEquals(1, resolveStep(1))
        assertEquals(4, resolveStep(4))
    }

    @Test
    fun stepResultMovesWithinTheRange() {
        assertEquals(6, stepResult(4, 2, 0, 10, 1))
        assertEquals(2, stepResult(4, 2, 0, 10, -1))
        assertEquals(5, stepResult(4, 1, 0, 10, 1))
    }

    @Test
    fun stepResultStopsAtTheRangeBoundaries() {
        assertEquals(10, stepResult(10, 2, 0, 10, 1))
        assertEquals(10, stepResult(9, 2, 0, 10, 1))
        assertEquals(0, stepResult(0, 2, 0, 10, -1))
        assertEquals(0, stepResult(1, 2, 0, 10, -1))
    }

    @Test
    fun stepResultCoercesOverstepsBackIntoTheRange() {
        assertEquals(0, stepResult(-1, 2, 0, 10, -1))
        assertEquals(10, stepResult(11, 2, 0, 10, 1))
        assertEquals(0, stepResult(-2, 1, 0, 10, -1))
        assertEquals(10, stepResult(12, 3, 0, 10, 1))
    }

    @Test
    fun stepResultGuardsIntOverflow() {
        assertEquals(Int.MAX_VALUE, stepResult(Int.MAX_VALUE, 1, 0, Int.MAX_VALUE, 1))
        assertEquals(Int.MIN_VALUE, stepResult(Int.MIN_VALUE, 1, Int.MIN_VALUE, 0, -1))
        assertEquals(
            Int.MAX_VALUE,
            stepResult(Int.MAX_VALUE - 1, 4, Int.MIN_VALUE, Int.MAX_VALUE, 1),
        )
        assertEquals(
            Int.MIN_VALUE,
            stepResult(Int.MIN_VALUE + 1, 4, Int.MIN_VALUE, Int.MAX_VALUE, -1),
        )
    }

    @Test
    fun stepResultLeavesInvertedRangesUncoerced() {
        assertEquals(Int.MAX_VALUE, stepResult(Int.MAX_VALUE, 1, 10, 5, 1))
        assertEquals(Int.MIN_VALUE, stepResult(Int.MIN_VALUE, 1, 10, 5, -1))
        assertEquals(8, stepResult(7, 1, 10, 5, 1))
        assertEquals(6, stepResult(7, 1, 10, 5, -1))
    }

    @Test
    fun stepResultWithNoDirectionReturnsTheValue() {
        assertEquals(4, stepResult(4, 2, 0, 10, 0))
    }

    @Test
    fun boundaryChecksFollowEnabledAndLimitState() {
        assertTrue(canIncrease(4, 10, true))
        assertFalse(canIncrease(10, 10, true))
        assertFalse(canIncrease(11, 10, true))
        assertFalse(canIncrease(4, 10, false))
        assertFalse(canIncrease(Int.MAX_VALUE, Int.MAX_VALUE, true))
        assertTrue(canDecrease(4, 0, true))
        assertFalse(canDecrease(0, 0, true))
        assertFalse(canDecrease(-1, 0, true))
        assertFalse(canDecrease(4, 0, false))
        assertFalse(canDecrease(Int.MIN_VALUE, Int.MIN_VALUE, true))
    }

    @Test
    fun boundaryChecksTreatInvertedRangesAsUnbounded() {
        assertTrue(canIncrease(7, Int.MAX_VALUE, true))
        assertTrue(canDecrease(7, Int.MIN_VALUE, true))
        assertFalse(canIncrease(Int.MAX_VALUE, Int.MAX_VALUE, true))
        assertFalse(canDecrease(Int.MIN_VALUE, Int.MIN_VALUE, true))
    }

    @Test
    fun buttonVisualsFollowDisabledPressedHoveredRestingPrecedence() {
        val colors = resolveNumberPickerColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            pressed: Boolean = false,
            hovered: Boolean = false,
        ) = resolveNumberPickerStepButtonVisuals(
            colors = colors,
            enabled = enabled,
            pressed = pressed,
            hovered = hovered,
        )

        assertEquals(colors.containerColor, visuals().container)
        assertEquals(colors.secondaryContentColor, visuals().content)
        assertEquals(colors.hoveredContainerColor, visuals(hovered = true).container)
        assertEquals(
            colors.pressedContainerColor,
            visuals(pressed = true, hovered = true).container,
        )
        assertEquals(colors.pressedContainerColor, visuals(pressed = true).container)
        assertEquals(colors.containerColor, visuals(enabled = false).container)
        assertEquals(colors.disabledContentColor, visuals(enabled = false).content)
        assertEquals(
            colors.containerColor,
            visuals(enabled = false, pressed = true, hovered = true).container,
        )
        assertEquals(
            colors.disabledContentColor,
            visuals(enabled = false, pressed = true, hovered = true).content,
        )
    }

    @Test
    fun defaultsExposeTouchHeightButtonSizeAndRepeatMetrics() {
        assertTrue(ElegantNumberPickerDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(40.dp, ElegantNumberPickerDefaults.ButtonSize)
        assertEquals(96.dp, NumberPickerMinimumWidth)
        assertEquals(16.dp, NumberPickerChevronSize)
        assertEquals(350L, NumberPickerRepeatInitialDelayMillis)
        assertEquals(80L, NumberPickerRepeatIntervalMillis)
    }
}

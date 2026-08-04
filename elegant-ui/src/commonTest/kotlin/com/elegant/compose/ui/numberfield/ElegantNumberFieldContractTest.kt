package com.elegant.compose.ui.numberfield

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantNumberFieldContractTest {

    @Test
    fun colorsResolveFilledInputRolesFromTheTheme() {
        val light = resolveNumberFieldColors(ElegantLightColors)
        val dark = resolveNumberFieldColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceSunken, light.containerColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredContainerColor)
        assertEquals(ElegantLightColors.surfaceRaised, light.focusedContainerColor)
        assertEquals(ElegantLightColors.surfaceSunken, light.disabledContainerColor)
        assertEquals(Color.Transparent, light.borderColor)
        assertEquals(Color.Transparent, light.hoveredBorderColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
        assertEquals(ElegantLightColors.statusCritical, light.errorBorderColor)
        assertEquals(Color.Transparent, light.disabledBorderColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledContentColor)
        assertEquals(ElegantLightColors.textTertiary, light.placeholderColor)
        assertEquals(ElegantLightColors.textSecondary, light.labelColor)
        assertEquals(ElegantLightColors.textSecondary, light.supportingTextColor)
        assertEquals(ElegantLightColors.statusCritical, light.errorTextColor)
        assertNotEquals(light, dark, "colors must follow the active theme")
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
    fun parseDraftRejectsEmptyMinusAndOverflowInput() {
        assertNull(parseDraft(""))
        assertNull(parseDraft("   "))
        assertNull(parseDraft("-"))
        assertNull(parseDraft(" - "))
        assertNull(parseDraft("12a"))
        assertNull(parseDraft("99999999999999999999"))
        assertNull(parseDraft("-99999999999999999999"))
    }

    @Test
    fun parseDraftAcceptsValidSignedIntegersWithWhitespace() {
        assertEquals(0, parseDraft("0"))
        assertEquals(12, parseDraft("12"))
        assertEquals(-7, parseDraft("-7"))
        assertEquals(42, parseDraft(" 42 "))
        assertEquals(Int.MAX_VALUE, parseDraft("2147483647"))
        assertEquals(Int.MIN_VALUE, parseDraft("-2147483648"))
    }

    @Test
    fun rangeCheckRejectsValuesOutsideTheRangeAndInvertedRanges() {
        assertTrue(isWithinRange(5, 0, 10))
        assertTrue(isWithinRange(0, 0, 10))
        assertTrue(isWithinRange(10, 0, 10))
        assertFalse(isWithinRange(11, 0, 10))
        assertFalse(isWithinRange(-1, 0, 10))
        assertFalse(isWithinRange(5, 10, 0))
    }

    @Test
    fun visualsFollowDisabledErrorFocusedHoveredRestingPrecedence() {
        val colors = resolveNumberFieldColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            hovered: Boolean = false,
            focused: Boolean = false,
            isError: Boolean = false,
        ) = resolveNumberFieldVisuals(
            colors = colors,
            enabled = enabled,
            hovered = hovered,
            focused = focused,
            isError = isError,
        )

        assertEquals(colors.disabledContainerColor, visuals(enabled = false).container)
        assertEquals(colors.disabledBorderColor, visuals(enabled = false, isError = true).border)
        assertEquals(1.dp, visuals(enabled = false, isError = true).borderWidth)
        assertEquals(colors.errorBorderColor, visuals(isError = true, focused = true).border)
        assertEquals(2.dp, visuals(isError = true, focused = true).borderWidth)
        assertEquals(
            colors.focusedContainerColor,
            visuals(focused = true, hovered = true).container,
        )
        assertEquals(colors.focusedBorderColor, visuals(focused = true, hovered = true).border)
        assertEquals(2.dp, visuals(focused = true).borderWidth)
        assertEquals(colors.hoveredBorderColor, visuals(hovered = true).border)
        assertEquals(1.dp, visuals().borderWidth)
        assertEquals(colors.containerColor, visuals().container)
    }

    @Test
    fun stepButtonVisualsFollowTertiaryInteractionRoles() {
        val resting = resolveStepButtonVisuals(
            ElegantLightColors,
            enabled = true,
            pressed = false,
            hovered = false,
            focused = false,
        )
        val hovered = resolveStepButtonVisuals(
            ElegantLightColors,
            enabled = true,
            pressed = false,
            hovered = true,
            focused = false,
        )
        val pressed = resolveStepButtonVisuals(
            ElegantLightColors,
            enabled = true,
            pressed = true,
            hovered = false,
            focused = false,
        )
        val focused = resolveStepButtonVisuals(
            ElegantLightColors,
            enabled = true,
            pressed = false,
            hovered = false,
            focused = true,
        )
        val disabled = resolveStepButtonVisuals(
            ElegantLightColors,
            enabled = false,
            pressed = false,
            hovered = false,
            focused = false,
        )

        assertEquals(Color.Transparent, resting.container)
        assertEquals(ElegantLightColors.textSecondary, resting.content)
        assertEquals(ElegantLightColors.surfaceHover, hovered.container)
        assertEquals(ElegantLightColors.textPrimary, hovered.content)
        assertEquals(ElegantLightColors.backgroundSubtle, pressed.container)
        assertEquals(ElegantLightColors.interactivePrimary, pressed.content)
        assertEquals(ElegantLightColors.focusRing, focused.border)
        assertEquals(2.dp, focused.borderWidth)
        assertEquals(Color.Transparent, disabled.container)
        assertEquals(ElegantLightColors.textTertiary, disabled.content)
        assertEquals(Color.Transparent, disabled.border)
        assertEquals(0.dp, disabled.borderWidth)
    }

    @Test
    fun defaultsExposeStepTouchHeightDurationAndStepButtonMetrics() {
        assertTrue(ElegantNumberFieldDefaults.Step == 1)
        assertTrue(ElegantNumberFieldDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantNumberFieldDefaults.AnimationDurationMillis,
        )
        assertTrue(NumberFieldStepButtonTargetSize >= 48.dp)
        assertEquals(32.dp, NumberFieldStepButtonVisualSize)
        assertEquals(16.dp, NumberFieldStepButtonIconSize)
    }
}

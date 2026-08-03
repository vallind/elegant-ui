package com.elegant.compose.ui.daterangepicker

import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.calendar.ElegantDate
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantDateRangePickerContractTest {

    @Test
    fun colorsFollowFilledInputRoles() {
        val light = resolveDateRangePickerColors(ElegantLightColors)
        val dark = resolveDateRangePickerColors(ElegantDarkColors)
        assertEquals(ElegantLightColors.surfaceSunken, light.containerColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredContainerColor)
        assertEquals(ElegantLightColors.surfaceRaised, light.focusedContainerColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
        assertEquals(ElegantLightColors.statusCritical, light.errorBorderColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.textTertiary, light.placeholderColor)
        assertEquals(ElegantLightColors.textSecondary, light.labelColor)
        assertTrue(light.containerColor != dark.containerColor)
        assertTrue(light.contentColor != dark.contentColor)
    }

    @Test
    fun visualsFollowStatePrecedence() {
        val colors = resolveDateRangePickerColors(ElegantLightColors)
        val disabled = resolveDateRangePickerVisuals(
            colors = colors,
            enabled = false,
            hovered = true,
            focused = true,
            isError = true,
        )
        assertEquals(colors.disabledContainerColor, disabled.container)
        assertEquals(colors.disabledBorderColor, disabled.border)
        assertEquals(DateRangePickerMetrics.RestingBorderWidth, disabled.borderWidth)
        val error = resolveDateRangePickerVisuals(
            colors = colors,
            enabled = true,
            hovered = false,
            focused = true,
            isError = true,
        )
        assertEquals(colors.errorBorderColor, error.border)
        assertEquals(DateRangePickerMetrics.FocusBorderWidth, error.borderWidth)
        val focused = resolveDateRangePickerVisuals(
            colors = colors,
            enabled = true,
            hovered = false,
            focused = true,
            isError = false,
        )
        assertEquals(colors.focusedContainerColor, focused.container)
        assertEquals(colors.focusedBorderColor, focused.border)
        val hovered = resolveDateRangePickerVisuals(
            colors = colors,
            enabled = true,
            hovered = true,
            focused = false,
            isError = false,
        )
        assertEquals(colors.hoveredContainerColor, hovered.container)
        assertEquals(DateRangePickerMetrics.RestingBorderWidth, hovered.borderWidth)
        val resting = resolveDateRangePickerVisuals(
            colors = colors,
            enabled = true,
            hovered = false,
            focused = false,
            isError = false,
        )
        assertEquals(colors.containerColor, resting.container)
        assertEquals(colors.borderColor, resting.border)
    }

    @Test
    fun formatDateCopyZeroPads() {
        assertEquals("2026-08-15", formatDateCopy(ElegantDate(2026, 8, 15)))
        assertEquals("2026-01-01", formatDateCopy(ElegantDate(2026, 1, 1)))
    }

    @Test
    fun rangeLabelCoversAllEndpointCombinations() {
        assertNull(rangeLabel(ElegantDateRange(null, null)))
        assertEquals(
            "2026-08-01 —",
            rangeLabel(ElegantDateRange(ElegantDate(2026, 8, 1), null)),
        )
        assertEquals(
            "2026-08-01 — 2026-08-15",
            rangeLabel(ElegantDateRange(ElegantDate(2026, 8, 1), ElegantDate(2026, 8, 15))),
        )
        assertEquals(
            "— 2026-08-15",
            rangeLabel(ElegantDateRange(null, ElegantDate(2026, 8, 15))),
        )
    }

    @Test
    fun isCompleteRequiresBothEndpoints() {
        assertFalse(isComplete(ElegantDateRange(null, null)))
        assertFalse(isComplete(ElegantDateRange(ElegantDate(2026, 8, 1), null)))
        assertTrue(isComplete(ElegantDateRange(ElegantDate(2026, 8, 1), ElegantDate(2026, 8, 15))))
    }

    @Test
    fun firstClickSetsStart() {
        assertEquals(
            ElegantDateRange(ElegantDate(2026, 8, 1), null),
            advanceRange(ElegantDateRange(null, null), ElegantDate(2026, 8, 1)),
        )
    }

    @Test
    fun secondClickAfterStartSetsEnd() {
        val current = ElegantDateRange(ElegantDate(2026, 8, 1), null)
        assertEquals(
            ElegantDateRange(ElegantDate(2026, 8, 1), ElegantDate(2026, 8, 15)),
            advanceRange(current, ElegantDate(2026, 8, 15)),
        )
    }

    @Test
    fun secondClickBeforeStartRepicksStart() {
        val current = ElegantDateRange(ElegantDate(2026, 8, 15), null)
        assertEquals(
            ElegantDateRange(ElegantDate(2026, 8, 1), null),
            advanceRange(current, ElegantDate(2026, 8, 1)),
        )
    }

    @Test
    fun secondClickOnStartCreatesSingleDayRange() {
        val date = ElegantDate(2026, 8, 15)
        assertEquals(
            ElegantDateRange(date, date),
            advanceRange(ElegantDateRange(date, null), date),
        )
    }

    @Test
    fun clickAfterCompleteRangeRestarts() {
        val current = ElegantDateRange(ElegantDate(2026, 8, 1), ElegantDate(2026, 8, 15))
        assertEquals(
            ElegantDateRange(ElegantDate(2026, 9, 1), null),
            advanceRange(current, ElegantDate(2026, 9, 1)),
        )
    }

    @Test
    fun pairMonthSteppingWrapsYears() {
        assertEquals(2026 to 9, nextPairMonth(2026 to 8))
        assertEquals(2026 to 7, prevPairMonth(2026 to 8))
        assertEquals(2027 to 1, nextPairMonth(2026 to 12))
        assertEquals(2025 to 12, prevPairMonth(2026 to 1))
    }

    @Test
    fun pairNavigationClampsToMonthBounds() {
        assertEquals(5, clampPairIndex(5, 5, 8))
        assertEquals(5, clampPairIndex(3, 5, 8))
        assertEquals(7, clampPairIndex(20, 5, 8))
        assertEquals(10, clampPairIndex(10, null, null))
        assertEquals(7, clampPairIndex(10, null, 8))
        assertEquals(5, clampPairIndex(3, 5, null))
    }

    @Test
    fun pairNavigationRespectsBoundsAtEdges() {
        assertTrue(canNavigatePairBack(6, 5))
        assertFalse(canNavigatePairBack(5, 5))
        assertTrue(canNavigatePairBack(4, null))
        assertTrue(canNavigatePairForward(6, 8))
        assertFalse(canNavigatePairForward(7, 8))
        assertTrue(canNavigatePairForward(4, null))
    }

    @Test
    fun defaultsExposeMinimumTouchHeight() {
        assertEquals(48.dp, ElegantDateRangePickerDefaults.MinimumTouchHeight)
    }
}

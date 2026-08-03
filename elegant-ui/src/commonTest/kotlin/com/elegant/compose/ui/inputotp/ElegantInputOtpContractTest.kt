package com.elegant.compose.ui.inputotp

import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantInputOtpContractTest {

    @Test
    fun colorsResolveFromTheActiveTheme() {
        val light = resolveInputOtpColors(ElegantLightColors)
        val dark = resolveInputOtpColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceSunken, light.cellContainerColor)
        assertEquals(ElegantLightColors.borderDefault, light.cellBorderColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedCellBorderColor)
        assertEquals(ElegantLightColors.statusCritical, light.errorCellBorderColor)
        assertEquals(ElegantLightColors.surfaceSunken, light.disabledCellContainerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledContentColor)
        assertEquals(ElegantLightColors.statusCritical, light.errorTextColor)
        assertNotEquals(light, dark, "colors must follow the active theme")
    }

    @Test
    fun valueCoercionKeepsOnlyDigitsAndTruncatesAtLength() {
        assertEquals("", coercedValue("", 6))
        assertEquals("123456", coercedValue("123456", 6))
        assertEquals("123456", coercedValue("1234567", 6))
        assertEquals("123", coercedValue("12a3b", 6))
        assertEquals("", coercedValue("abc", 6))
        assertEquals("123", coercedValue("1-2-3", 6))
        assertEquals("", coercedValue("123", 0))
    }

    @Test
    fun cellCharReturnsDigitOnlyForInRangeIndexes() {
        assertEquals('1', cellChar("123", 0))
        assertEquals('3', cellChar("123", 2))
        assertNull(cellChar("123", 3))
        assertNull(cellChar("123", -1))
        assertNull(cellChar("", 0))
    }

    @Test
    fun caretIndexPointsAtTheNextEmptyCellAndClampsWhenFull() {
        assertEquals(0, caretIndex("", 6))
        assertEquals(3, caretIndex("123", 6))
        assertEquals(5, caretIndex("123456", 6))
        assertEquals(5, caretIndex("1234567", 6))
        assertEquals(0, caretIndex("123", 0))
    }

    @Test
    fun focusedCellIndexClampsToValidCellBounds() {
        assertEquals(0, focusedCellIndex(0, 6))
        assertEquals(2, focusedCellIndex(2, 6))
        assertEquals(5, focusedCellIndex(9, 6))
        assertEquals(0, focusedCellIndex(-3, 6))
        assertEquals(0, focusedCellIndex(4, 1))
        assertEquals(0, focusedCellIndex(4, 0))
    }

    @Test
    fun defaultsExposeAccessibleCellsAndSixDigitDefaultLength() {
        assertTrue(ElegantInputOtpDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(48.dp, ElegantInputOtpDefaults.CellSize)
        assertEquals(8.dp, ElegantInputOtpDefaults.CellGap)
        assertEquals(6, ElegantInputOtpDefaults.DefaultLength)
    }

    @Test
    fun caretMetricsStayWithinCellBounds() {
        assertTrue(InputOtpCaretStrokeWidth > 0.dp)
        assertTrue(InputOtpCaretHeight > 0.dp)
        assertTrue(InputOtpCaretHeight < ElegantInputOtpDefaults.CellSize)
    }
}

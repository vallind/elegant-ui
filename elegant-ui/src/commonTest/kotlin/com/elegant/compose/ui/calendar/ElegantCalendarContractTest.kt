package com.elegant.compose.ui.calendar

import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ElegantCalendarContractTest {

    @Test
    fun leapYearRuleCoversCenturyBoundaries() {
        assertTrue(isLeapYear(2000))
        assertTrue(isLeapYear(2024))
        assertFalse(isLeapYear(1900))
        assertFalse(isLeapYear(2100))
        assertFalse(isLeapYear(2023))
    }

    @Test
    fun daysInMonthMatchesCivilCalendar() {
        assertEquals(31, daysInMonth(2026, 1))
        assertEquals(28, daysInMonth(2026, 2))
        assertEquals(29, daysInMonth(2024, 2))
        assertEquals(31, daysInMonth(2026, 3))
        assertEquals(30, daysInMonth(2026, 4))
        assertEquals(31, daysInMonth(2026, 5))
        assertEquals(30, daysInMonth(2026, 6))
        assertEquals(31, daysInMonth(2026, 7))
        assertEquals(31, daysInMonth(2026, 8))
        assertEquals(30, daysInMonth(2026, 9))
        assertEquals(31, daysInMonth(2026, 10))
        assertEquals(30, daysInMonth(2026, 11))
        assertEquals(31, daysInMonth(2026, 12))
        assertEquals(31, daysInMonth(2026, 0))
        assertEquals(31, daysInMonth(2026, 13))
    }

    @Test
    fun weekdayOffsetMatchesKnownDates() {
        assertEquals(5, firstWeekdayOffset(2000, 1))
        assertEquals(0, firstWeekdayOffset(2024, 1))
        assertEquals(5, firstWeekdayOffset(2026, 8))
        assertEquals(2, firstWeekdayOffset(2026, 7))
    }

    @Test
    fun daysSinceAnchorMatchesKnownSpans() {
        assertEquals(0, daysSinceAnchor(2000, 1, 1))
        assertEquals(31, daysSinceAnchor(2000, 2, 1))
        assertEquals(366, daysSinceAnchor(2001, 1, 1))
        assertEquals(8766, daysSinceAnchor(2024, 1, 1))
        assertEquals(9709, daysSinceAnchor(2026, 8, 1))
    }

    @Test
    fun dateGridProduces42CellsWithKnownAlignment() {
        val grid = dateGrid(2026, 8)
        assertEquals(42, grid.size)
        assertEquals(-4, grid[0])
        assertEquals(1, grid[5])
        assertEquals(2, grid[6])
        assertEquals(32, grid[36])
        assertEquals(33, grid[37])
    }

    @Test
    fun gridDayDateResolvesMonthWrapInBothDirections() {
        assertEquals(ElegantDate(2026, 7, 27), gridDayDate(2026, 8, -4))
        assertEquals(ElegantDate(2026, 8, 1), gridDayDate(2026, 8, 1))
        assertEquals(ElegantDate(2026, 9, 1), gridDayDate(2026, 8, 32))
        assertEquals(ElegantDate(2026, 12, 31), gridDayDate(2026, 12, 31))
        assertEquals(ElegantDate(2027, 1, 1), gridDayDate(2026, 12, 32))
        assertEquals(ElegantDate(2026, 1, 27), gridDayDate(2026, 2, -4))
        assertEquals(ElegantDate(2025, 12, 27), gridDayDate(2026, 1, -4))
    }

    @Test
    fun formattingProducesZeroPaddedStrings() {
        assertEquals("2026-08", formatMonth(2026, 8))
        assertEquals("2026-01", formatMonth(2026, 1))
        assertEquals("2026-08-15", formatDate(ElegantDate(2026, 8, 15)))
        assertEquals("2026-01-01", formatDate(ElegantDate(2026, 1, 1)))
    }

    @Test
    fun monthIndexRoundTrips() {
        assertEquals(monthIndex(2026, 1), monthIndex(2026, 1))
        assertEquals(2026 to 8, dateFromMonthIndex(monthIndex(2026, 8)))
        assertEquals(2027 to 1, dateFromMonthIndex(monthIndex(2026, 12) + 1))
        assertEquals(2025 to 12, dateFromMonthIndex(monthIndex(2026, 1) - 1))
    }

    @Test
    fun rangeChecksHandleNullAndEdges() {
        val date = ElegantDate(2026, 8, 15)
        assertTrue(isInRange(date, null, null))
        assertTrue(isInRange(date, ElegantDate(2026, 8, 15), ElegantDate(2026, 8, 15)))
        assertTrue(isInRange(date, ElegantDate(2026, 1, 1), ElegantDate(2026, 12, 31)))
        assertFalse(isInRange(date, ElegantDate(2026, 8, 16), null))
        assertFalse(isInRange(date, null, ElegantDate(2026, 8, 14)))
    }

    @Test
    fun adjacentMonthDetectionUsesMonthLength() {
        assertTrue(isAdjacentMonth(-4, 31))
        assertTrue(isAdjacentMonth(0, 31))
        assertTrue(isAdjacentMonth(32, 31))
        assertFalse(isAdjacentMonth(1, 31))
        assertFalse(isAdjacentMonth(31, 31))
        assertTrue(isAdjacentMonth(29, 28))
    }

    @Test
    fun elegantDateOrderingIsChronological() {
        assertTrue(ElegantDate(2026, 1, 1) < ElegantDate(2026, 1, 2))
        assertTrue(ElegantDate(2026, 1, 31) < ElegantDate(2026, 2, 1))
        assertTrue(ElegantDate(2025, 12, 31) < ElegantDate(2026, 1, 1))
        assertEquals(0, ElegantDate(2026, 8, 15).compareTo(ElegantDate(2026, 8, 15)))
    }

    @Test
    fun calendarColorsFollowThemeRoles() {
        val light = resolveCalendarColors(ElegantLightColors)
        val dark = resolveCalendarColors(ElegantDarkColors)
        assertEquals(ElegantLightColors.interactivePrimary, light.selectedDayContainerColor)
        assertEquals(ElegantLightColors.textInverse, light.selectedDayColor)
        assertTrue(light.dayColor != dark.dayColor)
    }

    @Test
    fun navigationClampingStaysWithinBounds() {
        val min = monthIndex(2026, 1)
        val max = monthIndex(2026, 12)
        assertEquals(min, clampMonthIndex(min - 5, min, max))
        assertEquals(max, clampMonthIndex(max + 5, min, max))
        assertEquals(monthIndex(2026, 6), clampMonthIndex(monthIndex(2026, 6), min, max))
        assertFalse(canNavigate(-1, min, min, max))
        assertFalse(canNavigate(1, max, min, max))
        assertTrue(canNavigate(1, min, min, max))
    }
}

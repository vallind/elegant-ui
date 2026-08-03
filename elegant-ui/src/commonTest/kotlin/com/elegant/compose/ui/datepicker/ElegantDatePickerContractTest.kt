package com.elegant.compose.ui.datepicker

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.calendar.ElegantDate
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantDatePickerContractTest {

    @Test
    fun colorsResolveFilledRolesFromTheActiveTheme() {
        val light = resolveDatePickerColors(ElegantLightColors)
        val dark = resolveDatePickerColors(ElegantDarkColors)

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
    fun dateCopyFormatsWithZeroPaddingForMonthAndDay() {
        assertEquals("2026-08-05", formatDateCopy(ElegantDate(2026, 8, 5)))
        assertEquals("2026-01-01", formatDateCopy(ElegantDate(2026, 1, 1)))
        assertEquals("2026-11-30", formatDateCopy(ElegantDate(2026, 11, 30)))
        assertEquals("2026-08-03", formatDateCopy(ElegantDate(2026, 8, 3)))
        assertEquals("0026-01-01", formatDateCopy(ElegantDate(26, 1, 1)))
    }

    @Test
    fun fieldLabelResolvesNullAndFormattedDates() {
        assertNull(fieldLabel(null))
        assertEquals("2026-08-05", fieldLabel(ElegantDate(2026, 8, 5)))
        assertEquals("2026-01-01", fieldLabel(ElegantDate(2026, 1, 1)))
    }

    @Test
    fun visualsFollowDisabledErrorFocusedHoveredRestingPrecedence() {
        val colors = resolveDatePickerColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            hovered: Boolean = false,
            focused: Boolean = false,
            isError: Boolean = false,
        ) = resolveDatePickerVisuals(
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
    fun popupPositionPlacesBelowAnchorAndClampsIntoWindow() {
        val anchor = IntRect(left = 10, top = 20, right = 210, bottom = 68)
        val popupSize = IntSize(width = 320, height = 380)
        val window = IntSize(width = 800, height = 600)

        assertEquals(
            IntOffset(x = 10, y = 72),
            datePickerPopupPosition(
                anchorBounds = anchor,
                popupSize = popupSize,
                offsetPx = 4,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 0, y = 0),
            datePickerPopupPosition(
                anchorBounds = anchor,
                popupSize = popupSize,
                offsetPx = 4,
                windowSize = IntSize(width = 100, height = 50),
            ),
        )
        assertEquals(
            IntOffset(x = 10, y = 220),
            datePickerPopupPosition(
                anchorBounds = IntRect(left = 10, top = 500, right = 210, bottom = 548),
                popupSize = popupSize,
                offsetPx = 4,
                windowSize = window,
            ),
        )
    }

    @Test
    fun defaultsExposeAccessibleTouchTargetAndMetrics() {
        assertEquals(48.dp, ElegantDatePickerDefaults.MinimumTouchHeight)
        assertTrue(ElegantDatePickerDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(4.dp, DatePickerMetrics.AnchorOffset)
        assertEquals(14.dp, DatePickerMetrics.FieldHorizontalPadding)
        assertEquals(1.dp, DatePickerMetrics.RestingBorderWidth)
        assertEquals(2.dp, DatePickerMetrics.FocusBorderWidth)
        assertEquals(18.dp, DatePickerMetrics.CalendarGlyphSize)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            DatePickerMetrics.AnimationDurationMillis,
        )
    }
}

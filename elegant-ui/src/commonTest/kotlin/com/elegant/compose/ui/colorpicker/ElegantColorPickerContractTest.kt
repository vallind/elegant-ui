package com.elegant.compose.ui.colorpicker

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantColorPickerContractTest {

    @Test
    fun defaultPaletteHoldsSixteenUniqueColors() {
        val palette = ElegantColorPickerDefaults.palette()

        assertEquals(16, palette.size)
        assertEquals(palette.size, palette.distinct().size)
    }

    @Test
    fun defaultPaletteLeadsWithSaturatedChromaticColorsAndEndsWithTints() {
        val palette = ElegantColorPickerDefaults.palette()

        assertEquals(Color(0xFFEF4444), palette[0])
        assertEquals(Color(0xFFEC4899), palette[7])
        assertEquals(Color(0xFFFECACA), palette[8])
        assertEquals(Color(0xFFFBCFE8), palette[15])
    }

    @Test
    fun colorHexFormatsSixDigitUppercaseHex() {
        assertEquals("#000000", colorHex(Color(0xFF000000)))
        assertEquals("#FFFFFF", colorHex(Color(0xFFFFFFFF)))
        assertEquals("#12ABEF", colorHex(Color(0xFF12ABEF)))
    }

    @Test
    fun colorHexDropsTheAlphaChannel() {
        assertEquals("#123456", colorHex(Color(0x55123456)))
    }

    @Test
    fun colorHexRoundTripsEveryPaletteColor() {
        ElegantColorPickerDefaults.palette().forEach { color ->
            val hex = colorHex(color)
            assertEquals(7, hex.length)
            assertTrue(hex.startsWith("#"))
            assertTrue(hex.substring(1).all { it.isDigit() || it in 'A'..'F' })
        }
    }

    @Test
    fun isSelectedComparesByComponents() {
        assertTrue(isSelected(Color(0xFFEF4444), Color(0xFFEF4444)))
        assertFalse(isSelected(Color(0xFFEF4444), Color(0xFFF97316)))
    }

    @Test
    fun colorsResolveFromThemeRoles() {
        val light = resolveColorPickerColors(ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceHover, light.containerColor)
        assertEquals(ElegantLightColors.borderDefault, light.borderColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.selectedBorderColor)
        assertEquals(light.containerColor, light.hoveredContainerColor)
    }

    @Test
    fun darkColorsResolveFromTheSameThemeRoles() {
        val dark = resolveColorPickerColors(ElegantDarkColors)

        assertEquals(ElegantDarkColors.surfaceHover, dark.containerColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.borderColor)
        assertEquals(ElegantDarkColors.interactivePrimary, dark.selectedBorderColor)
        assertEquals(dark.containerColor, dark.hoveredContainerColor)
    }

    @Test
    fun colorsFollowTheActiveTheme() {
        assertNotEquals(
            resolveColorPickerColors(ElegantLightColors),
            resolveColorPickerColors(ElegantDarkColors),
        )
    }

    @Test
    fun swatchVisualsFollowDisabledSelectedHoveredRestingPrecedence() {
        val colors = resolveColorPickerColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            selected: Boolean = false,
            hovered: Boolean = false,
        ) = resolveColorSwatchVisuals(
            colors = colors,
            enabled = enabled,
            selected = selected,
            hovered = hovered,
        )

        val disabled = visuals(enabled = false)
        assertEquals(colors.borderColor, disabled.ringColor)
        assertEquals(1.dp, disabled.ringWidth)
        assertEquals(0.4f, disabled.fillAlpha)

        val disabledSelected = visuals(enabled = false, selected = true, hovered = true)
        assertEquals(disabled.ringColor, disabledSelected.ringColor)
        assertEquals(1.dp, disabledSelected.ringWidth)

        assertEquals(colors.selectedBorderColor, visuals(selected = true, hovered = true).ringColor)
        assertEquals(2.dp, visuals(selected = true).ringWidth)

        assertEquals(colors.hoveredContainerColor, visuals(hovered = true).ringColor)
        assertEquals(1.dp, visuals(hovered = true).ringWidth)
        assertEquals(1f, visuals(hovered = true).fillAlpha)

        assertEquals(colors.borderColor, visuals().ringColor)
        assertEquals(1.dp, visuals().ringWidth)
        assertEquals(1f, visuals().fillAlpha)
    }

    @Test
    fun defaultsExposeCuratedSwatchGeometry() {
        assertEquals(32.dp, ElegantColorPickerDefaults.SwatchSize)
        assertEquals(8.dp, ElegantColorPickerDefaults.SwatchGap)
        assertTrue(ColorPickerMinimumTouchHeight >= 48.dp)
    }
}

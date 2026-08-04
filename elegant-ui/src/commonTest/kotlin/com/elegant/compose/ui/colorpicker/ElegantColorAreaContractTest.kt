package com.elegant.compose.ui.colorpicker

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantColorAreaContractTest {

    @Test
    fun rgbToHsvResolvesPureColors() {
        assertEquals(0f, rgbToHsv(Color(0xFFFF0000)).hue, 0.0001f)
        assertEquals(120f, rgbToHsv(Color(0xFF00FF00)).hue, 0.0001f)
        assertEquals(240f, rgbToHsv(Color(0xFF0000FF)).hue, 0.0001f)

        assertEquals(1f, rgbToHsv(Color(0xFFFF0000)).saturation, 0.0001f)
        assertEquals(1f, rgbToHsv(Color(0xFF00FF00)).value, 0.0001f)

        val black = rgbToHsv(Color(0xFF000000))
        assertEquals(0f, black.hue, 0.0001f)
        assertEquals(0f, black.saturation, 0.0001f)
        assertEquals(0f, black.value, 0.0001f)

        val white = rgbToHsv(Color(0xFFFFFFFF))
        assertEquals(0f, white.hue, 0.0001f)
        assertEquals(0f, white.saturation, 0.0001f)
        assertEquals(1f, white.value, 0.0001f)
    }

    @Test
    fun rgbToHsvResolvesAchromaticColorsWithZeroSaturationAndHue() {
        val grey = rgbToHsv(Color(0xFF808080))

        assertEquals(0f, grey.hue, 0.0001f)
        assertEquals(0f, grey.saturation, 0.0001f)
        assertEquals(0.5019608f, grey.value, 0.0001f)
    }

    @Test
    fun hsvToRgbResolvesPureHues() {
        assertEquals(Color(0xFFFF0000), hsvToRgb(HsvColor(0f, 1f, 1f)))
        assertEquals(Color(0xFF00FF00), hsvToRgb(HsvColor(120f, 1f, 1f)))
        assertEquals(Color(0xFF0000FF), hsvToRgb(HsvColor(240f, 1f, 1f)))
        assertEquals(Color(0xFFFF0000), hsvToRgb(HsvColor(360f, 1f, 1f)))
    }

    @Test
    fun hsvToRgbResolvesCyanAndMagenta() {
        assertEquals(Color(0xFF00FFFF), hsvToRgb(HsvColor(180f, 1f, 1f)))
        assertEquals(Color(0xFFFF00FF), hsvToRgb(HsvColor(300f, 1f, 1f)))
    }

    @Test
    fun hueToRgbResolvesTheSpectrumEndpoints() {
        assertEquals(Color(0xFFFF0000), hueToRgb(0f))
        assertEquals(Color(0xFFFFFF00), hueToRgb(60f))
        assertEquals(Color(0xFF00FF00), hueToRgb(120f))
        assertEquals(Color(0xFF00FFFF), hueToRgb(180f))
        assertEquals(Color(0xFF0000FF), hueToRgb(240f))
        assertEquals(Color(0xFFFF00FF), hueToRgb(300f))
        assertEquals(Color(0xFFFF0000), hueToRgb(360f))
    }

    @Test
    fun rgbHsvRoundTripsAcrossPureAndCuratedColors() {
        val samples = listOf(
            Color(0xFFFF0000),
            Color(0xFF00FF00),
            Color(0xFF0000FF),
            Color(0xFF000000),
            Color(0xFFFFFFFF),
            Color(0xFF8B5CF6),
        ) + ElegantColorPickerDefaults.palette()

        samples.forEach { color ->
            val roundTrip = hsvToRgb(rgbToHsv(color))
            assertEquals(color.red, roundTrip.red, 0.01f)
            assertEquals(color.green, roundTrip.green, 0.01f)
            assertEquals(color.blue, roundTrip.blue, 0.01f)
        }
    }

    @Test
    fun clampHsvClampsEveryChannel() {
        assertEquals(HsvColor(0f, 0f, 1f), clampHsv(HsvColor(-45f, -0.5f, 2f)))
        assertEquals(HsvColor(360f, 1f, 0f), clampHsv(HsvColor(400f, 1.5f, -1f)))
    }

    @Test
    fun clampHsvLeavesValidChannelsUntouched() {
        assertEquals(HsvColor(90f, 0.5f, 0.5f), clampHsv(HsvColor(90f, 0.5f, 0.5f)))
    }

    @Test
    fun panelColorKeepsTheHueAndSetsSaturationAndValue() {
        val red = panelColor(Color(0xFFFF0000), 0.5f, 0.5f)
        assertEquals(0.5f, red.red, 0.01f)
        assertEquals(0.25f, red.green, 0.01f)
        assertEquals(0.25f, red.blue, 0.01f)
        assertEquals(0f, rgbToHsv(red).hue, 0.001f)

        val green = panelColor(Color(0xFF00FF00), 0.25f, 0.75f)
        assertEquals(0.1875f, green.red, 0.01f)
        assertEquals(0.25f, green.green, 0.01f)
        assertEquals(0.1875f, green.blue, 0.01f)
        assertEquals(120f, rgbToHsv(green).hue, 0.001f)
    }

    @Test
    fun panelColorClampsOutOfRangeFractions() {
        val clamped = panelColor(Color(0xFFFF0000), 2f, -1f)

        assertEquals(Color(0xFFFF0000), clamped)
    }

    @Test
    fun thumbPositionResolvesPureColors() {
        assertEquals(Offset(1f, 0f), thumbPosition(Color(0xFFFF0000)))
        assertEquals(Offset(0f, 0f), thumbPosition(Color(0xFFFFFFFF)))
        assertEquals(Offset(0f, 1f), thumbPosition(Color(0xFF000000)))
    }

    @Test
    fun thumbPositionUsesTheInverseValue() {
        val position = thumbPosition(Color(0xFF800000))

        assertEquals(1f, position.x, 0.001f)
        assertEquals(0.498f, position.y, 0.001f)
    }

    @Test
    fun areaFractionFromPositionMapsPositions() {
        assertEquals(Offset(0f, 0f), areaFractionFromPosition(0f, 0f, 220f, 160f))
        assertEquals(Offset(1f, 1f), areaFractionFromPosition(220f, 160f, 220f, 160f))
        assertEquals(Offset(0.5f, 0.5f), areaFractionFromPosition(110f, 80f, 220f, 160f))
    }

    @Test
    fun areaFractionFromPositionClampsOutsidePositions() {
        assertEquals(Offset(0f, 1f), areaFractionFromPosition(-20f, 200f, 220f, 160f))
        assertEquals(Offset(1f, 0f), areaFractionFromPosition(500f, -40f, 220f, 160f))
    }

    @Test
    fun areaFractionFromPositionGuardsNonPositiveBounds() {
        assertEquals(Offset(0f, 0f), areaFractionFromPosition(50f, 50f, 0f, 0f))
    }

    @Test
    fun defaultsExposePanelGeometry() {
        assertEquals(220.dp, ElegantColorAreaDefaults.Width)
        assertEquals(160.dp, ElegantColorAreaDefaults.Height)
        assertEquals(16.dp, ElegantColorAreaDefaults.ThumbSize)
    }

    @Test
    fun colorsResolveFromThemeRoles() {
        val light = resolveColorAreaColors(ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceSunken, light.containerColor)
        assertEquals(ElegantLightColors.borderDefault, light.borderColor)
        assertEquals(ElegantLightColors.surfaceRaised, light.thumbColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
    }

    @Test
    fun darkColorsResolveFromTheSameThemeRoles() {
        val dark = resolveColorAreaColors(ElegantDarkColors)

        assertEquals(ElegantDarkColors.surfaceSunken, dark.containerColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.borderColor)
        assertEquals(ElegantDarkColors.surfaceRaised, dark.thumbColor)
        assertEquals(ElegantDarkColors.focusRing, dark.focusedBorderColor)
    }

    @Test
    fun colorsFollowTheActiveTheme() {
        assertNotEquals(
            resolveColorAreaColors(ElegantLightColors),
            resolveColorAreaColors(ElegantDarkColors),
        )
    }
}

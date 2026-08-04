package com.elegant.compose.ui.colorpicker

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantHueSliderContractTest {

    @Test
    fun hueFromPositionMapsZeroMidpointAndFull() {
        assertEquals(0f, hueFromPosition(0f, 200f))
        assertEquals(90f, hueFromPosition(50f, 200f))
        assertEquals(180f, hueFromPosition(100f, 200f))
        assertEquals(360f, hueFromPosition(200f, 200f))
    }

    @Test
    fun hueFromPositionClampsAndGuardsNonPositiveWidth() {
        assertEquals(0f, hueFromPosition(-50f, 200f))
        assertEquals(360f, hueFromPosition(300f, 200f))
        assertEquals(0f, hueFromPosition(50f, 0f))
    }

    @Test
    fun hueGradientColorsHoldsSevenSpectrumStops() {
        val stops = hueGradientColors()

        assertEquals(7, stops.size)
        assertEquals(
            listOf(
                Color(0xFFFF0000),
                Color(0xFFFFFF00),
                Color(0xFF00FF00),
                Color(0xFF00FFFF),
                Color(0xFF0000FF),
                Color(0xFFFF00FF),
                Color(0xFFFF0000),
            ),
            stops,
        )
    }

    @Test
    fun hueGradientColorsWrapsRedAtBothEnds() {
        val stops = hueGradientColors()

        assertTrue(stops.distinct().size == 6)
        assertEquals(stops.first(), stops.last())
    }

    @Test
    fun thumbXFractionResolvesFractions() {
        assertEquals(0f, thumbXFraction(0f))
        assertEquals(0.25f, thumbXFraction(90f))
        assertEquals(0.5f, thumbXFraction(180f))
        assertEquals(1f, thumbXFraction(360f))
    }

    @Test
    fun thumbXFractionClampsAndGuardsNaN() {
        assertEquals(0f, thumbXFraction(-20f))
        assertEquals(1f, thumbXFraction(400f))
        assertEquals(0f, thumbXFraction(Float.NaN))
    }

    @Test
    fun defaultsExposeTrackGeometry() {
        assertEquals(12.dp, ElegantHueSliderDefaults.Height)
        assertEquals(16.dp, ElegantHueSliderDefaults.ThumbSize)
    }

    @Test
    fun colorsResolveFromThemeRoles() {
        val light = resolveHueSliderColors(ElegantLightColors)

        assertEquals(Color.Transparent, light.trackColor)
        assertEquals(ElegantLightColors.surfaceRaised, light.thumbColor)
        assertEquals(ElegantLightColors.borderDefault, light.borderColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
    }

    @Test
    fun darkColorsResolveFromTheSameThemeRoles() {
        val dark = resolveHueSliderColors(ElegantDarkColors)

        assertEquals(Color.Transparent, dark.trackColor)
        assertEquals(ElegantDarkColors.surfaceRaised, dark.thumbColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.borderColor)
        assertEquals(ElegantDarkColors.focusRing, dark.focusedBorderColor)
    }

    @Test
    fun colorsFollowTheActiveTheme() {
        assertNotEquals(
            resolveHueSliderColors(ElegantLightColors),
            resolveHueSliderColors(ElegantDarkColors),
        )
    }
}

package com.elegant.compose.ui.scrollbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantScrollBarContractTest {
    @Test
    fun publicEnumsRemainStable() {
        assertEquals(
            listOf("Vertical", "Horizontal"),
            ElegantScrollBarOrientation.entries.map(ElegantScrollBarOrientation::name),
        )
    }

    @Test
    fun publicDefaultsRemainStable() {
        assertEquals(4.dp, ElegantScrollBarDefaults.ThumbWidth)
        assertEquals(8.dp, ElegantScrollBarDefaults.TrackWidth)
        assertEquals(0.1f, ElegantScrollBarDefaults.MinThumbFraction)
    }

    @Test
    fun scrollFractionMapsTheOffsetOntoTheScrollableSpan() {
        assertEquals(0f, scrollFraction(0f, 1000f))
        assertEquals(0.4f, scrollFraction(400f, 1000f))
        assertEquals(0.5f, scrollFraction(500f, 1000f))
        assertEquals(1f, scrollFraction(1000f, 1000f))
        assertEquals(1f, scrollFraction(1200f, 1000f))
        assertEquals(0f, scrollFraction(-10f, 1000f))
    }

    @Test
    fun scrollFractionIsNanSafeAndDegenerateSafe() {
        assertEquals(0f, scrollFraction(Float.NaN, 1000f))
        assertEquals(0f, scrollFraction(10f, Float.NaN))
        assertEquals(0f, scrollFraction(10f, 0f))
        assertEquals(0f, scrollFraction(10f, -100f))
    }

    @Test
    fun viewportFractionMapsTheTrackLengthOntoTheScrollableSpan() {
        assertEquals(0f, viewportFraction(0f, 1000f))
        assertEquals(0.28f, viewportFraction(280f, 1000f))
        assertEquals(0.5f, viewportFraction(500f, 1000f))
        assertEquals(1f, viewportFraction(1000f, 1000f))
        assertEquals(1f, viewportFraction(1200f, 1000f))
        assertEquals(1f, viewportFraction(280f, 200f))
    }

    @Test
    fun viewportFractionIsOneWhenTheContentFits() {
        assertEquals(1f, viewportFraction(280f, 0f))
        assertEquals(1f, viewportFraction(280f, -100f))
        assertEquals(1f, viewportFraction(Float.NaN, 1000f))
        assertEquals(1f, viewportFraction(280f, Float.NaN))
    }

    @Test
    fun thumbLengthScalesWithTheViewportFraction() {
        assertEquals(40f, thumbLength(400f, 0.1f, 0.1f))
        assertEquals(80f, thumbLength(400f, 0.2f, 0.1f))
        assertEquals(400f, thumbLength(400f, 1f, 0.1f))
        assertEquals(100f, thumbLength(1000f, 0.1f, 0.1f))
    }

    @Test
    fun thumbLengthRespectsTheMinimumFraction() {
        assertEquals(40f, thumbLength(400f, 0.05f, 0.1f))
        assertEquals(50f, thumbLength(100f, 0.1f, 0.5f))
        assertEquals(160f, thumbLength(400f, 0.15f, 0.4f))
        assertEquals(20f, thumbLength(400f, 0.05f, -0.5f))
    }

    @Test
    fun thumbLengthClampsItsFractions() {
        assertEquals(400f, thumbLength(400f, 2f, 0.1f))
        assertEquals(80f, thumbLength(400f, 0.05f, 0.2f))
        assertEquals(400f, thumbLength(400f, 0.05f, 1.5f))
    }

    @Test
    fun thumbLengthIsNanSafeAndDimensionSafe() {
        assertEquals(0f, thumbLength(Float.NaN, 0.2f, 0.1f))
        assertEquals(0f, thumbLength(400f, Float.NaN, 0.1f))
        assertEquals(0f, thumbLength(400f, 0.2f, Float.NaN))
        assertEquals(0f, thumbLength(0f, 0.2f, 0.1f))
        assertEquals(0f, thumbLength(-400f, 0.2f, 0.1f))
    }

    @Test
    fun thumbOffsetTracksTheRemainingTravel() {
        assertEquals(0f, thumbOffset(400f, 100f, 0f))
        assertEquals(150f, thumbOffset(400f, 100f, 0.5f))
        assertEquals(300f, thumbOffset(400f, 100f, 1f))
        assertEquals(300f, thumbOffset(400f, 100f, 2f))
        assertEquals(0f, thumbOffset(400f, 100f, -1f))
        assertEquals(200f, thumbOffset(400f, 0f, 0.5f))
    }

    @Test
    fun thumbOffsetReturnsZeroWhenTheThumbFillsTheTrack() {
        assertEquals(0f, thumbOffset(400f, 400f, 0.5f))
        assertEquals(0f, thumbOffset(400f, 500f, 0.5f))
        assertEquals(0f, thumbOffset(Float.NaN, 100f, 0.5f))
        assertEquals(0f, thumbOffset(400f, Float.NaN, 0.5f))
        assertEquals(0f, thumbOffset(400f, 100f, Float.NaN))
        assertEquals(0f, thumbOffset(0f, 100f, 0.5f))
        assertEquals(0f, thumbOffset(-400f, 100f, 0.5f))
    }

    @Test
    fun defaultColorsResolveSemanticThemeRoles() {
        val light = resolveScrollBarColors(ElegantLightColors)
        val dark = resolveScrollBarColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.textTertiary, light.thumbColor)
        assertEquals(ElegantLightColors.borderDefault, light.trackColor)
        assertEquals(ElegantDarkColors.textTertiary, dark.thumbColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.trackColor)
        assertNotEquals(light.thumbColor, dark.thumbColor)
        assertNotEquals(light, dark)
    }

    @Test
    fun customColorsRemainAnImmutableValueContract() {
        val colors = ElegantScrollBarColors(
            thumbColor = Color.Blue,
            trackColor = Color.Transparent,
        )

        assertEquals(Color.Blue, colors.thumbColor)
        assertEquals(Color.Transparent, colors.trackColor)
        assertEquals(colors, colors.copy())
    }
}

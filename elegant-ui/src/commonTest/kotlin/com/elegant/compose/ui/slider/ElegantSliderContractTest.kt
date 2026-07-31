package com.elegant.compose.ui.slider

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantSliderContractTest {

    @Test
    fun sliderFractionCoercesValuesOutsideTheRange() {
        assertEquals(0f, sliderFraction(-5f, 0f..10f))
        assertEquals(1f, sliderFraction(15f, 0f..10f))
        assertEquals(0f, sliderFraction(-10f, -10f..10f))
        assertEquals(1f, sliderFraction(10f, -10f..10f))
    }

    @Test
    fun sliderFractionHandlesNaNAndDegenerateRanges() {
        assertEquals(0f, sliderFraction(Float.NaN, 0f..1f))
        assertEquals(0f, sliderFraction(0.5f, 0f..0f))
        assertEquals(0f, sliderFraction(-1f, 5f..5f))
    }

    @Test
    fun sliderFractionResolvesMidpoints() {
        assertEquals(0.5f, sliderFraction(5f, 0f..10f))
        assertEquals(0.25f, sliderFraction(0f, -10f..30f))
        assertEquals(0.75f, sliderFraction(0.75f, 0f..1f))
    }

    @Test
    fun stepResolutionIsContinuousWithoutSteps() {
        assertEquals(0f, resolveStepValue(0f, 0f..10f, 0))
        assertEquals(5f, resolveStepValue(0.5f, 0f..10f, 0))
        assertEquals(10f, resolveStepValue(1f, 0f..10f, 0))
        assertEquals(0f, resolveStepValue(0.5f, -10f..10f, 0))
    }

    @Test
    fun stepResolutionSnapsToTheNearestDiscretePosition() {
        assertEquals(0f, resolveStepValue(0.1f, 0f..10f, 3))
        assertEquals(10f / 3f, resolveStepValue(0.25f, 0f..10f, 3))
        assertEquals(10f / 3f, resolveStepValue(0.49f, 0f..10f, 3))
        assertEquals(20f / 3f, resolveStepValue(0.51f, 0f..10f, 3))
        assertEquals(20f / 3f, resolveStepValue(0.75f, 0f..10f, 3))
        assertEquals(10f, resolveStepValue(0.9f, 0f..10f, 3))
        assertEquals(0f, resolveStepValue(0.24f, 0f..10f, 2))
        assertEquals(5f, resolveStepValue(0.49f, 0f..10f, 2))
        assertEquals(5f, resolveStepValue(0.51f, 0f..10f, 2))
        assertEquals(10f, resolveStepValue(0.76f, 0f..10f, 2))
        assertEquals(-10f, resolveStepValue(0f, -10f..10f, 4))
        assertEquals(5f, resolveStepValue(0.75f, -10f..10f, 4))
    }

    @Test
    fun stepResolutionBreaksTiesTowardTheEndOfTheRange() {
        assertEquals(10f, resolveStepValue(0.5f, 0f..10f, 1))
        assertEquals(5f, resolveStepValue(0.5f, 0f..10f, 2))
    }

    @Test
    fun stepResolutionClampsBoundaries() {
        assertEquals(0f, resolveStepValue(0f, 0f..10f, 3))
        assertEquals(10f, resolveStepValue(1f, 0f..10f, 3))
        assertEquals(0f, resolveStepValue(-0.5f, 0f..10f, 3))
        assertEquals(10f, resolveStepValue(1.5f, 0f..10f, 3))
        assertEquals(0f, resolveStepValue(Float.NaN, 0f..10f, 3))
        assertEquals(10f, resolveStepValue(1f, -10f..10f, 4))
    }

    @Test
    fun defaultColorsFollowTheActiveTheme() {
        val light = resolveSliderColors(ElegantLightColors)
        val dark = resolveSliderColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.borderDefault, light.trackColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.activeTrackColor)
        assertEquals(ElegantLightColors.borderStrong, light.hoveredTrackColor)
        assertEquals(ElegantLightColors.interactivePrimaryHover, light.hoveredActiveTrackColor)
        assertEquals(ElegantLightColors.interactivePrimaryPressed, light.pressedActiveTrackColor)
        assertEquals(ElegantLightColors.borderDefault, light.disabledTrackColor)
        assertEquals(ElegantLightColors.surfaceRaised, light.thumbColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.pressedThumbColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledThumbColor)
        assertEquals(ElegantLightColors.interactivePrimaryHover, light.focusedThumbColor)
        assertNotEquals(light, dark, "slider colors must follow the active theme")
    }

    @Test
    fun disabledActiveTrackIsAThemedAccentTint() {
        val light = resolveSliderColors(ElegantLightColors)

        assertEquals(ElegantLightColors.interactivePrimary.copy(alpha = 0.35f), light.disabledActiveTrackColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.disabledActiveTrackColor.copy(alpha = 1f))
    }

    @Test
    fun colorsModelDefaultsHoverPressedAndDisabledToTheirRestingColors() {
        val colors = ElegantSliderColors(
            trackColor = Color.Red,
            activeTrackColor = Color.Blue,
            thumbColor = Color.Green,
        )

        assertEquals(Color.Red, colors.hoveredTrackColor)
        assertEquals(Color.Red, colors.disabledTrackColor)
        assertEquals(Color.Blue, colors.hoveredActiveTrackColor)
        assertEquals(Color.Blue, colors.pressedActiveTrackColor)
        assertEquals(Color.Blue, colors.disabledActiveTrackColor)
        assertEquals(Color.Green, colors.hoveredThumbColor)
        assertEquals(Color.Green, colors.pressedThumbColor)
        assertEquals(Color.Green, colors.disabledThumbColor)
        assertEquals(Color.Green, colors.focusedThumbColor)
    }

    @Test
    fun defaultsMeetAccessibilityAndTokenBaselines() {
        assertEquals(4.dp, ElegantSliderDefaults.TrackHeight)
        assertEquals(20.dp, ElegantSliderDefaults.ThumbSize)
        assertTrue(ElegantSliderDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(ElegantMotion.standardDurationMillis, ElegantSliderDefaults.AnimationDurationMillis)
    }
}

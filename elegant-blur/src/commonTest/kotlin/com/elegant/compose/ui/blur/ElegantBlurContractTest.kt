// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.ui.blur

import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.blur.highlight.ElegantBloomStroke
import com.elegant.compose.ui.blur.highlight.ElegantHighlight
import com.elegant.compose.ui.blur.sensor.ElegantDeviceTilt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ElegantBlurContractTest {

    @Test
    fun progressiveBlurDefaultsDescribeAnEdgeFade() {
        val gradient = ElegantProgressiveBlur()
        assertEquals(90f, gradient.angle)
        assertEquals(0f, gradient.startFraction)
        assertEquals(1f, gradient.endFraction)
        assertEquals(1f, gradient.curve)
    }

    @Test
    fun progressiveBlurPresetsCoverTheFourEdges() {
        assertEquals(ElegantProgressiveBlur(angle = 90f, startFraction = 0f, endFraction = 1f), ElegantProgressiveBlur.Top)
        assertEquals(ElegantProgressiveBlur(angle = 270f, startFraction = 0f, endFraction = 1f), ElegantProgressiveBlur.Bottom)
        assertEquals(ElegantProgressiveBlur(angle = 0f, startFraction = 0f, endFraction = 1f), ElegantProgressiveBlur.Left)
        assertEquals(ElegantProgressiveBlur(angle = 180f, startFraction = 0f, endFraction = 1f), ElegantProgressiveBlur.Right)
    }

    @Test
    fun blurDefaultsCarryTheReferenceConstants() {
        assertEquals(20f, ElegantBlurDefaults.BlurRadius)
        assertEquals(0.0045f, ElegantBlurDefaults.NoiseCoefficient)
        assertEquals(0f, ElegantBlurDefaults.ProgressiveNoiseCoefficient)
        assertEquals(150f, ElegantBlurDefaults.MaxBlurRadius)
    }

    @Test
    fun blurColorsDefaultsAreNeutral() {
        val colors = ElegantBlurColors()
        assertTrue(colors.blendColors.isEmpty())
        assertEquals(0f, colors.brightness)
        assertEquals(1f, colors.contrast)
        assertEquals(1f, colors.saturation)
    }

    @Test
    fun blendModesCoverStandardAndExtendedRanges() {
        assertEquals(0, ElegantBlurBlendMode.Clear.value)
        assertEquals(3, ElegantBlurBlendMode.SrcOver.value)
        assertEquals(24, ElegantBlurBlendMode.Multiply.value)
        assertEquals(100, ElegantBlurBlendMode.LinearLight.value)
        assertEquals(200, ElegantBlurBlendMode.AlphaBlend.value)
    }

    @Test
    fun deviceTiltZeroIsTheNoSensorState() {
        assertEquals(0f, ElegantDeviceTilt.Zero.pitch)
        assertEquals(0f, ElegantDeviceTilt.Zero.roll)
        assertEquals(0f, ElegantDeviceTilt.Zero.gravityX)
        assertEquals(0f, ElegantDeviceTilt.Zero.gravityY)
    }

    @Test
    fun highlightDefaultsToTheMiddleLightStroke() {
        assertEquals(ElegantHighlight.GlassStrokeMiddleLight, ElegantHighlight.Default)
        assertEquals(0.8.dp, ElegantHighlight.GlassStrokeMiddleLight.width)
        assertEquals(1f, ElegantHighlight.GlassStrokeMiddleLight.alpha)
    }

    @Test
    fun highlightPresetsCoverLightAndDarkThemes() {
        val bigLight = ElegantHighlight(style = ElegantBloomStroke.GlassStrokeBigLight)
        val smallDark = ElegantHighlight(style = ElegantBloomStroke.GlassStrokeSmallDark)
        assertEquals(ElegantHighlight.GlassStrokeBigLight, bigLight)
        assertEquals(ElegantHighlight.GlassStrokeSmallDark, smallDark)
    }
}

// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.ui.blur

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ElegantBlurSmokeTest {

    @Test
    fun runtimeShadersAreSupportedOnSkia() {
        assertTrue(isRuntimeShaderSupported())
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun textureBlurComposesWithALayerBackdrop() = runComposeUiTest {
        setContent {
            val graphicsLayer = rememberGraphicsLayer()
            val backdrop = rememberElegantLayerBackdrop(graphicsLayer)
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFF112233))
                    .elegantLayerBackdrop(backdrop),
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .elegantTextureBlur(
                        backdrop = backdrop,
                        shape = RectangleShape,
                        blurRadius = 20f,
                    ),
            ) {
                Box(Modifier.fillMaxSize().background(Color.White.copy(alpha = 0.5f)))
            }
        }
        waitForIdle()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun progressiveTextureBlurComposesWithDefaults() = runComposeUiTest {
        setContent {
            val graphicsLayer = rememberGraphicsLayer()
            val backdrop = rememberElegantLayerBackdrop(graphicsLayer)
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFF445566))
                    .elegantLayerBackdrop(backdrop),
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .elegantProgressiveTextureBlur(
                        backdrop = backdrop,
                        shape = RectangleShape,
                        blurRadius = 40f,
                        gradient = ElegantProgressiveBlur.Bottom,
                    ),
            )
        }
        waitForIdle()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun blurDefaultsResolveStableColors() = runComposeUiTest {
        setContent {
            val colors = ElegantBlurDefaults.blurColors(
                blendColors = listOf(ElegantBlendColorEntry(Color.White, ElegantBlurBlendMode.SrcOver)),
                brightness = 0.1f,
            )
            assertEquals(1, colors.blendColors.size)
            assertEquals(0.1f, colors.brightness)
            assertEquals(1f, colors.contrast)
            assertEquals(1f, colors.saturation)
        }
        waitForIdle()
    }
}

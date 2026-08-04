package com.elegant.compose.ui.modal

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantModalContractTest {

    @Test
    fun colorsResolveThemeAwareSurfaceWithFixedScrim() {
        val light = resolveModalColors(ElegantLightColors)
        val dark = resolveModalColors(ElegantDarkColors)

        assertEquals(Color.Black.copy(alpha = ElegantModalDefaults.ScrimAlpha), light.scrimColor)
        assertEquals(Color.Black.copy(alpha = ElegantModalDefaults.ScrimAlpha), dark.scrimColor)
        assertEquals(ElegantLightColors.surfaceRaised, light.containerColor)
        assertEquals(ElegantDarkColors.surfaceRaised, dark.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantDarkColors.textPrimary, dark.contentColor)
        assertNotEquals(light, dark, "colors must follow the active theme")
    }

    @Test
    fun scrimDimstheSurfaceBehindItInBothThemes() {
        for ((name, colors) in listOf(
            "light" to ElegantLightColors,
            "dark" to ElegantDarkColors,
        )) {
            val scrim = resolveModalColors(colors).scrimColor
            val canvas = colors.backgroundCanvas
            val dimmed = blend(canvas, scrim)

            assertTrue(
                luminance(dimmed) < luminance(canvas),
                "$name scrim must dim the canvas behind the modal",
            )
        }
    }

    @Test
    fun contentStaysReadableOnTheModalContainer() {
        val light = resolveModalColors(ElegantLightColors)
        val dark = resolveModalColors(ElegantDarkColors)

        assertTrue(
            contrastRatio(light.contentColor, light.containerColor) >= 3f,
            "light modal content must stay readable on its container",
        )
        assertTrue(
            contrastRatio(dark.contentColor, dark.containerColor) >= 3f,
            "dark modal content must stay readable on its container",
        )
    }

    @Test
    fun defaultsExposeWidthScrimShapeAndMotion() {
        assertEquals(480.dp, ElegantModalDefaults.MaxWidth)
        assertEquals(480.dp, resolveModalMaxWidth())
        assertEquals(0.4f, ElegantModalDefaults.ScrimAlpha)
        assertEquals(RoundedCornerShape(ElegantRadius.lg), ElegantModalDefaults.Shape)
        assertEquals(
            ElegantMotion.emphasizedDurationMillis,
            ElegantModalDefaults.AnimationDurationMillis,
        )
    }

    private fun blend(background: Color, overlay: Color): Color = Color(
        red = background.red * (1f - overlay.alpha) + overlay.red * overlay.alpha,
        green = background.green * (1f - overlay.alpha) + overlay.green * overlay.alpha,
        blue = background.blue * (1f - overlay.alpha) + overlay.blue * overlay.alpha,
    )

    private fun luminance(color: Color): Float {
        fun channel(value: Float): Float {
            val linear = value
            return if (linear <= 0.03928f) {
                linear / 12.92f
            } else {
                ((linear + 0.055f) / 1.055f).pow(2.4f)
            }
        }
        return 0.2126f * channel(color.red) +
            0.7152f * channel(color.green) +
            0.0722f * channel(color.blue)
    }

    private fun contrastRatio(foreground: Color, background: Color): Float {
        val lighter = maxOf(luminance(foreground), luminance(background))
        val darker = minOf(luminance(foreground), luminance(background))
        return (lighter + 0.05f) / (darker + 0.05f)
    }
}

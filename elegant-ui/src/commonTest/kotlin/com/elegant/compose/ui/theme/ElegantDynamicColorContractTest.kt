package com.elegant.compose.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantDynamicColorContractTest {
    private val violetSeed = Color(0xFF6C4EFF)
    private val greenSeed = Color(0xFF147D64)
    private val orangeSeed = Color(0xFFB45309)

    @Test
    fun rgbToHslMapsPrimaryAndNeutralColors() {
        assertEquals(0f, rgbToHsl(Color.Red).h, 0.5f)
        assertEquals(1f, rgbToHsl(Color.Red).s, 0.01f)
        assertEquals(0.5f, rgbToHsl(Color.Red).l, 0.01f)
        assertEquals(120f, rgbToHsl(Color.Green).h, 0.5f)
        assertEquals(1f, rgbToHsl(Color.Green).s, 0.01f)
        assertEquals(0.5f, rgbToHsl(Color.Green).l, 0.01f)
        assertEquals(240f, rgbToHsl(Color.Blue).h, 0.5f)
        assertEquals(1f, rgbToHsl(Color.Blue).s, 0.01f)
        assertEquals(0.5f, rgbToHsl(Color.Blue).l, 0.01f)
        assertEquals(0f, rgbToHsl(Color.Black).h, 0f)
        assertEquals(0f, rgbToHsl(Color.Black).s, 0f)
        assertEquals(0f, rgbToHsl(Color.Black).l, 0.01f)
        assertEquals(1f, rgbToHsl(Color.White).l, 0.01f)
        assertEquals(0f, rgbToHsl(Color.Gray).s, 0.01f)
        assertEquals(0.5f, rgbToHsl(Color.Gray).l, 0.01f)
    }

    @Test
    fun hslRoundTripPreservesColorComponents() {
        for (color in listOf(Color.Red, Color.Green, Color.Blue, Color.Black, Color.White, Color.Gray)) {
            assertColorClose(color, hslToColor(rgbToHsl(color)))
        }
    }

    @Test
    fun shiftHueWrapsAroundTheColorWheel() {
        assertEquals(0f, clampHue(360f), 0f)
        assertEquals(330f, clampHue(-30f), 0f)
        assertEquals(30f, clampHue(750f), 0f)
        assertColorClose(Color.Red, shiftHue(Color.Red, 360f))
        assertColorClose(Color.Red, shiftHue(Color.Red, 720f))
        assertColorClose(Color.Red, shiftHue(Color.Red, -360f))
        assertColorClose(Color.Blue, shiftHue(Color.Red, -120f))
    }

    @Test
    fun lightenAndDarkenClampAtBothEnds() {
        assertColorClose(Color.White, lighten(Color.White, 0.5f))
        assertColorClose(Color.White, lighten(Color.White, 2f))
        assertColorClose(Color.Black, darken(Color.Black, 0.5f))
        assertColorClose(Color.Black, darken(Color.Black, 2f))
        assertEquals(1f, rgbToHsl(lighten(Color.Black, 0.9f)).l, 0.01f)
        assertEquals(0.06f, rgbToHsl(darken(Color.White, 0.94f)).l, 0.01f)
        assertEquals(0.6f, rgbToHsl(lighten(Color.Black, 0.6f)).l, 0.01f)
        assertEquals(0.4f, rgbToHsl(darken(Color.White, 0.6f)).l, 0.01f)
    }

    @Test
    fun derivationIsDeterministic() {
        assertEquals(
            deriveElegantColors(violetSeed, darkTheme = false),
            deriveElegantColors(violetSeed, darkTheme = false),
        )
        assertEquals(
            deriveElegantColors(violetSeed, darkTheme = true),
            deriveElegantColors(violetSeed, darkTheme = true),
        )
    }

    @Test
    fun lightAndDarkDerivationsDiffer() {
        val light = deriveElegantColors(violetSeed, darkTheme = false)
        val dark = deriveElegantColors(violetSeed, darkTheme = true)
        assertNotEquals(light, dark)
        assertNotEquals(light.backgroundCanvas, dark.backgroundCanvas)
        assertNotEquals(light.surfaceRaised, dark.surfaceRaised)
        assertNotEquals(light.textPrimary, dark.textPrimary)
        assertNotEquals(light.statusPositive, dark.statusPositive)
    }

    @Test
    fun keyColorBecomesInteractivePrimary() {
        assertEquals(violetSeed, deriveElegantColors(violetSeed, darkTheme = false).interactivePrimary)
        assertEquals(violetSeed, deriveElegantColors(violetSeed, darkTheme = true).interactivePrimary)
    }

    @Test
    fun interactiveVariantsShiftAroundTheSeed() {
        val light = deriveElegantColors(violetSeed, darkTheme = false)
        val dark = deriveElegantColors(violetSeed, darkTheme = true)
        assertColorClose(lighten(violetSeed, 0.06f), light.interactivePrimaryHover)
        assertColorClose(darken(violetSeed, 0.10f), light.interactivePrimaryPressed)
        assertColorClose(lighten(violetSeed, 0.30f), light.focusRing)
        assertColorClose(lighten(violetSeed, 0.06f), dark.interactivePrimaryHover)
        assertColorClose(darken(violetSeed, 0.10f), dark.interactivePrimaryPressed)
    }

    @Test
    fun textInverseContrastAgainstKeyColorMeetsWcagAa() {
        for (seed in listOf(violetSeed, greenSeed, orangeSeed)) {
            val light = deriveElegantColors(seed, darkTheme = false)
            val dark = deriveElegantColors(seed, darkTheme = true)
            for (colors in listOf(light, dark)) {
                val contrast = contrastRatio(colors.textInverse, colors.interactivePrimary)
                assertTrue(contrast >= 4.5f, "contrast $contrast for seed $seed")
            }
        }
    }

    @Test
    fun statusTonesAreDistinctFromEachOtherAndTheSeed() {
        val light = deriveElegantColors(violetSeed, darkTheme = false)
        val dark = deriveElegantColors(violetSeed, darkTheme = true)
        for (colors in listOf(light, dark)) {
            assertNotEquals(colors.statusPositive, colors.statusWarning)
            assertNotEquals(colors.statusWarning, colors.statusCritical)
            assertNotEquals(colors.statusCritical, colors.statusPositive)
            assertNotEquals(colors.statusPositive, colors.interactivePrimary)
            assertNotEquals(colors.statusWarning, colors.interactivePrimary)
            assertNotEquals(colors.statusCritical, colors.interactivePrimary)
            assertNotEquals(colors.onStatusPositive, colors.statusPositive)
            assertNotEquals(colors.onStatusWarning, colors.statusWarning)
            assertNotEquals(colors.onStatusCritical, colors.statusCritical)
        }
    }

    @Test
    fun surfacesSeparateFromCanvasInBothThemes() {
        val light = deriveElegantColors(violetSeed, darkTheme = false)
        val dark = deriveElegantColors(violetSeed, darkTheme = true)
        assertNotEquals(light.surfaceRaised, light.backgroundCanvas)
        assertNotEquals(light.surfaceDefault, light.backgroundSubtle)
        assertNotEquals(dark.surfaceRaised, dark.backgroundCanvas)
        assertNotEquals(dark.surfaceDefault, dark.backgroundSubtle)
    }

    @Test
    fun derivedPaletteFillsEveryRole() {
        for (colors in listOf(
            deriveElegantColors(violetSeed, darkTheme = false),
            deriveElegantColors(violetSeed, darkTheme = true),
        )) {
            assertEquals(colors, colors.copy())
        }
    }

    @Test
    fun bestInverseTextColorPicksByLuminanceThreshold() {
        val darkBackground = Color(0xFF111122)
        val lightBackground = Color(0xFFF0F0FF)
        assertEquals(Color.White, bestInverseTextColor(darkBackground))
        assertNotEquals(Color.White, bestInverseTextColor(lightBackground))
        assertTrue(bestInverseTextColor(lightBackground).luminance() < 0.1f)
    }

    @Test
    @Composable
    fun controllerExposesSeedAndDistinctThemePalettes() {
        val controller = ElegantThemeController(keyColor = violetSeed)
        assertEquals(violetSeed, controller.keyColor)
        assertEquals(deriveElegantColors(violetSeed, darkTheme = false), controller.lightColors())
        assertEquals(deriveElegantColors(violetSeed, darkTheme = true), controller.darkColors())
        assertNotEquals(controller.lightColors(), controller.darkColors())
    }

    private fun assertColorClose(expected: Color, actual: Color) {
        val tolerance = 0.011f
        assertTrue(abs(expected.red - actual.red) <= tolerance, "red ${expected.red} vs ${actual.red}")
        assertTrue(abs(expected.green - actual.green) <= tolerance, "green ${expected.green} vs ${actual.green}")
        assertTrue(abs(expected.blue - actual.blue) <= tolerance, "blue ${expected.blue} vs ${actual.blue}")
    }
}

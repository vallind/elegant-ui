package com.elegant.compose.ui.foundation.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantMonetMappingContractTest {
    private val violetSeed = Color(0xFF6C4EFF)
    private val greenSeed = Color(0xFF147D64)

    @Test
    fun seedDerivationIsDeterministic() {
        assertEquals(
            elegantColorsFromSeed(violetSeed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = false),
            elegantColorsFromSeed(violetSeed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = false),
        )
        assertEquals(
            elegantColorsFromSeed(violetSeed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = true),
            elegantColorsFromSeed(violetSeed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = true),
        )
    }

    @Test
    fun lightAndDarkPalettesDiffer() {
        val light = elegantColorsFromSeed(violetSeed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = false)
        val dark = elegantColorsFromSeed(violetSeed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = true)
        assertNotEquals(light, dark)
        assertTrue(light.backgroundCanvas.luminance() > dark.backgroundCanvas.luminance())
        assertTrue(light.surfaceDefault.luminance() > dark.surfaceDefault.luminance())
        assertTrue(light.textPrimary.luminance() < dark.textPrimary.luminance())
        assertNotEquals(light.statusPositive, dark.statusPositive)
    }

    @Test
    fun derivedPaletteIsOpaqueEverywhere() {
        val light = elegantColorsFromSeed(greenSeed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = false)
        val dark = elegantColorsFromSeed(greenSeed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = true)
        for (colors in listOf(light, dark)) {
            for (role in listOf(
                colors.backgroundCanvas, colors.backgroundSubtle, colors.surfaceDefault,
                colors.surfaceRaised, colors.surfaceSunken, colors.surfaceHover,
                colors.textPrimary, colors.textSecondary, colors.textTertiary, colors.textInverse,
                colors.borderDefault, colors.borderStrong,
                colors.interactivePrimary, colors.interactivePrimaryHover,
                colors.interactivePrimaryPressed, colors.focusRing,
                colors.statusPositive, colors.onStatusPositive,
                colors.statusWarning, colors.onStatusWarning,
                colors.statusCritical, colors.onStatusCritical,
            )) {
                assertEquals(1f, role.alpha, "role $role must be opaque")
            }
        }
    }

    @Test
    fun textInverseContrastsWithInteractivePrimary() {
        for (seed in listOf(violetSeed, greenSeed)) {
            val light = elegantColorsFromSeed(seed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = false)
            val dark = elegantColorsFromSeed(seed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = true)
            for (colors in listOf(light, dark)) {
                val contrast = contrastRatio(colors.textInverse, colors.interactivePrimary)
                assertTrue(contrast >= 4.4f, "contrast $contrast for seed $seed")
            }
        }
    }

    @Test
    fun statusRolesAreDistinctAndTheirContentContrasts() {
        val light = elegantColorsFromSeed(violetSeed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = false)
        val dark = elegantColorsFromSeed(violetSeed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = true)
        for (colors in listOf(light, dark)) {
            assertNotEquals(colors.statusPositive, colors.statusWarning)
            assertNotEquals(colors.statusWarning, colors.statusCritical)
            assertNotEquals(colors.statusCritical, colors.statusPositive)
            assertTrue(contrastRatio(colors.onStatusPositive, colors.statusPositive) >= 4.4f)
            assertTrue(contrastRatio(colors.onStatusWarning, colors.statusWarning) >= 4.4f)
            assertTrue(contrastRatio(colors.onStatusCritical, colors.statusCritical) >= 4.4f)
        }
    }

    @Test
    fun seedHueDrivesTheInteractivePrimary() {
        for (dark in listOf(false, true)) {
            val colors = elegantColorsFromSeed(violetSeed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = dark)
            val seedHue = rgbToHsl(violetSeed).h
            val primaryHue = rgbToHsl(colors.interactivePrimary).h
            val delta = kotlin.math.abs(primaryHue - seedHue)
            assertTrue(delta <= 15f, "primary hue $primaryHue vs seed hue $seedHue")
        }
    }

    @Test
    fun paletteStylesProduceDistinctPalettes() {
        val primaries = ElegantThemePaletteStyle.entries.map { style ->
            elegantColorsFromSeed(violetSeed, ElegantThemeColorSpec.Spec2021, style, dark = false).interactivePrimary
        }
        assertTrue(primaries.distinct().size >= 4, "styles must not collapse onto one palette")
    }

    @Test
    fun spec2025DowngradesGracefullyForUnsupportedStyles() {
        val spec2021 = elegantColorsFromSeed(violetSeed, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.Fidelity, dark = false)
        val downgraded = elegantColorsFromSeed(violetSeed, ElegantThemeColorSpec.Spec2025, ElegantThemePaletteStyle.Fidelity, dark = false)
        assertEquals(spec2021, downgraded)
    }

    @Test
    fun fallbackSeedDerivationIsStable() {
        val fallback = Color(MonetFallbackSeedArgb)
        assertEquals(
            elegantColorsFromSeed(fallback, ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = false),
            elegantColorsFromSeed(Color(0xFF6750A4), ElegantThemeColorSpec.Spec2021, ElegantThemePaletteStyle.TonalSpot, dark = false),
        )
    }

    @Test
    fun seedControllerConstructorPreservesHslDerivation() {
        val controller = ElegantThemeController(keyColor = violetSeed)
        assertEquals(ElegantColorSchemeMode.System, controller.colorSchemeMode)
        assertEquals(deriveElegantColors(violetSeed, darkTheme = false), controller.lightColors)
        assertEquals(deriveElegantColors(violetSeed, darkTheme = true), controller.darkColors)
        assertEquals(violetSeed, controller.keyColor)
    }
}

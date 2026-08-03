package com.elegant.compose.ui.fieldset

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantSpacing
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantFieldsetContractTest {

    @Test
    fun colorsFollowTheActiveThemeWithReadableContrast() {
        val light = resolveFieldsetColors(ElegantLightColors)
        val dark = resolveFieldsetColors(ElegantDarkColors)

        assertTrue(
            contrastRatio(light.legendColor, light.containerColor) >= 3f,
            "light legend must stay readable on its container",
        )
        assertTrue(
            contrastRatio(light.contentColor, light.containerColor) >= 3f,
            "light content must stay readable on its container",
        )
        assertTrue(
            contrastRatio(dark.legendColor, dark.containerColor) >= 3f,
            "dark legend must stay readable on its container",
        )
        assertTrue(
            contrastRatio(dark.contentColor, dark.containerColor) >= 3f,
            "dark content must stay readable on its container",
        )
        assertNotEquals(light, dark, "fieldset colors must follow the active theme")
    }

    @Test
    fun colorsMapToRaisedSurfaceBorderAndTextRoles() {
        val light = resolveFieldsetColors(ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceRaised, light.containerColor)
        assertEquals(ElegantLightColors.borderDefault, light.borderColor)
        assertEquals(ElegantLightColors.textSecondary, light.legendColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
    }

    @Test
    fun blankLegendsResolveToNull() {
        assertNull(resolveLegend(null))
        assertNull(resolveLegend(""))
        assertNull(resolveLegend("   "))
        assertNull(resolveLegend("\t\n"))
    }

    @Test
    fun nonBlankLegendsAreTrimmedAndKept() {
        assertEquals("Contact details", resolveLegend("Contact details"))
        assertEquals("Contact details", resolveLegend("  Contact details  "))
    }

    @Test
    fun defaultsExposeGridAlignedSpacing() {
        assertEquals(ElegantSpacing.md, ElegantFieldsetDefaults.LegendGap)
        assertEquals(ElegantSpacing.xl, ElegantFieldsetDefaults.ContentPadding)
        assertEquals(8.dp, ElegantFieldsetDefaults.LegendGap)
        assertEquals(16.dp, ElegantFieldsetDefaults.ContentPadding)
    }

    private fun contrastRatio(foreground: Color, background: Color): Float {
        fun luminance(color: Color): Float {
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

        val lighter = maxOf(luminance(foreground), luminance(background))
        val darker = minOf(luminance(foreground), luminance(background))
        return (lighter + 0.05f) / (darker + 0.05f)
    }
}

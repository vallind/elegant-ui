package com.elegant.compose.ui.alertdialog

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantAlertDialogContractTest {

    @Test
    fun colorsResolveThemeAwareRolesWithFixedScrim() {
        val light = resolveAlertDialogColors(ElegantLightColors)
        val dark = resolveAlertDialogColors(ElegantDarkColors)

        assertEquals(Color.Black.copy(alpha = ElegantAlertDialogDefaults.ScrimAlpha), light.scrimColor)
        assertEquals(Color.Black.copy(alpha = ElegantAlertDialogDefaults.ScrimAlpha), dark.scrimColor)
        assertEquals(ElegantLightColors.surfaceRaised, light.containerColor)
        assertEquals(ElegantDarkColors.surfaceRaised, dark.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantDarkColors.textPrimary, dark.contentColor)
        assertEquals(ElegantLightColors.textPrimary, light.titleColor)
        assertEquals(ElegantDarkColors.textPrimary, dark.titleColor)
        assertEquals(ElegantLightColors.textSecondary, light.descriptionColor)
        assertEquals(ElegantDarkColors.textSecondary, dark.descriptionColor)
        assertNotEquals(light, dark, "colors must follow the active theme")
    }

    @Test
    fun scrimDimsTheSurfaceBehindItInBothThemes() {
        for ((name, colors) in listOf(
            "light" to ElegantLightColors,
            "dark" to ElegantDarkColors,
        )) {
            val scrim = resolveAlertDialogColors(colors).scrimColor
            val canvas = colors.backgroundCanvas
            val dimmed = blend(canvas, scrim)

            assertTrue(
                luminance(dimmed) < luminance(canvas),
                "$name scrim must dim the canvas behind the dialog",
            )
        }
    }

    @Test
    fun titleAndDescriptionStayReadableOnTheDialogContainer() {
        val light = resolveAlertDialogColors(ElegantLightColors)
        val dark = resolveAlertDialogColors(ElegantDarkColors)

        assertTrue(
            contrastRatio(light.titleColor, light.containerColor) >= 3f,
            "light title must stay readable on its container",
        )
        assertTrue(
            contrastRatio(dark.titleColor, dark.containerColor) >= 3f,
            "dark title must stay readable on its container",
        )
        assertTrue(
            contrastRatio(light.descriptionColor, light.containerColor) >= 3f,
            "light description must stay readable on its container",
        )
        assertTrue(
            contrastRatio(dark.descriptionColor, dark.containerColor) >= 3f,
            "dark description must stay readable on its container",
        )
    }

    @Test
    fun descriptionResolvesNullAndBlankValuesToNull() {
        assertNull(resolveDescription(null))
        assertNull(resolveDescription(""))
        assertNull(resolveDescription("   "))
        assertNull(resolveDescription("\n\t"))
        assertEquals("Delete project?", resolveDescription("Delete project?"))
        assertEquals("Keep editing", resolveDescription("Keep editing"))
    }

    @Test
    fun dismissVisibilityRequiresNonNullBlankText() {
        assertTrue(!dismissButtonVisible(null), "null dismiss text must hide the dismiss button")
        assertTrue(!dismissButtonVisible(""), "blank dismiss text must hide the dismiss button")
        assertTrue(!dismissButtonVisible("   "), "whitespace dismiss text must hide the dismiss button")
        assertTrue(dismissButtonVisible("Cancel"), "non-blank dismiss text must show the dismiss button")
    }

    @Test
    fun defaultsMatchAlertDialogContract() {
        assertEquals(400.dp, ElegantAlertDialogDefaults.MaxWidth)
        assertEquals(400.dp, resolveAlertDialogMaxWidth())
        assertEquals(0.4f, ElegantAlertDialogDefaults.ScrimAlpha)
        assertEquals(
            ElegantMotion.emphasizedDurationMillis,
            ElegantAlertDialogDefaults.AnimationDurationMillis,
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

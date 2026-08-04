package com.elegant.compose.ui.label

import androidx.compose.ui.graphics.Color
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantLabelContractTest {
    @Test
    fun defaultsConstantsRemainStable() {
        assertEquals("*", ElegantLabelDefaults.RequiredSuffix)
        assertEquals(LabelRequiredSuffix, ElegantLabelDefaults.RequiredSuffix)
    }

    @Test
    fun themeRolesResolveFromLightColors() {
        val colors = resolveLabelColors(ElegantLightColors)
        assertEquals(ElegantLightColors.textSecondary, colors.contentColor)
        assertEquals(ElegantLightColors.statusCritical, colors.requiredColor)
        assertEquals(ElegantLightColors.textTertiary, colors.disabledContentColor)
    }

    @Test
    fun themeRolesResolveFromDarkColors() {
        val colors = resolveLabelColors(ElegantDarkColors)
        assertEquals(ElegantDarkColors.textSecondary, colors.contentColor)
        assertEquals(ElegantDarkColors.statusCritical, colors.requiredColor)
        assertEquals(ElegantDarkColors.textTertiary, colors.disabledContentColor)
    }

    @Test
    fun colorsResolveDistinctlyBetweenLightAndDarkThemes() {
        val light = resolveLabelColors(ElegantLightColors)
        val dark = resolveLabelColors(ElegantDarkColors)
        assertNotEquals(light.contentColor, dark.contentColor)
        assertNotEquals(light.requiredColor, dark.requiredColor)
        assertNotEquals(light.disabledContentColor, dark.disabledContentColor)
    }

    @Test
    fun colorResolutionPrefersContentColorWhenEnabled() {
        val colors = ElegantLabelColors(
            contentColor = Color(0xFF112233),
            requiredColor = Color(0xFF445566),
            disabledContentColor = Color(0xFF778899),
        )
        assertEquals(colors.contentColor, resolveLabelColor(colors = colors, enabled = true))
    }

    @Test
    fun colorResolutionFallsBackToDisabledContentColorWhenDisabled() {
        val colors = ElegantLabelColors(
            contentColor = Color(0xFF112233),
            requiredColor = Color(0xFF445566),
            disabledContentColor = Color(0xFF778899),
        )
        assertEquals(colors.disabledContentColor, resolveLabelColor(colors = colors, enabled = false))
    }

    @Test
    fun disabledContentColorFallsBackToContentColorWhenUnset() {
        val contentColor = Color(0xFF112233)
        val colors = ElegantLabelColors(
            contentColor = contentColor,
            requiredColor = Color(0xFF445566),
        )
        assertEquals(contentColor, colors.disabledContentColor)
    }

    @Test
    fun labelTextAppendsRequiredSuffixOnlyWhenRequired() {
        assertEquals("Full name", labelText(text = "Full name", required = false))
        assertEquals("Full name *", labelText(text = "Full name", required = true))
    }

    @Test
    fun labelTextKeepsBlankTextUnchanged() {
        assertEquals("", labelText(text = "", required = false))
        assertEquals("", labelText(text = "", required = true))
        assertEquals(" ", labelText(text = " ", required = true))
    }
}

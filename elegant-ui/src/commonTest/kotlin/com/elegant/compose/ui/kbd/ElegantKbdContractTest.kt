package com.elegant.compose.ui.kbd

import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantKbdContractTest {
    @Test
    fun defaultsConstantsRemainStable() {
        assertEquals(24.dp, ElegantKbdDefaults.MinHeight)
        assertEquals(6.dp, ElegantKbdDefaults.HorizontalPadding)
        assertEquals(1.dp, ElegantKbdDefaults.BorderWidth)
        assertEquals(KbdMinHeight, ElegantKbdDefaults.MinHeight)
        assertEquals(KbdHorizontalPadding, ElegantKbdDefaults.HorizontalPadding)
        assertEquals(KbdBorderWidth, ElegantKbdDefaults.BorderWidth)
    }

    @Test
    fun themeRolesResolveFromLightColors() {
        val colors = resolveKbdColors(ElegantLightColors)
        assertEquals(ElegantLightColors.surfaceSunken, colors.containerColor)
        assertEquals(ElegantLightColors.textPrimary, colors.contentColor)
        assertEquals(ElegantLightColors.borderDefault, colors.borderColor)
    }

    @Test
    fun themeRolesResolveFromDarkColors() {
        val colors = resolveKbdColors(ElegantDarkColors)
        assertEquals(ElegantDarkColors.surfaceSunken, colors.containerColor)
        assertEquals(ElegantDarkColors.textPrimary, colors.contentColor)
        assertEquals(ElegantDarkColors.borderDefault, colors.borderColor)
    }

    @Test
    fun colorsResolveDistinctlyBetweenLightAndDarkThemes() {
        val light = resolveKbdColors(ElegantLightColors)
        val dark = resolveKbdColors(ElegantDarkColors)
        assertNotEquals(light.containerColor, dark.containerColor)
        assertNotEquals(light.contentColor, dark.contentColor)
        assertNotEquals(light.borderColor, dark.borderColor)
    }
}

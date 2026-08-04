package com.elegant.compose.ui.smalltitle

import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantSmallTitleContractTest {
    @Test
    fun themeRolesResolveFromLightColors() {
        val colors = resolveSmallTitleColors(ElegantLightColors)
        assertEquals(ElegantLightColors.textSecondary, colors.contentColor)
        assertEquals(ElegantSmallTitleColors(ElegantLightColors.textSecondary), colors)
    }

    @Test
    fun themeRolesResolveFromDarkColors() {
        val colors = resolveSmallTitleColors(ElegantDarkColors)
        assertEquals(ElegantDarkColors.textSecondary, colors.contentColor)
        assertEquals(ElegantSmallTitleColors(ElegantDarkColors.textSecondary), colors)
    }

    @Test
    fun colorsResolveDistinctlyBetweenLightAndDarkThemes() {
        val light = resolveSmallTitleColors(ElegantLightColors)
        val dark = resolveSmallTitleColors(ElegantDarkColors)
        assertNotEquals(light, dark)
        assertNotEquals(light.contentColor, dark.contentColor)
    }
}

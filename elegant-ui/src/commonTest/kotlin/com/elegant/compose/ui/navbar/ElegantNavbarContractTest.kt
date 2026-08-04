package com.elegant.compose.ui.navbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantNavbarContractTest {
    @Test
    fun publicMetricsStayOnTheElegantRhythm() {
        assertEquals(56.dp, ElegantNavbarDefaults.Height)
        assertEquals(16.dp, ElegantNavbarDefaults.HorizontalPadding)
        assertEquals(4.dp, ElegantNavbarDefaults.ItemGap)
    }

    @Test
    fun defaultRolesResolveSemanticThemeColors() {
        val light = resolveNavbarColors(ElegantLightColors)
        val dark = resolveNavbarColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceDefault, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.borderDefault, light.borderColor)
        assertEquals(ElegantDarkColors.surfaceDefault, dark.containerColor)
        assertEquals(ElegantDarkColors.textPrimary, dark.contentColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.borderColor)
    }

    @Test
    fun defaultRolesAdaptBetweenLightAndDarkThemes() {
        val light = resolveNavbarColors(ElegantLightColors)
        val dark = resolveNavbarColors(ElegantDarkColors)

        assertNotEquals(light.containerColor, dark.containerColor)
        assertNotEquals(light.contentColor, dark.contentColor)
        assertNotEquals(light.borderColor, dark.borderColor)
    }

    @Test
    fun customColorsRemainAnImmutableValueContract() {
        val colors = ElegantNavbarColors(
            containerColor = Color.Red,
            contentColor = Color.White,
            borderColor = Color.Blue,
        )

        assertEquals(Color.Red, colors.containerColor)
        assertEquals(Color.White, colors.contentColor)
        assertEquals(Color.Blue, colors.borderColor)
        assertEquals(colors, colors.copy())
    }
}

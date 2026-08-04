package com.elegant.compose.ui.bottomsheet

import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ElegantBottomSheetContractTest {

    @Test
    fun colorsResolveFromThemeRoles() {
        val light = resolveBottomSheetColors(ElegantLightColors)
        val dark = resolveBottomSheetColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceRaised, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.borderStrong, light.handleColor)
        assertTrue(light.scrimColor != light.containerColor)
        assertTrue(light.containerColor != dark.containerColor)
        assertTrue(light.contentColor != dark.contentColor)
    }

    @Test
    fun defaultsMatchBottomSheetContract() {
        assertEquals(640.dp, ElegantBottomSheetDefaults.MaxWidth)
        assertEquals(0.4f, ElegantBottomSheetDefaults.ScrimAlpha)
        assertEquals(32.dp, ElegantBottomSheetDefaults.HandleWidth)
        assertEquals(4.dp, ElegantBottomSheetDefaults.HandleHeight)
    }

    @Test
    fun colorsAreImmutableValueCopies() {
        val base = resolveBottomSheetColors(ElegantLightColors)
        val copy = base.copy(contentColor = base.contentColor)
        assertEquals(base, copy)
    }
}

package com.elegant.compose.ui.emptystate

import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

internal class ElegantEmptyStateContractTest {

    @Test
    fun colorsResolveFromTheActiveTheme() {
        val light = resolveEmptyStateColors(ElegantLightColors)
        val dark = resolveEmptyStateColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.interactivePrimary.copy(alpha = 0.10f), light.iconContainerColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.iconContentColor)
        assertEquals(ElegantLightColors.textPrimary, light.titleColor)
        assertEquals(ElegantLightColors.textSecondary, light.descriptionColor)

        assertEquals(ElegantDarkColors.interactivePrimary.copy(alpha = 0.10f), dark.iconContainerColor)
        assertEquals(ElegantDarkColors.interactivePrimary, dark.iconContentColor)
        assertEquals(ElegantDarkColors.textPrimary, dark.titleColor)
        assertEquals(ElegantDarkColors.textSecondary, dark.descriptionColor)

        assertNotEquals(light, dark)
    }

    @Test
    fun blankDescriptionsResolveToNull() {
        assertNull(resolveDescription(null))
        assertNull(resolveDescription(""))
        assertNull(resolveDescription("   "))
        assertEquals("No results yet", resolveDescription("No results yet"))
        assertEquals("  kept as-is  ", resolveDescription("  kept as-is  "))
    }

    @Test
    fun defaultsExposeStableMetrics() {
        assertEquals(64.dp, ElegantEmptyStateDefaults.IconContainerSize)
        assertEquals(8.dp, ElegantEmptyStateDefaults.ItemGap)
        assertEquals(16.dp, ElegantEmptyStateDefaults.ActionGap)
        assertEquals(24.dp, ElegantEmptyStateDefaults.DefaultPadding)
    }
}

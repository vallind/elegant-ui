package com.elegant.compose.ui.navigationbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantNavigationBarContractTest {

    private val items = listOf(
        ElegantNavigationBarItem("Home"),
        ElegantNavigationBarItem("Library"),
        ElegantNavigationBarItem("Settings"),
    )

    @Test
    fun resolveSelectedIndexClampsOutOfRangeSelections() {
        assertEquals(2, resolveSelectedIndex(2, 5))
        assertEquals(0, resolveSelectedIndex(-3, 5))
        assertEquals(4, resolveSelectedIndex(9, 5))
        assertEquals(0, resolveSelectedIndex(0, 0))
        assertEquals(0, resolveSelectedIndex(-1, 0))
    }

    @Test
    fun resolveSelectedIndexAlwaysStaysInValidRange() {
        repeat(50) { index ->
            val resolved = resolveSelectedIndex(index, items.size)
            assertTrue(resolved in items.indices)
        }
    }

    @Test
    fun defaultItemModelIsEnabled() {
        assertTrue(ElegantNavigationBarItem("Home").enabled)
        assertEquals("Home", ElegantNavigationBarItem("Home").text)
        assertEquals(ElegantNavigationBarItem("Home"), ElegantNavigationBarItem("Home"))
    }

    @Test
    fun resolveNavigationBarColorsFollowsThemeRoles() {
        val light = resolveNavigationBarColors(ElegantLightColors)
        val dark = resolveNavigationBarColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceRaised, light.containerColor)
        assertEquals(ElegantLightColors.surfaceHover, light.selectedItemColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.selectedContentColor)
        assertEquals(ElegantLightColors.textSecondary, light.itemColor)
        assertEquals(ElegantLightColors.textPrimary, light.hoveredItemColor)
        assertEquals(ElegantLightColors.textPrimary, light.pressedItemColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledItemColor)
        assertEquals(ElegantLightColors.surfaceHover, light.indicatorColor)
        assertEquals(ElegantDarkColors.surfaceRaised, dark.containerColor)
        assertEquals(ElegantDarkColors.surfaceHover, dark.selectedItemColor)
        assertEquals(ElegantDarkColors.interactivePrimary, dark.selectedContentColor)
        assertEquals(ElegantDarkColors.textTertiary, dark.disabledItemColor)
        assertEquals(ElegantDarkColors.surfaceHover, dark.indicatorColor)
        assertNotEquals(light, dark, "the bar must follow the active theme")
    }

    @Test
    fun colorsDataClassDefaultsKeepInteractionColorsOnItemColor() {
        val base = ElegantNavigationBarColors(
            containerColor = ElegantLightColors.surfaceRaised,
            selectedItemColor = ElegantLightColors.surfaceHover,
            selectedContentColor = ElegantLightColors.interactivePrimary,
            itemColor = ElegantLightColors.textSecondary,
            indicatorColor = ElegantLightColors.surfaceHover,
        )

        assertEquals(base.itemColor, base.hoveredItemColor)
        assertEquals(base.itemColor, base.pressedItemColor)
        assertEquals(base.itemColor, base.disabledItemColor)
    }

    @Test
    fun defaultsMeetInteractionAndIndicatorContracts() {
        assertTrue(ElegantNavigationBarDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(64.dp, ElegantNavigationBarDefaults.MinimumTouchHeight)
        assertEquals(32.dp, ElegantNavigationBarDefaults.IndicatorSize)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantNavigationBarDefaults.AnimationDurationMillis,
        )
    }
}

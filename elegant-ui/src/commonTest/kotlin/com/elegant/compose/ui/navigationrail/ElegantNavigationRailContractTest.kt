package com.elegant.compose.ui.navigationrail

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantNavigationRailContractTest {

    @Test
    fun colorsMapThemeRoles() {
        val colors = resolveNavigationRailColors(ElegantLightColors)

        assertEquals(Color.Transparent, colors.containerColor)
        assertEquals(ElegantLightColors.surfaceHover, colors.selectedItemColor)
        assertEquals(ElegantLightColors.interactivePrimary, colors.selectedContentColor)
        assertEquals(ElegantLightColors.textSecondary, colors.itemColor)
        assertEquals(ElegantLightColors.textPrimary, colors.hoveredItemColor)
        assertEquals(ElegantLightColors.surfaceHover, colors.hoveredContainerColor)
        assertEquals(ElegantLightColors.textTertiary, colors.disabledItemColor)
        assertEquals(
            ElegantLightColors.interactivePrimary.copy(alpha = 0.12f),
            colors.indicatorColor,
        )
    }

    @Test
    fun colorsFollowTheActiveTheme() {
        val light = resolveNavigationRailColors(ElegantLightColors)
        val dark = resolveNavigationRailColors(ElegantDarkColors)

        assertNotEquals(light, dark)
        assertNotEquals(light.selectedContentColor, dark.selectedContentColor)
        assertNotEquals(light.indicatorColor, dark.indicatorColor)
    }

    @Test
    fun itemColorPrecedenceIsDisabledSelectedHoveredResting() {
        val colors = resolveNavigationRailColors(ElegantLightColors)

        assertEquals(
            colors.disabledItemColor,
            resolveItemColor(colors, selected = true, hovered = true, enabled = false),
        )
        assertEquals(
            colors.disabledItemColor,
            resolveItemColor(colors, selected = true, hovered = false, enabled = false),
        )
        assertEquals(
            colors.selectedContentColor,
            resolveItemColor(colors, selected = true, hovered = true, enabled = true),
        )
        assertEquals(
            colors.selectedContentColor,
            resolveItemColor(colors, selected = true, hovered = false, enabled = true),
        )
        assertEquals(
            colors.hoveredItemColor,
            resolveItemColor(colors, selected = false, hovered = true, enabled = true),
        )
        assertEquals(
            colors.itemColor,
            resolveItemColor(colors, selected = false, hovered = false, enabled = true),
        )
    }

    @Test
    fun indicatorPrecedenceIsSelectedHoveredResting() {
        val colors = resolveNavigationRailColors(ElegantLightColors)

        assertEquals(
            colors.selectedItemColor,
            resolveIndicatorContainer(colors, selected = true, hovered = true),
        )
        assertEquals(
            colors.indicatorColor,
            resolveIndicatorContainer(colors, selected = true, hovered = false),
        )
        assertEquals(
            colors.hoveredContainerColor,
            resolveIndicatorContainer(colors, selected = false, hovered = true),
        )
        assertEquals(
            colors.containerColor,
            resolveIndicatorContainer(colors, selected = false, hovered = false),
        )
    }

    @Test
    fun customColorsHonorDefaultsAndOverrideEveryState() {
        val inherited = ElegantNavigationRailColors(
            containerColor = Color.Red,
            selectedItemColor = Color.Yellow,
            selectedContentColor = Color.Blue,
            itemColor = Color.Green,
            indicatorColor = Color.Transparent,
        )

        assertEquals(Color.Green, inherited.hoveredItemColor)
        assertEquals(Color.Red, inherited.hoveredContainerColor)
        assertEquals(Color.Green, inherited.disabledItemColor)

        val custom = ElegantNavigationRailColors(
            containerColor = Color.Red,
            selectedItemColor = Color.Yellow,
            selectedContentColor = Color.Blue,
            itemColor = Color.Green,
            hoveredItemColor = Color.Cyan,
            hoveredContainerColor = Color.Magenta,
            disabledItemColor = Color.Gray,
            indicatorColor = Color.Transparent,
        )

        assertEquals(Color.Gray, resolveItemColor(custom, selected = true, hovered = true, enabled = false))
        assertEquals(Color.Blue, resolveItemColor(custom, selected = true, hovered = true, enabled = true))
        assertEquals(Color.Cyan, resolveItemColor(custom, selected = false, hovered = true, enabled = true))
        assertEquals(Color.Green, resolveItemColor(custom, selected = false, hovered = false, enabled = true))
        assertEquals(Color.Yellow, resolveIndicatorContainer(custom, selected = true, hovered = true))
        assertEquals(Color.Transparent, resolveIndicatorContainer(custom, selected = true, hovered = false))
        assertEquals(Color.Magenta, resolveIndicatorContainer(custom, selected = false, hovered = true))
        assertEquals(Color.Red, resolveIndicatorContainer(custom, selected = false, hovered = false))
    }

    @Test
    fun selectedIndexIsCoercedIntoItemIndices() {
        assertEquals(0, resolveSelectedIndex(-1, 3))
        assertEquals(1, resolveSelectedIndex(1, 3))
        assertEquals(2, resolveSelectedIndex(7, 3))
        assertEquals(0, resolveSelectedIndex(0, 1))
        assertEquals(0, resolveSelectedIndex(5, 0))
        assertEquals(0, resolveSelectedIndex(-4, 0))
    }

    @Test
    fun itemModelDefaultsToEnabled() {
        val item = ElegantNavigationRailItem("Home")

        assertTrue(item.enabled)
        assertEquals("Home", item.text)
        assertEquals("Trash", ElegantNavigationRailItem("Trash", enabled = false).text)
    }

    @Test
    fun defaultsExposeSizingAndMotion() {
        assertEquals(80.dp, ElegantNavigationRailDefaults.Width)
        assertEquals(48.dp, ElegantNavigationRailDefaults.MinimumTouchHeight)
        assertEquals(48.dp, ElegantNavigationRailDefaults.IndicatorSize)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantNavigationRailDefaults.AnimationDurationMillis,
        )
    }
}

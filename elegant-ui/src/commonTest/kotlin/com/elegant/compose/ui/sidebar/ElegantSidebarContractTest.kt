package com.elegant.compose.ui.sidebar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantSidebarContractTest {

    @Test
    fun colorsMapThemeRoles() {
        val colors = resolveSidebarColors(ElegantLightColors)

        assertEquals(Color.Transparent, colors.containerColor)
        assertEquals(ElegantLightColors.textSecondary, colors.itemContentColor)
        assertEquals(ElegantLightColors.interactivePrimary, colors.selectedItemContentColor)
        assertEquals(
            ElegantLightColors.interactivePrimary.copy(alpha = 0.10f),
            colors.selectedItemContainerColor,
        )
        assertEquals(ElegantLightColors.textPrimary, colors.hoveredItemContentColor)
        assertEquals(ElegantLightColors.surfaceHover, colors.hoveredItemContainerColor)
        assertEquals(ElegantLightColors.textTertiary, colors.disabledItemContentColor)
    }

    @Test
    fun colorsFollowTheActiveTheme() {
        val light = resolveSidebarColors(ElegantLightColors)
        val dark = resolveSidebarColors(ElegantDarkColors)

        assertNotEquals(light, dark)
        assertNotEquals(light.itemContentColor, dark.itemContentColor)
        assertNotEquals(light.selectedItemContainerColor, dark.selectedItemContainerColor)
    }

    @Test
    fun itemColorPrecedenceIsDisabledSelectedHoveredResting() {
        val colors = resolveSidebarColors(ElegantLightColors)

        assertEquals(
            colors.disabledItemContentColor,
            resolveItemColor(colors, selected = true, hovered = true, enabled = false),
        )
        assertEquals(
            colors.disabledItemContentColor,
            resolveItemColor(colors, selected = true, hovered = false, enabled = false),
        )
        assertEquals(
            colors.selectedItemContentColor,
            resolveItemColor(colors, selected = true, hovered = true, enabled = true),
        )
        assertEquals(
            colors.selectedItemContentColor,
            resolveItemColor(colors, selected = true, hovered = false, enabled = true),
        )
        assertEquals(
            colors.hoveredItemContentColor,
            resolveItemColor(colors, selected = false, hovered = true, enabled = true),
        )
        assertEquals(
            colors.itemContentColor,
            resolveItemColor(colors, selected = false, hovered = false, enabled = true),
        )
    }

    @Test
    fun itemContainerPrecedenceIsSelectedHoveredResting() {
        val colors = resolveSidebarColors(ElegantLightColors)

        assertEquals(
            colors.selectedItemContainerColor,
            resolveItemContainer(colors, selected = true, hovered = true),
        )
        assertEquals(
            colors.selectedItemContainerColor,
            resolveItemContainer(colors, selected = true, hovered = false),
        )
        assertEquals(
            colors.hoveredItemContainerColor,
            resolveItemContainer(colors, selected = false, hovered = true),
        )
        assertEquals(
            colors.containerColor,
            resolveItemContainer(colors, selected = false, hovered = false),
        )
    }

    @Test
    fun customColorsHonorDefaultsAndOverrideEveryState() {
        val inherited = ElegantSidebarColors(
            containerColor = Color.Red,
            itemContentColor = Color.Green,
            selectedItemContentColor = Color.Blue,
            selectedItemContainerColor = Color.Transparent,
        )

        assertEquals(Color.Green, inherited.hoveredItemContentColor)
        assertEquals(Color.Red, inherited.hoveredItemContainerColor)
        assertEquals(Color.Green, inherited.disabledItemContentColor)

        val custom = ElegantSidebarColors(
            containerColor = Color.Red,
            itemContentColor = Color.Green,
            selectedItemContentColor = Color.Blue,
            selectedItemContainerColor = Color.Transparent,
            hoveredItemContentColor = Color.Yellow,
            hoveredItemContainerColor = Color.Cyan,
            disabledItemContentColor = Color.Gray,
        )

        assertEquals(Color.Gray, resolveItemColor(custom, selected = true, hovered = true, enabled = false))
        assertEquals(Color.Blue, resolveItemColor(custom, selected = true, hovered = true, enabled = true))
        assertEquals(Color.Yellow, resolveItemColor(custom, selected = false, hovered = true, enabled = true))
        assertEquals(Color.Green, resolveItemColor(custom, selected = false, hovered = false, enabled = true))
        assertEquals(Color.Cyan, resolveItemContainer(custom, selected = false, hovered = true))
        assertEquals(Color.Red, resolveItemContainer(custom, selected = false, hovered = false))
    }

    @Test
    fun itemModelDefaultsToEnabled() {
        val item = ElegantSidebarItem("Overview")

        assertTrue(item.enabled)
        assertEquals("Overview", item.text)
        assertEquals("Reports", ElegantSidebarItem("Reports", enabled = false).text)
    }

    @Test
    fun defaultsExposeSizingSpacingAndMotion() {
        assertEquals(240.dp, ElegantSidebarDefaults.Width)
        assertEquals(40.dp, ElegantSidebarDefaults.ItemHeight)
        assertEquals(12.dp, ElegantSidebarDefaults.ItemHorizontalPadding)
        assertEquals(4.dp, ElegantSidebarDefaults.ItemGap)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantSidebarDefaults.AnimationDurationMillis,
        )
    }
}

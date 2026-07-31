package com.elegant.compose.ui.breadcrumb

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantBreadcrumbContractTest {

    @Test
    fun colorsResolveSemanticThemeRoles() {
        val light = resolveBreadcrumbColors(ElegantLightColors)
        val dark = resolveBreadcrumbColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.interactivePrimary, light.itemColor)
        assertEquals(ElegantLightColors.textPrimary, light.currentColor)
        assertEquals(ElegantLightColors.textTertiary, light.separatorColor)
        assertEquals(ElegantLightColors.interactivePrimaryHover, light.hoveredItemColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledItemColor)
        assertNotEquals(light, dark, "breadcrumb colors must follow the active theme")
    }

    @Test
    fun stateColorsFallBackToTheRestingItemColor() {
        val colors = ElegantBreadcrumbColors(
            itemColor = Color.Red,
            currentColor = Color.Green,
            separatorColor = Color.Blue,
        )

        assertEquals(Color.Red, colors.hoveredItemColor)
        assertEquals(Color.Red, colors.disabledItemColor)
        assertEquals(Color.Green, colors.currentColor)
        assertEquals(Color.Blue, colors.separatorColor)
    }

    @Test
    fun currentItemResolutionHandlesEmptySingleAndMultiItemBreadcrumbs() {
        assertFalse(isCurrentItem(0, 0))
        assertFalse(isCurrentItem(1, 0))

        assertTrue(isCurrentItem(0, 1))
        assertFalse(isCurrentItem(-1, 1))
        assertFalse(isCurrentItem(1, 1))

        assertFalse(isCurrentItem(0, 3))
        assertFalse(isCurrentItem(1, 3))
        assertTrue(isCurrentItem(2, 3))
        assertFalse(isCurrentItem(3, 3))
    }

    @Test
    fun itemColorPrecedenceFollowsCurrentDisabledHoveredResting() {
        val colors = ElegantBreadcrumbColors(
            itemColor = Color.Red,
            currentColor = Color.Green,
            separatorColor = Color.Blue,
            hoveredItemColor = Color.Magenta,
            disabledItemColor = Color.Gray,
        )

        assertEquals(
            Color.Green,
            resolveItemColor(colors, isCurrent = true, enabled = true, hovered = true),
        )
        assertEquals(
            Color.Green,
            resolveItemColor(colors, isCurrent = true, enabled = false, hovered = false),
        )
        assertEquals(
            Color.Gray,
            resolveItemColor(colors, isCurrent = false, enabled = false, hovered = true),
        )
        assertEquals(
            Color.Magenta,
            resolveItemColor(colors, isCurrent = false, enabled = true, hovered = true),
        )
        assertEquals(
            Color.Red,
            resolveItemColor(colors, isCurrent = false, enabled = true, hovered = false),
        )
    }

    @Test
    fun defaultsConstantsMeetAccessibilityAndRhythmRequirements() {
        assertTrue(ElegantBreadcrumbDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(4.dp, ElegantBreadcrumbDefaults.ItemGap)
        assertEquals(16.dp, BreadcrumbSeparatorSize)
    }

    @Test
    fun itemModelDefaultsToEnabledTextEntry() {
        assertEquals("Home", ElegantBreadcrumbItem("Home").text)
        assertTrue(ElegantBreadcrumbItem("Home").enabled)
        assertFalse(ElegantBreadcrumbItem("Archive", enabled = false).enabled)
        assertEquals(
            ElegantBreadcrumbItem("Home"),
            ElegantBreadcrumbItem("Home").copy(),
        )
    }
}

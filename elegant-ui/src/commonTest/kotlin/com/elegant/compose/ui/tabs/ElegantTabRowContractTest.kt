package com.elegant.compose.ui.tabs

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantTabRowContractTest {

    private val allEnabledTabs = listOf(
        ElegantTab("Overview"),
        ElegantTab("Projects"),
        ElegantTab("Settings"),
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
            val resolved = resolveSelectedIndex(index, allEnabledTabs.size)
            assertTrue(resolved in allEnabledTabs.indices)
        }
    }

    @Test
    fun nextEnabledTabMovesForwardAndWrapsAround() {
        assertEquals(1, nextEnabledTab(allEnabledTabs, 0, 1))
        assertEquals(2, nextEnabledTab(allEnabledTabs, 1, 1))
        assertEquals(0, nextEnabledTab(allEnabledTabs, 2, 1))
    }

    @Test
    fun nextEnabledTabMovesBackwardAndWrapsAround() {
        assertEquals(1, nextEnabledTab(allEnabledTabs, 2, -1))
        assertEquals(0, nextEnabledTab(allEnabledTabs, 1, -1))
        assertEquals(2, nextEnabledTab(allEnabledTabs, 0, -1))
    }

    @Test
    fun nextEnabledTabSkipsDisabledTabsInBothDirections() {
        val tabs = listOf(
            ElegantTab("Overview"),
            ElegantTab("Projects", enabled = false),
            ElegantTab("Settings"),
        )

        assertEquals(2, nextEnabledTab(tabs, 0, 1))
        assertEquals(0, nextEnabledTab(tabs, 2, 1))
        assertEquals(0, nextEnabledTab(tabs, 2, -1))
        assertEquals(2, nextEnabledTab(tabs, 1, 1))
    }

    @Test
    fun nextEnabledTabReturnsMinusOneWhenNoTabIsEnabled() {
        val tabs = listOf(
            ElegantTab("Overview", enabled = false),
            ElegantTab("Projects", enabled = false),
        )

        assertEquals(-1, nextEnabledTab(tabs, 0, 1))
        assertEquals(-1, nextEnabledTab(tabs, 1, -1))
        assertEquals(-1, nextEnabledTab(emptyList(), 0, 1))
    }

    @Test
    fun defaultTabModelIsEnabled() {
        assertTrue(ElegantTab("Overview").enabled)
        assertEquals("Overview", ElegantTab("Overview").text)
        assertEquals(ElegantTab("Overview"), ElegantTab("Overview"))
    }

    @Test
    fun resolveTabColorsFollowsThemeRoles() {
        val light = resolveTabColors(ElegantLightColors)
        val dark = resolveTabColors(ElegantDarkColors)

        assertEquals(Color.Transparent, light.containerColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.indicatorColor)
        assertEquals(ElegantLightColors.textSecondary, light.contentColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.selectedContentColor)
        assertEquals(ElegantLightColors.textPrimary, light.hoveredContentColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledContentColor)
        assertEquals(ElegantDarkColors.textTertiary, dark.disabledContentColor)
        assertNotEquals(light, dark, "tabs must follow the active theme")
    }

    @Test
    fun colorsDataClassDefaultsKeepHoveredAndDisabledOnContentColor() {
        val base = ElegantTabColors(
            containerColor = Color.Transparent,
            indicatorColor = ElegantLightColors.interactivePrimary,
            contentColor = ElegantLightColors.textSecondary,
            selectedContentColor = ElegantLightColors.interactivePrimary,
        )

        assertEquals(base.contentColor, base.hoveredContentColor)
        assertEquals(base.contentColor, base.disabledContentColor)
    }

    @Test
    fun defaultsMeetInteractionAndIndicatorContracts() {
        assertTrue(ElegantTabDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(2.dp, ElegantTabDefaults.IndicatorHeight)
        assertEquals(ElegantMotion.standardDurationMillis, ElegantTabDefaults.AnimationDurationMillis)
    }
}

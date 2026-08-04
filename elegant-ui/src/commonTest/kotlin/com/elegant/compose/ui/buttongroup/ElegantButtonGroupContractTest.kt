package com.elegant.compose.ui.buttongroup

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantButtonGroupContractTest {

    @Test
    fun itemModelDefaultsToEnabled() {
        assertEquals(ElegantButtonGroupItem("A"), ElegantButtonGroupItem("A", enabled = true))
        assertEquals(false, ElegantButtonGroupItem("A", enabled = false).enabled)
    }

    @Test
    fun defaultColorsResolveFromLightTheme() {
        val colors = resolveButtonGroupColors(ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceRaised, colors.containerColor)
        assertEquals(ElegantLightColors.textPrimary, colors.contentColor)
        assertEquals(ElegantLightColors.borderDefault, colors.borderColor)
        assertEquals(ElegantLightColors.backgroundSubtle, colors.selectedContainerColor)
        assertEquals(ElegantLightColors.interactivePrimary, colors.selectedContentColor)
        assertEquals(ElegantLightColors.surfaceHover, colors.hoveredContainerColor)
        assertEquals(ElegantLightColors.surfaceSunken, colors.pressedContainerColor)
        assertEquals(ElegantLightColors.surfaceSunken, colors.disabledContainerColor)
        assertEquals(ElegantLightColors.textTertiary, colors.disabledContentColor)
    }

    @Test
    fun colorsFollowTheActiveTheme() {
        assertNotEquals(
            resolveButtonGroupColors(ElegantLightColors),
            resolveButtonGroupColors(ElegantDarkColors),
        )
    }

    @Test
    fun colorsDefaultArgumentsStabilizeInteractionStates() {
        val colors = ElegantButtonGroupColors(
            containerColor = Color(0xFF000001),
            contentColor = Color(0xFF000002),
            borderColor = Color(0xFF000003),
            selectedContainerColor = Color(0xFF000004),
            selectedContentColor = Color(0xFF000005),
        )

        assertEquals(colors.containerColor, colors.hoveredContainerColor)
        assertEquals(colors.containerColor, colors.pressedContainerColor)
        assertEquals(colors.containerColor, colors.disabledContainerColor)
        assertEquals(colors.contentColor, colors.disabledContentColor)
    }

    @Test
    fun nullSelectionResolvesToNothing() {
        assertNull(resolveSelectedIndex(null, 3))
    }

    @Test
    fun emptyItemsResolveToNothing() {
        assertNull(resolveSelectedIndex(0, 0))
        assertNull(resolveSelectedIndex(null, 0))
    }

    @Test
    fun negativeSelectionCoercesToFirstItem() {
        assertEquals(0, resolveSelectedIndex(-1, 3))
        assertEquals(0, resolveSelectedIndex(-100, 3))
    }

    @Test
    fun overflowingSelectionCoercesToLastItem() {
        assertEquals(2, resolveSelectedIndex(3, 3))
        assertEquals(2, resolveSelectedIndex(100, 3))
    }

    @Test
    fun validSelectionIsPreserved() {
        assertEquals(1, resolveSelectedIndex(1, 3))
        assertEquals(2, resolveSelectedIndex(2, 3))
    }

    @Test
    fun defaultsMeetMetricAndAccessibilityContracts() {
        assertTrue(ElegantButtonGroupDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(36.dp, ElegantButtonGroupDefaults.Height)
        assertEquals(12.dp, ElegantButtonGroupDefaults.HorizontalPadding)
        assertEquals(ElegantMotion.standardDurationMillis, ElegantButtonGroupDefaults.AnimationDurationMillis)
    }
}

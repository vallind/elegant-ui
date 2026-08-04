package com.elegant.compose.ui.basiccomponent

import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ElegantBasicComponentContractTest {

    @Test
    fun colorsResolveFromThemeRoles() {
        val light = resolveBasicComponentColors(ElegantLightColors)
        val dark = resolveBasicComponentColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceDefault, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.titleColor)
        assertEquals(ElegantLightColors.textSecondary, light.summaryColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledTitleColor)
        assertTrue(light.containerColor != dark.containerColor)
        assertTrue(light.titleColor != dark.titleColor)
    }

    @Test
    fun textColorsRespectEnabledPriority() {
        val colors = resolveBasicComponentColors(ElegantLightColors)

        assertEquals(colors.titleColor, basicComponentTitleColor(colors, enabled = true))
        assertEquals(colors.disabledTitleColor, basicComponentTitleColor(colors, enabled = false))
        assertEquals(colors.summaryColor, basicComponentSummaryColor(colors, enabled = true))
        assertEquals(colors.disabledSummaryColor, basicComponentSummaryColor(colors, enabled = false))
    }

    @Test
    fun visualsResolvePressThenHoverThenRest() {
        val colors = resolveBasicComponentColors(ElegantLightColors)

        val pressed = resolveBasicComponentVisuals(colors, enabled = true, pressed = true, hovered = true)
        assertEquals(colors.pressedContainerColor, pressed.containerColor)

        val hovered = resolveBasicComponentVisuals(colors, enabled = true, pressed = false, hovered = true)
        assertEquals(colors.hoveredContainerColor, hovered.containerColor)

        val rest = resolveBasicComponentVisuals(colors, enabled = true, pressed = false, hovered = false)
        assertEquals(colors.containerColor, rest.containerColor)

        val disabled = resolveBasicComponentVisuals(colors, enabled = false, pressed = true, hovered = true)
        assertEquals(colors.containerColor, disabled.containerColor)
    }

    @Test
    fun defaultsMeetAccessibilityAndTokenBaselines() {
        assertTrue(ElegantBasicComponentDefaults.MinimumTouchHeight >= 48.dp)
    }
}

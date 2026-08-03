package com.elegant.compose.ui.preference

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantSpacing
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantArrowPreferenceContractTest {

    @Test
    fun defaultColorsFollowTheActiveTheme() {
        val light = resolveArrowPreferenceColors(ElegantLightColors)
        val dark = resolveArrowPreferenceColors(ElegantDarkColors)

        assertEquals(Color.Transparent, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.titleColor)
        assertEquals(ElegantLightColors.textSecondary, light.supportingTextColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledTitleColor)
        assertEquals(ElegantLightColors.borderDefault, light.dividerColor)
        assertEquals(ElegantLightColors.textTertiary, light.arrowColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredContainerColor)
        assertEquals(ElegantLightColors.backgroundSubtle, light.pressedContainerColor)
        assertNotEquals(light, dark, "preference colors must follow the active theme")
    }

    @Test
    fun customColorsDefaultInteractionTintsToContainer() {
        val colors = ElegantArrowPreferenceColors(
            containerColor = Color(0xFFFF0000),
            titleColor = Color.White,
            supportingTextColor = Color.Gray,
            disabledTitleColor = Color.DarkGray,
            dividerColor = Color.Black,
            arrowColor = Color.LightGray,
        )

        assertEquals(colors.containerColor, colors.hoveredContainerColor)
        assertEquals(colors.containerColor, colors.pressedContainerColor)
    }

    @Test
    fun containerPrecedenceFollowsDisabledPressedHoveredResting() {
        val colors = resolveArrowPreferenceColors(ElegantLightColors)

        fun container(
            enabled: Boolean = true,
            pressed: Boolean = false,
            hovered: Boolean = false,
        ) = resolveArrowPreferenceVisuals(
            colors = colors,
            enabled = enabled,
            pressed = pressed,
            hovered = hovered,
        ).containerColor

        assertEquals(colors.containerColor, container(enabled = false))
        assertEquals(
            colors.containerColor,
            container(enabled = false, pressed = true, hovered = true),
        )
        assertEquals(colors.pressedContainerColor, container(pressed = true, hovered = true))
        assertEquals(colors.hoveredContainerColor, container(hovered = true))
        assertEquals(colors.containerColor, container())
    }

    @Test
    fun blankSupportingTextResolvesToNull() {
        assertEquals("Details", resolveSupportingText("Details"))
        assertNull(resolveSupportingText(null))
        assertNull(resolveSupportingText(""))
        assertNull(resolveSupportingText("   "))
    }

    @Test
    fun defaultsMeetAccessibilityAndTokenBaselines() {
        assertTrue(ElegantArrowPreferenceDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ArrowPreferenceAnimationDurationMillis,
        )
        assertEquals(ElegantSpacing.xl, ArrowPreferenceContentPadding)
        assertEquals(ElegantSpacing.md, ArrowPreferenceGap)
        assertEquals(ElegantSpacing.xl, ArrowPreferenceDividerInset)
        assertEquals(18.dp, ArrowPreferenceChevronSize)
        assertEquals(2.dp, ArrowPreferenceChevronStrokeWidth)
    }
}

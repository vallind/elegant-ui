package com.elegant.compose.ui.radio

import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantRadioContractTest {

    @Test
    fun defaultColorsFollowTheActiveTheme() {
        val light = resolveRadioColors(ElegantLightColors)
        val dark = resolveRadioColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.interactivePrimary, light.selectedColor)
        assertEquals(ElegantLightColors.borderStrong, light.unselectedColor)
        assertEquals(ElegantLightColors.interactivePrimaryHover, light.hoveredSelectedColor)
        assertEquals(ElegantLightColors.interactivePrimaryPressed, light.pressedSelectedColor)
        assertEquals(ElegantLightColors.borderStrong, light.pressedUnselectedColor)
        assertEquals(ElegantLightColors.borderDefault, light.disabledUnselectedColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
        assertNotEquals(light, dark, "radio colors must follow the active theme")
    }

    @Test
    fun hoveredAndDisabledColorsAreThemedAccentTints() {
        val light = resolveRadioColors(ElegantLightColors)

        assertEquals(ElegantLightColors.interactivePrimary.copy(alpha = 0.55f), light.hoveredUnselectedColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.hoveredUnselectedColor.copy(alpha = 1f))
        assertEquals(ElegantLightColors.interactivePrimary.copy(alpha = 0.35f), light.disabledSelectedColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.disabledSelectedColor.copy(alpha = 1f))
    }

    @Test
    fun selectedAndUnselectedStatesStayVisuallyDistinct() {
        val light = resolveRadioColors(ElegantLightColors)

        assertNotEquals(light.selectedColor, light.unselectedColor)
        assertNotEquals(light.hoveredSelectedColor, light.hoveredUnselectedColor)
        assertNotEquals(light.pressedSelectedColor, light.pressedUnselectedColor)
        assertNotEquals(light.disabledSelectedColor, light.disabledUnselectedColor)
    }

    @Test
    fun visualPrecedenceFollowsDisabledPressedFocusedHoveredResting() {
        val colors = resolveRadioColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            selected: Boolean = false,
            pressed: Boolean = false,
            hovered: Boolean = false,
            focused: Boolean = false,
        ) = resolveRadioVisuals(
            colors = colors,
            enabled = enabled,
            selected = selected,
            pressed = pressed,
            hovered = hovered,
            focused = focused,
        )

        assertEquals(colors.disabledUnselectedColor, visuals(enabled = false).color)
        assertEquals(
            colors.disabledSelectedColor,
            visuals(enabled = false, selected = true, pressed = true, hovered = true, focused = true).color,
        )
        assertEquals(
            colors.pressedSelectedColor,
            visuals(selected = true, pressed = true, hovered = true, focused = true).color,
        )
        assertEquals(colors.focusedBorderColor, visuals(hovered = true, focused = true).color)
        assertEquals(colors.hoveredUnselectedColor, visuals(hovered = true).color)
        assertEquals(colors.unselectedColor, visuals().color)
        assertEquals(colors.selectedColor, visuals(selected = true).color)
    }

    @Test
    fun dotScaleTracksSelectionInEveryState() {
        val colors = resolveRadioColors(ElegantLightColors)

        assertEquals(1f, resolveRadioVisuals(
            colors = colors,
            enabled = false,
            selected = true,
            pressed = true,
            hovered = true,
            focused = true,
        ).dotScale)
        assertEquals(0f, resolveRadioVisuals(
            colors = colors,
            enabled = true,
            selected = false,
            pressed = true,
            hovered = true,
            focused = true,
        ).dotScale)
    }

    @Test
    fun defaultsMeetAccessibilityAndTokenBaselines() {
        assertEquals(20.dp, ElegantRadioDefaults.BoxSize)
        assertTrue(ElegantRadioDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(ElegantMotion.standardDurationMillis, ElegantRadioDefaults.AnimationDurationMillis)
    }
}

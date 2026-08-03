package com.elegant.compose.ui.togglebutton

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantToggleButtonContractTest {

    @Test
    fun defaultColorsResolveThemeAwareTones() {
        val light = resolveToggleButtonColors(ElegantLightColors)
        val dark = resolveToggleButtonColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceRaised, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.borderDefault, light.borderColor)
        assertEquals(1.dp, light.borderWidth)
        assertEquals(ElegantLightColors.backgroundSubtle, light.selectedContainerColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.selectedContentColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.selectedBorderColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredContainerColor)
        assertEquals(ElegantLightColors.surfaceSunken, light.pressedContainerColor)
        assertEquals(ElegantLightColors.surfaceSunken, light.disabledContainerColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledContentColor)
        assertEquals(ElegantLightColors.borderDefault, light.disabledBorderColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
        assertNotEquals(light.containerColor, dark.containerColor)
        assertNotEquals(light.selectedContentColor, dark.selectedContentColor)
    }

    @Test
    fun dataClassDefaultsFallBackToBaseTones() {
        val base = ElegantToggleButtonColors(
            containerColor = Color.Red,
            contentColor = Color.Green,
            borderColor = Color.Blue,
            borderWidth = 2.dp,
            selectedContainerColor = Color.Magenta,
            selectedContentColor = Color.Cyan,
            selectedBorderColor = Color.Yellow,
        )

        assertEquals(Color.Red, base.hoveredContainerColor)
        assertEquals(Color.Red, base.pressedContainerColor)
        assertEquals(Color.Red, base.disabledContainerColor)
        assertEquals(Color.Magenta, base.selectedContainerColor)
        assertEquals(Color.Green, base.hoveredContentColor)
        assertEquals(Color.Green, base.pressedContentColor)
        assertEquals(Color.Green, base.disabledContentColor)
        assertEquals(Color.Cyan, base.selectedContentColor)
        assertEquals(Color.Blue, base.disabledBorderColor)
        assertEquals(Color.Blue, base.focusedBorderColor)
        assertEquals(Color.Yellow, base.selectedBorderColor)
        assertEquals(2.dp, base.borderWidth)
    }

    @Test
    fun visualPrecedenceFollowsDisabledSelectedPressedHoveredResting() {
        val colors = resolveToggleButtonColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            selected: Boolean = false,
            pressed: Boolean = false,
            hovered: Boolean = false,
            focused: Boolean = false,
        ) = resolveToggleButtonVisuals(
            colors = colors,
            enabled = enabled,
            selected = selected,
            pressed = pressed,
            hovered = hovered,
            focused = focused,
        )

        val disabled = visuals(enabled = false)
        assertEquals(colors.disabledContainerColor, disabled.container)
        assertEquals(colors.disabledContentColor, disabled.content)
        assertEquals(colors.disabledBorderColor, disabled.border)
        assertEquals(colors.borderWidth, disabled.borderWidth)

        val disabledPressedSelected = visuals(enabled = false, pressed = true, selected = true)
        assertEquals(colors.disabledContainerColor, disabledPressedSelected.container)
        assertEquals(colors.disabledContentColor, disabledPressedSelected.content)

        assertEquals(
            colors.selectedContainerColor,
            visuals(pressed = true, selected = true).container,
        )
        assertEquals(
            colors.selectedContentColor,
            visuals(pressed = true, selected = true).content,
        )
        assertEquals(
            colors.selectedContainerColor,
            visuals(hovered = true, selected = true).container,
        )
        assertEquals(colors.pressedContainerColor, visuals(pressed = true).container)
        assertEquals(colors.hoveredContainerColor, visuals(hovered = true).container)
        assertEquals(colors.containerColor, visuals().container)
        assertEquals(colors.contentColor, visuals().content)
        assertEquals(colors.borderColor, visuals().border)
    }

    @Test
    fun focusBorderWinsOverSelectedAndHoveredWithoutChangingSelectedVisuals() {
        val colors = resolveToggleButtonColors(ElegantLightColors)
        val focused = resolveToggleButtonVisuals(
            colors = colors,
            enabled = true,
            selected = true,
            pressed = false,
            hovered = true,
            focused = true,
        )

        assertEquals(colors.focusedBorderColor, focused.border)
        assertEquals(colors.borderWidth, focused.borderWidth)
        assertEquals(colors.selectedContainerColor, focused.container)
        assertEquals(colors.selectedContentColor, focused.content)
    }

    @Test
    fun borderWidthStaysConstantAcrossEveryState() {
        val colors = resolveToggleButtonColors(ElegantLightColors)

        resolveToggleButtonVisuals(
            colors = colors,
            enabled = true,
            selected = false,
            pressed = false,
            hovered = false,
            focused = false,
        ).let { assertEquals(colors.borderWidth, it.borderWidth) }
        resolveToggleButtonVisuals(
            colors = colors,
            enabled = false,
            selected = true,
            pressed = true,
            hovered = true,
            focused = true,
        ).let { assertEquals(colors.borderWidth, it.borderWidth) }
    }

    @Test
    fun selectedAndRestingVisualsDiffer() {
        val colors = resolveToggleButtonColors(ElegantLightColors)
        val resting = resolveToggleButtonVisuals(
            colors = colors,
            enabled = true,
            selected = false,
            pressed = false,
            hovered = false,
            focused = false,
        )
        val selected = resolveToggleButtonVisuals(
            colors = colors,
            enabled = true,
            selected = true,
            pressed = false,
            hovered = false,
            focused = false,
        )

        assertNotEquals(resting.container, selected.container)
        assertNotEquals(resting.content, selected.content)
        assertNotEquals(resting.border, selected.border)
    }

    @Test
    fun contentStaysReadableOnItsContainer() {
        val light = resolveToggleButtonColors(ElegantLightColors)
        val dark = resolveToggleButtonColors(ElegantDarkColors)

        assertTrue(contrastRatio(light.contentColor, light.containerColor) >= 3f)
        assertTrue(contrastRatio(light.selectedContentColor, light.selectedContainerColor) >= 3f)
        assertTrue(contrastRatio(dark.contentColor, dark.containerColor) >= 3f)
        assertTrue(contrastRatio(dark.selectedContentColor, dark.selectedContainerColor) >= 3f)
    }

    @Test
    fun defaultsMeetMetricsAndAnimationContract() {
        assertTrue(ElegantToggleButtonDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(36.dp, ElegantToggleButtonDefaults.Height)
        assertTrue(ElegantToggleButtonDefaults.HorizontalPadding > 0.dp)
        assertTrue(ElegantToggleButtonDefaults.Height < ElegantToggleButtonDefaults.MinimumTouchHeight)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantToggleButtonDefaults.AnimationDurationMillis,
        )
    }

    @Test
    fun defaultShapeUsesSmallRadiusToken() {
        val shape = ElegantToggleButtonDefaults.shape() as RoundedCornerShape
        assertEquals(RoundedCornerShape(ElegantRadius.sm), shape)
    }

    private fun contrastRatio(foreground: Color, background: Color): Float {
        fun luminance(color: Color): Float {
            fun channel(value: Float): Float {
                val linear = value
                return if (linear <= 0.03928f) {
                    linear / 12.92f
                } else {
                    ((linear + 0.055f) / 1.055f).pow(2.4f)
                }
            }
            return 0.2126f * channel(color.red) +
                0.7152f * channel(color.green) +
                0.0722f * channel(color.blue)
        }

        val lighter = maxOf(luminance(foreground), luminance(background))
        val darker = minOf(luminance(foreground), luminance(background))
        return (lighter + 0.05f) / (darker + 0.05f)
    }
}

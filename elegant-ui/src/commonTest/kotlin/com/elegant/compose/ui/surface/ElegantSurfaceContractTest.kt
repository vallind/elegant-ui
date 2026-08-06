package com.elegant.compose.ui.surface

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantSurfaceContractTest {

    @Test
    fun defaultColorsResolveTheFoundationSurfaceRoles() {
        val light = resolveSurfaceColors(ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceDefault, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.borderDefault, light.borderColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredContainerColor)
        assertEquals(ElegantLightColors.backgroundSubtle, light.pressedContainerColor)
        assertEquals(ElegantLightColors.surfaceSunken, light.disabledContainerColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledContentColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
    }

    @Test
    fun colorsFollowTheActiveTheme() {
        val light = resolveSurfaceColors(ElegantLightColors)
        val dark = resolveSurfaceColors(ElegantDarkColors)

        assertNotEquals(light, dark, "surface colors must follow the active theme")
        assertEquals(ElegantDarkColors.surfaceDefault, dark.containerColor)
        assertEquals(ElegantDarkColors.textPrimary, dark.contentColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.borderColor)
        assertEquals(ElegantDarkColors.focusRing, dark.focusedBorderColor)
    }

    @Test
    fun contentStaysReadableOnItsContainerInBothThemes() {
        val light = resolveSurfaceColors(ElegantLightColors)
        val dark = resolveSurfaceColors(ElegantDarkColors)

        assertTrue(
            contrastRatio(light.contentColor, light.containerColor) >= 3f,
            "light content must stay readable on its container",
        )
        assertTrue(
            contrastRatio(dark.contentColor, dark.containerColor) >= 3f,
            "dark content must stay readable on its container",
        )
    }

    @Test
    fun defaultsExposeTouchTargetAnimationAndSharedShape() {
        assertTrue(ElegantSurfaceDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantSurfaceDefaults.AnimationDurationMillis,
        )
        assertEquals(
            RoundedCornerShape(ElegantRadius.lg),
            ElegantSurfaceDefaults.shape(),
        )
    }

    @Test
    fun visualPrecedenceFollowsDisabledPressedHoveredResting() {
        val colors = resolveSurfaceColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            pressed: Boolean = false,
            hovered: Boolean = false,
            focused: Boolean = false,
        ) = resolveSurfaceVisuals(
            colors = colors,
            borderWidth = 1.dp,
            enabled = enabled,
            pressed = pressed,
            hovered = hovered,
            focused = focused,
            interactive = true,
        )

        assertEquals(
            colors.disabledContainerColor,
            visuals(enabled = false, pressed = true, hovered = true).container,
        )
        assertEquals(colors.disabledContentColor, visuals(enabled = false).content)

        assertEquals(
            colors.pressedContainerColor,
            visuals(pressed = true, hovered = true).container,
        )
        assertEquals(colors.hoveredContainerColor, visuals(hovered = true).container)
        assertEquals(colors.containerColor, visuals().container)
        assertEquals(colors.contentColor, visuals().content)
    }

    @Test
    fun focusTakesOverTheBorderAtTwoDpWhileEnabled() {
        val colors = resolveSurfaceColors(ElegantLightColors)
        val focused = resolveSurfaceVisuals(
            colors = colors,
            borderWidth = 1.dp,
            enabled = true,
            pressed = false,
            hovered = true,
            focused = true,
            interactive = true,
        )

        assertEquals(colors.focusedBorderColor, focused.border)
        assertEquals(2.dp, focused.borderWidth)
        assertEquals(colors.hoveredContainerColor, focused.container)
    }

    @Test
    fun disabledFocusFallsBackToTheCallerBorder() {
        val colors = resolveSurfaceColors(ElegantLightColors)
        val disabledFocused = resolveSurfaceVisuals(
            colors = colors,
            borderWidth = 1.dp,
            enabled = false,
            pressed = false,
            hovered = false,
            focused = true,
            interactive = true,
        )

        assertEquals(colors.borderColor, disabledFocused.border)
        assertEquals(1.dp, disabledFocused.borderWidth)
        assertEquals(colors.disabledContainerColor, disabledFocused.container)
    }

    @Test
    fun callerBorderPassesThroughAtRest() {
        val colors = resolveSurfaceColors(ElegantLightColors)

        fun visuals(borderWidth: Dp) = resolveSurfaceVisuals(
            colors = colors,
            borderWidth = borderWidth,
            enabled = true,
            pressed = false,
            hovered = false,
            focused = false,
            interactive = true,
        )

        val bordered = visuals(borderWidth = 2.dp)
        assertEquals(colors.borderColor, bordered.border)
        assertEquals(2.dp, bordered.borderWidth)

        val borderless = visuals(borderWidth = 0.dp)
        assertEquals(colors.borderColor, borderless.border)
        assertEquals(0.dp, borderless.borderWidth)
    }

    @Test
    fun nonInteractiveSurfacesIgnoreInteractionStates() {
        val colors = resolveSurfaceColors(ElegantLightColors)

        fun visuals(
            pressed: Boolean,
            hovered: Boolean,
            focused: Boolean,
        ) = resolveSurfaceVisuals(
            colors = colors,
            borderWidth = 1.dp,
            enabled = true,
            pressed = pressed,
            hovered = hovered,
            focused = focused,
            interactive = false,
        )

        val resting = visuals(pressed = false, hovered = false, focused = false)
        val interacting = visuals(pressed = true, hovered = true, focused = true)

        assertEquals(resting.container, interacting.container)
        assertEquals(resting.content, interacting.content)
        assertEquals(resting.border, interacting.border)
        assertEquals(resting.borderWidth, interacting.borderWidth)
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

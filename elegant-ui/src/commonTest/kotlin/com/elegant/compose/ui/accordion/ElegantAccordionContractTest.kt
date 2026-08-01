package com.elegant.compose.ui.accordion

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantAccordionContractTest {

    @Test
    fun colorsResolveThemeAwareValuesWithValidContrast() {
        val light = resolveAccordionColors(ElegantLightColors)
        val dark = resolveAccordionColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceRaised, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(Color.Transparent, light.headerContainerColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredHeaderContainerColor)
        assertEquals(ElegantLightColors.backgroundSubtle, light.pressedHeaderContainerColor)
        assertEquals(ElegantLightColors.borderDefault, light.borderColor)
        assertEquals(ElegantLightColors.borderDefault, light.dividerColor)

        assertEquals(ElegantDarkColors.surfaceRaised, dark.containerColor)
        assertEquals(ElegantDarkColors.textPrimary, dark.contentColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.borderColor)

        assertTrue(
            contrastRatio(light.contentColor, light.containerColor) >= 3f,
            "light content must stay readable on the container",
        )
        assertTrue(
            contrastRatio(dark.contentColor, dark.containerColor) >= 3f,
            "dark content must stay readable on the container",
        )
        assertNotEquals(light, dark, "colors must follow the active theme")
    }

    @Test
    fun headerStatePrecedenceFollowsDisabledPressedHoveredResting() {
        val colors = resolveAccordionColors(ElegantLightColors)

        fun header(
            enabled: Boolean = true,
            pressed: Boolean = false,
            hovered: Boolean = false,
        ) = resolveAccordionHeaderContainer(
            colors = colors,
            enabled = enabled,
            pressed = pressed,
            hovered = hovered,
        )

        assertEquals(
            colors.headerContainerColor,
            header(enabled = false, pressed = true, hovered = true),
            "disabled wins over every interaction state",
        )
        assertEquals(
            colors.pressedHeaderContainerColor,
            header(pressed = true, hovered = true),
            "pressed wins over hovered",
        )
        assertEquals(
            colors.hoveredHeaderContainerColor,
            header(hovered = true),
        )
        assertEquals(
            colors.headerContainerColor,
            header(),
            "resting header stays transparent so the surface shows through",
        )
    }

    @Test
    fun interactionTintsStayVisibleInBothThemes() {
        listOf(ElegantLightColors, ElegantDarkColors).forEach { theme ->
            val colors = resolveAccordionColors(theme)

            assertNotEquals(
                colors.headerContainerColor,
                colors.hoveredHeaderContainerColor,
                "hovered and keyboard-focused headers must tint visibly",
            )
            assertNotEquals(
                colors.headerContainerColor,
                colors.pressedHeaderContainerColor,
                "pressed headers must tint visibly",
            )
        }
    }

    @Test
    fun chevronRotationPointsDownWhenCollapsedAndUpWhenExpanded() {
        assertEquals(0f, chevronRotation(expanded = false))
        assertEquals(180f, chevronRotation(expanded = true))
    }

    @Test
    fun defaultsExposeAccessibilityFloorAndSharedAnimationDuration() {
        assertTrue(
            ElegantAccordionDefaults.MinimumTouchHeight >= 48.dp,
            "headers must keep a 48dp minimum interactive root",
        )
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantAccordionDefaults.AnimationDurationMillis,
        )
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

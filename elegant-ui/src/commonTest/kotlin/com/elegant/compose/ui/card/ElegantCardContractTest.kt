package com.elegant.compose.ui.card

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantElevation
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantCardContractTest {

    @Test
    fun allStylesResolveThemeAwareColorsWithValidContrast() {
        ElegantCardStyle.entries.forEach { style ->
            val light = resolveCardColors(style, ElegantLightColors)
            val dark = resolveCardColors(style, ElegantDarkColors)

            assertTrue(
                contrastRatio(light.contentColor, light.containerColor) >= 3f,
                "light $style content must stay readable on its container",
            )
            assertTrue(
                contrastRatio(dark.contentColor, dark.containerColor) >= 3f,
                "dark $style content must stay readable on its container",
            )
            assertNotEquals(light, dark, "style $style must follow the active theme")
        }
    }

    @Test
    fun filledStyleUsesDefaultSurfaceWithoutBorder() {
        val light = resolveCardColors(ElegantCardStyle.Filled, ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceDefault, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(Color.Transparent, light.borderColor)
    }

    @Test
    fun outlinedStyleRaisesTheSurfaceAndShowsBorder() {
        val light = resolveCardColors(ElegantCardStyle.Outlined, ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceRaised, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.borderDefault, light.borderColor)
    }

    @Test
    fun elevatedStyleRaisesTheSurfaceWithoutBorder() {
        val light = resolveCardColors(ElegantCardStyle.Elevated, ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceRaised, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(Color.Transparent, light.borderColor)
    }

    @Test
    fun interactionStatesFollowTheTheme() {
        ElegantCardStyle.entries.forEach { style ->
            val light = resolveCardColors(style, ElegantLightColors)

            assertEquals(ElegantLightColors.surfaceHover, light.hoveredContainerColor)
            assertEquals(ElegantLightColors.backgroundSubtle, light.pressedContainerColor)
            assertEquals(ElegantLightColors.surfaceSunken, light.disabledContainerColor)
            assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
        }
    }

    @Test
    fun elevationMapsOnlyElevatedToTonalShadow() {
        assertEquals(ElegantElevation.none, resolveCardElevation(ElegantCardStyle.Filled))
        assertEquals(ElegantElevation.none, resolveCardElevation(ElegantCardStyle.Outlined))
        assertEquals(ElegantElevation.medium, resolveCardElevation(ElegantCardStyle.Elevated))
    }

    @Test
    fun defaultsExposeSharedShapeAnimationAndAccessibilityFloor() {
        ElegantCardStyle.entries.forEach { style ->
            assertEquals(
                RoundedCornerShape(ElegantRadius.lg),
                ElegantCardDefaults.shape(style),
            )
            assertEquals(resolveCardElevation(style), ElegantCardDefaults.elevation(style))
        }
        assertTrue(ElegantCardDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantCardDefaults.AnimationDurationMillis,
        )
    }

    @Test
    fun visualPrecedenceFollowsDisabledPressedHoveredResting() {
        val colors = resolveCardColors(ElegantCardStyle.Outlined, ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            pressed: Boolean = false,
            hovered: Boolean = false,
            focused: Boolean = false,
            interactive: Boolean = true,
        ) = resolveCardVisuals(
            colors = colors,
            style = ElegantCardStyle.Outlined,
            elevation = ElegantElevation.medium,
            enabled = enabled,
            pressed = pressed,
            hovered = hovered,
            focused = focused,
            interactive = interactive,
        )

        assertEquals(colors.disabledContainerColor, visuals(enabled = false).container)
        assertEquals(0.dp, visuals(enabled = false).elevation)

        assertEquals(
            colors.pressedContainerColor,
            visuals(pressed = true, hovered = true).container,
        )
        assertEquals(0.dp, visuals(pressed = true).elevation)

        assertEquals(colors.hoveredContainerColor, visuals(hovered = true).container)
        assertEquals(ElegantElevation.medium, visuals(hovered = true).elevation)

        assertEquals(colors.containerColor, visuals().container)
        assertEquals(ElegantElevation.medium, visuals().elevation)
    }

    @Test
    fun focusRingTakesOverBorderWithoutChangingContainer() {
        val colors = resolveCardColors(ElegantCardStyle.Filled, ElegantLightColors)
        val focused = resolveCardVisuals(
            colors = colors,
            style = ElegantCardStyle.Filled,
            elevation = ElegantElevation.none,
            enabled = true,
            pressed = false,
            hovered = false,
            focused = true,
            interactive = true,
        )

        assertEquals(colors.focusedBorderColor, focused.border)
        assertEquals(2.dp, focused.borderWidth)
        assertEquals(colors.containerColor, focused.container)
    }

    @Test
    fun nonInteractiveCardsIgnoreInteractionStates() {
        val colors = resolveCardColors(ElegantCardStyle.Outlined, ElegantLightColors)

        fun visuals(
            pressed: Boolean,
            hovered: Boolean,
            focused: Boolean,
        ) = resolveCardVisuals(
            colors = colors,
            style = ElegantCardStyle.Outlined,
            elevation = ElegantElevation.medium,
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
        assertEquals(resting.elevation, interacting.elevation)
    }

    @Test
    fun outlinedCardsDrawOneDpBorderAndFilledCardsStayBorderless() {
        val resting = resolveCardVisuals(
            colors = resolveCardColors(ElegantCardStyle.Filled, ElegantLightColors),
            style = ElegantCardStyle.Filled,
            elevation = ElegantElevation.none,
            enabled = true,
            pressed = false,
            hovered = false,
            focused = false,
            interactive = true,
        )
        val outlined = resolveCardVisuals(
            colors = resolveCardColors(ElegantCardStyle.Outlined, ElegantLightColors),
            style = ElegantCardStyle.Outlined,
            elevation = ElegantElevation.none,
            enabled = true,
            pressed = false,
            hovered = false,
            focused = false,
            interactive = true,
        )

        assertEquals(0.dp, resting.borderWidth)
        assertEquals(1.dp, outlined.borderWidth)
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

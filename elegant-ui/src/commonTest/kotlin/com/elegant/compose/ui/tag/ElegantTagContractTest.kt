package com.elegant.compose.ui.tag

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantRadius
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantTagContractTest {

    @Test
    fun sizeMetricsCoverEveryStableSize() {
        val metrics = ElegantTagSize.entries.associateWith { tagMetricsFor(it) }

        assertEquals(3, metrics.size)
        assertTrue(metrics.getValue(ElegantTagSize.Small).visualHeight <
            metrics.getValue(ElegantTagSize.Medium).visualHeight)
        assertTrue(metrics.getValue(ElegantTagSize.Medium).visualHeight <
            metrics.getValue(ElegantTagSize.Large).visualHeight)
        metrics.values.forEach { metric ->
            assertTrue(metric.horizontalPadding > 0.dp)
            assertTrue(metric.leadingContentSize > 0.dp)
            assertTrue(metric.gap > 0.dp)
        }
    }

    @Test
    fun allStylesResolveThemeAwareColorsWithValidContrast() {
        ElegantTagStyle.entries.forEach { style ->
            val light = resolveTagColors(style, ElegantLightColors)
            val dark = resolveTagColors(style, ElegantDarkColors)

            assertTrue(
                contrastRatio(
                    light.contentColor,
                    blendToSurface(light.containerColor, ElegantLightColors.backgroundCanvas),
                ) >= 3f,
                "light $style content must stay readable on its container",
            )
            assertTrue(
                contrastRatio(
                    dark.contentColor,
                    blendToSurface(dark.containerColor, ElegantDarkColors.backgroundCanvas),
                ) >= 3f,
                "dark $style content must stay readable on its container",
            )
            assertTrue(light.focusedBorderWidth >= 1.dp)
            assertTrue(dark.focusedBorderWidth >= 1.dp)
            assertNotEquals(light, dark, "style $style must follow the active theme")
        }
    }

    @Test
    fun filledStyleUsesAccentContainerAndInverseContent() {
        val light = resolveTagColors(ElegantTagStyle.Filled, ElegantLightColors)

        assertEquals(ElegantLightColors.interactivePrimary, light.containerColor)
        assertEquals(ElegantLightColors.textInverse, light.contentColor)
        assertEquals(ElegantLightColors.interactivePrimaryPressed, light.selectedContainerColor)
        assertNotEquals(light.containerColor, light.disabledContainerColor)
    }

    @Test
    fun outlinedAndPlainStylesStayQuiet() {
        val lightOutlined = resolveTagColors(ElegantTagStyle.Outlined, ElegantLightColors)
        val lightPlain = resolveTagColors(ElegantTagStyle.Plain, ElegantLightColors)

        assertEquals(Color.Transparent, lightOutlined.containerColor)
        assertTrue(lightOutlined.borderWidth > 0.dp)
        assertEquals(Color.Transparent, lightPlain.containerColor)
        assertEquals(0.dp, lightPlain.borderWidth)
        assertEquals(ElegantLightColors.textSecondary, lightPlain.contentColor)
    }

    @Test
    fun selectedColorsCommunicateEmphasis() {
        ElegantTagStyle.entries.forEach { style ->
            val colors = resolveTagColors(style, ElegantLightColors)

            if (style != ElegantTagStyle.Filled) {
                assertTrue(
                    colors.selectedContainerColor != colors.containerColor ||
                        colors.selectedBorderColor != colors.borderColor ||
                        colors.selectedBorderWidth != colors.borderWidth,
                    "style $style must change some selected visual",
                )
            }
        }
    }

    @Test
    fun visualPrecedenceFollowsDisabledPressedSelectedHoveredResting() {
        val colors = resolveTagColors(ElegantTagStyle.Tinted, ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            pressed: Boolean = false,
            hovered: Boolean = false,
            focused: Boolean = false,
            selected: Boolean = false,
            interactive: Boolean = true,
        ) = resolveTagVisuals(
            colors = colors,
            enabled = enabled,
            pressed = pressed,
            hovered = hovered,
            focused = focused,
            selected = selected,
            interactive = interactive,
        )

        assertEquals(colors.disabledContainerColor, visuals(enabled = false).container)
        assertEquals(colors.disabledContentColor, visuals(enabled = false).content)
        assertEquals(colors.disabledBorderColor, visuals(enabled = false).border)

        assertEquals(
            colors.pressedContainerColor,
            visuals(pressed = true, selected = true).container,
        )
        assertEquals(
            colors.selectedContainerColor,
            visuals(hovered = true, selected = true).container,
        )
        assertEquals(colors.hoveredContainerColor, visuals(hovered = true).container)
        assertEquals(colors.containerColor, visuals().container)
    }

    @Test
    fun focusBorderWinsOverSelectedAndHoveredWithoutChangingContainer() {
        val colors = resolveTagColors(ElegantTagStyle.Outlined, ElegantLightColors)
        val focused = resolveTagVisuals(
            colors = colors,
            enabled = true,
            pressed = false,
            hovered = true,
            focused = true,
            selected = true,
            interactive = true,
        )

        assertEquals(colors.focusedBorderColor, focused.border)
        assertEquals(colors.focusedBorderWidth, focused.borderWidth)
        assertEquals(colors.selectedContainerColor, focused.container)
    }

    @Test
    fun nonInteractiveTagsDoNotScaleOrPress() {
        val colors = resolveTagColors(ElegantTagStyle.Tinted, ElegantLightColors)

        val resting = resolveTagVisuals(
            colors = colors,
            enabled = true,
            pressed = false,
            hovered = false,
            focused = false,
            selected = false,
            interactive = false,
        )
        val pressed = resolveTagVisuals(
            colors = colors,
            enabled = true,
            pressed = true,
            hovered = false,
            focused = false,
            selected = false,
            interactive = false,
        )

        assertEquals(1f, resting.scale)
        assertEquals(resting.container, pressed.container)
    }

    @Test
    fun defaultShapeIsFullyRounded() {
        val shape = ElegantTagDefaults.shape() as RoundedCornerShape
        assertEquals(RoundedCornerShape(ElegantRadius.full), shape)
    }

    @Test
    fun interactiveMinTouchHeightMeetsAccessibilityFloor() {
        assertTrue(ElegantTagDefaults.MinimumTouchHeight >= 48.dp)
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

    private fun blendToSurface(overlay: Color, surface: Color): Color {
        val alpha = overlay.alpha
        if (alpha >= 1f) return overlay
        return Color(
            red = overlay.red * alpha + surface.red * (1f - alpha),
            green = overlay.green * alpha + surface.green * (1f - alpha),
            blue = overlay.blue * alpha + surface.blue * (1f - alpha),
        )
    }
}

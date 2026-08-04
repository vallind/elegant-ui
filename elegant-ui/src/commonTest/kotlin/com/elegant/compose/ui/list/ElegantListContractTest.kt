package com.elegant.compose.ui.list

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantListContractTest {

    @Test
    fun colorsResolveThemeAwareDefaults() {
        val light = resolveListItemColors(ElegantLightColors)
        val dark = resolveListItemColors(ElegantDarkColors)

        assertEquals(Color.Transparent, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.textSecondary, light.supportingTextColor)
        assertEquals(ElegantLightColors.textSecondary, light.leadingContentColor)
        assertEquals(ElegantLightColors.textSecondary, light.trailingContentColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredContainerColor)
        assertEquals(ElegantLightColors.backgroundSubtle, light.pressedContainerColor)
        assertEquals(Color.Transparent, light.disabledContainerColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledContentColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledSupportingTextColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)

        assertEquals(Color.Transparent, dark.containerColor)
        assertEquals(ElegantDarkColors.textPrimary, dark.contentColor)
        assertEquals(ElegantDarkColors.textSecondary, dark.supportingTextColor)
        assertEquals(ElegantDarkColors.surfaceHover, dark.hoveredContainerColor)
        assertEquals(ElegantDarkColors.backgroundSubtle, dark.pressedContainerColor)
        assertEquals(ElegantDarkColors.textTertiary, dark.disabledContentColor)
        assertEquals(ElegantDarkColors.textTertiary, dark.disabledSupportingTextColor)
        assertEquals(ElegantDarkColors.focusRing, dark.focusedBorderColor)
        assertNotEquals(light, dark)
    }

    @Test
    fun colorsClassDefaultsChainToRestingSlots() {
        val colors = ElegantListItemColors(
            containerColor = Color.Red,
            contentColor = Color.Blue,
            supportingTextColor = Color.Green,
            leadingContentColor = Color.Yellow,
            trailingContentColor = Color.Cyan,
        )

        assertEquals(Color.Red, colors.hoveredContainerColor)
        assertEquals(Color.Red, colors.pressedContainerColor)
        assertEquals(Color.Red, colors.disabledContainerColor)
        assertEquals(Color.Blue, colors.disabledContentColor)
        assertEquals(Color.Green, colors.disabledSupportingTextColor)
        assertEquals(Color.Red, colors.focusedBorderColor)
    }

    @Test
    fun selectedContainerResolvesToAccentTintOtherwiseContainerColor() {
        val colors = resolveListItemColors(ElegantLightColors)
        val selectedContainer = ElegantLightColors.interactivePrimary.copy(alpha = 0.10f)

        assertEquals(
            selectedContainer,
            resolveListItemContainer(
                selected = true,
                colors = colors,
                themeColors = ElegantLightColors,
            ),
        )
        assertEquals(
            colors.containerColor,
            resolveListItemContainer(
                selected = false,
                colors = colors,
                themeColors = ElegantLightColors,
            ),
        )
    }

    @Test
    fun selectedContainerHonorsCustomRestingContainerWhenNotSelected() {
        val colors = resolveListItemColors(ElegantLightColors)
            .copy(containerColor = Color.Magenta)

        assertEquals(
            Color.Magenta,
            resolveListItemContainer(
                selected = false,
                colors = colors,
                themeColors = ElegantLightColors,
            ),
        )
        assertEquals(
            ElegantLightColors.interactivePrimary.copy(alpha = 0.10f),
            resolveListItemContainer(
                selected = true,
                colors = colors,
                themeColors = ElegantLightColors,
            ),
        )
    }

    @Test
    fun visualPrecedenceFollowsDisabledPressedSelectedHoveredResting() {
        val colors = resolveListItemColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            pressed: Boolean = false,
            hovered: Boolean = false,
            focused: Boolean = false,
            selected: Boolean = false,
            interactive: Boolean = true,
        ) = resolveListItemVisuals(
            colors = colors,
            themeColors = ElegantLightColors,
            selected = selected,
            enabled = enabled,
            pressed = pressed,
            hovered = hovered,
            focused = focused,
            interactive = interactive,
        )

        assertEquals(
            colors.disabledContainerColor,
            visuals(enabled = false, pressed = true, hovered = true, selected = true).container,
        )
        assertEquals(
            colors.disabledContentColor,
            visuals(enabled = false).content,
        )
        assertEquals(
            colors.disabledSupportingTextColor,
            visuals(enabled = false).supportingText,
        )

        assertEquals(
            colors.pressedContainerColor,
            visuals(pressed = true, hovered = true, selected = true).container,
        )
        assertEquals(
            ElegantLightColors.interactivePrimary.copy(alpha = 0.10f),
            visuals(hovered = true, selected = true).container,
        )
        assertEquals(
            colors.hoveredContainerColor,
            visuals(hovered = true).container,
        )
        assertEquals(colors.containerColor, visuals().container)

        assertEquals(colors.contentColor, visuals().content)
        assertEquals(colors.supportingTextColor, visuals().supportingText)
    }

    @Test
    fun interactionStatesApplyOnlyToInteractiveItems() {
        val colors = resolveListItemColors(ElegantLightColors)

        fun visuals(
            pressed: Boolean,
            hovered: Boolean,
            focused: Boolean,
            selected: Boolean,
        ) = resolveListItemVisuals(
            colors = colors,
            themeColors = ElegantLightColors,
            selected = selected,
            enabled = true,
            pressed = pressed,
            hovered = hovered,
            focused = focused,
            interactive = false,
        )

        assertEquals(
            colors.containerColor,
            visuals(pressed = true, hovered = true, focused = true, selected = false).container,
        )
        assertEquals(
            Color.Transparent,
            visuals(pressed = true, hovered = true, focused = true, selected = false).border,
        )
        assertEquals(
            0.dp,
            visuals(pressed = true, hovered = true, focused = true, selected = false).borderWidth,
        )
        assertEquals(
            ElegantLightColors.interactivePrimary.copy(alpha = 0.10f),
            visuals(pressed = true, hovered = true, focused = true, selected = true).container,
        )
    }

    @Test
    fun focusRingAppearsOnlyForFocusedInteractiveItems() {
        val colors = resolveListItemColors(ElegantLightColors)

        val focused = resolveListItemVisuals(
            colors = colors,
            themeColors = ElegantLightColors,
            selected = false,
            enabled = true,
            pressed = false,
            hovered = false,
            focused = true,
            interactive = true,
        )

        assertEquals(colors.focusedBorderColor, focused.border)
        assertEquals(ListItemMetrics.FocusBorderWidth, focused.borderWidth)
        assertEquals(colors.containerColor, focused.container)
    }

    @Test
    fun defaultsExposeAccessibilityFloorAndStandardMotion() {
        assertTrue(ElegantListItemDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantListItemDefaults.AnimationDurationMillis,
        )
    }

    @Test
    fun metricsExposeSlotSizesGapAndFocusRingWidth() {
        assertEquals(20.dp, ListItemMetrics.LeadingSlotSize)
        assertEquals(20.dp, ListItemMetrics.TrailingSlotSize)
        assertEquals(16.dp, ListItemMetrics.SlotGap)
        assertEquals(2.dp, ListItemMetrics.FocusBorderWidth)
    }
}

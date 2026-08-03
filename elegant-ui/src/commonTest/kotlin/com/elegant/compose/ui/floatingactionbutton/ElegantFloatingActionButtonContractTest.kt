package com.elegant.compose.ui.floatingactionbutton

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ElegantFloatingActionButtonContractTest {
    @Test
    fun themeRolesResolveIntoFabColors() {
        val resolved = resolveFloatingActionButtonColors(testThemeColors)

        assertEquals(testThemeColors.interactivePrimary, resolved.containerColor)
        assertEquals(testThemeColors.textInverse, resolved.contentColor)
        assertEquals(testThemeColors.interactivePrimaryHover, resolved.hoveredContainerColor)
        assertEquals(testThemeColors.textInverse, resolved.hoveredContentColor)
        assertEquals(testThemeColors.interactivePrimaryPressed, resolved.pressedContainerColor)
        assertEquals(testThemeColors.textInverse, resolved.pressedContentColor)
        assertEquals(testThemeColors.surfaceSunken, resolved.disabledContainerColor)
        assertEquals(testThemeColors.textTertiary, resolved.disabledContentColor)
        assertEquals(testThemeColors.focusRing, resolved.focusedBorderColor)
    }

    @Test
    fun restingStateResolvesDefaultColors() {
        val visuals = resolveFloatingActionButtonVisuals(
            colors = testColors,
            enabled = true,
            pressed = false,
            hovered = false,
            focused = false,
        )

        assertEquals(testColors.containerColor, visuals.container)
        assertEquals(testColors.contentColor, visuals.content)
        assertEquals(Color.Transparent, visuals.border)
        assertEquals(0.dp, visuals.borderWidth)
        assertEquals(1f, visuals.scale)
    }

    @Test
    fun hoveredStateResolvesHoverColorsWithoutScale() {
        val visuals = resolveFloatingActionButtonVisuals(
            colors = testColors,
            enabled = true,
            pressed = false,
            hovered = true,
            focused = false,
        )

        assertEquals(testColors.hoveredContainerColor, visuals.container)
        assertEquals(testColors.hoveredContentColor, visuals.content)
        assertEquals(Color.Transparent, visuals.border)
        assertEquals(0.dp, visuals.borderWidth)
        assertEquals(1f, visuals.scale)
    }

    @Test
    fun focusedStateDrawsFocusBorderWhenNotPressed() {
        val visuals = resolveFloatingActionButtonVisuals(
            colors = testColors,
            enabled = true,
            pressed = false,
            hovered = false,
            focused = true,
        )

        assertEquals(testColors.containerColor, visuals.container)
        assertEquals(testColors.contentColor, visuals.content)
        assertEquals(testColors.focusedBorderColor, visuals.border)
        assertEquals(FocusedBorderWidth, visuals.borderWidth)
        assertEquals(1f, visuals.scale)
    }

    @Test
    fun pressedStateOverridesHoverAndFocusBorder() {
        val visuals = resolveFloatingActionButtonVisuals(
            colors = testColors,
            enabled = true,
            pressed = true,
            hovered = true,
            focused = true,
        )

        assertEquals(testColors.pressedContainerColor, visuals.container)
        assertEquals(testColors.pressedContentColor, visuals.content)
        assertEquals(Color.Transparent, visuals.border)
        assertEquals(0.dp, visuals.borderWidth)
        assertEquals(ElegantFloatingActionButtonDefaults.PressedScale, visuals.scale)
    }

    @Test
    fun disabledStateOverridesAllInteractions() {
        val visuals = resolveFloatingActionButtonVisuals(
            colors = testColors,
            enabled = false,
            pressed = true,
            hovered = true,
            focused = true,
        )

        assertEquals(testColors.disabledContainerColor, visuals.container)
        assertEquals(testColors.disabledContentColor, visuals.content)
        assertEquals(Color.Transparent, visuals.border)
        assertEquals(0.dp, visuals.borderWidth)
        assertEquals(1f, visuals.scale)
    }

    @Test
    fun sizeSelectionHonorsCompactFlag() {
        assertEquals(ElegantFloatingActionButtonDefaults.Size, fabSize(compact = false))
        assertEquals(ElegantFloatingActionButtonDefaults.CompactSize, fabSize(compact = true))
        assertEquals(24.dp, fabContentSize(compact = false))
        assertEquals(20.dp, fabContentSize(compact = true))
    }

    @Test
    fun defaultsRemainStable() {
        assertEquals(56.dp, ElegantFloatingActionButtonDefaults.Size)
        assertEquals(40.dp, ElegantFloatingActionButtonDefaults.CompactSize)
        assertEquals(ElegantMotion.standardDurationMillis, ElegantFloatingActionButtonDefaults.AnimationDurationMillis)
        assertEquals(0.96f, ElegantFloatingActionButtonDefaults.PressedScale)
    }

    @Test
    fun colorsClassDefaultsFollowContainerAndContent() {
        val colors = ElegantFloatingActionButtonColors(
            containerColor = Color(0xFF000001),
            contentColor = Color(0xFF000002),
        )

        assertEquals(colors.containerColor, colors.hoveredContainerColor)
        assertEquals(colors.containerColor, colors.pressedContainerColor)
        assertEquals(colors.containerColor, colors.disabledContainerColor)
        assertEquals(colors.containerColor, colors.focusedBorderColor)
        assertEquals(colors.contentColor, colors.hoveredContentColor)
        assertEquals(colors.contentColor, colors.pressedContentColor)
        assertEquals(colors.contentColor, colors.disabledContentColor)
    }

    private companion object {
        val testThemeColors = ElegantColors(
            backgroundCanvas = Color(0xFF000001),
            backgroundSubtle = Color(0xFF000002),
            surfaceDefault = Color(0xFF000003),
            surfaceRaised = Color(0xFF000004),
            surfaceSunken = Color(0xFF000005),
            textPrimary = Color(0xFF000006),
            textSecondary = Color(0xFF000007),
            textTertiary = Color(0xFF000008),
            textInverse = Color(0xFF000009),
            borderDefault = Color(0xFF00000A),
            borderStrong = Color(0xFF00000B),
            interactivePrimary = Color(0xFF00000C),
            interactivePrimaryPressed = Color(0xFF00000D),
            focusRing = Color(0xFF00000E),
            interactivePrimaryHover = Color(0xFF00000F),
        )

        val testColors = ElegantFloatingActionButtonColors(
            containerColor = Color(0xFF000001),
            contentColor = Color(0xFF000002),
            hoveredContainerColor = Color(0xFF000003),
            hoveredContentColor = Color(0xFF000004),
            pressedContainerColor = Color(0xFF000005),
            pressedContentColor = Color(0xFF000006),
            disabledContainerColor = Color(0xFF000007),
            disabledContentColor = Color(0xFF000008),
            focusedBorderColor = Color(0xFF000009),
        )
    }
}

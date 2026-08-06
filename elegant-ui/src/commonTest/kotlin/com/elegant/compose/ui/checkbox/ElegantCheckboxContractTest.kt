package com.elegant.compose.ui.checkbox

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantCheckboxContractTest {

    @Test
    fun colorsResolveThemeAwareDefaults() {
        val light = resolveCheckboxColors(ElegantLightColors)
        val dark = resolveCheckboxColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.interactivePrimary, light.checkedContainerColor)
        assertEquals(ElegantLightColors.textInverse, light.checkedContentColor)
        assertEquals(Color.Transparent, light.uncheckedContainerColor)
        assertEquals(ElegantLightColors.borderStrong, light.borderColor)
        assertEquals(ElegantLightColors.borderStrong, light.hoveredBorderColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
        assertEquals(ElegantLightColors.borderDefault, light.disabledBorderColor)
        assertNotEquals(light, dark, "checkbox colors must follow the active theme")
    }

    @Test
    fun disabledCheckedContainerFadesThePrimaryAccent() {
        val light = resolveCheckboxColors(ElegantLightColors)

        assertEquals(
            ElegantLightColors.interactivePrimary.copy(alpha = 0.35f),
            light.disabledCheckedContainerColor,
        )
        assertEquals(Color.Transparent, light.disabledUncheckedContainerColor)
    }

    @Test
    fun hoveredAndPressedContainersStayDistinctFromResting() {
        val light = resolveCheckboxColors(ElegantLightColors)

        assertEquals(ElegantLightColors.interactivePrimaryHover, light.hoveredCheckedContainerColor)
        assertEquals(ElegantLightColors.interactivePrimaryPressed, light.pressedCheckedContainerColor)
        assertEquals(ElegantLightColors.backgroundSubtle, light.hoveredUncheckedContainerColor)
        assertEquals(ElegantLightColors.surfaceSunken, light.pressedUncheckedContainerColor)
        assertNotEquals(light.checkedContainerColor, light.hoveredCheckedContainerColor)
        assertNotEquals(light.uncheckedContainerColor, light.hoveredUncheckedContainerColor)
    }

    @Test
    fun stateColorsDefaultToTheirBaseVisuals() {
        val container = Color(0xFFFF0000)
        val unchecked = Color(0xFF00FF00)
        val content = Color(0xFF0000FF)
        val border = Color(0xFFFFFFFF)
        val colors = ElegantCheckboxColors(
            checkedContainerColor = container,
            uncheckedContainerColor = unchecked,
            checkedContentColor = content,
            borderColor = border,
        )

        assertEquals(container, colors.hoveredCheckedContainerColor)
        assertEquals(unchecked, colors.hoveredUncheckedContainerColor)
        assertEquals(container, colors.pressedCheckedContainerColor)
        assertEquals(unchecked, colors.pressedUncheckedContainerColor)
        assertEquals(container, colors.disabledCheckedContainerColor)
        assertEquals(unchecked, colors.disabledUncheckedContainerColor)
        assertEquals(content, colors.disabledCheckedContentColor)
        assertEquals(border, colors.hoveredBorderColor)
        assertEquals(border, colors.focusedBorderColor)
        assertEquals(border, colors.disabledBorderColor)
    }

    @Test
    fun visualPrecedenceFollowsDisabledPressedHoveredResting() {
        val colors = resolveCheckboxColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            pressed: Boolean = false,
            hovered: Boolean = false,
            focused: Boolean = false,
            checked: Boolean = false,
        ) = resolveCheckboxVisuals(
            colors = colors,
            enabled = enabled,
            pressed = pressed,
            hovered = hovered,
            focused = focused,
            checked = checked,
        )

        assertEquals(colors.disabledUncheckedContainerColor, visuals(enabled = false).container)
        assertEquals(colors.disabledBorderColor, visuals(enabled = false, focused = true).border)
        assertEquals(
            colors.disabledCheckedContainerColor,
            visuals(enabled = false, pressed = true, hovered = true, checked = true).container,
        )
        assertEquals(
            colors.disabledCheckedContentColor,
            visuals(enabled = false, checked = true).check,
        )
        assertEquals(
            colors.pressedUncheckedContainerColor,
            visuals(pressed = true, hovered = true).container,
        )
        assertEquals(colors.hoveredUncheckedContainerColor, visuals(hovered = true).container)
        assertEquals(colors.uncheckedContainerColor, visuals().container)
    }

    @Test
    fun checkedIsSemanticAndCombinesWithInteractionStates() {
        val colors = resolveCheckboxColors(ElegantLightColors)

        assertEquals(
            colors.pressedCheckedContainerColor,
            resolveCheckboxVisuals(
                colors = colors,
                enabled = true,
                pressed = true,
                hovered = true,
                focused = false,
                checked = true,
            ).container,
        )
        assertEquals(
            colors.hoveredCheckedContainerColor,
            resolveCheckboxVisuals(
                colors = colors,
                enabled = true,
                pressed = false,
                hovered = true,
                focused = false,
                checked = true,
            ).container,
        )
        assertEquals(
            colors.checkedContainerColor,
            resolveCheckboxVisuals(
                colors = colors,
                enabled = true,
                pressed = false,
                hovered = false,
                focused = false,
                checked = true,
            ).container,
        )
    }

    @Test
    fun focusRingWinsOverHoverBorderWithoutChangingTheContainer() {
        val colors = resolveCheckboxColors(ElegantLightColors)
        val focused = resolveCheckboxVisuals(
            colors = colors,
            enabled = true,
            pressed = false,
            hovered = true,
            focused = true,
            checked = true,
        )

        assertEquals(colors.focusedBorderColor, focused.border)
        assertEquals(colors.hoveredCheckedContainerColor, focused.container)
    }

    @Test
    fun containerFollowsCheckWhenCheckedAndBorderWhenUnchecked() {
        val colors = resolveCheckboxColors(ElegantLightColors)

        assertEquals(
            colors.checkedContainerColor,
            resolveCheckboxVisuals(
                colors = colors,
                enabled = true,
                pressed = false,
                hovered = false,
                focused = false,
                checked = true,
            ).container,
        )
        assertEquals(
            colors.borderColor,
            resolveCheckboxVisuals(
                colors = colors,
                enabled = true,
                pressed = false,
                hovered = false,
                focused = false,
                checked = false,
            ).border,
        )
    }

    @Test
    fun checkmarkPathScalesWithBoxSize() {
        val small = checkmarkPath(50f)
        val large = checkmarkPath(100f)

        small.zip(large).forEach { (from, to) ->
            assertEquals(from.x * 2f, to.x, absoluteTolerance = 0.001f)
            assertEquals(from.y * 2f, to.y, absoluteTolerance = 0.001f)
        }
    }

    @Test
    fun checkmarkPathStaysInsideTheBox() {
        val size = 80f

        checkmarkPath(size).forEach { point ->
            assertTrue(point.x in 0f..size)
            assertTrue(point.y in 0f..size)
        }
    }

    @Test
    fun checkmarkPathKeepsSegmentOrdering() {
        val path = checkmarkPath(100f)

        assertEquals(3, path.size)
        assertTrue(path[0].x < path[1].x)
        assertTrue(path[1].x < path[2].x)
        assertTrue(path[0].y < path[1].y)
        assertTrue(path[2].y < path[1].y)
    }

    @Test
    fun checkmarkPathUsesDesignProportions() {
        val size = 100f
        val path = checkmarkPath(size)

        assertEquals(24f, path[0].x, absoluteTolerance = 0.001f)
        assertEquals(52f, path[0].y, absoluteTolerance = 0.001f)
        assertEquals(42f, path[1].x, absoluteTolerance = 0.001f)
        assertEquals(70f, path[1].y, absoluteTolerance = 0.001f)
        assertEquals(78f, path[2].x, absoluteTolerance = 0.001f)
        assertEquals(32f, path[2].y, absoluteTolerance = 0.001f)
    }

    @Test
    fun checkmarkPathRejectsNonPositiveSize() {
        assertFailsWith<IllegalArgumentException> { checkmarkPath(0f) }
        assertFailsWith<IllegalArgumentException> { checkmarkPath(-1f) }
    }

    @Test
    fun defaultsMeetAccessibilityAndRhythmContracts() {
        assertEquals(20.dp, ElegantCheckboxDefaults.BoxSize)
        assertTrue(ElegantCheckboxDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(ElegantMotion.standardDurationMillis, ElegantCheckboxDefaults.AnimationDurationMillis)
    }
}

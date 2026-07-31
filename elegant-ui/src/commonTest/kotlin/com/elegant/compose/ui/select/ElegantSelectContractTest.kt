package com.elegant.compose.ui.select

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantSelectContractTest {

    @Test
    fun optionTextResolutionIsNullSafe() {
        assertNull(selectOptionText(null))
        assertEquals("Starter", selectOptionText(ElegantSelectOption("Starter", "starter")))
        assertEquals("", selectOptionText(ElegantSelectOption("", "blank")))
    }

    @Test
    fun optionValidationKeepsOnlyOptionsWithTextAndValue() {
        val options = listOf(
            ElegantSelectOption("Starter", "starter"),
            ElegantSelectOption("", "empty-text"),
            ElegantSelectOption("   ", "blank-text"),
            ElegantSelectOption("No value", ""),
            ElegantSelectOption("", ""),
        )

        assertEquals(
            listOf(ElegantSelectOption("Starter", "starter")),
            validateOptions(options),
        )
    }

    @Test
    fun optionValidationPreservesOrderAndFlags() {
        val options = listOf(
            ElegantSelectOption("Pro", "pro", enabled = false),
            ElegantSelectOption("Enterprise", "enterprise"),
        )

        assertEquals(options, validateOptions(options))
    }

    @Test
    fun selectableOptionSearchWrapsAroundForward() {
        val options = listOf(
            ElegantSelectOption("A", "a", enabled = false),
            ElegantSelectOption("B", "b"),
            ElegantSelectOption("C", "c", enabled = false),
            ElegantSelectOption("D", "d"),
        )

        assertEquals(1, findSelectableOption(options, 0, 1))
        assertEquals(1, findSelectableOption(options, 3, 1))
        assertEquals(3, findSelectableOption(options, 2, 1))
        assertEquals(1, findSelectableOption(options, -1, 1))
    }

    @Test
    fun selectableOptionSearchWrapsAroundBackward() {
        val options = listOf(
            ElegantSelectOption("A", "a", enabled = false),
            ElegantSelectOption("B", "b"),
            ElegantSelectOption("C", "c", enabled = false),
            ElegantSelectOption("D", "d"),
        )

        assertEquals(1, findSelectableOption(options, 3, -1))
        assertEquals(3, findSelectableOption(options, 1, -1))
        assertEquals(3, findSelectableOption(options, -1, -1))
    }

    @Test
    fun selectableOptionSearchFallsBackToTheStartIndexWhenItIsTheOnlyEnabledOption() {
        val options = listOf(
            ElegantSelectOption("Only", "only", enabled = true),
        )

        assertEquals(0, findSelectableOption(options, 0, 1))
        assertEquals(0, findSelectableOption(options, 0, -1))
    }

    @Test
    fun selectableOptionSearchReportsFailureForEmptyOrFullyDisabledLists() {
        val disabled = listOf(
            ElegantSelectOption("A", "a", enabled = false),
            ElegantSelectOption("B", "b", enabled = false),
        )

        assertEquals(-1, findSelectableOption(emptyList(), 0, 1))
        assertEquals(-1, findSelectableOption(disabled, 0, 1))
        assertEquals(-1, findSelectableOption(disabled, 1, -1))
    }

    @Test
    fun selectableOptionSearchIgnoresZeroAndCoercesDirectionSign() {
        val options = listOf(
            ElegantSelectOption("A", "a"),
            ElegantSelectOption("B", "b"),
        )

        assertEquals(-1, findSelectableOption(options, 0, 0))
        assertEquals(1, findSelectableOption(options, 0, 5))
        assertEquals(0, findSelectableOption(options, 1, -5))
    }

    @Test
    fun visualsFollowDisabledErrorFocusedHoveredRestingPrecedence() {
        fun visuals(
            enabled: Boolean = true,
            hovered: Boolean = false,
            focused: Boolean = false,
            isError: Boolean = false,
        ) = resolveSelectVisuals(
            enabled = enabled,
            hovered = hovered,
            focused = focused,
            isError = isError,
            themeColors = ElegantLightColors,
        )

        assertEquals(ElegantLightColors.surfaceSunken, visuals().container)
        assertEquals(ElegantLightColors.surfaceHover, visuals(hovered = true).container)
        assertEquals(ElegantLightColors.surfaceRaised, visuals(focused = true).container)
        assertEquals(ElegantLightColors.surfaceSunken, visuals(enabled = false).container)
        assertEquals(ElegantLightColors.borderDefault, visuals().border)
        assertEquals(1.dp, visuals().borderWidth)
        assertEquals(ElegantLightColors.focusRing, visuals(focused = true, hovered = true).border)
        assertEquals(2.dp, visuals(focused = true).borderWidth)
        assertEquals(ElegantLightColors.statusCritical, visuals(isError = true, focused = true).border)
        assertEquals(2.dp, visuals(isError = true).borderWidth)
        assertEquals(ElegantLightColors.borderDefault, visuals(enabled = false, isError = true).border)
        assertEquals(1.dp, visuals(enabled = false).borderWidth)
        assertEquals(ElegantLightColors.textPrimary, visuals().content)
        assertEquals(ElegantLightColors.textTertiary, visuals(enabled = false).content)
    }

    @Test
    fun visualsFollowTheActiveTheme() {
        val light = resolveSelectVisuals(
            enabled = true,
            hovered = false,
            focused = false,
            isError = false,
            themeColors = ElegantLightColors,
        )
        val dark = resolveSelectVisuals(
            enabled = true,
            hovered = false,
            focused = false,
            isError = false,
            themeColors = ElegantDarkColors,
        )

        assertNotEquals(light.container, dark.container)
        assertNotEquals(light, dark)
    }

    @Test
    fun defaultsExposeTouchTargetMenuCapAndMotion() {
        assertTrue(ElegantSelectDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(320.dp, ElegantSelectDefaults.MenuMaxHeight)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantSelectDefaults.AnimationDurationMillis,
        )
    }

    @Test
    fun restingBorderIsDefaultNotTransparent() {
        val visuals = resolveSelectVisuals(
            enabled = true,
            hovered = false,
            focused = false,
            isError = false,
            themeColors = ElegantLightColors,
        )

        assertEquals(ElegantLightColors.borderDefault, visuals.border)
        assertNotEquals(Color.Transparent, visuals.border)
    }
}

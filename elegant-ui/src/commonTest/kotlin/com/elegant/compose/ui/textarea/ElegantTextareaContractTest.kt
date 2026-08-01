package com.elegant.compose.ui.textarea

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantTextareaContractTest {

    @Test
    fun colorsResolveFilledLikeRecessedContainerWithTransparentRestingBorder() {
        val light = resolveTextareaColors(ElegantLightColors)
        val dark = resolveTextareaColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceSunken, light.containerColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredContainerColor)
        assertEquals(ElegantLightColors.surfaceRaised, light.focusedContainerColor)
        assertEquals(ElegantLightColors.surfaceSunken, light.disabledContainerColor)
        assertEquals(Color.Transparent, light.borderColor)
        assertEquals(Color.Transparent, light.hoveredBorderColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
        assertEquals(ElegantLightColors.statusCritical, light.errorBorderColor)
        assertEquals(Color.Transparent, light.disabledBorderColor)
        assertNotEquals(light, dark, "colors must follow the active theme")
    }

    @Test
    fun colorsUseSemanticRolesForTextAndStatus() {
        val colors = resolveTextareaColors(ElegantLightColors)

        assertEquals(ElegantLightColors.textPrimary, colors.contentColor)
        assertEquals(ElegantLightColors.textTertiary, colors.disabledContentColor)
        assertEquals(ElegantLightColors.textTertiary, colors.placeholderColor)
        assertEquals(ElegantLightColors.textSecondary, colors.labelColor)
        assertEquals(ElegantLightColors.textSecondary, colors.supportingTextColor)
        assertEquals(ElegantLightColors.statusCritical, colors.errorTextColor)
    }

    @Test
    fun visualsFollowDisabledErrorFocusedHoveredRestingPrecedence() {
        val colors = resolveTextareaColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            hovered: Boolean = false,
            focused: Boolean = false,
            isError: Boolean = false,
        ) = resolveVisuals(
            colors = colors,
            enabled = enabled,
            hovered = hovered,
            focused = focused,
            isError = isError,
        )

        assertEquals(colors.disabledContainerColor, visuals(enabled = false).container)
        assertEquals(colors.disabledBorderColor, visuals(enabled = false, isError = true).border)
        assertEquals(1.dp, visuals(enabled = false, isError = true).borderWidth)
        assertEquals(colors.errorBorderColor, visuals(isError = true, focused = true).border)
        assertEquals(2.dp, visuals(isError = true, focused = true).borderWidth)
        assertEquals(
            colors.focusedContainerColor,
            visuals(focused = true, hovered = true).container,
        )
        assertEquals(colors.focusedBorderColor, visuals(focused = true, hovered = true).border)
        assertEquals(2.dp, visuals(focused = true).borderWidth)
        assertEquals(colors.hoveredBorderColor, visuals(hovered = true).border)
        assertEquals(1.dp, visuals().borderWidth)
        assertEquals(colors.containerColor, visuals().container)
    }

    @Test
    fun maxLengthResolutionCoercesNonPositiveValuesToUnlimited() {
        assertEquals(ElegantTextareaDefaults.MaxLengthUnlimited, resolveMaxLength(-1))
        assertEquals(ElegantTextareaDefaults.MaxLengthUnlimited, resolveMaxLength(0))
        assertEquals(12, resolveMaxLength(12))
        assertEquals(Int.MAX_VALUE, resolveMaxLength(Int.MAX_VALUE))
    }

    @Test
    fun inputIsTruncatedAtResolvedMaxLength() {
        assertEquals("hello", clampLength("hello", 5))
        assertEquals("hello", clampLength("hello world", 5))
        assertEquals("", clampLength("hello world", 0))
        assertEquals(
            "hello world",
            clampLength("hello world", ElegantTextareaDefaults.MaxLengthUnlimited),
        )
    }

    @Test
    fun linesResolutionCoercesMinimumAndMaximum() {
        assertEquals(3 to 8, resolveLines(3, 8))
        assertEquals(1 to 8, resolveLines(0, 8))
        assertEquals(3 to 3, resolveLines(3, 2))
        assertEquals(1 to 1, resolveLines(0, 0))
        assertEquals(1 to 1, resolveLines(-2, -1))
        assertEquals(1 to 1, resolveLines(1, 1))
    }

    @Test
    fun descriptionsHideBlankSupportingAndErrorText() {
        assertNull(resolveDescription(supportingText = null, errorText = null, isError = false))
        assertNull(resolveDescription(supportingText = "  ", errorText = "  ", isError = false))
        assertEquals(
            "Keep it short.",
            resolveDescription(supportingText = "Keep it short.", errorText = null, isError = false),
        )
        assertEquals(
            "Something broke.",
            resolveDescription(
                supportingText = "Keep it short.",
                errorText = "Something broke.",
                isError = true,
            ),
        )
        assertEquals(
            "Keep it short.",
            resolveDescription(supportingText = "Keep it short.", errorText = "  ", isError = true),
        )
    }

    @Test
    fun blankLabelsResolveToNull() {
        assertNull(resolveLabel(null))
        assertNull(resolveLabel(""))
        assertNull(resolveLabel("   "))
        assertEquals("Notes", resolveLabel("Notes"))
    }

    @Test
    fun defaultsExposeUnlimitedLengthAccessibleHeightAndLineRange() {
        assertTrue(ElegantTextareaDefaults.MaxLengthUnlimited == Int.MAX_VALUE)
        assertTrue(ElegantTextareaDefaults.MinimumTouchHeight >= 48.dp)
        assertTrue(ElegantTextareaDefaults.MinLines >= 1)
        assertTrue(ElegantTextareaDefaults.MaxLines >= ElegantTextareaDefaults.MinLines)
        assertEquals(3, ElegantTextareaDefaults.MinLines)
        assertEquals(8, ElegantTextareaDefaults.MaxLines)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantTextareaDefaults.AnimationDurationMillis,
        )
        assertEquals(RoundedCornerShape(ElegantRadius.md), ElegantTextareaDefaults.shape())
    }
}

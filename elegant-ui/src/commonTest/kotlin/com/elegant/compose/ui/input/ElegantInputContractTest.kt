package com.elegant.compose.ui.input

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantInputContractTest {

    @Test
    fun filledStyleResolvesRecessedContainerWithTransparentRestingBorder() {
        val light = resolveInputColors(ElegantInputStyle.Filled, ElegantLightColors)
        val dark = resolveInputColors(ElegantInputStyle.Filled, ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceSunken, light.containerColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredContainerColor)
        assertEquals(ElegantLightColors.surfaceRaised, light.focusedContainerColor)
        assertEquals(ElegantLightColors.surfaceSunken, light.disabledContainerColor)
        assertEquals(Color.Transparent, light.borderColor)
        assertEquals(Color.Transparent, light.hoveredBorderColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
        assertEquals(ElegantLightColors.statusCritical, light.errorBorderColor)
        assertNotEquals(light, dark, "filled style must follow the active theme")
    }

    @Test
    fun outlinedStyleResolvesTransparentContainerWithVisibleBorder() {
        val light = resolveInputColors(ElegantInputStyle.Outlined, ElegantLightColors)

        assertEquals(Color.Transparent, light.containerColor)
        assertEquals(Color.Transparent, light.focusedContainerColor)
        assertEquals(ElegantLightColors.borderDefault, light.borderColor)
        assertEquals(ElegantLightColors.borderStrong, light.hoveredBorderColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
        assertEquals(ElegantLightColors.statusCritical, light.errorBorderColor)
        assertEquals(ElegantLightColors.borderDefault, light.disabledBorderColor)
    }

    @Test
    fun bothStylesShareSemanticContentRoles() {
        ElegantInputStyle.entries.forEach { style ->
            val colors = resolveInputColors(style, ElegantLightColors)

            assertEquals(ElegantLightColors.textPrimary, colors.contentColor)
            assertEquals(ElegantLightColors.textTertiary, colors.disabledContentColor)
            assertEquals(ElegantLightColors.textTertiary, colors.placeholderColor)
            assertEquals(ElegantLightColors.textSecondary, colors.labelColor)
            assertEquals(ElegantLightColors.textSecondary, colors.supportingTextColor)
            assertEquals(ElegantLightColors.statusCritical, colors.errorTextColor)
        }
    }

    @Test
    fun filledAndOutlinedResolveDistinctContainers() {
        val filled = resolveInputColors(ElegantInputStyle.Filled, ElegantLightColors)
        val outlined = resolveInputColors(ElegantInputStyle.Outlined, ElegantLightColors)

        assertNotEquals(filled.containerColor, outlined.containerColor)
        assertNotEquals(filled.borderColor, outlined.borderColor)
        assertNotEquals(filled, outlined)
    }

    @Test
    fun visualsFollowDisabledErrorFocusedHoveredRestingPrecedence() {
        val colors = resolveInputColors(ElegantInputStyle.Filled, ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            hovered: Boolean = false,
            focused: Boolean = false,
            isError: Boolean = false,
        ) = resolveInputVisuals(
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
        assertEquals(
            ElegantInputDefaults.MaxLengthUnlimited,
            resolveInputMaxLength(-1),
        )
        assertEquals(
            ElegantInputDefaults.MaxLengthUnlimited,
            resolveInputMaxLength(0),
        )
        assertEquals(12, resolveInputMaxLength(12))
        assertEquals(Int.MAX_VALUE, resolveInputMaxLength(Int.MAX_VALUE))
    }

    @Test
    fun inputIsTruncatedAtResolvedMaxLength() {
        assertEquals("hello", clampInputLength("hello", 5))
        assertEquals("hello", clampInputLength("hello world", 5))
        assertEquals("", clampInputLength("hello world", 0))
        assertEquals(
            "hello world",
            clampInputLength("hello world", ElegantInputDefaults.MaxLengthUnlimited),
        )
    }

    @Test
    fun blankLabelsResolveToNull() {
        assertNull(resolveInputLabel(null))
        assertNull(resolveInputLabel(""))
        assertNull(resolveInputLabel("   "))
        assertEquals("Name", resolveInputLabel("Name"))
    }

    @Test
    fun defaultsExposeUnlimitedLengthAndAccessibleTouchTarget() {
        assertTrue(ElegantInputDefaults.MaxLengthUnlimited == Int.MAX_VALUE)
        assertTrue(ElegantInputDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantInputDefaults.AnimationDurationMillis,
        )
    }

    @Test
    fun stylesResolveTheSameSquircleAwareShape() {
        val filled = ElegantInputDefaults.shape(ElegantInputStyle.Filled)
        val outlined = ElegantInputDefaults.shape(ElegantInputStyle.Outlined)

        assertEquals(RoundedCornerShape(16.dp), filled)
        assertEquals(RoundedCornerShape(16.dp), outlined)
        assertEquals(filled, outlined)
    }
}

package com.elegant.compose.ui.searchbar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantSearchBarContractTest {

    @Test
    fun filledStyleResolvesRecessedContainerWithFocusOnlyBorder() {
        val light = resolveSearchBarColors(ElegantLightColors)
        val dark = resolveSearchBarColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceSunken, light.containerColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredContainerColor)
        assertEquals(ElegantLightColors.surfaceRaised, light.focusedContainerColor)
        assertEquals(ElegantLightColors.surfaceSunken, light.disabledContainerColor)
        assertEquals(Color.Transparent, light.borderColor)
        assertEquals(Color.Transparent, light.hoveredBorderColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
        assertEquals(Color.Transparent, light.disabledBorderColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledContentColor)
        assertEquals(ElegantLightColors.textTertiary, light.placeholderColor)
        assertNotEquals(light, dark, "search bar colors must follow the active theme")
    }

    @Test
    fun iconColorUsesSecondaryEmphasisForEnabledAndTertiaryForDisabled() {
        assertEquals(
            ElegantLightColors.textSecondary,
            resolveSearchBarIconColor(ElegantLightColors, enabled = true),
        )
        assertEquals(
            ElegantLightColors.textTertiary,
            resolveSearchBarIconColor(ElegantLightColors, enabled = false),
        )
        assertEquals(
            ElegantDarkColors.textSecondary,
            resolveSearchBarIconColor(ElegantDarkColors, enabled = true),
        )
        assertEquals(
            ElegantDarkColors.textTertiary,
            resolveSearchBarIconColor(ElegantDarkColors, enabled = false),
        )
        assertNotEquals(
            resolveSearchBarIconColor(ElegantLightColors, enabled = true),
            resolveSearchBarIconColor(ElegantDarkColors, enabled = true),
            "glyph color must follow the active theme",
        )
    }

    @Test
    fun clearButtonIsVisibleOnlyForNonEmptyQueries() {
        assertFalse(resolveClearVisibility(""))
        assertTrue(resolveClearVisibility("a"))
        assertTrue(resolveClearVisibility(" "))
        assertTrue(resolveClearVisibility("search query"))
    }

    @Test
    fun visualsFollowDisabledFocusedHoveredRestingPrecedence() {
        val colors = resolveSearchBarColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            hovered: Boolean = false,
            focused: Boolean = false,
        ) = resolveSearchBarVisuals(
            colors = colors,
            enabled = enabled,
            hovered = hovered,
            focused = focused,
        )

        assertEquals(colors.disabledContainerColor, visuals(enabled = false, focused = true).container)
        assertEquals(colors.disabledBorderColor, visuals(enabled = false, focused = true).border)
        assertEquals(1.dp, visuals(enabled = false, focused = true).borderWidth)
        assertEquals(
            colors.focusedContainerColor,
            visuals(focused = true, hovered = true).container,
        )
        assertEquals(colors.focusedBorderColor, visuals(focused = true, hovered = true).border)
        assertEquals(2.dp, visuals(focused = true).borderWidth)
        assertEquals(colors.hoveredContainerColor, visuals(hovered = true).container)
        assertEquals(colors.hoveredBorderColor, visuals(hovered = true).border)
        assertEquals(1.dp, visuals(hovered = true).borderWidth)
        assertEquals(colors.containerColor, visuals().container)
        assertEquals(1.dp, visuals().borderWidth)
    }

    @Test
    fun defaultsExposeAccessibleTouchTargetAndFullRadiusShape() {
        assertTrue(ElegantSearchBarDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(RoundedCornerShape(ElegantRadius.full), ElegantSearchBarDefaults.shape())
        assertEquals(
            ElegantMotion.standardDurationMillis,
            SearchBarMetrics.AnimationDurationMillis,
        )
    }
}

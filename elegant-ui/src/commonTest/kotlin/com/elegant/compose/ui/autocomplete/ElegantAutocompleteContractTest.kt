package com.elegant.compose.ui.autocomplete

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

internal class ElegantAutocompleteContractTest {

    private val options = listOf(
        ElegantAutocompleteOption(text = "France", value = "FR"),
        ElegantAutocompleteOption(text = "Germany", value = "DE"),
        ElegantAutocompleteOption(text = "Fiji", value = "FJ"),
        ElegantAutocompleteOption(text = "Netherlands", value = "NL"),
    )

    @Test
    fun colorsResolveFilledRolesFromTheActiveTheme() {
        val light = resolveAutocompleteColors(ElegantLightColors)
        val dark = resolveAutocompleteColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceSunken, light.containerColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredContainerColor)
        assertEquals(ElegantLightColors.surfaceRaised, light.focusedContainerColor)
        assertEquals(ElegantLightColors.surfaceSunken, light.disabledContainerColor)
        assertEquals(Color.Transparent, light.borderColor)
        assertEquals(Color.Transparent, light.hoveredBorderColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedBorderColor)
        assertEquals(ElegantLightColors.statusCritical, light.errorBorderColor)
        assertEquals(Color.Transparent, light.disabledBorderColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledContentColor)
        assertEquals(ElegantLightColors.textTertiary, light.placeholderColor)
        assertEquals(ElegantLightColors.textSecondary, light.labelColor)
        assertEquals(ElegantLightColors.textSecondary, light.supportingTextColor)
        assertEquals(ElegantLightColors.statusCritical, light.errorTextColor)
        assertNotEquals(light, dark, "colors must follow the active theme")
    }

    @Test
    fun blankQueryReturnsEveryOptionInOriginalOrder() {
        val all = filterOptions(options, "")

        assertEquals(options, all)
        assertSame(options, all, "a blank query must pass the option list through untouched")
        assertEquals(options, filterOptions(options, "   "))
    }

    @Test
    fun queryFiltersByCaseInsensitiveContains() {
        assertEquals(
            listOf(
                ElegantAutocompleteOption(text = "France", value = "FR"),
                ElegantAutocompleteOption(text = "Fiji", value = "FJ"),
            ),
            filterOptions(options, "F"),
        )
        assertEquals(
            listOf(ElegantAutocompleteOption(text = "France", value = "FR")),
            filterOptions(options, "fr"),
        )
        assertEquals(
            listOf(ElegantAutocompleteOption(text = "France", value = "FR")),
            filterOptions(options, "anc"),
        )
        assertEquals(
            listOf(ElegantAutocompleteOption(text = "Netherlands", value = "NL")),
            filterOptions(options, "net"),
        )
    }

    @Test
    fun unmatchedQueryReturnsEmptyList() {
        assertTrue(filterOptions(options, "xyz").isEmpty())
    }

    @Test
    fun filteringPreservesOriginalOptionOrder() {
        val shuffled = listOf(
            ElegantAutocompleteOption(text = "Netherlands", value = "NL"),
            ElegantAutocompleteOption(text = "France", value = "FR"),
            ElegantAutocompleteOption(text = "Fiji", value = "FJ"),
        )
        val result = filterOptions(shuffled, "F")

        assertEquals(
            listOf(
                ElegantAutocompleteOption(text = "France", value = "FR"),
                ElegantAutocompleteOption(text = "Fiji", value = "FJ"),
            ),
            result,
        )
        assertTrue(result.none { option -> option.value == "NL" })
    }

    @Test
    fun optionsWithBlankTextFollowTheSameFilteringRules() {
        val withBlank = listOf(
            ElegantAutocompleteOption(text = "", value = "EMPTY"),
            ElegantAutocompleteOption(text = "France", value = "FR"),
        )

        assertEquals(withBlank, filterOptions(withBlank, ""))
        assertEquals(
            listOf(ElegantAutocompleteOption(text = "France", value = "FR")),
            filterOptions(withBlank, "fr"),
        )
    }

    @Test
    fun listVisibilityRequiresFocusAndAtLeastOneMatch() {
        assertFalse(suggestionListVisible(focused = false, filteredCount = 0))
        assertFalse(suggestionListVisible(focused = false, filteredCount = 3))
        assertFalse(suggestionListVisible(focused = true, filteredCount = 0))
        assertTrue(suggestionListVisible(focused = true, filteredCount = 1))
        assertTrue(suggestionListVisible(focused = true, filteredCount = 8))
    }

    @Test
    fun visualsFollowDisabledErrorFocusedHoveredRestingPrecedence() {
        val colors = resolveAutocompleteColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            hovered: Boolean = false,
            focused: Boolean = false,
            isError: Boolean = false,
        ) = resolveAutocompleteVisuals(
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
    fun listPositionPlacesBelowAnchorAndClampsIntoWindow() {
        val anchor = IntRect(left = 10, top = 20, right = 210, bottom = 68)
        val listSize = IntSize(width = 200, height = 280)
        val window = IntSize(width = 800, height = 600)

        assertEquals(
            IntOffset(x = 10, y = 72),
            autocompleteListPosition(
                anchorBounds = anchor,
                listSize = listSize,
                offsetPx = 4,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 0, y = 0),
            autocompleteListPosition(
                anchorBounds = anchor,
                listSize = listSize,
                offsetPx = 4,
                windowSize = IntSize(width = 100, height = 50),
            ),
        )
        assertEquals(
            IntOffset(x = 10, y = 320),
            autocompleteListPosition(
                anchorBounds = IntRect(left = 10, top = 500, right = 210, bottom = 548),
                listSize = listSize,
                offsetPx = 4,
                windowSize = window,
            ),
        )
    }

    @Test
    fun defaultsExposeAccessibleTouchTargetAndMenuCap() {
        assertEquals(48.dp, ElegantAutocompleteDefaults.MinimumTouchHeight)
        assertTrue(ElegantAutocompleteDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(280.dp, ElegantAutocompleteDefaults.MenuMaxHeight)
        assertEquals(4.dp, AutocompleteMetrics.AnchorOffset)
        assertEquals(40.dp, AutocompleteMetrics.ItemMinHeight)
        assertEquals(16.dp, AutocompleteMetrics.ItemHorizontalPadding)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            AutocompleteMetrics.AnimationDurationMillis,
        )
    }
}

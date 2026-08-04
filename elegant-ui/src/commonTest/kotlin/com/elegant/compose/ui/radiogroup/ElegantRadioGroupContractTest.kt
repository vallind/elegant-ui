package com.elegant.compose.ui.radiogroup

import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantRadioGroupContractTest {

    @Test
    fun defaultColorsFollowTheActiveTheme() {
        val light = resolveRadioGroupColors(ElegantLightColors)
        val dark = resolveRadioGroupColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.textPrimary, light.labelColor)
        assertEquals(ElegantLightColors.textSecondary, light.supportingTextColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledLabelColor)
        assertNotEquals(light, dark, "radio group colors must follow the active theme")
    }

    @Test
    fun labelAndSupportingColorsStayVisuallyDistinct() {
        val light = resolveRadioGroupColors(ElegantLightColors)

        assertNotEquals(light.labelColor, light.supportingTextColor)
        assertNotEquals(light.labelColor, light.disabledLabelColor)
        assertNotEquals(light.supportingTextColor, light.disabledLabelColor)
    }

    @Test
    fun selectionMatchesOnlyNonNullNonBlankEqualValues() {
        assertFalse(isItemSelected(null, "standard"))
        assertFalse(isItemSelected("", "standard"))
        assertFalse(isItemSelected("  ", "standard"))
        assertTrue(isItemSelected("standard", "standard"))
        assertFalse(isItemSelected("standard", "express"))
    }

    @Test
    fun selectionIsCaseSensitive() {
        assertFalse(isItemSelected("Standard", "standard"))
    }

    @Test
    fun duplicateValuesSelectEveryMatchingItem() {
        assertTrue(isItemSelected("same", "same"))
        assertTrue(isItemSelected("same", "same"))
    }

    @Test
    fun canSelectRequiresBothItemAndGroupToBeEnabled() {
        assertTrue(canSelect(itemEnabled = true, groupEnabled = true))
        assertFalse(canSelect(itemEnabled = false, groupEnabled = true))
        assertFalse(canSelect(itemEnabled = true, groupEnabled = false))
        assertFalse(canSelect(itemEnabled = false, groupEnabled = false))
    }

    @Test
    fun supportingTextColorFallsBackToDisabledWhenTheGroupIsDisabled() {
        val colors = resolveRadioGroupColors(ElegantLightColors)

        assertEquals(colors.supportingTextColor, resolveSupportingTextColor(colors, enabled = true))
        assertEquals(colors.disabledLabelColor, resolveSupportingTextColor(colors, enabled = false))
    }

    @Test
    fun defaultsMeetTokenBaselines() {
        assertEquals(4.dp, ElegantRadioGroupDefaults.ItemGap)
    }
}

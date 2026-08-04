package com.elegant.compose.ui.preference

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantCheckboxPreferenceContractTest {

    @Test
    fun colorsResolveThemeAwareDefaults() {
        val light = resolveCheckboxPreferenceColors(ElegantLightColors)
        val dark = resolveCheckboxPreferenceColors(ElegantDarkColors)

        assertEquals(Color.Transparent, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.titleColor)
        assertEquals(ElegantLightColors.textSecondary, light.supportingTextColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledTitleColor)
        assertEquals(ElegantLightColors.borderDefault, light.dividerColor)
        assertNotEquals(light, dark, "checkbox preference colors must follow the active theme")
    }

    @Test
    fun containerColorIsTransparentInBothThemes() {
        val light = resolveCheckboxPreferenceColors(ElegantLightColors)
        val dark = resolveCheckboxPreferenceColors(ElegantDarkColors)

        assertEquals(Color.Transparent, light.containerColor)
        assertEquals(Color.Transparent, dark.containerColor)
    }

    @Test
    fun disabledTitleUsesTheTertiaryRoleWhileDividerUsesTheDefaultBorder() {
        val light = resolveCheckboxPreferenceColors(ElegantLightColors)
        val dark = resolveCheckboxPreferenceColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.textTertiary, light.disabledTitleColor)
        assertEquals(ElegantLightColors.borderDefault, light.dividerColor)
        assertEquals(ElegantDarkColors.textTertiary, dark.disabledTitleColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.dividerColor)
    }

    @Test
    fun supportingTextTreatsBlankValuesAsAbsent() {
        assertNull(resolveSupportingText(null))
        assertNull(resolveSupportingText(""))
        assertNull(resolveSupportingText("   "))
        assertNull(resolveSupportingText("\t\n"))
    }

    @Test
    fun supportingTextKeepsNonBlankValues() {
        assertEquals("Release notes", resolveSupportingText("Release notes"))
        assertEquals("  padded  ", resolveSupportingText("  padded  "))
    }

    @Test
    fun defaultsMeetAccessibilityAndRhythmContracts() {
        assertEquals(48.dp, ElegantCheckboxPreferenceDefaults.MinimumTouchHeight)
        assertTrue(ElegantCheckboxPreferenceDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(16.dp, CheckboxPreferenceRowHorizontalPadding)
        assertEquals(4.dp, CheckboxPreferenceRowVerticalPadding)
        assertEquals(8.dp, CheckboxPreferenceControlGap)
        assertEquals(1.dp, CheckboxPreferenceDividerHeight)
        assertEquals(16.dp, CheckboxPreferenceDividerStartInset)
    }
}

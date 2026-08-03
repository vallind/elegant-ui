package com.elegant.compose.ui.preference

import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantSpacing
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantSliderPreferenceContractTest {

    @Test
    fun defaultColorsFollowTheActiveTheme() {
        val light = resolveSliderPreferenceColors(ElegantLightColors)
        val dark = resolveSliderPreferenceColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.textPrimary, light.titleColor)
        assertEquals(ElegantLightColors.textSecondary, light.supportingTextColor)
        assertEquals(ElegantLightColors.textSecondary, light.valueColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledTitleColor)
        assertEquals(ElegantLightColors.borderDefault, light.dividerColor)
        assertNotEquals(light, dark, "slider preference colors must follow the active theme")
    }

    @Test
    fun titleFallsBackToDisabledToneWhileDisabled() {
        val colors = resolveSliderPreferenceColors(ElegantLightColors)

        assertEquals(ElegantLightColors.textPrimary, resolveSliderPreferenceTitleColor(colors, enabled = true))
        assertEquals(ElegantLightColors.textTertiary, resolveSliderPreferenceTitleColor(colors, enabled = false))
    }

    @Test
    fun valueFallsBackToDisabledToneWhileDisabled() {
        val colors = resolveSliderPreferenceColors(ElegantLightColors)

        assertEquals(ElegantLightColors.textSecondary, resolveSliderPreferenceValueColor(colors, enabled = true))
        assertEquals(ElegantLightColors.textTertiary, resolveSliderPreferenceValueColor(colors, enabled = false))
    }

    @Test
    fun supportingTextIsHiddenWhenBlank() {
        assertNull(resolveSupportingText(null))
        assertNull(resolveSupportingText(""))
        assertNull(resolveSupportingText("   "))
        assertEquals("Adjust display brightness", resolveSupportingText("Adjust display brightness"))
    }

    @Test
    fun defaultsMeetAccessibilityAndTokenBaselines() {
        assertEquals(56.dp, ElegantSliderPreferenceDefaults.MinimumTouchHeight)
        assertTrue(ElegantSliderPreferenceDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(ElegantSpacing.xl, SliderPreferenceContentPadding)
        assertEquals(ElegantSpacing.md, SliderPreferenceTitleValueGap)
        assertEquals(ElegantSpacing.xl, SliderPreferenceDividerInset)
        assertEquals(1.dp, SliderPreferenceDividerThickness)
    }
}

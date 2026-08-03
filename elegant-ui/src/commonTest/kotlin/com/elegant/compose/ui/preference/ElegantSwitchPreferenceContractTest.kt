package com.elegant.compose.ui.preference

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantSwitchPreferenceContractTest {

    @Test
    fun colorsResolveThemeAwareDefaults() {
        val light = resolvePreferenceColors(ElegantLightColors)
        val dark = resolvePreferenceColors(ElegantDarkColors)

        assertEquals(Color.Transparent, light.containerColor)
        assertEquals(ElegantLightColors.textPrimary, light.titleColor)
        assertEquals(ElegantLightColors.textSecondary, light.supportingTextColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledTitleColor)
        assertEquals(ElegantLightColors.borderDefault, light.dividerColor)
        assertNotEquals(light, dark, "preference colors must follow the active theme")
    }

    @Test
    fun darkColorsFollowTheActiveTheme() {
        val dark = resolvePreferenceColors(ElegantDarkColors)

        assertEquals(Color.Transparent, dark.containerColor)
        assertEquals(ElegantDarkColors.textPrimary, dark.titleColor)
        assertEquals(ElegantDarkColors.textSecondary, dark.supportingTextColor)
        assertEquals(ElegantDarkColors.textTertiary, dark.disabledTitleColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.dividerColor)
    }

    @Test
    fun supportingTextCollapsesBlankValuesToNull() {
        assertNull(resolveSupportingText(null))
        assertNull(resolveSupportingText(""))
        assertNull(resolveSupportingText("   "))
        assertNull(resolveSupportingText("\t\n"))
    }

    @Test
    fun supportingTextKeepsMeaningfulValues() {
        assertEquals("Sync across devices", resolveSupportingText("Sync across devices"))
        assertEquals("  Keep spacing  ", resolveSupportingText("  Keep spacing  "))
    }

    @Test
    fun defaultsMeetAccessibilityAndRhythmContracts() {
        assertEquals(48.dp, ElegantPreferenceDefaults.MinimumTouchHeight)
        assertTrue(ElegantPreferenceDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(16.dp, PreferenceMetrics.RowHorizontalPadding)
        assertEquals(2.dp, PreferenceMetrics.TitleBlockSpacing)
        assertEquals(8.dp, PreferenceMetrics.EndControlGap)
        assertEquals(16.dp, PreferenceMetrics.DividerInsetStart)
        assertEquals(1.dp, PreferenceMetrics.DividerHeight)
    }

    @Test
    fun customColorsRoundTripThroughTheDataClass() {
        val container = Color(0xFFFF0000)
        val title = Color(0xFF00FF00)
        val supporting = Color(0xFF0000FF)
        val disabledTitle = Color(0xFFFFFF00)
        val divider = Color(0xFF00FFFF)
        val colors = ElegantPreferenceColors(
            containerColor = container,
            titleColor = title,
            supportingTextColor = supporting,
            disabledTitleColor = disabledTitle,
            dividerColor = divider,
        )

        assertEquals(container, colors.containerColor)
        assertEquals(title, colors.titleColor)
        assertEquals(supporting, colors.supportingTextColor)
        assertEquals(disabledTitle, colors.disabledTitleColor)
        assertEquals(divider, colors.dividerColor)
    }
}

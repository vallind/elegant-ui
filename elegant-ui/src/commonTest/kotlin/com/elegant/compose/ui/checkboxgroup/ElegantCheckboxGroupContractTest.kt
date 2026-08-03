package com.elegant.compose.ui.checkboxgroup

import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantCheckboxGroupContractTest {

    @Test
    fun colorsResolveThemeAwareTextRoles() {
        val light = resolveCheckboxGroupColors(ElegantLightColors)
        val dark = resolveCheckboxGroupColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.textPrimary, light.labelColor)
        assertEquals(ElegantLightColors.textSecondary, light.supportingTextColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledLabelColor)
        assertNotEquals(light, dark, "checkbox group colors must follow the active theme")
    }

    @Test
    fun itemDefaultsToEnabledAndKeepsItsModelFields() {
        val item = ElegantCheckboxGroupItem(text = "Photos", value = "photos")

        assertEquals("Photos", item.text)
        assertEquals("photos", item.value)
        assertTrue(item.enabled)

        val disabled = ElegantCheckboxGroupItem(
            text = "Microphone",
            value = "microphone",
            enabled = false,
        )
        assertFalse(disabled.enabled)
    }

    @Test
    fun checkedMatchesItemValueAgainstTheSelectedSet() {
        val selected = setOf("camera", "photos")

        assertTrue(isChecked(selected, "camera"))
        assertTrue(isChecked(selected, "photos"))
        assertFalse(isChecked(selected, "microphone"))
        assertFalse(isChecked(emptySet(), "camera"))
    }

    @Test
    fun canToggleRequiresBothItemAndGroupEnabled() {
        assertTrue(canToggle(itemEnabled = true, groupEnabled = true))
        assertFalse(canToggle(itemEnabled = false, groupEnabled = true))
        assertFalse(canToggle(itemEnabled = true, groupEnabled = false))
        assertFalse(canToggle(itemEnabled = false, groupEnabled = false))
    }

    @Test
    fun defaultsKeepTheGroupOnTheFourDpRhythm() {
        assertEquals(4.dp, ElegantCheckboxGroupDefaults.ItemGap)
    }
}

package com.elegant.compose.ui.switchgroup

import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantSwitchGroupContractTest {

    @Test
    fun colorsResolveThemeAwareTextRoles() {
        val light = resolveSwitchGroupColors(ElegantLightColors)
        val dark = resolveSwitchGroupColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.textPrimary, light.labelColor)
        assertEquals(ElegantLightColors.textSecondary, light.supportingTextColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledLabelColor)
        assertNotEquals(light, dark, "switch group colors must follow the active theme")
    }

    @Test
    fun itemDefaultsToEnabledAndKeepsItsModelFields() {
        val item = ElegantSwitchGroupItem(text = "Push notifications", value = "push")

        assertEquals("Push notifications", item.text)
        assertEquals("push", item.value)
        assertTrue(item.enabled)

        val disabled = ElegantSwitchGroupItem(
            text = "In-app mentions",
            value = "mentions",
            enabled = false,
        )
        assertFalse(disabled.enabled)
    }

    @Test
    fun checkedMatchesItemValueAgainstTheSelectedSet() {
        val selected = setOf("push", "email")

        assertTrue(isChecked(selected, "push"))
        assertTrue(isChecked(selected, "email"))
        assertFalse(isChecked(selected, "mentions"))
        assertFalse(isChecked(emptySet(), "push"))
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
        assertEquals(4.dp, ElegantSwitchGroupDefaults.ItemGap)
    }
}

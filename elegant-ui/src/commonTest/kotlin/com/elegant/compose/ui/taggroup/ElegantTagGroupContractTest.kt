package com.elegant.compose.ui.taggroup

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantTagGroupContractTest {

    @Test
    fun colorsResolveThemeAwareChipRoles() {
        val light = resolveTagGroupColors(ElegantLightColors)
        val dark = resolveTagGroupColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.interactivePrimary, light.selectedContainerColor)
        assertEquals(ElegantLightColors.textInverse, light.selectedContentColor)
        assertEquals(Color.Transparent, light.unselectedContainerColor)
        assertEquals(ElegantLightColors.textPrimary, light.unselectedContentColor)
        assertEquals(ElegantLightColors.borderStrong, light.unselectedBorderColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledContentColor)
        assertNotEquals(light, dark, "tag group colors must follow the active theme")
    }

    @Test
    fun itemDefaultsToEnabledAndKeepsItsModelFields() {
        val item = ElegantTagGroupItem(text = "Design", value = "design")

        assertEquals("Design", item.text)
        assertEquals("design", item.value)
        assertTrue(item.enabled)

        val disabled = ElegantTagGroupItem(
            text = "Release",
            value = "release",
            enabled = false,
        )
        assertFalse(disabled.enabled)
    }

    @Test
    fun checkedMatchesItemValueAgainstTheSelectedSet() {
        val selected = setOf("design", "engineering")

        assertTrue(isChecked(selected, "design"))
        assertTrue(isChecked(selected, "engineering"))
        assertFalse(isChecked(selected, "release"))
        assertFalse(isChecked(emptySet(), "design"))
    }

    @Test
    fun canToggleRequiresBothItemAndGroupEnabled() {
        assertTrue(canToggle(itemEnabled = true, groupEnabled = true))
        assertFalse(canToggle(itemEnabled = false, groupEnabled = true))
        assertFalse(canToggle(itemEnabled = true, groupEnabled = false))
        assertFalse(canToggle(itemEnabled = false, groupEnabled = false))
    }

    @Test
    fun defaultsKeepTheGroupOnTheEightDpRhythm() {
        assertEquals(8.dp, ElegantTagGroupDefaults.ItemGap)
    }

    @Test
    fun chipMetricsStayCompactInsideTheTouchTarget() {
        assertTrue(TagGroupMinimumTouchHeight >= 48.dp)
        assertTrue(TagGroupChipHeight < TagGroupMinimumTouchHeight)
        assertTrue(TagGroupChipHorizontalPadding > 0.dp)
        assertTrue(TagGroupChipBorderWidth > 0.dp)
    }
}

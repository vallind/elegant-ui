package com.elegant.compose.ui.description

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantDescriptionContractTest {

    @Test
    fun publicModelsRemainStable() {
        val item = ElegantDescriptionItem(label = "Owner", value = "Maya Chen")
        assertEquals("Owner", item.label)
        assertEquals("Maya Chen", item.value)
        assertTrue(item.enabled)

        val disabled = ElegantDescriptionItem(
            label = "License",
            value = "Proprietary",
            enabled = false,
        )
        assertFalse(disabled.enabled)
    }

    @Test
    fun publicMetricsRemainOnTheElegantRhythm() {
        assertEquals(36.dp, ElegantDescriptionDefaults.RowMinHeight)
        assertEquals(140.dp, ElegantDescriptionDefaults.DefaultLabelWidth)
    }

    @Test
    fun defaultColorsResolveSemanticThemeRoles() {
        val light = resolveDescriptionColors(ElegantLightColors)

        assertEquals(ElegantLightColors.textSecondary, light.labelColor)
        assertEquals(ElegantLightColors.textPrimary, light.valueColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledValueColor)
        assertEquals(ElegantLightColors.borderDefault, light.dividerColor)
    }

    @Test
    fun defaultColorsAdaptBetweenLightAndDarkThemes() {
        val light = resolveDescriptionColors(ElegantLightColors)
        val dark = resolveDescriptionColors(ElegantDarkColors)

        assertNotEquals(light.labelColor, dark.labelColor)
        assertNotEquals(light.valueColor, dark.valueColor)
        assertNotEquals(light.disabledValueColor, dark.disabledValueColor)
        assertNotEquals(light.dividerColor, dark.dividerColor)
    }

    @Test
    fun dividersRenderBetweenRowsButNeverAfterTheLast() {
        assertFalse(rowDividerVisible(index = 0, itemCount = 0))
        assertFalse(rowDividerVisible(index = 0, itemCount = 1))
        assertTrue(rowDividerVisible(index = 0, itemCount = 2))
        assertTrue(rowDividerVisible(index = 0, itemCount = 3))
        assertTrue(rowDividerVisible(index = 1, itemCount = 3))
        assertFalse(rowDividerVisible(index = 2, itemCount = 3))
        assertFalse(rowDividerVisible(index = -1, itemCount = 3))
    }

    @Test
    fun labelWidthsCoerceToPositiveFiniteValues() {
        assertEquals(140.dp, resolveLabelWidth(140.dp))
        assertEquals(96.dp, resolveLabelWidth(96.dp))
        assertEquals(
            ElegantDescriptionDefaults.DefaultLabelWidth,
            resolveLabelWidth(0.dp),
        )
        assertEquals(
            ElegantDescriptionDefaults.DefaultLabelWidth,
            resolveLabelWidth((-20).dp),
        )
        assertEquals(
            ElegantDescriptionDefaults.DefaultLabelWidth,
            resolveLabelWidth(Dp.Infinity),
        )
        assertEquals(
            ElegantDescriptionDefaults.DefaultLabelWidth,
            resolveLabelWidth(Dp.Unspecified),
        )
    }

    @Test
    fun customColorsRemainAnImmutableValueContract() {
        val colors = ElegantDescriptionColors(
            labelColor = Color.Gray,
            valueColor = Color.Black,
            disabledValueColor = Color.LightGray,
            dividerColor = Color.DarkGray,
        )

        assertEquals(Color.Gray, colors.labelColor)
        assertEquals(Color.Black, colors.valueColor)
        assertEquals(colors, colors.copy())
    }
}

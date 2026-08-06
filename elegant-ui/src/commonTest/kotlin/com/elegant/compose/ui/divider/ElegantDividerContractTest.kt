package com.elegant.compose.ui.divider

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantDividerContractTest {
    @Test
    fun publicEnumsRemainStable() {
        assertEquals(
            listOf("Horizontal", "Vertical"),
            ElegantDividerOrientation.entries.map(ElegantDividerOrientation::name),
        )
        assertEquals(
            listOf("Solid", "Dashed"),
            ElegantDividerStyle.entries.map(ElegantDividerStyle::name),
        )
        assertEquals(
            listOf("Subtle", "Strong"),
            ElegantDividerEmphasis.entries.map(ElegantDividerEmphasis::name),
        )
        assertEquals(
            listOf("Start", "Center", "End"),
            ElegantDividerLabelPosition.entries.map(ElegantDividerLabelPosition::name),
        )
    }

    @Test
    fun publicMetricsRemainOnTheElegantSpacingRhythm() {
        assertEquals(0.75.dp, ElegantDividerDefaults.Thickness)
        assertEquals(12.dp, ElegantDividerDefaults.LabelGap)
        assertEquals(8.dp, ElegantDividerDefaults.DashLength)
        assertEquals(4.dp, ElegantDividerDefaults.DashGap)
    }

    @Test
    fun invalidDimensionsFallBackWithoutCrashingComposition() {
        assertEquals(ElegantDividerDefaults.Thickness, resolveDividerThickness(0.dp))
        assertEquals(ElegantDividerDefaults.Thickness, resolveDividerThickness((-2).dp))
        assertEquals(
            ElegantDividerDefaults.Thickness,
            resolveDividerThickness(Dp(Float.NaN)),
        )
        assertEquals(3.dp, resolveDividerThickness(3.dp))

        assertEquals(0.dp, resolveDividerLabelGap(0.dp))
        assertEquals(ElegantDividerDefaults.LabelGap, resolveDividerLabelGap((-4).dp))
        assertEquals(
            ElegantDividerDefaults.LabelGap,
            resolveDividerLabelGap(Dp(Float.POSITIVE_INFINITY)),
        )
        assertEquals(16.dp, resolveDividerLabelGap(16.dp))
    }

    @Test
    fun labelPositionsUseLogicalLineWeights() {
        assertEquals(
            DividerLineWeights(before = 0f, after = 1f),
            dividerLineWeightsFor(ElegantDividerLabelPosition.Start),
        )
        assertEquals(
            DividerLineWeights(before = 1f, after = 1f),
            dividerLineWeightsFor(ElegantDividerLabelPosition.Center),
        )
        assertEquals(
            DividerLineWeights(before = 1f, after = 0f),
            dividerLineWeightsFor(ElegantDividerLabelPosition.End),
        )
    }

    @Test
    fun emphasisResolvesSemanticThemeRoles() {
        val subtle = resolveDividerColors(
            emphasis = ElegantDividerEmphasis.Subtle,
            themeColors = ElegantLightColors,
        )
        val strong = resolveDividerColors(
            emphasis = ElegantDividerEmphasis.Strong,
            themeColors = ElegantLightColors,
        )

        assertEquals(ElegantLightColors.borderDefault, subtle.lineColor)
        assertEquals(ElegantLightColors.textSecondary, subtle.contentColor)
        assertEquals(ElegantLightColors.borderStrong, strong.lineColor)
        assertEquals(ElegantLightColors.textPrimary, strong.contentColor)
        assertNotEquals(subtle, strong)
    }

    @Test
    fun defaultRolesAdaptBetweenLightAndDarkThemes() {
        for (emphasis in ElegantDividerEmphasis.entries) {
            val light = resolveDividerColors(emphasis, ElegantLightColors)
            val dark = resolveDividerColors(emphasis, ElegantDarkColors)

            assertNotEquals(light.lineColor, dark.lineColor)
            assertNotEquals(light.contentColor, dark.contentColor)
        }
    }

    @Test
    fun customColorsRemainAnImmutableValueContract() {
        val colors = ElegantDividerColors(
            lineColor = Color.Red,
            contentColor = Color.White,
        )

        assertEquals(Color.Red, colors.lineColor)
        assertEquals(Color.White, colors.contentColor)
        assertEquals(colors, colors.copy())
    }
}

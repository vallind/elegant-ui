package com.elegant.compose.ui.spinner

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantSpinnerContractTest {

    @Test
    fun publicMetricsRemainOnTheElegantDesignTokens() {
        assertEquals(40.dp, ElegantSpinnerDefaults.Size)
        assertEquals(4.dp, ElegantSpinnerDefaults.StrokeWidth)
        assertEquals(DefaultSpinnerSize, ElegantSpinnerDefaults.Size)
        assertEquals(DefaultSpinnerStrokeWidth, ElegantSpinnerDefaults.StrokeWidth)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantSpinnerDefaults.AnimationDurationMillis,
        )
    }

    @Test
    fun colorsResolveSemanticThemeRoles() {
        val light = resolveSpinnerColors(ElegantLightColors)
        val dark = resolveSpinnerColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.interactivePrimary, light.indicatorColor)
        assertEquals(ElegantLightColors.borderDefault, light.trackColor)
        assertEquals(ElegantLightColors.textSecondary, light.labelColor)
        assertEquals(ElegantDarkColors.interactivePrimary, dark.indicatorColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.trackColor)
        assertEquals(ElegantDarkColors.textSecondary, dark.labelColor)
        assertNotEquals(light, dark)
    }

    @Test
    fun sizeFallsBackToTheDefaultWhenNonPositiveOrNonFinite() {
        assertEquals(48.dp, resolveSize(48.dp))
        assertEquals(DefaultSpinnerSize, resolveSize(0.dp))
        assertEquals(DefaultSpinnerSize, resolveSize((-8).dp))
        assertEquals(DefaultSpinnerSize, resolveSize(Dp.Infinity))
        assertEquals(DefaultSpinnerSize, resolveSize(Dp.Unspecified))
    }

    @Test
    fun strokeWidthFallsBackToTheDefaultWhenNonPositiveOrNonFinite() {
        assertEquals(3.dp, resolveStrokeWidth(3.dp))
        assertEquals(DefaultSpinnerStrokeWidth, resolveStrokeWidth(0.dp))
        assertEquals(DefaultSpinnerStrokeWidth, resolveStrokeWidth((-2).dp))
        assertEquals(DefaultSpinnerStrokeWidth, resolveStrokeWidth(Dp.Infinity))
        assertEquals(DefaultSpinnerStrokeWidth, resolveStrokeWidth(Dp.Unspecified))
    }

    @Test
    fun customColorsRemainAnImmutableValueContract() {
        val colors = ElegantSpinnerColors(
            indicatorColor = Color.Red,
            trackColor = Color.Gray,
            labelColor = Color.Blue,
        )

        assertEquals(Color.Red, colors.indicatorColor)
        assertEquals(Color.Gray, colors.trackColor)
        assertEquals(Color.Blue, colors.labelColor)
        assertEquals(colors, colors.copy())
    }
}

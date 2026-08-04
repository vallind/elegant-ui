package com.elegant.compose.ui.progress

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

internal class ElegantProgressContractTest {
    @Test
    fun publicMetricsRemainOnTheElegantDesignTokens() {
        assertEquals(4.dp, ElegantProgressDefaults.LinearTrackHeight)
        assertEquals(40.dp, ElegantProgressDefaults.CircularSize)
        assertEquals(4.dp, ElegantProgressDefaults.CircularStrokeWidth)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantProgressDefaults.AnimationDurationMillis,
        )
        assertEquals(1200, ElegantProgressDefaults.IndeterminateDurationMillis)
    }

    @Test
    fun indeterminateAnimationGeometryRemainsStable() {
        assertEquals(0.3f, LinearIndicatorSegmentFraction)
        assertEquals(270f, IndeterminateArcSweepDegrees)
    }

    @Test
    fun colorsResolveSemanticThemeRoles() {
        val light = resolveProgressColors(ElegantLightColors)
        val dark = resolveProgressColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.interactivePrimary, light.indicatorColor)
        assertEquals(ElegantLightColors.borderDefault, light.trackColor)
        assertEquals(ElegantDarkColors.interactivePrimary, dark.indicatorColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.trackColor)
        assertNotEquals(light, dark)
    }

    @Test
    fun clampProgressTreatsNullAndNanAsIndeterminate() {
        assertNull(clampProgress(null))
        assertNull(clampProgress(Float.NaN))
    }

    @Test
    fun clampProgressCoercesOutOfRangeValues() {
        assertEquals(0f, clampProgress(-0.5f))
        assertEquals(0f, clampProgress(Float.NEGATIVE_INFINITY))
        assertEquals(0f, clampProgress(0f))
        assertEquals(0.25f, clampProgress(0.25f))
        assertEquals(1f, clampProgress(1f))
        assertEquals(1f, clampProgress(1.5f))
        assertEquals(1f, clampProgress(Float.POSITIVE_INFINITY))
    }

    @Test
    fun circularSweepIsCoercedAndNanSafe() {
        assertEquals(0f, circularSweep(Float.NaN))
        assertEquals(0f, circularSweep(0f))
        assertEquals(90f, circularSweep(0.25f))
        assertEquals(180f, circularSweep(0.5f))
        assertEquals(360f, circularSweep(1f))
        assertEquals(0f, circularSweep(-1f))
        assertEquals(360f, circularSweep(2f))
    }

    @Test
    fun customColorsRemainAnImmutableValueContract() {
        val colors = ElegantProgressColors(
            indicatorColor = Color.Red,
            trackColor = Color.Gray,
        )

        assertEquals(Color.Red, colors.indicatorColor)
        assertEquals(Color.Gray, colors.trackColor)
        assertEquals(colors, colors.copy())
    }
}

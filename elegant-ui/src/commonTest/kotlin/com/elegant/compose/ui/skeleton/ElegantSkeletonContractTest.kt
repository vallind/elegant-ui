package com.elegant.compose.ui.skeleton

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantSkeletonContractTest {
    @Test
    fun publicMetricsAndDefaultsRemainStable() {
        assertEquals(1400, ElegantSkeletonDefaults.AnimationDurationMillis)
        assertEquals(3, DefaultSkeletonColumns)
        assertEquals(1, MinimumSkeletonColumns)
        assertEquals(0.6f, DefaultSkeletonLastLineWidthFraction)
        assertEquals(0.2f, MinimumSkeletonLastLineWidthFraction)
        assertEquals(12.dp, SkeletonBlockLineHeight)
    }

    @Test
    fun blockColumnsCoerceToAtLeastOne() {
        assertEquals(1, resolveBlockColumns(0))
        assertEquals(1, resolveBlockColumns(-1))
        assertEquals(1, resolveBlockColumns(Int.MIN_VALUE))
        assertEquals(1, resolveBlockColumns(1))
        assertEquals(3, resolveBlockColumns(3))
        assertEquals(7, resolveBlockColumns(7))
    }

    @Test
    fun lastLineWidthFractionFallsBackAndClamps() {
        assertEquals(0.6f, resolveLastLineWidth(Float.NaN))
        assertEquals(0.2f, resolveLastLineWidth(0f))
        assertEquals(0.2f, resolveLastLineWidth(-1f))
        assertEquals(0.2f, resolveLastLineWidth(Float.NEGATIVE_INFINITY))
        assertEquals(1f, resolveLastLineWidth(2f))
        assertEquals(1f, resolveLastLineWidth(Float.POSITIVE_INFINITY))
        assertEquals(0.2f, resolveLastLineWidth(0.2f))
        assertEquals(0.6f, resolveLastLineWidth(0.6f))
        assertEquals(0.5f, resolveLastLineWidth(0.5f))
        assertEquals(1f, resolveLastLineWidth(1f))
    }

    @Test
    fun defaultColorsResolveSemanticThemeRoles() {
        val light = resolveSkeletonColors(ElegantLightColors)
        val dark = resolveSkeletonColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.surfaceSunken, light.baseColor)
        assertEquals(ElegantLightColors.backgroundSubtle, light.highlightColor)
        assertEquals(ElegantDarkColors.surfaceSunken, dark.baseColor)
        assertEquals(ElegantDarkColors.backgroundSubtle, dark.highlightColor)
        assertNotEquals(light.baseColor, dark.baseColor)
        assertNotEquals(light.highlightColor, dark.highlightColor)
        assertNotEquals(light, dark)
    }

    @Test
    fun customColorsRemainAnImmutableValueContract() {
        val colors = ElegantSkeletonColors(
            baseColor = Color.Red,
            highlightColor = Color.White,
        )

        assertEquals(Color.Red, colors.baseColor)
        assertEquals(Color.White, colors.highlightColor)
        assertEquals(colors, colors.copy())
    }
}

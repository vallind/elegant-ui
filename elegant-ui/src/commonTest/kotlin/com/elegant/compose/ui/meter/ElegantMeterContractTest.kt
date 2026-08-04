package com.elegant.compose.ui.meter

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

internal class ElegantMeterContractTest {
    @Test
    fun publicEnumRemainsStable() {
        assertEquals(
            listOf("Neutral", "Positive", "Warning", "Critical"),
            ElegantMeterTone.entries.map(ElegantMeterTone::name),
        )
    }

    @Test
    fun publicDefaultsRemainOnTheElegantTokens() {
        assertEquals(6.dp, ElegantMeterDefaults.TrackHeight)
        assertEquals(0.33f, ElegantMeterDefaults.LowThreshold)
        assertEquals(0.66f, ElegantMeterDefaults.HighThreshold)
        assertEquals(0.9f, AutoCriticalFraction)
    }

    @Test
    fun colorsResolveSemanticThemeRoles() {
        val light = resolveMeterColors(ElegantLightColors)
        val dark = resolveMeterColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.borderDefault, light.trackColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.fillColor)
        assertEquals(ElegantLightColors.statusPositive, light.positiveFillColor)
        assertEquals(ElegantLightColors.statusWarning, light.warningFillColor)
        assertEquals(ElegantLightColors.statusCritical, light.criticalFillColor)
        assertEquals(ElegantLightColors.textPrimary, light.contentColor)
        assertEquals(ElegantLightColors.textSecondary, light.labelColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.trackColor)
        assertEquals(ElegantDarkColors.statusPositive, dark.positiveFillColor)
        assertEquals(ElegantDarkColors.statusWarning, dark.warningFillColor)
        assertEquals(ElegantDarkColors.statusCritical, dark.criticalFillColor)
        assertEquals(ElegantDarkColors.textPrimary, dark.contentColor)
        assertEquals(ElegantDarkColors.textSecondary, dark.labelColor)
        assertNotEquals(light, dark)
    }

    @Test
    fun autoToneResolvesSemanticZones() {
        assertEquals(ElegantMeterTone.Positive, resolveMeterTone(0f, null, 0.66f))
        assertEquals(ElegantMeterTone.Positive, resolveMeterTone(0.33f, null, 0.66f))
        assertEquals(ElegantMeterTone.Positive, resolveMeterTone(0.5f, null, 0.66f))
        assertEquals(ElegantMeterTone.Positive, resolveMeterTone(0.66f, null, 0.66f))
        assertEquals(ElegantMeterTone.Warning, resolveMeterTone(0.67f, null, 0.66f))
        assertEquals(ElegantMeterTone.Warning, resolveMeterTone(0.9f, null, 0.66f))
        assertEquals(ElegantMeterTone.Critical, resolveMeterTone(0.91f, null, 0.66f))
        assertEquals(ElegantMeterTone.Critical, resolveMeterTone(1f, null, 0.66f))
    }

    @Test
    fun autoToneHonorsCustomHighThreshold() {
        assertEquals(ElegantMeterTone.Warning, resolveMeterTone(0.5f, null, 0.4f))
        assertEquals(ElegantMeterTone.Positive, resolveMeterTone(0.2f, null, 0.4f))
        assertEquals(ElegantMeterTone.Positive, resolveMeterTone(0.99f, null, 1.2f))
    }

    @Test
    fun explicitToneOverridesAutoResolution() {
        assertEquals(
            ElegantMeterTone.Neutral,
            resolveMeterTone(0.95f, ElegantMeterTone.Neutral, 0.66f),
        )
        assertEquals(
            ElegantMeterTone.Positive,
            resolveMeterTone(0.95f, ElegantMeterTone.Positive, 0.66f),
        )
        assertEquals(
            ElegantMeterTone.Warning,
            resolveMeterTone(0.05f, ElegantMeterTone.Warning, 0.66f),
        )
        assertEquals(
            ElegantMeterTone.Critical,
            resolveMeterTone(0.05f, ElegantMeterTone.Critical, 0.66f),
        )
    }

    @Test
    fun nanResolvesNeutralUnlessExplicitToneForcesOne() {
        assertEquals(ElegantMeterTone.Neutral, resolveMeterTone(Float.NaN, null, 0.66f))
        assertEquals(
            ElegantMeterTone.Warning,
            resolveMeterTone(Float.NaN, ElegantMeterTone.Warning, 0.66f),
        )
    }

    @Test
    fun meterFractionCoercesValuesIntoTheRange() {
        assertEquals(0f, meterFraction(Float.NaN, 0f..1f))
        assertEquals(0f, meterFraction(-0.5f, 0f..1f))
        assertEquals(0f, meterFraction(Float.NEGATIVE_INFINITY, 0f..1f))
        assertEquals(0.25f, meterFraction(0.25f, 0f..1f))
        assertEquals(1f, meterFraction(1.5f, 0f..1f))
        assertEquals(1f, meterFraction(Float.POSITIVE_INFINITY, 0f..1f))
    }

    @Test
    fun meterFractionScalesCustomRanges() {
        assertEquals(0.25f, meterFraction(32f, 0f..128f))
        assertEquals(0.5f, meterFraction(50f, 0f..100f))
        assertEquals(0.5f, meterFraction(10f, -10f..30f))
        assertEquals(0.125f, meterFraction(-5f, -10f..30f))
        assertEquals(0f, meterFraction(-30f, -10f..30f))
        assertEquals(1f, meterFraction(35f, -10f..30f))
    }

    @Test
    fun meterFractionGuardsDegenerateRanges() {
        assertEquals(0f, meterFraction(0.5f, 0f..0f))
        assertEquals(0f, meterFraction(0.5f, 5f..1f))
        assertEquals(0f, meterFraction(Float.POSITIVE_INFINITY, 0f..Float.POSITIVE_INFINITY))
    }

    @Test
    fun labelsResolveBlanksToNull() {
        assertNull(resolveLabel(null))
        assertNull(resolveLabel(""))
        assertNull(resolveLabel("   "))
        assertEquals("Storage", resolveLabel("Storage"))
        assertEquals(" Storage ", resolveLabel(" Storage "))
    }

    @Test
    fun customColorsRemainAnImmutableValueContract() {
        val colors = ElegantMeterColors(
            trackColor = Color.Gray,
            fillColor = Color.Blue,
            positiveFillColor = Color.Green,
            warningFillColor = Color.Yellow,
            criticalFillColor = Color.Red,
            contentColor = Color.Black,
            labelColor = Color.DarkGray,
        )

        assertEquals(Color.Gray, colors.trackColor)
        assertEquals(Color.Blue, colors.fillColor)
        assertEquals(Color.Green, colors.positiveFillColor)
        assertEquals(Color.Yellow, colors.warningFillColor)
        assertEquals(Color.Red, colors.criticalFillColor)
        assertEquals(Color.Black, colors.contentColor)
        assertEquals(Color.DarkGray, colors.labelColor)
        assertEquals(colors, colors.copy())
    }
}

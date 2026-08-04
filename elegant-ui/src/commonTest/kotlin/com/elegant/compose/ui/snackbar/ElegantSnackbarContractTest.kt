package com.elegant.compose.ui.snackbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantSnackbarContractTest {

    @Test
    fun lightThemeResolvesRaisedContainerAndPrimaryContent() {
        val colors = resolveSnackbarColors(ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceRaised, colors.containerColor)
        assertEquals(ElegantLightColors.textPrimary, colors.contentColor)
        assertEquals(ElegantLightColors.interactivePrimary, colors.actionContentColor)
    }

    @Test
    fun darkThemeResolvesRaisedContainerAndPrimaryContent() {
        val colors = resolveSnackbarColors(ElegantDarkColors)

        assertEquals(ElegantDarkColors.surfaceRaised, colors.containerColor)
        assertEquals(ElegantDarkColors.textPrimary, colors.contentColor)
        assertEquals(ElegantDarkColors.interactivePrimary, colors.actionContentColor)
    }

    @Test
    fun colorsFollowTheActiveTheme() {
        assertNotEquals(
            resolveSnackbarColors(ElegantLightColors),
            resolveSnackbarColors(ElegantDarkColors),
        )
    }

    @Test
    fun colorsMeetMinimumContrastInBothThemes() {
        val light = resolveSnackbarColors(ElegantLightColors)
        val dark = resolveSnackbarColors(ElegantDarkColors)

        assertTrue(contrastRatio(light.contentColor, light.containerColor) >= 3f)
        assertTrue(contrastRatio(light.actionContentColor, light.containerColor) >= 3f)
        assertTrue(contrastRatio(dark.contentColor, dark.containerColor) >= 3f)
        assertTrue(contrastRatio(dark.actionContentColor, dark.containerColor) >= 3f)
    }

    @Test
    fun shortAndLongDurationsMapToTheirDefaultsConstants() {
        assertEquals(
            ElegantSnackbarDefaults.ShortDurationMillis,
            durationMillis(ElegantSnackbarDuration.Short),
        )
        assertEquals(
            ElegantSnackbarDefaults.LongDurationMillis,
            durationMillis(ElegantSnackbarDuration.Long),
        )
    }

    @Test
    fun indefiniteDurationNeverAutoDismisses() {
        assertEquals(Long.MAX_VALUE, durationMillis(ElegantSnackbarDuration.Indefinite))
    }

    @Test
    fun shortDismissesBeforeLong() {
        assertTrue(
            durationMillis(ElegantSnackbarDuration.Short) <
                durationMillis(ElegantSnackbarDuration.Long),
        )
    }

    @Test
    fun defaultsConstantsMatchTheSpecifiedTiming() {
        assertEquals(4_000L, ElegantSnackbarDefaults.ShortDurationMillis)
        assertEquals(10_000L, ElegantSnackbarDefaults.LongDurationMillis)
        assertTrue(ElegantSnackbarDefaults.ShortDurationMillis < ElegantSnackbarDefaults.LongDurationMillis)
        assertEquals(
            ElegantMotion.standardDurationMillis.toLong(),
            ElegantSnackbarDefaults.AnimationDurationMillis.toLong(),
        )
    }

    @Test
    fun maxWidthKeepsTheSurfaceReadableOnWideHosts() {
        assertTrue(ElegantSnackbarDefaults.MaxSnackbarWidth > 0.dp)
        assertTrue(ElegantSnackbarDefaults.MaxSnackbarWidth <= 800.dp)
    }

    private fun contrastRatio(foreground: Color, background: Color): Float {
        fun luminance(color: Color): Float {
            fun channel(value: Float): Float {
                val linear = value
                return if (linear <= 0.03928f) {
                    linear / 12.92f
                } else {
                    ((linear + 0.055f) / 1.055f).pow(2.4f)
                }
            }
            return 0.2126f * channel(color.red) +
                0.7152f * channel(color.green) +
                0.0722f * channel(color.blue)
        }

        val lighter = maxOf(luminance(foreground), luminance(background))
        val darker = minOf(luminance(foreground), luminance(background))
        return (lighter + 0.05f) / (darker + 0.05f)
    }
}

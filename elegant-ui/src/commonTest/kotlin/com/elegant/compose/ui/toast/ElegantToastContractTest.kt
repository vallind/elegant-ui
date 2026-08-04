package com.elegant.compose.ui.toast

import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantToastContractTest {

    @Test
    fun lightThemeResolvesRaisedContainerAndTextRoles() {
        val colors = resolveToastColors(ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceRaised, colors.containerColor)
        assertEquals(ElegantLightColors.textPrimary, colors.titleColor)
        assertEquals(ElegantLightColors.textSecondary, colors.descriptionColor)
        assertEquals(ElegantLightColors.textTertiary, colors.closeIconColor)
    }

    @Test
    fun darkThemeResolvesRaisedContainerAndTextRoles() {
        val colors = resolveToastColors(ElegantDarkColors)

        assertEquals(ElegantDarkColors.surfaceRaised, colors.containerColor)
        assertEquals(ElegantDarkColors.textPrimary, colors.titleColor)
        assertEquals(ElegantDarkColors.textSecondary, colors.descriptionColor)
        assertEquals(ElegantDarkColors.textTertiary, colors.closeIconColor)
    }

    @Test
    fun colorsFollowTheActiveTheme() {
        assertNotEquals(
            resolveToastColors(ElegantLightColors),
            resolveToastColors(ElegantDarkColors),
        )
    }

    @Test
    fun shortAndLongDurationsMapToTheirDefaultsConstants() {
        assertEquals(
            ElegantToastDefaults.ShortDurationMillis,
            durationMillis(ElegantToastDuration.Short),
        )
        assertEquals(
            ElegantToastDefaults.LongDurationMillis,
            durationMillis(ElegantToastDuration.Long),
        )
    }

    @Test
    fun indefiniteDurationNeverAutoDismisses() {
        assertEquals(Long.MAX_VALUE, durationMillis(ElegantToastDuration.Indefinite))
    }

    @Test
    fun shortDismissesBeforeLong() {
        assertTrue(
            durationMillis(ElegantToastDuration.Short) <
                durationMillis(ElegantToastDuration.Long),
        )
    }

    @Test
    fun defaultsConstantsMatchTheSpecifiedTiming() {
        assertEquals(4_000L, ElegantToastDefaults.ShortDurationMillis)
        assertEquals(10_000L, ElegantToastDefaults.LongDurationMillis)
        assertTrue(ElegantToastDefaults.ShortDurationMillis < ElegantToastDefaults.LongDurationMillis)
        assertEquals(
            ElegantMotion.standardDurationMillis.toLong(),
            ElegantToastDefaults.AnimationDurationMillis.toLong(),
        )
    }

    @Test
    fun blankDescriptionResolvesToNull() {
        assertNull(resolveDescription(null))
        assertNull(resolveDescription(""))
        assertNull(resolveDescription("   "))
        assertNull(resolveDescription("\t\n"))
    }

    @Test
    fun nonBlankDescriptionIsKept() {
        assertEquals("Saved automatically", resolveDescription("Saved automatically"))
        assertEquals("  keeps surrounding spaces  ", resolveDescription("  keeps surrounding spaces  "))
    }

    @Test
    fun maxWidthKeepsTheSurfaceCompactOnWideHosts() {
        assertEquals(360.dp, ElegantToastDefaults.MaxWidth)
        assertTrue(ElegantToastDefaults.MaxWidth <= 600.dp)
    }
}

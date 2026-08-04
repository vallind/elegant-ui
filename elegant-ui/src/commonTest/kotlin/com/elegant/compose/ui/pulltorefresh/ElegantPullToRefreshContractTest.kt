package com.elegant.compose.ui.pulltorefresh

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantPullToRefreshContractTest {
    @Test
    fun publicMetricsRemainOnTheElegantDesignTokens() {
        assertEquals(40.dp, ElegantPullToRefreshDefaults.IndicatorSize)
        assertEquals(4.dp, ElegantPullToRefreshDefaults.IndicatorStrokeWidth)
        assertEquals(80.dp, ElegantPullToRefreshDefaults.PullThreshold)
        assertEquals(1200, ElegantPullToRefreshDefaults.RefreshDurationMillis)
        assertEquals(
            ElegantMotion.emphasizedDurationMillis,
            ElegantPullToRefreshDefaults.AnimationDurationMillis,
        )
    }

    @Test
    fun pullFractionIsClampedToTheUnitRange() {
        assertEquals(0f, pullFraction(-40f, 80f))
        assertEquals(0f, pullFraction(0f, 80f))
        assertEquals(0.5f, pullFraction(40f, 80f))
        assertEquals(1f, pullFraction(80f, 80f))
        assertEquals(1f, pullFraction(160f, 80f))
    }

    @Test
    fun pullFractionIsZeroForNonPositiveThresholds() {
        assertEquals(0f, pullFraction(80f, 0f))
        assertEquals(0f, pullFraction(80f, -80f))
    }

    @Test
    fun shouldTriggerRefreshOnlyAtOrPastTheThresholdFraction() {
        assertFalse(shouldTriggerRefresh(0f, 1f))
        assertFalse(shouldTriggerRefresh(0.99f, 1f))
        assertTrue(shouldTriggerRefresh(1f, 1f))
        assertTrue(shouldTriggerRefresh(1.5f, 1f))
        assertTrue(shouldTriggerRefresh(1f, 0.75f))
    }

    @Test
    fun stateMachineResolvesIdlePullingAndRefreshing() {
        assertEquals(
            RefreshPullState.Idle,
            resolvePullState(isRefreshing = false, pullFraction = 0f, released = false),
        )
        assertEquals(
            RefreshPullState.Idle,
            resolvePullState(isRefreshing = false, pullFraction = 0f, released = true),
        )
        assertEquals(
            RefreshPullState.Pulling,
            resolvePullState(isRefreshing = false, pullFraction = 0.5f, released = false),
        )
        assertEquals(
            RefreshPullState.Pulling,
            resolvePullState(isRefreshing = false, pullFraction = 0.5f, released = true),
        )
        assertEquals(
            RefreshPullState.Pulling,
            resolvePullState(isRefreshing = false, pullFraction = 1f, released = false),
        )
        assertEquals(
            RefreshPullState.Refreshing,
            resolvePullState(isRefreshing = false, pullFraction = 1f, released = true),
        )
        assertEquals(
            RefreshPullState.Refreshing,
            resolvePullState(isRefreshing = true, pullFraction = 0f, released = false),
        )
        assertEquals(
            RefreshPullState.Refreshing,
            resolvePullState(isRefreshing = true, pullFraction = 1f, released = true),
        )
    }

    @Test
    fun indicatorAlphaTracksTheVisualState() {
        assertEquals(0f, pullIndicatorAlpha(RefreshPullState.Idle, 1f))
        assertEquals(0.5f, pullIndicatorAlpha(RefreshPullState.Pulling, 0.5f))
        assertEquals(1f, pullIndicatorAlpha(RefreshPullState.Refreshing, 0.25f))
    }

    @Test
    fun colorsResolveSemanticThemeRoles() {
        val light = resolvePullToRefreshColors(ElegantLightColors)
        val dark = resolvePullToRefreshColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.interactivePrimary, light.indicatorColor)
        assertEquals(ElegantLightColors.borderDefault, light.trackColor)
        assertEquals(ElegantLightColors.surfaceDefault, light.scrimColor)
        assertEquals(ElegantDarkColors.interactivePrimary, dark.indicatorColor)
        assertEquals(ElegantDarkColors.borderDefault, dark.trackColor)
        assertEquals(ElegantDarkColors.surfaceDefault, dark.scrimColor)
        assertNotEquals(light, dark)
    }

    @Test
    fun customColorsRemainAnImmutableValueContract() {
        val colors = ElegantPullToRefreshColors(
            indicatorColor = Color.Red,
            trackColor = Color.Gray,
            scrimColor = Color.Black,
        )

        assertEquals(Color.Red, colors.indicatorColor)
        assertEquals(Color.Gray, colors.trackColor)
        assertEquals(Color.Black, colors.scrimColor)
        assertEquals(colors, colors.copy())
    }
}

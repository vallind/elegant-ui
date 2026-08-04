package com.elegant.compose.ui.scaffold

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantScaffoldContractTest {
    @Test
    fun lightThemeResolvesCanvasBackgroundWithPrimaryContent() {
        val colors = resolveScaffoldColors(ElegantLightColors)

        assertEquals(ElegantLightColors.backgroundCanvas, colors.backgroundColor)
        assertEquals(ElegantLightColors.textPrimary, colors.contentColor)
    }

    @Test
    fun darkThemeResolvesCanvasBackgroundWithPrimaryContent() {
        val colors = resolveScaffoldColors(ElegantDarkColors)

        assertEquals(ElegantDarkColors.backgroundCanvas, colors.backgroundColor)
        assertEquals(ElegantDarkColors.textPrimary, colors.contentColor)
    }

    @Test
    fun colorsFollowTheActiveTheme() {
        assertNotEquals(
            resolveScaffoldColors(ElegantLightColors),
            resolveScaffoldColors(ElegantDarkColors),
        )
    }

    @Test
    fun contentPaddingTracksBothMeasuredBarHeights() {
        assertEquals(ScaffoldPadding(0, 0), resolveContentPadding(0, 0))
        assertEquals(ScaffoldPadding(56, 0), resolveContentPadding(56, 0))
        assertEquals(ScaffoldPadding(0, 80), resolveContentPadding(0, 80))
        assertEquals(ScaffoldPadding(56, 80), resolveContentPadding(56, 80))
    }

    @Test
    fun contentPaddingIsExactNotCumulative() {
        val padding = resolveContentPadding(56, 80)

        assertEquals(56, padding.top)
        assertEquals(80, padding.bottom)
    }

    @Test
    fun contentPaddingWithNoBarsLeavesFullBleedContent() {
        assertEquals(ScaffoldPadding(0, 0), resolveContentPadding(0, 0))
    }

    @Test
    fun fabOffsetStacksTheMarginAboveTheBottomBar() {
        assertEquals(16, fabPadding(0, 16))
        assertEquals(32, fabPadding(16, 16))
        assertEquals(80, fabPadding(64, 16))
    }

    @Test
    fun fabOffsetUsesTheConfiguredMargin() {
        assertEquals(40, fabPadding(24, 16))
        assertEquals(44, fabPadding(24, 20))
    }

    @Test
    fun defaultMarginsStayOnTheElegantRhythm() {
        assertEquals(16.dp, ElegantScaffoldDefaults.FloatingActionButtonMargin)
        assertEquals(8.dp, ElegantScaffoldDefaults.SnackbarHostMargin)
    }

    @Test
    fun customColorsRemainAnImmutableValueContract() {
        val colors = ElegantScaffoldColors(
            backgroundColor = Color.Red,
            contentColor = Color.White,
        )

        assertEquals(Color.Red, colors.backgroundColor)
        assertEquals(Color.White, colors.contentColor)
        assertEquals(colors, colors.copy())
    }
}

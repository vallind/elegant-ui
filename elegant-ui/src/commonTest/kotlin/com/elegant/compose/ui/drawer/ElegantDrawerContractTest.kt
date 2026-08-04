package com.elegant.compose.ui.drawer

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ElegantDrawerContractTest {

    @Test
    fun colorsResolveFromTheActiveTheme() {
        val colors = resolveDrawerColors(ElegantLightColors)

        assertEquals(Color.Black.copy(alpha = ElegantDrawerDefaults.ScrimAlpha), colors.scrimColor)
        assertEquals(ElegantLightColors.surfaceRaised, colors.containerColor)
        assertEquals(ElegantLightColors.textPrimary, colors.contentColor)
    }

    @Test
    fun visibleDrawerRestsAtZeroInEveryDirection() {
        ElegantDrawerPlacement.entries.forEach { placement ->
            LayoutDirection.entries.forEach { direction ->
                assertEquals(
                    0f,
                    drawerSlideOffset(
                        visible = true,
                        widthPx = 320f,
                        placement = placement,
                        layoutDirection = direction,
                    ),
                    "$placement in $direction must rest fully visible",
                )
            }
        }
    }

    @Test
    fun hiddenDrawerRestsOffScreenAtItsLogicalEdge() {
        assertEquals(
            -320f,
            drawerSlideOffset(
                visible = false,
                widthPx = 320f,
                placement = ElegantDrawerPlacement.Start,
                layoutDirection = LayoutDirection.Ltr,
            ),
        )
        assertEquals(
            320f,
            drawerSlideOffset(
                visible = false,
                widthPx = 320f,
                placement = ElegantDrawerPlacement.Start,
                layoutDirection = LayoutDirection.Rtl,
            ),
        )
        assertEquals(
            320f,
            drawerSlideOffset(
                visible = false,
                widthPx = 320f,
                placement = ElegantDrawerPlacement.End,
                layoutDirection = LayoutDirection.Ltr,
            ),
        )
        assertEquals(
            -320f,
            drawerSlideOffset(
                visible = false,
                widthPx = 320f,
                placement = ElegantDrawerPlacement.End,
                layoutDirection = LayoutDirection.Rtl,
            ),
        )
    }

    @Test
    fun defaultsMatchDrawerContract() {
        assertEquals(280.dp, ElegantDrawerDefaults.Width)
        assertEquals(0.4f, ElegantDrawerDefaults.ScrimAlpha)
        assertEquals(ElegantMotion.emphasizedDurationMillis, ElegantDrawerDefaults.AnimationDurationMillis)
    }
}

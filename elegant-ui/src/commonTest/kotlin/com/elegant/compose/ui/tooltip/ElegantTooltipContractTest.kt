package com.elegant.compose.ui.tooltip

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ElegantTooltipContractTest {

    private val anchor = IntRect(left = 200, top = 200, right = 320, bottom = 260)
    private val tooltipSize = IntSize(width = 120, height = 40)
    private val window = IntSize(width = 1000, height = 800)
    private val offset = 8

    @Test
    fun placementsPositionAroundAnchorInLtr() {
        assertEquals(
            IntOffset(x = 200, y = 152),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.Top,
                anchorBounds = anchor,
                tooltipSize = tooltipSize,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 200, y = 268),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.Bottom,
                anchorBounds = anchor,
                tooltipSize = tooltipSize,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 72, y = 210),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.Start,
                anchorBounds = anchor,
                tooltipSize = tooltipSize,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 328, y = 210),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.End,
                anchorBounds = anchor,
                tooltipSize = tooltipSize,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
    }

    @Test
    fun startAndEndMirrorInRtl() {
        fun position(placement: ElegantTooltipPlacement, direction: LayoutDirection) =
            tooltipPositionFor(
                placement = placement,
                anchorBounds = anchor,
                tooltipSize = tooltipSize,
                offsetPx = offset,
                layoutDirection = direction,
                windowSize = window,
            )

        assertEquals(
            position(ElegantTooltipPlacement.End, LayoutDirection.Ltr),
            position(ElegantTooltipPlacement.Start, LayoutDirection.Rtl),
        )
        assertEquals(
            position(ElegantTooltipPlacement.Start, LayoutDirection.Ltr),
            position(ElegantTooltipPlacement.End, LayoutDirection.Rtl),
        )
    }

    @Test
    fun positionsClampIntoWindowEdges() {
        val cornerAnchor = IntRect(left = 0, top = 0, right = 40, bottom = 40)
        val edgeAnchor = IntRect(left = 380, top = 280, right = 400, bottom = 300)
        val smallWindow = IntSize(width = 400, height = 300)
        val bigTooltip = IntSize(width = 100, height = 50)

        assertEquals(
            IntOffset(x = 0, y = 0),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.Top,
                anchorBounds = cornerAnchor,
                tooltipSize = bigTooltip,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = smallWindow,
            ),
        )
        assertEquals(
            IntOffset(x = 300, y = 222),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.Top,
                anchorBounds = edgeAnchor,
                tooltipSize = bigTooltip,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = smallWindow,
            ),
        )
        assertEquals(
            IntOffset(x = 300, y = 250),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.Bottom,
                anchorBounds = edgeAnchor,
                tooltipSize = bigTooltip,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = smallWindow,
            ),
        )
        assertEquals(
            IntOffset(x = 272, y = 250),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.Start,
                anchorBounds = edgeAnchor,
                tooltipSize = bigTooltip,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = smallWindow,
            ),
        )
        assertEquals(
            IntOffset(x = 300, y = 250),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.End,
                anchorBounds = edgeAnchor,
                tooltipSize = bigTooltip,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = smallWindow,
            ),
        )
    }

    @Test
    fun positionsClampWhenTooltipExceedsWindow() {
        val tinyWindow = IntSize(width = 100, height = 100)
        val hugeTooltip = IntSize(width = 200, height = 150)

        assertEquals(
            IntOffset(x = 0, y = 0),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.Top,
                anchorBounds = IntRect(left = 20, top = 20, right = 60, bottom = 60),
                tooltipSize = hugeTooltip,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = tinyWindow,
            ),
        )
    }

    @Test
    fun offsetIncreasesGapOnEveryPlacement() {
        val wideGap = 16

        assertEquals(
            IntOffset(x = 200, y = 144),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.Top,
                anchorBounds = anchor,
                tooltipSize = tooltipSize,
                offsetPx = wideGap,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 200, y = 276),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.Bottom,
                anchorBounds = anchor,
                tooltipSize = tooltipSize,
                offsetPx = wideGap,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 64, y = 210),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.Start,
                anchorBounds = anchor,
                tooltipSize = tooltipSize,
                offsetPx = wideGap,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 336, y = 210),
            tooltipPositionFor(
                placement = ElegantTooltipPlacement.End,
                anchorBounds = anchor,
                tooltipSize = tooltipSize,
                offsetPx = wideGap,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
    }

    @Test
    fun delaysClampNegativesToZeroAndPreservePositives() {
        assertEquals(600L to 100L, resolveTooltipDelays(show = 600L, hide = 100L))
        assertEquals(0L to 0L, resolveTooltipDelays(show = -5L, hide = -1L))
        assertEquals(0L to 0L, resolveTooltipDelays(show = -10L, hide = 0L))
        assertEquals(250L to 0L, resolveTooltipDelays(show = 250L, hide = -20L))
    }

    @Test
    fun defaultsMatchTooltipContract() {
        assertEquals(600L, ElegantTooltipDefaults.DefaultShowDelayMillis)
        assertEquals(100L, ElegantTooltipDefaults.DefaultHideDelayMillis)
        assertEquals(8.dp, ElegantTooltipDefaults.DefaultOffset)
        assertEquals(280.dp, ElegantTooltipDefaults.MaxWidth)
        assertEquals(
            600L to 100L,
            resolveTooltipDelays(
                show = ElegantTooltipDefaults.DefaultShowDelayMillis,
                hide = ElegantTooltipDefaults.DefaultHideDelayMillis,
            ),
        )
    }
}

package com.elegant.compose.ui.popover

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ElegantPopoverContractTest {

    private val anchor = IntRect(left = 200, top = 200, right = 320, bottom = 260)
    private val popoverSize = IntSize(width = 120, height = 40)
    private val window = IntSize(width = 1000, height = 800)
    private val offset = 8

    @Test
    fun placementsPositionAroundAnchorInLtr() {
        assertEquals(
            IntOffset(x = 200, y = 152),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.Top,
                anchorBounds = anchor,
                popoverSize = popoverSize,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 200, y = 268),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.Bottom,
                anchorBounds = anchor,
                popoverSize = popoverSize,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 72, y = 210),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.Start,
                anchorBounds = anchor,
                popoverSize = popoverSize,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 328, y = 210),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.End,
                anchorBounds = anchor,
                popoverSize = popoverSize,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
    }

    @Test
    fun startAndEndMirrorInRtl() {
        fun position(placement: ElegantPopoverPlacement, direction: LayoutDirection) =
            popoverPositionFor(
                placement = placement,
                anchorBounds = anchor,
                popoverSize = popoverSize,
                offsetPx = offset,
                layoutDirection = direction,
                windowSize = window,
            )

        assertEquals(
            position(ElegantPopoverPlacement.End, LayoutDirection.Ltr),
            position(ElegantPopoverPlacement.Start, LayoutDirection.Rtl),
        )
        assertEquals(
            position(ElegantPopoverPlacement.Start, LayoutDirection.Ltr),
            position(ElegantPopoverPlacement.End, LayoutDirection.Rtl),
        )
    }

    @Test
    fun positionsClampIntoWindowEdges() {
        val cornerAnchor = IntRect(left = 0, top = 0, right = 40, bottom = 40)
        val edgeAnchor = IntRect(left = 380, top = 280, right = 400, bottom = 300)
        val smallWindow = IntSize(width = 400, height = 300)
        val bigPopover = IntSize(width = 100, height = 50)

        assertEquals(
            IntOffset(x = 0, y = 0),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.Top,
                anchorBounds = cornerAnchor,
                popoverSize = bigPopover,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = smallWindow,
            ),
        )
        assertEquals(
            IntOffset(x = 300, y = 222),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.Top,
                anchorBounds = edgeAnchor,
                popoverSize = bigPopover,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = smallWindow,
            ),
        )
        assertEquals(
            IntOffset(x = 300, y = 250),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.Bottom,
                anchorBounds = edgeAnchor,
                popoverSize = bigPopover,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = smallWindow,
            ),
        )
        assertEquals(
            IntOffset(x = 272, y = 250),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.Start,
                anchorBounds = edgeAnchor,
                popoverSize = bigPopover,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = smallWindow,
            ),
        )
        assertEquals(
            IntOffset(x = 300, y = 250),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.End,
                anchorBounds = edgeAnchor,
                popoverSize = bigPopover,
                offsetPx = offset,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = smallWindow,
            ),
        )
    }

    @Test
    fun positionsClampWhenPopoverExceedsWindow() {
        val tinyWindow = IntSize(width = 100, height = 100)
        val hugePopover = IntSize(width = 200, height = 150)

        assertEquals(
            IntOffset(x = 0, y = 0),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.Top,
                anchorBounds = IntRect(left = 20, top = 20, right = 60, bottom = 60),
                popoverSize = hugePopover,
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
            popoverPositionFor(
                placement = ElegantPopoverPlacement.Top,
                anchorBounds = anchor,
                popoverSize = popoverSize,
                offsetPx = wideGap,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 200, y = 276),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.Bottom,
                anchorBounds = anchor,
                popoverSize = popoverSize,
                offsetPx = wideGap,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 64, y = 210),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.Start,
                anchorBounds = anchor,
                popoverSize = popoverSize,
                offsetPx = wideGap,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 336, y = 210),
            popoverPositionFor(
                placement = ElegantPopoverPlacement.End,
                anchorBounds = anchor,
                popoverSize = popoverSize,
                offsetPx = wideGap,
                layoutDirection = LayoutDirection.Ltr,
                windowSize = window,
            ),
        )
    }

    @Test
    fun colorsResolveFromThemeRoles() {
        val colors = resolvePopoverColors(ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceRaised, colors.containerColor)
        assertEquals(ElegantLightColors.textPrimary, colors.contentColor)
        assertEquals(ElegantLightColors.borderDefault, colors.borderColor)
    }

    @Test
    fun defaultsMatchPopoverContract() {
        assertEquals(320.dp, ElegantPopoverDefaults.MaxWidth)
        assertEquals(8.dp, ElegantPopoverDefaults.Offset)
        assertEquals(ElegantMotion.fastDurationMillis, ElegantPopoverDefaults.AnimationDurationMillis)
    }
}

package com.elegant.compose.ui.cascadingmenu

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ElegantCascadingMenuContractTest {

    private val copy = ElegantCascadingMenuItem(text = "Copy")
    private val paste = ElegantCascadingMenuItem(text = "Paste")
    private val edit = ElegantCascadingMenuItem(text = "Edit", children = listOf(copy, paste))
    private val image = ElegantCascadingMenuItem(text = "Image")
    private val table = ElegantCascadingMenuItem(text = "Table")
    private val insert = ElegantCascadingMenuItem(text = "Insert", children = listOf(image, table))
    private val tree = listOf(edit, insert)

    @Test
    fun colorsResolveFromThemeRoles() {
        val colors = resolveCascadingMenuColors(ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceRaised, colors.containerColor)
        assertEquals(ElegantLightColors.textPrimary, colors.contentColor)
        assertEquals(ElegantLightColors.textTertiary, colors.disabledContentColor)
        assertEquals(ElegantLightColors.surfaceHover, colors.hoveredContainerColor)
        assertEquals(ElegantLightColors.borderDefault, colors.borderColor)
    }

    @Test
    fun hasChildrenDistinguishesLeafFromParent() {
        assertFalse(hasChildren(copy))
        assertFalse(hasChildren(ElegantCascadingMenuItem(text = "Empty", children = emptyList())))
        assertTrue(hasChildren(edit))
    }

    @Test
    fun childAtFollowsValidPath() {
        assertEquals(listOf(copy, paste), childAt(tree, listOf(0)))
        assertEquals(listOf(image, table), childAt(tree, listOf(1)))
        assertEquals(emptyList(), childAt(tree, listOf(0, 0)))
        assertEquals(tree, childAt(tree, emptyList()))
    }

    @Test
    fun childAtReturnsEmptyWhenPathWalksOutOfRange() {
        assertEquals(emptyList(), childAt(tree, listOf(5)))
        assertEquals(emptyList(), childAt(tree, listOf(0, 5)))
        assertEquals(emptyList(), childAt(tree, listOf(0, 1, 2)))
        assertEquals(emptyList(), childAt(emptyList(), listOf(0)))
    }

    @Test
    fun submenuVisibleRequiresValidParentAtPathHead() {
        assertTrue(submenuVisible(tree, listOf(0)))
        assertFalse(submenuVisible(tree, listOf(1, 0)))
        assertFalse(submenuVisible(tree, emptyList()))
        assertFalse(submenuVisible(tree, listOf(5)))
        assertFalse(submenuVisible(tree, listOf(0, 5)))
        assertFalse(submenuVisible(listOf(copy), listOf(0)))
        assertFalse(submenuVisible(emptyList(), listOf(0)))
    }

    @Test
    fun resolveSubmenuPathLeavesLeafUnchanged() {
        assertEquals(listOf(0), resolveSubmenuPath(copy, path = listOf(0), index = 2))
        assertEquals(listOf(1, 1), resolveSubmenuPath(paste, path = listOf(1, 1), index = 3))
    }

    @Test
    fun resolveSubmenuPathOpensParentWhenNotOpen() {
        assertEquals(listOf(0), resolveSubmenuPath(edit, path = emptyList(), index = 0))
        assertEquals(listOf(0), resolveSubmenuPath(edit, path = listOf(1), index = 0))
        assertEquals(listOf(0, 1), resolveSubmenuPath(edit, path = listOf(0, 1), index = 0))
    }

    @Test
    fun resolveSubmenuPathKeepsChainWhenParentAlreadyOpen() {
        assertEquals(listOf(0, 1), resolveSubmenuPath(edit, path = listOf(0, 1), index = 0))
        assertEquals(listOf(1, 0), resolveSubmenuPath(insert, path = listOf(1, 0), index = 1))
    }

    @Test
    fun leafPathResolvesAncestorChainEndingAtLeaf() {
        assertEquals(listOf(edit, copy), leafPath(tree, listOf(0, 0)))
        assertEquals(listOf(edit, paste), leafPath(tree, listOf(0, 1)))
        assertEquals(listOf(insert, image), leafPath(tree, listOf(1, 0)))
        assertEquals(listOf(edit), leafPath(tree, listOf(0)))
        assertEquals(emptyList(), leafPath(tree, emptyList()))
    }

    @Test
    fun leafPathReturnsEmptyWhenPathWalksOutOfRange() {
        assertEquals(emptyList(), leafPath(tree, listOf(5)))
        assertEquals(emptyList(), leafPath(tree, listOf(0, 5)))
        assertEquals(emptyList(), leafPath(tree, listOf(0, 1, 2)))
        assertEquals(emptyList(), leafPath(emptyList(), listOf(0)))
    }

    @Test
    fun submenuOffsetSitsBesideParentEndEdge() {
        assertEquals(
            IntOffset(x = 114, y = 20),
            submenuOffsetPosition(
                parentItemBounds = IntRect(left = 10, top = 20, right = 110, bottom = 60),
                submenuSize = IntSize(width = 100, height = 50),
                offsetPx = 4,
                windowSize = IntSize(width = 400, height = 300),
            ),
        )
    }

    @Test
    fun submenuOffsetClampsIntoWindowEdges() {
        assertEquals(
            IntOffset(x = 250, y = 40),
            submenuOffsetPosition(
                parentItemBounds = IntRect(left = 300, top = 40, right = 400, bottom = 80),
                submenuSize = IntSize(width = 150, height = 50),
                offsetPx = 4,
                windowSize = IntSize(width = 400, height = 300),
            ),
        )
        assertEquals(
            IntOffset(x = 114, y = 220),
            submenuOffsetPosition(
                parentItemBounds = IntRect(left = 10, top = 260, right = 110, bottom = 300),
                submenuSize = IntSize(width = 100, height = 80),
                offsetPx = 4,
                windowSize = IntSize(width = 400, height = 300),
            ),
        )
    }

    @Test
    fun submenuOffsetClampsWhenSubmenuExceedsWindow() {
        assertEquals(
            IntOffset(x = 0, y = 0),
            submenuOffsetPosition(
                parentItemBounds = IntRect(left = 0, top = 0, right = 100, bottom = 40),
                submenuSize = IntSize(width = 500, height = 400),
                offsetPx = 4,
                windowSize = IntSize(width = 400, height = 300),
            ),
        )
    }

    @Test
    fun defaultsMatchCascadingMenuContract() {
        assertEquals(180.dp, ElegantCascadingMenuDefaults.MinWidth)
        assertEquals(320.dp, ElegantCascadingMenuDefaults.MaxHeight)
        assertEquals(40.dp, ElegantCascadingMenuDefaults.ItemHeight)
        assertEquals(16.dp, ElegantCascadingMenuDefaults.HorizontalPadding)
        assertEquals(4.dp, ElegantCascadingMenuDefaults.SubmenuOffset)
        assertEquals(
            ElegantMotion.fastDurationMillis,
            ElegantCascadingMenuDefaults.AnimationDurationMillis,
        )
    }
}

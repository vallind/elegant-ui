package com.elegant.compose.ui.pagination

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantPaginationContractTest {

    @Test
    fun colorsResolveFromThemeRoles() {
        val light = resolvePaginationColors(ElegantLightColors)
        val dark = resolvePaginationColors(ElegantDarkColors)

        assertEquals(Color.Transparent, light.containerColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.selectedItemColor)
        assertEquals(ElegantLightColors.textInverse, light.selectedContentColor)
        assertEquals(ElegantLightColors.textPrimary, light.itemColor)
        assertEquals(ElegantLightColors.surfaceHover, light.hoveredItemColor)
        assertEquals(ElegantLightColors.backgroundSubtle, light.pressedItemColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledItemColor)
        assertNotEquals(light, dark, "colors must follow the active theme")
    }

    @Test
    fun defaultsExposeStableMetrics() {
        assertEquals(36.dp, ElegantPaginationDefaults.ItemSize)
        assertEquals(4.dp, ElegantPaginationDefaults.ItemGap)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantPaginationDefaults.AnimationDurationMillis,
        )
        assertEquals(1, ElegantPaginationDefaults.SiblingCount)
    }

    @Test
    fun pageCountZeroOrNegativeRendersNothing() {
        assertEquals(emptyList<ElegantPaginationItem>(), paginationItems(1, 0, 1))
        assertEquals(emptyList<ElegantPaginationItem>(), paginationItems(1, -3, 1))
        assertEquals(emptyList<ElegantPaginationItem>(), paginationItems(5, 0, 2))
    }

    @Test
    fun singlePageKeepsCurrentPageBetweenDisabledNavigation() {
        assertEquals(
            listOf(
                ElegantPaginationItem.Previous,
                ElegantPaginationItem.Page(number = 1, enabled = false),
                ElegantPaginationItem.Next,
            ),
            paginationItems(1, 1, 1),
        )
    }

    @Test
    fun twoPagesNeverCollapseIntoEllipsis() {
        assertEquals(
            listOf(
                ElegantPaginationItem.Previous,
                ElegantPaginationItem.Page(number = 1, enabled = false),
                ElegantPaginationItem.Page(number = 2, enabled = true),
                ElegantPaginationItem.Next,
            ),
            paginationItems(1, 2, 1),
        )
        assertEquals(
            listOf(
                ElegantPaginationItem.Previous,
                ElegantPaginationItem.Page(number = 1, enabled = true),
                ElegantPaginationItem.Page(number = 2, enabled = false),
                ElegantPaginationItem.Next,
            ),
            paginationItems(2, 2, 3),
        )
    }

    @Test
    fun firstPageShowsOnlyTrailingEllipsis() {
        assertEquals(
            listOf(
                ElegantPaginationItem.Previous,
                ElegantPaginationItem.Page(number = 1, enabled = false),
                ElegantPaginationItem.Page(number = 2, enabled = true),
                ElegantPaginationItem.Ellipsis,
                ElegantPaginationItem.Page(number = 5, enabled = true),
                ElegantPaginationItem.Next,
            ),
            paginationItems(1, 5, 1),
        )
    }

    @Test
    fun lastPageShowsOnlyLeadingEllipsis() {
        assertEquals(
            listOf(
                ElegantPaginationItem.Previous,
                ElegantPaginationItem.Page(number = 1, enabled = true),
                ElegantPaginationItem.Ellipsis,
                ElegantPaginationItem.Page(number = 4, enabled = true),
                ElegantPaginationItem.Page(number = 5, enabled = false),
                ElegantPaginationItem.Next,
            ),
            paginationItems(5, 5, 1),
        )
    }

    @Test
    fun middlePageShowsEllipsisOnBothWideGaps() {
        assertEquals(
            listOf(
                ElegantPaginationItem.Previous,
                ElegantPaginationItem.Page(number = 1, enabled = true),
                ElegantPaginationItem.Ellipsis,
                ElegantPaginationItem.Page(number = 4, enabled = true),
                ElegantPaginationItem.Page(number = 5, enabled = false),
                ElegantPaginationItem.Page(number = 6, enabled = true),
                ElegantPaginationItem.Ellipsis,
                ElegantPaginationItem.Page(number = 10, enabled = true),
                ElegantPaginationItem.Next,
            ),
            paginationItems(5, 10, 1),
        )
    }

    @Test
    fun zeroSiblingsKeepOnlyNeighboringPage() {
        assertEquals(
            listOf(
                ElegantPaginationItem.Previous,
                ElegantPaginationItem.Page(number = 1, enabled = true),
                ElegantPaginationItem.Ellipsis,
                ElegantPaginationItem.Page(number = 5, enabled = false),
                ElegantPaginationItem.Ellipsis,
                ElegantPaginationItem.Page(number = 10, enabled = true),
                ElegantPaginationItem.Next,
            ),
            paginationItems(5, 10, 0),
        )
    }

    @Test
    fun wideSiblingWindowFoldsEllipsesIntoPages() {
        assertEquals(
            listOf(
                ElegantPaginationItem.Previous,
                ElegantPaginationItem.Page(number = 1, enabled = true),
                ElegantPaginationItem.Page(number = 2, enabled = true),
                ElegantPaginationItem.Page(number = 3, enabled = true),
                ElegantPaginationItem.Page(number = 4, enabled = true),
                ElegantPaginationItem.Page(number = 5, enabled = false),
                ElegantPaginationItem.Page(number = 6, enabled = true),
                ElegantPaginationItem.Page(number = 7, enabled = true),
                ElegantPaginationItem.Page(number = 8, enabled = true),
                ElegantPaginationItem.Ellipsis,
                ElegantPaginationItem.Page(number = 10, enabled = true),
                ElegantPaginationItem.Next,
            ),
            paginationItems(5, 10, 3),
        )
    }

    @Test
    fun pageIsCoercedIntoTheValidRange() {
        assertEquals(paginationItems(1, 10, 1), paginationItems(-5, 10, 1))
        assertEquals(paginationItems(10, 10, 1), paginationItems(99, 10, 1))
    }

    @Test
    fun firstAndLastPagesAreAlwaysPresent() {
        listOf(1, 3, 5, 9, 10).forEach { page ->
            val pages = paginationItems(page, 10, 1)
                .filterIsInstance<ElegantPaginationItem.Page>()
                .map { it.number }
            assertEquals(1, pages.first())
            assertEquals(10, pages.last())
        }
    }

    @Test
    fun onlyTheCurrentPageItemIsDisabled() {
        listOf(1, 4, 7, 10).forEach { page ->
            val disabledPages = paginationItems(page, 10, 1)
                .filterIsInstance<ElegantPaginationItem.Page>()
                .filterNot { it.enabled }
            assertEquals(1, disabledPages.size)
            assertEquals(page, disabledPages.single().number)
        }
    }

    @Test
    fun pagesNeverRepeatAcrossTheWindow() {
        listOf(1, 2, 5, 9, 10, 12).forEach { page ->
            listOf(0, 1, 3).forEach { siblingCount ->
                val numbers = paginationItems(page, 12, siblingCount)
                    .filterIsInstance<ElegantPaginationItem.Page>()
                    .map { it.number }
                assertEquals(
                    numbers.size,
                    numbers.toSet().size,
                    "page $page with siblingCount $siblingCount must not repeat pages",
                )
            }
        }
    }

    @Test
    fun ellipsisAppearsAtMostOncePerCollapsedGap() {
        listOf(1, 2, 5, 9, 12).forEach { page ->
            val ellipses = paginationItems(page, 12, 2).count {
                it == ElegantPaginationItem.Ellipsis
            }
            assertTrue(ellipses <= 2, "page $page must collapse each gap into a single ellipsis")
        }
    }

    @Test
    fun canGoPreviousOnlyFromTheSecondPageOnwards() {
        assertFalse(canGoPrevious(1))
        assertFalse(canGoPrevious(0))
        assertFalse(canGoPrevious(-2))
        assertTrue(canGoPrevious(2))
        assertTrue(canGoPrevious(8))
    }

    @Test
    fun canGoNextOnlyBeforeTheLastPage() {
        assertTrue(canGoNext(1, 5))
        assertTrue(canGoNext(4, 5))
        assertFalse(canGoNext(5, 5))
        assertFalse(canGoNext(5, 0))
        assertFalse(canGoNext(5, -2))
        assertFalse(canGoNext(6, 5))
    }

    @Test
    fun siblingCountIsCoercedToNonNegative() {
        assertEquals(0, resolveSiblingCount(-1))
        assertEquals(0, resolveSiblingCount(0))
        assertEquals(3, resolveSiblingCount(3))
    }

    @Test
    fun itemVisualsFollowDisabledSelectedPressedHoveredRestingPrecedence() {
        val colors = resolvePaginationColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            selected: Boolean = false,
            pressed: Boolean = false,
            hovered: Boolean = false,
        ) = resolvePaginationItemVisuals(
            colors = colors,
            enabled = enabled,
            selected = selected,
            pressed = pressed,
            hovered = hovered,
        )

        assertEquals(Color.Transparent, visuals().container)
        assertEquals(colors.itemColor, visuals().content)
        assertEquals(colors.hoveredItemColor, visuals(hovered = true).container)
        assertEquals(colors.pressedItemColor, visuals(pressed = true).container)
        assertEquals(colors.selectedItemColor, visuals(selected = true).container)
        assertEquals(colors.selectedContentColor, visuals(selected = true).content)
        assertEquals(
            colors.selectedItemColor,
            visuals(selected = true, pressed = true).container,
        )
        assertEquals(colors.disabledItemColor, visuals(enabled = false).content)
        assertEquals(Color.Transparent, visuals(enabled = false).container)
        assertEquals(colors.disabledItemColor, visuals(enabled = false, selected = true).content)
    }
}

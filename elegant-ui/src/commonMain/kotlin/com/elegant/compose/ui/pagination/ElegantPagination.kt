package com.elegant.compose.ui.pagination

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantTheme

/** One slot of a pagination row; [Page] carries its number and enabled state. */
internal sealed interface ElegantPaginationItem {
    /** Navigates one page towards the start of the sequence. */
    data object Previous : ElegantPaginationItem

    /** Navigates one page towards the end of the sequence. */
    data object Next : ElegantPaginationItem

    /** A selectable page number; [enabled] is false for the current page. */
    data class Page(
        val number: Int,
        val enabled: Boolean = true,
    ) : ElegantPaginationItem

    /** A decorative marker for a collapsed gap of pages. */
    data object Ellipsis : ElegantPaginationItem
}

/** Pointing direction of a pagination chevron. */
internal enum class PaginationChevronDirection {
    /** Points towards the start of the sequence. */
    Previous,

    /** Points towards the end of the sequence. */
    Next,
}

/**
 * Theme-aware colors used by [ElegantPagination].
 *
 * Use [ElegantPaginationDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor background of the pagination row.
 * @property selectedItemColor background of the current page item.
 * @property selectedContentColor content color of the current page item.
 * @property itemColor resting content color of interactive items and chevrons.
 * @property hoveredItemColor background of an item under the pointer.
 * @property pressedItemColor background of an item while pressed.
 * @property disabledItemColor content color of items while interaction is disabled.
 */
@Immutable
public data class ElegantPaginationColors(
    val containerColor: Color,
    val selectedItemColor: Color,
    val selectedContentColor: Color,
    val itemColor: Color,
    val hoveredItemColor: Color = itemColor,
    val pressedItemColor: Color = itemColor,
    val disabledItemColor: Color = itemColor,
)

/** Theme-aware defaults for [ElegantPagination]. */
public object ElegantPaginationDefaults {
    /** Edge length of every square page item and circular chevron button. */
    public val ItemSize: Dp = 36.dp

    /** Spacing between adjacent items. */
    public val ItemGap: Dp = 4.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Default number of pages shown on each side of the current page. */
    public const val SiblingCount: Int = 1

    /** Returns theme-aware colors for a pagination row. */
    @Composable
    public fun colors(): ElegantPaginationColors = resolvePaginationColors(ElegantTheme.colors)
}

/**
 * Renders a compact page-navigation row for a bounded page sequence.
 *
 * The row always shows the first and last page plus the pages within [siblingCount] of [page],
 * collapsing each collapsed gap into a single ellipsis. The current page renders with the selected
 * colors and does not invoke [onPageChange]; the previous and next chevron buttons are disabled at
 * the first and last page. A non-positive [pageCount] renders nothing. Page items expose a
 * [Role.Button] with the selected state, and chevron buttons expose a [Role.Button] with the
 * disabled state; item colors animate across hover, press, and focus changes.
 *
 * @param page current page number; coerced into `1..pageCount`.
 * @param onPageChange callback invoked with the target page when an item is activated.
 * @param pageCount total number of pages; non-positive values render nothing.
 * @param modifier modifier applied once to the pagination row.
 * @param enabled whether user interaction is accepted.
 * @param siblingCount number of pages shown on each side of the current page.
 * @param colors theme-aware colors for the row, items, and the selected page.
 */
@Composable
public fun ElegantPagination(
    page: Int,
    onPageChange: (Int) -> Unit,
    pageCount: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    siblingCount: Int = ElegantPaginationDefaults.SiblingCount,
    colors: ElegantPaginationColors = ElegantPaginationDefaults.colors(),
) {
    if (pageCount <= 0) {
        Box(modifier = modifier)
        return
    }
    val targetPage = page.coerceIn(1, pageCount)
    val items = remember(page, pageCount, siblingCount) {
        paginationItems(page = targetPage, pageCount = pageCount, siblingCount = siblingCount)
    }

    Row(
        modifier = modifier.background(
            color = colors.containerColor,
            shape = RoundedCornerShape(ElegantRadius.sm),
        ),
        horizontalArrangement = Arrangement.spacedBy(
            space = ElegantPaginationDefaults.ItemGap,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEach { item ->
            when (item) {
                ElegantPaginationItem.Previous -> PaginationNavButton(
                    direction = PaginationChevronDirection.Previous,
                    enabled = enabled && canGoPrevious(targetPage),
                    colors = colors,
                    onClick = { onPageChange(targetPage - 1) },
                )

                ElegantPaginationItem.Next -> PaginationNavButton(
                    direction = PaginationChevronDirection.Next,
                    enabled = enabled && canGoNext(targetPage, pageCount),
                    colors = colors,
                    onClick = { onPageChange(targetPage + 1) },
                )

                is ElegantPaginationItem.Page -> PaginationPageButton(
                    number = item.number,
                    selected = item.number == targetPage,
                    enabled = enabled,
                    clickable = enabled && item.enabled,
                    colors = colors,
                    onClick = { onPageChange(item.number) },
                )

                ElegantPaginationItem.Ellipsis -> PaginationEllipsis(colors = colors)
            }
        }
    }
}

@Composable
private fun PaginationPageButton(
    number: Int,
    selected: Boolean,
    enabled: Boolean,
    clickable: Boolean,
    colors: ElegantPaginationColors,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val visuals = resolvePaginationItemVisuals(
        colors = colors,
        enabled = enabled,
        selected = selected,
        pressed = pressed,
        hovered = hovered,
    )
    val shape = RoundedCornerShape(ElegantRadius.sm)
    val animatedContainer = animatedItemColor(visuals.container)
    val animatedContent = animatedItemColor(visuals.content)

    Box(
        modifier = Modifier
            .size(ElegantPaginationDefaults.ItemSize)
            .semantics(mergeDescendants = true) {
                role = Role.Button
                this.selected = selected
            }
            .clickable(
                enabled = clickable,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .clip(shape)
            .background(animatedContainer)
            .indication(
                interactionSource = interactionSource,
                indication = ripple(color = animatedContent),
            )
            .then(
                if (focused) {
                    Modifier.border(
                        width = 2.dp,
                        color = ElegantTheme.colors.focusRing,
                        shape = shape,
                    )
                } else {
                    Modifier
                },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = number.toString(),
            color = animatedContent,
            style = ElegantTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun PaginationNavButton(
    direction: PaginationChevronDirection,
    enabled: Boolean,
    colors: ElegantPaginationColors,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val visuals = resolvePaginationItemVisuals(
        colors = colors,
        enabled = enabled,
        selected = false,
        pressed = pressed,
        hovered = hovered,
    )
    val animatedContainer = animatedItemColor(visuals.container)
    val animatedContent = animatedItemColor(visuals.content)

    Box(
        modifier = Modifier
            .size(ElegantPaginationDefaults.ItemSize)
            .semantics(mergeDescendants = true) {
                role = Role.Button
                if (!enabled) disabled()
            }
            .clickable(
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .clip(CircleShape)
            .background(animatedContainer)
            .indication(
                interactionSource = interactionSource,
                indication = ripple(color = animatedContent),
            )
            .then(
                if (focused) {
                    Modifier.border(
                        width = 2.dp,
                        color = ElegantTheme.colors.focusRing,
                        shape = CircleShape,
                    )
                } else {
                    Modifier
                },
            ),
        contentAlignment = Alignment.Center,
    ) {
        PaginationChevron(direction = direction, color = animatedContent)
    }
}

@Composable
private fun PaginationEllipsis(colors: ElegantPaginationColors) {
    Box(
        modifier = Modifier.size(ElegantPaginationDefaults.ItemSize),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "…",
            color = colors.disabledItemColor,
            style = ElegantTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun PaginationChevron(
    direction: PaginationChevronDirection,
    color: Color,
) {
    Canvas(modifier = Modifier.size(ChevronSize)) {
        val strokeWidth = 2.dp.toPx()
        val midX = size.width / 2f
        val midY = size.height / 2f
        val armX = size.width * 0.26f
        val armY = size.height * 0.26f
        val tipX = when (direction) {
            PaginationChevronDirection.Previous -> midX - armX
            PaginationChevronDirection.Next -> midX + armX
        }
        drawLine(
            color = color,
            start = Offset(tipX, midY - armY),
            end = Offset(midX, midY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
        drawLine(
            color = color,
            start = Offset(midX, midY),
            end = Offset(tipX, midY + armY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
    }
}

@Composable
private fun animatedItemColor(target: Color): Color {
    val animated by animateColorAsState(
        targetValue = target,
        animationSpec = tween(
            durationMillis = ElegantPaginationDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantPaginationItemColor",
    )
    return animated
}


/** Resolves theme-aware pagination colors from [themeColors]. */
internal fun resolvePaginationColors(themeColors: ElegantColors): ElegantPaginationColors = ElegantPaginationColors(
    containerColor = Color.Transparent,
    selectedItemColor = themeColors.interactivePrimary,
    selectedContentColor = themeColors.textInverse,
    itemColor = themeColors.textPrimary,
    hoveredItemColor = themeColors.surfaceHover,
    pressedItemColor = themeColors.backgroundSubtle,
    disabledItemColor = themeColors.textTertiary,
)

/**
 * Computes the pagination row items for [page] within `1..pageCount`.
 *
 * Always includes [ElegantPaginationItem.Previous], [ElegantPaginationItem.Next], the first page,
 * the last page, and the pages within [siblingCount] of [page]; each collapsed gap becomes a single
 * [ElegantPaginationItem.Ellipsis]. Returns an empty list when [pageCount] is non-positive.
 */
internal fun paginationItems(
    page: Int,
    pageCount: Int,
    siblingCount: Int,
): List<ElegantPaginationItem> {
    if (pageCount <= 0) return emptyList()
    val targetPage = page.coerceIn(1, pageCount)
    val siblings = resolveSiblingCount(siblingCount)
    val windowStart = (targetPage - siblings).coerceAtLeast(1)
    val windowEnd = (targetPage + siblings).coerceAtMost(pageCount)

    val items = mutableListOf<ElegantPaginationItem>()
    items += ElegantPaginationItem.Previous
    items += ElegantPaginationItem.Page(number = 1, enabled = targetPage != 1)
    if (windowStart > 2) {
        items += ElegantPaginationItem.Ellipsis
    }
    for (number in maxOf(windowStart, 2)..minOf(windowEnd, pageCount - 1)) {
        items += ElegantPaginationItem.Page(number = number, enabled = number != targetPage)
    }
    if (windowEnd < pageCount - 1) {
        items += ElegantPaginationItem.Ellipsis
    }
    if (pageCount > 1) {
        items += ElegantPaginationItem.Page(number = pageCount, enabled = targetPage != pageCount)
    }
    items += ElegantPaginationItem.Next
    return items
}

/** Returns true when a previous-page navigation is possible from [page]. */
internal fun canGoPrevious(page: Int): Boolean = page > 1

/** Returns true when a next-page navigation is possible from [page] within [pageCount]. */
internal fun canGoNext(page: Int, pageCount: Int): Boolean = pageCount > 0 && page < pageCount

/** Coerces [siblingCount] to a non-negative value. */
internal fun resolveSiblingCount(siblingCount: Int): Int = siblingCount.coerceAtLeast(0)

/** Resolved container and content colors for one pagination item. */
@Immutable
internal data class PaginationItemVisuals(
    val container: Color,
    val content: Color,
)

/**
 * Resolves the state colors for one pagination item.
 *
 * Precedence: disabled, selected, pressed, hovered, resting. Disabled items keep a transparent
 * container and only replace the content color; selected always wins over interaction states.
 */
internal fun resolvePaginationItemVisuals(
    colors: ElegantPaginationColors,
    enabled: Boolean,
    selected: Boolean,
    pressed: Boolean,
    hovered: Boolean,
): PaginationItemVisuals {
    val container = when {
        !enabled -> Color.Transparent
        selected -> colors.selectedItemColor
        pressed -> colors.pressedItemColor
        hovered -> colors.hoveredItemColor
        else -> Color.Transparent
    }
    val content = when {
        !enabled -> colors.disabledItemColor
        selected -> colors.selectedContentColor
        else -> colors.itemColor
    }
    return PaginationItemVisuals(
        container = container,
        content = content,
    )
}

private val ChevronSize: Dp = 16.dp

package com.elegant.compose.ui.cascadingmenu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantElevation
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * One node of the [ElegantCascadingMenu] item tree.
 *
 * The model is recursive: [children] holds the submenu shown beside a parent item, and leaf items
 * keep it empty. An item with children opens its submenu when hovered or clicked and never invokes
 * [ElegantCascadingMenu.onItemClick] by itself; a leaf item invokes it with the ancestor chain
 * ending at the leaf. Because [ElegantCascadingMenu] takes the tree as a `List`, keep the caller
 * owned [items] list identity stable across recompositions.
 *
 * @property text item label, truncated with an ellipsis when space runs out.
 * @property enabled whether the item accepts activation; disabled items never invoke callbacks.
 * @property children child items shown in the submenu; empty for leaf items.
 */
@Immutable
public data class ElegantCascadingMenuItem(
    val text: String,
    val enabled: Boolean = true,
    val children: List<ElegantCascadingMenuItem> = emptyList(),
)

/**
 * Theme-aware surface colors used by [ElegantCascadingMenu].
 *
 * Use [ElegantCascadingMenuDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor menu surface background at every level.
 * @property contentColor text and chevron color inside the menu.
 * @property disabledContentColor text and chevron color of disabled items.
 * @property hoveredContainerColor background of hovered items.
 * @property borderColor recommended border or separator color for surfaces placed next to the menu.
 */
@Immutable
public data class ElegantCascadingMenuColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContentColor: Color,
    val hoveredContainerColor: Color,
    val borderColor: Color,
)

/** Defaults shared by the Elegant UI cascading menu APIs. */
public object ElegantCascadingMenuDefaults {
    /** Minimum menu width; the surface grows to fit the widest item. */
    public val MinWidth: Dp = 180.dp

    /** Maximum menu height before the item column starts scrolling. */
    public val MaxHeight: Dp = 320.dp

    /** Height of one item row at every menu level. */
    public val ItemHeight: Dp = 40.dp

    /** Horizontal padding inside every item row. */
    public val HorizontalPadding: Dp = 16.dp

    /** Gap between a parent item's end edge and its submenu surface. */
    public val SubmenuOffset: Dp = 4.dp

    /** Duration of the root menu entrance transition. */
    public const val AnimationDurationMillis: Int = ElegantMotion.fastDurationMillis

    /** Side length of the chevron glyph drawn beside parent items. */
    internal val ChevronSize: Dp = 16.dp

    /** Default menu surface colors resolved from the active [ElegantTheme]. */
    @Composable
    public fun colors(): ElegantCascadingMenuColors = resolveCascadingMenuColors(ElegantTheme.colors)
}

/**
 * Shows a multi-level action list on nested surfaces anchored below its anchor Box.
 *
 * The caller owns the trigger and places [ElegantCascadingMenu] inside the same Box as the trigger,
 * exactly like [com.elegant.compose.ui.menu.ElegantMenu]: the root surface drops below that Box,
 * start-aligned, and is clamped into the window. [items] is a recursive tree; items with children
 * render a trailing chevron and open a child surface beside them, and only one submenu chain is
 * open at a time. Pointing at a parent item opens its submenu, clicking a parent item opens it too,
 * and hovering or clicking a different parent replaces the open chain. Clicking a leaf item invokes
 * [onItemClick] with the ancestor chain ending at the leaf, for example `[Edit, Copy]`, and resets
 * the open chain; the caller dismisses the menu in the same callback.
 *
 * Dismissal follows the [com.elegant.compose.ui.menu.ElegantMenu] contract: the root popup is
 * focusable and dismisses on outside click, Escape, or the platform back gesture, invoking
 * [onDismissRequest] and closing the whole chain. Submenu popups are not focusable, share the
 * root's dismissal, and their visibility is driven by internal state, so keyboard focus moves into
 * the root menu when it opens and returns to the trigger when it dismisses.
 *
 * The root surface fades in over [ElegantCascadingMenuDefaults.AnimationDurationMillis], is clipped
 * to [ElegantRadius.md], casts a medium shadow, and scrolls its items once content exceeds
 * [ElegantCascadingMenuDefaults.MaxHeight]. Every item row is
 * [ElegantCascadingMenuDefaults.ItemHeight] tall with
 * [ElegantCascadingMenuDefaults.HorizontalPadding] horizontal padding, shows the hovered background
 * while pointed at, and disabled items never invoke callbacks.
 *
 * @param expanded whether the menu chain is shown.
 * @param onDismissRequest called when the user requests dismissal, such as by tapping outside or
 *   pressing Escape.
 * @param items recursive menu tree; leaf items keep `children` empty.
 * @param onItemClick called with the ancestor chain ending at the clicked leaf item, for example
 *   `[Edit, Copy]`; the caller owns dismissal after a selection.
 * @param modifier modifier applied to the scrollable item column of the root surface.
 * @param colors menu surface colors for every level.
 */
@Composable
public fun ElegantCascadingMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<ElegantCascadingMenuItem>,
    onItemClick: (List<ElegantCascadingMenuItem>) -> Unit,
    modifier: Modifier = Modifier,
    colors: ElegantCascadingMenuColors = ElegantCascadingMenuDefaults.colors(),
) {
    val density = LocalDensity.current
    val offsetPx = with(density) { ElegantCascadingMenuDefaults.SubmenuOffset.roundToPx() }
    val positionProvider = remember(offsetPx) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset = clampCascadingMenuPosition(
                position = IntOffset(
                    x = anchorBounds.left,
                    y = anchorBounds.top + anchorBounds.height + offsetPx,
                ),
                size = popupContentSize,
                windowSize = windowSize,
            )
        }
    }
    var openSubmenuPath by remember { mutableStateOf(emptyList<Int>()) }

    LaunchedEffect(expanded) {
        if (!expanded) openSubmenuPath = emptyList()
    }

    if (expanded) {
        Popup(
            popupPositionProvider = positionProvider,
            onDismissRequest = onDismissRequest,
            properties = PopupProperties(focusable = true),
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = ElegantCascadingMenuDefaults.AnimationDurationMillis,
                        easing = FastOutSlowInEasing,
                    ),
                ),
            ) {
                ElegantCascadingMenuLevel(
                    items = items,
                    remainingPath = openSubmenuPath,
                    parentPath = emptyList(),
                    onSubmenuPathChange = { openSubmenuPath = it },
                    onLeafClick = { indexPath ->
                        openSubmenuPath = emptyList()
                        onItemClick(leafPath(items, indexPath))
                    },
                    modifier = modifier,
                    colors = colors,
                )
            }
        }
    }
}

/**
 * One menu surface of [ElegantCascadingMenu], rendered for the root menu and for every submenu.
 *
 * [remainingPath] is the open submenu chain relative to this level: a non-empty head that points at
 * a parent item renders that item's submenu inside an anchored, non-focusable popup and passes the
 * rest of the chain down. [parentPath] is this level's index chain from the root, appended to the
 * leaf index when a leaf item is clicked. The surface is a medium-shadowed
 * [ElegantRadius.md]-rounded [ElegantCascadingMenuColors.containerColor] box that scrolls its item
 * column once content exceeds [ElegantCascadingMenuDefaults.MaxHeight].
 *
 * @param items items rendered at this level.
 * @param remainingPath open submenu chain relative to this level; consumed one index per level.
 * @param parentPath index chain from the root to this level's menu.
 * @param onSubmenuPathChange reports the new open submenu chain relative to this level.
 * @param onLeafClick reports the root-relative index path of a clicked leaf item.
 * @param modifier modifier applied to the surface root.
 * @param colors menu surface colors.
 */
@Composable
private fun ElegantCascadingMenuLevel(
    items: List<ElegantCascadingMenuItem>,
    remainingPath: List<Int>,
    parentPath: List<Int>,
    onSubmenuPathChange: (List<Int>) -> Unit,
    onLeafClick: (List<Int>) -> Unit,
    modifier: Modifier = Modifier,
    colors: ElegantCascadingMenuColors,
) {
    val density = LocalDensity.current
    val offsetPx = with(density) { ElegantCascadingMenuDefaults.SubmenuOffset.roundToPx() }
    val submenuPositionProvider = remember(offsetPx) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset = submenuOffsetPosition(anchorBounds, popupContentSize, offsetPx, windowSize)
        }
    }
    val currentOnSubmenuPathChange by rememberUpdatedState(onSubmenuPathChange)
    val currentOnLeafClick by rememberUpdatedState(onLeafClick)
    val openIndex = if (submenuVisible(items, remainingPath)) remainingPath.first() else -1
    val shape = RoundedCornerShape(ElegantRadius.md)

    Box(
        modifier = modifier
            .shadow(
                elevation = ElegantElevation.medium,
                shape = shape,
                clip = false,
            )
            .clip(shape)
            .background(colors.containerColor)
            .widthIn(min = ElegantCascadingMenuDefaults.MinWidth),
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .heightIn(max = ElegantCascadingMenuDefaults.MaxHeight)
                .verticalScroll(rememberScrollState()),
        ) {
            items.forEachIndexed { index, item ->
                val itemPath = parentPath + index
                Box(modifier = Modifier.fillMaxWidth()) {
                    CascadingMenuItemRow(
                        item = item,
                        remainingPath = remainingPath,
                        index = index,
                        itemPath = itemPath,
                        onSubmenuPathChange = currentOnSubmenuPathChange,
                        onLeafClick = currentOnLeafClick,
                        colors = colors,
                    )
                    if (index == openIndex) {
                        Popup(
                            popupPositionProvider = submenuPositionProvider,
                            properties = PopupProperties(focusable = false),
                        ) {
                            ElegantCascadingMenuLevel(
                                items = childAt(items, remainingPath.take(1)),
                                remainingPath = remainingPath.drop(1),
                                parentPath = itemPath,
                                onSubmenuPathChange = { changedPath ->
                                    currentOnSubmenuPathChange(listOf(openIndex) + changedPath)
                                },
                                onLeafClick = currentOnLeafClick,
                                modifier = Modifier,
                                colors = colors,
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * One action row of [ElegantCascadingMenu] at any level.
 *
 * The row is [ElegantCascadingMenuDefaults.ItemHeight] tall, spans the menu width, and pads
 * horizontally by [ElegantCascadingMenuDefaults.HorizontalPadding]. Hovered rows show
 * [ElegantCascadingMenuColors.hoveredContainerColor]; disabled rows never invoke callbacks and
 * expose the disabled semantic state. Items with children draw a trailing chevron and open their
 * submenu on hover and on click through [resolveSubmenuPath]; leaf items report their
 * root-relative path through [onLeafClick]. The row carries [Role.Button] semantics so screen
 * readers and keyboard activation treat it as an action.
 *
 * @param item the item rendered by this row.
 * @param remainingPath open submenu chain relative to this row's level.
 * @param index index of the item within its menu.
 * @param itemPath root-relative index path of this item.
 * @param onSubmenuPathChange reports the new open submenu chain relative to this row's level.
 * @param onLeafClick reports [itemPath] when the leaf item is activated.
 * @param colors menu surface colors resolving the row appearance.
 */
@Composable
private fun CascadingMenuItemRow(
    item: ElegantCascadingMenuItem,
    remainingPath: List<Int>,
    index: Int,
    itemPath: List<Int>,
    onSubmenuPathChange: (List<Int>) -> Unit,
    onLeafClick: (List<Int>) -> Unit,
    colors: ElegantCascadingMenuColors,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val background by animateColorAsState(
        targetValue = if (item.enabled && hovered) colors.hoveredContainerColor else Color.Transparent,
        animationSpec = tween(
            durationMillis = ElegantCascadingMenuDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantCascadingMenuItemBackground",
    )
    val contentColor = if (item.enabled) colors.contentColor else colors.disabledContentColor
    val currentOnSubmenuPathChange by rememberUpdatedState(onSubmenuPathChange)
    val currentOnLeafClick by rememberUpdatedState(onLeafClick)

    LaunchedEffect(hovered) {
        if (hovered && item.enabled) {
            currentOnSubmenuPathChange(resolveSubmenuPath(item, remainingPath, index))
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = ElegantCascadingMenuDefaults.ItemHeight)
            .background(background)
            .semantics {
                role = Role.Button
                if (!item.enabled) disabled()
            }
            .clickable(
                enabled = item.enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (hasChildren(item)) {
                        currentOnSubmenuPathChange(resolveSubmenuPath(item, remainingPath, index))
                    } else {
                        currentOnLeafClick(itemPath)
                    }
                },
            )
            .padding(horizontal = ElegantCascadingMenuDefaults.HorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = item.text,
            modifier = Modifier.weight(1f),
            color = contentColor,
            style = ElegantTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        if (hasChildren(item)) {
            Spacer(Modifier.width(ElegantSpacing.xs))
            CascadingMenuChevron(color = contentColor)
        }
    }
}

/**
 * Draws the 16dp right-pointing chevron shown beside items that open a submenu.
 *
 * @param color chevron stroke color.
 */
@Composable
private fun CascadingMenuChevron(color: Color) {
    Canvas(modifier = Modifier.size(ElegantCascadingMenuDefaults.ChevronSize)) {
        val path = Path().apply {
            moveTo(size.width * 0.36f, size.height * 0.30f)
            lineTo(size.width * 0.64f, size.height * 0.50f)
            lineTo(size.width * 0.36f, size.height * 0.70f)
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            ),
        )
    }
}

/**
 * Resolves the default cascading menu colors from [themeColors].
 *
 * @param themeColors active theme color roles.
 */
internal fun resolveCascadingMenuColors(themeColors: ElegantColors): ElegantCascadingMenuColors =
    ElegantCascadingMenuColors(
        containerColor = themeColors.surfaceRaised,
        contentColor = themeColors.textPrimary,
        disabledContentColor = themeColors.textTertiary,
        hoveredContainerColor = themeColors.surfaceHover,
        borderColor = themeColors.borderDefault,
    )

/**
 * Whether [item] opens a submenu; false for leaf items.
 *
 * @param item the item to inspect.
 */
internal fun hasChildren(item: ElegantCascadingMenuItem): Boolean = item.children.isNotEmpty()

/**
 * Follows [path] as indices into [items] and returns the child list at the end of the path.
 *
 * Returns an empty list when [path] walks out of range or ends on a leaf item.
 *
 * @param items item list the path is relative to.
 * @param path index chain into [items].
 */
internal fun childAt(
    items: List<ElegantCascadingMenuItem>,
    path: List<Int>,
): List<ElegantCascadingMenuItem> {
    var current = items
    for (index in path) {
        if (index !in current.indices) return emptyList()
        current = current[index].children
    }
    return current
}

/**
 * Whether the menu rendered from [items] shows a submenu for the first index of [path].
 *
 * The submenu is shown when [path] is non-empty, its first index points at a valid item, and that
 * item has children; deeper path entries describe the open chain below it.
 *
 * @param items item list the path is relative to.
 * @param path open submenu chain relative to [items].
 */
internal fun submenuVisible(
    items: List<ElegantCascadingMenuItem>,
    path: List<Int>,
): Boolean {
    if (path.isEmpty()) return false
    var current = items
    for (index in path) {
        if (index !in current.indices) return false
        val item = current[index]
        if (!hasChildren(item)) return false
        current = item.children
    }
    return true
}

/**
 * The new open submenu chain when the item at [index] is hovered or clicked.
 *
 * A leaf item leaves [path] unchanged. A parent item keeps [path] when its submenu is already the
 * open one at this level, preserving the open chain below it; otherwise the chain is replaced with
 * just this item, keeping only one submenu chain open at a time.
 *
 * @param item the hovered or clicked item.
 * @param path open submenu chain relative to the item's menu.
 * @param index index of [item] within its menu.
 */
internal fun resolveSubmenuPath(
    item: ElegantCascadingMenuItem,
    path: List<Int>,
    index: Int,
): List<Int> = when {
    !hasChildren(item) -> path
    path.firstOrNull() == index -> path
    else -> listOf(index)
}

/**
 * Resolves [path] into the ancestor chain of items ending at the leaf.
 *
 * Returns an empty list when [path] walks out of range or through a leaf item.
 *
 * @param items root item list of the menu tree.
 * @param path root-relative index chain ending at a leaf.
 */
internal fun leafPath(
    items: List<ElegantCascadingMenuItem>,
    path: List<Int>,
): List<ElegantCascadingMenuItem> {
    val result = mutableListOf<ElegantCascadingMenuItem>()
    var current = items
    for (index in path) {
        if (index !in current.indices) return emptyList()
        val item = current[index]
        result += item
        current = item.children
    }
    return result
}

/**
 * Position of a submenu surface beside its parent item: the parent's end edge plus [offsetPx],
 * vertically aligned to the parent's top, and clamped into [windowSize].
 *
 * @param parentItemBounds bounds of the parent item the submenu opens beside.
 * @param submenuSize size of the submenu surface.
 * @param offsetPx gap between the parent item and the submenu surface.
 * @param windowSize enclosing window or popup the submenu is clamped into.
 */
internal fun submenuOffsetPosition(
    parentItemBounds: IntRect,
    submenuSize: IntSize,
    offsetPx: Int,
    windowSize: IntSize,
): IntOffset = clampCascadingMenuPosition(
    position = IntOffset(
        x = parentItemBounds.right + offsetPx,
        y = parentItemBounds.top,
    ),
    size = submenuSize,
    windowSize = windowSize,
)

/**
 * Clamps [position] so a surface of [size] stays fully inside [windowSize].
 *
 * @param position desired surface position.
 * @param size surface size.
 * @param windowSize enclosing window or popup the surface is clamped into.
 */
internal fun clampCascadingMenuPosition(
    position: IntOffset,
    size: IntSize,
    windowSize: IntSize,
): IntOffset = IntOffset(
    x = position.x.coerceIn(0, (windowSize.width - size.width).coerceAtLeast(0)),
    y = position.y.coerceIn(0, (windowSize.height - size.height).coerceAtLeast(0)),
)

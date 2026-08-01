package com.elegant.compose.ui.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
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
 * Theme-aware surface colors used by [ElegantMenu] and [ElegantMenuItem].
 *
 * Use [ElegantMenuDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor menu surface background.
 * @property contentColor text and icon color inside the menu.
 * @property disabledContentColor text and icon color of disabled items.
 * @property dividerColor recommended divider color for separators placed between items.
 * @property selectedItemColor background of a semantically selected item.
 * @property hoveredItemColor background of hovered or pressed items.
 */
@Immutable
public data class ElegantMenuColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContentColor: Color,
    val dividerColor: Color,
    val selectedItemColor: Color,
    val hoveredItemColor: Color = containerColor,
)

/** Defaults shared by Elegant UI menu APIs. */
public object ElegantMenuDefaults {
    /** Minimum menu width; the surface grows to fit the widest item. */
    public val MinWidth: Dp = 160.dp

    /** Maximum menu height before the item column starts scrolling. */
    public val MaxHeight: Dp = 320.dp

    /** Height of one menu item row. */
    public val ItemHeight: Dp = 40.dp

    /** Horizontal padding inside every menu item. */
    public val HorizontalPadding: Dp = 16.dp

    /** Duration of the menu entrance transition. */
    public const val AnimationDurationMillis: Int = ElegantMotion.fastDurationMillis

    /** Gap between the anchored Box and the menu surface. */
    internal val AnchorOffset: Dp = 4.dp

    /** Side of the box that hosts leading and trailing item content. */
    internal val ItemSlotSize: Dp = 20.dp

    /** Default menu surface colors resolved from the active [ElegantTheme]. */
    @Composable
    public fun colors(): ElegantMenuColors = resolveMenuColors(ElegantTheme.colors)
}

/**
 * Shows a list of actions on a temporary surface anchored below its anchor Box.
 *
 * The caller owns the trigger and places [ElegantMenu] inside the same Box as the trigger. The
 * popup anchors to that Box: the surface drops below its bottom edge, start-aligned, and is
 * clamped into the window. The caller controls the anchor Box by sizing the Box that wraps the
 * trigger; wrapping only the trigger keeps the anchor exactly on the trigger.
 *
 * The platform popup dismisses the menu on outside click, Escape, or the platform back gesture,
 * invoking [onDismissRequest]. Because the popup is focusable, keyboard focus moves into the menu
 * when it opens and returns to the trigger when it dismisses; focused items activate with Enter or
 * Space through the item's clickable semantics.
 *
 * The surface fades in over [ElegantMenuDefaults.AnimationDurationMillis], is clipped to
 * [ElegantRadius.md], casts a medium shadow, and scrolls its items once content exceeds
 * [ElegantMenuDefaults.MaxHeight]. Item rows are supplied by the caller with [ElegantMenuItem],
 * and separators between groups are plain siblings such as `ElegantDivider`.
 *
 * @param expanded whether the menu surface is shown.
 * @param onDismissRequest called when the user requests dismissal, such as by tapping outside or
 *   pressing Escape.
 * @param modifier modifier applied to the scrollable item column inside the surface.
 * @param colors menu surface colors.
 * @param content menu items and custom rows.
 */
@Composable
public fun ElegantMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ElegantMenuColors = ElegantMenuDefaults.colors(),
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val offsetPx = with(density) { ElegantMenuDefaults.AnchorOffset.roundToPx() }
    val positionProvider = remember(offsetPx) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset {
                val position = IntOffset(
                    x = anchorBounds.left,
                    y = anchorBounds.top + menuAnchorPosition(anchorBounds.height, offsetPx),
                )
                return clampMenuPosition(position, popupContentSize, windowSize)
            }
        }
    }

    if (expanded) {
        Popup(
            popupPositionProvider = positionProvider,
            onDismissRequest = onDismissRequest,
            properties = PopupProperties(focusable = true),
        ) {
            val shape = RoundedCornerShape(ElegantRadius.md)
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = ElegantMenuDefaults.AnimationDurationMillis,
                        easing = FastOutSlowInEasing,
                    ),
                ),
            ) {
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = ElegantElevation.medium,
                            shape = shape,
                            clip = false,
                        )
                        .clip(shape)
                        .background(colors.containerColor)
                        .widthIn(min = ElegantMenuDefaults.MinWidth),
                ) {
                    Column(
                        modifier = modifier
                            .width(IntrinsicSize.Max)
                            .heightIn(max = ElegantMenuDefaults.MaxHeight)
                            .verticalScroll(rememberScrollState()),
                    ) {
                        CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
                            content()
                        }
                    }
                }
            }
        }
    }
}

/**
 * One selectable action row inside an [ElegantMenu].
 *
 * The row is [ElegantMenuDefaults.ItemHeight] tall, spans the menu width, and pads horizontally by
 * [ElegantMenuDefaults.HorizontalPadding]. [leadingContent] and [trailingContent] render inside
 * 20dp boxes separated from the label by a 12dp gap and inherit the resolved item content color.
 * Hovered and pressed rows show [ElegantMenuColors.hoveredItemColor]; disabled rows never invoke
 * [onClick] and expose the disabled semantic state. The row carries [Role.Button] semantics so
 * screen readers and keyboard activation treat it as an action.
 *
 * @param text item label, truncated with an ellipsis when space runs out.
 * @param onClick called when the enabled item is activated.
 * @param modifier modifier applied once to the item row.
 * @param enabled whether the item accepts activation and shows the enabled appearance.
 * @param leadingContent optional content before the label, such as an icon or status dot.
 * @param trailingContent optional content after the label, such as an icon or shortcut hint.
 * @param colors menu colors resolving the item's enabled and disabled appearance.
 */
@Composable
public fun ElegantMenuItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    colors: ElegantMenuColors = ElegantMenuDefaults.colors(),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val pressed by interactionSource.collectIsPressedAsState()
    val background by animateColorAsState(
        targetValue = if (enabled && (pressed || hovered)) {
            colors.hoveredItemColor
        } else {
            Color.Transparent
        },
        animationSpec = tween(
            durationMillis = ElegantMenuDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantMenuItemBackground",
    )
    val contentColor = if (enabled) colors.contentColor else colors.disabledContentColor

    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = ElegantMenuDefaults.ItemHeight)
            .background(background)
            .clickable(
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = ElegantMenuDefaults.HorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            if (leadingContent != null) {
                Box(
                    modifier = Modifier.size(ElegantMenuDefaults.ItemSlotSize),
                    contentAlignment = Alignment.Center,
                ) {
                    leadingContent()
                }
                Spacer(Modifier.width(ElegantSpacing.lg))
            }

            Text(
                text = text,
                modifier = Modifier.weight(1f),
                color = contentColor,
                style = ElegantTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            if (trailingContent != null) {
                Spacer(Modifier.width(ElegantSpacing.lg))
                Box(
                    modifier = Modifier.size(ElegantMenuDefaults.ItemSlotSize),
                    contentAlignment = Alignment.Center,
                ) {
                    trailingContent()
                }
            }
        }
    }
}

/** Vertical offset of the menu surface below the anchor: anchor height plus the gap. */
internal fun menuAnchorPosition(anchorHeight: Int, offsetPx: Int): Int = anchorHeight + offsetPx

/** Clamps [position] so the menu stays fully inside the window, pinning to the start edge. */
internal fun clampMenuPosition(
    position: IntOffset,
    menuSize: IntSize,
    windowSize: IntSize,
): IntOffset = IntOffset(
    x = position.x.coerceIn(0, (windowSize.width - menuSize.width).coerceAtLeast(0)),
    y = position.y.coerceIn(0, (windowSize.height - menuSize.height).coerceAtLeast(0)),
)

internal fun resolveMenuColors(themeColors: ElegantColors): ElegantMenuColors = ElegantMenuColors(
    containerColor = themeColors.surfaceRaised,
    contentColor = themeColors.textPrimary,
    disabledContentColor = themeColors.textTertiary,
    dividerColor = themeColors.borderDefault,
    selectedItemColor = themeColors.surfaceHover,
    hoveredItemColor = themeColors.surfaceHover,
)

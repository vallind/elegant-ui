package com.elegant.compose.ui.popover

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantElevation
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import kotlin.math.roundToInt

/**
 * Logical placement of a popover relative to its anchor.
 *
 * [Start] and [End] are layout-direction-aware: in RTL layouts they mirror to the opposite edges.
 */
public enum class ElegantPopoverPlacement {
    /** Places the popover above the anchor, horizontally centered. */
    Top,

    /** Places the popover below the anchor, horizontally centered. */
    Bottom,

    /** Places the popover at the logical start edge, vertically centered on the anchor. */
    Start,

    /** Places the popover at the logical end edge, vertically centered on the anchor. */
    End,
}

/**
 * Surface colors of an [ElegantPopover].
 *
 * @property containerColor popover surface background.
 * @property contentColor text and icon color inside the popover surface.
 * @property borderColor popover surface outline.
 */
@Immutable
public data class ElegantPopoverColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color,
)

/** Defaults shared by Elegant UI popover APIs. */
public object ElegantPopoverDefaults {
    /** Maximum popover width before content wraps. */
    public val MaxWidth: Dp = 320.dp

    /** Gap between the anchor and the popover. */
    public val Offset: Dp = 8.dp

    /** Duration of the popover entrance transition. */
    public const val AnimationDurationMillis: Int = ElegantMotion.fastDurationMillis

    /** Default popover surface colors resolved from the active [ElegantTheme]. */
    @Composable
    public fun colors(): ElegantPopoverColors = resolvePopoverColors(ElegantTheme.colors)
}

/**
 * Shows a floating [popover] surface anchored to [content] when the trigger is clicked.
 *
 * Clicking the wrapped trigger toggles the popover open. The platform popup dismisses it on
 * outside click, Escape, or the platform back gesture; because the popup is focusable, keyboard
 * focus moves into the popover when it opens and returns to the trigger when it dismisses, both
 * owned by the popup window. The popover content is caller-owned and may contain interactive
 * controls, because the popup participates in focus traversal.
 *
 * The popover is a floating surface without an arrow, clamped to the window. It fades in on open
 * using [ElegantPopoverDefaults.AnimationDurationMillis]. [ElegantPopoverPlacement.Start] and
 * [ElegantPopoverPlacement.End] mirror automatically in RTL layouts. A disabled trigger never
 * opens its popover.
 *
 * @param popover floating popup content shown near the trigger.
 * @param modifier modifier applied once to the trigger.
 * @param enabled whether clicking the trigger can open the popover.
 * @param placement logical placement of the popover around the trigger.
 * @param offset gap between the trigger and the popover.
 * @param colors popover surface colors.
 * @param content trigger content; clicking it toggles the popover.
 */
@Composable
public fun ElegantPopover(
    popover: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placement: ElegantPopoverPlacement = ElegantPopoverPlacement.Bottom,
    offset: Dp = ElegantPopoverDefaults.Offset,
    colors: ElegantPopoverColors = ElegantPopoverDefaults.colors(),
    content: @Composable () -> Unit,
) {
    var open by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val density = LocalDensity.current
    val offsetPx = with(density) { offset.roundToPx() }
    var anchorBounds by remember { mutableStateOf(IntRect.Zero) }

    val positionProvider = remember(placement, offsetPx, anchorBounds) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset = popoverPositionFor(
                placement = placement,
                anchorBounds = anchorBounds,
                popoverSize = popupContentSize,
                offsetPx = offsetPx,
                layoutDirection = layoutDirection,
                windowSize = windowSize,
            )
        }
    }

    Box(
        modifier = modifier
            .onGloballyPositioned { position ->
                val bounds = position.boundsInWindow()
                anchorBounds = IntRect(
                    left = bounds.left.roundToInt(),
                    top = bounds.top.roundToInt(),
                    right = bounds.right.roundToInt(),
                    bottom = bounds.bottom.roundToInt(),
                )
            }
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = enabled,
            ) {
                open = !open
            },
    ) {
        content()
        if (enabled && open) {
            Popup(
                popupPositionProvider = positionProvider,
                onDismissRequest = { open = false },
                properties = PopupProperties(focusable = true),
            ) {
                val focusRequester = remember { FocusRequester() }
                val shape = RoundedCornerShape(ElegantRadius.md)
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = ElegantPopoverDefaults.AnimationDurationMillis,
                            easing = FastOutSlowInEasing,
                        ),
                    ),
                ) {
                    Box(
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .focusable()
                            .shadow(
                                elevation = ElegantElevation.medium,
                                shape = shape,
                                clip = false,
                            )
                            .clip(shape)
                            .background(colors.containerColor)
                            .border(
                                width = 1.dp,
                                color = colors.borderColor,
                                shape = shape,
                            )
                            .widthIn(max = ElegantPopoverDefaults.MaxWidth)
                            .padding(ElegantSpacing.lg),
                    ) {
                        CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
                            popover()
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }
        }
    }
}

internal fun popoverPositionFor(
    placement: ElegantPopoverPlacement,
    anchorBounds: IntRect,
    popoverSize: IntSize,
    offsetPx: Int,
    layoutDirection: LayoutDirection,
    windowSize: IntSize,
): IntOffset {
    val horizontalCenter = anchorBounds.left + anchorBounds.width / 2 - popoverSize.width / 2
    val verticalCenter = anchorBounds.top + anchorBounds.height / 2 - popoverSize.height / 2
    val (x, y) = when (placement) {
        ElegantPopoverPlacement.Top -> IntOffset(
            x = horizontalCenter,
            y = anchorBounds.top - popoverSize.height - offsetPx,
        )

        ElegantPopoverPlacement.Bottom -> IntOffset(
            x = horizontalCenter,
            y = anchorBounds.bottom + offsetPx,
        )

        ElegantPopoverPlacement.Start -> if (layoutDirection == LayoutDirection.Ltr) {
            IntOffset(
                x = anchorBounds.left - popoverSize.width - offsetPx,
                y = verticalCenter,
            )
        } else {
            IntOffset(
                x = anchorBounds.right + offsetPx,
                y = verticalCenter,
            )
        }

        ElegantPopoverPlacement.End -> if (layoutDirection == LayoutDirection.Ltr) {
            IntOffset(
                x = anchorBounds.right + offsetPx,
                y = verticalCenter,
            )
        } else {
            IntOffset(
                x = anchorBounds.left - popoverSize.width - offsetPx,
                y = verticalCenter,
            )
        }
    }
    return IntOffset(
        x = x.coerceIn(0, (windowSize.width - popoverSize.width).coerceAtLeast(0)),
        y = y.coerceIn(0, (windowSize.height - popoverSize.height).coerceAtLeast(0)),
    )
}

internal fun resolvePopoverColors(themeColors: ElegantColors): ElegantPopoverColors = ElegantPopoverColors(
    containerColor = themeColors.surfaceRaised,
    contentColor = themeColors.textPrimary,
    borderColor = themeColors.borderDefault,
)

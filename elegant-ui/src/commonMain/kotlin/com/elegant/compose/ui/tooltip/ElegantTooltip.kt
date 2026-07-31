package com.elegant.compose.ui.tooltip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
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
import kotlin.math.roundToInt
import androidx.compose.ui.window.PopupProperties
import com.elegant.compose.ui.theme.ElegantElevation
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.delay

/**
 * Logical placement of a tooltip relative to its anchor.
 *
 * [Start] and [End] are layout-direction-aware: in RTL layouts they mirror to the opposite edges.
 */
public enum class ElegantTooltipPlacement {
    /** Places the tooltip above the anchor, horizontally centered. */
    Top,

    /** Places the tooltip below the anchor, horizontally centered. */
    Bottom,

    /** Places the tooltip at the logical start edge, vertically centered on the anchor. */
    Start,

    /** Places the tooltip at the logical end edge, vertically centered on the anchor. */
    End,
}

/** Defaults shared by Elegant UI tooltip APIs. */
public object ElegantTooltipDefaults {
    /** Delay before a hover-only tooltip appears. */
    public const val DefaultShowDelayMillis: Long = 600L

    /** Delay before a leaving tooltip disappears after the pointer leaves or focus moves away. */
    public const val DefaultHideDelayMillis: Long = 100L

    /** Gap between the anchor and the tooltip. */
    public val DefaultOffset: Dp = 8.dp

    /** Maximum tooltip width before text wraps. */
    public val MaxWidth: Dp = 280.dp
}

/**
 * Standard tooltip surface used by [ElegantTooltipBox].
 *
 * Renders a raised container with a subtle border and shadow, compact label typography, and a
 * [ElegantTooltipDefaults.MaxWidth] cap. Intended for the [ElegantTooltipBox] tooltip slot; it can
 * also be placed inline where a persistent label surface is needed.
 *
 * @param text tooltip label.
 * @param modifier modifier applied once to the tooltip surface.
 */
@Composable
public fun ElegantTooltip(
    text: String,
    modifier: Modifier = Modifier,
) {
    val colors = ElegantTheme.colors
    val shape = RoundedCornerShape(ElegantRadius.md)
    Box(
        modifier = modifier
            .shadow(
                elevation = ElegantElevation.low,
                shape = shape,
                clip = false,
            )
            .clip(shape)
            .background(colors.surfaceRaised)
            .border(
                width = 1.dp,
                color = colors.borderDefault,
                shape = shape,
            )
            .widthIn(max = ElegantTooltipDefaults.MaxWidth)
            .padding(
                horizontal = ElegantSpacing.md,
                vertical = ElegantSpacing.xs,
            ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.textPrimary) {
            ProvideTextStyle(value = ElegantTheme.typography.labelSmall) {
                Text(text = text)
            }
        }
    }
}

/**
 * Shows [tooltip] near [content] on hover, keyboard focus, or touch long-press.
 *
 * Hover reveals the tooltip after [showDelayMillis]; keyboard focus and touch long-press reveal it
 * immediately. The tooltip hides [hideDelayMillis] after the pointer leaves or focus moves away,
 * and immediately when a long-press is released. A disabled anchor never reveals its tooltip.
 *
 * The popup is not focusable and never requests dismissal, so the anchor and the surrounding layout
 * stay fully interactive. The tooltip is clamped to the window, and [ElegantTooltipPlacement.Start]
 * and [ElegantTooltipPlacement.End] mirror automatically in RTL layouts.
 *
 * @param tooltip popup content shown near the anchor; use [ElegantTooltip] for the standard surface.
 * @param modifier modifier applied once to the anchor.
 * @param enabled whether hover, focus, and long-press can reveal the tooltip.
 * @param placement logical placement of the tooltip around the anchor.
 * @param showDelayMillis hover reveal delay; negative values resolve to 0.
 * @param hideDelayMillis leave and focus-loss hide delay; negative values resolve to 0.
 * @param offset gap between the anchor and the tooltip.
 * @param content anchor content receiving the hover, focus, and long-press interactions.
 */
@Composable
public fun ElegantTooltipBox(
    tooltip: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placement: ElegantTooltipPlacement = ElegantTooltipPlacement.Top,
    showDelayMillis: Long = ElegantTooltipDefaults.DefaultShowDelayMillis,
    hideDelayMillis: Long = ElegantTooltipDefaults.DefaultHideDelayMillis,
    offset: Dp = ElegantTooltipDefaults.DefaultOffset,
    content: @Composable () -> Unit,
) {
    val (resolvedShowDelayMillis, resolvedHideDelayMillis) = resolveTooltipDelays(
        show = showDelayMillis,
        hide = hideDelayMillis,
    )
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val density = LocalDensity.current
    val offsetPx = with(density) { offset.roundToPx() }
    var anchorBounds by remember { mutableStateOf(IntRect.Zero) }
    var longPressActive by remember { mutableStateOf(false) }
    var lastTriggeredByLongPress by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }

    val showRequested = hovered || focused || longPressActive

    LaunchedEffect(showRequested, hovered, focused, longPressActive, enabled) {
        if (!enabled) {
            lastTriggeredByLongPress = false
            visible = false
            return@LaunchedEffect
        }
        if (showRequested) {
            if (hovered && !focused && !longPressActive) {
                delay(resolvedShowDelayMillis)
            }
            lastTriggeredByLongPress = longPressActive
            visible = true
        } else if (visible) {
            delay(if (lastTriggeredByLongPress) 0L else resolvedHideDelayMillis)
            visible = false
        }
    }

    val pressModifier = if (enabled) {
        Modifier.pointerInput(Unit) {
            detectTapGestures(
                onLongPress = { longPressActive = true },
                onPress = {
                    try {
                        awaitRelease()
                        longPressActive = false
                    } catch (cancellation: CancellationException) {
                        longPressActive = false
                        throw cancellation
                    }
                },
            )
        }
    } else {
        Modifier
    }

    val positionProvider = remember(placement, offsetPx, anchorBounds) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset = tooltipPositionFor(
                placement = placement,
                anchorBounds = anchorBounds,
                tooltipSize = popupContentSize,
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
            .hoverable(interactionSource = interactionSource, enabled = enabled)
            .focusable(interactionSource = interactionSource, enabled = enabled)
            .then(pressModifier),
    ) {
        content()
        if (visible) {
            Popup(
                popupPositionProvider = positionProvider,
                onDismissRequest = {},
                properties = PopupProperties(focusable = false),
            ) {
                tooltip()
            }
        }
    }
}

internal fun tooltipPositionFor(
    placement: ElegantTooltipPlacement,
    anchorBounds: IntRect,
    tooltipSize: IntSize,
    offsetPx: Int,
    layoutDirection: LayoutDirection,
    windowSize: IntSize,
): IntOffset {
    val horizontalCenter = anchorBounds.left + anchorBounds.width / 2 - tooltipSize.width / 2
    val verticalCenter = anchorBounds.top + anchorBounds.height / 2 - tooltipSize.height / 2
    val (x, y) = when (placement) {
        ElegantTooltipPlacement.Top -> IntOffset(
            x = horizontalCenter,
            y = anchorBounds.top - tooltipSize.height - offsetPx,
        )

        ElegantTooltipPlacement.Bottom -> IntOffset(
            x = horizontalCenter,
            y = anchorBounds.bottom + offsetPx,
        )

        ElegantTooltipPlacement.Start -> if (layoutDirection == LayoutDirection.Ltr) {
            IntOffset(
                x = anchorBounds.left - tooltipSize.width - offsetPx,
                y = verticalCenter,
            )
        } else {
            IntOffset(
                x = anchorBounds.right + offsetPx,
                y = verticalCenter,
            )
        }

        ElegantTooltipPlacement.End -> if (layoutDirection == LayoutDirection.Ltr) {
            IntOffset(
                x = anchorBounds.right + offsetPx,
                y = verticalCenter,
            )
        } else {
            IntOffset(
                x = anchorBounds.left - tooltipSize.width - offsetPx,
                y = verticalCenter,
            )
        }
    }
    return IntOffset(
        x = x.coerceIn(0, (windowSize.width - tooltipSize.width).coerceAtLeast(0)),
        y = y.coerceIn(0, (windowSize.height - tooltipSize.height).coerceAtLeast(0)),
    )
}

internal fun resolveTooltipDelays(
    show: Long,
    hide: Long,
): Pair<Long, Long> = show.coerceAtLeast(0L) to hide.coerceAtLeast(0L)

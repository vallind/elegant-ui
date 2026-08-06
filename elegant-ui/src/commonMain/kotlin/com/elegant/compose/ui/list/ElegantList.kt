package com.elegant.compose.ui.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantListItem].
 *
 * Use [ElegantListItemDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor default container color; transparent for the default look.
 * @property contentColor default title and primary content color.
 * @property supportingTextColor supporting-text line color.
 * @property leadingContentColor leading-slot content color.
 * @property trailingContentColor trailing-slot content color.
 * @property hoveredContainerColor hovered container color.
 * @property pressedContainerColor pressed container color.
 * @property disabledContainerColor disabled container color.
 * @property disabledContentColor disabled title and primary content color.
 * @property disabledSupportingTextColor disabled supporting-text color.
 * @property focusedBorderColor keyboard focus-ring color.
 */
@Immutable
public data class ElegantListItemColors(
    val containerColor: Color,
    val contentColor: Color,
    val supportingTextColor: Color,
    val leadingContentColor: Color,
    val trailingContentColor: Color,
    val hoveredContainerColor: Color = containerColor,
    val pressedContainerColor: Color = containerColor,
    val disabledContainerColor: Color = containerColor,
    val disabledContentColor: Color = contentColor,
    val disabledSupportingTextColor: Color = supportingTextColor,
    val focusedBorderColor: Color = containerColor,
)

/** Theme-aware defaults for [ElegantListItem]. */
public object ElegantListItemDefaults {
    /** Minimum row height kept by every list item. */
    public val MinimumTouchHeight: Dp = 56.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors for the default look. */
    @Composable
    public fun colors(): ElegantListItemColors = resolveListItemColors(
        themeColors = ElegantTheme.colors,
    )
}

@Immutable
internal data class ListItemVisuals(
    val container: Color,
    val content: Color,
    val supportingText: Color,
    val border: Color,
    val borderWidth: Dp,
)

internal object ListItemMetrics {
    /** Leading and trailing slot box size. */
    val LeadingSlotSize: Dp = 20.dp
    val TrailingSlotSize: Dp = 20.dp
    /** Gap between the title block and either slot. */
    val SlotGap: Dp = 16.dp
    /** Focus-ring stroke width. */
    val FocusBorderWidth: Dp = 2.dp
}

/**
 * Arranges [ElegantListItem]s vertically.
 *
 * [ElegantList] is a plain non-scrolling column that adds no spacing of its own: every item owns
 * its padding through its `contentPadding`, so the caller stays in control of row density and
 * separators. Wrap the list in `verticalScroll` or a lazy column when the content can overflow the
 * viewport.
 *
 * @param modifier modifier applied once to the column root.
 * @param content items rendered vertically.
 */
@Composable
@NonRestartableComposable
public fun ElegantList(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        content()
    }
}

/**
 * Renders a row with an optional leading slot, a two-line title block, and an optional trailing
 * slot.
 *
 * An item without [onClick] is non-interactive: it renders the title, supporting text, and slots,
 * keeps the semantics of its content, and supports no focus. Passing [onClick] turns the item into
 * a button-like row with a 56dp minimum interactive root, a merged [Role.Button] label that
 * announces [selected] and [enabled], a visible focus ring, and animated hover and press container
 * feedback with a ripple. Hover and press feedback apply only while [onClick] is set; [selected]
 * is a semantic state that replaces the resting container with the accent-tinted container
 * resolved from the active theme, so it also applies to non-interactive items.
 *
 * The item owns the text styles: the [title] lambda receives `labelLarge` typography and the
 * [supportingText] lambda receives `bodyMedium` typography plus the supporting text color. Leading
 * and trailing slots are centered in 20dp boxes separated by 16dp gaps and receive their slot
 * colors through [LocalContentColor]. The item keeps a 56dp minimum row height and fills the width
 * of its container.
 *
 * State precedence: disabled, pressed, selected, hovered, resting. The focus ring renders only for
 * focused interactive items.
 *
 * @param leadingContent optional content before the title, centered in a 20dp box.
 * @param modifier modifier applied once to the item root.
 * @param title primary line; the component owns its `labelLarge` text style.
 * @param supportingText optional secondary line; the component owns its `bodyMedium` text style and
 *   supporting text color.
 * @param trailingContent optional content after the title block, centered in a 20dp box.
 * @param onClick optional activation callback; null keeps the item non-interactive.
 * @param enabled whether user interaction is accepted.
 * @param selected whether the item communicates a chosen state.
 * @param colors theme-aware state colors.
 * @param contentPadding inner padding around the row content.
 */
@Composable
public fun ElegantListItem(
    leadingContent: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    supportingText: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    selected: Boolean = false,
    colors: ElegantListItemColors = ElegantListItemDefaults.colors(),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
) {
    val interactive = onClick != null
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val visuals = resolveListItemVisuals(
        colors = colors,
        themeColors = ElegantTheme.colors,
        selected = selected,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
        focused = focused,
        interactive = interactive,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantListItemContainer",
    )
    val animatedContent by animateColorAsState(
        targetValue = visuals.content,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantListItemContent",
    )
    val animatedSupportingText by animateColorAsState(
        targetValue = visuals.supportingText,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantListItemSupportingText",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantListItemBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantListItemBorderWidth",
    )

    val semanticModifier = if (interactive) {
        Modifier.semantics(mergeDescendants = true) {
            role = Role.Button
            if (!enabled) disabled()
            this.selected = selected
        }
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .then(semanticModifier)
            .defaultMinSize(minHeight = ElegantListItemDefaults.MinimumTouchHeight)
            .clickable(
                enabled = interactive && enabled,
                role = if (interactive) Role.Button else null,
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick?.invoke() },
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(animatedContainer)
                .indication(
                    interactionSource = interactionSource,
                    indication = if (interactive) {
                        LocalIndication.current
                    } else {
                        null
                    },
                )
                .then(
                    if (animatedBorderWidth > 0.dp) {
                        Modifier.border(BorderStroke(animatedBorderWidth, animatedBorder))
                    } else {
                        Modifier
                    },
                )
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (leadingContent != null) {
                CompositionLocalProvider(LocalContentColor provides colors.leadingContentColor) {
                    Box(
                        modifier = Modifier.size(ListItemMetrics.LeadingSlotSize),
                        contentAlignment = Alignment.Center,
                    ) {
                        leadingContent()
                    }
                }
                Spacer(Modifier.width(ListItemMetrics.SlotGap))
            }

            Column(modifier = Modifier.weight(1f)) {
                CompositionLocalProvider(LocalContentColor provides animatedContent) {
                    ProvideTextStyle(ElegantTheme.typography.labelLarge) {
                        title()
                    }
                }
                if (supportingText != null) {
                    CompositionLocalProvider(LocalContentColor provides animatedSupportingText) {
                        ProvideTextStyle(ElegantTheme.typography.bodyMedium) {
                            supportingText()
                        }
                    }
                }
            }

            if (trailingContent != null) {
                Spacer(Modifier.width(ListItemMetrics.SlotGap))
                CompositionLocalProvider(LocalContentColor provides colors.trailingContentColor) {
                    Box(
                        modifier = Modifier.size(ListItemMetrics.TrailingSlotSize),
                        contentAlignment = Alignment.Center,
                    ) {
                        trailingContent()
                    }
                }
            }
        }
    }
}

internal fun resolveListItemColors(
    themeColors: ElegantColors,
): ElegantListItemColors = ElegantListItemColors(
    containerColor = Color.Transparent,
    contentColor = themeColors.textPrimary,
    supportingTextColor = themeColors.textSecondary,
    leadingContentColor = themeColors.textSecondary,
    trailingContentColor = themeColors.textSecondary,
    hoveredContainerColor = themeColors.surfaceHover,
    pressedContainerColor = themeColors.backgroundSubtle,
    disabledContainerColor = Color.Transparent,
    disabledContentColor = themeColors.textTertiary,
    disabledSupportingTextColor = themeColors.textTertiary,
    focusedBorderColor = themeColors.focusRing,
)

internal fun resolveListItemContainer(
    selected: Boolean,
    colors: ElegantListItemColors,
    themeColors: ElegantColors,
): Color = when {
    selected -> themeColors.interactivePrimary.copy(alpha = 0.10f)
    else -> colors.containerColor
}

internal fun resolveListItemVisuals(
    colors: ElegantListItemColors,
    themeColors: ElegantColors,
    selected: Boolean,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
    focused: Boolean,
    interactive: Boolean,
): ListItemVisuals {
    val container = when {
        !enabled -> colors.disabledContainerColor
        pressed && interactive -> colors.pressedContainerColor
        selected -> resolveListItemContainer(
            selected = selected,
            colors = colors,
            themeColors = themeColors,
        )
        hovered && interactive -> colors.hoveredContainerColor
        else -> colors.containerColor
    }
    val content = when {
        !enabled -> colors.disabledContentColor
        else -> colors.contentColor
    }
    val supportingText = when {
        !enabled -> colors.disabledSupportingTextColor
        else -> colors.supportingTextColor
    }
    val border = when {
        focused && interactive -> colors.focusedBorderColor
        else -> Color.Transparent
    }
    val borderWidth = when {
        focused && interactive -> ListItemMetrics.FocusBorderWidth
        else -> 0.dp
    }

    return ListItemVisuals(
        container = container,
        content = content,
        supportingText = supportingText,
        border = border,
        borderWidth = borderWidth,
    )
}

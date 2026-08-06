package com.elegant.compose.ui.listpopup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
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
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantElevation
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * One option offered by [ElegantListPopup].
 *
 * [text] is rendered in the option row, [value] is the stable identity compared against the
 * popup's selected value and delivered with the selection, and [enabled] marks options
 * that cannot be chosen. Disabled options render with the tertiary text color, ignore clicks, and
 * announce the disabled state through semantics.
 *
 * @property text text rendered in the option row.
 * @property value stable identity compared against the selection and delivered with it.
 * @property enabled whether the option can be chosen.
 */
@Immutable
public data class ElegantListPopupOption(
    val text: String,
    val value: String,
    val enabled: Boolean = true,
)

/**
 * Theme-aware surface colors used by [ElegantListPopup].
 *
 * Use [ElegantListPopupDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor popup surface background.
 * @property contentColor text color of enabled, unselected options.
 * @property disabledContentColor text color of disabled options.
 * @property selectedContentColor text and check color of the selected option.
 * @property selectedContainerColor background of the selected option.
 * @property hoveredContainerColor background of hovered options.
 * @property borderColor popup surface border color.
 */
@Immutable
public data class ElegantListPopupColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContentColor: Color,
    val selectedContentColor: Color,
    val selectedContainerColor: Color,
    val hoveredContainerColor: Color,
    val borderColor: Color,
)

/** Defaults shared by Elegant UI list popup APIs. */
public object ElegantListPopupDefaults {
    /** Minimum popup width; the surface grows to fit the widest option. */
    public val MinWidth: Dp = 160.dp

    /** Maximum popup height before the option list starts scrolling. */
    public val MaxHeight: Dp = 320.dp

    /** Height of one option row. */
    public val ItemHeight: Dp = 40.dp

    /** Horizontal padding inside every option row. */
    public val HorizontalPadding: Dp = 16.dp

    /** Duration of the popup entrance transition. */
    public const val AnimationDurationMillis: Int = ElegantMotion.fastDurationMillis

    /** Gap between the anchored Box and the popup surface. */
    internal val AnchorOffset: Dp = 4.dp

    /** Size of the trailing check glyph on the selected option. */
    internal val CheckGlyphSize: Dp = 18.dp

    /** Default popup surface colors resolved from the active [ElegantTheme]. */
    @Composable
    public fun colors(): ElegantListPopupColors = resolveListPopupColors(ElegantTheme.colors)
}

/**
 * Shows a data-driven single-choice option list on a temporary surface anchored below its anchor
 * Box.
 *
 * The caller owns the trigger and places [ElegantListPopup] inside the same Box as the trigger.
 * The popup anchors to that Box: the surface drops below its bottom edge, start-aligned, and is
 * clamped into the window. The caller controls the anchor Box by sizing the Box that wraps the
 * trigger; wrapping only the trigger keeps the anchor exactly on the trigger.
 *
 * The platform popup dismisses the popup on outside click, Escape, or the platform back gesture,
 * invoking [onDismissRequest]. Because the popup is focusable, keyboard focus moves into the list
 * when it opens and returns to the trigger when it dismisses; focused options activate with Enter
 * or Space through the option's clickable semantics. Selecting an option invokes
 * [onOptionSelected] with that option and nothing else: the caller owns [expanded] and decides in
 * the callback whether the popup stays open.
 *
 * The surface fades in over [ElegantListPopupDefaults.AnimationDurationMillis], is rounded with
 * [ElegantRadius.md], painted with a 1dp [ElegantListPopupColors.borderColor] border, casts a
 * medium shadow, and scrolls its options once content exceeds
 * [ElegantListPopupDefaults.MaxHeight]. The option matching [selectedValue] renders with the
 * selected content color, a subtle selected background, and a trailing check glyph; hovered
 * options show the hovered background. Each option carries [Role.Button] semantics with the
 * selected and disabled states announced.
 *
 * @param expanded whether the popup surface is shown.
 * @param onDismissRequest called when the user requests dismissal, such as by tapping outside or
 *   pressing Escape.
 * @param options option list rendered in the popup, in the given order.
 * @param selectedValue value of the currently selected option, matched against
 *   [ElegantListPopupOption.value]; null renders no option as selected.
 * @param onOptionSelected called with the option chosen by the user; the popup does not dismiss
 *   itself, the caller owns [expanded].
 * @param modifier modifier applied to the scrollable option column inside the surface.
 * @param colors popup surface and option colors.
 */
@Composable
public fun ElegantListPopup(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    options: List<ElegantListPopupOption>,
    selectedValue: String?,
    onOptionSelected: (ElegantListPopupOption) -> Unit,
    modifier: Modifier = Modifier,
    colors: ElegantListPopupColors = ElegantListPopupDefaults.colors(),
) {
    val density = LocalDensity.current
    val offsetPx = with(density) { ElegantListPopupDefaults.AnchorOffset.roundToPx() }
    val positionProvider = remember(offsetPx) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset = listPopupPosition(
                anchorBounds = anchorBounds,
                popupSize = popupContentSize,
                offsetPx = offsetPx,
                windowSize = windowSize,
            )
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
                        durationMillis = ElegantListPopupDefaults.AnimationDurationMillis,
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
                        .border(
                            border = BorderStroke(1.dp, colors.borderColor),
                            shape = shape,
                        )
                        .widthIn(min = ElegantListPopupDefaults.MinWidth),
                ) {
                    Column(
                        modifier = modifier
                            .width(IntrinsicSize.Max)
                            .heightIn(max = ElegantListPopupDefaults.MaxHeight)
                            .verticalScroll(rememberScrollState()),
                    ) {
                        options.forEach { option ->
                            ListPopupOptionRow(
                                option = option,
                                selectedValue = selectedValue,
                                colors = colors,
                                onClick = { onOptionSelected(option) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ListPopupOptionRow(
    option: ElegantListPopupOption,
    selectedValue: String?,
    colors: ElegantListPopupColors,
    onClick: () -> Unit,
) {
    val isOptionSelected = isSelected(selectedValue, option.value)
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val background by animateColorAsState(
        targetValue = listPopupItemBackground(
            colors = colors,
            enabled = option.enabled,
            hovered = hovered,
            selected = isOptionSelected,
        ),
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantListPopupItemBackground",
    )
    val contentColor = listPopupItemTextColor(
        colors = colors,
        enabled = option.enabled,
        selected = isOptionSelected,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = ElegantListPopupDefaults.ItemHeight)
            .background(background)
            .clickable(
                enabled = option.enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick,
            )
            .semantics {
                role = Role.Button
                selected = isOptionSelected
                if (!option.enabled) disabled()
            }
            .padding(horizontal = ElegantListPopupDefaults.HorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = option.text,
            modifier = Modifier.weight(1f),
            color = contentColor,
            style = ElegantTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        if (isOptionSelected) {
            Spacer(Modifier.width(ElegantSpacing.md))
            ListPopupCheckMark(color = colors.selectedContentColor)
        }
    }
}

@Composable
private fun ListPopupCheckMark(color: Color) {
    Canvas(modifier = Modifier.size(ElegantListPopupDefaults.CheckGlyphSize)) {
        val strokeWidth = 2.dp.toPx()
        val path = Path().apply {
            moveTo(size.width * 0.20f, size.height * 0.52f)
            lineTo(size.width * 0.44f, size.height * 0.74f)
            lineTo(size.width * 0.82f, size.height * 0.26f)
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            ),
        )
    }
}

/** Whether [optionValue] is the currently selected option value. */
internal fun isSelected(selectedValue: String?, optionValue: String): Boolean =
    selectedValue != null && selectedValue == optionValue

/** Resolves the option text color for the disabled and selected state precedence. */
internal fun listPopupItemTextColor(
    colors: ElegantListPopupColors,
    enabled: Boolean,
    selected: Boolean,
): Color = when {
    !enabled -> colors.disabledContentColor
    selected -> colors.selectedContentColor
    else -> colors.contentColor
}

/** Resolves the option background for the disabled, hovered, and selected state precedence. */
internal fun listPopupItemBackground(
    colors: ElegantListPopupColors,
    enabled: Boolean,
    hovered: Boolean,
    selected: Boolean,
): Color = when {
    !enabled -> Color.Transparent
    hovered -> colors.hoveredContainerColor
    selected -> colors.selectedContainerColor
    else -> Color.Transparent
}

/** Places the popup surface below the anchor, start-aligned, clamped into the window. */
internal fun listPopupPosition(
    anchorBounds: IntRect,
    popupSize: IntSize,
    offsetPx: Int,
    windowSize: IntSize,
): IntOffset = IntOffset(
    x = anchorBounds.left.coerceIn(0, (windowSize.width - popupSize.width).coerceAtLeast(0)),
    y = (anchorBounds.top + anchorBounds.height + offsetPx)
        .coerceIn(0, (windowSize.height - popupSize.height).coerceAtLeast(0)),
)

/** Resolves the theme-aware list popup colors from [themeColors]. */
internal fun resolveListPopupColors(themeColors: ElegantColors): ElegantListPopupColors =
    ElegantListPopupColors(
        containerColor = themeColors.surfaceRaised,
        contentColor = themeColors.textPrimary,
        disabledContentColor = themeColors.textTertiary,
        selectedContentColor = themeColors.interactivePrimary,
        selectedContainerColor = themeColors.backgroundSubtle,
        hoveredContainerColor = themeColors.surfaceHover,
        borderColor = themeColors.borderDefault,
    )

package com.elegant.compose.ui.datepicker

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.error
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
import com.elegant.compose.ui.calendar.ElegantCalendar
import com.elegant.compose.ui.calendar.ElegantDate
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import kotlin.math.roundToInt

/**
 * Theme-aware state colors used by [ElegantDatePicker].
 *
 * Use [ElegantDatePickerDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor default container color.
 * @property hoveredContainerColor hovered container color.
 * @property focusedContainerColor focused container color.
 * @property disabledContainerColor disabled container color.
 * @property borderColor default border color.
 * @property hoveredBorderColor hovered border color.
 * @property focusedBorderColor focused border color.
 * @property errorBorderColor error border color.
 * @property disabledBorderColor disabled border color.
 * @property contentColor default text color.
 * @property disabledContentColor disabled text color.
 * @property placeholderColor placeholder text color.
 * @property labelColor label text color.
 * @property supportingTextColor supporting text color.
 * @property errorTextColor error text color.
 */
@Immutable
public data class ElegantDatePickerColors(
    val containerColor: Color,
    val hoveredContainerColor: Color,
    val focusedContainerColor: Color,
    val disabledContainerColor: Color,
    val borderColor: Color,
    val hoveredBorderColor: Color,
    val focusedBorderColor: Color,
    val errorBorderColor: Color,
    val disabledBorderColor: Color,
    val contentColor: Color,
    val disabledContentColor: Color,
    val placeholderColor: Color,
    val labelColor: Color,
    val supportingTextColor: Color,
    val errorTextColor: Color,
)

/** Theme-aware defaults for [ElegantDatePicker]. */
public object ElegantDatePickerDefaults {
    /** Minimum field-container height kept by the date picker field. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Returns theme-aware Filled field colors. */
    @Composable
    public fun colors(): ElegantDatePickerColors = resolveDatePickerColors(ElegantTheme.colors)
}

/** Internal metrics shared by the date picker field and its calendar popup. */
internal object DatePickerMetrics {
    /** Standard state-transition duration. */
    const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Gap between the field container and the calendar popup. */
    val AnchorOffset: Dp = 4.dp

    /** Horizontal field padding. */
    val FieldHorizontalPadding: Dp = 14.dp

    /** Resting and hovered border stroke width. */
    val RestingBorderWidth: Dp = 1.dp

    /** Focused and error border stroke width. */
    val FocusBorderWidth: Dp = 2.dp

    /** Trailing calendar glyph edge length. */
    val CalendarGlyphSize: Dp = 18.dp
}

@Immutable
internal data class DatePickerVisuals(
    val container: Color,
    val border: Color,
    val borderWidth: Dp,
)

/**
 * Displays a read-only date field that opens a calendar popup below it.
 *
 * The field follows the Filled input rhythm: an optional label above, a 48dp sunken container that
 * lifts on hover and focus, a 2dp focus ring while focused or a 2dp critical border while in
 * error, and supporting or error text below. The chosen date renders as a zero-padded YYYY-MM-DD
 * string, or the placeholder while the field is enabled and no date is chosen. The field is never
 * editable: clicking it opens the popup.
 *
 * Clicking the field shows [ElegantCalendar] in an anchored, focusable popup directly below the
 * field, start-aligned and clamped to the window. The popup starts from [date], then [minDate].
 * Selecting a day invokes [onDateSelected] and closes the popup; clicking outside or pressing
 * Escape also closes it, both gestures being delivered by the platform popup. The popup does not
 * close when the field loses focus. When [enabled] is false the field renders dimmed, never opens
 * the popup, and never invokes [onDateSelected].
 *
 * Semantics: the field exposes [Role.Button] with the formatted date, or "Pick a date" while
 * empty, as its content description; it announces the error text when [isError] and [errorText]
 * are set, and announces the disabled state when [enabled] is false.
 *
 * @param date the selected date, or null while empty; owned by the caller.
 * @param onDateSelected callback invoked when a day is chosen in the popup.
 * @param modifier modifier applied once to the component root.
 * @param enabled whether the field can open the popup and choose dates.
 * @param label optional label shown above the field.
 * @param placeholder optional hint shown inside the field while it is enabled and empty.
 * @param isError whether the field communicates an error state.
 * @param errorText optional error message shown below the field and announced through semantics
 *   when [isError].
 * @param supportingText optional guidance shown below the field unless error text is shown.
 * @param minDate earliest selectable date, or null for no lower bound.
 * @param maxDate latest selectable date, or null for no upper bound.
 * @param colors theme-aware state colors.
 */
@Composable
public fun ElegantDatePicker(
    date: ElegantDate?,
    onDateSelected: (ElegantDate) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    errorText: String? = null,
    supportingText: String? = null,
    minDate: ElegantDate? = null,
    maxDate: ElegantDate? = null,
    colors: ElegantDatePickerColors = ElegantDatePickerDefaults.colors(),
) {
    val resolvedLabel = label?.takeIf { it.isNotBlank() }
    val resolvedErrorText = if (isError && !errorText.isNullOrBlank()) errorText else null
    val shape = RoundedCornerShape(ElegantRadius.md)
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val visuals = resolveDatePickerVisuals(
        colors = colors,
        enabled = enabled,
        hovered = hovered,
        focused = focused,
        isError = isError,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = tween(
            durationMillis = DatePickerMetrics.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantDatePickerContainer",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = tween(
            durationMillis = DatePickerMetrics.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantDatePickerBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = tween(
            durationMillis = DatePickerMetrics.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantDatePickerBorderWidth",
    )
    val animatedContent by animateColorAsState(
        targetValue = if (enabled) colors.contentColor else colors.disabledContentColor,
        animationSpec = tween(
            durationMillis = DatePickerMetrics.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantDatePickerContent",
    )

    var open by remember { mutableStateOf(false) }
    var anchorBounds by remember { mutableStateOf(IntRect.Zero) }

    LaunchedEffect(enabled) {
        if (!enabled) open = false
    }

    val density = LocalDensity.current
    val offsetPx = with(density) { DatePickerMetrics.AnchorOffset.roundToPx() }
    val positionProvider = remember(offsetPx) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset = datePickerPopupPosition(
                anchorBounds = anchorBounds,
                popupSize = popupContentSize,
                offsetPx = offsetPx,
                windowSize = windowSize,
            )
        }
    }

    val fieldText = fieldLabel(date)
    val glyphColor = if (enabled) ElegantTheme.colors.textSecondary else colors.disabledContentColor
    val helperText = resolvedErrorText ?: supportingText

    Column(modifier = modifier) {
        if (resolvedLabel != null) {
            Text(
                text = resolvedLabel,
                modifier = Modifier.fillMaxWidth(),
                color = colors.labelColor,
                style = ElegantTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(ElegantSpacing.md))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { position ->
                    anchorBounds = position.boundsInWindow().let { bounds ->
                IntRect(
                    left = bounds.left.roundToInt(),
                    top = bounds.top.roundToInt(),
                    right = bounds.right.roundToInt(),
                    bottom = bounds.bottom.roundToInt(),
                )
            }
                },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = ElegantDatePickerDefaults.MinimumTouchHeight)
                    .semantics(mergeDescendants = true) {
                        role = Role.Button
                        contentDescription = fieldText ?: "Pick a date"
                        if (!enabled) disabled()
                        if (resolvedErrorText != null) error(resolvedErrorText)
                    }
                    .clip(shape)
                    .background(animatedContainer)
                    .border(
                        border = BorderStroke(animatedBorderWidth, animatedBorder),
                        shape = shape,
                    )
                    .focusable(
                        interactionSource = interactionSource,
                        enabled = enabled,
                    )
                    .hoverable(
                        interactionSource = interactionSource,
                        enabled = enabled,
                    )
                    .clickable(
                        enabled = enabled,
                        role = Role.Button,
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { open = true },
                    )
                    .padding(horizontal = DatePickerMetrics.FieldHorizontalPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (fieldText != null) {
                    Text(
                        text = fieldText,
                        modifier = Modifier
                            .weight(1f)
                            .clearAndSetSemantics { },
                        color = animatedContent,
                        style = ElegantTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                } else if (enabled && placeholder != null) {
                    Text(
                        text = placeholder,
                        modifier = Modifier
                            .weight(1f)
                            .clearAndSetSemantics { },
                        color = colors.placeholderColor,
                        style = ElegantTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                } else {
                    Spacer(Modifier.weight(1f))
                }

                Spacer(Modifier.width(ElegantSpacing.md))
                DatePickerCalendarGlyph(color = glyphColor)
            }

            if (open && enabled) {
                Popup(
                    popupPositionProvider = positionProvider,
                    onDismissRequest = { open = false },
                    properties = PopupProperties(focusable = true),
                ) {
                    ElegantCalendar(
                        selectedDate = date,
                        onDateSelected = { selected ->
                            onDateSelected(selected)
                            open = false
                        },
                        minDate = minDate,
                        maxDate = maxDate,
                        initialMonth = date ?: minDate ?: null,
                    )
                }
            }
        }

        if (helperText != null) {
            Spacer(Modifier.height(ElegantSpacing.sm))
            Text(
                text = helperText,
                modifier = Modifier.fillMaxWidth(),
                color = if (resolvedErrorText != null) {
                    colors.errorTextColor
                } else {
                    colors.supportingTextColor
                },
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun DatePickerCalendarGlyph(color: Color) {
    Canvas(modifier = Modifier.size(DatePickerMetrics.CalendarGlyphSize)) {
        val strokeWidth = 1.5.dp.toPx()
        val topInset = 2.5.dp.toPx()
        drawRoundRect(
            color = color,
            topLeft = Offset(strokeWidth / 2f, topInset),
            size = Size(size.width - strokeWidth, size.height - topInset - strokeWidth / 2f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx()),
            style = Stroke(width = strokeWidth),
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.25f, 0f),
            end = Offset(size.width * 0.25f, topInset),
            strokeWidth = strokeWidth,
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.75f, 0f),
            end = Offset(size.width * 0.75f, topInset),
            strokeWidth = strokeWidth,
        )
    }
}

/** Resolves the theme-aware Filled field colors from [themeColors]. */
internal fun resolveDatePickerColors(themeColors: ElegantColors): ElegantDatePickerColors =
    ElegantDatePickerColors(
        containerColor = themeColors.surfaceSunken,
        hoveredContainerColor = themeColors.surfaceHover,
        focusedContainerColor = themeColors.surfaceRaised,
        disabledContainerColor = themeColors.surfaceSunken,
        borderColor = Color.Transparent,
        hoveredBorderColor = Color.Transparent,
        focusedBorderColor = themeColors.focusRing,
        errorBorderColor = themeColors.statusCritical,
        disabledBorderColor = Color.Transparent,
        contentColor = themeColors.textPrimary,
        disabledContentColor = themeColors.textTertiary,
        placeholderColor = themeColors.textTertiary,
        labelColor = themeColors.textSecondary,
        supportingTextColor = themeColors.textSecondary,
        errorTextColor = themeColors.statusCritical,
    )

/** Resolves the field container, border, and border-width for the interaction state precedence. */
internal fun resolveDatePickerVisuals(
    colors: ElegantDatePickerColors,
    enabled: Boolean,
    hovered: Boolean,
    focused: Boolean,
    isError: Boolean,
): DatePickerVisuals {
    val container = when {
        !enabled -> colors.disabledContainerColor
        focused -> colors.focusedContainerColor
        hovered -> colors.hoveredContainerColor
        else -> colors.containerColor
    }
    val border = when {
        !enabled -> colors.disabledBorderColor
        isError -> colors.errorBorderColor
        focused -> colors.focusedBorderColor
        hovered -> colors.hoveredBorderColor
        else -> colors.borderColor
    }
    val borderWidth = when {
        !enabled -> DatePickerMetrics.RestingBorderWidth
        isError -> DatePickerMetrics.FocusBorderWidth
        focused -> DatePickerMetrics.FocusBorderWidth
        else -> DatePickerMetrics.RestingBorderWidth
    }
    return DatePickerVisuals(
        container = container,
        border = border,
        borderWidth = borderWidth,
    )
}

/** Formats [date] as a zero-padded YYYY-MM-DD string. */
internal fun formatDateCopy(date: ElegantDate): String =
    date.year.toString().padStart(4, '0') +
        "-" + date.month.coerceIn(1, 12).toString().padStart(2, '0') +
        "-" + date.day.toString().padStart(2, '0')

/** Returns the field text for [date], or null while no date is chosen. */
internal fun fieldLabel(date: ElegantDate?): String? = date?.let(::formatDateCopy)

/** Places the calendar popup below the anchor, start-aligned, clamped into the window. */
internal fun datePickerPopupPosition(
    anchorBounds: IntRect,
    popupSize: IntSize,
    offsetPx: Int,
    windowSize: IntSize,
): IntOffset = IntOffset(
    x = anchorBounds.left.coerceIn(0, (windowSize.width - popupSize.width).coerceAtLeast(0)),
    y = (anchorBounds.top + anchorBounds.height + offsetPx)
        .coerceIn(0, (windowSize.height - popupSize.height).coerceAtLeast(0)),
)

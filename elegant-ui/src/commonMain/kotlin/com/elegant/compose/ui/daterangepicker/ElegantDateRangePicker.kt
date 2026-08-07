package com.elegant.compose.ui.daterangepicker

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
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
import com.elegant.compose.ui.calendar.ElegantCalendar
import com.elegant.compose.ui.calendar.ElegantCalendarDefaults
import com.elegant.compose.ui.calendar.ElegantDate
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantElevation
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * An immutable date-range model used by [ElegantDateRangePicker].
 *
 * A range with both [start] and [end] null is empty; a range with a non-null [start] and a null
 * [end] is in progress and awaits its end date. A range with a null [start] and a non-null [end]
 * is invalid and must not be constructed; [ElegantDateRangePicker] never emits one.
 *
 * @property start the earlier endpoint, or null when the range is empty or in progress.
 * @property end the later endpoint, or null when the range is empty or in progress.
 */
@Immutable
public data class ElegantDateRange(
    val start: ElegantDate?,
    val end: ElegantDate?,
)

/**
 * Theme-aware state colors used by [ElegantDateRangePicker].
 *
 * Use [ElegantDateRangePickerDefaults.colors] for theme-aware defaults, then use [copy] for
 * supported product-level customization.
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
public data class ElegantDateRangePickerColors(
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

/** Theme-aware defaults for [ElegantDateRangePicker]. */
public object ElegantDateRangePickerDefaults {
    /** Minimum field-container height kept by the date-range picker. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Returns theme-aware colors for the picker field. */
    @Composable
    public fun colors(): ElegantDateRangePickerColors = resolveDateRangePickerColors(ElegantTheme.colors)
}

/** Internal metrics shared by the date-range-picker field and its calendar panel. */
internal object DateRangePickerMetrics {
    /** Standard state-transition duration. */
    const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Gap between the field container and the calendar panel. */
    val AnchorOffset: Dp = 4.dp

    /** Horizontal field padding. */
    val FieldHorizontalPadding: Dp = 14.dp

    /** Resting and hovered border stroke width. */
    val RestingBorderWidth: Dp = 1.dp

    /** Focused and error border stroke width. */
    val FocusBorderWidth: Dp = 2.dp

    /** Gap between the two month grids inside the panel. */
    val CalendarGap: Dp = ElegantSpacing.md
}

@Immutable
internal data class DateRangePickerVisuals(
    val container: Color,
    val border: Color,
    val borderWidth: Dp,
)

/**
 * Displays a read-only date-range field that opens a two-month calendar panel.
 *
 * [range] is the controlled selection model: the caller owns it and must write the newest value
 * back from [onRangeSelected], which fires after every day click. The field follows the Filled
 * input rhythm: a sunken 48dp container with a 12dp rounding that paints a 2dp
 * [ElegantTheme.colors.focusRing] border while focused or while the panel is open, a 2dp
 * [ElegantTheme.colors.statusCritical] border while in error, and a transparent 1dp border
 * otherwise. It shows the formatted "start — end" readout, or [placeholder] while it is enabled
 * and empty.
 *
 * Clicking the field opens an anchored popup holding two [ElegantCalendar] grids side by side.
 * The visible pair starts at the month of the current start date, then [minDate], then January
 * 2000 — the library has no clock in `commonMain` — and a header row above the grids steps the
 * pair backward and forward by one month, clamped to the months of [minDate] and [maxDate].
 * Every click on a selectable day advances the range: the first click sets the start, the second
 * click sets the end, a second click before the start moves the start and clears the end so the
 * range can be re-picked, and a click after a complete range starts a new one. The panel stays
 * open after a selection so both endpoints can be adjusted; it closes on an outside click or
 * Escape, both delivered by the platform popup. Days between the endpoints are not tinted in
 * this version: the range is communicated by the two selected endpoints and the field readout.
 *
 * Days outside [minDate]..[maxDate] are disabled by the calendars and never invoke the callback.
 * When [enabled] is false the field dims, rejects clicks and focus, and never opens the panel.
 *
 * Semantics: the field exposes [Role.Button] with a content description built from the readout,
 * or "Pick a date range" when it is empty; the error text is announced through semantics when
 * [isError], and the disabled state when [enabled] is false.
 *
 * @param range the current selection, owned by the caller.
 * @param onRangeSelected callback invoked with the newest accepted range after every day click.
 * @param modifier modifier applied once to the picker root.
 * @param enabled whether the field accepts clicks and focus and the panel can open.
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
public fun ElegantDateRangePicker(
    range: ElegantDateRange,
    onRangeSelected: (ElegantDateRange) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    errorText: String? = null,
    supportingText: String? = null,
    minDate: ElegantDate? = null,
    maxDate: ElegantDate? = null,
    colors: ElegantDateRangePickerColors = ElegantDateRangePickerDefaults.colors(),
) {
    val resolvedLabel = label?.takeIf { it.isNotBlank() }
    val resolvedErrorText = if (isError && !errorText.isNullOrBlank()) errorText else null
    val shape = RoundedCornerShape(ElegantRadius.md)
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val focusRingEnabled = ElegantTheme.focusRingEnabled
    var expanded by remember { mutableStateOf(false) }
    val currentRange by rememberUpdatedState(range)
    val currentOnRangeSelected by rememberUpdatedState(onRangeSelected)
    val visuals = resolveDateRangePickerVisuals(
        colors = colors,
        enabled = enabled,
        hovered = hovered,
        focused = (focused || expanded) && focusRingEnabled,
        isError = isError,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantDateRangePickerContainer",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantDateRangePickerBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantDateRangePickerBorderWidth",
    )
    val animatedContent by animateColorAsState(
        targetValue = if (enabled) colors.contentColor else colors.disabledContentColor,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantDateRangePickerContent",
    )

    LaunchedEffect(enabled) {
        if (!enabled) expanded = false
    }

    val density = LocalDensity.current
    val offsetPx = with(density) { DateRangePickerMetrics.AnchorOffset.roundToPx() }
    val positionProvider = remember(offsetPx) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset = IntOffset(
                x = anchorBounds.left.coerceIn(0, (windowSize.width - popupContentSize.width).coerceAtLeast(0)),
                y = (anchorBounds.top + anchorBounds.height + offsetPx)
                    .coerceIn(0, (windowSize.height - popupContentSize.height).coerceAtLeast(0)),
            )
        }
    }

    val readout = rangeLabel(range)
    val showPlaceholder = enabled && readout == null && !placeholder.isNullOrBlank()
    val displayText = readout ?: if (showPlaceholder) placeholder else ""
    val displayColor = if (readout != null) animatedContent else colors.placeholderColor
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

        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = ElegantDateRangePickerDefaults.MinimumTouchHeight)
                    .semantics(mergeDescendants = true) {
                        role = Role.Button
                        contentDescription = readout ?: "Pick a date range"
                        if (!enabled) disabled()
                        if (resolvedErrorText != null) error(resolvedErrorText)
                    }
                    .clip(shape)
                    .background(animatedContainer)
                    .border(
                        border = BorderStroke(animatedBorderWidth, animatedBorder),
                        shape = shape,
                    )
                    .clickable(
                        enabled = enabled,
                        interactionSource = interactionSource,
                        indication = LocalIndication.current,
                        onClick = { expanded = true },
                    )
                    .padding(horizontal = DateRangePickerMetrics.FieldHorizontalPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = displayText,
                    modifier = Modifier.weight(1f),
                    color = displayColor,
                    style = ElegantTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            if (expanded && enabled) {
                Popup(
                    popupPositionProvider = positionProvider,
                    onDismissRequest = { expanded = false },
                    properties = PopupProperties(focusable = true),
                ) {
                    RangePickerPanel(
                        range = currentRange,
                        onDateSelected = { date ->
                            currentOnRangeSelected(advanceRange(currentRange, date))
                        },
                        minDate = minDate,
                        maxDate = maxDate,
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
private fun RangePickerPanel(
    range: ElegantDateRange,
    onDateSelected: (ElegantDate) -> Unit,
    minDate: ElegantDate?,
    maxDate: ElegantDate?,
) {
    val themeColors = ElegantTheme.colors
    val shape = RoundedCornerShape(ElegantRadius.md)
    val minIndex = minDate?.let { monthIndex(it.year, it.month) }
    val maxIndex = maxDate?.let { monthIndex(it.year, it.month) }
    val initialIndex = clampPairIndex(
        index = range.start?.let { monthIndex(it.year, it.month) }
            ?: minDate?.let { monthIndex(it.year, it.month) }
            ?: monthIndex(2000, 1),
        minIndex = minIndex,
        maxIndex = maxIndex,
    )
    var firstMonthIndex by remember { mutableStateOf(initialIndex) }
    val firstMonth = dateFromMonthIndex(firstMonthIndex)
    val secondMonth = nextMonth(firstMonth.first, firstMonth.second)
    val canGoBack = canNavigatePairBack(firstMonthIndex, minIndex)
    val canGoForward = canNavigatePairForward(firstMonthIndex, maxIndex)
    val focusRequester = remember { FocusRequester() }
    val calendarColors = ElegantCalendarDefaults.colors()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .focusRequester(focusRequester)
            .focusable()
            .shadow(
                elevation = ElegantElevation.medium,
                shape = shape,
                clip = false,
            )
            .clip(shape)
            .background(themeColors.surfaceRaised)
            .border(
                border = BorderStroke(DateRangePickerMetrics.RestingBorderWidth, themeColors.borderDefault),
                shape = shape,
            )
            .padding(ElegantSpacing.md),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RangePairNavButton(
                direction = -1,
                onClick = {
                    firstMonthIndex = clampPairIndex(firstMonthIndex - 1, minIndex, maxIndex)
                },
                enabled = canGoBack,
                contentDescription = "Previous month pair",
                color = if (canGoBack) themeColors.textPrimary else themeColors.textTertiary,
            )
            Text(
                text = formatMonthCopy(firstMonth.first, firstMonth.second) +
                    " — " +
                    formatMonthCopy(secondMonth.first, secondMonth.second),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = themeColors.textPrimary,
                style = ElegantTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            RangePairNavButton(
                direction = 1,
                onClick = {
                    firstMonthIndex = clampPairIndex(firstMonthIndex + 1, minIndex, maxIndex)
                },
                enabled = canGoForward,
                contentDescription = "Next month pair",
                color = if (canGoForward) themeColors.textPrimary else themeColors.textTertiary,
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(DateRangePickerMetrics.CalendarGap)) {
            ElegantCalendar(
                selectedDate = range.start,
                onDateSelected = onDateSelected,
                initialMonth = ElegantDate(firstMonth.first, firstMonth.second, 1),
                minDate = minDate,
                maxDate = maxDate,
                colors = calendarColors,
            )
            ElegantCalendar(
                selectedDate = range.start,
                onDateSelected = onDateSelected,
                initialMonth = ElegantDate(secondMonth.first, secondMonth.second, 1),
                minDate = minDate,
                maxDate = maxDate,
                colors = calendarColors,
            )
        }
    }
}

@Composable
private fun RangePairNavButton(
    direction: Int,
    onClick: () -> Unit,
    enabled: Boolean,
    contentDescription: String,
    color: Color,
) {
    Box(
        modifier = Modifier
            .size(ElegantCalendarDefaults.NavigationSize)
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onClick,
            )
            .semantics {
                role = Role.Button
                this.contentDescription = contentDescription
                if (!enabled) disabled()
            },
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.size(18.dp)) {
            val centerY = size.height / 2f
            val half = 4f
            val startX = if (direction < 0) size.width / 2f + half else size.width / 2f - half
            val endX = if (direction < 0) size.width / 2f - half else size.width / 2f + half
            drawLine(
                color = color,
                start = Offset(startX, centerY - half),
                end = Offset(endX, centerY),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
            drawLine(
                color = color,
                start = Offset(startX, centerY + half),
                end = Offset(endX, centerY),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
        }
    }
}

/** Resolves the theme-aware filled field colors from [themeColors]. */
internal fun resolveDateRangePickerColors(themeColors: ElegantColors): ElegantDateRangePickerColors =
    ElegantDateRangePickerColors(
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
internal fun resolveDateRangePickerVisuals(
    colors: ElegantDateRangePickerColors,
    enabled: Boolean,
    hovered: Boolean,
    focused: Boolean,
    isError: Boolean,
): DateRangePickerVisuals {
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
        !enabled -> DateRangePickerMetrics.RestingBorderWidth
        isError -> DateRangePickerMetrics.FocusBorderWidth
        focused -> DateRangePickerMetrics.FocusBorderWidth
        else -> DateRangePickerMetrics.RestingBorderWidth
    }
    return DateRangePickerVisuals(
        container = container,
        border = border,
        borderWidth = borderWidth,
    )
}

/** Formats [date] as a zero-padded "yyyy-MM-dd" string. */
internal fun formatDateCopy(date: ElegantDate): String =
    date.year.toString().padStart(4, '0') +
        "-" + date.month.coerceIn(1, 12).toString().padStart(2, '0') +
        "-" + date.day.toString().padStart(2, '0')

/** Builds the field readout for [range]: null when empty, otherwise a "start — end" text. */
internal fun rangeLabel(range: ElegantDateRange): String? {
    val start = range.start
    val end = range.end
    return when {
        start == null && end == null -> null
        end == null -> formatDateCopy(start!!) + " —"
        start == null -> "— " + formatDateCopy(end!!)
        else -> formatDateCopy(start) + " — " + formatDateCopy(end)
    }
}

/** Whether [range] has both endpoints and is ready to be submitted. */
internal fun isComplete(range: ElegantDateRange): Boolean = range.start != null && range.end != null

/** Advances [current] by one day click: first click sets the start, later clicks set or re-pick the end. */
internal fun advanceRange(current: ElegantDateRange, picked: ElegantDate): ElegantDateRange {
    val start = current.start
    val end = current.end
    return when {
        start == null || end != null -> ElegantDateRange(start = picked, end = null)
        picked < start -> ElegantDateRange(start = picked, end = null)
        else -> ElegantDateRange(start = start, end = picked)
    }
}

/** The first month of the pair one month ahead of [firstMonth]. */
internal fun nextPairMonth(firstMonth: Pair<Int, Int>): Pair<Int, Int> =
    nextMonth(firstMonth.first, firstMonth.second)

/** The first month of the pair one month behind [firstMonth]. */
internal fun prevPairMonth(firstMonth: Pair<Int, Int>): Pair<Int, Int> =
    previousMonth(firstMonth.first, firstMonth.second)

/** Clamps a pair's first month so the pair stays inside the months of [minIndex] and [maxIndex]. */
internal fun clampPairIndex(index: Int, minIndex: Int?, maxIndex: Int?): Int {
    var result = index
    if (minIndex != null && result < minIndex) result = minIndex
    if (maxIndex != null && result > maxIndex - 1) result = maxIndex - 1
    return result
}

/** Whether a pair starting at [index] can step one month backward without leaving [minIndex]. */
internal fun canNavigatePairBack(index: Int, minIndex: Int?): Boolean =
    minIndex == null || index - 1 >= minIndex

/** Whether a pair starting at [index] can step one month forward without leaving [maxIndex]. */
internal fun canNavigatePairForward(index: Int, maxIndex: Int?): Boolean =
    maxIndex == null || index + 2 <= maxIndex

private fun monthIndex(year: Int, month: Int): Int = year * 12 + month.coerceIn(1, 12) - 1

private fun dateFromMonthIndex(index: Int): Pair<Int, Int> {
    val year = index / 12
    val month = index % 12 + 1
    return year to month
}

private fun nextMonth(year: Int, month: Int): Pair<Int, Int> = when (month) {
    12 -> year + 1 to 1
    else -> year to month + 1
}

private fun previousMonth(year: Int, month: Int): Pair<Int, Int> = when (month) {
    1 -> year - 1 to 12
    else -> year to month - 1
}

private fun formatMonthCopy(year: Int, month: Int): String =
    year.toString().padStart(4, '0') + "-" + month.coerceIn(1, 12).toString().padStart(2, '0')

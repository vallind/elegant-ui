package com.elegant.compose.ui.calendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * A civil calendar date with an Int-based year, month (1..12), and day.
 *
 * The model is deliberately independent of platform date APIs so the calendar family works on
 * Android, Desktop JVM, and Web/Wasm from `commonMain`.
 */
@Immutable
public data class ElegantDate(
    val year: Int,
    val month: Int,
    val day: Int,
) : Comparable<ElegantDate> {

    override fun compareTo(other: ElegantDate): Int {
        val thisIndex = year * 372 + month * 31 + day
        val otherIndex = other.year * 372 + other.month * 31 + other.day
        return thisIndex.compareTo(otherIndex)
    }
}

/**
 * Theme-aware colors used by [ElegantCalendar].
 *
 * @property containerColor calendar container background.
 * @property headerColor month title and navigation color.
 * @property weekdayColor weekday header text color.
 * @property dayColor regular day text color.
 * @property selectedDayColor text color of the selected day.
 * @property selectedDayContainerColor selected day background.
 * @property todayColor reserved for a future clock-driven today marker.
 * @property disabledDayColor out-of-range and adjacent-month day color.
 * @property hoveredDayColor hovered selectable day background.
 * @property borderColor reserved outline color.
 */
@Immutable
public data class ElegantCalendarColors(
    val containerColor: Color,
    val headerColor: Color,
    val weekdayColor: Color,
    val dayColor: Color,
    val selectedDayColor: Color,
    val selectedDayContainerColor: Color,
    val todayColor: Color,
    val disabledDayColor: Color,
    val hoveredDayColor: Color,
    val borderColor: Color,
)

/** Defaults and theme-aware factories shared by the calendar family. */
public object ElegantCalendarDefaults {
    /** Weekday header row height. */
    public val WeekdayRowHeight: Dp = 32.dp

    /** Day cell edge length. */
    public val DayCellSize: Dp = 40.dp

    /** Horizontal gap between day cells. */
    public val DayCellGap: Dp = 4.dp

    /** Navigation button edge length. */
    public val NavigationSize: Dp = 40.dp

    /** Returns theme-aware calendar colors. */
    @Composable
    public fun colors(): ElegantCalendarColors = resolveCalendarColors(ElegantTheme.colors)
}

/**
 * Displays a single-month day grid with navigation and selection.
 *
 * The grid is Monday-first with 42 cells; adjacent-month days render dimmed and are not
 * selectable. Days outside [minDate]..[maxDate] are disabled. The library has no clock in
 * `commonMain`, so the initially visible month defaults to [initialMonth], then
 * [selectedDate], then [minDate], then January 2000; callers that need "this month" pass
 * their own [initialMonth].
 *
 * @param selectedDate currently selected date, or null.
 * @param onDateSelected callback invoked when a selectable day is chosen.
 * @param modifier modifier applied once to the calendar root.
 * @param enabled whether selection and navigation are accepted.
 * @param initialMonth month shown on first composition.
 * @param minDate earliest selectable date, or null for no lower bound.
 * @param maxDate latest selectable date, or null for no upper bound.
 * @param colors theme-aware calendar colors.
 */
@Composable
public fun ElegantCalendar(
    selectedDate: ElegantDate?,
    onDateSelected: (ElegantDate) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    initialMonth: ElegantDate? = null,
    minDate: ElegantDate? = null,
    maxDate: ElegantDate? = null,
    colors: ElegantCalendarColors = ElegantCalendarDefaults.colors(),
) {
    val initialIndex = initialMonth
        ?.let { monthIndex(it.year, it.month) }
        ?: selectedDate?.let { monthIndex(it.year, it.month) }
        ?: minDate?.let { monthIndex(it.year, it.month) }
        ?: monthIndex(2000, 1)
    var visibleMonthIndex by remember(initialIndex, minDate, maxDate) {
        mutableStateOf(initialIndex)
    }
    val (visibleYear, visibleMonth) = dateFromMonthIndex(visibleMonthIndex)
    val minIndex = minDate?.let { monthIndex(it.year, it.month) }
    val maxIndex = maxDate?.let { monthIndex(it.year, it.month) }
    val grid = dateGrid(visibleYear, visibleMonth)
    val daysInCurrent = daysInMonth(visibleYear, visibleMonth)

    Column(
        modifier = modifier
            .background(colors.containerColor)
            .padding(ElegantSpacing.md),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CalendarNavButton(
                direction = -1,
                onClick = {
                    val target = visibleMonthIndex - 1
                    visibleMonthIndex = clampMonthIndex(target, minIndex, maxIndex)
                },
                enabled = enabled && canNavigate(-1, visibleMonthIndex, minIndex, maxIndex),
                contentDescription = "Previous month",
                color = colors.headerColor,
            )
            Text(
                text = formatMonth(visibleYear, visibleMonth),
                color = colors.headerColor,
                style = ElegantTheme.typography.labelMedium,
            )
            CalendarNavButton(
                direction = 1,
                onClick = {
                    val target = visibleMonthIndex + 1
                    visibleMonthIndex = clampMonthIndex(target, minIndex, maxIndex)
                },
                enabled = enabled && canNavigate(1, visibleMonthIndex, minIndex, maxIndex),
                contentDescription = "Next month",
                color = colors.headerColor,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(ElegantCalendarDefaults.WeekdayRowHeight),
            horizontalArrangement = Arrangement.spacedBy(ElegantCalendarDefaults.DayCellGap, Alignment.CenterHorizontally),
        ) {
            for (weekday in WeekdayLabels) {
                Box(
                    modifier = Modifier.size(ElegantCalendarDefaults.DayCellSize),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = weekday,
                        color = colors.weekdayColor,
                        style = ElegantTheme.typography.labelSmall,
                    )
                }
            }
        }

        for (week in 0 until 6) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantCalendarDefaults.DayCellGap, Alignment.CenterHorizontally),
            ) {
                for (column in 0 until 7) {
                    val rawDay = grid[week * 7 + column]
                    val date = gridDayDate(visibleYear, visibleMonth, rawDay)
                    val adjacent = isAdjacentMonth(rawDay, daysInCurrent)
                    val selectable = enabled && !adjacent && isInRange(date, minDate, maxDate)
                    CalendarDayCell(
                        date = date,
                        rawDay = rawDay,
                        adjacent = adjacent,
                        selectable = selectable,
                        selected = date == selectedDate,
                        colors = colors,
                        onClick = { onDateSelected(date) },
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarNavButton(
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
                start = androidx.compose.ui.geometry.Offset(startX, centerY - half),
                end = androidx.compose.ui.geometry.Offset(endX, centerY),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(startX, centerY + half),
                end = androidx.compose.ui.geometry.Offset(endX, centerY),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
        }
    }
}

@Composable
private fun CalendarDayCell(
    date: ElegantDate,
    rawDay: Int,
    adjacent: Boolean,
    selectable: Boolean,
    selected: Boolean,
    colors: ElegantCalendarColors,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val container = when {
        selected -> colors.selectedDayContainerColor
        hovered && selectable -> colors.hoveredDayColor
        else -> Color.Transparent
    }
    val content = when {
        selected -> colors.selectedDayColor
        adjacent || !selectable -> colors.disabledDayColor
        else -> colors.dayColor
    }

    Box(
        modifier = Modifier
            .size(ElegantCalendarDefaults.DayCellSize)
            .semantics(mergeDescendants = true) {
                role = Role.Button
                this.contentDescription = formatDate(date)
                this.selected = selected
                if (!selectable) disabled()
            }
            .clickable(
                enabled = selectable,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick,
            )
            .clip(CircleShape)
            .background(container),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = if (adjacent) {
                adjacentDayNumber(rawDay, daysInMonth(date.year, date.month))
            } else {
                rawDay.toString()
            },
            color = content,
            style = ElegantTheme.typography.labelMedium,
        )
    }
}

internal fun adjacentDayNumber(rawDay: Int, daysInAdjacentMonth: Int): String =
    if (rawDay <= 0) (rawDay + daysInAdjacentMonth).toString() else (rawDay - daysInAdjacentMonth).toString()

internal fun isLeapYear(year: Int): Boolean =
    year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)

internal fun daysInMonth(year: Int, month: Int): Int = when (month.coerceIn(1, 12)) {
    2 -> if (isLeapYear(year)) 29 else 28
    4, 6, 9, 11 -> 30
    else -> 31
}

internal fun daysSinceAnchor(year: Int, month: Int, day: Int): Int {
    var days = day - 1
    for (m in 1 until month.coerceIn(1, 12)) {
        days += daysInMonth(year, m)
    }
    for (y in 2000 until year) {
        days += if (isLeapYear(y)) 366 else 365
    }
    return days
}

internal fun firstWeekdayOffset(year: Int, month: Int): Int =
    (daysSinceAnchor(year, month, 1) + 5) % 7

internal fun dateGrid(year: Int, month: Int): List<Int> {
    val offset = firstWeekdayOffset(year, month)
    val dayCount = daysInMonth(year, month)
    val cells = mutableListOf<Int>()
    for (index in 0 until 42) {
        val raw = index - offset + 1
        cells.add(raw)
    }
    return cells
}

internal fun gridDayDate(year: Int, month: Int, rawDay: Int): ElegantDate {
    val dayCount = daysInMonth(year, month)
    return when {
        rawDay <= 0 -> {
            val previous = previousMonth(year, month)
            ElegantDate(previous.first, previous.second, rawDay + daysInMonth(previous.first, previous.second))
        }
        rawDay > dayCount -> {
            val next = nextMonth(year, month)
            ElegantDate(next.first, next.second, rawDay - dayCount)
        }
        else -> ElegantDate(year, month, rawDay)
    }
}

internal fun previousMonth(year: Int, month: Int): Pair<Int, Int> = when (month) {
    1 -> year - 1 to 12
    else -> year to month - 1
}

internal fun nextMonth(year: Int, month: Int): Pair<Int, Int> = when (month) {
    12 -> year + 1 to 1
    else -> year to month + 1
}

internal fun formatMonth(year: Int, month: Int): String =
    year.toString().padStart(4, '0') + "-" + month.coerceIn(1, 12).toString().padStart(2, '0')

internal fun formatDate(date: ElegantDate): String =
    date.year.toString().padStart(4, '0') +
        "-" + date.month.coerceIn(1, 12).toString().padStart(2, '0') +
        "-" + date.day.toString().padStart(2, '0')

internal fun monthIndex(year: Int, month: Int): Int = year * 12 + month.coerceIn(1, 12) - 1

internal fun dateFromMonthIndex(index: Int): Pair<Int, Int> {
    val year = index / 12
    val month = index % 12 + 1
    return year to month
}

internal fun clampMonthIndex(index: Int, minIndex: Int?, maxIndex: Int?): Int {
    var result = index
    if (minIndex != null && result < minIndex) result = minIndex
    if (maxIndex != null && result > maxIndex) result = maxIndex
    return result
}

internal fun canNavigate(direction: Int, currentIndex: Int, minIndex: Int?, maxIndex: Int?): Boolean {
    val target = currentIndex + direction
    if (minIndex != null && target < minIndex) return false
    if (maxIndex != null && target > maxIndex) return false
    return true
}

internal fun isInRange(date: ElegantDate, minDate: ElegantDate?, maxDate: ElegantDate?): Boolean {
    if (minDate != null && date < minDate) return false
    if (maxDate != null && date > maxDate) return false
    return true
}

internal fun isAdjacentMonth(rawDay: Int, daysInCurrent: Int): Boolean =
    rawDay <= 0 || rawDay > daysInCurrent

internal fun resolveCalendarColors(themeColors: com.elegant.compose.ui.foundation.theme.ElegantColors): ElegantCalendarColors =
    ElegantCalendarColors(
        containerColor = Color.Transparent,
        headerColor = themeColors.textPrimary,
        weekdayColor = themeColors.textTertiary,
        dayColor = themeColors.textPrimary,
        selectedDayColor = themeColors.textInverse,
        selectedDayContainerColor = themeColors.interactivePrimary,
        todayColor = themeColors.interactivePrimary,
        disabledDayColor = themeColors.textTertiary,
        hoveredDayColor = themeColors.surfaceHover,
        borderColor = themeColors.borderDefault,
    )

private val WeekdayLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
internal val CalendarDayCellGap: Dp = 4.dp
internal val CalendarDayCellSize: Dp = 40.dp
internal val CalendarNavigationSize: Dp = 40.dp
internal val WeekdayRowHeight: Dp = 32.dp

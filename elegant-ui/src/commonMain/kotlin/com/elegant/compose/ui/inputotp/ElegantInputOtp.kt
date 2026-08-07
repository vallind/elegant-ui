package com.elegant.compose.ui.inputotp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantInputOtp].
 *
 * Use [ElegantInputOtpDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property cellContainerColor default cell background color.
 * @property cellBorderColor resting cell border color.
 * @property focusedCellBorderColor focused cell border color.
 * @property errorCellBorderColor error cell border color.
 * @property disabledCellContainerColor disabled cell background color.
 * @property contentColor default digit and caret color.
 * @property disabledContentColor disabled digit color.
 * @property errorTextColor error text color.
 */
@Immutable
public data class ElegantInputOtpColors(
    val cellContainerColor: Color,
    val cellBorderColor: Color,
    val focusedCellBorderColor: Color,
    val errorCellBorderColor: Color,
    val disabledCellContainerColor: Color,
    val contentColor: Color,
    val disabledContentColor: Color,
    val errorTextColor: Color,
)

/** Theme-aware defaults for [ElegantInputOtp]. */
public object ElegantInputOtpDefaults {
    /** Minimum interactive height of the cell row. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Side length of every square cell. */
    public val CellSize: Dp = 48.dp

    /** Horizontal gap between adjacent cells. */
    public val CellGap: Dp = 8.dp

    /** Number of cells rendered when [ElegantInputOtp.length] is omitted. */
    public const val DefaultLength: Int = 6

    /** Returns theme-aware colors for [ElegantInputOtp]. */
    @Composable
    public fun colors(): ElegantInputOtpColors = resolveInputOtpColors(ElegantTheme.colors)
}

/** Width of the caret line drawn in the focused empty cell. */
internal val InputOtpCaretStrokeWidth: Dp = 2.dp

/** Height of the caret line drawn in the focused empty cell. */
internal val InputOtpCaretHeight: Dp = 20.dp

/**
 * Displays a controlled single-line one-time-passcode entry as a row of square cells.
 *
 * The component is controlled: [value] is owned by the caller and must be written back from
 * [onValueChange]. Input is filtered to digits and truncated at [length] before the callback
 * fires; each cell renders one digit of the accepted value from left to right. The hidden text
 * field uses the numeric keyboard type because the digits are shown plainly in their cells rather
 * than masked like a password.
 *
 * While the hidden field is focused, the cell at the caret position (the first empty cell, or the
 * last cell when [value] is full) shows the focus-ring border and a caret. When [isError] is true
 * every cell border turns `statusCritical` with a 2dp stroke and [errorText] is shown below the
 * cells and announced through semantics. When [enabled] is false the field rejects focus and
 * input and the cells are dimmed.
 *
 * @param value the current code content, owned by the caller.
 * @param onValueChange callback invoked with the newest accepted code content.
 * @param modifier modifier applied once to the input root.
 * @param enabled whether the field accepts focus and input.
 * @param length number of cells; input is truncated at this length and values below one fall back
 *   to a single cell.
 * @param isError whether the field communicates an error state.
 * @param errorText optional error message shown below the cells and announced through semantics
 *   when [isError].
 * @param colors theme-aware state colors.
 */
@Composable
public fun ElegantInputOtp(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    length: Int = ElegantInputOtpDefaults.DefaultLength,
    isError: Boolean = false,
    errorText: String? = null,
    colors: ElegantInputOtpColors = ElegantInputOtpDefaults.colors(),
) {
    val resolvedLength = length.coerceAtLeast(1)
    val resolvedValue = coercedValue(value, resolvedLength)
    val resolvedErrorText = if (isError && !errorText.isNullOrBlank()) errorText else null
    val shape = RoundedCornerShape(ElegantRadius.sm)
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val focusRingEnabled = ElegantTheme.focusRingEnabled
    val activeCaretIndex = when {
        !enabled || !focused -> -1
        else -> focusedCellIndex(caretIndex(resolvedValue, resolvedLength), resolvedLength)
    }

    Column(modifier = modifier) {
        Box {
            Row(
                modifier = Modifier.clearAndSetSemantics { },
                horizontalArrangement = Arrangement.spacedBy(ElegantInputOtpDefaults.CellGap),
            ) {
                for (index in 0 until resolvedLength) {
                    val char = cellChar(resolvedValue, index)
                    val isFocusedCell = index == activeCaretIndex
                    val cellContainer = if (enabled) {
                        colors.cellContainerColor
                    } else {
                        colors.disabledCellContainerColor
                    }
                    val cellBorder = when {
                        !enabled -> colors.cellBorderColor
                        isError -> colors.errorCellBorderColor
                        isFocusedCell && focusRingEnabled -> colors.focusedCellBorderColor
                        else -> colors.cellBorderColor
                    }
                    val cellBorderWidth = when {
                        !enabled -> 1.dp
                        isError || (isFocusedCell && focusRingEnabled) -> 2.dp
                        else -> 1.dp
                    }
                    val contentColor = if (enabled) {
                        colors.contentColor
                    } else {
                        colors.disabledContentColor
                    }

                    Box(
                        modifier = Modifier
                            .size(ElegantInputOtpDefaults.CellSize)
                            .clip(shape)
                            .background(cellContainer)
                            .border(BorderStroke(cellBorderWidth, cellBorder), shape),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (char != null) {
                            Text(
                                text = char.toString(),
                                color = contentColor,
                                style = ElegantTheme.typography.labelLarge,
                            )
                        } else if (isFocusedCell) {
                            Canvas(
                                modifier = Modifier.size(
                                    width = InputOtpCaretStrokeWidth,
                                    height = InputOtpCaretHeight,
                                ),
                            ) {
                                drawLine(
                                    color = contentColor,
                                    start = Offset(size.width / 2f, 0f),
                                    end = Offset(size.width / 2f, size.height),
                                    strokeWidth = InputOtpCaretStrokeWidth.toPx(),
                                    cap = StrokeCap.Round,
                                )
                            }
                        }
                    }
                }
            }

            BasicTextField(
                value = resolvedValue,
                onValueChange = { onValueChange(coercedValue(it, resolvedLength)) },
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0f)
                    .semantics {
                        if (!enabled) disabled()
                        if (resolvedErrorText != null) error(resolvedErrorText)
                    },
                enabled = enabled,
                singleLine = true,
                textStyle = ElegantTheme.typography.bodyMedium.copy(color = Color.Transparent),
                cursorBrush = SolidColor(Color.Transparent),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                interactionSource = interactionSource,
            )
        }

        if (resolvedErrorText != null) {
            Spacer(Modifier.height(ElegantSpacing.sm))
            Text(
                text = resolvedErrorText,
                modifier = Modifier.fillMaxWidth(),
                color = colors.errorTextColor,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
}

internal fun resolveInputOtpColors(themeColors: ElegantColors): ElegantInputOtpColors =
    ElegantInputOtpColors(
        cellContainerColor = themeColors.surfaceSunken,
        cellBorderColor = themeColors.borderDefault,
        focusedCellBorderColor = themeColors.focusRing,
        errorCellBorderColor = themeColors.statusCritical,
        disabledCellContainerColor = themeColors.surfaceSunken,
        contentColor = themeColors.textPrimary,
        disabledContentColor = themeColors.textTertiary,
        errorTextColor = themeColors.statusCritical,
    )

internal fun coercedValue(value: String, length: Int): String =
    value.filter(Char::isDigit).take(length.coerceAtLeast(0))

internal fun cellChar(value: String, index: Int): Char? =
    value.getOrNull(index)

internal fun caretIndex(value: String, length: Int): Int =
    value.length.coerceIn(0, (length - 1).coerceAtLeast(0))

internal fun focusedCellIndex(focusIndex: Int, length: Int): Int =
    focusIndex.coerceIn(0, (length - 1).coerceAtLeast(0))

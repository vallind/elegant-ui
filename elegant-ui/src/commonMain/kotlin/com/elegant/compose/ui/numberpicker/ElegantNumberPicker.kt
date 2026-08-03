package com.elegant.compose.ui.numberpicker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantTheme
import kotlinx.coroutines.delay

/**
 * Theme-aware state colors used by [ElegantNumberPicker].
 *
 * Use [ElegantNumberPickerDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor resting container color of the step buttons.
 * @property contentColor value text color.
 * @property secondaryContentColor step-button glyph color.
 * @property disabledContentColor value and glyph color when the picker or a button is disabled.
 * @property hoveredContainerColor hovered step-button container color.
 * @property pressedContainerColor pressed step-button container color.
 * @property dividerColor separator color available when surrounding content needs a divider.
 */
@Immutable
public data class ElegantNumberPickerColors(
    val containerColor: Color,
    val contentColor: Color,
    val secondaryContentColor: Color,
    val disabledContentColor: Color,
    val hoveredContainerColor: Color = containerColor,
    val pressedContainerColor: Color = containerColor,
    val dividerColor: Color,
)

/** Theme-aware defaults for [ElegantNumberPicker]. */
public object ElegantNumberPickerDefaults {
    /** Minimum height reserved for the centered value text. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Edge length of each circular step button. */
    public val ButtonSize: Dp = 40.dp

    /** Returns theme-aware colors matching the stepper roles. */
    @Composable
    public fun colors(): ElegantNumberPickerColors = resolveNumberPickerColors(ElegantTheme.colors)
}

/** Initial hold delay before a pressed step button starts repeating. */
internal const val NumberPickerRepeatInitialDelayMillis: Long = 350L

/** Delay between repeated steps while a step button is held. */
internal const val NumberPickerRepeatIntervalMillis: Long = 80L

/** Minimum width of the picker column. */
internal val NumberPickerMinimumWidth: Dp = 96.dp

/** Chevron glyph edge length used on the step buttons. */
internal val NumberPickerChevronSize: Dp = 16.dp

@Immutable
internal data class NumberPickerStepButtonVisuals(
    val container: Color,
    val content: Color,
)

/**
 * Displays a controlled integer as a vertical stepper: a centered value with circular increase
 * and decrease buttons above and below it.
 *
 * The value is owned by the caller: every accepted step is written back through [onValueChange].
 * The buttons apply [step] in their direction and are disabled, but stay visible with disabled
 * colors, once a step would leave [minValue]..[maxValue]. An inverted range ([minValue] greater
 * than [maxValue]) is treated as unbounded: stepping never clamps and only stops at the [Int]
 * limits.
 *
 * A quick press on either button steps once; holding the button repeats the step after an initial
 * delay of 350ms and then every 80ms until the pointer is released. A disabled picker renders
 * both buttons and the value with disabled colors and never invokes [onValueChange].
 *
 * Each button exposes a [Role.Button] with an "Increase" or "Decrease" content description for
 * accessibility services; the centered value remains plain readable text.
 *
 * @param value the current integer, owned by the caller.
 * @param onValueChange callback invoked with the newest accepted integer value.
 * @param modifier modifier applied once to the picker root.
 * @param enabled whether stepping and pointer interaction are accepted.
 * @param minValue smallest value reachable by stepping; an inverted range behaves as unbounded.
 * @param maxValue largest value reachable by stepping; an inverted range behaves as unbounded.
 * @param step increment applied per step; non-positive values fall back to 1.
 * @param colors theme-aware state colors.
 */
@Composable
public fun ElegantNumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    minValue: Int = 0,
    maxValue: Int = Int.MAX_VALUE,
    step: Int = 1,
    colors: ElegantNumberPickerColors = ElegantNumberPickerDefaults.colors(),
) {
    val increaseEnabled = canIncrease(
        value = value,
        maxValue = if (minValue <= maxValue) maxValue else Int.MAX_VALUE,
        enabled = enabled,
    )
    val decreaseEnabled = canDecrease(
        value = value,
        minValue = if (minValue <= maxValue) minValue else Int.MIN_VALUE,
        enabled = enabled,
    )

    fun applyStep(direction: Int) {
        val target = stepResult(
            value = value,
            step = step,
            minValue = minValue,
            maxValue = maxValue,
            direction = direction,
        )
        if (target != value) onValueChange(target)
    }

    Column(
        modifier = modifier.widthIn(min = NumberPickerMinimumWidth),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NumberPickerStepButton(
            direction = 1,
            enabled = increaseEnabled,
            contentDescription = "Increase",
            colors = colors,
            onStep = { applyStep(1) },
        )
        Text(
            text = value.toString(),
            modifier = Modifier.defaultMinSize(
                minHeight = ElegantNumberPickerDefaults.MinimumTouchHeight,
            ),
            color = if (enabled) colors.contentColor else colors.disabledContentColor,
            style = ElegantTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
        )
        NumberPickerStepButton(
            direction = -1,
            enabled = decreaseEnabled,
            contentDescription = "Decrease",
            colors = colors,
            onStep = { applyStep(-1) },
        )
    }
}

@Composable
private fun NumberPickerStepButton(
    direction: Int,
    enabled: Boolean,
    contentDescription: String,
    colors: ElegantNumberPickerColors,
    onStep: (Int) -> Unit,
) {
    var pressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val visuals = resolveNumberPickerStepButtonVisuals(
        colors = colors,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
    )

    val currentEnabled by rememberUpdatedState(enabled)
    val currentOnStep by rememberUpdatedState(onStep)

    LaunchedEffect(pressed, direction) {
        var first = true
        while (pressed) {
            if (currentEnabled) {
                currentOnStep(direction)
            }
            delay(
                if (first) {
                    NumberPickerRepeatInitialDelayMillis
                } else {
                    NumberPickerRepeatIntervalMillis
                },
            )
            first = false
        }
    }

    Box(
        modifier = Modifier
            .size(ElegantNumberPickerDefaults.ButtonSize)
            .semantics(mergeDescendants = true) {
                role = Role.Button
                this.contentDescription = contentDescription
                if (!enabled) disabled()
            }
            .pointerInput(enabled) {
                if (enabled) {
                    detectTapGestures(
                        onPress = {
                            pressed = true
                            try {
                                tryAwaitRelease()
                            } finally {
                                pressed = false
                            }
                        },
                        onLongPress = {},
                    )
                }
            }
            .hoverable(interactionSource = interactionSource, enabled = enabled)
            .clip(CircleShape)
            .background(visuals.container),
        contentAlignment = Alignment.Center,
    ) {
        NumberPickerChevron(direction = direction, color = visuals.content)
    }
}

@Composable
private fun NumberPickerChevron(
    direction: Int,
    color: Color,
) {
    Canvas(modifier = Modifier.size(NumberPickerChevronSize)) {
        val strokeWidth = 2.dp.toPx()
        val midX = size.width / 2f
        val midY = size.height / 2f
        val armX = size.width * 0.26f
        val armY = size.height * 0.26f
        val tipY = if (direction > 0) midY - armY else midY + armY
        drawLine(
            color = color,
            start = Offset(midX - armX, tipY),
            end = Offset(midX, midY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
        drawLine(
            color = color,
            start = Offset(midX, midY),
            end = Offset(midX + armX, tipY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
    }
}

/** Resolves theme-aware stepper colors from [themeColors]. */
internal fun resolveNumberPickerColors(
    themeColors: ElegantColors,
): ElegantNumberPickerColors = ElegantNumberPickerColors(
    containerColor = Color.Transparent,
    contentColor = themeColors.textPrimary,
    secondaryContentColor = themeColors.textSecondary,
    disabledContentColor = themeColors.textTertiary,
    hoveredContainerColor = themeColors.surfaceHover,
    pressedContainerColor = themeColors.backgroundSubtle,
    dividerColor = themeColors.borderDefault,
)

internal fun resolveNumberPickerStepButtonVisuals(
    colors: ElegantNumberPickerColors,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
): NumberPickerStepButtonVisuals {
    val container = when {
        !enabled -> colors.containerColor
        pressed -> colors.pressedContainerColor
        hovered -> colors.hoveredContainerColor
        else -> colors.containerColor
    }
    val content = when {
        !enabled -> colors.disabledContentColor
        else -> colors.secondaryContentColor
    }
    return NumberPickerStepButtonVisuals(
        container = container,
        content = content,
    )
}

internal fun resolveStep(step: Int): Int = if (step > 0) step else 1

internal fun canIncrease(value: Int, maxValue: Int, enabled: Boolean): Boolean =
    enabled && value < maxValue

internal fun canDecrease(value: Int, minValue: Int, enabled: Boolean): Boolean =
    enabled && value > minValue

internal fun stepResult(
    value: Int,
    step: Int,
    minValue: Int,
    maxValue: Int,
    direction: Int,
): Int {
    val resolvedStep = resolveStep(step)
    val target = when {
        direction > 0 -> {
            if (value > Int.MAX_VALUE - resolvedStep) Int.MAX_VALUE else value + resolvedStep
        }

        direction < 0 -> {
            if (value < Int.MIN_VALUE + resolvedStep) Int.MIN_VALUE else value - resolvedStep
        }

        else -> value
    }
    return if (minValue <= maxValue) target.coerceIn(minValue, maxValue) else target
}

package com.elegant.compose.ui.numberfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantNumberField].
 *
 * Use [ElegantNumberFieldDefaults.colors] for theme-aware defaults, then use [copy] for supported
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
 * @property contentColor default text and icon color.
 * @property disabledContentColor disabled text and icon color.
 * @property placeholderColor placeholder text color.
 * @property labelColor label text color.
 * @property supportingTextColor supporting text color.
 * @property errorTextColor error text color.
 */
@Immutable
public data class ElegantNumberFieldColors(
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

/** Theme-aware defaults for [ElegantNumberField]. */
public object ElegantNumberFieldDefaults {
    /** Default increment used by the step buttons and the arrow keys. */
    public const val Step: Int = 1

    /** Minimum field-container height used by the field. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware colors matching the filled input roles. */
    @Composable
    public fun colors(): ElegantNumberFieldColors = resolveNumberFieldColors(ElegantTheme.colors)
}

/** Minimum step-button interaction target. */
internal val NumberFieldStepButtonTargetSize: Dp = 48.dp

/** Step-button visual container size. */
internal val NumberFieldStepButtonVisualSize: Dp = 32.dp

/** Step-button glyph canvas size. */
internal val NumberFieldStepButtonIconSize: Dp = 16.dp

@Immutable
internal data class NumberFieldVisuals(
    val container: Color,
    val border: Color,
    val borderWidth: Dp,
)

@Immutable
internal data class StepButtonVisuals(
    val container: Color,
    val content: Color,
    val border: Color,
    val borderWidth: Dp,
)

/**
 * Displays a controlled integer field with an optional label, placeholder, supporting or error
 * text, a leading icon, and compact increase and decrease step buttons.
 *
 * The committed value is owned by the caller: accepted edits are written back through
 * [onValueChange]. While the field has focus, the edited text is kept as a local draft and is
 * committed whenever it parses to an [Int] inside [minValue]..[maxValue]; incomplete or invalid
 * input such as an empty draft or a dangling minus sign keeps the draft without invoking
 * [onValueChange]. When focus is lost the draft is reset to [value]. An inverted range
 * ([minValue] greater than [maxValue]) is treated as unbounded: no value is rejected and
 * stepping only stops at the [Int] limits.
 *
 * The step buttons apply [step] in the selected direction when the result stays inside the
 * range; a button is disabled once the boundary is reached but stays visible, and is announced
 * as an increase or decrease action for accessibility services. ArrowUp and ArrowDown step the
 * focused field by the same increment.
 *
 * @param value the current committed integer, owned by the caller.
 * @param onValueChange callback invoked with the newest accepted integer value.
 * @param modifier modifier applied once to the field root.
 * @param enabled whether the field accepts focus, input, and stepping.
 * @param label optional label shown above the field.
 * @param placeholder optional hint shown inside the field while it is enabled and empty.
 * @param minValue smallest value accepted by the field; an inverted range behaves as unbounded.
 * @param maxValue largest value accepted by the field; an inverted range behaves as unbounded.
 * @param step increment used by the step buttons and the arrow keys; non-positive values fall
 *   back to [ElegantNumberFieldDefaults.Step].
 * @param supportingText optional guidance shown below the field unless error text is shown.
 * @param isError whether the field communicates an error state.
 * @param errorText optional error message shown below the field and announced through semantics
 *   when [isError].
 * @param colors theme-aware state colors.
 * @param leadingIcon optional content before the input area, tinted with the field content color.
 */
@Composable
public fun ElegantNumberField(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    placeholder: String? = null,
    minValue: Int = Int.MIN_VALUE,
    maxValue: Int = Int.MAX_VALUE,
    step: Int = ElegantNumberFieldDefaults.Step,
    supportingText: String? = null,
    isError: Boolean = false,
    errorText: String? = null,
    colors: ElegantNumberFieldColors = ElegantNumberFieldDefaults.colors(),
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    val resolvedLabel = if (label.isNullOrBlank()) null else label
    val resolvedErrorText = if (isError && !errorText.isNullOrBlank()) errorText else null
    val shape = RoundedCornerShape(ElegantRadius.md)
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val visuals = resolveNumberFieldVisuals(
        colors = colors,
        enabled = enabled,
        hovered = hovered,
        focused = focused,
        isError = isError,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = tween(
            durationMillis = ElegantNumberFieldDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantNumberFieldContainer",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = tween(
            durationMillis = ElegantNumberFieldDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantNumberFieldBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = tween(
            durationMillis = ElegantNumberFieldDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantNumberFieldBorderWidth",
    )
    val animatedContent by animateColorAsState(
        targetValue = if (enabled) colors.contentColor else colors.disabledContentColor,
        animationSpec = tween(
            durationMillis = ElegantNumberFieldDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantNumberFieldContent",
    )

    var draft by remember(value) { mutableStateOf(value.toString()) }
    val currentValue by rememberUpdatedState(value)
    LaunchedEffect(focused) {
        if (!focused) draft = currentValue.toString()
    }

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

    val inputTextStyle = ElegantTheme.typography.bodyMedium.copy(color = animatedContent)
    val showPlaceholder = enabled && draft.isEmpty() && placeholder != null
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = ElegantNumberFieldDefaults.MinimumTouchHeight)
                .clip(shape)
                .background(animatedContainer)
                .border(
                    border = BorderStroke(animatedBorderWidth, animatedBorder),
                    shape = shape,
                )
                .hoverable(
                    interactionSource = interactionSource,
                    enabled = enabled,
                )
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CompositionLocalProvider(LocalContentColor provides animatedContent) {
                if (leadingIcon != null) {
                    Box(
                        modifier = Modifier.size(20.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        leadingIcon()
                    }
                    Spacer(Modifier.width(ElegantSpacing.md))
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .semantics(mergeDescendants = true) {
                            if (!enabled) disabled()
                            if (resolvedErrorText != null) error(resolvedErrorText)
                        },
                    contentAlignment = Alignment.CenterStart,
                ) {
                    BasicTextField(
                        value = draft,
                        onValueChange = { new ->
                            draft = new
                            parseDraft(new)?.let { parsed ->
                                if (isWithinRange(parsed, minValue, maxValue)) {
                                    onValueChange(parsed)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .onKeyEvent { event ->
                                if (event.type != KeyEventType.KeyDown || !enabled) {
                                    false
                                } else {
                                    val direction = when (event.key) {
                                        Key.DirectionUp -> 1
                                        Key.DirectionDown -> -1
                                        else -> null
                                    }
                                    if (direction != null) {
                                        applyStep(direction)
                                        true
                                    } else {
                                        false
                                    }
                                }
                            },
                        enabled = enabled,
                        singleLine = true,
                        textStyle = inputTextStyle,
                        interactionSource = interactionSource,
                        cursorBrush = SolidColor(animatedContent),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart,
                            ) {
                                if (showPlaceholder) {
                                    Text(
                                        text = placeholder,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clearAndSetSemantics { },
                                        color = colors.placeholderColor,
                                        style = ElegantTheme.typography.bodyMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                }
                                innerTextField()
                            }
                        },
                    )
                }
            }

            Spacer(Modifier.width(ElegantSpacing.xs))

            NumberFieldStepButton(
                direction = -1,
                enabled = enabled &&
                    stepResult(value, step, minValue, maxValue, -1) != value,
                contentDescription = "Decrease value",
                onClick = { applyStep(-1) },
            )
            NumberFieldStepButton(
                direction = 1,
                enabled = enabled &&
                    stepResult(value, step, minValue, maxValue, 1) != value,
                contentDescription = "Increase value",
                onClick = { applyStep(1) },
            )
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

internal fun resolveNumberFieldColors(
    themeColors: ElegantColors,
): ElegantNumberFieldColors = ElegantNumberFieldColors(
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

internal fun resolveNumberFieldVisuals(
    colors: ElegantNumberFieldColors,
    enabled: Boolean,
    hovered: Boolean,
    focused: Boolean,
    isError: Boolean,
): NumberFieldVisuals {
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
        !enabled -> 1.dp
        isError -> 2.dp
        focused -> 2.dp
        else -> 1.dp
    }
    return NumberFieldVisuals(
        container = container,
        border = border,
        borderWidth = borderWidth,
    )
}

internal fun resolveStepButtonVisuals(
    themeColors: ElegantColors,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
    focused: Boolean,
): StepButtonVisuals {
    val container = when {
        !enabled -> Color.Transparent
        pressed -> themeColors.backgroundSubtle
        hovered -> themeColors.surfaceHover
        else -> Color.Transparent
    }
    val content = when {
        !enabled -> themeColors.textTertiary
        pressed -> themeColors.interactivePrimary
        hovered -> themeColors.textPrimary
        else -> themeColors.textSecondary
    }
    val focusedBorder = focused && enabled
    return StepButtonVisuals(
        container = container,
        content = content,
        border = if (focusedBorder) themeColors.focusRing else Color.Transparent,
        borderWidth = if (focusedBorder) 2.dp else 0.dp,
    )
}

internal fun resolveStep(step: Int): Int = if (step > 0) step else 1

internal fun isWithinRange(value: Int, minValue: Int, maxValue: Int): Boolean =
    minValue <= maxValue && value in minValue..maxValue

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

internal fun parseDraft(text: String): Int? {
    val trimmed = text.trim()
    if (trimmed.isEmpty() || trimmed == "-") return null
    return trimmed.toIntOrNull()
}

@Composable
private fun NumberFieldStepButton(
    direction: Int,
    enabled: Boolean,
    contentDescription: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val visuals = resolveStepButtonVisuals(
        themeColors = ElegantTheme.colors,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
        focused = focused,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = tween(
            durationMillis = ElegantNumberFieldDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantNumberFieldStepButtonContainer",
    )
    val animatedContent by animateColorAsState(
        targetValue = visuals.content,
        animationSpec = tween(
            durationMillis = ElegantNumberFieldDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantNumberFieldStepButtonContent",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = tween(
            durationMillis = ElegantNumberFieldDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantNumberFieldStepButtonBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = tween(
            durationMillis = ElegantNumberFieldDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantNumberFieldStepButtonBorderWidth",
    )

    Box(
        modifier = Modifier
            .size(NumberFieldStepButtonTargetSize)
            .semantics(mergeDescendants = true) {
                this.contentDescription = contentDescription
                role = Role.Button
                if (!enabled) disabled()
            }
            .clickable(
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(NumberFieldStepButtonVisualSize)
                .clip(RoundedCornerShape(10.dp))
                .background(animatedContainer)
                .border(
                    border = BorderStroke(animatedBorderWidth, animatedBorder),
                    shape = RoundedCornerShape(10.dp),
                )
                .indication(
                    interactionSource = interactionSource,
                    indication = ripple(color = animatedContent),
                ),
            contentAlignment = Alignment.Center,
        ) {
            CompositionLocalProvider(LocalContentColor provides animatedContent) {
                StepGlyph(
                    direction = direction,
                    modifier = Modifier.size(NumberFieldStepButtonIconSize),
                )
            }
        }
    }
}

@Composable
private fun StepGlyph(
    direction: Int,
    modifier: Modifier = Modifier,
) {
    val color = LocalContentColor.current
    Canvas(modifier = modifier) {
        val strokeWidth = 2.dp.toPx()
        val halfStroke = strokeWidth / 2f
        val centerY = size.height / 2f
        drawLine(
            color = color,
            start = Offset(halfStroke, centerY),
            end = Offset(size.width - halfStroke, centerY),
            strokeWidth = strokeWidth,
        )
        if (direction > 0) {
            val centerX = size.width / 2f
            drawLine(
                color = color,
                start = Offset(centerX, halfStroke),
                end = Offset(centerX, size.height - halfStroke),
                strokeWidth = strokeWidth,
            )
        }
    }
}

package com.elegant.compose.ui.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/** Visual variants supported by [ElegantInput]. */
public enum class ElegantInputStyle {
    /** Recessed sunken container for the primary input of a surface. */
    Filled,

    /** Transparent container with a visible resting border for secondary inputs. */
    Outlined,
}

/**
 * Theme-aware state colors used by [ElegantInput].
 *
 * Use [ElegantInputDefaults.colors] for theme-aware defaults, then use [copy] for supported
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
public data class ElegantInputColors(
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

/** Theme-aware defaults for [ElegantInput]. */
public object ElegantInputDefaults {
    /** Sentinel for inputs without a length limit. */
    public const val MaxLengthUnlimited: Int = Int.MAX_VALUE

    /** Minimum field-container height used by every input. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware colors for [style]. */
    @Composable
    public fun colors(
        style: ElegantInputStyle = ElegantInputStyle.Filled,
    ): ElegantInputColors = resolveInputColors(
        style = style,
        themeColors = ElegantTheme.colors,
    )

    /** Returns the container shape for [style]. */
    public fun shape(style: ElegantInputStyle = ElegantInputStyle.Filled): Shape = when (style) {
        ElegantInputStyle.Filled -> RoundedCornerShape(ElegantRadius.md)
        ElegantInputStyle.Outlined -> RoundedCornerShape(10.dp)
    }
}

@Immutable
internal data class InputVisuals(
    val container: Color,
    val border: Color,
    val borderWidth: Dp,
)

/**
 * Displays a controlled single-line text field with an optional label, placeholder, supporting or
 * error text, and leading and trailing icons.
 *
 * The field is a controlled component: [value] is owned by the caller and must be written back
 * from [onValueChange]. Input past [maxLength] is truncated before [onValueChange] fires. When
 * [enabled] is false the field rejects focus and input; when [readOnly] is true it stays
 * focusable and selectable but cannot be edited. The placeholder is shown only while the field is
 * enabled and empty.
 *
 * @param value the current field content, owned by the caller.
 * @param onValueChange callback invoked with the newest accepted field content.
 * @param modifier modifier applied once to the input root.
 * @param enabled whether the field accepts focus and input.
 * @param readOnly whether the field can be focused and copied but not edited.
 * @param label optional label shown above the field.
 * @param placeholder optional hint shown inside the field while it is enabled and empty.
 * @param supportingText optional guidance shown below the field unless error text is shown.
 * @param isError whether the field communicates an error state.
 * @param errorText optional error message shown below the field and announced through semantics
 *   when [isError].
 * @param maxLength maximum accepted character count; input is truncated at this length and values
 *   that are not positive fall back to [ElegantInputDefaults.MaxLengthUnlimited].
 * @param style visual variant.
 * @param colors theme-aware state colors.
 * @param leadingIcon optional content before the input area, tinted with the field content color.
 * @param trailingIcon optional content after the input area, tinted with the field content color.
 */
@Composable
public fun ElegantInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    errorText: String? = null,
    maxLength: Int = ElegantInputDefaults.MaxLengthUnlimited,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    style: ElegantInputStyle = ElegantInputStyle.Filled,
    colors: ElegantInputColors = ElegantInputDefaults.colors(style),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val resolvedLabel = resolveInputLabel(label)
    val resolvedErrorText = if (isError && !errorText.isNullOrBlank()) errorText else null
    val resolvedMaxLength = resolveInputMaxLength(maxLength)
    val shape = ElegantInputDefaults.shape(style)
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val visuals = resolveInputVisuals(
        colors = colors,
        enabled = enabled,
        hovered = hovered,
        focused = focused,
        isError = isError,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = tween(
            durationMillis = ElegantInputDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantInputContainer",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = tween(
            durationMillis = ElegantInputDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantInputBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = tween(
            durationMillis = ElegantInputDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantInputBorderWidth",
    )
    val animatedContent by animateColorAsState(
        targetValue = if (enabled) colors.contentColor else colors.disabledContentColor,
        animationSpec = tween(
            durationMillis = ElegantInputDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantInputContent",
    )
    val inputTextStyle = ElegantTheme.typography.bodyMedium.copy(color = animatedContent)
    val showPlaceholder = enabled && value.isEmpty() && placeholder != null
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
                .defaultMinSize(minHeight = ElegantInputDefaults.MinimumTouchHeight)
                .semantics(mergeDescendants = true) {
                    if (!enabled) disabled()
                    if (resolvedErrorText != null) error(resolvedErrorText)
                }
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

                BasicTextField(
                    value = value,
                    onValueChange = { new ->
                        onValueChange(clampInputLength(new, resolvedMaxLength))
                    },
                    modifier = Modifier.weight(1f),
                    enabled = enabled,
                    readOnly = readOnly,
                    singleLine = true,
                    textStyle = inputTextStyle,
                    interactionSource = interactionSource,
                    cursorBrush = SolidColor(animatedContent),
                    visualTransformation = visualTransformation,
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

                if (trailingIcon != null) {
                    Spacer(Modifier.width(ElegantSpacing.md))
                    Box(
                        modifier = Modifier.size(20.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        trailingIcon()
                    }
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

internal fun resolveInputColors(
    style: ElegantInputStyle,
    themeColors: ElegantColors,
): ElegantInputColors = when (style) {
    ElegantInputStyle.Filled -> ElegantInputColors(
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

    ElegantInputStyle.Outlined -> ElegantInputColors(
        containerColor = Color.Transparent,
        hoveredContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        borderColor = themeColors.borderDefault,
        hoveredBorderColor = themeColors.borderStrong,
        focusedBorderColor = themeColors.focusRing,
        errorBorderColor = themeColors.statusCritical,
        disabledBorderColor = themeColors.borderDefault,
        contentColor = themeColors.textPrimary,
        disabledContentColor = themeColors.textTertiary,
        placeholderColor = themeColors.textTertiary,
        labelColor = themeColors.textSecondary,
        supportingTextColor = themeColors.textSecondary,
        errorTextColor = themeColors.statusCritical,
    )
}

internal fun resolveInputVisuals(
    colors: ElegantInputColors,
    enabled: Boolean,
    hovered: Boolean,
    focused: Boolean,
    isError: Boolean,
): InputVisuals {
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
    return InputVisuals(
        container = container,
        border = border,
        borderWidth = borderWidth,
    )
}

internal fun resolveInputMaxLength(maxLength: Int): Int =
    if (maxLength > 0) maxLength else ElegantInputDefaults.MaxLengthUnlimited

internal fun resolveInputLabel(label: String?): String? =
    if (label.isNullOrBlank()) null else label

internal fun clampInputLength(value: String, maxLength: Int): String =
    if (value.length <= maxLength) value else value.take(maxLength)

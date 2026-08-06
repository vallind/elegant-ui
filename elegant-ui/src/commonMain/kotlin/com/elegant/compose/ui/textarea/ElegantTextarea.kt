package com.elegant.compose.ui.textarea

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.foundation.shape.resolveSquircleAwareShape
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantTextarea].
 *
 * Use [ElegantTextareaDefaults.colors] for theme-aware defaults, then use [copy] for supported
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
public data class ElegantTextareaColors(
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

/** Theme-aware defaults for [ElegantTextarea]. */
public object ElegantTextareaDefaults {
    /** Sentinel for textareas without a length limit. */
    public const val MaxLengthUnlimited: Int = Int.MAX_VALUE

    /** Minimum field-container height used by every textarea. */
    public val MinimumTouchHeight: Dp = 120.dp

    /** Minimum number of visible text lines shown by default. */
    public const val MinLines: Int = 3

    /** Maximum number of visible text lines before the field scrolls. */
    public const val MaxLines: Int = 8

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware colors for [ElegantTextarea]. */
    @Composable
    public fun colors(): ElegantTextareaColors = resolveTextareaColors(ElegantTheme.colors)

    /** Returns the squircle-aware container shape for [ElegantTextarea], a 16dp rounded square. */
    public fun shape(): Shape = RoundedCornerShape(16.dp)
}

@Immutable
internal data class TextareaVisuals(
    val container: Color,
    val border: Color,
    val borderWidth: Dp,
)

/**
 * Displays a controlled multi-line text field with an optional label, placeholder, supporting or
 * error text, and leading and trailing icons.
 *
 * The field is a controlled component: [value] is owned by the caller and must be written back
 * from [onValueChange]. Input past [maxLength] is truncated before [onValueChange] fires. The
 * field reserves [minLines] visible text lines, grows with content up to [maxLines], and then
 * scrolls. When [enabled] is false the field rejects focus and input; when [readOnly] is true it
 * stays focusable and selectable but cannot be edited. The placeholder is shown only while the
 * field is enabled and empty.
 *
 * @param value the current field content, owned by the caller.
 * @param onValueChange callback invoked with the newest accepted field content.
 * @param modifier modifier applied once to the textarea root.
 * @param enabled whether the field accepts focus and input.
 * @param readOnly whether the field can be focused and copied but not edited.
 * @param label optional label shown above the field.
 * @param placeholder optional hint shown inside the field while it is enabled and empty.
 * @param supportingText optional guidance shown below the field unless error text is shown.
 * @param isError whether the field communicates an error state.
 * @param errorText optional error message shown below the field and announced through semantics
 *   when [isError].
 * @param maxLength maximum accepted character count; input is truncated at this length and values
 *   that are not positive fall back to [ElegantTextareaDefaults.MaxLengthUnlimited].
 * @param minLines minimum number of visible text lines, coerced to at least 1.
 * @param maxLines maximum number of visible text lines before scrolling, coerced to at least
 *   [minLines].
 * @param colors theme-aware state colors.
 * @param leadingIcon optional content before the input area, tinted with the field content color.
 * @param trailingIcon optional content after the input area, tinted with the field content color.
 */
@Composable
public fun ElegantTextarea(
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
    maxLength: Int = ElegantTextareaDefaults.MaxLengthUnlimited,
    minLines: Int = ElegantTextareaDefaults.MinLines,
    maxLines: Int = ElegantTextareaDefaults.MaxLines,
    colors: ElegantTextareaColors = ElegantTextareaDefaults.colors(),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val resolvedLabel = resolveLabel(label)
    val resolvedErrorText = if (isError && !errorText.isNullOrBlank()) errorText else null
    val resolvedMaxLength = resolveMaxLength(maxLength)
    val (resolvedMinLines, resolvedMaxLines) = resolveLines(minLines, maxLines)
    val effectiveShape = resolveSquircleAwareShape(
        userShape = ElegantTextareaDefaults.shape(),
        defaultShape = ElegantTextareaDefaults.shape(),
        cornerRadius = 16.dp,
    )
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val visuals = resolveVisuals(
        colors = colors,
        enabled = enabled,
        hovered = hovered,
        focused = focused,
        isError = isError,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantTextareaContainer",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantTextareaBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantTextareaBorderWidth",
    )
    val animatedContent by animateColorAsState(
        targetValue = if (enabled) colors.contentColor else colors.disabledContentColor,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantTextareaContent",
    )
    val inputTextStyle = ElegantTheme.typography.bodyMedium.copy(color = animatedContent)
    val showPlaceholder = enabled && value.isEmpty() && placeholder != null
    val helperText = resolveDescription(
        supportingText = supportingText,
        errorText = errorText,
        isError = isError,
    )

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
                .defaultMinSize(minHeight = ElegantTextareaDefaults.MinimumTouchHeight)
                .semantics(mergeDescendants = true) {
                    if (!enabled) disabled()
                    if (resolvedErrorText != null) error(resolvedErrorText)
                }
                .clip(effectiveShape)
                .background(animatedContainer)
                .border(
                    border = BorderStroke(animatedBorderWidth, animatedBorder),
                    shape = effectiveShape,
                )
                .hoverable(
                    interactionSource = interactionSource,
                    enabled = enabled,
                )
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.Top,
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
                        onValueChange(clampLength(new, resolvedMaxLength))
                    },
                    modifier = Modifier.weight(1f),
                    enabled = enabled,
                    readOnly = readOnly,
                    textStyle = inputTextStyle,
                    minLines = resolvedMinLines,
                    maxLines = resolvedMaxLines,
                    interactionSource = interactionSource,
                    cursorBrush = SolidColor(animatedContent),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopStart,
                        ) {
                            if (showPlaceholder) {
                                Text(
                                    text = placeholder,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clearAndSetSemantics { },
                                    color = colors.placeholderColor,
                                    style = ElegantTheme.typography.bodyMedium,
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

internal fun resolveTextareaColors(themeColors: ElegantColors): ElegantTextareaColors =
    ElegantTextareaColors(
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

internal fun resolveVisuals(
    colors: ElegantTextareaColors,
    enabled: Boolean,
    hovered: Boolean,
    focused: Boolean,
    isError: Boolean,
): TextareaVisuals {
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
    return TextareaVisuals(
        container = container,
        border = border,
        borderWidth = borderWidth,
    )
}

internal fun resolveMaxLength(maxLength: Int): Int =
    if (maxLength > 0) maxLength else ElegantTextareaDefaults.MaxLengthUnlimited

internal fun resolveLines(minLines: Int, maxLines: Int): Pair<Int, Int> {
    val resolvedMinLines = if (minLines >= 1) minLines else 1
    val resolvedMaxLines = if (maxLines >= resolvedMinLines) maxLines else resolvedMinLines
    return resolvedMinLines to resolvedMaxLines
}

internal fun resolveLabel(label: String?): String? =
    if (label.isNullOrBlank()) null else label

internal fun resolveDescription(
    supportingText: String?,
    errorText: String?,
    isError: Boolean,
): String? = when {
    isError && !errorText.isNullOrBlank() -> errorText
    !supportingText.isNullOrBlank() -> supportingText
    else -> null
}

internal fun clampLength(value: String, maxLength: Int): String =
    if (value.length <= maxLength) value else value.take(maxLength)

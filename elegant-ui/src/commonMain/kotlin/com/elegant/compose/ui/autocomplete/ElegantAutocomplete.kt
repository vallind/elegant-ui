package com.elegant.compose.ui.autocomplete

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
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
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantElevation
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * One suggestion offered by [ElegantAutocomplete].
 *
 * [text] is rendered in the suggestion row, [value] is the stable identity delivered with the
 * selection, and [enabled] marks options that cannot be chosen. Disabled options render with the
 * tertiary text color, ignore clicks, and announce the disabled state through semantics.
 *
 * @property text text rendered in the suggestion row.
 * @property value stable identity delivered with the selection.
 * @property enabled whether the option can be chosen.
 */
@Immutable
public data class ElegantAutocompleteOption(
    val text: String,
    val value: String,
    val enabled: Boolean = true,
)

/**
 * Theme-aware state colors used by [ElegantAutocomplete].
 *
 * Use [ElegantAutocompleteDefaults.colors] for theme-aware defaults, then use [copy] for supported
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
 * @property contentColor default text and cursor color.
 * @property disabledContentColor disabled text and cursor color.
 * @property placeholderColor placeholder text color.
 * @property labelColor label text color.
 * @property supportingTextColor supporting text color.
 * @property errorTextColor error text color.
 */
@Immutable
public data class ElegantAutocompleteColors(
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

/** Theme-aware defaults for [ElegantAutocomplete]. */
public object ElegantAutocompleteDefaults {
    /** Minimum field-container height kept by the autocomplete field. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Maximum suggestion-list height before the list scrolls. */
    public val MenuMaxHeight: Dp = 280.dp

    /** Returns theme-aware colors for the field and its suggestion list. */
    @Composable
    public fun colors(): ElegantAutocompleteColors = resolveAutocompleteColors(ElegantTheme.colors)
}

/** Internal metrics shared by the autocomplete field and its suggestion list. */
internal object AutocompleteMetrics {
    /** Standard state-transition duration. */
    const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Gap between the field container and the suggestion surface. */
    val AnchorOffset: Dp = 4.dp

    /** Minimum height of one suggestion row. */
    val ItemMinHeight: Dp = 40.dp

    /** Horizontal padding inside every suggestion row. */
    val ItemHorizontalPadding: Dp = 16.dp

    /** Horizontal field padding. */
    val FieldHorizontalPadding: Dp = 14.dp

    /** Resting and hovered border stroke width. */
    val RestingBorderWidth: Dp = 1.dp

    /** Focused and error border stroke width. */
    val FocusBorderWidth: Dp = 2.dp
}

@Immutable
internal data class AutocompleteVisuals(
    val container: Color,
    val border: Color,
    val borderWidth: Dp,
)

/**
 * Displays a controlled text field with an inline suggestion list.
 *
 * The field follows the Filled input rhythm: a sunken 48dp container with a 12dp rounding that
 * paints a 2dp [ElegantTheme.colors.focusRing] border while focused or a 2dp
 * [ElegantTheme.colors.statusCritical] border while in error, and a transparent 1dp border
 * otherwise. A label, placeholder, supporting text, and error text behave exactly like
 * [com.elegant.compose.ui.input.ElegantInput].
 *
 * While the field is focused and at least one option matches the query, the suggestions appear on
 * a raised surface anchored below the field: it is as wide as the field, rounded with
 * [ElegantRadius.md], casts a medium shadow, and scrolls once its content exceeds
 * [ElegantAutocompleteDefaults.MenuMaxHeight]. The popup captures focus so items stay keyboard
 * accessible. The list closes when a suggestion is selected, when the user clicks outside or
 * presses Escape (both delivered by the platform popup), or when the field loses focus. Selecting
 * an option invokes [onOptionSelected] with that option and closes the list.
 *
 * A blank query shows every option; otherwise options whose [ElegantAutocompleteOption.text]
 * contains the query are shown, case-insensitively, in their original order. The displayed query
 * is always the caller-owned state: the caller decides how to update it after a selection.
 *
 * Semantics: the field keeps the text-field role of the underlying [BasicTextField], announces the
 * error text when [isError] and [errorText] is non-blank, and announces the disabled state when
 * [enabled] is false. Disabled options never invoke the selection callback.
 *
 * @param query the current query text, owned by the caller.
 * @param onQueryChange callback invoked with the newest accepted query.
 * @param options full option list the suggestions are filtered from.
 * @param onOptionSelected callback invoked with the option chosen from the suggestion list.
 * @param modifier modifier applied once to the component root.
 * @param enabled whether the field accepts focus and input and the list can open.
 * @param label optional label shown above the field.
 * @param placeholder optional hint shown inside the field while it is enabled and empty.
 * @param isError whether the field communicates an error state.
 * @param errorText optional error message shown below the field and announced through semantics
 *   when [isError].
 * @param supportingText optional guidance shown below the field unless error text is shown.
 * @param colors theme-aware state colors.
 */
@Composable
public fun ElegantAutocomplete(
    query: String,
    onQueryChange: (String) -> Unit,
    options: List<ElegantAutocompleteOption>,
    onOptionSelected: (ElegantAutocompleteOption) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    errorText: String? = null,
    supportingText: String? = null,
    colors: ElegantAutocompleteColors = ElegantAutocompleteDefaults.colors(),
) {
    val resolvedLabel = label?.takeIf { it.isNotBlank() }
    val resolvedErrorText = if (isError && !errorText.isNullOrBlank()) errorText else null
    val filteredOptions = remember(query, options) { filterOptions(options, query) }
    val shape = RoundedCornerShape(ElegantRadius.md)
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val focusRingEnabled = ElegantTheme.focusRingEnabled
    val visuals = resolveAutocompleteVisuals(
        colors = colors,
        enabled = enabled,
        hovered = hovered,
        focused = focused && focusRingEnabled,
        isError = isError,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantAutocompleteContainer",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantAutocompleteBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantAutocompleteBorderWidth",
    )
    val animatedContent by animateColorAsState(
        targetValue = if (enabled) colors.contentColor else colors.disabledContentColor,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantAutocompleteContent",
    )
    val inputTextStyle = ElegantTheme.typography.bodyMedium.copy(color = animatedContent)

    var expanded by remember { mutableStateOf(false) }
    var fieldHasFocus by remember { mutableStateOf(false) }
    var popupHasFocus by remember { mutableStateOf(false) }
    var anchorWidth by remember { mutableStateOf(0.dp) }

    LaunchedEffect(enabled) {
        if (!enabled) expanded = false
    }
    LaunchedEffect(expanded, fieldHasFocus, popupHasFocus) {
        if (expanded && !fieldHasFocus && !popupHasFocus) expanded = false
    }

    val density = LocalDensity.current
    val offsetPx = with(density) { AutocompleteMetrics.AnchorOffset.roundToPx() }
    val positionProvider = remember(offsetPx) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset = autocompleteListPosition(
                anchorBounds = anchorBounds,
                listSize = popupContentSize,
                offsetPx = offsetPx,
                windowSize = windowSize,
            )
        }
    }
    val currentOnOptionSelected by rememberUpdatedState(onOptionSelected)
    val showList = expanded && enabled && anchorWidth > 0.dp && suggestionListVisible(
        focused = fieldHasFocus || popupHasFocus,
        filteredCount = filteredOptions.size,
    )
    val showPlaceholder = enabled && query.isEmpty() && placeholder != null
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
                    anchorWidth = with(density) { position.size.width.toDp() }
                }
                .onFocusChanged { state ->
                    fieldHasFocus = state.isFocused
                    if (state.isFocused && enabled) expanded = true
                },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = ElegantAutocompleteDefaults.MinimumTouchHeight)
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
                    .padding(horizontal = AutocompleteMetrics.FieldHorizontalPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier.weight(1f),
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

            if (showList) {
                Popup(
                    popupPositionProvider = positionProvider,
                    onDismissRequest = { expanded = false },
                    properties = PopupProperties(focusable = true),
                ) {
                    val focusRequester = remember { FocusRequester() }
                    val surfaceShape = RoundedCornerShape(ElegantRadius.md)
                    Box(
                        modifier = Modifier
                            .onFocusChanged { popupHasFocus = it.isFocused }
                            .focusRequester(focusRequester)
                            .focusable()
                            .shadow(
                                elevation = ElegantElevation.medium,
                                shape = surfaceShape,
                                clip = false,
                            )
                            .clip(surfaceShape)
                            .background(ElegantTheme.colors.surfaceRaised)
                            .width(anchorWidth)
                            .heightIn(max = ElegantAutocompleteDefaults.MenuMaxHeight),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = ElegantAutocompleteDefaults.MenuMaxHeight)
                                .verticalScroll(rememberScrollState()),
                        ) {
                            filteredOptions.forEach { option ->
                                AutocompleteOptionRow(
                                    option = option,
                                    onClick = {
                                        currentOnOptionSelected(option)
                                        expanded = false
                                    },
                                )
                            }
                        }
                    }
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
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

@Composable
private fun AutocompleteOptionRow(
    option: ElegantAutocompleteOption,
    onClick: () -> Unit,
) {
    val themeColors = ElegantTheme.colors
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val rowBackground = if (option.enabled && hovered) themeColors.surfaceHover else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = AutocompleteMetrics.ItemMinHeight)
            .background(rowBackground)
            .clickable(
                enabled = option.enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick,
            )
            .semantics {
                role = Role.Button
                if (!option.enabled) disabled()
            }
            .padding(horizontal = AutocompleteMetrics.ItemHorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = option.text,
            modifier = Modifier.fillMaxWidth(),
            color = if (option.enabled) themeColors.textPrimary else themeColors.textTertiary,
            style = ElegantTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/** Resolves the theme-aware filled field colors from [themeColors]. */
internal fun resolveAutocompleteColors(themeColors: ElegantColors): ElegantAutocompleteColors =
    ElegantAutocompleteColors(
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
internal fun resolveAutocompleteVisuals(
    colors: ElegantAutocompleteColors,
    enabled: Boolean,
    hovered: Boolean,
    focused: Boolean,
    isError: Boolean,
): AutocompleteVisuals {
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
        !enabled -> AutocompleteMetrics.RestingBorderWidth
        isError -> AutocompleteMetrics.FocusBorderWidth
        focused -> AutocompleteMetrics.FocusBorderWidth
        else -> AutocompleteMetrics.RestingBorderWidth
    }
    return AutocompleteVisuals(
        container = container,
        border = border,
        borderWidth = borderWidth,
    )
}

/** Filters [options] against [query]: a blank query returns everything, otherwise options whose text contains the query, case-insensitively, keep their original order. */
internal fun filterOptions(
    options: List<ElegantAutocompleteOption>,
    query: String,
): List<ElegantAutocompleteOption> = if (query.isBlank()) {
    options
} else {
    options.filter { option -> option.text.contains(query, ignoreCase = true) }
}

/** Whether the suggestion list should be shown for the given focus and match count. */
internal fun suggestionListVisible(focused: Boolean, filteredCount: Int): Boolean =
    focused && filteredCount > 0

/** Places the suggestion surface below the anchor, start-aligned, clamped into the window. */
internal fun autocompleteListPosition(
    anchorBounds: IntRect,
    listSize: IntSize,
    offsetPx: Int,
    windowSize: IntSize,
): IntOffset = IntOffset(
    x = anchorBounds.left.coerceIn(0, (windowSize.width - listSize.width).coerceAtLeast(0)),
    y = (anchorBounds.top + anchorBounds.height + offsetPx)
        .coerceIn(0, (windowSize.height - listSize.height).coerceAtLeast(0)),
)

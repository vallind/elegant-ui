package com.elegant.compose.ui.searchbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantSearchBar].
 *
 * Use [ElegantSearchBarDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor default container color.
 * @property hoveredContainerColor hovered container color.
 * @property focusedContainerColor focused container color.
 * @property disabledContainerColor disabled container color.
 * @property borderColor default border color.
 * @property hoveredBorderColor hovered border color.
 * @property focusedBorderColor focused border color.
 * @property disabledBorderColor disabled border color.
 * @property contentColor default text and cursor color.
 * @property disabledContentColor disabled text and cursor color.
 * @property placeholderColor placeholder text color.
 */
@Immutable
public data class ElegantSearchBarColors(
    val containerColor: Color,
    val hoveredContainerColor: Color,
    val focusedContainerColor: Color,
    val disabledContainerColor: Color,
    val borderColor: Color,
    val hoveredBorderColor: Color,
    val focusedBorderColor: Color,
    val disabledBorderColor: Color,
    val contentColor: Color,
    val disabledContentColor: Color,
    val placeholderColor: Color,
)

/** Theme-aware defaults for [ElegantSearchBar]. */
public object ElegantSearchBarDefaults {
    /** Minimum field-container height kept by the search field. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Returns theme-aware state colors for the filled search field. */
    @Composable
    public fun colors(): ElegantSearchBarColors = resolveSearchBarColors(
        themeColors = ElegantTheme.colors,
    )

    /** Returns the fully rounded pill shape of the search field. */
    public fun shape(): Shape = RoundedCornerShape(ElegantRadius.full)
}

internal object SearchBarMetrics {
    /** Standard state-transition duration. */
    const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Drawn search-glyph canvas size. */
    val MagnifierSize: Dp = 18.dp

    /** Drawn clear-glyph canvas size. */
    val ClearButtonSize: Dp = 18.dp

    /** Trailing slot box size. */
    val TrailingSlotSize: Dp = 20.dp

    /** Gap between the input area and either trailing affordance. */
    val Gap: Dp = 8.dp

    /** Horizontal field padding. */
    val HorizontalPadding: Dp = 14.dp

    /** Resting and hovered border stroke width. */
    val RestingBorderWidth: Dp = 1.dp

    /** Focused border stroke width. */
    val FocusBorderWidth: Dp = 2.dp

    /** Drawn glyph stroke width. */
    val GlyphStrokeWidth: Dp = 2.dp
}

@Immutable
internal data class SearchBarVisuals(
    val container: Color,
    val border: Color,
    val borderWidth: Dp,
)

/**
 * Displays a controlled single-line search field with an owned magnifier glyph, an optional clear
 * affordance, and a configurable search action.
 *
 * The field is a controlled component: [query] is owned by the caller and must be written back
 * from [onQueryChange]. The leading magnifier glyph is drawn by the component and is not
 * configurable. While the query is not empty a clear button with a drawn X appears at the trailing
 * edge, before [trailingContent]; activating it invokes [onClear] or, when [onClear] is null,
 * writes an empty query through [onQueryChange]. The placeholder is shown only while the field is
 * enabled and empty.
 *
 * When [onSearch] is set, the IME shows a search action button and the Enter key (including the
 * numeric-pad Enter on Desktop) triggers the callback; the search action is announced through the
 * field's keyboard actions.
 *
 * @param query the current search query, owned by the caller.
 * @param onQueryChange callback invoked with the newest accepted query.
 * @param modifier modifier applied once to the search field root.
 * @param enabled whether the field accepts focus, input, and the clear action.
 * @param placeholder optional hint shown inside the field while it is enabled and empty.
 * @param onSearch optional callback invoked by the IME search action and the Enter key; null
 *   disables both triggers.
 * @param onClear optional callback invoked by the clear button; null clears the query through
 *   [onQueryChange].
 * @param colors theme-aware state colors.
 * @param trailingContent optional content after the input area, shown at the trailing edge.
 */
@Composable
public fun ElegantSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: String? = null,
    onSearch: (() -> Unit)? = null,
    onClear: (() -> Unit)? = null,
    colors: ElegantSearchBarColors = ElegantSearchBarDefaults.colors(),
    trailingContent: (@Composable () -> Unit)? = null,
) {
    val shape = ElegantSearchBarDefaults.shape()
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val focusRingEnabled = ElegantTheme.focusRingEnabled
    val visuals = resolveSearchBarVisuals(
        colors = colors,
        enabled = enabled,
        hovered = hovered,
        focused = focused && focusRingEnabled,
    )
    val iconColor = resolveSearchBarIconColor(
        themeColors = ElegantTheme.colors,
        enabled = enabled,
    )
    val showClear = resolveClearVisibility(query)
    val showPlaceholder = enabled && query.isEmpty() && placeholder != null

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSearchBarContainer",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSearchBarBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSearchBarBorderWidth",
    )
    val animatedContent by animateColorAsState(
        targetValue = if (enabled) colors.contentColor else colors.disabledContentColor,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSearchBarContent",
    )
    val inputTextStyle = ElegantTheme.typography.bodyMedium.copy(color = animatedContent)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = ElegantSearchBarDefaults.MinimumTouchHeight)
            .semantics {
                if (!enabled) disabled()
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
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown && enabled && onSearch != null) {
                    if (event.key == Key.Enter || event.key == Key.NumPadEnter) {
                        onSearch()
                        true
                    } else {
                        false
                    }
                } else {
                    false
                }
            }
            .padding(horizontal = SearchBarMetrics.HorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Canvas(
            modifier = Modifier.size(SearchBarMetrics.MagnifierSize),
        ) {
            val strokeWidth = SearchBarMetrics.GlyphStrokeWidth.toPx()
            val center = Offset(size.width * 0.40f, size.height * 0.40f)
            val radius = size.minDimension * 0.24f
            drawCircle(
                color = iconColor,
                center = center,
                radius = radius,
                style = Stroke(width = strokeWidth),
            )
            val handleOffset = radius * 0.7071f
            drawLine(
                color = iconColor,
                start = Offset(center.x + handleOffset, center.y + handleOffset),
                end = Offset(size.width * 0.72f, size.height * 0.72f),
                strokeWidth = strokeWidth,
            )
        }
        Spacer(Modifier.width(SearchBarMetrics.Gap))

        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            enabled = enabled,
            singleLine = true,
            textStyle = inputTextStyle,
            cursorBrush = SolidColor(animatedContent),
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch?.invoke() }),
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

        if (showClear) {
            Spacer(Modifier.width(SearchBarMetrics.Gap))
            Box(
                modifier = Modifier
                    .size(SearchBarMetrics.ClearButtonSize)
                    .defaultMinSize(minHeight = ElegantSearchBarDefaults.MinimumTouchHeight)
                    .clickable(
                        enabled = enabled,
                        role = Role.Button,
                        onClick = {
                            onClear?.invoke() ?: onQueryChange("")
                        },
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Canvas(
                    modifier = Modifier.size(SearchBarMetrics.ClearButtonSize),
                ) {
                    val strokeWidth = SearchBarMetrics.GlyphStrokeWidth.toPx()
                    val inset = size.minDimension * 0.28f
                    drawLine(
                        color = iconColor,
                        start = Offset(inset, inset),
                        end = Offset(size.width - inset, size.height - inset),
                        strokeWidth = strokeWidth,
                    )
                    drawLine(
                        color = iconColor,
                        start = Offset(size.width - inset, inset),
                        end = Offset(inset, size.height - inset),
                        strokeWidth = strokeWidth,
                    )
                }
            }
        }

        if (trailingContent != null) {
            Spacer(Modifier.width(SearchBarMetrics.Gap))
            Box(
                modifier = Modifier.size(SearchBarMetrics.TrailingSlotSize),
                contentAlignment = Alignment.Center,
            ) {
                trailingContent()
            }
        }
    }
}

internal fun resolveSearchBarColors(
    themeColors: ElegantColors,
): ElegantSearchBarColors = ElegantSearchBarColors(
    containerColor = themeColors.surfaceSunken,
    hoveredContainerColor = themeColors.surfaceHover,
    focusedContainerColor = themeColors.surfaceRaised,
    disabledContainerColor = themeColors.surfaceSunken,
    borderColor = Color.Transparent,
    hoveredBorderColor = Color.Transparent,
    focusedBorderColor = themeColors.focusRing,
    disabledBorderColor = Color.Transparent,
    contentColor = themeColors.textPrimary,
    disabledContentColor = themeColors.textTertiary,
    placeholderColor = themeColors.textTertiary,
)

internal fun resolveSearchBarIconColor(
    themeColors: ElegantColors,
    enabled: Boolean,
): Color = if (enabled) themeColors.textSecondary else themeColors.textTertiary

internal fun resolveSearchBarVisuals(
    colors: ElegantSearchBarColors,
    enabled: Boolean,
    hovered: Boolean,
    focused: Boolean,
): SearchBarVisuals {
    val container = when {
        !enabled -> colors.disabledContainerColor
        focused -> colors.focusedContainerColor
        hovered -> colors.hoveredContainerColor
        else -> colors.containerColor
    }
    val border = when {
        !enabled -> colors.disabledBorderColor
        focused -> colors.focusedBorderColor
        hovered -> colors.hoveredBorderColor
        else -> colors.borderColor
    }
    val borderWidth = when {
        !enabled -> SearchBarMetrics.RestingBorderWidth
        focused -> SearchBarMetrics.FocusBorderWidth
        else -> SearchBarMetrics.RestingBorderWidth
    }
    return SearchBarVisuals(
        container = container,
        border = border,
        borderWidth = borderWidth,
    )
}

internal fun resolveClearVisibility(query: String): Boolean = query.isNotEmpty()

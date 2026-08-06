package com.elegant.compose.ui.select

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.collapse
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.expand
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * One choice offered by [ElegantSelect], used both as the selection model and the menu-item model.
 *
 * [value] is the stable identity used to compare options and to store or submit the choice;
 * [text] is what is rendered in the trigger and the menu item. Options with a blank [text] or
 * [value] are ignored when the option list is resolved.
 *
 * @property text text rendered in the trigger and the menu item.
 * @property value stable identity used to compare options and to store the choice.
 * @property enabled whether the option can be chosen from the menu.
 */
@Immutable
public data class ElegantSelectOption(
    val text: String,
    val value: String,
    val enabled: Boolean = true,
)

/** Theme-aware defaults for [ElegantSelect]. */
public object ElegantSelectDefaults {
    /** Minimum trigger height used by the select field. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Maximum menu height before the option list scrolls. */
    public val MenuMaxHeight: Dp = 320.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis
}

@Immutable
internal data class SelectVisuals(
    val container: Color,
    val border: Color,
    val borderWidth: Dp,
    val content: Color,
)

/**
 * Displays a controlled single-choice field with an optional label, placeholder, supporting or
 * error text, and a themed option menu.
 *
 * The select is a controlled component: [selectedOption] is owned by the caller and must be
 * written back from [onOptionSelected]. [options] is the full option list; entries with a blank
 * text or value are ignored. The trigger follows the Filled input rhythm: a sunken 48dp container
 * with a 12dp rounding that paints a 2dp [ElegantTheme.colors.focusRing] border while focused or
 * a 2dp [ElegantTheme.colors.statusCritical] border while in error, and a 1dp
 * [ElegantTheme.colors.borderDefault] border otherwise. Clicking it opens a Material 3 dropdown
 * menu fully themed with Elegant tokens: it captures focus, supports keyboard traversal and
 * escape, dismisses on outside click, and scrolls its options once they exceed
 * [ElegantSelectDefaults.MenuMaxHeight]. The chosen option carries a check indicator in the menu.
 *
 * Semantics: the trigger exposes [Role.DropdownList] with expand and collapse actions, announces
 * the selected option text, and is disabled while [enabled] is false. The error text is announced
 * through semantics when [isError]. When [enabled] is false the trigger rejects clicks and focus,
 * the menu cannot open, and the placeholder is hidden.
 *
 * @param selectedOption currently chosen option, or null when nothing is selected; owned by the
 *   caller and matched against menu options by data equality.
 * @param onOptionSelected callback invoked with the option chosen from the menu.
 * @param options full option list; entries with a blank text or value are ignored.
 * @param modifier modifier applied once to the select root.
 * @param enabled whether the trigger accepts clicks and focus and the menu can open.
 * @param label optional label shown above the field.
 * @param placeholder optional hint shown inside the field while it is enabled and nothing is
 *   selected.
 * @param isError whether the field communicates an error state.
 * @param errorText optional error message shown below the field and announced through semantics
 *   when [isError].
 * @param supportingText optional guidance shown below the field unless error text is shown.
 * @param interactionSource optional interaction source shared with the trigger; one is created
 *   and remembered when null.
 */
@Composable
public fun ElegantSelect(
    selectedOption: ElegantSelectOption?,
    onOptionSelected: (ElegantSelectOption) -> Unit,
    options: List<ElegantSelectOption>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    errorText: String? = null,
    supportingText: String? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    val resolvedLabel = label?.takeIf { it.isNotBlank() }
    val resolvedErrorText = if (isError && !errorText.isNullOrBlank()) errorText else null
    val resolvedOptions = remember(options) { validateOptions(options) }
    val themeColors = ElegantTheme.colors
    val shape = RoundedCornerShape(ElegantRadius.md)
    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    var expanded by remember { mutableStateOf(false) }
    val currentOnOptionSelected by rememberUpdatedState(onOptionSelected)

    LaunchedEffect(enabled) {
        if (!enabled) expanded = false
    }

    val visuals = resolveSelectVisuals(
        enabled = enabled,
        hovered = hovered,
        focused = focused || expanded,
        isError = isError,
        themeColors = themeColors,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSelectContainer",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSelectBorder",
    )
    val animatedBorderWidth by animateDpAsState(
        targetValue = visuals.borderWidth,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSelectBorderWidth",
    )
    val animatedContent by animateColorAsState(
        targetValue = visuals.content,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSelectContent",
    )
    val animatedChevron by animateColorAsState(
        targetValue = if (enabled) themeColors.textSecondary else themeColors.textTertiary,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSelectChevronColor",
    )
    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSelectChevronRotation",
    )

    val selectedText = selectOptionText(selectedOption)
    val showPlaceholder = enabled && selectedText == null && !placeholder.isNullOrBlank()
    val displayText = selectedText ?: if (showPlaceholder) placeholder else ""
    val displayColor = if (selectedText != null) {
        animatedContent
    } else {
        themeColors.textTertiary
    }
    val helperText = resolvedErrorText ?: supportingText

    Column(modifier = modifier) {
        if (resolvedLabel != null) {
            Text(
                text = resolvedLabel,
                modifier = Modifier.fillMaxWidth(),
                color = themeColors.textSecondary,
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
                    .defaultMinSize(minHeight = ElegantSelectDefaults.MinimumTouchHeight)
                    .semantics(mergeDescendants = true) {
                        role = Role.DropdownList
                        if (expanded) {
                            collapse {
                                if (enabled) {
                                    expanded = false
                                    true
                                } else {
                                    false
                                }
                            }
                        } else {
                            expand {
                                if (enabled) {
                                    expanded = true
                                    true
                                } else {
                                    false
                                }
                            }
                        }
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
                        interactionSource = resolvedInteractionSource,
                        indication = LocalIndication.current,
                        onClick = { expanded = true },
                    )
                    .padding(horizontal = 14.dp),
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
                Spacer(Modifier.width(ElegantSpacing.md))
                SelectChevron(
                    color = animatedChevron,
                    rotation = chevronRotation,
                )
            }

            if (enabled) {
                val itemColors = MenuDefaults.itemColors(
                    textColor = themeColors.textPrimary,
                    disabledTextColor = themeColors.textTertiary,
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.heightIn(max = ElegantSelectDefaults.MenuMaxHeight),
                    containerColor = themeColors.surfaceRaised,
                    shape = shape,
                    border = BorderStroke(1.dp, themeColors.borderDefault),
                ) {
                    resolvedOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = option.text,
                                    style = ElegantTheme.typography.bodyMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            },
                            onClick = {
                                currentOnOptionSelected(option)
                                expanded = false
                            },
                            enabled = option.enabled,
                            leadingIcon = if (option == selectedOption) {
                                { SelectCheckMark(color = themeColors.interactivePrimary) }
                            } else {
                                null
                            },
                            colors = itemColors,
                        )
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
                    themeColors.statusCritical
                } else {
                    themeColors.textSecondary
                },
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun SelectChevron(
    color: Color,
    rotation: Float,
) {
    Canvas(
        modifier = Modifier
            .size(12.dp)
            .graphicsLayer { rotationZ = rotation },
    ) {
        val strokeWidth = 1.5.dp.toPx()
        val midX = size.width / 2f
        val topY = size.height * 0.36f
        val bottomY = size.height * 0.64f
        drawLine(
            color = color,
            start = Offset(midX - size.width * 0.24f, topY),
            end = Offset(midX, bottomY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
        drawLine(
            color = color,
            start = Offset(midX, bottomY),
            end = Offset(midX + size.width * 0.24f, topY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
    }
}

@Composable
private fun SelectCheckMark(color: Color) {
    Canvas(modifier = Modifier.size(18.dp)) {
        val strokeWidth = 2.dp.toPx()
        val path = Path().apply {
            moveTo(size.width * 0.20f, size.height * 0.52f)
            lineTo(size.width * 0.44f, size.height * 0.74f)
            lineTo(size.width * 0.82f, size.height * 0.26f)
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            ),
        )
    }
}

internal fun resolveSelectVisuals(
    enabled: Boolean,
    hovered: Boolean,
    focused: Boolean,
    isError: Boolean,
    themeColors: ElegantColors,
): SelectVisuals {
    val container = when {
        !enabled -> themeColors.surfaceSunken
        focused -> themeColors.surfaceRaised
        hovered -> themeColors.surfaceHover
        else -> themeColors.surfaceSunken
    }
    val border = when {
        !enabled -> themeColors.borderDefault
        isError -> themeColors.statusCritical
        focused -> themeColors.focusRing
        else -> themeColors.borderDefault
    }
    val borderWidth = when {
        !enabled -> 1.dp
        isError -> 2.dp
        focused -> 2.dp
        else -> 1.dp
    }
    val content = if (enabled) themeColors.textPrimary else themeColors.textTertiary
    return SelectVisuals(
        container = container,
        border = border,
        borderWidth = borderWidth,
        content = content,
    )
}

internal fun selectOptionText(option: ElegantSelectOption?): String? = option?.text

internal fun validateOptions(options: List<ElegantSelectOption>): List<ElegantSelectOption> =
    options.filter { option -> option.text.isNotBlank() && option.value.isNotBlank() }

internal fun findSelectableOption(
    options: List<ElegantSelectOption>,
    index: Int,
    direction: Int,
): Int {
    val step = direction.coerceIn(-1, 1)
    if (options.isEmpty() || step == 0) return -1
    var candidate = if (index in options.indices) index else if (step > 0) -1 else options.size
    repeat(options.size) {
        candidate += step
        candidate = ((candidate % options.size) + options.size) % options.size
        if (options[candidate].enabled) return candidate
    }
    return -1
}

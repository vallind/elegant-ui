package com.elegant.compose.ui.accordion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.collapse
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.expand
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
 * Theme-aware state colors used by [ElegantAccordion] and [ElegantAccordionItem].
 *
 * Use [ElegantAccordionDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor default container color of the accordion surface.
 * @property contentColor default content color, provided to item content through
 *   [LocalContentColor].
 * @property headerContainerColor resting header color; transparent by default so the container
 *   surface shows through.
 * @property hoveredHeaderContainerColor hovered (and keyboard-focused) header color.
 * @property pressedHeaderContainerColor pressed header color.
 * @property borderColor 1dp border color of the accordion surface.
 * @property dividerColor separator color reserved for product-level item dividers; the default
 *   layout draws no dividers, so items are separated by their collapsed and expanded states.
 */
@Immutable
public data class ElegantAccordionColors(
    val containerColor: Color,
    val contentColor: Color,
    val headerContainerColor: Color,
    val hoveredHeaderContainerColor: Color = headerContainerColor,
    val pressedHeaderContainerColor: Color = headerContainerColor,
    val borderColor: Color,
    val dividerColor: Color,
)

/** Theme-aware defaults for [ElegantAccordion]. */
public object ElegantAccordionDefaults {
    /** Minimum interactive header height used by every [ElegantAccordionItem]. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard expand, collapse, and state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors for [ElegantAccordion] and [ElegantAccordionItem]. */
    @Composable
    public fun colors(): ElegantAccordionColors = resolveAccordionColors(ElegantTheme.colors)
}

/** Chevron edge length used in every [ElegantAccordionItem] header. */
internal val AccordionChevronSize: Dp = 18.dp

/**
 * Groups expandable content in one bordered surface.
 *
 * [ElegantAccordion] is a `Column` container with a 1dp [ElegantAccordionColors.borderColor]
 * border and the [ElegantRadius.md] corner radius. It adds no spacing, so [ElegantAccordionItem]s
 * stack flush against each other; the default layout draws no dividers between items, leaving the
 * collapsed and expanded states to separate them visually. Content receives
 * [ElegantAccordionColors.contentColor] through [LocalContentColor].
 *
 * @param modifier modifier applied once to the accordion root.
 * @param colors theme-aware state colors.
 * @param content accordion content; typically one or more [ElegantAccordionItem]s.
 */
@Composable
public fun ElegantAccordion(
    modifier: Modifier = Modifier,
    colors: ElegantAccordionColors = ElegantAccordionDefaults.colors(),
    content: @Composable () -> Unit,
) {
    val shape = RoundedCornerShape(ElegantRadius.md)

    Column(
        modifier = modifier
            .clip(shape)
            .background(colors.containerColor)
            .border(
                border = BorderStroke(1.dp, colors.borderColor),
                shape = shape,
            ),
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
            content()
        }
    }
}

/**
 * One controlled expandable entry inside [ElegantAccordion].
 *
 * The header row keeps a 48dp minimum interactive root with 16dp horizontal padding and combines
 * a leading chevron with the [title] and an optional [supportingText]. The chevron draws two
 * lines and rotates 180 degrees while [expanded], with the rotation animated over
 * [ElegantAccordionDefaults.AnimationDurationMillis]. Header color follows the
 * disabled, pressed, hovered-or-focused, resting precedence. The body shows through
 * [AnimatedVisibility] with a vertical expand and fade-in, wrapped in 16dp horizontal and bottom
 * padding; no divider separates the header from the body.
 *
 * The item is controlled: [expanded] is owned by the caller and must be written back from
 * [onToggle]. Semantics merge into one [Role.Button] node that announces [expanded] through the
 * `expanded` property, exposes expand or collapse actions, and is disabled while [enabled] is
 * false. A disabled item never invokes [onToggle], and its chevron drops to the tertiary text
 * color.
 *
 * @param title header label; truncated with an ellipsis when it does not fit on one line.
 * @param expanded whether the body is expanded; owned by the caller.
 * @param onToggle callback invoked when the header is activated to toggle [expanded].
 * @param modifier modifier applied once to the item root.
 * @param enabled whether the header accepts activation.
 * @param supportingText optional secondary line under [title], shown in the secondary text color.
 * @param colors theme-aware state colors.
 * @param content body shown while [expanded]; padding is owned by the item.
 */
@Composable
public fun ElegantAccordionItem(
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    supportingText: String? = null,
    colors: ElegantAccordionColors = ElegantAccordionDefaults.colors(),
    content: @Composable () -> Unit,
) {
    val resolvedInteractionSource = remember { MutableInteractionSource() }
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    val themeColors = ElegantTheme.colors
    val headerContainer = resolveAccordionHeaderContainer(
        colors = colors,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered || focused,
    )
    val animatedHeaderContainer by animateColorAsState(
        targetValue = headerContainer,
        animationSpec = tween(
            durationMillis = ElegantAccordionDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantAccordionHeaderContainer",
    )
    val animatedChevronRotation by animateFloatAsState(
        targetValue = chevronRotation(expanded),
        animationSpec = tween(
            durationMillis = ElegantAccordionDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantAccordionChevronRotation",
    )
    val isExpanded = expanded

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = ElegantAccordionDefaults.MinimumTouchHeight)
                .semantics(mergeDescendants = true) {
                    role = Role.Button
                    if (isExpanded) {
                        collapse {
                            if (enabled) {
                                onToggle()
                                true
                            } else {
                                false
                            }
                        }
                    } else {
                        expand {
                            if (enabled) {
                                onToggle()
                                true
                            } else {
                                false
                            }
                        }
                    }
                    if (!enabled) disabled()
                }
                .background(animatedHeaderContainer)
                .clickable(
                    enabled = enabled,
                    role = Role.Button,
                    interactionSource = resolvedInteractionSource,
                    indication = null,
                    onClick = { onToggle() },
                )
                .padding(horizontal = ElegantSpacing.xl),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AccordionChevron(
                color = if (enabled) {
                    themeColors.textSecondary
                } else {
                    themeColors.textTertiary
                },
                rotation = animatedChevronRotation,
            )
            Spacer(Modifier.width(ElegantSpacing.md))
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = ElegantTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (supportingText != null) {
                    Text(
                        text = supportingText,
                        color = themeColors.textSecondary,
                        style = ElegantTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(
                animationSpec = tween(
                    durationMillis = ElegantAccordionDefaults.AnimationDurationMillis,
                    easing = FastOutSlowInEasing,
                ),
            ) + fadeIn(
                animationSpec = tween(
                    durationMillis = ElegantAccordionDefaults.AnimationDurationMillis,
                    easing = FastOutSlowInEasing,
                ),
            ),
            exit = shrinkVertically(
                animationSpec = tween(
                    durationMillis = ElegantAccordionDefaults.AnimationDurationMillis,
                    easing = FastOutSlowInEasing,
                ),
            ) + fadeOut(
                animationSpec = tween(
                    durationMillis = ElegantAccordionDefaults.AnimationDurationMillis,
                    easing = FastOutSlowInEasing,
                ),
            ),
        ) {
            Box(
                modifier = Modifier.padding(
                    start = ElegantSpacing.xl,
                    end = ElegantSpacing.xl,
                    bottom = ElegantSpacing.xl,
                ),
            ) {
                content()
            }
        }
    }
}

@Composable
private fun AccordionChevron(
    color: Color,
    rotation: Float,
) {
    Canvas(
        modifier = Modifier
            .size(AccordionChevronSize)
            .graphicsLayer { rotationZ = rotation },
    ) {
        val strokeWidth = 2.dp.toPx()
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

internal fun resolveAccordionColors(themeColors: ElegantColors): ElegantAccordionColors =
    ElegantAccordionColors(
        containerColor = themeColors.surfaceRaised,
        contentColor = themeColors.textPrimary,
        headerContainerColor = Color.Transparent,
        hoveredHeaderContainerColor = themeColors.surfaceHover,
        pressedHeaderContainerColor = themeColors.backgroundSubtle,
        borderColor = themeColors.borderDefault,
        dividerColor = themeColors.borderDefault,
    )

internal fun resolveAccordionHeaderContainer(
    colors: ElegantAccordionColors,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
): Color = when {
    !enabled -> colors.headerContainerColor
    pressed -> colors.pressedHeaderContainerColor
    hovered -> colors.hoveredHeaderContainerColor
    else -> colors.headerContainerColor
}

internal fun chevronRotation(expanded: Boolean): Float = if (expanded) 180f else 0f

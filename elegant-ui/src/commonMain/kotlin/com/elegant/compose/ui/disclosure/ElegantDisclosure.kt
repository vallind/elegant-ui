package com.elegant.compose.ui.disclosure

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
import androidx.compose.foundation.LocalIndication
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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantDisclosure] and [ElegantDisclosureGroup].
 *
 * Use [ElegantDisclosureDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor default container color of the disclosure surface.
 * @property contentColor default content color, provided to the body through [LocalContentColor].
 * @property headerContainerColor resting header color; transparent by default so the container
 *   surface shows through.
 * @property hoveredHeaderContainerColor hovered (and keyboard-focused) header color.
 * @property pressedHeaderContainerColor pressed header color.
 * @property borderColor 1dp border color of the disclosure surface.
 */
@Immutable
public data class ElegantDisclosureColors(
    val containerColor: Color,
    val contentColor: Color,
    val headerContainerColor: Color,
    val hoveredHeaderContainerColor: Color = headerContainerColor,
    val pressedHeaderContainerColor: Color = headerContainerColor,
    val borderColor: Color,
)

/** Theme-aware defaults for [ElegantDisclosure] and [ElegantDisclosureGroup]. */
public object ElegantDisclosureDefaults {
    /** Minimum interactive header height used by every [ElegantDisclosure]. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard expand, collapse, and state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors for [ElegantDisclosure] and [ElegantDisclosureGroup]. */
    @Composable
    public fun colors(): ElegantDisclosureColors = resolveDisclosureColors(ElegantTheme.colors)
}

/** Chevron edge length used in every [ElegantDisclosure] header. */
internal val DisclosureChevronSize: Dp = 18.dp

/**
 * One controlled expandable section presented as its own bordered block.
 *
 * [ElegantDisclosure] is a `Column` surface with a 1dp [ElegantDisclosureColors.borderColor]
 * border and the [ElegantRadius.md] corner radius, so a standalone disclosure reads as one card.
 * The header row keeps a 48dp minimum interactive root with 16dp horizontal padding and combines
 * the [title] and an optional [supportingText] with a trailing chevron. The chevron draws two
 * lines and rotates 180 degrees while [expanded], with the rotation animated over
 * [ElegantDisclosureDefaults.AnimationDurationMillis]. Header color follows the
 * disabled, pressed, hovered-or-focused, resting precedence. The body shows through
 * [AnimatedVisibility] with a vertical expand and fade-in, wrapped in 16dp horizontal and bottom
 * padding; no divider separates the header from the body.
 *
 * The disclosure is controlled: [expanded] is owned by the caller and must be written back from
 * [onToggle]. Semantics merge into one [Role.Button] node that announces [expanded] through the
 * `expanded` property, exposes expand or collapse actions, and is disabled while [enabled] is
 * false. A disabled disclosure never invokes [onToggle], and its chevron drops to the tertiary
 * text color.
 *
 * @param title header label; truncated with an ellipsis when it does not fit on one line.
 * @param expanded whether the body is expanded; owned by the caller.
 * @param onToggle callback invoked when the header is activated to toggle [expanded].
 * @param modifier modifier applied once to the disclosure root.
 * @param enabled whether the header accepts activation.
 * @param supportingText optional secondary line under [title], shown in the secondary text color.
 * @param colors theme-aware state colors.
 * @param content body shown while [expanded]; padding is owned by the disclosure.
 */
@Composable
public fun ElegantDisclosure(
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    supportingText: String? = null,
    colors: ElegantDisclosureColors = ElegantDisclosureDefaults.colors(),
    content: @Composable () -> Unit,
) {
    val shape = RoundedCornerShape(ElegantRadius.md)
    val resolvedInteractionSource = remember { MutableInteractionSource() }
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    val focusRingEnabled = ElegantTheme.focusRingEnabled
    val themeColors = ElegantTheme.colors
    val headerContainer = resolveDisclosureHeaderContainer(
        colors = colors,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered || (focused && focusRingEnabled),
    )
    val animatedHeaderContainer by animateColorAsState(
        targetValue = headerContainer,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantDisclosureHeaderContainer",
    )
    val animatedChevronRotation by animateFloatAsState(
        targetValue = chevronRotation(expanded),
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantDisclosureChevronRotation",
    )
    val isExpanded = expanded

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = ElegantDisclosureDefaults.MinimumTouchHeight)
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
                        indication = LocalIndication.current,
                        onClick = { onToggle() },
                    )
                    .padding(horizontal = ElegantSpacing.xl),
                verticalAlignment = Alignment.CenterVertically,
            ) {
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
                DisclosureChevron(
                    color = if (enabled) {
                        themeColors.textSecondary
                    } else {
                        themeColors.textTertiary
                    },
                    rotation = animatedChevronRotation,
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = ElegantDisclosureDefaults.AnimationDurationMillis,
                        easing = FastOutSlowInEasing,
                    ),
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = ElegantDisclosureDefaults.AnimationDurationMillis,
                        easing = FastOutSlowInEasing,
                    ),
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = ElegantDisclosureDefaults.AnimationDurationMillis,
                        easing = FastOutSlowInEasing,
                    ),
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = ElegantDisclosureDefaults.AnimationDurationMillis,
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
}

/**
 * Groups [ElegantDisclosure]s inside one bordered surface.
 *
 * [ElegantDisclosureGroup] is a `Column` container with a 1dp [ElegantDisclosureColors.borderColor]
 * border and the [ElegantRadius.md] corner radius, mirroring the [com.elegant.compose.ui.accordion.
 * ElegantAccordion] container. It adds no spacing, so disclosures stack flush against each other;
 * the group draws no dividers between siblings because a disclosure cannot know whether it is the
 * last child. Each disclosure keeps its own bordered block, so the group border wraps the stack
 * and the per-disclosure borders separate the entries. Content receives
 * [ElegantDisclosureColors.contentColor] through [LocalContentColor].
 *
 * @param modifier modifier applied once to the group root.
 * @param colors theme-aware state colors.
 * @param content group content; typically one or more [ElegantDisclosure]s.
 */
@Composable
public fun ElegantDisclosureGroup(
    modifier: Modifier = Modifier,
    colors: ElegantDisclosureColors = ElegantDisclosureDefaults.colors(),
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

@Composable
private fun DisclosureChevron(
    color: Color,
    rotation: Float,
) {
    Canvas(
        modifier = Modifier
            .size(DisclosureChevronSize)
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

internal fun resolveDisclosureColors(themeColors: ElegantColors): ElegantDisclosureColors =
    ElegantDisclosureColors(
        containerColor = themeColors.surfaceRaised,
        contentColor = themeColors.textPrimary,
        headerContainerColor = Color.Transparent,
        hoveredHeaderContainerColor = themeColors.surfaceHover,
        pressedHeaderContainerColor = themeColors.backgroundSubtle,
        borderColor = themeColors.borderDefault,
    )

internal fun resolveDisclosureHeaderContainer(
    colors: ElegantDisclosureColors,
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

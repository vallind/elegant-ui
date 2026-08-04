// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.ui.basiccomponent

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantBasicComponent].
 *
 * Use [ElegantBasicComponentDefaults.colors] for theme-aware defaults, then use [copy] for
 * supported product-level customization.
 *
 * @property containerColor resting row background color.
 * @property titleColor title text color.
 * @property summaryColor summary text color.
 * @property disabledTitleColor title color while interaction is disabled.
 * @property disabledSummaryColor summary color while interaction is disabled.
 * @property hoveredContainerColor row background color while a pointer hovers the row.
 * @property pressedContainerColor row background color while the row is pressed.
 */
@Immutable
public data class ElegantBasicComponentColors(
    val containerColor: Color,
    val titleColor: Color,
    val summaryColor: Color,
    val disabledTitleColor: Color,
    val disabledSummaryColor: Color,
    val hoveredContainerColor: Color = containerColor,
    val pressedContainerColor: Color = containerColor,
)

/** Theme-aware defaults for [ElegantBasicComponent]. */
public object ElegantBasicComponentDefaults {
    /** Minimum interactive row height. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Default padding inside the row. */
    public val InsideMargin: PaddingValues = PaddingValues(
        horizontal = ElegantSpacing.xl,
        vertical = ElegantSpacing.sm,
    )

    /** Returns theme-aware state colors. */
    @Composable
    public fun colors(): ElegantBasicComponentColors = resolveBasicComponentColors(ElegantTheme.colors)
}

@Immutable
internal data class BasicComponentVisuals(
    val containerColor: Color,
)

internal const val BasicComponentAnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

/**
 * Presents a settings-style row with optional leading content, a title, a summary, trailing
 * content, and an optional bottom block.
 *
 * The whole row is the interactive target when [onClick] is provided: the row keeps the
 * [ElegantBasicComponentDefaults.MinimumTouchHeight] minimum height, animates hovered and
 * pressed container colors, and announces [role], an
 * optional [onClickLabel], and the disabled state. [holdDownState] forces the pressed visual
 * state while true. When [content] is provided it replaces the standard title and summary text
 * block; [bottomAction] renders below the row.
 *
 * @param title optional row title; rendered with the summary unless [content] is provided.
 * @param modifier modifier applied once to the interactive row.
 * @param summary optional summary text below the title; blank text is hidden.
 * @param startAction optional leading content before the title block.
 * @param endActions optional trailing content after the title block.
 * @param bottomAction optional content rendered below the row.
 * @param onClick optional callback invoked when the row accepts a click; null keeps it plain.
 * @param onClickLabel optional accessible label describing the row action.
 * @param role optional semantic role announced for the row.
 * @param selected optional selected state announced for the row; null omits the property.
 * @param holdDownState forces the pressed visual state while true.
 * @param enabled whether user interaction is accepted.
 * @param colors theme-aware state colors.
 * @param insideMargin padding inside the row.
 * @param content optional replacement for the standard title and summary text block.
 */
@Composable
public fun ElegantBasicComponent(
    title: String? = null,
    modifier: Modifier = Modifier,
    summary: String? = null,
    startAction: (@Composable () -> Unit)? = null,
    endActions: (@Composable RowScope.() -> Unit)? = null,
    bottomAction: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    onClickLabel: String? = null,
    role: Role? = null,
    selected: Boolean? = null,
    holdDownState: Boolean = false,
    enabled: Boolean = true,
    colors: ElegantBasicComponentColors = ElegantBasicComponentDefaults.colors(),
    insideMargin: PaddingValues = ElegantBasicComponentDefaults.InsideMargin,
    content: @Composable () -> Unit = {},
) {
    val interactive = onClick != null
    val interactionSource = remember { MutableInteractionSource() }
    if (holdDownState) {
        DisposableEffect(Unit) {
            val press = PressInteraction.Press(Offset.Zero)
            interactionSource.tryEmit(press)
            onDispose { interactionSource.tryEmit(PressInteraction.Release(press)) }
        }
    }
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val visuals = resolveBasicComponentVisuals(
        colors = colors,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
    )
    val animatedContainer by animateColorAsState(
        targetValue = visuals.containerColor,
        animationSpec = tween(
            durationMillis = BasicComponentAnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantBasicComponentContainer",
    )
    val currentOnClick by rememberUpdatedState(onClick)
    val resolvedRole = role ?: if (interactive) Role.Button else null
    val hasCustomContent = content !== EmptyBasicComponentContent

    Column(
        modifier = modifier
            .background(animatedContainer)
            .defaultMinSize(minHeight = ElegantBasicComponentDefaults.MinimumTouchHeight)
            .then(
                if (interactive) {
                    Modifier.clickable(
                        enabled = enabled,
                        role = resolvedRole,
                        onClickLabel = onClickLabel,
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { currentOnClick?.invoke() },
                    )
                } else {
                    Modifier
                },
            )
            .semantics {
                if (resolvedRole != null) this.role = resolvedRole
                if (selected != null) this.selected = selected!!
                if (interactive && !enabled) disabled()
            }
            .padding(insideMargin),
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (startAction != null) {
                startAction()
                Spacer(modifier = Modifier.width(ElegantSpacing.md))
            }
            Column(modifier = Modifier.weight(1f)) {
                if (hasCustomContent) {
                    content()
                } else {
                    if (title != null) {
                        Text(
                            text = title,
                            style = ElegantTheme.typography.labelMedium,
                            color = basicComponentTitleColor(colors, enabled),
                        )
                    }
                    if (summary != null) {
                        Text(
                            text = summary,
                            style = ElegantTheme.typography.bodyMedium,
                            color = basicComponentSummaryColor(colors, enabled),
                        )
                    }
                }
            }
            if (endActions != null) {
                Spacer(modifier = Modifier.width(ElegantSpacing.md))
                endActions()
            }
        }
        if (bottomAction != null) {
            Spacer(modifier = Modifier.width(0.dp))
            bottomAction()
        }
    }
}

private val EmptyBasicComponentContent: @Composable () -> Unit = {}

internal fun resolveBasicComponentColors(themeColors: ElegantColors): ElegantBasicComponentColors =
    ElegantBasicComponentColors(
        containerColor = themeColors.surfaceDefault,
        titleColor = themeColors.textPrimary,
        summaryColor = themeColors.textSecondary,
        disabledTitleColor = themeColors.textTertiary,
        disabledSummaryColor = themeColors.textTertiary,
        hoveredContainerColor = themeColors.surfaceHover,
        pressedContainerColor = themeColors.surfaceSunken,
    )

internal fun basicComponentTitleColor(
    colors: ElegantBasicComponentColors,
    enabled: Boolean,
): Color = if (enabled) colors.titleColor else colors.disabledTitleColor

internal fun basicComponentSummaryColor(
    colors: ElegantBasicComponentColors,
    enabled: Boolean,
): Color = if (enabled) colors.summaryColor else colors.disabledSummaryColor

internal fun resolveBasicComponentVisuals(
    colors: ElegantBasicComponentColors,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
): BasicComponentVisuals {
    val container = when {
        !enabled -> colors.containerColor
        pressed -> colors.pressedContainerColor
        hovered -> colors.hoveredContainerColor
        else -> colors.containerColor
    }
    return BasicComponentVisuals(containerColor = container)
}

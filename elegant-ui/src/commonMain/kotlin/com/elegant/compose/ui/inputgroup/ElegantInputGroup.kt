package com.elegant.compose.ui.inputgroup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

/** Shared outline width of the joined group border. */
internal val InputGroupBorderWidth: Dp = 1.dp

/** Corner rounding shared by the group container and its border. */
internal val InputGroupShape: Shape = RoundedCornerShape(ElegantRadius.md)

/**
 * Theme-aware colors used by [ElegantInputGroup].
 *
 * Use [ElegantInputGroupDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor shared background of the joined cluster.
 * @property borderColor shared outline color of the joined cluster.
 * @property contentColor content color provided to children through `LocalContentColor`.
 */
@Immutable
public data class ElegantInputGroupColors(
    val containerColor: Color,
    val borderColor: Color,
    val contentColor: Color,
)

/** Theme-aware defaults for [ElegantInputGroup]. */
public object ElegantInputGroupDefaults {
    /** Returns theme-aware cluster colors. */
    @Composable
    public fun colors(): ElegantInputGroupColors = resolveInputGroupColors(ElegantTheme.colors)
}

/**
 * Joins adjacent fields and inline actions into one bordered cluster.
 *
 * [content] renders inside a `Row` that sits behind a shared rounded container: the cluster is
 * clipped to a 12dp rounded shape, filled with [ElegantInputGroupColors.containerColor], outlined
 * with a 1dp [ElegantInputGroupColors.borderColor] border, and padded by 4dp. [ElegantInputGroup]
 * declares no semantics of its own and never takes focus: every child keeps its own behavior,
 * focus, interaction state, and semantics, which makes the group a pure layout container.
 *
 * [ElegantInputGroupColors.contentColor] is provided to [content] through `LocalContentColor` so
 * plain text and icons inherit the theme. Children are typically `ElegantInput` instances in the
 * `Outlined` style, which share the cluster border instead of drawing their own.
 *
 * @param modifier modifier applied once to the group root.
 * @param colors theme-aware container, border, and content colors.
 * @param content fields and inline actions rendered inside the cluster.
 */
@Composable
public fun ElegantInputGroup(
    modifier: Modifier = Modifier,
    colors: ElegantInputGroupColors = ElegantInputGroupDefaults.colors(),
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .clip(InputGroupShape)
            .background(colors.containerColor)
            .border(
                width = InputGroupBorderWidth,
                color = colors.borderColor,
                shape = InputGroupShape,
            )
            .padding(ElegantSpacing.xs),
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
            content()
        }
    }
}

/** Resolves theme-aware cluster colors for [ElegantInputGroup]. */
internal fun resolveInputGroupColors(themeColors: ElegantColors): ElegantInputGroupColors =
    ElegantInputGroupColors(
        containerColor = themeColors.surfaceRaised,
        borderColor = themeColors.borderDefault,
        contentColor = themeColors.textPrimary,
    )

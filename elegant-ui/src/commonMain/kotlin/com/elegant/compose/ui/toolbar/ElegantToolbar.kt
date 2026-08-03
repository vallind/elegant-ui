package com.elegant.compose.ui.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * Theme-aware colors used by [ElegantToolbar].
 *
 * Use [ElegantToolbarDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor flat inline strip background color.
 * @property contentColor default content color, provided to [ElegantToolbar] content through
 * [LocalContentColor].
 * @property dividerColor suggested color for caller-drawn separators between actions.
 */
@Immutable
public data class ElegantToolbarColors(
    val containerColor: Color,
    val contentColor: Color,
    val dividerColor: Color,
)

/** Theme-aware defaults for [ElegantToolbar]. */
public object ElegantToolbarDefaults {
    /** Fixed strip height, matching the 48dp interactive-action standard. */
    public val Height: Dp = 48.dp

    /** Horizontal inset between the strip edge and the first and last actions. */
    public val HorizontalPadding: Dp = 4.dp

    /** Recommended spacing between adjacent actions. */
    public val ItemGap: Dp = 4.dp

    /** Returns theme-aware colors for [ElegantToolbar]. */
    @Composable
    public fun colors(): ElegantToolbarColors =
        resolveToolbarColors(ElegantTheme.colors)
}

/**
 * Flat inline strip hosting a row of actions in the caller's layout.
 *
 * A flush, borderless surface that fills the width of its container at a fixed 48dp height: the
 * row keeps its padding and the caller's modifier, so the strip sits flush inside any surface,
 * card, or editor the caller already draws. Unlike [com.elegant.compose.ui.floatingtoolbar
 * .ElegantFloatingToolbar] — a raised floating pill with rounded ends and a cast shadow that wraps
 * its content — [ElegantToolbar] adds no elevation, no rounding, and no positioning of its own:
 * borders and separators around the strip are the caller's choice.
 *
 * [ElegantToolbar] is non-interactive and contributes no semantics; actions inside define their
 * own roles and states. [colors]'s content color is provided to [content] through
 * [LocalContentColor], and [ElegantToolbarColors.dividerColor] is available for caller-drawn
 * separators.
 *
 * @param modifier modifier applied once to the strip root.
 * @param colors theme-aware strip and content colors.
 * @param content row of actions; spacing between actions is the caller's responsibility.
 */
@Composable
public fun ElegantToolbar(
    modifier: Modifier = Modifier,
    colors: ElegantToolbarColors = ElegantToolbarDefaults.colors(),
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(ElegantToolbarDefaults.Height)
            .background(colors.containerColor)
            .padding(horizontal = ElegantToolbarDefaults.HorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
            content()
        }
    }
}

/** Resolves theme roles into [ElegantToolbarColors]. */
internal fun resolveToolbarColors(themeColors: ElegantColors): ElegantToolbarColors =
    ElegantToolbarColors(
        containerColor = themeColors.backgroundSubtle,
        contentColor = themeColors.textPrimary,
        dividerColor = themeColors.borderDefault,
    )

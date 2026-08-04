package com.elegant.compose.ui.smalltitle

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware colors used by [ElegantSmallTitle].
 *
 * Use [ElegantSmallTitleDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property contentColor label text color.
 */
@Immutable
public data class ElegantSmallTitleColors(
    val contentColor: Color,
)

/** Theme-aware defaults for [ElegantSmallTitle]. */
public object ElegantSmallTitleDefaults {
    /** Returns theme-aware colors for the default look. */
    @Composable
    public fun colors(): ElegantSmallTitleColors = resolveSmallTitleColors(
        themeColors = ElegantTheme.colors,
    )
}

/**
 * Renders a compact non-interactive section heading.
 *
 * The title draws [text] in `labelSmall` typography on a single line using the secondary text
 * color, so it reads as a quiet label above preference groups and settings sections while the
 * surrounding content keeps visual emphasis. Longer text is truncated with an ellipsis. The title
 * adds no semantics node of its own, preserving the semantics of [text] for assistive technology.
 *
 * @param text heading text rendered in `labelSmall` typography.
 * @param modifier modifier applied once to the title.
 * @param colors theme-aware label colors.
 */
@Composable
public fun ElegantSmallTitle(
    text: String,
    modifier: Modifier = Modifier,
    colors: ElegantSmallTitleColors = ElegantSmallTitleDefaults.colors(),
) {
    Text(
        text = text,
        modifier = modifier,
        color = colors.contentColor,
        style = ElegantTheme.typography.labelSmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

/**
 * Resolves the theme-aware default small-title colors.
 *
 * The label uses the secondary text role so section headings stay quieter than primary content.
 */
internal fun resolveSmallTitleColors(themeColors: ElegantColors): ElegantSmallTitleColors =
    ElegantSmallTitleColors(
        contentColor = themeColors.textSecondary,
    )

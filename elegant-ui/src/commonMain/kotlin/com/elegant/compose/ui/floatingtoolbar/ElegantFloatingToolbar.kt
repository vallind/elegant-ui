package com.elegant.compose.ui.floatingtoolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantElevation
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * Theme-aware colors used by [ElegantFloatingToolbar].
 *
 * Use [ElegantFloatingToolbarDefaults.colors] for theme-aware defaults, then use [copy] for
 * supported product-level customization.
 *
 * @property containerColor raised pill background color.
 * @property contentColor default content color, provided to [ElegantFloatingToolbar] content
 * through [LocalContentColor].
 * @property dividerColor suggested color for caller-drawn separators between actions.
 */
@Immutable
public data class ElegantFloatingToolbarColors(
    val containerColor: Color,
    val contentColor: Color,
    val dividerColor: Color,
)

/** Theme-aware defaults for [ElegantFloatingToolbar]. */
public object ElegantFloatingToolbarDefaults {
    /** Minimum pill height, matching the 48dp interactive-action standard. */
    public val Height: Dp = 48.dp

    /** Horizontal inset between the pill edge and the first and last actions. */
    public val HorizontalPadding: Dp = 4.dp

    /** Recommended spacing between adjacent actions. */
    public val ItemGap: Dp = 4.dp

    /** Returns theme-aware colors for [ElegantFloatingToolbar]. */
    @Composable
    public fun colors(): ElegantFloatingToolbarColors =
        resolveFloatingToolbarColors(ElegantTheme.colors)
}

/**
 * Floating pill surface hosting a row of actions above content.
 *
 * A raised container with fully rounded ends, medium tonal elevation, and a 48dp minimum height
 * that wraps its content: the row measures intrinsic width and grows in height when content
 * exceeds the minimum. The component provides no positioning of its own; callers wrap it in a
 * [androidx.compose.foundation.layout.Box] or any positioned container to float it over a
 * selection, a paragraph, or an editor.
 *
 * [ElegantFloatingToolbar] is non-interactive and contributes no semantics; actions inside define
 * their own roles and states. [colors]'s content color is provided to [content] through
 * [LocalContentColor], and [ElegantFloatingToolbarColors.dividerColor] is available for
 * caller-drawn separators.
 *
 * @param modifier modifier applied once to the pill root.
 * @param colors theme-aware pill and content colors.
 * @param content row of actions; spacing between actions is the caller's responsibility.
 */
@Composable
public fun ElegantFloatingToolbar(
    modifier: Modifier = Modifier,
    colors: ElegantFloatingToolbarColors = ElegantFloatingToolbarDefaults.colors(),
    content: @Composable RowScope.() -> Unit,
) {
    val shape = RoundedCornerShape(ElegantRadius.full)

    Row(
        modifier = modifier
            .shadow(
                elevation = ElegantElevation.medium,
                shape = shape,
                clip = false,
            )
            .clip(shape)
            .background(colors.containerColor)
            .heightIn(min = ElegantFloatingToolbarDefaults.Height)
            .padding(horizontal = ElegantFloatingToolbarDefaults.HorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
            content()
        }
    }
}

/** Resolves theme roles into [ElegantFloatingToolbarColors]. */
internal fun resolveFloatingToolbarColors(themeColors: ElegantColors): ElegantFloatingToolbarColors =
    ElegantFloatingToolbarColors(
        containerColor = themeColors.surfaceRaised,
        contentColor = themeColors.textPrimary,
        dividerColor = themeColors.borderDefault,
    )

package com.elegant.compose.ui.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware colors used by [ElegantNavbar].
 *
 * Use [ElegantNavbarDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor bar background color.
 * @property contentColor title color, provided to [ElegantNavbar] title content through
 * [LocalContentColor].
 * @property borderColor bottom separator line color.
 */
@Immutable
public data class ElegantNavbarColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color,
)

/** Theme-aware defaults for [ElegantNavbar]. */
public object ElegantNavbarDefaults {
    /** Standard top-bar height. */
    public val Height: Dp = 56.dp

    /** Horizontal breathing room at both bar edges. */
    public val HorizontalPadding: Dp = ElegantSpacing.xl

    /** Gap between the navigation icon and the title. */
    public val ItemGap: Dp = ElegantSpacing.xs

    /** Returns theme-aware bar colors. */
    @Composable
    public fun colors(): ElegantNavbarColors = resolveNavbarColors(
        themeColors = ElegantTheme.colors,
    )
}

/**
 * Places a top app-bar row with an optional navigation icon, a title, and trailing actions.
 *
 * The bar is a non-interactive container: it defines no role, owns no focus, and has no hover,
 * press, or disabled visuals. [title] and [actions] keep their own semantics, and interactive
 * children such as icon buttons keep their own 48dp touch target and interaction feedback.
 *
 * The title receives [colors]'s content color through [LocalContentColor]. Its text style is
 * caller-owned, so supply the desired style explicitly (for example
 * `ElegantTheme.typography.titleMedium`); the bar never applies a text style itself.
 *
 * The bar renders at [ElegantNavbarDefaults.Height] with
 * [ElegantNavbarDefaults.HorizontalPadding] on both edges and a 1dp bottom border in [colors]'s
 * border color. The navigation icon is measured at its natural size (an icon button keeps its
 * 48dp minimum touch target) and is separated from the title by
 * [ElegantNavbarDefaults.ItemGap].
 *
 * @param navigationIcon optional leading content placed before the title.
 * @param modifier modifier applied once to the bar root.
 * @param title title content; the text style is caller-owned.
 * @param actions trailing action content laid out in row scope.
 * @param colors theme-aware container, content, and border colors.
 */
@Composable
public fun ElegantNavbar(
    navigationIcon: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    colors: ElegantNavbarColors = ElegantNavbarDefaults.colors(),
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(ElegantNavbarDefaults.Height)
            .background(colors.containerColor)
            .navbarBottomBorder(colors.borderColor)
            .padding(horizontal = ElegantNavbarDefaults.HorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (navigationIcon != null) {
            Box {
                navigationIcon()
            }
            Spacer(modifier = Modifier.width(ElegantNavbarDefaults.ItemGap))
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart,
        ) {
            CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
                title()
            }
        }

        actions()
    }
}

private fun Modifier.navbarBottomBorder(color: Color): Modifier = drawBehind {
    val strokeWidth = 1.dp.toPx()
    drawLine(
        color = color,
        start = Offset(0f, size.height - strokeWidth / 2f),
        end = Offset(size.width, size.height - strokeWidth / 2f),
        strokeWidth = strokeWidth,
    )
}

internal fun resolveNavbarColors(
    themeColors: ElegantColors,
): ElegantNavbarColors = ElegantNavbarColors(
    containerColor = themeColors.surfaceDefault,
    contentColor = themeColors.textPrimary,
    borderColor = themeColors.borderDefault,
)

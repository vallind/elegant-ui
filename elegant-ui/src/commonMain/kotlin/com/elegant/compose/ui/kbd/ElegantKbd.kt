package com.elegant.compose.ui.kbd

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantTheme

internal val KbdMinHeight: Dp = 24.dp

internal val KbdHorizontalPadding: Dp = 6.dp

internal val KbdBorderWidth: Dp = 1.dp

/**
 * Theme-aware colors used by Elegant UI keyboard-key badges.
 *
 * @property containerColor recessed badge background.
 * @property contentColor key label color.
 * @property borderColor optical outline color.
 */
@Immutable
public data class ElegantKbdColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color,
)

/** Defaults and theme-aware factories shared by the Elegant UI keyboard-key badge API. */
public object ElegantKbdDefaults {
    /** Default minimum height of the keyboard-key badge. */
    public val MinHeight: Dp = KbdMinHeight

    /** Default horizontal padding inside the keyboard-key badge. */
    public val HorizontalPadding: Dp = KbdHorizontalPadding

    /** Default optical outline width of the keyboard-key badge. */
    public val BorderWidth: Dp = KbdBorderWidth

    /** Returns theme-aware keyboard-key badge colors. */
    @Composable
    public fun colors(): ElegantKbdColors = resolveKbdColors(ElegantTheme.colors)
}

/**
 * Displays a compact non-interactive keyboard-key badge.
 *
 * The badge renders a recessed container with a subtle rounded outline and centers [text] inside.
 * It carries no interaction role and no semantics node of its own, so the key label text remains
 * readable to assistive technology without adding noise to the surrounding shortcuts.
 *
 * @param text key or chord label displayed inside the badge.
 * @param modifier modifier applied once to the badge container.
 * @param colors theme-aware container, content, and border colors.
 */
@Composable
public fun ElegantKbd(
    text: String,
    modifier: Modifier = Modifier,
    colors: ElegantKbdColors = ElegantKbdDefaults.colors(),
) {
    val shape = RoundedCornerShape(ElegantRadius.xs)
    Box(
        modifier = modifier
            .defaultMinSize(minHeight = ElegantKbdDefaults.MinHeight)
            .clip(shape)
            .background(colors.containerColor)
            .border(
                width = ElegantKbdDefaults.BorderWidth,
                color = colors.borderColor,
                shape = shape,
            )
            .padding(horizontal = ElegantKbdDefaults.HorizontalPadding),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = colors.contentColor,
            style = ElegantTheme.typography.labelSmall,
        )
    }
}

internal fun resolveKbdColors(themeColors: ElegantColors): ElegantKbdColors = ElegantKbdColors(
    containerColor = themeColors.surfaceSunken,
    contentColor = themeColors.textPrimary,
    borderColor = themeColors.borderDefault,
)

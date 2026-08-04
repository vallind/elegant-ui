package com.elegant.compose.ui.emptystate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware colors used by [ElegantEmptyState].
 *
 * Use [ElegantEmptyStateDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property iconContainerColor background of the circular icon container.
 * @property iconContentColor icon content color, provided through [LocalContentColor].
 * @property titleColor title text color.
 * @property descriptionColor description text color.
 */
@Immutable
public data class ElegantEmptyStateColors(
    val iconContainerColor: Color,
    val iconContentColor: Color,
    val titleColor: Color,
    val descriptionColor: Color,
)

/** Defaults and factories for [ElegantEmptyState]. */
public object ElegantEmptyStateDefaults {
    /** Diameter of the circular icon container. */
    public val IconContainerSize: Dp = 64.dp

    /** Vertical gap between the icon, title, and description blocks. */
    public val ItemGap: Dp = 8.dp

    /** Vertical gap between the description block and the action slot. */
    public val ActionGap: Dp = 16.dp

    /** Default padding around the whole empty-state layout. */
    public val DefaultPadding: Dp = 24.dp

    /** Returns theme-aware empty-state colors. */
    @Composable
    public fun colors(): ElegantEmptyStateColors = resolveEmptyStateColors(ElegantTheme.colors)
}

/**
 * Displays an absent-content message with optional icon and action.
 *
 * `ElegantEmptyState` is a non-interactive display component: it stacks [icon], [title],
 * [description], and [action] in a centered column, adds no semantics of its own, and supports no
 * hover, press, focus, or disabled state. The [title] is required; a blank [description] is
 * omitted entirely.
 *
 * When [icon] is present it is drawn inside a 64dp circle tinted with
 * [ElegantEmptyStateColors.iconContainerColor], and its content receives
 * [ElegantEmptyStateColors.iconContentColor] through [LocalContentColor]. The icon slot owns its
 * own semantics: keep the icon's `contentDescription` null for a purely decorative icon, or supply
 * a localized description when the icon carries meaning.
 *
 * @param icon optional visual content inside the circular icon container.
 * @param modifier modifier applied once to the empty-state root.
 * @param title primary message shown above the description.
 * @param description supporting message shown under the title; blank values are omitted.
 * @param action optional action slot below the description, separated by a wider gap.
 * @param colors theme-aware icon, title, and description colors.
 * @param contentPadding padding around the whole layout.
 */
@Composable
public fun ElegantEmptyState(
    icon: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    action: (@Composable () -> Unit)? = null,
    colors: ElegantEmptyStateColors = ElegantEmptyStateDefaults.colors(),
    contentPadding: PaddingValues = PaddingValues(ElegantEmptyStateDefaults.DefaultPadding),
) {
    val resolvedDescription = resolveDescription(description)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ElegantEmptyStateDefaults.ActionGap),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ElegantEmptyStateDefaults.ItemGap),
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier
                        .size(ElegantEmptyStateDefaults.IconContainerSize)
                        .clip(CircleShape)
                        .background(colors.iconContainerColor),
                    contentAlignment = Alignment.Center,
                ) {
                    CompositionLocalProvider(LocalContentColor provides colors.iconContentColor) {
                        icon()
                    }
                }
            }

            Text(
                text = title,
                color = colors.titleColor,
                style = ElegantTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )

            if (resolvedDescription != null) {
                Text(
                    text = resolvedDescription,
                    color = colors.descriptionColor,
                    style = ElegantTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }

        if (action != null) {
            action()
        }
    }
}

internal fun resolveDescription(description: String?): String? =
    if (description.isNullOrBlank()) null else description

internal fun resolveEmptyStateColors(themeColors: ElegantColors): ElegantEmptyStateColors =
    ElegantEmptyStateColors(
        iconContainerColor = themeColors.interactivePrimary.copy(alpha = 0.10f),
        iconContentColor = themeColors.interactivePrimary,
        titleColor = themeColors.textPrimary,
        descriptionColor = themeColors.textSecondary,
    )

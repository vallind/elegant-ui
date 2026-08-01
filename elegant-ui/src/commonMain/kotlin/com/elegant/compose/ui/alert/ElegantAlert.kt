package com.elegant.compose.ui.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantTheme

/** Semantic visual styles supported by [ElegantAlert]. */
public enum class ElegantAlertStyle {
    /** General information that needs neither emphasis nor severity. */
    Neutral,

    /** Successful, available, or healthy outcome. */
    Positive,

    /** Situation that requires awareness but is not yet critical. */
    Warning,

    /** Urgent, failed, destructive, or otherwise critical situation. */
    Critical,
}

/**
 * Theme-aware colors used by [ElegantAlert].
 *
 * Use [ElegantAlertDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor alert background.
 * @property contentColor title text color.
 * @property supportingColor description text color.
 * @property borderColor optical outline color.
 * @property iconColor icon color provided through [LocalContentColor].
 */
@Immutable
public data class ElegantAlertColors(
    val containerColor: Color,
    val contentColor: Color,
    val supportingColor: Color,
    val borderColor: Color,
    val iconColor: Color,
)

/** Defaults and theme-aware factories shared by Elegant UI alert APIs. */
public object ElegantAlertDefaults {
    /**
     * Returns theme-aware colors for [style].
     *
     * The default style is [ElegantAlertStyle.Neutral] because alerts default to informative,
     * unopinionated presentation; severity is opt-in through [ElegantAlertStyle.Positive],
     * [ElegantAlertStyle.Warning], or [ElegantAlertStyle.Critical].
     */
    @Composable
    public fun colors(
        style: ElegantAlertStyle = ElegantAlertStyle.Neutral,
    ): ElegantAlertColors = resolveAlertColors(
        style = style,
        themeColors = ElegantTheme.colors,
    )
}

@Immutable
internal data class AlertMetrics(
    val horizontalPadding: Dp,
    val verticalPadding: Dp,
    val borderWidth: Dp,
    val iconSize: Dp,
    val iconGap: Dp,
    val actionGap: Dp,
)

internal val DefaultAlertMetrics = AlertMetrics(
    horizontalPadding = 16.dp,
    verticalPadding = 12.dp,
    borderWidth = 1.dp,
    iconSize = 20.dp,
    iconGap = 12.dp,
    actionGap = 16.dp,
)

/**
 * Displays a non-interactive status banner with optional icon and action.
 *
 * `ElegantAlert` communicates state without stealing interaction: the banner itself has no role,
 * focus, or press handling, so [title] and [description] keep plain-text semantics and the
 * [action] slot stays fully caller-owned. A blank [description] is omitted entirely. The [icon]
 * slot receives [ElegantAlertColors.iconColor] through [LocalContentColor]; keep its
 * `contentDescription` null for a purely decorative icon, or supply a localized description when
 * the icon carries meaning.
 *
 * @param style semantic visual style.
 * @param modifier modifier applied once to the banner root.
 * @param title primary message shown in the banner.
 * @param description supporting message shown under the title; blank values are omitted.
 * @param icon optional content before the title, tinted with the icon color.
 * @param action optional content after the text column, such as a button or text link.
 * @param colors theme-aware container, text, border, and icon colors.
 */
@Composable
public fun ElegantAlert(
    style: ElegantAlertStyle = ElegantAlertStyle.Neutral,
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    icon: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
    colors: ElegantAlertColors = ElegantAlertDefaults.colors(style),
) {
    val resolvedDescription = resolveDescription(description)
    val shape = RoundedCornerShape(ElegantRadius.md)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(colors.containerColor)
            .border(
                width = DefaultAlertMetrics.borderWidth,
                color = colors.borderColor,
                shape = shape,
            )
            .padding(
                horizontal = DefaultAlertMetrics.horizontalPadding,
                vertical = DefaultAlertMetrics.verticalPadding,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            CompositionLocalProvider(LocalContentColor provides colors.iconColor) {
                Box(
                    modifier = Modifier.size(DefaultAlertMetrics.iconSize),
                    contentAlignment = Alignment.Center,
                ) {
                    icon()
                }
            }
            Spacer(Modifier.width(DefaultAlertMetrics.iconGap))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = colors.contentColor,
                style = ElegantTheme.typography.labelMedium,
            )
            if (resolvedDescription != null) {
                Text(
                    text = resolvedDescription,
                    color = colors.supportingColor,
                    style = ElegantTheme.typography.bodyMedium,
                )
            }
        }

        if (action != null) {
            Spacer(Modifier.width(DefaultAlertMetrics.actionGap))
            action()
        }
    }
}

internal fun resolveAlertColors(
    style: ElegantAlertStyle,
    themeColors: ElegantColors,
): ElegantAlertColors = when (style) {
    ElegantAlertStyle.Neutral -> ElegantAlertColors(
        containerColor = themeColors.backgroundSubtle,
        contentColor = themeColors.textPrimary,
        supportingColor = themeColors.textSecondary,
        borderColor = themeColors.borderDefault,
        iconColor = themeColors.textSecondary,
    )

    ElegantAlertStyle.Positive -> ElegantAlertColors(
        containerColor = themeColors.statusPositive.copy(alpha = 0.10f),
        contentColor = themeColors.textPrimary,
        supportingColor = themeColors.textSecondary,
        borderColor = themeColors.statusPositive.copy(alpha = 0.30f),
        iconColor = themeColors.statusPositive,
    )

    ElegantAlertStyle.Warning -> ElegantAlertColors(
        containerColor = themeColors.statusWarning.copy(alpha = 0.10f),
        contentColor = themeColors.textPrimary,
        supportingColor = themeColors.textSecondary,
        borderColor = themeColors.statusWarning.copy(alpha = 0.30f),
        iconColor = themeColors.statusWarning,
    )

    ElegantAlertStyle.Critical -> ElegantAlertColors(
        containerColor = themeColors.statusCritical.copy(alpha = 0.10f),
        contentColor = themeColors.textPrimary,
        supportingColor = themeColors.textSecondary,
        borderColor = themeColors.statusCritical.copy(alpha = 0.30f),
        iconColor = themeColors.statusCritical,
    )
}

internal fun resolveDescription(description: String?): String? =
    if (description.isNullOrBlank()) null else description

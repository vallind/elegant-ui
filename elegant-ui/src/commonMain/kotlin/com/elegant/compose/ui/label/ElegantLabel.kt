package com.elegant.compose.ui.label

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/** Default required-field suffix appended to the combined label string of a required label. */
internal val LabelRequiredSuffix: String = "*"

/**
 * Theme-aware colors used by an [ElegantLabel].
 *
 * @property contentColor label text color.
 * @property requiredColor color of the required-field suffix.
 * @property disabledContentColor label text color while the label is disabled; falls back to
 *   [contentColor] when not set explicitly.
 */
@Immutable
public data class ElegantLabelColors(
    val contentColor: Color,
    val requiredColor: Color,
    val disabledContentColor: Color = contentColor,
)

/** Defaults and theme-aware factories shared by the Elegant UI label API. */
public object ElegantLabelDefaults {
    /** Default required-field suffix rendered after the label text. */
    public val RequiredSuffix: String = LabelRequiredSuffix

    /** Returns theme-aware colors for an [ElegantLabel]. */
    @Composable
    public fun colors(): ElegantLabelColors = resolveLabelColors(ElegantTheme.colors)
}

/**
 * Renders a non-interactive form-field label with an optional required marker.
 *
 * The label renders [text] as a single-line `labelMedium` text with ellipsis and animates the
 * content color when [enabled] changes. When [required] is true, a separate `"*"` suffix in the
 * required color is placed right after the text with a 2dp gap, so the marker stays visible even
 * when the label text is truncated. The label keeps the content semantics of its text and adds no
 * interaction role or semantics node of its own.
 *
 * @param text label text shown next to the field.
 * @param modifier modifier applied once to the label root.
 * @param required whether the required-field suffix is rendered after [text].
 * @param enabled whether the label renders in its enabled content color; `false` renders it in
 *   the disabled content color.
 * @param colors theme-aware content and required-marker colors.
 */
@Composable
public fun ElegantLabel(
    text: String,
    modifier: Modifier = Modifier,
    required: Boolean = false,
    enabled: Boolean = true,
    colors: ElegantLabelColors = ElegantLabelDefaults.colors(),
) {
    val animatedColor by animateColorAsState(
        targetValue = resolveLabelColor(colors = colors, enabled = enabled),
        animationSpec = tween(
            durationMillis = ElegantMotion.standardDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantLabelContent",
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = ElegantTheme.typography.labelMedium,
            color = animatedColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        if (required) {
            Text(
                text = ElegantLabelDefaults.RequiredSuffix,
                modifier = Modifier.padding(start = ElegantSpacing.xxs),
                style = ElegantTheme.typography.labelMedium,
                color = colors.requiredColor,
            )
        }
    }
}

/** Resolves theme-aware label colors from [themeColors]. */
internal fun resolveLabelColors(themeColors: ElegantColors): ElegantLabelColors = ElegantLabelColors(
    contentColor = themeColors.textSecondary,
    requiredColor = themeColors.statusCritical,
    disabledContentColor = themeColors.textTertiary,
)

/** Resolves the effective label text color for [enabled]. */
internal fun resolveLabelColor(colors: ElegantLabelColors, enabled: Boolean): Color =
    if (enabled) colors.contentColor else colors.disabledContentColor

/**
 * Returns the combined label string for [text] and [required].
 *
 * Blank [text] is returned unchanged, so a required marker never decorates an empty label. A
 * required non-blank label becomes `"<text> *"`.
 */
internal fun labelText(text: String, required: Boolean): String = when {
    required && text.isNotBlank() -> "$text *"
    else -> text
}

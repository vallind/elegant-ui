package com.elegant.compose.ui.fieldset

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * Theme-aware colors used by [ElegantFieldset].
 *
 * Use [ElegantFieldsetDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor container color of the bordered section.
 * @property borderColor 1dp border color of the section outline.
 * @property legendColor legend text color.
 * @property contentColor content color, provided to [ElegantFieldset] content through
 *   [LocalContentColor].
 */
@Immutable
public data class ElegantFieldsetColors(
    val containerColor: Color,
    val borderColor: Color,
    val legendColor: Color,
    val contentColor: Color,
)

/** Theme-aware defaults for [ElegantFieldset]. */
public object ElegantFieldsetDefaults {
    /** Gap between the legend and the content. */
    public val LegendGap: Dp = 8.dp

    /** Padding applied inside the bordered section. */
    public val ContentPadding: Dp = 16.dp

    /** Returns theme-aware colors. */
    @Composable
    public fun colors(): ElegantFieldsetColors = resolveFieldsetColors(ElegantTheme.colors)
}

/**
 * Groups form fields in a bordered section with an optional legend.
 *
 * The fieldset renders a raised, rounded container with a 1dp border and [ElegantFieldsetDefaults.ContentPadding]
 * of internal padding. [legend] is trimmed before rendering; when it is null or blank the legend row
 * is omitted entirely and the content starts at the top of the section. Content receives [colors]'s
 * content color through [LocalContentColor].
 *
 * The fieldset is non-interactive: it adds no role, focus, or click handling, and the semantics of
 * [content] pass through unchanged. The caller owns the section width through [modifier] and the
 * spacing between fields inside [content].
 *
 * @param modifier modifier applied once to the fieldset root.
 * @param legend optional legend shown above the content; null or blank legends are omitted.
 * @param colors theme-aware colors.
 * @param content fields or content grouped inside the bordered section.
 */
@Composable
public fun ElegantFieldset(
    modifier: Modifier = Modifier,
    legend: String? = null,
    colors: ElegantFieldsetColors = ElegantFieldsetDefaults.colors(),
    content: @Composable () -> Unit,
) {
    val resolvedLegend = resolveLegend(legend)
    val shape = RoundedCornerShape(ElegantRadius.md)

    Column(
        modifier = modifier
            .clip(shape)
            .border(width = 1.dp, color = colors.borderColor, shape = shape)
            .background(colors.containerColor)
            .padding(ElegantFieldsetDefaults.ContentPadding),
    ) {
        if (resolvedLegend != null) {
            Text(
                text = resolvedLegend,
                style = ElegantTheme.typography.labelMedium,
                color = colors.legendColor,
            )
            Spacer(Modifier.height(ElegantFieldsetDefaults.LegendGap))
        }
        CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
            content()
        }
    }
}

internal fun resolveFieldsetColors(themeColors: ElegantColors): ElegantFieldsetColors =
    ElegantFieldsetColors(
        containerColor = themeColors.surfaceRaised,
        borderColor = themeColors.borderDefault,
        legendColor = themeColors.textSecondary,
        contentColor = themeColors.textPrimary,
    )

internal fun resolveLegend(legend: String?): String? =
    legend?.trim()?.takeIf { it.isNotBlank() }

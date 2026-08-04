package com.elegant.compose.ui.spinner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.progress.ElegantCircularProgressIndicator
import com.elegant.compose.ui.progress.ElegantProgressColors
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware colors used by Elegant UI spinners.
 *
 * @property indicatorColor rotating arc color of the ring.
 * @property trackColor static ring color behind the indicator.
 * @property labelColor loading label text color.
 */
@Immutable
public data class ElegantSpinnerColors(
    val indicatorColor: Color,
    val trackColor: Color,
    val labelColor: Color,
)

/** Defaults and the theme-aware factory shared by the Elegant UI spinner. */
public object ElegantSpinnerDefaults {
    /** 40dp diameter of the spinner ring. */
    public val Size: Dp = DefaultSpinnerSize

    /** 4dp stroke thickness of the spinner ring. */
    public val StrokeWidth: Dp = DefaultSpinnerStrokeWidth

    /** Standard 160ms state-transition duration. */
    public val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware spinner colors for the active Light or Dark theme. */
    @Composable
    public fun colors(): ElegantSpinnerColors = resolveSpinnerColors(ElegantTheme.colors)
}

/**
 * A centered indeterminate loading indicator with an optional label.
 *
 * Renders a rotating circular ring above an optional [label], centered in a column with a 12dp gap.
 * The ring is always in its indeterminate state; [size] and [strokeWidth] fall back to their
 * defaults when non-positive or non-finite. The component is non-interactive: the root exposes
 * `progressBarRangeInfo` semantics for an indeterminate progress bar, the track is decorative, and
 * the label remains readable as regular text.
 *
 * @param modifier modifier applied once to the spinner root.
 * @param size diameter of the spinner ring; non-positive or non-finite values fall back to the default.
 * @param strokeWidth stroke thickness of the spinner ring; non-positive or non-finite values fall back to the default.
 * @param label optional loading label shown below the ring; null hides it.
 * @param colors theme-aware indicator, track, and label colors.
 */
@Composable
public fun ElegantSpinner(
    modifier: Modifier = Modifier,
    size: Dp = ElegantSpinnerDefaults.Size,
    strokeWidth: Dp = ElegantSpinnerDefaults.StrokeWidth,
    label: String? = null,
    colors: ElegantSpinnerColors = ElegantSpinnerDefaults.colors(),
) {
    Column(
        modifier = modifier.semantics {
            progressBarRangeInfo = ProgressBarRangeInfo(0f, 0f..1f, 0)
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
    ) {
        ElegantCircularProgressIndicator(
            progress = null,
            size = resolveSize(size),
            strokeWidth = resolveStrokeWidth(strokeWidth),
            colors = ElegantProgressColors(
                indicatorColor = colors.indicatorColor,
                trackColor = colors.trackColor,
            ),
        )
        if (label != null) {
            Text(
                text = label,
                style = ElegantTheme.typography.labelMedium,
                color = colors.labelColor,
            )
        }
    }
}

/** Default 40dp diameter of the spinner ring. */
internal val DefaultSpinnerSize: Dp = 40.dp

/** Default 4dp stroke thickness of the spinner ring. */
internal val DefaultSpinnerStrokeWidth: Dp = 4.dp

internal fun resolveSpinnerColors(themeColors: ElegantColors): ElegantSpinnerColors =
    ElegantSpinnerColors(
        indicatorColor = themeColors.interactivePrimary,
        trackColor = themeColors.borderDefault,
        labelColor = themeColors.textSecondary,
    )

internal fun resolveSize(size: Dp): Dp =
    if (size.value.isFinite() && size > 0.dp) size else DefaultSpinnerSize

internal fun resolveStrokeWidth(strokeWidth: Dp): Dp =
    if (strokeWidth.value.isFinite() && strokeWidth > 0.dp) strokeWidth else DefaultSpinnerStrokeWidth

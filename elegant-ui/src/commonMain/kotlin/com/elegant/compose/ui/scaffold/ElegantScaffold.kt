package com.elegant.compose.ui.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * Theme-aware colors used by [ElegantScaffold].
 *
 * Use [ElegantScaffoldDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property backgroundColor canvas painted behind every scaffold layer.
 * @property contentColor primary content color, provided to the content slot through
 * [LocalContentColor].
 */
@Immutable
public data class ElegantScaffoldColors(
    val backgroundColor: Color,
    val contentColor: Color,
)

/** Theme-aware defaults for [ElegantScaffold]. */
public object ElegantScaffoldDefaults {
    /** Gap between the floating action button and the screen edge and the bottom bar. */
    public val FloatingActionButtonMargin: Dp = 16.dp

    /** Gap between the snackbar host and the bottom bar. */
    public val SnackbarHostMargin: Dp = 8.dp

    /** Returns theme-aware scaffold colors. */
    @Composable
    public fun colors(): ElegantScaffoldColors = resolveScaffoldColors(
        themeColors = ElegantTheme.colors,
    )
}

/**
 * Lays out a screen shell with pinned bars, a floating action button, and a snackbar host.
 *
 * The scaffold fills its constraints and paints [ElegantScaffoldColors.backgroundColor] behind
 * every layer. The optional [topBar] is pinned to the top edge and the optional [bottomBar] to the
 * bottom edge; both are measured with `onSizeChanged`, so their height changes propagate to the
 * content insets automatically. The content slot fills the remaining area underneath the bars and
 * receives a [PaddingValues] whose top equals the measured top bar height and whose bottom equals
 * the measured bottom bar height, letting the caller inset scrollable content exactly.
 *
 * The optional [floatingActionButton] floats above the bottom bar, anchored to the bottom end with
 * [ElegantScaffoldDefaults.FloatingActionButtonMargin] on both edges. The optional [snackbarHost]
 * floats above the bottom bar, centered horizontally with [ElegantScaffoldDefaults.SnackbarHostMargin]
 * of clearance; it draws below the floating action button, so a snackbar host stays reachable
 * when both are present.
 *
 * The scaffold defines no role, owns no focus, and never merges or clears the semantics of its
 * content; interactive children keep their own accessibility contract. The content slot receives
 * [ElegantScaffoldColors.contentColor] through [LocalContentColor]; bar and button colors are
 * owned by the components passed to the slots.
 *
 * @param modifier modifier applied once to the scaffold root.
 * @param topBar optional content pinned to the top edge and measured for the content top inset.
 * @param bottomBar optional content pinned to the bottom edge and measured for the content bottom
 *   inset.
 * @param floatingActionButton optional content floating above the bottom bar at the bottom end.
 * @param snackbarHost optional content floating above the bottom bar, centered horizontally.
 * @param colors theme-aware background and content colors.
 * @param content primary content filling the area underneath the bars; receives the insets
 *   produced by the measured bar heights.
 */
@Composable
public fun ElegantScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    snackbarHost: (@Composable () -> Unit)? = null,
    colors: ElegantScaffoldColors = ElegantScaffoldDefaults.colors(),
    content: @Composable (PaddingValues) -> Unit,
) {
    var topBarHeight by remember { mutableStateOf(0) }
    var bottomBarHeight by remember { mutableStateOf(0) }
    val fabMarginPx = with(LocalDensity.current) {
        ElegantScaffoldDefaults.FloatingActionButtonMargin.roundToPx()
    }
    val snackbarMarginPx = with(LocalDensity.current) {
        ElegantScaffoldDefaults.SnackbarHostMargin.roundToPx()
    }
    val padding = resolveContentPadding(topBarHeight, bottomBarHeight)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.backgroundColor),
    ) {
        if (topBar != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .onSizeChanged { topBarHeight = it.height },
            ) {
                topBar()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
                content(
                    PaddingValues(
                        top = padding.top.toDp(),
                        bottom = padding.bottom.toDp(),
                    ),
                )
            }
        }

        if (bottomBar != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .onSizeChanged { bottomBarHeight = it.height },
            ) {
                bottomBar()
            }
        }

        if (snackbarHost != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = (bottomBarHeight + snackbarMarginPx).toDp()),
            ) {
                snackbarHost()
            }
        }

        if (floatingActionButton != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = ElegantScaffoldDefaults.FloatingActionButtonMargin,
                        bottom = fabPadding(bottomBarHeight, fabMarginPx).toDp(),
                    ),
            ) {
                floatingActionButton()
            }
        }
    }
}

/**
 * Measured insets for the scaffold content area.
 *
 * @property top top inset in pixels, equal to the measured top bar height.
 * @property bottom bottom inset in pixels, equal to the measured bottom bar height.
 */
internal data class ScaffoldPadding(
    val top: Int,
    val bottom: Int,
)

/**
 * Resolves the content insets from the measured bar heights.
 */
internal fun resolveContentPadding(
    topBarHeightPx: Int,
    bottomBarHeightPx: Int,
): ScaffoldPadding = ScaffoldPadding(
    top = topBarHeightPx,
    bottom = bottomBarHeightPx,
)

/**
 * Resolves the floating action button bottom offset in pixels: the bottom bar height plus the
 * caller-supplied margin.
 */
internal fun fabPadding(
    bottomBarHeightPx: Int,
    fabMarginPx: Int,
): Int = bottomBarHeightPx + fabMarginPx

/**
 * Resolves theme-aware colors for [ElegantScaffold].
 */
internal fun resolveScaffoldColors(
    themeColors: ElegantColors,
): ElegantScaffoldColors = ElegantScaffoldColors(
    backgroundColor = themeColors.backgroundCanvas,
    contentColor = themeColors.textPrimary,
)

package com.elegant.compose.ui.drawer

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantElevation
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Logical edge from which the drawer panel slides in.
 *
 * [Start] and [End] are layout-direction-aware: in RTL layouts they mirror to the opposite edges.
 */
public enum class ElegantDrawerPlacement {
    /** Logical start edge; left in LTR layouts, right in RTL layouts. */
    Start,

    /** Logical end edge; right in LTR layouts, left in RTL layouts. */
    End,
}

/**
 * Theme-aware colors used by [ElegantDrawer].
 *
 * Use [ElegantDrawerDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property scrimColor dimming overlay drawn behind the panel; carries the resting scrim strength.
 * @property containerColor panel container color.
 * @property contentColor default panel content color, provided through [LocalContentColor].
 */
@Immutable
public data class ElegantDrawerColors(
    val scrimColor: Color,
    val containerColor: Color,
    val contentColor: Color,
)

/** Theme-aware defaults for [ElegantDrawer]. */
public object ElegantDrawerDefaults {
    /** Default panel width. */
    public val Width: Dp = 280.dp

    /** Resting alpha of the [ElegantDrawerColors.scrimColor] overlay. */
    public const val ScrimAlpha: Float = 0.4f

    /** Standard slide and fade duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.emphasizedDurationMillis

    /** Returns theme-aware scrim, container, and content colors. */
    @Composable
    public fun colors(): ElegantDrawerColors = resolveDrawerColors(ElegantTheme.colors)
}

/**
 * Displays a modal side panel over a dimming scrim.
 *
 * The drawer is a controlled overlay: [visible] decides whether the panel is composed inside a
 * dialog window, and every dismissal path invokes [onDismissRequest]. Clicking the scrim, pressing
 * Escape, or pressing the system back key (Android) dismisses the drawer; the caller closes it
 * programmatically by setting [visible] to false.
 *
 * While visible, the dialog window captures keyboard focus inside the panel and restores it to the
 * caller's window when the drawer closes. Panel content is caller-owned and receives
 * [ElegantDrawerColors.contentColor] through [LocalContentColor].
 *
 * The panel slides in from the logical [placement] edge while the scrim fades in, both using
 * [ElegantDrawerDefaults.AnimationDurationMillis]. Panel content scrolls vertically when it exceeds
 * the available height.
 *
 * @param visible whether the drawer is shown; false removes the dialog window.
 * @param onDismissRequest invoked on scrim click, Escape, back key, or programmatic dismissal.
 * @param modifier modifier applied once to the drawer panel.
 * @param placement logical edge the panel slides in from.
 * @param width panel width.
 * @param colors theme-aware scrim, container, and content colors.
 * @param content drawer panel content.
 */
@Composable
public fun ElegantDrawer(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    placement: ElegantDrawerPlacement = ElegantDrawerPlacement.Start,
    width: Dp = ElegantDrawerDefaults.Width,
    colors: ElegantDrawerColors = ElegantDrawerDefaults.colors(),
    content: @Composable () -> Unit,
) {
    if (visible) {
        DrawerDialog(
            onDismissRequest = onDismissRequest,
            modifier = modifier,
            placement = placement,
            width = width,
            colors = colors,
            content = content,
        )
    }
}

@Composable
private fun DrawerDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    placement: ElegantDrawerPlacement,
    width: Dp,
    colors: ElegantDrawerColors,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val widthPx = with(density) { width.toPx() }
    var entered by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        entered = true
    }
    val animatedOffset by animateDpAsState(
        targetValue = with(density) {
            drawerSlideOffset(
                visible = entered,
                widthPx = widthPx,
                placement = placement,
                layoutDirection = layoutDirection,
            ).toDp()
        },
        animationSpec = tween(
            durationMillis = ElegantDrawerDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantDrawerOffset",
    )
    val scrimAlpha by animateFloatAsState(
        targetValue = if (entered) 1f else 0f,
        animationSpec = tween(
            durationMillis = ElegantDrawerDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantDrawerScrimAlpha",
    )
    val panelAlignment = when (layoutDirection) {
        LayoutDirection.Ltr -> if (placement == ElegantDrawerPlacement.Start) {
            Alignment.CenterStart
        } else {
            Alignment.CenterEnd
        }

        LayoutDirection.Rtl -> if (placement == ElegantDrawerPlacement.Start) {
            Alignment.CenterEnd
        } else {
            Alignment.CenterStart
        }
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.scrimColor)
                .alpha(scrimAlpha),
        ) {
            Box(
                modifier = modifier
                    .width(width)
                    .fillMaxHeight()
                    .align(panelAlignment)
                    .offset(x = animatedOffset)
                    .shadow(elevation = ElegantElevation.medium)
                    .background(colors.containerColor),
            ) {
                CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(ElegantSpacing.xl)
                            .verticalScroll(rememberScrollState()),
                    ) {
                        content()
                    }
                }
            }
        }
    }
}

internal fun resolveDrawerColors(themeColors: ElegantColors): ElegantDrawerColors = ElegantDrawerColors(
    scrimColor = Color.Black.copy(alpha = ElegantDrawerDefaults.ScrimAlpha),
    containerColor = themeColors.surfaceRaised,
    contentColor = themeColors.textPrimary,
)

internal fun drawerSlideOffset(
    visible: Boolean,
    widthPx: Float,
    placement: ElegantDrawerPlacement,
    layoutDirection: LayoutDirection,
): Float {
    if (visible) {
        return 0f
    }
    val slidesFromLogicalStart = when (layoutDirection) {
        LayoutDirection.Ltr -> placement == ElegantDrawerPlacement.Start
        LayoutDirection.Rtl -> placement == ElegantDrawerPlacement.End
    }
    return if (slidesFromLogicalStart) -widthPx else widthPx
}

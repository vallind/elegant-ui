package com.elegant.compose.ui.modal

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantElevation
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware colors used by [ElegantModal].
 *
 * Use [ElegantModalDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property scrimColor dimming overlay drawn across the window behind the modal surface.
 * @property containerColor modal surface container color.
 * @property contentColor content color provided to the modal content through [LocalContentColor].
 */
@Immutable
public data class ElegantModalColors(
    val scrimColor: Color,
    val containerColor: Color,
    val contentColor: Color,
)

/** Theme-aware defaults for [ElegantModal]. */
public object ElegantModalDefaults {
    /** Maximum width of the modal surface before the content wraps. */
    public val MaxWidth: Dp = resolveModalMaxWidth()

    /** Shared rounded corner shape of the modal surface. */
    public val Shape: Shape = RoundedCornerShape(ElegantRadius.lg)

    /** Alpha applied to the standard black scrim overlay color. */
    public const val ScrimAlpha: Float = 0.4f

    /** Duration of the modal entrance transition. */
    public const val AnimationDurationMillis: Int = ElegantMotion.emphasizedDurationMillis

    /** Returns theme-aware colors for the active Light or Dark theme. */
    @Composable
    public fun colors(): ElegantModalColors = resolveModalColors(ElegantTheme.colors)
}

/**
 * Shows a modal surface centered over a dimming scrim inside a platform dialog window.
 *
 * [ElegantModal] is a controlled overlay: the caller owns [visible] and removes the dialog from
 * composition by setting it to false. The dialog renders the full-screen scrim, the centered
 * surface capped at [ElegantModalDefaults.MaxWidth] with 24dp internal padding, a medium tonal
 * shadow, and the [colors]'s content color through [LocalContentColor]; the caller owns the title,
 * description, and action layout inside [content].
 *
 * Dismissal contract:
 * - an outside click on the scrim invokes [onDismissRequest] through `dismissOnClickOutside`;
 * - the system back key on Android or the Escape key on Desktop and Web invokes [onDismissRequest]
 *   through `dismissOnBackPress`;
 * - setting [visible] to false removes the dialog window directly without invoking
 *   [onDismissRequest], because the caller already owns that decision.
 *
 * Focus contract: while visible, the platform dialog window captures focus, restricts focus
 * traversal to the modal content, and makes the surrounding app inert; on dismissal, focus returns
 * to the previously focused element. The surface fades and scales in (alpha 0 to 1, scale 0.98 to
 * 1) over [ElegantModalDefaults.AnimationDurationMillis] while the scrim appears with the dialog
 * window.
 *
 * @param visible whether the modal is shown; false composes nothing.
 * @param onDismissRequest callback invoked for user-initiated dismissal: scrim click, back press
 * on Android, or Escape on Desktop and Web.
 * @param modifier modifier applied once to the full-screen dialog root.
 * @param shape clipping and shadow shape of the modal surface.
 * @param colors theme-aware modal colors.
 * @param content modal content; receives [colors]'s content color through [LocalContentColor].
 */
@Composable
public fun ElegantModal(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = ElegantModalDefaults.Shape,
    colors: ElegantModalColors = ElegantModalDefaults.colors(),
    content: @Composable () -> Unit,
) {
    if (!visible) {
        return
    }
    val entranceProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        entranceProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = ElegantModalDefaults.AnimationDurationMillis,
                easing = FastOutSlowInEasing,
            ),
        )
    }
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.scrimColor),
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer {
                        val progress = entranceProgress.value
                        alpha = progress
                        scaleX = 0.98f + 0.02f * progress
                        scaleY = 0.98f + 0.02f * progress
                    }
                    .widthIn(max = resolveModalMaxWidth())
                    .shadow(
                        elevation = ElegantElevation.medium,
                        shape = shape,
                        clip = false,
                    )
                    .clip(shape)
                    .background(colors.containerColor)
                    .padding(ElegantSpacing.xxxl),
            ) {
                CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
                    content()
                }
            }
        }
    }
}

/** Returns the maximum width of the modal surface before the content wraps. */
internal fun resolveModalMaxWidth(): Dp = 480.dp

/**
 * Resolves theme-aware modal colors.
 *
 * The scrim is a fixed black overlay at [ElegantModalDefaults.ScrimAlpha] rather than a theme
 * role, because the dimming layer must stay visibly darker than both Light and Dark surfaces.
 */
internal fun resolveModalColors(themeColors: ElegantColors): ElegantModalColors = ElegantModalColors(
    scrimColor = Color.Black.copy(alpha = ElegantModalDefaults.ScrimAlpha),
    containerColor = themeColors.surfaceRaised,
    contentColor = themeColors.textPrimary,
)

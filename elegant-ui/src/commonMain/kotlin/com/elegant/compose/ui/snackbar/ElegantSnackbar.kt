package com.elegant.compose.ui.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantElevation
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay

/**
 * Auto-dismiss timing presets supported by [ElegantSnackbarHostState.showSnackbar].
 */
public enum class ElegantSnackbarDuration {
    /** Dismissed after [ElegantSnackbarDefaults.ShortDurationMillis] milliseconds. */
    Short,

    /** Dismissed after [ElegantSnackbarDefaults.LongDurationMillis] milliseconds. */
    Long,

    /** Dismissed only by the action click; never by a timer. */
    Indefinite,
}

/**
 * State colors used by [ElegantSnackbar].
 *
 * Use [ElegantSnackbarDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor surface color behind the message and action.
 * @property contentColor message text color.
 * @property actionContentColor action label color.
 */
@Immutable
public data class ElegantSnackbarColors(
    val containerColor: Color,
    val contentColor: Color,
    val actionContentColor: Color,
)

/** Theme-aware defaults for [ElegantSnackbar]. */
public object ElegantSnackbarDefaults {
    /** Auto-dismiss delay for [ElegantSnackbarDuration.Short]. */
    public const val ShortDurationMillis: Long = 4_000L

    /** Auto-dismiss delay for [ElegantSnackbarDuration.Long]. */
    public const val LongDurationMillis: Long = 10_000L

    /** Standard slide-and-fade transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Minimum surface height and action touch-target height. */
    internal val MinimumHeight: Dp = 48.dp

    /** Preferred snackbar width; wider hosts keep the surface centered. */
    internal val MaxSnackbarWidth: Dp = 568.dp

    /** Returns theme-aware colors for the active theme. */
    @Composable
    public fun colors(): ElegantSnackbarColors = resolveSnackbarColors(ElegantTheme.colors)
}

/**
 * Data backing one [ElegantSnackbarHostState.showSnackbar] call.
 */
internal data class SnackbarData(
    val id: Long,
    val message: String,
    val actionLabel: String?,
    val duration: ElegantSnackbarDuration,
    val dismissed: CompletableDeferred<Unit>,
)

/**
 * Hoists the transient message shown by [ElegantSnackbarHost].
 *
 * Create one instance per host with `remember { ElegantSnackbarHostState() }`. [showSnackbar]
 * suspends until the message is dismissed by the host: either automatically after the selected
 * [ElegantSnackbarDuration] elapses or when the user clicks the optional action label. Showing a
 * new message replaces the current one; the replaced call resumes as if it had been dismissed.
 */
public class ElegantSnackbarHostState {
    internal var currentData by mutableStateOf<SnackbarData?>(null)
        private set

    private var nextId = 0L

    /**
     * Shows a transient message and suspends until it is dismissed.
     *
     * Call from a coroutine scope that outlives the host, such as
     * `rememberCoroutineScope().launch { ... }`. Cancellation of the calling coroutine aborts
     * the suspension; the host keeps auto-dismissing the message on its own timer.
     *
     * @param message transient message text.
     * @param actionLabel optional action label; clicking it dismisses the message.
     * @param duration auto-dismiss timing preset.
     */
    public suspend fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        duration: ElegantSnackbarDuration = ElegantSnackbarDuration.Short,
    ) {
        val data = SnackbarData(
            id = nextId++,
            message = message,
            actionLabel = actionLabel,
            duration = duration,
            dismissed = CompletableDeferred(),
        )
        currentData?.dismissed?.complete(Unit)
        currentData = data
        data.dismissed.await()
    }

    /** Dismisses [data] when it is still the visible message. */
    internal fun dismiss(data: SnackbarData) {
        if (currentData === data) {
            currentData = null
        }
        data.dismissed.complete(Unit)
    }
}

/** Reads the message currently shown by [ElegantSnackbarHostState] for composition. */
@Composable
internal fun ElegantSnackbarHostState.snackbarData(): SnackbarData? = currentData

/**
 * Hosts the transient message shown by [ElegantSnackbarHostState.showSnackbar].
 *
 * Place the host where the message should appear, typically pinned to the bottom of a screen or
 * surface; the message is aligned to the bottom center of the host's bounds. The host owns the
 * dismissal lifecycle: it auto-dismisses after the selected [ElegantSnackbarDuration], keeps
 * [ElegantSnackbarDuration.Indefinite] messages until the action is clicked, and slides the
 * message in and out with [ElegantSnackbarDefaults.AnimationDurationMillis].
 *
 * @param hostState hoisted state driving the shown message.
 * @param modifier modifier applied to the host root.
 * @param colors theme-aware state colors.
 */
@Composable
public fun ElegantSnackbarHost(
    hostState: ElegantSnackbarHostState,
    modifier: Modifier = Modifier,
    colors: ElegantSnackbarColors = ElegantSnackbarDefaults.colors(),
) {
    val data = hostState.snackbarData()

    LaunchedEffect(data?.id) {
        val current = data ?: return@LaunchedEffect
        if (current.duration != ElegantSnackbarDuration.Indefinite) {
            delay(durationMillis(current.duration))
            hostState.dismiss(current)
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        AnimatedVisibility(
            visible = data != null,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = ElegantSnackbarDefaults.AnimationDurationMillis,
                    easing = FastOutSlowInEasing,
                ),
            ) + slideInVertically(
                animationSpec = tween(
                    durationMillis = ElegantSnackbarDefaults.AnimationDurationMillis,
                    easing = FastOutSlowInEasing,
                ),
                initialOffsetY = { it },
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = ElegantSnackbarDefaults.AnimationDurationMillis,
                    easing = FastOutSlowInEasing,
                ),
            ) + slideOutVertically(
                animationSpec = tween(
                    durationMillis = ElegantSnackbarDefaults.AnimationDurationMillis,
                    easing = FastOutSlowInEasing,
                ),
                targetOffsetY = { it },
            ),
        ) {
            val current = data ?: return@AnimatedVisibility
            ElegantSnackbar(
                text = current.message,
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = ElegantSnackbarDefaults.MaxSnackbarWidth),
                actionLabel = current.actionLabel,
                onActionClick = { hostState.dismiss(current) },
                colors = colors,
            )
        }
    }
}

/**
 * Displays a transient message surface.
 *
 * Use [ElegantSnackbarHost] and [ElegantSnackbarHostState] for the animated, auto-dismissing
 * pattern; this composable renders the surface alone for custom layouts. The surface is not
 * focusable: it preserves the semantics of its content and announces no interactive role. When
 * [actionLabel] is provided, the label is announced as a button and keeps a 48dp minimum touch
 * target; the message text never intercepts clicks.
 *
 * @param text transient message text.
 * @param modifier modifier applied to the surface root.
 * @param actionLabel optional action label shown after the message.
 * @param onActionClick callback invoked when the action label is clicked; null renders the label
 *   without interaction.
 * @param colors theme-aware state colors.
 */
@Composable
public fun ElegantSnackbar(
    text: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    colors: ElegantSnackbarColors = ElegantSnackbarDefaults.colors(),
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .defaultMinSize(minHeight = ElegantSnackbarDefaults.MinimumHeight)
            .shadow(
                elevation = ElegantElevation.medium,
                shape = RoundedCornerShape(ElegantRadius.md),
                clip = false,
            )
            .clip(RoundedCornerShape(ElegantRadius.md))
            .background(colors.containerColor)
            .padding(horizontal = ElegantSpacing.xl, vertical = ElegantSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = ElegantTheme.typography.bodyMedium,
            color = colors.contentColor,
        )
        if (actionLabel != null) {
            Spacer(Modifier.width(ElegantSpacing.lg))
            Box(
                modifier = Modifier
                    .defaultMinSize(minHeight = ElegantSnackbarDefaults.MinimumHeight)
                    .clip(RoundedCornerShape(ElegantRadius.xs))
                    .clickable(
                        enabled = onActionClick != null,
                        role = Role.Button,
                        interactionSource = interactionSource,
                        indication = ripple(color = colors.actionContentColor),
                        onClick = { onActionClick?.invoke() },
                    )
                    .padding(horizontal = ElegantSpacing.xs),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = actionLabel,
                    style = ElegantTheme.typography.labelMedium,
                    color = colors.actionContentColor,
                )
            }
        }
    }
}

/**
 * Maps [duration] to its auto-dismiss delay in milliseconds.
 */
internal fun durationMillis(duration: ElegantSnackbarDuration): Long = when (duration) {
    ElegantSnackbarDuration.Short -> ElegantSnackbarDefaults.ShortDurationMillis
    ElegantSnackbarDuration.Long -> ElegantSnackbarDefaults.LongDurationMillis
    ElegantSnackbarDuration.Indefinite -> Long.MAX_VALUE
}

/**
 * Resolves theme-aware colors for [ElegantSnackbar].
 */
internal fun resolveSnackbarColors(themeColors: ElegantColors): ElegantSnackbarColors = ElegantSnackbarColors(
    containerColor = themeColors.surfaceRaised,
    contentColor = themeColors.textPrimary,
    actionContentColor = themeColors.interactivePrimary,
)

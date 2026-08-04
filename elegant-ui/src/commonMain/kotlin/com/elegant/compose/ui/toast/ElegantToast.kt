package com.elegant.compose.ui.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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

/** Stroke width of the two close X glyph lines. */
internal val ToastCloseGlyphStrokeWidth: Dp = 2.dp

/**
 * Auto-dismiss timing presets supported by [ElegantToastHostState.showToast].
 */
public enum class ElegantToastDuration {
    /** Dismissed after [ElegantToastDefaults.ShortDurationMillis] milliseconds. */
    Short,

    /** Dismissed after [ElegantToastDefaults.LongDurationMillis] milliseconds. */
    Long,

    /** Dismissed only by the close action; never by a timer. */
    Indefinite,
}

/**
 * State colors used by [ElegantToast].
 *
 * Use [ElegantToastDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor surface color behind the title and description.
 * @property titleColor title text color.
 * @property descriptionColor description text color.
 * @property closeIconColor close glyph color.
 */
@Immutable
public data class ElegantToastColors(
    val containerColor: Color,
    val titleColor: Color,
    val descriptionColor: Color,
    val closeIconColor: Color,
)

/** Theme-aware defaults for [ElegantToast]. */
public object ElegantToastDefaults {
    /** Auto-dismiss delay for [ElegantToastDuration.Short]. */
    public const val ShortDurationMillis: Long = 4_000L

    /** Auto-dismiss delay for [ElegantToastDuration.Long]. */
    public const val LongDurationMillis: Long = 10_000L

    /** Standard slide-and-fade transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Maximum toast width; wider hosts keep the surface compact. */
    public val MaxWidth: Dp = 360.dp

    /** Minimum surface height and close-action touch-target height. */
    internal val MinimumHeight: Dp = 48.dp

    /** Visual box hosting the close X glyph. */
    internal val CloseGlyphSize: Dp = 18.dp

    /** Returns theme-aware colors for the active theme. */
    @Composable
    public fun colors(): ElegantToastColors = resolveToastColors(ElegantTheme.colors)
}

/**
 * Data backing one [ElegantToastHostState.showToast] call.
 */
internal data class ToastData(
    val id: Long,
    val title: String,
    val description: String?,
    val duration: ElegantToastDuration,
    val dismissed: CompletableDeferred<Unit>,
)

/**
 * Hoists the transient message shown by [ElegantToastHost].
 *
 * Create one instance per host with `remember { ElegantToastHostState() }`. [showToast] suspends
 * until the message is dismissed by the host: either automatically after the selected
 * [ElegantToastDuration] elapses or when the user clicks the close action. Showing a new message
 * replaces the current one; the replaced call resumes as if it had been dismissed.
 */
public class ElegantToastHostState {
    internal var currentData by mutableStateOf<ToastData?>(null)
        private set

    private var nextId = 0L

    /**
     * Shows a transient message and suspends until it is dismissed.
     *
     * Call from a coroutine scope that outlives the host, such as
     * `rememberCoroutineScope().launch { ... }`. Cancellation of the calling coroutine aborts
     * the suspension; the host keeps auto-dismissing the message on its own timer.
     *
     * @param title title text of the message.
     * @param description optional supporting text; blank values hide the description.
     * @param duration auto-dismiss timing preset.
     */
    public suspend fun showToast(
        title: String,
        description: String? = null,
        duration: ElegantToastDuration = ElegantToastDuration.Short,
    ) {
        val data = ToastData(
            id = nextId++,
            title = title,
            description = description,
            duration = duration,
            dismissed = CompletableDeferred(),
        )
        currentData?.dismissed?.complete(Unit)
        currentData = data
        data.dismissed.await()
    }

    /** Dismisses [data] when it is still the visible message. */
    internal fun dismiss(data: ToastData) {
        if (currentData === data) {
            currentData = null
        }
        data.dismissed.complete(Unit)
    }
}

/** Reads the message currently shown by [ElegantToastHostState] for composition. */
@Composable
internal fun ElegantToastHostState.toastData(): ToastData? = currentData

/**
 * Hosts the transient message shown by [ElegantToastHostState.showToast].
 *
 * Place the host where the message should appear, typically pinned to the top of a screen or
 * surface; the message is aligned to the top center of the host's bounds. The host owns the
 * dismissal lifecycle: it auto-dismisses after the selected [ElegantToastDuration], keeps
 * [ElegantToastDuration.Indefinite] messages until the close action is clicked, and slides the
 * message in and out with [ElegantToastDefaults.AnimationDurationMillis].
 *
 * @param hostState hoisted state driving the shown message.
 * @param modifier modifier applied to the host root.
 * @param colors theme-aware state colors.
 */
@Composable
public fun ElegantToastHost(
    hostState: ElegantToastHostState,
    modifier: Modifier = Modifier,
    colors: ElegantToastColors = ElegantToastDefaults.colors(),
) {
    val data = hostState.toastData()

    LaunchedEffect(data?.id) {
        val current = data ?: return@LaunchedEffect
        if (current.duration != ElegantToastDuration.Indefinite) {
            delay(durationMillis(current.duration))
            hostState.dismiss(current)
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.TopCenter) {
        AnimatedVisibility(
            visible = data != null,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = ElegantToastDefaults.AnimationDurationMillis,
                    easing = FastOutSlowInEasing,
                ),
            ) + slideInVertically(
                animationSpec = tween(
                    durationMillis = ElegantToastDefaults.AnimationDurationMillis,
                    easing = FastOutSlowInEasing,
                ),
                initialOffsetY = { -it },
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = ElegantToastDefaults.AnimationDurationMillis,
                    easing = FastOutSlowInEasing,
                ),
            ) + slideOutVertically(
                animationSpec = tween(
                    durationMillis = ElegantToastDefaults.AnimationDurationMillis,
                    easing = FastOutSlowInEasing,
                ),
                targetOffsetY = { -it },
            ),
        ) {
            val current = data ?: return@AnimatedVisibility
            ElegantToast(
                title = current.title,
                modifier = Modifier.widthIn(max = ElegantToastDefaults.MaxWidth),
                description = current.description,
                onClose = { hostState.dismiss(current) },
                colors = colors,
            )
        }
    }
}

/**
 * Displays a transient message surface.
 *
 * Use [ElegantToastHost] and [ElegantToastHostState] for the animated, auto-dismissing pattern;
 * this composable renders the surface alone for custom layouts. The surface is not focusable: it
 * preserves the semantics of its content and announces no interactive role. A blank [description]
 * hides the supporting text entirely. When [onClose] is provided, the close action is announced
 * as [Role.Button] with the `"Close"` content description and keeps a 48dp minimum touch target;
 * the title and description text never intercept clicks.
 *
 * @param title title text of the message.
 * @param modifier modifier applied to the surface root.
 * @param description optional supporting text; blank values hide the description.
 * @param colors theme-aware state colors.
 * @param onClose callback invoked when the close action is clicked; null renders no close action.
 */
@Composable
public fun ElegantToast(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    colors: ElegantToastColors = ElegantToastDefaults.colors(),
    onClose: (() -> Unit)? = null,
) {
    val resolvedDescription = resolveDescription(description)
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .defaultMinSize(minHeight = ElegantToastDefaults.MinimumHeight)
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
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = ElegantTheme.typography.labelMedium,
                color = colors.titleColor,
            )
            if (resolvedDescription != null) {
                Text(
                    text = resolvedDescription,
                    style = ElegantTheme.typography.bodyMedium,
                    color = colors.descriptionColor,
                )
            }
        }
        if (onClose != null) {
            Spacer(Modifier.width(ElegantSpacing.lg))
            Box(
                modifier = Modifier
                    .defaultMinSize(minHeight = ElegantToastDefaults.MinimumHeight)
                    .clip(RoundedCornerShape(ElegantRadius.xs))
                    .semantics {
                        this.contentDescription = "Close"
                    }
                    .clickable(
                        role = Role.Button,
                        interactionSource = interactionSource,
                        indication = ripple(color = colors.closeIconColor),
                        onClick = onClose,
                    )
                    .padding(horizontal = ElegantSpacing.xs),
                contentAlignment = Alignment.Center,
            ) {
                Canvas(modifier = Modifier.size(ElegantToastDefaults.CloseGlyphSize)) {
                    val inset = size.width / 3f
                    drawLine(
                        color = colors.closeIconColor,
                        start = Offset(inset, inset),
                        end = Offset(size.width - inset, size.height - inset),
                        strokeWidth = ToastCloseGlyphStrokeWidth.toPx(),
                        cap = StrokeCap.Round,
                    )
                    drawLine(
                        color = colors.closeIconColor,
                        start = Offset(size.width - inset, inset),
                        end = Offset(inset, size.height - inset),
                        strokeWidth = ToastCloseGlyphStrokeWidth.toPx(),
                        cap = StrokeCap.Round,
                    )
                }
            }
        }
    }
}

/**
 * Maps [duration] to its auto-dismiss delay in milliseconds.
 */
internal fun durationMillis(duration: ElegantToastDuration): Long = when (duration) {
    ElegantToastDuration.Short -> ElegantToastDefaults.ShortDurationMillis
    ElegantToastDuration.Long -> ElegantToastDefaults.LongDurationMillis
    ElegantToastDuration.Indefinite -> Long.MAX_VALUE
}

/**
 * Resolves theme-aware colors for [ElegantToast].
 */
internal fun resolveToastColors(themeColors: ElegantColors): ElegantToastColors = ElegantToastColors(
    containerColor = themeColors.surfaceRaised,
    titleColor = themeColors.textPrimary,
    descriptionColor = themeColors.textSecondary,
    closeIconColor = themeColors.textTertiary,
)

/**
 * Returns [description] unless it is blank, in which case `null` hides the supporting text.
 */
internal fun resolveDescription(description: String?): String? = description?.takeIf { it.isNotBlank() }

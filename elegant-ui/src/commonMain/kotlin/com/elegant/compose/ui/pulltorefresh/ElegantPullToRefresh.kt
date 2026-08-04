package com.elegant.compose.ui.pulltorefresh

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.progress.ElegantCircularProgressIndicator
import com.elegant.compose.ui.progress.ElegantProgressColors
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Theme-aware colors used by [ElegantPullToRefresh].
 *
 * @property indicatorColor filled segment of the pull arc and the indeterminate ring.
 * @property trackColor empty ring behind the indicator arc.
 * @property scrimColor disc drawn beneath the ring so it stays legible over content.
 */
@Immutable
public data class ElegantPullToRefreshColors(
    val indicatorColor: Color,
    val trackColor: Color,
    val scrimColor: Color,
)

/** Defaults and theme-aware factories shared by [ElegantPullToRefresh]. */
public object ElegantPullToRefreshDefaults {
    /** 40dp diameter of the indicator disc and ring. */
    public val IndicatorSize: Dp = 40.dp

    /** 4dp stroke thickness of the circular ring. */
    public val IndicatorStrokeWidth: Dp = 4.dp

    /** 80dp pull distance at which a release triggers [ElegantPullToRefresh.onRefresh]. */
    public val PullThreshold: Dp = 80.dp

    /**
     * Recommended minimum refresh window: 1200ms, matching the indeterminate sweep so the ring
     * completes one rotation before the caller flips `isRefreshing` back to false.
     */
    public val RefreshDurationMillis: Int = 1200

    /** Emphasized 220ms duration of the pull settle animation. */
    public val AnimationDurationMillis: Int = ElegantMotion.emphasizedDurationMillis

    /** Returns theme-aware colors for the active Light or Dark theme. */
    @Composable
    public fun colors(): ElegantPullToRefreshColors = resolvePullToRefreshColors(ElegantTheme.colors)
}

/**
 * Adds a pull-to-refresh gesture to scrollable content without nesting a scrollable.
 *
 * The wrapper observes the vertical scroll of [content] through a nested-scroll connection: while
 * the content is at its start, a drag downward accumulates a pull distance instead of scrolling.
 * Releasing at or beyond [ElegantPullToRefreshDefaults.PullThreshold] invokes [onRefresh] once and
 * snaps the indicator into the refreshing position; releasing below the threshold animates the
 * indicator back. [isRefreshing] is caller-controlled: keep it true while the refresh runs and the
 * indicator keeps rotating indeterminately, then flip it back to false to retract the indicator.
 *
 * The overlay is decorative and never changes the measured size of [content]; the wrapped content
 * keeps its own semantics. The gesture is disabled while [enabled] is false and never triggers
 * [onRefresh] while [isRefreshing] is already true.
 *
 * @param isRefreshing whether a refresh is currently in progress.
 * @param onRefresh called when a pull crosses the threshold and is released.
 * @param modifier modifier applied once to the wrapper root.
 * @param enabled whether the pull gesture may accumulate distance and trigger a refresh.
 * @param colors theme-aware indicator, track, and scrim colors.
 * @param content scrollable content wrapped by the pull gesture.
 */
@Composable
public fun ElegantPullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantPullToRefreshColors = ElegantPullToRefreshDefaults.colors(),
    content: @Composable () -> Unit,
) {
    val currentOnRefresh by rememberUpdatedState(onRefresh)
    val currentEnabled by rememberUpdatedState(enabled)
    val currentIsRefreshing by rememberUpdatedState(isRefreshing)
    val scope = rememberCoroutineScope()
    val pull = remember { Animatable(0f) }
    val thresholdPx = with(LocalDensity.current) {
        ElegantPullToRefreshDefaults.PullThreshold.toPx()
    }
    val connection = remember(pull, thresholdPx) {
        PullToRefreshNestedScrollConnection(
            pull = pull,
            scope = scope,
            isEnabled = { currentEnabled },
            isRefreshing = { currentIsRefreshing },
            onRefresh = { currentOnRefresh() },
            thresholdPx = thresholdPx,
        )
    }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) {
            connection.settlePullTo(0f)
        }
    }
    LaunchedEffect(enabled) {
        if (!enabled) {
            connection.settlePullTo(0f)
        }
    }

    val pullFractionValue = pullFraction(pull.value, thresholdPx)
    val pullState = resolvePullState(
        isRefreshing = currentIsRefreshing,
        pullFraction = pullFractionValue,
        released = connection.released,
    )

    Layout(
        modifier = modifier
            .nestedScroll(connection)
            .pointerInput(connection) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Final)
                        if (event.changes.any { it.previousPressed && !it.pressed }) {
                            connection.onDragReleased()
                        }
                    }
                }
            },
        content = {
            Box { content() }
            PullToRefreshIndicator(
                progress = if (pullState == RefreshPullState.Refreshing) {
                    null
                } else {
                    pullFractionValue
                },
                alpha = pullIndicatorAlpha(pullState, pullFractionValue),
                rotation = if (pullState == RefreshPullState.Refreshing) {
                    0f
                } else {
                    360f * pullFractionValue
                },
                translationY = pull.value,
                colors = colors,
            )
        },
    ) { measurables, constraints ->
        val contentPlaceable = measurables[0].measure(constraints)
        val indicatorPlaceable = measurables[1].measure(
            constraints.copy(minWidth = 0, minHeight = 0),
        )
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.placeRelative(0, 0)
            indicatorPlaceable.placeRelative(
                x = (contentPlaceable.width - indicatorPlaceable.width) / 2,
                y = 0,
            )
        }
    }
}

/** Visual states of the pull indicator. */
internal enum class RefreshPullState {
    /** No pull distance and no refresh in progress; the indicator is hidden. */
    Idle,

    /** A drag is accumulating or settling pull distance below the trigger. */
    Pulling,

    /** A release committed the pull or [ElegantPullToRefresh.isRefreshing] is true. */
    Refreshing,
}

/**
 * Bridges the wrapped content's scroll into the pull indicator.
 *
 * The connection intercepts drag deltas in `onPreScroll` only while the tracked child scroll offset
 * is at its start, so the wrapped scrollable keeps consuming its own scroll at all other times.
 */
internal class PullToRefreshNestedScrollConnection(
    private val pull: Animatable<Float, AnimationVector1D>,
    private val scope: CoroutineScope,
    private val isEnabled: () -> Boolean,
    private val isRefreshing: () -> Boolean,
    private val onRefresh: () -> Unit,
    private val thresholdPx: Float,
) : NestedScrollConnection {

    private var childScrollOffset = 0f

    /** Whether the current pull was committed by a release, snapshot-backed for recomposition. */
    internal var released by mutableStateOf(false)

    private fun snapPull(delta: Float) {
        val target = (pull.value + delta).coerceIn(0f, thresholdPx)
        scope.launch {
            pull.snapTo(target)
        }
    }

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        if (source != NestedScrollSource.Drag || !isEnabled()) return Offset.Zero
        val delta = available.y
        return when {
            pull.value > 0f -> {
                released = false
                snapPull(delta)
                Offset(0f, delta)
            }

            delta > 0f && childScrollOffset <= 0f -> {
                released = false
                snapPull(delta)
                Offset(0f, delta)
            }

            else -> Offset.Zero
        }
    }

    override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
        childScrollOffset = (childScrollOffset + consumed.y).coerceAtLeast(0f)
        return Offset.Zero
    }

    /** Commits or cancels the pull when the dragging pointer goes up. */
    internal fun onDragReleased() {
        if (pull.value <= 0f) return
        released = true
        val fraction = pullFraction(pull.value, thresholdPx)
        when {
            isRefreshing() -> settlePullTo(thresholdPx)

            shouldTriggerRefresh(fraction, TriggerThresholdFraction) && isEnabled() -> {
                onRefresh()
                settlePullTo(thresholdPx)
            }

            else -> settlePullTo(0f)
        }
    }

    /** Animates the pull distance to [target] in pixels. */
    internal fun settlePullTo(target: Float) {
        scope.launch {
            pull.animateTo(
                targetValue = target,
                animationSpec = tween(
                    durationMillis = ElegantPullToRefreshDefaults.AnimationDurationMillis,
                ),
            )
        }
    }
}

/** Fraction of the pull threshold that must be reached at release to trigger a refresh. */
internal const val TriggerThresholdFraction: Float = 1f

internal fun resolvePullState(
    isRefreshing: Boolean,
    pullFraction: Float,
    released: Boolean,
): RefreshPullState = when {
    isRefreshing -> RefreshPullState.Refreshing

    pullFraction <= 0f -> RefreshPullState.Idle

    released && pullFraction >= TriggerThresholdFraction -> RefreshPullState.Refreshing

    else -> RefreshPullState.Pulling
}

internal fun shouldTriggerRefresh(pullFraction: Float, thresholdFraction: Float): Boolean =
    pullFraction >= thresholdFraction

internal fun pullFraction(pullDistancePx: Float, thresholdPx: Float): Float =
    if (thresholdPx <= 0f) 0f else (pullDistancePx / thresholdPx).coerceIn(0f, 1f)

internal fun pullIndicatorAlpha(pullState: RefreshPullState, pullFraction: Float): Float =
    when (pullState) {
        RefreshPullState.Idle -> 0f
        RefreshPullState.Pulling -> pullFraction
        RefreshPullState.Refreshing -> 1f
    }

internal fun resolvePullToRefreshColors(themeColors: ElegantColors): ElegantPullToRefreshColors =
    ElegantPullToRefreshColors(
        indicatorColor = themeColors.interactivePrimary,
        trackColor = themeColors.borderDefault,
        scrimColor = themeColors.surfaceDefault,
    )

@Composable
private fun PullToRefreshIndicator(
    progress: Float?,
    alpha: Float,
    rotation: Float,
    translationY: Float,
    colors: ElegantPullToRefreshColors,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clearAndSetSemantics {}
            .graphicsLayer {
                this.alpha = alpha
                rotationZ = rotation
                this.translationY = translationY
            }
            .padding(top = ElegantSpacing.xl)
            .size(ElegantPullToRefreshDefaults.IndicatorSize)
            .clip(CircleShape)
            .background(colors.scrimColor),
        contentAlignment = Alignment.Center,
    ) {
        ElegantCircularProgressIndicator(
            progress = progress,
            size = ElegantPullToRefreshDefaults.IndicatorSize,
            strokeWidth = ElegantPullToRefreshDefaults.IndicatorStrokeWidth,
            colors = ElegantProgressColors(
                indicatorColor = colors.indicatorColor,
                trackColor = colors.trackColor,
            ),
        )
    }
}

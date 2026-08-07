// Copyright 2026, elegant-ui contributors
// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from Miuix (https://github.com/yukonga/Miuix) under Apache-2.0.

package com.elegant.compose.ui.topappbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirst
import androidx.compose.ui.util.fastRoundToInt
import androidx.compose.ui.util.lerp
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import kotlin.math.abs
import kotlinx.coroutines.launch

/**
 * A top app bar that collapses its large title while the content below it scrolls.
 *
 * The bar is driven by an [ElegantTopAppBarScrollBehavior] created with
 * [rememberElegantTopAppBarScrollBehavior]; attach its
 * [ElegantTopAppBarScrollBehavior.nestedScrollConnection] to the scrollable content with
 * `Modifier.nestedScroll(...)`. While the content scrolls up, the large title slides out and
 * fades, the bar height interpolates down to [ElegantTopAppBarDefaults.CollapsedHeight], and the
 * small title fades and slides in; scrolling down expands the bar back. Without a
 * [scrollBehavior] the bar renders fully expanded and static.
 *
 * @param title collapsed small title text.
 * @param modifier modifier applied once to the bar root.
 * @param largeTitle expanded large title text; defaults to [title].
 * @param subtitle supporting text rendered below the large title.
 * @param navigationIcon optional leading slot, typically a back or menu button.
 * @param actions optional trailing slot for action icons.
 * @param scrollBehavior the behavior that collapses and expands the bar; attach its
 *   [ElegantTopAppBarScrollBehavior.nestedScrollConnection] to the scrollable content.
 * @param colors theme-aware state colors.
 */
@Composable
public fun ElegantTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    largeTitle: String = title,
    subtitle: String? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    scrollBehavior: ElegantTopAppBarScrollBehavior? = null,
    colors: ElegantTopAppBarColors = ElegantTopAppBarDefaults.colors(),
) {
    val actionsRow: @Composable () -> Unit = {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            content = actions ?: {},
        )
    }

    ElegantTopAppBarLayout(
        title = title,
        largeTitle = largeTitle,
        subtitle = subtitle,
        navigationIcon = navigationIcon,
        actions = actionsRow,
        scrollBehavior = scrollBehavior,
        colors = colors,
        modifier = modifier,
    )
}

/**
 * Theme-aware state colors used by [ElegantTopAppBar].
 *
 * Use [ElegantTopAppBarDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor bar background color.
 * @property titleColor collapsed small title text color.
 * @property largeTitleColor expanded large title text color.
 * @property subtitleColor subtitle text color.
 */
@Immutable
public data class ElegantTopAppBarColors(
    val containerColor: Color,
    val titleColor: Color,
    val largeTitleColor: Color,
    val subtitleColor: Color,
)

/** Theme-aware defaults for [ElegantTopAppBar]. */
public object ElegantTopAppBarDefaults {
    /** The collapsed height of the bar. */
    public val CollapsedHeight: Dp = 52.dp

    /** The default horizontal padding of the title and large title. */
    public val TitlePadding: Dp = 26.dp

    /** The default start padding of the navigation icon. */
    public val NavigationIconPadding: Dp = 16.dp

    /** The default end padding of the action icons. */
    public val ActionIconPadding: Dp = 16.dp

    /** The bottom padding below the large title when no subtitle is present. */
    public val LargeTitleBottomPadding: Dp = 4.dp

    /** The bottom padding below the subtitle. */
    public val SubtitleBottomPadding: Dp = 8.dp

    /** Returns theme-aware state colors. */
    @Composable
    public fun colors(): ElegantTopAppBarColors = ElegantTopAppBarColors(
        containerColor = ElegantTheme.colors.surfaceDefault,
        titleColor = ElegantTheme.colors.textPrimary,
        largeTitleColor = ElegantTheme.colors.textPrimary,
        subtitleColor = ElegantTheme.colors.textSecondary,
    )
}

/**
 * A state object that can be hoisted to control and observe the top app bar collapse state.
 *
 * The state is read and updated by an [ElegantTopAppBarScrollBehavior]; in most cases it is
 * created with [rememberElegantTopAppBarState] and passed to
 * [rememberElegantTopAppBarScrollBehavior].
 *
 * @param initialHeightOffsetLimit the initial [heightOffsetLimit] in pixels.
 * @param initialHeightOffset the initial [heightOffset] in pixels.
 * @param initialContentOffset the initial [contentOffset] in pixels.
 */
@Stable
public class ElegantTopAppBarState(
    initialHeightOffsetLimit: Float,
    initialHeightOffset: Float,
    initialContentOffset: Float,
) {
    /**
     * The pixel limit that the bar is allowed to collapse to. The limit is negative because
     * [heightOffset] moves up from zero; the layout writes the large title's measured height here.
     */
    public var heightOffsetLimit: Float = initialHeightOffsetLimit

    /**
     * The current height offset in pixels, applied to the fixed height of the bar to control the
     * displayed height while content scrolls. Updates are coerced between [heightOffsetLimit]
     * and zero.
     */
    public var heightOffset: Float
        get() = _heightOffset.floatValue
        set(newOffset) {
            _heightOffset.floatValue = clampTopAppBarHeightOffset(newOffset, heightOffsetLimit)
        }

    /**
     * The total offset of the content scrolled under the bar, updated by the scroll behavior from
     * [NestedScrollConnection.onPostScroll] consumed deltas.
     */
    public var contentOffset: Float = initialContentOffset

    /**
     * The collapsed percentage of the bar: `0.0` is fully expanded and `1.0` is fully collapsed
     * (computed as [heightOffset] / [heightOffsetLimit]).
     */
    public val collapsedFraction: Float
        get() = resolveTopAppBarCollapsedFraction(heightOffset, heightOffsetLimit)

    private var _heightOffset = mutableFloatStateOf(initialHeightOffset)

    public companion object {
        /** The default [Saver] implementation for [ElegantTopAppBarState]. */
        public val Saver: Saver<ElegantTopAppBarState, *> = listSaver(
            save = {
                listOf(it.heightOffsetLimit, it.heightOffset, it.contentOffset)
            },
            restore = {
                ElegantTopAppBarState(
                    initialHeightOffsetLimit = it[0] as Float,
                    initialHeightOffset = it[1] as Float,
                    initialContentOffset = it[2] as Float,
                )
            },
        )
    }
}

/**
 * Creates an [ElegantTopAppBarState] that is remembered across configuration changes.
 *
 * @param initialHeightOffsetLimit the initial [ElegantTopAppBarState.heightOffsetLimit] in pixels.
 * @param initialHeightOffset the initial [ElegantTopAppBarState.heightOffset] in pixels.
 * @param initialContentOffset the initial [ElegantTopAppBarState.contentOffset] in pixels.
 */
@Composable
public fun rememberElegantTopAppBarState(
    initialHeightOffsetLimit: Float = -Float.MAX_VALUE,
    initialHeightOffset: Float = 0f,
    initialContentOffset: Float = 0f,
): ElegantTopAppBarState = rememberSaveable(saver = ElegantTopAppBarState.Saver) {
    ElegantTopAppBarState(initialHeightOffsetLimit, initialHeightOffset, initialContentOffset)
}

/**
 * A scroll behavior that collapses the top app bar while the nested content scrolls up and
 * expands it back when the content is pulled all the way down.
 *
 * Create it with [rememberElegantTopAppBarScrollBehavior], pass it to [ElegantTopAppBar], and
 * attach its [nestedScrollConnection] to the scrollable content with `Modifier.nestedScroll(...)`.
 *
 * @param state the state that tracks the collapse progress.
 * @param canScroll whether scroll events are handled by this behavior.
 * @param snapAnimationSpec how the bar snaps to fully collapsed or fully expanded after a fling
 *   or drag leaves it in an intermediate position.
 * @param flingAnimationSpec how the bar continues the user's fling velocity.
 */
@Stable
public class ElegantTopAppBarScrollBehavior(
    public val state: ElegantTopAppBarState,
    public val snapAnimationSpec: AnimationSpec<Float>?,
    public val flingAnimationSpec: DecayAnimationSpec<Float>?,
    private val canScroll: () -> Boolean,
) {

    /**
     * A [NestedScrollConnection] that updates the bar's [state]; attach it to the scrollable
     * content with `Modifier.nestedScroll(...)`.
     */
    public val nestedScrollConnection: NestedScrollConnection =
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Don't intercept when scrolling down.
                if (!canScroll() || available.y > 0) return Offset.Zero
                val prevHeightOffset = state.heightOffset
                state.heightOffset += available.y
                return if (prevHeightOffset != state.heightOffset) {
                    // The bar is collapsing or expanding; consume the Y-axis scroll only.
                    available.copy(x = 0f)
                } else {
                    Offset.Zero
                }
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                if (!canScroll()) return Offset.Zero
                state.contentOffset += consumed.y

                if (available.y < 0f || consumed.y < 0f) {
                    // Scrolling up: keep the height offset in sync with what the child consumed.
                    val oldHeightOffset = state.heightOffset
                    state.heightOffset += consumed.y
                    return Offset(0f, state.heightOffset - oldHeightOffset)
                }

                if (available.y > 0f) {
                    // Scrolling down: consume up to the available delta to expand the bar back.
                    val oldHeightOffset = state.heightOffset
                    state.heightOffset += available.y
                    return Offset(0f, state.heightOffset - oldHeightOffset)
                }
                return Offset.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                if (available.y > 0) {
                    // Reset the content offset when scrolling all the way down, eliminating float
                    // precision drift.
                    state.contentOffset = 0f
                }
                return settleTopAppBar(
                    state = state,
                    velocity = available.y,
                    flingAnimationSpec = flingAnimationSpec,
                    snapAnimationSpec = snapAnimationSpec,
                )
            }
        }
}

/**
 * Creates an [ElegantTopAppBarScrollBehavior] remembered across compositions.
 *
 * @param state the state that tracks the collapse progress; created with
 *   [rememberElegantTopAppBarState].
 * @param canScroll a callback used to determine whether scroll events are handled.
 * @param snapAnimationSpec the snap animation; defaults to a Folme spring with a `0.3s` response.
 * @param flingAnimationSpec the fling decay animation; defaults to spline-based decay.
 */
@Composable
public fun rememberElegantTopAppBarScrollBehavior(
    state: ElegantTopAppBarState = rememberElegantTopAppBarState(),
    canScroll: () -> Boolean = { true },
    snapAnimationSpec: AnimationSpec<Float>? = elegantFolmeSpring(
        dampingRatio = 1.0f,
        responseSeconds = 0.3f,
    ),
    flingAnimationSpec: DecayAnimationSpec<Float>? = rememberSplineBasedDecay(),
): ElegantTopAppBarScrollBehavior = remember(state, canScroll, snapAnimationSpec, flingAnimationSpec) {
    ElegantTopAppBarScrollBehavior(
        state = state,
        snapAnimationSpec = snapAnimationSpec,
        flingAnimationSpec = flingAnimationSpec,
        canScroll = canScroll,
    )
}

/** Coerces a height offset into the bar's collapse range. */
internal fun clampTopAppBarHeightOffset(offset: Float, limit: Float): Float =
    offset.coerceIn(minimumValue = limit, maximumValue = 0f)

/** The collapsed percentage of the bar; `0.0` expanded, `1.0` collapsed. */
internal fun resolveTopAppBarCollapsedFraction(offset: Float, limit: Float): Float =
    if (limit != 0f) offset / limit else 0f

/** The snap target after a fling or drag: fully expanded below half, fully collapsed above. */
internal fun resolveTopAppBarSettleTarget(fraction: Float, limit: Float): Float =
    if (fraction < 0.5f) 0f else limit

/**
 * Settles the bar to a stable state (fully expanded or collapsed) by animating its height offset
 * with the fling velocity followed by a snap, and returns the consumed velocity.
 */
private suspend fun settleTopAppBar(
    state: ElegantTopAppBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?,
): Velocity {
    // Nothing to settle when the bar is already fully collapsed or expanded. The collapsed check
    // tolerates float precision.
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) return Velocity.Zero
    var remainingVelocity = velocity
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(initialValue = 0f, initialVelocity = velocity).animateDecay(
            flingAnimationSpec,
        ) {
            val delta = value - lastValue
            val initialHeightOffset = state.heightOffset
            state.heightOffset = initialHeightOffset + delta
            val consumed = abs(initialHeightOffset - state.heightOffset)
            lastValue = value
            remainingVelocity = this.velocity
            // Stop when the bar stops consuming to avoid rounding drift.
            if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
        }
    }
    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 && state.heightOffset > state.heightOffsetLimit) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                resolveTopAppBarSettleTarget(state.collapsedFraction, state.heightOffsetLimit),
                animationSpec = snapAnimationSpec,
            ) {
                state.heightOffset = value
            }
        }
    }
    return Velocity(0f, velocity - remainingVelocity)
}

@Composable
private fun ElegantTopAppBarLayout(
    title: String,
    largeTitle: String,
    subtitle: String?,
    navigationIcon: (@Composable () -> Unit)?,
    actions: @Composable () -> Unit,
    scrollBehavior: ElegantTopAppBarScrollBehavior?,
    colors: ElegantTopAppBarColors,
    modifier: Modifier = Modifier,
) {
    val titleStyle = ElegantTheme.typography.titleLarge
    val largeTitleStyle = ElegantTheme.typography.headlineLarge
    val subtitleStyle = ElegantTheme.typography.bodyMedium

    // Producer lambdas — reads stay in layout/draw phases so scrolling never recomposes this subtree.
    val scrolledOffset = remember(scrollBehavior) {
        { scrollBehavior?.state?.heightOffset ?: 0f }
    }
    val largeTitleAlpha = remember(scrollBehavior) {
        {
            val fraction = scrollBehavior?.state?.collapsedFraction ?: 0f
            1f - (fraction * 3f).coerceIn(0f, 1f)
        }
    }
    val updateHeightOffsetLimit = remember(scrollBehavior) {
        { height: Int ->
            scrollBehavior?.state?.let { state ->
                val limit = -height.toFloat()
                if (state.heightOffsetLimit != limit) state.heightOffsetLimit = limit
            }
            Unit
        }
    }

    // Boolean derivedStateOf invalidates only on flip, so the spring fires once per crossing
    // instead of every frame.
    val smallTitleVisible by remember(scrollBehavior) {
        derivedStateOf {
            (scrollBehavior?.state?.collapsedFraction ?: 0f) * 3f >= 1f
        }
    }
    val smallTitleAlpha = remember { Animatable(if (smallTitleVisible) 1f else 0f) }
    val smallTitleTranslationY = remember { Animatable(if (smallTitleVisible) 0f else 20f) }

    LaunchedEffect(smallTitleVisible) {
        if (smallTitleVisible) {
            val showSpec = elegantFolmeSpring<Float>(dampingRatio = 1.0f, responseSeconds = 0.3f)
            launch { smallTitleAlpha.animateTo(1f, showSpec) }
            launch { smallTitleTranslationY.animateTo(0f, showSpec) }
        } else {
            val hideSpec = elegantFolmeSpring<Float>(dampingRatio = 1.0f, responseSeconds = 0.15f)
            launch { smallTitleAlpha.animateTo(0f, hideSpec) }
            launch { smallTitleTranslationY.animateTo(20f, hideSpec) }
        }
    }

    val animatedTitleColor by animateColorAsState(
        targetValue = colors.titleColor,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantTopAppBarTitleColor",
    )
    val animatedLargeTitleColor by animateColorAsState(
        targetValue = colors.largeTitleColor,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantTopAppBarLargeTitleColor",
    )
    val animatedSubtitleColor by animateColorAsState(
        targetValue = colors.subtitleColor,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantTopAppBarSubtitleColor",
    )

    Layout(
        {
            Box(
                Modifier
                    .layoutId("navigationIcon")
                    .padding(start = ElegantTopAppBarDefaults.NavigationIconPadding),
            ) {
                navigationIcon?.invoke()
            }
            Box(
                Modifier
                    .layoutId("title")
                    .padding(horizontal = ElegantTopAppBarDefaults.TitlePadding)
                    .graphicsLayer {
                        alpha = smallTitleAlpha.value
                        translationY = smallTitleTranslationY.value
                    },
            ) {
                Text(
                    text = title,
                    style = titleStyle,
                    color = animatedTitleColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                )
            }
            Box(
                Modifier
                    .layoutId("actionIcons")
                    .padding(end = ElegantTopAppBarDefaults.ActionIconPadding),
            ) {
                actions()
            }
            Box(
                Modifier
                    .layoutId("largeTitle")
                    .padding(top = ElegantTopAppBarDefaults.CollapsedHeight)
                    .padding(horizontal = ElegantTopAppBarDefaults.TitlePadding)
                    .graphicsLayer { alpha = largeTitleAlpha() },
            ) {
                Column(
                    modifier = Modifier
                        .offset {
                            val value = scrolledOffset()
                            IntOffset(0, value.fastRoundToInt())
                        }
                        .onSizeChanged { updateHeightOffsetLimit(it.height) },
                ) {
                    Text(
                        text = largeTitle,
                        style = largeTitleStyle,
                        color = animatedLargeTitleColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            style = subtitleStyle,
                            color = animatedSubtitleColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        },
        modifier = modifier
            .background(colors.containerColor)
            .clipToBounds()
            .pointerInput(Unit) {
                detectTapGestures { /* Consume click */ }
            },
    ) { measurables, constraints ->
        val navigationIconPlaceable =
            measurables
                .firstOrNull { it.layoutId == "navigationIcon" }
                ?.measure(constraints.copy(minWidth = 0, minHeight = 0))

        val actionIconsPlaceable =
            measurables
                .firstOrNull { it.layoutId == "actionIcons" }
                ?.measure(constraints.copy(minWidth = 0, minHeight = 0))

        val maxTitleWidth =
            if (constraints.maxWidth == Constraints.Infinity) {
                constraints.maxWidth
            } else {
                (constraints.maxWidth -
                    (navigationIconPlaceable?.width ?: 0) -
                    (actionIconsPlaceable?.width ?: 0))
                    .coerceAtLeast(0)
            }
        val titleMaxWidth =
            if (maxTitleWidth == Constraints.Infinity) {
                maxTitleWidth
            } else {
                (maxTitleWidth * TITLE_WIDTH_FRACTION).fastRoundToInt()
            }

        val titlePlaceable =
            measurables
                .fastFirst { it.layoutId == "title" }
                .measure(constraints.copy(minWidth = 0, maxWidth = titleMaxWidth, minHeight = 0))

        val largeTitlePlaceable =
            measurables
                .fastFirst { it.layoutId == "largeTitle" }
                .measure(
                    constraints.copy(
                        minWidth = 0,
                        minHeight = 0,
                        maxHeight = Constraints.Infinity,
                    ),
                )

        val collapsedHeight = ElegantTopAppBarDefaults.CollapsedHeight.roundToPx()
        // The large title box carries the collapsed height as top padding; subtract it back for
        // the pure expansion amount.
        val expansion = (largeTitlePlaceable.height - collapsedHeight).coerceAtLeast(0)
        val barHeight = if (expansion > 0) {
            val offset = scrolledOffset()
            val collapseFraction = if (offset.isNaN()) {
                0f
            } else {
                (abs(offset) / expansion.toFloat()).coerceIn(0f, 1f)
            }
            lerp(
                start = collapsedHeight,
                stop = collapsedHeight + expansion,
                fraction = 1f - collapseFraction,
            )
        } else {
            collapsedHeight
        }

        val verticalCenter = collapsedHeight / 2
        val bottomPadding = if (subtitle != null) {
            ElegantTopAppBarDefaults.SubtitleBottomPadding.roundToPx()
        } else {
            ElegantTopAppBarDefaults.LargeTitleBottomPadding.roundToPx()
        }
        val layoutHeight = barHeight + bottomPadding

        layout(constraints.maxWidth, layoutHeight) {
            navigationIconPlaceable?.placeRelative(
                x = 0,
                y = verticalCenter - navigationIconPlaceable.height / 2,
            )

            var baseX = (constraints.maxWidth - titlePlaceable.width) / 2
            val navWidth = navigationIconPlaceable?.width ?: 0
            val actionWidth = actionIconsPlaceable?.width ?: 0
            if (baseX < navWidth) {
                baseX += (navWidth - baseX)
            } else if (baseX + titlePlaceable.width > constraints.maxWidth - actionWidth) {
                baseX += ((constraints.maxWidth - actionWidth) - (baseX + titlePlaceable.width))
            }
            titlePlaceable.placeRelative(
                x = baseX,
                y = verticalCenter - titlePlaceable.height / 2,
            )

            actionIconsPlaceable?.placeRelative(
                x = constraints.maxWidth - actionIconsPlaceable.width,
                y = verticalCenter - actionIconsPlaceable.height / 2,
            )

            largeTitlePlaceable.placeRelative(x = 0, y = 0)
        }
    }
}

// Slack so the centered title isn't butted against the navigation icon or actions.
private const val TITLE_WIDTH_FRACTION = 0.9

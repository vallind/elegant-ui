package com.elegant.compose.ui.bottomsheet

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Density
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantElevation
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.ui.input.pointer.pointerInput

/**
 * Theme-aware colors used by [ElegantBottomSheet].
 *
 * @property scrimColor scrim overlay color.
 * @property containerColor sheet surface color.
 * @property contentColor locally provided content color.
 * @property handleColor drag-handle indicator color.
 */
@Immutable
public data class ElegantBottomSheetColors(
    val scrimColor: Color,
    val containerColor: Color,
    val contentColor: Color,
    val handleColor: Color,
)

/** Defaults and theme-aware factories shared by bottom sheets. */
public object ElegantBottomSheetDefaults {
    /** Maximum sheet width on wide layouts. */
    public val MaxWidth: Dp = 640.dp

    /** Scrim overlay alpha. */
    public const val ScrimAlpha: Float = 0.4f

    /** Sheet entrance and exit duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.emphasizedDurationMillis

    /** Drag-handle width. */
    public val HandleWidth: Dp = 32.dp

    /** Drag-handle height. */
    public val HandleHeight: Dp = 4.dp

    /** Returns theme-aware bottom sheet colors. */
    @Composable
    public fun colors(): ElegantBottomSheetColors = resolveBottomSheetColors(ElegantTheme.colors)
}

/**
 * Presents a modal sheet that slides in from the bottom edge over a scrim.
 *
 * Dismissal is defined explicitly: scrim outside click and Escape/back invoke
 * [onDismissRequest]; the dialog window captures focus while visible and restores it on close.
 * The sheet is width-capped ([ElegantBottomSheetDefaults.MaxWidth]), top-corners rounded,
 * scrollable, and shows a centered drag-handle above [content].
 *
 * Dragging the sheet downward past a quarter of the window height dismisses it on release;
 * a shorter drag springs back.
 *
 * @param visible whether the sheet is shown; the caller owns the dismiss state.
 * @param onDismissRequest callback for scrim click, Escape, back, or a dismiss drag.
 * @param modifier modifier applied once to the sheet panel.
 * @param title optional title rendered in a header row above the content.
 * @param startAction optional leading header content (drawn before [title]).
 * @param endAction optional trailing header content (drawn after [title]).
 * @param colors theme-aware sheet colors.
 * @param content sheet content below the drag handle.
 */
@Composable
public fun ElegantBottomSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    startAction: (@Composable () -> Unit)? = null,
    endAction: (@Composable () -> Unit)? = null,
    colors: ElegantBottomSheetColors = ElegantBottomSheetDefaults.colors(),
    content: @Composable ColumnScope.() -> Unit,
) {
    if (!visible) return

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
        ),
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val scrimAlpha = remember { Animatable(0f) }
            val slideOffset = remember { Animatable(1f) }
            var dragOffset by remember { mutableFloatStateOf(0f) }
            LaunchedEffect(Unit) {
                scrimAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = ElegantBottomSheetDefaults.AnimationDurationMillis,
                        easing = FastOutSlowInEasing,
                    ),
                )
                slideOffset.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = ElegantBottomSheetDefaults.AnimationDurationMillis,
                        easing = FastOutSlowInEasing,
                    ),
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.scrimColor.copy(alpha = scrimAlpha.value)),
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .widthIn(max = ElegantBottomSheetDefaults.MaxWidth)
                    .fillMaxWidth()
                    .graphicsLayer {
                        translationY = maxHeight.value * density + dragOffset
                    }
                    .pointerInput(onDismissRequest) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { change, dragAmount ->
                                change.consume()
                                if (dragAmount > 0f) {
                                    dragOffset += dragAmount
                                }
                            },
                            onDragEnd = {
                                val dismissThreshold = maxHeight.value * density * 0.25f
                                if (dragOffset > dismissThreshold) {
                                    onDismissRequest()
                                } else {
                                    dragOffset = 0f
                                }
                            },
                            onDragCancel = {
                                dragOffset = 0f
                            },
                        )
                    }
                    .shadow(
                        elevation = ElegantElevation.medium,
                        shape = RoundedCornerShape(
                            topStart = ElegantRadius.lg,
                            topEnd = ElegantRadius.lg,
                        ),
                        clip = false,
                    )
                    .clip(
                        RoundedCornerShape(
                            topStart = ElegantRadius.lg,
                            topEnd = ElegantRadius.lg,
                        ),
                    )
                    .background(colors.containerColor),
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = ElegantSpacing.sm),
                        contentAlignment = Alignment.Center,
                    ) {
                        Box(
                            modifier = Modifier
                                .width(ElegantBottomSheetDefaults.HandleWidth)
                                .height(ElegantBottomSheetDefaults.HandleHeight)
                                .clip(RoundedCornerShape(ElegantRadius.full))
                                .background(colors.handleColor),
                        )
                    }
                    val headerVisible = title != null || startAction != null || endAction != null
                    if (headerVisible) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = ElegantSpacing.lg)
                                .padding(bottom = ElegantSpacing.xs),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (startAction != null) {
                                startAction()
                                Spacer(modifier = Modifier.width(ElegantSpacing.sm))
                            }
                            if (title != null) {
                                Text(
                                    text = title,
                                    style = ElegantTheme.typography.titleMedium,
                                    color = colors.contentColor,
                                    modifier = Modifier.weight(1f),
                                )
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            if (endAction != null) {
                                Spacer(modifier = Modifier.width(ElegantSpacing.sm))
                                endAction()
                            }
                        }
                    }
                    CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
                        content()
                    }
                }
            }
        }
    }
}


internal fun resolveBottomSheetColors(
    themeColors: ElegantColors,
): ElegantBottomSheetColors = ElegantBottomSheetColors(
    scrimColor = Color.Black.copy(alpha = ElegantBottomSheetDefaults.ScrimAlpha),
    containerColor = themeColors.surfaceRaised,
    contentColor = themeColors.textPrimary,
    handleColor = themeColors.borderStrong,
)


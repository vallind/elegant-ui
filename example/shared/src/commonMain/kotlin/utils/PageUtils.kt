// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

package utils

import LocalAppState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.elyon.kmp.basic.ScrollBehavior
import io.elyon.kmp.basic.SmallTopAppBar
import io.elyon.kmp.basic.TopAppBar
import io.elyon.kmp.blur.BlendColorEntry
import io.elyon.kmp.blur.BlurColors
import io.elyon.kmp.blur.BlurDefaults
import io.elyon.kmp.blur.LayerBackdrop
import io.elyon.kmp.blur.ProgressiveBlur
import io.elyon.kmp.blur.isRuntimeShaderSupported
import io.elyon.kmp.blur.progressiveTextureBlur
import io.elyon.kmp.blur.rememberLayerBackdrop
import io.elyon.kmp.blur.textureBlur
import io.elyon.kmp.theme.ElyonTheme
import io.elyon.kmp.utils.overScrollVertical
import io.elyon.kmp.utils.scrollEndHaptic

fun Modifier.pageScrollModifiers(
    enableScrollEndHaptic: Boolean,
    showTopAppBar: Boolean,
    topAppBarScrollBehavior: ScrollBehavior,
): Modifier = this
    .then(if (enableScrollEndHaptic) Modifier.scrollEndHaptic() else Modifier)
    .overScrollVertical()
    .then(if (showTopAppBar) Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection) else Modifier)
    .fillMaxHeight()

@Composable
fun pageContentPadding(
    innerPadding: PaddingValues,
    outerPadding: PaddingValues,
    isWideScreen: Boolean,
    extraTop: Dp = 0.dp,
    extraStart: Dp = 0.dp,
    extraEnd: Dp = 0.dp,
    extraBottom: Dp = 0.dp,
): PaddingValues {
    val topPadding = innerPadding.calculateTopPadding() + extraTop
    val bottomPadding = outerPadding.calculateBottomPadding() + extraBottom + if (isWideScreen) {
        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + WindowInsets.captionBar.asPaddingValues()
            .calculateBottomPadding()
    } else {
        0.dp
    }

    return remember(topPadding, bottomPadding, extraStart, extraEnd, extraBottom) {
        PaddingValues(
            top = topPadding,
            start = extraStart,
            end = extraEnd,
            bottom = bottomPadding,
        )
    }
}

@Composable
fun AdaptiveTopAppBar(
    title: String,
    showTopAppBar: Boolean,
    isWideScreen: Boolean,
    scrollBehavior: ScrollBehavior,
    subtitle: String = "",
    color: Color = ElyonTheme.colorScheme.surface,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    bottomContent: @Composable () -> Unit = {},
) {
    if (showTopAppBar) {
        if (isWideScreen) {
            SmallTopAppBar(
                title = title,
                subtitle = subtitle,
                color = color,
                scrollBehavior = scrollBehavior,
                defaultWindowInsetsPadding = false,
                navigationIcon = navigationIcon,
                actions = actions,
                bottomContent = bottomContent,
            )
        } else {
            TopAppBar(
                title = title,
                subtitle = subtitle,
                color = color,
                scrollBehavior = scrollBehavior,
                navigationIcon = navigationIcon,
                actions = actions,
                bottomContent = bottomContent,
            )
        }
    }
}

@Composable
fun rememberBlurBackdrop(): LayerBackdrop? {
    val appState = LocalAppState.current
    if (!appState.enableBlur || !isRuntimeShaderSupported()) return null
    val surfaceColor = ElyonTheme.colorScheme.surface
    return rememberLayerBackdrop {
        drawRect(surfaceColor)
        drawContent()
    }
}

@Composable
fun BlurredBar(
    backdrop: LayerBackdrop?,
    blurEnabled: Boolean,
    scrollBehavior: ScrollBehavior? = null,
    content: @Composable () -> Unit,
) {
    val progressive = LocalAppState.current.blurStyle == 1
    val blurActive = blurEnabled && backdrop != null
    Box(
        modifier = if (blurActive && !progressive) {
            Modifier.textureBlur(
                backdrop = backdrop,
                shape = RectangleShape,
                blurRadius = 25f,
                colors = barBlurColors(),
            )
        } else {
            Modifier
        },
    ) {
        if (blurActive && progressive) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        alpha = scrollBehavior?.state
                            ?.let { (-it.contentOffset / 48.dp.toPx()).coerceIn(0f, 1f) }
                            ?: 1f
                    }
                    .progressiveTextureBlur(
                        backdrop = backdrop,
                        shape = RectangleShape,
                        gradient = ProgressiveBlur.Top.copy(curve = 2.2f),
                        blurRadius = 10f,
                        colors = barBlurColors(progressive = true),
                    ),
            )
        }
        content()
    }
}

@Composable
private fun barBlurColors(progressive: Boolean = false): BlurColors = BlurDefaults.blurColors(
    blendColors = listOf(
        BlendColorEntry(color = ElyonTheme.colorScheme.surface.copy(if (progressive) 0.3f else 0.8f)),
    ),
)

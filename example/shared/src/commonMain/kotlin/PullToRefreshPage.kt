// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

@file:OptIn(ExperimentalScrollBarApi::class)

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import component.BackNavigationIcon
import kotlinx.coroutines.delay
import io.elyon.kmp.basic.Card
import io.elyon.kmp.basic.CardDefaults
import io.elyon.kmp.basic.Icon
import io.elyon.kmp.basic.IconButton
import io.elyon.kmp.basic.ElyonScrollBehavior
import io.elyon.kmp.basic.PullToRefresh
import io.elyon.kmp.basic.Scaffold
import io.elyon.kmp.basic.Text
import io.elyon.kmp.basic.VerticalScrollBar
import io.elyon.kmp.basic.rememberPullToRefreshState
import io.elyon.kmp.basic.rememberScrollBarAdapter
import io.elyon.kmp.blur.layerBackdrop
import io.elyon.kmp.icon.ElyonIcons
import io.elyon.kmp.icon.extended.Refresh
import io.elyon.kmp.interfaces.ExperimentalScrollBarApi
import io.elyon.kmp.preference.OverlayDropdownPreference
import io.elyon.kmp.preference.SliderPreference
import io.elyon.kmp.preference.WindowDropdownPreference
import io.elyon.kmp.squircle.squircleClip
import io.elyon.kmp.theme.ElyonTheme
import io.elyon.kmp.utils.PressFeedbackType
import io.elyon.kmp.window.WindowBottomSheet
import utils.AdaptiveTopAppBar
import utils.BlurredBar
import utils.pageContentPadding
import utils.pageScrollModifiers
import utils.rememberBlurBackdrop
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun PullToRefreshPage(
    padding: PaddingValues,
) {
    val navigator = LocalNavigator.current
    val appState = LocalAppState.current
    val isWideScreen = LocalIsWideScreen.current
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    var thresholdValue by remember { mutableFloatStateOf(0.25f) }
    var currentPullProgress by remember { mutableFloatStateOf(0f) }
    val pullToRefreshState = rememberPullToRefreshState(
        refreshThreshold = thresholdValue,
    )
    val topAppBarScrollBehavior = ElyonScrollBehavior()

    val backdrop = rememberBlurBackdrop()
    val blurActive = backdrop != null
    val barColor = if (blurActive) Color.Transparent else ElyonTheme.colorScheme.surface

    val dropdownOptions = remember { listOf("Option 1", "Option 2", "Option 3", "Option 4") }
    var dropdownSelectedOption by remember { mutableIntStateOf(0) }
    var dropdownCount by remember { mutableIntStateOf(6) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(100.milliseconds)
            dropdownCount += 6
            isRefreshing = false
        }
    }

    Scaffold(
        topBar = {
            BlurredBar(backdrop, blurActive, topAppBarScrollBehavior) {
                AdaptiveTopAppBar(
                    title = "Popup",
                    showTopAppBar = appState.showTopAppBar,
                    isWideScreen = isWideScreen,
                    scrollBehavior = topAppBarScrollBehavior,
                    color = barColor,
                    navigationIcon = {
                        BackNavigationIcon(
                            onClick = { navigator.pop() },
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = { isRefreshing = true },
                        ) {
                            Icon(
                                imageVector = ElyonIcons.Refresh,
                                contentDescription = "Refresh",
                                tint = ElyonTheme.colorScheme.onBackground,
                            )
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        val contentPadding = pageContentPadding(
            innerPadding,
            padding,
            true,
            extraTop = 12.dp,
            extraStart = if (isWideScreen) 0.dp else WindowInsets.displayCutout.asPaddingValues().calculateLeftPadding(LayoutDirection.Ltr),
            extraEnd = WindowInsets.displayCutout.asPaddingValues().calculateRightPadding(LayoutDirection.Ltr),
            extraBottom = 12.dp,
        )
        Box(modifier = if (backdrop != null) Modifier.layerBackdrop(backdrop) else Modifier) {
            PullToRefresh(
                isRefreshing = isRefreshing,
                onRefresh = { isRefreshing = true },
                pullToRefreshState = pullToRefreshState,
                topAppBarScrollBehavior = if (appState.showTopAppBar) topAppBarScrollBehavior else null,
                contentPadding = contentPadding,
                onPullProgress = { currentPullProgress = it },
            ) {
                val lazyListState = rememberLazyListState()
                Box {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.pageScrollModifiers(
                            appState.enableScrollEndHaptic,
                            appState.showTopAppBar,
                            topAppBarScrollBehavior,
                        ),
                        contentPadding = contentPadding,
                    ) {
                        item(key = "progress_card") {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                cornerRadius = 12.dp,
                                insideMargin = PaddingValues(16.dp),
                                colors = CardDefaults.defaultColors(
                                    color = ElyonTheme.colorScheme.surfaceContainer,
                                ),
                                pressFeedbackType = PressFeedbackType.Sink,
                                showIndication = true,
                                onClick = { showSettings = true },
                            ) {
                                Text(
                                    text = "Pull Progress: ${(currentPullProgress * 100).toInt()}%",
                                    style = ElyonTheme.textStyles.body1,
                                    color = ElyonTheme.colorScheme.onSurface,
                                )
                                Text(
                                    text = "Threshold: ${(thresholdValue * 100).toInt()}%",
                                    style = ElyonTheme.textStyles.body2,
                                    color = ElyonTheme.colorScheme.onSurfaceVariantSummary,
                                )
                            }
                        }
                        items(
                            count = dropdownCount,
                            key = { "dropdown_$it" },
                        ) { i ->
                            val isFirst = i == 0
                            val isLast = i == dropdownCount - 1
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp)
                                    .squircleClip(
                                        topStart = if (isFirst) 16.dp else 0.dp,
                                        topEnd = if (isFirst) 16.dp else 0.dp,
                                        bottomEnd = if (isLast) 16.dp else 0.dp,
                                        bottomStart = if (isLast) 16.dp else 0.dp,
                                    )
                                    .background(ElyonTheme.colorScheme.surfaceContainer),
                            ) {
                                if (i % 2 == 0) {
                                    OverlayDropdownPreference(
                                        title = "OverlayDropdownPref ${i + 1}",
                                        items = dropdownOptions,
                                        selectedIndex = dropdownSelectedOption,
                                        onSelectedIndexChange = { newOption ->
                                            dropdownSelectedOption = newOption
                                        },
                                    )
                                } else {
                                    WindowDropdownPreference(
                                        title = "WindowDropdownPref ${i + 1}",
                                        items = dropdownOptions,
                                        selectedIndex = dropdownSelectedOption,
                                        onSelectedIndexChange = { newOption ->
                                            dropdownSelectedOption = newOption
                                        },
                                    )
                                }
                            }
                        }
                    }
                    VerticalScrollBar(
                        adapter = rememberScrollBarAdapter(lazyListState),
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                        trackPadding = contentPadding,
                    )
                }
            }
        }
    }

    WindowBottomSheet(
        title = "PullToRefresh Settings",
        show = showSettings,
        onDismissRequest = { showSettings = false },
    ) {
        Card(
            insideMargin = PaddingValues(),
            colors = CardDefaults.defaultColors(
                color = ElyonTheme.colorScheme.secondaryContainer,
            ),
        ) {
            SliderPreference(
                title = "Refresh Threshold",
                summary = if (thresholdValue == 0f) {
                    "Any pull triggers refresh."
                } else {
                    "Pull ${(thresholdValue * 100).toInt()}% of the drag range to refresh."
                },
                value = thresholdValue,
                onValueChange = { thresholdValue = it },
                valueRange = 0f..1f,
                steps = 99,
                showKeyPoints = true,
                keyPoints = listOf(0f, 0.25f, 0.5f, 0.75f, 1f),
            )
        }
        Spacer(
            Modifier.padding(
                bottom = 12.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() +
                    WindowInsets.captionBar.asPaddingValues().calculateBottomPadding(),
            ),
        )
    }
}

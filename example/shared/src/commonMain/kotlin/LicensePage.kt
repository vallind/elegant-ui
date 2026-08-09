// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

@file:OptIn(ExperimentalScrollBarApi::class)

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import component.BackNavigationIcon
import kotlinx.coroutines.CancellationException
import io.elyon.kmp.basic.Card
import io.elyon.kmp.basic.ElyonScrollBehavior
import io.elyon.kmp.basic.Scaffold
import io.elyon.kmp.basic.VerticalScrollBar
import io.elyon.kmp.basic.rememberScrollBarAdapter
import io.elyon.kmp.blur.isRuntimeShaderSupported
import io.elyon.kmp.blur.layerBackdrop
import io.elyon.kmp.blur.rememberLayerBackdrop
import io.elyon.kmp.interfaces.ExperimentalScrollBarApi
import io.elyon.kmp.preference.ArrowPreference
import io.elyon.kmp.shared.generated.resources.Res
import io.elyon.kmp.theme.ElyonTheme
import utils.AdaptiveTopAppBar
import utils.BlurredBar
import utils.Library
import utils.SimpleJsonParser
import utils.pageContentPadding
import utils.pageScrollModifiers

@Composable
fun LicensePage(
    padding: PaddingValues,
) {
    val appState = LocalAppState.current
    val isWideScreen = LocalIsWideScreen.current
    val blurSupported = isRuntimeShaderSupported()
    val surfaceColor = ElyonTheme.colorScheme.surface
    val backdrop = if (blurSupported) {
        rememberLayerBackdrop {
            drawRect(surfaceColor)
            drawContent()
        }
    } else {
        null
    }
    val blurActive = appState.enableBlur && blurSupported
    val barColor = if (blurActive) Color.Transparent else ElyonTheme.colorScheme.surface
    val topAppBarScrollBehavior = ElyonScrollBehavior()
    val navigator = LocalNavigator.current

    val libraries by produceState<List<Library>?>(initialValue = null) {
        try {
            val jsonString = Res.readBytes("files/aboutlibraries.json").decodeToString()
            val libs = SimpleJsonParser(jsonString).parseLibs()
            value = libs.libraries
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Scaffold(
        topBar = {
            BlurredBar(backdrop, blurActive, topAppBarScrollBehavior) {
                AdaptiveTopAppBar(
                    title = "Third Party Licenses",
                    showTopAppBar = appState.showTopAppBar,
                    isWideScreen = isWideScreen,
                    scrollBehavior = topAppBarScrollBehavior,
                    color = barColor,
                    navigationIcon = {
                        BackNavigationIcon(
                            onClick = { navigator.pop() },
                        )
                    },
                )
            }
        },
    ) { innerPadding ->
        val uriHandler = LocalUriHandler.current
        val lazyListState = rememberLazyListState()
        val contentPadding = pageContentPadding(
            innerPadding,
            padding,
            isWideScreen,
            extraStart = if (isWideScreen) 0.dp else WindowInsets.displayCutout.asPaddingValues().calculateLeftPadding(LayoutDirection.Ltr),
            extraEnd = WindowInsets.displayCutout.asPaddingValues().calculateRightPadding(LayoutDirection.Ltr),
        )
        Box(modifier = if (backdrop != null) Modifier.layerBackdrop(backdrop) else Modifier) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.pageScrollModifiers(
                    appState.enableScrollEndHaptic,
                    appState.showTopAppBar,
                    topAppBarScrollBehavior,
                ),
                contentPadding = contentPadding,
            ) {
                libraries?.let { libs ->
                    items(libs, key = { it.uniqueId }) { library ->
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(top = 12.dp),
                        ) {
                            ArrowPreference(
                                title = library.name,
                                summary = "${library.artifactVersion}, ${library.licenses.firstOrNull()}",
                                onClick = {
                                    library.website?.let {
                                        uriHandler.openUri(library.website)
                                    }
                                },
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))
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

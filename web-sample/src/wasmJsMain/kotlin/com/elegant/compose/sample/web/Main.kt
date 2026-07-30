package com.elegant.compose.sample.web

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.elegant.compose.showcase.ElegantShowcaseApp
import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
private fun requestedComponentId(): String =
    js("new URLSearchParams(window.location.search).get('id') || 'button'")

@OptIn(ExperimentalComposeUiApi::class)
public fun main() {
    ComposeViewport(viewportContainerId = "webApp") {
        ElegantShowcaseApp(componentId = requestedComponentId())
    }
}

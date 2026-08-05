package com.elegant.compose.sample.web

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.elegant.compose.showcase.ElegantShowcaseApp
import com.elegant.compose.showcase.ElegantShowcaseBrowser
import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
private fun requestedComponentId(): String? =
    js("new URLSearchParams(window.location.search).get('id')")

@OptIn(ExperimentalComposeUiApi::class)
public fun main() {
    ComposeViewport(viewportContainerId = "webApp") {
        val componentId = requestedComponentId()
        if (componentId == null) {
            ElegantShowcaseBrowser()
        } else {
            ElegantShowcaseApp(componentId = componentId)
        }
    }
}

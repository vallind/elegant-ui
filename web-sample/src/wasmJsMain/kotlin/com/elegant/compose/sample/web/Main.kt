package com.elegant.compose.sample.web

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.elegant.compose.example.ExampleApp
import com.elegant.compose.showcase.ElegantShowcaseApp
import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
private fun requestedComponentId(): String? =
    js("new URLSearchParams(window.location.search).get('id')")

@OptIn(ExperimentalComposeUiApi::class)
public fun main() {
    ComposeViewport(viewportContainerId = "webApp") {
        val componentId = requestedComponentId()
        if (componentId == null) {
            ExampleApp()
        } else {
            ElegantShowcaseApp(componentId = componentId)
        }
    }
}

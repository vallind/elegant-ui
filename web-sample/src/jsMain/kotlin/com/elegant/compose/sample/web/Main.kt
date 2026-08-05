package com.elegant.compose.sample.web

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.elegant.compose.showcase.ElegantShowcaseApp
import com.elegant.compose.showcase.ElegantShowcaseBrowser
import kotlin.js.ExperimentalJsExport
import kotlin.js.Json
import kotlin.js.json
import kotlin.js.unsafeCast

private fun requestedComponentId(): String? {
    val query: String = js("window.location.search")
    val params: Json = js("new URLSearchParams(query)")
    return params.get("id").unsafeCast<String?>()
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalJsExport::class)
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

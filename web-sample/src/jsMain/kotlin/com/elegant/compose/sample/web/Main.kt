package com.elegant.compose.sample.web

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.elegant.compose.showcase.ElegantShowcaseApp
import kotlin.js.ExperimentalJsExport
import kotlin.js.Json
import kotlin.js.json
import kotlin.js.unsafeCast

private fun requestedComponentId(): String {
    val query: String = js("window.location.search")
    val params: Json = js("new URLSearchParams(query)")
    val id: String = params.get("id").unsafeCast<String?>() ?: "button"
    return id
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalJsExport::class)
public fun main() {
    ComposeViewport(viewportContainerId = "webApp") {
        ElegantShowcaseApp(componentId = requestedComponentId())
    }
}

package com.elegant.compose.sample.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.elegant.compose.showcase.ElegantShowcaseBrowser

public fun main(): Unit = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Elegant UI Showcase",
    ) {
        ElegantShowcaseBrowser()
    }
}

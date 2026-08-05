package com.elegant.compose.sample.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.elegant.compose.example.ExampleApp

public fun main(): Unit = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Elegant UI",
    ) {
        ExampleApp()
    }
}

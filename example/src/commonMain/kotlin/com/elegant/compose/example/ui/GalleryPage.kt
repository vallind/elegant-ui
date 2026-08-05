// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.runtime.Composable
import com.elegant.compose.showcase.ElegantShowcaseBrowser

/**
 * Gallery tab of the example app: embeds the full showcase browser (every component demo with its
 * tag selector) as the showcase entry promised by the app's home page.
 *
 * @param onBack callback popping this page from the back stack.
 */
@Composable
internal fun GalleryPage(onBack: () -> Unit) {
    ScenePage(title = "Component Gallery", onBack = onBack) {
        ElegantShowcaseBrowser()
    }
}

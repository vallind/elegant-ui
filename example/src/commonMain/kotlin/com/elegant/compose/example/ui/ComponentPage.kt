// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.elegant.compose.showcase.ElegantShowcaseApp

/**
 * Detail page of one component: hosts the shared showcase page for [slug] under the scene chrome,
 * so every showcase component stays reachable inside the example app.
 *
 * @param slug showcase component id (see [com.elegant.compose.showcase.ElegantShowcaseIds]).
 * @param onBack callback popping this page from the back stack.
 */
@Composable
internal fun ComponentPage(
    slug: String,
    onBack: () -> Unit,
) {
    ScenePage(title = slug, onBack = onBack) {
        Box(modifier = Modifier.fillMaxSize()) {
            ElegantShowcaseApp(componentId = slug)
        }
    }
}

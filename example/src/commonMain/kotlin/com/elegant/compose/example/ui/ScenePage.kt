// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.elegant.compose.ui.foundation.icons.ElegantIcons
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.iconbutton.ElegantIconButton
import com.elegant.compose.ui.navbar.ElegantNavbar

/**
 * Shared chrome of the pushed example pages: an [ElegantNavbar] with a back action and a title,
 * above the page content. The title style is caller-agnostic (the bar owns no text style), so the
 * page title always uses [ElegantTheme.typography.titleMedium].
 *
 * @param title page title rendered in the bar.
 * @param onBack callback popping this page from the back stack.
 * @param content page body rendered below the bar.
 */
@Composable
internal fun ScenePage(
    title: String,
    onBack: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ElegantNavbar(
            navigationIcon = {
                ElegantIconButton(onClick = onBack, contentDescription = "Back") {
                    Icon(imageVector = ElegantIcons.ArrowLeft, contentDescription = null)
                }
            },
            title = { Text(text = title, style = ElegantTheme.typography.titleMedium) },
        )
        content()
    }
}

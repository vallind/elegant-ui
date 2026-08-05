// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Entry point of the complete app example.
 *
 * Owns application-level state (currently the dark theme preference, persisted per platform
 * through [rememberSaveable]), provides it through [LocalAppState] / [LocalAppStateUpdater], and
 * renders the adaptive [AppContent] shell inside [ElegantTheme].
 */
@Composable
public fun ExampleApp() {
    var darkTheme by rememberSaveable { mutableStateOf(false) }
    val appState = AppState(darkTheme = darkTheme)
    ElegantTheme(darkTheme = darkTheme) {
        CompositionLocalProvider(
            LocalAppState provides appState,
            LocalAppStateUpdater provides { updated -> darkTheme = updated.darkTheme },
        ) {
            AppContent()
        }
    }
}

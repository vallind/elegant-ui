// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Immutable snapshot of the example app's application-level state.
 *
 * Held in [LocalAppState] and updated through [LocalAppStateUpdater] so any page can read and
 * change app-level preferences without threading callbacks through every screen.
 *
 * @property darkTheme whether the app renders in the dark color scheme.
 */
@Immutable
public data class AppState(
    val darkTheme: Boolean = false,
)

/** Current [AppState] for pages that need to read app-level state. */
public val LocalAppState: androidx.compose.runtime.ProvidableCompositionLocal<AppState> =
    staticCompositionLocalOf { AppState() }

/** Callback that publishes a new [AppState]; the app root is the only writer. */
public val LocalAppStateUpdater: androidx.compose.runtime.ProvidableCompositionLocal<(AppState) -> Unit> =
    staticCompositionLocalOf<(AppState) -> Unit> { {} }

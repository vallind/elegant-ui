// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

package io.elyon.kmp.theme

import androidx.compose.runtime.staticCompositionLocalOf
import io.elyon.kmp.overlay.OverlayBottomSheet
import io.elyon.kmp.overlay.OverlayDialog
import io.elyon.kmp.overlay.OverlayListPopup
import io.elyon.kmp.window.WindowBottomSheet
import io.elyon.kmp.window.WindowDialog
import io.elyon.kmp.window.WindowListPopup

/**
 * CompositionLocal that provides a dismiss request function for overlay components.
 *
 * This is automatically provided by all overlay components ([OverlayDialog], [WindowDialog],
 * [OverlayBottomSheet], [WindowBottomSheet], [OverlayListPopup], [WindowListPopup]).
 *
 * Call the provided function to request dismissal from inside overlay content:
 * ```kotlin
 * val dismiss = LocalDismissState.current
 * Button(onClick = { dismiss?.invoke() }) { Text("Close") }
 * ```
 */
val LocalDismissState = staticCompositionLocalOf<(() -> Unit)?> { null }

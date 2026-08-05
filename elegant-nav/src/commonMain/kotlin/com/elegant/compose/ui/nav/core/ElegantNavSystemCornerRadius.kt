// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

/**
 * The device screen's corner radius, intended for [ElegantNavDisplayEffects.cornerClipRadius] so a
 * full-window navigation entry is clipped to match the rounded screen corner as it slides in.
 *
 * Android reads the [android.view.RoundedCorner] insets API (31+, bottom-left position), falling
 * back to the framework corner dimen, then to `0.dp` for flat-corner screens. Skiko targets
 * (Desktop / iOS / macOS / Web) return `0.dp`, since there is no OS-level screen corner to match.
 */
@Composable
expect fun rememberElegantNavSystemCornerRadius(): Dp

// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Skiko targets have no OS-level screen corner to match, so no rounding is applied. */
@Composable
actual fun rememberElegantNavSystemCornerRadius(): Dp = 0.dp

// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.blur.shader

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

@SuppressLint("ObsoleteSdkInt")
@ChecksSdkIntAtLeast(Build.VERSION_CODES.S)
actual fun isRenderEffectSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

@file:Suppress("FunctionName")

package com.elegant.compose.ui.blur

import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import com.elegant.compose.ui.blur.shader.asBrush as coreAsBrush
import com.elegant.compose.ui.blur.shader.asComposeShader as coreAsComposeShader
import com.elegant.compose.ui.blur.shader.isRuntimeShaderSupported as coreIsRuntimeShaderSupported

/** Back-compat re-export. New code should use `com.elegant.compose.ui.blur.shader.RuntimeShader`. */
typealias RuntimeShader = com.elegant.compose.ui.blur.shader.RuntimeShader

/**
 * Back-compat re-export.
 *
 * @param shaderString The AGSL/SkSL shader source code to compile into the [RuntimeShader].
 */
fun RuntimeShader(shaderString: String): RuntimeShader = com.elegant.compose.ui.blur.shader.RuntimeShader(shaderString)

/** Back-compat re-export. */
fun RuntimeShader.asComposeShader(): Shader = coreAsComposeShader()

/** Back-compat re-export. */
fun RuntimeShader.asBrush(): ShaderBrush = coreAsBrush()

/** Back-compat re-export. */
fun isRuntimeShaderSupported(): Boolean = coreIsRuntimeShaderSupported()

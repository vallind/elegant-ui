// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.blur

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized

/**
 * Cache for compiled [RuntimeShader] instances, avoiding recompilation each frame.
 */
sealed interface ElegantRuntimeShaderCache {

    /**
     * Returns a cached [RuntimeShader] for the given [key], compiling from [string] if not yet cached.
     *
     * @param key The cache key identifying the compiled [RuntimeShader].
     * @param string The AGSL/SkSL shader source compiled on a cache miss.
     */
    fun obtainRuntimeShader(key: String, string: String): RuntimeShader
}

/**
 * CompositionLocal providing a shared [ElegantRuntimeShaderCache] within the composition tree.
 *
 * All backdrop effect scopes within the same composition share this cache,
 * avoiding redundant shader compilation while allowing the cache to be
 * garbage-collected when the composition is disposed.
 */
val LocalElegantRuntimeShaderCache = staticCompositionLocalOf<ElegantRuntimeShaderCache> {
    ElegantRuntimeShaderCacheImpl()
}

@OptIn(InternalCoroutinesApi::class)
internal class ElegantRuntimeShaderCacheImpl : ElegantRuntimeShaderCache {

    private val lock = SynchronizedObject()
    private val runtimeShaders = mutableMapOf<String, RuntimeShader>()

    override fun obtainRuntimeShader(key: String, string: String): RuntimeShader = synchronized(lock) {
        runtimeShaders.getOrPut(key) { RuntimeShader(string) }
    }
}

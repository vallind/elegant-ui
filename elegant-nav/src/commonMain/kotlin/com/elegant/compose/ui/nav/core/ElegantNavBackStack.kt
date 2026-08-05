// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * The navigation back stack: a [SnapshotStateList] of [ElegantNavKey].
 *
 * This is the public type name; callers may operate the list directly (add/removeAt/...) or via a
 * [ElegantNavController]. Persistence across configuration changes and process death is provided by
 * [rememberElegantNavBackStack].
 */
public typealias ElegantNavBackStack = SnapshotStateList<ElegantNavKey>

/**
 * Non-composable constructor for a [ElegantNavBackStack], primarily for tests and off-composition setup.
 */
public fun elegantNavBackStackOf(vararg elements: ElegantNavKey): ElegantNavBackStack = elements.toList().toMutableStateList()

/** Json used to (de)serialize the back stack. Keys must be `@Serializable`. */
@PublishedApi
internal val ElegantNavBackStackJson: Json = Json { ignoreUnknownKeys = true }

/**
 * Builds a reflection-free [Saver] for a back stack whose keys are all of type [T].
 *
 * [elementsSerializer] is the compiler-generated serializer for `List<T>`, obtained at the call
 * site via the reified `serializer<List<T>>()`. For a `@Serializable sealed` route hierarchy this
 * is a closed-polymorphic serializer that needs no `SerializersModule` and works on every target
 * (no JVM reflection, no `InternalSerializationApi`).
 *
 * Non-`@Serializable` keys surface a `SerializationException` rather than silently dropping data
 * (design spec §12 known risk).
 */
@PublishedApi
internal fun <T : ElegantNavKey> elegantNavBackStackSaver(elementsSerializer: KSerializer<List<T>>): Saver<ElegantNavBackStack, String> = Saver(
    save = { stack ->
        @Suppress("UNCHECKED_CAST")
        ElegantNavBackStackJson.encodeToString(elementsSerializer, stack.toList() as List<T>)
    },
    restore = { encoded ->
        val decoded: List<ElegantNavKey> = ElegantNavBackStackJson.decodeFromString(elementsSerializer, encoded)
        decoded.toMutableStateList()
    },
)

/**
 * Remembers a [ElegantNavBackStack] seeded with [elements], persisted via [rememberSaveable].
 *
 * The key type [T] is captured reflection-free so persistence works on every target. When seeding
 * with a single concrete key, pass the route supertype explicitly so the whole hierarchy can be
 * encoded, e.g. `rememberElegantNavBackStack<Route>(Route.Home)`.
 *
 * ```kotlin
 * val backStack = rememberElegantNavBackStack<Route>(Route.Home)
 * ```
 */
@Composable
public inline fun <reified T : ElegantNavKey> rememberElegantNavBackStack(vararg elements: T): ElegantNavBackStack {
    // T is fixed by the reified call site, so the serializer never changes -> keyless remember.
    val saver = remember { elegantNavBackStackSaver(serializer<List<T>>()) }
    return rememberSaveable(saver = saver) {
        val seed: List<ElegantNavKey> = elements.toList()
        seed.toMutableStateList()
    }
}

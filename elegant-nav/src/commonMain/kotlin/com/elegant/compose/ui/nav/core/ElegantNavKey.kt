// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.core

/**
 * Marker interface for navigation destination keys.
 *
 * A [ElegantNavKey] is a pure tag carrying no behavior. User route hierarchies implement it and should
 * annotate the hierarchy with `@Serializable` (kotlinx.serialization) so the back stack can be
 * persisted across configuration changes and process death via [rememberElegantNavBackStack].
 *
 * ```kotlin
 * @Serializable
 * sealed interface Route : ElegantNavKey {
 *     @Serializable data object Home : Route
 *     @Serializable data class Detail(val id: String) : Route
 * }
 * ```
 *
 * `@Serializable` is a hard requirement when the stack is persisted via [rememberElegantNavBackStack]: a
 * non-serializable key type throws `SerializationException` at the first composition (serializer
 * capture), and a key escaping the captured hierarchy throws at state-save time. Stacks built with
 * [elegantNavBackStackOf] are in-memory only and place no serializability requirement on keys.
 */
public interface ElegantNavKey

// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example

import com.elegant.compose.ui.nav.core.ElegantNavBackStack
import com.elegant.compose.ui.nav.core.ElegantNavKey

/**
 * Pure back-stack operations shared by the app shell and the scene pages.
 *
 * All helpers are deliberately side-effect free on anything but the passed [ElegantNavBackStack],
 * which keeps them unit-testable in `commonTest` without a UI harness.
 */

/** Pushes [route] unless it is already the top entry, keeping scene entries unique on top. */
internal fun pushIdempotent(backStack: ElegantNavBackStack, route: ElegantNavKey) {
    if (backStack.lastOrNull() != route) {
        backStack.add(route)
    }
}

/** Pops the top entry when the stack holds more than one route. */
internal fun popBackStack(backStack: ElegantNavBackStack) {
    if (backStack.size > 1) {
        backStack.removeAt(backStack.lastIndex)
    }
}

/**
 * Resolves a tab destination: when [route] already sits somewhere on the stack, pops everything
 * above it (so the tab surfaces without duplicating state); otherwise pushes it.
 */
internal fun navigateToTab(backStack: ElegantNavBackStack, route: ElegantNavKey) {
    val index = backStack.indexOf(route)
    if (index >= 0) {
        while (backStack.size > index + 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    } else {
        backStack.add(route)
    }
}

/** Replaces the top entry with [route] without changing the stack depth. */
internal fun replaceTop(backStack: ElegantNavBackStack, route: ElegantNavKey) {
    if (backStack.isNotEmpty()) {
        backStack[backStack.lastIndex] = route
    } else {
        backStack.add(route)
    }
}

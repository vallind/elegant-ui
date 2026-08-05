// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example

import com.elegant.compose.showcase.ElegantShowcaseIds
import com.elegant.compose.ui.nav.core.ElegantNavKey
import com.elegant.compose.ui.nav.core.elegantNavBackStackOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ScenesTest {

    @Test
    fun sceneSlugsCoverEveryShowcaseId() {
        assertEquals(
            ElegantShowcaseIds,
            allSceneSlugs,
            "Every showcase id must be demonstrated by exactly one scene; missing ids mean a " +
                "scene list or the scene catalog is out of sync with the showcase registry.",
        )
    }

    @Test
    fun everySceneDemonstratesAtLeastOneComponent() {
        assertTrue(ExampleScenes.isNotEmpty())
        ExampleScenes.forEach { scene ->
            assertTrue(
                scene.slugs.isNotEmpty(),
                "Scene ${scene.title} must demonstrate at least one component",
            )
        }
    }

    @Test
    fun filterComponentIdsReturnsSortedMatches() {
        val ids = setOf("button", "icon-button", "badge", "button-group")
        assertEquals(listOf("badge"), filterComponentIds(ids, "badge"))
        assertEquals(listOf("badge", "button", "button-group", "icon-button"), filterComponentIds(ids, ""))
        assertEquals(listOf("icon-button"), filterComponentIds(ids, "ICON"))
        assertEquals(emptyList(), filterComponentIds(ids, "zzz"))
    }

    @Test
    fun routeLabelsAreUniqueAndNonBlank() {
        val routes = listOf(
            ExampleRoute.Home,
            ExampleRoute.Inputs,
            ExampleRoute.Buttons,
            ExampleRoute.Display,
            ExampleRoute.Feedback,
            ExampleRoute.Navigation,
            ExampleRoute.Overlays,
            ExampleRoute.Settings,
            ExampleRoute.Gallery,
            ExampleRoute.Component("button"),
        )
        val labels = routes.map { it.label() }
        labels.forEach { assertTrue(it.isNotBlank()) }
        assertEquals(labels.distinct().size, labels.size, "Route labels must be distinct")
        assertNotEquals(ExampleRoute.Inputs.label(), ExampleRoute.Buttons.label())
    }

    @Test
    fun selectedTabIndexFindsDeepestTabOnTheStack() {
        assertEquals(0, selectedTabIndex(elegantNavBackStackOf(ExampleRoute.Home), ExampleTabs))
        assertEquals(
            1,
            selectedTabIndex(
                elegantNavBackStackOf(ExampleRoute.Home, ExampleRoute.Inputs),
                ExampleTabs,
            ),
        )
        val stack = elegantNavBackStackOf(
            ExampleRoute.Home,
            ExampleRoute.Inputs,
            ExampleRoute.Component("button"),
        )
        assertEquals(1, selectedTabIndex(stack, ExampleTabs))
        assertEquals(4, selectedTabIndex(elegantNavBackStackOf(ExampleRoute.Settings), ExampleTabs))
    }

    @Test
    fun tabDestinationsAreValidRoutes() {
        ExampleTabs.forEach { route ->
            assertTrue(route in ExampleScenes.map { it.route } || route == ExampleRoute.Home || route == ExampleRoute.Gallery)
        }
    }

    @Test
    fun pushIdempotentKeepsStackUniqueAtTheTop() {
        val stack = elegantNavBackStackOf(ExampleRoute.Home)
        pushIdempotent(stack, ExampleRoute.Inputs)
        pushIdempotent(stack, ExampleRoute.Inputs)
        assertEquals(listOf<ElegantNavKey>(ExampleRoute.Home, ExampleRoute.Inputs), stack.toList())
    }

    @Test
    fun navigateToTabPopsBackToAnExistingTab() {
        val stack = elegantNavBackStackOf(
            ExampleRoute.Home,
            ExampleRoute.Inputs,
            ExampleRoute.Component("button"),
        )
        navigateToTab(stack, ExampleRoute.Home)
        assertEquals(listOf<ElegantNavKey>(ExampleRoute.Home), stack.toList())
    }

    @Test
    fun navigateToTabPushesANewTab() {
        val stack = elegantNavBackStackOf(ExampleRoute.Home)
        navigateToTab(stack, ExampleRoute.Settings)
        assertEquals(listOf<ElegantNavKey>(ExampleRoute.Home, ExampleRoute.Settings), stack.toList())
    }

    @Test
    fun replaceTopKeepsStackDepth() {
        val stack = elegantNavBackStackOf(ExampleRoute.Home, ExampleRoute.Inputs)
        replaceTop(stack, ExampleRoute.Display)
        assertEquals(listOf<ElegantNavKey>(ExampleRoute.Home, ExampleRoute.Display), stack.toList())
    }

    @Test
    fun popBackStackKeepsTheRoot() {
        val stack = elegantNavBackStackOf(ExampleRoute.Home)
        popBackStack(stack)
        assertEquals(listOf<ElegantNavKey>(ExampleRoute.Home), stack.toList())
        pushIdempotent(stack, ExampleRoute.Overlays)
        popBackStack(stack)
        assertEquals(listOf<ElegantNavKey>(ExampleRoute.Home), stack.toList())
    }
}

// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example

import com.elegant.compose.ui.nav.core.ElegantNavKey
import kotlinx.serialization.Serializable

/**
 * Routes of the complete app example.
 *
 * Every route is an [ElegantNavKey] so the single back stack can drive
 * [com.elegant.compose.ui.nav.core.ElegantNavDisplay]. The scene routes are the hand-written
 * scenario pages; [Route.Component] hosts the shared showcase page of one component and
 * [Route.Gallery] embeds the full showcase browser.
 */
@Serializable
public sealed interface ExampleRoute : ElegantNavKey {
    /** Landing page with search, scene entries, and the full component list. */
    @Serializable
    public data object Home : ExampleRoute

    /** Form and control inputs scene. */
    @Serializable
    public data object Inputs : ExampleRoute

    /** Action surfaces scene. */
    @Serializable
    public data object Buttons : ExampleRoute

    /** Information display scene. */
    @Serializable
    public data object Display : ExampleRoute

    /** Operation feedback scene. */
    @Serializable
    public data object Feedback : ExampleRoute

    /** Navigation scene. */
    @Serializable
    public data object Navigation : ExampleRoute

    /** Overlay and popup scene. */
    @Serializable
    public data object Overlays : ExampleRoute

    /** Preferences scene. */
    @Serializable
    public data object Settings : ExampleRoute

    /** Full showcase browser embedded in the example. */
    @Serializable
    public data object Gallery : ExampleRoute

    /** Showcase page of one component, identified by its showcase slug. */
    @Serializable
    public data class Component(val slug: String) : ExampleRoute
}

/**
 * Display label of the route used by tabs, scene cards, and page headers.
 */
internal fun ExampleRoute.label(): String = when (this) {
    ExampleRoute.Home -> "Home"
    ExampleRoute.Inputs -> "Inputs"
    ExampleRoute.Buttons -> "Buttons"
    ExampleRoute.Display -> "Display"
    ExampleRoute.Feedback -> "Feedback"
    ExampleRoute.Navigation -> "Navigation"
    ExampleRoute.Overlays -> "Overlays"
    ExampleRoute.Settings -> "Settings"
    ExampleRoute.Gallery -> "Gallery"
    is ExampleRoute.Component -> "Component"
}

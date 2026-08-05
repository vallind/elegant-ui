// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example

/**
 * Catalog entry of one hand-written scene page.
 *
 * @property route route opened by the scene card.
 * @property title card title.
 * @property description one-line summary shown under the title.
 * @property slugs showcase slugs of the components the scene demonstrates.
 */
internal data class Scene(
    val route: ExampleRoute,
    val title: String,
    val description: String,
    val slugs: List<String>,
)

/**
 * The hand-written scene pages and the components each one demonstrates. Together they cover every
 * id in [com.elegant.compose.showcase.ElegantShowcaseIds]; the home page additionally reaches any
 * id through its component detail list.
 */
internal val ExampleScenes: List<Scene> = listOf(
    Scene(
        route = ExampleRoute.Inputs,
        title = "Inputs",
        description = "Form fields, selection controls, and pickers",
        slugs = listOf(
            "input", "textarea", "number-field", "input-otp", "autocomplete", "select",
            "checkbox", "checkbox-group", "radio", "radio-group", "switch", "switch-group",
            "slider", "number-picker", "color-picker", "color-picker-panel",
            "calendar", "date-picker", "date-range-picker", "fieldset", "input-group",
            "search-bar", "label",
        ),
    ),
    Scene(
        route = ExampleRoute.Buttons,
        title = "Buttons",
        description = "Action surfaces and toolbars",
        slugs = listOf(
            "button", "button-group", "icon-button", "close-button", "toggle-button",
            "floating-action-button", "toolbar", "floating-toolbar", "tag-group", "tag", "link",
        ),
    ),
    Scene(
        route = ExampleRoute.Display,
        title = "Display",
        description = "Content, data, and status presentation",
        slugs = listOf(
            "avatar", "badge", "divider", "card", "list", "table", "description", "meter",
            "kbd", "small-title", "basic-component", "surface", "skeleton",
            "progress-indicator", "spinner", "empty-state", "accordion", "disclosure",
            "icons", "squircle", "blur",
        ),
    ),
    Scene(
        route = ExampleRoute.Feedback,
        title = "Feedback",
        description = "Alerts, dialogs, and transient messages",
        slugs = listOf(
            "alert", "alert-dialog", "toast", "snackbar", "tooltip",
        ),
    ),
    Scene(
        route = ExampleRoute.Navigation,
        title = "Navigation",
        description = "Tabs, bars, and screen transitions",
        slugs = listOf(
            "tabs", "breadcrumb", "pagination", "navbar", "sidebar", "navigation-bar",
            "navigation-rail", "navigation", "pull-to-refresh", "scroll-bar", "scroll-shadow",
            "scaffold",
        ),
    ),
    Scene(
        route = ExampleRoute.Overlays,
        title = "Overlays",
        description = "Modals, sheets, drawers, and popups",
        slugs = listOf(
            "modal", "bottom-sheet", "drawer", "popover", "menu", "list-popup",
            "cascading-menu",
        ),
    ),
    Scene(
        route = ExampleRoute.Settings,
        title = "Settings",
        description = "Preferences and theme control",
        slugs = listOf(
            "switch-preference", "checkbox-preference", "radio-preference",
            "slider-preference", "arrow-preference", "theme-controller",
        ),
    ),
)

/** Every showcase slug demonstrated by at least one [ExampleScenes] page. */
internal val allSceneSlugs: Set<String> = ExampleScenes.flatMap { it.slugs }.toSet()

/**
 * Filters [ids] by [query] as a case-insensitive substring match and returns the matches sorted;
 * a blank query returns the whole set.
 */
internal fun filterComponentIds(ids: Set<String>, query: String): List<String> {
    val normalized = query.trim().lowercase()
    return ids.filter { normalized.isEmpty() || it.contains(normalized) }.sorted()
}

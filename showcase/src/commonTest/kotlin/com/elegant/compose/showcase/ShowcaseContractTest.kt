package com.elegant.compose.showcase

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ShowcaseContractTest {
    @Test
    fun supportedComponentSlugsRemainStable() {
        assertEquals(
            setOf(
                "button",
                "icon-button",
                "avatar",
                "badge",
                "divider",
                "tag",
                "tooltip",
                "input",
                "checkbox",
                "radio",
                "switch",
                "slider",
                "select",
                "card",
                "list",
                "empty-state",
                "modal",
                "drawer",
                "table",
                "tabs",
                "breadcrumb",
                "navbar",
                "sidebar",
                "progress-indicator",
                "skeleton",
                "alert",
                "snackbar",
                "textarea",
                "number-field",
                "search-bar",
                "pagination",
                "navigation-bar",
                "navigation-rail",
                "floating-action-button",
                "popover",
                "menu",
                "accordion",
                "link",
                "kbd",
            ),
            SupportedShowcaseComponentIds,
        )
    }
}

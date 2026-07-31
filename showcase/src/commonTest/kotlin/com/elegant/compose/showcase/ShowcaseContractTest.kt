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
            ),
            SupportedShowcaseComponentIds,
        )
    }
}

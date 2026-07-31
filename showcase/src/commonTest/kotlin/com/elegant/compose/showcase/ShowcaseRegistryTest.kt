package com.elegant.compose.showcase

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ShowcaseRegistryTest {
    @Test
    fun componentSlugsRemainStableAndPortable() {
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
        assertTrue(SupportedShowcaseComponentIds.all { slug -> slug == slug.lowercase() })
    }
}

package com.elegant.compose.showcase

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ShowcaseContractTest {
    @Test
    fun supportedComponentSlugsRemainStable() {
        assertEquals(setOf("button", "icon-button"), SupportedShowcaseComponentIds)
    }
}

package com.elegant.compose.showcase

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ShowcaseRegistryTest {
    @Test
    fun buttonSlugRemainsTheSingleSharedMilestone() {
        assertEquals(setOf("button"), SupportedShowcaseComponentIds)
        assertTrue(SupportedShowcaseComponentIds.all { slug -> slug == slug.lowercase() })
    }
}

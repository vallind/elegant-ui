package com.elegant.compose.ui.avatar

import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ElegantAvatarContractTest {
    @Test
    fun avatarSizesRemainStable() {
        assertEquals(
            listOf("Small", "Medium", "Large"),
            ElegantAvatarSize.entries.map(ElegantAvatarSize::name),
        )
    }

    @Test
    fun initialsUseFirstAndLastWords() {
        assertEquals("AL", ElegantAvatarDefaults.initials("Ada Lovelace"))
        assertEquals("AL", ElegantAvatarDefaults.initials("Ada Byron Lovelace"))
    }

    @Test
    fun initialsSupportSingleWordAndCjkNames() {
        assertEquals("GR", ElegantAvatarDefaults.initials("Grace"))
        assertEquals("林晓", ElegantAvatarDefaults.initials("林晓"))
        assertEquals("林晓", ElegantAvatarDefaults.initials("林 晓"))
    }

    @Test
    fun initialsIgnorePunctuationAndHandleBlankNames() {
        assertEquals("MA", ElegantAvatarDefaults.initials("María-José"))
        assertEquals("?", ElegantAvatarDefaults.initials("   "))
        assertEquals("?", ElegantAvatarDefaults.initials("---"))
    }

    @Test
    fun sizeMetricsAndOutlineRemainStable() {
        assertEquals(32.dp, avatarContainerSizeFor(ElegantAvatarSize.Small))
        assertEquals(40.dp, avatarContainerSizeFor(ElegantAvatarSize.Medium))
        assertEquals(56.dp, avatarContainerSizeFor(ElegantAvatarSize.Large))
        assertEquals(1.dp, ElegantAvatarDefaults.BorderWidth)
    }
}

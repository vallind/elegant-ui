package com.elegant.compose.ui.button

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ElegantButtonContractTest {
    @Test
    fun buttonStylesRemainStable() {
        assertEquals(
            listOf("Primary", "Secondary", "Tertiary"),
            ElegantButtonStyle.entries.map(ElegantButtonStyle::name),
        )
    }

    @Test
    fun buttonSizesRemainStable() {
        assertEquals(
            listOf("Small", "Medium", "Large"),
            ElegantButtonSize.entries.map(ElegantButtonSize::name),
        )
    }
}

package com.elegant.compose.ui.icon

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ElegantIconsExtendedContractTest {

    @Test
    fun extendedIconsAreLazilyCachedAndStable() {
        assertEquals(ElegantIcons.Refresh, ElegantIcons.Refresh)
        assertEquals(ElegantIcons.Delete, ElegantIcons.Delete)
        assertEquals(ElegantIcons.VolumeOff, ElegantIcons.VolumeOff)
        assertEquals(ElegantIcons.Power, ElegantIcons.Power)
    }

    @Test
    fun extendedIconsUseThe24DpViewport() {
        val icon = ElegantIcons.Refresh
        assertEquals(24f, icon.defaultWidth.value)
        assertEquals(24f, icon.defaultHeight.value)
    }

    @Test
    fun allExtendedGlyphsResolve() {
        val icons = listOf(
            ElegantIcons.Refresh,
            ElegantIcons.Download,
            ElegantIcons.Upload,
            ElegantIcons.VolumeUp,
            ElegantIcons.VolumeDown,
            ElegantIcons.VolumeOff,
            ElegantIcons.Filter,
            ElegantIcons.Save,
            ElegantIcons.Send,
            ElegantIcons.Reply,
            ElegantIcons.Forward,
            ElegantIcons.Lock,
            ElegantIcons.Unlock,
            ElegantIcons.Eye,
            ElegantIcons.EyeOff,
            ElegantIcons.Calendar,
            ElegantIcons.Clock,
            ElegantIcons.Location,
            ElegantIcons.Camera,
            ElegantIcons.Image,
            ElegantIcons.Play,
            ElegantIcons.Pause,
            ElegantIcons.Info,
            ElegantIcons.Warning,
            ElegantIcons.Help,
            ElegantIcons.List,
            ElegantIcons.Grid,
            ElegantIcons.Sun,
            ElegantIcons.Moon,
            ElegantIcons.Brightness,
            ElegantIcons.Copy,
            ElegantIcons.Power,
        )
        assertTrue(icons.all { it.name.startsWith("ElegantIcons.") })
        assertEquals(32, icons.size)
    }
}

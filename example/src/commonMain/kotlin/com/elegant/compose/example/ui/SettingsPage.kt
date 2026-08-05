// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.example.AppState
import com.elegant.compose.example.LocalAppState
import com.elegant.compose.example.LocalAppStateUpdater
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.preference.ElegantArrowPreference
import com.elegant.compose.ui.preference.ElegantCheckboxPreference
import com.elegant.compose.ui.preference.ElegantRadioPreference
import com.elegant.compose.ui.preference.ElegantSliderPreference
import com.elegant.compose.ui.preference.ElegantSwitchPreference
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle
import com.elegant.compose.ui.surface.ElegantSurface

/**
 * Settings scene: preference rows that drive real app state — the dark theme switch writes back
 * through [LocalAppStateUpdater] — plus a key-color seed block showing derived palettes.
 *
 * @param onBack callback popping this page from the back stack.
 */
@Composable
internal fun SettingsPage(onBack: () -> Unit) {
    ScenePage(title = "Settings", onBack = onBack) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ElegantSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        ) {
            Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            Appearance()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            Notifications()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            Preferences()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            ThemeSeed()
            Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        }
    }
}

/** Appearance preferences; the dark theme switch mutates the real app state. */
@Composable
private fun Appearance() {
    ElegantSmallTitle(text = "Appearance")
    val appState = LocalAppState.current
    val updateAppState = LocalAppStateUpdater.current
    ElegantSwitchPreference(
        title = "Dark theme",
        checked = appState.darkTheme,
        onCheckedChange = { updateAppState(appState.copy(darkTheme = it)) },
        supportingText = "Switch between the light and dark palettes",
        showDivider = true,
    )
    var fontSize by rememberSaveable { mutableStateOf(0.5f) }
    ElegantSliderPreference(
        title = "Text size",
        value = fontSize,
        onValueChange = { fontSize = it },
        valueRange = 0f..1f,
        steps = 3,
        valueFormatter = {
            when {
                it < 0.25f -> "Small"
                it < 0.5f -> "Default"
                it < 0.75f -> "Large"
                else -> "Extra large"
            }
        },
        showDivider = true,
    )
    var reduceMotion by rememberSaveable { mutableStateOf(false) }
    ElegantCheckboxPreference(
        title = "Reduce motion",
        checked = reduceMotion,
        onCheckedChange = { reduceMotion = it },
        supportingText = "Minimize transitions and animations",
        showDivider = true,
    )
    var defaultTab by rememberSaveable { mutableStateOf("home") }
    ElegantRadioPreference(
        title = "Start on",
        selected = defaultTab == "home",
        onSelect = { defaultTab = "home" },
        supportingText = "Home tab",
        showDivider = true,
    )
    ElegantRadioPreference(
        title = "Start on",
        selected = defaultTab == "inputs",
        onSelect = { defaultTab = "inputs" },
        supportingText = "Inputs tab",
        showDivider = false,
    )
}

/** Notification preferences. */
@Composable
private fun Notifications() {
    ElegantSmallTitle(text = "Notifications")
    var push by rememberSaveable { mutableStateOf(true) }
    ElegantSwitchPreference(
        title = "Push notifications",
        checked = push,
        onCheckedChange = { push = it },
        showDivider = true,
    )
    var summary by rememberSaveable { mutableStateOf(true) }
    ElegantSwitchPreference(
        title = "Daily summary",
        checked = summary,
        onCheckedChange = { summary = it },
        showDivider = false,
    )
}

/** Static preference rows ending in the About link. */
@Composable
private fun Preferences() {
    ElegantSmallTitle(text = "About")
    ElegantArrowPreference(
        title = "Licenses",
        supportingText = "Apache-2.0",
        onClick = {},
        showDivider = true,
    )
    ElegantArrowPreference(
        title = "Version",
        supportingText = "0.2.0",
        onClick = {},
        showDivider = false,
    )
}

/** A local key-color seed showing a derived palette, mirroring the theme-controller demo. */
@Composable
private fun ThemeSeed() {
    ElegantSmallTitle(text = "Key color seed")
    ElegantTheme(keyColor = Color(0xFF6C4EFF), darkTheme = LocalAppState.current.darkTheme) {
        ElegantSurface(
            modifier = Modifier.fillMaxWidth(),
            borderWidth = 1.dp,
        ) {
            Column(modifier = Modifier.padding(ElegantSpacing.lg)) {
                Text(
                    text = "Violet 6C4EFF",
                    style = ElegantTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(ElegantSpacing.sm))
                Text(
                    text = "This surface derives its palette from a single key color.",
                    style = ElegantTheme.typography.bodyMedium,
                    color = ElegantTheme.colors.textSecondary,
                )
            }
        }
    }
}

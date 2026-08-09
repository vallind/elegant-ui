// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.elyon.kmp.basic.Card
import io.elyon.kmp.basic.Icon
import io.elyon.kmp.basic.IconButton
import io.elyon.kmp.basic.TextButton
import io.elyon.kmp.icon.ElyonIcons
import io.elyon.kmp.icon.extended.Close
import io.elyon.kmp.icon.extended.Ok
import io.elyon.kmp.preference.SwitchPreference
import io.elyon.kmp.theme.ElyonTheme
import io.elyon.kmp.theme.LocalDismissState
import io.elyon.kmp.window.WindowBottomSheet

@Composable
fun WindowBottomSheetDemo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(demoBackground()),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .widthIn(max = 600.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var showBottomSheet by remember { mutableStateOf(false) }
            var notificationsEnabled by remember { mutableStateOf(true) }
            var soundEnabled by remember { mutableStateOf(false) }

            Card {
                TextButton(
                    text = "Show a WindowBottomSheet",
                    onClick = { showBottomSheet = true },
                )
                WindowBottomSheet(
                    show = showBottomSheet,
                    title = "WindowBottomSheet Title",
                    startAction = {
                        val dismiss = LocalDismissState.current
                        IconButton(onClick = { dismiss?.invoke() }) {
                            Icon(
                                imageVector = ElyonIcons.Close,
                                contentDescription = "Cancel",
                                tint = ElyonTheme.colorScheme.onBackground,
                            )
                        }
                    },
                    endAction = {
                        val dismiss = LocalDismissState.current
                        IconButton(onClick = { dismiss?.invoke() }) {
                            Icon(
                                imageVector = ElyonIcons.Ok,
                                contentDescription = "Confirm",
                                tint = ElyonTheme.colorScheme.onBackground,
                            )
                        }
                    },
                    onDismissRequest = { showBottomSheet = false },
                ) {
                    Card(modifier = Modifier.padding(bottom = 16.dp)) {
                        SwitchPreference(
                            title = "Notifications",
                            summary = "Receive push notifications",
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                        )
                        SwitchPreference(
                            title = "Sound",
                            summary = "Play sound on notification",
                            checked = soundEnabled,
                            onCheckedChange = { soundEnabled = it },
                        )
                    }
                }
            }
        }
    }
}

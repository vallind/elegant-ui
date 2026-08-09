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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.elyon.kmp.basic.Card
import io.elyon.kmp.basic.FloatingActionButton
import io.elyon.kmp.basic.Icon
import io.elyon.kmp.basic.NavigationBar
import io.elyon.kmp.basic.NavigationBarItem
import io.elyon.kmp.basic.NavigationItem
import io.elyon.kmp.basic.Scaffold
import io.elyon.kmp.basic.SmallTopAppBar
import io.elyon.kmp.basic.Text
import io.elyon.kmp.icon.ElyonIcons
import io.elyon.kmp.icon.extended.Contacts
import io.elyon.kmp.icon.extended.Settings
import io.elyon.kmp.icon.extended.VerticalSplit
import io.elyon.kmp.theme.ElyonTheme

@Composable
fun ScaffoldDemo() {
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
            val pages = listOf("Home", "Profile", "Settings")
            val items = listOf(
                NavigationItem("Home", ElyonIcons.VerticalSplit),
                NavigationItem("Profile", ElyonIcons.Contacts),
                NavigationItem("Settings", ElyonIcons.Settings),
            )
            var selectedIndex by remember { mutableIntStateOf(0) }
            Card {
                Scaffold(
                    topBar = {
                        SmallTopAppBar(
                            title = "SmallTopAppBar",
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedIndex == index,
                                    onClick = { selectedIndex = index },
                                    icon = item.icon,
                                    label = item.label,
                                )
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                // Handle FAB click
                            },
                        ) {
                            Icon(
                                imageVector = ElyonIcons.Contacts,
                                contentDescription = "Personal",
                                tint = Color.White,
                            )
                        }
                    },
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Current: ${pages[selectedIndex]}",
                            style = ElyonTheme.textStyles.title1,
                        )
                    }
                }
            }
        }
    }
}

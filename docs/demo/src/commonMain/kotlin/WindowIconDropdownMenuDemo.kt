// Copyright 2026, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.elyon.kmp.basic.Card
import io.elyon.kmp.basic.DropdownEntry
import io.elyon.kmp.basic.DropdownItem
import io.elyon.kmp.basic.Icon
import io.elyon.kmp.basic.Scaffold
import io.elyon.kmp.basic.SmallTopAppBar
import io.elyon.kmp.basic.Text
import io.elyon.kmp.icon.ElyonIcons
import io.elyon.kmp.icon.extended.Edit
import io.elyon.kmp.icon.extended.MoreCircle
import io.elyon.kmp.icon.extended.SelectAll
import io.elyon.kmp.icon.extended.Sort
import io.elyon.kmp.menu.WindowIconDropdownMenu
import io.elyon.kmp.theme.ElyonTheme

@Composable
fun WindowIconDropdownMenuDemo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(demoBackground()),
        contentAlignment = Alignment.Center,
    ) {
        var lastAction by remember { mutableStateOf("None") }
        var sortIndex by remember { mutableIntStateOf(0) }
        val sortLabels = listOf("Name", "Date", "Size")
        var multiSelected by remember { mutableStateOf(setOf("Photos")) }

        val actionEntry = DropdownEntry(
            items = listOf("Edit", "Duplicate", "Share", "Delete").map { text ->
                DropdownItem(text = text, onClick = { lastAction = text })
            },
        )
        val sortEntry = DropdownEntry(
            items = sortLabels.mapIndexed { index, text ->
                DropdownItem(
                    text = text,
                    selected = sortIndex == index,
                    onClick = { sortIndex = index },
                )
            },
        )
        val multiSelectEntry = DropdownEntry(
            items = listOf("Photos", "Videos", "Files").map { text ->
                DropdownItem(
                    text = text,
                    selected = text in multiSelected,
                    onClick = {
                        multiSelected = if (text in multiSelected) {
                            multiSelected - text
                        } else {
                            multiSelected + text
                        }
                    },
                )
            },
        )
        val emptyEntry = DropdownEntry(items = listOf(DropdownItem(text = "Option 1")))

        Card(modifier = Modifier.padding(16.dp).widthIn(max = 600.dp).fillMaxWidth()) {
            Scaffold(
                topBar = {
                    SmallTopAppBar(
                        title = "Inbox",
                        actions = {
                            WindowIconDropdownMenu(entry = actionEntry) {
                                Icon(
                                    imageVector = ElyonIcons.Edit,
                                    tint = ElyonTheme.colorScheme.onBackground,
                                    contentDescription = "Action menu",
                                )
                            }
                            WindowIconDropdownMenu(entry = sortEntry) {
                                Icon(
                                    imageVector = ElyonIcons.Sort,
                                    tint = ElyonTheme.colorScheme.onBackground,
                                    contentDescription = "Sort",
                                )
                            }
                            WindowIconDropdownMenu(
                                entry = multiSelectEntry,
                                collapseOnSelection = false,
                            ) {
                                Icon(
                                    imageVector = ElyonIcons.SelectAll,
                                    tint = ElyonTheme.colorScheme.onBackground,
                                    contentDescription = "Multiple selection",
                                )
                            }
                            WindowIconDropdownMenu(
                                entry = emptyEntry,
                                enabled = false,
                            ) {
                                Icon(
                                    imageVector = ElyonIcons.MoreCircle,
                                    tint = ElyonTheme.colorScheme.disabledOnSecondaryVariant,
                                    contentDescription = "More",
                                )
                            }
                        },
                    )
                },
            ) { padding ->
                LazyColumn(
                    contentPadding = PaddingValues(top = padding.calculateTopPadding()),
                ) {
                    item {
                        Spacer(Modifier.height(8.dp))
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(text = "Last action: $lastAction")
                            Text(text = "Sort by: ${sortLabels[sortIndex]}")
                            Text(
                                text = "Selected: " +
                                    multiSelected.joinToString().ifEmpty { "None" },
                            )
                        }
                    }
                }
            }
        }
    }
}

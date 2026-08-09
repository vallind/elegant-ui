// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.elyon.kmp.basic.BasicComponent
import io.elyon.kmp.basic.BasicComponentDefaults
import io.elyon.kmp.basic.InputField
import io.elyon.kmp.basic.SearchBar
import io.elyon.kmp.basic.Text
import io.elyon.kmp.theme.ElyonTheme

@Composable
fun SearchBarDemo() {
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
            var searchValue by remember { mutableStateOf("") }
            var expanded by remember { mutableStateOf(false) }
            SearchBar(
                inputField = {
                    InputField(
                        query = searchValue,
                        onQueryChange = { searchValue = it },
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        label = "Search",
                    )
                },
                outsideEndAction = {
                    Text(
                        modifier = Modifier
                            .clickable(
                                interactionSource = null,
                                indication = null,
                            ) {
                                expanded = false
                                searchValue = ""
                            },
                        text = "Cancel",
                        color = ElyonTheme.colorScheme.primary,
                    )
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
            ) {
                Column(
                    Modifier.fillMaxSize(),
                ) {
                    repeat(4) { idx ->
                        val resultText = "Suggestion $idx"
                        BasicComponent(
                            title = resultText,
                            titleColor = BasicComponentDefaults.titleColor(Color.White),
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                searchValue = resultText
                                expanded = false
                            },
                        )
                    }
                }
            }
        }
    }
}

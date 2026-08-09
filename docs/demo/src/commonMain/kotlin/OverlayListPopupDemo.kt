// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.elyon.kmp.basic.DropdownImpl
import io.elyon.kmp.basic.ListPopupColumn
import io.elyon.kmp.basic.PopupPositionProvider
import io.elyon.kmp.basic.Scaffold
import io.elyon.kmp.basic.TextButton
import io.elyon.kmp.overlay.OverlayListPopup

@Composable
fun OverlayListPopupDemo() {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(demoBackground()),
            contentAlignment = Alignment.Center,
        ) {
            var showPopup by remember { mutableStateOf(false) }
            var selectedIndex by remember { mutableIntStateOf(0) }
            val items = listOf("Option 1", "Disabled option", "Option 3")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                Box {
                    TextButton(
                        text = "Click to show menu",
                        onClick = { showPopup = true },
                        modifier = Modifier.padding(top = 16.dp),
                    )
                    OverlayListPopup(
                        show = showPopup,
                        alignment = PopupPositionProvider.Align.TopStart,
                        onDismissRequest = { showPopup = false }, // Close the popup menu
                    ) {
                        ListPopupColumn {
                            items.forEachIndexed { index, string ->
                                DropdownImpl(
                                    text = string,
                                    optionSize = items.size,
                                    isSelected = selectedIndex == index,
                                    index = index,
                                    enabled = index != 1,
                                    onSelectedIndexChange = { selectedIdx ->
                                        selectedIndex = selectedIdx
                                        showPopup = false // Close the popup menu
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.elegant.compose.ui.bottomsheet.ElegantBottomSheet
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.cascadingmenu.ElegantCascadingMenu
import com.elegant.compose.ui.cascadingmenu.ElegantCascadingMenuItem
import com.elegant.compose.ui.drawer.ElegantDrawer
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.listpopup.ElegantListPopup
import com.elegant.compose.ui.listpopup.ElegantListPopupOption
import com.elegant.compose.ui.menu.ElegantMenu
import com.elegant.compose.ui.menu.ElegantMenuItem
import com.elegant.compose.ui.modal.ElegantModal
import com.elegant.compose.ui.popover.ElegantPopover
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle

/**
 * Overlays scene: modal, bottom sheet, drawer, popover, menu, list popup, and cascading menu.
 *
 * @param onBack callback popping this page from the back stack.
 */
@Composable
internal fun OverlaysPage(onBack: () -> Unit) {
    ScenePage(title = "Overlays", onBack = onBack) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ElegantSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        ) {
            Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            DialogLayers()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            AnchoredPopups()
            Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        }
    }
}

/** Modal, bottom sheet, and drawer states triggered from buttons. */
@Composable
private fun DialogLayers() {
    ElegantSmallTitle(text = "Dialog layers")
    var modalVisible by rememberSaveable { mutableStateOf(false) }
    var sheetVisible by rememberSaveable { mutableStateOf(false) }
    var drawerVisible by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
    ) {
        ElegantButton(onClick = { modalVisible = true }, modifier = Modifier.weight(1f)) {
            Text("Modal")
        }
        ElegantButton(onClick = { sheetVisible = true }, modifier = Modifier.weight(1f)) {
            Text("Sheet")
        }
        ElegantButton(onClick = { drawerVisible = true }, modifier = Modifier.weight(1f)) {
            Text("Drawer")
        }
    }
    ElegantModal(
        visible = modalVisible,
        onDismissRequest = { modalVisible = false },
    ) {
        Column(
            modifier = Modifier.padding(ElegantSpacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Upgrade plan", style = ElegantTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            Text(
                text = "Unlock unlimited components for \$9/month.",
                style = ElegantTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(ElegantSpacing.lg))
            ElegantButton(onClick = { modalVisible = false }, modifier = Modifier.fillMaxWidth()) {
                Text("Continue")
            }
        }
    }
    ElegantBottomSheet(
        visible = sheetVisible,
        onDismissRequest = { sheetVisible = false },
        title = "Share",
    ) {
        Text(
            text = "Choose where to share this file.",
            modifier = Modifier.padding(horizontal = ElegantSpacing.lg),
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.md))
    }
    ElegantDrawer(
        visible = drawerVisible,
        onDismissRequest = { drawerVisible = false },
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(ElegantSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        ) {
            Text("Menu", style = ElegantTheme.typography.titleMedium)
            Text("Inbox", style = ElegantTheme.typography.bodyMedium)
            Text("Archive", style = ElegantTheme.typography.bodyMedium)
            Text("Trash", style = ElegantTheme.typography.bodyMedium)
        }
    }
}

/** Popover, menu, list popup, and cascading menu anchored to their triggers. */
@Composable
private fun AnchoredPopups() {
    ElegantSmallTitle(text = "Anchored popups")
    ElegantPopover(
        popover = {
            Text(
                text = "Sync keeps every device up to date.",
                modifier = Modifier.padding(ElegantSpacing.md),
            )
        },
    ) {
        ElegantButton(onClick = {}) {
            Text("Popover")
        }
    }
    var menuExpanded by rememberSaveable { mutableStateOf(false) }
    Box {
        ElegantButton(onClick = { menuExpanded = true }) {
            Text("Menu")
        }
        ElegantMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
        ) {
            ElegantMenuItem(
                text = "Rename",
                onClick = { menuExpanded = false },
            )
            ElegantMenuItem(
                text = "Duplicate",
                onClick = { menuExpanded = false },
            )
            ElegantMenuItem(
                text = "Delete",
                onClick = { menuExpanded = false },
            )
        }
    }
    var listPopupExpanded by rememberSaveable { mutableStateOf(false) }
    var listSelection by remember { mutableStateOf<ElegantListPopupOption?>(null) }
    Box {
        ElegantButton(onClick = { listPopupExpanded = true }) {
            Text("List popup")
        }
        ElegantListPopup(
            expanded = listPopupExpanded,
            onDismissRequest = { listPopupExpanded = false },
            options = remember {
                listOf(
                    ElegantListPopupOption(text = "Option A", value = "a"),
                    ElegantListPopupOption(text = "Option B", value = "b"),
                    ElegantListPopupOption(text = "Option C", value = "c"),
                )
            },
            selectedValue = listSelection?.value,
            onOptionSelected = {
                listSelection = it
                listPopupExpanded = false
            },
        )
    }
    var cascadeExpanded by rememberSaveable { mutableStateOf(false) }
    Box {
        ElegantButton(onClick = { cascadeExpanded = true }) {
            Text("Cascading menu")
        }
        ElegantCascadingMenu(
            expanded = cascadeExpanded,
            onDismissRequest = { cascadeExpanded = false },
            items = remember {
                listOf(
                    ElegantCascadingMenuItem(
                        text = "New",
                        children = listOf(
                            ElegantCascadingMenuItem(text = "Folder"),
                            ElegantCascadingMenuItem(text = "File"),
                        ),
                    ),
                    ElegantCascadingMenuItem(text = "Open"),
                )
            },
            onItemClick = { cascadeExpanded = false },
        )
    }
}

// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

package component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.elyon.kmp.basic.Card
import io.elyon.kmp.basic.CardDefaults
import io.elyon.kmp.basic.Icon
import io.elyon.kmp.basic.IconButton
import io.elyon.kmp.basic.SmallTitle
import io.elyon.kmp.basic.TextField
import io.elyon.kmp.icon.ElyonIcons
import io.elyon.kmp.icon.extended.Close
import io.elyon.kmp.icon.extended.Ok
import io.elyon.kmp.overlay.OverlayBottomSheet
import io.elyon.kmp.preference.ArrowPreference
import io.elyon.kmp.preference.OverlayDropdownPreference
import io.elyon.kmp.preference.SwitchPreference
import io.elyon.kmp.preference.WindowDropdownPreference
import io.elyon.kmp.theme.LocalDismissState
import io.elyon.kmp.theme.ElyonTheme
import io.elyon.kmp.utils.overScrollVertical
import io.elyon.kmp.utils.scrollEndHaptic
import io.elyon.kmp.window.WindowBottomSheet

private val BottomSheetDropdownOptions = listOf("Option 1", "Option 2")

fun LazyListScope.bottomSheetSection() {
    item(key = "bottomSheet") {
        var showSuperBottomSheet by remember { mutableStateOf(false) }
        var showWindowBottomSheet by remember { mutableStateOf(false) }
        var superBottomSheetHoldDown by remember { mutableStateOf(false) }
        var windowBottomSheetHoldDown by remember { mutableStateOf(false) }
        var bottomSheetDropdownSelectedOption by remember { mutableIntStateOf(0) }
        var bottomSheetSuperSwitchState by remember { mutableStateOf(true) }

        SmallTitle(text = "BottomSheet")
        Card(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
        ) {
            ArrowPreference(
                title = "BottomSheet (O)",
                summary = "Click to show an OverlayBottomSheet",
                onClick = {
                    showSuperBottomSheet = true
                    superBottomSheetHoldDown = true
                },
                holdDownState = superBottomSheetHoldDown,
            )
            ArrowPreference(
                title = "BottomSheet (W)",
                summary = "Click to show a WindowBottomSheet",
                onClick = {
                    showWindowBottomSheet = true
                    windowBottomSheetHoldDown = true
                },
                holdDownState = windowBottomSheetHoldDown,
            )
        }

        SuperBottomSheetDemo(
            show = showSuperBottomSheet,
            onDismissRequest = { showSuperBottomSheet = false },
            dropdownSelectedIndex = bottomSheetDropdownSelectedOption,
            onDropdownSelectedIndexChange = { bottomSheetDropdownSelectedOption = it },
            switchChecked = bottomSheetSuperSwitchState,
            onSwitchCheckedChange = { bottomSheetSuperSwitchState = it },
            onDismissFinished = { superBottomSheetHoldDown = false },
        )
        WindowBottomSheetDemo(
            show = showWindowBottomSheet,
            onDismissRequest = { showWindowBottomSheet = false },
            dropdownSelectedIndex = bottomSheetDropdownSelectedOption,
            onDropdownSelectedIndexChange = { bottomSheetDropdownSelectedOption = it },
            switchChecked = bottomSheetSuperSwitchState,
            onSwitchCheckedChange = { bottomSheetSuperSwitchState = it },
            onDismissFinished = { windowBottomSheetHoldDown = false },
        )
    }
}

@Composable
private fun SuperBottomSheetDemo(
    show: Boolean,
    onDismissRequest: () -> Unit,
    dropdownSelectedIndex: Int,
    onDropdownSelectedIndexChange: (Int) -> Unit,
    switchChecked: Boolean,
    onSwitchCheckedChange: (Boolean) -> Unit,
    onDismissFinished: () -> Unit,
) {
    var allowDismiss by remember { mutableStateOf(true) }
    var enableNestedScroll by remember { mutableStateOf(true) }

    OverlayBottomSheet(
        title = "BottomSheet (O)",
        show = show,
        allowDismiss = allowDismiss,
        enableNestedScroll = enableNestedScroll,
        onDismissRequest = onDismissRequest,
        onDismissFinished = onDismissFinished,
        startAction = {
            BottomSheetActionButton(ElyonIcons.Close, "Cancel", onClick = onDismissRequest)
        },
        endAction = {
            BottomSheetActionButton(ElyonIcons.Ok, "Confirm", onClick = onDismissRequest)
        },
    ) {
        BottomSheetContent(
            allowDismiss = allowDismiss,
            onAllowDismissChange = { allowDismiss = it },
            enableNestedScroll = enableNestedScroll,
            onEnableNestedScrollChange = { enableNestedScroll = it },
            switchChecked = switchChecked,
            onSwitchCheckedChange = onSwitchCheckedChange,
        ) {
            OverlayDropdownPreference(
                title = "DropdownPref (O)",
                items = BottomSheetDropdownOptions,
                selectedIndex = dropdownSelectedIndex,
                onSelectedIndexChange = onDropdownSelectedIndexChange,
            )
        }
    }
}

@Composable
private fun WindowBottomSheetDemo(
    show: Boolean,
    onDismissRequest: () -> Unit,
    dropdownSelectedIndex: Int,
    onDropdownSelectedIndexChange: (Int) -> Unit,
    switchChecked: Boolean,
    onSwitchCheckedChange: (Boolean) -> Unit,
    onDismissFinished: () -> Unit,
) {
    var allowDismiss by remember { mutableStateOf(true) }
    var enableNestedScroll by remember { mutableStateOf(true) }

    WindowBottomSheet(
        title = "BottomSheet (W)",
        show = show,
        allowDismiss = allowDismiss,
        enableNestedScroll = enableNestedScroll,
        onDismissRequest = onDismissRequest,
        onDismissFinished = onDismissFinished,
        startAction = {
            val dismissState = LocalDismissState.current
            BottomSheetActionButton(ElyonIcons.Close, "Cancel", onClick = { dismissState?.invoke() })
        },
        endAction = {
            val dismissState = LocalDismissState.current
            BottomSheetActionButton(ElyonIcons.Ok, "Confirm", onClick = { dismissState?.invoke() })
        },
    ) {
        BottomSheetContent(
            allowDismiss = allowDismiss,
            onAllowDismissChange = { allowDismiss = it },
            enableNestedScroll = enableNestedScroll,
            onEnableNestedScrollChange = { enableNestedScroll = it },
            switchChecked = switchChecked,
            onSwitchCheckedChange = onSwitchCheckedChange,
        ) {
            WindowDropdownPreference(
                title = "DropdownPref (W)",
                items = BottomSheetDropdownOptions,
                selectedIndex = dropdownSelectedIndex,
                onSelectedIndexChange = onDropdownSelectedIndexChange,
            )
        }
    }
}

/**
 * Shared content of both BottomSheet demos. The only part that differs between the Overlay and
 * Window variants is the dropdown preference, supplied through the [dropdown] slot.
 */
@Composable
private fun BottomSheetContent(
    allowDismiss: Boolean,
    onAllowDismissChange: (Boolean) -> Unit,
    enableNestedScroll: Boolean,
    onEnableNestedScrollChange: (Boolean) -> Unit,
    switchChecked: Boolean,
    onSwitchCheckedChange: (Boolean) -> Unit,
    dropdown: @Composable () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .scrollEndHaptic()
            .overScrollVertical(),
    ) {
        item {
            SmallTitle(
                text = "Behavior Settings",
                insideMargin = PaddingValues(16.dp, 8.dp),
            )
            Card(
                modifier = Modifier.padding(bottom = 12.dp),
                colors = CardDefaults.defaultColors(
                    color = ElyonTheme.colorScheme.secondaryContainer,
                ),
            ) {
                SwitchPreference(
                    title = "Allow Dismiss",
                    summary = "Drag or Back to dismiss",
                    checked = allowDismiss,
                    onCheckedChange = onAllowDismissChange,
                )
                SwitchPreference(
                    title = "Enable NestedScroll",
                    summary = "Scroll content vs Drag sheet",
                    checked = enableNestedScroll,
                    onCheckedChange = onEnableNestedScrollChange,
                )
            }
        }
        item {
            var textFieldValue by remember { mutableStateOf("") }
            TextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                label = "TextField",
                modifier = Modifier.padding(bottom = 12.dp),
            )
            Card(
                modifier = Modifier.padding(bottom = 12.dp),
                colors = CardDefaults.defaultColors(
                    color = ElyonTheme.colorScheme.secondaryContainer,
                ),
            ) {
                dropdown()
                SwitchPreference(
                    title = "SwitchPref",
                    checked = switchChecked,
                    onCheckedChange = onSwitchCheckedChange,
                )
            }
            Spacer(
                Modifier.padding(
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() +
                        WindowInsets.captionBar.asPaddingValues().calculateBottomPadding(),
                ),
            )
        }
    }
}

@Composable
private fun BottomSheetActionButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = ElyonTheme.colorScheme.onBackground,
        )
    }
}

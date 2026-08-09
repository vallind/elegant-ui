// Copyright 2026, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

package io.elyon.kmp.overlay

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.elyon.kmp.basic.DropdownColors
import io.elyon.kmp.basic.DropdownDefaults
import io.elyon.kmp.basic.DropdownEntry
import io.elyon.kmp.basic.DropdownItem
import io.elyon.kmp.basic.ListPopupDefaults
import io.elyon.kmp.basic.PopupPositionProvider
import io.elyon.kmp.layout.CascadingListPopupLayout
import io.elyon.kmp.utils.ElyonPopupUtils.Companion.PopupLayout

/**
 * A cascading list popup rendered inside the host `Scaffold`. Cascading depth is limited to 2.
 *
 * @param show Whether the popup is shown.
 * @param entries Grouped dropdown entries; top-level [DropdownItem]s with non-empty
 *   [DropdownItem.children] become submenu triggers. Keep the entry and item order stable while the
 *   popup is shown; item state such as [DropdownItem.selected] may change.
 * @param onDismissRequest Invoked when the popup wants to be dismissed.
 * @param onDismissFinished Invoked after the exit animation finishes.
 * @param popupModifier Modifier applied to the popup body.
 * @param popupPositionProvider Position strategy for the primary popup relative to its anchor.
 * @param alignment Alignment of the primary popup.
 * @param enableWindowDim Whether to dim the rest of the window while the popup is shown.
 * @param maxHeight Maximum height of either side. Null bounds it by the safe area.
 * @param minWidth Minimum width of the popup.
 * @param renderInRootScaffold Whether to render in the outermost Scaffold.
 * @param dropdownColors Colors used by every row.
 * @param collapseOnSelection When true, selecting any leaf dismisses the popup.
 */
@Composable
fun OverlayCascadingListPopup(
    show: Boolean,
    entries: List<DropdownEntry>,
    onDismissRequest: () -> Unit,
    popupModifier: Modifier = Modifier,
    onDismissFinished: (() -> Unit)? = null,
    popupPositionProvider: PopupPositionProvider = ListPopupDefaults.DropdownPositionProvider,
    alignment: PopupPositionProvider.Align = PopupPositionProvider.Align.End,
    enableWindowDim: Boolean = true,
    maxHeight: Dp? = null,
    minWidth: Dp = 200.dp,
    renderInRootScaffold: Boolean = true,
    dropdownColors: DropdownColors = DropdownDefaults.dropdownColors(),
    collapseOnSelection: Boolean = true,
) {
    CascadingListPopupLayout(
        show = show,
        popupHost = { visible, hostContent ->
            val visibleState = remember { mutableStateOf(false) }
            visibleState.value = visible
            PopupLayout(
                visible = visibleState,
                enableWindowDim = false,
                enableBackHandler = false,
                enterTransition = EnterTransition.None,
                exitTransition = ExitTransition.None,
                renderInRootScaffold = renderInRootScaffold,
            ) {
                hostContent()
            }
        },
        entries = entries,
        onDismissRequest = onDismissRequest,
        onDismissFinished = onDismissFinished,
        popupModifier = popupModifier,
        popupPositionProvider = popupPositionProvider,
        alignment = alignment,
        enableWindowDim = enableWindowDim,
        maxHeight = maxHeight,
        minWidth = minWidth,
        dropdownColors = dropdownColors,
        collapseOnSelection = collapseOnSelection,
    )
}

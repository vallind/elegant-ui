// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.elegant.compose.ui.alert.ElegantAlert
import com.elegant.compose.ui.alert.ElegantAlertStyle
import com.elegant.compose.ui.alertdialog.ElegantAlertDialog
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.button.ElegantButtonStyle
import com.elegant.compose.ui.foundation.icons.ElegantIcons
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle
import com.elegant.compose.ui.snackbar.ElegantSnackbar
import com.elegant.compose.ui.snackbar.ElegantSnackbarHost
import com.elegant.compose.ui.snackbar.ElegantSnackbarHostState
import com.elegant.compose.ui.toast.ElegantToast
import com.elegant.compose.ui.toast.ElegantToastHost
import com.elegant.compose.ui.toast.ElegantToastHostState
import com.elegant.compose.ui.tooltip.ElegantTooltip
import com.elegant.compose.ui.tooltip.ElegantTooltipBox
import kotlinx.coroutines.launch

/**
 * Feedback scene: alerts, an alert dialog, transient toasts and snackbars driven through their
 * host states, and a hover/focus tooltip.
 *
 * @param onBack callback popping this page from the back stack.
 */
@Composable
internal fun FeedbackPage(onBack: () -> Unit) {
    ScenePage(title = "Feedback", onBack = onBack) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ElegantSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        ) {
            Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            Alerts()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            TransientMessages()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            Tooltips()
            Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        }
    }
}

/** Inline alerts and the confirmation alert dialog. */
@Composable
private fun Alerts() {
    ElegantSmallTitle(text = "Alerts")
    ElegantAlert(
        style = ElegantAlertStyle.Neutral,
        title = "Heads up",
        description = "Your trial ends in 7 days.",
        modifier = Modifier.fillMaxWidth(),
    )
    ElegantAlert(
        style = ElegantAlertStyle.Warning,
        title = "Low battery",
        description = "Connect the charger to keep syncing.",
        modifier = Modifier.fillMaxWidth(),
    )
    ElegantAlert(
        style = ElegantAlertStyle.Critical,
        title = "Sync failed",
        description = "Check your connection and retry.",
        modifier = Modifier.fillMaxWidth(),
        action = {
            ElegantButton(onClick = {}) {
                Text("Retry")
            }
        },
    )
    var dialogVisible by rememberSaveable { mutableStateOf(false) }
    ElegantButton(onClick = { dialogVisible = true }) {
        Text("Delete project")
    }
    ElegantAlertDialog(
        visible = dialogVisible,
        onDismissRequest = { dialogVisible = false },
        title = "Delete project?",
        description = "This action removes all files and cannot be undone.",
        confirmText = "Delete",
        onConfirm = { dialogVisible = false },
        dismissText = "Cancel",
        onDismiss = { dialogVisible = false },
    )
}

/** Toast and snackbar hosts triggered from buttons. */
@Composable
private fun TransientMessages() {
    ElegantSmallTitle(text = "Transient messages")
    val toastHostState = remember { ElegantToastHostState() }
    val snackbarHostState = remember { ElegantSnackbarHostState() }
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
    ) {
        ElegantButton(
            onClick = { scope.launch { toastHostState.showToast("Changes saved") } },
            modifier = Modifier.weight(1f),
        ) {
            Text("Show toast")
        }
        ElegantButton(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Update downloaded",
                        actionLabel = "Install",
                    )
                }
            },
            modifier = Modifier.weight(1f),
        ) {
            Text("Show snackbar")
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
    ) {
        ElegantToastHost(hostState = toastHostState)
        ElegantSnackbarHost(hostState = snackbarHostState)
    }
    ElegantToast(
        title = "Static toast",
        description = "Hosts animate the message in and out.",
        modifier = Modifier.fillMaxWidth(),
    )
    ElegantSnackbar(
        text = "Static snackbar",
        modifier = Modifier.fillMaxWidth(),
    )
}

/** Tooltip anchors. */
@Composable
private fun Tooltips() {
    ElegantSmallTitle(text = "Tooltips")
    ElegantTooltipBox(
        tooltip = { ElegantTooltip(text = "Backup runs nightly") },
    ) {
        ElegantButton(onClick = {}, style = ElegantButtonStyle.Secondary) {
            Icon(
                imageVector = ElegantIcons.Refresh,
                contentDescription = null,
            )
            Text("Hover me")
        }
    }
}

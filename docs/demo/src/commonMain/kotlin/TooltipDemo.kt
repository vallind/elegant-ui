// Copyright 2026, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.elyon.kmp.basic.Card
import io.elyon.kmp.basic.Icon
import io.elyon.kmp.basic.IconButton
import io.elyon.kmp.basic.RichTooltipBox
import io.elyon.kmp.basic.TooltipBox
import io.elyon.kmp.basic.rememberTooltipState
import io.elyon.kmp.icon.ElyonIcons
import io.elyon.kmp.icon.extended.Edit
import io.elyon.kmp.icon.extended.Info

@Composable
fun TooltipDemo() {
    val richState = rememberTooltipState(isPersistent = true)
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
            Card {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        TooltipBox(text = "Edit") {
                            IconButton(onClick = {}) {
                                Icon(imageVector = ElyonIcons.Edit, contentDescription = "Edit")
                            }
                        }
                        RichTooltipBox(
                            title = "Rich tooltip",
                            text = "Rich tooltips show a title, supporting text, and an optional action. " +
                                "Move onto the tooltip to use the action, or tap outside to dismiss.",
                            actionText = "Got it",
                            onActionClick = {},
                            state = richState,
                        ) {
                            IconButton(onClick = {}) {
                                Icon(imageVector = ElyonIcons.Info, contentDescription = "Rich tooltip")
                            }
                        }
                    }
                }
            }
        }
    }
}

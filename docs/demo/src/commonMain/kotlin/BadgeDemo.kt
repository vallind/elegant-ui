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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.elyon.kmp.basic.Badge
import io.elyon.kmp.basic.BadgedBox
import io.elyon.kmp.basic.Card
import io.elyon.kmp.basic.Icon
import io.elyon.kmp.basic.Text
import io.elyon.kmp.icon.ElyonIcons
import io.elyon.kmp.icon.extended.Email
import io.elyon.kmp.icon.extended.Favorites
import io.elyon.kmp.icon.extended.Messages
import io.elyon.kmp.icon.extended.Settings

@Composable
fun BadgeDemo() {
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
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        BadgedBox(badge = { Badge() }) {
                            Icon(
                                imageVector = ElyonIcons.Messages,
                                contentDescription = "Messages",
                                modifier = Modifier.size(28.dp),
                            )
                        }
                        BadgedBox(badge = { Badge { Text("8") } }) {
                            Icon(
                                imageVector = ElyonIcons.Email,
                                contentDescription = "Email",
                                modifier = Modifier.size(28.dp),
                            )
                        }
                        BadgedBox(badge = { Badge { Text("99+") } }) {
                            Icon(
                                imageVector = ElyonIcons.Settings,
                                contentDescription = "Settings",
                                modifier = Modifier.size(28.dp),
                            )
                        }
                        BadgedBox(badge = { Badge { Text("5") } }) {
                            Icon(
                                imageVector = ElyonIcons.Favorites,
                                contentDescription = "Favorites",
                                modifier = Modifier.size(28.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

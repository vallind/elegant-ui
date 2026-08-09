// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

package component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.elyon.kmp.basic.BasicComponent
import io.elyon.kmp.basic.Card
import io.elyon.kmp.basic.SmallTitle
import io.elyon.kmp.basic.Text
import io.elyon.kmp.theme.ElyonTheme

fun LazyListScope.basicComponentSection() {
    item(key = "basicComponent") {
        SmallTitle(text = "Basic Component")
        Card(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
        ) {
            BasicComponent(
                title = "Title",
                summary = "Summary",
                startAction = {
                    Text(
                        text = "Start",
                    )
                },
                endActions = {
                    Text(
                        text = "End1",
                        fontSize = ElyonTheme.textStyles.body2.fontSize,
                        color = ElyonTheme.colorScheme.onSurfaceVariantActions,
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "End2",
                        fontSize = ElyonTheme.textStyles.body2.fontSize,
                        color = ElyonTheme.colorScheme.onSurfaceVariantActions,
                    )
                },
                enabled = true,
            )
            BasicComponent(
                title = "Title",
                summary = "Summary",
                startAction = {
                    Text(
                        text = "Start",
                        color = ElyonTheme.colorScheme.disabledOnSecondaryVariant,
                    )
                },
                endActions = {
                    Text(
                        text = "End1",
                        fontSize = ElyonTheme.textStyles.body2.fontSize,
                        color = ElyonTheme.colorScheme.disabledOnSecondaryVariant,
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "End2",
                        fontSize = ElyonTheme.textStyles.body2.fontSize,
                        color = ElyonTheme.colorScheme.disabledOnSecondaryVariant,
                    )
                },
                enabled = false,
            )
        }
    }
}

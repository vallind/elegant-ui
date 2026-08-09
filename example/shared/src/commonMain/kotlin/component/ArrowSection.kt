// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

package component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.elyon.kmp.basic.ButtonDefaults
import io.elyon.kmp.basic.Card
import io.elyon.kmp.basic.Icon
import io.elyon.kmp.basic.SmallTitle
import io.elyon.kmp.basic.Text
import io.elyon.kmp.basic.TextButton
import io.elyon.kmp.basic.TextField
import io.elyon.kmp.icon.ElyonIcons
import io.elyon.kmp.icon.extended.Contacts
import io.elyon.kmp.overlay.OverlayDialog
import io.elyon.kmp.preference.ArrowPreference
import io.elyon.kmp.preference.SliderPreference
import io.elyon.kmp.theme.ElyonTheme

fun LazyListScope.arrowSection() {
    item(key = "arrow") {
        var volume by remember { mutableFloatStateOf(0.5f) }
        val showVolumeDialog = rememberSaveable { mutableStateOf(false) }
        val volumeDialogHoldDown = rememberSaveable { mutableStateOf(false) }

        SmallTitle(text = "Arrow")
        Card(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
        ) {
            ArrowPreference(
                title = "Arrow",
                startAction = {
                    Box(
                        modifier = Modifier.padding(end = 8.dp),
                    ) {
                        Icon(
                            imageVector = ElyonIcons.Contacts,
                            contentDescription = "Personal",
                            tint = ElyonTheme.colorScheme.onBackground,
                        )
                    }
                },
                endActions = {
                    Text(
                        text = "End",
                        fontSize = ElyonTheme.textStyles.body2.fontSize,
                        color = ElyonTheme.colorScheme.onSurfaceVariantActions,
                    )
                },
                onClick = {},
            )
            SliderPreference(
                title = "Volume",
                valueText = "${(volume * 100).toInt()}%",
                value = volume,
                onValueChange = { volume = it },
                onClick = {
                    showVolumeDialog.value = true
                    volumeDialogHoldDown.value = true
                },
                holdDownState = volumeDialogHoldDown.value,
            )
            ArrowPreference(
                title = "Disabled Arrow",
                endActions = {
                    Text(
                        text = "End",
                        fontSize = ElyonTheme.textStyles.body2.fontSize,
                        color = ElyonTheme.colorScheme.disabledOnSecondaryVariant,
                    )
                },
                enabled = false,
            )
        }

        SliderDialog(
            showVolumeDialog,
            volumeState = { volume },
            onVolumeChange = { volume = it },
            onDismissFinished = { volumeDialogHoldDown.value = false },
        )
    }
}

@Composable
private fun SliderDialog(
    showDialog: MutableState<Boolean>,
    volumeState: () -> Float,
    onVolumeChange: (Float) -> Unit,
    onDismissFinished: () -> Unit,
) {
    OverlayDialog(
        show = showDialog.value,
        title = "Adjust Volume",
        summary = "Enter 0-100",
        onDismissRequest = {
            showDialog.value = false
        },
        onDismissFinished = onDismissFinished,
        content = {
            var text by remember { mutableStateOf(((volumeState() * 100).toInt()).toString()) }
            TextField(
                modifier = Modifier.padding(bottom = 16.dp),
                value = text,
                maxLines = 1,
                onValueChange = { newValue ->
                    val digits = newValue.filter { it.isDigit() }
                    if (digits.isEmpty()) {
                        text = ""
                    } else {
                        val limited = digits.take(3)
                        val num = limited.toIntOrNull() ?: 0
                        val clamped = num.coerceIn(0, 100)
                        text = clamped.toString()
                    }
                },
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(
                    text = "Cancel",
                    onClick = { showDialog.value = false },
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(20.dp))
                TextButton(
                    text = "Confirm",
                    onClick = {
                        val parsed = text.toIntOrNull()
                        val clamped = parsed?.coerceIn(0, 100) ?: ((volumeState() * 100).toInt())
                        onVolumeChange(clamped / 100f)
                        showDialog.value = false
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.textButtonColorsPrimary(),
                )
            }
        },
    )
}

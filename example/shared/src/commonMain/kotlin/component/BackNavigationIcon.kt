// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

package component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import io.elyon.kmp.basic.Icon
import io.elyon.kmp.basic.IconButton
import io.elyon.kmp.icon.ElyonIcons
import io.elyon.kmp.icon.extended.Back
import io.elyon.kmp.theme.ElyonTheme.colorScheme

@Composable
fun BackNavigationIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val layoutDirection = LocalLayoutDirection.current
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.graphicsLayer {
                if (layoutDirection == LayoutDirection.Rtl) scaleX = -1f
            },
            imageVector = ElyonIcons.Back,
            contentDescription = null,
            tint = colorScheme.onBackground,
        )
    }
}

// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle

/**
 * Typography tab: every [ElegantTheme.typography] style rendered with its style name and a sample
 * line, mirroring the reference example's text style page.
 */
@Composable
internal fun TypographyPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = ElegantSpacing.lg),
    ) {
        Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        Text(
            text = "Typography",
            style = ElegantTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.xs))
        Text(
            text = "The type scale used by components and layouts",
            style = ElegantTheme.typography.bodyMedium,
            color = ElegantTheme.colors.textSecondary,
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.lg))
        ElegantSmallTitle(text = "Type scale")
        Spacer(modifier = Modifier.height(ElegantSpacing.sm))
        TypographyStyleRow(name = "labelSmall", style = ElegantTheme.typography.labelSmall)
        TypographyStyleRow(name = "labelMedium", style = ElegantTheme.typography.labelMedium)
        TypographyStyleRow(name = "labelLarge", style = ElegantTheme.typography.labelLarge)
        TypographyStyleRow(name = "bodyMedium", style = ElegantTheme.typography.bodyMedium)
        TypographyStyleRow(name = "titleMedium", style = ElegantTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(ElegantSpacing.xl))
    }
}

/** One style row: the style name above a sample line rendered in that style. */
@Composable
private fun TypographyStyleRow(name: String, style: TextStyle) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = ElegantSpacing.sm)) {
        Text(
            text = name,
            style = ElegantTheme.typography.labelSmall,
            color = ElegantTheme.colors.textSecondary,
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.xs))
        Text(
            text = "Elegant UI · The quick brown fox jumps",
            style = style,
            color = ElegantTheme.colors.textPrimary,
        )
    }
}

// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle

/**
 * Theme colors tab: every [ElegantTheme.colors] role rendered as a swatch with its role name and
 * hex value, grouped by purpose. Follows the ambient light/dark scheme of the app.
 */
@Composable
internal fun ColorsPage() {
    val colors = ElegantTheme.colors
    val groups = remember(colors) {
        listOf(
            "Background" to listOf(
                ColorRole("backgroundCanvas", colors.backgroundCanvas),
                ColorRole("backgroundSubtle", colors.backgroundSubtle),
            ),
            "Surfaces" to listOf(
                ColorRole("surfaceDefault", colors.surfaceDefault),
                ColorRole("surfaceRaised", colors.surfaceRaised),
                ColorRole("surfaceSunken", colors.surfaceSunken),
                ColorRole("surfaceHover", colors.surfaceHover),
            ),
            "Text" to listOf(
                ColorRole("textPrimary", colors.textPrimary),
                ColorRole("textSecondary", colors.textSecondary),
                ColorRole("textTertiary", colors.textTertiary),
                ColorRole("textInverse", colors.textInverse),
            ),
            "Borders" to listOf(
                ColorRole("borderDefault", colors.borderDefault),
                ColorRole("borderStrong", colors.borderStrong),
            ),
            "Interactive" to listOf(
                ColorRole("interactivePrimary", colors.interactivePrimary),
                ColorRole("interactivePrimaryHover", colors.interactivePrimaryHover),
                ColorRole("interactivePrimaryPressed", colors.interactivePrimaryPressed),
                ColorRole("focusRing", colors.focusRing),
            ),
            "Status" to listOf(
                ColorRole("statusPositive", colors.statusPositive),
                ColorRole("onStatusPositive", colors.onStatusPositive),
                ColorRole("statusWarning", colors.statusWarning),
                ColorRole("onStatusWarning", colors.onStatusWarning),
                ColorRole("statusCritical", colors.statusCritical),
                ColorRole("onStatusCritical", colors.onStatusCritical),
            ),
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = ElegantSpacing.lg),
    ) {
        Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        Text(
            text = "Theme Colors",
            style = ElegantTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.xs))
        Text(
            text = "Semantic color roles shared by every component",
            style = ElegantTheme.typography.bodyMedium,
            color = ElegantTheme.colors.textSecondary,
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.lg))
        for ((group, roles) in groups) {
            ElegantSmallTitle(text = group)
            Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            for (role in roles) {
                ColorSwatchRow(role = role)
                Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            }
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
        }
    }
}

/** One color role: a swatch square with the role name and its hex value. */
@Composable
private fun ColorSwatchRow(role: ColorRole) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .size(48.dp)
                .background(color = role.color)
                .border(width = 1.dp, color = ElegantTheme.colors.borderDefault),
        ) {}
        Spacer(modifier = Modifier.width(ElegantSpacing.md))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = role.name,
                style = ElegantTheme.typography.labelLarge,
            )
            Text(
                text = role.toHex(),
                style = ElegantTheme.typography.labelSmall,
                color = ElegantTheme.colors.textSecondary,
            )
        }
    }
}

/** A named color role with its hex serialization. */
@Immutable
private data class ColorRole(
    val name: String,
    val color: Color,
) {
    fun toHex(): String {
        val argb = color.toArgb()
        val rgb = argb and 0xFFFFFF
        return "#" + rgb.toString(16).uppercase().padStart(6, '0')
    }
}

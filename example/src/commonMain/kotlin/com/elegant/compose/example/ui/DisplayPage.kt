// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.accordion.ElegantAccordion
import com.elegant.compose.ui.accordion.ElegantAccordionItem
import com.elegant.compose.ui.avatar.ElegantAvatar
import com.elegant.compose.ui.avatar.ElegantAvatarSize
import com.elegant.compose.ui.badge.ElegantBadge
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponent
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.card.ElegantCard
import com.elegant.compose.ui.card.ElegantCardStyle
import com.elegant.compose.ui.description.ElegantDescription
import com.elegant.compose.ui.description.ElegantDescriptionItem
import com.elegant.compose.ui.disclosure.ElegantDisclosure
import com.elegant.compose.ui.divider.ElegantDivider
import com.elegant.compose.ui.emptystate.ElegantEmptyState
import com.elegant.compose.ui.foundation.effect.elegantBlur
import com.elegant.compose.ui.foundation.icons.ElegantIcons
import com.elegant.compose.ui.foundation.shape.elegantSquircleSurface
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.kbd.ElegantKbd
import com.elegant.compose.ui.list.ElegantList
import com.elegant.compose.ui.list.ElegantListItem
import com.elegant.compose.ui.meter.ElegantMeter
import com.elegant.compose.ui.meter.ElegantMeterTone
import com.elegant.compose.ui.progress.ElegantCircularProgressIndicator
import com.elegant.compose.ui.progress.ElegantLinearProgressIndicator
import com.elegant.compose.ui.skeleton.ElegantSkeleton
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle
import com.elegant.compose.ui.spinner.ElegantSpinner
import com.elegant.compose.ui.surface.ElegantSurface
import com.elegant.compose.ui.table.ElegantTable
import com.elegant.compose.ui.table.ElegantTableColumn
import com.elegant.compose.ui.table.ElegantTableRow

/**
 * Display scene: content, data, and status presentation — avatars, badges, cards, lists, tables,
 * descriptions, meters, progress, skeletons, empty states, and the foundation surface helpers.
 *
 * @param onBack callback popping this page from the back stack.
 */
@Composable
internal fun DisplayPage(onBack: () -> Unit) {
    ScenePage(title = "Display", onBack = onBack) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ElegantSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        ) {
            Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            Identity()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            DataPresentation()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            StatusIndicators()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            Surfaces()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            Expanders()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            FoundationHelpers()
            Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        }
    }
}

/** Avatars, badges, tags, and keyboard hints. */
@Composable
private fun Identity() {
    ElegantSmallTitle(text = "Identity")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantAvatar(name = "Alex Chen", modifier = Modifier.size(48.dp))
        ElegantBadge { Text("New") }
        ElegantKbd(text = "Ctrl + K")
        ElegantSpinner()
    }
    var count by rememberSaveable { mutableIntStateOf(3) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantBadge(
            modifier = Modifier.padding(horizontal = ElegantSpacing.sm),
        ) {
            Text("$count")
        }
        ElegantSmallTitle(text = "Notifications")
    }
}

/** Cards, lists, tables, and descriptions. */
@Composable
private fun DataPresentation() {
    ElegantSmallTitle(text = "Data presentation")
    ElegantCard(style = ElegantCardStyle.Elevated) {
        Column(modifier = Modifier.padding(ElegantSpacing.md)) {
            Text("Storage", style = ElegantTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            ElegantMeter(value = 0.64f, label = "Used")
            Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            Text(
                text = "64% of 128 GB used",
                style = ElegantTheme.typography.bodyMedium,
                color = ElegantTheme.colors.textSecondary,
            )
        }
    }
    ElegantList(modifier = Modifier.fillMaxWidth()) {
        ElegantListItem(
            leadingContent = {
                ElegantAvatar(name = "Sam Lee", size = ElegantAvatarSize.Small)
            },
            title = { Text("Sam Lee") },
            supportingText = { Text("Online") },
            trailingContent = { Icon(imageVector = ElegantIcons.ChevronRight, contentDescription = null) },
            onClick = {},
        )
        ElegantListItem(
            title = { Text("Shared documents") },
            supportingText = { Text("12 files") },
            onClick = {},
        )
    }
    ElegantTable(
        columns = remember {
            listOf(
                ElegantTableColumn(title = "Component"),
                ElegantTableColumn(title = "Target"),
                ElegantTableColumn(title = "Status"),
            )
        },
        rows = remember {
            listOf(
                ElegantTableRow(listOf("button", "Android", "Ready")),
                ElegantTableRow(listOf("calendar", "Desktop", "Ready")),
                ElegantTableRow(listOf("modal", "Web", "Review")),
            )
        },
        modifier = Modifier.fillMaxWidth(),
    )
    ElegantDescription(
        items = remember {
            listOf(
                ElegantDescriptionItem(label = "Version", value = "0.2.0"),
                ElegantDescriptionItem(label = "License", value = "Apache-2.0"),
                ElegantDescriptionItem(label = "Targets", value = "Android, Desktop, Web"),
            )
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

/** Meters, progress indicators, skeletons, and empty states. */
@Composable
private fun StatusIndicators() {
    ElegantSmallTitle(text = "Status")
    ElegantMeter(value = 0.8f, label = "Battery")
    ElegantMeter(value = 0.4f, tone = ElegantMeterTone.Warning, label = "Warning")
    ElegantLinearProgressIndicator(progress = 0.45f, modifier = Modifier.fillMaxWidth())
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantCircularProgressIndicator(progress = 0.65f)
        ElegantLinearProgressIndicator(progress = null, modifier = Modifier.weight(1f))
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
    ) {
        ElegantSkeleton(modifier = Modifier.fillMaxWidth().height(56.dp))
        ElegantSkeleton(modifier = Modifier.fillMaxWidth().height(16.dp))
        ElegantSkeleton(modifier = Modifier.fillMaxWidth().height(16.dp))
    }
    ElegantEmptyState(
        icon = {
            Icon(
                imageVector = ElegantIcons.Image,
                contentDescription = null,
                tint = ElegantTheme.colors.textTertiary,
            )
        },
        title = "No media yet",
        description = "Upload your first image to see it here.",
        modifier = Modifier.fillMaxWidth(),
    )
}

/** Surfaces, basic components, and divider helpers. */
@Composable
private fun Surfaces() {
    ElegantSmallTitle(text = "Surfaces")
    ElegantSurface(
        modifier = Modifier.fillMaxWidth(),
        borderWidth = 1.dp,
    ) {
        Text(
            text = "A bordered surface with padding",
            modifier = Modifier.padding(ElegantSpacing.md),
            style = ElegantTheme.typography.bodyMedium,
        )
    }
    ElegantBasicComponent(
        title = "Update available",
        summary = "Version 0.3.0 adds calendar and pickers.",
        startAction = {
            Icon(imageVector = ElegantIcons.Info, contentDescription = null)
        },
        endActions = {
            ElegantBadge { Text("NEW") }
        },
        bottomAction = {
            ElegantButton(onClick = {}) {
                Text("Update now")
            }
        },
    )
    ElegantDivider()
    Text(
        text = "Section above is divided",
        style = ElegantTheme.typography.bodyMedium,
        color = ElegantTheme.colors.textSecondary,
    )
}

/** Accordion and disclosure groups. */
@Composable
private fun Expanders() {
    ElegantSmallTitle(text = "Expanders")
    var accordionOpen by rememberSaveable { mutableStateOf(true) }
    ElegantAccordion {
        ElegantAccordionItem(
            title = "Why Compose Multiplatform?",
            expanded = accordionOpen,
            onToggle = { accordionOpen = !accordionOpen },
        ) {
            Text(
                text = "One UI codebase runs on Android, Desktop JVM, and Web.",
                modifier = Modifier.padding(ElegantSpacing.md),
                style = ElegantTheme.typography.bodyMedium,
            )
        }
        ElegantAccordionItem(
            title = "Where do icons come from?",
            expanded = !accordionOpen,
            onToggle = { accordionOpen = !accordionOpen },
        ) {
            Text(
                text = "ElegantIcons ships self-drawn vector glyphs.",
                modifier = Modifier.padding(ElegantSpacing.md),
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
    var disclosureOpen by rememberSaveable { mutableStateOf(false) }
    ElegantDisclosure(
        title = "Release notes",
        expanded = disclosureOpen,
        onToggle = { disclosureOpen = !disclosureOpen },
    ) {
        Text(
            text = "0.2.0: new input components and navigation scenes.",
            modifier = Modifier.padding(ElegantSpacing.md),
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}

/** Icons, squircle surfaces, and blur effects. */
@Composable
private fun FoundationHelpers() {
    ElegantSmallTitle(text = "Foundations")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
    ) {
        val glyphs = listOf(
            ElegantIcons.Home,
            ElegantIcons.Star,
            ElegantIcons.Heart,
            ElegantIcons.Search,
            ElegantIcons.Settings,
            ElegantIcons.Notifications,
            ElegantIcons.Share,
            ElegantIcons.Play,
            ElegantIcons.Lock,
            ElegantIcons.Filter,
        )
        glyphs.forEach { glyph ->
            Icon(
                imageVector = glyph,
                contentDescription = null,
                tint = ElegantTheme.colors.textSecondary,
            )
        }
    }
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .elegantSquircleSurface(color = ElegantTheme.colors.surfaceDefault)
                .padding(ElegantSpacing.lg),
        ) {
            Text(
                text = "Squircle surface",
                style = ElegantTheme.typography.titleMedium,
            )
            Text(
                text = "Rendered through ElegantSquircleShape",
                style = ElegantTheme.typography.bodyMedium,
                color = ElegantTheme.colors.textSecondary,
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .elegantBlur(radius = 6.dp)
            .background(ElegantTheme.colors.surfaceRaised)
            .padding(ElegantSpacing.lg),
    ) {
        Text(
            text = "Blurred surface",
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}

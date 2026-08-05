// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elegant.compose.example.ExampleRoute
import com.elegant.compose.example.ExampleScenes
import com.elegant.compose.example.Scene
import com.elegant.compose.example.filterComponentIds
import com.elegant.compose.showcase.ElegantShowcaseIds
import com.elegant.compose.ui.card.ElegantCard
import com.elegant.compose.ui.foundation.icons.ElegantIcons
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.list.ElegantList
import com.elegant.compose.ui.list.ElegantListItem
import com.elegant.compose.ui.searchbar.ElegantSearchBar
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle

/**
 * Landing tab of the example app.
 *
 * Offers a component search, a matrix of scene entry cards, a gallery entry, and the full sorted
 * component list; every entry pushes its route or component detail page onto the shared stack.
 *
 * @param onOpenScene callback opening a scene route.
 * @param onOpenComponent callback opening the component detail page of a showcase slug.
 * @param onOpenGallery callback opening the showcase browser.
 */
@Composable
internal fun HomePage(
    onOpenScene: (ExampleRoute) -> Unit,
    onOpenComponent: (String) -> Unit,
    onOpenGallery: () -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }
    val componentIds = remember { ElegantShowcaseIds }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = ElegantSpacing.lg),
    ) {
        Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        Text(
            text = "Elegant UI",
            style = ElegantTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.xs))
        Text(
            text = "A complete app example across Android, Desktop, and Web",
            style = ElegantTheme.typography.bodyMedium,
            color = ElegantTheme.colors.textSecondary,
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.lg))
        ElegantSearchBar(
            query = query,
            onQueryChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Search components",
            onClear = { query = "" },
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        SceneGrid(
            onOpenScene = onOpenScene,
        )
        ElegantCard(
            onClick = onOpenGallery,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(ElegantSpacing.md),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Component Gallery",
                        style = ElegantTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(ElegantSpacing.xs))
                    Text(
                        text = "Browse every component demo in the showcase browser",
                        style = ElegantTheme.typography.bodyMedium,
                        color = ElegantTheme.colors.textSecondary,
                    )
                }
                Icon(
                    imageVector = ElegantIcons.ChevronRight,
                    contentDescription = null,
                    tint = ElegantTheme.colors.textSecondary,
                )
            }
        }
        Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        ElegantSmallTitle(text = "All components")
        Spacer(modifier = Modifier.height(ElegantSpacing.sm))
        ElegantList(modifier = Modifier.fillMaxWidth()) {
            for (slug in filterComponentIds(componentIds, query)) {
                ElegantListItem(
                    title = { Text(text = slug) },
                    trailingContent = {
                        Icon(
                            imageVector = ElegantIcons.ChevronRight,
                            contentDescription = null,
                            tint = ElegantTheme.colors.textSecondary,
                        )
                    },
                    onClick = { onOpenComponent(slug) },
                )
            }
        }
        Spacer(modifier = Modifier.height(ElegantSpacing.xl))
    }
}

/** Scene entry cards laid out in rows of two (narrow) or three (wide) equal cards. */
@Composable
private fun SceneGrid(onOpenScene: (ExampleRoute) -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val perRow = if (maxWidth >= 600.dp) 3 else 2
        for (row in ExampleScenes.chunked(perRow)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            ) {
                for (scene in row) {
                    SceneCard(
                        scene = scene,
                        onClick = { onOpenScene(scene.route) },
                        modifier = Modifier.weight(1f),
                    )
                }
                repeat(perRow - row.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
        }
    }
}

/** One tappable scene entry card. */
@Composable
private fun SceneCard(
    scene: Scene,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElegantCard(onClick = onClick, modifier = modifier) {
        Column(modifier = Modifier.padding(ElegantSpacing.md)) {
            Text(text = scene.title, style = ElegantTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(ElegantSpacing.xs))
            Text(
                text = scene.description,
                style = ElegantTheme.typography.bodyMedium,
                color = ElegantTheme.colors.textSecondary,
            )
        }
    }
}

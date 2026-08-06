// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
 * Offers a component search above the categorized component list: every scene category renders its
 * component rows, and each category header opens its hand-written scene page. This mirrors the
 * reference example, where the home page scrolls through all components grouped by category.
 *
 * @param onOpenScene callback pushing a scene route (and the gallery) onto the stack.
 * @param onOpenTab callback switching to a tab destination such as Settings.
 * @param onOpenComponent callback opening the component detail page of a showcase slug.
 */
@Composable
internal fun HomePage(
    onOpenScene: (ExampleRoute) -> Unit,
    onOpenTab: (ExampleRoute) -> Unit,
    onOpenComponent: (String) -> Unit,
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
        for (scene in ExampleScenes) {
            CategorySection(
                scene = scene,
                query = query,
                onOpenScene = { onOpenScene(scene.route) },
                onOpenTab = { onOpenTab(scene.route) },
                onOpenComponent = onOpenComponent,
            )
            Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        }
        GalleryCard(
            onClick = { onOpenScene(ExampleRoute.Gallery) },
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.xl))
    }
}

/**
 * One component category: a header row with the scene entry and the category's component rows,
 * filtered by [query]. Hidden entirely when the query matches no component of the category.
 */
@Composable
private fun CategorySection(
    scene: Scene,
    query: String,
    onOpenScene: () -> Unit,
    onOpenTab: () -> Unit,
    onOpenComponent: (String) -> Unit,
) {
    val slugs = filterComponentIds(scene.slugs.toSet(), query)
    if (slugs.isEmpty()) return
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantSmallTitle(
            text = scene.title,
            modifier = Modifier.weight(1f),
        )
        val openScene = if (scene.route == ExampleRoute.Settings) onOpenTab else onOpenScene
        Text(
            text = "Open scene",
            style = ElegantTheme.typography.labelMedium,
            color = ElegantTheme.colors.interactivePrimary,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = openScene,
                )
                .padding(vertical = ElegantSpacing.sm),
        )
    }
    Spacer(modifier = Modifier.height(ElegantSpacing.sm))
    ElegantList(modifier = Modifier.fillMaxWidth()) {
        for (slug in slugs) {
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
}

/** Gallery entry card opening the showcase browser. */
@Composable
private fun GalleryCard(onClick: () -> Unit) {
    ElegantCard(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
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
}

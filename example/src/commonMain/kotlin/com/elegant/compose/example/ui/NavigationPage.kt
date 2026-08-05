// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.breadcrumb.ElegantBreadcrumb
import com.elegant.compose.ui.breadcrumb.ElegantBreadcrumbItem
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.floatingactionbutton.ElegantFloatingActionButton
import com.elegant.compose.ui.foundation.icons.ElegantIcons
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.nav.core.ElegantNavBackStack
import com.elegant.compose.ui.nav.core.ElegantNavDisplay
import com.elegant.compose.ui.nav.core.ElegantNavKey
import com.elegant.compose.ui.nav.core.rememberElegantNavBackStack
import com.elegant.compose.ui.navbar.ElegantNavbar
import com.elegant.compose.ui.navigationbar.ElegantNavigationBar
import com.elegant.compose.ui.navigationbar.ElegantNavigationBarItem
import com.elegant.compose.ui.navigationrail.ElegantNavigationRail
import com.elegant.compose.ui.navigationrail.ElegantNavigationRailItem
import com.elegant.compose.ui.pagination.ElegantPagination
import com.elegant.compose.ui.pulltorefresh.ElegantPullToRefresh
import com.elegant.compose.ui.scaffold.ElegantScaffold
import com.elegant.compose.ui.scrollbar.ElegantScrollBar
import com.elegant.compose.ui.scrollbar.ElegantScrollBarOrientation
import com.elegant.compose.ui.scrollshadow.ElegantScrollShadow
import com.elegant.compose.ui.sidebar.ElegantSidebar
import com.elegant.compose.ui.sidebar.ElegantSidebarItem
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle
import com.elegant.compose.ui.surface.ElegantSurface
import com.elegant.compose.ui.tabs.ElegantTab
import com.elegant.compose.ui.tabs.ElegantTabRow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

/**
 * Navigation scene: tabs, breadcrumbs, pagination, app bars, sidebar, the navigation bars, a
 * nested [ElegantNavDisplay] stack demo, pull-to-refresh, scroll feedback, and a scaffold.
 *
 * @param onBack callback popping this page from the back stack.
 */
@Composable
internal fun NavigationPage(onBack: () -> Unit) {
    ScenePage(title = "Navigation", onBack = onBack) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ElegantSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        ) {
            Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            TabsAndBars()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            StackDemo()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            ScrollFeedback()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            SidebarDemo()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            NavigationBars()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            ScaffoldDemo()
            Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        }
    }
}

/** Tabs, breadcrumb, pagination, and an app bar. */
@Composable
private fun TabsAndBars() {
    ElegantSmallTitle(text = "Tabs and bars")
    var tab by rememberSaveable { mutableIntStateOf(0) }
    ElegantTabRow(
        tabs = remember {
            listOf(
                ElegantTab(text = "Overview"),
                ElegantTab(text = "Activity"),
                ElegantTab(text = "Settings"),
            )
        },
        selectedIndex = tab,
        onSelect = { tab = it },
        modifier = Modifier.fillMaxWidth(),
    )
    var page by rememberSaveable { mutableIntStateOf(1) }
    ElegantBreadcrumb(
        items = remember {
            listOf(
                ElegantBreadcrumbItem(text = "Home"),
                ElegantBreadcrumbItem(text = "Library"),
                ElegantBreadcrumbItem(text = "Components"),
            )
        },
        modifier = Modifier.fillMaxWidth(),
    )
    ElegantPagination(
        page = page,
        onPageChange = { page = it },
        pageCount = 9,
        modifier = Modifier.fillMaxWidth(),
    )
    ElegantNavbar(
        title = { Text(text = "App Bar", style = ElegantTheme.typography.titleMedium) },
    )
}

/** A nested back stack demonstrating [ElegantNavDisplay] inside the scene. */
@Composable
private fun StackDemo() {
    ElegantSmallTitle(text = "Back stack")
    val backStack = rememberElegantNavBackStack<NavDemoRoute>(NavDemoRoute.Home)
    ElegantSurface(
        modifier = Modifier.fillMaxWidth().height(240.dp),
        borderWidth = 1.dp,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(ElegantSpacing.md),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
            ) {
                ElegantButton(
                    onClick = { pushNested(backStack, NavDemoRoute.Detail) },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Push")
                }
                ElegantButton(
                    onClick = { popNested(backStack) },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Pop")
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                ElegantNavDisplay(backStack = backStack) {
                    entry<NavDemoRoute.Home> {
                        NestedPage(
                            title = "Home",
                            hint = "The default slide transition",
                        )
                    }
                    entry<NavDemoRoute.Detail> {
                        NestedPage(
                            title = "Detail",
                            hint = "Swipe right to go back",
                        )
                    }
                }
            }
        }
    }
}

/** Private nested route hierarchy of the [StackDemo] display. */
@Serializable
private sealed interface NavDemoRoute : ElegantNavKey {
    @Serializable
    data object Home : NavDemoRoute

    @Serializable
    data object Detail : NavDemoRoute
}

private fun pushNested(backStack: ElegantNavBackStack, route: NavDemoRoute) {
    if (backStack.lastOrNull() != route) {
        backStack.add(route)
    }
}

private fun popNested(backStack: ElegantNavBackStack) {
    if (backStack.size > 1) {
        backStack.removeAt(backStack.lastIndex)
    }
}

/** One page of the nested [StackDemo] display. */
@Composable
private fun NestedPage(title: String, hint: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = if (title == "Home") ElegantTheme.colors.backgroundCanvas else ElegantTheme.colors.surfaceDefault)
            .padding(ElegantSpacing.md),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, style = ElegantTheme.typography.titleMedium)
            Text(
                text = hint,
                style = ElegantTheme.typography.bodyMedium,
                color = ElegantTheme.colors.textSecondary,
            )
        }
    }
}

/** Pull-to-refresh plus scroll bar and scroll shadow over a shared list. */
@Composable
private fun ScrollFeedback() {
    ElegantSmallTitle(text = "Scroll feedback")
    var refreshing by rememberSaveable { mutableStateOf(false) }
    var refreshCount by rememberSaveable { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    ElegantPullToRefresh(
        isRefreshing = refreshing,
        onRefresh = {
            scope.launch {
                refreshing = true
                delay(1200)
                refreshCount++
                refreshing = false
            }
        },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .verticalScroll(scrollState)
                .padding(horizontal = ElegantSpacing.md),
        ) {
            for (i in 1..24) {
                Text(
                    text = "Row $i",
                    style = ElegantTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = ElegantSpacing.sm),
                )
            }
        }
    }
    Text(
        text = "Pulled $refreshCount time(s) — scroll the list",
        style = ElegantTheme.typography.bodyMedium,
        color = ElegantTheme.colors.textSecondary,
    )
    Row(
        modifier = Modifier.fillMaxWidth().height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = ElegantSpacing.sm),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(end = ElegantSpacing.md),
            ) {
                repeat(8) {
                    Text(
                        text = "Shadow row $it",
                        style = ElegantTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = ElegantSpacing.sm),
                    )
                }
            }
            ElegantScrollShadow(state = scrollState)
        }
        Box(modifier = Modifier.width(24.dp).fillMaxHeight()) {
            ElegantScrollBar(
                state = scrollState,
                orientation = ElegantScrollBarOrientation.Vertical,
            )
        }
    }
}

/** A compact sidebar with a header and selectable entries. */
@Composable
private fun SidebarDemo() {
    ElegantSmallTitle(text = "Sidebar")
    var section by rememberSaveable { mutableIntStateOf(0) }
    Row(
        modifier = Modifier.fillMaxWidth().height(220.dp),
    ) {
        ElegantSidebar(
            selectedIndex = section,
            onSelect = { section = it },
            items = remember {
                listOf(
                    ElegantSidebarItem(text = "Overview"),
                    ElegantSidebarItem(text = "Analytics"),
                    ElegantSidebarItem(text = "Reports"),
                    ElegantSidebarItem(text = "Settings"),
                )
            },
            header = {
                Text(
                    text = "Workspace",
                    style = ElegantTheme.typography.titleMedium,
                    modifier = Modifier.padding(ElegantSpacing.md),
                )
            },
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(ElegantTheme.colors.backgroundCanvas)
                .padding(ElegantSpacing.md),
        ) {
            Text(
                text = "Section ${section + 1}",
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
}

/** Bottom navigation bar and rail side by side. */
@Composable
private fun NavigationBars() {
    ElegantSmallTitle(text = "Navigation bars")
    var barTab by rememberSaveable { mutableIntStateOf(0) }
    Column(modifier = Modifier.fillMaxWidth()) {
        ElegantNavigationBar(
            selectedIndex = barTab,
            onSelect = { barTab = it },
            items = remember {
                listOf(
                    ElegantNavigationBarItem(text = "Home", icon = ElegantIcons.Home),
                    ElegantNavigationBarItem(text = "Search", icon = ElegantIcons.Search),
                    ElegantNavigationBarItem(text = "Profile", icon = ElegantIcons.Person),
                )
            },
        )
        var railTab by rememberSaveable { mutableIntStateOf(0) }
        Row(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            ElegantNavigationRail(
                selectedIndex = railTab,
                onSelect = { railTab = it },
                items = remember {
                    listOf(
                        ElegantNavigationRailItem(text = "Home", icon = ElegantIcons.Home),
                        ElegantNavigationRailItem(text = "Gallery", icon = ElegantIcons.Image),
                        ElegantNavigationRailItem(text = "Settings", icon = ElegantIcons.Settings),
                    )
                },
                modifier = Modifier.fillMaxHeight(),
            )
        }
    }
}

/** A scaffold composing a top bar, content, and a floating action button. */
@Composable
private fun ScaffoldDemo() {
    ElegantSmallTitle(text = "Scaffold")
    ElegantScaffold(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        topBar = {
            ElegantNavbar(title = { Text("Scaffold", style = ElegantTheme.typography.titleMedium) })
        },
        floatingActionButton = {
            ElegantFloatingActionButton(onClick = {}) {
                Text("+")
            }
        },
    ) { innerPadding ->
        Text(
            text = "Content respects the bars through innerPadding",
            modifier = Modifier.padding(innerPadding).padding(ElegantSpacing.md),
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}

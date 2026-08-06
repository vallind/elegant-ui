// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.elegant.compose.example.ui.ButtonsPage
import com.elegant.compose.example.ui.ColorsPage
import com.elegant.compose.example.ui.ComponentPage
import com.elegant.compose.example.ui.DisplayPage
import com.elegant.compose.example.ui.FeedbackPage
import com.elegant.compose.example.ui.GalleryPage
import com.elegant.compose.example.ui.HomePage
import com.elegant.compose.example.ui.IconsPage
import com.elegant.compose.example.ui.InputsPage
import com.elegant.compose.example.ui.NavigationPage
import com.elegant.compose.example.ui.OverlaysPage
import com.elegant.compose.example.ui.SettingsPage
import com.elegant.compose.example.ui.TypographyPage
import com.elegant.compose.ui.foundation.icons.Brightness
import com.elegant.compose.ui.foundation.icons.ElegantIcons
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.nav.core.ElegantNavBackStack
import com.elegant.compose.ui.nav.core.ElegantNavDisplay
import com.elegant.compose.ui.nav.core.rememberElegantNavBackStack
import com.elegant.compose.ui.navigationbar.ElegantNavigationBar
import com.elegant.compose.ui.navigationbar.ElegantNavigationBarItem
import com.elegant.compose.ui.navigationrail.ElegantNavigationRail
import com.elegant.compose.ui.navigationrail.ElegantNavigationRailItem

/**
 * Tab destinations surfaced by the bottom navigation bar on narrow windows and the navigation
 * rail on wide windows, mirroring the five-page structure of the reference example: a home page
 * plus the icon, color, and typography rosters and settings. The scene pages are reached from the
 * home page's category sections.
 */
internal val ExampleTabs: List<ExampleRoute> = listOf(
    ExampleRoute.Home,
    ExampleRoute.Icons,
    ExampleRoute.Colors,
    ExampleRoute.Typography,
    ExampleRoute.Settings,
)

/** Icon of a tab destination; non-tab routes fall back to the home glyph. */
internal fun ExampleRoute.tabIcon(): ImageVector = when (this) {
    ExampleRoute.Home -> ElegantIcons.Home
    ExampleRoute.Icons -> ElegantIcons.Star
    ExampleRoute.Colors -> ElegantIcons.Brightness
    ExampleRoute.Typography -> ElegantIcons.Edit
    ExampleRoute.Settings -> ElegantIcons.Settings
    else -> ElegantIcons.Home
}

/**
 * Index of the deepest tab destination present on the stack, used as the selected tab while a
 * sub-page (a scene or component detail) is on top; 0 when no tab is on the stack.
 */
internal fun selectedTabIndex(backStack: ElegantNavBackStack, tabs: List<ExampleRoute>): Int {
    for (i in backStack.indices.reversed()) {
        val index = tabs.indexOf(backStack[i])
        if (index >= 0) return index
    }
    return 0
}

/**
 * Adaptive shell of the complete app example.
 *
 * Renders the shared back stack through [ElegantNavDisplay]. Narrow windows show a bottom
 * [ElegantNavigationBar]; wide windows replace it with a leading [ElegantNavigationRail]. All ten
 * routes of [ExampleRoute] are registered so the showcase pages and scene pages share one stack.
 */
@Composable
public fun AppContent() {
    val backStack = rememberElegantNavBackStack<ExampleRoute>(ExampleRoute.Home)
    val tabs = remember { ExampleTabs }
    val tabItems = remember(tabs) { tabs.map { it.tabIcon() to it.label() } }
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        if (maxWidth >= 600.dp) {
            Row(modifier = Modifier.fillMaxSize()) {
                ElegantNavigationRail(
                    selectedIndex = selectedTabIndex(backStack, tabs),
                    onSelect = { navigateToTab(backStack, tabs[it]) },
                    items = remember(tabItems) {
                        tabItems.map { (icon, label) -> ElegantNavigationRailItem(text = label, icon = icon) }
                    },
                    modifier = Modifier.fillMaxHeight(),
                    header = {
                        Text(
                            text = "Elegant",
                            style = ElegantTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(
                                horizontal = ElegantSpacing.lg,
                                vertical = ElegantSpacing.xl,
                            ),
                        )
                    },
                )
                Box(modifier = Modifier.weight(1f)) {
                    ExampleNavDisplay(backStack = backStack)
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    ExampleNavDisplay(backStack = backStack)
                }
                ElegantNavigationBar(
                    selectedIndex = selectedTabIndex(backStack, tabs),
                    onSelect = { navigateToTab(backStack, tabs[it]) },
                    items = remember(tabItems) {
                        tabItems.map { (icon, label) -> ElegantNavigationBarItem(text = label, icon = icon) }
                    },
                )
            }
        }
    }
}

/** Registers every [ExampleRoute] on the shared [ElegantNavDisplay] instance. */
@Composable
private fun ExampleNavDisplay(backStack: ElegantNavBackStack) {
    ElegantNavDisplay(backStack = backStack) {
        entry<ExampleRoute.Home> {
            HomePage(
                onOpenScene = { pushIdempotent(backStack, it) },
                onOpenTab = { navigateToTab(backStack, it) },
                onOpenComponent = { pushIdempotent(backStack, ExampleRoute.Component(it)) },
            )
        }
        entry<ExampleRoute.Icons> {
            IconsPage()
        }
        entry<ExampleRoute.Colors> {
            ColorsPage()
        }
        entry<ExampleRoute.Typography> {
            TypographyPage()
        }
        entry<ExampleRoute.Inputs> {
            InputsPage(onBack = { popBackStack(backStack) })
        }
        entry<ExampleRoute.Buttons> {
            ButtonsPage(onBack = { popBackStack(backStack) })
        }
        entry<ExampleRoute.Display> {
            DisplayPage(onBack = { popBackStack(backStack) })
        }
        entry<ExampleRoute.Feedback> {
            FeedbackPage(onBack = { popBackStack(backStack) })
        }
        entry<ExampleRoute.Navigation> {
            NavigationPage(onBack = { popBackStack(backStack) })
        }
        entry<ExampleRoute.Overlays> {
            OverlaysPage(onBack = { popBackStack(backStack) })
        }
        entry<ExampleRoute.Settings> {
            SettingsPage(onBack = { popBackStack(backStack) })
        }
        entry<ExampleRoute.Gallery> {
            GalleryPage(onBack = { popBackStack(backStack) })
        }
        entry<ExampleRoute.Component> { route ->
            ComponentPage(slug = route.slug, onBack = { popBackStack(backStack) })
        }
    }
}

// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.button.ElegantButtonSize
import com.elegant.compose.ui.button.ElegantButtonStyle
import com.elegant.compose.ui.buttongroup.ElegantButtonGroup
import com.elegant.compose.ui.buttongroup.ElegantButtonGroupItem
import com.elegant.compose.ui.closebutton.ElegantCloseButton
import com.elegant.compose.ui.floatingactionbutton.ElegantFloatingActionButton
import com.elegant.compose.ui.floatingtoolbar.ElegantFloatingToolbar
import com.elegant.compose.ui.foundation.icons.ElegantIcons
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.iconbutton.ElegantIconButton
import com.elegant.compose.ui.link.ElegantLink
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle
import com.elegant.compose.ui.tag.ElegantTag
import com.elegant.compose.ui.tag.ElegantTagStyle
import com.elegant.compose.ui.taggroup.ElegantTagGroup
import com.elegant.compose.ui.taggroup.ElegantTagGroupItem
import com.elegant.compose.ui.togglebutton.ElegantToggleButton
import com.elegant.compose.ui.toolbar.ElegantToolbar

/**
 * Buttons scene: a realistic action area with every button style, grouped and icon actions, tag
 * filters, toolbars, and a floating action button.
 *
 * @param onBack callback popping this page from the back stack.
 */
@Composable
internal fun ButtonsPage(onBack: () -> Unit) {
    ScenePage(title = "Buttons", onBack = onBack) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ElegantSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        ) {
            Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            ActionButtons()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            GroupedActions()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            IconActions()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            TagActions()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            Toolbars()
            Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        }
    }
}

/** The three button styles at two sizes, plus loading and disabled states. */
@Composable
private fun ActionButtons() {
    ElegantSmallTitle(text = "Actions")
    var loading by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
    ) {
        ElegantButton(
            onClick = {},
            modifier = Modifier.weight(1f),
            style = ElegantButtonStyle.Primary,
        ) {
            Text("Primary")
        }
        ElegantButton(
            onClick = {},
            modifier = Modifier.weight(1f),
            style = ElegantButtonStyle.Secondary,
        ) {
            Text("Secondary")
        }
        ElegantButton(
            onClick = {},
            modifier = Modifier.weight(1f),
            style = ElegantButtonStyle.Tertiary,
        ) {
            Text("Tertiary")
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
    ) {
        ElegantButton(
            onClick = { loading = !loading },
            modifier = Modifier.weight(1f),
            loading = loading,
            leadingIcon = {
                Icon(imageVector = ElegantIcons.Refresh, contentDescription = null)
            },
        ) {
            Text(if (loading) "Working" else "Loading")
        }
        ElegantButton(
            onClick = {},
            modifier = Modifier.weight(1f),
            enabled = false,
        ) {
            Text("Disabled")
        }
        ElegantButton(
            onClick = {},
            modifier = Modifier.weight(1f),
            size = ElegantButtonSize.Small,
            style = ElegantButtonStyle.Secondary,
        ) {
            Text("Small")
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantButton(
            onClick = {},
            style = ElegantButtonStyle.Secondary,
        ) {
            Text("Save")
        }
        ElegantTag(style = ElegantTagStyle.Outlined) {
            Text("Auto-saved")
        }
        ElegantLink(
            text = "Forgot password?",
            onClick = {},
        )
    }
}

/** Button group and toggle buttons. */
@Composable
private fun GroupedActions() {
    ElegantSmallTitle(text = "Grouped actions")
    var group by remember { mutableStateOf<Int?>(0) }
    ElegantButtonGroup(
        selectedIndex = group,
        onSelect = { group = it },
        items = remember {
            listOf(
                ElegantButtonGroupItem(text = "Day"),
                ElegantButtonGroupItem(text = "Week"),
                ElegantButtonGroupItem(text = "Month"),
            )
        },
    )
    var toggled by rememberSaveable { mutableStateOf(false) }
    ElegantToggleButton(
        selected = toggled,
        onToggle = { toggled = it },
    ) {
        Text(if (toggled) "Starred" else "Star")
    }
    var favorite by rememberSaveable { mutableStateOf(true) }
    ElegantToggleButton(
        selected = favorite,
        onToggle = { favorite = it },
    ) {
        Icon(imageVector = ElegantIcons.Star, contentDescription = null)
    }
}

/** Icon-only actions and the floating action button. */
@Composable
private fun IconActions() {
    ElegantSmallTitle(text = "Icon actions")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantIconButton(onClick = {}, contentDescription = "Share") {
            Icon(imageVector = ElegantIcons.Share, contentDescription = null)
        }
        ElegantIconButton(onClick = {}, contentDescription = "Edit") {
            Icon(imageVector = ElegantIcons.Edit, contentDescription = null)
        }
        ElegantIconButton(onClick = {}, contentDescription = "Delete") {
            Icon(imageVector = ElegantIcons.Delete, contentDescription = null)
        }
        ElegantCloseButton(onClick = {})
        ElegantFloatingActionButton(onClick = {}) {
            Icon(imageVector = ElegantIcons.Plus, contentDescription = null)
        }
    }
}

/** Tag group filters and standalone tags. */
@Composable
private fun TagActions() {
    ElegantSmallTitle(text = "Tag filters")
    var filters by remember { mutableStateOf(setOf("All")) }
    ElegantTagGroup(
        selectedValues = filters,
        onToggle = { value, checked ->
            filters = if (checked) filters + value else filters - value
        },
        items = remember {
            listOf(
                ElegantTagGroupItem(text = "All", value = "All"),
                ElegantTagGroupItem(text = "Design", value = "Design"),
                ElegantTagGroupItem(text = "Engineering", value = "Engineering"),
                ElegantTagGroupItem(text = "Marketing", value = "Marketing"),
            )
        },
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
    ) {
        ElegantTag { Text("New") }
        ElegantTag(style = ElegantTagStyle.Outlined) { Text("v0.2.0") }
        ElegantTag(
            selected = true,
            style = ElegantTagStyle.Filled,
        ) {
            Text("Stable")
        }
    }
}

/** A top toolbar and a floating toolbar with common actions. */
@Composable
private fun Toolbars() {
    ElegantSmallTitle(text = "Toolbars")
    var toolbarCount by rememberSaveable { mutableIntStateOf(0) }
    ElegantToolbar {
        ElegantIconButton(
            onClick = { toolbarCount-- },
            contentDescription = "Undo",
        ) {
            Icon(imageVector = ElegantIcons.ArrowLeft, contentDescription = null)
        }
        Text(
            text = "Draft $toolbarCount",
            modifier = Modifier.weight(1f).padding(start = ElegantSpacing.md),
        )
        ElegantIconButton(
            onClick = { toolbarCount++ },
            contentDescription = "Redo",
        ) {
            Icon(imageVector = ElegantIcons.ArrowRight, contentDescription = null)
        }
        ElegantIconButton(onClick = {}, contentDescription = "More") {
            Icon(imageVector = ElegantIcons.MoreVert, contentDescription = null)
        }
    }
    ElegantFloatingToolbar {
        ElegantIconButton(onClick = {}, contentDescription = "Bold") {
            Text("B")
        }
        ElegantIconButton(onClick = {}, contentDescription = "Italic") {
            Text("I")
        }
        ElegantIconButton(onClick = {}, contentDescription = "Link") {
            Text("L")
        }
    }
}

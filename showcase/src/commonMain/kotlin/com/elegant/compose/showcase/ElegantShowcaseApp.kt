package com.elegant.compose.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elegant.compose.showcase.generated.resources.Res
import com.elegant.compose.showcase.generated.resources.add_rounded
import com.elegant.compose.showcase.generated.resources.arrow_forward_rounded
import com.elegant.compose.showcase.generated.resources.check_rounded
import com.elegant.compose.showcase.generated.resources.delete_rounded
import com.elegant.compose.showcase.generated.resources.edit_rounded
import com.elegant.compose.showcase.generated.resources.more_vert_rounded
import com.elegant.compose.showcase.generated.resources.person_rounded
import com.elegant.compose.showcase.generated.resources.share_rounded
import com.elegant.compose.ui.avatar.ElegantAvatar
import com.elegant.compose.ui.avatar.ElegantAvatarColors
import com.elegant.compose.ui.avatar.ElegantAvatarSize
import com.elegant.compose.ui.badge.ElegantBadge
import com.elegant.compose.ui.badge.ElegantBadgeBox
import com.elegant.compose.ui.badge.ElegantBadgeDot
import com.elegant.compose.ui.badge.ElegantBadgePlacement
import com.elegant.compose.ui.badge.ElegantBadgeSize
import com.elegant.compose.ui.badge.ElegantBadgeStyle
import com.elegant.compose.ui.badge.ElegantCountBadge
import com.elegant.compose.ui.breadcrumb.ElegantBreadcrumb
import com.elegant.compose.ui.breadcrumb.ElegantBreadcrumbItem
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.button.ElegantButtonSize
import com.elegant.compose.ui.button.ElegantButtonStyle
import com.elegant.compose.ui.card.ElegantCard
import com.elegant.compose.ui.card.ElegantCardStyle
import com.elegant.compose.ui.checkbox.ElegantCheckbox
import com.elegant.compose.ui.divider.ElegantDivider
import com.elegant.compose.ui.divider.ElegantDividerEmphasis
import com.elegant.compose.ui.divider.ElegantDividerLabelPosition
import com.elegant.compose.ui.divider.ElegantDividerOrientation
import com.elegant.compose.ui.divider.ElegantDividerStyle
import com.elegant.compose.ui.divider.ElegantLabeledDivider
import com.elegant.compose.ui.drawer.ElegantDrawer
import com.elegant.compose.ui.drawer.ElegantDrawerPlacement
import com.elegant.compose.ui.emptystate.ElegantEmptyState
import com.elegant.compose.ui.iconbutton.ElegantIconButton
import com.elegant.compose.ui.iconbutton.ElegantIconButtonSize
import com.elegant.compose.ui.iconbutton.ElegantIconButtonStyle
import com.elegant.compose.ui.input.ElegantInput
import com.elegant.compose.ui.input.ElegantInputStyle
import com.elegant.compose.ui.list.ElegantList
import com.elegant.compose.ui.list.ElegantListItem
import com.elegant.compose.ui.modal.ElegantModal
import com.elegant.compose.ui.navbar.ElegantNavbar
import com.elegant.compose.ui.radio.ElegantRadio
import com.elegant.compose.ui.select.ElegantSelect
import com.elegant.compose.ui.select.ElegantSelectOption
import com.elegant.compose.ui.sidebar.ElegantSidebar
import com.elegant.compose.ui.sidebar.ElegantSidebarItem
import com.elegant.compose.ui.slider.ElegantSlider
import com.elegant.compose.ui.switch.ElegantSwitch
import com.elegant.compose.ui.table.ElegantTable
import com.elegant.compose.ui.table.ElegantTableColumn
import com.elegant.compose.ui.table.ElegantTableRow
import com.elegant.compose.ui.tabs.ElegantTab
import com.elegant.compose.ui.tabs.ElegantTabRow
import com.elegant.compose.ui.tag.ElegantTag
import com.elegant.compose.ui.tag.ElegantTagSize
import com.elegant.compose.ui.tag.ElegantTagStyle
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme
import com.elegant.compose.ui.tooltip.ElegantTooltip
import com.elegant.compose.ui.tooltip.ElegantTooltipBox
import com.elegant.compose.ui.tooltip.ElegantTooltipPlacement
import kotlin.math.roundToInt
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

internal val SupportedShowcaseComponentIds: Set<String> =
    setOf(
        "button",
        "icon-button",
        "avatar",
        "badge",
        "divider",
        "tooltip",
        "input",
        "checkbox",
        "radio",
        "switch",
        "slider",
        "select",
        "card",
        "list",
        "empty-state",
        "modal",
        "drawer",
        "table",
        "tabs",
        "breadcrumb",
        "navbar",
        "sidebar",
        "tag",
    )

/**
 * Shared component showcase used by Android, Desktop JVM, and Web/Wasm launchers.
 *
 * [componentId] selects the component page requested by the documentation iframe. Unknown values
 * render a clear fallback instead of silently displaying an unrelated component.
 */
@Composable
public fun ElegantShowcaseApp(
    componentId: String = "button",
) {
    when (componentId) {
        "button" -> ButtonShowcase()
        "icon-button" -> IconButtonShowcase()
        "avatar" -> AvatarShowcase()
        "badge" -> BadgeShowcase()
        "divider" -> DividerShowcase()
        "tooltip" -> TooltipShowcase()
        "input" -> InputShowcase()
        "checkbox" -> CheckboxShowcase()
        "radio" -> RadioShowcase()
        "switch" -> SwitchShowcase()
        "slider" -> SliderShowcase()
        "select" -> SelectShowcase()
        "card" -> CardShowcase()
        "list" -> ListShowcase()
        "empty-state" -> EmptyStateShowcase()
        "modal" -> ModalShowcase()
        "drawer" -> DrawerShowcase()
        "table" -> TableShowcase()
        "tabs" -> TabsShowcase()
        "breadcrumb" -> BreadcrumbShowcase()
        "navbar" -> NavbarShowcase()
        "sidebar" -> SidebarShowcase()
        "tag" -> TagShowcase()
        else -> UnknownComponent(componentId)
    }
}

@Composable
private fun ButtonShowcase() {
    var loading by rememberSaveable { mutableStateOf(false) }
    var tapCount by remember { mutableIntStateOf(0) }

    ShowcasePage(title = "Elegant Button") { compact ->
        val colors = ElegantTheme.colors
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Clear action hierarchy",
            description = "Three emphasis levels share one optical rhythm across every target.",
        ) {
            Text(
                text = "Size scale",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            SizeRow(
                style = ElegantButtonStyle.Primary,
                onClick = { tapCount++ },
            )

            Text(
                text = "Emphasis",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            ) {
                ElegantButton(
                    onClick = { tapCount++ },
                    leadingIcon = { ResourceIcon(Res.drawable.add_rounded) },
                ) {
                    Text("Create")
                }
                ElegantButton(
                    onClick = { tapCount++ },
                    style = ElegantButtonStyle.Secondary,
                ) {
                    Text("Review")
                }
                ElegantButton(
                    onClick = { tapCount++ },
                    style = ElegantButtonStyle.Tertiary,
                    trailingIcon = { ForwardIcon() },
                ) {
                    Text("Learn more")
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Ready to publish",
            description = "Actions stay balanced inside a realistic confirmation surface.",
        ) {
            ConfirmationSurface()

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            ) {
                ElegantButton(
                    onClick = { tapCount++ },
                    style = ElegantButtonStyle.Secondary,
                ) {
                    Text("Save draft")
                }
                ElegantButton(
                    onClick = { loading = !loading },
                    loading = loading,
                    loadingStateDescription = "Publishing",
                    leadingIcon = { ResourceIcon(Res.drawable.check_rounded) },
                ) {
                    Text("Publish changes")
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Interaction feedback",
            description = "Hover, press, keyboard focus, loading, and disabled states remain distinct.",
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            ) {
                ElegantButton(
                    onClick = { tapCount++ },
                    style = ElegantButtonStyle.Secondary,
                    leadingIcon = { ResourceIcon(Res.drawable.add_rounded) },
                    trailingIcon = { ForwardIcon() },
                ) {
                    Text("With icons")
                }
                ElegantButton(
                    onClick = {},
                    enabled = false,
                ) {
                    Text("Disabled")
                }
            }

            Text(
                text = "Accepted actions  $tapCount",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun IconButtonShowcase() {
    var loading by rememberSaveable { mutableStateOf(false) }
    var actionCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(loading) {
        if (loading) {
            delay(900)
            loading = false
        }
    }

    ShowcasePage(title = "Elegant IconButton") { compact ->
        val colors = ElegantTheme.colors
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Compact actions, one rhythm",
            description = "Three sizes and emphasis levels preserve a 48dp interaction target.",
        ) {
            Text(
                text = "Size scale",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            IconButtonSizeRow(onClick = { actionCount++ })

            Text(
                text = "Emphasis",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            ) {
                ShowcaseIconButton(
                    resource = Res.drawable.edit_rounded,
                    contentDescription = "Edit",
                    style = ElegantIconButtonStyle.Primary,
                    onClick = { actionCount++ },
                )
                ShowcaseIconButton(
                    resource = Res.drawable.share_rounded,
                    contentDescription = "Share",
                    style = ElegantIconButtonStyle.Secondary,
                    onClick = { actionCount++ },
                )
                ShowcaseIconButton(
                    resource = Res.drawable.more_vert_rounded,
                    contentDescription = "More options",
                    style = ElegantIconButtonStyle.Tertiary,
                    onClick = { actionCount++ },
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A toolbar that stays quiet",
            description = "Icon actions support nearby content without competing with it.",
        ) {
            CompactToolbar(
                compact = compact,
                onAction = { actionCount++ },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Feedback without ambiguity",
            description = "Loading and disabled actions retain their geometry and accessible name.",
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            ) {
                ElegantIconButton(
                    onClick = { loading = true },
                    contentDescription = "Refresh actions",
                    loading = loading,
                    loadingStateDescription = "Refreshing actions",
                    style = ElegantIconButtonStyle.Primary,
                ) {
                    ResourceIcon(Res.drawable.check_rounded)
                }
                ShowcaseIconButton(
                    resource = Res.drawable.delete_rounded,
                    contentDescription = "Delete",
                    enabled = false,
                    style = ElegantIconButtonStyle.Secondary,
                    onClick = {},
                )
            }

            Text(
                text = "Accepted actions  $actionCount",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun AvatarShowcase() {
    ShowcasePage(title = "Elegant Avatar") { compact ->
        val colors = ElegantTheme.colors
        val accentColors = ElegantAvatarColors(
            containerColor = colors.interactivePrimary,
            contentColor = colors.textInverse,
            borderColor = colors.interactivePrimaryPressed,
        )
        val neutralColors = ElegantAvatarColors(
            containerColor = colors.surfaceSunken,
            contentColor = colors.textSecondary,
            borderColor = colors.borderDefault,
        )

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Identity at every density",
            description = "Three optical sizes keep initials centered and recognizable.",
        ) {
            Text(
                text = "Size scale",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            AvatarSizeRow()

            Text(
                text = "Tone",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantAvatar(name = "Ada Lovelace")
                ElegantAvatar(
                    name = "Noah Williams",
                    colors = accentColors,
                )
                ElegantAvatar(
                    name = "林晓",
                    colors = neutralColors,
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A calm team roster",
            description = "Identity, supporting text, and compact actions stay visually balanced.",
        ) {
            TeamMemberRow(
                name = "Maya Chen",
                role = "Product designer",
                avatarColors = accentColors,
            )
            TeamMemberRow(
                name = "Elliot Stone",
                role = "Compose engineer",
                avatarColors = neutralColors,
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "FALLBACKS",
            title = "Useful before an image arrives",
            description = "Generated initials, explicit labels, and custom visual content share one frame.",
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantAvatar(name = "Grace Hopper")
                ElegantAvatar(
                    name = "Elegant UI",
                    initials = "EU",
                    colors = accentColors,
                )
                ElegantAvatar(
                    name = "Guest",
                    contentDescription = "Guest profile",
                    colors = neutralColors,
                ) {
                    ResourceIcon(
                        resource = Res.drawable.person_rounded,
                        modifier = Modifier.size(22.dp),
                    )
                }
                ElegantAvatar(
                    name = "Decorative workspace",
                    contentDescription = null,
                    colors = neutralColors,
                ) {
                    Text(
                        text = "◆",
                        color = colors.textSecondary,
                        style = ElegantTheme.typography.labelLarge,
                    )
                }
            }

            Text(
                text = "Custom content remains clipped by the same shape and owns no duplicate label.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun BadgeShowcase() {
    var updateCount by rememberSaveable { mutableIntStateOf(3) }

    ShowcasePage(title = "Elegant Badge") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Status with precise emphasis",
            description = "Five semantic tones and three optical sizes stay compact without becoming vague.",
        ) {
            Text(
                text = "Semantic styles",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            BadgeStyleRow()

            Text(
                text = "Size scale",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            BadgeSizeRow()
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Signals that support content",
            description = "Presence and counts compose around existing controls without changing their measured size.",
        ) {
            BadgeCompositionPreview(updateCount = updateCount)

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            ) {
                ElegantButton(
                    onClick = { updateCount = (updateCount + 1).coerceAtMost(12) },
                    size = ElegantButtonSize.Small,
                ) {
                    Text("Add update")
                }
                ElegantButton(
                    onClick = { updateCount = 0 },
                    style = ElegantButtonStyle.Secondary,
                    size = ElegantButtonSize.Small,
                ) {
                    Text("Clear")
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "MODES",
            title = "Dots, counts, and logical corners",
            description = "Zero visibility, overflow formatting, and start/end placement remain predictable.",
        ) {
            Text(
                text = "Count boundaries",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantCountBadge(count = 0, showZero = true)
                ElegantCountBadge(count = 8)
                ElegantCountBadge(count = 42, style = ElegantBadgeStyle.Positive)
                ElegantCountBadge(count = 120, style = ElegantBadgeStyle.Critical)
            }

            Text(
                text = "Logical placement",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xl),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xl),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                BadgePlacementPreview(ElegantBadgePlacement.TopStart)
                BadgePlacementPreview(ElegantBadgePlacement.TopEnd)
                BadgePlacementPreview(ElegantBadgePlacement.BottomStart)
                BadgePlacementPreview(ElegantBadgePlacement.BottomEnd)
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun DividerShowcase() {
    ShowcasePage(title = "Elegant Divider") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Separation without visual noise",
            description = "Subtle and strong emphasis combine with solid or dashed strokes on the same semantic rhythm.",
        ) {
            Text(
                text = "Subtle solid",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantDivider()

            Text(
                text = "Strong solid",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantDivider(emphasis = ElegantDividerEmphasis.Strong)

            Text(
                text = "Subtle dashed",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantDivider(style = ElegantDividerStyle.Dashed)
        }

        DemoCard(
            compact = compact,
            eyebrow = "LABELS",
            title = "Logical alignment with owned rhythm",
            description = "Labels stay optically centered, preserve their own semantics, and mirror start/end placement in RTL.",
        ) {
            for (position in ElegantDividerLabelPosition.entries) {
                ElegantLabeledDivider(labelPosition = position) {
                    Text(position.name)
                }
            }

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                ElegantLabeledDivider(
                    labelPosition = ElegantDividerLabelPosition.Start,
                    style = ElegantDividerStyle.Dashed,
                    emphasis = ElegantDividerEmphasis.Strong,
                ) {
                    Text("RTL · Start")
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Structure that supports content",
            description = "Horizontal and vertical boundaries compose with existing identity components without becoming interactive.",
        ) {
            DividerRosterPreview()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .background(
                        color = colors.backgroundSubtle,
                        shape = RoundedCornerShape(ElegantRadius.md),
                    )
                    .padding(ElegantSpacing.lg),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DividerMetric(
                    value = "24",
                    label = "Components",
                    modifier = Modifier.weight(1f),
                )
                ElegantDivider(
                    modifier = Modifier.fillMaxHeight(),
                    orientation = ElegantDividerOrientation.Vertical,
                    emphasis = ElegantDividerEmphasis.Strong,
                )
                DividerMetric(
                    value = "3",
                    label = "Platforms",
                    modifier = Modifier.weight(1f),
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun TagShowcase() {
    var filters by rememberSaveable {
        mutableStateOf(setOf("Design", "Multiplatform"))
    }
    var tone by rememberSaveable { mutableStateOf("Refined") }

    ShowcasePage(title = "Elegant Tag") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Classification with one rhythm",
            description = "Four visual variants and three optical sizes share the same semantic states.",
        ) {
            Text(
                text = "Styles",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            TagStyleRow()

            Text(
                text = "Size scale",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            TagSizeRow()
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Filters that stay selectable",
            description = "Selectable tags announce their state and keep a 48dp interaction target.",
        ) {
            Text(
                text = "Filter releases",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                for (candidate in listOf("Design", "Engineering", "Multiplatform", "Release")) {
                    val selected = candidate in filters
                    ElegantTag(
                        onClick = {
                            filters = if (selected) {
                                filters - candidate
                            } else {
                                filters + candidate
                            }
                        },
                        selected = selected,
                        style = if (selected) {
                            ElegantTagStyle.Filled
                        } else {
                            ElegantTagStyle.Outlined
                        },
                        leadingContent = if (selected) {
                            {
                                BadgeDot(color = colors.textInverse)
                            }
                        } else {
                            null
                        },
                    ) {
                        Text(candidate)
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Selected, disabled, and quiet",
            description = "Selection, disabled, and non-interactive modes remain visually distinct.",
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantTag(
                    onClick = { tone = "Refined" },
                    selected = tone == "Refined",
                ) {
                    Text("Refined")
                }
                ElegantTag(
                    onClick = { tone = "Vibrant" },
                    selected = tone == "Vibrant",
                    style = ElegantTagStyle.Filled,
                ) {
                    Text("Vibrant")
                }
                ElegantTag(
                    onClick = { tone = "Quiet" },
                    selected = tone == "Quiet",
                    style = ElegantTagStyle.Plain,
                ) {
                    Text("Quiet")
                }
                ElegantTag(
                    onClick = {},
                    enabled = false,
                    selected = false,
                ) {
                    Text("Disabled")
                }
                ElegantTag(style = ElegantTagStyle.Plain) {
                    Text("Read-only")
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun TooltipShowcase() {
    ShowcasePage(title = "Elegant Tooltip") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "PLACEMENTS",
            title = "Logical around the anchor",
            description = "Top, bottom, start, and end placements hover, focus, or long-press their compact controls.",
        ) {
            Text(
                text = "Hover, focus, or long-press an action to reveal its tooltip.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
            ) {
                TooltipPlacementPreview(ElegantTooltipPlacement.Top)
                TooltipPlacementPreview(ElegantTooltipPlacement.Bottom)
                TooltipPlacementPreview(ElegantTooltipPlacement.Start)
                TooltipPlacementPreview(ElegantTooltipPlacement.End)
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A toolbar that explains itself",
            description = "Icon-only actions carry short labels without permanent clutter.",
        ) {
            TooltipToolbar(compact = compact)
        }

        DemoCard(
            compact = compact,
            eyebrow = "VARIANT",
            title = "The standard tooltip surface",
            description = "ElegantTooltip renders the raised label surface for the popup slot or inline.",
        ) {
            Text(
                text = "Inline surface",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantTooltip(text = "This is an ElegantTooltip surface")
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun TooltipPlacementPreview(
    placement: ElegantTooltipPlacement,
) {
    ElegantTooltipBox(
        tooltip = {
            ElegantTooltip(text = "${placement.name} placement")
        },
        placement = placement,
    ) {
        ElegantButton(
            onClick = {},
            style = ElegantButtonStyle.Secondary,
            size = ElegantButtonSize.Small,
        ) {
            Text(placement.name)
        }
    }
}

@Composable
private fun TooltipToolbar(
    compact: Boolean,
) {
    val colors = ElegantTheme.colors
    val content: @Composable (Modifier) -> Unit = { modifier ->
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs),
        ) {
            Text(
                text = "Release notes",
                color = colors.textPrimary,
                style = ElegantTheme.typography.titleMedium,
            )
            Text(
                text = "Updated just now",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
    val actions: @Composable () -> Unit = {
        Row(
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TooltipIconButton(
                resource = Res.drawable.edit_rounded,
                contentDescription = "Edit release notes",
                tooltipText = "Edit",
                style = ElegantIconButtonStyle.Secondary,
            )
            TooltipIconButton(
                resource = Res.drawable.share_rounded,
                contentDescription = "Share release notes",
                tooltipText = "Share",
            )
            TooltipIconButton(
                resource = Res.drawable.more_vert_rounded,
                contentDescription = "More release note actions",
                tooltipText = "More",
            )
        }
    }

    if (compact) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colors.surfaceHover,
                    shape = RoundedCornerShape(ElegantRadius.md),
                )
                .padding(ElegantSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        ) {
            content(Modifier.fillMaxWidth())
            actions()
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colors.surfaceHover,
                    shape = RoundedCornerShape(ElegantRadius.md),
                )
                .padding(ElegantSpacing.lg),
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content(Modifier.weight(1f))
            actions()
        }
    }
}

@Composable
private fun TooltipIconButton(
    resource: DrawableResource,
    contentDescription: String,
    tooltipText: String,
    style: ElegantIconButtonStyle = ElegantIconButtonStyle.Tertiary,
) {
    ElegantTooltipBox(
        tooltip = {
            ElegantTooltip(text = tooltipText)
        },
    ) {
        ShowcaseIconButton(
            resource = resource,
            contentDescription = contentDescription,
            style = style,
            onClick = {},
        )
    }
}

@Composable
private fun InputShowcase() {
    var message by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var coupon by rememberSaveable { mutableStateOf("") }
    var nickname by rememberSaveable { mutableStateOf("") }
    var bio by rememberSaveable { mutableStateOf("") }
    var query by rememberSaveable { mutableStateOf("") }

    ShowcasePage(title = "Elegant Input") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Label, placeholder, and guidance",
            description = "A labeled field with supporting text keeps forms self-explanatory.",
        ) {
            ElegantInput(
                value = message,
                onValueChange = { message = it },
                label = "Message",
                placeholder = "Write something kind",
                supportingText = "Keep it short and specific.",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STYLES",
            title = "Filled and Outlined with icons",
            description = "Both variants share one 48dp field height and the same interaction states.",
        ) {
            ElegantInput(
                value = email,
                onValueChange = { email = it },
                label = "Email address",
                placeholder = "you@example.com",
                leadingIcon = { ResourceIcon(Res.drawable.person_rounded) },
            )
            ElegantInput(
                value = coupon,
                onValueChange = { coupon = it },
                label = "Coupon code",
                placeholder = "Try SUMMER20",
                style = ElegantInputStyle.Outlined,
                trailingIcon = { ResourceIcon(Res.drawable.check_rounded) },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Enabled, disabled, read-only, and error",
            description = "Semantic states stay visible without losing the field geometry.",
        ) {
            ElegantInput(
                value = "Maya Chen",
                onValueChange = {},
                label = "Display name",
                supportingText = "Shown to other members of the workspace.",
                readOnly = true,
            )
            ElegantInput(
                value = "Legacy account",
                onValueChange = {},
                label = "Account",
                enabled = false,
            )
            ElegantInput(
                value = nickname,
                onValueChange = { nickname = it },
                label = "Nickname",
                placeholder = "e.g. Nova",
                isError = true,
                errorText = "Must be 3-20 characters and start with a letter.",
            )
            ElegantInput(
                value = bio,
                onValueChange = { bio = it },
                label = "Bio",
                maxLength = 20,
                supportingText = "${bio.length}/20",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A search field on a surface",
            description = "The input stretches next to a compact action on a soft surface.",
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colors.backgroundSubtle,
                        shape = RoundedCornerShape(ElegantRadius.lg),
                    )
                    .padding(ElegantSpacing.md),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantInput(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.weight(1f),
                    placeholder = "Search components",
                    style = ElegantInputStyle.Outlined,
                    leadingIcon = { ResourceIcon(Res.drawable.edit_rounded) },
                )
                ShowcaseIconButton(
                    resource = Res.drawable.arrow_forward_rounded,
                    contentDescription = "Run search",
                    style = ElegantIconButtonStyle.Primary,
                    onClick = {},
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun CheckboxShowcase() {
    var notifications by rememberSaveable { mutableStateOf(true) }
    var autoUpdates by rememberSaveable { mutableStateOf(false) }
    var productTips by rememberSaveable { mutableStateOf(false) }
    var emailUpdates by rememberSaveable { mutableStateOf(true) }
    var announcements by rememberSaveable { mutableStateOf(false) }

    ShowcasePage(title = "Elegant Checkbox") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Selection with one rhythm",
            description = "A 20dp rounded box animates its checkmark and keeps a 48dp interactive target.",
        ) {
            Text(
                text = "Basic",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantCheckbox(
                    checked = false,
                    onCheckedChange = {},
                    label = "Unchecked",
                )
                ElegantCheckbox(
                    checked = true,
                    onCheckedChange = {},
                    label = "Checked",
                )
                ElegantCheckbox(
                    checked = true,
                    onCheckedChange = {},
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Settings that stay scannable",
            description = "Checkbox rows pair a label with supporting text inside a calm settings surface.",
        ) {
            CheckboxSettingsPreview(
                notifications = notifications,
                onNotificationsChange = { notifications = it },
                autoUpdates = autoUpdates,
                onAutoUpdatesChange = { autoUpdates = it },
                productTips = productTips,
                onProductTipsChange = { productTips = it },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Checked, unchecked, and disabled",
            description = "Interaction states stay distinct and disabled rows never invoke callbacks.",
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantCheckbox(
                    checked = true,
                    onCheckedChange = {},
                    label = "Checked",
                )
                ElegantCheckbox(
                    checked = false,
                    onCheckedChange = {},
                    label = "Unchecked",
                )
                ElegantCheckbox(
                    checked = true,
                    onCheckedChange = {},
                    enabled = false,
                    label = "Disabled checked",
                )
                ElegantCheckbox(
                    checked = false,
                    onCheckedChange = {},
                    enabled = false,
                    label = "Disabled",
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "CONTROLLED",
            title = "State owned by the caller",
            description = "A dependent checkbox stays disabled until its parent preference is accepted.",
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
            ) {
                ElegantCheckbox(
                    checked = emailUpdates,
                    onCheckedChange = { emailUpdates = it },
                    label = "Email updates",
                )
                ElegantCheckbox(
                    checked = announcements,
                    onCheckedChange = { announcements = it },
                    enabled = emailUpdates,
                    label = "Announcements",
                )
            }
            Text(
                text = "Email ${if (emailUpdates) "on" else "off"} · Announcements ${if (announcements) "on" else "off"}",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun CheckboxSettingsPreview(
    notifications: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    autoUpdates: Boolean,
    onAutoUpdatesChange: (Boolean) -> Unit,
    productTips: Boolean,
    onProductTipsChange: (Boolean) -> Unit,
) {
    val colors = ElegantTheme.colors
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colors.backgroundSubtle,
                shape = RoundedCornerShape(ElegantRadius.md),
            )
            .padding(horizontal = ElegantSpacing.lg),
    ) {
        CheckboxSettingRow(
            label = "Push notifications",
            supporting = "Product and security alerts",
            checked = notifications,
            onCheckedChange = onNotificationsChange,
        )
        ElegantDivider(contentDescription = "Next notification setting")
        CheckboxSettingRow(
            label = "Automatic updates",
            supporting = "Install new versions overnight",
            checked = autoUpdates,
            onCheckedChange = onAutoUpdatesChange,
        )
        ElegantDivider(contentDescription = "Next notification setting")
        CheckboxSettingRow(
            label = "Product tips",
            supporting = "Occasional usage guidance",
            checked = productTips,
            onCheckedChange = onProductTipsChange,
        )
    }
}

@Composable
private fun CheckboxSettingRow(
    label: String,
    supporting: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val colors = ElegantTheme.colors
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs),
        ) {
            Text(
                text = label,
                color = colors.textPrimary,
                style = ElegantTheme.typography.labelLarge,
            )
            Text(
                text = supporting,
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
        ElegantCheckbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}

@Composable
private fun RadioShowcase() {
    var accent by rememberSaveable { mutableStateOf("Violet") }

    ShowcasePage(title = "Elegant Radio") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "One choice from a set",
            description = "A 20dp circular indicator fills with an animated dot while the 48dp row stays the interactive target.",
        ) {
            Text(
                text = "Basic",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                ElegantRadio(
                    selected = true,
                    onSelect = {},
                    label = "Selected",
                )
                ElegantRadio(
                    selected = false,
                    onSelect = {},
                    label = "Unselected",
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A group that stays exclusive",
            description = "Radios share one selection state, so exactly one option is chosen at a time.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                for (candidate in listOf("Violet", "Indigo", "Teal")) {
                    ElegantRadio(
                        selected = accent == candidate,
                        onSelect = { accent = candidate },
                        label = "$candidate accent",
                    )
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Selected, unselected, and disabled",
            description = "Disabled radios keep their selection visible with quiet theme colors and announce no interaction.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                ElegantRadio(
                    selected = true,
                    onSelect = { accent = "Violet" },
                    label = "Violet",
                )
                ElegantRadio(
                    selected = false,
                    onSelect = { accent = "Indigo" },
                    label = "Indigo",
                )
                ElegantRadio(
                    selected = true,
                    onSelect = {},
                    enabled = false,
                    label = "Unavailable",
                )
                ElegantRadio(
                    selected = false,
                    onSelect = {},
                    enabled = false,
                    label = "Not offered",
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable

private fun BadgeDot(color: Color) {
    Box(
        modifier = Modifier
            .size(6.dp)
            .background(color = color, shape = RoundedCornerShape(ElegantRadius.full)),
    )
}

@Composable
private fun TagStyleRow() {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        for (style in ElegantTagStyle.entries) {
            ElegantTag(style = style) {
                Text(style.name)
            }
        }
    }
}

@Composable
private fun TagSizeRow() {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantTag(size = ElegantTagSize.Small) {
            Text("Small")
        }
        ElegantTag(size = ElegantTagSize.Medium) {
            Text("Medium")
        }
        ElegantTag(size = ElegantTagSize.Large) {
            Text("Large")
        }
    }
}

@Composable
private fun ShowcasePage(
    title: String,
    content: @Composable (compact: Boolean) -> Unit,
) {
    var darkTheme by rememberSaveable { mutableStateOf(false) }

    ElegantTheme(darkTheme = darkTheme) {
        val colors = ElegantTheme.colors
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.backgroundCanvas),
        ) {
            val compact = maxWidth < 600.dp
            val pagePadding = if (compact) ElegantSpacing.lg else ElegantSpacing.xxxl
            val contentWidth = (maxWidth - pagePadding * 2)
                .coerceAtMost(880.dp)
                .coerceAtLeast(0.dp)
            val contentOffset = ((maxWidth - contentWidth) / 2)
                .coerceAtLeast(0.dp)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .statusBarsPadding()
                    .padding(vertical = ElegantSpacing.xl),
                horizontalAlignment = Alignment.Start,
            ) {
                Column(
                    modifier = Modifier
                        .width(contentWidth)
                        .offset(x = contentOffset),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xl),
                ) {
                    ShowcaseHeader(
                        title = title,
                        compact = compact,
                        darkTheme = darkTheme,
                        onDarkThemeChange = { darkTheme = it },
                    )
                    content(compact)
                }
            }
        }
    }
}

@Composable
private fun DividerRosterPreview() {
    val colors = ElegantTheme.colors
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colors.backgroundSubtle,
                shape = RoundedCornerShape(ElegantRadius.md),
            )
            .padding(horizontal = ElegantSpacing.lg),
    ) {
        DividerRosterRow(
            name = "Maya Chen",
            role = "Design systems",
            initials = "MC",
        )
        ElegantDivider(
            modifier = Modifier.padding(start = 52.dp),
            contentDescription = "Next team member",
        )
        DividerRosterRow(
            name = "Noah Williams",
            role = "Multiplatform engineering",
            initials = "NW",
        )
    }
}

@Composable
private fun DividerRosterRow(
    name: String,
    role: String,
    initials: String,
) {
    val colors = ElegantTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = ElegantSpacing.lg),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantAvatar(
            name = name,
            initials = initials,
            size = ElegantAvatarSize.Small,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs),
        ) {
            Text(
                text = name,
                color = colors.textPrimary,
                style = ElegantTheme.typography.labelLarge,
            )
            Text(
                text = role,
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun DividerMetric(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    val colors = ElegantTheme.colors
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs),
    ) {
        Text(
            text = value,
            color = colors.textPrimary,
            style = ElegantTheme.typography.titleMedium,
        )
        Text(
            text = label,
            color = colors.textSecondary,
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun ShowcaseHeader(
    title: String,
    compact: Boolean,
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
) {
    if (compact) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        ) {
            HeaderTitle(title = title)
            ThemeToggle(
                darkTheme = darkTheme,
                onDarkThemeChange = onDarkThemeChange,
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HeaderTitle(
                title = title,
                modifier = Modifier.weight(1f),
            )
            ThemeToggle(
                darkTheme = darkTheme,
                onDarkThemeChange = onDarkThemeChange,
            )
        }
    }
}

@Composable
private fun HeaderTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    val colors = ElegantTheme.colors
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
    ) {
        Text(
            text = title,
            color = colors.textPrimary,
            fontSize = 28.sp,
            lineHeight = 34.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.4).sp,
        )
        Text(
            text = "One shared contract · Android · Desktop · Web",
            modifier = Modifier.fillMaxWidth(),
            color = colors.textSecondary,
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun ThemeToggle(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
) {
    val colors = ElegantTheme.colors
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
    ) {
        Text(
            text = if (darkTheme) "Dark" else "Light",
            color = colors.textSecondary,
            style = ElegantTheme.typography.bodyMedium,
        )
        Switch(
            checked = darkTheme,
            onCheckedChange = onDarkThemeChange,
        )
    }
}

@Composable
private fun ConfirmationSurface() {
    val colors = ElegantTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colors.surfaceHover,
                shape = RoundedCornerShape(ElegantRadius.md),
            )
            .padding(ElegantSpacing.lg),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = colors.interactivePrimary,
                    shape = RoundedCornerShape(ElegantRadius.sm),
                ),
            contentAlignment = Alignment.Center,
        ) {
            ResourceIcon(
                resource = Res.drawable.check_rounded,
                modifier = Modifier.size(18.dp),
                tint = colors.textInverse,
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs),
        ) {
            Text(
                text = "Button milestone",
                color = colors.textPrimary,
                style = ElegantTheme.typography.titleMedium,
            )
            Text(
                text = "Interaction and visual tokens are synchronized across supported targets.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun SizeRow(
    style: ElegantButtonStyle,
    onClick: () -> Unit,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantButton(onClick = onClick, style = style, size = ElegantButtonSize.Small) {
            Text("Small")
        }
        ElegantButton(onClick = onClick, style = style, size = ElegantButtonSize.Medium) {
            Text("Medium")
        }
        ElegantButton(onClick = onClick, style = style, size = ElegantButtonSize.Large) {
            Text("Large")
        }
    }
}

@Composable
private fun IconButtonSizeRow(
    onClick: () -> Unit,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        ShowcaseIconButton(
            resource = Res.drawable.edit_rounded,
            contentDescription = "Edit small item",
            style = ElegantIconButtonStyle.Primary,
            size = ElegantIconButtonSize.Small,
            onClick = onClick,
        )
        ShowcaseIconButton(
            resource = Res.drawable.edit_rounded,
            contentDescription = "Edit medium item",
            style = ElegantIconButtonStyle.Primary,
            size = ElegantIconButtonSize.Medium,
            onClick = onClick,
        )
        ShowcaseIconButton(
            resource = Res.drawable.edit_rounded,
            contentDescription = "Edit large item",
            style = ElegantIconButtonStyle.Primary,
            size = ElegantIconButtonSize.Large,
            onClick = onClick,
        )
    }
}

@Composable
private fun AvatarSizeRow() {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantAvatar(
            name = "Small avatar",
            initials = "S",
            size = ElegantAvatarSize.Small,
        )
        ElegantAvatar(
            name = "Medium avatar",
            initials = "M",
            size = ElegantAvatarSize.Medium,
        )
        ElegantAvatar(
            name = "Large avatar",
            initials = "L",
            size = ElegantAvatarSize.Large,
        )
    }
}

@Composable
private fun BadgeStyleRow() {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        for (style in ElegantBadgeStyle.entries) {
            ElegantBadge(style = style) {
                Text(style.name)
            }
        }
    }
}

@Composable
private fun BadgeSizeRow() {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantBadge(size = ElegantBadgeSize.Small) {
            Text("Small")
        }
        ElegantBadge(size = ElegantBadgeSize.Medium) {
            Text("Medium")
        }
        ElegantBadge(size = ElegantBadgeSize.Large) {
            Text("Large")
        }
    }
}

@Composable
private fun BadgeCompositionPreview(
    updateCount: Int,
) {
    val colors = ElegantTheme.colors
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = colors.backgroundSubtle,
                    shape = RoundedCornerShape(ElegantRadius.md),
                )
                .padding(ElegantSpacing.lg),
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ElegantBadgeBox(
                badge = {
                    ElegantBadgeDot(
                        contentDescription = "Online",
                        style = ElegantBadgeStyle.Positive,
                    )
                },
                placement = ElegantBadgePlacement.BottomEnd,
            ) {
                ElegantAvatar(name = "Maya Chen")
            }
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs)) {
                Text(
                    text = "Maya Chen",
                    color = colors.textPrimary,
                    style = ElegantTheme.typography.labelLarge,
                )
                Text(
                    text = "Available",
                    color = colors.textSecondary,
                    style = ElegantTheme.typography.bodyMedium,
                )
            }
        }

        Row(
            modifier = Modifier
                .background(
                    color = colors.backgroundSubtle,
                    shape = RoundedCornerShape(ElegantRadius.md),
                )
                .padding(ElegantSpacing.lg),
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ElegantBadgeBox(
                badge = {
                    ElegantCountBadge(
                        count = updateCount,
                        maxCount = 9,
                        contentDescription = "$updateCount unread updates",
                        size = ElegantBadgeSize.Small,
                    )
                },
            ) {
                ShowcaseIconButton(
                    resource = Res.drawable.person_rounded,
                    contentDescription = "Team updates",
                    style = ElegantIconButtonStyle.Secondary,
                    onClick = {},
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs)) {
                Text(
                    text = "Team updates",
                    color = colors.textPrimary,
                    style = ElegantTheme.typography.labelLarge,
                )
                Text(
                    text = if (updateCount == 0) "All caught up" else "$updateCount waiting",
                    color = colors.textSecondary,
                    style = ElegantTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
private fun BadgePlacementPreview(
    placement: ElegantBadgePlacement,
) {
    ElegantBadgeBox(
        badge = {
            ElegantBadgeDot(
                contentDescription = placement.name,
                style = ElegantBadgeStyle.Positive,
                size = ElegantBadgeSize.Small,
            )
        },
        placement = placement,
    ) {
        ElegantAvatar(
            name = placement.name,
            initials = placement.name
                .filter(Char::isUpperCase)
                .take(2),
            size = ElegantAvatarSize.Small,
        )
    }
}

@Composable
private fun TeamMemberRow(
    name: String,
    role: String,
    avatarColors: ElegantAvatarColors,
) {
    val colors = ElegantTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colors.backgroundSubtle,
                shape = RoundedCornerShape(ElegantRadius.md),
            )
            .padding(ElegantSpacing.lg),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantAvatar(
            name = name,
            colors = avatarColors,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs),
        ) {
            Text(
                text = name,
                color = colors.textPrimary,
                style = ElegantTheme.typography.labelLarge,
            )
            Text(
                text = role,
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
        ShowcaseIconButton(
            resource = Res.drawable.more_vert_rounded,
            contentDescription = "More actions for $name",
            onClick = {},
        )
    }
}

@Composable
private fun CompactToolbar(
    compact: Boolean,
    onAction: () -> Unit,
) {
    val colors = ElegantTheme.colors
    val content: @Composable (Modifier) -> Unit = { modifier ->
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs),
        ) {
            Text(
                text = "Release notes",
                color = colors.textPrimary,
                style = ElegantTheme.typography.titleMedium,
            )
            Text(
                text = "Updated just now",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
    val actions: @Composable () -> Unit = {
        Row(
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShowcaseIconButton(
                resource = Res.drawable.edit_rounded,
                contentDescription = "Edit release notes",
                style = ElegantIconButtonStyle.Secondary,
                onClick = onAction,
            )
            ShowcaseIconButton(
                resource = Res.drawable.share_rounded,
                contentDescription = "Share release notes",
                onClick = onAction,
            )
            ShowcaseIconButton(
                resource = Res.drawable.more_vert_rounded,
                contentDescription = "More release note actions",
                onClick = onAction,
            )
        }
    }

    if (compact) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colors.surfaceHover,
                    shape = RoundedCornerShape(ElegantRadius.md),
                )
                .padding(ElegantSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        ) {
            content(Modifier.fillMaxWidth())
            actions()
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colors.surfaceHover,
                    shape = RoundedCornerShape(ElegantRadius.md),
                )
                .padding(ElegantSpacing.lg),
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content(Modifier.weight(1f))
            actions()
        }
    }
}

@Composable
private fun ShowcaseIconButton(
    resource: DrawableResource,
    contentDescription: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: ElegantIconButtonStyle = ElegantIconButtonStyle.Tertiary,
    size: ElegantIconButtonSize = ElegantIconButtonSize.Medium,
    onClick: () -> Unit,
) {
    ElegantIconButton(
        onClick = onClick,
        contentDescription = contentDescription,
        modifier = modifier,
        enabled = enabled,
        style = style,
        size = size,
    ) {
        ResourceIcon(resource)
    }
}

@Composable
private fun DemoCard(
    compact: Boolean,
    eyebrow: String,
    title: String,
    description: String,
    content: @Composable () -> Unit,
) {
    val colors = ElegantTheme.colors
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colors.surfaceRaised,
                shape = RoundedCornerShape(ElegantRadius.lg),
            )
            .border(
                width = 1.dp,
                color = colors.borderDefault,
                shape = RoundedCornerShape(ElegantRadius.lg),
            )
            .padding(if (compact) ElegantSpacing.xl else ElegantSpacing.xxl),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
    ) {
        Text(
            text = eyebrow,
            color = colors.interactivePrimary,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
        ) {
            Text(
                text = title,
                color = colors.textPrimary,
                style = ElegantTheme.typography.titleMedium,
            )
            Text(
                text = description,
                modifier = Modifier.fillMaxWidth(),
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
        content()
    }
}

@Composable
private fun ResourceIcon(
    resource: DrawableResource,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        painter = painterResource(resource),
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
        tint = tint,
    )
}

@Composable
private fun ForwardIcon() {
    val direction = LocalLayoutDirection.current
    ResourceIcon(
        resource = Res.drawable.arrow_forward_rounded,
        modifier = Modifier.graphicsLayer {
            scaleX = if (direction == LayoutDirection.Rtl) -1f else 1f
        },
    )
}

@Composable
private fun SwitchShowcase() {
    var wifi by rememberSaveable { mutableStateOf(true) }
    var bluetooth by rememberSaveable { mutableStateOf(false) }
    var batterySaver by rememberSaveable { mutableStateOf(false) }

    ShowcasePage(title = "Elegant Switch") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "On and off with one tap",
            description = "A 44dp capsule track slides a 16dp thumb while the 48dp row stays the interactive target.",
        ) {
            Text(
                text = "Basic",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantSwitch(
                    checked = false,
                    onCheckedChange = {},
                    label = "Off",
                )
                ElegantSwitch(
                    checked = true,
                    onCheckedChange = {},
                    label = "On",
                )
                ElegantSwitch(
                    checked = true,
                    onCheckedChange = {},
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "On, off, and disabled",
            description = "Interaction states stay distinct and disabled switches never invoke callbacks.",
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantSwitch(
                    checked = true,
                    onCheckedChange = {},
                    label = "On",
                )
                ElegantSwitch(
                    checked = false,
                    onCheckedChange = {},
                    label = "Off",
                )
                ElegantSwitch(
                    checked = true,
                    onCheckedChange = {},
                    enabled = false,
                    label = "Disabled on",
                )
                ElegantSwitch(
                    checked = false,
                    onCheckedChange = {},
                    enabled = false,
                    label = "Disabled",
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Settings that stay scannable",
            description = "Switch rows pair a label with supporting text inside a calm settings surface.",
        ) {
            SwitchSettingsPreview(
                wifi = wifi,
                onWifiChange = { wifi = it },
                bluetooth = bluetooth,
                onBluetoothChange = { bluetooth = it },
                batterySaver = batterySaver,
                onBatterySaverChange = { batterySaver = it },
            )
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun SwitchSettingsPreview(
    wifi: Boolean,
    onWifiChange: (Boolean) -> Unit,
    bluetooth: Boolean,
    onBluetoothChange: (Boolean) -> Unit,
    batterySaver: Boolean,
    onBatterySaverChange: (Boolean) -> Unit,
) {
    val colors = ElegantTheme.colors
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colors.backgroundSubtle,
                shape = RoundedCornerShape(ElegantRadius.md),
            )
            .padding(horizontal = ElegantSpacing.lg),
    ) {
        SwitchSettingRow(
            label = "Wi-Fi",
            supporting = "Join known networks automatically",
            checked = wifi,
            onCheckedChange = onWifiChange,
        )
        ElegantDivider(contentDescription = "Next switch setting")
        SwitchSettingRow(
            label = "Bluetooth",
            supporting = "Share audio and connect devices",
            checked = bluetooth,
            onCheckedChange = onBluetoothChange,
        )
        ElegantDivider(contentDescription = "Next switch setting")
        SwitchSettingRow(
            label = "Battery saver",
            supporting = "Limit background activity",
            checked = batterySaver,
            onCheckedChange = onBatterySaverChange,
        )
    }
}

@Composable
private fun SwitchSettingRow(
    label: String,
    supporting: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val colors = ElegantTheme.colors
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs),
        ) {
            Text(
                text = label,
                color = colors.textPrimary,
                style = ElegantTheme.typography.labelLarge,
            )
            Text(
                text = supporting,
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
        ElegantSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}

@Composable
private fun SliderShowcase() {
    var volume by rememberSaveable { mutableStateOf(0.5f) }
    var level by remember { mutableIntStateOf(2) }
    var brightness by rememberSaveable { mutableStateOf(0.7f) }

    ShowcasePage(title = "Elegant Slider") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Continuous value selection",
            description = "Drag anywhere on the track or tap to jump; the thumb follows the reported value.",
        ) {
            Text(
                text = "Volume ${(volume * 100).roundToInt()}%",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            ElegantSlider(
                value = volume,
                onValueChange = { volume = it },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STEPS",
            title = "Discrete positions",
            description = "Steps snap the value to evenly spaced positions between the endpoints.",
        ) {
            Text(
                text = "Level $level of 4",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            ElegantSlider(
                value = level.toFloat(),
                onValueChange = { level = it.roundToInt() },
                valueRange = 0f..4f,
                steps = 4,
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled",
            description = "Disabled sliders keep their value visible with quiet theme colors.",
        ) {
            ElegantSlider(
                value = 0.5f,
                onValueChange = {},
                enabled = false,
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A setting with its value",
            description = "A label row reads the current value next to the slider.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Brightness",
                    style = ElegantTheme.typography.labelMedium,
                    color = colors.textPrimary,
                )
                Text(
                    text = "${(brightness * 100).roundToInt()}%",
                    style = ElegantTheme.typography.labelMedium,
                    color = colors.textSecondary,
                )
            }
            ElegantSlider(
                value = brightness,
                onValueChange = { brightness = it },
            )
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun SelectShowcase() {
    var release by rememberSaveable { mutableStateOf("0.1") }
    val releases = listOf(
        ElegantSelectOption(text = "0.1 - Foundations", value = "0.1"),
        ElegantSelectOption(text = "0.2 - Forms", value = "0.2"),
        ElegantSelectOption(text = "0.3 - Content", value = "0.3"),
        ElegantSelectOption(text = "0.4 - Navigation", value = "0.4", enabled = false),
    )
    val selectedOption = releases.firstOrNull { it.value == release }

    ShowcasePage(title = "Elegant Select") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "One choice from a menu",
            description = "The field mirrors the input rhythm while the menu owns keyboard navigation and dismissal.",
        ) {
            ElegantSelect(
                selectedOption = selectedOption,
                onOptionSelected = { release = it.value },
                options = releases,
                label = "Release",
                placeholder = "Choose a release",
                supportingText = "Stable releases are listed first.",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Error and disabled",
            description = "Error borders and text stay consistent with the input family.",
        ) {
            ElegantSelect(
                selectedOption = null,
                onOptionSelected = {},
                options = releases,
                label = "Target",
                placeholder = "Required",
                isError = true,
                errorText = "Choose a target release.",
            )
            ElegantSelect(
                selectedOption = selectedOption,
                onOptionSelected = {},
                options = releases,
                label = "Archived release",
                enabled = false,
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A form with a select",
            description = "Input and Select share one field rhythm inside a form surface.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                ElegantInput(
                    value = release,
                    onValueChange = { release = it },
                    label = "Release note",
                    placeholder = "What changed",
                )
                ElegantSelect(
                    selectedOption = selectedOption,
                    onOptionSelected = { release = it.value },
                    options = releases,
                    label = "Release",
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun CardShowcase() {
    var openCount by remember { mutableIntStateOf(0) }

    ShowcasePage(title = "Elegant Card") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "STYLES",
            title = "Three surface treatments",
            description = "Filled, Outlined, and Elevated cards share one content contract.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                ElegantCard(style = ElegantCardStyle.Filled) {
                    Column(Modifier.padding(ElegantSpacing.xl)) {
                        Text("Filled", style = ElegantTheme.typography.labelLarge)
                        Text(
                            text = "The default surface for content blocks.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                }
                ElegantCard(style = ElegantCardStyle.Outlined) {
                    Column(Modifier.padding(ElegantSpacing.xl)) {
                        Text("Outlined", style = ElegantTheme.typography.labelLarge)
                        Text(
                            text = "A bordered surface that separates without weight.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                }
                ElegantCard(style = ElegantCardStyle.Elevated) {
                    Column(Modifier.padding(ElegantSpacing.xl)) {
                        Text("Elevated", style = ElegantTheme.typography.labelLarge)
                        Text(
                            text = "A raised surface for floating content.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "INTERACTIVE",
            title = "Cards that respond",
            description = "Passing onClick adds hover, press, focus, and ripple without changing layout.",
        ) {
            ElegantCard(
                onClick = { openCount++ },
                style = ElegantCardStyle.Outlined,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(ElegantSpacing.xl),
                    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ElegantAvatar(name = "Maya Chen", initials = "MC")
                    Column(Modifier.weight(1f)) {
                        Text("Open project", style = ElegantTheme.typography.labelLarge)
                        Text(
                            text = "Tap the card to open the workspace.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                    Text(
                        text = "$openCount",
                        color = colors.textSecondary,
                        style = ElegantTheme.typography.bodyMedium,
                    )
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A profile surface",
            description = "Cards compose with identity and compact actions.",
        ) {
            ElegantCard(style = ElegantCardStyle.Filled) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(ElegantSpacing.xl),
                    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ElegantAvatar(name = "Maya Chen", initials = "MC")
                    Column(Modifier.weight(1f)) {
                        Text("Maya Chen", style = ElegantTheme.typography.labelLarge)
                        Text(
                            text = "Product designer",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                    ShowcaseIconButton(
                        resource = Res.drawable.more_vert_rounded,
                        contentDescription = "More profile actions",
                        onClick = {},
                    )
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun ListShowcase() {
    var selectedSettings by remember { mutableIntStateOf(0) }
    var selectedMember by remember { mutableIntStateOf(0) }

    ShowcasePage(title = "Elegant List") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Rows with rhythm",
            description = "Items stack without spacing; each row owns its padding and two-line structure.",
        ) {
            ElegantList {
                ElegantListItem(
                    title = { Text("General") },
                    supportingText = { Text("Appearance, storage, and performance") },
                )
                ElegantListItem(
                    title = { Text("Notifications") },
                    supportingText = { Text("Badges, sounds, and summary") },
                )
                ElegantListItem(
                    title = { Text("Account") },
                    supportingText = { Text("Sign-in, privacy, and security") },
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "LEADING & TRAILING",
            title = "Slots on both ends",
            description = "Leading and trailing content sit in 20dp boxes with 16dp gaps.",
        ) {
            ElegantList {
                ElegantListItem(
                    leadingContent = { ResourceIcon(Res.drawable.person_rounded) },
                    title = { Text("Profile") },
                    supportingText = { Text("Photo, name, and contact details") },
                    trailingContent = { ResourceIcon(Res.drawable.arrow_forward_rounded) },
                )
                ElegantListItem(
                    leadingContent = { ResourceIcon(Res.drawable.edit_rounded) },
                    title = { Text("Edit details") },
                    supportingText = { Text("Keep your information current") },
                    trailingContent = {
                        ShowcaseIconButton(
                            resource = Res.drawable.more_vert_rounded,
                            contentDescription = "More account actions",
                            onClick = {},
                        )
                    },
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "INTERACTIVE",
            title = "Selectable rows",
            description = "Selected rows tint with the theme accent; press and hover animate the container.",
        ) {
            ElegantList {
                ElegantListItem(
                    title = { Text("Wi-Fi") },
                    supportingText = { Text("Home network") },
                    onClick = { selectedSettings = 0 },
                    selected = selectedSettings == 0,
                )
                ElegantListItem(
                    title = { Text("Bluetooth") },
                    supportingText = { Text("On") },
                    onClick = { selectedSettings = 1 },
                    selected = selectedSettings == 1,
                )
                ElegantListItem(
                    title = { Text("Airplane mode") },
                    supportingText = { Text("Not available while flying") },
                    onClick = {},
                    enabled = false,
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Team roster",
            description = "Avatar, two-line identity, and selection inside one list.",
        ) {
            ElegantList {
                ElegantListItem(
                    leadingContent = {
                        ElegantAvatar(
                            name = "Maya Chen",
                            initials = "MC",
                            size = ElegantAvatarSize.Small,
                        )
                    },
                    title = { Text("Maya Chen") },
                    supportingText = { Text("Design systems lead") },
                    onClick = { selectedMember = 0 },
                    selected = selectedMember == 0,
                )
                ElegantListItem(
                    leadingContent = {
                        ElegantAvatar(
                            name = "Noah Williams",
                            initials = "NW",
                            size = ElegantAvatarSize.Small,
                        )
                    },
                    title = { Text("Noah Williams") },
                    supportingText = { Text("Multiplatform engineering") },
                    onClick = { selectedMember = 1 },
                    selected = selectedMember == 1,
                )
                ElegantListItem(
                    leadingContent = {
                        ElegantAvatar(
                            name = "Ava Rodriguez",
                            initials = "AR",
                            size = ElegantAvatarSize.Small,
                        )
                    },
                    title = { Text("Ava Rodriguez") },
                    supportingText = { Text("Accessibility research") },
                    onClick = { selectedMember = 2 },
                    selected = selectedMember == 2,
                )
            }
        }
    }
}

@Composable
private fun EmptyStateShowcase() {
    ShowcasePage(title = "Elegant EmptyState") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "The default message",
            description = "Icon, title, and description stack in a centered, non-interactive composition.",
        ) {
            ElegantEmptyState(
                icon = {
                    ResourceIcon(
                        resource = Res.drawable.check_rounded,
                        modifier = Modifier.size(28.dp),
                    )
                },
                title = "All caught up",
                description = "No unread conversations. New activity will appear here.",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "ACTION",
            title = "Guide the next step",
            description = "The action slot hosts a primary call to action below the description.",
        ) {
            ElegantEmptyState(
                icon = {
                    ResourceIcon(
                        resource = Res.drawable.add_rounded,
                        modifier = Modifier.size(28.dp),
                    )
                },
                title = "Start your first project",
                description = "Create a workspace to begin organizing your work.",
                action = {
                    ElegantButton(onClick = {}) {
                        Text("New project")
                    }
                },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "An empty inbox surface",
            description = "The centered layout fills the width of its host surface.",
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colors.backgroundSubtle,
                        shape = RoundedCornerShape(ElegantRadius.md),
                    ),
            ) {
                ElegantEmptyState(
                    icon = {
                        ResourceIcon(
                            resource = Res.drawable.person_rounded,
                            modifier = Modifier.size(28.dp),
                        )
                    },
                    title = "Inbox zero",
                    description = "Every conversation has been handled. Enjoy the quiet.",
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun ModalShowcase() {
    ShowcasePage(title = "Elegant Modal") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A controlled overlay",
            description = "ElegantModal renders the scrim, the 480dp-capped surface, and 24dp padding; the caller owns title, description, and actions.",
        ) {
            var visible by remember { mutableStateOf(false) }

            Text(
                text = "Tap the trigger to open a centered modal surface.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantButton(onClick = { visible = true }) {
                Text("Open modal")
            }

            ElegantModal(
                visible = visible,
                onDismissRequest = { visible = false },
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                        Text(
                            text = "Delete project?",
                            style = ElegantTheme.typography.titleMedium,
                        )
                        Text(
                            text = "This action cannot be undone.",
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md, Alignment.End),
                    ) {
                        ElegantButton(
                            onClick = { visible = false },
                            style = ElegantButtonStyle.Secondary,
                        ) {
                            Text("Cancel")
                        }
                        ElegantButton(onClick = { visible = false }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A confirmation flow",
            description = "Scrim click, back/Escape, Cancel, and Delete all leave the modal closed under caller control.",
        ) {
            var pending by remember { mutableStateOf(false) }
            var confirmed by remember { mutableStateOf(false) }

            Text(
                text = if (confirmed) "File deleted." else "Tap delete to confirm a destructive action.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantButton(
                onClick = { pending = true },
                style = if (confirmed) ElegantButtonStyle.Secondary else ElegantButtonStyle.Primary,
            ) {
                Text("Delete file")
            }

            ElegantModal(
                visible = pending,
                onDismissRequest = { pending = false },
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                        Text(
                            text = "Delete this file?",
                            style = ElegantTheme.typography.titleMedium,
                        )
                        Text(
                            text = "The file is removed permanently.",
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md, Alignment.End),
                    ) {
                        ElegantButton(
                            onClick = { pending = false },
                            style = ElegantButtonStyle.Secondary,
                        ) {
                            Text("Cancel")
                        }
                        ElegantButton(
                            onClick = {
                                pending = false
                                confirmed = true
                            },
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun DrawerShowcase() {
    var startVisible by remember { mutableStateOf(false) }
    var endVisible by remember { mutableStateOf(false) }

    ShowcasePage(title = "Elegant Drawer") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Modal side panel",
            description = "A scrim-backed panel that slides in from the logical start edge.",
        ) {
            ElegantButton(onClick = { startVisible = true }) {
                Text("Open drawer")
            }
            Text(
                text = "Dismiss by scrim click, Escape, or the back key.",
                color = ElegantTheme.colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "PLACEMENT",
            title = "End placement",
            description = "The same panel slides in from the logical end edge.",
        ) {
            ElegantButton(onClick = { endVisible = true }) {
                Text("Open end drawer")
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Navigation drawer",
            description = "A profile header and menu items inside the scrolling panel.",
        ) {
            Text(
                text = "Open the start drawer to see the navigation pattern.",
                color = ElegantTheme.colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }

        ElegantDrawer(
            visible = startVisible,
            onDismissRequest = { startVisible = false },
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ElegantAvatar(name = "Maya Chen", initials = "MC")
                    Column(Modifier.padding(start = ElegantSpacing.lg)) {
                        Text("Maya Chen")
                        Text("maya@elegant.com")
                    }
                }
                ElegantDivider(Modifier.padding(vertical = ElegantSpacing.md))
                Text("Inbox")
                Text("Starred")
                Text("Sent mail")
                ElegantDivider(Modifier.padding(vertical = ElegantSpacing.md))
                Text("Settings")
                Text("Help")
            }
        }

        ElegantDrawer(
            visible = endVisible,
            onDismissRequest = { endVisible = false },
            placement = ElegantDrawerPlacement.End,
        ) {
            Column {
                Text("Filters")
                Text("Price")
                Text("Rating")
                Text("Availability")
            }
        }
    }
}

@Composable
private fun TableShowcase() {
    ShowcasePage(title = "Elegant Table") { compact ->
        val componentColumns = listOf(
            ElegantTableColumn(title = "Component"),
            ElegantTableColumn(title = "Family", textAlign = TextAlign.Center),
            ElegantTableColumn(title = "Status", textAlign = TextAlign.End),
        )
        val componentRows = listOf(
            ElegantTableRow(cells = listOf("Button", "Action", "Available")),
            ElegantTableRow(cells = listOf("Divider", "Display", "Available")),
            ElegantTableRow(cells = listOf("Modal", "Overlay", "In progress")),
            ElegantTableRow(cells = listOf("Table", "Display", "New")),
        )

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Tabular data on one rhythm",
            description = "A bordered grid keeps the header, separators, and data rows aligned without interaction overhead.",
        ) {
            ElegantTable(columns = componentColumns, rows = componentRows)
        }

        val weightedColumns = listOf(
            ElegantTableColumn(title = "Release", weight = 1.4f),
            ElegantTableColumn(title = "Components", weight = 1f, textAlign = TextAlign.End),
            ElegantTableColumn(title = "Platforms", weight = 1.2f, textAlign = TextAlign.Center),
        )
        val weightedRows = listOf(
            ElegantTableRow(cells = listOf("0.1", "4", "3")),
            ElegantTableRow(cells = listOf("0.2", "9", "3")),
            ElegantTableRow(cells = listOf("0.3", "16", "3")),
        )

        DemoCard(
            compact = compact,
            eyebrow = "WEIGHT",
            title = "Proportional columns with per-column alignment",
            description = "Weights share the available width while alignment keeps numerals comparable.",
        ) {
            ElegantTable(columns = weightedColumns, rows = weightedRows)
        }

        val metricColumns = listOf(
            ElegantTableColumn(title = "Metric", width = 144.dp),
            ElegantTableColumn(title = "Value", textAlign = TextAlign.End),
        )
        val metricRows = listOf(
            ElegantTableRow(cells = listOf("Available components", "16")),
            ElegantTableRow(cells = listOf("Supported platforms", "3")),
            ElegantTableRow(cells = listOf("Tests passing", "412")),
        )

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Metrics that read at a glance",
            description = "A fixed identity column keeps labels stable while the flexible column fills the rest.",
        ) {
            ElegantTable(columns = metricColumns, rows = metricRows)
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun TabsShowcase() {
    var selected by rememberSaveable { mutableStateOf(0) }
    val colors = ElegantTheme.colors

    ShowcasePage(title = "Elegant Tabs") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Fixed tabs switch one content area",
            description = "Equal-width tabs keep the selection in a single controlled state.",
        ) {
            val tabs = listOf(
                ElegantTab("Overview"),
                ElegantTab("Projects"),
                ElegantTab("Settings"),
            )
            ElegantTabRow(
                tabs = tabs,
                selectedIndex = selected,
                onSelect = { selected = it },
            )
            Text(
                text = "Showing ${tabs[selected.coerceIn(tabs.indices)].text}",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
                modifier = Modifier.padding(top = ElegantSpacing.lg),
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "SCROLLABLE",
            title = "Many tabs at natural width",
            description = "Scrollable tabs keep their width and wrap with the arrow keys.",
        ) {
            ElegantTabRow(
                tabs = listOf(
                    ElegantTab("Design"),
                    ElegantTab("Engineering"),
                    ElegantTab("Multiplatform"),
                    ElegantTab("Release"),
                    ElegantTab("Docs"),
                    ElegantTab("Community"),
                ),
                selectedIndex = selected,
                onSelect = { selected = it },
                scrollable = true,
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled tabs stay visible",
            description = "A disabled tab keeps its slot and announces its state without callbacks.",
        ) {
            ElegantTabRow(
                tabs = listOf(
                    ElegantTab("General"),
                    ElegantTab("Security", enabled = false),
                    ElegantTab("Billing"),
                ),
                selectedIndex = selected,
                onSelect = { selected = it },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Tabs above a list",
            description = "The selected tab drives the items shown below the strip.",
        ) {
            ElegantTabRow(
                tabs = listOf(ElegantTab("Members"), ElegantTab("Activity")),
                selectedIndex = selected,
                onSelect = { selected = it },
            )
            val members = listOf("Maya Chen", "Noah Williams", "Ava Johnson")
            val activities = listOf(
                "Maya pushed to main",
                "Noah closed issue #42",
                "Ava merged PR #128",
            )
            Column(
                modifier = Modifier.padding(top = ElegantSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
            ) {
                for (item in if (selected == 0) members else activities) {
                    Text(
                        text = item,
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.textPrimary,
                    )
                }
            }
        }
    }
}

@Composable
private fun BreadcrumbShowcase() {
    ShowcasePage(title = "Elegant Breadcrumb") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Hierarchy with a live trail",
            description = "The trailing entry reads as the current page while earlier entries stay clickable links.",
        ) {
            var path by remember { mutableStateOf(listOf("Home", "Library", "Compose")) }

            Column {
                ElegantBreadcrumb(
                    items = path.map { ElegantBreadcrumbItem(it) },
                    onItemClick = { index -> path = path.take(index + 1) },
                )
                Spacer(Modifier.height(ElegantSpacing.sm))
                Text(
                    text = "Tap an entry to rewind the trail.",
                    style = ElegantTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled and non-interactive entries",
            description = "Disabled entries stay visible but never activate; without a callback the trail is a pure location display.",
        ) {
            Column {
                ElegantBreadcrumb(
                    items = listOf(
                        ElegantBreadcrumbItem("Home"),
                        ElegantBreadcrumbItem("Drafts", enabled = false),
                        ElegantBreadcrumbItem("Current draft"),
                    ),
                    onItemClick = {},
                )
                Spacer(Modifier.height(ElegantSpacing.lg))
                ElegantBreadcrumb(
                    items = listOf(
                        ElegantBreadcrumbItem("Home"),
                        ElegantBreadcrumbItem("Settings"),
                    ),
                )
                Spacer(Modifier.height(ElegantSpacing.sm))
                Text(
                    text = "The second trail has no callback, so every entry is plain text.",
                    style = ElegantTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Breadcrumb above a document title",
            description = "The current entry pairs with the page heading to anchor navigation.",
        ) {
            Column {
                ElegantBreadcrumb(
                    items = listOf(
                        ElegantBreadcrumbItem("Home"),
                        ElegantBreadcrumbItem("Guides"),
                        ElegantBreadcrumbItem("Breadcrumb"),
                    ),
                    onItemClick = {},
                )
                Spacer(Modifier.height(ElegantSpacing.xs))
                Text(
                    text = "Breadcrumb",
                    style = ElegantTheme.typography.titleMedium,
                )
                Spacer(Modifier.height(ElegantSpacing.xs))
                Text(
                    text = "Trailing entries read as the document title.",
                    style = ElegantTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                )
            }
        }
    }
}

@Composable
private fun NavbarShowcase() {
    ShowcasePage(title = "Elegant Navbar") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Top app bar",
            description = "A 56dp bar with an optional navigation icon, a caller-styled title, and trailing actions separated by a 4dp item gap.",
        ) {
            ElegantNavbar(
                navigationIcon = {
                    ShowcaseIconButton(
                        resource = Res.drawable.person_rounded,
                        contentDescription = "Profile",
                        onClick = {},
                    )
                },
                title = {
                    Text(
                        text = "Home",
                        style = ElegantTheme.typography.titleMedium,
                    )
                },
                actions = {
                    ShowcaseIconButton(
                        resource = Res.drawable.share_rounded,
                        contentDescription = "Share",
                        onClick = {},
                    )
                    ShowcaseIconButton(
                        resource = Res.drawable.more_vert_rounded,
                        contentDescription = "More options",
                        onClick = {},
                    )
                },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Document page",
            description = "A navbar sits above a divider and body content, while actions stay in the trailing row.",
        ) {
            Column {
                ElegantNavbar(
                    navigationIcon = {
                        ShowcaseIconButton(
                            resource = Res.drawable.arrow_forward_rounded,
                            contentDescription = "Back",
                            onClick = {},
                        )
                    },
                    title = {
                        Text(
                            text = "Release notes",
                            style = ElegantTheme.typography.titleMedium,
                        )
                    },
                    actions = {
                        ShowcaseIconButton(
                            resource = Res.drawable.edit_rounded,
                            contentDescription = "Edit",
                            onClick = {},
                        )
                        ShowcaseIconButton(
                            resource = Res.drawable.check_rounded,
                            contentDescription = "Publish",
                            onClick = {},
                        )
                    },
                )
                ElegantDivider()
                Text(
                    text = "A document page that places a navbar above a divider and body content.",
                    modifier = Modifier.padding(ElegantSpacing.xl),
                    color = ElegantTheme.colors.textSecondary,
                    style = ElegantTheme.typography.bodyMedium,
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun SidebarShowcase() {
    var selected by remember { mutableStateOf(0) }

    ShowcasePage(title = "Elegant Sidebar") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Controlled destination list",
            description = "A fixed-width rail with a selected index, header and footer slots, and animated item states.",
        ) {
            ElegantSidebar(
                selectedIndex = selected,
                onSelect = { selected = it },
                items = listOf(
                    ElegantSidebarItem("Overview"),
                    ElegantSidebarItem("Analytics"),
                    ElegantSidebarItem("Reports"),
                    ElegantSidebarItem("Billing"),
                ),
                header = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ElegantAvatar(name = "Maya Chen", initials = "MC")
                        Column {
                            Text("Maya Chen")
                            Text("Design systems")
                        }
                    }
                },
                footer = {
                    Text("Settings")
                },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled and non-interactive",
            description = "Per-item enabled flags keep disabled entries resting, and dropping onSelect renders plain text.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xl),
            ) {
                ElegantSidebar(
                    selectedIndex = 1,
                    onSelect = {},
                    items = listOf(
                        ElegantSidebarItem("Inbox"),
                        ElegantSidebarItem("Archived"),
                        ElegantSidebarItem("Trash", enabled = false),
                    ),
                )
                ElegantSidebar(
                    selectedIndex = null,
                    onSelect = null,
                    items = listOf(
                        ElegantSidebarItem("Read-only A"),
                        ElegantSidebarItem("Read-only B"),
                    ),
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Navigation beside content",
            description = "The sidebar anchors a layout row while content flows next to it.",
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(ElegantTheme.colors.backgroundSubtle),
            ) {
                ElegantSidebar(
                    selectedIndex = selected,
                    onSelect = { selected = it },
                    items = listOf(
                        ElegantSidebarItem("Dashboard"),
                        ElegantSidebarItem("Projects"),
                        ElegantSidebarItem("Team"),
                    ),
                    header = {
                        Text("Acme Corp")
                    },
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(ElegantSpacing.xl),
                ) {
                    Text("Dashboard", style = ElegantTheme.typography.titleMedium)
                    Text("Welcome back, Maya.", style = ElegantTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun UnknownComponent(componentId: String) {
    ElegantTheme {
        val colors = ElegantTheme.colors
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.backgroundCanvas)
                .padding(ElegantSpacing.xxxl),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        ) {
            Text(
                text = "Unknown component",
                color = colors.textPrimary,
                style = ElegantTheme.typography.titleMedium,
            )
            Text(
                text = componentId,
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
}

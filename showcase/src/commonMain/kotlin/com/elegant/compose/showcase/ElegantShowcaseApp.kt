package com.elegant.compose.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.elegant.compose.ui.accordion.ElegantAccordion
import com.elegant.compose.ui.accordion.ElegantAccordionItem
import com.elegant.compose.ui.alert.ElegantAlert
import com.elegant.compose.ui.alert.ElegantAlertStyle
import com.elegant.compose.ui.alertdialog.ElegantAlertDialog
import com.elegant.compose.ui.autocomplete.ElegantAutocomplete
import com.elegant.compose.ui.autocomplete.ElegantAutocompleteOption
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
import com.elegant.compose.ui.buttongroup.ElegantButtonGroup
import com.elegant.compose.ui.buttongroup.ElegantButtonGroupItem
import com.elegant.compose.ui.calendar.ElegantCalendar
import com.elegant.compose.ui.calendar.ElegantDate
import com.elegant.compose.ui.card.ElegantCard
import com.elegant.compose.ui.card.ElegantCardStyle
import com.elegant.compose.ui.cascadingmenu.ElegantCascadingMenu
import com.elegant.compose.ui.cascadingmenu.ElegantCascadingMenuItem
import com.elegant.compose.ui.checkbox.ElegantCheckbox
import com.elegant.compose.ui.checkboxgroup.ElegantCheckboxGroup
import com.elegant.compose.ui.checkboxgroup.ElegantCheckboxGroupItem
import com.elegant.compose.ui.closebutton.ElegantCloseButton
import com.elegant.compose.ui.colorpicker.ElegantColorPicker
import com.elegant.compose.ui.colorpicker.ElegantColorPickerDefaults
import com.elegant.compose.ui.colorpicker.ElegantColorPickerPanel
import com.elegant.compose.ui.datepicker.ElegantDatePicker
import com.elegant.compose.ui.daterangepicker.ElegantDateRange
import com.elegant.compose.ui.daterangepicker.ElegantDateRangePicker
import com.elegant.compose.ui.description.ElegantDescription
import com.elegant.compose.ui.description.ElegantDescriptionItem
import com.elegant.compose.ui.disclosure.ElegantDisclosure
import com.elegant.compose.ui.disclosure.ElegantDisclosureGroup
import com.elegant.compose.ui.divider.ElegantDivider
import com.elegant.compose.ui.divider.ElegantDividerEmphasis
import com.elegant.compose.ui.divider.ElegantDividerLabelPosition
import com.elegant.compose.ui.divider.ElegantDividerOrientation
import com.elegant.compose.ui.divider.ElegantDividerStyle
import com.elegant.compose.ui.divider.ElegantLabeledDivider
import com.elegant.compose.ui.drawer.ElegantDrawer
import com.elegant.compose.ui.drawer.ElegantDrawerPlacement
import com.elegant.compose.ui.foundation.effect.BlurEdgeTreatment
import com.elegant.compose.ui.foundation.effect.elegantBlur
import com.elegant.compose.ui.emptystate.ElegantEmptyState
import com.elegant.compose.ui.fieldset.ElegantFieldset
import com.elegant.compose.ui.floatingactionbutton.ElegantFloatingActionButton
import com.elegant.compose.ui.floatingtoolbar.ElegantFloatingToolbar
import com.elegant.compose.ui.iconbutton.ElegantIcon
import com.elegant.compose.ui.foundation.icons.ElegantIcons
import com.elegant.compose.ui.iconbutton.ElegantIconButton
import com.elegant.compose.ui.iconbutton.ElegantIconButtonSize
import com.elegant.compose.ui.iconbutton.ElegantIconButtonStyle
import com.elegant.compose.ui.input.ElegantInput
import com.elegant.compose.ui.input.ElegantInputStyle
import com.elegant.compose.ui.inputgroup.ElegantInputGroup
import com.elegant.compose.ui.inputotp.ElegantInputOtp
import com.elegant.compose.ui.kbd.ElegantKbd
import com.elegant.compose.ui.label.ElegantLabel
import com.elegant.compose.ui.link.ElegantLink
import com.elegant.compose.ui.list.ElegantList
import com.elegant.compose.ui.list.ElegantListItem
import com.elegant.compose.ui.listpopup.ElegantListPopup
import com.elegant.compose.ui.listpopup.ElegantListPopupOption
import com.elegant.compose.ui.menu.ElegantMenu
import com.elegant.compose.ui.menu.ElegantMenuItem
import com.elegant.compose.ui.meter.ElegantMeter
import com.elegant.compose.ui.meter.ElegantMeterTone
import com.elegant.compose.ui.bottomsheet.ElegantBottomSheet
import com.elegant.compose.ui.modal.ElegantModal
import com.elegant.compose.ui.nav.core.ElegantNavBackStack
import com.elegant.compose.ui.nav.core.ElegantNavCornerClipMode
import com.elegant.compose.ui.nav.core.ElegantNavDisplay
import com.elegant.compose.ui.nav.core.ElegantNavDisplayEffects
import com.elegant.compose.ui.nav.core.ElegantNavKey
import com.elegant.compose.ui.nav.core.rememberElegantNavBackStack
import com.elegant.compose.ui.nav.core.rememberElegantNavSystemCornerRadius
import com.elegant.compose.ui.nav.transition.ElegantNavSwipeDirection
import com.elegant.compose.ui.nav.transition.ElegantNavTransition
import com.elegant.compose.ui.nav.transition.ElegantNavTransitions
import com.elegant.compose.ui.nav.transition.elegantNavGraphicsTransition
import com.elegant.compose.ui.navbar.ElegantNavbar
import com.elegant.compose.ui.navigationbar.ElegantNavigationBar
import com.elegant.compose.ui.navigationbar.ElegantNavigationBarItem
import com.elegant.compose.ui.navigationrail.ElegantNavigationRail
import com.elegant.compose.ui.navigationrail.ElegantNavigationRailItem
import com.elegant.compose.ui.numberfield.ElegantNumberField
import com.elegant.compose.ui.numberpicker.ElegantNumberPicker
import com.elegant.compose.ui.pagination.ElegantPagination
import com.elegant.compose.ui.popover.ElegantPopover
import com.elegant.compose.ui.popover.ElegantPopoverPlacement
import com.elegant.compose.ui.preference.ElegantArrowPreference
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponent
import com.elegant.compose.ui.preference.ElegantCheckboxPreference
import com.elegant.compose.ui.preference.ElegantRadioPreference
import com.elegant.compose.ui.preference.ElegantSliderPreference
import com.elegant.compose.ui.preference.ElegantSwitchPreference
import com.elegant.compose.ui.progress.ElegantCircularProgressIndicator
import com.elegant.compose.ui.progress.ElegantLinearProgressIndicator
import com.elegant.compose.ui.pulltorefresh.ElegantPullToRefresh
import com.elegant.compose.ui.pulltorefresh.ElegantPullToRefreshDefaults
import com.elegant.compose.ui.radio.ElegantRadio
import com.elegant.compose.ui.radiogroup.ElegantRadioGroup
import com.elegant.compose.ui.radiogroup.ElegantRadioGroupItem
import com.elegant.compose.ui.scaffold.ElegantScaffold
import com.elegant.compose.ui.scrollbar.ElegantScrollBar
import com.elegant.compose.ui.scrollbar.ElegantScrollBarOrientation
import com.elegant.compose.ui.scrollshadow.ElegantScrollShadow
import com.elegant.compose.ui.scrollshadow.ElegantScrollShadowOrientation
import com.elegant.compose.ui.searchbar.ElegantSearchBar
import com.elegant.compose.ui.select.ElegantSelect
import com.elegant.compose.ui.select.ElegantSelectOption
import com.elegant.compose.ui.foundation.shape.ElegantSquircleShape
import com.elegant.compose.ui.sidebar.ElegantSidebar
import com.elegant.compose.ui.sidebar.ElegantSidebarItem
import com.elegant.compose.ui.skeleton.ElegantSkeleton
import com.elegant.compose.ui.skeleton.ElegantSkeletonBlock
import com.elegant.compose.ui.slider.ElegantSlider
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle
import com.elegant.compose.ui.snackbar.ElegantSnackbar
import com.elegant.compose.ui.snackbar.ElegantSnackbarHost
import com.elegant.compose.ui.snackbar.ElegantSnackbarHostState
import com.elegant.compose.ui.spinner.ElegantSpinner
import com.elegant.compose.ui.surface.ElegantSurface
import com.elegant.compose.ui.switch.ElegantSwitch
import com.elegant.compose.ui.switchgroup.ElegantSwitchGroup
import com.elegant.compose.ui.switchgroup.ElegantSwitchGroupItem
import com.elegant.compose.ui.table.ElegantTable
import com.elegant.compose.ui.table.ElegantTableColumn
import com.elegant.compose.ui.table.ElegantTableRow
import com.elegant.compose.ui.tabs.ElegantTab
import com.elegant.compose.ui.tabs.ElegantTabRow
import com.elegant.compose.ui.tag.ElegantTag
import com.elegant.compose.ui.tag.ElegantTagSize
import com.elegant.compose.ui.tag.ElegantTagStyle
import com.elegant.compose.ui.taggroup.ElegantTagGroup
import com.elegant.compose.ui.taggroup.ElegantTagGroupItem
import com.elegant.compose.ui.textarea.ElegantTextarea
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.toast.ElegantToast
import com.elegant.compose.ui.toast.ElegantToastHost
import com.elegant.compose.ui.toast.ElegantToastHostState
import com.elegant.compose.ui.togglebutton.ElegantToggleButton
import com.elegant.compose.ui.togglebutton.ElegantToggleButtonGroup
import com.elegant.compose.ui.toolbar.ElegantToolbar
import com.elegant.compose.ui.tooltip.ElegantTooltip
import com.elegant.compose.ui.tooltip.ElegantTooltipBox
import com.elegant.compose.ui.tooltip.ElegantTooltipPlacement
import kotlin.math.roundToInt
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Launcher entry that pairs a component selector bar with the shared showcase.
 *
 * Desktop and Android launchers call this instead of [ElegantShowcaseApp] so all registered
 * components stay reachable; the Web launcher keeps the `?id={slug}` routing contract. The
 * selector reads the same registry as [ElegantShowcaseApp], so unknown ids resolve to the
 * fallback page.
 */
@Composable
public fun ElegantShowcaseBrowser(initialComponentId: String = "button") {
    val ids = SupportedShowcaseComponentIds.toList().sorted()
    val startId = if (initialComponentId in ids) initialComponentId else ids.first()
    var selected by rememberSaveable { mutableStateOf(startId) }

    Column(modifier = Modifier.fillMaxSize()) {
        ElegantTheme {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ElegantTheme.colors.surfaceDefault)
                    .horizontalScroll(rememberScrollState())
                    .padding(ElegantSpacing.md),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (id in ids) {
                    ElegantTag(
                        onClick = { selected = id },
                        selected = id == selected,
                        style = if (id == selected) {
                            ElegantTagStyle.Filled
                        } else {
                            ElegantTagStyle.Outlined
                        },
                    ) {
                        Text(id)
                    }
                }
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            ElegantShowcaseApp(componentId = selected)
        }
    }
}

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
        "bottom-sheet",
        "drawer",
        "table",
        "tabs",
        "breadcrumb",
        "navbar",
        "sidebar",
        "progress-indicator",
        "skeleton",
        "alert",
        "snackbar",
        "textarea",
        "number-field",
        "search-bar",
        "pagination",
        "navigation-bar",
        "navigation",
        "navigation-rail",
        "floating-action-button",
        "popover",
        "menu",
        "accordion",
        "link",
        "kbd",
        "surface",
        "scaffold",
        "pull-to-refresh",
        "meter",
        "description",
        "toggle-button",
        "button-group",
        "close-button",
        "radio-group",
        "checkbox-group",
        "scroll-shadow",
        "spinner",
        "switch-preference",
        "checkbox-preference",
        "radio-preference",
        "slider-preference",
        "arrow-preference",
        "basic-component",
        "small-title",
        "floating-toolbar",
        "scroll-bar",
        "alert-dialog",
        "input-otp",
        "disclosure",
        "label",
        "fieldset",
        "switch-group",
        "tag-group",
        "toolbar",
        "toast",
        "autocomplete",
        "input-group",
        "color-picker",
        "calendar",
        "date-picker",
        "date-range-picker",
        "number-picker",
        "theme-controller",
        "icons",
        "squircle",
        "blur",
        "list-popup",
        "cascading-menu",
        "color-picker-panel",
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
        "bottom-sheet" -> BottomSheetShowcase()
        "drawer" -> DrawerShowcase()
        "table" -> TableShowcase()
        "tabs" -> TabsShowcase()
        "breadcrumb" -> BreadcrumbShowcase()
        "navbar" -> NavbarShowcase()
        "sidebar" -> SidebarShowcase()
        "progress-indicator" -> ProgressShowcase()
        "skeleton" -> SkeletonShowcase()
        "alert" -> AlertShowcase()
        "snackbar" -> SnackbarShowcase()
        "textarea" -> TextareaShowcase()
        "number-field" -> NumberFieldShowcase()
        "search-bar" -> SearchBarShowcase()
        "pagination" -> PaginationShowcase()
        "navigation-bar" -> NavigationBarShowcase()
        "navigation" -> NavigationShowcase()
        "navigation-rail" -> NavigationRailShowcase()
        "floating-action-button" -> FloatingActionButtonShowcase()
        "popover" -> PopoverShowcase()
        "menu" -> MenuShowcase()
        "accordion" -> AccordionShowcase()
        "link" -> LinkShowcase()
        "kbd" -> KbdShowcase()
        "surface" -> SurfaceShowcase()
        "scaffold" -> ScaffoldShowcase()
        "pull-to-refresh" -> PullToRefreshShowcase()
        "meter" -> MeterShowcase()
        "description" -> DescriptionShowcase()
        "toggle-button" -> ToggleButtonShowcase()
        "button-group" -> ButtonGroupShowcase()
        "close-button" -> CloseButtonShowcase()
        "radio-group" -> RadioGroupShowcase()
        "checkbox-group" -> CheckboxGroupShowcase()
        "scroll-shadow" -> ScrollShadowShowcase()
        "spinner" -> SpinnerShowcase()
        "switch-preference" -> SwitchPreferenceShowcase()
        "checkbox-preference" -> CheckboxPreferenceShowcase()
        "radio-preference" -> RadioPreferenceShowcase()
        "slider-preference" -> SliderPreferenceShowcase()
        "arrow-preference" -> ArrowPreferenceShowcase()
        "basic-component" -> BasicComponentShowcase()
        "small-title" -> SmallTitleShowcase()
        "floating-toolbar" -> FloatingToolbarShowcase()
        "scroll-bar" -> ScrollBarShowcase()
        "alert-dialog" -> AlertDialogShowcase()
        "input-otp" -> InputOtpShowcase()
        "disclosure" -> DisclosureShowcase()
        "label" -> LabelShowcase()
        "fieldset" -> FieldsetShowcase()
        "switch-group" -> SwitchGroupShowcase()
        "tag-group" -> TagGroupShowcase()
        "toolbar" -> ToolbarShowcase()
        "toast" -> ToastShowcase()
        "autocomplete" -> AutocompleteShowcase()
        "input-group" -> InputGroupShowcase()
        "color-picker" -> ColorPickerShowcase()
        "calendar" -> CalendarShowcase()
        "date-picker" -> DatePickerShowcase()
        "date-range-picker" -> DateRangePickerShowcase()
        "number-picker" -> NumberPickerShowcase()
        "theme-controller" -> ThemeControllerShowcase()
        "icons" -> IconsShowcase()
        "squircle" -> SquircleShowcase()
        "blur" -> BlurShowcase()
        "list-popup" -> ListPopupShowcase()
        "cascading-menu" -> CascadingMenuShowcase()
        "color-picker-panel" -> ColorPickerPanelShowcase()
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
private fun ProgressShowcase() {
    var uploadProgress by remember { mutableStateOf(0.35f) }

    ShowcasePage(title = "Elegant Progress") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Determinate linear and circular",
            description = "A fraction in 0f..1f fills the track and sweeps the ring clockwise from the top.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xxl),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantCircularProgressIndicator(progress = uploadProgress)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                ) {
                    Text(
                        text = "Upload ${(uploadProgress * 100).roundToInt()}%",
                        style = ElegantTheme.typography.labelMedium,
                        color = colors.textSecondary,
                    )
                    ElegantLinearProgressIndicator(progress = uploadProgress)
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "INDETERMINATE",
            title = "Continuous activity",
            description = "Pass null to show an endless sweeping segment or rotating arc.",
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xxl),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xl),
                ) {
                    ElegantLinearProgressIndicator(progress = null)
                    ElegantLinearProgressIndicator(progress = null)
                }
                ElegantCircularProgressIndicator(progress = null)
                ElegantCircularProgressIndicator(progress = null)
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "File upload row",
            description = "A circular spinner and a linear track report the same upload with an action button.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantCircularProgressIndicator(progress = uploadProgress)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
                ) {
                    Text(
                        text = "report.pdf",
                        style = ElegantTheme.typography.labelMedium,
                        color = colors.textPrimary,
                    )
                    ElegantLinearProgressIndicator(progress = uploadProgress)
                    Text(
                        text = "${(uploadProgress * 100).roundToInt()}% of 100%",
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                }
                ElegantButton(
                    onClick = {
                        uploadProgress = if (uploadProgress >= 1f) {
                            0f
                        } else {
                            (uploadProgress + 0.2f).coerceAtMost(1f)
                        }
                    },
                ) {
                    Text(if (uploadProgress >= 1f) "Reset" else "Upload")
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun SkeletonShowcase() {
    ShowcasePage(title = "Elegant Skeleton") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Placeholders while content loads",
            description = "A single skeleton or a block of lines previews content without layout jumps.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
                ElegantSkeleton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp),
                )
                ElegantSkeletonBlock(
                    columns = 3,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A profile card skeleton",
            description = "Circle and lines stand in for the avatar and text until data arrives.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantSkeleton(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                )
                ElegantSkeletonBlock(
                    columns = 2,
                    modifier = Modifier.weight(1f),
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun AlertShowcase() {
    var memberName by rememberSaveable { mutableStateOf("") }
    var memberEmail by rememberSaveable { mutableStateOf("") }

    ShowcasePage(title = "Elegant Alert") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Four semantic tones",
            description = "Neutral, positive, warning, and critical alerts tint their container, border, and icon from the active theme.",
        ) {
            ElegantAlert(
                title = "Scheduled maintenance",
                description = "The service resumes at 06:00 UTC.",
            )
            ElegantAlert(
                title = "Backup completed",
                description = "All projects synced without errors.",
                style = ElegantAlertStyle.Positive,
                icon = { ResourceIcon(Res.drawable.check_rounded) },
            )
            ElegantAlert(
                title = "Low disk space",
                description = "Only 10% of the volume remains.",
                style = ElegantAlertStyle.Warning,
            )
            ElegantAlert(
                title = "Deployment failed",
                description = "Review the logs and retry the release.",
                style = ElegantAlertStyle.Critical,
                icon = { ResourceIcon(Res.drawable.delete_rounded) },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "ACTION",
            title = "Caller-owned actions",
            description = "The banner stays non-interactive while a button in the action slot keeps its own focus and activation.",
        ) {
            ElegantAlert(
                title = "Session expired",
                description = "Sign in again to continue working.",
                style = ElegantAlertStyle.Critical,
                icon = { ResourceIcon(Res.drawable.person_rounded) },
                action = {
                    ElegantButton(
                        onClick = {},
                        style = ElegantButtonStyle.Secondary,
                        size = ElegantButtonSize.Small,
                    ) {
                        Text("Sign in")
                    }
                },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "An alert above a form",
            description = "A critical alert explains the blocked state before the fields that need attention.",
        ) {
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
                    .padding(ElegantSpacing.xl),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
            ) {
                ElegantAlert(
                    title = "Workspace at capacity",
                    description = "Upgrade the plan or remove members to invite new collaborators.",
                    style = ElegantAlertStyle.Critical,
                    icon = { ResourceIcon(Res.drawable.person_rounded) },
                    action = {
                        ElegantButton(
                            onClick = {},
                            style = ElegantButtonStyle.Secondary,
                            size = ElegantButtonSize.Small,
                        ) {
                            Text("Manage")
                        }
                    },
                )
                ElegantInput(
                    value = memberName,
                    onValueChange = { memberName = it },
                    label = "Full name",
                    placeholder = "e.g. Maya Chen",
                    leadingIcon = { ResourceIcon(Res.drawable.person_rounded) },
                )
                ElegantInput(
                    value = memberEmail,
                    onValueChange = { memberEmail = it },
                    label = "Email address",
                    placeholder = "you@example.com",
                    leadingIcon = { ResourceIcon(Res.drawable.add_rounded) },
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun SnackbarShowcase() {
    val snackbarHostState = remember { ElegantSnackbarHostState() }
    val scope = rememberCoroutineScope()
    var deletedCount by remember { mutableIntStateOf(0) }
    var completedFlows by remember { mutableIntStateOf(0) }

    ShowcasePage(title = "Elegant Snackbar") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Host, state, and surface",
            description = "The host pins the message to the bottom center of its bounds; showSnackbar suspends until dismissal.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                ElegantSnackbar(
                    text = "Message deleted",
                    actionLabel = "Undo",
                    onActionClick = {},
                )
                ElegantButton(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Live message at the bottom of this page")
                        }
                    },
                ) {
                    Text("Show snackbar")
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "ACTION",
            title = "One clear call to action",
            description = "An action label keeps a 48dp touch target, shows a ripple, and dismisses the message on click.",
        ) {
            ElegantButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Item moved to trash",
                            actionLabel = "Undo",
                        )
                    }
                },
            ) {
                Text("Move to trash")
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Undo flow",
            description = "Dismissal resumes the calling coroutine, so follow-up work chains behind the feedback.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                Text(
                    text = "Deleted items: $deletedCount",
                    style = ElegantTheme.typography.bodyMedium,
                    color = ElegantTheme.colors.textSecondary,
                )
                ElegantButton(
                    onClick = {
                        deletedCount += 1
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Item deleted",
                                actionLabel = "Undo",
                            )
                            completedFlows += 1
                        }
                    },
                ) {
                    Text("Delete item")
                }
                Text(
                    text = "Workflows resumed after dismissal: $completedFlows",
                    style = ElegantTheme.typography.bodyMedium,
                    color = ElegantTheme.colors.textSecondary,
                )
            }
        }

        Box(Modifier.fillMaxWidth()) {
            ElegantSnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun TextareaShowcase() {
    var notes by rememberSaveable { mutableStateOf("") }
    var draft by rememberSaveable { mutableStateOf("") }
    var bio by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }

    ShowcasePage(title = "Elegant Textarea") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Label, placeholder, and guidance",
            description = "A labeled multi-line field with supporting text keeps forms self-explanatory.",
        ) {
            ElegantTextarea(
                value = notes,
                onValueChange = { notes = it },
                label = "Release notes",
                placeholder = "Summarize what changed",
                supportingText = "Markdown is supported.",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled, read-only, error, and length limit",
            description = "Semantic states stay visible without losing the field geometry.",
        ) {
            ElegantTextarea(
                value = "Kept as an immutable record.",
                onValueChange = {},
                label = "Audit note",
                readOnly = true,
            )
            ElegantTextarea(
                value = "Legacy note",
                onValueChange = {},
                label = "Archive",
                enabled = false,
            )
            ElegantTextarea(
                value = bio,
                onValueChange = { bio = it },
                label = "Bio",
                isError = true,
                errorText = "Must be 3-200 characters.",
            )
            ElegantTextarea(
                value = draft,
                onValueChange = { draft = it },
                label = "Draft",
                maxLength = 200,
                supportingText = "${draft.length}/200",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A compact form row",
            description = "The textarea pairs with an input in one horizontal form row.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalAlignment = Alignment.Top,
            ) {
                ElegantInput(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.weight(1f),
                    label = "Your name",
                    placeholder = "e.g. Maya Chen",
                )
                ElegantTextarea(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.weight(1f),
                    label = "Message",
                    placeholder = "Write a few lines",
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun NumberFieldShowcase() {
    var quantity by remember { mutableStateOf(1) }
    var guests by remember { mutableStateOf(2) }
    var tickets by remember { mutableStateOf(2) }

    ShowcasePage(title = "Elegant NumberField") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Basic field with step buttons",
            description = "A labeled integer field with supporting text and compact increase and decrease buttons.",
        ) {
            ElegantNumberField(
                value = quantity,
                onValueChange = { quantity = it },
                label = "Quantity",
                minValue = 1,
                maxValue = 99,
                supportingText = "Orders of at least 1 ship free.",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "RANGE",
            title = "Bounded stepping",
            description = "Buttons and arrow keys stop at the range boundaries and disable there.",
        ) {
            ElegantNumberField(
                value = guests,
                onValueChange = { guests = it },
                label = "Guests",
                minValue = 1,
                maxValue = 8,
                step = 2,
                supportingText = "Even counts keep tables balanced.",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled and error",
            description = "Disabled fields reject input and stepping; errors show critical text below the field.",
        ) {
            ElegantNumberField(
                value = 4,
                onValueChange = {},
                label = "Disabled field",
                enabled = false,
            )
            ElegantNumberField(
                value = 12,
                onValueChange = {},
                label = "People",
                minValue = 1,
                maxValue = 50,
                isError = true,
                errorText = "The hall fits at most 50 people.",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A quantity row",
            description = "The field stretches beside a row label for compact quantity entry.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Tickets",
                    modifier = Modifier.weight(1f),
                    color = ElegantTheme.colors.textSecondary,
                    style = ElegantTheme.typography.labelMedium,
                )
                ElegantNumberField(
                    value = tickets,
                    onValueChange = { tickets = it },
                    modifier = Modifier.weight(1.4f),
                    minValue = 1,
                    maxValue = 10,
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun SearchBarShowcase() {
    val componentCatalog = listOf("Badge", "Divider", "Input", "Search Bar", "Tag")
    var query by remember { mutableStateOf("") }
    var draft by remember { mutableStateOf("Components") }

    ShowcasePage(title = "Elegant SearchBar") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Pill-shaped search field",
            description = "The owned magnifier glyph leads the field; the placeholder shows while the query is empty.",
        ) {
            ElegantSearchBar(
                query = query,
                onQueryChange = { query = it },
                placeholder = "Search components",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled and clearable",
            description = "A disabled field rejects focus and input; a non-empty query reveals the drawn-X clear button.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
                ElegantSearchBar(
                    query = "Components",
                    onQueryChange = {},
                    enabled = false,
                )
                ElegantSearchBar(
                    query = draft,
                    onQueryChange = { draft = it },
                    placeholder = "Search components",
                    onClear = { draft = "" },
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Filtering a list",
            description = "The query filters an ElegantList of components sitting beneath the field.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
                ElegantSearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    placeholder = "Filter components",
                )
                val visibleItems = componentCatalog.filter { it.contains(query, ignoreCase = true) }
                if (visibleItems.isEmpty()) {
                    Text(
                        text = "No components match \"$query\".",
                        style = ElegantTheme.typography.bodyMedium,
                        color = ElegantTheme.colors.textSecondary,
                    )
                } else {
                    ElegantList {
                        visibleItems.forEach { item ->
                            ElegantListItem(title = { Text(item) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PaginationShowcase() {
    ShowcasePage(title = "Elegant Pagination") { compact ->
        var page by remember { mutableStateOf(1) }

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Bounded pagination keeps a controlled page state",
            description = "The current page stays selected, first and last pages remain visible, and chevrons disable at the boundaries.",
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ElegantPagination(
                    page = page,
                    onPageChange = { page = it },
                    pageCount = 10,
                )
                Text(
                    text = "Page $page of 10",
                    style = ElegantTheme.typography.bodyMedium,
                    color = ElegantTheme.colors.textSecondary,
                )
            }
        }

        var widePage by remember { mutableStateOf(1) }

        DemoCard(
            compact = compact,
            eyebrow = "MANY PAGES",
            title = "Distant pages collapse into a single ellipsis per gap",
            description = "Sibling pages widen the visible window while collapsed gaps keep the row compact.",
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ElegantPagination(
                    page = widePage,
                    onPageChange = { widePage = it },
                    pageCount = 50,
                    siblingCount = 2,
                )
                Text(
                    text = "Page $widePage of 50",
                    style = ElegantTheme.typography.bodyMedium,
                    color = ElegantTheme.colors.textSecondary,
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled pagination keeps layout and announces itself",
            description = "Every item renders with the disabled colors and chevrons are non-interactive.",
        ) {
            ElegantPagination(
                page = 3,
                onPageChange = {},
                pageCount = 10,
                enabled = false,
            )
        }

        val pageSize = 5
        var dataPage by remember { mutableStateOf(1) }
        val entries = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta")
        val visibleEntries = entries.slice(
            ((dataPage - 1) * pageSize).coerceAtLeast(0) until
                (dataPage * pageSize).coerceAtMost(entries.size),
        )
        val tableColumns = listOf(
            ElegantTableColumn(title = "Entry"),
            ElegantTableColumn(title = "Index", textAlign = TextAlign.End),
        )

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A paginated table pages through its rows",
            description = "The table renders one slice while the pagination row stays in control.",
        ) {
            Column {
                ElegantTable(
                    columns = tableColumns,
                    rows = visibleEntries.mapIndexed { index, entry ->
                        ElegantTableRow(
                            cells = listOf(
                                entry,
                                (((dataPage - 1) * pageSize) + index + 1).toString(),
                            ),
                        )
                    },
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    ElegantPagination(
                        page = dataPage,
                        onPageChange = { dataPage = it },
                        pageCount = ((entries.size + pageSize - 1) / pageSize),
                    )
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun NavigationBarShowcase() {
    var selected by rememberSaveable { mutableStateOf(0) }
    val colors = ElegantTheme.colors
    val items = listOf(
        ElegantNavigationBarItem("Home"),
        ElegantNavigationBarItem("Library"),
        ElegantNavigationBarItem("Settings"),
    )

    ShowcasePage(title = "Elegant NavigationBar") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Destinations switch one content area",
            description = "Equal-width items keep the selection in a single controlled state with a pill indicator.",
        ) {
            ElegantNavigationBar(
                selectedIndex = selected,
                onSelect = { selected = it },
                items = items,
            )
            Text(
                text = "Showing ${items[selected.coerceIn(items.indices)].text}",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
                modifier = Modifier.padding(top = ElegantSpacing.lg),
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled items stay visible",
            description = "A disabled item keeps its slot and announces its state without callbacks.",
        ) {
            ElegantNavigationBar(
                selectedIndex = selected,
                onSelect = { selected = it },
                items = listOf(
                    ElegantNavigationBarItem("Home"),
                    ElegantNavigationBarItem("Archive", enabled = false),
                    ElegantNavigationBarItem("Shared"),
                ),
            )
            Text(
                text = "Disabled bar",
                style = ElegantTheme.typography.labelMedium,
                color = colors.textTertiary,
                modifier = Modifier.padding(top = ElegantSpacing.lg),
            )
            ElegantNavigationBar(
                selectedIndex = 0,
                onSelect = {},
                items = listOf(ElegantNavigationBarItem("Offline")),
                enabled = false,
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Page with a top bar and bottom navigation",
            description = "The bar pins to the bottom while the content column fills the rest.",
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
            ) {
                ElegantNavbar(
                    title = {
                        Text(
                            text = items[selected.coerceIn(items.indices)].text,
                            style = ElegantTheme.typography.titleMedium,
                        )
                    },
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(colors.backgroundCanvas),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Content for ${items[selected.coerceIn(items.indices)].text}",
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                }
                ElegantNavigationBar(
                    selectedIndex = selected,
                    onSelect = { selected = it },
                    items = items,
                )
            }
        }
    }
}

@Composable
private fun NavigationRailShowcase() {
    var selected by remember { mutableStateOf(0) }

    ShowcasePage(title = "Elegant NavigationRail") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Controlled destination rail",
            description = "An 80dp rail with a selected index, header slot, and animated item states.",
        ) {
            ElegantNavigationRail(
                selectedIndex = selected,
                onSelect = { selected = it },
                items = listOf(
                    ElegantNavigationRailItem("Home"),
                    ElegantNavigationRailItem("Search"),
                    ElegantNavigationRailItem("Notifications"),
                    ElegantNavigationRailItem("Profile"),
                ),
                header = {
                    ElegantAvatar(name = "Maya Chen", initials = "MC")
                },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled item and non-interactive bar",
            description = "Per-item enabled flags keep disabled entries resting, and disabling the whole bar drops all interactivity.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xl),
            ) {
                ElegantNavigationRail(
                    selectedIndex = 1,
                    onSelect = {},
                    items = listOf(
                        ElegantNavigationRailItem("Inbox"),
                        ElegantNavigationRailItem("Archived"),
                        ElegantNavigationRailItem("Trash", enabled = false),
                    ),
                )
                ElegantNavigationRail(
                    selectedIndex = 0,
                    onSelect = {},
                    enabled = false,
                    items = listOf(
                        ElegantNavigationRailItem("Read-only A"),
                        ElegantNavigationRailItem("Read-only B"),
                    ),
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Rail beside content",
            description = "The rail anchors a layout row while content flows next to it.",
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(ElegantTheme.colors.backgroundSubtle),
            ) {
                ElegantNavigationRail(
                    selectedIndex = selected,
                    onSelect = { selected = it },
                    items = listOf(
                        ElegantNavigationRailItem("Dashboard"),
                        ElegantNavigationRailItem("Projects"),
                        ElegantNavigationRailItem("Team"),
                    ),
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(ElegantSpacing.xl),
                ) {
                    Text("Dashboard", style = ElegantTheme.typography.titleMedium)
                    Text("Content goes here.", style = ElegantTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun FloatingActionButtonShowcase() {
    ShowcasePage(title = "Elegant FloatingActionButton") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Standard and compact",
            description = "A 56dp circle (40dp compact) floats above the content with medium elevation, animated state colors, and a restrained press scale.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xl),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantFloatingActionButton(onClick = {}) {
                    ResourceIcon(Res.drawable.add_rounded)
                }
                ElegantFloatingActionButton(
                    onClick = {},
                    compact = true,
                ) {
                    ResourceIcon(Res.drawable.add_rounded)
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled",
            description = "Disabled FABs keep their fixed touch target, never invoke callbacks, and announce the disabled state.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xl),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantFloatingActionButton(
                    onClick = {},
                    enabled = false,
                ) {
                    ResourceIcon(Res.drawable.add_rounded)
                }
                ElegantFloatingActionButton(
                    onClick = {},
                    compact = true,
                    enabled = false,
                ) {
                    ResourceIcon(Res.drawable.add_rounded)
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Anchored to the screen edge",
            description = "The FAB anchors to the bottom end of its container, floating above the content it controls.",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(ElegantTheme.colors.backgroundSubtle)
                    .padding(ElegantSpacing.md),
            ) {
                Text(
                    text = "Content surface",
                    style = ElegantTheme.typography.bodyMedium,
                    color = ElegantTheme.colors.textSecondary,
                )
                ElegantFloatingActionButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.BottomEnd),
                ) {
                    ResourceIcon(Res.drawable.add_rounded)
                }
            }
        }
    }
}

@Composable
private fun PopoverShowcase() {
    ShowcasePage(title = "Elegant Popover") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "One tap opens the settings panel",
            description = "Clicking the trigger toggles the popover; outside click and Escape dismiss it.",
        ) {
            Text(
                text = "Tap the trigger to open the popover.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantPopover(
                popover = {
                    Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                        Text("Settings", style = ElegantTheme.typography.labelMedium)
                        Text(
                            text = "Notifications, appearance, and account preferences.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                },
            ) {
                PopoverTrigger("Settings")
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "PLACEMENT",
            title = "Logical around the trigger",
            description = "Top, bottom, start, and end placements; Start and End mirror in RTL layouts.",
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
            ) {
                PopoverPlacementPreview(ElegantPopoverPlacement.Top)
                PopoverPlacementPreview(ElegantPopoverPlacement.Bottom)
                PopoverPlacementPreview(ElegantPopoverPlacement.Start)
                PopoverPlacementPreview(ElegantPopoverPlacement.End)
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A popover that takes action",
            description = "The focusable popup lets interactive controls live inside the popover.",
        ) {
            ElegantPopover(
                popover = {
                    Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                        Text("Clear all filters?", style = ElegantTheme.typography.labelMedium)
                        ElegantButton(
                            onClick = {},
                            style = ElegantButtonStyle.Secondary,
                        ) {
                            Text("Confirm")
                        }
                    }
                },
                placement = ElegantPopoverPlacement.End,
            ) {
                ShowcaseIconButton(
                    resource = Res.drawable.more_vert_rounded,
                    contentDescription = "More actions",
                    onClick = {},
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun PopoverTrigger(label: String) {
    val colors = ElegantTheme.colors
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(ElegantRadius.sm))
            .background(colors.backgroundSubtle)
            .padding(horizontal = ElegantSpacing.lg, vertical = ElegantSpacing.sm),
    ) {
        Text(
            text = label,
            color = colors.textPrimary,
            style = ElegantTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun PopoverPlacementPreview(placement: ElegantPopoverPlacement) {
    ElegantPopover(
        popover = { Text("${placement.name} placement") },
        placement = placement,
    ) {
        PopoverTrigger(placement.name)
    }
}

@Composable
private fun MenuShowcase() {
    var moreOpen by remember { mutableStateOf(false) }
    var profileOpen by remember { mutableStateOf(false) }
    var toolbarMoreOpen by remember { mutableStateOf(false) }
    var toolbarProfileOpen by remember { mutableStateOf(false) }
    var sortOpen by remember { mutableStateOf(false) }

    ShowcasePage(title = "Elegant Menu") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A dropdown anchored to its trigger",
            description = "The trigger lives in a Box with ElegantMenu; the surface drops below that Box, start-aligned, and dismisses on outside click, Escape, or back.",
        ) {
            Box {
                ShowcaseIconButton(
                    resource = Res.drawable.more_vert_rounded,
                    contentDescription = "More options",
                    onClick = { moreOpen = true },
                )
                ElegantMenu(
                    expanded = moreOpen,
                    onDismissRequest = { moreOpen = false },
                ) {
                    ElegantMenuItem(
                        text = "Edit",
                        onClick = { moreOpen = false },
                        leadingContent = { ResourceIcon(Res.drawable.edit_rounded) },
                    )
                    ElegantMenuItem(
                        text = "Share",
                        onClick = { moreOpen = false },
                        leadingContent = { ResourceIcon(Res.drawable.share_rounded) },
                    )
                    ElegantMenuItem(
                        text = "Delete",
                        onClick = { moreOpen = false },
                        leadingContent = { ResourceIcon(Res.drawable.delete_rounded) },
                    )
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled items and slot content",
            description = "Disabled items never invoke callbacks; leading and trailing slots render in 20dp boxes and follow the item color.",
        ) {
            Box {
                ShowcaseIconButton(
                    resource = Res.drawable.person_rounded,
                    contentDescription = "Profile",
                    onClick = { profileOpen = true },
                )
                ElegantMenu(
                    expanded = profileOpen,
                    onDismissRequest = { profileOpen = false },
                ) {
                    ElegantMenuItem(
                        text = "Account",
                        onClick = { profileOpen = false },
                        leadingContent = { ResourceIcon(Res.drawable.person_rounded) },
                        trailingContent = { ResourceIcon(Res.drawable.check_rounded) },
                    )
                    ElegantMenuItem(
                        text = "Restore",
                        onClick = { profileOpen = false },
                        enabled = false,
                        leadingContent = { ResourceIcon(Res.drawable.add_rounded) },
                        trailingContent = { ResourceIcon(Res.drawable.check_rounded) },
                    )
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Toolbar actions",
            description = "Three icon menus share one row; each anchor Box wraps only its own trigger.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box {
                    ShowcaseIconButton(
                        resource = Res.drawable.more_vert_rounded,
                        contentDescription = "More",
                        onClick = { toolbarMoreOpen = true },
                    )
                    ElegantMenu(
                        expanded = toolbarMoreOpen,
                        onDismissRequest = { toolbarMoreOpen = false },
                    ) {
                        ElegantMenuItem("New file", onClick = { toolbarMoreOpen = false },
                            leadingContent = { ResourceIcon(Res.drawable.add_rounded) })
                        ElegantMenuItem("Open", onClick = { toolbarMoreOpen = false },
                            leadingContent = { ResourceIcon(Res.drawable.share_rounded) })
                    }
                }
                Box {
                    ShowcaseIconButton(
                        resource = Res.drawable.person_rounded,
                        contentDescription = "Profile",
                        onClick = { toolbarProfileOpen = true },
                    )
                    ElegantMenu(
                        expanded = toolbarProfileOpen,
                        onDismissRequest = { toolbarProfileOpen = false },
                    ) {
                        ElegantMenuItem("Account", onClick = { toolbarProfileOpen = false })
                        ElegantMenuItem("Sign out", onClick = { toolbarProfileOpen = false })
                    }
                }
                Box {
                    ShowcaseIconButton(
                        resource = Res.drawable.check_rounded,
                        contentDescription = "Sort",
                        onClick = { sortOpen = true },
                    )
                    ElegantMenu(
                        expanded = sortOpen,
                        onDismissRequest = { sortOpen = false },
                    ) {
                        ElegantMenuItem("Name", onClick = { sortOpen = false },
                            trailingContent = { ResourceIcon(Res.drawable.check_rounded) })
                        ElegantMenuItem("Date", onClick = { sortOpen = false })
                    }
                }
            }
        }
    }
}

@Composable
private fun AccordionShowcase() {
    var expandedIndex by remember { mutableIntStateOf(-1) }
    val expandedItems = remember { mutableStateListOf<Int>() }

    ShowcasePage(title = "Elegant Accordion") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Single-expand accordion",
            description = "One item stays open; activating it again collapses it.",
        ) {
            ElegantAccordion {
                listOf("General", "Appearance", "Privacy").forEachIndexed { index, title ->
                    ElegantAccordionItem(
                        title = title,
                        expanded = expandedIndex == index,
                        onToggle = {
                            expandedIndex = if (expandedIndex == index) -1 else index
                        },
                    ) {
                        Text(
                            text = "Settings for $title reveal here.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "MULTI",
            title = "Multiple expanded items",
            description = "Each item toggles independently.",
        ) {
            ElegantAccordion {
                listOf("Releases", "Contributors", "Documentation").forEachIndexed { index, title ->
                    ElegantAccordionItem(
                        title = title,
                        expanded = expandedItems.contains(index),
                        onToggle = {
                            if (expandedItems.contains(index)) {
                                expandedItems.remove(index)
                            } else {
                                expandedItems.add(index)
                            }
                        },
                        supportingText = "Independent expanded state",
                    ) {
                        Text(
                            text = "$title body stays independent of the other items.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "FAQ card",
            description = "An accordion groups answers in one bordered surface.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                Text(
                    text = "Frequently asked",
                    style = ElegantTheme.typography.labelLarge,
                    color = colors.textPrimary,
                )
                ElegantAccordion {
                    ElegantAccordionItem(
                        title = "What is Elegant UI?",
                        expanded = true,
                        onToggle = {},
                        supportingText = "Refined Compose Multiplatform components",
                    ) {
                        Text(
                            text = "A shared component library for Android, Desktop, and Web.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                    ElegantAccordionItem(
                        title = "Which platforms are supported?",
                        expanded = false,
                        onToggle = {},
                    ) {
                        Text(
                            text = "Android 24+, Desktop JVM, and Web/Wasm.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                    ElegantAccordionItem(
                        title = "Is the component library free?",
                        expanded = false,
                        onToggle = {},
                        enabled = false,
                        supportingText = "Disabled items never invoke onToggle",
                    ) {
                        Text(
                            text = "Hidden body.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LinkShowcase() {
    var clicks by remember { mutableStateOf(0) }

    ShowcasePage(title = "Elegant Link") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Inline links",
            description = "Text-only links keep a compact label while the touch target expands to the 48dp minimum.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                ElegantLink(
                    text = "View release notes",
                    onClick = { clicks += 1 },
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Signed out. ",
                        color = ElegantTheme.colors.textSecondary,
                        style = ElegantTheme.typography.bodyMedium,
                    )
                    ElegantLink(
                        text = "Sign in",
                        onClick = { clicks += 1 },
                    )
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled and hover",
            description = "Hovered links brighten to the hover role; disabled links drop to tertiary text and never invoke the callback.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                ElegantLink(
                    text = "Open settings",
                    onClick = { clicks += 1 },
                )
                ElegantLink(
                    text = "Unavailable action",
                    onClick = { clicks += 1 },
                    enabled = false,
                )
                Text(
                    text = "Activated $clicks times",
                    color = ElegantTheme.colors.textSecondary,
                    style = ElegantTheme.typography.bodyMedium,
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Profile card",
            description = "A link row on a card surface next to an identity avatar.",
        ) {
            ElegantCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(ElegantSpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                    ) {
                        ElegantAvatar(name = "Ada Lovelace")
                        Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs)) {
                            Text(
                                text = "Ada Lovelace",
                                style = ElegantTheme.typography.labelLarge,
                            )
                            Text(
                                text = "Analytical Engine",
                                color = ElegantTheme.colors.textSecondary,
                                style = ElegantTheme.typography.bodyMedium,
                            )
                        }
                    }
                    ElegantLink(
                        text = "View full profile",
                        onClick = { clicks += 1 },
                    )
                }
            }
        }
    }
}

@Composable
private fun KbdShowcase() {
    ShowcasePage(title = "Elegant Kbd") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Single keys and chords",
            description = "Compact key badges share one recessed visual contract.",
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantKbd(text = "K")
                ElegantKbd(text = "Esc")
                ElegantKbd(text = "Cmd + K")
                ElegantKbd(text = "Ctrl + Shift + P")
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Shortcut list",
            description = "A key badge pairs with its action label in a scannable column.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ElegantKbd(text = "Ctrl + K")
                    Text(
                        text = "Open command palette",
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ElegantKbd(text = "Ctrl + Shift + P")
                    Text(
                        text = "Show all commands",
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ElegantKbd(text = "Ctrl + B")
                    Text(
                        text = "Toggle sidebar",
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT 2",
            title = "Search shortcut hint",
            description = "Search bars hint at their activation shortcut with key badges.",
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Search",
                    style = ElegantTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                )
                ElegantKbd(text = "Cmd + F")
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun SurfaceShowcase() {
    var taps by remember { mutableIntStateOf(0) }

    ShowcasePage(title = "Elegant Surface") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A low-level container",
            description = "No style presets, no padding: background, border, and click only.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                ElegantSurface {
                    Column(Modifier.padding(ElegantSpacing.xl)) {
                        Text("Plain", style = ElegantTheme.typography.labelLarge)
                        Text(
                            text = "The resting container color with no border.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                }
                ElegantSurface(borderWidth = 1.dp) {
                    Column(Modifier.padding(ElegantSpacing.xl)) {
                        Text("Bordered", style = ElegantTheme.typography.labelLarge)
                        Text(
                            text = "A 1dp border outlines the container.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                }
                ElegantSurface(
                    onClick = { taps++ },
                    borderWidth = 1.dp,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(ElegantSpacing.xl),
                        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text("Clickable", style = ElegantTheme.typography.labelLarge)
                            Text(
                                text = "Hover, press, focus, and ripple land here.",
                                color = colors.textSecondary,
                                style = ElegantTheme.typography.bodyMedium,
                            )
                        }
                        Text(
                            text = "$taps",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Nested surfaces",
            description = "An outer surface holds inner surfaces for layered layouts.",
        ) {
            ElegantSurface(borderWidth = 1.dp) {
                Column(Modifier.padding(ElegantSpacing.xl)) {
                    Text("Outer surface", style = ElegantTheme.typography.labelLarge)
                    Text(
                        text = "Each surface provides its own content color.",
                        color = colors.textSecondary,
                        style = ElegantTheme.typography.bodyMedium,
                    )
                    Spacer(Modifier.height(ElegantSpacing.md))
                    ElegantSurface {
                        Column(Modifier.padding(ElegantSpacing.lg)) {
                            Text("Inner surface", style = ElegantTheme.typography.labelMedium)
                            Text(
                                text = "Nested content groups without a new card.",
                                color = colors.textSecondary,
                                style = ElegantTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun ScaffoldShowcase() {
    val snackbarHostState = remember { ElegantSnackbarHostState() }
    val scope = rememberCoroutineScope()
    var selected by remember { mutableStateOf(0) }
    val items = listOf(
        ElegantNavigationBarItem("Home"),
        ElegantNavigationBarItem("Library"),
        ElegantNavigationBarItem("Settings"),
    )

    ShowcasePage(title = "Elegant Scaffold") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Bars, FAB, and snackbar in one shell",
            description = "Bar heights are measured automatically and the content insets follow; the FAB and snackbar host float above the bottom bar.",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
            ) {
                ElegantScaffold(
                    topBar = {
                        ElegantNavbar(
                            title = {
                                Text(
                                    text = "Home",
                                    style = ElegantTheme.typography.titleMedium,
                                )
                            },
                        )
                    },
                    floatingActionButton = {
                        ElegantFloatingActionButton(
                            onClick = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Message drafted")
                                }
                            },
                        ) {
                            ResourceIcon(Res.drawable.edit_rounded)
                        }
                    },
                    snackbarHost = {
                        ElegantSnackbarHost(hostState = snackbarHostState)
                    },
                ) { innerPadding ->
                    Text(
                        text = "Content insets below the measured top bar and stays clear of the floating action button.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding)
                            .padding(ElegantSpacing.xl),
                        style = ElegantTheme.typography.bodyMedium,
                        color = ElegantTheme.colors.textSecondary,
                    )
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Full page with bottom navigation",
            description = "A navbar, a destination list, and a bottom navigation bar share one measured shell.",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
            ) {
                ElegantScaffold(
                    topBar = {
                        ElegantNavbar(
                            title = {
                                Text(
                                    text = items[selected.coerceIn(items.indices)].text,
                                    style = ElegantTheme.typography.titleMedium,
                                )
                            },
                        )
                    },
                    bottomBar = {
                        ElegantNavigationBar(
                            selectedIndex = selected,
                            onSelect = { selected = it },
                            items = items,
                        )
                    },
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding)
                            .padding(horizontal = ElegantSpacing.xl),
                        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                    ) {
                        items.forEachIndexed { index, item ->
                            Text(
                                text = "${item.text} entry ${index + 1}",
                                style = ElegantTheme.typography.labelLarge,
                                color = ElegantTheme.colors.textPrimary,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PullToRefreshShowcase() {
    var refreshes by remember { mutableStateOf(0) }
    var refreshing by remember { mutableStateOf(false) }

    ShowcasePage(title = "Elegant PullToRefresh") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Pull down to refresh",
            description = "Drag the list past the 80dp threshold and release to trigger a refresh counted below.",
        ) {
            ElegantPullToRefresh(
                isRefreshing = refreshing,
                onRefresh = { refreshing = true },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .verticalScroll(rememberScrollState())
                        .background(
                            color = colors.backgroundSubtle,
                            shape = RoundedCornerShape(ElegantRadius.md),
                        )
                        .padding(ElegantSpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                ) {
                    repeat(16) { index ->
                        Text(
                            text = "Item $index — refreshed $refreshes times",
                            style = ElegantTheme.typography.bodyMedium,
                            color = colors.textSecondary,
                        )
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Refreshable list",
            description = "An ElegantList feed inside the pull container updates its counter when a refresh completes.",
        ) {
            ElegantPullToRefresh(
                isRefreshing = refreshing,
                onRefresh = { refreshing = true },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    ElegantList {
                        repeat(12) { index ->
                            ElegantListItem(
                                title = { Text("Inbox message $index") },
                                supportingText = { Text("Refreshed $refreshes times") },
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(ElegantPullToRefreshDefaults.RefreshDurationMillis.toLong())
            refreshes += 1
            refreshing = false
        }
    }
}

@Composable
private fun MeterShowcase() {
    ShowcasePage(title = "Elegant Meter") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Auto tones across usage zones",
            description = "The tone stays healthy up to the high threshold, warns above it, and turns critical near full.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
                ElegantMeter(value = 0.22f, label = "Low usage")
                ElegantMeter(value = 0.48f, label = "Medium usage")
                ElegantMeter(value = 0.82f, label = "High usage")
                ElegantMeter(value = 0.97f, label = "Near full")
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "TONES",
            title = "Explicit semantic tones",
            description = "Force a tone with the tone parameter to decouple the fill color from the value.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
                ElegantMeter(value = 0.6f, tone = ElegantMeterTone.Neutral, label = "Neutral")
                ElegantMeter(value = 0.6f, tone = ElegantMeterTone.Positive, label = "Positive")
                ElegantMeter(value = 0.6f, tone = ElegantMeterTone.Warning, label = "Warning")
                ElegantMeter(value = 0.6f, tone = ElegantMeterTone.Critical, label = "Critical")
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Storage row",
            description = "A labeled meter measures a storage partition against its own value range.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
                ) {
                    ElegantMeter(
                        value = 63.4f,
                        valueRange = 0f..128f,
                        label = "Storage",
                    )
                    Text(
                        text = "63.4 GB of 128 GB",
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun DescriptionShowcase() {
    ShowcasePage(title = "Elegant Description") { compact ->
        val profileItems = listOf(
            ElegantDescriptionItem(label = "Owner", value = "Maya Chen"),
            ElegantDescriptionItem(label = "Repository", value = "elegant"),
            ElegantDescriptionItem(label = "License", value = "Proprietary", enabled = false),
        )

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Key-value rows on one rhythm",
            description = "A fixed label column keeps terms aligned while flexible values fill the remaining width.",
        ) {
            ElegantDescription(items = profileItems)
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A profile card with an identity header",
            description = "The non-interactive card wraps the description list under an avatar-led header.",
        ) {
            ElegantCard {
                Column(
                    modifier = Modifier.padding(ElegantSpacing.xl),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ElegantAvatar(name = "Maya Chen", initials = "MC")
                        Column {
                            Text(
                                text = "Maya Chen",
                                style = ElegantTheme.typography.labelLarge,
                                color = ElegantTheme.colors.textPrimary,
                            )
                            Text(
                                text = "Design systems lead",
                                style = ElegantTheme.typography.bodyMedium,
                                color = ElegantTheme.colors.textSecondary,
                            )
                        }
                    }
                    ElegantDescription(items = profileItems)
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun ToggleButtonShowcase() {
    var nightMode by remember { mutableStateOf(false) }
    var viewPeriod by remember { mutableIntStateOf(0) }

    ShowcasePage(title = "Elegant ToggleButton") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Single and grouped toggles",
            description = "One 48dp touch target per toggle; the group joins them with zero spacing.",
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            ) {
                ElegantToggleButton(
                    selected = nightMode,
                    onToggle = { nightMode = it },
                ) {
                    Text("Night mode")
                }
                ElegantToggleButtonGroup {
                    ElegantToggleButton(
                        selected = viewPeriod == 0,
                        onToggle = { if (it) viewPeriod = 0 },
                    ) {
                        Text("Day")
                    }
                    ElegantToggleButton(
                        selected = viewPeriod == 1,
                        onToggle = { if (it) viewPeriod = 1 },
                    ) {
                        Text("Week")
                    }
                    ElegantToggleButton(
                        selected = viewPeriod == 2,
                        onToggle = { if (it) viewPeriod = 2 },
                    ) {
                        Text("Month")
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Filter a list",
            description = "A toggle cluster drives the rows of a compact list surface.",
        ) {
            var activeFilter by remember { mutableIntStateOf(0) }
            val members = listOf(
                "Maya Chen" to "Design systems",
                "Noah Williams" to "Multiplatform engineering",
                "Ava Patel" to "Product research",
                "Leo Tanaka" to "Quality engineering",
            )
            val offline = setOf("Leo Tanaka")

            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
                ElegantToggleButtonGroup {
                    ElegantToggleButton(
                        selected = activeFilter == 0,
                        onToggle = { if (it) activeFilter = 0 },
                    ) {
                        Text("All")
                    }
                    ElegantToggleButton(
                        selected = activeFilter == 1,
                        onToggle = { if (it) activeFilter = 1 },
                    ) {
                        Text("Active")
                    }
                    ElegantToggleButton(
                        selected = activeFilter == 2,
                        onToggle = { if (it) activeFilter = 2 },
                    ) {
                        Text("Offline")
                    }
                }
                ElegantList {
                    members.forEach { (name, role) ->
                        if (activeFilter == 0 || (activeFilter == 1 && name !in offline) ||
                            (activeFilter == 2 && name in offline)
                        ) {
                            ElegantListItem(
                                title = { Text(name) },
                                supportingText = { Text(role) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ButtonGroupShowcase() {
    var selected by remember { mutableStateOf(0) }
    val colors = ElegantTheme.colors

    ShowcasePage(title = "Elegant ButtonGroup") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Segmented control switches content",
            description = "Equal-width cells keep one controlled selection shared with the content below.",
        ) {
            val views = listOf(
                ElegantButtonGroupItem("Day"),
                ElegantButtonGroupItem("Week"),
                ElegantButtonGroupItem("Month"),
            )
            ElegantButtonGroup(
                selectedIndex = selected,
                onSelect = { selected = it },
                items = views,
            )
            Text(
                text = "Showing ${views[selected].text} view",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
                modifier = Modifier.padding(top = ElegantSpacing.lg),
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled items and null selection",
            description = "A disabled item keeps its slot, a disabled group stays quiet, and null shows no selection.",
        ) {
            ElegantButtonGroup(
                selectedIndex = selected,
                onSelect = { selected = it },
                items = listOf(
                    ElegantButtonGroupItem("General"),
                    ElegantButtonGroupItem("Security", enabled = false),
                    ElegantButtonGroupItem("Billing"),
                ),
            )
            Column(modifier = Modifier.padding(top = ElegantSpacing.lg)) {
                Text(
                    text = "Disabled group",
                    style = ElegantTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                )
                ElegantButtonGroup(
                    selectedIndex = 1,
                    onSelect = {},
                    items = listOf(
                        ElegantButtonGroupItem("Offline"),
                        ElegantButtonGroupItem("Online"),
                    ),
                    enabled = false,
                    modifier = Modifier.padding(top = ElegantSpacing.sm),
                )
                Text(
                    text = "Null selection",
                    style = ElegantTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                    modifier = Modifier.padding(top = ElegantSpacing.lg),
                )
                ElegantButtonGroup(
                    selectedIndex = null,
                    onSelect = {},
                    items = listOf(
                        ElegantButtonGroupItem("Compact"),
                        ElegantButtonGroupItem("Comfortable"),
                    ),
                    modifier = Modifier.padding(top = ElegantSpacing.sm),
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Toolbar row",
            description = "A compact control row combines the group with contextual text.",
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
            ) {
                ElegantButtonGroup(
                    selectedIndex = selected,
                    onSelect = { selected = it },
                    items = listOf(
                        ElegantButtonGroupItem("List"),
                        ElegantButtonGroupItem("Grid"),
                    ),
                )
                Text(
                    text = "${selected + 1} of 2 layouts",
                    style = ElegantTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                )
            }
        }
    }
}

@Composable
private fun CloseButtonShowcase() {
    var dismissCount by remember { mutableIntStateOf(0) }

    ShowcasePage(title = "Elegant CloseButton") { compact ->
        val colors = ElegantTheme.colors
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "One dismiss action, one rhythm",
            description = "A fixed X glyph on a quiet pill inside a 48dp interaction target.",
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantCloseButton(onClick = { dismissCount++ })
                ElegantCloseButton(
                    onClick = { dismissCount++ },
                    contentDescription = "Dismiss suggestions",
                )
                ElegantCloseButton(onClick = {}, enabled = false)
            }

            Text(
                text = "Dismissed  $dismissCount",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A card that lets go",
            description = "Close buttons anchor to the header corner of dismissible surfaces.",
        ) {
            ElegantCard {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(ElegantSpacing.xl),
                        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
                    ) {
                        Text(
                            text = "Release notes",
                            style = ElegantTheme.typography.titleMedium,
                            color = colors.textPrimary,
                        )
                        Text(
                            text = "CloseButton joins 51 components across Android, Desktop, and Web.",
                            style = ElegantTheme.typography.bodyMedium,
                            color = colors.textSecondary,
                        )
                    }
                    ElegantCloseButton(
                        onClick = { dismissCount++ },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(ElegantSpacing.xs),
                    )
                }
            }
        }
    }
}

@Composable
private fun RadioGroupShowcase() {
    var accent by rememberSaveable { mutableStateOf("violet") }

    ShowcasePage(title = "Elegant RadioGroup") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "One value from a bounded set",
            description = "Each row keeps the 48dp radio target while the group adds 4dp of rhythm and one shared selection state.",
        ) {
            ElegantRadioGroup(
                selectedValue = accent,
                onSelect = { accent = it },
                items = listOf(
                    ElegantRadioGroupItem(text = "Violet", value = "violet"),
                    ElegantRadioGroupItem(text = "Indigo", value = "indigo"),
                    ElegantRadioGroupItem(text = "Teal", value = "teal"),
                ),
                supportingText = "Choose the accent used across the app.",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled item and disabled group",
            description = "A disabled item keeps its selection visible but rejects interaction; disabling the group dims every row and the supporting text.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                ElegantRadioGroup(
                    selectedValue = "pro",
                    onSelect = { accent = it },
                    items = listOf(
                        ElegantRadioGroupItem(text = "Free", value = "free"),
                        ElegantRadioGroupItem(text = "Pro", value = "pro"),
                        ElegantRadioGroupItem(text = "Team", value = "team", enabled = false),
                    ),
                )
                ElegantRadioGroup(
                    selectedValue = "express",
                    onSelect = {},
                    enabled = false,
                    items = listOf(
                        ElegantRadioGroupItem(text = "Standard", value = "standard"),
                        ElegantRadioGroupItem(text = "Express", value = "express"),
                    ),
                    supportingText = "Unavailable while the order is locked.",
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Checkout delivery form",
            description = "A radio group picks the delivery method and an input collects the street address.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                ElegantRadioGroup(
                    selectedValue = accent,
                    onSelect = { accent = it },
                    items = listOf(
                        ElegantRadioGroupItem(text = "Standard", value = "standard"),
                        ElegantRadioGroupItem(text = "Express", value = "express"),
                        ElegantRadioGroupItem(text = "Overnight", value = "overnight"),
                    ),
                )
                ElegantInput(
                    value = accent,
                    onValueChange = { accent = it },
                    label = "Street address",
                    placeholder = "123 Main Street",
                    supportingText = "Required for delivery.",
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun CheckboxGroupShowcase() {
    var permissions by remember { mutableStateOf(setOf("camera")) }
    var channels by remember { mutableStateOf(setOf("stable", "beta")) }

    ShowcasePage(title = "Elegant Checkbox Group") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A vertical multi-select list",
            description = "Checkbox rows stack on a 4dp rhythm with one shared, caller-owned selection set.",
        ) {
            ElegantCheckboxGroup(
                selectedValues = permissions,
                onToggle = { value, checked ->
                    permissions = if (checked) permissions + value else permissions - value
                },
                items = listOf(
                    ElegantCheckboxGroupItem(text = "Camera", value = "camera"),
                    ElegantCheckboxGroupItem(text = "Photos", value = "photos"),
                    ElegantCheckboxGroupItem(text = "Microphone", value = "microphone"),
                ),
                supportingText = "Choose what this app may access.",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled items and groups",
            description = "A disabled item or a disabled group never invokes onToggle.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xl)) {
                ElegantCheckboxGroup(
                    selectedValues = setOf("camera"),
                    onToggle = { _, _ -> },
                    items = listOf(
                        ElegantCheckboxGroupItem(text = "Camera", value = "camera"),
                        ElegantCheckboxGroupItem(
                            text = "Microphone",
                            value = "microphone",
                            enabled = false,
                        ),
                    ),
                )
                ElegantCheckboxGroup(
                    selectedValues = setOf("notifications"),
                    onToggle = { _, _ -> },
                    enabled = false,
                    items = listOf(
                        ElegantCheckboxGroupItem(text = "Notifications", value = "notifications"),
                        ElegantCheckboxGroupItem(text = "Announcements", value = "announcements"),
                    ),
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Settings inside a card",
            description = "A release-channel preference rendered as a group inside a settings card.",
        ) {
            ElegantCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(ElegantSpacing.xl),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
                ) {
                    Text(
                        text = "Release channel",
                        style = ElegantTheme.typography.titleMedium,
                        color = ElegantTheme.colors.textPrimary,
                    )
                    ElegantCheckboxGroup(
                        selectedValues = channels,
                        onToggle = { value, checked ->
                            channels = if (checked) channels + value else channels - value
                        },
                        items = listOf(
                            ElegantCheckboxGroupItem(text = "Stable", value = "stable"),
                            ElegantCheckboxGroupItem(text = "Beta", value = "beta"),
                            ElegantCheckboxGroupItem(text = "Nightly", value = "nightly"),
                        ),
                        supportingText = "Nightly builds reset preferences each release.",
                    )
                }
            }
        }
    }
}

@Composable
private fun ScrollShadowShowcase() {
    ShowcasePage(title = "Elegant ScrollShadow") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Vertical scroll shadow",
            description = "The top and bottom edges fade while the list can scroll further.",
        ) {
            val scrollState = rememberScrollState()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(ElegantTheme.colors.surfaceDefault),
            ) {
                ElegantList(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    repeat(10) { index ->
                        ElegantListItem(
                            title = { Text("Item $index") },
                            supportingText = { Text("Supporting line $index") },
                        )
                    }
                }
                ElegantScrollShadow(state = scrollState)
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "HORIZONTAL",
            title = "Horizontal scroll shadow",
            description = "The start and end edges fade while the row can scroll further.",
        ) {
            val scrollState = rememberScrollState()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(scrollState),
                ) {
                    repeat(10) { index ->
                        ElegantListItem(
                            title = { Text("Item $index") },
                            modifier = Modifier.width(160.dp),
                        )
                    }
                }
                ElegantScrollShadow(
                    state = scrollState,
                    orientation = ElegantScrollShadowOrientation.Horizontal,
                )
            }
        }
    }
}

@Composable
private fun SpinnerShowcase() {
    ShowcasePage(title = "Elegant Spinner") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Indeterminate loading",
            description = "A rotating ring signals continuous activity, with an optional label below.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xxl),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantSpinner()
                ElegantSpinner(label = "Loading...")
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "SIZES",
            title = "Small, medium, and large",
            description = "Tune the ring with size and strokeWidth while the layout stays centered.",
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xxl),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantSpinner(size = 24.dp, strokeWidth = 3.dp)
                ElegantSpinner(size = 40.dp, strokeWidth = 4.dp)
                ElegantSpinner(size = 56.dp, strokeWidth = 5.dp)
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Loading card",
            description = "A spinner with a label pairs with skeleton lines while real content loads.",
        ) {
            ElegantCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(ElegantSpacing.xl),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    ElegantSpinner(label = "Loading...")
                    Spacer(modifier = Modifier.height(ElegantSpacing.lg))
                    ElegantSkeletonBlock(columns = 3)
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun SwitchPreferenceShowcase() {
    var notifications by remember { mutableStateOf(true) }
    var sound by remember { mutableStateOf(true) }
    var batterySaver by remember { mutableStateOf(false) }
    var wifi by remember { mutableStateOf(true) }
    var bluetooth by remember { mutableStateOf(false) }

    ShowcasePage(title = "Elegant SwitchPreference") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A settings card of three preferences",
            description = "Each row pairs a 48dp title block with an end-anchored switch and a closing divider.",
        ) {
            ElegantCard(modifier = Modifier.fillMaxWidth()) {
                ElegantSwitchPreference(
                    title = "Notifications",
                    checked = notifications,
                    onCheckedChange = { notifications = it },
                    supportingText = "Receive push notifications",
                )
                ElegantSwitchPreference(
                    title = "Sound",
                    checked = sound,
                    onCheckedChange = { sound = it },
                    supportingText = "Play a sound when new items arrive",
                )
                ElegantSwitchPreference(
                    title = "Battery saver",
                    checked = batterySaver,
                    onCheckedChange = { batterySaver = it },
                    supportingText = "Limit background activity",
                    showDivider = false,
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Preferences inside an ElegantList",
            description = "Switch preferences slot into a list card with the standard 48dp row rhythm, including a disabled row.",
        ) {
            ElegantCard(modifier = Modifier.fillMaxWidth()) {
                ElegantList {
                    ElegantSwitchPreference(
                        title = "Wi-Fi",
                        checked = wifi,
                        onCheckedChange = { wifi = it },
                        supportingText = "Join known networks automatically",
                    )
                    ElegantSwitchPreference(
                        title = "Bluetooth",
                        checked = bluetooth,
                        onCheckedChange = { bluetooth = it },
                        supportingText = "Share audio and connect devices",
                    )
                    ElegantSwitchPreference(
                        title = "Airplane mode",
                        checked = false,
                        onCheckedChange = {},
                        enabled = false,
                        showDivider = false,
                    )
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun CheckboxPreferenceShowcase() {
    var camera by remember { mutableStateOf(true) }
    var photos by remember { mutableStateOf(false) }

    ShowcasePage(title = "Elegant CheckboxPreference") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Permission rows",
            description = "Each row pairs a title block with an end-anchored checkbox and a closing divider.",
        ) {
            ElegantCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    ElegantCheckboxPreference(
                        title = "Camera",
                        checked = camera,
                        onCheckedChange = { camera = it },
                        supportingText = "Allow photo and video capture",
                    )
                    ElegantCheckboxPreference(
                        title = "Photos",
                        checked = photos,
                        onCheckedChange = { photos = it },
                        supportingText = "Read and write the photo library",
                    )
                    ElegantCheckboxPreference(
                        title = "Microphone",
                        checked = false,
                        onCheckedChange = {},
                        enabled = false,
                        supportingText = "Currently unavailable",
                        showDivider = false,
                    )
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun RadioPreferenceShowcase() {
    var theme by rememberSaveable { mutableStateOf("Violet") }

    ShowcasePage(title = "Elegant RadioPreference") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A row that selects itself",
            description = "The whole row is the interactive target; the trailing radio mirrors the same state.",
        ) {
            ElegantCard {
                Column {
                    for (candidate in listOf("Violet", "Indigo", "Teal")) {
                        ElegantRadioPreference(
                            title = candidate,
                            selected = theme == candidate,
                            onSelect = { theme = candidate },
                            showDivider = candidate != "Teal",
                        )
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A settings group on a card",
            description = "Supporting text, a disabled row, and dividers stay exclusive inside one card surface.",
        ) {
            ElegantCard {
                Column {
                    ElegantRadioPreference(
                        title = "Standard",
                        selected = theme == "Standard",
                        onSelect = { theme = "Standard" },
                        supportingText = "3 to 5 business days",
                    )
                    ElegantRadioPreference(
                        title = "Express",
                        selected = theme == "Express",
                        onSelect = { theme = "Express" },
                        supportingText = "1 to 2 business days",
                    )
                    ElegantRadioPreference(
                        title = "Overnight",
                        selected = false,
                        onSelect = {},
                        enabled = false,
                        supportingText = "Currently unavailable",
                        showDivider = false,
                    )
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun SliderPreferenceShowcase() {
    var brightness by remember { mutableStateOf(0.7f) }
    var volume by remember { mutableStateOf(0.5f) }

    ShowcasePage(title = "Elegant SliderPreference") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A display settings row",
            description = "The title row shows the formatted value; the slider below owns drag, tap, and keyboard interaction.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                ElegantSliderPreference(
                    title = "Brightness",
                    value = brightness,
                    onValueChange = { brightness = it },
                    valueFormatter = { "${(it * 100).roundToInt()}%" },
                )
                ElegantSliderPreference(
                    title = "Volume",
                    value = volume,
                    onValueChange = { volume = it },
                    valueFormatter = { "${(it * 100).roundToInt()}%" },
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Inside a settings surface",
            description = "Preferences stack inside a card, each row closed by its inset divider.",
        ) {
            ElegantCard(style = ElegantCardStyle.Filled) {
                Column(Modifier.padding(ElegantSpacing.md)) {
                    Text(
                        text = "Display",
                        modifier = Modifier.padding(horizontal = ElegantSpacing.md, vertical = ElegantSpacing.xs),
                        style = ElegantTheme.typography.labelLarge,
                        color = colors.textPrimary,
                    )
                    ElegantSliderPreference(
                        title = "Brightness",
                        value = brightness,
                        onValueChange = { brightness = it },
                        valueFormatter = { "${(it * 100).roundToInt()}%" },
                    )
                    ElegantSliderPreference(
                        title = "Volume",
                        value = volume,
                        onValueChange = { volume = it },
                        valueFormatter = { "${(it * 100).roundToInt()}%" },
                        showDivider = false,
                    )
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun ArrowPreferenceShowcase() {
    var opened by remember { mutableStateOf(0) }

    ShowcasePage(title = "Elegant ArrowPreference") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "One row, one destination",
            description = "The whole row is the interactive target; the trailing chevron signals a drill-in.",
        ) {
            ElegantCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    ElegantArrowPreference(
                        title = "Account",
                        onClick = { opened++ },
                        supportingText = "Signed in as violet@example.com",
                    )
                    ElegantArrowPreference(
                        title = "Notifications",
                        onClick = { opened++ },
                        supportingText = "Alerts for messages and mentions",
                    )
                    ElegantArrowPreference(
                        title = "About",
                        onClick = { opened++ },
                        showDivider = false,
                    )
                }
            }
            Text(
                text = "Opened $opened settings screen(s)",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
                modifier = Modifier.padding(top = ElegantSpacing.md),
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Settings groups on a card surface",
            description = "Rows stack with their own dividers and keep the 48dp minimum target.",
        ) {
            ElegantCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    ElegantArrowPreference(
                        title = "General",
                        onClick = { opened++ },
                        supportingText = "Language, region, and appearance",
                    )
                    ElegantArrowPreference(
                        title = "Privacy",
                        onClick = {},
                        enabled = false,
                        supportingText = "Locked by administrator",
                    )
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun SmallTitleShowcase() {
    var darkMode by remember { mutableStateOf(false) }
    var analytics by remember { mutableStateOf(true) }
    var soundEffects by remember { mutableStateOf(false) }

    ShowcasePage(title = "Elegant SmallTitle") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Quiet section headings",
            description = "Small labels lead preference rows without competing with them.",
        ) {
            ElegantSmallTitle(text = "GENERAL")
            ElegantSwitchPreference(
                title = "Dark mode",
                checked = darkMode,
                onCheckedChange = { darkMode = it },
            )
            ElegantSmallTitle(text = "PREFERENCES")
            ElegantSwitchPreference(
                title = "Usage analytics",
                checked = analytics,
                onCheckedChange = { analytics = it },
                supportingText = "Send anonymous usage data",
            )
            ElegantCheckboxPreference(
                title = "Sound effects",
                checked = soundEffects,
                onCheckedChange = { soundEffects = it },
                showDivider = false,
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Titled settings surface",
            description = "Three titled groups keep a settings screen scannable.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                    ElegantSmallTitle(text = "DISPLAY")
                    ElegantCard(style = ElegantCardStyle.Outlined) {
                        Column {
                            ElegantSwitchPreference(
                                title = "Dark mode",
                                checked = darkMode,
                                onCheckedChange = { darkMode = it },
                                showDivider = true,
                            )
                            ElegantSwitchPreference(
                                title = "Reduce motion",
                                checked = soundEffects,
                                onCheckedChange = { soundEffects = it },
                                showDivider = false,
                            )
                        }
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                    ElegantSmallTitle(text = "PRIVACY")
                    ElegantCard(style = ElegantCardStyle.Outlined) {
                        Column {
                            ElegantCheckboxPreference(
                                title = "Usage analytics",
                                checked = analytics,
                                onCheckedChange = { analytics = it },
                                showDivider = false,
                            )
                        }
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                    ElegantSmallTitle(text = "ABOUT")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Version",
                            style = ElegantTheme.typography.bodyMedium,
                            color = ElegantTheme.colors.textPrimary,
                        )
                        Text(
                            text = "1.0.0",
                            style = ElegantTheme.typography.bodyMedium,
                            color = ElegantTheme.colors.textSecondary,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FloatingToolbarShowcase() {
    ShowcasePage(title = "Elegant FloatingToolbar") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Actions floating over a selection",
            description = "A raised 48dp pill with fully rounded ends and medium elevation wraps its actions; callers position it with Modifier.align inside their own Box.",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(ElegantTheme.colors.backgroundSubtle),
            ) {
                Text(
                    text = "Selection context",
                    modifier = Modifier.padding(ElegantSpacing.md),
                    style = ElegantTheme.typography.bodyMedium,
                    color = ElegantTheme.colors.textSecondary,
                )
                ElegantFloatingToolbar(
                    modifier = Modifier.align(Alignment.TopCenter),
                ) {
                    ShowcaseIconButton(
                        resource = Res.drawable.edit_rounded,
                        contentDescription = "Edit",
                        modifier = Modifier.padding(horizontal = ElegantSpacing.xxs),
                        onClick = {},
                    )
                    ShowcaseIconButton(
                        resource = Res.drawable.delete_rounded,
                        contentDescription = "Delete",
                        modifier = Modifier.padding(horizontal = ElegantSpacing.xxs),
                        onClick = {},
                    )
                    ShowcaseIconButton(
                        resource = Res.drawable.share_rounded,
                        contentDescription = "Share",
                        modifier = Modifier.padding(horizontal = ElegantSpacing.xxs),
                        onClick = {},
                    )
                    ShowcaseIconButton(
                        resource = Res.drawable.more_vert_rounded,
                        contentDescription = "More options",
                        modifier = Modifier.padding(horizontal = ElegantSpacing.xxs),
                        onClick = {},
                    )
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Hovering above a paragraph",
            description = "The toolbar floats above reading content while its actions keep their own focus, hover, and press states.",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ElegantTheme.colors.backgroundSubtle),
            ) {
                Text(
                    text = "The toolbar hovers above this paragraph. Actions inside keep their own focus, hover, and press states, and the pill provides their content color through LocalContentColor.",
                    modifier = Modifier.padding(ElegantSpacing.md),
                    style = ElegantTheme.typography.bodyMedium,
                    color = ElegantTheme.colors.textSecondary,
                )
                ElegantFloatingToolbar(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = ElegantSpacing.xl),
                ) {
                    ShowcaseIconButton(
                        resource = Res.drawable.edit_rounded,
                        contentDescription = "Edit",
                        onClick = {},
                    )
                    ShowcaseIconButton(
                        resource = Res.drawable.delete_rounded,
                        contentDescription = "Delete",
                        onClick = {},
                    )
                }
            }
        }
    }
}

@Composable
private fun ScrollBarShowcase() {
    ShowcasePage(title = "Elegant ScrollBar") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Vertical scroll bar",
            description = "A slim thumb tracks the scroll position along the end edge.",
        ) {
            val scrollState = rememberScrollState()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(ElegantTheme.colors.surfaceDefault),
            ) {
                ElegantList(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    repeat(10) { index ->
                        ElegantListItem(
                            title = { Text("Item $index") },
                            supportingText = { Text("Supporting line $index") },
                        )
                    }
                }
                ElegantScrollBar(
                    state = scrollState,
                    modifier = Modifier.align(Alignment.CenterEnd),
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "HORIZONTAL",
            title = "Horizontal scroll bar",
            description = "A horizontal scroll bar hugs the bottom edge of the row.",
        ) {
            val scrollState = rememberScrollState()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(scrollState),
                ) {
                    repeat(10) { index ->
                        ElegantListItem(
                            title = { Text("Item $index") },
                            modifier = Modifier.width(160.dp),
                        )
                    }
                }
                ElegantScrollBar(
                    state = scrollState,
                    orientation = ElegantScrollBarOrientation.Horizontal,
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            }
        }
    }
}

@Composable
private fun AlertDialogShowcase() {
    ShowcasePage(title = "Elegant AlertDialog") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A delete confirmation",
            description = "ElegantAlertDialog renders the title, description, and action row; Confirm and dismiss are caller-controlled paths.",
        ) {
            var visible by remember { mutableStateOf(false) }

            Text(
                text = "Tap the trigger to open a delete confirmation.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantButton(onClick = { visible = true }) {
                Text("Delete project")
            }

            ElegantAlertDialog(
                visible = visible,
                onDismissRequest = { visible = false },
                title = "Delete project?",
                description = "This action cannot be undone.",
                confirmText = "Delete",
                onConfirm = { visible = false },
                dismissText = "Cancel",
                onDismiss = { visible = false },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "A gated confirm button",
            description = "confirmEnabled keeps the confirm action inert until the caller allows it; the content slot hosts the enabling control.",
        ) {
            var visible by remember { mutableStateOf(false) }
            var canConfirm by remember { mutableStateOf(false) }

            Text(
                text = "The confirm button stays disabled until enabled inside the dialog.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantButton(onClick = { visible = true }) {
                Text("Open dialog")
            }

            ElegantAlertDialog(
                visible = visible,
                onDismissRequest = { visible = false },
                title = "Release version?",
                description = "The release is irreversible for this channel.",
                confirmText = "Release",
                onConfirm = {
                    visible = false
                    canConfirm = false
                },
                dismissText = "Cancel",
                onDismiss = { visible = false },
                confirmEnabled = canConfirm,
            ) {
                if (!canConfirm) {
                    ElegantButton(
                        onClick = { canConfirm = true },
                        style = ElegantButtonStyle.Secondary,
                        size = ElegantButtonSize.Small,
                    ) {
                        Text("I understand")
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A sign-out flow",
            description = "Dismiss, Cancel, and back/Escape leave the session intact; Confirm resolves the outcome under caller control.",
        ) {
            var pendingSignOut by remember { mutableStateOf(false) }
            var signedOut by remember { mutableStateOf(false) }

            Text(
                text = if (signedOut) "Signed out." else "Tap sign out to confirm the session end.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantButton(
                onClick = { pendingSignOut = true },
                style = if (signedOut) ElegantButtonStyle.Secondary else ElegantButtonStyle.Primary,
            ) {
                Text("Sign out")
            }

            ElegantAlertDialog(
                visible = pendingSignOut,
                onDismissRequest = { pendingSignOut = false },
                title = "Sign out?",
                description = "Unsaved changes are lost.",
                confirmText = "Sign out",
                onConfirm = {
                    pendingSignOut = false
                    signedOut = true
                },
                dismissText = "Stay signed in",
                onDismiss = { pendingSignOut = false },
            )
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun InputOtpShowcase() {
    var code by rememberSaveable { mutableStateOf("") }
    var attempt by rememberSaveable { mutableStateOf("") }

    ShowcasePage(title = "Elegant InputOtp") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A six-digit verification code",
            description = "Digits are accepted into square cells; the caret marks the next empty cell.",
        ) {
            ElegantInputOtp(
                value = code,
                onValueChange = { code = it },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled and error states",
            description = "A disabled field dims its cells; an error turns every cell border critical.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
                ElegantInputOtp(
                    value = "123",
                    onValueChange = {},
                    enabled = false,
                )
                ElegantInputOtp(
                    value = attempt,
                    onValueChange = { attempt = it },
                    isError = true,
                    errorText = "The code you entered is incorrect.",
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Verification card with resend",
            description = "The OTP strip sits on a card with delivery guidance and a resend action.",
        ) {
            ElegantCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(ElegantSpacing.xl),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                ) {
                    Text(
                        text = "Verify your phone",
                        style = ElegantTheme.typography.titleMedium,
                        color = colors.textPrimary,
                    )
                    Text(
                        text = "We sent a 6-digit code to +86 138 0000 0000.",
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                    ElegantInputOtp(
                        value = code,
                        onValueChange = { code = it },
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
                    ) {
                        Text(
                            text = "Didn't get it?",
                            style = ElegantTheme.typography.bodyMedium,
                            color = colors.textSecondary,
                        )
                        Text(
                            text = "Resend code",
                            style = ElegantTheme.typography.bodyMedium,
                            color = colors.interactivePrimary,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun DisclosureShowcase() {
    var expandedIndex by remember { mutableIntStateOf(-1) }

    ShowcasePage(title = "Elegant Disclosure") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Single-expand disclosures",
            description = "Two standalone cards; opening one collapses the other.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                listOf("Release notes", "Keyboard shortcuts").forEachIndexed { index, title ->
                    ElegantDisclosure(
                        title = title,
                        expanded = expandedIndex == index,
                        onToggle = {
                            expandedIndex = if (expandedIndex == index) -1 else index
                        },
                    ) {
                        Text(
                            text = "$title body reveals with a vertical expand animation.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "FAQ list",
            description = "A group wraps the FAQ blocks in one bordered surface.",
        ) {
            ElegantDisclosureGroup {
                ElegantDisclosure(
                    title = "What is Elegant UI?",
                    expanded = true,
                    onToggle = {},
                    supportingText = "Refined Compose Multiplatform components",
                ) {
                    Text(
                        text = "A shared component library for Android, Desktop, and Web.",
                        color = colors.textSecondary,
                        style = ElegantTheme.typography.bodyMedium,
                    )
                }
                ElegantDisclosure(
                    title = "Which platforms are supported?",
                    expanded = false,
                    onToggle = {},
                ) {
                    Text(
                        text = "Android 24+, Desktop JVM, and Web/Wasm.",
                        color = colors.textSecondary,
                        style = ElegantTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
private fun LabelShowcase() {
    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    ShowcasePage(title = "Elegant Label") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Labels above inputs",
            description = "Required, optional, and disabled labels sit directly above their fields.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
                Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                    ElegantLabel(text = "Full name", required = true)
                    ElegantInput(value = fullName, onValueChange = { fullName = it })
                }
                Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                    ElegantLabel(text = "Email address")
                    ElegantInput(value = email, onValueChange = { email = it })
                }
                Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                    ElegantLabel(text = "Nickname", enabled = false)
                    ElegantInput(value = "", onValueChange = {}, enabled = false)
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Registration form",
            description = "A card groups labeled fields into one scannable form.",
        ) {
            ElegantCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(ElegantSpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                        ElegantLabel(text = "Full name", required = true)
                        ElegantInput(value = fullName, onValueChange = { fullName = it })
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                        ElegantLabel(text = "Email address", required = true)
                        ElegantInput(value = email, onValueChange = { email = it })
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                        ElegantLabel(text = "Promo code")
                        ElegantInput(value = "", onValueChange = {})
                    }
                }
            }
        }
    }
}

@Composable
private fun FieldsetShowcase() {
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    ShowcasePage(title = "Elegant Fieldset") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A bordered form section",
            description = "A legend labels the section and content receives the content color.",
        ) {
            ElegantFieldset(legend = "Contact details") {
                Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
                    ElegantInput(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Email address",
                    )
                    ElegantInput(
                        value = phone,
                        onValueChange = { phone = it },
                        placeholder = "Phone number",
                    )
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A checkout card",
            description = "Two fieldsets separate shipping and payment on one card.",
        ) {
            ElegantCard {
                Column(
                    modifier = Modifier.padding(ElegantSpacing.xl),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                ) {
                    ElegantFieldset(legend = "Shipping address") {
                        ElegantInput(value = email, onValueChange = { email = it })
                    }
                    ElegantFieldset(legend = "Payment details") {
                        ElegantInput(value = phone, onValueChange = { phone = it })
                    }
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun SwitchGroupShowcase() {
    var channels by remember { mutableStateOf(setOf("push", "email")) }

    ShowcasePage(title = "Elegant SwitchGroup") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Notification channels",
            description = "Switch rows share one caller-owned selection set on a 4dp rhythm.",
        ) {
            ElegantSwitchGroup(
                selectedValues = channels,
                onToggle = { value, checked ->
                    channels = if (checked) channels + value else channels - value
                },
                items = listOf(
                    ElegantSwitchGroupItem(text = "Push notifications", value = "push"),
                    ElegantSwitchGroupItem(text = "Email digest", value = "email"),
                    ElegantSwitchGroupItem(text = "In-app mentions", value = "mentions"),
                ),
                supportingText = "Choose how you want to be notified.",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled items and groups",
            description = "A disabled item or a disabled group never invokes onToggle.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xl)) {
                ElegantSwitchGroup(
                    selectedValues = setOf("camera"),
                    onToggle = { _, _ -> },
                    items = listOf(
                        ElegantSwitchGroupItem(text = "Camera", value = "camera"),
                        ElegantSwitchGroupItem(
                            text = "Microphone",
                            value = "microphone",
                            enabled = false,
                        ),
                    ),
                )
                ElegantSwitchGroup(
                    selectedValues = setOf("notifications"),
                    onToggle = { _, _ -> },
                    enabled = false,
                    items = listOf(
                        ElegantSwitchGroupItem(text = "Notifications", value = "notifications"),
                        ElegantSwitchGroupItem(text = "Announcements", value = "announcements"),
                    ),
                )
            }
        }
    }
}

@Composable
private fun TagGroupShowcase() {
    var filters by remember { mutableStateOf(setOf("design", "multiplatform")) }

    ShowcasePage(title = "Elegant TagGroup") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Filter chips that wrap",
            description = "Selectable chips share one caller-owned selection set and wrap on an 8dp rhythm.",
        ) {
            ElegantTagGroup(
                selectedValues = filters,
                onToggle = { value, checked ->
                    filters = if (checked) filters + value else filters - value
                },
                items = listOf(
                    ElegantTagGroupItem(text = "Design", value = "design"),
                    ElegantTagGroupItem(text = "Engineering", value = "engineering"),
                    ElegantTagGroupItem(text = "Multiplatform", value = "multiplatform"),
                    ElegantTagGroupItem(text = "Release", value = "release"),
                ),
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled items and groups",
            description = "A disabled chip or a disabled group never invokes onToggle.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xl)) {
                ElegantTagGroup(
                    selectedValues = setOf("design"),
                    onToggle = { _, _ -> },
                    items = listOf(
                        ElegantTagGroupItem(text = "Design", value = "design"),
                        ElegantTagGroupItem(
                            text = "Release",
                            value = "release",
                            enabled = false,
                        ),
                    ),
                )
                ElegantTagGroup(
                    selectedValues = setOf("notifications"),
                    onToggle = { _, _ -> },
                    enabled = false,
                    items = listOf(
                        ElegantTagGroupItem(text = "Notifications", value = "notifications"),
                        ElegantTagGroupItem(text = "Announcements", value = "announcements"),
                    ),
                )
            }
        }
    }
}

@Composable
private fun ToolbarShowcase() {
    var draft by rememberSaveable { mutableStateOf("") }

    ShowcasePage(title = "Elegant Toolbar") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Actions above a list",
            description = "A flat 48dp strip fills the width and hosts icon actions on a 4dp rhythm.",
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
            ) {
                ElegantToolbar {
                    ShowcaseIconButton(
                        resource = Res.drawable.add_rounded,
                        contentDescription = "Add item",
                        onClick = {},
                    )
                    ShowcaseIconButton(
                        resource = Res.drawable.edit_rounded,
                        contentDescription = "Edit selected",
                        onClick = {},
                    )
                    ShowcaseIconButton(
                        resource = Res.drawable.share_rounded,
                        contentDescription = "Share",
                        onClick = {},
                    )
                    ShowcaseIconButton(
                        resource = Res.drawable.delete_rounded,
                        contentDescription = "Delete selected",
                        onClick = {},
                    )
                }
                ElegantList {
                    ElegantListItem(
                        leadingContent = { ResourceIcon(Res.drawable.person_rounded) },
                        title = { Text("General") },
                        supportingText = { Text("Appearance, storage, and performance") },
                    )
                    ElegantListItem(
                        leadingContent = { ResourceIcon(Res.drawable.check_rounded) },
                        title = { Text("Notifications") },
                        supportingText = { Text("Badges, sounds, and summary") },
                    )
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Formatting strip above an editor",
            description = "The strip sits flush above a textarea; separators are the caller's choice.",
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
            ) {
                ElegantToolbar {
                    ShowcaseIconButton(
                        resource = Res.drawable.add_rounded,
                        contentDescription = "Attach",
                        onClick = {},
                    )
                    ShowcaseIconButton(
                        resource = Res.drawable.check_rounded,
                        contentDescription = "Confirm",
                        onClick = {},
                    )
                    ShowcaseIconButton(
                        resource = Res.drawable.more_vert_rounded,
                        contentDescription = "More options",
                        onClick = {},
                    )
                    ShowcaseIconButton(
                        resource = Res.drawable.delete_rounded,
                        contentDescription = "Clear draft",
                        onClick = { draft = "" },
                    )
                }
                ElegantTextarea(
                    value = draft,
                    onValueChange = { draft = it },
                    placeholder = "Type a message",
                    minLines = 3,
                    maxLines = 6,
                )
            }
        }
    }
}

@Composable
private fun ToastShowcase() {
    val toastHostState = remember { ElegantToastHostState() }
    val scope = rememberCoroutineScope()
    var savedCount by remember { mutableIntStateOf(0) }
    var flowsResumed by remember { mutableIntStateOf(0) }

    ShowcasePage(title = "Elegant Toast") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Host, state, and surface",
            description = "The host pins the toast to the top center of its bounds; showToast suspends until dismissal.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                ElegantToast(
                    title = "Changes saved",
                    description = "Synced to your workspace.",
                    onClose = {},
                )
                ElegantButton(
                    onClick = {
                        scope.launch {
                            toastHostState.showToast("Live toast at the top of this page")
                        }
                    },
                ) {
                    Text("Show toast")
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "ACTION",
            title = "Title, description, and close",
            description = "A supporting description adds context; the close action dismisses the toast immediately.",
        ) {
            ElegantButton(
                onClick = {
                    scope.launch {
                        toastHostState.showToast(
                            title = "Download complete",
                            description = "The file is ready in your library.",
                        )
                    }
                },
            ) {
                Text("Download complete")
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Save flow",
            description = "Dismissal resumes the calling coroutine, so follow-up work chains behind the feedback.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                Text(
                    text = "Saved drafts: $savedCount",
                    style = ElegantTheme.typography.bodyMedium,
                    color = ElegantTheme.colors.textSecondary,
                )
                ElegantButton(
                    onClick = {
                        savedCount += 1
                        scope.launch {
                            toastHostState.showToast(
                                title = "Draft saved",
                                description = "Synced a moment ago.",
                            )
                            flowsResumed += 1
                        }
                    },
                ) {
                    Text("Save draft")
                }
                Text(
                    text = "Workflows resumed after dismissal: $flowsResumed",
                    style = ElegantTheme.typography.bodyMedium,
                    color = ElegantTheme.colors.textSecondary,
                )
            }
        }

        Box(Modifier.fillMaxWidth()) {
            ElegantToastHost(
                hostState = toastHostState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun AutocompleteShowcase() {
    var countryQuery by remember { mutableStateOf("") }
    var cityQuery by remember { mutableStateOf("") }
    var cityCode by remember { mutableStateOf("") }
    val countryOptions = listOf(
        ElegantAutocompleteOption(text = "France", value = "FR"),
        ElegantAutocompleteOption(text = "Germany", value = "DE"),
        ElegantAutocompleteOption(text = "Netherlands", value = "NL"),
        ElegantAutocompleteOption(text = "Norway", value = "NO"),
        ElegantAutocompleteOption(text = "Sweden", value = "SE"),
        ElegantAutocompleteOption(text = "Switzerland", value = "CH"),
        ElegantAutocompleteOption(text = "Fiji", value = "FJ"),
        ElegantAutocompleteOption(text = "Canada", value = "CA"),
    )

    ShowcasePage(title = "Elegant Autocomplete") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Filtered suggestions below the field",
            description = "Typing filters the option list; a blank query shows every option and selecting a row reports the choice.",
        ) {
            ElegantAutocomplete(
                query = countryQuery,
                onQueryChange = { countryQuery = it },
                options = countryOptions,
                onOptionSelected = { option ->
                    countryQuery = option.text
                },
                label = "Country",
                placeholder = "Search a country",
                supportingText = "Choose the country that best fits.",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A form row with an autocomplete",
            description = "The autocomplete pairs with a plain input on the same form row.",
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
                ElegantAutocomplete(
                    query = cityQuery,
                    onQueryChange = { cityQuery = it },
                    modifier = Modifier.weight(1f),
                    options = listOf(
                        ElegantAutocompleteOption(text = "Paris", value = "PAR"),
                        ElegantAutocompleteOption(text = "Berlin", value = "BER"),
                        ElegantAutocompleteOption(text = "Amsterdam", value = "AMS"),
                        ElegantAutocompleteOption(text = "Oslo", value = "OSL"),
                    ),
                    onOptionSelected = { option ->
                        cityQuery = option.text
                        cityCode = option.value
                    },
                    label = "City",
                    placeholder = "Type a city",
                )
                ElegantInput(
                    value = cityCode,
                    onValueChange = { cityCode = it },
                    modifier = Modifier.weight(1f),
                    label = "City code",
                    placeholder = "Auto-filled",
                )
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun InputGroupShowcase() {
    var unit by remember { mutableStateOf("Monthly") }
    var query by remember { mutableStateOf("") }

    ShowcasePage(title = "Elegant InputGroup") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Prefix, select-like cell, and suffix",
            description = "Adjacent fields merge behind one shared border with 4dp inner padding.",
        ) {
            Text(
                text = "Amount",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            ElegantInputGroup {
                ElegantInput(
                    value = "$",
                    onValueChange = {},
                    readOnly = true,
                    style = ElegantInputStyle.Outlined,
                    modifier = Modifier.width(48.dp),
                )
                ElegantInput(
                    value = unit,
                    onValueChange = { unit = it },
                    style = ElegantInputStyle.Outlined,
                    modifier = Modifier.weight(1f),
                    trailingIcon = { Text("\u25BE") },
                )
                Row(
                    modifier = Modifier
                        .height(48.dp)
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "per month",
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A search row",
            description = "An outlined field and a trailing action share one cluster on any width.",
        ) {
            ElegantInputGroup(modifier = Modifier.fillMaxWidth()) {
                ElegantInput(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = "Search releases",
                    style = ElegantInputStyle.Outlined,
                    modifier = Modifier.weight(1f),
                )
                Row(
                    modifier = Modifier
                        .height(48.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Search",
                        style = ElegantTheme.typography.labelMedium,
                        color = colors.interactivePrimary,
                    )
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun ColorPickerShowcase() {
    val accentColor = ElegantTheme.colors.interactivePrimary
    var selected by remember { mutableStateOf(ElegantColorPickerDefaults.palette().first()) }
    var accent by remember { mutableStateOf(accentColor) }

    ShowcasePage(title = "Elegant ColorPicker") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A curated 16-color palette",
            description = "Eight saturated chromatic colors and eight light tints wrap on the 8dp rhythm.",
        ) {
            Text(
                text = "Selected ${hexReadout(selected)}",
                style = ElegantTheme.typography.bodyMedium,
                color = colors.textSecondary,
            )
            ElegantColorPicker(
                selectedColor = selected,
                onColorSelected = { selected = it },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled without losing the choice",
            description = "A disabled picker renders at 40% opacity and never fires the callback.",
        ) {
            ElegantColorPicker(
                selectedColor = selected,
                onColorSelected = {},
                enabled = false,
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Theme accent picker",
            description = "A small palette built from theme roles keeps the accent in sync with the theme.",
        ) {
            val accentPalette = listOf(
                colors.interactivePrimary,
                colors.interactivePrimaryHover,
                colors.interactivePrimaryPressed,
                colors.focusRing,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "\u25CF",
                    style = ElegantTheme.typography.titleMedium,
                    color = accent,
                )
                Spacer(Modifier.width(ElegantSpacing.md))
                Text(
                    text = "Accent ${hexReadout(accent)}",
                    style = ElegantTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                )
            }
            ElegantColorPicker(
                selectedColor = accent,
                onColorSelected = { accent = it },
                colors = accentPalette,
            )
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

private fun hexReadout(color: Color): String =
    listOf(color.red, color.green, color.blue).joinToString(prefix = "#", separator = "") {
        (it * 255f).roundToInt().coerceIn(0, 255).toString(16).uppercase().padStart(2, '0')
    }

@Composable
private fun CalendarShowcase() {
    var selected by remember { mutableStateOf<ElegantDate?>(null) }
    var bounded by remember { mutableStateOf<ElegantDate?>(null) }

    ShowcasePage(title = "Elegant Calendar") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A controlled month grid",
            description = "Monday-first 42-cell grid with navigation and a caller-owned selection.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                ElegantCalendar(
                    selectedDate = selected,
                    onDateSelected = { selected = it },
                )
                Text(
                    text = selected?.let { date ->
                        "${date.year}-${date.month.toString().padStart(2, '0')}-${date.day.toString().padStart(2, '0')}"
                    } ?: "No date selected",
                    color = colors.textSecondary,
                    style = ElegantTheme.typography.bodyMedium,
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "RANGE",
            title = "Bounded selection window",
            description = "Days outside the window render dimmed and are never selectable.",
        ) {
            ElegantCalendar(
                selectedDate = bounded,
                onDateSelected = { bounded = it },
                minDate = ElegantDate(2026, 8, 1),
                maxDate = ElegantDate(2026, 8, 31),
                initialMonth = ElegantDate(2026, 8, 1),
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A booking card",
            description = "A calendar inside a quiet card anchors the reservation flow.",
        ) {
            ElegantCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(ElegantSpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                ) {
                    Text(
                        text = "Choose a night",
                        color = colors.textPrimary,
                        style = ElegantTheme.typography.titleMedium,
                    )
                    ElegantCalendar(
                        selectedDate = selected,
                        onDateSelected = { selected = it },
                        minDate = ElegantDate(2026, 8, 1),
                        maxDate = ElegantDate(2026, 8, 31),
                        initialMonth = ElegantDate(2026, 8, 1),
                    )
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun DatePickerShowcase() {
    var selected by remember { mutableStateOf<ElegantDate?>(null) }
    var checkIn by remember { mutableStateOf<ElegantDate?>(null) }
    var checkOut by remember { mutableStateOf<ElegantDate?>(null) }

    ShowcasePage(title = "Elegant DatePicker") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Pick a date from a calendar popup",
            description = "A read-only Filled field opens a focusable calendar below it; choosing a day invokes the callback and closes the popup.",
        ) {
            ElegantDatePicker(
                date = selected,
                onDateSelected = { selected = it },
                label = "Departure",
                placeholder = "Pick a departure date",
            )
            Spacer(Modifier.height(ElegantSpacing.md))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Chosen",
                    modifier = Modifier.weight(1f),
                    color = ElegantTheme.colors.textSecondary,
                    style = ElegantTheme.typography.bodyMedium,
                )
                Text(
                    text = selected?.let { date ->
                        "${date.year}-${date.month.toString().padStart(2, '0')}-${date.day.toString().padStart(2, '0')}"
                    } ?: "None",
                    color = ElegantTheme.colors.textPrimary,
                    style = ElegantTheme.typography.labelMedium,
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled and error",
            description = "A disabled field never opens the popup; an error field paints a critical border and shows the error text.",
        ) {
            ElegantDatePicker(
                date = null,
                onDateSelected = {},
                label = "Arrival",
                enabled = false,
                placeholder = "Unavailable",
            )
            Spacer(Modifier.height(ElegantSpacing.md))
            ElegantDatePicker(
                date = selected,
                onDateSelected = { selected = it },
                label = "Return",
                isError = true,
                errorText = "Choose a return date.",
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Booking card",
            description = "A check-in and check-out pair inside a quiet card, bounded to the booking window.",
        ) {
            ElegantCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(ElegantSpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                ) {
                    Text(
                        text = "Book your stay",
                        color = ElegantTheme.colors.textPrimary,
                        style = ElegantTheme.typography.titleMedium,
                    )
                    ElegantDatePicker(
                        date = checkIn,
                        onDateSelected = { checkIn = it },
                        label = "Check-in",
                        placeholder = "Pick a check-in date",
                        minDate = ElegantDate(2026, 8, 1),
                        maxDate = ElegantDate(2026, 8, 31),
                    )
                    ElegantDatePicker(
                        date = checkOut,
                        onDateSelected = { checkOut = it },
                        label = "Check-out",
                        placeholder = "Pick a check-out date",
                        minDate = checkIn ?: ElegantDate(2026, 8, 1),
                        maxDate = ElegantDate(2026, 8, 31),
                    )
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun DateRangePickerShowcase() {
    var range by remember { mutableStateOf(ElegantDateRange(null, null)) }

    ShowcasePage(title = "Elegant DateRangePicker") { compact ->
        val colors = ElegantTheme.colors
        val start = range.start
        val end = range.end
        val rangeText = when {
            start == null -> "Nothing selected yet"
            end == null -> "Start: ${start!!.year}-${start.month}-${start.day}"
            else -> "${start.year}-${start.month}-${start.day} — ${end.year}-${end.month}-${end.day}"
        }

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A two-month range picker",
            description = "The first click sets the start, the second sets the end; the readout mirrors the selection.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                ElegantDateRangePicker(
                    range = range,
                    onRangeSelected = { range = it },
                    label = "Stay dates",
                    placeholder = "Pick a stay",
                    supportingText = "Select a start and an end day.",
                )
                Text(
                    text = "Selected: $rangeText",
                    color = colors.textSecondary,
                    style = ElegantTheme.typography.bodyMedium,
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A booking summary card",
            description = "A bounded stay range with validation and a card that summarizes the booking.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
                ElegantDateRangePicker(
                    range = range,
                    onRangeSelected = { range = it },
                    label = "Trip dates",
                    minDate = ElegantDate(2026, 8, 1),
                    maxDate = ElegantDate(2026, 12, 31),
                    isError = range.start == null && range.end == null,
                    errorText = "Choose a start and an end day to book.",
                )
                ElegantCard {
                    Column(Modifier.padding(ElegantSpacing.xl)) {
                        Text("Mountain retreat", style = ElegantTheme.typography.titleMedium)
                        Text(
                            text = "Two nights in the alpine cabin, in season.",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun NumberPickerShowcase() {
    var quantity by remember { mutableStateOf(1) }
    var seats by remember { mutableStateOf(2) }

    ShowcasePage(title = "Elegant NumberPicker") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Bounded stepper",
            description = "A large centered value with circular increase and decrease buttons that stop at the range boundaries.",
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ElegantNumberPicker(
                    value = quantity,
                    onValueChange = { quantity = it },
                    minValue = 1,
                    maxValue = 10,
                )
                Spacer(Modifier.height(ElegantSpacing.md))
                Text(
                    text = "Current: $quantity",
                    color = ElegantTheme.colors.textSecondary,
                    style = ElegantTheme.typography.bodyMedium,
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A quantity card",
            description = "A compact row picker inside a card for selecting seats.",
        ) {
            ElegantCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(ElegantSpacing.xl),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Seats",
                        color = ElegantTheme.colors.textSecondary,
                        style = ElegantTheme.typography.labelMedium,
                    )
                    ElegantNumberPicker(
                        value = seats,
                        onValueChange = { seats = it },
                        minValue = 1,
                        maxValue = 8,
                    )
                }
            }
        }

        Spacer(Modifier.height(ElegantSpacing.md))
    }
}

@Composable
private fun ThemeControllerShowcase() {
    ShowcasePage(title = "Elegant Dynamic Color") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Derivation Seeds",
            description = "Each key color derives a full palette through a local ElegantTheme(keyColor = ...) override.",
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
            ) {
                KeyColorBlock(seed = Color(0xFF6C4EFF), label = "Violet 6C4EFF")
                KeyColorBlock(seed = Color(0xFF147D64), label = "Green 147D64")
                KeyColorBlock(seed = Color(0xFFB45309), label = "Orange B45309")
            }
        }
        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Default Theme Comparison",
            description = "The ambient default palette beside the same violet palette derived from an explicit key color.",
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
            ) {
                KeyColorBlock(seed = null, label = "Default Theme")
                KeyColorBlock(seed = Color(0xFF6C4EFF), label = "Derived Violet")
            }
        }
    }
}

@Composable
private fun KeyColorBlock(
    seed: Color?,
    label: String,
) {
    if (seed == null) {
        KeyColorSurface(label = label)
    } else {
        ElegantTheme(keyColor = seed) {
            KeyColorSurface(label = label)
        }
    }
}

@Composable
private fun KeyColorSurface(label: String) {
    val colors = ElegantTheme.colors
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colors.surfaceRaised,
                shape = RoundedCornerShape(ElegantRadius.md),
            )
            .padding(ElegantSpacing.lg),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
    ) {
        Text(
            text = label,
            color = colors.textPrimary,
            style = ElegantTheme.typography.labelLarge,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ElegantButton(onClick = {}) {
                Text("Action")
            }
            ElegantTag {
                Text("Tag")
            }
            ElegantBadge {
                Text("New")
            }
        }
    }
}

@Composable
private fun BottomSheetShowcase() {
    ShowcasePage(title = "Elegant BottomSheet") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "OVERLAY",
            title = "A bottom sheet",
            description = "ElegantBottomSheet slides a width-capped, top-rounded panel over a scrim; Escape, back, and scrim clicks all dismiss.",
        ) {
            var visible by remember { mutableStateOf(false) }

            Text(
                text = "Tap the trigger to open the sheet from the bottom edge.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantButton(onClick = { visible = true }) {
                Text("Open sheet")
            }

            ElegantBottomSheet(
                visible = visible,
                onDismissRequest = { visible = false },
                title = "Share options",
                endAction = {
                    ElegantCloseButton(onClick = { visible = false })
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = ElegantSpacing.lg)
                        .padding(bottom = ElegantSpacing.xl),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                ) {
                    Text(
                        text = "Pick where to share this file. The sheet keeps focus and returns it when dismissed.",
                        color = colors.textSecondary,
                        style = ElegantTheme.typography.bodyMedium,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
                    ) {
                        repeat(4) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(ElegantRadius.md))
                                    .background(colors.surfaceSunken),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text("\u2022", style = ElegantTheme.typography.titleMedium)
                            }
                        }
                    }
                    ElegantButton(
                        onClick = { visible = false },
                        style = ElegantButtonStyle.Secondary,
                    ) {
                        Text("Close")
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATE",
            title = "Caller-owned dismissal",
            description = "Every dismiss route funnels through onDismissRequest, so the sheet cannot close itself.",
        ) {
            var open by remember { mutableStateOf(false) }
            var dismissed by remember { mutableStateOf(false) }

            Text(
                text = if (dismissed) "Sheet was dismissed by the caller." else "Open it, then dismiss with Escape or the button.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantButton(onClick = { open = true }) {
                Text("Open sheet")
            }

            ElegantBottomSheet(
                visible = open,
                onDismissRequest = {
                    open = false
                    dismissed = true
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(ElegantSpacing.lg),
                ) {
                    Text(
                        text = "Dismissible sheet",
                        style = ElegantTheme.typography.titleMedium,
                    )
                    ElegantButton(
                        onClick = { open = false },
                        style = ElegantButtonStyle.Secondary,
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

@Composable
private fun BasicComponentShowcase() {
    ShowcasePage(title = "Elegant BasicComponent") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "SETTINGS ROW",
            title = "A composed row",
            description = "ElegantBasicComponent combines leading content, a title block, trailing controls, and an optional bottom block into one interactive row.",
        ) {
            var enabled by remember { mutableStateOf(true) }

            ElegantBasicComponent(
                title = "Airplane mode",
                summary = "Turn off all wireless connections",
                startAction = {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(ElegantRadius.md))
                            .background(colors.interactivePrimary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("\u2708", style = ElegantTheme.typography.labelMedium)
                    }
                },
                endActions = {
                    ElegantSwitch(
                        checked = enabled,
                        onCheckedChange = { enabled = it },
                    )
                },
                onClick = { enabled = !enabled },
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "SLOTS",
            title = "Trailing chevron",
            description = "endActions and bottomAction compose freely; holdDownState forces the pressed visual.",
        ) {
            var held by remember { mutableStateOf(false) }

            Text(
                text = if (held) "Row is held down." else "Toggle the hold state below.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
            ElegantBasicComponent(
                title = "Drill into details",
                summary = "Open the nested screen",
                onClick = { },
                holdDownState = held,
                endActions = {
                    Icon(
                        imageVector = ElegantIcons.ChevronRight,
                        contentDescription = null,
                        tint = colors.textTertiary,
                    )
                },
                bottomAction = {
                    Spacer(modifier = Modifier.height(ElegantSpacing.sm))
                    Text(
                        text = "Bottom helper content",
                        color = colors.textTertiary,
                        style = ElegantTheme.typography.bodyMedium,
                    )
                },
            )
            ElegantButton(
                onClick = { held = !held },
                style = ElegantButtonStyle.Secondary,
            ) {
                Text(if (held) "Release hold" else "Hold row")
            }
        }
    }
}

@Composable
private fun IconsShowcase() {
    ShowcasePage(title = "Elegant Icons") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "The built-in vector set",
            description = "24 tintable icons ship with the library; each inherits the ambient content color.",
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
            ) {
                ElegantIcons.All.forEach { icon ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
                    ) {
                        ElegantIcon(
                            icon = icon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = colors.textPrimary,
                        )
                    }
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Role tinting and button slots",
            description = "Icons take theme roles directly or ride inside button slots.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantIcon(
                    icon = ElegantIcons.Check,
                    contentDescription = null,
                    tint = colors.statusPositive,
                )
                ElegantIcon(
                    icon = ElegantIcons.Notifications,
                    contentDescription = null,
                    tint = colors.textSecondary,
                )
                ElegantIcon(
                    icon = ElegantIcons.Delete,
                    contentDescription = null,
                    tint = colors.statusCritical,
                )
                ElegantButton(
                    onClick = {},
                    leadingIcon = {
                        ElegantIcon(icon = ElegantIcons.Plus, contentDescription = null)
                    },
                ) {
                    Text("Create")
                }
            }
        }
    }
}

@Composable
private fun SquircleShowcase() {
    ShowcasePage(title = "Elegant Squircle") { compact ->
        val colors = ElegantTheme.colors
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Smoothing scale",
            description = "One shape, three curvatures: plain corners at 0, the default at 0.6, and the roundest superellipse at 1.",
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            ) {
                SquircleSwatch(colors = colors, smoothing = 0f, label = "Plain corner")
                SquircleSwatch(colors = colors, smoothing = 0.6f, label = "Default smooth")
                SquircleSwatch(colors = colors, smoothing = 1f, label = "Roundest")
            }
        }
        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Identity surfaces",
            description = "Squircle avatars keep the rounded-square identity without the tangent break of plain rounded corners.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xl),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantAvatar(
                    name = "Maya Chen",
                    initials = "MC",
                    size = ElegantAvatarSize.Large,
                    shape = ElegantSquircleShape(cornerRadius = 18.dp, smoothing = 0.8f),
                )
                Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
                    Text(
                        text = "Maya Chen",
                        style = ElegantTheme.typography.labelLarge,
                        color = colors.textPrimary,
                    )
                    Text(
                        text = "Design systems",
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                }
            }
            ElegantSurface(
                shape = ElegantSquircleShape(cornerRadius = 20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = ElegantSpacing.lg),
            ) {
                Column(Modifier.padding(ElegantSpacing.xl)) {
                    Text(
                        text = "Squircle card",
                        style = ElegantTheme.typography.labelLarge,
                        color = colors.textPrimary,
                    )
                    Text(
                        text = "Continuous curvature from corner to corner.",
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                }
            }
        }
    }
}

@Composable
private fun SquircleSwatch(
    colors: com.elegant.compose.ui.foundation.theme.ElegantColors,
    smoothing: Float,
    label: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantSurface(
            shape = ElegantSquircleShape(cornerRadius = 14.dp, smoothing = smoothing),
            modifier = Modifier.size(width = 96.dp, height = 64.dp),
        ) {
            Text(
                text = smoothing.toString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = ElegantTheme.typography.labelSmall,
                color = colors.textSecondary,
            )
        }
        Text(
            text = label,
            style = ElegantTheme.typography.labelMedium,
            color = colors.textSecondary,
        )
    }
}

@Composable
private fun BlurShowcase() {
    ShowcasePage(title = "Elegant Blur") { compact ->
        val colors = ElegantTheme.colors
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Blurred copy behind crisp copy",
            description = "elegantBlur blurs the node's own content; layer a blurred copy behind crisp content for a frosted halo.",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp),
            ) {
                Text(
                    text = "Frosted",
                    modifier = Modifier
                        .fillMaxWidth()
                        .elegantBlur(radius = 10.dp),
                    style = ElegantTheme.typography.titleMedium,
                    color = colors.textPrimary,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Frosted",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = ElegantSpacing.lg),
                    style = ElegantTheme.typography.titleMedium,
                    color = colors.textPrimary,
                    textAlign = TextAlign.Center,
                )
            }
        }
        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Toolbar with a blurred edge",
            description = "An unbounded blur softens one label's edges without affecting its neighbors.",
        ) {
            ElegantSurface {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(ElegantSpacing.lg),
                    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Library",
                        style = ElegantTheme.typography.labelLarge,
                        color = colors.textPrimary,
                    )
                    Text(
                        text = "Recent",
                        style = ElegantTheme.typography.labelMedium,
                        color = colors.textSecondary,
                    )
                    Text(
                        text = "Drafts",
                        modifier = Modifier.elegantBlur(
                            radius = 6.dp,
                            edgeTreatment = BlurEdgeTreatment.Unbounded,
                        ),
                        style = ElegantTheme.typography.labelMedium,
                        color = colors.textPrimary,
                    )
                }
            }
        }
    }
}

@Composable
private fun ListPopupShowcase() {
    var expanded by remember { mutableStateOf(false) }
    var statesExpanded by remember { mutableStateOf(false) }
    var formExpanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf("paris") }
    var note by remember { mutableStateOf("") }

    val cities = listOf(
        ElegantListPopupOption(text = "Paris", value = "paris"),
        ElegantListPopupOption(text = "London", value = "london"),
        ElegantListPopupOption(text = "Tokyo", value = "tokyo"),
    )

    ShowcasePage(title = "Elegant ListPopup") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A single-choice list anchored to its trigger",
            description = "The trigger lives in a Box with ElegantListPopup; the surface drops below that Box, start-aligned, highlights the selected option, and dismisses on outside click, Escape, or back.",
        ) {
            Box {
                ElegantButton(onClick = { expanded = true }) {
                    Text("Choose city")
                }
                ElegantListPopup(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    options = cities,
                    selectedValue = selectedValue,
                    onOptionSelected = { option ->
                        selectedValue = option.value
                        expanded = false
                    },
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled options and the selected check",
            description = "The selected option shows the interactive color, a subtle background, and a check glyph; disabled options render with the tertiary color and never invoke callbacks.",
        ) {
            Box {
                ElegantButton(onClick = { statesExpanded = true }) {
                    Text("Choose city")
                }
                ElegantListPopup(
                    expanded = statesExpanded,
                    onDismissRequest = { statesExpanded = false },
                    options = cities + ElegantListPopupOption(
                        text = "Berlin",
                        value = "berlin",
                        enabled = false,
                    ),
                    selectedValue = selectedValue,
                    onOptionSelected = { option ->
                        selectedValue = option.value
                        statesExpanded = false
                    },
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "A form row with an input",
            description = "The popup picks the city while ElegantInput collects a free-text note; both values stay caller-owned.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElegantInput(
                    value = note,
                    onValueChange = { note = it },
                    modifier = Modifier.weight(1f),
                    label = "Delivery note",
                    placeholder = "Optional note",
                )
                Box {
                    ElegantButton(onClick = { formExpanded = true }) {
                        Text("City")
                    }
                    ElegantListPopup(
                        expanded = formExpanded,
                        onDismissRequest = { formExpanded = false },
                        options = cities,
                        selectedValue = selectedValue,
                        onOptionSelected = { option ->
                            selectedValue = option.value
                            formExpanded = false
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun CascadingMenuShowcase() {
    var editOpen by remember { mutableStateOf(false) }
    var toolbarOpen by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("Nothing selected") }

    val editItems = listOf(
        ElegantCascadingMenuItem(
            text = "Edit",
            children = listOf(
                ElegantCascadingMenuItem(text = "Copy"),
                ElegantCascadingMenuItem(text = "Paste"),
                ElegantCascadingMenuItem(text = "Restore", enabled = false),
            ),
        ),
        ElegantCascadingMenuItem(
            text = "Insert",
            children = listOf(
                ElegantCascadingMenuItem(text = "Image"),
                ElegantCascadingMenuItem(text = "Table"),
            ),
        ),
    )
    val toolbarItems = listOf(
        ElegantCascadingMenuItem(
            text = "File",
            children = listOf(
                ElegantCascadingMenuItem(text = "New file"),
                ElegantCascadingMenuItem(text = "Open"),
                ElegantCascadingMenuItem(text = "Save as"),
            ),
        ),
        ElegantCascadingMenuItem(
            text = "View",
            children = listOf(
                ElegantCascadingMenuItem(text = "Zoom in"),
                ElegantCascadingMenuItem(text = "Zoom out"),
            ),
        ),
    )

    ShowcasePage(title = "Elegant CascadingMenu") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "A two-level menu anchored to its trigger",
            description = "Hovering or clicking a parent item opens its submenu beside it; clicking a leaf reports the ancestor chain and dismisses the menu.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
                Box {
                    ElegantButton(onClick = { editOpen = true }) {
                        Text("Edit document")
                    }
                    ElegantCascadingMenu(
                        expanded = editOpen,
                        onDismissRequest = { editOpen = false },
                        items = editItems,
                        onItemClick = { path ->
                            editOpen = false
                            selected = path.joinToString(" > ") { it.text }
                        },
                    )
                }
                Text(text = selected, style = ElegantTheme.typography.bodyMedium)
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Toolbar menus",
            description = "Two-level menus hang off toolbar buttons; each anchor Box wraps only its own trigger.",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            ) {
                Box {
                    ElegantButton(onClick = { toolbarOpen = true }) {
                        Text("File")
                    }
                    ElegantCascadingMenu(
                        expanded = toolbarOpen,
                        onDismissRequest = { toolbarOpen = false },
                        items = toolbarItems,
                        onItemClick = { path ->
                            toolbarOpen = false
                            selected = path.joinToString(" > ") { it.text }
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun ColorPickerPanelShowcase() {
    val defaultAccent = ElegantTheme.colors.interactivePrimary
    var color by remember { mutableStateOf(Color(0xFF6C4EFF)) }
    var accent by remember { mutableStateOf(defaultAccent) }

    ShowcasePage(title = "Elegant ColorPicker Panel") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "Free-form HSV selection",
            description = "The area keeps the hue while the slider picks it; the caller owns the color.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
                ElegantColorPickerPanel(
                    color = color,
                    onColorChange = { color = it },
                )
                Text(
                    text = "Color ${hexReadout(color)}",
                    style = ElegantTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                )
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "Disabled without losing the choice",
            description = "A disabled panel renders both controls at 40% opacity and never fires the callback.",
        ) {
            ElegantColorPickerPanel(
                color = color,
                onColorChange = {},
                enabled = false,
            )
        }

        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "Theme accent editor",
            description = "Drag through the continuous HSV space or jump straight to a curated swatch.",
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
                ElegantColorPickerPanel(
                    color = accent,
                    onColorChange = { accent = it },
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "\u25CF",
                        style = ElegantTheme.typography.titleMedium,
                        color = accent,
                    )
                    Spacer(Modifier.width(ElegantSpacing.md))
                    Text(
                        text = "Accent ${hexReadout(accent)}",
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                }
                ElegantColorPicker(
                    selectedColor = accent,
                    onColorSelected = { accent = it },
                )
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

@Serializable
private sealed interface NavDemoRoute : ElegantNavKey {
    @Serializable data object Home : NavDemoRoute
    @Serializable data object Detail : NavDemoRoute
    @Serializable data class Item(val id: Int) : NavDemoRoute
    @Serializable data object Sheet : NavDemoRoute
}

private fun NavDemoRoute.label(): String = when (this) {
    NavDemoRoute.Home -> "Home"
    NavDemoRoute.Detail -> "Detail"
    is NavDemoRoute.Item -> "Item ${id}"
    NavDemoRoute.Sheet -> "Sheet"
}

private val navDemoScaleFade: ElegantNavTransition = elegantNavGraphicsTransition { scope ->
    val d = scope.relativeDepth.coerceIn(-1f, 1f)
    alpha = if (d <= 0f) 1f + d else 1f - d
    scaleX = 1f - 0.08f * d.coerceAtLeast(0f)
    scaleY = scaleX
}

private fun pushIdempotent(backStack: ElegantNavBackStack, key: NavDemoRoute) {
    if (key !in backStack) backStack.add(key)
}

@Composable
private fun NavDemoPage(route: NavDemoRoute, tint: Color, hint: String) {
    val colors = ElegantTheme.colors
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(tint),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
        ) {
            Text(
                text = route.label(),
                color = colors.textPrimary,
                style = ElegantTheme.typography.titleMedium,
            )
            Text(
                text = hint,
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun NavDemoPane(content: @Composable () -> Unit) {
    val colors = ElegantTheme.colors
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .clip(RoundedCornerShape(ElegantRadius.lg))
            .border(
                width = 1.dp,
                color = colors.borderDefault,
                shape = RoundedCornerShape(ElegantRadius.lg),
            ),
    ) {
        content()
    }
}

@Composable
private fun NavDemoControls(
    backStack: ElegantNavBackStack,
    buttons: @Composable () -> Unit,
) {
    val colors = ElegantTheme.colors
    Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.sm)) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
        ) {
            buttons()
        }
        Text(
            text = "Stack: ${backStack.joinToString(" › ") { (it as NavDemoRoute).label() }}",
            color = colors.textSecondary,
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun NavigationShowcase() {
    ShowcasePage(title = "Elegant Navigation") { compact ->
        val colors = ElegantTheme.colors

        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "One float drives the whole stack",
            description = "ElegantNavDisplay keeps a single animated depth float across the whole back stack. Push, pop, and replace settle through the same spring, and swipe-to-dismiss follows the finger 1:1.",
        ) {
            val backStack = rememberElegantNavBackStack<NavDemoRoute>(NavDemoRoute.Home)
            NavDemoPane {
                ElegantNavDisplay(backStack = backStack) {
                    entry<NavDemoRoute.Home> {
                        NavDemoPage(
                            route = NavDemoRoute.Home,
                            tint = colors.backgroundCanvas,
                            hint = "The default slide keeps the page below parallaxing",
                        )
                    }
                    entry<NavDemoRoute.Detail>(swipeDismiss = ElegantNavSwipeDirection.LeftToRight) {
                        NavDemoPage(
                            route = NavDemoRoute.Detail,
                            tint = colors.surfaceDefault,
                            hint = "Swipe right to dismiss",
                        )
                    }
                    entry<NavDemoRoute.Item>(swipeDismiss = ElegantNavSwipeDirection.LeftToRight) { route ->
                        NavDemoPage(
                            route = route,
                            tint = colors.interactivePrimary.copy(alpha = 0.06f),
                            hint = "Swipe right to dismiss",
                        )
                    }
                }
            }
            NavDemoControls(backStack = backStack) {
                ElegantButton(onClick = { pushIdempotent(backStack, NavDemoRoute.Detail) }) {
                    Text("Push detail")
                }
                ElegantButton(onClick = { pushIdempotent(backStack, NavDemoRoute.Item(2)) }) {
                    Text("Push item")
                }
                ElegantButton(onClick = { backStack.removeLastOrNull() }) {
                    Text("Pop")
                }
                ElegantButton(
                    onClick = { if (backStack.isNotEmpty()) backStack[backStack.lastIndex] = NavDemoRoute.Item(9) },
                ) {
                    Text("Replace top")
                }
            }
        }

        DemoCard(
            compact = compact,
            eyebrow = "TRANSITIONS",
            title = "Presets, per-route overrides, and effects",
            description = "The Modal preset slides up from the bottom and keeps the page below visible; a custom scale-fade transition applies per route; display effects round the corners, dim the stack, and paint a backdrop.",
        ) {
            val backStack = rememberElegantNavBackStack<NavDemoRoute>(NavDemoRoute.Home)
            NavDemoPane {
                ElegantNavDisplay(
                    backStack = backStack,
                    effects = ElegantNavDisplayEffects(
                        cornerClipRadius = rememberElegantNavSystemCornerRadius(),
                        cornerClipMode = ElegantNavCornerClipMode.All,
                        dimAmount = 0.32f,
                        backdropColor = colors.backgroundCanvas,
                    ),
                ) {
                    entry<NavDemoRoute.Home> {
                        NavDemoPage(
                            route = NavDemoRoute.Home,
                            tint = colors.backgroundCanvas,
                            hint = "The backdrop extends the page background outward",
                        )
                    }
                    entry<NavDemoRoute.Sheet>(
                        transition = ElegantNavTransitions.Modal,
                        swipeDismiss = ElegantNavSwipeDirection.TopToBottom,
                    ) {
                        NavDemoPage(
                            route = NavDemoRoute.Sheet,
                            tint = colors.surfaceRaised,
                            hint = "Swipe down to dismiss",
                        )
                    }
                    entry<NavDemoRoute.Item>(transition = navDemoScaleFade) { route ->
                        NavDemoPage(
                            route = route,
                            tint = colors.interactivePrimary.copy(alpha = 0.06f),
                            hint = "Custom scale-fade transition",
                        )
                    }
                }
            }
            NavDemoControls(backStack = backStack) {
                ElegantButton(onClick = { pushIdempotent(backStack, NavDemoRoute.Sheet) }) {
                    Text("Open sheet")
                }
                ElegantButton(onClick = { pushIdempotent(backStack, NavDemoRoute.Item(2)) }) {
                    Text("Open item")
                }
                ElegantButton(onClick = { backStack.removeLastOrNull() }) {
                    Text("Pop")
                }
            }
        }
    }
}

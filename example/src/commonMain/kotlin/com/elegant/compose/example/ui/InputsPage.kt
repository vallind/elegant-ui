// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.elegant.compose.ui.autocomplete.ElegantAutocomplete
import com.elegant.compose.ui.autocomplete.ElegantAutocompleteOption
import com.elegant.compose.ui.calendar.ElegantCalendar
import com.elegant.compose.ui.calendar.ElegantDate
import com.elegant.compose.ui.checkbox.ElegantCheckbox
import com.elegant.compose.ui.checkboxgroup.ElegantCheckboxGroup
import com.elegant.compose.ui.checkboxgroup.ElegantCheckboxGroupItem
import com.elegant.compose.ui.colorpicker.ElegantColorPicker
import com.elegant.compose.ui.colorpicker.ElegantColorPickerPanel
import com.elegant.compose.ui.daterangepicker.ElegantDateRange
import com.elegant.compose.ui.daterangepicker.ElegantDateRangePicker
import com.elegant.compose.ui.datepicker.ElegantDatePicker
import com.elegant.compose.ui.fieldset.ElegantFieldset
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.input.ElegantInput
import com.elegant.compose.ui.inputgroup.ElegantInputGroup
import com.elegant.compose.ui.inputotp.ElegantInputOtp
import com.elegant.compose.ui.label.ElegantLabel
import com.elegant.compose.ui.numberfield.ElegantNumberField
import com.elegant.compose.ui.numberpicker.ElegantNumberPicker
import com.elegant.compose.ui.radiogroup.ElegantRadioGroup
import com.elegant.compose.ui.radiogroup.ElegantRadioGroupItem
import com.elegant.compose.ui.searchbar.ElegantSearchBar
import com.elegant.compose.ui.select.ElegantSelect
import com.elegant.compose.ui.select.ElegantSelectOption
import com.elegant.compose.ui.slider.ElegantSlider
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle
import com.elegant.compose.ui.switch.ElegantSwitch
import com.elegant.compose.ui.switchgroup.ElegantSwitchGroup
import com.elegant.compose.ui.switchgroup.ElegantSwitchGroupItem
import com.elegant.compose.ui.textarea.ElegantTextarea

/**
 * Inputs scene: a realistic profile form plus the full matrix of text fields, selection controls,
 * sliders, and pickers.
 *
 * @param onBack callback popping this page from the back stack.
 */
@Composable
internal fun InputsPage(onBack: () -> Unit) {
    ScenePage(title = "Inputs", onBack = onBack) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ElegantSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        ) {
            Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            ProfileForm()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            TextFields()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            SelectionControls()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            Sliders()
            Spacer(modifier = Modifier.height(ElegantSpacing.md))
            Pickers()
            Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        }
    }
}

/** Profile form: fieldsets grouping inputs, textarea, number field, and OTP. */
@Composable
private fun ProfileForm() {
    ElegantFieldset(legend = "Account", modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(ElegantSpacing.md),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        ) {
            var name by rememberSaveable { mutableStateOf("") }
            var email by rememberSaveable { mutableStateOf("") }
            ElegantLabel(text = "Display name", required = true)
            ElegantInput(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = "Display name",
                placeholder = "How should we call you?",
            )
            ElegantInput(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = "Email",
                placeholder = "you@example.com",
                isError = email.isNotBlank() && !email.contains("@"),
                errorText = "Enter a valid email address",
            )
            var age by rememberSaveable { mutableStateOf(28) }
            ElegantNumberField(
                value = age,
                onValueChange = { age = it },
                modifier = Modifier.fillMaxWidth(),
                label = "Age",
                minValue = 0,
                maxValue = 120,
            )
            var otp by rememberSaveable { mutableStateOf("") }
            ElegantInputOtp(
                value = otp,
                onValueChange = { otp = it },
                length = 6,
                isError = otp.isNotBlank() && otp.length < 6,
                errorText = "6 digits required",
            )
        }
    }
    ElegantFieldset(legend = "Biography", modifier = Modifier.fillMaxWidth()) {
        var bio by rememberSaveable { mutableStateOf("") }
        ElegantTextarea(
            value = bio,
            onValueChange = { bio = it },
            modifier = Modifier.fillMaxWidth().padding(ElegantSpacing.md),
            label = "About you",
            placeholder = "A short introduction",
            minLines = 3,
            maxLines = 6,
            maxLength = 200,
        )
    }
}

/** Text fields: search bar, labeled input, error input, and an input group. */
@Composable
private fun TextFields() {
    ElegantSmallTitle(text = "Text fields")
    var search by rememberSaveable { mutableStateOf("") }
    ElegantSearchBar(
        query = search,
        onQueryChange = { search = it },
        modifier = Modifier.fillMaxWidth(),
        placeholder = "Search anything",
        onClear = { search = "" },
    )
    var handle by rememberSaveable { mutableStateOf("") }
    ElegantInputGroup(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "elegant-ui/",
            style = ElegantTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = ElegantSpacing.md),
        )
        ElegantInput(
            value = handle,
            onValueChange = { handle = it },
            modifier = Modifier.weight(1f),
            placeholder = "username",
        )
    }
}

/** Selection controls: checkbox, radio, switch, groups, select, and autocomplete. */
@Composable
private fun SelectionControls() {
    ElegantSmallTitle(text = "Selection")
    var notifications by rememberSaveable { mutableStateOf(true) }
    var updates by rememberSaveable { mutableStateOf(false) }
    ElegantSwitch(
        checked = notifications,
        onCheckedChange = { notifications = it },
        label = "Push notifications",
    )
    ElegantSwitch(
        checked = updates,
        onCheckedChange = { updates = it },
        label = "Product updates",
    )
    var agree by rememberSaveable { mutableStateOf(false) }
    ElegantCheckbox(
        checked = agree,
        onCheckedChange = { agree = it },
        label = "I agree to the terms",
    )
    var plan by rememberSaveable { mutableStateOf("free") }
    ElegantRadioGroup(
        selectedValue = plan,
        onSelect = { plan = it },
        items = remember {
            listOf(
                ElegantRadioGroupItem(text = "Free", value = "free"),
                ElegantRadioGroupItem(text = "Pro", value = "pro"),
                ElegantRadioGroupItem(text = "Team", value = "team"),
            )
        },
    )
    var tags by remember { mutableStateOf(setOf("design")) }
    ElegantCheckboxGroup(
        selectedValues = tags,
        onToggle = { value, checked ->
            tags = if (checked) tags + value else tags - value
        },
        items = remember {
            listOf(
                ElegantCheckboxGroupItem(text = "Design", value = "design"),
                ElegantCheckboxGroupItem(text = "Engineering", value = "engineering"),
                ElegantCheckboxGroupItem(text = "Marketing", value = "marketing"),
            )
        },
    )
    var channels by remember { mutableStateOf(setOf("email")) }
    ElegantSwitchGroup(
        selectedValues = channels,
        onToggle = { value, checked ->
            channels = if (checked) channels + value else channels - value
        },
        items = remember {
            listOf(
                ElegantSwitchGroupItem(text = "Email", value = "email"),
                ElegantSwitchGroupItem(text = "SMS", value = "sms"),
                ElegantSwitchGroupItem(text = "In-app", value = "inapp"),
            )
        },
    )
    var selectedOption by remember { mutableStateOf<ElegantSelectOption?>(null) }
    ElegantSelect(
        selectedOption = selectedOption,
        onOptionSelected = { selectedOption = it },
        options = remember {
            listOf(
                ElegantSelectOption(text = "Personal", value = "personal"),
                ElegantSelectOption(text = "Business", value = "business"),
                ElegantSelectOption(text = "Education", value = "education"),
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = "Account type",
        placeholder = "Pick one",
    )
    var autocompleteQuery by rememberSaveable { mutableStateOf("") }
    var autocompleteSelection by remember { mutableStateOf<ElegantAutocompleteOption?>(null) }
    ElegantAutocomplete(
        query = autocompleteQuery,
        onQueryChange = { autocompleteQuery = it },
        options = remember {
            listOf(
                ElegantAutocompleteOption(text = "Berlin", value = "berlin"),
                ElegantAutocompleteOption(text = "Paris", value = "paris"),
                ElegantAutocompleteOption(text = "Tokyo", value = "tokyo"),
                ElegantAutocompleteOption(text = "New York", value = "newyork"),
            )
        },
        onOptionSelected = {
            autocompleteSelection = it
            autocompleteQuery = it.text
        },
        modifier = Modifier.fillMaxWidth(),
        label = "Home city",
        placeholder = "Start typing a city",
    )
    if (autocompleteSelection != null) {
        Text(
            text = "Selected: ${autocompleteSelection!!.text}",
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}

/** Sliders: continuous, stepped, and labeled ranges. */
@Composable
private fun Sliders() {
    ElegantSmallTitle(text = "Sliders")
    var volume by rememberSaveable { mutableStateOf(0.6f) }
    ElegantSlider(
        value = volume,
        onValueChange = { volume = it },
        modifier = Modifier.fillMaxWidth(),
    )
    var rating by rememberSaveable { mutableStateOf(3f) }
    ElegantSlider(
        value = rating,
        onValueChange = { rating = it },
        modifier = Modifier.fillMaxWidth(),
        valueRange = 0f..5f,
        steps = 4,
    )
}

/** Pickers: number, color, calendar, and date pickers. */
@Composable
private fun Pickers() {
    ElegantSmallTitle(text = "Pickers")
    var quantity by rememberSaveable { mutableStateOf(2) }
    ElegantNumberPicker(
        value = quantity,
        onValueChange = { quantity = it },
        minValue = 1,
        maxValue = 10,
    )
    var accent by remember { mutableStateOf(Color(0xFF6C4EFF)) }
    ElegantColorPicker(
        selectedColor = accent,
        onColorSelected = { accent = it },
        modifier = Modifier.fillMaxWidth(),
    )
    var panelColor by remember { mutableStateOf(Color(0xFF147D64)) }
    ElegantColorPickerPanel(
        color = panelColor,
        onColorChange = { panelColor = it },
        modifier = Modifier.fillMaxWidth(),
    )
    var birthday by remember { mutableStateOf<ElegantDate?>(null) }
    ElegantDatePicker(
        date = birthday,
        onDateSelected = { birthday = it },
        modifier = Modifier.fillMaxWidth(),
        label = "Birthday",
        placeholder = "Pick a date",
    )
    var trip by remember { mutableStateOf(ElegantDateRange(null, null)) }
    ElegantDateRangePicker(
        range = trip,
        onRangeSelected = { trip = it },
        modifier = Modifier.fillMaxWidth(),
        label = "Trip dates",
    )
    var vacation by remember { mutableStateOf<ElegantDate?>(null) }
    ElegantCalendar(
        selectedDate = vacation,
        onDateSelected = { vacation = it },
        modifier = Modifier.fillMaxWidth(),
    )
}

# DatePicker

`ElegantDatePicker` is a read-only date field with a calendar popup: the field renders the chosen date on a Filled input surface, and clicking it opens `ElegantCalendar` below it. Use it anywhere a caller needs one bounded civil date, such as booking or form flows, on Android, Desktop JVM, and Web/Wasm.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=date-picker" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.datepicker.ElegantDatePicker
import com.elegant.compose.ui.datepicker.ElegantDatePickerColors
import com.elegant.compose.ui.datepicker.ElegantDatePickerDefaults
import com.elegant.compose.ui.calendar.ElegantDate
```

## Basic Usage

`ElegantDatePicker` is a controlled component: keep the chosen `date` in caller-owned state and write every selection back from `onDateSelected`. While no date is chosen, the field shows the `placeholder`; a chosen date renders as a zero-padded `YYYY-MM-DD` string.

```kotlin
var selected by remember { mutableStateOf<ElegantDate?>(null) }

ElegantDatePicker(
    date = selected,
    onDateSelected = { selected = it },
    label = "Departure",
    placeholder = "Pick a departure date",
)
```

## Component States

Clicking the field opens a focusable calendar popup anchored directly below it, start-aligned and clamped to the window. The popup starts from the selected date, then `minDate`, and closes when a day is selected (which also invokes `onDateSelected`), when the user clicks outside, or when the user presses Escape — both gestures are delivered by the platform popup. The popup does not close when the field loses focus.

`isError` paints the field border with `statusCritical`, replaces the supporting text with the error text below the field, and announces the error text through semantics. When `enabled` is false the field renders dimmed, never opens the popup, and never invokes `onDateSelected`; days outside `minDate`..`maxDate` are disabled inside the calendar and can never be chosen.

```kotlin
ElegantDatePicker(
    date = selected,
    onDateSelected = { selected = it },
    label = "Arrival",
    isError = true,
    errorText = "Choose an arrival date.",
)

ElegantDatePicker(
    date = null,
    onDateSelected = {},
    label = "Departure",
    enabled = false,
)
```

## Properties

### ElegantDatePicker Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `date` | `ElegantDate?` | Selected date, owned by the caller | - | Yes |
| `onDateSelected` | `(ElegantDate) -> Unit` | Callback invoked when a day is chosen | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the component root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the field can open the popup and choose dates | `true` | No |
| `label` | `String?` | Label shown above the field | `null` | No |
| `placeholder` | `String?` | Hint shown inside the field while it is enabled and empty | `null` | No |
| `isError` | `Boolean` | Whether the field communicates an error state | `false` | No |
| `errorText` | `String?` | Error message shown below the field and announced when `isError` | `null` | No |
| `supportingText` | `String?` | Guidance shown below the field unless error text is shown | `null` | No |
| `minDate` | `ElegantDate?` | Earliest selectable date | `null` | No |
| `maxDate` | `ElegantDate?` | Latest selectable date | `null` | No |
| `colors` | `ElegantDatePickerColors` | Theme-aware state colors | `ElegantDatePickerDefaults.colors()` | No |

### ElegantDate

`ElegantDate(year, month, day)` is an immutable civil-date value with `month` in 1..12, comparable chronologically. The date picker shares the model with `ElegantCalendar` and never depends on platform date types.

### ElegantDatePickerDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum field-container height |
| `colors()` | `ElegantDatePickerColors` | Theme-aware Light/Dark Filled field colors |

### ElegantDatePickerColors

`ElegantDatePickerColors` contains container, border, and content colors for the resting, hovered, focused, disabled, and error states, plus the placeholder, label, supporting-text, and error-text colors. Start with `ElegantDatePickerDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Bounded Booking Range

```kotlin
ElegantDatePicker(
    date = selected,
    onDateSelected = { selected = it },
    label = "Check-in",
    minDate = ElegantDate(2026, 8, 1),
    maxDate = ElegantDate(2026, 8, 31),
)
```

### Custom Emphasis

```kotlin
val baseColors = ElegantDatePickerDefaults.colors()

ElegantDatePicker(
    date = selected,
    onDateSelected = { selected = it },
    label = "Departure",
    colors = baseColors.copy(
        errorBorderColor = Color(0xFFC63D52),
        errorTextColor = Color(0xFFC63D52),
    ),
    isError = true,
    errorText = "Choose a departure date.",
)
```

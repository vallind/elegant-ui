# Date Range Picker

`ElegantDateRangePicker` is a read-only date-range field that opens a two-month calendar panel: it reuses `ElegantCalendar` grids side by side, follows the Filled input rhythm of the input family, and carries a controlled immutable `ElegantDateRange` model across Android, Desktop JVM, and Web/Wasm from `commonMain` without platform date APIs.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=date-range-picker" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.daterangepicker.ElegantDateRange
import com.elegant.compose.ui.daterangepicker.ElegantDateRangePicker
import com.elegant.compose.ui.daterangepicker.ElegantDateRangePickerColors
import com.elegant.compose.ui.daterangepicker.ElegantDateRangePickerDefaults
import com.elegant.compose.ui.calendar.ElegantDate
```

## Basic Usage

The range is a controlled `ElegantDateRange`: the caller owns it and writes the newest value back from `onRangeSelected`, which fires after every day click.

```kotlin
var range by remember { mutableStateOf(ElegantDateRange(null, null)) }

ElegantDateRangePicker(
    range = range,
    onRangeSelected = { range = it },
    label = "Stay dates",
    placeholder = "Pick a stay",
)
```

## Component States

The first click on a selectable day sets the start; the second click sets the end; a second click before the start moves the start and clears the end so the range can be re-picked; a click after a complete range starts a new one. The panel stays open after a selection so both endpoints can be adjusted, and closes on an outside click or Escape, both delivered by the platform popup. Days outside `minDate`..`maxDate` render disabled and never invoke the callback. In-between days are not tinted in this version: the range is communicated by the two endpoints and the field readout.

The panel shows two months side by side starting at the month of the current start, then `minDate`, then January 2000 — the library has no clock in `commonMain` — and its header row steps the pair by one month, clamped to the months of `minDate`/`maxDate`. When `enabled = false` the field dims, rejects clicks and focus, and never opens the panel.

```kotlin
ElegantDateRangePicker(
    range = range,
    onRangeSelected = { range = it },
    minDate = ElegantDate(2026, 1, 1),
    maxDate = ElegantDate(2026, 12, 31),
)
```

## Properties

### ElegantDateRangePicker Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `range` | `ElegantDateRange` | Current selection, owned by the caller | - | Yes |
| `onRangeSelected` | `(ElegantDateRange) -> Unit` | Callback with the newest accepted range | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the picker root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the field accepts clicks and the panel can open | `true` | No |
| `label` | `String?` | Optional label shown above the field | `null` | No |
| `placeholder` | `String?` | Optional hint shown while enabled and empty | `null` | No |
| `isError` | `Boolean` | Whether the field communicates an error state | `false` | No |
| `errorText` | `String?` | Optional error message below the field | `null` | No |
| `supportingText` | `String?` | Optional guidance below the field | `null` | No |
| `minDate` | `ElegantDate?` | Earliest selectable date | `null` | No |
| `maxDate` | `ElegantDate?` | Latest selectable date | `null` | No |
| `colors` | `ElegantDateRangePickerColors` | Theme-aware state colors | `ElegantDateRangePickerDefaults.colors()` | No |

### ElegantDateRange

`ElegantDateRange(start, end)` is an immutable range model over `ElegantDate`. Both endpoints null means nothing has been picked; a start with a null end is a range awaiting its end. A null start with a non-null end is invalid and must not be constructed; the picker never emits one.

### ElegantDateRangePickerDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | Default 48dp field container height |
| `colors()` | `ElegantDateRangePickerColors` | Theme-aware Light/Dark colors |

### ElegantDateRangePickerColors

`ElegantDateRangePickerColors` carries the same field roles as the input family: container, hovered, focused, disabled, border, content, placeholder, label, supporting, and error colors. Start with `ElegantDateRangePickerDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Bounded Booking Range

```kotlin
ElegantDateRangePicker(
    range = range,
    onRangeSelected = { range = it },
    label = "Trip dates",
    minDate = ElegantDate(2026, 8, 1),
    maxDate = ElegantDate(2026, 8, 31),
)
```

### Error Feedback

```kotlin
ElegantDateRangePicker(
    range = range,
    onRangeSelected = { range = it },
    label = "Stay dates",
    isError = range.end == null && range.start != null,
    errorText = "Pick an end day to complete the stay.",
)
```

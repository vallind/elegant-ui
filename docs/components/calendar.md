# Calendar

`ElegantCalendar` is a refined month-grid date picker with navigation, range bounds, and a stable `ElegantDate` model that works on Android, Desktop JVM, and Web/Wasm from `commonMain` without platform date APIs. It is the foundation of the calendar family.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=calendar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.calendar.ElegantCalendar
import com.elegant.compose.ui.calendar.ElegantCalendarColors
import com.elegant.compose.ui.calendar.ElegantCalendarDefaults
import com.elegant.compose.ui.calendar.ElegantDate
```

## Basic Usage

A controlled calendar renders a Monday-first 42-cell month grid; the selected date is owned by the caller.

```kotlin
var selected by remember { mutableStateOf<ElegantDate?>(null) }

ElegantCalendar(
    selectedDate = selected,
    onDateSelected = { selected = it },
)
```

## Component States

The grid is Monday-first. Leading and trailing days of adjacent months render dimmed and are never selectable. Days outside `minDate`..`maxDate` and the whole grid when `enabled = false` are disabled: they render in the disabled role, announce themselves as disabled, and never invoke `onDateSelected`. The selected day fills with the accent container and inverse text; hovered selectable days tint with the hover surface.

Month navigation clamps to the months of `minDate`/`maxDate` when those bounds exist. The library has no clock in `commonMain`, so the initially visible month defaults to `initialMonth`, then `selectedDate`, then `minDate`, then January 2000; pass `initialMonth` when "this month" is the desired start.

```kotlin
ElegantCalendar(
    selectedDate = selected,
    onDateSelected = { selected = it },
    minDate = ElegantDate(2026, 1, 1),
    maxDate = ElegantDate(2026, 12, 31),
)
```

## Properties

### ElegantCalendar Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `selectedDate` | `ElegantDate?` | Currently selected date | - | Yes |
| `onDateSelected` | `(ElegantDate) -> Unit` | Callback with the chosen day | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the calendar root | `Modifier` | No |
| `enabled` | `Boolean` | Whether selection and navigation are accepted | `true` | No |
| `initialMonth` | `ElegantDate?` | Month shown on first composition | `null` | No |
| `minDate` | `ElegantDate?` | Earliest selectable date | `null` | No |
| `maxDate` | `ElegantDate?` | Latest selectable date | `null` | No |
| `colors` | `ElegantCalendarColors` | Theme-aware calendar colors | `ElegantCalendarDefaults.colors()` | No |

### ElegantDate

`ElegantDate(year, month, day)` is an immutable civil-date value with `month` in 1..12, comparable chronologically. Use `ElegantDateDefaults`-style construction directly; the calendar family never depends on platform date types.

### ElegantCalendarDefaults

| Member | Type | Description |
| --- | --- | --- |
| `WeekdayRowHeight` | `Dp` | Default 32dp weekday header row height |
| `DayCellSize` | `Dp` | Default 40dp day cell edge length |
| `DayCellGap` | `Dp` | Default 4dp gap between day cells |
| `NavigationSize` | `Dp` | Default 40dp month navigation button size |
| `colors()` | `ElegantCalendarColors` | Theme-aware Light/Dark colors |

### ElegantCalendarColors

`ElegantCalendarColors` contains container, header, weekday, day, selected, disabled, hovered, today, and border roles. Start with `ElegantCalendarDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Bounded Booking Range

```kotlin
ElegantCalendar(
    selectedDate = selected,
    onDateSelected = { selected = it },
    minDate = ElegantDate(2026, 8, 1),
    maxDate = ElegantDate(2026, 8, 31),
    initialMonth = ElegantDate(2026, 8, 1),
)
```

### Custom Emphasis

```kotlin
val baseColors = ElegantCalendarDefaults.colors()

ElegantCalendar(
    selectedDate = selected,
    onDateSelected = { selected = it },
    colors = baseColors.copy(
        selectedDayContainerColor = Color(0xFF6C4EFF),
        selectedDayColor = Color.White,
    ),
)
```

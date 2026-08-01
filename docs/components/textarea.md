# Textarea

`ElegantTextarea` is a refined multi-line text field for forms and notes with a label and placeholder, supporting or error text, an optional character limit, a configurable visible line range, and leading and trailing icons. It reuses the `ElegantInput` visual contract: a sunken container that outlines only on focus or error, starting from 3 lines and growing to 8 before scrolling.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=textarea" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.textarea.ElegantTextarea
import com.elegant.compose.ui.textarea.ElegantTextareaColors
import com.elegant.compose.ui.textarea.ElegantTextareaDefaults
```

## Basic Usage

`ElegantTextarea` is a controlled component: keep `value` in a `remember`-backed state and write every accepted change back from `onValueChange`. Input past the `maxLength` limit is truncated before the callback fires.

```kotlin
var notes by remember { mutableStateOf("") }

ElegantTextarea(
    value = notes,
    onValueChange = { notes = it },
    label = "Release notes",
    placeholder = "Summarize what changed",
    supportingText = "Markdown is supported.",
)
```

## Lines

The field reserves `minLines` visible text lines and grows with content until `maxLines`, after which it scrolls. `minLines` is coerced to at least 1 and `maxLines` to at least `minLines`, so any caller values stay valid.

```kotlin
ElegantTextarea(
    value = draft,
    onValueChange = { draft = it },
    label = "Draft",
    minLines = 4,
    maxLines = 12,
)
```

## Component States

`ElegantTextarea` follows the interaction precedence: disabled, error border, focused border, hovered border, resting. `isError` combines with focus visuals: the border turns `statusCritical` while the container keeps its focus color, and the error text replaces the supporting text below the field and is announced through semantics.

When `enabled` is false the field rejects focus and input; `readOnly` keeps focus and copy but blocks edits. Placeholder text is shown only while the field is enabled and empty, and the displayed value is always the caller-owned state.

```kotlin
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
```

## Properties

### ElegantTextarea Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `value` | `String` | Current field content, owned by the caller | - | Yes |
| `onValueChange` | `(String) -> Unit` | Callback invoked with the newest accepted field content | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the textarea root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the field accepts focus and input | `true` | No |
| `readOnly` | `Boolean` | Whether the field can be focused and copied but not edited | `false` | No |
| `label` | `String?` | Label shown above the field | `null` | No |
| `placeholder` | `String?` | Hint shown inside the field while it is enabled and empty | `null` | No |
| `supportingText` | `String?` | Guidance shown below the field unless error text is shown | `null` | No |
| `isError` | `Boolean` | Whether the field communicates an error state | `false` | No |
| `errorText` | `String?` | Error message shown below the field and announced when `isError` | `null` | No |
| `maxLength` | `Int` | Maximum accepted character count; input is truncated at this length | `ElegantTextareaDefaults.MaxLengthUnlimited` | No |
| `minLines` | `Int` | Minimum number of visible text lines | `ElegantTextareaDefaults.MinLines` | No |
| `maxLines` | `Int` | Maximum number of visible text lines before scrolling | `ElegantTextareaDefaults.MaxLines` | No |
| `colors` | `ElegantTextareaColors` | Theme-aware state colors | `ElegantTextareaDefaults.colors()` | No |
| `leadingIcon` | `@Composable (() -> Unit)?` | Content before the input area | `null` | No |
| `trailingIcon` | `@Composable (() -> Unit)?` | Content after the input area | `null` | No |

### ElegantTextareaDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MaxLengthUnlimited` | `Int` | `Int.MAX_VALUE` sentinel for textareas without a length limit |
| `MinimumTouchHeight` | `Dp` | 120dp minimum field-container height |
| `MinLines` | `Int` | Default 3 minimum visible text lines |
| `MaxLines` | `Int` | Default 8 maximum visible text lines before scrolling |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantTextareaColors` | Theme-aware Light/Dark state colors |
| `shape()` | `Shape` | 12dp rounded container shape |

### ElegantTextareaColors

`ElegantTextareaColors` contains container, border, and content colors for the resting, hovered, focused, disabled, and error states, plus the placeholder, label, supporting-text, and error-text colors. It mirrors `ElegantInputColors` field for field, so a product-specific input hierarchy can be reused for textareas. Start with `ElegantTextareaDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Length-Limited Textarea with Counter

```kotlin
var bio by remember { mutableStateOf("") }

ElegantTextarea(
    value = bio,
    onValueChange = { bio = it },
    label = "Bio",
    maxLength = 200,
    supportingText = "${bio.length}/200",
)
```

### Custom Error Styling

```kotlin
val baseColors = ElegantTextareaDefaults.colors()

ElegantTextarea(
    value = draft,
    onValueChange = { draft = it },
    label = "Draft",
    colors = baseColors.copy(
        errorBorderColor = Color(0xFFC63D52),
        errorTextColor = Color(0xFFC63D52),
    ),
    isError = true,
    errorText = "This draft contains content that is not allowed.",
)
```

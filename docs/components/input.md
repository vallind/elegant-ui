# Input

`ElegantInput` is a refined single-line text field for forms and search with two visual variants, a label and placeholder, supporting or error text, an optional character limit, and leading and trailing icons. Use it for any editable text where a Badge cannot carry content and a Button is for actions.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=input" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.input.ElegantInput
import com.elegant.compose.ui.input.ElegantInputColors
import com.elegant.compose.ui.input.ElegantInputDefaults
import com.elegant.compose.ui.input.ElegantInputStyle
```

## Basic Usage

`ElegantInput` is a controlled component: keep `value` in a `remember`-backed state and write every accepted change back from `onValueChange`. Input past the `maxLength` limit is truncated before the callback fires.

```kotlin
var email by remember { mutableStateOf("") }

ElegantInput(
    value = email,
    onValueChange = { email = it },
    label = "Email address",
    placeholder = "you@example.com",
    supportingText = "We only use this to send you updates.",
)
```

## Styles

`Filled` recesses the field into the surface with a sunken container and outlines only on focus or error. `Outlined` keeps a transparent container with a visible resting border. Both variants share the same 48dp minimum field height, interaction states, and slot layout.

```kotlin
ElegantInput(
    value = message,
    onValueChange = { message = it },
    label = "Message",
    leadingIcon = { Text("✎") },
)

ElegantInput(
    value = coupon,
    onValueChange = { coupon = it },
    label = "Coupon code",
    style = ElegantInputStyle.Outlined,
    trailingIcon = { Text("✓") },
)
```

## Component States

`ElegantInput` follows the interaction precedence: disabled, error border, focused border, hovered border, resting. `isError` combines with focus visuals: the border turns `statusCritical` while the container keeps its focus color, and the error text replaces the supporting text below the field and is announced through semantics.

When `enabled` is false the field rejects focus and input; `readOnly` keeps focus and copy but blocks edits. Placeholder text is shown only while the field is enabled and empty, and the displayed value is always the caller-owned state.

```kotlin
ElegantInput(
    value = "Maya Chen",
    onValueChange = {},
    label = "Display name",
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
    isError = true,
    errorText = "Must be 3-20 characters.",
)
```

## Properties

### ElegantInput Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `value` | `String` | Current field content, owned by the caller | - | Yes |
| `onValueChange` | `(String) -> Unit` | Callback invoked with the newest accepted field content | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the input root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the field accepts focus and input | `true` | No |
| `readOnly` | `Boolean` | Whether the field can be focused and copied but not edited | `false` | No |
| `label` | `String?` | Label shown above the field | `null` | No |
| `placeholder` | `String?` | Hint shown inside the field while it is enabled and empty | `null` | No |
| `supportingText` | `String?` | Guidance shown below the field unless error text is shown | `null` | No |
| `isError` | `Boolean` | Whether the field communicates an error state | `false` | No |
| `errorText` | `String?` | Error message shown below the field and announced when `isError` | `null` | No |
| `maxLength` | `Int` | Maximum accepted character count; input is truncated at this length | `ElegantInputDefaults.MaxLengthUnlimited` | No |
| `visualTransformation` | `VisualTransformation` | Transforms the displayed text without changing the underlying value; use for password masking | `VisualTransformation.None` | No |
| `style` | `ElegantInputStyle` | Visual variant | `ElegantInputStyle.Filled` | No |
| `colors` | `ElegantInputColors` | Theme-aware state colors | `ElegantInputDefaults.colors(style)` | No |
| `leadingIcon` | `@Composable (() -> Unit)?` | Content before the input area | `null` | No |
| `trailingIcon` | `@Composable (() -> Unit)?` | Content after the input area | `null` | No |

### ElegantInputStyle Values

| Value | Behavior |
| --- | --- |
| `Filled` | Sunken recessed container with a transparent resting border |
| `Outlined` | Transparent container with a visible resting border |

### ElegantInputDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MaxLengthUnlimited` | `Int` | `Int.MAX_VALUE` sentinel for inputs without a length limit |
| `MinimumTouchHeight` | `Dp` | 48dp minimum field-container height |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors(style)` | `ElegantInputColors` | Theme-aware Light/Dark colors for the selected style |
| `shape(style)` | `Shape` | 12dp rounded shape for `Filled`, 10dp for `Outlined` |

### ElegantInputColors

`ElegantInputColors` contains container, border, and content colors for the resting, hovered, focused, disabled, and error states, plus the placeholder, label, supporting-text, and error-text colors. Start with `ElegantInputDefaults.colors(style)` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Length-Limited Field with Counter

```kotlin
var bio by remember { mutableStateOf("") }

ElegantInput(
    value = bio,
    onValueChange = { bio = it },
    label = "Bio",
    maxLength = 20,
    supportingText = "${bio.length}/20",
)
```

### Custom Error Styling

```kotlin
val baseColors = ElegantInputDefaults.colors(ElegantInputStyle.Outlined)

ElegantInput(
    value = code,
    onValueChange = { code = it },
    label = "Coupon code",
    style = ElegantInputStyle.Outlined,
    colors = baseColors.copy(
        errorBorderColor = Color(0xFFC63D52),
        errorTextColor = Color(0xFFC63D52),
    ),
    isError = true,
    errorText = "This code has expired.",
)
```

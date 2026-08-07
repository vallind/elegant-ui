# InputOtp

`ElegantInputOtp` is a refined one-time-passcode (OTP) entry control built from a row of square cells. It accepts digits only, truncates input at the configured length, highlights the focused cell with a caret and, when the theme enables focus rings, a focus ring, and can surface an inline error message. Use it for verification codes, two-factor authentication, and other short numeric secrets.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=input-otp" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.inputotp.ElegantInputOtp
import com.elegant.compose.ui.inputotp.ElegantInputOtpColors
import com.elegant.compose.ui.inputotp.ElegantInputOtpDefaults
```

## Basic Usage

`ElegantInputOtp` is a controlled component: keep `value` in a `remember`-backed state and write every accepted change back from `onValueChange`. Input is filtered to digits and truncated at the `length` limit before the callback fires; each cell displays one digit of the accepted value from left to right.

```kotlin
var code by remember { mutableStateOf("") }

ElegantInputOtp(
    value = code,
    onValueChange = { code = it },
)
```

## Component States

While the hidden text field is focused, the cell at the caret position (the first empty cell, or the last cell when `value` is full) shows a caret and, when the theme enables focus rings, is highlighted with the `focusRing` border. `isError` turns every cell border `statusCritical` with a 2dp stroke and shows `errorText` below the cells; the error message is also announced through semantics.

When `enabled` is false the field rejects focus and input: the cells keep their sunken container, but their digits are dimmed to `textTertiary`. Only digits are accepted, so letters and symbols are silently dropped before the callback fires.

```kotlin
ElegantInputOtp(
    value = "123",
    onValueChange = {},
    enabled = false,
)

ElegantInputOtp(
    value = code,
    onValueChange = { code = it },
    isError = true,
    errorText = "The code you entered is incorrect.",
)
```

## Properties

### ElegantInputOtp Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `value` | `String` | Current code content, owned by the caller | - | Yes |
| `onValueChange` | `(String) -> Unit` | Callback invoked with the newest accepted code content | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the input root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the field accepts focus and input | `true` | No |
| `length` | `Int` | Number of cells; input is truncated at this length | `ElegantInputOtpDefaults.DefaultLength` | No |
| `isError` | `Boolean` | Whether the field communicates an error state | `false` | No |
| `errorText` | `String?` | Error message shown below the cells and announced when `isError` | `null` | No |
| `colors` | `ElegantInputOtpColors` | Theme-aware state colors | `ElegantInputOtpDefaults.colors()` | No |

### ElegantInputOtpDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive row height |
| `CellSize` | `Dp` | 48dp side length of every square cell |
| `CellGap` | `Dp` | 8dp horizontal gap between adjacent cells |
| `DefaultLength` | `Int` | Default number of cells, 6 |
| `colors()` | `ElegantInputOtpColors` | Theme-aware Light/Dark cell, border, content, and error colors |

### ElegantInputOtpColors

`ElegantInputOtpColors` contains the cell container, border, content, and error-text colors for the resting, focused, error, and disabled states. Start with `ElegantInputOtpDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Custom Length and Error Handling

```kotlin
var resetCode by remember { mutableStateOf("") }

ElegantInputOtp(
    value = resetCode,
    onValueChange = { resetCode = it },
    length = 4,
    isError = resetCode.isNotEmpty() && resetCode != "1234",
    errorText = "The reset code is invalid.",
)
```

### Custom Error Styling

```kotlin
val baseColors = ElegantInputOtpDefaults.colors()

ElegantInputOtp(
    value = code,
    onValueChange = { code = it },
    colors = baseColors.copy(
        errorCellBorderColor = Color(0xFFC63D52),
        errorTextColor = Color(0xFFC63D52),
    ),
    isError = true,
    errorText = "This code has expired.",
)
```

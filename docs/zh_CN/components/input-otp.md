# InputOtp

`ElegantInputOtp` 是用于验证码场景的精致一次性密码（OTP）输入组件,由一排方形单元格组成。它只接受数字,按配置长度截断输入,以插入符高亮当前单元格,并在主题启用焦点环时以聚焦环描边,并可显示内联错误信息。适用于验证码、双因素认证与其他短数字密钥。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=input-otp" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.inputotp.ElegantInputOtp
import com.elegant.compose.ui.inputotp.ElegantInputOtpColors
import com.elegant.compose.ui.inputotp.ElegantInputOtpDefaults
```

## 基本用法

`ElegantInputOtp` 是受控组件:将 `value` 保存在 `remember` 状态中,并在 `onValueChange` 中写回每一次被接受的变更。输入会在回调触发前被过滤为数字并按 `length` 截断;每个单元格从左到右显示一位已接受的数字。

```kotlin
var code by remember { mutableStateOf("") }

ElegantInputOtp(
    value = code,
    onValueChange = { code = it },
)
```

## 组件状态

当隐藏的文本输入框聚焦时,插入符所在位置的单元格（第一个空单元格;若 `value` 已满则为最后一个单元格）会显示插入符,并在主题启用焦点环时以 `focusRing` 边框高亮。`isError` 会将所有单元格边框变为 `statusCritical` 并加粗至 2dp,同时在单元格下方显示 `errorText`;该错误信息也会通过语义播报。

当 `enabled` 为 false 时,输入框拒绝聚焦与输入:单元格保持凹陷容器,但数字会被调暗为 `textTertiary`。组件只接受数字,字母与符号会在回调触发前被静默丢弃。

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
    errorText = "你输入的验证码不正确。",
)
```

## 属性

### ElegantInputOtp 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `value` | `String` | 当前验证码内容,由调用方持有 | - | 是 |
| `onValueChange` | `(String) -> Unit` | 以最新被接受的验证码内容触发的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到输入组件根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 输入框是否接受聚焦与输入 | `true` | 否 |
| `length` | `Int` | 单元格数量;输入在此长度处截断 | `ElegantInputOtpDefaults.DefaultLength` | 否 |
| `isError` | `Boolean` | 输入框是否表达错误状态 | `false` | 否 |
| `errorText` | `String?` | 当 `isError` 时显示在单元格下方并播报的错误信息 | `null` | 否 |
| `colors` | `ElegantInputOtpColors` | 主题感知的状态颜色 | `ElegantInputOtpDefaults.colors()` | 否 |

### ElegantInputOtpDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp 最小交互行高 |
| `CellSize` | `Dp` | 每个方形单元格的 48dp 边长 |
| `CellGap` | `Dp` | 相邻单元格之间 8dp 的水平间距 |
| `DefaultLength` | `Int` | 默认单元格数量,为 6 |
| `colors()` | `ElegantInputOtpColors` | 单元格、边框、内容与错误颜色的 Light/Dark 主题感知颜色 |

### ElegantInputOtpColors

`ElegantInputOtpColors` 包含 resting、focused、error 与 disabled 各状态的单元格容器色、边框色、内容色与错误文本色。应先调用 `ElegantInputOtpDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 自定义长度与错误处理

```kotlin
var resetCode by remember { mutableStateOf("") }

ElegantInputOtp(
    value = resetCode,
    onValueChange = { resetCode = it },
    length = 4,
    isError = resetCode.isNotEmpty() && resetCode != "1234",
    errorText = "重置码无效。",
)
```

### 自定义错误样式

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
    errorText = "该验证码已过期。",
)
```

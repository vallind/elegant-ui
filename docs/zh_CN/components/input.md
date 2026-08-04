# Input

`ElegantInput` 是适用于表单与搜索的精致单行文本输入组件,提供两种视觉变体、标签与占位符、辅助文本或错误文本、可选的字数限制以及前后缀图标。它适用于任何可编辑文本:此时 Badge 承载不了内容,而 Button 用于操作。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=input" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.input.ElegantInput
import com.elegant.compose.ui.input.ElegantInputColors
import com.elegant.compose.ui.input.ElegantInputDefaults
import com.elegant.compose.ui.input.ElegantInputStyle
```

## 基本用法

`ElegantInput` 是受控组件:将 `value` 保存在 `remember` 状态中,并在 `onValueChange` 中写回每一次被接受的变更。超过 `maxLength` 的输入会在回调触发前被截断。

```kotlin
var email by remember { mutableStateOf("") }

ElegantInput(
    value = email,
    onValueChange = { email = it },
    label = "邮箱地址",
    placeholder = "you@example.com",
    supportingText = "我们只会用它向你发送更新。",
)
```

## 风格

`Filled` 以凹陷容器让输入框融入表面,仅在聚焦或出错时显示描边。`Outlined` 保持透明容器,并显示可见的静止边框。两种变体共享相同的 48dp 最小输入框高度、交互状态与插槽布局。

```kotlin
ElegantInput(
    value = message,
    onValueChange = { message = it },
    label = "留言",
    leadingIcon = { Text("✎") },
)

ElegantInput(
    value = coupon,
    onValueChange = { coupon = it },
    label = "优惠码",
    style = ElegantInputStyle.Outlined,
    trailingIcon = { Text("✓") },
)
```

## 组件状态

`ElegantInput` 遵循交互优先级:disabled、错误边框、focused 边框、hovered 边框、resting。`isError` 与聚焦视觉组合:边框变为 `statusCritical`,而容器保持聚焦色;错误文本替换输入框下方的辅助文本,并通过语义播报。

当 `enabled` 为 false 时,输入框拒绝聚焦与输入;`readOnly` 保留聚焦与复制,但禁止编辑。占位符仅在输入框处于启用且为空时显示,展示的值始终由调用方持有。

```kotlin
ElegantInput(
    value = "Maya Chen",
    onValueChange = {},
    label = "显示名称",
    readOnly = true,
)

ElegantInput(
    value = "旧账户",
    onValueChange = {},
    label = "账户",
    enabled = false,
)

ElegantInput(
    value = nickname,
    onValueChange = { nickname = it },
    label = "昵称",
    isError = true,
    errorText = "长度需为 3-20 个字符。",
)
```

## 属性

### ElegantInput 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `value` | `String` | 当前输入内容,由调用方持有 | - | 是 |
| `onValueChange` | `(String) -> Unit` | 以最新被接受的输入内容触发的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到输入组件根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 输入框是否接受聚焦与输入 | `true` | 否 |
| `readOnly` | `Boolean` | 输入框是否可聚焦与复制但不可编辑 | `false` | 否 |
| `label` | `String?` | 显示在输入框上方的标签 | `null` | 否 |
| `placeholder` | `String?` | 输入框启用且为空时显示在内部的提示 | `null` | 否 |
| `supportingText` | `String?` | 显示在输入框下方的辅助文本,错误文本出现时隐藏 | `null` | 否 |
| `isError` | `Boolean` | 输入框是否表达错误状态 | `false` | 否 |
| `errorText` | `String?` | 当 `isError` 时显示并播报的错误信息 | `null` | 否 |
| `maxLength` | `Int` | 最大接受字符数;超出部分的输入会被截断 | `ElegantInputDefaults.MaxLengthUnlimited` | 否 |
| `visualTransformation` | `VisualTransformation` | 转换显示文本而不改变底层值;用于密码掩码 | `VisualTransformation.None` | 否 |
| `style` | `ElegantInputStyle` | 视觉变体 | `ElegantInputStyle.Filled` | 否 |
| `colors` | `ElegantInputColors` | 主题感知的状态颜色 | `ElegantInputDefaults.colors(style)` | 否 |
| `leadingIcon` | `@Composable (() -> Unit)?` | 输入区域前的内容 | `null` | 否 |
| `trailingIcon` | `@Composable (() -> Unit)?` | 输入区域后的内容 | `null` | 否 |

### ElegantInputStyle 可选值

| 值 | 行为 |
| --- | --- |
| `Filled` | 凹陷容器,静止边框透明 |
| `Outlined` | 透明容器,静止边框可见 |

### ElegantInputDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MaxLengthUnlimited` | `Int` | `Int.MAX_VALUE` 哨兵值,表示不限长度 |
| `MinimumTouchHeight` | `Dp` | 48dp 最小输入框高度 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors(style)` | `ElegantInputColors` | 所选风格的 Light/Dark 主题感知颜色 |
| `shape(style)` | `Shape` | `Filled` 为 12dp 圆角,`Outlined` 为 10dp 圆角 |

### ElegantInputColors

`ElegantInputColors` 包含 resting、hovered、focused、disabled 与 error 各状态的容器色、边框色与内容色,以及占位符、标签、辅助文本与错误文本颜色。应先调用 `ElegantInputDefaults.colors(style)`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 带计数器的限长输入

```kotlin
var bio by remember { mutableStateOf("") }

ElegantInput(
    value = bio,
    onValueChange = { bio = it },
    label = "简介",
    maxLength = 20,
    supportingText = "${bio.length}/20",
)
```

### 自定义错误样式

```kotlin
val baseColors = ElegantInputDefaults.colors(ElegantInputStyle.Outlined)

ElegantInput(
    value = code,
    onValueChange = { code = it },
    label = "优惠码",
    style = ElegantInputStyle.Outlined,
    colors = baseColors.copy(
        errorBorderColor = Color(0xFFC63D52),
        errorTextColor = Color(0xFFC63D52),
    ),
    isError = true,
    errorText = "该优惠码已过期。",
)
```

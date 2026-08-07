# Textarea

`ElegantTextarea` 是适用于表单与备注的精致多行文本输入组件,提供标签与占位符、辅助文本或错误文本、可选的字数限制、可配置的可见行数范围以及前后缀图标。它复用 `ElegantInput` 的视觉契约:凹陷容器仅在聚焦或出错时显示描边,默认从 3 行开始,增长到 8 行后滚动。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=textarea" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.textarea.ElegantTextarea
import com.elegant.compose.ui.textarea.ElegantTextareaColors
import com.elegant.compose.ui.textarea.ElegantTextareaDefaults
```

## 基本用法

`ElegantTextarea` 是受控组件:将 `value` 保存在 `remember` 状态中,并在 `onValueChange` 中写回每一次被接受的变更。超过 `maxLength` 的输入会在回调触发前被截断。

```kotlin
var notes by remember { mutableStateOf("") }

ElegantTextarea(
    value = notes,
    onValueChange = { notes = it },
    label = "发布说明",
    placeholder = "概括本次变更",
    supportingText = "支持 Markdown。",
)
```

## 行数

输入区域预留 `minLines` 行可见文本,内容增多时随之增高,达到 `maxLines` 后开始滚动。`minLines` 至少为 1,`maxLines` 至少为 `minLines`,因此任何调用方取值都保持有效。

```kotlin
ElegantTextarea(
    value = draft,
    onValueChange = { draft = it },
    label = "草稿",
    minLines = 4,
    maxLines = 12,
)
```

## 组件状态

`ElegantTextarea` 遵循交互优先级:disabled、错误边框、focused 边框、hovered 边框、resting。`isError` 与聚焦视觉组合:边框变为 `statusCritical`,而容器保持聚焦色;错误文本替换输入框下方的辅助文本,并通过语义播报。

当 `enabled` 为 false 时,输入框拒绝聚焦与输入;`readOnly` 保留聚焦与复制,但禁止编辑。占位符仅在输入框处于启用且为空时显示,展示的值始终由调用方持有。

```kotlin
ElegantTextarea(
    value = "作为不可变记录保留。",
    onValueChange = {},
    label = "审计备注",
    readOnly = true,
)

ElegantTextarea(
    value = "旧备注",
    onValueChange = {},
    label = "归档",
    enabled = false,
)

ElegantTextarea(
    value = bio,
    onValueChange = { bio = it },
    label = "简介",
    isError = true,
    errorText = "长度需为 3-200 个字符。",
)
```

## 属性

### ElegantTextarea 属性

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
| `maxLength` | `Int` | 最大接受字符数;超出部分的输入会被截断 | `ElegantTextareaDefaults.MaxLengthUnlimited` | 否 |
| `minLines` | `Int` | 可见文本的最小行数 | `ElegantTextareaDefaults.MinLines` | 否 |
| `maxLines` | `Int` | 滚动前可见文本的最大行数 | `ElegantTextareaDefaults.MaxLines` | 否 |
| `colors` | `ElegantTextareaColors` | 主题感知的状态颜色 | `ElegantTextareaDefaults.colors()` | 否 |
| `leadingIcon` | `@Composable (() -> Unit)?` | 输入区域前的内容 | `null` | 否 |
| `trailingIcon` | `@Composable (() -> Unit)?` | 输入区域后的内容 | `null` | 否 |

### ElegantTextareaDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MaxLengthUnlimited` | `Int` | `Int.MAX_VALUE` 哨兵值,表示不限长度 |
| `MinimumTouchHeight` | `Dp` | 120dp 最小输入区域高度 |
| `MinLines` | `Int` | 默认 3 行最小可见文本 |
| `MaxLines` | `Int` | 默认 8 行滚动前最大可见文本 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors()` | `ElegantTextareaColors` | Light/Dark 主题感知的状态颜色 |
| `shape()` | `Shape` | 12dp 圆角容器形状 |

### ElegantTextareaColors

`ElegantTextareaColors` 包含 resting、hovered、focused、disabled 与 error 各状态的容器色、边框色与内容色,以及占位符、标签、辅助文本与错误文本颜色。它与 `ElegantInputColors` 逐字段一致,因此产品自定义的输入组件配色可复用于多行文本域。应先调用 `ElegantTextareaDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 带计数器的限长文本域

```kotlin
var bio by remember { mutableStateOf("") }

ElegantTextarea(
    value = bio,
    onValueChange = { bio = it },
    label = "简介",
    maxLength = 200,
    supportingText = "${bio.length}/200",
)
```

### 自定义错误样式

```kotlin
val baseColors = ElegantTextareaDefaults.colors()

ElegantTextarea(
    value = draft,
    onValueChange = { draft = it },
    label = "草稿",
    colors = baseColors.copy(
        errorBorderColor = Color(0xFFC63D52),
        errorTextColor = Color(0xFFC63D52),
    ),
    isError = true,
    errorText = "该草稿包含不允许的内容。",
)
```

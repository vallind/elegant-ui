# ListPopup

`ElegantListPopup` 在同时包含触发器和弹出列表的 Box 下方显示一个数据驱动的单选选项列表:触发器由调用方持有,`ElegantListPopup` 与触发器放在同一个 Box 中,列表表面从该 Box 的下缘落下,起始侧对齐并限制在窗口内。与选中值匹配的选项以交互色、浅色背景和尾部勾选标记高亮。点击外部、按 Escape 或使用平台返回手势都会关闭列表。可聚焦的弹窗在打开时把键盘焦点移入列表;选择选项通过 `onOptionSelected` 上报,`expanded` 由调用方持有——弹出列表在选中后不会自行关闭。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=list-popup" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.listpopup.ElegantListPopup
import com.elegant.compose.ui.listpopup.ElegantListPopupDefaults
import com.elegant.compose.ui.listpopup.ElegantListPopupOption
```

## 基本用法

将 `ElegantListPopup` 放在与触发器相同的 Box 中。弹出列表锚定在该 Box 上,因此只包裹触发器即可让下拉列表精准落在触发器下方;调用方负责切换 `expanded`,在 `onDismissRequest` 中重置它,并在 `onOptionSelected` 中把所选选项的 value 写回 `selectedValue`。

```kotlin
var expanded by remember { mutableStateOf(false) }
var selectedValue by remember { mutableStateOf("paris") }

Box {
    ElegantButton(onClick = { expanded = true }) {
        Text("选择城市")
    }
    ElegantListPopup(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        options = listOf(
            ElegantListPopupOption(text = "巴黎", value = "paris"),
            ElegantListPopupOption(text = "伦敦", value = "london"),
            ElegantListPopupOption(text = "东京", value = "tokyo"),
        ),
        selectedValue = selectedValue,
        onOptionSelected = { option ->
            selectedValue = option.value
            expanded = false
        },
    )
}
```

## 选项

`ElegantListPopupOption` 把渲染用的 `text` 与稳定的 `value` 身份配对,`value` 用于与 `selectedValue` 比较并随选择一起上报。禁用选项以三级文字色渲染、忽略点击并播报禁用状态;禁用选项仍可以是选中值。

```kotlin
ElegantListPopupOption(
    text = "柏林",
    value = "berlin",
    enabled = false,
)
```

## 组件状态

选择启用的选项会以该选项调用 `onOptionSelected`;弹出列表不会自行关闭,因此由调用方在回调中决定。外部点击、Escape 和返回键通过 `onDismissRequest` 关闭。可聚焦的弹窗在打开时把键盘焦点移入列表,关闭时归还给触发器;聚焦的选项可用 Enter 或空格激活。选中选项显示交互色文字、选中背景与勾选标记;悬停选项显示悬停背景;禁用选项永不触发回调。

```kotlin
ElegantListPopup(
    expanded = expanded,
    onDismissRequest = { expanded = false },
    options = options,
    selectedValue = selectedValue,
    onOptionSelected = { option -> selectedValue = option.value },
)
```

## 属性

### ElegantListPopup 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `expanded` | `Boolean` | 是否显示列表表面 | - | 是 |
| `onDismissRequest` | `() -> Unit` | 用户请求关闭时调用 | - | 是 |
| `options` | `List<ElegantListPopupOption>` | 按给定顺序渲染的选项列表 | - | 是 |
| `selectedValue` | `String?` | 当前选中选项的 value,与 `ElegantListPopupOption.value` 匹配 | `null` | 否 |
| `onOptionSelected` | `(ElegantListPopupOption) -> Unit` | 用户选中的选项触发调用 | - | 是 |
| `modifier` | `Modifier` | 应用在可滚动选项列上的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantListPopupColors` | 列表表面与选项颜色 | `ElegantListPopupDefaults.colors()` | 否 |

### ElegantListPopupOption

| 属性名 | 类型 | 说明 |
| --- | --- | --- |
| `text` | `String` | 选项行中渲染的文字 |
| `value` | `String` | 与选中值匹配的稳定身份 |
| `enabled` | `Boolean` | 选项是否可选 |

### ElegantListPopupColors

| 属性名 | 类型 | 说明 |
| --- | --- | --- |
| `containerColor` | `Color` | 列表表面背景 |
| `contentColor` | `Color` | 启用且未选中选项的文字颜色 |
| `disabledContentColor` | `Color` | 禁用选项的文字颜色 |
| `selectedContentColor` | `Color` | 选中选项的文字与勾选颜色 |
| `selectedContainerColor` | `Color` | 选中选项的背景 |
| `hoveredContainerColor` | `Color` | 悬停选项的背景 |
| `borderColor` | `Color` | 列表表面边框颜色 |

### ElegantListPopupDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinWidth` | `Dp` | 160dp 最小列表宽度;表面随最宽选项增长 |
| `MaxHeight` | `Dp` | 320dp 最大列表高度,超出后选项列表滚动 |
| `ItemHeight` | `Dp` | 40dp 单个选项行高 |
| `HorizontalPadding` | `Dp` | 每个选项行内部 16dp 水平内边距 |
| `AnimationDurationMillis` | `Int` | 90ms 列表进入过渡时长 |

## 进阶用法

### 表单行中的弹出列表

把列表弹出与 `ElegantInput` 配对,让表单从弹出选择中读取城市、从输入框中读取自由文本备注;两个字段都持有调用方拥有的值。

```kotlin
var selectedValue by remember { mutableStateOf("paris") }
var note by remember { mutableStateOf("") }

Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
    ElegantInput(
        value = note,
        onValueChange = { note = it },
        label = "配送备注",
        placeholder = "可选备注",
    )
    Box {
        ElegantButton(onClick = { expanded = true }) {
            Text("选择城市")
        }
        ElegantListPopup(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            options = options,
            selectedValue = selectedValue,
            onOptionSelected = { option ->
                selectedValue = option.value
                expanded = false
            },
        )
    }
}
```

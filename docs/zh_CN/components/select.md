# Select

`ElegantSelect` 是适用于表单与筛选的精致单选字段:带标签的 Filled 风格触发器会打开一个主题化的选项菜单,并在所选项目上显示对勾;菜单负责焦点、键盘遍历、Esc 与点击外部区域的关闭处理。当必须从预定义列表中选择恰好一个值、而 Radio 行占用过多空间时,请使用它。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=select" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.select.ElegantSelect
import com.elegant.compose.ui.select.ElegantSelectDefaults
import com.elegant.compose.ui.select.ElegantSelectOption
```

## 基本用法

`ElegantSelect` 是受控组件:将 `selectedOption` 保存在 `remember` 状态中,并在 `onOptionSelected` 中写回每一次选择。传入完整的选项列表——文本或值为空的条目会被忽略——触发器会显示所选选项的文本;未选择任何内容时显示 `placeholder`。

```kotlin
var plan by remember { mutableStateOf<ElegantSelectOption?>(null) }
val plans = listOf(
    ElegantSelectOption("Starter", "starter"),
    ElegantSelectOption("Pro", "pro"),
    ElegantSelectOption("Enterprise", "enterprise"),
)

ElegantSelect(
    selectedOption = plan,
    onOptionSelected = { plan = it },
    options = plans,
    label = "工作区套餐",
    placeholder = "选择套餐",
    supportingText = "按月计费,可随时取消。",
)
```

## 选项模型

`ElegantSelectOption` 同时作为选择模型与菜单项模型。`value` 是用于存储、查询或提交的稳定标识;`text` 是展示的文本。将 `enabled` 设为 `false` 会让该选项在菜单中显示为禁用,永远无法被选中。

```kotlin
ElegantSelectOption(
    text = "旧账户",
    value = "legacy",
    enabled = false,
)
```

## 组件状态

触发器遵循交互优先级:disabled、错误边框、focused 边框、resting。`isError` 会将边框变为 2dp 的 `statusCritical`,并用 `errorText` 替换辅助文本,同时通过语义播报错误。触发器暴露 `DropdownList` 角色并播报展开状态;禁用选项在菜单中保持可见,但无法被选中。

当 `enabled` 为 false 时,触发器拒绝点击与聚焦,菜单永远不会打开,占位符与 Filled 输入框一样被隐藏。

```kotlin
ElegantSelect(
    selectedOption = region,
    onOptionSelected = { region = it },
    options = regions,
    label = "区域",
    isError = true,
    errorText = "该区域已不再可用。",
)

ElegantSelect(
    selectedOption = ElegantSelectOption("Legacy", "legacy"),
    onOptionSelected = {},
    options = legacyOptions,
    label = "账户",
    enabled = false,
)
```

## 属性

### ElegantSelect 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `selectedOption` | `ElegantSelectOption?` | 当前选择的选项,由调用方持有;按数据相等匹配 | - | 是 |
| `onOptionSelected` | `(ElegantSelectOption) -> Unit` | 以菜单中选中的选项触发的回调 | - | 是 |
| `options` | `List<ElegantSelectOption>` | 完整选项列表;文本或值为空的条目会被忽略 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到选择组件根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 触发器是否接受点击与聚焦、菜单是否可以打开 | `true` | 否 |
| `label` | `String?` | 显示在字段上方的标签 | `null` | 否 |
| `placeholder` | `String?` | 字段启用且未选择任何内容时显示在内部的提示 | `null` | 否 |
| `isError` | `Boolean` | 字段是否表达错误状态 | `false` | 否 |
| `errorText` | `String?` | 当 `isError` 时显示并播报的错误信息 | `null` | 否 |
| `supportingText` | `String?` | 显示在字段下方的辅助文本,错误文本出现时隐藏 | `null` | 否 |
| `interactionSource` | `MutableInteractionSource?` | 与触发器共享的可选交互源 | `null` | 否 |

### ElegantSelectOption 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 在触发器与菜单项中渲染的文本 | - | 是 |
| `value` | `String` | 用于比较选项与存储选择的稳定标识 | - | 是 |
| `enabled` | `Boolean` | 该选项是否可以从菜单中被选中 | `true` | 否 |

### ElegantSelectDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp 最小触发器高度 |
| `MenuMaxHeight` | `Dp` | 320dp 菜单高度上限,超过后选项列表滚动 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |

## 进阶用法

### 带禁用选项的长列表

当选项超过 `ElegantSelectDefaults.MenuMaxHeight` 时菜单开始滚动;禁用选项保持可见,但永远无法被选中。

```kotlin
val countries = remember {
    listOf(
        ElegantSelectOption("China", "cn"),
        ElegantSelectOption("Germany", "de"),
        ElegantSelectOption("Japan", "jp"),
        ElegantSelectOption("Norway", "no", enabled = false),
        ElegantSelectOption("United Kingdom", "gb"),
        ElegantSelectOption("United States", "us"),
    )
}

ElegantSelect(
    selectedOption = country,
    onOptionSelected = { country = it },
    options = countries,
    label = "国家",
    placeholder = "选择国家",
)
```

### 表单行中的 Select

触发器会填满父级宽度,因此它可以自然地与 `ElegantInput` 并排组成表单行。

```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
    ElegantInput(
        value = teamName,
        onValueChange = { teamName = it },
        modifier = Modifier.weight(1f),
        label = "团队名称",
        placeholder = "例如 Nova",
    )
    ElegantSelect(
        selectedOption = teamSize,
        onOptionSelected = { teamSize = it },
        options = sizes,
        modifier = Modifier.weight(1f),
        label = "团队规模",
        placeholder = "选择规模",
    )
}
```

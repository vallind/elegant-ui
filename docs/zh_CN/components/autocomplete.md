# Autocomplete

`ElegantAutocomplete` 是带内联建议列表的受控文本输入组件:当输入框聚焦时,匹配查询的选项会出现在锚定于输入框下方的浮层上,选择其中一项会触发选中回调。它适用于由调用方持有查询文本、且需要结构化选项的自由输入场景,例如国家或产品选择器。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=autocomplete" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.autocomplete.ElegantAutocomplete
import com.elegant.compose.ui.autocomplete.ElegantAutocompleteColors
import com.elegant.compose.ui.autocomplete.ElegantAutocompleteDefaults
import com.elegant.compose.ui.autocomplete.ElegantAutocompleteOption
```

## 基本用法

`ElegantAutocomplete` 是受控组件:将 `query` 保存在 `remember` 状态中,并在 `onQueryChange` 中写回每一次被接受的变更。完整的 `options` 列表会按查询过滤——空白查询显示全部选项,否则显示文本包含查询内容的选项,不区分大小写,并保持原始顺序。选择一条建议会触发 `onOptionSelected` 并关闭列表;如何更新查询由调用方决定。

```kotlin
var query by remember { mutableStateOf("") }

ElegantAutocomplete(
    query = query,
    onQueryChange = { query = it },
    options = listOf(
        ElegantAutocompleteOption(text = "法国", value = "FR"),
        ElegantAutocompleteOption(text = "德国", value = "DE"),
    ),
    onOptionSelected = { option ->
        query = option.text
    },
    label = "国家",
    placeholder = "搜索国家",
)
```

## 选项

`ElegantAutocompleteOption` 用于建模一条建议:`text` 渲染在建议行中,`value` 是随选中结果提交的稳定标识,`enabled` 标记不可选择的选项。禁用选项以三级文本色渲染、忽略点击,并通过语义播报禁用状态。

```kotlin
val countries = listOf(
    ElegantAutocompleteOption(text = "法国", value = "FR"),
    ElegantAutocompleteOption(text = "德国", value = "DE"),
    ElegantAutocompleteOption(text = "斐济", value = "FJ", enabled = false),
)
```

## 组件状态

当输入框聚焦且至少有一个选项匹配查询时,建议列表出现;选中建议、点击外部区域或按下 Escape、输入框失去焦点时,列表关闭。列表锚定在输入框下方,被限制在窗口范围内,一旦超过 `ElegantAutocompleteDefaults.MenuMaxHeight` 即开始滚动。

`isError` 会将输入框边框绘制为 `statusCritical`,并在输入框下方以错误文本替换辅助文本;错误文本会通过语义播报。当 `enabled` 为 false 时,输入框拒绝聚焦与输入,列表永远不会打开。

```kotlin
ElegantAutocomplete(
    query = "F",
    onQueryChange = {},
    options = countries,
    onOptionSelected = {},
    label = "国家",
    isError = true,
    errorText = "请从列表中选择一个国家。",
)

ElegantAutocomplete(
    query = "法国",
    onQueryChange = {},
    options = countries,
    onOptionSelected = {},
    label = "国家",
    enabled = false,
)
```

## 属性

### ElegantAutocomplete 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `query` | `String` | 当前查询文本,由调用方持有 | - | 是 |
| `onQueryChange` | `(String) -> Unit` | 以最新被接受的查询文本触发的回调 | - | 是 |
| `options` | `List<ElegantAutocompleteOption>` | 用于过滤建议的完整选项列表 | - | 是 |
| `onOptionSelected` | `(ElegantAutocompleteOption) -> Unit` | 选中一条建议时触发的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到组件根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 输入框是否接受聚焦与输入 | `true` | 否 |
| `label` | `String?` | 显示在输入框上方的标签 | `null` | 否 |
| `placeholder` | `String?` | 输入框启用且为空时显示在内部的提示 | `null` | 否 |
| `isError` | `Boolean` | 输入框是否表达错误状态 | `false` | 否 |
| `errorText` | `String?` | 当 `isError` 时显示并播报的错误信息 | `null` | 否 |
| `supportingText` | `String?` | 显示在输入框下方的辅助文本,错误文本出现时隐藏 | `null` | 否 |
| `colors` | `ElegantAutocompleteColors` | 主题感知的状态颜色 | `ElegantAutocompleteDefaults.colors()` | 否 |

### ElegantAutocompleteOption

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 渲染在建议行中的文本 | - | 是 |
| `value` | `String` | 随选中结果提交的稳定标识 | - | 是 |
| `enabled` | `Boolean` | 选项是否可选 | `true` | 否 |

### ElegantAutocompleteDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp 最小输入框高度 |
| `MenuMaxHeight` | `Dp` | 280dp 建议列表最大高度,超出后滚动 |
| `colors()` | `ElegantAutocompleteColors` | Light/Dark 主题感知的输入框与建议颜色 |

### ElegantAutocompleteColors

`ElegantAutocompleteColors` 包含 resting、hovered、focused、disabled 与 error 各状态的容器色、边框色与内容色,以及占位符、标签、辅助文本与错误文本颜色。应先调用 `ElegantAutocompleteDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 自定义错误样式

```kotlin
val baseColors = ElegantAutocompleteDefaults.colors()

ElegantAutocomplete(
    query = city,
    onQueryChange = { city = it },
    options = cities,
    onOptionSelected = { option -> city = option.text },
    label = "城市",
    colors = baseColors.copy(
        errorBorderColor = Color(0xFFC63D52),
        errorTextColor = Color(0xFFC63D52),
    ),
    isError = true,
    errorText = "请从列表中选择一个城市。",
)
```

# InputGroup

`ElegantInputGroup` 将相邻的输入框与内联操作合并为一个带边框的簇,共享同一个容器、描边与 4dp 内边距。它适用于金额输入、搜索行、单位选择器等复合控件:此时多个输入属于同一个逻辑单元。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=input-group" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.inputgroup.ElegantInputGroup
import com.elegant.compose.ui.inputgroup.ElegantInputGroupColors
import com.elegant.compose.ui.inputgroup.ElegantInputGroupDefaults
```

## 基本用法

`ElegantInputGroup` 是纯容器:它渲染一行圆角内容,由 `content` 提供各个输入框。建议搭配 `Outlined` 风格的 `ElegantInput`,让子组件共享簇的边框,而不是各自绘制。

```kotlin
var query by remember { mutableStateOf("") }

ElegantInputGroup {
    ElegantInput(
        value = query,
        onValueChange = { query = it },
        placeholder = "搜索组件",
        style = ElegantInputStyle.Outlined,
        modifier = Modifier.weight(1f),
    )
}
```

## 组合

子组件排列在同一个 `Row` 中,共享一条 12dp 圆角边框与 4dp 内边距。每个子组件保留自身的行为、聚焦与语义:分组只负责将内容裁剪为圆角形状、应用共享的背景与描边,并通过 `LocalContentColor` 向子组件提供其 `contentColor`。

```kotlin
var amount by remember { mutableStateOf("") }

ElegantInputGroup {
    ElegantInput(
        value = "$",
        onValueChange = {},
        readOnly = true,
        style = ElegantInputStyle.Outlined,
        modifier = Modifier.width(48.dp),
    )
    ElegantInput(
        value = amount,
        onValueChange = { amount = it },
        style = ElegantInputStyle.Outlined,
        modifier = Modifier.weight(1f),
    )
    ElegantInput(
        value = "USD",
        onValueChange = {},
        readOnly = true,
        style = ElegantInputStyle.Outlined,
        modifier = Modifier.width(56.dp),
    )
}
```

## 组件状态

分组本身没有交互状态:它不参与聚焦,不显示 hover 或 press 反馈,也不声明自身语义。disabled 与 error 状态属于子组件,它们在簇内保留各自的视觉语言。

```kotlin
ElegantInputGroup {
    ElegantInput(
        value = "优惠券",
        onValueChange = {},
        readOnly = true,
        style = ElegantInputStyle.Outlined,
    )
    ElegantInput(
        value = "WELCOME10",
        onValueChange = {},
        style = ElegantInputStyle.Outlined,
        enabled = false,
        modifier = Modifier.weight(1f),
    )
}
```

## 属性

### ElegantInputGroup 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到分组根节点的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantInputGroupColors` | 主题感知的容器色、边框色与内容色 | `ElegantInputGroupDefaults.colors()` | 否 |
| `content` | `@Composable RowScope.() -> Unit` | 渲染在簇内的输入框与内联操作 | - | 是 |

### ElegantInputGroupDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `colors()` | `ElegantInputGroupColors` | 主题感知的 Light/Dark 颜色:raised 容器、默认边框、primary 内容 |

### ElegantInputGroupColors

`ElegantInputGroupColors` 包含簇的容器色、共享边框色,以及通过 `LocalContentColor` 提供给子组件的内容色。应先调用 `ElegantInputGroupDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 带操作后缀的搜索行

尾部操作可以以普通文本的形式位于同一簇内;分组的 `contentColor` 使其与主题保持一致。

```kotlin
var query by remember { mutableStateOf("") }

ElegantInputGroup(modifier = Modifier.fillMaxWidth()) {
    ElegantInput(
        value = query,
        onValueChange = { query = it },
        placeholder = "搜索发行版本",
        style = ElegantInputStyle.Outlined,
        modifier = Modifier.weight(1f),
    )
    Row(
        modifier = Modifier
            .defaultMinSize(minHeight = 48.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "搜索",
            color = ElegantTheme.colors.interactivePrimary,
            style = ElegantTheme.typography.labelMedium,
        )
    }
}
```

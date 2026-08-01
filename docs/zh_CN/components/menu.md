# Menu

`ElegantMenu` 在同时包含触发器和菜单的 Box 下方显示一个临时动作列表:触发器由调用方持有,`ElegantMenu` 与触发器放在同一个 Box 中,菜单表面从该 Box 的下缘落下,起始侧对齐并限制在窗口内。点击外部、按 Escape 或使用平台返回手势都会关闭菜单。可聚焦的弹窗在打开时把键盘焦点移入菜单;菜单项是带可选前置与后置插槽的 40dp 行。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=menu" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.menu.ElegantMenu
import com.elegant.compose.ui.menu.ElegantMenuDefaults
import com.elegant.compose.ui.menu.ElegantMenuItem
```

## 基本用法

将 `ElegantMenu` 放在与触发器相同的 Box 中。菜单锚定在该 Box 上,因此只包裹触发器即可让下拉菜单精准落在触发器下方;调用方负责切换 `expanded`,并在 `onDismissRequest` 与每个菜单项的 `onClick` 中重置它。

```kotlin
var expanded by remember { mutableStateOf(false) }

Box {
    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "更多选项")
    }
    ElegantMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        ElegantMenuItem(
            text = "编辑",
            onClick = { expanded = false },
        )
        ElegantMenuItem(
            text = "分享",
            onClick = { expanded = false },
        )
    }
}
```

## 菜单项插槽

`ElegantMenuItem` 支持前置与后置插槽,渲染在 20dp 的盒子中并留有 12dp 间距,适合放置图标、状态点与快捷键提示。插槽内容继承解析后的菜单项内容颜色,并随菜单项一起禁用。

```kotlin
ElegantMenuItem(
    text = "移至回收站",
    onClick = { /* 处理 */ },
    leadingContent = { Icon(Icons.Default.Delete, contentDescription = null) },
    trailingContent = { Text("Del") },
)
```

## 组件状态

菜单表面本身没有视觉状态;交互都发生在菜单项上。悬停与按下的菜单项显示悬停背景,禁用菜单项永不触发回调。可聚焦的弹窗在外部点击、Escape 或返回键时关闭;打开时键盘焦点移入菜单,关闭时归还给触发器;聚焦的菜单项可用 Enter 或空格激活。

```kotlin
ElegantMenuItem(
    text = "恢复",
    onClick = { expanded = false },
    enabled = false,
)
```

## 属性

### ElegantMenu 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `expanded` | `Boolean` | 是否显示菜单表面 | - | 是 |
| `onDismissRequest` | `() -> Unit` | 用户请求关闭时调用 | - | 是 |
| `modifier` | `Modifier` | 应用在可滚动菜单项列上的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantMenuColors` | 菜单表面颜色 | `ElegantMenuDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | 菜单项与自定义行 | - | 是 |

### ElegantMenuItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 菜单项标签,超长时以省略号截断 | - | 是 |
| `onClick` | `() -> Unit` | 启用的菜单项被激活时调用 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到菜单项行的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 菜单项是否接受激活 | `true` | 否 |
| `leadingContent` | `@Composable (() -> Unit)?` | 标签之前的内容,如图标 | `null` | 否 |
| `trailingContent` | `@Composable (() -> Unit)?` | 标签之后的内容,如快捷键提示 | `null` | 否 |
| `colors` | `ElegantMenuColors` | 解析菜单项外观的菜单颜色 | `ElegantMenuDefaults.colors()` | 否 |

### ElegantMenuColors

| 属性名 | 类型 | 说明 |
| --- | --- | --- |
| `containerColor` | `Color` | 菜单表面背景 |
| `contentColor` | `Color` | 菜单内的文字与图标颜色 |
| `disabledContentColor` | `Color` | 禁用菜单项的文字与图标颜色 |
| `dividerColor` | `Color` | 菜单项之间分隔线的推荐颜色 |
| `selectedItemColor` | `Color` | 语义上已选中菜单项的背景 |
| `hoveredItemColor` | `Color` | 悬停或按下菜单项的背景 |

### ElegantMenuDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinWidth` | `Dp` | 160dp 最小菜单宽度;表面随最宽菜单项增长 |
| `MaxHeight` | `Dp` | 320dp 最大菜单高度,超出后菜单项列滚动 |
| `ItemHeight` | `Dp` | 40dp 单个菜单项行高 |
| `HorizontalPadding` | `Dp` | 每个菜单项内部 16dp 水平内边距 |
| `AnimationDurationMillis` | `Int` | 90ms 菜单进入过渡时长 |

## 进阶用法

### 菜单分组

用 `ElegantDivider` 分隔相关菜单项;`ElegantMenuColors` 中的分隔线颜色角色与默认分隔线主题一致,分组菜单保持协调。

```kotlin
ElegantMenuItem(
    text = "个人资料",
    onClick = { expanded = false },
    leadingContent = { Icon(Icons.Default.Person, contentDescription = null) },
)
ElegantMenuItem(
    text = "设置",
    onClick = { expanded = false },
    leadingContent = { Icon(Icons.Default.Settings, contentDescription = null) },
)
ElegantDivider(modifier = Modifier.padding(vertical = ElegantSpacing.xs))
ElegantMenuItem(
    text = "退出登录",
    onClick = { expanded = false },
)
```

### 可滚动的菜单

菜单项列超过 `ElegantMenuDefaults.MaxHeight` 后开始滚动;添加后置插槽可以提示折叠区域之外还有更多操作。

```kotlin
ElegantMenu(
    expanded = expanded,
    onDismissRequest = { expanded = false },
    modifier = Modifier.heightIn(max = 240.dp),
) {
    repeat(10) { index ->
        ElegantMenuItem(
            text = "项目 ${index + 1}",
            onClick = { expanded = false },
        )
    }
}
```

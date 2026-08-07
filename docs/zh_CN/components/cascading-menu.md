# 级联菜单

`ElegantCascadingMenu` 在同时包含触发器和菜单的 Box 下方,以嵌套的表面显示多级动作列表:触发器由调用方持有,`ElegantCascadingMenu` 与触发器放在同一个 Box 中,根表面从该 Box 的下缘落下,起始侧对齐并限制在窗口内。带子项的菜单项会渲染尾部箭头,并在其旁边打开子菜单表面;指向或点击这样的菜单项会替换当前打开的级联链,点击叶子菜单项会通过 `onItemClick` 报告其祖先链并关闭菜单。点击外部、按 Escape 或使用平台返回手势都会通过可聚焦的根弹窗关闭整条链。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=cascading-menu" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.cascadingmenu.ElegantCascadingMenu
import com.elegant.compose.ui.cascadingmenu.ElegantCascadingMenuDefaults
import com.elegant.compose.ui.cascadingmenu.ElegantCascadingMenuItem
```

## 基本用法

与 `ElegantMenu` 相同,把 `ElegantCascadingMenu` 放在与触发器相同的 Box 中。调用方负责切换 `expanded`,并在 `onDismissRequest` 中重置它;`onItemClick` 收到被点击叶子菜单项的祖先链,以叶子结尾,例如 `[编辑, 复制]`。

```kotlin
var expanded by remember { mutableStateOf(false) }

val items = listOf(
    ElegantCascadingMenuItem(
        text = "编辑",
        children = listOf(
            ElegantCascadingMenuItem(text = "复制"),
            ElegantCascadingMenuItem(text = "粘贴"),
        ),
    ),
    ElegantCascadingMenuItem(
        text = "插入",
        children = listOf(
            ElegantCascadingMenuItem(text = "图片"),
            ElegantCascadingMenuItem(text = "表格"),
        ),
    ),
)

Box {
    ElegantButton(onClick = { expanded = true }) {
        Text("编辑文档")
    }
    ElegantCascadingMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        items = items,
        onItemClick = { path ->
            expanded = false
            // path 为 [编辑, 复制]、[编辑, 粘贴]、[插入, 图片]、...
        },
    )
}
```

## 嵌套菜单项

`ElegantCascadingMenuItem` 是递归模型:`children` 持有父菜单项的子菜单,叶子菜单项保持为空。禁用的父菜单项在悬停时仍然显示子菜单,但禁用菜单项永不触发回调。

```kotlin
ElegantCascadingMenuItem(
    text = "样式",
    children = listOf(
        ElegantCascadingMenuItem(text = "加粗"),
        ElegantCascadingMenuItem(
            text = "对齐",
            children = listOf(
                ElegantCascadingMenuItem(text = "左对齐"),
                ElegantCascadingMenuItem(text = "居中"),
                ElegantCascadingMenuItem(text = "右对齐"),
            ),
        ),
        ElegantCascadingMenuItem(text = "删除线", enabled = false),
    ),
)
```

## 组件状态

每一级的交互都发生在菜单项上:悬停的菜单项显示悬停背景,禁用菜单项永不触发回调,悬停或点击不同的父菜单项会替换当前打开的级联链。根弹窗可聚焦,在外部点击、Escape 或返回键时关闭并收起整条链;子菜单表面不可聚焦,跟随根弹窗关闭;打开时键盘焦点移入根菜单,关闭时归还给触发器。

```kotlin
ElegantCascadingMenuItem(
    text = "恢复",
    enabled = false,
)
```

## 属性

### ElegantCascadingMenu 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `expanded` | `Boolean` | 是否显示菜单链 | - | 是 |
| `onDismissRequest` | `() -> Unit` | 用户请求关闭时调用 | - | 是 |
| `items` | `List<ElegantCascadingMenuItem>` | 菜单中显示的递归菜单树 | - | 是 |
| `onItemClick` | `(List<ElegantCascadingMenuItem>) -> Unit` | 以被点击叶子菜单项结尾的祖先链作为参数调用 | - | 是 |
| `modifier` | `Modifier` | 应用在根表面可滚动菜单项列上的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantCascadingMenuColors` | 每一级的菜单表面颜色 | `ElegantCascadingMenuDefaults.colors()` | 否 |

### ElegantCascadingMenuItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 菜单项标签,超长时以省略号截断 | - | 是 |
| `enabled` | `Boolean` | 菜单项是否接受激活 | `true` | 否 |
| `children` | `List<ElegantCascadingMenuItem>` | 子菜单中显示的菜单项;叶子菜单项为空 | `emptyList()` | 否 |

### ElegantCascadingMenuColors

| 属性名 | 类型 | 说明 |
| --- | --- | --- |
| `containerColor` | `Color` | 每一级的菜单表面背景 |
| `contentColor` | `Color` | 菜单内的文字与箭头颜色 |
| `disabledContentColor` | `Color` | 禁用菜单项的文字与箭头颜色 |
| `hoveredContainerColor` | `Color` | 悬停菜单项的背景 |
| `borderColor` | `Color` | 放在菜单旁的表面对推荐的边框或分隔线颜色 |

### ElegantCascadingMenuDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinWidth` | `Dp` | 180dp 最小菜单宽度;表面随最宽菜单项增长 |
| `MaxHeight` | `Dp` | 320dp 最大菜单高度,超出后菜单项列滚动 |
| `ItemHeight` | `Dp` | 40dp 每一级单个菜单项行高 |
| `HorizontalPadding` | `Dp` | 每个菜单项行内部 16dp 水平内边距 |
| `SubmenuOffset` | `Dp` | 父菜单项末端与子菜单表面之间 4dp 间隙 |
| `AnimationDurationMillis` | `Int` | 90ms 根菜单进入过渡时长 |

## 进阶用法

### 从数据构建菜单

当菜单需要与变化的内容保持同步时,从数据模型构建菜单树;`onItemClick` 报告的菜单项链可以直接映射回源对象。

```kotlin
data class Action(val title: String, val subActions: List<Action> = emptyList())

val actions: List<Action> = /* ... */

val items = actions.map { action ->
    ElegantCascadingMenuItem(
        text = action.title,
        children = action.subActions.map { subAction ->
            ElegantCascadingMenuItem(text = subAction.title)
        },
    )
}

ElegantCascadingMenu(
    expanded = expanded,
    onDismissRequest = { expanded = false },
    items = items,
    onItemClick = { path ->
        expanded = false
        val clicked = path.last().text
    },
)
```

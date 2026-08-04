# Icons

`ElegantIcon` 以主题感知着色渲染内置的 `ElegantIcons` 矢量图标集。24 个图标随库以矢量路径形式提供——无需资源文件、无平台素材——在 Android、Desktop JVM 与 Web/Wasm 上表现一致。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=icons" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.icon.ElegantIcon
import com.elegant.compose.ui.icon.ElegantIconDefaults
import com.elegant.compose.ui.icon.ElegantIcons
```

## 基本用法

`contentDescription` 为 null 时图标保持纯装饰;非空值则提供无障碍标签。

```kotlin
ElegantIcon(
    icon = ElegantIcons.Search,
    contentDescription = "搜索",
)
```

## 组件状态

图标是非交互组件。颜色跟随环境内容色;需要特定角色时传入 `tint`。矢量路径的填充色在渲染时被替换,因此同一图标在所有主题与状态色下均可用。

```kotlin
ElegantIcon(
    icon = ElegantIcons.Delete,
    contentDescription = "删除",
    tint = ElegantTheme.colors.statusCritical,
)
```

## 属性

### ElegantIcon 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `icon` | `ImageVector` | 要绘制的矢量图标 | - | 是 |
| `contentDescription` | `String?` | 本地化无障碍标签;null 为装饰性图标 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到图标根节点的修饰符 | `Modifier` | 否 |
| `tint` | `Color` | 图标颜色 | `LocalContentColor.current` | 否 |

### ElegantIcons

`ElegantIcons` 提供 24 个图标:`ArrowLeft/Right/Up/Down`、`ChevronLeft/Right/Up/Down`、`Check`、`Close`、`Plus`、`Minus`、`Search`、`Edit`、`Delete`、`Share`、`MoreVert`、`MoreHoriz`、`Person`、`Home`、`Settings`、`Notifications`、`Star`、`Heart`,以及 `All`(按声明顺序的完整列表)。

### ElegantIconDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Size` | `Dp` | 默认 24dp 图标边长 |

## 进阶用法

### 用于按钮插槽

```kotlin
ElegantButton(
    onClick = {},
    leadingIcon = {
        ElegantIcon(icon = ElegantIcons.Plus, contentDescription = null)
    },
) {
    Text("创建")
}
```

### 主题角色着色

```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
    ElegantIcon(
        icon = ElegantIcons.Home,
        contentDescription = "首页",
        tint = ElegantTheme.colors.textPrimary,
    )
    ElegantIcon(
        icon = ElegantIcons.Notifications,
        contentDescription = null,
        tint = ElegantTheme.colors.textSecondary,
    )
}
```

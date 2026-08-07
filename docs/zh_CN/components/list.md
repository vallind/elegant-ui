# List

`ElegantList` 与 `ElegantListItem` 用于构建设置页、导航与成员名册等场景中无障碍且主题感知的纵向行。`ElegantList` 是不滚动的普通列容器,`ElegantListItem` 是行原语,提供可选的前置槽、两行标题块、可选的后置槽以及可选的选中交互。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=list" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.list.ElegantList
import com.elegant.compose.ui.list.ElegantListItem
import com.elegant.compose.ui.list.ElegantListItemColors
import com.elegant.compose.ui.list.ElegantListItemDefaults
```

## 基本用法

`ElegantList` 纵向排列条目且自身不添加间距,因此每个条目拥有自己的内边距,行密度由调用方掌控。列表本身不滚动;当内容可能超出视口时,请将其包裹在 `verticalScroll` 或惰性列中。不传 `onClick` 的条目是非交互组件:它仅显示标题、辅助文本与槽位,保留内容自身的语义,并且不支持焦点。

```kotlin
ElegantList {
    ElegantListItem(title = { Text("通用") })
    ElegantListItem(
        title = { Text("通知") },
        supportingText = { Text("角标、声音与摘要") },
    )
    ElegantListItem(
        title = { Text("账户") },
        supportingText = { Text("登录、隐私与安全") },
    )
}
```

## 组件状态

传入 `onClick` 后条目变为按钮式行:它会播报 `Role.Button` 以及 `selected` 与 `disabled`,保持 48dp 最小行高,在主题启用焦点环时显示焦点环,并带有波纹的 hover 与 press 容器反馈动画。选中的条目会将常驻透明容器替换为由当前主题解析出的强调色调容器,该效果同样适用于非交互条目。

状态优先级:disabled、pressed、selected、hovered、resting。hover 与 press 反馈仅作用于交互条目,焦点环也仅在主题启用时对聚焦的交互条目渲染。

```kotlin
var selected by remember { mutableIntStateOf(0) }

ElegantList {
    ElegantListItem(
        title = { Text("无线网络") },
        onClick = { selected = 0 },
        selected = selected == 0,
    )
    ElegantListItem(
        title = { Text("蓝牙") },
        onClick = { selected = 1 },
        selected = selected == 1,
    )
    ElegantListItem(
        title = { Text("飞行模式") },
        onClick = {},
        enabled = false,
    )
}
```

## 属性

### ElegantList 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到列根节点的修饰符 | `Modifier` | 否 |
| `content` | `@Composable () -> Unit` | 纵向渲染的条目;每个条目拥有自己的内边距 | - | 是 |

### ElegantListItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `leadingContent` | `(@Composable () -> Unit)?` | 标题前的可选内容,居中于 20dp 盒子 | `null` | 否 |
| `modifier` | `Modifier` | 仅应用一次到条目根节点的修饰符 | `Modifier` | 否 |
| `title` | `@Composable () -> Unit` | 主行,提供 `labelLarge` 字体样式 | - | 是 |
| `supportingText` | `(@Composable () -> Unit)?` | 可选的次行,提供 `bodyMedium` 字体样式 | `null` | 否 |
| `trailingContent` | `(@Composable () -> Unit)?` | 标题块后的可选内容,居中于 20dp 盒子 | `null` | 否 |
| `onClick` | `(() -> Unit)?` | 可选激活回调;null 保持非交互 | `null` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互 | `true` | 否 |
| `selected` | `Boolean` | 条目是否传达已选中的状态 | `false` | 否 |
| `colors` | `ElegantListItemColors` | 主题感知的状态颜色 | `ElegantListItemDefaults.colors()` | 否 |
| `contentPadding` | `PaddingValues` | 行内容的内边距 | `PaddingValues(horizontal = 16.dp, vertical = 8.dp)` | 否 |

### ElegantListItemDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 每个条目保持的 48dp 最小行高 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors()` | `ElegantListItemColors` | Light/Dark 主题感知颜色 |

### ElegantListItemColors

`ElegantListItemColors` 包含透明常驻容器、主内容、辅助文本以及前置与后置槽颜色,还有 hovered、pressed、disabled 与焦点环覆盖值。应先调用 `ElegantListItemDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 团队成员名册组合

在 `ElegantListItem` 行内组合 `ElegantAvatar`、两行标题块与 `ElegantIconButton`,构建真实的名册界面;当成员增多时,将列表包裹在 `verticalScroll` 中。

```kotlin
Column(
    modifier = Modifier
        .fillMaxWidth()
        .heightIn(max = 320.dp)
        .verticalScroll(rememberScrollState()),
) {
    ElegantList {
        ElegantListItem(
            leadingContent = {
                ElegantAvatar(
                    name = "Maya Chen",
                    initials = "MC",
                    size = ElegantAvatarSize.Small,
                )
            },
            title = { Text("Maya Chen") },
            supportingText = { Text("设计系统负责人") },
            trailingContent = {
                ElegantIconButton(
                    onClick = { /* 打开个人资料菜单 */ },
                    contentDescription = "更多个人资料操作",
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }
            },
            onClick = {},
        )
        ElegantListItem(
            leadingContent = {
                ElegantAvatar(
                    name = "Noah Williams",
                    initials = "NW",
                    size = ElegantAvatarSize.Small,
                )
            },
            title = { Text("Noah Williams") },
            supportingText = { Text("跨平台工程") },
            onClick = {},
        )
    }
}
```

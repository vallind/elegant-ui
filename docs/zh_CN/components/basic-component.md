# BasicComponent 基础行

一种设置行基座:将前导内容、标题块、尾部控件与可选底部内容组合成一个可交互行。用于构建与偏好设置家族保持一致的自定义设置行。

<iframe id="demoIframe" src="../../compose/index.html?id=basic-component" style="width: 100%; height: 320px; border: 1px solid var(--vp-c-divider); border-radius: 8px;"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponent
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponentDefaults
```

## 基本用法

提供 `title` 与 `summary`;添加 `onClick` 前行为普通行,添加后整行成为可交互目标。

```kotlin
ElegantBasicComponent(
    title = "飞行模式",
    summary = "关闭所有无线连接",
    startAction = {
        Icon(
            imageVector = ElegantIcons.Settings,
            contentDescription = null,
        )
    },
    endActions = {
        ElegantSwitch(
            checked = enabled,
            onCheckedChange = { enabled = it },
        )
    },
    onClick = { /* 打开设置 */ },
)
```

## 组件状态

- **可交互**:提供 `onClick` 后行高提升到 48dp 最小值,启用悬停与按压容器颜色,并宣布按钮角色。
- **按住**:`holdDownState` 为 true 时强制按下视觉。
- **禁用**:`enabled = false` 时拒绝点击,文本颜色回退到禁用角色。

## 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| :--- | :--- | :--- | :--- | :--- |
| `title` | `String?` | 可选行标题;未提供 `content` 时与摘要一起渲染。 | `null` | 否 |
| `modifier` | `Modifier` | 应用于可交互行。 | `Modifier` | 否 |
| `summary` | `String?` | 标题下方的可选摘要文本。 | `null` | 否 |
| `startAction` | `(@Composable () -> Unit)?` | 标题块之前的可选前导内容。 | `null` | 否 |
| `endActions` | `(@Composable RowScope.() -> Unit)?` | 标题块之后的可选尾部内容。 | `null` | 否 |
| `bottomAction` | `(@Composable () -> Unit)?` | 渲染在行下方的可选内容。 | `null` | 否 |
| `onClick` | `(() -> Unit)?` | 可选激活回调;null 保持行为普通行。 | `null` | 否 |
| `onClickLabel` | `String?` | 描述行操作的可选无障碍标签。 | `null` | 否 |
| `role` | `Role?` | 为行宣布的可选语义角色。 | 交互时为 `Role.Button` | 否 |
| `holdDownState` | `Boolean` | 为 true 时强制按下视觉状态。 | `false` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互。 | `true` | 否 |
| `colors` | `ElegantBasicComponentColors` | 主题化状态颜色。 | `ElegantBasicComponentDefaults.colors()` | 否 |
| `insideMargin` | `PaddingValues` | 行内边距。 | `ElegantBasicComponentDefaults.InsideMargin` | 否 |
| `content` | `@Composable () -> Unit` | 可选的标准标题与摘要块替代内容。 | `{}` | 否 |

## 进阶用法

### 自定义内容块

用任意组合函数替换标准文本块:

```kotlin
ElegantBasicComponent(
    onClick = { },
    content = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("自定义块", modifier = Modifier.weight(1f))
            ElegantBadge(text = "新增")
        }
    },
)
```

### 底部辅助内容

```kotlin
ElegantBasicComponent(
    title = "存储",
    summary = "管理空间占用",
    onClick = { },
    bottomAction = {
        Text(
            text = "已用 42.5 GB / 128 GB",
            style = ElegantTheme.typography.bodyMedium,
            color = ElegantTheme.colors.textTertiary,
        )
    },
)
```

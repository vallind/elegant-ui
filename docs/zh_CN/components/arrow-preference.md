# ArrowPreference

`ElegantArrowPreference` 是设置风格的导航行,用于跳转或进入另一个页面。整行都是交互目标:点击标题、辅助文本或尾部箭头都会触发 `onClick`。它渲染 `labelMedium` 标题与可选的 `bodyMedium` 辅助行、指向逻辑方向(即起始方向)的尾部箭头、hovered 与 pressed 容器反馈,以及从起始边缘内缩 16dp 的可选底部分割线。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=arrow-preference" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.preference.ElegantArrowPreference
import com.elegant.compose.ui.preference.ElegantArrowPreferenceColors
import com.elegant.compose.ui.preference.ElegantArrowPreferenceDefaults
```

## 基本用法

箭头偏好行需要 `title` 与 `onClick`。行保持 48dp 最小交互高度;当布局为从右到左时,尾部箭头水平镜像。

```kotlin
ElegantArrowPreference(
    title = "账户",
    onClick = { openSettings("account") },
)

ElegantArrowPreference(
    title = "通知",
    onClick = { openSettings("notifications") },
    supportingText = "管理提醒偏好",
    showDivider = false,
)
```

## 组件状态

行会播报 `Role.Button` 与禁用状态。整行可点击激活,包括标题、辅助文本与尾部箭头。

状态优先级:disabled、pressed、hovered、resting。禁用行保持 resting 容器,并将标题切换为禁用颜色。

```kotlin
ElegantArrowPreference(
    title = "通用",
    onClick = { openSettings("general") },
    supportingText = "语言、地区与外观",
)

ElegantArrowPreference(
    title = "外观",
    onClick = { openSettings("appearance") },
    supportingText = "主题、密度与字体大小",
)

ElegantArrowPreference(
    title = "隐私",
    onClick = {},
    enabled = false,
    supportingText = "已被管理员锁定",
)
```

## 属性

### ElegantArrowPreference 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `title` | `String` | 以 `labelMedium` 显示的行标题 | - | 是 |
| `onClick` | `() -> Unit` | 行接受点击时调用的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到交互行的修饰符 | `Modifier` | 否 |
| `supportingText` | `String?` | 标题之下可选显示的辅助文本 | `null` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互 | `true` | 否 |
| `colors` | `ElegantArrowPreferenceColors` | 主题感知的状态颜色 | `ElegantArrowPreferenceDefaults.colors()` | 否 |
| `showDivider` | `Boolean` | 是否绘制内缩 16dp 的底部分割线 | `true` | 否 |

### ElegantArrowPreferenceDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp 最小交互行高度 |
| `colors()` | `ElegantArrowPreferenceColors` | Light/Dark 主题感知颜色 |

### ElegantArrowPreferenceColors

`ElegantArrowPreferenceColors` 包含 resting 容器与标题颜色、辅助文本、分割线与箭头颜色、禁用标题颜色,以及 hovered 与 pressed 容器色调。应先调用 `ElegantArrowPreferenceDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 卡片上的设置组

在卡片表面堆叠行,并让行分割线绘制分隔。

```kotlin
ElegantCard(
    modifier = Modifier.fillMaxWidth(),
) {
    Column {
        ElegantArrowPreference(
            title = "个人资料",
            onClick = { openSettings("profile") },
            supportingText = "姓名、头像与联系方式",
        )
        ElegantArrowPreference(
            title = "安全",
            onClick = { openSettings("security") },
            supportingText = "密码与两步验证",
        )
        ElegantArrowPreference(
            title = "关于",
            onClick = { openSettings("about") },
            showDivider = false,
        )
    }
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantArrowPreferenceDefaults.colors()

ElegantArrowPreference(
    title = "工作区",
    onClick = { openSettings("workspace") },
    colors = baseColors.copy(
        titleColor = Color(0xFF147D64),
        arrowColor = Color(0xFF6E727A),
    ),
    supportingText = "成员、套餐与账单",
)
```

# Drawer

`ElegantDrawer` 是模态浮层,侧边面板会在调暗的遮罩层上方滑入。它适用于应用导航、筛选器,以及必须停留在当前屏幕内的设置。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=drawer" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.drawer.ElegantDrawer
import com.elegant.compose.ui.drawer.ElegantDrawerColors
import com.elegant.compose.ui.drawer.ElegantDrawerDefaults
import com.elegant.compose.ui.drawer.ElegantDrawerPlacement
```

## 基本用法

Drawer 是受控组件:传入 `visible` 控制显示,`onDismissRequest` 接收每一次关闭请求。面板默认宽度为 280dp,在渐显遮罩层上方滑入,内容支持垂直滚动。

```kotlin
var visible by remember { mutableStateOf(false) }

ElegantButton(onClick = { visible = true }) {
    Text("打开导航")
}

ElegantDrawer(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    Column {
        Text("收件箱")
        Text("星标")
        Text("已发送")
    }
}
```

## 位置

`ElegantDrawerPlacement` 选择逻辑边缘:`Start` 在 LTR 布局中将面板绘制在左边缘,RTL 中镜像到右侧;`End` 相反。

```kotlin
ElegantDrawer(
    visible = visible,
    onDismissRequest = { visible = false },
    placement = ElegantDrawerPlacement.End,
) {
    Text("End 抽屉")
}
```

## 组件状态

关闭方式:点击遮罩层、按下 Escape,或按下系统返回键(Android)都会触发 `onDismissRequest`;将 `visible` 设为 false 可编程关闭抽屉。

焦点:显示期间,抽屉将键盘焦点捕获在面板内部,并在抽屉关闭后将其恢复到调用方窗口。

面板位于抬升表面之上,使用主文本颜色并投射中等阴影。当内容超出可用高度时,内容会垂直滚动;抽屉本身没有 disabled 状态,因为启用行为由触发按钮负责。

```kotlin
ElegantDrawer(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    Column {
        repeat(20) { index ->
            Text("条目 $index")
        }
    }
}
```

## 属性

### ElegantDrawer 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `visible` | `Boolean` | 是否显示抽屉 | - | 是 |
| `onDismissRequest` | `() -> Unit` | 点击遮罩层、Escape、返回键或编程关闭时触发 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到抽屉面板的修饰符 | `Modifier` | 否 |
| `placement` | `ElegantDrawerPlacement` | 面板滑入的逻辑边缘 | `ElegantDrawerPlacement.Start` | 否 |
| `width` | `Dp` | 面板宽度 | `ElegantDrawerDefaults.Width` | 否 |
| `colors` | `ElegantDrawerColors` | 主题感知的遮罩、容器与内容颜色 | `ElegantDrawerDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | 抽屉面板内容 | - | 是 |

### ElegantDrawerPlacement 可选值

| 值 | 行为 |
| --- | --- |
| `Start` | 逻辑起始边缘;LTR 在左,RTL 在右 |
| `End` | 逻辑结束边缘;LTR 在右,RTL 在左 |

### ElegantDrawerDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Width` | `Dp` | 280dp 默认面板宽度 |
| `ScrimAlpha` | `Float` | 遮罩层 0.4 常驻透明度 |
| `AnimationDurationMillis` | `Int` | 强调 220ms 滑入与淡入时长 |
| `colors()` | `ElegantDrawerColors` | 主题感知的遮罩、容器与内容颜色 |

### ElegantDrawerColors

`ElegantDrawerColors` 包含遮罩叠加色、面板容器色,以及通过 `LocalContentColor` 提供给面板的内容色。应先调用 `ElegantDrawerDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 导航抽屉组合

组合 `ElegantAvatar`、`ElegantDivider` 与文本条目,构建带个人资料头部的导航抽屉。

```kotlin
ElegantDrawer(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ElegantAvatar(name = "Maya Chen", initials = "MC")
            Column(Modifier.padding(start = ElegantSpacing.lg)) {
                Text("Maya Chen")
                Text("maya@elegant.com")
            }
        }
        ElegantDivider(Modifier.padding(vertical = ElegantSpacing.md))
        Text("收件箱")
        Text("星标")
        Text("已发送")
    }
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantDrawerDefaults.colors()

ElegantDrawer(
    visible = visible,
    onDismissRequest = { visible = false },
    colors = baseColors.copy(
        containerColor = Color(0xFF6C4EFF),
        contentColor = Color.White,
    ),
) {
    Text("自定义表面")
}
```

# Floating Action Button

`ElegantFloatingActionButton` 是 Elegant UI 中用于屏幕主要操作的圆形、抬升式操作组件。它提供标准与紧凑两种尺寸、指针悬停、带克制缩放的触控按压、主题启用时的键盘焦点环以及主题感知的禁用颜色。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=floating-action-button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.floatingactionbutton.ElegantFloatingActionButton
import com.elegant.compose.ui.floatingactionbutton.ElegantFloatingActionButtonColors
import com.elegant.compose.ui.floatingactionbutton.ElegantFloatingActionButtonDefaults
```

## 基本用法

悬浮操作按钮用于屏幕的主要操作，通常锚定在内容上方：

```kotlin
ElegantFloatingActionButton(
    onClick = { /* 处理点击事件 */ },
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "添加",
    )
}
```

## 组件状态

悬停、按压和键盘焦点反馈会通过共享交互源自动解析。悬停时容器色发生偏移，按压时容器色沉淀并在固定触控区域内应用克制缩放，主题启用焦点环时,键盘焦点会在圆形周围绘制焦点环。

### 禁用状态

```kotlin
ElegantFloatingActionButton(
    onClick = { /* 处理点击事件 */ },
    enabled = false,
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "添加",
    )
}
```

## 属性

### ElegantFloatingActionButton 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `onClick` | `() -> Unit` | FAB 接受点击时触发的回调 | - | 是 |
| `modifier` | `Modifier` | 应用于固定尺寸触控区域容器的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | FAB 是否可以接受用户交互 | `true` | 否 |
| `compact` | `Boolean` | 是否使用紧凑的 40dp 圆形尺寸 | `false` | 否 |
| `colors` | `ElegantFloatingActionButtonColors` | 主题感知的默认、悬停、按压、聚焦和禁用颜色 | `ElegantFloatingActionButtonDefaults.colors()` | 否 |
| `interactionSource` | `MutableInteractionSource?` | 用于观察悬停、按压和焦点的可选提升交互源 | `null` | 否 |
| `content` | `@Composable () -> Unit` | 居中显示在 FAB 内的可组合内容 | - | 是 |

### ElegantFloatingActionButtonDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Size` | `Dp` | 标准 56dp 圆形尺寸 |
| `CompactSize` | `Dp` | 紧凑 40dp 圆形尺寸 |
| `AnimationDurationMillis` | `Int` | 标准状态过渡时长 |
| `PressedScale` | `Float` | 克制的按压缩放比例，保留固定触控目标 |
| `colors()` | `ElegantFloatingActionButtonColors` | 返回主题感知的 FAB 颜色 |

### ElegantFloatingActionButtonColors

`ElegantFloatingActionButtonColors` 包含默认、悬停、按压、聚焦和禁用状态下的容器色与内容色，以及焦点环边框色。应先调用 `ElegantFloatingActionButtonDefaults.colors()`，再通过 `copy(...)` 只覆盖产品明确支持的视觉值。

## 进阶用法

### 紧凑型悬浮操作按钮

在密集表面或工具栏中使用紧凑尺寸：

```kotlin
ElegantFloatingActionButton(
    onClick = { /* 处理点击事件 */ },
    compact = true,
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "添加",
    )
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantFloatingActionButtonDefaults.colors()

ElegantFloatingActionButton(
    onClick = { /* 处理点击事件 */ },
    colors = baseColors.copy(
        containerColor = Color(0xFF0F766E),
        pressedContainerColor = Color(0xFF115E59),
    ),
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "添加",
    )
}
```

# 主题控制器

`ElegantThemeController` 为 Elegant UI 带来类 Monet 的动态色彩主题。单一种子颜色即可派生完整的 `ElegantColors` 调色板——交互色调、表面、文本、边框与状态色——派生过程纯粹且确定，在 Android、Desktop JVM 与 Web/Wasm 上行为完全一致。派生仅使用纯 Kotlin 实现，不依赖任何平台颜色 API，且现有 `ElegantColors` 模型保持不变，所有组件无需改动即可继续工作。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=theme-controller" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.theme.ElegantTheme
import com.elegant.compose.ui.theme.ElegantThemeController
import com.elegant.compose.ui.theme.deriveElegantColors
import androidx.compose.ui.graphics.Color
```

## 基本用法

### 从关键色派生

向 `ElegantTheme` 传入 `keyColor`，完整调色板将自动从种子颜色派生：

```kotlin
ElegantTheme(
    keyColor = Color(0xFF6C4EFF),
) {
    // 所有组件都会解析为从紫色种子派生的颜色
}
```

### 主题控制器

`ElegantThemeController` 是一个轻量状态持有者，暴露单个种子颜色派生的浅色与深色调色板：

```kotlin
val controller = ElegantThemeController(keyColor = Color(0xFF147D64))

ElegantTheme(
    darkTheme = true,
    colors = controller.darkColors(),
) {
    // 绿色派生的深色调色板
}
```

## 组件状态

### 浅色与深色调色板

深色调色板使用深色表面、浅色文本与提亮的强调状态色；浅色调色板使用浅色表面、深色文本与加深的强调状态色：

```kotlin
ElegantTheme(
    keyColor = Color(0xFFB45309),
    darkTheme = true,
) {
    // 橙色派生的深色调色板
}
```

### 交互色调

悬停、按压与焦点色调均由关键色派生：悬停将种子提亮 `0.06`，按压加深 `0.10`，焦点环提亮 `0.30`。禁用与加载状态与内置调色板一样，继续由派生角色解析。

## 属性

### ElegantTheme 属性

`keyColor` 重载委托给现有 `ElegantTheme` 函数，原始签名保持不变。

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `keyColor` | `Color` | 用于派生完整调色板的种子颜色 | - | 是 |
| `darkTheme` | `Boolean` | 是否派生浅色或深色调色板 | `isSystemInDarkTheme()` | 否 |
| `typography` | `ElegantTypography` | 提供给内容的排版角色 | `DefaultElegantTypography` | 否 |
| `content` | `@Composable () -> Unit` | 使用派生调色板渲染的内容 | - | 是 |

### ElegantThemeController 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `keyColor` | `Color` | 控制器保存的种子颜色 | - | 是 |
| `lightColors()` | `ElegantColors` | 为浅色主题派生的调色板 | - | 否 |
| `darkColors()` | `ElegantColors` | 为深色主题派生的调色板 | - | 否 |

### deriveElegantColors 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `keyColor` | `Color` | 调色板派生所依据的种子颜色 | - | 是 |
| `darkTheme` | `Boolean` | 是否派生深色调色板 | - | 是 |

## 进阶用法

### 使用派生颜色的自定义调色板

`deriveElegantColors` 是纯函数：可在组合之外调用、缓存结果，或传入现有 `colors` 参数以获得完全控制：

```kotlin
val derived = deriveElegantColors(
    keyColor = Color(0xFF147D64),
    darkTheme = false,
)

ElegantTheme(
    colors = derived,
) {
    // 显式调色板，在所有平台上结果一致
}
```

### 派生规则

- `interactivePrimary` 即关键色；悬停提亮 `0.06`，按压加深 `0.10`，焦点环提亮 `0.30`。
- 关键色亮度低于 `0.45` 时 `textInverse` 为白色，否则为近黑色（`#111216`）。
- 表面保持种子色相、低饱和度与每个主题固定的中性亮度。
- 文本与边框为纯中性灰，亮度按主题固定。
- 状态色固定色相：绿 `150`、琥珀 `45`、红 `350`；饱和度保留种子饱和度并保底 `0.45`；亮度浅色主题为 `0.33`、深色主题为 `0.60`；`onStatus*` 颜色按对比度解析。

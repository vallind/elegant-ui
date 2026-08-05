# 主题控制器

`ElegantThemeController` 为 Elegant UI 带来类 Monet 的动态色彩主题。单一种子颜色通过 Material 3 动态色彩算法（HCT + 色调方案）派生完整的 `ElegantColors` 调色板，并映射到交互、表面、文本、边框与状态角色上。控制器同时暴露固定调色板，一个状态持有者即可在内置与派生配色方案之间切换；修改其任意属性都会重组主题。在 Android 上 `Monet*` 模式可读取系统壁纸调色板，而 Desktop JVM 与 Web/Wasm 从种子或固定回退色派生。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=theme-controller" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.foundation.theme.ElegantColorSchemeMode
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.foundation.theme.ElegantThemeColorSpec
import com.elegant.compose.ui.foundation.theme.ElegantThemeController
import com.elegant.compose.ui.foundation.theme.ElegantThemePaletteStyle
import androidx.compose.ui.graphics.Color
```

## 基本用法

### 使用关键色的 Monet 模式

向 `ElegantThemeController` 传入 `keyColor` 与 `Monet*` 模式，再交给 `ElegantTheme`；完整调色板将通过 Material 3 算法从种子颜色派生：

```kotlin
val controller = remember {
    ElegantThemeController(
        colorSchemeMode = ElegantColorSchemeMode.MonetSystem,
        keyColor = Color(0xFF6C4EFF),
    )
}

ElegantTheme(controller = controller) {
    // 所有组件都会解析为从紫色种子派生的颜色
}
```

### 兼容构造函数

`ElegantThemeController(keyColor)` 构造函数保持原有 HSL 推导契约：它即时推导浅色与深色调色板，并以 `System` 模式启动：

```kotlin
val controller = remember { ElegantThemeController(keyColor = Color(0xFF147D64)) }

ElegantTheme(controller = controller) {
    // 绿色派生的浅色或深色调色板跟随系统外观
}
```

## 组件状态

### 浅色与深色调色板

`Monet*` 模式跟随系统外观（`MonetSystem`）或固定使用某套色板（`MonetLight`、`MonetDark`）；显式的 `isDark` 可在 `System` 与 `MonetSystem` 模式中覆盖系统外观：

```kotlin
ElegantTheme(
    controller = remember {
        ElegantThemeController(
            colorSchemeMode = ElegantColorSchemeMode.MonetDark,
            keyColor = Color(0xFFB45309),
        )
    },
) {
    // 橙色派生的深色调色板
}
```

### 交互色调

交互角色映射自方案的 primary 及其容器色；悬停、按压与焦点色调由其基础角色使用与种子推导相同的 HSL 位移派生——悬停将基础色提亮 `0.06`，按压加深 `0.10`，焦点环提亮 `0.30`。表面悬停将 `surfaceDefault` 提亮 `0.04`，下陷表面将其加深 `0.05`。禁用与加载状态与内置调色板一样，继续由派生角色解析。

## 属性

### ElegantTheme 属性

`controller` 重载通过 `ElegantThemeController.currentColors()` 解析调色板；原有 `ElegantTheme` 函数签名保持不变。

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `controller` | `ElegantThemeController` | 解析当前调色板的状态持有者 | - | 是 |
| `typography` | `ElegantTypography` | 提供给内容的排版角色 | `DefaultElegantTypography` | 否 |
| `content` | `@Composable () -> Unit` | 使用解析调色板渲染的内容 | - | 是 |

### ElegantThemeController 属性

所有属性都由 compose 状态支撑；对其中任意一项赋值都会重组主题。

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `colorSchemeMode` | `ElegantColorSchemeMode` | 当前调色板的解析方式 | `System` | 否 |
| `lightColors` | `ElegantColors` | 浅色外观使用的固定调色板 | `ElegantLightColors` | 否 |
| `darkColors` | `ElegantColors` | 深色外观使用的固定调色板 | `ElegantDarkColors` | 否 |
| `keyColor` | `Color?` | `Monet*` 模式的种子颜色；`null` 时交由平台调色板 | `null` | 否 |
| `colorSpec` | `ElegantThemeColorSpec` | 派生使用的 Material 色彩规范 | `Spec2021` | 否 |
| `paletteStyle` | `ElegantThemePaletteStyle` | 派生使用的调色板风格 | `TonalSpot` | 否 |
| `isDark` | `Boolean?` | 显式深色模式覆盖；`null` 跟随系统外观 | `null` | 否 |
| `currentColors()` | `ElegantColors` | 按当前模式解析活动调色板的 `@Composable` 方法 | - | 否 |

### ElegantColorSchemeMode 取值

| 值 | 说明 |
| --- | --- |
| `System` | 使用固定调色板跟随系统外观 |
| `Light` | 始终使用浅色固定调色板 |
| `Dark` | 始终使用深色固定调色板 |
| `MonetSystem` | 跟随系统外观，从种子或平台调色板派生 |
| `MonetLight` | 从种子或平台调色板派生浅色调色板 |
| `MonetDark` | 从种子或平台调色板派生深色调色板 |

### ElegantThemePaletteStyle 取值

| 值 | 说明 |
| --- | --- |
| `TonalSpot` | 基线色调点方案 |
| `Neutral` | 低色度中性方案 |
| `Vibrant` | 高色度鲜艳方案 |
| `Expressive` | 表现力色调偏移方案 |
| `Rainbow` | 彩虹多色相方案 |
| `FruitSalad` | 水果沙拉互补色相方案 |
| `Monochrome` | 完全单色方案 |
| `Fidelity` | 保留种子色相的高保真方案 |
| `Content` | 面向内容驱动调色板的内容方案 |

### ElegantThemeColorSpec 取值

| 值 | 说明 |
| --- | --- |
| `Spec2021` | Material 色彩规范 2021 修订版 |
| `Spec2025` | 2025 修订版；`TonalSpot`、`Neutral`、`Vibrant`、`Expressive` 支持，其余风格降级为 `Spec2021` |

## 进阶用法

### 平台动态颜色

当 `keyColor = null` 时，`Monet*` 模式向平台请求其调色板。Android 读取壁纸调色板——API 33+ 使用调色板覆盖 JSON，API 31–32 使用系统强调色资源；更低版本使用固定种子（`#6750A4`）。Desktop JVM 与 Web/Wasm 无法访问壁纸，始终回退到固定种子：

```kotlin
ElegantTheme(
    controller = remember {
        ElegantThemeController(colorSchemeMode = ElegantColorSchemeMode.MonetSystem)
    },
) {
    // Android：系统壁纸调色板；Desktop/Web：固定回退种子
}
```

### 调色板风格与色彩规范

`paletteStyle` 选择九种 Material 方案之一；色彩规范修订版仅在方案支持时生效，否则优雅降级。调色板派生纯粹且确定，因此相同的种子、规范、风格与外观在所有平台上产生相同的 `ElegantColors`。

### 使用 deriveElegantColors 的纯推导

原有 HSL 推导仍以纯函数形式可用：可在组合之外调用、缓存结果，或传入现有 `colors` 参数以获得完全控制：

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

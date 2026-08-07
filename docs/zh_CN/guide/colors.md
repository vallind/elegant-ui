# 颜色

`ElegantColors` 是每个组件消费的语义颜色模型。组件从不硬编码原始颜色,而是解析主题角色,保证亮色与暗色色板一致。

## 语义角色

| 分组 | 角色 | 用途 |
| :--- | :--- | :--- |
| 画布 | `backgroundCanvas`、`backgroundSubtle` | 应用与页面背景 |
| 表面 | `surfaceDefault`、`surfaceRaised`、`surfaceSunken`、`surfaceHover` | 容器、卡片、浮起面板、按下行 |
| 文本 | `textPrimary`、`textSecondary`、`textTertiary`、`textInverse` | 内容层级与主色上的反色文本 |
| 边框 | `borderDefault`、`borderStrong` | 细线与强分隔线 |
| 交互 | `interactivePrimary`、`interactivePrimaryPressed`、`interactivePrimaryHover` | 主操作在各交互状态下的颜色 |
| 焦点 | `focusRing` | 键盘焦点可见性(通过 `ElegantTheme(focusRingEnabled = true)` 开启) |
| 状态 | `statusPositive`、`statusWarning`、`statusCritical` + `onStatus*` | 语义反馈色 |

## 读取角色

```kotlin
Text(
    text = "主要内容",
    color = ElegantTheme.colors.textPrimary,
)
```

## 状态解析

角色按固定优先级解析:禁用或过渡锁定、按下或拖拽、键盘焦点、指针悬停、静止。选中、勾选、错误、加载、展开等语义状态与交互视觉叠加。

## 无障碍

焦点环必须在亮色与暗色主题中都可见,且不能只依赖颜色。文本与交互表面的对比度必须满足两套色板的基础要求。

## 动态色板

`ElegantThemeController` 从一个种子色推导完整角色集。见[主题](./theme)。

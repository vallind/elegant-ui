# 平台支持

Elegant UI 已按 Kotlin Multiplatform 组织工程，但不会把“可迁移”误写成“已经支持”。

| 平台 | 状态 | Source set | 验收要求 |
| --- | --- | --- | --- |
| Android | 已支持 | `commonMain` + `androidMain` | 干净 CI、Sample APK、真机验收 |
| Desktop JVM | 计划中 | 未来 `desktopMain` | 编译、桌面 Sample、测试、发布变体 |
| iOS | 计划中 | 未来 `iosMain` | 模拟器/真机构建、无障碍检查、发布变体 |
| Web/Wasm | 计划中 | 未来 `wasmJsMain` | 浏览器构建、键盘/无障碍检查、发布变体 |

## common-first 不等于已经支持多平台

只要 API 与行为能够安全共享，组件就优先放在 `commonMain`。只有某个平台已经具备 target、CI、Sample、测试、文档和发布产物时，才可以标记为已支持。

## 公共 API 边界

公共组件签名不得暴露 `Context`、`Activity`、`Drawable` 或 `android.graphics` 等 Android 平台类型。确实需要 Android 能力时，应在 `androidMain` 中实现，并通过窄接口与 common 层连接。

## 当前兼容承诺

`0.x` 阶段只对 Android 提供兼容承诺。未来平台应复用相同组件名和核心参数，平台差异通过新增 API 或范围明确的适配器提供。

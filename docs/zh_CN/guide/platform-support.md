# 平台支持

Elegant UI 是面向三个正式目标的 Compose Multiplatform 组件库。

| 平台 | 状态 | 组件库 target | 验收要求 |
| --- | --- | --- | --- |
| Android | 已支持 | `android` | CI 通过、Sample APK、触控与无障碍检查、真机验收 |
| Desktop JVM | 已支持 | `jvm("desktop")` | CI 通过、可运行桌面示例、键盘/鼠标/焦点验收 |
| Web/Wasm | 已支持 | `wasmJs` | CI 通过、浏览器分发包、键盘/焦点/响应式验收 |
| Web/JS | 已支持 | `js` (IR, browser) | CI 通过、浏览器分发包、键盘/焦点/响应式验收 |
| iOS | 不在当前范围 | 无 | 不提供源码集、发布产物、示例或兼容承诺 |

## 共享 API 契约

公开组件、Token、状态模型、Defaults 与大部分交互行为位于 `commonMain`。只有无法通过通用 Compose API 表达的能力，才能进入平台源码集。

公开组件签名不得暴露 Android 类型、Swing/AWT 类型、浏览器 DOM 对象或其他平台类型。平台集成必须通过窄接口隔离。

## 平台行为

三个目标保持一致的公开 API 与语义状态模型，但输入方式和系统集成可以适配：

- Android：触控、硬件键盘、TalkBack、密度与字体缩放。
- Desktop JVM：鼠标、键盘、焦点遍历、窗口缩放与高 DPI。
- Web/Wasm：键盘、指针、浏览器焦点、视口缩放与支持 WasmGC 的浏览器。
- Web/JS：在 JS Canvas 目标上支持键盘、指针、浏览器焦点与视口缩放。

组件若只在一个或两个正式目标上通过编译与验收，就不能视为完成。

# Elegant UI — 项目需求基线

> 状态：Android、Desktop JVM、Web/Wasm 三端架构已锁定；Button 进入三端 CI 与验收流程。

## 1. 项目定位

- **项目名称**：Elegant UI
- **项目类型**：全新的 Compose Multiplatform UI 组件库，不改造、不复制任何现有项目
- **技术栈**：Kotlin Multiplatform + Compose Multiplatform
- **正式目标**：Android API 24+、Desktop JVM、Web/Wasm
- **不在当前范围**：iOS
- **交付形态**：KMP Maven publication、Android AAR、Android Sample、Desktop distributable、Web/Wasm distribution、英文/简体中文 VitePress 文档网站、GitHub Pages
- **设计目标**：精美、优雅、高级、克制、现代、精致、可维护、可扩展
- **视觉关键词**：Apple HIG 的克制与空间感 × Linear 的清晰与效率 × Ant Design 的精细化 × Material 3 的系统性
- **参考资料用途**：仅提取工程规范、API 约定、组件工作流与质量标准，不继承原项目品牌、包名、组件视觉或源码

## 2. 首版范围

### Foundations

- Color tokens：原始色阶、语义色、组件色
- Typography：Display、Headline、Title、Body、Label
- Spacing：4dp 基础网格
- Radius：圆角层级与胶囊形态
- Elevation：阴影与层级
- Motion：时长、缓动、状态过渡
- Theme：Light / Dark

### Components V1（23 个）

#### 基础组件（7）

1. Button
2. IconButton
3. Avatar
4. Badge
5. Divider
6. Tag
7. Tooltip

#### 表单组件（6）

8. Input
9. Select
10. Checkbox
11. Switch
12. Radio
13. Slider

#### 内容组件（6）

14. Card
15. List
16. Table
17. EmptyState
18. Modal
19. Drawer

#### 导航组件（4）

20. Navbar
21. Sidebar
22. Tabs
23. Breadcrumb

## 3. API 与平台原则

- 公开组件、Token、状态、Defaults、Colors 与主要交互逻辑默认进入 `commonMain`
- 公共 API 不得暴露 Android、AWT/Swing、浏览器 DOM 等平台类型
- 参数顺序统一：必需参数 → Modifier → 状态开关 → 视觉参数 → 可选 slot → content lambda
- 复杂视觉状态组件提供 `XxxDefaults` 与不可变 `XxxColors`
- 状态至少覆盖 default、pressed、focused、selected、disabled、error（按组件适用）
- 组件具备 Compose Semantics、正确 Role、交互尺寸与无障碍对比度
- 公共 API 提供 KDoc；示例、文档与真实 API 一致
- Android、Desktop JVM、Web/Wasm 必须全部编译并通过对应交互验收
- iOS 不得在普通组件任务中加入

## 4. 工程结构

```text
:elegant-ui       发布组件库，Android + Desktop + Web/Wasm
:showcase         三端共享组件状态矩阵与 slug 注册
:sample           Android launcher
:desktop-sample   Desktop JVM launcher
:web-sample       Compose Web/Wasm launcher和文档 iframe 来源
```

## 5. 文档网站要求

每个组件同时交付：

- 英文与简体中文 Miuix 格式组件页
- API 参数表与 Defaults / Colors 说明
- 基础用法、状态、变体与进阶示例
- 真实 Compose Web/Wasm iframe Demo
- 双语侧边栏与组件索引

HTML 视觉仿真不再作为正式 Demo。文档站必须构建 `:web-sample` 并把实际 Wasm 分发包复制到 `docs/public/compose`。

## 6. 单组件完成定义

1. `commonMain` 组件源码、Token、KDoc 与测试
2. `:showcase` slug 注册与完整状态矩阵
3. Android、Desktop、Web 三端 launcher 可运行
4. 英文与中文组件文档
5. 双语索引和侧边栏
6. 真实 Compose Web iframe
7. KMP Maven publication 含三端变体
8. 两条 Actions 工作流成功
9. Android、Desktop、Web 平台验收记录完成

## 7. 执行顺序

1. 锁定需求、API、状态、视觉、无障碍与平台契约
2. 在 `commonMain` 实现组件与测试
3. 在 `:showcase` 注册同一三端 Demo
4. 构建 Android、Desktop、Web
5. 添加双语 Miuix 组件页与真实 Web iframe
6. 发布本地 KMP Maven 仓库
7. GitHub Actions 构建三端产物并部署网站
8. 完成三端验收并关闭里程碑

23 个组件严格逐个闭环；当前组件未通过三端验收前，不进入下一个组件。

## 8. 工程标识

- Gradle root：`elegant-ui`
- Library：`:elegant-ui`
- Shared showcase：`:showcase`
- Android：`:sample`
- Desktop：`:desktop-sample`
- Web：`:web-sample`
- Kotlin package：`com.elegant.compose.ui`
- 文档：英文 `docs/`，简体中文 `docs/zh_CN/`

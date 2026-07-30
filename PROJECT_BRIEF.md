# Elegant Compose — 项目需求基线

> 状态：Foundations 已锁定；Button 进入 GitHub Actions + 真机验收流程。

## 1. 项目定位

- **项目名称**：Elegant Compose（暂定，可在工程初始化前统一替换）
- **项目类型**：全新的 Android UI 组件库，不改造、不复制任何现有项目
- **技术栈**：Kotlin + Jetpack Compose
- **交付形态**：Android Library、示例应用、英文文档、简体中文文档
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

## 3. API 原则

- 所有组件优先使用语义 Token，禁止在组件内部散落硬编码颜色
- 参数顺序统一：必需参数 → Modifier → 状态开关 → 视觉参数 → content lambda
- 每个有复杂视觉状态的组件提供 `XxxDefaults` 与不可变 `XxxColors`
- 状态至少覆盖：default、pressed、focused、selected、disabled、error（按组件适用）
- 组件必须具备 Compose Semantics、正确 Role、触控尺寸与无障碍对比度
- 公共 API 需提供 KDoc；示例与文档 API 必须一致
- V1 仅使用 Android/Compose 能力；不引入其他现有组件库的品牌或实现

## 4. 文档要求

每个组件同时交付：

- 英文组件页
- 简体中文组件页
- API 参数表
- Defaults / Colors 说明
- 基础用法
- 状态与变体
- 无障碍说明
- 可运行 Demo

中英文页面保持章节与示例一一对应。

## 5. 单组件完成定义

每个组件只有同时完成以下事项才算完成：

1. 组件源码
2. 示例 Section
3. 示例应用注册
4. 文档 Demo
5. Demo 注册
6. 英文与中文组件文档
7. 英文与中文组件索引
8. 英文与中文侧边栏入口
9. 编译、格式化、测试与视觉检查

## 6. 执行顺序

1. **需求与视觉契约锁定**
2. **单组件 Compose 实现**
3. **示例应用与双语文档**
4. **推送独立 GitHub 仓库**
5. **GitHub Actions 构建 APK 与 AAR**
6. **真机安装、视觉与交互验收**
7. **按真机反馈修正后关闭组件里程碑**

23 个组件严格逐个闭环；当前组件未通过真机验收前，不进入下一个组件。Figma 用作视觉基准，但其 API 调用额度不再阻塞 Compose 实现与真机测试。

## 7. 暂定工程标识

- Gradle root project：`elegant-compose`
- Library module：`:elegant-ui`
- Demo module：`:sample`
- Kotlin package：`com.elegant.compose.ui`
- 文档目录：`docs/en/`、`docs/zh-CN/`

以上标识在 Step 2 结束前仍可整体重命名；进入 Step 3 后冻结。

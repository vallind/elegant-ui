# 组件总览

Elegant UI V1 共包含 83 个组件，分为四个类别。只有完成 Compose 实现与完整交付闭环的组件才会在网站中标记为可用。

## 基础组件

| 组件 | 用途 | 常见用法 | 状态 |
| --- | --- | --- | --- |
| [Button 按钮](./button) | 通过三种强调层级触发即时操作 | 表单提交与主操作 | 已完成 |
| [IconButton 图标按钮](./icon-button) | 触发紧凑且无障碍的图标操作 | 工具栏与紧凑辅助操作 | 已完成 |
| [Avatar 头像](./avatar) | 使用首字母或自定义内容表示人物或实体 | 用户资料与实体身份 | 已完成 |
| [Badge 徽标](./badge) | 显示状态或数量 | 未读数量与状态圆点 | 已完成 |
| [Divider 分割线](./divider) | 分隔内容区域 | 区块分隔与层级划分 | 已完成 |
| [Tag](./tag) | 标记或分类内容 | 内容分类与筛选 | 已完成 |
| [Tooltip](./tooltip) | 解释紧凑控件 | 图标说明与元素提示 | 已完成 |
| [ProgressIndicator](./progress-indicator) | 展示任务进度 | 加载与任务进度 | 已完成 |
| [Skeleton](./skeleton) | 预览加载中的内容 | 加载中的内容占位预览 | 已完成 |
| [Alert](./alert) | 呈现重要消息 | 页面级消息强调 | 已完成 |
| [AlertDialog](./alert-dialog) | 确认破坏性决策 | 破坏性操作确认流程 | 已完成 |
| [Snackbar](./snackbar) | 展示临时反馈 | 临时操作结果反馈 | 已完成 |
| [Toast](./toast) | 展示顶部反馈 | 顶部轻量反馈 | 已完成 |
| [Link](./link) | 以内联文本导航 | 行内导航与链接 | 已完成 |
| [Label](./label) | 标注表单字段 | 表单字段标签 | 已完成 |
| [Kbd](./kbd) | 标注键盘按键 | 键盘快捷键提示 | 已完成 |
| [Surface](./surface) | 分层底层容器 | 底层容器分层 | 已完成 |
| [Spinner](./spinner) | 展示不定进度加载环 | 不确定时长加载指示 | 已完成 |
| [FloatingToolbar](./floating-toolbar) | 浮动紧凑操作条 | 内容附近的快捷操作 | 已完成 |
| [ScrollBar](./scroll-bar) | 指示滚动位置 | 滚动位置指示 | 已完成 |
| [Toolbar](./toolbar) | 锚定内联操作条 | 行内操作条 | 已完成 |
| [TopAppBar](./top-app-bar) | 折叠页面大标题 | 可折叠大标题应用栏 | 已完成 |
| [SmallTitle](./small-title) | 标注设置分组 | 设置分组标题 | 已完成 |
| [BasicComponent 基础行](./basic-component) | 组合自定义设置行 | 自定义行式设置项 | 已完成 |
| [ScrollShadow](./scroll-shadow) | 淡化可滚动边缘 | 滚动位置阴影提示 | 已完成 |

## 表单组件

| 组件 | 用途 | 常见用法 | 状态 |
| --- | --- | --- | --- |
| [Input](./input) | 收集文本输入 | 表单填写与搜索 | 已完成 |
| [InputOtp](./input-otp) | 输入验证码 | 验证码输入 | 已完成 |
| [Autocomplete](./autocomplete) | 从过滤建议中选择 | 输入联想建议 | 已完成 |
| [InputGroup](./input-group) | 组合相关字段 | 分组字段组合 | 已完成 |
| [Checkbox](./checkbox) | 切换布尔选项 | 多选与协议勾选 | 已完成 |
| [Radio](./radio) | 从一组中选择一项 | 互斥单选 | 已完成 |
| [Textarea](./textarea) | 收集多行文本 | 多行文本输入 | 已完成 |
| [NumberField](./number-field) | 步进输入整数 | 带步进的数字输入 | 已完成 |
| [SearchBar](./search-bar) | 搜索与筛选 | 内容搜索与快速查找 | 已完成 |
| [RadioGroup](./radio-group) | 组合互斥选项 | 分组单选 | 已完成 |
| [CheckboxGroup](./checkbox-group) | 组合多选选项 | 分组多选 | 已完成 |
| [SwitchGroup](./switch-group) | 组合布尔选项 | 分组开关设置 | 已完成 |
| [TagGroup](./tag-group) | 组合可选中标签 | 成组可筛选标签 | 已完成 |
| [SwitchPreference](./switch-preference) | 切换设置行 | 带开关的设置行 | 已完成 |
| [CheckboxPreference](./checkbox-preference) | 勾选设置行 | 带复选框的设置行 | 已完成 |
| [RadioPreference](./radio-preference) | 选择设置行 | 带单选按钮的设置行 | 已完成 |
| [SliderPreference](./slider-preference) | 调节设置值 | 带滑块的设置行 | 已完成 |
| [ToggleButton](./toggle-button) | 集群切换选项 | 分段式互斥选择 | 已完成 |
| [ButtonGroup](./button-group) | 选择分段选项 | 成组操作按钮 | 已完成 |

| [Select](./select) | 从列表中选择一项 | 列表选项选择 | 已完成 |
| [Switch](./switch) | 切换布尔选项 | 开关设置 | 已完成 |
| [Slider](./slider) | 从范围中选取数值 | 音量与范围调节 | 已完成 |

## 内容组件

| 组件 | 用途 | 常见用法 | 状态 |
| --- | --- | --- | --- |
| [Card](./card) | 在表面中分组内容 | 内容分组与信息展示 | 已完成 |
| [List](./list) | 展示相关内容行 | 可滚动条目集合 | 已完成 |
| [EmptyState](./empty-state) | 解释空内容区域 | 空内容引导 | 已完成 |
| [Modal](./modal) | 在居中表面聚焦任务 | 聚焦任务的居中表面 | 已完成 |
| [BottomSheet](./bottom-sheet) | 从底部边缘滑出模态表面 | 分享面板与移动端操作 | 已完成 |

| [Drawer](./drawer) | 从边缘展开导航或筛选 | 侧边导航面板 | 已完成 |
| [Table](./table) | 展示表格数据 | 结构化数据展示 | 已完成 |
| [Pagination](./pagination) | 分页浏览长内容 | 分页内容浏览 | 已完成 |
| [Accordion](./accordion) | 折叠与展开区块 | 可展开内容区块 | 已完成 |
| [Disclosure](./disclosure) | 展开单个区块 | 行内可展开详情 | 已完成 |
| [Fieldset](./fieldset) | 分组表单字段 | 相关字段分组 | 已完成 |
| [ColorPicker](./color-picker) | 从精选色板中选择 | 主题取色 | 已完成 |
| [ColorPickerPanel](./color-picker-panel) | 从 HSV 空间选择 | 精细颜色编辑 | 已完成 |
| [Calendar](./calendar) | 从月历网格选择日期 | 日历浏览与排期 | 已完成 |
| [DatePicker](./date-picker) | 从弹出日历选择日期 | 单个日期选择 | 已完成 |
| [DateRangePicker](./date-range-picker) | 选择开始与结束日期 | 日期范围选择 | 已完成 |
| [NumberPicker](./number-picker) | 步进选择有界数值 | 时间与数量选择 | 已完成 |
| [Scaffold](./scaffold) | 组合栏与内容的外壳 | 应用外壳布局 | 已完成 |
| [CloseButton](./close-button) | 关闭表面 | 弹窗与面板关闭 | 已完成 |
| [PullToRefresh](./pull-to-refresh) | 下拉刷新可滚动内容 | 下拉刷新数据 | 已完成 |
| [Popover](./popover) | 浮动展示上下文操作 | 上下文锚定浮层 | 已完成 |
| [Menu](./menu) | 提供紧凑操作列表 | 操作与选项菜单 | 已完成 |
| [ListPopup](./list-popup) | 从锚定列表选择 | 带勾选列表的弹出层 | 已完成 |
| [CascadingMenu](./cascading-menu) | 提供多级菜单 | 层级菜单导航 | 已完成 |
| [Description](./description) | 展示键值行 | 键值详情列表 | 已完成 |
| [Meter](./meter) | 按阈值展示用量 | 等级与评分展示 | 已完成 |
| [FloatingActionButton](./floating-action-button) | 呈现主要操作 | 突出的快捷操作 | 已完成 |
| [Table](./table) | 展示表格数据 | 结构化数据展示 | 已完成 |

## 导航组件

| 组件 | 用途 | 常见用法 | 状态 |
| --- | --- | --- | --- |
| [Navbar](./navbar) | 锚定顶级导航 | 应用顶级导航 | 已完成 |
| [Sidebar](./sidebar) | 锚定次级导航 | 次级导航锚定 | 已完成 |
| [Tabs](./tabs) | 切换相关视图 | 视图间分类浏览 | 已完成 |
| [Breadcrumb](./breadcrumb) | 显示当前位置 | 路径位置追踪 | 已完成 |
| [NavigationBar](./navigation-bar) | 锚定底部导航 | 底部标签切换 | 已完成 |
| [NavigationRail](./navigation-rail) | 锚定侧边导航 | 侧边标签切换 | 已完成 |
| [ArrowPreference](./arrow-preference) | 进入设置行详情 | 可进入下一级的设置行 | 已完成 |

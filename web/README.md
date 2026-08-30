# 微光漫摄 · 二次元社团网站

这是一个使用 Vue 3 + Vite 构建的社团展示站。项目采用“战术终端 / 动画档案”视觉方向：深色控制台、编号导航、状态标识、青色信号线和珊瑚色警示色共同组成页面骨架。

当前版本优先完成界面和交互框架，活动数据仍可按需录入；部门页面已采用无图片的部门档案布局，只展示部门名称与简介。

## 快速开始

环境要求：Node.js 18+，npm 9+。

```bash
npm install
npm run dev
```

开发服务器启动后，打开终端显示的本地地址（通常是 `http://localhost:5173`）。

常用命令：

```bash
npm run build    # 构建生产文件到 dist/
npm run preview  # 预览已构建的 dist/
npm run format   # 使用 Prettier 格式化 CSS 和 src/
```

## 目录结构

```text
.
├─ index.html                 # Vite HTML 入口
├─ package.json               # 脚本和依赖
├─ vite.config.mjs            # Vite + Vue 插件配置
├─ css/
│  └─ styles.css              # 全局设计令牌、reset 和无障碍规则
├─ image/                     # 页面使用的图片资源
└─ src/
   ├─ main.js                 # 创建 Vue 应用并挂载全局 CSS
   ├─ App.vue                 # 应用外壳、hash 路由、主题和页面切换
   ├─ components/             # 可复用的展示组件（样式写在各自 SFC 的 <style> 中）
   │  ├─ SiteHeader.vue       # 顶部导航、主题切换、快捷操作
   │  ├─ SiteFooter.vue       # 页脚信息
   │  ├─ Eyebrow.vue          # 小型英文栏目标签
   │  ├─ SectionHeading.vue   # 区块标题
   │  ├─ DepartmentCard.vue   # 部门档案卡片
   │  └─ EventCard.vue        # 活动条目卡片
   ├─ data/                   # 静态内容
   │  ├─ club.js              # 社团名称、简介和统计信息
   │  ├─ departments.js       # 部门列表（名称、简介和档案编号）
   │  └─ events.js            # 活动列表（稳定 ID、日期、标题、地点和简介）
   └─ views/                  # 五个页面视图
      ├─ HomeView.vue         # 首页
      ├─ ClubView.vue         # 关于社团
      ├─ DepartmentsView.vue  # 部门档案
      ├─ EventsView.vue       # 活动日历
      └─ JoinView.vue         # 加入我们
```

## 页面和路由

项目没有使用 Vue Router，而是使用 URL hash 实现单页导航。`App.vue` 中的 `routeViews` 是唯一的路由映射表。

| Hash | 页面 | 顶栏编号 | 状态代码 |
| --- | --- | --- | --- |
| `#home` | 首页 / BASE CAMP | 01 | `INDEX` |
| `#club` | 关于社团 / PROFILE | 02 | `ABOUT` |
| `#works` | 部门档案 / DEPARTMENTS | 03 | `UNITS` |
| `#events` | 活动日历 / SCHEDULE | 04 | `EVENTS` |
| `#join` | 加入我们 / OPEN CALL | 05 | `JOIN` |

直接访问不存在的 hash 会回退到 `#home`。导航点击由 `SiteHeader` 发出 `navigate` 事件，`App.vue` 更新 hash 和当前视图；页面内部按钮也通过同一个事件切换页面。

## 核心运行机制

### 应用外壳：`src/App.vue`

- `route`：当前 hash 路由。
- `currentView`：根据路由选择要渲染的 Vue 组件。
- `routeMeta`：为顶部状态栏提供编号、页面标题和代码。
- `theme`：当前主题，值为 `day` 或 `night`。
- `readTheme()` / `toggleTheme()`：从 `localStorage` 读取和保存主题，键名为 `weiguang-theme`。
- `Transition` + GSAP：路由切换时执行淡入和水平位移动画，页面进入后再对重点元素做轻微位移动画。

### 主题

主题通过 `document.body.dataset.theme` 注入：

```html
<body data-theme="day">
```

全局 CSS 使用变量控制颜色。夜间主题覆盖 `--paper`、`--ink`、`--muted`、`--coral` 等变量，因此新增组件时应优先使用这些变量，不要写死页面背景和文字颜色。

### 响应式布局

- 桌面端：顶部横向 command deck 导航。
- `880px` 以下：导航变为可横向滚动的第二行，内容区取消顶部留白。
- `620px` 以下：缩小标题、状态栏和导航项，并将多列内容压缩为单列。

 样式遵循 Vue 单文件组件规范：组件和页面布局、响应式规则、动效都写在对应 `.vue` 文件的 `<style scoped>` 中；`App.vue` 的外壳和路由过渡使用普通 `<style>`，因为它们需要跨页面生效。`css/styles.css` 只负责字体、颜色变量、reset 和全局无障碍规则。新增组件时不要把局部选择器继续堆到全局 CSS。

## 数据模型

### 社团信息：`src/data/club.js`

```js
{
  name: String,
  englishName: String,
  tagline: String,
  intro: String,
  stats: [{ value: String, label: String }]
}
```

首页和关于页面直接读取 `club.intro`、`club.stats` 等字段。

### 部门：`src/data/departments.js`

```js
{
  id: String,        // 稳定的部门标识
  code: String,      // 档案编号，例如 GAME、VISUAL
  name: String,      // 部门名称
  intro: String      // 部门简介
}
```

`DepartmentsView.vue` 会过滤掉缺少 `name` 或 `intro` 的记录，再遍历渲染 `DepartmentCard`。页面不依赖图片字段；如暂无部门资料，会显示明确的空状态而不是渲染空卡片。

### 活动：`src/data/events.js`

```js
{
  id: String,       // 稳定且唯一的活动标识，用作 Vue 列表 key
  date: String,   // 日期数字
  title: String,  // 活动名称
  place: String,  // 地点
  brief: String    // 活动简介
}
```

首页展示前三条活动，活动日历展示全部活动。`id` 必须在活动之间保持唯一，不能使用日期作为 key，因为同一天可能有多个活动。填充或修改活动内容时只需更新数据文件，不需要修改卡片组件。

## 组件通信约定

- `App.vue` 向 `SiteHeader` 传入 `activeRoute`、`isNight`。
- `SiteHeader` 只负责发出 `navigate` 和 `toggle-theme`，不直接修改全局状态。
- 各个 view 通过 `defineEmits(["navigate"])` 将页面跳转请求交给 `App.vue`。
- `DepartmentCard` 和 `EventCard` 通过必填 prop 接收单条数据，保持无状态展示。

新增页面时，按以下顺序接入：

1. 在 `src/views/` 新建 `XxxView.vue`。
2. 在 `App.vue` 导入组件并加入 `routeViews`。
3. 在 `routeMeta` 增加页面元信息。
4. 在 `SiteHeader.vue` 的 `navItems` 增加导航项。
5. 如果页面需要进入动画，在 `animatePage()` 的 `focusSelector` 中增加选择器。

## 图片资源

页面当前使用 `image/1757438527327.png` 作为社团徽章，`image/qq.jpg` 作为加入页面海报。Vue 组件通过相对路径导入图片，替换资源时保持导入变量和 `alt` 文本同步更新。

## 开发注意事项

- 内容数据集中放在 `src/data/`，不要把大量文案直接复制到卡片组件中。
- 新增颜色、间距或断点时，优先扩展 `css/styles.css` 顶部的 CSS 变量；局部布局规则写入所属 Vue 文件的 `<style>`。
- 导航依赖 hash，刷新页面后仍能保留当前页面；不要把页面跳转改成普通链接而绕过 `navigate()`。
- 主题读取有 `try/catch`，这是为了兼容禁用 `localStorage` 的 `file://` 环境。
- `JoinView.vue` 中的提交状态目前只是本地 `submitted` ref，尚未连接后端或表单接口。
- `dist/` 是构建产物，不建议直接编辑；修改源码后重新执行 `npm run build`。

## 验证清单

提交前建议执行：

```bash
npm run build
```

然后在浏览器中检查：

- 五个 hash 页面都能通过顶部导航打开。
- 日间 / 夜间主题切换后刷新仍保持选择。
- 桌面端和窄屏端导航、卡片没有溢出。
- 部门或活动数据为空时，页面仍保持稳定布局；填入真实数据后卡片正常显示。

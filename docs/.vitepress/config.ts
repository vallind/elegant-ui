import { defineConfig } from 'vitepress'

const repository = 'https://github.com/vallind/elegant-ui'
const base = process.env.DOCS_BASE ?? '/elegant-ui/'

const englishSidebar = {
  '/guide/': [
    {
      text: 'Guide',
      items: [
        { text: 'Getting started', link: '/guide/getting-started' },
        { text: 'Installation', link: '/guide/installation' },
        { text: 'Local development', link: '/guide/local-development' },
        { text: 'Platform support', link: '/guide/platform-support' },
        { text: 'Design principles', link: '/guide/design-principles' },
      ],
    },
  ],
  '/components/': [
    {
      text: 'Components',
      items: [
        { text: 'Overview', link: '/components/' },
        { text: 'Button', link: '/components/button' },
        { text: 'IconButton', link: '/components/icon-button' },
      ],
    },
  ],
}

const chineseSidebar = {
  '/zh_CN/guide/': [
    {
      text: '指南',
      items: [
        { text: '快速开始', link: '/zh_CN/guide/getting-started' },
        { text: '安装与依赖', link: '/zh_CN/guide/installation' },
        { text: '本地联调', link: '/zh_CN/guide/local-development' },
        { text: '平台支持', link: '/zh_CN/guide/platform-support' },
        { text: '设计原则', link: '/zh_CN/guide/design-principles' },
      ],
    },
  ],
  '/zh_CN/components/': [
    {
      text: '组件',
      items: [
        { text: '总览', link: '/zh_CN/components/' },
        { text: 'Button 按钮', link: '/zh_CN/components/button' },
        { text: 'IconButton 图标按钮', link: '/zh_CN/components/icon-button' },
      ],
    },
  ],
}

export default defineConfig({
  title: 'Elegant UI',
  description: 'Refined Compose Multiplatform components for Android, Desktop JVM, and Web/Wasm.',
  base,
  cleanUrls: true,
  lastUpdated: true,
  head: [
    ['link', { rel: 'icon', type: 'image/svg+xml', href: `${base}logo.svg` }],
    ['meta', { name: 'theme-color', content: '#6d5dfc' }],
  ],
  locales: {
    root: {
      label: 'English',
      lang: 'en-US',
      themeConfig: {
        nav: [
          { text: 'Guide', link: '/guide/getting-started' },
          { text: 'Components', link: '/components/' },
          { text: 'GitHub', link: repository },
        ],
        sidebar: englishSidebar,
        editLink: {
          pattern: `${repository}/edit/main/docs/:path`,
          text: 'Edit this page on GitHub',
        },
        outline: {
          level: [2, 3],
          label: 'On this page',
        },
        langMenuLabel: 'Change language',
        sidebarMenuLabel: 'Menu',
        returnToTopLabel: 'Return to top',
      },
    },
    zh_CN: {
      label: '简体中文',
      lang: 'zh-CN',
      link: '/zh_CN/',
      title: 'Elegant UI',
      description: '面向 Android、Desktop JVM 与 Web/Wasm 的精致 Compose Multiplatform 组件库。',
      themeConfig: {
        nav: [
          { text: '指南', link: '/zh_CN/guide/getting-started' },
          { text: '组件', link: '/zh_CN/components/' },
          { text: 'GitHub', link: repository },
        ],
        sidebar: chineseSidebar,
        editLink: {
          pattern: `${repository}/edit/main/docs/:path`,
          text: '在 GitHub 上编辑此页',
        },
        outline: {
          level: [2, 3],
          label: '页面导航',
        },
        langMenuLabel: '切换语言',
        sidebarMenuLabel: '菜单',
        returnToTopLabel: '返回顶部',
        docFooter: {
          prev: '上一页',
          next: '下一页',
        },
        lastUpdated: {
          text: '最后更新',
        },
      },
    },
  },
  themeConfig: {
    logo: `${base}logo.svg`,
    siteTitle: 'Elegant UI',
    search: { provider: 'local' },
    socialLinks: [
      { icon: 'github', link: repository },
    ],
    footer: {
      message: 'Elegant Android components for Kotlin and Jetpack Compose.',
      copyright: 'Copyright © 2026 Elegant UI contributors',
    },
  },
})

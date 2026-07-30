import { readdir, readFile } from 'node:fs/promises'
import { dirname, join } from 'node:path'
import { fileURLToPath } from 'node:url'

const docsRoot = dirname(dirname(fileURLToPath(import.meta.url)))
const englishDir = join(docsRoot, 'components')
const chineseDir = join(docsRoot, 'zh_CN', 'components')
const demoDocument = await readFile(join(docsRoot, 'public', 'compose', 'index.html'), 'utf8')

const requiredGuideSlugs = [
  'getting-started',
  'installation',
  'local-development',
  'platform-support',
  'design-principles',
]


const componentSlugs = async (directory) =>
  (await readdir(directory))
    .filter((name) => name.endsWith('.md') && name !== 'index.md')
    .map((name) => name.replace(/\.md$/, ''))
    .sort()

const englishSlugs = await componentSlugs(englishDir)
const chineseSlugs = await componentSlugs(chineseDir)

const fail = (message) => {
  console.error(`Documentation validation failed: ${message}`)
  process.exitCode = 1
}

const assertHeadingsInOrder = (slug, locale, page, headings) => {
  let previous = -1
  for (const heading of headings) {
    const index = page.indexOf(`\n${heading}\n`)
    if (index < 0) {
      fail(`${slug} ${locale} page is missing required heading: ${heading}`)
      continue
    }
    if (index <= previous) {
      fail(`${slug} ${locale} page has an invalid heading order near: ${heading}`)
    }
    previous = index
  }
}

if (englishSlugs.join('\n') !== chineseSlugs.join('\n')) {
  fail(
    `component locale mismatch\nEnglish: ${englishSlugs.join(', ')}\nChinese: ${chineseSlugs.join(', ')}`,
  )
}

const config = await readFile(join(docsRoot, '.vitepress', 'config.ts'), 'utf8')
const englishIndex = await readFile(join(englishDir, 'index.md'), 'utf8')
const chineseIndex = await readFile(join(chineseDir, 'index.md'), 'utf8')

for (const slug of requiredGuideSlugs) {
  const englishPath = join(docsRoot, 'guide', `${slug}.md`)
  const chinesePath = join(docsRoot, 'zh_CN', 'guide', `${slug}.md`)

  let englishGuide
  let chineseGuide
  try {
    englishGuide = await readFile(englishPath, 'utf8')
    chineseGuide = await readFile(chinesePath, 'utf8')
  } catch {
    fail(`guide locale pair is missing for ${slug}`)
    continue
  }

  if (!config.includes(`link: '/guide/${slug}'`)) {
    fail(`English guide sidebar is missing /guide/${slug}`)
  }
  if (!config.includes(`link: '/zh_CN/guide/${slug}'`)) {
    fail(`Chinese guide sidebar is missing /zh_CN/guide/${slug}`)
  }

  const englishKotlinBlocks = (englishGuide.match(/```kotlin/g) ?? []).length
  const chineseKotlinBlocks = (chineseGuide.match(/```kotlin/g) ?? []).length
  if (englishKotlinBlocks !== chineseKotlinBlocks) {
    fail(
      `${slug} guide Kotlin example count differs: English=${englishKotlinBlocks}, Chinese=${chineseKotlinBlocks}`,
    )
  }
}

for (const requiredText of [
  'io.github.vallind:elegant-ui:0.1.0-SNAPSHOT',
  'elegant-ui-maven-repository',
  'only supported runtime target',
]) {
  const installation = await readFile(join(docsRoot, 'guide', 'installation.md'), 'utf8')
  const platformSupport = await readFile(join(docsRoot, 'guide', 'platform-support.md'), 'utf8')
  const combined = `${installation}\n${platformSupport}`
  if (!combined.includes(requiredText)) {
    fail(`English consumer/platform guides are missing required contract text: ${requiredText}`)
  }
}

for (const slug of englishSlugs) {
  const englishPage = await readFile(join(englishDir, `${slug}.md`), 'utf8')
  const chinesePage = await readFile(join(chineseDir, `${slug}.md`), 'utf8')

  if (!config.includes(`link: '/components/${slug}'`)) {
    fail(`English sidebar is missing /components/${slug}`)
  }
  if (!config.includes(`link: '/zh_CN/components/${slug}'`)) {
    fail(`Chinese sidebar is missing /zh_CN/components/${slug}`)
  }
  if (!englishIndex.includes(`](./${slug})`)) {
    fail(`English component index is missing ./${slug}`)
  }
  if (!chineseIndex.includes(`](./${slug})`)) {
    fail(`Chinese component index is missing ./${slug}`)
  }

  assertHeadingsInOrder(slug, 'English', englishPage, [
    '## Import',
    '## Basic Usage',
    '## Component States',
    '## Properties',
    '## Advanced Usage',
  ])
  assertHeadingsInOrder(slug, 'Chinese', chinesePage, [
    '## 引入',
    '## 基本用法',
    '## 组件状态',
    '## 属性',
    '## 进阶用法',
  ])

  const englishIframe = `src="../compose/index.html?id=${slug}"`
  const chineseIframe = `src="../../compose/index.html?id=${slug}"`
  if (!englishPage.includes('id="demoIframe"') || !englishPage.includes(englishIframe)) {
    fail(`${slug} English page is missing the Miuix-style demo iframe (${englishIframe})`)
  }
  if (!chinesePage.includes('id="demoIframe"') || !chinesePage.includes(chineseIframe)) {
    fail(`${slug} Chinese page is missing the Miuix-style demo iframe (${chineseIframe})`)
  }

  const englishIntroEnd = englishPage.indexOf('\n## Import\n')
  const englishDemoIndex = englishPage.indexOf('id="demoIframe"')
  const chineseIntroEnd = chinesePage.indexOf('\n## 引入\n')
  const chineseDemoIndex = chinesePage.indexOf('id="demoIframe"')
  if (englishDemoIndex < 0 || englishDemoIndex > englishIntroEnd) {
    fail(`${slug} English iframe must appear immediately before Import content`)
  }
  if (chineseDemoIndex < 0 || chineseDemoIndex > chineseIntroEnd) {
    fail(`${slug} Chinese iframe must appear immediately before 引入 content`)
  }

  if (!englishPage.includes('| Property Name | Type | Description | Default Value | Required |')) {
    fail(`${slug} English property table does not use the Miuix column contract`)
  }
  if (!chinesePage.includes('| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |')) {
    fail(`${slug} Chinese property table does not use the Miuix column contract`)
  }

  for (const forbidden of ['## Sizes', '## Accessibility', '## Physical-device checks']) {
    if (englishPage.includes(forbidden)) {
      fail(`${slug} English page contains non-Miuix top-level section: ${forbidden}`)
    }
  }
  for (const forbidden of ['## 尺寸', '## 无障碍', '## 真机检查项']) {
    if (chinesePage.includes(forbidden)) {
      fail(`${slug} Chinese page contains non-Miuix top-level section: ${forbidden}`)
    }
  }

  const demoRegistryPattern = new RegExp(`\\b${slug}\\s*:\\s*[A-Za-z_$][\\w$]*`)
  if (!demoRegistryPattern.test(demoDocument)) {
    fail(`${slug} is missing from docs/public/compose/index.html demoRenderers`)
  }

  const englishKotlinBlocks = (englishPage.match(/```kotlin/g) ?? []).length
  const chineseKotlinBlocks = (chinesePage.match(/```kotlin/g) ?? []).length
  if (englishKotlinBlocks !== chineseKotlinBlocks) {
    fail(
      `${slug} Kotlin example count differs: English=${englishKotlinBlocks}, Chinese=${chineseKotlinBlocks}`,
    )
  }
}

if (!process.exitCode) {
  console.log(
    `Documentation validation passed for ${englishSlugs.length} Miuix-format component page(s) and ${requiredGuideSlugs.length} bilingual guide pair(s).`,
  )
}

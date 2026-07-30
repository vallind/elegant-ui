import { readdir, readFile } from 'node:fs/promises'
import { dirname, join } from 'node:path'
import { fileURLToPath } from 'node:url'

const docsRoot = dirname(dirname(fileURLToPath(import.meta.url)))
const englishDir = join(docsRoot, 'components')
const chineseDir = join(docsRoot, 'zh_CN', 'components')

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

if (englishSlugs.join('\n') !== chineseSlugs.join('\n')) {
  fail(
    `component locale mismatch\nEnglish: ${englishSlugs.join(', ')}\nChinese: ${chineseSlugs.join(', ')}`,
  )
}

const config = await readFile(join(docsRoot, '.vitepress', 'config.ts'), 'utf8')
const englishIndex = await readFile(join(englishDir, 'index.md'), 'utf8')
const chineseIndex = await readFile(join(chineseDir, 'index.md'), 'utf8')

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

  const englishKotlinBlocks = (englishPage.match(/```kotlin/g) ?? []).length
  const chineseKotlinBlocks = (chinesePage.match(/```kotlin/g) ?? []).length
  if (englishKotlinBlocks !== chineseKotlinBlocks) {
    fail(
      `${slug} Kotlin example count differs: English=${englishKotlinBlocks}, Chinese=${chineseKotlinBlocks}`,
    )
  }
}

if (!process.exitCode) {
  console.log(`Documentation validation passed for ${englishSlugs.length} component page(s).`)
}

import { cp, mkdir, rm, stat } from 'node:fs/promises'
import { dirname, join, resolve } from 'node:path'
import { fileURLToPath } from 'node:url'

const docsRoot = dirname(dirname(fileURLToPath(import.meta.url)))
const repositoryRoot = resolve(docsRoot, '..')
const source = join(repositoryRoot, 'web-sample', 'build', 'dist', 'wasmJs', 'productionExecutable')
const destination = join(docsRoot, 'public', 'compose')

try {
  const sourceStats = await stat(source)
  if (!sourceStats.isDirectory()) throw new Error('not a directory')
} catch {
  console.error('Compose Web demo is missing. Run: gradle :web-sample:wasmJsBrowserDistribution')
  process.exit(1)
}

await rm(destination, { recursive: true, force: true })
await mkdir(destination, { recursive: true })
await cp(source, destination, { recursive: true })
console.log(`Copied Compose Web demo to ${destination}`)

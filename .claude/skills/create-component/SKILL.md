---
name: create-component
description: >-
  Create a new Compose Multiplatform UI component for Elegant UI, including
  shared source and tests, showcase registration, English and Simplified
  Chinese Miuix-format pages, component indexes, VitePress sidebars, and the
  supported Android/Desktop/Web wiring. Use for add component, new composable,
  continue the component library, 新建组件, 添加组件, 新组件, or 继续组件库.
---

# Create an Elegant UI component

Add one new component and its companion changes beyond the component file: shared tests, the showcase route, bilingual docs, website discovery, and the project-required target checks. The eight touch points are listed at the end.

AGENTS.md and current source are authoritative. This skill only gives the workflow and locations; if it conflicts with them, they win. Read references/completed-components.md to select repository-native references. Do not start another component in the same change. iOS, Figma, and screenshot evidence are outside this skill.

## Step 1: Gather requirements

Infer established details from the request and both component indexes. Ask only for a decision that would change the public API:

1. Component name — public ElegantXxx name and package
2. Family — action, display, status, container, input, overlay, or another family
3. Purpose — behavior and non-goals
4. Key parameters — state, callbacks, styles, sizes, slots, and content
5. Visual model — whether ElegantXxxDefaults, ElegantXxxColors, or another immutable model is needed

If the user only asks to continue, choose the first planned entry whose prerequisites are available in:

    docs/components/index.md
    docs/zh_CN/components/index.md

## Step 2: Read reference files by component type

Read the relevant API section of AGENTS.md, then select and read one completed component's source and contract test in full. Also read its showcase section and both docs pages.

| Component type | Starting reference | Key points |
| :--- | :--- | :--- |
| Text action | elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/button/ElegantButton.kt | controlled action, loading, styles, sizes, slots |
| Icon action | elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/iconbutton/ElegantIconButton.kt | accessible name, 48dp target, action parity |
| Identity/display | elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/avatar/ElegantAvatar.kt | fallback, semantics, custom content |
| Status/count/overlay | elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/badge/ElegantBadge.kt | tones, overflow, RTL placement, measurement |
| Drawn separator | elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/divider/ElegantDivider.kt | orientation, emphasis, stroke, semantics |
| New family | closest entry in references/completed-components.md | follow repository shape, define a new contract |

Use references for conventions, not for blindly copying dimensions, states, slots, or implementation.

## Step 3: Write source and tests

Create the shared implementation and meaningful contract tests here:

    elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/{package}/
    elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/{package}/

Follow AGENTS.md for API order, KDoc, tokens, state resolution, semantics, accessibility, compatibility, and platform boundaries. Implement in commonMain first; use platform source sets only when common Compose cannot express the behavior. Keep platform types out of the shared public API, use ElegantTheme values, and do not expose an unmodified Material component. If a shared primitive changes, test affected completed components too.

## Step 4: Add the shared showcase demo

Update:

    showcase/src/commonMain/kotlin/com/elegant/compose/showcase/ElegantShowcaseApp.kt

Register the lowercase slug and add one shared section covering the default look, public variants, disabled state, and the important interaction or slot. The same route must work on Android, Desktop, and Web; Web uses ?id={slug}. Keep launcher modules thin and do not make an HTML-only demo.

## Step 5: Add documentation and discovery

Create:

    docs/components/{slug}.md
    docs/zh_CN/components/{slug}.md

Follow the Miuix-format order in AGENTS.md and keep the two pages aligned. Put the real Compose Web/Wasm iframe directly after the introduction:

    src="../compose/index.html?id={slug}"
    src="../../compose/index.html?id={slug}"

Document the public API in signature order. Update both component indexes and both sidebar entries in docs/.vitepress/config.ts. Keep a new component Planned / 计划中 until its complete milestone is ready.

## Step 6: Verify and land

Run the required commands from AGENTS.md as independent invocations. Keep the three platform builds separate:

    ./scripts/validate-kmp-boundaries.sh
    gradle :sample:assembleDebug --stacktrace --no-daemon
    gradle :desktop-sample:createDistributable --stacktrace --no-daemon
    gradle :web-sample:wasmJsBrowserDistribution --stacktrace --no-daemon

Run the applicable library, showcase, and documentation checks required by AGENTS.md; record only commands that actually ran in VALIDATION.md. Use one coherent Conventional Commit for the component and companion changes. Push and wait for CI when the user requests remote delivery.

## Checklist

The eight Miuix-style touch points for Elegant UI are:

1. Component source
2. Meaningful common tests
3. Shared showcase section and stable slug route
4. English and Simplified Chinese pages with the real iframe
5. English and Chinese component-index rows
6. English and Chinese sidebar entries
7. Applicable target and docs checks recorded in VALIDATION.md
8. One coherent commit; remote CI when remote delivery is requested

When the component becomes available, add one concise row to references/completed-components.md in the same commit.

---
name: create-component
description: >-
  Create or complete one Compose Multiplatform UI component for Elegant UI,
  including every required wiring point: shared library source and tests,
  shared showcase demo and slug registration, English and Simplified Chinese
  Miuix-format pages, both component indexes, VitePress sidebars,
  Android/Desktop/Web verification, publication, and CI. Use whenever the user
  wants to add a component, create a composable, continue the component library,
  or complete a component milestone, including 新建组件、添加组件、继续组件库、完成组件.
---

# Create an Elegant UI component

Add one component to Elegant UI and complete every companion change beyond the component file itself: tests, shared showcase registration, bilingual documentation, website discovery, three-platform verification, publication, and CI.

The authoritative sources for conventions are `AGENTS.md` and the existing repository source. This skill describes the workflow and where changes go; if it conflicts with `AGENTS.md` or current source, they win. Read `references/completed-components.md` to choose completed Elegant UI components as implementation and delivery references.

Work on exactly one component milestone. iOS, Figma work, screenshot evidence, and pixel-diff artifacts are excluded unless the user separately requests them.

## Step 1: Gather requirements

Infer what is already established by the request, the two component indexes, and existing code. Ask only when a missing decision would materially change the public contract:

1. **Component name** — public `ElegantXxx` name and package name
2. **Milestone type** — new component or a concrete refinement of an available component
3. **Purpose** — what it does, where it is used, and what is out of scope
4. **Key parameters** — required state, callbacks, state flags, styles, sizes, slots, and content
5. **Visual model** — whether it needs `ElegantXxxDefaults`, `ElegantXxxColors`, or a smaller immutable model
6. **Stable documentation slug** — lowercase and unchanged once published

When the user only asks to continue, read:

```text
docs/components/index.md
docs/zh_CN/components/index.md
```

Select the first planned component whose prerequisites are already available. Do not start or scaffold a later component in the same milestone.

## Step 2: Read reference files by component type

Before writing code, read the Component API contract, Foundations and visual rules, Accessibility and input, and Component definition of done sections in `AGENTS.md`. Then read `references/completed-components.md`, choose the closest completed component, and read its source and contract test in full.

| Component type | Starting reference | Key points |
| :--- | :--- | :--- |
| Text action | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/button/ElegantButton.kt` | controlled activation, loading, emphasis styles, sizes, content slots |
| Compact or icon-only action | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/iconbutton/ElegantIconButton.kt` | accessible name, 48dp target, icon ownership, action-state parity |
| Non-interactive identity/display | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/avatar/ElegantAvatar.kt` | fallback content, decorative versus named semantics, custom content |
| Status, count, or overlay | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/badge/ElegantBadge.kt` | semantic tones, overflow, RTL placement, overlay measurement |
| Minimal or custom-drawn separator | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/divider/ElegantDivider.kt` | orientation, emphasis, stroke style, decorative semantics |
| New family | closest entry in `references/completed-components.md` | reuse repository delivery shape, then define the new family contract |

Also read the selected reference's showcase section and English/Chinese pages. Read only the theme or internal primitive files that it actually uses. Existing source wins if the reference catalog becomes stale.

Use completed components for conventions, not as templates to copy blindly. Do not inherit a dimension, state, slot, or interaction merely because the reference has it.

## Step 3: Write the component source and tests

Create shared source under:

```text
elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/{package}/
```

Create stable contract and pure-state tests under:

```text
elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/{package}/
```

Follow `AGENTS.md` for public parameter order, KDoc, state models, semantics, minimum target size, theme tokens, Light/Dark behavior, RTL, loading behavior, and compatibility. Match the chosen reference's coding style and idioms.

Important repository-specific rules:

- implement in `commonMain` first;
- use `androidMain`, `desktopMain`, or `wasmJsMain` only for unavoidable platform APIs;
- never expose Android, AWT/Swing, browser DOM, or other platform types in the shared public API;
- resolve state to visual values in one place;
- add or extend shared foundations only when the active component needs them;
- use semantic values from `ElegantTheme`, not raw colors in component code;
- provide correct role and state semantics for interactive and non-interactive forms;
- use controlled state and callbacks for mutable behavior;
- do not expose an unmodified Material component as the Elegant UI contract;
- do not reference or imitate another library's brand in code, comments, KDoc, or visuals;
- test stable enums and meaningful pure contract logic; do not add tautological tests.

If shared foundations change, run the affected completed-component tests as regressions.

## Step 4: Add the shared showcase demo

Update:

```text
showcase/src/commonMain/kotlin/com/elegant/compose/showcase/ElegantShowcaseApp.kt
```

1. Register the stable route: `"{slug}" -> XxxShowcase()`.
2. Add one shared showcase section for the component.
3. Cover the default appearance, every public style and size, applicable disabled/loading/error/selected states, and key content slots.
4. Include real interaction and constrained or long content when those are part of the contract.
5. Reuse the responsive showcase structure and theme switching already present.

The same shared showcase must run on Android, Desktop, and Web. Keep platform launchers thin. The Web launcher obtains the slug from `?id={slug}`; do not create a separate HTML imitation for documentation.

## Step 5: Add documentation

1. **Component pages** — create:

   ```text
   docs/components/{slug}.md
   docs/zh_CN/components/{slug}.md
   ```

   Follow the Miuix-format order defined in `AGENTS.md`: introduction, iframe, Import, Basic Usage, component-specific behavior, Component States, Properties, and Advanced Usage. Keep English and Chinese examples and API identifiers aligned.

2. **Real interactive preview** — place the iframe directly after the introduction:

   ```html
   src="../compose/index.html?id={slug}"
   src="../../compose/index.html?id={slug}"
   ```

   It must load the real `:web-sample` Compose Web/Wasm distribution.

3. **Component overview** — add the component to:

   ```text
   docs/components/index.md
   docs/zh_CN/components/index.md
   ```

   Keep both indexes synchronized. Change Planned / 计划中 to Available / 已完成 only when the complete milestone is ready to land.

4. **Sidebars** — add the English and Chinese entries in:

   ```text
   docs/.vitepress/config.ts
   ```

5. **Validation record** — update `VALIDATION.md` with commands actually run and unavailable runtime checks. Do not manufacture evidence or claim an unrun check passed.

## Step 6: Verify and land

Run repository checks as separate invocations. In particular, keep Android, Desktop, and Web build tasks separate so one platform does not block the other two.

Shared library and boundary checks:

```bash
./scripts/validate-kmp-boundaries.sh
gradle :elegant-ui:build --stacktrace --no-daemon
gradle :showcase:build --stacktrace --no-daemon
gradle :elegant-ui:publishAllPublicationsToBuildRepository --stacktrace --no-daemon
```

Android:

```bash
gradle :sample:assembleDebug --stacktrace --no-daemon
```

Desktop JVM:

```bash
gradle :desktop-sample:createDistributable --stacktrace --no-daemon
```

Web/Wasm:

```bash
gradle :web-sample:wasmJsBrowserDistribution --stacktrace --no-daemon
```

Documentation:

```bash
cd docs
npm install
npm run docs:check
npm run docs:build
```

Exercise the shared showcase on the supported runtimes available locally and record platform acceptance in `VALIDATION.md` as required by `AGENTS.md`. GitHub Actions is authoritative when a local Android SDK, browser runtime, or packaging tool is unavailable.

Use one coherent Conventional Commit for the component and all companion changes. Push it, wait for both **Multiplatform Build** and **Documentation** workflows to succeed, confirm the working tree is clean and local `HEAD` matches the remote, then stop without starting the next component.

## Checklist

A component milestone lands only when all 8 touch points are complete:

1. Shared component source and meaningful `commonTest` coverage
2. Shared showcase implementation
3. Stable slug registration and Web query routing
4. English and Simplified Chinese Miuix-format component pages
5. English and Chinese component overview entries
6. English and Chinese VitePress sidebar entries
7. Boundary, publication, documentation, and separate Android/Desktop/Web checks recorded in `VALIDATION.md`
8. One coherent commit pushed with both GitHub Actions workflows successful

When a new component becomes available, add one concise entry to `references/completed-components.md` in the same commit so future components can use it as a repository-native reference.

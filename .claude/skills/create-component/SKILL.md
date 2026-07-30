---
name: create-component
description: Advance the Elegant UI V1 component library by delivering exactly one component across Android, Desktop JVM, and Web/Wasm, using completed repository components as implementation-process references. Includes dependency-aware milestone selection, shared foundations, commonMain source, tests, shared showcase registration, Miuix-format bilingual pages, real Compose Web iframe, KMP publication, CI artifacts, and platform acceptance. Use for add IconButton, implement Checkbox, continue the component library, next planned component, 新建组件, 添加组件, 继续组件库, or finish a component milestone.
---

# Create an Elegant UI component

Complete one component as a reviewable, published, three-platform milestone. Android, Desktop JVM, and Web/Wasm are all supported. iOS is out of scope.

## Operating model

- Treat `AGENTS.md` as the authority and this skill as its execution sequence.
- Keep exactly one active component milestone.
- Use the closest completed repository component as the process and quality reference.
- Copy delivery structure and family conventions, never component-specific implementation or external branding.
- Add only shared primitives required by the active component.
- Stop after the active component is pushed and both workflows succeed. Do not start the next component in the same milestone.
- Do not use Figma unless the user explicitly approves a separate Figma milestone.

Button is the initial completed reference. Read all of these before starting the next action component:

```text
elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/button/ElegantButton.kt
elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/button/ElegantButtonContractTest.kt
showcase/src/commonMain/kotlin/com/elegant/compose/showcase/ElegantShowcaseApp.kt
showcase/src/commonTest/kotlin/com/elegant/compose/showcase/
docs/components/button.md
docs/zh_CN/components/button.md
VALIDATION.md
```

Also read the theme and token files used by the reference component. As new components land, prefer a completed component from the same family over Button.

## 0. Select one milestone

Read both progress indexes:

```text
docs/components/index.md
docs/zh_CN/components/index.md
```

If the user names a component, verify it belongs to the V1 scope and identify its stable prerequisites. If the user only asks to continue, select the first planned component whose dependencies are already available. Keep the two indexes synchronized, but do not mark the component available until the milestone closes.

Do not create source, test, documentation, or registry placeholders for later components.

## 1. Inspect the repository

Read:

- `AGENTS.md`
- `PROJECT_BRIEF.md`
- `FLOW.md`
- `VALIDATION.md`
- the closest implementation in `elegant-ui/src/commonMain/`
- the shared registry in `showcase/src/commonMain/.../ElegantShowcaseApp.kt`
- the matching English and Chinese component pages
- `.github/workflows/android.yml`
- `.github/workflows/docs.yml`

Confirm these targets remain present in both `:elegant-ui` and `:showcase`:

```text
android
jvm("desktop")
wasmJs
```

Map the selected component to:

- its closest completed family reference;
- the shared tokens and primitives it should reuse;
- any missing foundation that must be delivered inside this milestone;
- completed components that could be affected by foundation changes.

## 2. Freeze the component contract

Record:

- purpose and non-goals;
- public signature and parameter order;
- public enums, state holders, Defaults, and Colors;
- full state matrix and mutually exclusive states;
- semantic token additions;
- visible metrics and minimum interactive target;
- motion duration/easing;
- Light/Dark behavior;
- semantics role and state descriptions;
- Android touch/TalkBack checks;
- Desktop mouse/keyboard/focus/window checks;
- Web pointer/keyboard/focus/viewport checks;
- documentation slug and Miuix page outline.
- component-family naming and state-precedence rules inherited from completed references;
- customization anatomy: Defaults, Colors or state model, named slots, and intentionally non-customizable internals;
- cross-component compositions that must remain valid.

## 3. Choose source sets

Default implementation path:

```text
elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/<component>/
```

Use platform source sets only for unavoidable platform APIs:

```text
elegant-ui/src/androidMain/
elegant-ui/src/desktopMain/
elegant-ui/src/wasmJsMain/
```

Never expose Android, AWT/Swing, browser DOM, or other platform objects from shared public signatures.

## 4. Implement source and foundations

- Follow the parameter order in `AGENTS.md`.
- Reuse the closest completed component's conventions for naming, sizing, state resolution, semantics, and KDoc shape.
- Use semantic tokens from `ElegantTheme`.
- Add `ElegantXxxDefaults` and immutable visual/state models when needed.
- Centralize state resolution.
- Add complete public KDoc.
- Use correct semantics and state descriptions.
- Enforce the minimum interactive target.
- Prevent duplicate activation during loading/transition states.
- Preserve Light/Dark, RTL, density, font scale, high DPI, browser zoom, and viewport resizing.
- Do not wrap and re-export a Material component without an Elegant UI contract.
- Do not copy Button-specific behavior into a different component family.
- When adding a shared primitive, keep it lower-level than its consumers, test it directly, and rerun affected completed-component tests.

Add meaningful `commonTest` coverage for stable and pure logic.

## 5. Run boundary validation

```bash
./scripts/validate-kmp-boundaries.sh
```

It must reject platform imports in common code, missing targets, missing sample modules, and legacy `src/main` layouts in KMP modules.

## 6. Register the shared showcase

Update `showcase/src/commonMain/.../ElegantShowcaseApp.kt`:

- register `"{slug}" -> ...`;
- render default, every public style and size, disabled, loading, error, selected, and other applicable states;
- include interactive state transitions;
- follow completed showcase pages for responsive structure, hierarchy, theme switching, and real usage context;
- keep the demo UI shared rather than copied into platform launchers;
- preserve Light/Dark switching.

Keep launchers thin:

- `sample` launches the shared showcase on Android;
- `desktop-sample` launches it in a desktop window;
- `web-sample` reads `?id={slug}` and launches it through `ComposeViewport`.

## 7. Add bilingual Miuix-format pages

Create:

```text
docs/components/{slug}.md
docs/zh_CN/components/{slug}.md
```

Use the fixed order:

```text
# ComponentName
introduction
iframe
Import / 引入
Basic Usage / 基本用法
component-specific types or behavior
Component States / 组件状态
Properties / 属性
Advanced Usage / 进阶用法
```

Use:

```html
src="../compose/index.html?id={slug}"
src="../../compose/index.html?id={slug}"
```

Document every public parameter in signature order and every user-facing enum, Defaults, Colors, constant, and factory. Keep both locales structurally synchronized.

## 8. Update website discovery

Update:

```text
docs/.vitepress/config.ts
docs/components/index.md
docs/zh_CN/components/index.md
```

The documentation iframe must be the actual `:web-sample` Compose Web/Wasm distribution. Do not add a separate HTML imitation of component behavior.

Keep both component indexes structurally aligned. Change the active component from planned to available only when every completion gate is ready to land in the same milestone.

## 9. Verify all targets

Run when available:

```bash
./scripts/validate-kmp-boundaries.sh

gradle :elegant-ui:build --stacktrace --no-daemon
gradle :showcase:build --stacktrace --no-daemon
gradle :elegant-ui:publishAllPublicationsToBuildRepository --stacktrace --no-daemon
gradle :sample:assembleDebug --stacktrace --no-daemon
gradle :desktop-sample:createDistributable --stacktrace --no-daemon
gradle :web-sample:wasmJsBrowserDistribution --stacktrace --no-daemon

cd docs
npm install
npm run docs:check
npm run docs:build
```

Verify the Maven repository contains root metadata plus Android, Desktop JVM, and Web/Wasm publications.

## 10. Platform acceptance

Update `VALIDATION.md` and complete applicable checks:

- Android: physical device, touch, hardware keyboard, TalkBack, font scale, RTL.
- Desktop: mouse, keyboard, focus traversal, resize, high DPI, Linux CI distributable.
- Web: modern WasmGC browser, pointer, keyboard, focus, browser zoom, responsive viewport.

A component is not complete if any supported platform is skipped without an explicit, approved exception.

## 11. Close the milestone

Before marking the component available:

1. Run the active component tests and all completed-component tests affected by shared foundation changes.
2. Run documentation validation after updating both indexes and sidebars.
3. Review the public API diff for naming, parameter order, additive compatibility, and family consistency.
4. Update `VALIDATION.md` with commands actually run and any authoritative GitHub-only checks.
5. Use one coherent Conventional Commit for the milestone; use separate follow-up commits only for independently discovered fixes.
6. Push when the user requested a complete remote delivery.
7. Wait for both **Multiplatform Build** and **Documentation** workflows to finish successfully.
8. Confirm the working tree is clean and local `HEAD` matches the pushed branch.
9. Stop. Report the next planned component without starting it.

## Completion checklist

1. One planned V1 component was selected with stable dependencies.
2. A completed family component was used as the local process reference.
3. Contract, family conventions, customization anatomy, and slug are locked.
4. Shared source and KDoc are complete.
5. No platform types leak into common/public API.
6. Boundary validation passes.
7. Tokens, shared primitives, Defaults/Colors, state logic, and tests are complete.
8. Affected completed-component tests still pass.
9. Shared showcase registration is interactive and complete.
10. Android, Desktop, and Web builds succeed.
11. English and Chinese pages match the API.
12. Real Compose Web iframe, both sidebars, and both indexes are updated.
13. The active component is marked available in both progress indexes.
14. KMP Maven publication includes all three variants.
15. Multiplatform and Documentation workflows succeed.
16. Platform acceptance is recorded.
17. The public API remains additive or has an explicit migration.
18. No unrelated next-component or iOS work is included.

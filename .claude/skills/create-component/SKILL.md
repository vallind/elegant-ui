---
name: create-component
description: Deliver one Elegant UI component across Android, Desktop JVM, and Web/Wasm with commonMain source, tests, shared showcase registration, Miuix-format bilingual website pages, real Compose Web iframe, KMP publication, CI artifacts, and platform acceptance. Use for add IconButton, implement Checkbox, 新建组件, 添加组件, or finish a component milestone.
---

# Create an Elegant UI component

Complete one component as a reviewable, published, three-platform milestone. Android, Desktop JVM, and Web/Wasm are all supported. iOS is out of scope.

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
- Use semantic tokens from `ElegantTheme`.
- Add `ElegantXxxDefaults` and immutable visual/state models when needed.
- Centralize state resolution.
- Add complete public KDoc.
- Use correct semantics and state descriptions.
- Enforce the minimum interactive target.
- Prevent duplicate activation during loading/transition states.
- Preserve Light/Dark, RTL, density, font scale, high DPI, browser zoom, and viewport resizing.
- Do not wrap and re-export a Material component without an Elegant UI contract.

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

## Completion checklist

1. Contract and slug are locked.
2. Shared source and KDoc are complete.
3. No platform types leak into common/public API.
4. Boundary validation passes.
5. Tokens, Defaults/Colors, state logic, and tests are complete.
6. Shared showcase registration is interactive and complete.
7. Android, Desktop, and Web builds succeed.
8. English and Chinese pages match the API.
9. Real Compose Web iframe, both sidebars, and both indexes are updated.
10. KMP Maven publication includes all three variants.
11. Multiplatform and Documentation workflows succeed.
12. Platform acceptance is recorded.
13. No unrelated next-component or iOS work is included.

---
name: create-component
description: Create or complete one Elegant UI component through the Android-first, common-first Kotlin Multiplatform workflow: contract, commonMain source, semantic tokens, public KDoc, Android sample, synchronized Miuix-format English and Simplified Chinese website pages, iframe demo registration, KMP boundary checks, Maven/APK/AAR GitHub Actions artifacts, and physical-device validation. Use for requests such as add IconButton, implement Checkbox, 新建组件, 添加组件, or finish a component milestone.
---

# Create an Elegant UI component

Complete one component as a reviewable, installable, bilingual milestone. Android is the only supported runtime target. Write shareable code in `commonMain`; do not add speculative Desktop, iOS, or Web implementations.

## Outcome

A successful component milestone includes:

- public Compose API in `commonMain`;
- semantic tokens, defaults, colors, state resolution, KDoc, and tests;
- Android sample demo and registration;
- English and Simplified Chinese Miuix-format component pages;
- iframe visual demo registered by component slug;
- both locale sidebars and component indexes;
- KMP boundary validation;
- KMP build and Maven publication;
- Android APK and extracted AAR artifacts;
- documentation deployment;
- physical-device acceptance.

Do not begin the next component until all applicable gates pass.

## Step 1: Inspect the repository

Read:

- `AGENTS.md`
- `PROJECT_BRIEF.md`
- `FLOW.md`
- `VALIDATION.md`
- the closest component in `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/`
- both locale pages for that component family
- `docs/public/compose/index.html`
- `.github/workflows/android.yml`
- `.github/workflows/docs.yml`

Confirm the current platform statement remains:

```text
Android: supported
Desktop JVM: planned
 iOS: planned
Web/Wasm: planned
```

Do not add another target as part of ordinary component work.

## Step 2: Write a compact contract

Record:

- component purpose;
- public function signature and parameter order;
- public enums, state holders, defaults, and colors;
- full state matrix and mutually exclusive states;
- semantic token additions;
- minimum touch target and visible metrics;
- animation duration/easing;
- Light/Dark behavior;
- semantics role and state descriptions;
- RTL and font-scale expectations;
- Android sample scenarios;
- website page outline and iframe slug;
- platform dependencies and why the implementation belongs in `commonMain` or `androidMain`;
- component-specific physical-device checks.

Any Android-only requirement must be explicit. Keep Android types out of public API.

## Step 3: Choose the source set

Default path:

```text
elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/<component>/
```

Use `androidMain` only when the implementation genuinely requires Android APIs or Android resources.

When platform behavior is needed:

1. define the smallest stable common contract;
2. implement the Android behavior in `androidMain`;
3. prefer dependency injection or a narrow interface;
4. use `expect` / `actual` only when compile-time platform specialization is the clearest design;
5. document the fallback and Android acceptance checks.

Never create `elegant-ui/src/main`.

## Step 4: Implement foundations and source

Required rules:

- Follow the parameter order in `AGENTS.md`.
- Required behavior/state comes first, then `Modifier`, state flags, visual configuration, optional slots, and main content last.
- Use semantic values from `ElegantTheme` and foundations.
- Add raw colors only to the theme/foundation layer.
- Use `@Immutable` only for truly immutable values.
- Centralize state-to-visual resolution.
- Expose `ElegantXxxDefaults` and `ElegantXxxColors` when reusable visual configuration exists.
- Use correct semantics roles and state descriptions.
- Enforce at least a 48dp interactive root.
- Disable duplicate interaction during loading or transition states.
- Preserve Light/Dark and RTL behavior.
- Add public KDoc to all public declarations.
- Do not expose Android platform types from common/public signatures.
- Do not expose an unmodified Material component as the Elegant UI contract.

Add common tests for stable enums, pure state logic, or public contract behavior. Platform behavior belongs in the matching platform test source set when enabled.

## Step 5: Run the KMP boundary check

Run:

```bash
./scripts/validate-kmp-boundaries.sh
```

The check must fail when:

- `commonMain` imports `android.*`;
- `commonMain` imports known Android-only AndroidX APIs;
- the legacy library `src/main` layout returns.

Also inspect public declarations manually for Android types that grep cannot identify reliably.

## Step 6: Add and register the Android sample

The sample is the source of truth for physical interaction.

1. Add a focused demo screen or section.
2. Register it in the sample entry/navigation.
3. Show default, every public style/variant, every size, disabled, loading, error, selected, or other applicable states.
4. Add interactive state so callbacks and state transitions can be tested.
5. Keep Light/Dark switching available.
6. Use Elegant UI semantic values rather than demo-only color constants.
7. Add concise on-device instructions for non-obvious checks.

A website iframe or Compose Preview does not replace the sample APK.

## Step 7: Add bilingual Miuix-format website pages

Create:

```text
docs/components/{slug}.md
docs/zh_CN/components/{slug}.md
```

Use the fixed order.

### English

```markdown
# ComponentName

One concise introduction paragraph.

<iframe id="demoIframe" ... src="../compose/index.html?id={slug}" ...></iframe>

## Import
## Basic Usage
## Component-specific types or behavior
## Component States
## Properties
### ComponentName Properties
### Public enums / defaults / colors when present
## Advanced Usage
```

### Simplified Chinese

```markdown
# ComponentName

一段简洁介绍。

<iframe id="demoIframe" ... src="../../compose/index.html?id={slug}" ...></iframe>

## 引入
## 基本用法
## 组件特有类型或行为
## 组件状态
## 属性
### ComponentName 属性
### 已公开枚举 / Defaults / Colors
## 进阶用法
```

Rules:

- The iframe immediately follows the introduction; do not add a separate Demo heading.
- Property tables use property name, type, description, default value, required.
- Document every public parameter in signature order.
- Document every public enum, defaults member, colors field/model, constant, and factory that users need.
- Keep both locale Kotlin block counts and API identifiers aligned.
- Do not document planned platform APIs.
- Use the platform-support guide to state that Android is the only supported target.

## Step 8: Register the iframe demo and discovery points

Update:

```text
docs/public/compose/index.html
docs/.vitepress/config.ts
docs/components/index.md
docs/zh_CN/components/index.md
```

The iframe renderer must:

- register `?id={slug}`;
- work as a standalone static page;
- expose meaningful variants and states;
- support Light/Dark when applicable;
- remain keyboard accessible and responsive;
- state that the Android APK is the behavioral source of truth;
- avoid pretending to reproduce behavior a browser mock cannot represent faithfully.

Update README links when the active milestone changes.

## Step 9: Update device validation

Update `VALIDATION.md` with:

- device model and Android version;
- display and font scale;
- Light/Dark;
- hierarchy, spacing, typography, and contrast;
- touch target, press, focus, keyboard, screen reader, and RTL;
- loading/duplicate-action behavior;
- component-specific gestures or input;
- observations and acceptance decision.

## Step 10: Verify documentation and builds

Run when the environment supports them:

```bash
./scripts/validate-kmp-boundaries.sh

cd docs
npm install
npm run docs:check
npm run docs:build
cd ..

gradle :elegant-ui:build --stacktrace --no-daemon
gradle :elegant-ui:publishAllPublicationsToBuildRepository --stacktrace --no-daemon
gradle :sample:assembleDebug --stacktrace --no-daemon
gradle check lint --stacktrace --no-daemon
```

Confirm:

- `elegant-ui/build/repo/` contains root KMP metadata, POMs, sources, and an Android AAR;
- `sample/build/outputs/apk/debug/sample-debug.apk` exists;
- both locales build and route correctly;
- the component slug resolves in the iframe registry;
- the **Documentation** workflow succeeds;
- the **Android Build** workflow uploads `elegant-ui-maven-repository`, a direct Android AAR, and the sample APK.

Do not claim success for commands that were not executed.

## Step 11: Physical-device gate

Have the user install the latest successful APK and complete `VALIDATION.md`. Classify feedback as functional, accessibility, visual, Android/device, or preference. Fix accepted defects, rebuild affected workflows, and repeat validation.

## Consumer compatibility check

For public API changes, verify all supported Android consumption paths remain valid:

```kotlin
implementation(project(":elegant-ui"))
implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
```

The first is for the same build. The second is for Maven Local, the CI Maven repository artifact, and future Maven Central releases. Avoid instructions that require consumers to copy only an AAR.

## Commit style

Use one coherent Conventional Commit milestone:

```text
feat(component): add icon button closed loop
```

Use narrower follow-ups only when necessary:

```text
fix(icon-button): preserve focus ring in dark theme
docs(icon-button): align website and Compose states
```

## Completion checklist

A component lands only when:

1. Contract and source-set decision are locked.
2. Common-first source and public KDoc are complete.
3. No Android platform types leak into common/public API.
4. KMP boundary validation passes.
5. Tokens, defaults, colors, states, and common tests are complete.
6. Android sample demo is registered and interactive.
7. English and Chinese Miuix-format pages match the real API.
8. Iframe demo, both sidebars, and both indexes are updated.
9. `VALIDATION.md` covers the component.
10. Documentation CI succeeds.
11. KMP build and Maven publication succeed.
12. Android CI produces Maven, AAR, and APK artifacts.
13. Physical-device validation is accepted.
14. No speculative target or next-component work is included.

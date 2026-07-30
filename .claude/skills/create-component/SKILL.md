---
name: create-component
description: Create or complete an Elegant UI Android Jetpack Compose component through the full repository workflow: visual/API contract, semantic tokens, library source, sample demo and registration, synchronized English and Simplified Chinese VitePress pages using the Miuix component-documentation template, iframe demo registration, website navigation, GitHub Actions website/APK/AAR builds, and physical-device validation. Use whenever the user asks to add, create, scaffold, implement, or finish a component or composable in vallind/elegant-ui, including requests that only name the component such as "add IconButton", "new component", "添加组件", "新建组件", or "实现 Checkbox".
---

# Create an Elegant UI Component

Add one Android Jetpack Compose component to Elegant UI and complete every companion change required for a reviewable, installable, bilingual, website-published, physical-device-tested milestone.

`AGENTS.md` at the repository root is authoritative for API conventions, tokens, accessibility, website rules, validation, and commit style. Existing source is authoritative for the current project structure. When this skill conflicts with either, `AGENTS.md` and current source win.

## Step 1: Gather and Lock Requirements

Resolve every item that materially affects the public API, state matrix, or documentation contract:

1. **Component name** — `PascalCase`, with the public composable prefixed `Elegant` when appropriate, for example `ElegantIconButton`.
2. **V1 category** — basic, form, content, or navigation, matching `PROJECT_BRIEF.md`.
3. **Purpose** — what user task the component supports and when it should not be used.
4. **Required state** — such as `checked`, `selected`, `value`, `expanded`, or `loading`.
5. **Callbacks** — user-originated actions and state-change callbacks.
6. **Variants and sizes** — only stable design-system axes; do not create variants for arbitrary icons or labels.
7. **Content slots** — label, leading/trailing icon, supporting content, or custom content.
8. **States** — default, pressed, focused, disabled, loading, selected, error, or others that genuinely apply.
9. **Accessibility contract** — role, state semantics, touch target, focus visibility, content descriptions, font scaling, and RTL.
10. **Website contract** — component slug, category, page sections, preview scenarios, English/Chinese labels, sidebar location, and component-index entry.
11. **Figma contract** — use it when available. If automation is unavailable, record the agreed contract and continue; do not block Compose, website, or CI.

Do not begin implementation while a genuine public-API fork is unresolved. Do not ask about details already locked in `PROJECT_BRIEF.md`, Figma, or existing source.

## Step 2: Inspect the Repository and Closest Reference

Before writing code:

1. Read `AGENTS.md`.
2. Read `PROJECT_BRIEF.md`, `FLOW.md`, and the current `VALIDATION.md`.
3. Read the closest existing component source in full.
4. Read its English and Chinese website pages together.
5. Read the sample entry point and note how the component will be exposed on device.
6. Inspect theme/token files before proposing any color, spacing, radius, or motion value.
7. Inspect `docs/.vitepress/config.ts`, the relevant component overview pages, and existing preview components.
8. Inspect `.github/workflows/android.yml` and `.github/workflows/docs.yml` so verification commands, deployment base, and artifact paths remain correct.

Choose the closest reference by behavior, not visual resemblance:

| Component behavior | Reference | Review focus |
| :--- | :--- | :--- |
| Clickable action | `elegant-ui/.../button/ElegantButton.kt` | interaction source, press/focus state, 48dp root, loading lockout, slots |
| Theme-aware component | `elegant-ui/.../theme/ElegantTheme.kt` and `ElegantColor.kt` | semantic Light/Dark values and composition locals |
| Foundation dimensions | `elegant-ui/.../theme/ElegantTokens.kt` | spacing/radius naming and reuse |
| Physical-device demo | `sample/.../MainActivity.kt` | visible state matrix, theme switch, interaction checks |
| Bilingual website page | `docs/components/button.md` and `docs/zh_CN/components/button.md` | one-to-one structure and API fidelity |
| Website demo | `docs/public/compose/index.html?id=button` | Miuix-style iframe contract, query-id registry, Light/Dark and meaningful states, Android-runtime disclaimer |

## Step 3: Define the Component Contract

Before code, write a compact implementation contract in notes or the active plan:

- Public composable signature and parameter order.
- Public enums/data classes/defaults objects.
- State matrix and which states are mutually exclusive.
- Token additions and their semantic names.
- Minimum touch target and visible dimensions.
- Animation durations/easing and reduced-motion considerations when applicable.
- Light/Dark behavior.
- Semantics role, state descriptions, and disabled behavior.
- Sample scenarios.
- English/Chinese website outline.
- Iframe demo behavior, query-id registry entry, and limitations.
- Sidebar category and component overview row.
- Physical-device checks unique to the component.

Prefer a small, coherent public API. Implementation-only metrics and visual resolution models remain private.

## Step 4: Implement Tokens and Library Source

Create the component family under:

```text
elegant-ui/src/main/java/com/elegant/compose/ui/<component>/
```

Use a single primary source file until the implementation has a clear reason to split.

Required rules:

- Follow the parameter order in `AGENTS.md`.
- Use semantic values from `ElegantTheme` and foundation/component defaults.
- Add raw color values only in the theme/foundation layer.
- Use `@Immutable` only for truly immutable state/color/metric models.
- Centralize state-to-visual resolution.
- Keep content slots composable and keep the main content lambda last.
- Use the correct semantics `Role` and expose state.
- Enforce at least a 48dp interactive root.
- Disable interaction during loading or other non-interactive transitional states.
- Preserve Light/Dark parity.
- Respect RTL for directional content.
- Add public KDoc for the composable, public enums, defaults, and public data classes.
- Do not expose raw Material styling as the Elegant UI contract.

Create `ElegantXxxDefaults` and `ElegantXxxColors` when the component has reusable visual configuration. Do not add a parameter merely because Figma contains a token; public parameters represent supported product-level customization.

## Step 5: Add the Sample Demo and Registration

The sample must make physical-device validation possible without reading source.

1. Follow the current sample architecture. Extract `sample/.../component/{Name}Demo.kt` when the entry point would otherwise become unwieldy.
2. Register the component in sample navigation or the main screen.
3. Show the default configuration.
4. Show every public style/variant and size.
5. Show disabled and component-specific edge states.
6. Add interactive state so callbacks, selection, loading, expansion, drag, or input can be tested.
7. Make Light/Dark switching available.
8. Avoid demo-only hardcoded colors; consume `ElegantTheme`.
9. Include concise on-device instructions for checks that are not visually obvious.

A website preview, screenshot, or Compose Preview is insufficient because the delivery gate is a real APK.

## Step 6: Add the Bilingual Website Content

Create or update:

```text
docs/components/{slug}.md
docs/zh_CN/components/{slug}.md
```

The pages must correspond one to one, use the real public API, and follow the Miuix component-documentation template.

### Required English structure

```markdown
# ComponentName

`ComponentName` is ...

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id={slug}" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import
## Basic Usage
## Component-Specific Types or Behavior
## Component States
## Properties
### ComponentName Properties
### Public Enum / Defaults / Colors subsections when they exist
## Advanced Usage
```

### Required Simplified Chinese structure

Use the same order and examples, with these fixed headings:

```markdown
# ComponentName
## 引入
## 基本用法
## 组件特有类型或行为
## 组件状态
## 属性
### ComponentName 属性
### 已公开枚举 / Defaults / Colors 子章节（存在时）
## 进阶用法
```

The Chinese iframe source is `../../compose/index.html?id={slug}`.

### Documentation rules

- Keep the introduction to one concise paragraph describing purpose and supported forms.
- Place the iframe immediately after the introduction; do not add a separate `Demo` heading.
- Use component-specific top-level sections only between Basic Usage and Component States, such as `Button Types` or `Selection Modes`.
- Property tables use exactly: property name, type, description, default value, required.
- Document every public parameter in signature order.
- Document public enums, defaults objects, colors classes, constants, and factory methods under `## Properties` when they actually exist.
- Put size metrics in the public size/defaults subsection instead of a standalone top-level Sizes section.
- Keep accessibility, RTL, physical-device, and repository-policy checklists in source KDoc, sample guidance, `AGENTS.md`, and `VALIDATION.md`; do not append them as generic top-level component-doc sections.
- Do not document planned APIs or private implementation models.
- English and Chinese Kotlin block counts and API identifiers must match.

### Iframe demo

Register or update the component visual demo in:

```text
docs/public/compose/index.html
```

The demo is selected by `?id={slug}`. Add one renderer and one `demoRenderers` registry entry per component.

Demo requirements:

- Work as a standalone static page copied by VitePress from `docs/public/`.
- Support Light/Dark when the Android component does.
- Expose meaningful public variants and states.
- Remain keyboard accessible and responsive inside the iframe.
- State inside the demo that the Android APK is the source of truth for Compose semantics and device behavior.
- Do not fake behavior that cannot be represented faithfully in a browser.

## Step 7: Register Website Navigation and Indexes

A component page is not complete until all website discovery points are updated:

1. Add the English link to the correct category in `docs/.vitepress/config.ts`.
2. Add the Simplified Chinese link to the matching category and order.
3. Add the component row/card to `docs/components/index.md`.
4. Add the mirrored row/card to `docs/zh_CN/components/index.md`.
5. Confirm locale switching preserves the equivalent route.
6. Confirm `docs/public/compose/index.html` contains the `{slug}` demo renderer and registry entry.
7. Update README website/component links when the active milestone changes.

Never add only one locale, one sidebar, or one component index.

## Step 8: Update Physical-Device Validation

Update `VALIDATION.md` for the active component milestone. Preserve general checks and add component-specific checks, including installation, Light/Dark, hierarchy, touch target, focus/press feedback, duplicate-action prevention, font scaling, landscape, RTL, and component-specific gesture/keyboard/screen-reader/haptic behavior.

Require the tester to record device model, Android version, display scale, font scale, and observations.

## Step 9: Verify Website and Android Builds

Run locally when the environment supports it:

```bash
cd docs
npm install
npm run docs:build
cd ..

gradle check --stacktrace --no-daemon
gradle lint --stacktrace --no-daemon
gradle :sample:assembleDebug :elegant-ui:assembleRelease --stacktrace --no-daemon
```

Website verification must confirm:

- No broken Markdown/Vue imports.
- English and Chinese pages both build.
- Sidebar links resolve.
- Locale switch reaches the mirrored page.
- Every component iframe resolves to `compose/index.html?id={slug}` and the demo registry recognizes the slug.
- GitHub Pages uses base `/elegant-ui/`.

Then push the coherent component milestone and verify both workflows:

- **Documentation** builds the VitePress site and deploys GitHub Pages on `main`.
- **Android Build** produces the APK, AAR, and checksums.

Required Android artifacts:

```text
sample/build/outputs/apk/debug/sample-debug.apk
elegant-ui/build/outputs/aar/elegant-ui-release.aar
```

Never describe static parsing or visual inspection as a successful website or Android build.

## Step 10: Physical-Device Gate

Have the user install the latest successful APK artifact and complete `VALIDATION.md`.

Classify feedback as functional, accessibility, visual, device/platform, or preference. Fix accepted defects, rebuild both affected workflows, and repeat device validation. Do not begin the next component until the active component is accepted.

## Commit Style

Use Conventional Commit style. A normal component milestone is one coherent commit:

```text
feat(component): add icon button closed loop
```

Use narrower follow-ups only when necessary:

```text
fix(icon-button): preserve focus ring in dark theme
docs(icon-button): align website preview and Compose states
```

## Completion Checklist

A component lands only when all applicable touchpoints are complete:

1. Component contract is locked.
2. Semantic/foundation/component tokens are added or reused correctly.
3. Library source and public KDoc are complete.
4. Sample demo is added and registered in the APK.
5. English website page is complete.
6. Simplified Chinese website page mirrors English.
7. The Miuix-style iframe is present in both locale pages and the component is registered in `docs/public/compose/index.html`.
8. Both locale sidebars register the component.
9. Both component overview pages register the component.
10. README/project status is updated when the milestone changed.
11. `VALIDATION.md` covers the component.
12. The VitePress website builds and the Documentation workflow succeeds.
13. Android GitHub Actions produces APK and AAR artifacts.
14. Physical-device results are recorded and accepted.
15. No unrelated component work is included.

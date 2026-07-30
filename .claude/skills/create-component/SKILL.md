---
name: create-component
description: Create or complete an Elegant UI Android Jetpack Compose component through the full repository workflow: visual/API contract, semantic tokens, library source, sample demo and registration, matching English and Simplified Chinese documentation, GitHub Actions APK/AAR build, and physical-device validation. Use whenever the user asks to add, create, scaffold, implement, or finish a component or composable in vallind/elegant-ui, including requests that only name the component such as "add IconButton", "new component", "添加组件", "新建组件", or "实现 Checkbox".
---

# Create an Elegant UI Component

Add one Android Jetpack Compose component to Elegant UI and complete every companion change required for a reviewable, installable, bilingual, physical-device-tested milestone.

`AGENTS.md` at the repository root is authoritative for API conventions, tokens, accessibility, validation, and commit style. Existing source is authoritative for the current project structure. When this skill conflicts with either, `AGENTS.md` and current source win.

## Step 1: Gather and Lock Requirements

Resolve every item that materially affects the public API or state matrix:

1. **Component name** — `PascalCase`, with the public composable prefixed `Elegant` when appropriate, for example `ElegantIconButton`.
2. **V1 category** — basic, form, content, or navigation, matching `PROJECT_BRIEF.md`.
3. **Purpose** — what user task the component supports and when it should not be used.
4. **Required state** — such as `checked`, `selected`, `value`, `expanded`, or `loading`.
5. **Callbacks** — user-originated actions and state-change callbacks.
6. **Variants and sizes** — only stable design-system axes; do not create variants for arbitrary icons or labels.
7. **Content slots** — label, leading/trailing icon, supporting content, or custom content.
8. **States** — default, pressed, focused, disabled, loading, selected, error, or others that genuinely apply.
9. **Accessibility contract** — role, state semantics, touch target, focus visibility, content descriptions, font scaling, and RTL.
10. **Figma contract** — use it when available. If automation is unavailable, record the agreed contract and continue; do not block Compose or CI.

Do not begin implementation while a genuine public-API fork is unresolved. Do not ask about details that are already locked in `PROJECT_BRIEF.md`, Figma, or existing source.

## Step 2: Inspect the Repository and Closest Reference

Before writing code:

1. Read `AGENTS.md`.
2. Read `PROJECT_BRIEF.md`, `FLOW.md`, and the current `VALIDATION.md`.
3. Read the closest existing component source in full.
4. Read its English and Chinese documentation together.
5. Read the sample entry point and note how the component will be exposed on device.
6. Inspect theme/token files before proposing any color, spacing, radius, or motion value.
7. Inspect `.github/workflows/android.yml` so verification commands and artifact paths remain correct.

Choose the closest reference by behavior, not by visual resemblance:

| Component behavior | Reference | Review focus |
| :--- | :--- | :--- |
| Clickable action | `elegant-ui/.../button/ElegantButton.kt` | interaction source, press/focus state, 48dp root, loading lockout, slots |
| Theme-aware component | `elegant-ui/.../theme/ElegantTheme.kt` and `ElegantColor.kt` | semantic Light/Dark values and composition locals |
| Foundation dimensions | `elegant-ui/.../theme/ElegantTokens.kt` | spacing/radius naming and reuse |
| Physical-device demo | `sample/.../MainActivity.kt` | visible state matrix, theme switch, interaction checks |
| Bilingual docs | `docs/button.md` and `docs/zh/button.md` | one-to-one structure and API fidelity |

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
- English/Chinese documentation outline.
- Physical-device checks unique to the component.

Prefer a small, coherent public API. Implementation-only metrics and visual resolution models remain private.

## Step 4: Implement Tokens and Library Source

### Location

Create the component family under:

```text
elegant-ui/src/main/java/com/elegant/compose/ui/<component>/
```

Use a single primary source file until the implementation has a clear reason to split.

### Required implementation rules

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

### Defaults and colors

Create `ElegantXxxDefaults` and `ElegantXxxColors` when the component has reusable visual configuration. Avoid a public colors class for a minimal component when a small number of semantic `Color` parameters is clearer.

Do not add a parameter merely because Figma contains a token. Public parameters represent supported product-level customization.

## Step 5: Add the Sample Demo and Registration

The sample must make physical-device validation possible without reading source.

1. Follow the current sample architecture. If the catalog has been split into component demos, create `sample/.../component/{Name}Demo.kt`; otherwise keep `MainActivity.kt` readable and extract a component demo when it would otherwise become unwieldy.
2. Register the component in the sample navigation or main screen.
3. Show the default configuration.
4. Show every public style/variant and size.
5. Show disabled and component-specific edge states.
6. Add interactive state where useful so callbacks, selection, loading, expansion, drag, or input can be tested.
7. Make Light/Dark switching available.
8. Avoid demo-only hardcoded colors; consume `ElegantTheme`.
9. Include concise on-device instructions for checks that are not visually obvious.

A screenshot-only or preview-only example is insufficient because the delivery gate is a real APK.

## Step 6: Add Matching English and Chinese Documentation

Create or update:

```text
docs/{slug}.md
docs/zh/{slug}.md
```

The pages must correspond one to one and use the real public API. Include, when applicable:

1. Purpose and usage guidance.
2. Import statement.
3. Basic example.
4. Styles/variants.
5. Sizes and dimensions.
6. States and interaction behavior.
7. Parameter table.
8. `ElegantXxxDefaults` and `ElegantXxxColors` documentation.
9. Advanced examples or controlled-state usage.
10. Accessibility and RTL guidance.
11. Physical-device checks.

Do not document planned APIs. Compile examples mentally against the actual signature, then verify through the build when possible.

Update README component/milestone links and `PROJECT_BRIEF.md` status only when the active milestone or accepted scope has changed.

## Step 7: Update Physical-Device Validation

Update `VALIDATION.md` for the active component milestone. Preserve general checks and add component-specific checks, for example:

- Installation and launch.
- Light/Dark switching without crash.
- Visual hierarchy and state clarity.
- One-handed touch behavior and 48dp target.
- Press/focus feedback.
- Disabled/loading duplicate-action prevention.
- Font scaling and localization resilience.
- Landscape layout.
- RTL direction.
- Gesture, keyboard, screen-reader, or haptic behavior when applicable.

Require the tester to record device model, Android version, display scale, font scale, and observations.

## Step 8: Verify with GitHub Actions

Run locally when the environment supports it:

```bash
gradle check --stacktrace --no-daemon
gradle lint --stacktrace --no-daemon
gradle :sample:assembleDebug :elegant-ui:assembleRelease --stacktrace --no-daemon
```

Then push the coherent component milestone and verify the **Android Build** workflow.

Required artifacts:

```text
sample/build/outputs/apk/debug/sample-debug.apk
elegant-ui/build/outputs/aar/elegant-ui-release.aar
```

The workflow must upload the APK, AAR, and checksums. If CI fails, inspect the failing job and fix it before requesting device validation.

Never describe static parsing or visual inspection as a successful Android build.

## Step 9: Physical-Device Gate

Have the user install the latest successful APK artifact and complete `VALIDATION.md`.

Classify feedback as:

- **Functional defect** — crash, callback/state error, duplicate action, unusable input.
- **Accessibility defect** — touch target, semantics, focus, contrast, font scale, RTL.
- **Visual defect** — spacing, alignment, typography, hierarchy, shape, color, motion.
- **Device/platform defect** — API-level, OEM, density, orientation, insets, keyboard.
- **Preference** — valid subjective adjustment that does not violate the locked contract.

Fix accepted defects, rebuild through CI, and repeat device validation. Do not begin the next component until the active component is accepted.

## Commit Style

Use the repository's Conventional Commit rules. A normal component milestone is one coherent commit:

```text
feat(component): add icon button closed loop
```

Use narrower follow-up commits only when necessary:

```text
fix(icon-button): preserve focus ring in dark theme
docs(icon-button): clarify content descriptions
```

## Completion Checklist

A component lands only when all applicable touchpoints are complete:

1. Component contract is locked.
2. Semantic/foundation/component tokens are added or reused correctly.
3. Library source and public KDoc are complete.
4. Sample demo is added.
5. Sample demo is registered and reachable in the APK.
6. English documentation is complete.
7. Simplified Chinese documentation mirrors English.
8. README/project status is updated when the milestone changed.
9. `VALIDATION.md` covers the component.
10. GitHub Actions produces APK and AAR artifacts.
11. Physical-device results are recorded and accepted.
12. No unrelated component work is included.

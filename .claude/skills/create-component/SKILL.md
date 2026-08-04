---
name: create-component
description: >-
  Create a new Compose Multiplatform UI component for Elegant UI, including
  shared source and tests, showcase registration, English and Simplified
  Chinese Miuix-format pages, component indexes, VitePress sidebars, and the
  supported Android/Desktop/Web wiring. Use for add component, new composable,
  continue the component library, 新建组件, 添加组件, 新组件, or 继续组件库.
---

# Create an Elegant UI Component

Add a new component to the Elegant UI library and complete every companion change beyond the component file itself — contract tests, showcase section, bilingual docs, indexes, sidebars: 8 touch points in total (see the checklist at the end).

The authoritative sources for conventions are `AGENTS.md` at the repo root (API Conventions, Key Patterns, Critical Constraints, Verified CMP 1.11 constraints) and the reference source files listed below. This skill only describes the workflow and where the changes go; wherever it conflicts with AGENTS.md or existing source, they win — source code does not go stale, details copied into a skill do.

## Step 1: Gather requirements

Infer established details from the request and both component indexes. Ask only for a decision that would change the public API:

1. **Component name** — public `ElegantXxx` name and package (e.g. `ElegantTag` → `com.elegant.compose.ui.tag`)
2. **Family** — action, display, status, container, input, overlay, selection, collection, navigation, or another family (determines the reference file and package location)
3. **Brief description** — what it does, where it is used, and non-goals
4. **Key parameters** — state, callbacks, content slots, styles, sizes, configuration
5. **Visual model** — whether an `ElegantXxxDefaults` + `ElegantXxxColors` pair is needed; minimal components can take plain `Color` parameters (see how Divider does it)

If the user only asks to continue, choose the first planned entry whose prerequisites are available in `docs/components/index.md` and `docs/zh_CN/components/index.md`. Ask only for a decision that would change the public API.

## Step 2: Read reference files by component type

Read the relevant API section of `AGENTS.md`, pick the closest type from the table below, and read its reference source and contract test in full. Also read its showcase section and both docs pages.

| Component type | Reference (under `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/`) | Key points |
| :--- | :--- | :--- |
| Clickable filled | `button/ElegantButton.kt`, `tag/ElegantTag.kt` | controlled activation, 48dp target, interaction precedence, disabled/loading semantics, ripple |
| Icon action | `iconbutton/ElegantIconButton.kt`, `icon/ElegantIcons.kt` | required accessible name, icon ownership, compact visual inside a 48dp target |
| Container / surface | `card/ElegantCard.kt`, `surface/ElegantSurface.kt`, `list/ElegantListItem.kt` | style presets, optional clickable, caller-owned padding, 48dp rows |
| Minimal drawn (non-interactive) | `divider/ElegantDivider.kt`, `avatar/ElegantAvatar.kt`, `badge/ElegantBadge.kt` | decorative-by-default semantics, semantic tones, pure text resolution |
| Animated / custom-drawn | `progress/ElegantProgress.kt`, `switch/ElegantSwitch.kt`, `slider/ElegantSlider.kt` | infinite transitions, drag gestures, progress semantics |
| Field / form | `input/ElegantInput.kt`, `textarea/ElegantTextarea.kt` | Filled visuals, error semantics, icon slots, maxLength coercion |
| Selection / settings row | `preference/ElegantSwitchPreference.kt`, `preference/ElegantRadioPreference.kt`, `buttongroup/ElegantButtonGroup.kt` | title block + end control, grouped Colors resolved by a selected flag, checked-state precedence |
| Anchored popup / overlay | `menu/ElegantMenu.kt`, `autocomplete/ElegantAutocomplete.kt`, `modal/ElegantModal.kt` | focusable popup, dismissal contract, focus handoff |
| Data-driven collection | `table/ElegantTable.kt`, `calendar/ElegantCalendar.kt`, `tabs/ElegantTabRow.kt` | stable item models, pure resolution, grid/list rendering |
| Rendering primitive | `icon/ElegantIcons.kt`, `shape/ElegantSquircleShape.kt`, `effect/ElegantBlur.kt` | vector geometry helpers, pure shape math, no Path in JUnit |
| New family | the closest available component above | follow repository shape, define a new contract |

Use references for conventions, not for blindly copying dimensions, states, slots, or implementation.

## Step 3: Write the component source file

Locations:

```text
elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/{package}/Elegant{Name}.kt
elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/{package}/Elegant{Name}ContractTest.kt
```

Structure and conventions follow AGENTS.md (license header on new files, KDoc on every public declaration, parameter order, Defaults object, `@Immutable` Colors class, `@NonRestartableComposable`/`rememberUpdatedState`/`@Immutable`-vs-`@Stable` rules). Below are the pitfalls AGENTS.md does not spell out:

- **Verified CMP 1.11 constraints**: read the "Verified Compose Multiplatform 1.11 constraints" section of AGENTS.md first — `clickable` role/indication, `awaitPointerEventScope`, `NestedScrollConnection` as interface, no composable getters inside `remember { }`, desktop-only `BlurEffect`, `curveTo` not `cubicTo`, no `Path` in plain JUnit
- **@NonRestartableComposable** is not the default template: apply it only to thin wrappers that fully delegate and read no state themselves
- **Theming**: colors always come from `ElegantTheme.colors.*` and text styles from `ElegantTheme.typography.*`; never hardcode. Resolve state to colors in an internal pure function (`resolveXxxColors`) so `commonTest` can cover every precedence branch without a UI harness
- **Semantics**: set the correct `Role`; disabled and loading states must not invoke callbacks and must be announced; caller-configurable localized state descriptions, never buried wording
- **RTL**: directional behavior uses layout direction; mirror start/end with `LocalLayoutDirection` + `placeRelative`/`graphicsLayer`, never hardcode left/right
- **Platforms**: implement in `commonMain` first; use platform source sets only for genuine platform differences; no Android/AWT/DOM imports in common code
- **Single-file layout**: the main composable(s), the Defaults object, the Colors class, and the internal resolvers live in one file; internal metrics and resolvers stay `internal` with pure `commonTest` coverage
- **Showcase registration**: the showcase slug branch, route case, and section composable must land in the same change as the component — `docs:check` mechanically verifies the source contains `"{slug}" ->`

## Step 4: Showcase section

1. Add a showcase section in `showcase/src/commonMain/kotlin/com/elegant/compose/showcase/ElegantShowcaseApp.kt`: a `"{slug}" ->` branch in the shared `when`, the route case, and one section composable registered in `SupportedShowcaseComponentIds`. The section covers the default look, public variants, the disabled state, and the important interaction or slot, in labeled `DemoCard` groups. Use this shape:

```kotlin
@Composable
private fun XxxShowcase() {
    ShowcasePage(title = "Elegant Xxx") { compact ->
        DemoCard(
            compact = compact,
            eyebrow = "FOUNDATIONS",
            title = "...",
            description = "...",
        ) {
            // default look + public variants
        }
        DemoCard(
            compact = compact,
            eyebrow = "STATES",
            title = "...",
            description = "...",
        ) {
            // disabled state + important interaction or slot
        }
        DemoCard(
            compact = compact,
            eyebrow = "IN CONTEXT",
            title = "...",
            description = "...",
        ) {
            // realistic cross-component composition with existing components
        }
        Spacer(Modifier.height(ElegantSpacing.md))
    }
}
```

2. The same route must work on Android, Desktop, and Web; Web uses `?id={slug}`. Keep launcher modules thin and do not make an HTML-only demo.
3. Update the slug sets in `showcase/src/commonTest/kotlin/com/elegant/compose/showcase/ShowcaseContractTest.kt` and `ShowcaseRegistryTest.kt` in the same change.

## Step 5: Docs

1. **Doc pages**: `docs/components/{slug}.md` and `docs/zh_CN/components/{slug}.md` (slug all-lowercase, e.g. `icon-button`, `empty-state`). Miuix format: intro, iframe directly after it (English `src="../compose/index.html?id={slug}"`, Chinese `../../compose/index.html?id={slug}`), Import, Basic Usage, component-specific types, Component States, Properties tables, Advanced Usage. Keep the EN and zh content in one-to-one correspondence — `docs:check` mechanically verifies page sets, heading order, property-table columns, iframe placement, and Kotlin example counts.
2. **Component overview**: add one row each to the overview tables in `docs/components/index.md` and `docs/zh_CN/components/index.md`; flip the status from Planned / 计划中 to Available / 已完成.
3. **Sidebar**: add one entry to the matching group in `docs/.vitepress/config.ts` in both the English and Chinese sidebars: `{ text: 'Xxx', link: '/components/{slug}' }`, with the `/zh_CN` link prefix in the Chinese sidebar.

## Step 6: Verify and land

Run the required commands from AGENTS.md as independent invocations. Component-level gates (per worktree): library and showcase compile on Android, Desktop JVM, and Web/Wasm, desktop tests pass. Batch-level gates (KMP boundary validation, publication, the three platform samples, docs validation and build) run once per group of merged milestones; GitHub Actions is the authoritative clean environment for browser tests, Android assembly, and final artifacts. Record only commands that actually ran in VALIDATION.md. Use one coherent Conventional Commit for the component and companion changes; push and wait for CI when the user requests remote delivery.

## Checklist (self-check)

A new component lands only when all 8 are done:

1. Component source file (`elegant-ui/.../{package}/Elegant{Name}.kt`)
2. `commonTest` contract tests (`Elegant{Name}ContractTest.kt`)
3. `ElegantShowcaseApp.kt` showcase section (slug branch, route case, section composable) + registration tests
4. `docs/components/{slug}.md` + `docs/zh_CN/components/{slug}.md`
5. One row each in `docs/components/index.md` + `docs/zh_CN/components/index.md`
6. Sidebar entries in both locales of `docs/.vitepress/config.ts`
7. Library/showcase builds and desktop tests pass; docs validation and build pass
8. One coherent `feat(component):` commit; remote CI when remote delivery is requested

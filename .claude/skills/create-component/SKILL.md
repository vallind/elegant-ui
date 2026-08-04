---
name: create-component
description: >-
  Create a new Compose Multiplatform UI component for Elegant UI, including
  every required wiring point (library source, contract tests, showcase
  section + registration, EN/zh_CN docs pages, component indexes, VitePress
  sidebars). Use whenever the user wants to add a new component, create a new
  composable, scaffold a component, or add a UI element to Elegant UI — even if
  they only name the component ("add a Tag"). Triggers on "create component",
  "new component", "add a component", "scaffold component", "new composable",
  "新建组件", "添加组件", "新组件", "加个组件", "继续组件库".
---

# Create an Elegant UI Component

Add a new component to the Elegant UI library and complete every companion change beyond the component file itself — contract tests, showcase section, bilingual docs, indexes, sidebars: 8 touch points in total (see the checklist at the end).

The authoritative sources for conventions are `AGENTS.md` at the repo root (API Conventions, Key Patterns, Critical Constraints) and the reference source files listed below. This skill only describes the workflow and where the changes go; wherever it conflicts with AGENTS.md or existing source, they win — source code does not go stale, details copied into a skill do.

## Step 1: Gather requirements

Ask the user for whatever is not yet provided:

1. **Component name** — public `ElegantXxx` name and package (e.g. `ElegantTag` → `com.elegant.compose.ui.tag`)
2. **Category** — action, display, status, container, input, overlay, selection, collection, navigation, or another family (determines the reference file and package location)
3. **Brief description** — what it does, where it is used, and non-goals
4. **Key parameters** — state, callbacks, content slots, styles, sizes, configuration
5. **Whether a Colors class is needed** — most components need one; minimal components can take plain `Color` parameters (see how Divider does it)

If the user only asks to continue the library, choose the first planned entry whose prerequisites are available in `docs/components/index.md` and `docs/zh_CN/components/index.md`. Ask only for a decision that would change the public API.

## Step 2: Read reference files by component type

Before generating code, read the API Conventions section of `AGENTS.md`, pick the closest type from the table below, and read its reference source and contract test in full. Also read its showcase section and both docs pages. `references/completed-components.md` lists every available reference by responsibility.

| Component type | Reference (under `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/`) | Key points |
| :--- | :--- | :--- |
| Text action | `button/ElegantButton.kt` | controlled activation, loading, three styles and sizes, slots, 48dp target, shared action visuals |
| Icon action | `iconbutton/ElegantIconButton.kt` | required accessible name, icon ownership, compact visual inside a 48dp target |
| Identity/display | `avatar/ElegantAvatar.kt` | fallback, decorative versus named semantics, custom content slot |
| Status/count/overlay | `badge/ElegantBadge.kt` | semantic tones, count coercion and overflow, logical RTL placement, overlay without changing content measurement |
| Drawn separator | `divider/ElegantDivider.kt` | orientation, emphasis, stroke, decorative-by-default semantics |
| Label/classification | `tag/ElegantTag.kt` | four variants, three sizes, optional selectable interaction, interactive versus non-interactive semantics |
| Field / input family | `input/ElegantInput.kt`, `textarea/ElegantTextarea.kt` | Filled visuals, error semantics, icon slots, maxLength coercion |
| Selection family | `checkbox/ElegantCheckbox.kt`, `togglebutton/ElegantToggleButton.kt` | toggleable/selectable role, checked-state resolution, group propagation |
| Overlay family | `menu/ElegantMenu.kt`, `autocomplete/ElegantAutocomplete.kt` | anchored focusable popup, dismissal contract, focus handoff |
| Calendar family | `calendar/ElegantCalendar.kt` | pure date math, Monday-first grid, ElegantDate model |
| Icons / rendering | `icon/ElegantIcons.kt`, `shape/ElegantSquircleShape.kt` | vector geometry helpers, pure shape math, no Path in JUnit |
| New family | closest entry in `references/completed-components.md` | follow repository shape, define a new contract |

Use references for conventions, not for blindly copying dimensions, states, slots, or implementation.

## Step 3: Write the component source file

Locations:

```text
elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/{package}/Elegant{Name}.kt
elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/{package}/Elegant{Name}ContractTest.kt
```

Structure and conventions follow AGENTS.md (KDoc on every public declaration, parameter order, Defaults object, `@Immutable` Colors class, `@NonRestartableComposable`/`rememberUpdatedState`/`@Immutable`-vs-`@Stable` rules). New files carry the repository license header (see AGENTS.md Code Style). Below are the pitfalls AGENTS.md does not spell out:

- **Verified CMP 1.11 constraints**: read the "Verified Compose Multiplatform 1.11 constraints" section of AGENTS.md first — `clickable` role/indication, `awaitPointerEventScope`, `NestedScrollConnection` as interface, no composable getters inside `remember { }`, desktop-only `BlurEffect`, `curveTo` not `cubicTo`, no `Path` in plain JUnit
- **@NonRestartableComposable** is not the default template: apply it only to thin wrappers that fully delegate and read no state themselves
- **Theming**: colors always come from `ElegantTheme.colors.*` and text styles from `ElegantTheme.typography.*`; never hardcode. Resolve state to colors in an internal pure function (`resolveXxxColors`) so `commonTest` can cover every precedence branch without a UI harness
- **Semantics**: set the correct `Role`; disabled and loading states must not invoke callbacks and must be announced; caller-configurable localized state descriptions, never buried wording
- **RTL**: directional behavior uses layout direction; mirror start/end with `LocalLayoutDirection` + `placeRelative`/`graphicsLayer`, never hardcode left/right
- **Platforms**: implement in `commonMain` first; use platform source sets only for genuine platform differences; no Android/AWT/DOM imports in common code
- **Single-file layout**: the main composable(s), the Defaults object, and the Colors class live in one file; internal metrics and resolvers stay `internal` with pure `commonTest` coverage
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

## Step 6: Verify

- `gradle :elegant-ui:build --stacktrace --no-daemon` (compiles all three targets and runs desktop tests; also run `gradle :showcase:build --stacktrace --no-daemon` for the shared showcase)
- `cd docs && npm install && npm run docs:check && npm run docs:build`
- Run the applicable platform samples (commands in AGENTS.md Key Commands) when the environment allows

Commits follow the AGENTS.md Git Commit Style (the component and its example/docs companion changes go in one `feat(component):` commit).

## Checklist (self-check)

A new component lands only when all 8 are done:

1. Component source file (`elegant-ui/.../{package}/Elegant{Name}.kt`)
2. `commonTest` contract tests (`Elegant{Name}ContractTest.kt`)
3. `ElegantShowcaseApp.kt` showcase section (slug branch, route case, section composable) + registration tests
4. `docs/components/{slug}.md` + `docs/zh_CN/components/{slug}.md`
5. One row each in `docs/components/index.md` + `docs/zh_CN/components/index.md`
6. Sidebar entries in both locales of `docs/.vitepress/config.ts`
7. Library/showcase builds and desktop tests pass; docs validation and build pass
8. One coherent `feat(component):` commit; one row added to `references/completed-components.md`; remote CI when remote delivery is requested

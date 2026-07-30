# Elegant UI

Elegant UI is an Android UI component library built with Kotlin and Jetpack Compose. The repository contains the reusable `:elegant-ui` library, an installable `:sample` application, bilingual component documentation, and a GitHub Actions pipeline that produces an APK and AAR for physical-device validation.

The visual direction is refined, elegant, premium, restrained, modern, and precise. Figma is the visual source of truth, but unavailable Figma automation must not block implementation, CI, or device testing.

## Quick Start

- For a new component, API change, or meaningful refactor, write a short plan before editing and keep it aligned with the work.
- Read the closest existing component and its English/Chinese documentation before creating a new API.
- Complete one component end to end. Do not begin the next V1 component until the current one has passed CI and physical-device validation.
- Run the relevant Gradle checks before handoff. Do not report a component as complete while a required check is failing or unverified.
- When library or Android APIs may have changed, consult current official Android, Kotlin, Compose, and Gradle documentation rather than relying on memory.

## Key Commands

The repository currently uses the system Gradle executable in CI. Use the Gradle Wrapper instead when it is added to the repository.

| Action | Command |
| :--- | :--- |
| Build sample APK | `gradle :sample:assembleDebug --stacktrace --no-daemon` |
| Build library AAR | `gradle :elegant-ui:assembleRelease --stacktrace --no-daemon` |
| Build both deliverables | `gradle :sample:assembleDebug :elegant-ui:assembleRelease --stacktrace --no-daemon` |
| Run verification tasks | `gradle check --stacktrace --no-daemon` |
| Run Android lint | `gradle lint --stacktrace --no-daemon` |
| Install sample on a connected device | `gradle :sample:installDebug` |
| List available tasks | `gradle tasks` |
| Run documentation website | `cd docs && npm install && npm run docs:dev` |
| Validate bilingual website registration | `cd docs && npm run docs:check` |
| Build documentation website | `cd docs && npm install && npm run docs:build` |

GitHub Actions is the authoritative clean build until the repository includes a checked-in Gradle Wrapper and local Android SDK configuration is known to be equivalent.

## Repository Structure

| Path | Purpose |
| :--- | :--- |
| `elegant-ui/` | Reusable Android library and all public Elegant UI APIs |
| `elegant-ui/src/main/java/com/elegant/compose/ui/theme/` | Color scheme, theme entry point, spacing, radius, and future design tokens |
| `elegant-ui/src/main/java/com/elegant/compose/ui/<component>/` | One package per component family, such as `button/` |
| `sample/` | Installable physical-device demo application |
| `docs/` | VitePress documentation website root and English content |
| `docs/components/` | English component pages; one Markdown page per delivered component |
| `docs/zh_CN/` | Simplified Chinese mirror of English guide and component pages |
| `docs/.vitepress/` | Website configuration, custom theme, globally registered demos, and styles |
| `.github/workflows/android.yml` | Clean APK/AAR build and artifact upload |
| `.github/workflows/docs.yml` | VitePress build and GitHub Pages deployment |
| `PROJECT_BRIEF.md` | Locked project scope, V1 list, and project-level design/API principles |
| `FLOW.md` | Delivery sequence from contract through device acceptance |
| `VALIDATION.md` | Current physical-device acceptance checklist |
| `.claude/skills/` | Repeatable repository workflows for Claude-compatible agents |

## Source Layout

Library packages live under:

```text
elegant-ui/src/main/java/com/elegant/compose/ui/
├── theme/       # Foundations and semantic tokens
├── button/      # Button family
└── <component>/ # Future component families
```

Keep public component APIs, their defaults, and their state/color models close together. Extract a shared utility only after at least two components need the same behavior and the abstraction has a clear semantic name.

## Design and Token Rules

- Components consume semantic values from `ElegantTheme`, `ElegantSpacing`, `ElegantRadius`, and component-specific defaults.
- Raw color values belong in theme/foundation files. Do not scatter hexadecimal colors through component implementations or demos.
- Material 3 may be used as Android/Compose infrastructure, but Elegant UI owns its visual contract, tokens, states, and public API. Do not expose an unmodified Material component as an Elegant UI component.
- Light and dark themes are equal requirements. Every state and component variant must be checked in both themes.
- Preserve a calm hierarchy: one dominant primary action, restrained secondary containers, and low-emphasis tertiary actions.
- Use the Figma specification when available. When Figma automation is unavailable, use the locked contract and continue through Compose, CI, and physical-device validation.
- Code, KDoc, and public component names must use Elegant UI terminology and must not claim to be another vendor's design system.

## Kotlin and Compose Style

- Use explicit `public` for public API declarations, matching the current source.
- Use trailing commas in multiline declarations and calls.
- Prefer immutable state models. Mark truly immutable public or internal value classes with `@Immutable`.
- Do not annotate a class `@Immutable` when it contains mutable state, mutable collections, or behavior whose equality is not stable.
- Keep composables focused. Move pure state-to-metrics and state-to-visual mapping into private functions when that makes the state matrix easier to review.
- Use `remember` only for values that should survive recomposition or are expensive to recreate. Include every value read by a calculation in its key set.
- Use `rememberUpdatedState` only to prevent stale captures inside long-lived effects or remembered callbacks. Do not add it when a callback is directly forwarded to a child composable.
- Animation labels must be stable and descriptive.
- Avoid unnecessary intrinsic measurement and custom layout. Use a custom `Layout` only when standard Compose layout primitives cannot satisfy the contract and document the measurement invariant.
- Keep imports sorted and remove unused imports before handoff.

## Public API Conventions

### Composable Parameter Order

Use this order unless the closest existing component demonstrates a stronger component-specific reason:

```kotlin
@Composable
public fun ElegantComponent(
    // 1. Required behavior and state
    onClick: () -> Unit,
    // 2. Modifier
    modifier: Modifier = Modifier,
    // 3. Structural variants
    style: ElegantComponentStyle = ElegantComponentStyle.Primary,
    size: ElegantComponentSize = ElegantComponentSize.Medium,
    // 4. State flags
    enabled: Boolean = true,
    loading: Boolean = false,
    // 5. Optional visual/content slots
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    // 6. Main content, always last
    content: @Composable () -> Unit,
)
```

Required state values such as `checked`, `selected`, `value`, or `expanded` belong before `modifier`. Their callbacks should remain adjacent when that improves API comprehension.

### Defaults Objects

A component with reusable dimensions, shapes, motion, or colors should expose an `ElegantXxxDefaults` object instead of adding unrelated optional parameters to the main composable.

```kotlin
public object ElegantComponentDefaults {
    public val MinTouchHeight: Dp = 48.dp
    public val MediumHeight: Dp = 40.dp

    @Composable
    public fun colors(
        containerColor: Color = ElegantTheme.colors.surfaceRaised,
        contentColor: Color = ElegantTheme.colors.textPrimary,
    ): ElegantComponentColors = remember(containerColor, contentColor) {
        ElegantComponentColors(
            containerColor = containerColor,
            contentColor = contentColor,
        )
    }
}
```

Use public customization only when it represents a stable design-system decision. Keep implementation details private.

### Colors and State Models

```kotlin
@Immutable
public data class ElegantComponentColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
)
```

- Resolve visual state centrally rather than spreading `if (enabled)` and `if (pressed)` branches through the layout tree.
- Prefer semantic names such as `containerColor`, `contentColor`, `focusRingColor`, and `errorColor`.
- Do not expose raw Figma token IDs or implementation-only primitive names in the public API.

### Content Slots

- Main content lambdas are last.
- Slot APIs use `@Composable () -> Unit` unless a receiver scope is genuinely needed.
- Leading and trailing icons are optional slots, not enum variants.
- Decorative icons use `contentDescription = null`. Add a description only when the icon communicates information absent from the visible label.

## State, Interaction, and Accessibility

- Interactive components must expose the correct Compose semantics role and state.
- The minimum touch target is 48dp even when the visible control is smaller.
- Disabled and loading states must prevent duplicate activation.
- Loading keeps the control's semantic role and exposes an understandable state description.
- Focus must be visible and must not rely only on a subtle color shift.
- Pressed feedback should be perceptible but restrained.
- Toggle-like controls emit haptics from the user interaction callback, never merely because external state changed.
- Directional affordances must respect RTL. Test icon direction and start/end padding with RTL layout direction.
- Verify increased font scale and landscape layout; avoid fixed widths that clip localized or scaled text.
- Do not use color as the only signal for error, selection, or disabled state when another visual or semantic signal is appropriate.

## Component Delivery Workflow

Use `.claude/skills/create-component/SKILL.md` for the detailed procedure. Every component must close the following loop:

1. Lock the component name, category, API, states, sizes, and visual contract.
2. Add or update semantic/component tokens.
3. Implement the library source and public KDoc.
4. Add a sample-app demo and register it in the sample experience.
5. Add matching English and Simplified Chinese website pages under `docs/components/` and `docs/zh_CN/components/`.
6. Register the component in both website sidebars and both component overview pages.
7. Add or update an interactive website preview when the component can be represented faithfully without pretending it is the Android runtime.
8. Update milestone/status and physical-device validation material where applicable.
9. Pass the VitePress documentation build and the clean Android GitHub Actions build that produces the sample APK and library AAR.
10. Install the APK on a physical Android device and record acceptance or actionable defects.

A source file alone is not a completed component.

## Documentation Website Rules

- The website uses VitePress. English pages live at `docs/components/{slug}.md`; Simplified Chinese pages live at `docs/zh_CN/components/{slug}.md`.
- Guide pages follow the same mirror rule under `docs/guide/` and `docs/zh_CN/guide/`.
- English and Chinese pages must have the same section order, code examples, API names, tables, and behavioral claims.
- Every delivered component must be registered in the English and Chinese sidebars in `docs/.vitepress/config.ts` and in both component overview pages.
- Component pages should follow the mature-library structure: purpose, interactive or visual demo, import, basic usage, variants, sizes, states, parameters, defaults/colors when public, advanced usage, accessibility, and physical-device checks.
- Website previews are documentation aids. They must not claim to be the Android Compose runtime, and they must not replace the sample APK or physical-device gate.
- Reuse globally registered Vue preview components from `docs/.vitepress/theme/components/` when several pages need the same documentation UI pattern.
- Code examples must compile against the current public API. Do not document planned parameters as if they already exist.
- Use `dp`, `sp`, API identifiers, and enum values consistently across both languages.
- Run `cd docs && npm run docs:check` and `npm run docs:build` before handoff. A Markdown-only change is incomplete if navigation, locale routing, or the VitePress build is broken.
- Update README website links and current milestone information when a component becomes the active or accepted milestone.

## Testing and Verification

Before handoff, perform every applicable check:

1. `gradle check --stacktrace --no-daemon`
2. `gradle lint --stacktrace --no-daemon`
3. `gradle :sample:assembleDebug :elegant-ui:assembleRelease --stacktrace --no-daemon`
4. `cd docs && npm install && npm run docs:build`
5. Confirm expected APK, AAR, and `docs/.vitepress/dist/` output paths exist.
6. Inspect both **Android Build** and **Documentation** GitHub Actions rather than assuming local static checks prove clean builds or deployment.
7. Install the APK on a physical device and complete `VALIDATION.md` for the active milestone.

When a check cannot run, state exactly why and leave it unverified. Do not replace a missing compiler/build result with a syntax-only claim.

## Critical Constraints

- Do not start the next V1 component before the active component passes the agreed physical-device gate.
- Do not hardcode component colors outside the theme/token layer.
- Do not reduce the interactive root below a 48dp touch target.
- Do not keep a button or action clickable while loading.
- Do not add English-only or Chinese-only component documentation.
- Do not add a component website page without updating both locale sidebars and both component overview pages.
- Do not describe a web preview as the actual Android Compose implementation.
- Do not silently change a locked public API to make an implementation easier.
- Do not copy source, package names, visual assets, or branding from reference component libraries. References are for engineering process and quality standards only.
- Do not edit generated build outputs or commit `.gradle/`, `build/`, IDE state, or `local.properties`.
- Do not claim physical-device validation without a named device/Android version and recorded observations.

## Git Commit Style

Use Conventional Commit style with a narrow scope:

```text
feat(button): add loading state
feat(component): add icon button closed loop
fix(button): preserve 48dp touch target
fix(theme): correct dark focus ring contrast
docs(button): align English and Chinese examples
chore(ci): upload APK checksums
chore(repo): add agent guidance
```

For a new component milestone, keep the component source, sample wiring, bilingual docs, and validation changes in one coherent commit unless the change is too large to review safely.

## Definition of Done

A component is done only when:

- The public API and visual/state contract are implemented.
- Theme values are semantic and Light/Dark behavior is complete.
- Semantics, focus, disabled/loading behavior, RTL, and touch target are addressed.
- The sample exposes the meaningful variants and states.
- English and Chinese website pages match the implementation and each other.
- Both locale sidebars and component overview pages register the component.
- The VitePress site builds successfully and the Documentation workflow can deploy it.
- Clean Android CI produces both APK and AAR artifacts.
- Physical-device results are recorded and accepted.
- No unrelated component work is bundled into the milestone.

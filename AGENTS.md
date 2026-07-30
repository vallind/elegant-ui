# Elegant UI repository guidance

Elegant UI is a refined Compose Multiplatform component library. Android, Desktop JVM, and Web/Wasm are supported targets. The repository contains the published `:elegant-ui` KMP library, a shared `:showcase`, three platform launchers, synchronized English and Simplified Chinese documentation, and GitHub Actions for multiplatform artifacts and GitHub Pages.

## Platform contract

- **Android:** supported, API 24+
- **Desktop JVM:** supported on current 64-bit macOS, Windows, and Linux environments supported by Compose Multiplatform
- **Web/Wasm:** supported on modern browsers with WasmGC
- **iOS:** out of scope; do not add iOS targets, source sets, APIs, samples, or claims without a separate approved milestone
- A component is incomplete when it fails to compile or demonstrate its public contract on any supported target.
- Keep the public API shared. Platform-specific behavior may adapt input modality and system integration, but not silently change component meaning.

## Authority and scope

- This file is the single repository-wide engineering authority. `CLAUDE.md` references it.
- Read the closest source, tests, sample, docs pages, and workflows before editing.
- Preserve the project’s original design language. Reference mature projects for process and quality, never for brand or copied implementation.
- Work on one component milestone at a time. Do not begin the next component before all required gates pass.
- Verify unstable tooling facts against current official Kotlin, Compose Multiplatform, Android, Gradle, VitePress, and GitHub Actions documentation.

## Change workflow

- For a significant component, foundation, or release change, keep a short working plan updated until the milestone closes.
- Use `.claude/skills/create-component/SKILL.md` for new components, material component refinements, and unnamed “continue the library” requests.
- Before changing an available component, build an impact map covering its public API, pure contract logic, shared primitives, showcase route, both documentation pages, and downstream completed components.
- For a bug fix, reproduce the failure with the narrowest meaningful test or showcase state, fix the lowest responsible layer, and keep a regression test when the behavior can be expressed deterministically.
- For a public API or behavior change, update source, tests, showcase usage, English and Chinese API documentation, and validation notes in the same milestone.
- Do not mix opportunistic refactors, dependency upgrades, unrelated component work, or formatting churn into a component milestone.
- Use the progress indexes as the scope ledger and the create-component skill’s completed-reference catalog as the implementation routing table. Add a newly completed component to that catalog in the same milestone.

## Component library evolution

The existing per-component workflow and definition of done remain the delivery authority. Library growth adds sequencing and reuse rules; it does not weaken or replace any component gate.

- `docs/components/index.md` and `docs/zh_CN/components/index.md` are the synchronized V1 scope and progress ledger.
- Keep component names, groups, purposes, order, and statuses aligned between both indexes.
- Mark a component `Available` / `已完成` only in the milestone that satisfies the complete component definition of done.
- A `Planned` component does not justify placeholder APIs, empty tests, stub documentation pages, or speculative source sets.
- Keep exactly one active component milestone. When asked to continue the library without naming a component, select the first planned component whose dependencies are already stable.
- A specifically requested component may take priority, but any missing shared prerequisite must be completed inside the same milestone or called out before implementation.
- Finish, validate, document, commit, push, and observe both workflows for the active milestone before selecting the next component.

Build the library in dependency-aware layers:

1. **Foundations:** semantic colors, typography, spacing, shape, motion, elevation, interaction, focus, selection, field, collection, and overlay primitives.
2. **Actions and simple display:** Button is the completed reference; then IconButton, Divider, Badge, Avatar, and Tag as their dependencies allow.
3. **Fields and selection:** Input, Checkbox, Radio, Switch, Slider, and Select, sharing field, validation, selection, and keyboard contracts.
4. **Surfaces and feedback:** Card, List, EmptyState, Tooltip, Modal, and Drawer, sharing surface, focus restoration, dismissal, and overlay behavior.
5. **Data and navigation:** Table, Tabs, Breadcrumb, Navbar, and Sidebar, reusing collection, selection, focus, and adaptive-layout primitives.

Add a shared primitive only when the active component needs it. Keep its contract narrow, test it independently, and migrate completed components when the new primitive replaces duplicated behavior. Do not build an unused framework in anticipation of future components.

For every component family:

- reuse established size names, interaction states, semantic colors, motion curves, icon metrics, and accessibility vocabulary;
- keep equivalent parameters in the same relative signature order;
- prefer shared internal resolvers over copied state precedence;
- test representative cross-component composition, not only isolated rendering;
- keep customization intentional through Defaults, Colors, state models, and named slots without exposing implementation internals.

Button is the initial repository reference for delivery shape and action-component quality. Future completed components become the preferred reference for their own family. Copy the closed-loop structure—source, tests, showcase, documentation, validation, and publication—not component-specific code or assumptions.

Figma is not part of the default repository workflow. Do not create, connect, read, or update Figma assets unless the user explicitly approves a separate Figma milestone.

No screenshot or visual-evidence artifact is a repository completion gate. Perform visual and interaction review on the real launchers when available, record the result or limitation in `VALIDATION.md`, and do not require committed screenshots, pixel-diff baselines, or a separate evidence approval step.

## Repository map

| Path | Responsibility |
| --- | --- |
| `elegant-ui/src/commonMain/` | Public components, tokens, state, defaults, semantics, shared behavior |
| `elegant-ui/src/commonTest/` | Platform-independent contract and pure-state tests |
| `elegant-ui/src/androidMain/` | Android-only adapters and manifest |
| `elegant-ui/src/desktopMain/` | Desktop-only adapters when genuinely required |
| `elegant-ui/src/wasmJsMain/` | Browser-only adapters when genuinely required |
| `showcase/src/commonMain/` | Shared component gallery and component-slug registry |
| `sample/` | Android application launcher and physical-device validation |
| `desktop-sample/` | Desktop JVM launcher and keyboard/mouse/window validation |
| `web-sample/` | Compose Web/Wasm launcher and documentation iframe runtime |
| `docs/` | VitePress website, English root and `zh_CN` mirror |
| `scripts/validate-kmp-boundaries.sh` | Source-set and platform-target boundary checks |
| `.github/workflows/android.yml` | Multiplatform build and artifact workflow |
| `.github/workflows/docs.yml` | Compose Web demo + VitePress Pages workflow |

## Required commands

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

Only claim a command passed when it actually ran successfully. GitHub Actions is the authoritative clean environment when local Android SDK, browser runtime, or packaging tools are unavailable.

## Source-set policy

1. Put public Composables, theme values, state models, defaults, semantic behavior, and reusable animation/layout logic in `commonMain`.
2. Use `androidMain`, `desktopMain`, or `wasmJsMain` only for APIs that cannot be expressed with common Compose Multiplatform APIs.
3. Define the smallest stable common contract before adding platform implementations.
4. Prefer dependency injection or a narrow interface. Use `expect`/`actual` only when compile-time specialization is the clearest design.
5. Never recreate `elegant-ui/src/main` or `showcase/src/main`.

Common/public code must not expose or import:

- Android framework types such as `Context`, `Activity`, `Drawable`, `View`, or resource IDs
- Desktop-only AWT or Swing types
- browser DOM, `kotlinx.browser`, or `org.w3c.dom` types
- platform path, window, clipboard, or lifecycle objects without a narrow abstraction

`androidx.compose.*` APIs are allowed only when the artifact is available to Compose Multiplatform `commonMain`.

## Component API contract

Use this public parameter order:

1. required behavior/state parameters
2. `modifier: Modifier = Modifier`
3. state flags and controlled values
4. visual configuration such as style, size, colors, shape
5. optional slots
6. primary `content` lambda last

Example:

```kotlin
@Composable
public fun ElegantComponent(
    value: Value,
    onValueChange: (Value) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    style: ElegantComponentStyle = ElegantComponentStyle.Default,
    colors: ElegantComponentColors = ElegantComponentDefaults.colors(),
    leadingContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
)
```

Requirements:

- Add public KDoc to every public declaration.
- Use `@Immutable` only for values that are actually immutable.
- Complex visual APIs expose `ElegantXxxDefaults` and `ElegantXxxColors` or an equivalent immutable state model.
- Centralize state-to-visual resolution.
- Do not expose an unmodified Material component as the Elegant UI public contract.
- Preserve binary/source compatibility deliberately; document breaking changes during `0.x`.

Additional API rules:

- Prefer controlled state plus callbacks for mutable behavior. Convenience state holders may be added only when their ownership and save/restore behavior are explicit.
- Avoid boolean combinations that permit contradictory states. Use a stable enum or immutable state model when states are mutually exclusive.
- Keep user-facing semantic descriptions caller-configurable and localizable; do not bury required accessibility wording in private implementation.
- Apply the caller’s `modifier` exactly once to the public root unless the KDoc explicitly documents a different contract.
- Keep slot responsibilities precise. Components own spacing, alignment, default content color, and default text style around slots; slot content owns its internal drawing.
- Expose `interactionSource` only when observing or controlling interactions is part of the useful public contract, and keep its null/default convention aligned within the component family.
- Prefer additive overloads or defaulted trailing parameters. Avoid overloads whose lambda or default combinations are ambiguous at Kotlin call sites.
- Keep implementation-only metrics and resolvers internal. Public customization should express product intent rather than expose layout machinery.

## State and interaction contract

Freeze state precedence before implementation. For competing interaction visuals, use this baseline unless the family contract documents a reason to differ:

1. disabled or transition-locked interaction;
2. pressed or dragged;
3. keyboard focused;
4. pointer hovered;
5. resting.

Selected, checked, error, loading, expanded, and indeterminate are semantic states, not interchangeable interaction visuals. Define how they combine with the precedence above, ensure they remain announced where applicable, and test the resolver directly.

- Disabled and loading states must not invoke callbacks.
- Loading must preserve layout where replacement content would otherwise cause visible jumps.
- Focus treatment must remain visible in Light and Dark themes and must not rely only on color when another affordance is practical.
- Hover effects may enrich Desktop and Web but must not be required to understand or operate the component.
- Press motion must not shrink the actual hit target or move neighboring layout.
- Directional behavior must use layout direction; do not hardcode left/right when start/end is intended.
- Components that open overlays must define dismissal, focus capture, focus restoration, escape/back handling, and outside-click behavior before implementation.

## Compose implementation discipline

- Keep side effects out of composition. Use effects only for lifecycle-bound work and key them only with values read by the effect.
- Use `rememberUpdatedState` for values read by long-lived effects or remembered callbacks; do not add it to ordinary direct callback forwarding.
- Use `@NonRestartableComposable` only for thin delegates that read no state themselves.
- `@Immutable` requires genuinely immutable fields. Lambda-bearing models are not immutable merely because their properties are `val`.
- Standard `List`, `Set`, and `Map` parameters are unstable to Compose. Prefer stable models, immutable collections when already supported, or clearly documented caller-owned identity; do not add a dependency solely to decorate one API.
- Avoid unnecessary intrinsic measurement, subcomposition, allocation in drawing/layout loops, and state objects recreated on every recomposition.
- Hoist reusable pure logic out of composables so state precedence, formatting, measurement choices, and fallback behavior can be tested in `commonTest`.
- Reuse Material or foundation implementation primitives internally only when they are available to all supported targets and can be fully themed and semantically adapted. Elegant UI remains the public contract.
- Add a new dependency only when the active component cannot reasonably use existing Compose/Kotlin facilities; verify Android, Desktop, and Wasm compatibility before accepting it.

## Foundations and visual rules

- Use semantic values from `ElegantTheme`; raw colors belong only in foundation/theme files.
- Use a 4dp spacing grid unless the component contract explicitly documents an exception.
- Support Light and Dark themes.
- Preserve hierarchy, contrast, typography, radius, and motion across all supported targets.
- The interactive root must meet a 48dp minimum target unless a stricter platform rule applies.
- Loading or transition states must prevent duplicate activation.
- Do not encode platform-specific input assumptions into shared visuals.

Refinement is part of implementation, not a later re-skin:

- establish one clear primary element and keep secondary information quieter;
- tune optical alignment, icon-to-label spacing, baseline rhythm, and shape proportions instead of relying only on geometric centering;
- keep component density intentional across size variants rather than scaling every metric by one factor;
- make hover, focus, press, selected, error, disabled, and loading differences visible but restrained;
- test long content, empty content, custom slots, RTL, large font scale, narrow width, and dark surfaces where the contract permits them;
- avoid decorative borders, shadows, gradients, or motion that do not communicate hierarchy or state;
- demonstrate at least one realistic composition with already available components when the component is designed to compose with them.

## Accessibility and input

Every interactive component must define correct Compose semantics, role, selected/disabled/error/loading state, and useful state descriptions.

Validate by platform:

- **Android:** touch, hardware keyboard, TalkBack, font scale, density, RTL, Light/Dark.
- **Desktop:** mouse hover/press, keyboard activation, focus traversal, high DPI, window resize, Light/Dark.
- **Web/Wasm:** pointer, keyboard activation, browser focus, viewport resize, browser zoom, Light/Dark, supported screen-reader behavior where applicable.

A browser visual approximation is not accepted. The documentation iframe is built from the real `:web-sample` Compose Web/Wasm application.

## Shared showcase contract

`showcase/src/commonMain/.../ElegantShowcaseApp.kt` is the single component-demo registry.

For every new component:

- add a stable lowercase slug;
- register the slug in the shared `when`/registry;
- render the same state matrix on Android, Desktop, and Web;
- show public variants in labeled groups and include at least one realistic cross-component composition when applicable;
- keep content responsive without assuming a fixed documentation iframe width;
- keep platform launchers thin;
- avoid duplicating component demo UI in launcher modules.

Platform launchers may supply platform context such as the Web query parameter, window title, or Android Activity lifecycle, but must call the shared showcase.

## Miuix-format component documentation

Each component creates both:

```text
docs/components/{slug}.md
docs/zh_CN/components/{slug}.md
```

Required top-level order:

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

Rules:

- Place the iframe directly after the introduction; do not add a separate Demo heading.
- Use `../compose/index.html?id={slug}` for English and `../../compose/index.html?id={slug}` for Chinese.
- The iframe must load the real Compose Web/Wasm build generated from `:web-sample`.
- Property tables use property name, type, description, default value, required.
- Document public parameters in signature order and cover all public enums, Defaults, Colors, constants, and factories users need.
- Keep English and Chinese Kotlin example counts and API identifiers aligned.
- Update both sidebars and both component indexes.

## Testing requirements

For each component:

- add `commonTest` coverage for stable enums, pure state resolution, and contract logic;
- add platform tests when behavior depends on a platform adapter;
- compile all three targets;
- exercise the shared showcase on all three launchers;
- verify Web query routing for the component slug;
- keep platform-specific validation recorded in `VALIDATION.md`.

Do not add empty or tautological tests merely to satisfy a file-count requirement.

For shared foundations and component families:

- run the tests and showcase routes of every completed component affected by a token, resolver, or primitive change;
- add regression coverage when fixing a state-priority, sizing, focus, selection, dismissal, or adaptive-layout defect;
- test representative compositions such as icon plus label, field plus validation, selection inside a collection, and overlay trigger plus focus restoration when those contracts exist;
- keep manual platform acceptance explicit where automation is not yet available.

Test behavior rather than implementation trivia:

- pure resolvers cover every state and precedence branch;
- enums and defaults cover stable public values that consumers may depend on;
- fallback, formatting, range coercion, selection, and dismissal logic cover boundaries and invalid-but-representable input;
- layout tests are required only when a layout contract can regress and the repository has a reliable cross-platform harness;
- do not assert private call order, animation frame-by-frame values, or exact implementation structure unless those are the contract.

## CI and artifacts

The **Multiplatform Build** workflow must produce:

- `elegant-ui-maven-repository` containing root KMP metadata and Android/Desktop/Web publications
- `elegant-ui-android-aar`
- `elegant-ui-android-sample`
- `elegant-ui-desktop-sample-linux`
- `elegant-ui-web-sample`

The **Documentation** workflow must:

1. build `:web-sample:wasmJsBrowserDistribution`;
2. copy the real distribution into `docs/public/compose`;
3. run documentation validation;
4. build VitePress;
5. deploy GitHub Pages on `main` pushes.

## Consumer contract

Supported same-build dependency:

```kotlin
implementation(project(":elegant-ui"))
```

Supported KMP publication dependency:

```kotlin
commonMain.dependencies {
    implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
}
```

Standalone Android apps may use the same coordinate in `dependencies`. Do not instruct consumers to guess target-suffixed artifacts or copy only an AAR for normal integration.

## API and release evolution

- Treat every public declaration as a library-wide contract, even during `0.x`.
- Prefer additive changes and defaulted trailing parameters. Do not reorder or silently reinterpret existing parameters.
- Deprecate before removal whenever compatibility permits, and document the migration in both locales.
- Review all completed components when changing shared tokens, typography, shapes, motion, interaction semantics, or foundation state models.
- Keep component-family naming consistent; do not introduce synonyms for an existing style, size, state, or slot concept without a migration reason.
- Avoid dependency cycles between foundations and components. Higher-level components may depend on lower-level primitives, never the reverse.
- Add automated API/binary compatibility validation as a dedicated release milestone before declaring a stable `1.0` API.
- A release is not ready when a component marked available is missing from the publication, showcase registry, bilingual documentation, or either workflow artifact set.

## Component definition of done

A component lands only when:

1. Public API, states, visual contract, and slug are locked.
2. Shared implementation and KDoc are complete.
3. Platform types do not leak into common/public API.
4. Semantic tokens, Defaults/Colors, state resolution, and tests are complete.
5. KMP boundary validation passes.
6. Shared showcase registration covers all public variants and states.
7. Android, Desktop, and Web launchers compile and display the component.
8. Android touch/TalkBack checks are accepted.
9. Desktop keyboard/mouse/focus/window checks are accepted.
10. Web keyboard/pointer/focus/viewport checks are accepted.
11. English and Chinese Miuix-format pages match the real API.
12. The real Compose Web iframe, both sidebars, and both indexes are updated.
13. Maven publication contains all supported variants.
14. Both GitHub Actions workflows succeed.
15. No unrelated next-component or iOS work is included.

## V1 library definition of done

Elegant UI V1 is complete only when:

1. Every component listed in both component indexes is marked available in both locales.
2. Every available component independently satisfies the component definition of done.
3. Shared foundations replace avoidable family-level duplication and have direct contract tests.
4. Action, field, selection, surface, overlay, collection, feedback, and navigation families use consistent naming and state precedence.
5. Representative cross-component compositions work on Android, Desktop JVM, and Web/Wasm.
6. All public APIs remain consumable from the root KMP publication coordinate.
7. The complete shared showcase is navigable by stable slugs on all three launchers.
8. English and Chinese indexes, sidebars, component pages, API identifiers, and examples remain synchronized.
9. Full-library builds, publications, samples, documentation validation, and both GitHub Actions workflows succeed together.
10. No planned V1 component is represented by a placeholder implementation or documentation-only claim.

## Commit style

Use one coherent Conventional Commit per milestone:

```text
feat(component): add icon button across android desktop and web
fix(button): preserve keyboard focus on web
build(kmp): repair desktop and wasm publications
```

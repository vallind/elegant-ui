# Completed component references

Use this catalog only after reading `AGENTS.md` and both component progress indexes. An entry belongs here only when the component is marked Available / 已完成 and its milestone passed both workflows.

Choose references by responsibility. A milestone may use one component for behavior, another for layout or semantics, and a third for delivery wiring. Read the selected source and contract test in full; then read its showcase section, English page, Chinese page, and relevant theme or internal primitive.

## Available references

| Reference | Use it for | Important contracts | Files |
| --- | --- | --- | --- |
| Button | Text actions, loading actions, action emphasis, leading/trailing slots | controlled activation, 48dp target, interaction precedence, three styles and sizes, loading layout stability, shared action visuals | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/button/ElegantButton.kt`, `elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/button/ElegantButtonContractTest.kt`, `docs/components/button.md`, `docs/zh_CN/components/button.md` |
| IconButton | Compact or icon-only actions | required accessible name, icon ownership, compact visual inside a 48dp target, loading and interaction parity with Button | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/iconbutton/ElegantIconButton.kt`, `elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/iconbutton/ElegantIconButtonContractTest.kt`, `docs/components/icon-button.md`, `docs/zh_CN/components/icon-button.md` |
| Avatar | Non-interactive identity and simple display components | content fallback, decorative versus named semantics, size-specific typography, custom content slot, pure text resolution | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/avatar/ElegantAvatar.kt`, `elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/avatar/ElegantAvatarContractTest.kt`, `docs/components/avatar.md`, `docs/zh_CN/components/avatar.md` |
| Badge | Compact status, counts, and content overlays | non-interactive semantics, semantic status tones, count coercion and overflow, logical RTL placement, overlay composition without changing content measurement | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/badge/ElegantBadge.kt`, `elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/badge/ElegantBadgeContractTest.kt`, `docs/components/badge.md`, `docs/zh_CN/components/badge.md` |
| Divider | Non-interactive boundaries and labeled content separation | horizontal and bounded vertical orientation, subtle/strong emphasis, solid/dashed strokes, decorative-by-default semantics, logical RTL label placement | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/divider/ElegantDivider.kt`, `elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/divider/ElegantDividerContractTest.kt`, `docs/components/divider.md`, `docs/zh_CN/components/divider.md` |
| Tag | Label or classification with optional selection | four variants, three sizes, selectable interaction, interactive versus non-interactive semantics, 48dp target inside compact pill | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/tag/ElegantTag.kt`, `elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/tag/ElegantTagContractTest.kt`, `docs/components/tag.md`, `docs/zh_CN/components/tag.md` |
| Tooltip | Non-modal annotation overlays | logical placements, delayed hover/focus/long-press reveal, pure position clamping, non-focusable popup | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/tooltip/ElegantTooltip.kt`, `elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/tooltip/ElegantTooltipContractTest.kt`, `docs/components/tooltip.md`, `docs/zh_CN/components/tooltip.md` |
| Input | Text entry with labels and validation | Filled/Outlined styles, maxLength truncation, error semantics, leading/trailing slots, focus ring | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/input/ElegantInput.kt`, `elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/input/ElegantInputContractTest.kt`, `docs/components/input.md`, `docs/zh_CN/components/input.md` |
| Checkbox | Boolean selection | toggleable role, animated checkmark path, disabled/pressed/hovered precedence, optional label | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/checkbox/ElegantCheckbox.kt`, `elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/checkbox/ElegantCheckboxContractTest.kt`, `docs/components/checkbox.md`, `docs/zh_CN/components/checkbox.md` |
| Radio | Exclusive single choice | selectable role, animated inner dot, disabled/pressed/hovered precedence, optional label | `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/radio/ElegantRadio.kt`, `elegant-ui/src/commonTest/kotlin/com/elegant/compose/ui/radio/ElegantRadioContractTest.kt`, `docs/components/radio.md`, `docs/zh_CN/components/radio.md` |

## Shared references

- Action components must also read `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/internal/action/` and its `commonTest` coverage before adding or changing interaction-state resolution.
- Every component reads the relevant files under `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/theme/`; add tokens only when the active contract needs them.
- Every component reads `showcase/src/commonMain/kotlin/com/elegant/compose/showcase/ElegantShowcaseApp.kt` and the showcase routing tests because the registry is shared.
- Every component reads `VALIDATION.md`, `.github/workflows/android.yml`, and `.github/workflows/docs.yml` before closing the milestone.

## Selection guide

- For a new action, start with Button or IconButton and reuse the shared action resolver when its state model matches.
- For a non-interactive display primitive, start with Avatar and remove identity-specific assumptions.
- For compact status, numeric overflow, or corner overlays, start with Badge and preserve its non-interactive and logical-placement contracts.
- For a component family not represented here, use the closest available component for repository delivery shape, inspect current Compose Multiplatform primitives, and define the new family contract inside the active milestone.
- Do not copy dimensions, styles, state names, or slots merely because a reference has them. Reuse conventions only when they express the same product meaning.

## Maintenance rule

When a milestone makes a component available, add one concise row describing what future work should learn from it. When a component contract materially changes, update its row in the same commit. Never add planned components as aspirational references.

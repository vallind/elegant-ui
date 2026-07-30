# Physical-device validation checklist

Build: Button KMP-ready Android milestone `0.1.0-SNAPSHOT`

## Environment record

- Device model:
- Android version:
- Display size/density:
- Display scale:
- Font scale:
- APK workflow run / commit:
- Observations:

## Installation and integration

- [ ] Latest `elegant-ui-sample-apk` artifact installs and launches
- [ ] Sample is built against `project(":elegant-ui")` after the commonMain migration
- [ ] No crash during Light/Dark switching
- [ ] No missing classes or resources from the KMP Android publication

## Button visual and interaction checks

- [ ] Primary, Secondary, and Tertiary hierarchy is clear
- [ ] Small, Medium, and Large proportions feel balanced
- [ ] Every button is easy to tap one-handed and the interactive root is at least 48dp
- [ ] Pressed feedback is visible but restrained
- [ ] Focus feedback remains visible in Light and Dark themes
- [ ] Loading prevents duplicate taps and exposes a meaningful state description
- [ ] Disabled state is recognizable and cannot activate
- [ ] Leading/trailing icons align optically with labels
- [ ] Custom `ElegantButtonColors` do not break contrast or focus indication

## Adaptation and accessibility

- [ ] Text remains readable at increased system font scale
- [ ] Layout remains usable in landscape
- [ ] Directional content behaves correctly in RTL
- [ ] TalkBack announces button role, disabled/loading state, and label correctly
- [ ] Keyboard or hardware focus can reach and activate interactive buttons where supported

Record acceptance or actionable defects before beginning the next component.

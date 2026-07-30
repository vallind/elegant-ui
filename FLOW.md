# Delivery flow

Figma remains the visual source of truth, but it no longer blocks implementation.

For each component:

1. Freeze the visual/API contract.
2. Implement the Compose library component.
3. Add the sample app section and bilingual documentation.
4. Push to an isolated GitHub repository.
5. GitHub Actions builds a debug APK and release AAR.
6. The user installs the APK on a physical device and reports observations.
7. Fix visual, interaction, accessibility, or device-specific issues.
8. Mark the component complete and begin the next component.

A component is not complete until the physical-device validation checklist is accepted.

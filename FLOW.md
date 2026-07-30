# Delivery flow

Figma remains the visual source of truth, but it no longer blocks implementation.

For each component:

1. Freeze the visual, API, state, accessibility, and website contract.
2. Implement semantic tokens and the Compose library component.
3. Add and register the physical-device sample demo.
4. Add matching English and Simplified Chinese VitePress component pages.
5. Add or update the website preview, both locale sidebars, and both component overview pages.
6. Run the documentation validator and VitePress build.
7. Push one coherent component milestone.
8. GitHub Actions deploys the documentation website and builds the debug APK and release AAR.
9. The user installs the APK on a physical device and reports observations.
10. Fix website, visual, interaction, accessibility, or device-specific issues.
11. Mark the component complete and begin the next component.

A component is not complete until both CI workflows pass and the physical-device validation checklist is accepted.

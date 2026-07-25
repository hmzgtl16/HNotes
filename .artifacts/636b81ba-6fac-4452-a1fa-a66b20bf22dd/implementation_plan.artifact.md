# Fix "Unknown screen LabelNavKey" IllegalStateException

The application crashes with `java.lang.IllegalStateException: Unknown screen LabelNavKey(noteId=1)` when navigating to the label selection screen. This occurs because the `label` feature module is not included as a dependency in the `:app` module, preventing Hilt from discovering and injecting its navigation entry builder.

## Proposed Changes

### [app](file:///D:/Projects/StudioProjects/HNotes/app)

#### [MODIFY] [build.gradle.kts](file:///D:/Projects/StudioProjects/HNotes/app/build.gradle.kts)
- Add `projects.feature.label.api` and `projects.feature.label.impl` to the dependencies block.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:assembleDebug` to ensure the project builds with the new dependencies.

### Manual Verification
- Deploy the app and navigate to a note.
- Trigger the label selection (usually via a button in the note screen).
- Verify that the `LabelDialog` appears without crashing.

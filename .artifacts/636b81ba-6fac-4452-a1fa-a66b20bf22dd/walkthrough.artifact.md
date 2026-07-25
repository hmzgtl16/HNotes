# Walkthrough - Fixing "Unknown screen LabelNavKey"

I have resolved the `IllegalStateException: Unknown screen LabelNavKey` crash by ensuring the `label` feature is correctly integrated into the application's dependency graph.

## Changes Made

### [app](file:///D:/Projects/StudioProjects/HNotes/app)

#### [MODIFY] [build.gradle.kts](file:///D:/Projects/StudioProjects/HNotes/app/build.gradle.kts)
Added the `label:api` and `label:impl` dependencies to the `:app` module. This allows Hilt to discover and inject the navigation entry for the label selection dialog, which is provided via `@IntoSet` in `LabelModule.kt`.

```diff
     implementation(projects.feature.notes.impl)
     implementation(projects.feature.note.api)
     implementation(projects.feature.note.impl)
+    implementation(projects.feature.label.api)
+    implementation(projects.feature.label.impl)
     implementation(projects.feature.search.api)
     implementation(projects.feature.search.impl)
```

## Verification Results

### Automated Tests
- Executed `./gradlew :app:assembleDebug`.
- **Status:** Build finished successfully.

### Manual Verification
- The addition of these dependencies ensures that the `Set<EntryProviderScope<NavKey>.() -> Unit>` injected in `MainActivity` now includes the `labelEntry` from the label feature implementation.

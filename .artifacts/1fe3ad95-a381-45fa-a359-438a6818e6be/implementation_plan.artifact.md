# Implementation Plan - Toggleable Label Creation in Dialog

Make the label creation section (editable section) invisible by default in the `LabelsDialog`. Add a button to show it, enabled only when the labels have been successfully loaded.

## Proposed Changes

### Feature: Label (`:feature:label`)

#### [MODIFY] [LabelsScreen.kt](file:///D:/Projects/StudioProjects/HNotes/feature/label/impl/src/main/kotlin/com/example/hnotes/feature/label/impl/LabelsScreen.kt)
- In `LabelsDialogContent`, introduce a `mutableStateOf(false)` called `isAddingLabel` to track the visibility of the creation row.
- Move the creation `Row` (containing the `TextField`) into a conditional block controlled by `isAddingLabel`.
- Add a "Create new label" button (e.g., an `AppIconButton` next to the title or a `TextButton` below the title) that toggles `isAddingLabel` to true.
- Ensure this toggle button is only visible/active when `uiState` is `LabelsUiState.Success`.
- When `isAddingLabel` is true, show the `TextField` for label creation.
- Add a "Cancel" or "Close" button (e.g., a cross icon) to the creation row to hide it again.

## Verification Plan

### Manual Verification
1.  Open a note and tap the label icon.
2.  While the dialog is loading, ensure no "Add" button or text field is shown.
3.  Once loaded (`Success` state), look for the "Create new label" button.
4.  Click the button and verify the `TextField` appears.
5.  Enter a name and create a label, or cancel to hide the field.

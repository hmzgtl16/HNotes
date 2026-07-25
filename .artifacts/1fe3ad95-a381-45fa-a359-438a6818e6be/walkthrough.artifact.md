# Walkthrough - Toggleable Label Creation

I have updated the labels dialog to hide the creation field by default. It can now be toggled via an "Add" button, which is only available after the labels have successfully loaded.

## Changes Made

### Feature: Label (`:feature:label`)
- **[MODIFY]** [LabelsScreen.kt](file:///D:/Projects/StudioProjects/HNotes/feature/label/impl/src/main/kotlin/com/example/hnotes/feature/label/impl/LabelsScreen.kt):
    - Added `isAddingLabel` local state to `LabelsDialogContent`.
    - Wrapped the label creation `Row` in a conditional block.
    - Added an `IconButton` (Plus icon) next to the "Edit Labels" title to show the creation field.
    - This button is only visible when the UI state is `Success`.
    - Added a "Cancel" button (Close icon) within the creation row to hide the field again.
    - Automatically hides the creation field after a label is successfully added.

## Verification Results

### Manual Verification
1.  **Dialog Loading**: When opening the labels dialog, the "Add" button and text field are hidden until loading completes.
2.  **Toggle Visibility**: Once loaded, tap the `+` icon next to the title. The "Create new label" field appears.
3.  **Creation**: Enter a label name and tap the checkmark. The label is created and the field is hidden.
4.  **Cancellation**: Tap the `X` icon next to the text field to hide it without creating a label.

> [!NOTE]
> This change reduces visual clutter in the dialog, especially when a user only wants to select existing labels rather than manage them.

# Task List - Add Background Color to Labels

- [ ] Core Models
    - [ ] Update `Label.kt` in `:core:model`
- [ ] Database Layer
    - [ ] Update `LabelEntity.kt` in `:core:database`
    - [ ] Update `ApplicationDatabase.kt` (version 3)
- [ ] Data Layer
    - [ ] Update `Mapper.kt` in `:core:data`
- [ ] UI Components
    - [ ] Update `NoteCard.kt` in `:core:ui` to render colored labels
- [ ] Feature: Label (`:feature:label`)
    - [ ] Update `LabelsDialogContent` in `LabelsScreen.kt` to include color selection
    - [ ] Update `SelectableLabelItem` and `EditableLabelItem` to show colors
- [ ] Feature: Note (`:feature:note`)
    - [ ] Update `NoteScreen.kt` to render colored chips
- [ ] Verification
    - [ ] Build app
    - [ ] Verify database migration
    - [ ] Verify color selection and chip rendering

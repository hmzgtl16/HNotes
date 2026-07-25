package com.example.hnotes.feature.note.impl

import com.example.hnotes.core.model.Item
import com.example.hnotes.core.model.Label
import com.example.hnotes.core.model.Note
import com.example.hnotes.core.model.Reminder

data class NoteUiState(
    val note: Note? = Note(),
    val title: String = "",
    val content: String = "",
    val backgroundColor: Int? = null,
    val reminder: Reminder? = null,
    val items: List<Item> = emptyList(),
    val labels: List<Label> = emptyList(),
    val isEdited: Boolean = false,
    val isReminderPickerVisible: Boolean = false,
    val isPaletteVisible: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val isLabelsDialogVisible: Boolean = false,
    val canUndo: Boolean = false,
    val canRedo: Boolean = false,
) {

    fun getEditableSnapshot(): EditableNoteState = EditableNoteState(
        title = title,
        content = content,
        backgroundColor = backgroundColor,
        reminder = reminder,
        items = items,
        labels = labels
    )
}

data class EditableNoteState(
    val title: String,
    val content: String,
    val backgroundColor: Int?,
    val reminder: Reminder?,
    val items: List<Item>,
    val labels: List<Label>
)

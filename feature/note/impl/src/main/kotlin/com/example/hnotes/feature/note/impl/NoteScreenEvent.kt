package com.example.hnotes.feature.note.impl

import com.example.hnotes.core.model.Item
import com.example.hnotes.core.model.Reminder

interface NoteScreenEvent {
    data class TitleChanged(val title: String) : NoteScreenEvent
    data class ContentChanged(val content: String) : NoteScreenEvent
    data class BackgroundColorChanged(val color: Int?) : NoteScreenEvent
    data class ReminderChanged(val reminder: Reminder?) : NoteScreenEvent
    data object AddItem : NoteScreenEvent
    data class RemoveItem(val index: Int) : NoteScreenEvent
    data class UpdateItem(val index: Int, val item: Item) : NoteScreenEvent
    data class ReminderPickerVisibilityChanged(val isVisible: Boolean) : NoteScreenEvent
    data class PaletteVisibilityChanged(val isVisible: Boolean) : NoteScreenEvent
    data class DeleteDialogVisibilityChanged(val isVisible: Boolean) : NoteScreenEvent
    data object Undo : NoteScreenEvent
    data object Redo : NoteScreenEvent
    data object SaveNote : NoteScreenEvent
    data object CopyNote : NoteScreenEvent
    data object DeleteNote : NoteScreenEvent
}


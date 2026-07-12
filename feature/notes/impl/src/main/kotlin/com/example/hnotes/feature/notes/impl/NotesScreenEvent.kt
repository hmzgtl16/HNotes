package com.example.hnotes.feature.notes.impl

import com.example.hnotes.core.model.Note

sealed interface NotesScreenEvent {
    data class MultiSelectionChanged(val enabled: Boolean) : NotesScreenEvent
    data class NoteSelectedChanged(val note: Note) : NotesScreenEvent
    data class SelectAllNotesChecked(val checked: Boolean) : NotesScreenEvent
    data object DeleteNotes : NotesScreenEvent
    data object RestoreNotes : NotesScreenEvent
    data object UndoSnackbarDismissed : NotesScreenEvent
    data class PinNote(val note: Note) : NotesScreenEvent
    data object PinNotes : NotesScreenEvent
    data class NavigateToNote(val noteId: Long? = null) : NotesScreenEvent
}
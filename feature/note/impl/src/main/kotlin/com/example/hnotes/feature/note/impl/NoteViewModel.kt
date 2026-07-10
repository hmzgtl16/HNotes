package com.example.hnotes.feature.note.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.hnotes.core.data.repository.NoteRepository
import com.example.hnotes.core.model.Item
import com.example.hnotes.core.model.Note
import com.example.hnotes.core.navigation.Navigator
import com.example.hnotes.core.navigation.Route
import com.example.hnotes.feature.note.api.navigation.NoteNavKey
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@HiltViewModel(assistedFactory = NoteViewModel.Factory::class)
class NoteViewModel @AssistedInject constructor(
    private val navigator: Navigator,
    private val noteRepository: NoteRepository,
    @Assisted navKey: NoteNavKey
) : ViewModel() {

    val uiState: StateFlow<NoteUiState>
        field = MutableStateFlow(NoteUiState())

    private val undoStack = ArrayDeque<EditableNoteState>()
    private val redoStack = ArrayDeque<EditableNoteState>()

    init {
        navKey.noteId?.let { id ->
            viewModelScope.launch {
                noteRepository.getNoteById(id = id)
                    .filterNotNull()
                    .collect { note ->
                        val initialEditableState = EditableNoteState(
                            title = note.title,
                            content = note.content,
                            backgroundColor = note.backgroundColor,
                            reminder = note.reminder,
                            items = note.items
                        )

                        clearUndoRedoStacks()

                        uiState.update {
                            it.copy(
                                note = note,
                                title = initialEditableState.title,
                                content = initialEditableState.content,
                                backgroundColor = initialEditableState.backgroundColor,
                                reminder = initialEditableState.reminder,
                                items = initialEditableState.items,
                                isEdited = false
                            )
                        }
                    }
            }
        } ?: run {
            clearUndoRedoStacks()

            uiState.update {
                it.copy(
                    canUndo = undoStack.isNotEmpty(),
                    canRedo = redoStack.isNotEmpty()
                )
            }
        }
    }

    fun onEvent(event: NoteScreenEvent) {
        val currentStateSnapshot = uiState.value.getEditableSnapshot()

        when (event) {
            is NoteScreenEvent.TitleChanged -> {
                addChangeToHistory(previousState = currentStateSnapshot)
                updateEditableState(newEditableState = currentStateSnapshot.copy(title = event.title))
            }

            is NoteScreenEvent.ContentChanged -> {
                addChangeToHistory(previousState = currentStateSnapshot)
                updateEditableState(newEditableState = currentStateSnapshot.copy(content = event.content))
            }

            is NoteScreenEvent.BackgroundColorChanged -> {
                addChangeToHistory(previousState = currentStateSnapshot)
                updateEditableState(newEditableState = currentStateSnapshot.copy(backgroundColor = event.color))
            }

            is NoteScreenEvent.ReminderChanged -> {
                addChangeToHistory(previousState = currentStateSnapshot)
                updateEditableState(newEditableState = currentStateSnapshot.copy(reminder = event.reminder))
            }

            is NoteScreenEvent.AddItem -> {
                addChangeToHistory(previousState = currentStateSnapshot)
                val items = currentStateSnapshot.items.toMutableList()
                items.add(element = Item())
                updateEditableState(newEditableState = currentStateSnapshot.copy(items = items))
            }

            is NoteScreenEvent.RemoveItem -> {
                addChangeToHistory(previousState = currentStateSnapshot)
                val items = currentStateSnapshot.items.toMutableList()
                items.removeAt(index = event.index)
                updateEditableState(newEditableState = currentStateSnapshot.copy(items = items))
            }

            is NoteScreenEvent.UpdateItem -> {
                addChangeToHistory(previousState = currentStateSnapshot)
                val items = currentStateSnapshot.items.toMutableList()
                items.set(index = event.index, element = event.item)
                updateEditableState(newEditableState = currentStateSnapshot.copy(items = items))
            }

            is NoteScreenEvent.ReminderPickerVisibilityChanged -> uiState.update {
                it.copy(isReminderPickerVisible = event.isVisible)
            }

            is NoteScreenEvent.PaletteVisibilityChanged -> uiState.update {
                it.copy(isPaletteVisible = event.isVisible)
            }

            is NoteScreenEvent.DeleteDialogVisibilityChanged -> uiState.update {
                it.copy(isDeleteDialogVisible = event.isVisible)
            }

            is NoteScreenEvent.Undo -> undo()
            is NoteScreenEvent.Redo -> redo()
            is NoteScreenEvent.SaveNote -> saveNote()
            is NoteScreenEvent.CopyNote -> copyNote()
            is NoteScreenEvent.DeleteNote -> deleteNote()
        }
    }

    private fun updateEditableState(
        newEditableState: EditableNoteState,
        markAsEdited: Boolean = true
    ) {
        uiState.update {
            it.copy(
                title = newEditableState.title,
                content = newEditableState.content,
                backgroundColor = newEditableState.backgroundColor,
                reminder = newEditableState.reminder,
                items = newEditableState.items,
                isEdited = if (markAsEdited) true else it.isEdited,
                canUndo = undoStack.isNotEmpty(),
                canRedo = redoStack.isNotEmpty()
            )
        }
    }

    private fun addChangeToHistory(previousState: EditableNoteState) {
        if (undoStack.size >= MAX_HISTORY_SIZE) {
            undoStack.removeFirst()
        }
        undoStack.addLast(previousState)
        redoStack.clear()
    }

    private fun clearUndoRedoStacks() {
        undoStack.clear()
        redoStack.clear()
        uiState.update {
            it.copy(
                canUndo = undoStack.isNotEmpty(),
                canRedo = redoStack.isNotEmpty()
            )
        }
    }

    private fun undo() {
        if (undoStack.isNotEmpty()) {
            val stateToRestore = undoStack.removeLast()
            val currentStateForRedo = uiState.value.getEditableSnapshot()
            redoStack.addLast(currentStateForRedo)
            updateEditableState(stateToRestore)
        }
    }

    private fun redo() {
        if (redoStack.isNotEmpty()) {
            val stateToRestore = redoStack.removeLast()
            val currentStateForUndo = uiState.value.getEditableSnapshot()
            undoStack.addLast(currentStateForUndo)
            updateEditableState(stateToRestore)
        }
    }

    private fun saveNote() = viewModelScope.launch {
        val currentState = uiState.value
        if (!currentState.isEdited) {
            navigator.navigateBack()
            return@launch
        }

        val noteToSave = currentState.note?.copy(
            title = currentState.title,
            content = currentState.content,
            reminder = currentState.reminder,
            items = currentState.items,
            backgroundColor = currentState.backgroundColor,
            updated = Clock.System.now()
        ) ?: Note()
        noteRepository.upsertNote(note = noteToSave)
        navigator.navigateBack()
    }

    private fun copyNote() = viewModelScope.launch {
        val currentState = uiState.value
        val originalNote = currentState.note ?: return@launch

        val noteToCopy = originalNote.copy(
            id = 0,
            reminder = originalNote.reminder?.copy(id = 0L),
            items = originalNote.items.map { it.copy(id = 0L) },
            created = Clock.System.now(),
            updated = Clock.System.now()
        )

        noteRepository.upsertNote(note = noteToCopy)
        navigator.navigateBack()
    }

    private fun deleteNote() = viewModelScope.launch {
        val currentState = uiState.value
        val noteToDelete = currentState.note ?: return@launch
        noteRepository.deleteNote(note = noteToDelete)
        navigator.navigateBack()
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: NoteNavKey): NoteViewModel
    }

    companion object {
        private const val MAX_HISTORY_SIZE = 30
    }
}


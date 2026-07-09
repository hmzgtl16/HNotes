package com.example.hnotes.feature.notes.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnotes.core.data.repository.NoteRepository
import com.example.hnotes.core.model.Note
import com.example.hnotes.core.navigation.Navigator
import com.example.hnotes.feature.note.api.navigation.NoteNavKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val navigator: Navigator,
    private val noteRepository: NoteRepository
) : ViewModel() {

    val uiState: StateFlow<NotesUiState> 
    field = MutableStateFlow(value = NotesUiState())

    private var undoDeleteJob: Job? = null

    init {
        viewModelScope.launch {
            noteRepository.notes
                .map<List<Note>, NotesState> {
                    val groupedNotes = it
                        .groupBy(Note::pinned)
                        .toSortedMap(compareByDescending(Boolean::not))
                    NotesState.Success(notes = groupedNotes)
                }
                .onStart { emit(NotesState.Loading) }
                .collect { state ->
                    uiState.update { it.copy(notesState = state) }
                }
        }
    }

    fun onEvent(event: NotesScreenEvent) {
        when (event) {
            is NotesScreenEvent.MultiSelectionChanged -> uiState.update {
                it.copy(isMultiSelectionEnabled = event.enabled)
            }

            is NotesScreenEvent.NoteSelectedChanged -> uiState.update {
                val updatedSelectedNotes =
                    if (it.selectedNotes.contains(event.note)) it.selectedNotes - event.note
                    else it.selectedNotes + event.note

                it.copy(selectedNotes = updatedSelectedNotes)
            }

            is NotesScreenEvent.SelectAllNotesChecked -> uiState.update {
                val allNotes = (it.notesState as? NotesState.Success)
                    ?.notes?.values?.flatten() ?: emptyList()
                it.copy(selectedNotes = if (event.checked) allNotes else emptyList())
            }

            is NotesScreenEvent.DeleteNotes -> deleteNotes()
            is NotesScreenEvent.RestoreNotes -> restoreNotes()

            is NotesScreenEvent.UndoSnackbarDismissed -> {
                uiState.update { it.copy(showUndoDeleteSnackbar = false) }
            }

            is NotesScreenEvent.PinNote -> pinNote(note = event.note)
            is NotesScreenEvent.PinNotes -> pinNotes()
            is NotesScreenEvent.NavigateToNote -> navigateToNote(noteId = event.noteId)
        }
    }

    private fun deleteNotes() = viewModelScope.launch {
        val notesToDelete = uiState.value.selectedNotes
        if (notesToDelete.isEmpty()) return@launch

        noteRepository.deleteNotes(notes = notesToDelete)

        uiState.update {
            it.copy(
                selectedNotes = emptyList(),
                isMultiSelectionEnabled = false,
                recentlyDeletedNotes = notesToDelete,
                showUndoDeleteSnackbar = true
            )
        }

        undoDeleteJob?.cancel()
        undoDeleteJob = viewModelScope.launch {
            delay(5000L.milliseconds)
            uiState.update {
                if (it.recentlyDeletedNotes == notesToDelete) {
                    it.copy(recentlyDeletedNotes = emptyList(), showUndoDeleteSnackbar = false)
                } else {
                    it
                }
            }
        }
    }

    private fun restoreNotes() = viewModelScope.launch {
        undoDeleteJob?.cancel()
        val notesToRestore = uiState.value.recentlyDeletedNotes
        if (notesToRestore.isEmpty()) return@launch

        noteRepository.updateNotes(notes = notesToRestore)
        uiState.update {
            it.copy(recentlyDeletedNotes = emptyList(), showUndoDeleteSnackbar = false)
        }
    }

    private fun pinNote(note: Note) = viewModelScope.launch {
        val noteToPin = note.copy(pinned = !note.pinned)
        noteRepository.upsertNote(note = noteToPin)
    }

    private fun pinNotes() = viewModelScope.launch {
        val selectedNotes = uiState.value.selectedNotes
        if (selectedNotes.isEmpty()) return@launch

        val shouldPin = selectedNotes.any { !it.pinned }
        val notesToPin = selectedNotes.map { it.copy(pinned = shouldPin) }
        noteRepository.updateNotes(notes = notesToPin)
        uiState.update {
            it.copy(
                selectedNotes = emptyList(),
                isMultiSelectionEnabled = false
            )
        }
    }

    private fun navigateToNote(noteId: Long?) = viewModelScope.launch {
        navigator.navigateTo(navKey = NoteNavKey(noteId = noteId))
    }
}

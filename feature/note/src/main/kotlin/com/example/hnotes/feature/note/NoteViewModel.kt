package com.example.hnotes.feature.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.hnotes.core.data.repository.NoteRepository
import com.example.hnotes.core.model.data.Note
import com.example.hnotes.core.ui.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        val id = savedStateHandle.toRoute<Route.NoteRoute>().id

        viewModelScope.launch {
            noteRepository.getNote(id = id).collect {
                _note.value = it
                _title.value = it?.title ?: ""
                _description.value = it?.description ?: ""
                _backgroundColor.value = it?.backgroundColor
                _pinned.value = it?.isPinned ?: false
                _isEdited.value = false
            }
        }
    }

    private val _note = MutableStateFlow<Note?>(value = null)
    val note: StateFlow<Note?> get() = _note

    private val _title = MutableStateFlow(value = "")
    val title: StateFlow<String> get() = _title

    private val _description = MutableStateFlow(value = "")
    val description: StateFlow<String> get() = _description

    private val _backgroundColor = MutableStateFlow<Int?>(value = null)
    val backgroundColor: StateFlow<Int?> get() = _backgroundColor

    private val _pinned = MutableStateFlow(value = false)
    val pinned: StateFlow<Boolean> get() = _pinned

    private val _paletteVisibility = MutableStateFlow(value = false)
    val paletteVisibility: StateFlow<Boolean> get() = _paletteVisibility

    private val _deleteDialogVisibility = MutableStateFlow(value = false)
    val deleteDialogVisibility: StateFlow<Boolean> get() = _deleteDialogVisibility

    private val _isEdited = MutableStateFlow(value = false)
    private val isEdited: StateFlow<Boolean> get() = _isEdited

    private val _isDeleted = MutableStateFlow(value = false)
    val isDeleted: StateFlow<Boolean> get() = _isDeleted

    fun onTitleChange(value: String) {
        _isEdited.value = true
        _title.value = value
    }

    fun onDescriptionChange(value: String) {
        _isEdited.value = true
        _description.value = value
    }

    fun onBackgroundColorChange(value: Int?) {
        _isEdited.value = true
        _backgroundColor.value = value
    }

    fun onPinnedChange(value: Boolean) {
        _isEdited.value = true
        _pinned.value = value
    }

    fun onPaletteVisibilityChange(value: Boolean) {
        _paletteVisibility.value = value
    }

    fun onDeleteDialogVisibilityChange(value: Boolean) {
        _deleteDialogVisibility.value = value
    }

    fun saveNote() = viewModelScope.launch {
        if (!isEdited.value) return@launch
        val note = note.value ?: Note()
        noteRepository.updateNote(
            note = note.copy(
                title = title.value,
                description = description.value,
                updated = Clock.System.now(),
                isPinned = pinned.value,
                backgroundColor = backgroundColor.value,
            )
        )
        _isEdited.value = false
    }

    fun copyNote() = viewModelScope.launch {
        val note = note.value?.copy(
            id = 0,
            created = Clock.System.now(),
            updated = Clock.System.now()
        ) ?: return@launch
        noteRepository.updateNote(note = note)
    }

    fun deleteNote() = viewModelScope.launch {
        val note = note.value ?: return@launch
        _isDeleted.value = noteRepository.deleteNote(note = note).isSuccess
    }
}

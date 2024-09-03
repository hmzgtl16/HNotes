package com.example.hnotes.feature.notes

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.hnotes.core.designsystem.component.HNotesBackground
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.model.data.Note
import com.example.hnotes.core.ui.DevicePreviews
import com.example.hnotes.core.ui.NotesPreviewParameterProvider

class NotesScreenPreviewsScreenshots {

    @DevicePreviews
    @Composable
    fun NotesScreenLoading() {
        HNotesTheme {
            HNotesBackground {
                NotesScreen(
                    uiState = NotesUiState.Loading,
                    selectedNotes = emptyList(),
                    onSelectAllNotesChecked = {},
                    multiSelectionEnabled = false,
                    onMultiSelectionChanged = {},
                    noteSelected = { false },
                    onNoteSelectedChanged = {},
                    deleteNotes = {},
                    pinNotes = {},
                    onNoteClick = {},
                    onPinClick = {},
                    deletedNotes = emptyList(),
                    isDeleteSuccess = false,
                    restoreNotes = {}
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun NotesScreenEmpty() {
        HNotesTheme {
            HNotesBackground {
                NotesScreen(
                    uiState = NotesUiState.Success(notes = emptyMap()),
                    selectedNotes = emptyList(),
                    onSelectAllNotesChecked = {},
                    multiSelectionEnabled = false,
                    onMultiSelectionChanged = {},
                    noteSelected = { false },
                    onNoteSelectedChanged = {},
                    deleteNotes = {},
                    pinNotes = {},
                    onNoteClick = {},
                    onPinClick = {},
                    deletedNotes = emptyList(),
                    isDeleteSuccess = false,
                    restoreNotes = {}
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun NotesScreenPopulated(
        @PreviewParameter(NotesPreviewParameterProvider::class) notes: Map<Boolean,List<Note>>
    ) {
        HNotesTheme {
            HNotesBackground {
                NotesScreen(
                    uiState = NotesUiState.Success(notes = notes),
                    selectedNotes = notes.values.flatten().filter(Note::isPinned),
                    onSelectAllNotesChecked = {},
                    multiSelectionEnabled = true,
                    onMultiSelectionChanged = {},
                    noteSelected = { notes.values.flatten().filter(Note::isPinned).contains(element = it) },
                    onNoteSelectedChanged = {},
                    deleteNotes = {},
                    pinNotes = {},
                    onNoteClick = {},
                    onPinClick = {},
                    deletedNotes = emptyList(),
                    isDeleteSuccess = false,
                    restoreNotes = {}
                )
            }
        }
    }
}
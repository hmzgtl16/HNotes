package com.example.hnotes.feature.notes

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.model.data.Note
import com.example.hnotes.core.testing.data.noteTestData
import org.junit.Rule
import org.junit.Test

class NotesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun notesScreen_Loading() {

        composeTestRule.setContent {
            HNotesTheme {
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

        composeTestRule
            .onNodeWithContentDescription(
                label = composeTestRule.activity.resources.getString(R.string.feature_notes_loading)
            )
            .assertExists()
    }

    @Test
    fun notesScreen_Content() {

        composeTestRule
            .setContent {
                HNotesTheme {
                    NotesScreen(
                        uiState = NotesUiState.Success(notes = noteTestData),
                        selectedNotes = noteTestData.values.flatten().filter(Note::isPinned),
                        onSelectAllNotesChecked = {},
                        multiSelectionEnabled = true,
                        onMultiSelectionChanged = {},
                        noteSelected = { noteTestData.values.flatten().filter(Note::isPinned).contains(element = it) },
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

        composeTestRule
            .onNodeWithText(text = noteTestData[true]!!.first().title)
            .assertExists()
    }

    @Test
    fun notesScreen_Empty() {

        composeTestRule.setContent {
            HNotesTheme {
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

        composeTestRule
            .onNodeWithText(text = composeTestRule.activity.getString(R.string.feature_notes_empty_error))
            .assertExists()

        composeTestRule
            .onNodeWithText(text = composeTestRule.activity.getString(R.string.feature_notes_empty_description))
            .assertExists()
    }
}


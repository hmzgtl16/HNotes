/*
 * Copyright (c) 2026 GATTAL Hamza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.hnotes.feature.notes.impl

import androidx.compose.ui.state.ToggleableState
import com.example.hnotes.core.model.Label
import com.example.hnotes.core.model.Note

sealed interface NotesState {
    data object Loading : NotesState

    data class Success(val notes: Map<Boolean, List<Note>>) : NotesState
}

data class NotesUiState(
    val notesState: NotesState = NotesState.Loading,
    val selectedNotes: List<Note> = emptyList(),
    val isMultiSelectionEnabled: Boolean = false,
    val recentlyDeletedNotes: List<Note> = emptyList(),
    val showUndoDeleteSnackbar: Boolean = false,
    val filterLabel: Label? = null,
    val allLabels: List<Label> = emptyList(),
)

val NotesUiState.allNotesSelectedState: ToggleableState
    get() {
        return when (val notesState = this.notesState) {
            is NotesState.Success -> {
                val allNotes = notesState.notes.values.flatten()
                when {
                    this.selectedNotes.isEmpty() -> ToggleableState.Off
                    allNotes.isNotEmpty() && this.selectedNotes.containsAll(allNotes) -> ToggleableState.On
                    this.selectedNotes.isNotEmpty() -> ToggleableState.Indeterminate
                    else -> ToggleableState.Off
                }
            }

            else -> {
                ToggleableState.Off
            }
        }
    }

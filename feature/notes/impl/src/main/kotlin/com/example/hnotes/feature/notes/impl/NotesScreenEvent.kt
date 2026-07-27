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

import com.example.hnotes.core.model.Label
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

    data class FilterLabelChanged(val label: Label?) : NotesScreenEvent
}

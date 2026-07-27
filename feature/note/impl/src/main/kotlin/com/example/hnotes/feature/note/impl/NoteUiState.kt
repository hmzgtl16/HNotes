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
    fun getEditableSnapshot(): EditableNoteState =
        EditableNoteState(
            title = title,
            content = content,
            backgroundColor = backgroundColor,
            reminder = reminder,
            items = items,
            labels = labels,
        )
}

data class EditableNoteState(val title: String, val content: String, val backgroundColor: Int?, val reminder: Reminder?, val items: List<Item>, val labels: List<Label>)

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

    data object NavigateToLabel : NoteScreenEvent
}

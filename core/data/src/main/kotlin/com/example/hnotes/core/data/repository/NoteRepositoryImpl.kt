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
package com.example.hnotes.core.data.repository

import com.example.hnotes.core.alarm.AlarmScheduler
import com.example.hnotes.core.data.util.toEntity
import com.example.hnotes.core.data.util.toModel
import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.database.model.PopulatedNoteEntity
import com.example.hnotes.core.model.Item
import com.example.hnotes.core.model.Label
import com.example.hnotes.core.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl
    @Inject
    constructor(private val noteDao: NoteDao, private val alarmScheduler: AlarmScheduler) : NoteRepository {
        override val notes: Flow<List<Note>> =
            noteDao.getAllNotes()
                .map { it.map(PopulatedNoteEntity::toModel) }

        override suspend fun getNoteById(id: Long): Flow<Note?> =
            noteDao.getNoteById(id = id)
                .map { it?.toModel() }

        override suspend fun upsertNote(note: Note) {
            noteDao.upsertNoteWithItems(
                note = note.toEntity(),
                reminder = note.reminder?.toEntity(),
                items = note.items.map(Item::toEntity),
                labels = note.labels.map(Label::toEntity),
            )

            if (note.reminder == null) {
                deleteNoteReminder(noteId = note.id)
            }

            note.reminder?.let {
                alarmScheduler.cancel(id = it.id)
                alarmScheduler.schedule(
                    id = it.id,
                    scheduleTime = it.time,
                    repeatMode = it.repeatMode,
                )
            }

            deleteItemsExcludingIds(
                noteId = note.id,
                ids = note.items.map(Item::id),
            )
        }

        override suspend fun updateNotes(notes: List<Note>) {
            notes.forEach {
                noteDao.upsertNoteWithItems(
                    note = it.toEntity(),
                    reminder = it.reminder?.toEntity(),
                    items = it.items.map(Item::toEntity),
                    labels = it.labels.map(Label::toEntity),
                )
            }
        }

        override suspend fun deleteNote(note: Note) {
            noteDao.deleteNote(note = note.toEntity())
        }

        override suspend fun deleteNotes(notes: List<Note>) {
            noteDao.deleteNotes(notes = notes.map(Note::toEntity))
        }

        override suspend fun deleteItem(item: Item) {
            noteDao.deleteItem(item = item.toEntity())
        }

        override suspend fun deleteItems(items: List<Item>) {
            noteDao.deleteItems(items = items.map(Item::toEntity))
        }

        override suspend fun deleteNoteReminder(noteId: Long) {
            noteDao.deleteReminderByNoteId(noteId = noteId)
        }

        override suspend fun deleteItemsExcludingIds(noteId: Long, ids: List<Long>) {
            noteDao.deleteItemsExcludingIds(noteId = noteId, ids = ids.toSet())
        }
    }

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
package com.example.hnotes.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.database.model.ItemEntity
import com.example.hnotes.core.database.model.NoteEntity
import com.example.hnotes.core.database.model.ReminderEntity
import com.example.hnotes.core.database.util.RepeatMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.time.Instant

class NoteDaoTest {
    private lateinit var db: ApplicationDatabase
    private lateinit var noteDao: NoteDao

    @BeforeTest
    fun setup() {
        db =
            run {
                val context = ApplicationProvider.getApplicationContext<Context>()
                Room.inMemoryDatabaseBuilder(
                    context,
                    ApplicationDatabase::class.java,
                ).build()
            }
        noteDao = db.noteDao()
    }

    @AfterTest
    fun teardown() = db.close()

    @Test
    fun upsertNoteNewNoteInsertion() =
        runTest {
            val note =
                testNoteEntity(
                    title = "Test Note",
                    content = "This is a test note.",
                    createdAt = Instant.parse("2023-10-01T10:00:00Z"),
                    updatedAt = Instant.parse("2023-10-01T10:00:00Z"),
                )

            val actual = noteDao.upsertNote(note)
            assertNotEquals(0L, actual)
        }

    @Test
    fun upsertNoteExistingNoteUpdate() =
        runTest {
            val note =
                testNoteEntity(
                    title = "Test Note",
                    content = "This is a test note.",
                    createdAt = Instant.parse("2023-10-01T10:00:00Z"),
                    updatedAt = Instant.parse("2023-10-01T10:00:00Z"),
                )

            val initialId = noteDao.upsertNote(note)
            val updatedNote =
                note.copy(
                    id = initialId,
                    title = "Updated Test Note",
                    content = "This is an updated test note.",
                    updatedAt = Instant.parse("2023-10-02T10:00:00Z"),
                )
            val updatedId = noteDao.upsertNote(updatedNote)

            assertEquals(expected = -1L, actual = updatedId)
        }

    @Test
    fun upsertNoteWithItemsNewNoteInsertionWithoutReminderAndItems() =
        runTest {
            val note =
                testNoteEntity(
                    title = "Test Note",
                    content = "This is a test note.",
                    createdAt = Instant.parse("2023-10-01T10:00:00Z"),
                    updatedAt = Instant.parse("2023-10-01T10:00:00Z"),
                )

            noteDao.upsertNoteWithItems(note = note, reminder = null, items = emptyList())

            val noteWithItems = noteDao.getAllNotes().first()

            assertEquals(expected = 1, actual = noteWithItems.size)
            assertEquals(expected = note.title, actual = noteWithItems.first().note.title)
            assertEquals(expected = 0, actual = noteWithItems.first().items.size)
        }

    @Test
    fun upsertNoteWithItemsNewNoteInsertionWithReminderAndItems() =
        runTest {
            val note =
                testNoteEntity(
                    title = "Test Note",
                    content = "This is a test note.",
                    createdAt = Instant.parse("2023-10-01T10:00:00Z"),
                    updatedAt = Instant.parse("2023-10-01T10:00:00Z"),
                )

            val reminder =
                testReminderEntity(
                    time = Instant.parse("2023-10-02T10:00:00Z"),
                    reminderMode = RepeatMode.NONE,
                )

            val items =
                listOf(
                    testItemEntity(content = "Item 1"),
                    testItemEntity(content = "Item 2"),
                )

            noteDao.upsertNoteWithItems(note = note, reminder = reminder, items = items)

            val noteWithItems = noteDao.getAllNotes().first()

            assertEquals(expected = 1, actual = noteWithItems.size)
            assertEquals(expected = note.title, actual = noteWithItems.first().note.title)
            assertEquals(expected = reminder.time, actual = noteWithItems.first().reminder?.time)
            assertEquals(expected = 2, actual = noteWithItems.first().items.size)
        }

    @Test
    fun upsertNoteWithItemsExistingNoteUpdateWithoutReminderAndItems() =
        runTest {
            val note =
                testNoteEntity(
                    title = "Test Note",
                    content = "This is a test note.",
                    createdAt = Instant.parse("2023-10-01T10:00:00Z"),
                    updatedAt = Instant.parse("2023-10-01T10:00:00Z"),
                )

            val initialId = noteDao.upsertNote(note)
            val updatedNote =
                note.copy(
                    id = initialId,
                    title = "Updated Test Note",
                    content = "This is an updated test note.",
                    updatedAt = Instant.parse("2023-10-02T10:00:00Z"),
                )

            noteDao.upsertNoteWithItems(note = updatedNote, reminder = null, items = emptyList())

            val noteWithItems = noteDao.getAllNotes().first()

            assertEquals(expected = 1, actual = noteWithItems.size)
            assertEquals(expected = updatedNote.title, actual = noteWithItems.first().note.title)
            assertEquals(expected = 0, actual = noteWithItems.first().items.size)
        }

    @Test
    fun upsertNoteWithItemsExistingNoteUpdateWithReminderAndItems() =
        runTest {
            val note =
                testNoteEntity(
                    title = "Test Note",
                    content = "This is a test note.",
                    createdAt = Instant.parse("2023-10-01T10:00:00Z"),
                    updatedAt = Instant.parse("2023-10-01T10:00:00Z"),
                )

            val initialId = noteDao.upsertNote(note)
            val updatedNote =
                note.copy(
                    id = initialId,
                    title = "Updated Test Note",
                    content = "This is an updated test note.",
                    updatedAt = Instant.parse("2023-10-02T10:00:00Z"),
                )
            val reminder =
                testReminderEntity(
                    time = Instant.parse("2023-10-02T10:00:00Z"),
                    reminderMode = RepeatMode.NONE,
                )
            val items =
                listOf(
                    testItemEntity(content = "Updated Item 1"),
                    testItemEntity(content = "Updated Item 2"),
                )

            noteDao.upsertNoteWithItems(note = updatedNote, reminder = reminder, items = items)

            val noteWithItems = noteDao.getAllNotes().first()

            assertEquals(expected = 1, actual = noteWithItems.size)
            assertEquals(expected = updatedNote.title, actual = noteWithItems.first().note.title)
            assertEquals(expected = 2, actual = noteWithItems.first().items.size)
        }

    @Test
    fun deleteNote() =
        runTest {
            val note =
                testNoteEntity(
                    title = "Test Note",
                    content = "This is a test note.",
                    createdAt = Instant.parse("2023-10-01T10:00:00Z"),
                    updatedAt = Instant.parse("2023-10-01T10:00:00Z"),
                )

            val reminder =
                testReminderEntity(
                    time = Instant.parse("2023-10-02T10:00:00Z"),
                    reminderMode = RepeatMode.NONE,
                )

            val items =
                listOf(
                    testItemEntity(content = "Item 1"),
                    testItemEntity(content = "Item 2"),
                )

            noteDao.upsertNoteWithItems(note = note, reminder = reminder, items = items)

            val notes = noteDao.getAllNotes().first()

            noteDao.deleteNote(note = notes.first().note)

            val deletedNote = noteDao.getNoteById(id = notes.first().note.id)

            assertNull(actual = deletedNote)
        }

    @Test
    fun deleteNotes() =
        runTest {
            val note1 =
                testNoteEntity(
                    title = "Test Note 1",
                    content = "This is the first test note.",
                    createdAt = Instant.parse("2023-10-01T10:00:00Z"),
                    updatedAt = Instant.parse("2023-10-01T10:00:00Z"),
                )
            val note2 =
                testNoteEntity(
                    title = "Test Note 2",
                    content = "This is the second test note.",
                    createdAt = Instant.parse("2023-10-02T10:00:00Z"),
                    updatedAt = Instant.parse("2023-10-02T10:00:00Z"),
                )
            val note3 =
                testNoteEntity(
                    title = "Test Note 3",
                    content = "This is the third test note.",
                    createdAt = Instant.parse("2023-10-03T10:00:00Z"),
                    updatedAt = Instant.parse("2023-10-03T10:00:00Z"),
                )

            noteDao.upsertNoteWithItems(note = note1, reminder = null, items = emptyList())
            noteDao.upsertNoteWithItems(note = note2, reminder = null, items = emptyList())
            noteDao.upsertNoteWithItems(note = note3, reminder = null, items = emptyList())

            val notes = noteDao.getAllNotes().first()

            noteDao.deleteNotes(notes = listOf(notes.first().note, notes.last().note))

            val remainingNotes = noteDao.getAllNotes().first()

            assertEquals(expected = 1, actual = remainingNotes.size)
            assertEquals(expected = note2.title, actual = remainingNotes.first().note.title)
        }

    @Test
    fun deleteItem() =
        runTest {
            val note =
                testNoteEntity(
                    title = "Test Note",
                    content = "This is a test note.",
                    createdAt = Instant.parse("2023-10-01T10:00:00Z"),
                    updatedAt = Instant.parse("2023-10-01T10:00:00Z"),
                )
            val items =
                listOf(
                    testItemEntity(content = "Item 1"),
                    testItemEntity(content = "Item 2"),
                    testItemEntity(content = "Item 3"),
                )

            noteDao.upsertNoteWithItems(note = note, reminder = null, items = items)

            val notes = noteDao.getAllNotes().first()

            noteDao.deleteItem(item = notes.first().items.first())

            val notesAfterDeletion = noteDao.getAllNotes().first()

            assertEquals(expected = 2, actual = notesAfterDeletion.first().items.size)
        }

    @Test
    fun deleteItems() =
        runTest {
            val note =
                testNoteEntity(
                    title = "Test Note",
                    content = "This is a test note.",
                    createdAt = Instant.parse("2023-10-01T10:00:00Z"),
                    updatedAt = Instant.parse("2023-10-01T10:00:00Z"),
                )
            val items =
                listOf(
                    testItemEntity(content = "Item 1"),
                    testItemEntity(content = "Item 2"),
                    testItemEntity(content = "Item 3"),
                )

            noteDao.upsertNoteWithItems(note = note, reminder = null, items = items)

            val notes = noteDao.getAllNotes().first()

            noteDao.deleteItems(items = notes.first().items)

            val notesAfterDeletion = noteDao.getAllNotes().first()

            assertEquals(expected = 0, actual = notesAfterDeletion.first().items.size)
        }
}

private fun testNoteEntity(id: Long = 0L, title: String, content: String, createdAt: Instant, updatedAt: Instant): NoteEntity =
    NoteEntity(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

private fun testReminderEntity(id: Long = 0L, time: Instant, reminderMode: RepeatMode = RepeatMode.NONE, noteId: Long = 0L): ReminderEntity =
    ReminderEntity(
        id = id,
        time = time,
        repeatMode = reminderMode,
        noteId = noteId,
    )

private fun testItemEntity(id: Long = 0L, content: String, isCompleted: Boolean = false, noteId: Long = 0L): ItemEntity =
    ItemEntity(
        id = id,
        content = content,
        checked = isCompleted,
        noteId = noteId,
    )

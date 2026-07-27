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
package com.example.hnotes.core.data

import com.example.hnotes.core.data.repository.NoteRepository
import com.example.hnotes.core.data.repository.NoteRepositoryImpl
import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.model.Item
import com.example.hnotes.core.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertTrue

class NoteRepositoryTest {
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var repository: NoteRepository

    private lateinit var noteDao: NoteDao

    private lateinit var alarmScheduler: AlarmSchedulerTest

    @BeforeTest
    fun setup() {
        noteDao = NoteDaoTest()
        repository =
            NoteRepositoryImpl(
                noteDao = noteDao,
                alarmScheduler = alarmScheduler,
            )
    }

    @Test
    fun testAddNote() =
        testScope.runTest {
            val note =
                Note(
                    id = 1,
                    title = "Test Note",
                    content = "This is a test note.",
                )
            repository.upsertNote(note = note)
            val notes = repository.notes.first()

            assertContains(iterable = notes, element = note)
        }

    @Test
    fun testAddNoteWithItems() =
        testScope.runTest {
            val note =
                Note(
                    id = 1,
                    title = "Test Note with Items",
                    content = "This is a test note with items.",
                    items =
                    listOf(
                        Item(id = 1L, content = "Item 1"),
                        Item(id = 2L, content = "Item 2"),
                        Item(id = 3L, content = "Item 3"),
                    ),
                )
            repository.upsertNote(note = note)

            val notes = repository.notes.first()

            assertContains(iterable = notes, element = note)
            assertTrue(actual = notes.first().items.isNotEmpty())
        }

    @Test
    fun testUpsertNote() =
        testScope.runTest {
            val note =
                Note(
                    id = 1,
                    title = "Test Note",
                    content = "This is a test note.",
                )
            repository.upsertNote(note = note)

            val updatedNote = note.copy(title = "Updated Test Note")
            repository.upsertNote(note = updatedNote)

            val notes = repository.notes.first()

            assertContains(iterable = notes, element = updatedNote)
        }

    @Test
    fun testUpsertNoteWithItems() =
        testScope.runTest {
            val note =
                Note(
                    id = 1,
                    title = "Test Note with Items",
                    content = "This is a test note with items.",
                    items =
                    listOf(
                        Item(id = 1L, content = "Item 1"),
                        Item(id = 2L, content = "Item 2"),
                        Item(id = 3L, content = "Item 3"),
                    ),
                )
            repository.upsertNote(note = note)

            val updatedNote =
                note.copy(
                    title = "Updated Test Note with Items",
                    items = note.items.map { it.copy(content = "${it.content} (updated)") },
                )
            repository.upsertNote(note = updatedNote)

            val notes = repository.notes.first()

            assertContains(iterable = notes, element = updatedNote)
        }

    @Test
    fun testUpsertNotes() =
        testScope.runTest {
            val note1 =
                Note(
                    id = 1,
                    title = "Test Note 1",
                    content = "This is a test note 1.",
                )
            val note2 =
                Note(
                    id = 2,
                    title = "Test Note 2",
                    content = "This is a test note 2.",
                )
            repository.upsertNote(note = note1)
            repository.upsertNote(note = note2)

            val updatedNote1 = note1.copy(title = "Updated Test Note 1")
            val updatedNote2 = note2.copy(title = "Updated Test Note 2")
            repository.updateNotes(notes = listOf(updatedNote1, updatedNote2))

            val notes = repository.notes.first()

            assertContains(iterable = notes, element = updatedNote1)
            assertContains(iterable = notes, element = updatedNote2)
        }

    @Test
    fun testUpsertNotesWithItems() =
        testScope.runTest {
            val note1 =
                Note(
                    id = 1,
                    title = "Test Note 1 with Items",
                    content = "This is a test note 1 with items.",
                    items =
                    listOf(
                        Item(id = 1L, content = "Item 1"),
                        Item(id = 2L, content = "Item 2"),
                    ),
                )
            val note2 =
                Note(
                    id = 2,
                    title = "Test Note 2 with Items",
                    content = "This is a test note 2 with items.",
                    items =
                    listOf(
                        Item(id = 3L, content = "Item 3"),
                        Item(id = 4L, content = "Item 4"),
                    ),
                )
            repository.upsertNote(note = note1)
            repository.upsertNote(note = note2)

            val updatedNote1 =
                note1.copy(
                    title = "Updated Test Note 1 with Items",
                    items = note1.items.map { it.copy(content = "${it.content} (updated)") },
                )
            val updatedNote2 =
                note2.copy(
                    title = "Updated Test Note 2 with Items",
                    items = note2.items.map { it.copy(content = "${it.content} (updated)") },
                )
            repository.updateNotes(notes = listOf(updatedNote1, updatedNote2))

            val notes = repository.notes.first()

            assertContains(iterable = notes, element = updatedNote1)
            assertContains(iterable = notes, element = updatedNote2)
        }

    @Test
    fun testDeleteNote() =
        testScope.runTest {
            val note =
                Note(
                    id = 1,
                    title = "Test Note",
                    content = "This is a test note.",
                )
            repository.upsertNote(note = note)
            repository.deleteNote(note = note)

            val notes = repository.notes.first()

            assertTrue(actual = !notes.contains(element = note))
        }

    @Test
    fun testDeleteNotes() =
        testScope.runTest {
            val note1 =
                Note(
                    id = 1,
                    title = "Test Note 1",
                    content = "This is a test note 1.",
                )
            val note2 =
                Note(
                    id = 2,
                    title = "Test Note 2",
                    content = "This is a test note 2.",
                )
            val note3 =
                Note(
                    id = 3,
                    title = "Test Note 3",
                    content = "This is a test note 3.",
                )
            repository.upsertNote(note = note1)
            repository.upsertNote(note = note2)
            repository.upsertNote(note = note3)

            repository.deleteNotes(notes = listOf(note1, note2))

            val notes = repository.notes.first()

            assertContains(iterable = notes, element = note3)
            assertTrue(actual = !notes.containsAll(listOf(note1, note2)))
        }

    @Test
    fun testDeleteItem() =
        testScope.runTest {
            val note =
                Note(
                    id = 1,
                    title = "Test Note with Items",
                    content = "This is a test note with items.",
                    items =
                    listOf(
                        Item(id = 1L, content = "Item 1"),
                        Item(id = 2L, content = "Item 2"),
                    ),
                )
            repository.upsertNote(note = note)

            val itemToDelete = note.items.first()
            repository.deleteItem(item = itemToDelete)

            val notes = repository.notes.first()

            assertTrue(actual = notes.first().items.none { it.id == itemToDelete.id })
        }

    @Test
    fun testDeleteItems() =
        testScope.runTest {
            val note =
                Note(
                    id = 1,
                    title = "Test Note with Items",
                    content = "This is a test note with items.",
                    items =
                    listOf(
                        Item(id = 1L, content = "Item 1"),
                        Item(id = 2L, content = "Item 2"),
                        Item(id = 3L, content = "Item 3"),
                    ),
                )
            repository.upsertNote(note = note)

            val itemsToDelete = note.items.take(2)
            repository.deleteItems(items = itemsToDelete)

            val notes = repository.notes.first()

            assertTrue(actual = notes.first().items.none { it.id in itemsToDelete.map(Item::id) })
        }
}

package com.example.hnotes.core

import android.content.Context
import androidx.collection.longListOf
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.hnotes.core.database.HNotesDatabase
import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.database.model.NoteEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class NoteDaoTest {

    private lateinit var db: HNotesDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            HNotesDatabase::class.java,
        ).build()
        noteDao = db.noteDao()
    }

    @After
    fun closeDb() = db.close()

    @Test
    fun noteDao_fetches_notes_by_descending_created_date() = runTest {

        val noteEntities = listOf(
            testNoteEntity(id = 4),
            testNoteEntity(id = 1),
            testNoteEntity(id = 6),
            testNoteEntity(id = 5),
            testNoteEntity(id = 7),
            testNoteEntity(id = 3),
            testNoteEntity(id = 2)
        )

        noteDao.upsertNotes(notes = noteEntities)

        val savedNoteEntities = noteDao.getNotes().first()

        val expected = listOf(7L, 6L, 5L, 4L, 3L, 2L, 1L)
        val actual = savedNoteEntities.map(NoteEntity::id)

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun noteDao_fetches_notes_by_ids_by_descending_created_date() = runTest {

        val noteEntities = listOf(
            testNoteEntity(id = 4),
            testNoteEntity(id = 1),
            testNoteEntity(id = 6),
            testNoteEntity(id = 5),
            testNoteEntity(id = 7),
            testNoteEntity(id = 3),
            testNoteEntity(id = 2)
        )

        noteDao.upsertNotes(notes = noteEntities)

        val savedNoteEntities = noteDao.getNotesByIds(ids = setOf("7", "2", "1")).first()

        val expected = listOf(7L, 2L, 1L)
        val actual = savedNoteEntities.map(NoteEntity::id)

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun noteDao_fetch_note_by_id() = runTest {

        val noteEntity = testNoteEntity(id = 100)

        noteDao.upsertNote(note = noteEntity)

        val savedNoteEntity = noteDao.getNoteById(id = noteEntity.id).first()

        val expected = noteEntity.id
        val actual = savedNoteEntity?.id

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun noteDao_deletes_notes_by_ids() = runTest {

        val noteEntities = listOf(
            testNoteEntity(id = 1),
            testNoteEntity(id = 2),
            testNoteEntity(id = 3),
            testNoteEntity(id = 4),
            testNoteEntity(id = 5),
            testNoteEntity(id = 6),
            testNoteEntity(id = 7)
        )

        noteDao.upsertNotes(notes = noteEntities)

        val (toDelete, toKeep) =
            noteEntities.partition { it.id.toInt() % 2 == 0 }

        noteDao.deleteNotes(notes = toDelete)

        val remainingNoteEntities = noteDao.getNotes().first()

        val expected = toKeep.map(NoteEntity::id).toSet()
        val actual = remainingNoteEntities.map(NoteEntity::id).toSet()

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun noteDao_delete_note() = runTest {
        val noteEntities = listOf(
            testNoteEntity(id = 1),
            testNoteEntity(id = 2),
            testNoteEntity(id = 3),
            testNoteEntity(id = 4),
            testNoteEntity(id = 5),
            testNoteEntity(id = 6),
            testNoteEntity(id = 7)
        )

        noteDao.upsertNotes(notes = noteEntities)

        noteDao.deleteNote(note = noteEntities.first())

        val remainingNoteEntities = noteDao.getNotes().first()

        val expected = remainingNoteEntities.contains(noteEntities.first())

        assertEquals(expected = expected, actual = false)
    }
}

private fun testNoteEntity(id: Long) = NoteEntity(
    id = id,
    title = "",
    description = "",
    created = Instant.fromEpochMilliseconds(epochMilliseconds = id),
    updated = Instant.fromEpochMilliseconds(epochMilliseconds = id),
    isPinned = false,
    backgroundColor = 0
)

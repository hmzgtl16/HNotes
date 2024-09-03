package com.example.hnotes.core.data.repository

import com.example.hnotes.core.data.dao.TestNoteDao
import com.example.hnotes.core.data.dao.testNoteEntity
import com.example.hnotes.core.data.dao.testTaskEntity
import com.example.hnotes.core.database.model.NoteEntity
import com.example.hnotes.core.database.model.TaskEntity
import com.example.hnotes.core.database.model.asExternalModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class NoteRepositoryTest {

    private lateinit var repository: NoteRepositoryImpl
    private lateinit var dao: TestNoteDao

    @Before
    fun setup() {
        dao = TestNoteDao()
        repository = NoteRepositoryImpl(noteDao = dao)
    }

    @Test
    fun noteRepository_get_notes_by_note_dao() = runTest {

        assertEquals(
            expected = dao.getNotes().first().map(NoteEntity::asExternalModel),
            actual = repository.notes.first()
        )
    }

    @Test
    fun noteRepository_get_note_by_note_dao() = runTest {
        val id = 1L
        assertEquals(
            expected = dao.getNoteById(id = id).first().asExternalModel(),
            actual = repository.getNote(id = id).first()
        )
    }

    @Test
    fun noteRepository_add_note_by_note_dao() = runTest {

        val toAdd = testNoteEntity(id = 11L)
       // repository.addNote(note = toAdd.asExternalModel())

        assertEquals(
            expected = toAdd.asExternalModel(),
            actual = repository.getNote(id = toAdd.id).first()
        )

        assertEquals(
            expected = dao.getNoteById(id = toAdd.id).first().asExternalModel(),
            actual = repository.getNote(id = toAdd.id).first()
        )
    }

    @Test
    fun noteRepository_update_note_by_note_dao() = runTest {
        val id = 1L
        val toUpdate = dao.getNoteById(id = id).first()
        repository.updateNote(note = toUpdate.copy(title = "Updated").asExternalModel())

        assertEquals(
            expected = "Updated",
            actual = repository.getNote(id = id).first()?.title
        )

        assertEquals(
            expected = dao.getNoteById(id = id).first().asExternalModel(),
            actual = repository.getNote(id = id).first()
        )
    }

    @Test
    fun noteRepository_delete_note_by_note_dao() = runTest {
        val id = 1L
        val toDelete = dao.getNoteById(id = id).first()

        repository.deleteNote(note = toDelete.asExternalModel())

        assertEquals(
            expected = dao.getNotes().first().map(NoteEntity::asExternalModel),
            actual = repository.notes.first()
        )
    }
}
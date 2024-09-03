package com.example.hnotes.core.data.repository

import com.example.hnotes.core.data.utils.asEntity
import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.database.model.NoteEntity
import com.example.hnotes.core.database.model.asExternalModel
import com.example.hnotes.core.model.data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface NoteRepository {

    val notes: Flow<List<Note>>

    suspend fun getNote(id: Long): Flow<Note?>

    suspend fun updateNote(note: Note)
    suspend fun updateNotes(notes: List<Note>)

    suspend fun deleteNote(note: Note): Result<Unit>
    suspend fun deleteNotes(notes: List<Note>): Result<Unit>
}

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override val notes: Flow<List<Note>> =
        noteDao.getNotes().map { it.map(NoteEntity::asExternalModel) }

    override suspend fun getNote(id: Long): Flow<Note?> =
        noteDao.getNoteById(id = id).map { it?.asExternalModel() }

    override suspend fun updateNote(note: Note) =
        noteDao.upsertNote(note = note.asEntity())

    override suspend fun updateNotes(notes: List<Note>) =
        noteDao.upsertNotes(notes = notes.map(Note::asEntity))

    override suspend fun deleteNote(note: Note) = runCatching {
        noteDao.deleteNote(note = note.asEntity())
    }

    override suspend fun deleteNotes(notes: List<Note>) = runCatching {
        noteDao.deleteNotes(notes = notes.map(Note::asEntity))
    }
}


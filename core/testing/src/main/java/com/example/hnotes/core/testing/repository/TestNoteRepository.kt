package com.example.hnotes.core.testing.repository

import com.example.hnotes.core.data.repository.NoteRepository
import com.example.hnotes.core.model.data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestNoteRepository : NoteRepository {

    private val notesFlow: MutableStateFlow<List<Note>> = MutableStateFlow(value = emptyList())

    override val notes: Flow<List<Note>> =
        notesFlow.map { it.sortedByDescending { note -> note.updated } }

    override suspend fun getNote(id: Long): Flow<Note> =
        notesFlow.map { notes -> notes.first { it.id == id } }

    override suspend fun updateNote(note: Note) = notesFlow.update {
        val index = it.indexOfFirst { n -> n.id == note.id }
        it.toMutableList().set(index = index, element = note)
        it.sortedByDescending { note -> note.updated }
    }

    override suspend fun updateNotes(notes: List<Note>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(note: Note): Result<Unit> = kotlin.runCatching {
        notesFlow.update {
            val index = it.indexOfFirst { n -> n.id == note.id }
            it.toMutableList().removeAt(index = index)
            it.sortedByDescending { note -> note.updated }
        }
    }

    override suspend fun deleteNotes(notes: List<Note>): Result<Unit> {
        TODO("Not yet implemented")
    }

    fun addNotes(notes: List<Note>) = notesFlow.update {
        (it + notes).sortedByDescending { note -> note.updated }
    }
}
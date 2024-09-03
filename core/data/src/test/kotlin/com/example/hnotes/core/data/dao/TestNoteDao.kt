package com.example.hnotes.core.data.dao

import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.database.model.NoteEntity
import com.example.hnotes.core.database.model.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Instant

class TestNoteDao: NoteDao {
    private val entitiesStateFlow = MutableStateFlow(
        value = mutableListOf(
            testNoteEntity(id = 1),
            testNoteEntity(id = 2),
            testNoteEntity(id = 3),
            testNoteEntity(id = 4),
            testNoteEntity(id = 5),
            testNoteEntity(id = 6),
            testNoteEntity(id = 7),
            testNoteEntity(id = 8)
        )
    )

    override suspend fun insertNote(note: NoteEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun upsertNote(note: NoteEntity) {
        entitiesStateFlow.update {
            val toUpsertIndex = it.indexOfFirst { taskEntity ->
                taskEntity.id == note.id
            }

            if (toUpsertIndex != -1) it.set(index = toUpsertIndex, element = note)
            else it.add(element = note)

            it.sortByDescending(selector = NoteEntity::updated)
            it
        }
    }

    override suspend fun upsertNotes(notes: List<NoteEntity>) {
        entitiesStateFlow.update {

            it.addAll(elements = notes)
            it.sortByDescending(selector = NoteEntity::updated)
            it
        }
    }

    override suspend fun deleteNote(note: NoteEntity) {
        entitiesStateFlow.update { entities ->
            entities.removeIf { it.id == note.id }
            entities
        }
    }

    override suspend fun deleteNotes(notes: List<NoteEntity>) {
        val ids = notes.map(NoteEntity::id)
        entitiesStateFlow.update { entities ->
            entities.removeAll { it.id in ids }
            entities
        }
    }

    override fun getNoteById(id: Long): Flow<NoteEntity> = entitiesStateFlow
        .map { entities -> entities.first { it.id == id } }

    override fun getNotes(): Flow<List<NoteEntity>> = entitiesStateFlow
    override fun getNotesByIds(ids: Set<String>): Flow<List<NoteEntity>> {
        TODO("Not yet implemented")
    }
}

fun testNoteEntity(id: Long) = NoteEntity(
    id = id,
    title = "",
    description = "",
    created = Instant.fromEpochMilliseconds(epochMilliseconds = id),
    updated = Instant.fromEpochMilliseconds(epochMilliseconds = id),
    isPinned = false,
    backgroundColor = 0,
)
package com.example.hnotes.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.hnotes.core.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note: NoteEntity)

    @Upsert
    suspend fun upsertNotes(notes: List<NoteEntity>)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Delete
    suspend fun deleteNotes(notes: List<NoteEntity>)

    @Query(value = "SELECT * FROM table_note WHERE note_id = :id")
    fun getNoteById(id: Long): Flow<NoteEntity?>

    @Query(value = "SELECT * FROM table_note ORDER BY note_created DESC")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query(value = "SELECT * FROM table_note WHERE note_id IN (:ids) ORDER BY note_created DESC")
    fun getNotesByIds(ids: Set<String>): Flow<List<NoteEntity>>
}

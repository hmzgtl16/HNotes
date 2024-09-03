package com.example.hnotes.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteFtsDao {

    @Query("SELECT note_fts_id FROM table_note_fts WHERE table_note_fts MATCH :text")
    fun searchAllNotes(text: String): Flow<List<String>>
}
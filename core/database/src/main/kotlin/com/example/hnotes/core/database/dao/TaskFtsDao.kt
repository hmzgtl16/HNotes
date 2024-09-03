package com.example.hnotes.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskFtsDao {

    @Query("SELECT task_fts_id FROM table_task_fts WHERE table_task_fts MATCH :text")
    fun searchAllTasks(text: String): Flow<List<String>>
}
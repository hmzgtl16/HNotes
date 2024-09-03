package com.example.hnotes.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ListFtsDao {

    @Query("SELECT list_fts_id FROM table_list_fts WHERE table_list_fts MATCH :text")
    fun searchAllLists(text: String): Flow<List<String>>

    @Query("SELECT item_fts_list_id FROM table_item_fts WHERE table_item_fts MATCH :text")
    fun searchAllItems(text: String): Flow<List<String>>
}


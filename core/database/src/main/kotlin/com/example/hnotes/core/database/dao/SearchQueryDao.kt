package com.example.hnotes.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.hnotes.core.database.model.SearchQueryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchQueryDao {

    @Query(value = "SELECT * FROM table_search_query ORDER BY search_query_queried DESC LIMIT :limit")
    fun getSearchQueries(limit: Int): Flow<List<SearchQueryEntity>>

    @Upsert
    suspend fun upsertSearchQuery(searchQuery: SearchQueryEntity)

    @Query(value = "DELETE FROM table_search_query")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(searchQueryEntity: SearchQueryEntity)
}
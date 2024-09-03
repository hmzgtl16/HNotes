package com.example.hnotes.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hnotes.core.model.data.SearchQuery
import kotlinx.datetime.Instant

@Entity(tableName = "table_search_query")
data class SearchQueryEntity(
    @PrimaryKey
    @ColumnInfo(name = "search_query_text")
    val text: String,
    @ColumnInfo(name = "search_query_queried")
    val queried: Instant
)

fun SearchQueryEntity.asExternalModel() = SearchQuery(
    query = text,
    queried = queried,
)
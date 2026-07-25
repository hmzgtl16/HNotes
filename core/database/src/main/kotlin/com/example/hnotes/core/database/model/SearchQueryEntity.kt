package com.example.hnotes.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

@Entity(tableName = "search_queries")
data class SearchQueryEntity(
    @PrimaryKey
    val text: String,
    val queried: Instant
)

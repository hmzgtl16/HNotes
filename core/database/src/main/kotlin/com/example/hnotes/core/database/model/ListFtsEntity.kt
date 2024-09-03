package com.example.hnotes.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "table_list_fts")
@Fts4
data class ListFtsEntity(
    @ColumnInfo(name = "list_fts_id")
    val id: Long,
    @ColumnInfo(name = "list_fts_title")
    val title: String
)


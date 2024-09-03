package com.example.hnotes.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "table_item_fts")
@Fts4
data class ItemFtsEntity(
    @ColumnInfo(name = "item_fts_id")
    val id: Long,
    @ColumnInfo(name = "item_fts_title")
    val title: String,
    @ColumnInfo(name = "item_fts_list_id")
    var listId: Long
)

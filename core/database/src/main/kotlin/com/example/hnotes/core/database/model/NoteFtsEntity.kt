package com.example.hnotes.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "table_note_fts")
@Fts4
data class NoteFtsEntity(
    @ColumnInfo(name = "note_fts_id")
    val id: Long,
    @ColumnInfo(name = "note_fts_title")
    var title: String,
    @ColumnInfo(name = "note_fts_description", defaultValue = "")
    var description: String,
)


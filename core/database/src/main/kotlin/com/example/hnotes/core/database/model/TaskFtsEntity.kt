package com.example.hnotes.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "table_task_fts")
@Fts4
data class TaskFtsEntity(
    @ColumnInfo(name = "task_fts_id")
    val id: Long,
    @ColumnInfo(name = "task_fts_title")
    val title: String
)


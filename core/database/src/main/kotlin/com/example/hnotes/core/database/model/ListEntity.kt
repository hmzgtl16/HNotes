package com.example.hnotes.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hnotes.core.model.data.List
import kotlinx.datetime.Instant

@Entity(tableName = "table_list")
data class ListEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "list_id")
    val id: Long = 0L,
    @ColumnInfo(name = "list_title")
    val title: String,
    @ColumnInfo(name = "list_created")
    var created: Instant,
    @ColumnInfo(name = "list_updated")
    var updated: Instant,
    @ColumnInfo(name = "list_is_pinned")
    val isPinned: Boolean,
    @ColumnInfo(name = "list_background_color")
    var backgroundColor: Int?
)

package com.example.hnotes.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.hnotes.core.model.data.Item

@Entity(
    tableName = "table_item",
    indices = [ Index(value = arrayOf("item_list_id")) ],
    foreignKeys = [
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = arrayOf("list_id"),
            childColumns = arrayOf("item_list_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    val id: Long,
    @ColumnInfo(name = "item_title")
    val title: String,
    @ColumnInfo(name = "item_is_completed")
    val isCompleted: Boolean,
    @ColumnInfo(name = "item_list_id")
    var listId: Long
)

fun ItemEntity.asExternalModel() = Item(
    id = id,
    title = title,
    isCompleted = isCompleted
)

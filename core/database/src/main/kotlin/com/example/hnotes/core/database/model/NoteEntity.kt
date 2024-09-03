package com.example.hnotes.core.database.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hnotes.core.model.data.Note
import kotlinx.datetime.Instant

@Entity(tableName = "table_note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val id: Long = 0L,
    @ColumnInfo(name = "note_title")
    var title: String,
    @ColumnInfo(name = "note_description", defaultValue = "")
    var description: String,
    @ColumnInfo(name = "note_created")
    var created: Instant,
    @ColumnInfo(name = "note_updated")
    var updated: Instant,
    @ColumnInfo(name = "note_is_pinned", defaultValue = "false")
    var isPinned: Boolean,
    @ColumnInfo(name = "note_background_color")
    var backgroundColor: Int?
)

fun NoteEntity.asExternalModel() = Note(
    id = id,
    title = title,
    description = description,
    created = created,
    updated = updated,
    isPinned = isPinned,
    backgroundColor = backgroundColor
)

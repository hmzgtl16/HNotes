package com.example.hnotes.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val content: String,
    val pinned: Boolean = false,
    val backgroundColor: Int? = null,
    val createdAt: Instant,
    val updatedAt: Instant
)

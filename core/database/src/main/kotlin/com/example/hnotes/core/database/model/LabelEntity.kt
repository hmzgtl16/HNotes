package com.example.hnotes.core.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "labels",
    indices = [Index(value = ["name"], unique = true)]
)
data class LabelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val backgroundColor: Int? = null
)

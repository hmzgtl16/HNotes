package com.example.hnotes.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hnotes.core.database.util.RepeatMode
import com.example.hnotes.core.database.util.asModelRepeatMode
import com.example.hnotes.core.model.data.Task
import kotlinx.datetime.Instant

@Entity(tableName = "table_task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    val id: Long = 0L,
    @ColumnInfo(name = "task_title")
    val title: String,
    @ColumnInfo(name = "task_created")
    val created: Instant,
    @ColumnInfo(name = "task_updated")
    val updated: Instant,
    @ColumnInfo(name = "task_reminder")
    val reminder: Instant,
    @ColumnInfo(name = "task_repeat_mode")
    val repeatMode: RepeatMode,
    @ColumnInfo(name = "task_is_completed", defaultValue = "false")
    val isCompleted: Boolean
)

fun TaskEntity.asExternalModel() = Task(
    id = id,
    title = title,
    created = created,
    updated = updated,
    reminder = reminder,
    repeatMode = repeatMode.asModelRepeatMode(),
    isCompleted = isCompleted,
)

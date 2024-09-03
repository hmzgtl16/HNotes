package com.example.hnotes.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.hnotes.core.database.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Upsert
    suspend fun upsertTasks(tasks: List<TaskEntity>)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Delete
    suspend fun deleteTasks(tasks: List<TaskEntity>)

    @Query(value = "SELECT * FROM table_task ORDER BY task_created, task_reminder DESC")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query(value = "SELECT * FROM table_task WHERE task_id = :id")
     fun getTaskById(id: Long): Flow<TaskEntity?>

    @Query(value = "SELECT * FROM table_task WHERE task_id IN (:ids) ORDER BY task_created, task_reminder DESC")
    fun getTasksByIds(ids: Set<String>): Flow<List<TaskEntity>>
}


package com.example.hnotes.core.data.repository

import com.example.hnotes.core.alarm.AlarmScheduler
import com.example.hnotes.core.data.utils.asEntity
import com.example.hnotes.core.database.dao.TaskDao
import com.example.hnotes.core.database.model.TaskEntity
import com.example.hnotes.core.database.model.asExternalModel
import com.example.hnotes.core.model.data.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface TaskRepository {

    val tasks: Flow<List<Task>>

    suspend fun getTask(id: Long): Flow<Task?>

    suspend fun updateTask(task: Task)
    suspend fun updateTasks(tasks: List<Task>)

    suspend fun deleteTask(task: Task)
    suspend fun deleteTasks(tasks: List<Task>): Result<Unit>
}

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val alarmScheduler: AlarmScheduler
) : TaskRepository {

    override val tasks: Flow<List<Task>> =
        taskDao.getTasks().map { it.map(TaskEntity::asExternalModel) }

    override suspend fun getTask(id: Long): Flow<Task?> =
        taskDao.getTaskById(id = id).map { it?.asExternalModel() }

    override suspend fun updateTask(task: Task) {
        taskDao.upsertTask(task = task.asEntity())
        alarmScheduler.cancel(task = task)
        alarmScheduler.schedule(task = task)
    }

    override suspend fun updateTasks(tasks: List<Task>) =
        taskDao.upsertTasks(tasks = tasks.map(Task::asEntity))

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task = task.asEntity())
        alarmScheduler.cancel(task = task)
    }

    override suspend fun deleteTasks(tasks: List<Task>) = runCatching {
        taskDao.deleteTasks(tasks = tasks.map(Task::asEntity))
        tasks.forEach(alarmScheduler::cancel)
    }
}


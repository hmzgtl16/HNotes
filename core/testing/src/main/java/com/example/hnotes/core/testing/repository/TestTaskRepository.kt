package com.example.hnotes.core.testing.repository

import com.example.hnotes.core.data.repository.TaskRepository
import com.example.hnotes.core.model.data.Task
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class TestTaskRepository() : TaskRepository {

    private val tasksFlow: MutableSharedFlow<List<Task>> =
    MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val tasks: Flow<List<Task>> = tasksFlow

    override suspend fun updateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTasks(tasks: List<Task>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTasks(tasks: List<Task>): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(id: Long): Flow<Task> = tasksFlow.map {
        it.first { task -> task.id == id }
    }
}
package com.example.hnotes.core.data.dao

import com.example.hnotes.core.database.dao.TaskDao
import com.example.hnotes.core.database.model.TaskEntity
import com.example.hnotes.core.database.util.RepeatMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Instant

class TestTaskDao: TaskDao {

    private val entitiesStateFlow = MutableStateFlow(
        value = mutableListOf(
            testTaskEntity(id = 1),
            testTaskEntity(id = 2),
            testTaskEntity(id = 3),
            testTaskEntity(id = 4),
            testTaskEntity(id = 5),
            testTaskEntity(id = 6),
            testTaskEntity(id = 7),
            testTaskEntity(id = 8),
            testTaskEntity(id = 9),
            testTaskEntity(id = 10)
        )
    )

    override suspend fun upsertTask(task: TaskEntity) {
        entitiesStateFlow.update {
            val toUpsertIndex = it.indexOfFirst { taskEntity ->
                taskEntity.id == task.id
            }

            if (toUpsertIndex != -1) it.set(index = toUpsertIndex, task)
            else it.add(element = task)

            it.sortByDescending(selector = TaskEntity::created)
            it
        }
    }

    override suspend fun upsertTasks(tasks: List<TaskEntity>) {
        entitiesStateFlow.update {

            it.addAll(elements = tasks)
            it.sortByDescending(selector = TaskEntity::created)
            it
        }
    }

    override suspend fun deleteTask(task: TaskEntity) {
        entitiesStateFlow.update { entities ->
            entities.removeIf { it.id == task.id }
            entities
        }
    }

    override suspend fun deleteTasks(tasks: List<TaskEntity>) {
        val ids = tasks.map(TaskEntity::id)
        entitiesStateFlow.update { entities ->
            entities.removeAll { it.id in ids }
            entities
        }
    }

    override fun getTaskById(id: Long): Flow<TaskEntity> = entitiesStateFlow
        .map { entities -> entities.first { it.id == id }}

    override fun getTasks(): Flow<List<TaskEntity>> = entitiesStateFlow
    override fun getTasksByIds(ids: Set<String>): Flow<List<TaskEntity>> {
        TODO("Not yet implemented")
    }

}

fun testTaskEntity(id: Long) = TaskEntity(
    id = id,
    title = "",
    created = Instant.fromEpochMilliseconds(epochMilliseconds = id),
    updated = Instant.fromEpochMilliseconds(epochMilliseconds = id),
    reminder = Instant.fromEpochMilliseconds(epochMilliseconds = (id + 4) * 3),
    repeatMode = RepeatMode.NONE,
    isCompleted = false
)
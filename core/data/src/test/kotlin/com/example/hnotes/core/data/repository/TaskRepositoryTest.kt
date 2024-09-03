package com.example.hnotes.core.data.repository

import com.example.hnotes.core.alarm.AlarmScheduler
import com.example.hnotes.core.alarm.AlarmSchedulerImpl
import com.example.hnotes.core.data.dao.TestTaskDao
import com.example.hnotes.core.data.dao.testTaskEntity
import com.example.hnotes.core.database.model.TaskEntity
import com.example.hnotes.core.database.model.asExternalModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskRepositoryTest {

    private lateinit var repository: TaskRepositoryImpl
    private lateinit var dao: TestTaskDao
    private lateinit var alarmScheduler: AlarmScheduler

    @Before
    fun setup() {
        dao = TestTaskDao()
        alarmScheduler = AlarmSchedulerImpl(context = )
        repository = TaskRepositoryImpl(taskDao = dao, alarmScheduler = alarmScheduler)
    }

    @Test
    fun taskRepository_get_tasks_by_task_dao() = runTest {

        assertEquals(
            expected = dao.getTasks().first().map(TaskEntity::asExternalModel),
            actual = repository.tasks.first()
        )
    }

    @Test
    fun taskRepository_get_task_by_task_dao() = runTest {
        val id = 1L
        assertEquals(
            expected = dao.getTaskById(id = id).first().asExternalModel(),
            actual = repository.getTask(id = id).first()
        )
    }

    @Test
    fun taskRepository_add_task_by_task_dao() = runTest {

        val toAdd = testTaskEntity(id = 11L)
        //repository.addTask(task = toAdd.asExternalModel())

        assertEquals(
            expected = toAdd.asExternalModel(),
            actual = repository.getTask(id = toAdd.id).first()
        )

        assertEquals(
            expected = dao.getTaskById(id = toAdd.id).first().asExternalModel(),
            actual = repository.getTask(id = toAdd.id).first()
        )
    }

    @Test
    fun taskRepository_update_task_by_task_dao() = runTest {
        val id = 1L
        val toUpdate = dao.getTaskById(id = id).first()
        repository.updateTask(task = toUpdate.copy(title = "Updated").asExternalModel())

        assertEquals(
            expected = "Updated",
            actual = repository.getTask(id = id).first()?.title
        )

        assertEquals(
            expected = dao.getTaskById(id = id).first().asExternalModel(),
            actual = repository.getTask(id = id).first()
        )
    }

    @Test
    fun taskRepository_delete_task_by_task_dao() = runTest {
        val id = 1L
        val toDelete = dao.getTaskById(id = id).first()

        repository.deleteTask(task = toDelete.asExternalModel())

        assertEquals(
            expected = dao.getTasks().first().map(TaskEntity::asExternalModel),
            actual = repository.tasks.first()
        )
    }
}
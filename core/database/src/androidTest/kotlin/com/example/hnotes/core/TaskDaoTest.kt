package com.example.hnotes.core

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.hnotes.core.database.HNotesDatabase
import com.example.hnotes.core.database.dao.TaskDao
import com.example.hnotes.core.database.model.TaskEntity
import com.example.hnotes.core.database.util.RepeatMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class TaskDaoTest {

    private lateinit var db: HNotesDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            HNotesDatabase::class.java,
        ).build()
        taskDao = db.taskDao()
    }

    @After
    fun closeDb() = db.close()

    @Test
    fun taskDao_fetches_tasks_by_descending_created_date() = runTest {

        val taskEntities = listOf(
            testTaskEntity(id = 4),
            testTaskEntity(id = 1),
            testTaskEntity(id = 6),
            testTaskEntity(id = 5),
            testTaskEntity(id = 7),
            testTaskEntity(id = 3),
            testTaskEntity(id = 2)
        )

        taskDao.upsertTasks(tasks = taskEntities)

        val savedTaskEntities = taskDao.getTasks().first()

        val expected = listOf(7L, 6L, 5L, 4L, 3L, 2L, 1L).reversed()
        val actual = savedTaskEntities.map(TaskEntity::id)

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun taskDao_fetches_tasks_by_ids_by_descending_created_date() = runTest {

        val taskEntities = listOf(
            testTaskEntity(id = 4),
            testTaskEntity(id = 1),
            testTaskEntity(id = 6),
            testTaskEntity(id = 5),
            testTaskEntity(id = 7),
            testTaskEntity(id = 3),
            testTaskEntity(id = 2)
        )

        taskDao.upsertTasks(tasks = taskEntities)

        val savedTaskEntities = taskDao.getTasksByIds(ids = setOf("7", "2", "1")).first()

        val expected = listOf(7L, 2L, 1L).reversed()
        val actual = savedTaskEntities.map(TaskEntity::id)

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun taskDao_fetch_task_by_id() = runTest {

        val taskEntity = testTaskEntity(id = 100)

        taskDao.upsertTask(task = taskEntity)

        val savedTaskEntity = taskDao.getTaskById(id = taskEntity.id).first()

        val expected = taskEntity.id
        val actual = savedTaskEntity?.id

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun taskDao_deletes_tasks_by_ids() = runTest {

        val taskEntities = listOf(
            testTaskEntity(id = 1),
            testTaskEntity(id = 2),
            testTaskEntity(id = 3),
            testTaskEntity(id = 4),
            testTaskEntity(id = 5),
            testTaskEntity(id = 6),
            testTaskEntity(id = 7)
        )

        taskDao.upsertTasks(tasks = taskEntities)

        val (toDelete, toKeep) =
            taskEntities.partition { it.id.toInt() % 2 == 0 }

        taskDao.deleteTasks(tasks = toDelete)

        val remainingTaskEntities = taskDao.getTasks().first()

        assertEquals(
            expected = toKeep.map(TaskEntity::id).toSet(),
            actual = remainingTaskEntities.map(TaskEntity::id).toSet()
        )
    }

    @Test
    fun taskDao_delete_task() = runTest {

        val taskEntities = listOf(
            testTaskEntity(id = 1),
            testTaskEntity(id = 2),
            testTaskEntity(id = 3),
            testTaskEntity(id = 4),
            testTaskEntity(id = 5),
            testTaskEntity(id = 6),
            testTaskEntity(id = 7)
        )

        taskDao.upsertTasks(tasks = taskEntities)

        taskDao.deleteTask(task = taskEntities.first())

        val remainingTaskEntities = taskDao.getTasks().first()

        assertEquals(
            expected = remainingTaskEntities.contains(taskEntities.first()),
            actual = false
        )
    }
}

private fun testTaskEntity(id: Long) = TaskEntity(
    id = id,
    title = "",
    created = Instant.fromEpochMilliseconds(epochMilliseconds = id),
    updated = Instant.fromEpochMilliseconds(epochMilliseconds = id),
    reminder = Instant.fromEpochMilliseconds(epochMilliseconds = id + 1),
    repeatMode = RepeatMode.NONE,
    isCompleted = false
)

package com.example.hnotes.core

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.hnotes.core.database.HNotesDatabase
import com.example.hnotes.core.database.dao.ListDao
import com.example.hnotes.core.database.model.ItemEntity
import com.example.hnotes.core.database.model.ListEntity
import com.example.hnotes.core.database.model.ListWithItems
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ListDaoTest {

    private lateinit var db: HNotesDatabase
    private lateinit var listDao: ListDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            HNotesDatabase::class.java,
        ).build()
        listDao = db.listDao()
    }

    @After
    fun closeDb() = db.close()

    @Test
    fun listDao_fetches_lists_by_descending_created_date() = runTest {

        val listEntities = listOf(
            testListEntity(id = 4),
            testListEntity(id = 1),
            testListEntity(id = 6),
            testListEntity(id = 5)
        )

        val itemEntities = listOf(
            testItemEntity(id = 1, listId = 1),
            testItemEntity(id = 2, listId = 5),
            testItemEntity(id = 3, listId = 1),
            testItemEntity(id = 4, listId = 6),
            testItemEntity(id = 5, listId = 1),
            testItemEntity(id = 6, listId = 4),
            testItemEntity(id = 7, listId = 6),
            testItemEntity(id = 8, listId = 4),
            testItemEntity(id = 9, listId = 1),
            testItemEntity(id = 10, listId = 4),
            testItemEntity(id = 11, listId = 4),
            testItemEntity(id = 12, listId = 1),
            testItemEntity(id = 13, listId = 6),
            testItemEntity(id = 14, listId = 6),
            testItemEntity(id = 15, listId = 5),
            testItemEntity(id = 16, listId = 5)
        )

        listEntities.forEach { listEntity ->
            listDao.upsertListWithItems(
                list = listEntity,
                items = itemEntities.filter { it.listId == listEntity.id }
            )
        }

        val savedListEntities = listDao.getLists().first()

        val expected = listOf<Long>(6L, 5L, 4L, 1L)

        val actual = savedListEntities
            .map(ListWithItems::list)
            .map(ListEntity::id)


        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun listDao_fetches_lists_by_ids_by_descending_created_date() = runTest {

        val listEntities = listOf(
            testListEntity(id = 4),
            testListEntity(id = 1),
            testListEntity(id = 6),
            testListEntity(id = 5)
        )

        val itemEntities = listOf(
            testItemEntity(id = 1, listId = 1),
            testItemEntity(id = 2, listId = 5),
            testItemEntity(id = 3, listId = 1),
            testItemEntity(id = 4, listId = 6),
            testItemEntity(id = 5, listId = 1),
            testItemEntity(id = 6, listId = 4),
            testItemEntity(id = 7, listId = 6),
            testItemEntity(id = 8, listId = 4),
            testItemEntity(id = 9, listId = 1),
            testItemEntity(id = 10, listId = 4),
            testItemEntity(id = 11, listId = 4),
            testItemEntity(id = 12, listId = 1),
            testItemEntity(id = 13, listId = 6),
            testItemEntity(id = 14, listId = 6),
            testItemEntity(id = 15, listId = 5),
            testItemEntity(id = 16, listId = 5)
        )

        listEntities.forEach { listEntity ->
            listDao.upsertListWithItems(
                list = listEntity,
                items = itemEntities.filter { it.listId == listEntity.id }
            )
        }

        val savedListEntities = listDao.getListsByIds(ids = setOf("1", "5")).first()

        val expected = listOf<Long>(5L, 1L)

        val actual = savedListEntities
            .map(ListWithItems::list)
            .map(ListEntity::id)

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun listDao_fetch_list_by_id() = runTest {

        val listEntity = testListEntity(id = 100)

        val itemEntities = listOf(
            testItemEntity(id = 1, listId = 100),
            testItemEntity(id = 2, listId = 100),
            testItemEntity(id = 3, listId = 100),
            testItemEntity(id = 4, listId = 100),
            testItemEntity(id = 5, listId = 100),
            testItemEntity(id = 6, listId = 100),
            testItemEntity(id = 7, listId = 100),
            testItemEntity(id = 8, listId = 100),
            testItemEntity(id = 9, listId = 100),
            testItemEntity(id = 10, listId = 100),
            testItemEntity(id = 11, listId = 100),
            testItemEntity(id = 12, listId = 100),
            testItemEntity(id = 13, listId = 100),
            testItemEntity(id = 14, listId = 100),
            testItemEntity(id = 15, listId = 100)
        )

        listDao.upsertListWithItems(list = listEntity, items = itemEntities)

        val savedListEntity = listDao.getListById(id = listEntity.id).first()

        val expected = listEntity.id

        val actual = savedListEntity?.list?.id

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun listDao_deletes_lists() = runTest {

        val listEntities = listOf(
            testListEntity(id = 4),
            testListEntity(id = 1),
            testListEntity(id = 6),
            testListEntity(id = 5)
        )

        val itemEntities = listOf(
            testItemEntity(id = 1, listId = 1),
            testItemEntity(id = 2, listId = 5),
            testItemEntity(id = 3, listId = 1),
            testItemEntity(id = 4, listId = 6),
            testItemEntity(id = 5, listId = 1),
            testItemEntity(id = 6, listId = 4),
            testItemEntity(id = 7, listId = 6),
            testItemEntity(id = 8, listId = 4),
            testItemEntity(id = 9, listId = 1),
            testItemEntity(id = 10, listId = 4),
            testItemEntity(id = 11, listId = 4),
            testItemEntity(id = 12, listId = 1),
            testItemEntity(id = 13, listId = 6),
            testItemEntity(id = 14, listId = 6),
            testItemEntity(id = 15, listId = 5),
            testItemEntity(id = 16, listId = 5)
        )

        listEntities.forEach { listEntity ->
            listDao.upsertListWithItems(
                list = listEntity,
                items = itemEntities.filter { it.listId == listEntity.id }
            )
        }

        val (toDelete, toKeep) =
            listEntities.partition { it.id.toInt() % 2 == 0 }

        listDao.deleteLists(lists = toDelete)

        val remainingListEntities = listDao.getLists().first()

        val expected = toKeep.map(ListEntity::id).toSet()
        val actual = remainingListEntities.map(ListWithItems::list).map(ListEntity::id).toSet()

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun listDao_delete_list() = runTest {

        val listEntities = listOf(
            testListEntity(id = 4),
            testListEntity(id = 1),
            testListEntity(id = 6),
            testListEntity(id = 5)
        )

        val itemEntities = listOf(
            testItemEntity(id = 1, listId = 1),
            testItemEntity(id = 2, listId = 5),
            testItemEntity(id = 3, listId = 1),
            testItemEntity(id = 4, listId = 6),
            testItemEntity(id = 5, listId = 1),
            testItemEntity(id = 6, listId = 4),
            testItemEntity(id = 7, listId = 6),
            testItemEntity(id = 8, listId = 4),
            testItemEntity(id = 9, listId = 1),
            testItemEntity(id = 10, listId = 4),
            testItemEntity(id = 11, listId = 4),
            testItemEntity(id = 12, listId = 1),
            testItemEntity(id = 13, listId = 6),
            testItemEntity(id = 14, listId = 6),
            testItemEntity(id = 15, listId = 5),
            testItemEntity(id = 16, listId = 5)
        )

        listEntities.forEach { listEntity ->
            listDao.upsertListWithItems(
                list = listEntity,
                items = itemEntities.filter { it.listId == listEntity.id }
            )
        }

        val toDelete = listEntities.first { it.id == 1L  }

        listDao.deleteList(list = toDelete)

        val remainingListEntities = listDao.getLists().first()

        val expected = listEntities.filter { it.id != 1L }.map(ListEntity::id).toSet()
        val actual = remainingListEntities.map(ListWithItems::list).map(ListEntity::id).toSet()

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun listDao_delete_list_item() = runTest {

        val listEntities = listOf(
            testListEntity(id = 4),
            testListEntity(id = 1),
            testListEntity(id = 6),
            testListEntity(id = 5)
        )

        val itemEntities = listOf(
            testItemEntity(id = 1, listId = 1),
            testItemEntity(id = 2, listId = 5),
            testItemEntity(id = 3, listId = 1),
            testItemEntity(id = 4, listId = 6),
            testItemEntity(id = 5, listId = 1),
            testItemEntity(id = 6, listId = 4),
            testItemEntity(id = 7, listId = 6),
            testItemEntity(id = 8, listId = 4),
            testItemEntity(id = 9, listId = 1),
            testItemEntity(id = 10, listId = 4),
            testItemEntity(id = 11, listId = 4),
            testItemEntity(id = 12, listId = 1),
            testItemEntity(id = 13, listId = 6),
            testItemEntity(id = 14, listId = 6),
            testItemEntity(id = 15, listId = 5),
            testItemEntity(id = 16, listId = 5)
        )

        listEntities.forEach { listEntity ->
            listDao.upsertListWithItems(
                list = listEntity,
                items = itemEntities.filter { it.listId == listEntity.id }
            )
        }

        listDao.deleteItem(item = itemEntities.first())

        val remainingListEntities = listDao.getLists().first()

        val expected = remainingListEntities.none { it.items.contains(itemEntities.first()) }

        assertEquals(expected = expected, actual = true)
    }
}

private fun testListEntity(id: Long) = ListEntity(
    id = id,
    title = "",
    created = Instant.fromEpochMilliseconds(epochMilliseconds = id),
    updated = Instant.fromEpochMilliseconds(epochMilliseconds = id),
    isPinned = false,
    backgroundColor = null
)

private fun testItemEntity(id: Long, listId: Long) = ItemEntity(
    id = id,
    title = "",
    isCompleted = false,
    listId = listId
)

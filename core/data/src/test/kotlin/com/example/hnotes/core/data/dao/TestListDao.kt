package com.example.hnotes.core.data.dao

import com.example.hnotes.core.database.dao.ListDao
import com.example.hnotes.core.database.model.ItemEntity
import com.example.hnotes.core.database.model.ListEntity
import com.example.hnotes.core.database.model.ListWithItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Instant

class TestListDao : ListDao {
    private val listEntitiesStateFlow = MutableStateFlow(
        value = mutableListOf(
            testListEntity(id = 1),
            testListEntity(id = 2),
            testListEntity(id = 3),
            testListEntity(id = 4)
        )
    )

    private val itemEntitiesStateFlow = MutableStateFlow(
        value = mutableListOf(
            testItemEntity(id = 1, listId = 1),
            testItemEntity(id = 2, listId = 1),
            testItemEntity(id = 3, listId = 1),
            testItemEntity(id = 4, listId = 1),
            testItemEntity(id = 5, listId = 2),
            testItemEntity(id = 6, listId = 2),
            testItemEntity(id = 7, listId = 2),
            testItemEntity(id = 8, listId = 2),
            testItemEntity(id = 9, listId = 3),
            testItemEntity(id = 10, listId = 3),
            testItemEntity(id = 11, listId = 3),
            testItemEntity(id = 12, listId = 3),
            testItemEntity(id = 13, listId = 4),
            testItemEntity(id = 14, listId = 4),
            testItemEntity(id = 15, listId = 4),
            testItemEntity(id = 16, listId = 4)
        )
    )

    override suspend fun upsertList(list: ListEntity): Long {
        listEntitiesStateFlow.update {
            val toUpsertIndex = it.indexOfFirst { listEntity ->
                listEntity.id == list.id
            }

            if (toUpsertIndex != -1) it.set(index = toUpsertIndex, list)
            else it.add(element = list)

            it.sortByDescending(selector = ListEntity::timestamp)
            it
        }
        return listEntitiesStateFlow.value.first { it.id == list.id }.id
    }

    override suspend fun upsertLists(lists: List<ListEntity>): List<Long> {
        listEntitiesStateFlow.update {
            it.addAll(elements = lists)

            it.sortByDescending(selector = ListEntity::timestamp)
            it
        }
        return listEntitiesStateFlow.value
            .map(ListEntity::id)
            .filter { it in lists.map(ListEntity::id) }
    }

    override suspend fun upsertItem(item: ItemEntity) {
        itemEntitiesStateFlow.update {
            it.add(element = item)
            it
        }
    }

    override suspend fun upsertItems(items: List<ItemEntity>) {
        itemEntitiesStateFlow.update {
            it.addAll(elements = items)
            it
        }
    }

    override suspend fun deleteList(list: ListEntity) {
        listEntitiesStateFlow.update { entities ->
            entities.removeIf { it.id == list.id }
            entities
        }
    }

    override suspend fun deleteLists(lists: List<ListEntity>) {
        val ids = lists.map(ListEntity::id)
        listEntitiesStateFlow.update { entities ->
            entities.removeAll { it.id in ids }
            entities
        }
    }

    override fun getLists(): Flow<List<ListWithItems>> = listEntitiesStateFlow
        .map {
            it.map { listEntity ->
                ListWithItems(
                    list = listEntity,
                    items = itemEntitiesStateFlow.value
                        .filter { itemEntity -> itemEntity.listId == listEntity.id }
                )
            }
        }

    override fun getListById(id: Long): Flow<ListWithItems> = listEntitiesStateFlow
        .map { entities -> entities.first { it.id == id } }
        .map { entity ->
            ListWithItems(
                list = entity,
                items = itemEntitiesStateFlow.value.filter { it.listId == entity.id }
            )
        }

    override fun getItems(): Flow<List<ItemEntity>> = itemEntitiesStateFlow
}

fun testListEntity(id: Long) = ListEntity(
    id = id,
    title = "",
    timestamp = Instant.fromEpochMilliseconds(epochMilliseconds = id),
    isPinned = false
)

fun testItemEntity(id: Long, listId: Long) = ItemEntity(
    id = id,
    title = "",
    isCompleted = false,
    listId = listId
)
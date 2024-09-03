package com.example.hnotes.core.data.repository

import com.example.hnotes.core.data.utils.asEntity
import com.example.hnotes.core.database.dao.ListDao
import com.example.hnotes.core.database.model.ListWithItems
import com.example.hnotes.core.database.model.asExternalModel
import com.example.hnotes.core.model.data.Item
import com.example.hnotes.core.model.data.List
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.List as KList

interface ListRepository {

    val lists: Flow<KList<List>>

    suspend fun getList(id: Long): Flow<List?>

    suspend fun updateList(list: List)
    suspend fun updateLists(lists: KList<List>)

    suspend fun deleteList(list: List): Result<Unit>
    suspend fun deleteLists(lists: KList<List>): Result<Unit>

    suspend fun deleteItem(item: Item): Result<Unit>
}

class ListRepositoryImpl @Inject constructor(
    private val listDao: ListDao
) : ListRepository {

    override val lists: Flow<KList<List>> =
        listDao.getLists().map { it.map(ListWithItems::asExternalModel) }

    override suspend fun getList(id: Long): Flow<List?> =
        listDao.getListById(id = id).map { it?.asExternalModel() }

    override suspend fun updateList(list: List) {
        listDao.upsertListWithItems(
            list = list.asEntity(),
            items = list.items.map(Item::asEntity)
        )
    }

    override suspend fun updateLists(lists: KList<List>) =
        lists.forEach { updateList(list = it) }

    override suspend fun deleteList(list: List) = runCatching {
        listDao.deleteList(list = list.asEntity())
    }

    override suspend fun deleteLists(lists: KList<List>) = runCatching {
        listDao.deleteLists(lists = lists.map(List::asEntity))
    }

    override suspend fun deleteItem(item: Item) = runCatching {
        listDao.deleteItem(item = item.asEntity())
    }
}
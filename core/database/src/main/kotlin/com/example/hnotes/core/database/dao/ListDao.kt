package com.example.hnotes.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.hnotes.core.database.model.ItemEntity
import com.example.hnotes.core.database.model.ListEntity
import com.example.hnotes.core.database.model.ListWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {

    @Upsert
    suspend fun upsertList(list: ListEntity): Long

    @Upsert
    suspend fun upsertItems(items: List<ItemEntity>)

    @Transaction
    suspend fun upsertListWithItems(list: ListEntity, items: List<ItemEntity>) {
        val listId = upsertList(list = list)
        items.forEach { it.listId = listId.takeIf { id -> id != -1L } ?: list.id }
        upsertItems(items = items)
    }

    @Delete
    suspend fun deleteList(list: ListEntity)

    @Delete
    suspend fun deleteLists(lists: List<ListEntity>)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Transaction
    @Query(value = "SELECT * FROM table_list ORDER BY list_created DESC")
    fun getLists(): Flow<List<ListWithItems>>

    @Transaction
    @Query(value = "SELECT * FROM table_list WHERE list_id = :id")
    fun getListById(id: Long): Flow<ListWithItems?>

    @Transaction
    @Query(value = "SELECT * FROM table_list WHERE list_id IN (:ids) ORDER BY list_created DESC")
    fun getListsByIds(ids: Set<String>): Flow<List<ListWithItems>>
}
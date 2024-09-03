package com.example.hnotes.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.hnotes.core.model.data.Item
import com.example.hnotes.core.model.data.List
import kotlin.collections.List as KList

data class ListWithItems(
    @Embedded val list: ListEntity,
    @Relation(
        parentColumn = "list_id",
        entityColumn = "item_list_id"
    )
    val items: KList<ItemEntity>
)

fun ListWithItems.asExternalModel() = List(
    id = list.id,
    title = list.title,
    created = list.created,
    updated = list.updated,
    isPinned = list.isPinned,
    items = items.map(ItemEntity::asExternalModel),
    backgroundColor = list.backgroundColor
)
package com.example.hnotes.core.data.utils

import com.example.hnotes.core.database.model.ItemEntity
import com.example.hnotes.core.database.model.ListEntity
import com.example.hnotes.core.database.model.NoteEntity
import com.example.hnotes.core.database.model.SearchQueryEntity
import com.example.hnotes.core.database.model.TaskEntity
import com.example.hnotes.core.model.data.Item
import com.example.hnotes.core.model.data.List
import com.example.hnotes.core.model.data.Note
import com.example.hnotes.core.model.data.Task
import com.example.hnotes.core.database.util.RepeatMode as EntityRepeatMode
import com.example.hnotes.core.model.data.RepeatMode
import com.example.hnotes.core.model.data.SearchQuery
import kotlin.collections.List as KList

fun Note.asEntity() = NoteEntity(
    id = id,
    title = title,
    description = description,
    created = created,
    updated = updated,
    isPinned = isPinned,
    backgroundColor = backgroundColor,
)

fun Task.asEntity() = TaskEntity(
    id = id,
    title = title,
    created = created,
    updated = updated,
    reminder = reminder,
    repeatMode = repeatMode.asEntityRepeatMode(),
    isCompleted = isCompleted,
)

fun Item.asEntity(listId: Long = 0L) = ItemEntity(
    id = id,
    title = title,
    isCompleted = isCompleted,
    listId = listId
)

fun List.asEntity() = ListEntity(
    id = id,
    title = title,
    created = created,
    updated = updated,
    isPinned = isPinned,
    backgroundColor = backgroundColor
)

fun RepeatMode.asEntityRepeatMode(): EntityRepeatMode =
    EntityRepeatMode.entries.first { it.name == name }

fun SearchQuery.asEntity(): SearchQueryEntity =
    SearchQueryEntity(text = query, queried = queried)
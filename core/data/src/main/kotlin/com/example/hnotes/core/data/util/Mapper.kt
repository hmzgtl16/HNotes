package com.example.hnotes.core.data.util

import com.example.hnotes.core.database.model.ItemEntity
import com.example.hnotes.core.database.model.LabelEntity
import com.example.hnotes.core.database.model.NoteEntity
import com.example.hnotes.core.database.model.PopulatedNoteEntity
import com.example.hnotes.core.database.model.ReminderEntity
import com.example.hnotes.core.database.model.SearchQueryEntity
import com.example.hnotes.core.model.Item
import com.example.hnotes.core.model.Label
import com.example.hnotes.core.model.Note
import com.example.hnotes.core.model.Reminder
import com.example.hnotes.core.model.RepeatMode
import com.example.hnotes.core.model.SearchQuery
import com.example.hnotes.core.database.util.RepeatMode as ReminderRepeatModeEntity

fun Note.toEntity(): NoteEntity = NoteEntity(
    id = id,
    title = title,
    content = content,
    pinned = pinned,
    backgroundColor = backgroundColor,
    createdAt = created,
    updatedAt = updated
)

fun Label.toEntity(): LabelEntity = LabelEntity(
    id = id,
    name = name,
    backgroundColor = backgroundColor
)

fun LabelEntity.toModel(): Label = Label(
    id = id,
    name = name,
    backgroundColor = backgroundColor
)

fun Reminder.toEntity(noteId: Long = 0L): ReminderEntity = ReminderEntity(
    id = id,
    time = time,
    repeatMode = repeatMode.toEntity(),
    noteId = noteId
)

fun Item.toEntity(noteId: Long = 0L): ItemEntity = ItemEntity(
    id = id,
    content = content,
    checked = checked,
    noteId = noteId
)

fun ReminderEntity.toModel(): Reminder = Reminder(
    id = id,
    time = time,
    repeatMode = repeatMode.toModel()
)

fun ItemEntity.toModel(): Item = Item(
    id = id,
    content = content,
    checked = checked
)

fun PopulatedNoteEntity.toModel(): Note = Note(
    id = note.id,
    title = note.title,
    content = note.content,
    pinned = note.pinned,
    backgroundColor = note.backgroundColor,
    created = note.createdAt,
    updated = note.updatedAt,
    reminder = reminder?.toModel(),
    items = items.map(ItemEntity::toModel),
    labels = labels.map(LabelEntity::toModel)
)

fun RepeatMode.toEntity(): ReminderRepeatModeEntity = when (this) {
    RepeatMode.NONE -> ReminderRepeatModeEntity.NONE
    RepeatMode.DAILY -> ReminderRepeatModeEntity.DAILY
    RepeatMode.WEEKLY -> ReminderRepeatModeEntity.WEEKLY
    RepeatMode.MONTHLY -> ReminderRepeatModeEntity.MONTHLY
    RepeatMode.YEARLY -> ReminderRepeatModeEntity.YEARLY
}

fun ReminderRepeatModeEntity.toModel(): RepeatMode = when (this) {
    ReminderRepeatModeEntity.NONE -> RepeatMode.NONE
    ReminderRepeatModeEntity.DAILY -> RepeatMode.DAILY
    ReminderRepeatModeEntity.WEEKLY -> RepeatMode.WEEKLY
    ReminderRepeatModeEntity.MONTHLY -> RepeatMode.MONTHLY
    ReminderRepeatModeEntity.YEARLY -> RepeatMode.YEARLY
}

fun SearchQuery.toEntity(): SearchQueryEntity = SearchQueryEntity(
    text = query,
    queried = queried
)

fun SearchQueryEntity.toModel(): SearchQuery = SearchQuery(
    query = text,
    queried = queried
)

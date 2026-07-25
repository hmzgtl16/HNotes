package com.example.hnotes.core.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PopulatedNoteEntity(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "noteId"
    )
    val items: List<ItemEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "noteId"
    )
    val reminder: ReminderEntity?,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NoteLabelCrossRef::class,
            parentColumn = "noteId",
            entityColumn = "labelId"
        )
    )
    val labels: List<LabelEntity>
)


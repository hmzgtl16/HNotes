package com.example.hnotes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hnotes.core.database.dao.ListDao
import com.example.hnotes.core.database.dao.ListFtsDao
import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.database.dao.NoteFtsDao
import com.example.hnotes.core.database.dao.SearchQueryDao
import com.example.hnotes.core.database.dao.TaskDao
import com.example.hnotes.core.database.dao.TaskFtsDao
import com.example.hnotes.core.database.model.ItemEntity
import com.example.hnotes.core.database.model.ItemFtsEntity
import com.example.hnotes.core.database.model.ListEntity
import com.example.hnotes.core.database.model.ListFtsEntity
import com.example.hnotes.core.database.model.NoteEntity
import com.example.hnotes.core.database.model.NoteFtsEntity
import com.example.hnotes.core.database.model.SearchQueryEntity
import com.example.hnotes.core.database.model.TaskEntity
import com.example.hnotes.core.database.model.TaskFtsEntity
import com.example.hnotes.core.database.util.InstantConverter
import com.example.hnotes.core.database.util.RepeatModeConverter

@Database(
    entities = [
        ItemEntity::class,
        ItemFtsEntity::class,
        ListEntity::class,
        ListFtsEntity::class,
        NoteEntity::class,
        NoteFtsEntity::class,
        SearchQueryEntity::class,
        TaskEntity::class,
        TaskFtsEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    value = [
        InstantConverter::class,
        RepeatModeConverter::class
    ]
)
internal abstract class HNotesDatabase : RoomDatabase() {

    abstract fun listDao(): ListDao
    abstract fun listFtsDao(): ListFtsDao
    abstract fun noteDao(): NoteDao
    abstract fun noteFtsDao(): NoteFtsDao
    abstract fun searchQueryDao(): SearchQueryDao
    abstract fun taskDao(): TaskDao
    abstract fun taskFtsDao(): TaskFtsDao

}

const val H_NOTES_DATABASE_NAME = "hnotes-database"

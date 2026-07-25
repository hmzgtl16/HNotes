package com.example.hnotes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hnotes.core.database.dao.LabelDao
import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.database.dao.NoteFtsDao
import com.example.hnotes.core.database.dao.SearchQueryDao
import com.example.hnotes.core.database.model.ItemEntity
import com.example.hnotes.core.database.model.ItemFtsEntity
import com.example.hnotes.core.database.model.LabelEntity
import com.example.hnotes.core.database.model.NoteEntity
import com.example.hnotes.core.database.model.NoteFtsEntity
import com.example.hnotes.core.database.model.NoteLabelCrossRef
import com.example.hnotes.core.database.model.ReminderEntity
import com.example.hnotes.core.database.model.SearchQueryEntity
import com.example.hnotes.core.database.util.InstantConverter
import com.example.hnotes.core.database.util.RepeatModeConverter

@Database(
    entities = [
        NoteEntity::class,
        ReminderEntity::class,
        ItemEntity::class,
        SearchQueryEntity::class,
        NoteFtsEntity::class,
        ItemFtsEntity::class,
        LabelEntity::class,
        NoteLabelCrossRef::class
    ],
    version = 3,
    exportSchema = true
)
@TypeConverters(
        InstantConverter::class,
    RepeatModeConverter::class
)
internal abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun labelDao(): LabelDao
    abstract fun searchQueryDao(): SearchQueryDao
    abstract fun noteFtsDao(): NoteFtsDao
}

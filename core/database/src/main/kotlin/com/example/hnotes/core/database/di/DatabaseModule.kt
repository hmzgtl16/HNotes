package com.example.hnotes.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.hnotes.core.database.HNotesDatabase
import com.example.hnotes.core.database.H_NOTES_DATABASE_NAME
import com.example.hnotes.core.database.util.ItemCallback
import com.example.hnotes.core.database.util.ListCallback
import com.example.hnotes.core.database.util.NoteCallback
import com.example.hnotes.core.database.util.TaskCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideHNotesDatabase(
        @ApplicationContext context: Context
    ): HNotesDatabase = Room.databaseBuilder(
        context = context,
        klass = HNotesDatabase::class.java,
        name = H_NOTES_DATABASE_NAME
    )
        .addCallback(callback = ItemCallback)
        .addCallback(callback = ListCallback)
        .addCallback(callback = NoteCallback)
        .addCallback(callback = TaskCallback)
        .build()
}


package com.example.hnotes.core.database.di

import com.example.hnotes.core.database.HNotesDatabase
import com.example.hnotes.core.database.dao.ListDao
import com.example.hnotes.core.database.dao.ListFtsDao
import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.database.dao.NoteFtsDao
import com.example.hnotes.core.database.dao.SearchQueryDao
import com.example.hnotes.core.database.dao.TaskDao
import com.example.hnotes.core.database.dao.TaskFtsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun provideListDao(
        database: HNotesDatabase
    ): ListDao = database.listDao()

    @Provides
    fun provideListFtsDao(
        database: HNotesDatabase
    ): ListFtsDao = database.listFtsDao()

    @Provides
    fun provideNoteDao(
        database: HNotesDatabase
    ): NoteDao = database.noteDao()

    @Provides
    fun provideNoteFtsDao(
        database: HNotesDatabase
    ): NoteFtsDao = database.noteFtsDao()

    @Provides
    fun provideSearchQueryDao(
        database: HNotesDatabase
    ): SearchQueryDao = database.searchQueryDao()

    @Provides
    fun provideTaskDao(
        database: HNotesDatabase
    ): TaskDao = database.taskDao()

    @Provides
    fun provideTaskFtsDao(
        database: HNotesDatabase
    ): TaskFtsDao = database.taskFtsDao()
}
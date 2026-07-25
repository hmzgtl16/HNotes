package com.example.hnotes.core.database.di

import com.example.hnotes.core.database.ApplicationDatabase
import com.example.hnotes.core.database.dao.LabelDao
import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.database.dao.NoteFtsDao
import com.example.hnotes.core.database.dao.SearchQueryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun provideNoteDao(database: ApplicationDatabase): NoteDao =
        database.noteDao()

    @Provides
    fun provideLabelDao(database: ApplicationDatabase): LabelDao =
        database.labelDao()

    @Provides
    fun provideSearchQueryDao(database: ApplicationDatabase): SearchQueryDao =
        database.searchQueryDao()

    @Provides
    fun provideNoteFtsDao(database: ApplicationDatabase): NoteFtsDao =
        database.noteFtsDao()
}
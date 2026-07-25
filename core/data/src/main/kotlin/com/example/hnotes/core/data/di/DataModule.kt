package com.example.hnotes.core.data.di

import com.example.hnotes.core.data.repository.LabelRepository
import com.example.hnotes.core.data.repository.LabelRepositoryImpl
import com.example.hnotes.core.data.repository.NoteRepository
import com.example.hnotes.core.data.repository.NoteRepositoryImpl
import com.example.hnotes.core.data.repository.SearchRepository
import com.example.hnotes.core.data.repository.SearchRepositoryImpl
import com.example.hnotes.core.data.repository.UserDataRepository
import com.example.hnotes.core.data.repository.UserDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl
    ): UserDataRepository

    @Binds
    internal abstract fun bindsNoteRepository(
        noteRepository: NoteRepositoryImpl
    ): NoteRepository

    @Binds
    internal abstract fun bindsLabelRepository(
        labelRepository: LabelRepositoryImpl
    ): LabelRepository

    @Binds
    internal abstract fun bindsSearchRepository(
        searchRepository: SearchRepositoryImpl
    ): SearchRepository
}

package com.example.hnotes.core.data.di

import com.example.hnotes.core.data.repository.ListRepository
import com.example.hnotes.core.data.repository.ListRepositoryImpl
import com.example.hnotes.core.data.repository.NoteRepository
import com.example.hnotes.core.data.repository.NoteRepositoryImpl
import com.example.hnotes.core.data.repository.SearchRepository
import com.example.hnotes.core.data.repository.SearchRepositoryImpl
import com.example.hnotes.core.data.repository.TaskRepository
import com.example.hnotes.core.data.repository.TaskRepositoryImpl
import com.example.hnotes.core.data.repository.UserDataRepository
import com.example.hnotes.core.data.repository.UserDataRepositoryImpl
import com.example.hnotes.core.data.utils.TimeZoneBroadcastMonitor
import com.example.hnotes.core.data.utils.TimeZoneMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindsNoteRepository(
        noteRepository: NoteRepositoryImpl
    ): NoteRepository

    @Binds
    internal abstract fun bindsTaskRepository(
        taskRepository: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    internal abstract fun bindsListRepository(
        listRepository: ListRepositoryImpl
    ): ListRepository

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl
    ): UserDataRepository

    @Binds
    internal abstract fun bindsSearchRepository(
        searchRepository: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    internal abstract fun bindsTimeZoneMonitor(
        timeZoneMonitor: TimeZoneBroadcastMonitor
    ): TimeZoneMonitor
}
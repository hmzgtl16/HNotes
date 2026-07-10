package com.example.hnotes.feature.note.impl.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.feature.note.impl.navigation.noteEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object NoteModule {

    @IntoSet
    @Provides
    fun provideNoteEntryBuilder() : EntryProviderScope<NavKey>.() -> Unit = {
        noteEntry()
    }
}
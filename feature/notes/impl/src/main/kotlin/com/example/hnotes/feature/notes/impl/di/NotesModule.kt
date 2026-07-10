package com.example.hnotes.feature.notes.impl.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.feature.notes.impl.navigation.notesEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object NotesModule {

    @IntoSet
    @Provides
    fun provideNotesEntryBuilder() : EntryProviderScope<NavKey>.() -> Unit = {
        notesEntry()
    }
}
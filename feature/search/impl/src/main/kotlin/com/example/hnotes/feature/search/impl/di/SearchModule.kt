package com.example.hnotes.feature.search.impl.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.feature.search.impl.navigation.searchEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object SearchModule {

    @IntoSet
    @Provides
    fun provideSearchEntryBuilder() : EntryProviderScope<NavKey>.() -> Unit = {
        searchEntry()
    }
}
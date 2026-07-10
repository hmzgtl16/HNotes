package com.example.hnotes.feature.settings.impl.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.feature.settings.impl.navigation.settingsEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object SettingsModule {

    @IntoSet
    @Provides
    fun provideSettingsEntryBuilder() : EntryProviderScope<NavKey>.() -> Unit = {
        settingsEntry()
    }
}
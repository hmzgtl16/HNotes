package com.example.hnotes.feature.label.impl.di

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.feature.label.impl.navigation.labelEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object LabelModule {
    @IntoSet
    @Provides
    fun provideLabelEntryBuilder() : EntryProviderScope<NavKey>.() -> Unit = {
        labelEntry()
    }
}
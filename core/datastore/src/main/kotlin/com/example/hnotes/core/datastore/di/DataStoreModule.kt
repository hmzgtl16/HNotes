package com.example.hnotes.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.hnotes.core.common.ApplicationScope
import com.example.hnotes.core.common.Dispatcher
import com.example.hnotes.core.common.HNotesDispatchers
import com.example.hnotes.core.datastore.UserPreferences
import com.example.hnotes.core.datastore.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(HNotesDispatchers.IO) dispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        userPreferencesSerializer: UserPreferencesSerializer,
    ): DataStore<UserPreferences> = DataStoreFactory
        .create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(context = scope.coroutineContext + dispatcher),
            produceFile = { context.dataStoreFile(fileName = "user_preferences.pb") }
        )
}


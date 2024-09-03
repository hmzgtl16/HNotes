package com.example.hnotes.core.notification.di

import android.content.Context
import com.example.hnotes.core.notification.Notifier
import com.example.hnotes.core.notification.NotifierImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideNotifier(
        @ApplicationContext context: Context
    ): Notifier = NotifierImpl(context = context)
}


package com.example.hnotes.core.alarm.di

import android.content.Context
import com.example.hnotes.core.alarm.AlarmScheduler
import com.example.hnotes.core.alarm.AlarmSchedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {

    @Provides
    @Singleton
    fun provideAlarmScheduler(
        @ApplicationContext context: Context
    ): AlarmScheduler = AlarmSchedulerImpl(context = context)
}
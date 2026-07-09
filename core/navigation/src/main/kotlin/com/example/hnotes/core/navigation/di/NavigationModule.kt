package com.example.hnotes.core.navigation.di

import com.example.hnotes.core.navigation.Navigator3
import com.example.hnotes.core.navigation.Navigator3Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NavigationModule {

    @Provides
    @Singleton
    internal fun bindsNavigator3(): Navigator3 = Navigator3Impl()
}
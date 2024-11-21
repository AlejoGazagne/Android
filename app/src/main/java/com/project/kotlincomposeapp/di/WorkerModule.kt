package com.project.kotlincomposeapp.di

import com.project.kotlincomposeapp.data.local.dao.EventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

    @Provides
    @Singleton
    fun provideCheckFavoritesWorkerFactory(
        eventDao: EventDao,
    ): EventNotificationFactory {
        return EventNotificationFactory(eventDao)
    }
}
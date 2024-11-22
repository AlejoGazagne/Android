package com.project.kotlincomposeapp.di

import com.project.kotlincomposeapp.data.local.dao.EventDao
import com.project.kotlincomposeapp.data.local.dao.NotificationDao
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
        notificationDao: NotificationDao
    ): EventNotificationFactory {
        return EventNotificationFactory(eventDao, notificationDao)
    }
}
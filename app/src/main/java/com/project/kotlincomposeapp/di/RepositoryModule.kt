package com.project.kotlincomposeapp.di

import com.project.kotlincomposeapp.data.repository.EventLocalStorageRepositoryImpl
import com.project.kotlincomposeapp.data.repository.FavoriteEventLocalStorageRepositoryImpl
import com.project.kotlincomposeapp.data.repository.NotificationLocalStorageRepositoryImpl
import com.project.kotlincomposeapp.data.repository.UserLocalStorageRepositoryImpl
import com.project.kotlincomposeapp.domain.repository.EventLocalStorageRepository
import com.project.kotlincomposeapp.domain.repository.FavoriteEventLocalStorageRepository
import com.project.kotlincomposeapp.domain.repository.NotificationLocalStorageRepository
import com.project.kotlincomposeapp.domain.repository.UserLocalStorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    // Provisión para UserLocalStorageRepository
    @Singleton
    @Binds
    abstract fun bindUserLocalStorageRepository(
        impl: UserLocalStorageRepositoryImpl
    ): UserLocalStorageRepository

    // Provisión para EventLocalStorageRepository
    @Singleton
    @Binds
    abstract fun bindEventLocalStorageRepository(
        impl: EventLocalStorageRepositoryImpl
    ): EventLocalStorageRepository

    // Provisión para NotificationLocalStorageRepository
    @Singleton
    @Binds
    abstract fun bindNotificationLocalStorageRepository(
        impl: NotificationLocalStorageRepositoryImpl
    ): NotificationLocalStorageRepository

    // Provisión para FavoriteEventLocalStorageRepository
    @Singleton
    @Binds
    abstract fun bindFavoriteEventLocalStorageRepository(
        impl: FavoriteEventLocalStorageRepositoryImpl
    ): FavoriteEventLocalStorageRepository
}
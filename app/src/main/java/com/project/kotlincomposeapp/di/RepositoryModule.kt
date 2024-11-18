package com.project.kotlincomposeapp.di

import com.project.kotlincomposeapp.data.repository.LocalStorageRepositoryImpl
import com.project.kotlincomposeapp.domain.repository.LocalStorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindLocalStorageRepository(localStorageRepository : LocalStorageRepositoryImpl): LocalStorageRepository
}
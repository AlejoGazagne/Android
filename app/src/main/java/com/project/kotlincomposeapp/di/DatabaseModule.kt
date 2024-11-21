package com.project.kotlincomposeapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.project.kotlincomposeapp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }


    @Singleton
    @Provides
    fun provideEventDao(appDatabase: AppDatabase) = appDatabase.eventDao()

    @Singleton
    @Provides
    fun provideNotificationDao(appDatabase: AppDatabase) = appDatabase.notificationDao()

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()

}
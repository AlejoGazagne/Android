package com.project.kotlincomposeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.kotlincomposeapp.data.local.dao.EventDao
import com.project.kotlincomposeapp.data.local.dao.NotificationDao
import com.project.kotlincomposeapp.data.local.entity.EventEntity
import com.project.kotlincomposeapp.data.local.entity.NotificationEntity

@Database(entities = [EventEntity::class, NotificationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun eventDao(): EventDao
    abstract fun notificationDao(): NotificationDao
}
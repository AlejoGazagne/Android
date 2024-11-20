package com.project.kotlincomposeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.kotlincomposeapp.data.local.entity.NotificationEntity

@Dao
interface NotificationDao {

    @Query("SELECT * FROM notifications WHERE isRead = 0")
    suspend fun getUnreadNotifications(): MutableList<NotificationEntity>

    @Query("SELECT * FROM notifications WHERE isDeleted = 0")
    suspend fun getNotifications(): MutableList<NotificationEntity>

    @Query("INSERT INTO notifications (title, message, date, isRead, isDeleted) VALUES (:title, :message, :date, :isRead, :isDeleted)")
    suspend fun saveNotification(title: String, message: String, date: String, isRead: Boolean, isDeleted: Boolean)

    @Query("UPDATE notifications SET isDeleted = :isDeleted WHERE title = :title AND message = :message AND date = :date AND isRead = :isRead")
    suspend fun deleteNotification(title: String, message: String, date: String, isRead: Boolean, isDeleted: Boolean)

    @Query("UPDATE notifications SET isRead = :isRead WHERE title = :title AND message = :message AND date = :date AND isDeleted = :isDeleted")
    suspend fun markNotificationAsRead(title: String, message: String, date: String, isRead: Boolean, isDeleted: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNotifications(notificationEntities: List<NotificationEntity>)
}
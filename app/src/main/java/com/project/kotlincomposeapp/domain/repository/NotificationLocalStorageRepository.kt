package com.project.kotlincomposeapp.domain.repository

import com.project.kotlincomposeapp.domain.model.NotificationModel

interface NotificationLocalStorageRepository {

    suspend fun getUnreadNotifications(): MutableList<NotificationModel>

    suspend fun getNotifications(): MutableList<NotificationModel>

    suspend fun markNotificationAsRead(notification: NotificationModel)

    suspend fun saveNotification(notification: NotificationModel)

    suspend fun deleteNotification(notification: NotificationModel)

}
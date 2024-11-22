package com.project.kotlincomposeapp.data.repository

import com.project.kotlincomposeapp.data.local.dao.NotificationDao
import com.project.kotlincomposeapp.data.local.entity.NotificationEntity
import com.project.kotlincomposeapp.data.remote.NotificationsApiService
import com.project.kotlincomposeapp.domain.model.NotificationModel
import com.project.kotlincomposeapp.domain.repository.NotificationLocalStorageRepository
import javax.inject.Inject

class NotificationLocalStorageRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao,
    private val notificationApi: NotificationsApiService
): NotificationLocalStorageRepository {
    // Notifications

    override suspend fun getUnreadNotifications(): MutableList<NotificationModel> {
        val notifications = notificationDao.getUnreadNotifications()
        val notificationModels = mutableListOf<NotificationModel>()
        notifications.forEach {
            notificationModels.add(
                NotificationModel(
                    it.title,
                    it.message,
                    it.date,
                    it.isRead,
                    it.isDeleted
                )
            )
        }
        return notificationModels
    }

    override suspend fun getNotifications(): MutableList<NotificationModel> {
        val apiNotifications = notificationApi.getNotifications()
        val dbNotifications = notificationDao.getNotifications()


        val notificationEntities = apiNotifications.map { apiNotification ->
            apiNotification.copy(isRead = dbNotifications.find { it.title == apiNotification.title }?.isRead ?: false)
        }

        notificationDao.saveNotifications(notificationEntities)

        return notificationDao.getNotifications().map { notificationEntity ->
            NotificationModel(
                notificationEntity.title,
                notificationEntity.message,
                notificationEntity.date,
                notificationEntity.isRead,
                notificationEntity.isDeleted
            )
        }.toMutableList()

    }

    override suspend fun markNotificationAsRead(notification: NotificationModel) {
        try {
            notificationDao.markNotificationAsRead(
                notification.title,
                notification.message,
                notification.date,
                true,
                notification.isDeleted
            )
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun saveNotification(notification: NotificationModel) {
        try {
            notificationDao.saveNotification(
                NotificationEntity(
                    0,
                    notification.title,
                    notification.message,
                    notification.date,
                    notification.isRead,
                    notification.isDeleted
                )
            )
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteNotification(notification: NotificationModel) {
        try {
            notificationDao.deleteNotification(
                notification.title,
                notification.message,
                notification.date,
                notification.isRead,
                true
            )
        } catch (e: Exception) {
            throw e
        }
    }
}
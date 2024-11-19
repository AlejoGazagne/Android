package com.project.kotlincomposeapp.data.repository

import com.project.kotlincomposeapp.data.local.dao.EventDao
import com.project.kotlincomposeapp.data.local.dao.NotificationDao
import com.project.kotlincomposeapp.data.local.dao.UserDao
import com.project.kotlincomposeapp.data.local.entity.UserEntity
import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.NotificationModel
import com.project.kotlincomposeapp.domain.model.UserModel
import com.project.kotlincomposeapp.domain.repository.LocalStorageRepository
import javax.inject.Inject

class LocalStorageRepositoryImpl @Inject constructor(private val EventDao: EventDao, private val NotificationDao: NotificationDao, private val userDao: UserDao) :
    LocalStorageRepository {

    // Events

    override suspend fun getEvents(): MutableList<EventModel> {
        val events = EventDao.getEvents()
        val eventModels = mutableListOf<EventModel>()
        events.forEach {
            eventModels.add(EventModel(it.title, it.date, it.location, it.image, it.capacity, it.organizer, it.isFavorite))
        }
        return eventModels
    }

    override suspend fun getEventByTitle(title: String): MutableList<EventModel> {
        val events = EventDao.getEventByTitle(title)
        val eventModels = mutableListOf<EventModel>()
        events.forEach {
            eventModels.add(EventModel(it.title, it.date, it.location, it.image, it.capacity, it.organizer, it.isFavorite))
        }
        return eventModels
    }

    override suspend fun getEventsByLocation(location: String): MutableList<EventModel> {
        val events = EventDao.getEventByLocation(location)
        val eventModels = mutableListOf<EventModel>()
        events.forEach {
            eventModels.add(EventModel(it.title, it.date, it.location, it.image, it.capacity, it.organizer, it.isFavorite))
        }
        return eventModels
    }

    override suspend fun getFavoriteEvents(): MutableList<EventModel> {
        val events = EventDao.getFavoriteEvents()
        val eventModels = mutableListOf<EventModel>()
        events.forEach {
            eventModels.add(EventModel(it.title, it.date, it.location, it.image, it.capacity, it.organizer, it.isFavorite))
        }
        return eventModels
    }

    override suspend fun toggleFavoriteEvent(event: EventModel) {
        try {
            EventDao.saveFavoriteEvent(event.title, event.date, event.location, event.image, event.capacity, event.organizer, !event.isFavorite)
        } catch (e: Exception) {
            throw e
        }

    }

    // Notifications

    override suspend fun getUnreadNotifications(): MutableList<NotificationModel> {
        val notifications = NotificationDao.getUnreadNotifications()
        val notificationModels = mutableListOf<NotificationModel>()
        notifications.forEach {
            notificationModels.add(NotificationModel(it.title, it.message, it.date, it.isRead, it.isDeleted))
        }
        return notificationModels
    }

    override suspend fun getNotifications(): MutableList<NotificationModel> {
        val notifications = NotificationDao.getNotifications()
        val notificationModels = mutableListOf<NotificationModel>()
        notifications.forEach {
            notificationModels.add(NotificationModel(it.title, it.message, it.date, it.isRead, it.isDeleted))
        }
        return notificationModels
    }

    override suspend fun markNotificationAsRead(notification: NotificationModel) {
        try {
            NotificationDao.markNotificationAsRead(notification.title, notification.message, notification.date, true, notification.isDeleted)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun saveNotification(notification: NotificationModel) {
        try {
            NotificationDao.saveNotification(notification.title, notification.message, notification.date, notification.isRead, notification.isDeleted)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteNotification(notification: NotificationModel) {
        try {
            NotificationDao.deleteNotification(notification.title, notification.message, notification.date, notification.isRead, true)
        } catch (e: Exception) {
            throw e
        }
    }

    // User
    override suspend fun getUser(email: String): UserModel {
        try {
            val userEntity = userDao.getUser(email)
            val userModel = UserModel(userEntity!!.name, userEntity.email, userEntity.password)
            return userModel
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateUser(user: UserModel) {
        try {
            userDao.updateUser(UserEntity(0, user.name, user.email, user.password))
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteAllUsers() {
        try {
            userDao.deleteAllUsers()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun insertUser(user: UserModel) {
        try {
            userDao.insertUser(UserEntity(0, user.name, user.email, user.password))
        } catch (e: Exception) {
            throw e
        }
    }
}
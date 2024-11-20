package com.project.kotlincomposeapp.data.repository

import android.util.Log
import com.project.kotlincomposeapp.data.local.dao.EventDao
import com.project.kotlincomposeapp.data.local.dao.NotificationDao
import com.project.kotlincomposeapp.data.local.dao.UserDao
import com.project.kotlincomposeapp.data.local.entity.UserEntity
import com.project.kotlincomposeapp.data.remote.EventApiService
import com.project.kotlincomposeapp.data.remote.NotificationsApiService
import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.NotificationModel
import com.project.kotlincomposeapp.domain.model.UserModel
import com.project.kotlincomposeapp.domain.repository.LocalStorageRepository
import javax.inject.Inject
import kotlin.math.log

class LocalStorageRepositoryImpl @Inject constructor(
    private val EventDao: EventDao,
    private val NotificationDao: NotificationDao,
    private val EventApi: EventApiService,
    private val NotificationApi: NotificationsApiService,
    private val userDao: UserDao
) : LocalStorageRepository {

    // Events

    override suspend fun getEvents(): MutableList<EventModel> {
        // Obtén los eventos de la API y los eventos existentes en la base de datos
        val apiEvents = EventApi.getEvents()
        val dbEvents = EventDao.getEvents().associateBy { it.id }

        // Mapear eventos de la API, preservando los favoritos de la base de datos
        val eventEntities = apiEvents.map { apiEvent ->
            apiEvent.copy(isFavorite = dbEvents[apiEvent.id]?.isFavorite ?: false)
        }

        // Guarda los eventos actualizados en la base de datos
        EventDao.saveEvents(eventEntities)

        // Convierte las entidades de la base de datos en modelos de aplicación
        return EventDao.getEvents().map { eventEntity ->
            EventModel(
                title = eventEntity.title,
                date = eventEntity.date,
                location = eventEntity.location,
                image = eventEntity.image,
                capacity = eventEntity.capacity,
                organizer = eventEntity.organizer,
                isFavorite = eventEntity.isFavorite
            )
        }.toMutableList()
    }


    override suspend fun getEventByTitle(title: String): EventModel {
        val event = EventDao.getEventByTitle(title)
        return EventModel(
            event.title,
            event.date,
            event.location,
            event.image,
            event.capacity,
            event.organizer,
            event.isFavorite
        )
    }

    override suspend fun getEventsByLocation(location: String): MutableList<EventModel> {
        val events = EventDao.getEventByLocation(location)
        val eventModels = mutableListOf<EventModel>()
        events.forEach {
            eventModels.add(
                EventModel(
                    it.title,
                    it.date,
                    it.location,
                    it.image,
                    it.capacity,
                    it.organizer,
                    it.isFavorite
                )
            )
        }
        return eventModels
    }

    override suspend fun getFavoriteEvents(): MutableList<EventModel> {
        val events = EventDao.getFavoriteEvents()
        val eventModels = mutableListOf<EventModel>()
        events.forEach {
            eventModels.add(
                EventModel(
                    it.title,
                    it.date,
                    it.location,
                    it.image,
                    it.capacity,
                    it.organizer,
                    it.isFavorite
                )
            )
        }
        return eventModels
    }

    override suspend fun toggleFavoriteEvent(event: EventModel) {
        try {
            EventDao.saveFavoriteEvent(
                event.title,
                !event.isFavorite
            )
        } catch (e: Exception) {
            throw e
        }

    }

    // Notifications

    override suspend fun getUnreadNotifications(): MutableList<NotificationModel> {
        val notifications = NotificationDao.getUnreadNotifications()
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
        val apiNotifications = NotificationApi.getNotifications()
        val dbNotifications = NotificationDao.getNotifications()


        val notificationEntities = apiNotifications.map { apiNotification ->
            apiNotification.copy(isRead = dbNotifications.find { it.title == apiNotification.title }?.isRead ?: false)
        }

        NotificationDao.saveNotifications(notificationEntities)

        return NotificationDao.getNotifications().map { notificationEntity ->
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
            NotificationDao.markNotificationAsRead(
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
            NotificationDao.saveNotification(
                notification.title,
                notification.message,
                notification.date,
                notification.isRead,
                notification.isDeleted
            )
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteNotification(notification: NotificationModel) {
        try {
            NotificationDao.deleteNotification(
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